package com.chest.currency.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chest.currency.dao.CashReceiptDaoImpl;
import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CoinsSequence;
import com.chest.currency.entity.model.DSB;
import com.chest.currency.entity.model.DSBAccountDetail;
import com.chest.currency.entity.model.DateRange;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.FreshFromRBI;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.Indent;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DenominationType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.Status;
import com.chest.currency.enums.YesNo;
import com.chest.currency.exception.BaseGuiException;
import com.chest.currency.jpa.persistence.converter.CurrencyFormatter;
import com.chest.currency.service.BinDashboardService;
import com.chest.currency.service.CashReceiptService;
import com.chest.currency.service.ICMCService;
import com.chest.currency.service.ProcessingRoomService;
import com.chest.currency.util.UtilityJpa;
import com.chest.currency.util.UtilityMapper;
import com.chest.currency.viewBean.IRVVoucherWrapper;
import com.mysema.query.Tuple;

@org.springframework.stereotype.Controller
public class CashReceiptController {

	private static final Logger LOG = LoggerFactory.getLogger(CashReceiptController.class);

	@Autowired
	BinDashboardService binDashboardService;

	@Autowired
	CashReceiptService cashReceiptService;

	@Autowired
	ICMCService icmcService;

	@Autowired
	CashReceiptDaoImpl cashReceiptDao;

	@Autowired
	ProcessingRoomService processingRoomService;

	@Autowired
	String prnFilePath;

	@RequestMapping(value = "/QRPath")
	@ResponseBody
	public List<String> processBranchReceipt(@RequestBody BranchReceipt branchReceipt, HttpSession session)
			throws Exception {

		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		StringBuilder sb = null;
		List<String> prnList = new ArrayList<>();
		StringBuilder sbBinName = new StringBuilder();
		List<BranchReceipt> shrinkBeanList = null;

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			LOG.info("branchReceipt " + branchReceipt);
			LOG.info("branchReceipt.isFromProcessingRoom() " + branchReceipt.isFromProcessingRoom());
			if (branchReceipt.isFromProcessingRoom()) {
				branchReceipt.setBundle(branchReceipt.getBundle().multiply(BigDecimal.valueOf(10)));
			}
			branchReceipt.setInsertBy(user.getId());
			branchReceipt.setUpdateBy(user.getId());
			branchReceipt.setInsertTime(now);
			branchReceipt.setUpdateTime(now);
			branchReceipt.setPendingBundleRequest(new BigDecimal(0));
			if (branchReceipt.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
				branchReceipt.setCurrencyType(CurrencyType.UNPROCESS);
			}
			if (branchReceipt.getProcessedOrUnprocessed().equalsIgnoreCase("PROCESSED")) {
				branchReceipt.setCurrencyType(branchReceipt.getCurrencyType());
			}
			shrinkBeanList = cashReceiptService.processBranchReceipt(branchReceipt, user);

			prnList.add(sbBinName.toString());
			boolean isAllSuccess = shrinkBeanList != null && shrinkBeanList.size() > 0;
			if (isAllSuccess) {
				for (BranchReceipt br : shrinkBeanList) {
					sbBinName.append(br.getBin()).append(",");
					try {
						String oldtext = readPRNFileData();
						if (branchReceipt.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
							String replacedtext = oldtext.replaceAll("bin", "" + br.getBin());
							replacedtext = replacedtext.replaceAll("branch", "" + br.getBranch());
							replacedtext = replacedtext.replaceAll("solId", "" + br.getSolId());
							replacedtext = replacedtext.replaceAll("denom", "" + br.getDenomination());
							replacedtext = replacedtext.replaceAll("bundle", "" + br.getBundle());

							String formattedTotal = CurrencyFormatter.inrFormatter(br.getTotal()).toString();
							replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

							sb = new StringBuilder(replacedtext);
							UtilityJpa.PrintToPrinter(sb, user);
						} else if (branchReceipt.getProcessedOrUnprocessed().equalsIgnoreCase("PROCESSED")) {
							for (int i = 0; i < branchReceipt.getBundle().intValue(); i++) {
								String replacedtext = oldtext.replaceAll("bin", "" + br.getCurrencyType());
								replacedtext = replacedtext.replaceAll("Bin: ", "" + "");
								replacedtext = replacedtext.replaceAll("Branch: ", "" + br.getBranch());
								replacedtext = replacedtext.replaceAll("Sol ID :", "" + "");
								replacedtext = replacedtext.replaceAll("branch", "" + br.getDenomination());
								replacedtext = replacedtext.replaceAll("solId", "" + br.getBin());
								replacedtext = replacedtext.replaceAll("denom", "" + br.getDenomination());
								replacedtext = replacedtext.replaceAll("bundle", "" + BigDecimal.ONE);

								String formattedTotal = CurrencyFormatter
										.inrFormatter(BigDecimal.valueOf(br.getDenomination() * 1000)).toString();
								replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

								sb = new StringBuilder(replacedtext);
								UtilityJpa.PrintToPrinter(sb, user);
							}
						}
						prnList.add(sb.toString());
						LOG.info("Branch Receipt PRN: " + sb);

						// UtilityJpa.PrintToPrinter(sb, user);

					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			} else {
				throw new RuntimeException("Error while saving saveTxListAndShrink, Please try again");
			}
			prnList.set(0, sbBinName.toString());
			LOG.info("BRANCH RECEIPT end");
		}
		return prnList;
	}

	@RequestMapping(value = "/RBIQRPath")
	@ResponseBody
	public List<String> processFreshFromRBI(@RequestBody FreshFromRBI fresh, HttpSession session,
			BinCategoryType binCategoryType, CashType cashType, CashSource cashSource) throws Exception {

		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		StringBuilder sb = null;
		List<String> prnList = new ArrayList<>();
		StringBuilder sbBinName = new StringBuilder();
		List<FreshFromRBI> freshList = null;

		synchronized (icmcService.getSynchronizedIcmc(user)) {

			fresh.setInsertBy(user.getId());
			fresh.setUpdateBy(user.getId());
			fresh.setInsertTime(now);
			fresh.setUpdateTime(now);
			fresh.setCurrencyType(CurrencyType.FRESH);
			fresh.setCashSource(CashSource.RBI);
			
			if (fresh.getNotesOrCoins().equalsIgnoreCase("NOTES")) {
				if (fresh.getBinOrBox().equalsIgnoreCase("BOX")) {
					fresh.setBinCategoryType(BinCategoryType.BOX);
					fresh.setCashType(CashType.NOTES);
				} else if (fresh.getBinOrBox().equalsIgnoreCase("BIN")) {
					fresh.setBinCategoryType(BinCategoryType.BIN);
					fresh.setCashType(CashType.NOTES);
				}
			} else if (fresh.getNotesOrCoins().equalsIgnoreCase("COINS")) {
				fresh.setCashType(CashType.COINS);
				CoinsSequence coinSequenceFromDB = cashReceiptService.getSequence(user.getIcmcId(),
						fresh.getDenomination());
				if (coinSequenceFromDB == null) {
					fresh.setBagSequenceFromDB(fresh.getBagSequenceFromDB());
				} else {
					fresh.setBagSequenceFromDB(coinSequenceFromDB.getSequence());
				}
			}

			freshList = cashReceiptService.processFreshFromRBI(fresh, user, YesNo.No, binCategoryType, cashType);
			boolean isAllSuccess = freshList != null && freshList.size() > 0;
			if (isAllSuccess) {
				for (FreshFromRBI freshFromRBI : freshList) {
					sbBinName.append(freshFromRBI.getBin()).append(",");

					if (freshFromRBI.getCashType().equals(CashType.NOTES)) {
						try {
							String oldtext = readPRNFileData();
							String replacedtext = UtilityMapper.getPRNToPrintForFreshNotes(freshFromRBI, oldtext);
							sb = new StringBuilder(replacedtext);
							prnList.add(sb.toString());

							LOG.info("Fresh From Notes RBI PRN: " + sb);
							UtilityJpa.PrintToPrinter(sb, user);

						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					} else if (freshFromRBI.getCashType().equals(CashType.COINS)) {
						for (int i = 1; i <= freshFromRBI.getNoOfBags(); i++) {
							try {
								String oldtext = readPRNFileData();
								int sequence = freshFromRBI.getBagSequenceFromDB() + i;
								String replacedtext = UtilityMapper.getPRNToPrintForFreshCoins(freshFromRBI, oldtext,
										sequence);
								sb = new StringBuilder(replacedtext);
								prnList.add(sb.toString());

								LOG.info("Fresh Coins From RBI PRN: " + sb);
								UtilityJpa.PrintToPrinter(sb, user);

							} catch (IOException ioe) {
								ioe.printStackTrace();
							}
						}
					}
				}
			} else {
				throw new RuntimeException("Problem while saving saveTxListAndFreshFromRBI, Please try again");
			}
			LOG.info("Fresh From RBI PRN: " + sbBinName.toString());
			// prnList.set(0, sbBinName.toString());
		}
		LOG.info("before Return RBI PRN: " + sbBinName.toString());
		prnList.add(sbBinName.toString());
		return prnList;
	}

	@RequestMapping(value = "/dirvQRPath")
	@ResponseBody
	public List<String> processDiversionIRV(@RequestBody DiversionIRV dirv, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		StringBuilder sb = null;
		List<String> prnList = new ArrayList<>();
		StringBuilder sbBinName = new StringBuilder();
		List<DiversionIRV> diversionList = null;

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			dirv.setInsertBy(user.getId());
			dirv.setUpdateBy(user.getId());
			dirv.setInsertTime(now);
			dirv.setUpdateTime(now);
			dirv.setBinCategoryType(dirv.getBinCategoryType());
			if (dirv.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
				dirv.setCurrencyType(CurrencyType.UNPROCESS);
			}
			if (dirv.getProcessedOrUnprocessed().equalsIgnoreCase("PROCESSED")) {
				dirv.setCurrencyType(dirv.getCurrencyType());
			}
			diversionList = cashReceiptService.processDiversionIRV(dirv, user);
			prnList.add(sbBinName.toString());

			boolean isAllSuccess = diversionList != null && diversionList.size() > 0;
			if (isAllSuccess) {
				for (DiversionIRV diversionIRV : diversionList) {
					sbBinName.append(diversionIRV.getBinNumber()).append(",");
					try {
						String oldtext = readPRNFileData();
						if (dirv.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
							String replacedtext = oldtext.replaceAll("bin", "" + diversionIRV.getBinNumber());
							replacedtext = replacedtext.replaceAll("Branch: ", "" + "Bank: ");
							replacedtext = replacedtext.replaceAll("Sol ID :", "" + "OrderNo: ");
							replacedtext = replacedtext.replaceAll("branch", "" + diversionIRV.getBankName());
							replacedtext = replacedtext.replaceAll("solId", "" + diversionIRV.getRbiOrderNo());
							replacedtext = replacedtext.replaceAll("denom", "" + diversionIRV.getDenomination());
							replacedtext = replacedtext.replaceAll("bundle", "" + diversionIRV.getBundle());

							String formattedTotal = CurrencyFormatter.inrFormatter(diversionIRV.getTotal()).toString();
							replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

							sb = new StringBuilder(replacedtext);
							UtilityJpa.PrintToPrinter(sb, user);

						} else if (dirv.getProcessedOrUnprocessed().equalsIgnoreCase("PROCESSED")) {
							for (int i = 0; i < dirv.getBundle().intValue(); i++) {
								String replacedtext = oldtext.replaceAll("bin", "" + diversionIRV.getCurrencyType());
								replacedtext = replacedtext.replaceAll("Bin: ", "" + "");
								replacedtext = replacedtext.replaceAll("Branch: ", "" + "");
								replacedtext = replacedtext.replaceAll("Sol ID :", "" + "");
								replacedtext = replacedtext.replaceAll("branch", "" + diversionIRV.getDenomination());
								replacedtext = replacedtext.replaceAll("solId", "" + diversionIRV.getBinNumber());
								replacedtext = replacedtext.replaceAll("denom", "" + diversionIRV.getDenomination());
								replacedtext = replacedtext.replaceAll("bundle", "" + BigDecimal.ONE);

								String formattedTotal = CurrencyFormatter
										.inrFormatter(BigDecimal.valueOf(diversionIRV.getDenomination() * 1000))
										.toString();
								replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

								sb = new StringBuilder(replacedtext);
								UtilityJpa.PrintToPrinter(sb, user);
							}
						}
						prnList.add(sb.toString());

						LOG.info("Diversion IRV PRN: " + sb);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			} else {
				throw new RuntimeException("Error while saving saveTxListAndDiversionIRV, Please try again");
			}
			prnList.set(0, sbBinName.toString());
			LOG.info("Diversion IRV ");
		}
		return prnList;
	}

	@RequestMapping(value = "/DSBQRPath")
	@ResponseBody
	public List<String> processDSB(@RequestBody DSB dsb, HttpSession session) throws Exception {

		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		StringBuilder sb = null;
		StringBuilder sbBinName = new StringBuilder();
		List<String> prnList = new ArrayList<>();
		List<DSB> dsbList = null;

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			prnList.add(sbBinName.toString());
			dsb.setInsertBy(user.getId());
			dsb.setUpdateBy(user.getId());
			dsb.setCashSource(CashSource.DSB);
			dsb.setInsertTime(now);
			dsb.setUpdateTime(now);
			if (dsb.getProcessingOrVault().equalsIgnoreCase("Vault")
					|| dsb.getProcessingOrVault().equalsIgnoreCase("BIN")) {
				dsb.setBinCategoryType(BinCategoryType.BIN);
			} else if (dsb.getProcessingOrVault().equalsIgnoreCase("BOX")) {
				dsb.setBinCategoryType(BinCategoryType.BOX);
			} else {
				dsb.setBinCategoryType(BinCategoryType.PROCESSING);
			}
			dsb.setCurrencyType(CurrencyType.UNPROCESS);
			LOG.info("dsb  Form Data getProcessingOrVault" + dsb.getProcessingOrVault());
			try {
				LOG.info("dsb from Form Data after Validation: " + dsb);
				dsbList = cashReceiptService.processDSB(dsb, user);
			} catch (Exception ex) {
				LOG.info("Error has occred " + ex);
				throw ex;
			}
			boolean isAllSuccess = dsbList != null && dsbList.size() > 0;
			LOG.info("isAllSuccess DSB " + isAllSuccess);
			if (isAllSuccess) {
				for (DSB dsbQR : dsbList) {
					sbBinName.append(dsbQR.getBin()).append(",");
					try {
						String oldtext = readPRNFileData();
						String replacedtext = oldtext.replaceAll("bin", "" + dsbQR.getBin());
						replacedtext = replacedtext.replaceAll("Branch: ", "" + "Name: ");
						replacedtext = replacedtext.replaceAll("Sol ID :", "" + "A/C No.: ");
						replacedtext = replacedtext.replaceAll("branch", "" + dsbQR.getName());
						replacedtext = replacedtext.replaceAll("solId", "" + dsbQR.getAccountNumber());
						replacedtext = replacedtext.replaceAll("denom", "" + dsbQR.getDenomination());
						replacedtext = replacedtext.replaceAll("bundle", "" + dsbQR.getBundle());

						String formattedTotal = CurrencyFormatter.inrFormatter(dsbQR.getTotal()).toString();
						replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

						sb = new StringBuilder(replacedtext);
						prnList.add(sb.toString());

						UtilityJpa.PrintToPrinter(sb, user);
						LOG.info("DSB prn: " + sb);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			} else {
				throw new BaseGuiException("Error while saving saveTxListAndDSB, Please try again");
			}
			prnList.set(0, sbBinName.toString());
			LOG.info("DSB end");
		}
		return prnList;
	}

	@RequestMapping("/Addshrink")
	public ModelAndView branchReceipt() {
		BranchReceipt obj = new BranchReceipt();
		return new ModelAndView("shrinkEntry", "user", obj);
	}

	@RequestMapping("/viewShrink")
	public ModelAndView branchReceiptList(HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		sDate.set(Calendar.HOUR, 0);
		sDate.set(Calendar.HOUR_OF_DAY, 0);
		sDate.set(Calendar.MINUTE, 0);
		sDate.set(Calendar.SECOND, 0);
		sDate.set(Calendar.MILLISECOND, 0);

		eDate.set(Calendar.HOUR, 24);
		eDate.set(Calendar.HOUR_OF_DAY, 23);
		eDate.set(Calendar.MINUTE, 59);
		eDate.set(Calendar.SECOND, 59);
		eDate.set(Calendar.MILLISECOND, 999);

		List<BranchReceipt> list = cashReceiptService.getBrachReceiptRecord(user, sDate, eDate);
		return new ModelAndView("viewShrink", "records", list);
	}

	@RequestMapping("/freshFromRbi")
	public ModelAndView freshFromRBI() {
		FreshFromRBI freshFromRBI = new FreshFromRBI();
		return new ModelAndView("freshFromRbi", "user", freshFromRBI);
	}

	@RequestMapping("/viewFresh")
	public ModelAndView viewFreshFromRBI(HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		sDate.set(Calendar.HOUR, 0);
		sDate.set(Calendar.HOUR_OF_DAY, 0);
		sDate.set(Calendar.MINUTE, 0);
		sDate.set(Calendar.SECOND, 0);
		sDate.set(Calendar.MILLISECOND, 0);

		eDate.set(Calendar.HOUR, 24);
		eDate.set(Calendar.HOUR_OF_DAY, 23);
		eDate.set(Calendar.MINUTE, 59);
		eDate.set(Calendar.SECOND, 59);
		eDate.set(Calendar.MILLISECOND, 999);

		List<FreshFromRBI> freshList = cashReceiptService.getFreshFromRBIRecord(user, sDate, eDate);
		return new ModelAndView("viewFresh", "records", freshList);
	}

	@RequestMapping("/editFreshRBI")
	public ModelAndView editFreshRBIReceipt(@RequestParam Long id, FreshFromRBI freshFromRBI, HttpSession session,
			RedirectAttributes redirectAttributes) {
		ModelMap model = new ModelMap();
		User user = (User) session.getAttribute("login");

		freshFromRBI = cashReceiptService.getFreshFromRBIRecordById(id, user.getIcmcId());

		model.put("user", freshFromRBI);
		return new ModelAndView("editFreshRBI", model);
	}

	@RequestMapping("/editCoFreshRBI")
	public ModelAndView editCoFreshRBIReceipt(@RequestParam Long id, FreshFromRBI freshFromRBI, HttpSession session,
			RedirectAttributes redirectAttributes) {
		ModelMap model = new ModelMap();
		User user = (User) session.getAttribute("login");

		freshFromRBI = cashReceiptService.getFreshFromRBIRecordById(id, user.getIcmcId());

		model.put("user", freshFromRBI);
		return new ModelAndView("editcoFreshRBI", model);
	}

	@RequestMapping("/updateFreshRBI")
	public ModelAndView updateFreshRBIData(FreshFromRBI freshRBIReceipt, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			FreshFromRBI freshRBIReceiptDb = cashReceiptService.getFreshFromRBIRecordById(freshRBIReceipt.getId(),
					user.getIcmcId());

			BinTransaction binTxn = cashReceiptService.getBinTxnRecordForRBIFreshedit(freshRBIReceiptDb,
					user.getIcmcId());
			if (binTxn == null || (binTxn.getPendingBundleRequest().compareTo(BigDecimal.ZERO) != 0)) {
				redirectAttributes.addFlashAttribute("errorMsg",
						"Selected record can't be edit,It has been alredy indent");
			} else {
				cashReceiptService.processForUpdatingFreshRBI(binTxn, freshRBIReceipt, freshRBIReceiptDb, user);
			}
		}
		return new ModelAndView("redirect:./viewFresh");
	}

	@RequestMapping("/editOtherBank")
	public ModelAndView editOtherBankReceipt(@RequestParam Long id, BankReceipt bankReceipt, HttpSession session,
			RedirectAttributes redirectAttributes) {
		ModelMap model = new ModelMap();
		User user = (User) session.getAttribute("login");
		bankReceipt = cashReceiptService.getBankReceiptRecordById(id, user.getIcmcId());

		Indent indent = processingRoomService.getUpdateIndentOtherBankRequest(bankReceipt, user.getIcmcId());

		if ((bankReceipt.getBinCategoryType() != null
				&& bankReceipt.getBinCategoryType().equals(BinCategoryType.PROCESSING) && indent != null
				&& indent.getPendingBundleRequest().compareTo(bankReceipt.getBundle()) != 0)
				|| (bankReceipt != null && bankReceipt.getBinNumber() != null && bankReceipt.getIsIndent())) {
			redirectAttributes.addFlashAttribute("errorMsg",
					"Machine Allocated or Indent Requested record can't be edited");
			return new ModelAndView("redirect:./viewBankReceipt");
		} else {
			model.put("user", bankReceipt);
		}

		return new ModelAndView("editOtherBank", model);
	}

	@RequestMapping("/updateOtherBankData")
	@ResponseBody
	public ModelAndView updateOtherBankData(BankReceipt otherBankReceipt, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {

		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			BankReceipt otherBankReceiptDb = cashReceiptService.getBankReceiptRecordById(otherBankReceipt.getId(),
					user.getIcmcId());

			Indent indent = processingRoomService.getUpdateIndentOtherBankRequest(otherBankReceiptDb, user.getIcmcId());

			if ((otherBankReceiptDb.getBinCategoryType() != null
					&& otherBankReceiptDb.getBinCategoryType().equals(BinCategoryType.PROCESSING) && indent != null
					&& indent.getPendingBundleRequest().compareTo(otherBankReceiptDb.getBundle()) != 0)
					|| (otherBankReceiptDb != null && otherBankReceiptDb.getBinNumber() != null
							&& otherBankReceiptDb.getIsIndent())) {
				redirectAttributes.addFlashAttribute("errorMsg",
						"Machine Allocated or Indent Requested record can't be edited");
				return new ModelAndView("redirect:./viewBankReceipt");
			}

			else if (otherBankReceiptDb.getBinCategoryType() == BinCategoryType.PROCESSING) {
				Indent indent2 = processingRoomService.getUpdateIndentOtherBankRequest(otherBankReceiptDb,
						user.getIcmcId());
				cashReceiptService.processForUpdatingIndentOtherBankReceipt(otherBankReceipt, otherBankReceiptDb,
						indent2, user);
			} else {

				BinTransaction binTxn = cashReceiptService.getBinTxnRecordForBankReceiptedit(otherBankReceipt,
						user.getIcmcId());
				cashReceiptService.processForUpdatingBankReceipt(binTxn, otherBankReceipt, otherBankReceiptDb, user);
			}

		}

		return new ModelAndView("redirect:./viewBankReceipt");

	}

	@RequestMapping("/Dirv")
	public ModelAndView DIRV() {
		DiversionIRV obj = new DiversionIRV();
		return new ModelAndView("Dirv", "user", obj);
	}

	@RequestMapping("/viewDirv")
	public ModelAndView viewDiversionIRV(HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		sDate.set(Calendar.HOUR, 0);
		sDate.set(Calendar.HOUR_OF_DAY, 0);
		sDate.set(Calendar.MINUTE, 0);
		sDate.set(Calendar.SECOND, 0);
		sDate.set(Calendar.MILLISECOND, 0);

		eDate.set(Calendar.HOUR, 24);
		eDate.set(Calendar.HOUR_OF_DAY, 23);
		eDate.set(Calendar.MINUTE, 59);
		eDate.set(Calendar.SECOND, 59);
		eDate.set(Calendar.MILLISECOND, 999);

		List<DiversionIRV> diversionIRVList = cashReceiptService.getDiversionIRVRecord(user, sDate, eDate);
		return new ModelAndView("viewDirv", "records", diversionIRVList);
	}

	@RequestMapping("/DSB")
	public ModelAndView DSB(HttpSession session) {
		DSB obj = new DSB();
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		List<DSBAccountDetail> dsbAcountDetialList = cashReceiptService.getDSBAccountDetail(user.getIcmcId());
		map.put("user", obj);
		map.put("dsbAccount", dsbAcountDetialList);
		return new ModelAndView("DSB", map);
	}

	@RequestMapping("/viewDSB")
	public ModelAndView viewDSB(HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		sDate.set(Calendar.HOUR, 0);
		sDate.set(Calendar.HOUR_OF_DAY, 0);
		sDate.set(Calendar.MINUTE, 0);
		sDate.set(Calendar.SECOND, 0);
		sDate.set(Calendar.MILLISECOND, 0);

		eDate.set(Calendar.HOUR, 24);
		eDate.set(Calendar.HOUR_OF_DAY, 23);
		eDate.set(Calendar.MINUTE, 59);
		eDate.set(Calendar.SECOND, 59);
		eDate.set(Calendar.MILLISECOND, 999);

		List<DSB> dsbList = cashReceiptService.getDSBRecord(user, sDate, eDate);
		return new ModelAndView("viewDSB", "records", dsbList);
	}

	@RequestMapping("/viewBankReceipt")
	public ModelAndView viewOtherBankReceipt(HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		sDate.set(Calendar.HOUR, 0);
		sDate.set(Calendar.HOUR_OF_DAY, 0);
		sDate.set(Calendar.MINUTE, 0);
		sDate.set(Calendar.SECOND, 0);
		sDate.set(Calendar.MILLISECOND, 0);

		eDate.set(Calendar.HOUR, 24);
		eDate.set(Calendar.HOUR_OF_DAY, 23);
		eDate.set(Calendar.MINUTE, 59);
		eDate.set(Calendar.SECOND, 59);
		eDate.set(Calendar.MILLISECOND, 999);

		List<BankReceipt> list = cashReceiptService.getBankReceiptRecord(user, sDate, eDate);
		return new ModelAndView("viewBankReceipt", "records", list);
	}

	@RequestMapping("/AddOtherBankReceipt")
	public ModelAndView addOtherICMCReceipt() {
		BankReceipt obj = new BankReceipt();
		ModelMap map = new ModelMap();
		List<ICMC> icmcList = cashReceiptService.getICMCName();
		map.put("user", obj);
		map.put("icmc", icmcList);
		return new ModelAndView("AddOtherBankReceipt", map);
	}

	@RequestMapping(value = "/bankReceipt")
	@ResponseBody
	public List<String> processOtherBankReceipt(@RequestBody BankReceipt bankReceipt, HttpSession session)
			throws Exception {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();

		StringBuilder sb = null;
		StringBuilder sbBinName = new StringBuilder();
		List<String> prnList = new ArrayList<>();
		List<BankReceipt> otherBankReceiptList = null;

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			LOG.info("Other Bank Receipt bankReceipt " + bankReceipt);
			bankReceipt.setInsertBy(user.getId());
			bankReceipt.setUpdateBy(user.getId());
			bankReceipt.setInsertTime(now);
			bankReceipt.setUpdateTime(now);
			bankReceipt.setStatus(1);
			bankReceipt.setCurrencyType(CurrencyType.UNPROCESS);
			bankReceipt.setBinCategoryType(bankReceipt.getBinCategoryType());

			otherBankReceiptList = cashReceiptService.processBankReceipt(bankReceipt, user);
			boolean isAllSuccess = otherBankReceiptList != null && otherBankReceiptList.size() > 0;
			if (isAllSuccess) {
				for (BankReceipt otherBankReceipt : otherBankReceiptList) {
					sbBinName.append(otherBankReceipt.getBinNumber()).append(",");
					try {
						String oldtext = readPRNFileData();
						String replacedtext = oldtext.replaceAll("bin", "" + otherBankReceipt.getBinNumber());
						replacedtext = replacedtext.replaceAll("Branch: ", "" + "Bank: ");
						replacedtext = replacedtext.replaceAll("Sol ID :", "" + "UTR No: ");
						replacedtext = replacedtext.replaceAll("branch", "" + otherBankReceipt.getBankName());
						replacedtext = replacedtext.replaceAll("solId", "" + otherBankReceipt.getRtgsUTRNo());
						replacedtext = replacedtext.replaceAll("denom", "" + otherBankReceipt.getDenomination());
						replacedtext = replacedtext.replaceAll("bundle", "" + otherBankReceipt.getBundle());
						// replacedtext = replacedtext.replaceAll("total", "" +
						// otherBankReceipt.getTotal());

						String formattedTotal = CurrencyFormatter.inrFormatter(otherBankReceipt.getTotal()).toString();
						replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

						sb = new StringBuilder(replacedtext);
						prnList.add(sb.toString());

						UtilityJpa.PrintToPrinter(sb, user);
						LOG.info("Other Bank Receipt PRN: " + sb);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			} else {
				throw new RuntimeException("Error while saving Other Bank Receipt, Please try again");
			}
			LOG.info("Other Bank Receipt sbBinName.toString() " + sbBinName.toString());

			LOG.info("Other Bank Receipt return " + prnList);
		}
		prnList.add(sbBinName.toString());
		// prnList.set(0, sbBinName.toString());
		return prnList;
	}

	@RequestMapping(value = "/branchName")
	@ResponseBody
	public String branchName(@RequestParam(value = "solid") String solId) {
		String branchName = cashReceiptService.getBranchNameBySolId(solId);
		return branchName;
	}

	@RequestMapping("/IRVReports")
	public ModelAndView IRVReports(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();

		LOG.info("From Date: " + dateRange.getFromDate());
		LOG.info("To Date: " + dateRange.getToDate());

		// orv
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}
		sDate.set(Calendar.HOUR, 0);
		sDate.set(Calendar.HOUR_OF_DAY, 0);
		sDate.set(Calendar.MINUTE, 0);
		sDate.set(Calendar.SECOND, 0);
		sDate.set(Calendar.MILLISECOND, 0);

		eDate.set(Calendar.HOUR, 24);
		eDate.set(Calendar.HOUR_OF_DAY, 23);
		eDate.set(Calendar.MINUTE, 59);
		eDate.set(Calendar.SECOND, 59);
		eDate.set(Calendar.MILLISECOND, 999);

		List<Tuple> IRVList = cashReceiptService.getIRVReportRecord(user.getIcmcId(), sDate, eDate);
		List<BranchReceipt> branchReceipts = new ArrayList<>();
		for (Tuple t : IRVList) {
			BranchReceipt branchReceipt = new BranchReceipt();
			branchReceipt.setSolId(t.get(0, String.class));
			branchReceipt.setBranch(t.get(1, String.class));
			branchReceipt.setSrNumber(t.get(2, String.class));
			branchReceipt.setTotal(t.get(3, BigDecimal.class));
			branchReceipts.add(branchReceipt);
		}
		map.put("branchReceipts", branchReceipts);

		List<Tuple> IRVListforDsb = cashReceiptService.getIRVReportRecordsForDSb(user.getIcmcId(), sDate, eDate);
		ICMC icmc = binDashboardService.getICMCObj(user.getIcmcId());
		List<DSB> dsbs = new ArrayList<>();
		for (Tuple t : IRVListforDsb) {
			DSB dsb = new DSB();
			dsb.setName(t.get(2, String.class));
			dsb.setAccountNumber(t.get(3, String.class));
			dsb.setTotal(t.get(4, BigDecimal.class));
			dsb.setLinkBranchSolId(icmc.getLinkBranchSolId());
			dsbs.add(dsb);
		}
		map.put("dsbs", dsbs);

		List<Tuple> IRVListforBankReceipts = cashReceiptService.getIRVReportRecordForOtherBanks(user.getIcmcId(), sDate,
				eDate);
		List<BankReceipt> bankReceipts = new ArrayList<>();
		for (Tuple t : IRVListforBankReceipts) {
			BankReceipt bankReceipt = new BankReceipt();
			bankReceipt.setBankName(t.get(0, String.class));
			bankReceipt.setSolId(t.get(1, String.class));
			bankReceipt.setBranch(t.get(2, String.class));
			bankReceipt.setRtgsUTRNo(t.get(3, String.class));
			bankReceipt.setTotal(t.get(4, BigDecimal.class));
			bankReceipts.add(bankReceipt);
		}
		map.put("bankReceipts", bankReceipts);

		BigDecimal deno1 = BigDecimal.ZERO;
		BigDecimal deno2 = BigDecimal.ZERO;
		BigDecimal deno5 = BigDecimal.ZERO;
		BigDecimal deno10 = BigDecimal.ZERO;
		BigDecimal deno20 = BigDecimal.ZERO;
		BigDecimal deno50 = BigDecimal.ZERO;
		BigDecimal deno100 = BigDecimal.ZERO;
		BigDecimal deno200 = BigDecimal.ZERO;
		BigDecimal deno500 = BigDecimal.ZERO;
		BigDecimal deno1000 = BigDecimal.ZERO;
		BigDecimal deno2000 = BigDecimal.ZERO;
		BigDecimal sum = BigDecimal.ZERO;

		List<Tuple> ibitList = cashReceiptService.getIBITForIRV(user.getIcmcId(), sDate, eDate);

		for (Tuple tuple : ibitList) {
			if (tuple.get(0, Integer.class).equals(2000)) {
				deno2000 = tuple.get(1, BigDecimal.class).multiply(new BigDecimal(2000));
			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				deno1000 = tuple.get(1, BigDecimal.class).multiply(new BigDecimal(1000));
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				deno500 = tuple.get(1, BigDecimal.class).multiply(new BigDecimal(500));
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				deno200 = tuple.get(1, BigDecimal.class).multiply(new BigDecimal(200));
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				deno100 = tuple.get(1, BigDecimal.class).multiply(new BigDecimal(100));
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				deno50 = tuple.get(1, BigDecimal.class).multiply(new BigDecimal(50));
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				deno20 = tuple.get(1, BigDecimal.class).multiply(new BigDecimal(20));
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				deno10 = tuple.get(1, BigDecimal.class).multiply(new BigDecimal(10));
			}
			if (tuple.get(0, Integer.class).equals(5)) {
				deno5 = tuple.get(1, BigDecimal.class).multiply(new BigDecimal(5));
			}
			if (tuple.get(0, Integer.class).equals(2)) {
				deno2 = tuple.get(1, BigDecimal.class).multiply(new BigDecimal(2));
			}
			if (tuple.get(0, Integer.class).equals(1)) {
				deno1 = tuple.get(1, BigDecimal.class).multiply(new BigDecimal(1));
			}
			sum = deno1.add(deno2).add(deno5).add(deno10).add(deno20).add(deno50).add(deno100).add(deno200).add(deno500)
					.add(deno1000).add(deno2000);
		}

		map.put("sum", sum);

		String linkBranchSolID = cashReceiptService.getLinkBranchSolID(user.getIcmcId().longValue());

		String servicingICMC = cashReceiptService.getServicingICMC(linkBranchSolID);

		map.put("servicingICMC", servicingICMC);

		map.put("linkBranchSolID", linkBranchSolID);

		return new ModelAndView("IRVReports", map);
	}

	@RequestMapping("/IRVVoucher")
	public ModelAndView IRVVoucher(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		BranchReceipt obj = new BranchReceipt();
		List<BranchReceipt> branchList = cashReceiptService.getBrachFromBranchReceipt(user.getIcmcId());
		map.put("records", branchList);
		map.put("user", obj);
		return new ModelAndView("IRVVoucher", map);
	}

	@RequestMapping("/viewIRVVoucher")
	public ModelAndView viewIRVVoucher(@RequestParam(value = "solId") String solId, HttpSession session) {
		ModelMap map = new ModelMap();
		List<Tuple> voucherList = cashReceiptService.getVoucherRecord(solId);

		IRVVoucherWrapper iRVVoucherWrapper = UtilityJpa.prepareBranchReceiptIRVVoucher(voucherList);

		map.put("iRVVoucherWrapper", iRVVoucherWrapper);
		map.put("records", iRVVoucherWrapper.getBranchReceipts());
		return new ModelAndView("viewIRVVoucher", map);
	}

	@RequestMapping(value = "/getAccountNumber")
	@ResponseBody
	public String getAccountNumber(@RequestParam(value = "name") String name, HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<String> accountNumber = cashReceiptService.getAccountNumberForDSB(name, user.getIcmcId());
		return accountNumber.toString();
	}

	@RequestMapping("/processFreshFromRBI")
	public ModelAndView processFreshFromRBI(HttpSession session) {
		FreshFromRBI obj = new FreshFromRBI();
		ModelMap map = new ModelMap();
		/* map.put("denominationList", DenominationType.values()); */
		User user = (User) session.getAttribute("login");
		List<Tuple> freshListFromIndent = cashReceiptService.getDataFromIndentForFreshProcessing(user.getIcmcId());
		map.put("user", obj);
		map.put("freshList", freshListFromIndent);
		return new ModelAndView("processFreshFromRBI", map);
	}

	@ExceptionHandler(BaseGuiException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public BaseGuiException handleException(BaseGuiException ex, HttpServletResponse response) throws IOException {
		return ex;
	}

	@RequestMapping(value = "/RBIQRPathForProcess")
	@ResponseBody
	public List<String> processFreshFromRBIAfterCounting(@RequestBody FreshFromRBI freshFromRBi, HttpSession session) {
		List<FreshFromRBI> freshList = null;
		StringBuilder sb = null;
		StringBuilder sbBinName = new StringBuilder();
		List<String> prnList = new ArrayList<>();
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			freshFromRBi.setInsertBy(user.getId());
			freshFromRBi.setUpdateBy(user.getId());
			freshFromRBi.setInsertTime(now);
			freshFromRBi.setUpdateTime(now);
			freshFromRBi.setCurrencyType(CurrencyType.FRESH);
			freshFromRBi.setCashSource(CashSource.RBI);
			try {
				freshList = cashReceiptService.processFreshFromRBIAfterCounting(freshFromRBi, user);
			} catch (Exception ex) {
				LOG.info("Error has occred", ex);
				throw ex;
			}

			boolean isAllSuccess = freshList != null && freshList.size() > 0;
			if (isAllSuccess) {
				for (FreshFromRBI freshFromRBIQR : freshList) {
					sbBinName.append(freshFromRBIQR.getBin()).append(",");
					try {
						String oldtext = readPRNFileData();
						for (int i = 0; i < freshFromRBIQR.getBundle().intValue(); i++) {
							String replacedtext = oldtext.replaceAll("bin", "" + freshFromRBIQR.getCurrencyType());
							replacedtext = replacedtext.replaceAll("Bin: ", "" + "");
							replacedtext = replacedtext.replaceAll("Branch: ", "" + "");
							replacedtext = replacedtext.replaceAll("Sol ID :", "" + "");
							replacedtext = replacedtext.replaceAll("branch", "" + freshFromRBIQR.getDenomination());
							replacedtext = replacedtext.replaceAll("solId", "" + freshFromRBIQR.getBin());
							replacedtext = replacedtext.replaceAll("denom", "" + freshFromRBIQR.getDenomination());
							replacedtext = replacedtext.replaceAll("bundle", "" + BigDecimal.ONE);

							String formattedTotal = CurrencyFormatter
									.inrFormatter(BigDecimal.valueOf(freshFromRBIQR.getDenomination() * 1000))
									.toString();
							replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

							sb = new StringBuilder(replacedtext);
							prnList.add(sb.toString());
							LOG.info("Process FreshFromRBI O/P PRN  =" + sb);

							UtilityJpa.PrintToPrinter(sb, user);
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			} else {
				throw new BaseGuiException("Error while processing fresh from RBI, Please try again");
			}
			prnList.set(0, sbBinName.toString());
		}
		return prnList;
	}

	@RequestMapping("/createBox")
	public ModelAndView addBox() {
		ModelMap map = new ModelMap();
		BoxMaster obj = new BoxMaster();
		map.put("denominationList", DenominationType.values());
		map.put("cashSource", CashSource.values());
		map.put("currencyTypeList", CurrencyType.values());
		map.put("user", obj);
		return new ModelAndView("/createBox", map);
	}

	@RequestMapping("/saveBOX")
	public ModelAndView saveBOX(@ModelAttribute("user") BoxMaster boxMaster, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			boxMaster.setBoxName(boxMaster.getBoxName().replaceAll("\\s+", ""));
			Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
			Matcher matcher = pattern.matcher(boxMaster.getBoxName());
			boolean checkSpecialChar = matcher.find();
			if (checkSpecialChar == false && boxMaster.getBoxName() != null) {
				BoxMaster dbBoxName = cashReceiptService.isValidBox(user.getIcmcId(), boxMaster.getBoxName());
				BinMaster dbBinName = binDashboardService.isValidBin(user.getIcmcId(), boxMaster.getBoxName());
				if (dbBoxName != null) {
					redirectAttributes.addFlashAttribute("duplicateBox", "Box with this name already Exists..");
					return new ModelAndView("redirect:./createBox");
				}
				if (dbBinName != null) {
					redirectAttributes.addFlashAttribute("duplicateBox",
							"Box with this name already Exists in Bin list..");
					return new ModelAndView("redirect:./createBox");
				} else {
					boxMaster.setInsertBy(user.getId());
					boxMaster.setUpdateBy(user.getId());
					boxMaster.setIcmcId(user.getIcmcId());
					Calendar now = Calendar.getInstance();
					boxMaster.setInsertTime(now);
					boxMaster.setUpdateTime(now);
					boxMaster.setIsAllocated(0);
					boxMaster.setStatus(Status.ENABLED);
					cashReceiptService.saveBox(boxMaster);
					redirectAttributes.addFlashAttribute("successMsg", "New Box has been created successfully");
					return new ModelAndView("redirect:./viewBoxDetails");
				}
			}
			redirectAttributes.addFlashAttribute("duplicateBox", "Please Insert Currect Box name.");
			return new ModelAndView("redirect:./createBox");
		}
	}

	@RequestMapping("/viewBoxDetails")
	public ModelAndView viewAccountDetails(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<BoxMaster> boxDetailsList = cashReceiptService.boxMasterDetails(user.getIcmcId());
		return new ModelAndView("viewBoxDetails", "records", boxDetailsList);
	}

	@RequestMapping("/editBox")
	public ModelAndView editBox(@RequestParam Long id, BoxMaster boxMaster, HttpSession session,
			RedirectAttributes redirectAttributes) {
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");
		boxMaster = cashReceiptService.getBoxDetailsById(id);

		map.put("denominationList", DenominationType.values());
		map.put("currencyTypeList", CurrencyType.values());
		map.put("user", boxMaster);
		BinTransaction binTxn = cashReceiptService.getBinTxnRecordForUpdateBox(boxMaster, user.getIcmcId());
		if (binTxn != null) {
			redirectAttributes.addFlashAttribute("errorMsg",
					"Selected record can't be edited since it has been already get BIN to BOX..");
			return new ModelAndView("redirect:./viewBoxDetails");
		}

		return new ModelAndView("editBox", map);
	}

	@RequestMapping("/updateBox")
	public ModelAndView updateBox(@ModelAttribute("user") BoxMaster boxMaster, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			boxMaster.setUpdateBy(user.getId());
			boxMaster.setUpdateTime(now);
			BoxMaster boxMasterDB = cashReceiptService.getBoxDetailsById(boxMaster.getId());

			boxMaster.setIcmcId(boxMasterDB.getIcmcId());
			boxMaster.setIsAllocated(boxMasterDB.getIsAllocated());
			boxMaster.setInsertBy(boxMasterDB.getInsertBy());
			boxMaster.setInsertTime(boxMasterDB.getInsertTime());
			cashReceiptService.updateBoxDetails(boxMaster);
			redirectAttributes.addFlashAttribute("updateMsg", "Box details has been updated successfully");
		}
		return new ModelAndView("redirect:./viewBoxDetails");
	}

	@RequestMapping("/editShrinkEntry")
	@ResponseBody
	public ModelAndView editShrinkEntry(@RequestParam Long id, BranchReceipt branchReceipt, HttpSession session,
			RedirectAttributes redirectAttributes) {
		ModelMap model = new ModelMap();
		User user = (User) session.getAttribute("login");
		branchReceipt = cashReceiptService.getBranchReceiptRecordById(id, user.getIcmcId());

		if (branchReceipt.getStatus().equals(OtherStatus.RECEIVED)) {
			BigDecimal bundle = branchReceipt.getBundle();
			String processUnprocess = branchReceipt.getProcessedOrUnprocessed();
			model.put("bundle", bundle);
			model.put("user", branchReceipt);
			model.put("processUnprocess", processUnprocess);
		} else {
			redirectAttributes.addFlashAttribute("errorMsg",
					"Selected record can't be edited since it has been already indented..");
			return new ModelAndView("redirect:./viewShrink");
		}
		return new ModelAndView("editShrinkEntry", model);
	}

	@RequestMapping("/editShrinkPrEntry")
	@ResponseBody
	public ModelAndView editShrinkPrEntry(@RequestParam Long id, BranchReceipt branchReceipt, HttpSession session,
			RedirectAttributes redirectAttributes) {
		ModelMap model = new ModelMap();
		User user = (User) session.getAttribute("login");
		branchReceipt = cashReceiptService.getBranchReceiptRecordById(id, user.getIcmcId());

		if (branchReceipt.getStatus().equals(OtherStatus.RECEIVED)) {
			BigDecimal packets = branchReceipt.getBundle().multiply(BigDecimal.TEN);
			String processUnprocess = branchReceipt.getProcessedOrUnprocessed();
			model.put("packets", packets);
			model.put("user", branchReceipt);
			model.put("processUnprocess", processUnprocess);
		} else {
			redirectAttributes.addFlashAttribute("errorMsg",
					"Selected record can't be edited since it has been already indented..");
			return new ModelAndView("redirect:./viewShrink");
		}
		return new ModelAndView("editShrinkPrEntry", model);
	}

	@RequestMapping("/updateShrinkEntry")
	@ResponseBody
	public List<String> updateShrinkEntry(@RequestBody BranchReceipt branchReceipt, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		List<BranchReceipt> branchReceiptList = new ArrayList<>();

		StringBuilder sb = null;
		List<String> prnList = new ArrayList<>();
		StringBuilder sbBinName = new StringBuilder();

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			BranchReceipt branchReceiptDb = cashReceiptService.getBranchReceiptRecordById(branchReceipt.getId(),
					user.getIcmcId());
			if (!branchReceiptDb.getStatus().equals(OtherStatus.RECEIVED)) {
				redirectAttributes.addFlashAttribute("errorMsg",
						"Selected record can't be edited since it has been already indented..");
				prnList.add("Selected record can't be edited since it has been already indented");
				return prnList;
			} else {

				BinTransaction binTxn = cashReceiptService.getBinTxnRecordForUpdateedit(branchReceiptDb,
						user.getIcmcId());
				branchReceiptList = cashReceiptService.processForUpdatingShrinkEntry(binTxn, branchReceipt,
						branchReceiptDb, user);
				prnList.add(sbBinName.toString());

				boolean isAllSuccess = branchReceiptList != null && branchReceiptList.size() > 0;
				if (isAllSuccess) {
					for (BranchReceipt brTemp : branchReceiptList) {
						sbBinName.append(brTemp.getBin()).append(",");
						try {
							String oldtext = readPRNFileData();
							String replacedtext = oldtext.replaceAll("bin", "" + brTemp.getBin());
							replacedtext = replacedtext.replaceAll("branch", "" + brTemp.getBranch());
							replacedtext = replacedtext.replaceAll("solId", "" + brTemp.getSolId());
							replacedtext = replacedtext.replaceAll("denom", "" + brTemp.getDenomination());
							replacedtext = replacedtext.replaceAll("bundle", "" + brTemp.getBundle());

							String formattedTotal = CurrencyFormatter.inrFormatter(brTemp.getTotal()).toString();
							replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

							sb = new StringBuilder(replacedtext);
							prnList.add(sb.toString());
							LOG.info("Branch Receipt PRN: " + sb);

							UtilityJpa.PrintToPrinter(sb, user);

						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}
				} else {
					throw new BaseGuiException("Error while updating shrink entry, Please try again");
				}
				prnList.set(0, sbBinName.toString());
			}
			return prnList;
		}
	}

	@RequestMapping("/editDSB")
	@ResponseBody
	public ModelAndView editDSB(@RequestParam Long id, DSB dsb, HttpSession session,
			RedirectAttributes redirectAttributes) {
		ModelMap model = new ModelMap();
		User user = (User) session.getAttribute("login");
		dsb = cashReceiptService.getDSBReceiptRecordById(id, user.getIcmcId());

		Indent indent = processingRoomService.getUpdateIndentRequest(dsb, user.getIcmcId());

		if ((dsb != null && dsb.getProcessingOrVault().equalsIgnoreCase("processing") && indent != null
				&& indent.getPendingBundleRequest().compareTo(dsb.getBundle()) != 0)
				|| (dsb != null && dsb.getProcessingOrVault().equalsIgnoreCase("vault") && dsb.getIsIndent())) {
			redirectAttributes.addFlashAttribute("errorMsg",
					"Machine Allocated or Indent Requested record can't be edited");
			return new ModelAndView("redirect:./viewDSB");
		} else {
			List<DSBAccountDetail> dsbAcountDetialList = cashReceiptService.getDSBAccountDetail(user.getIcmcId());
			model.put("dsbAccount", dsbAcountDetialList);
			model.put("user", dsb);
		}

		return new ModelAndView("editDSB", model);
	}

	@RequestMapping("/updateDSB")
	@ResponseBody
	public ModelAndView updateDSB(DSB dsb, HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			DSB dsbdb = cashReceiptService.getDSBReceiptRecordById(dsb.getId(), user.getIcmcId());
			BinTransaction binTxn = new BinTransaction();
			Indent indent = processingRoomService.getUpdateIndentRequest(dsbdb, user.getIcmcId());
			if ((dsbdb != null && dsbdb.getProcessingOrVault().equalsIgnoreCase("processing") && indent != null
					&& indent.getPendingBundleRequest().compareTo(dsbdb.getBundle()) != 0)
					|| (dsbdb != null && dsbdb.getProcessingOrVault().equalsIgnoreCase("vault")
							&& dsbdb.getIsIndent())) {
				redirectAttributes.addFlashAttribute("errorMsg",
						"Machine Allocated or Indent Requested record can't be edited");
				return new ModelAndView("redirect:./viewDSB");
			} else if (dsbdb.getProcessingOrVault().equalsIgnoreCase("Vault")) {
				binTxn = cashReceiptService.getBinTxnRecordForUpdatingDSB(dsbdb, user.getIcmcId());
				cashReceiptService.processForUpdatingDSBReceipt(binTxn, dsb, dsbdb, user);
			}

			else if (dsbdb.getProcessingOrVault().equalsIgnoreCase("processing")) {
				Indent indent2 = processingRoomService.getUpdateIndentRequest(dsbdb, user.getIcmcId());
				cashReceiptService.processForUpdatingIndentDSBReceipt(binTxn, dsb, dsbdb, indent2, user);
			}
		}

		return new ModelAndView("redirect:./viewDSB");
	}

	@RequestMapping("/editDirv")
	public ModelAndView editDiversionReceipt(@RequestParam Long id, DiversionIRV diversionIRV, HttpSession session,
			RedirectAttributes redirectAttributes) {
		ModelMap model = new ModelMap();
		User user = (User) session.getAttribute("login");
		diversionIRV = cashReceiptService.getDiversionIRVRecordById(id, user.getIcmcId());

		Indent indent = processingRoomService.getUpdateIndentIVRRequest(diversionIRV, user.getIcmcId());

		if ((diversionIRV != null && diversionIRV.getBinNumber() == null && indent != null
				&& indent.getPendingBundleRequest().compareTo(diversionIRV.getBundle()) != 0)
				|| (diversionIRV != null && diversionIRV.getBinNumber() != null && diversionIRV.getIsIndent())) {
			redirectAttributes.addFlashAttribute("errorMsg",
					"Machine Allocated or Indent Requested record can't be edited");
			return new ModelAndView("redirect:./viewDirv");
		} else {
			model.put("user", diversionIRV);
		}
		return new ModelAndView("editDirv", model);
	}

	@RequestMapping("/updateDirv")
	@ResponseBody
	public ModelAndView updateDiversionReceipt(DiversionIRV diversionIRV, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		boolean isAllSuccess = false;
		Calendar now = Calendar.getInstance();

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			DiversionIRV diversionIRVDB = cashReceiptService.getDiversionIRVRecordById(diversionIRV.getId(),
					user.getIcmcId());
			Indent indent = processingRoomService.getUpdateIndentIVRRequest(diversionIRVDB, user.getIcmcId());

			if ((diversionIRVDB != null && diversionIRVDB.getBinNumber() == null && indent != null
					&& indent.getPendingBundleRequest().compareTo(diversionIRVDB.getBundle()) != 0)
					|| (diversionIRVDB != null && diversionIRVDB.getBinNumber() != null
							&& diversionIRVDB.getIsIndent())) {
				redirectAttributes.addFlashAttribute("errorMsg",
						"Machine Allocated or Indent Requested record can't be edited");
				return new ModelAndView("redirect:./viewDirv");
			} else if ((diversionIRVDB.getBinCategoryType() == BinCategoryType.BIN)
					|| (diversionIRVDB.getBinCategoryType() == BinCategoryType.BOX)) {
				BinTransaction binTxn = cashReceiptService.getBinTxnRecordForUpdatingDiversionReceipt(diversionIRVDB,
						user.getIcmcId());
				isAllSuccess = cashReceiptService.processForUpdatingDiversionReceipt(binTxn, diversionIRVDB, user);
				if (!isAllSuccess) {
					throw new BaseGuiException("Error while updating Diversion Receipt, Please try again");
				}
				diversionIRV.setInsertBy(user.getId());
				diversionIRV.setUpdateBy(user.getId());
				diversionIRV.setInsertTime(now);
				diversionIRV.setUpdateTime(now);
				diversionIRV.setOrderDate(diversionIRVDB.getOrderDate());
				diversionIRV.setExpiryDate(diversionIRVDB.getExpiryDate());
				diversionIRV.setBinCategoryType(diversionIRVDB.getBinCategoryType());
				diversionIRV.setCurrencyType(diversionIRVDB.getCurrencyType());
				diversionIRV.setProcessedOrUnprocessed(diversionIRVDB.getProcessedOrUnprocessed());
				cashReceiptService.processDiversionIRV(diversionIRV, user);
			} else {
				Indent indent2 = processingRoomService.getUpdateIndentIVRRequest(diversionIRVDB, user.getIcmcId());
				cashReceiptService.processForUpdatingIndentIVRReceipt(diversionIRV, diversionIRVDB, indent2, user);
			}

		}
		return new ModelAndView("redirect:./viewDirv");
	}

	@RequestMapping("/CashReceiptReports")
	public ModelAndView CashReceiptReports(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}
		sDate.set(Calendar.HOUR, 0);
		sDate.set(Calendar.HOUR_OF_DAY, 0);
		sDate.set(Calendar.MINUTE, 0);
		sDate.set(Calendar.SECOND, 0);
		sDate.set(Calendar.MILLISECOND, 0);

		eDate.set(Calendar.HOUR, 24);
		eDate.set(Calendar.HOUR_OF_DAY, 23);
		eDate.set(Calendar.MINUTE, 59);
		eDate.set(Calendar.SECOND, 59);
		eDate.set(Calendar.MILLISECOND, 999);

		List<Tuple> cashReceiptList = cashReceiptService.getIRVReportRecord(user.getIcmcId(), sDate, eDate);
		List<BranchReceipt> branchReceipts = new ArrayList<>();
		for (Tuple t : cashReceiptList) {
			BranchReceipt branchReceipt = new BranchReceipt();
			branchReceipt.setSolId(t.get(0, String.class));
			branchReceipt.setBranch(t.get(1, String.class));
			branchReceipt.setSrNumber(t.get(2, String.class));
			branchReceipt.setTotal(t.get(3, BigDecimal.class));
			branchReceipts.add(branchReceipt);
		}
		map.put("branchReceipts", branchReceipts);

		return new ModelAndView("CashReceiptReports", map);
	}

	@RequestMapping(value = "/getReceiptSequence")
	@ResponseBody
	public Integer getReceiptSequence(@RequestParam(value = "name") String name, HttpSession session) {
		User user = (User) session.getAttribute("login");
		DSB dsb = new DSB();
		dsb.setName(name);
		Integer sequence = cashReceiptService.getDSBReceiptSequence(user.getIcmcId(), dsb);
		if (sequence != null) {
			sequence++;
		} else {
			sequence = 1;
		}
		return sequence;
	}

	@RequestMapping("/rePrintBranchQR")
	public ModelAndView rePrintReceiptQR(@RequestParam Long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		StringBuilder sb = null;
		BranchReceipt br = cashReceiptService.getBranchReceiptRecordById(id, user.getIcmcId());
		try {
			String oldtext = readPRNFileData();
			String replacedtext = oldtext.replaceAll("bin", "" + br.getBin());
			replacedtext = replacedtext.replaceAll("branch", "" + br.getBranch());
			replacedtext = replacedtext.replaceAll("solId", "" + br.getSolId());
			replacedtext = replacedtext.replaceAll("denom", "" + br.getDenomination());
			replacedtext = replacedtext.replaceAll("bundle", "" + br.getBundle());

			String formattedTotal = CurrencyFormatter.inrFormatter(br.getTotal()).toString();
			replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

			sb = new StringBuilder(replacedtext);
			LOG.info("Branch Receipt PRN: " + sb);

			UtilityJpa.PrintToPrinter(sb, user);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return new ModelAndView("redirect:./viewShrink");
	}

	@RequestMapping("/rePrintDSBQR")
	public ModelAndView rePrintDSBReceiptQR(@RequestParam Long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		StringBuilder sb = null;
		DSB dsbQR = cashReceiptService.getDSBReceiptRecordById(id, user.getIcmcId());
		try {
			String oldtext = readPRNFileData();
			String replacedtext = oldtext.replaceAll("bin", "" + dsbQR.getBin());
			replacedtext = replacedtext.replaceAll("Branch: ", "" + "Name: ");
			replacedtext = replacedtext.replaceAll("Sol ID :", "" + "A/C No.: ");
			replacedtext = replacedtext.replaceAll("branch", "" + dsbQR.getName());
			replacedtext = replacedtext.replaceAll("solId", "" + dsbQR.getAccountNumber());
			replacedtext = replacedtext.replaceAll("denom", "" + dsbQR.getDenomination());
			replacedtext = replacedtext.replaceAll("bundle", "" + dsbQR.getBundle());

			String formattedTotal = CurrencyFormatter.inrFormatter(dsbQR.getTotal()).toString();
			replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);
			sb = new StringBuilder(replacedtext);
			LOG.info("DSB prn: " + sb);

			UtilityJpa.PrintToPrinter(sb, user);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return new ModelAndView("redirect:./viewDSB");
	}

	@RequestMapping("/rePrintDirvQR")
	public ModelAndView rePrintDirvQR(@RequestParam Long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		StringBuilder sb = null;
		DiversionIRV dirv = cashReceiptService.getDiversionIRVRecordById(id, user.getIcmcId());
		try {
			String oldtext = readPRNFileData();
			String replacedtext = oldtext.replaceAll("bin", "" + dirv.getBinNumber());
			replacedtext = replacedtext.replaceAll("Branch: ", "" + "Bank: ");
			replacedtext = replacedtext.replaceAll("Sol ID :", "" + "OrderNo: ");
			replacedtext = replacedtext.replaceAll("branch", "" + dirv.getBankName());
			replacedtext = replacedtext.replaceAll("solId", "" + dirv.getRbiOrderNo());
			replacedtext = replacedtext.replaceAll("denom", "" + dirv.getDenomination());
			replacedtext = replacedtext.replaceAll("bundle", "" + dirv.getBundle());

			String formattedTotal = CurrencyFormatter.inrFormatter(dirv.getTotal()).toString();
			replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);
			sb = new StringBuilder(replacedtext);

			LOG.info("Diversion IRV PRN: " + sb);
			UtilityJpa.PrintToPrinter(sb, user);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return new ModelAndView("redirect:./viewDirv");
	}

	@RequestMapping("/rePrintBankQR")
	public ModelAndView rePrintBankQR(@RequestParam Long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		StringBuilder sb = null;
		BankReceipt bankReceipt = cashReceiptService.getBankReceiptRecordById(id, user.getIcmcId());
		try {
			String oldtext = readPRNFileData();
			String replacedtext = oldtext.replaceAll("bin", "" + bankReceipt.getBinNumber());
			replacedtext = replacedtext.replaceAll("Branch: ", "" + "Bank: ");
			replacedtext = replacedtext.replaceAll("Sol ID :", "" + "UTR No: ");
			replacedtext = replacedtext.replaceAll("branch", "" + bankReceipt.getBankName());
			replacedtext = replacedtext.replaceAll("solId", "" + bankReceipt.getRtgsUTRNo());
			replacedtext = replacedtext.replaceAll("denom", "" + bankReceipt.getDenomination());
			replacedtext = replacedtext.replaceAll("bundle", "" + bankReceipt.getBundle());

			String formattedTotal = CurrencyFormatter.inrFormatter(bankReceipt.getTotal()).toString();
			replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);
			sb = new StringBuilder(replacedtext);
			LOG.info("Other Bank Receipt PRN: " + sb);
			UtilityJpa.PrintToPrinter(sb, user);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return new ModelAndView("redirect:./viewBankReceipt");
	}

	private String readPRNFileData() throws FileNotFoundException, IOException {
		File file = new File(prnFilePath);
		LOG.info("file for bank print " + file);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		LOG.info("reader " + reader);
		String line = "", oldtext = "";
		while ((line = reader.readLine()) != null) {
			oldtext += line + "\r\n";
		}
		reader.close();
		return oldtext;
	}

	@RequestMapping("/rePrintFreshQR")
	public ModelAndView rePrintFreshQR(@RequestParam Long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		StringBuilder sb = null;
		FreshFromRBI freshFromRBI = cashReceiptService.getFreshFromRBIRecordById(id, user.getIcmcId());
		if (freshFromRBI.getCashType().equals(CashType.NOTES)) {
			try {
				String oldtext = readPRNFileData();
				String replacedtext = UtilityMapper.getPRNToPrintForFreshNotes(freshFromRBI, oldtext);
				sb = new StringBuilder(replacedtext);
				LOG.info("Fresh Notes From RBI PRN: " + sb);
				UtilityJpa.PrintToPrinter(sb, user);

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		if (freshFromRBI.getCashType().equals(CashType.COINS)) {
			for (int i = 1; i <= freshFromRBI.getNoOfBags(); i++) {
				try {
					String oldtext = readPRNFileData();
					int sequence = freshFromRBI.getBagSequenceFromDB() + i;
					String replacedtext = UtilityMapper.getPRNToPrintForFreshCoins(freshFromRBI, oldtext, sequence);
					sb = new StringBuilder(replacedtext);
					LOG.info("Fresh Coins From RBI PRN: " + sb);
					UtilityJpa.PrintToPrinter(sb, user);

				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		return new ModelAndView("redirect:./viewFresh");
	}

}
