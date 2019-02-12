package com.chest.currency.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBigDecimal;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.BinStatus;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.Status;
import com.chest.currency.enums.YesNo;
import com.chest.currency.service.BinDashboardService;
import com.chest.currency.service.BinTransactionService;
import com.chest.currency.service.CashPaymentService;
import com.mysema.query.Tuple;

@Controller
public class MigrationController {

	private static final Logger LOG = LoggerFactory.getLogger(MigrationController.class);

	@Autowired
	BinTransactionService binTransactionService;

	@Autowired
	CashPaymentService cashPaymentService;

	@Autowired
	BinDashboardService binDashboardService;

	@Autowired
	String documentFilePath;

	@RequestMapping("/binTransaction")
	public ModelAndView viewHolidayMaster(HttpSession session) {
		LOG.error("Going to upload Bin Transaction Data");
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		if (user.getIcmcId() != null) {
			List<Tuple> branchReceipts = cashPaymentService.getAllShrinkWrapBundleFromBranchReceipt(user.getIcmcId());
			List<Tuple> summaryListForCoins = binDashboardService.getRecordCoinsForSummary(user.getIcmcId());
			List<Tuple> summaryList = binDashboardService.getRecordForSummary(user.getIcmcId());
			map.put("branchData", branchReceipts.size());
			map.put("migratedDataNotes", summaryList.size());
			map.put("migratedDataCoins", summaryListForCoins.size());
		}

		map.addAttribute("documentFilePath", "./files/" + documentFilePath);
		return new ModelAndView("binTransaction", map);
	}

	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), new ParseInt(),
				new ParseBigDecimal(), new ParseDate("dd/MM/yy", new DateToCalendar()), new ParseBigDecimal(),
				new ParseEnum(CurrencyType.class), new ParseEnum(BinStatus.class),
				// new ParseBigInteger(),
				new Optional(new ParseEnum(CashSource.class)), new ParseEnum(BinCategoryType.class),
				new ParseEnum(CashType.class),
				// new ParseEnum(YesNo.class),
				new Optional(), };
		return processors;
	}

	@RequestMapping(value = "/uploadBinTransaction", method = RequestMethod.POST)
	public ModelAndView uploadBinTransaction(@RequestParam MultipartFile file, HttpServletRequest request,
			BinTransaction binTransaction, HttpSession session, RedirectAttributes redirectAttributes) {
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		User user = (User) session.getAttribute("login");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		try {
			try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
				int i;

				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// List<BinTransaction> binList=new ArrayList<BinTransaction>();
		List<BinTransaction> binList = new LinkedList<>();
		BinTransaction icmBinTransaction = null;

		try {
			try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile),
					CsvPreference.STANDARD_PREFERENCE)) {
				final String[] headers = beanReader.getHeader(true);
				final CellProcessor[] processors = getProcessors();
				while ((icmBinTransaction = beanReader.read(BinTransaction.class, headers, processors)) != null) {
					binList.add(icmBinTransaction);
				}
			}
		} catch (Exception r) {
			r.printStackTrace();
			LOG.error("Error Message: " + r.getMessage());
			// redirectAttributes.addFlashAttribute("errorMsg", "CSV file is not
			// of standard format");

			redirectAttributes.addFlashAttribute("errorMsg", "" + r.getMessage() + "");

			return new ModelAndView("redirect:./binTransaction");
		}

		for (BinTransaction btnx : binList) {
			btnx.setBinNumber(btnx.getBinNumber().replaceAll("\\s+", ""));
			Calendar updateTime = Calendar.getInstance();
			updateTime.add(Calendar.DATE, -1);
			btnx.setUpdateTime(updateTime);
			btnx.setIcmcId(user.getIcmcId());
			btnx.setVerified(YesNo.Yes);
			btnx.setInsertBy(user.getId());
			btnx.setUpdateBy(user.getId());
			btnx.setPendingBundleRequest(new java.math.BigDecimal(0));
			if ("".equals(btnx.getBinNumber())) {
				redirectAttributes.addFlashAttribute("errorMsg", "Not allowed to upload blank bin");
				return new ModelAndView("redirect:./binTransaction");
			}
			BinTransaction dbBinName = binTransactionService.isValidBin(user.getIcmcId(), btnx.getBinNumber());
			if (dbBinName != null) {
				redirectAttributes.addFlashAttribute("errorMsg",
						"Duplicate BIN " + btnx.getBinNumber() + ", It is already Existing in Database");
				return new ModelAndView("redirect:./binTransaction");
			}

		}
		try {

			boolean isSuccess = binTransactionService.UploadBinTransaction(binList, binTransaction, user.getIcmcId());
			LOG.error("binTransactionService isSuccess " + isSuccess);

			redirectAttributes.addFlashAttribute("successMsg",
					"Bin Transaction records have been uploaded successfully");
		} catch (Exception ex) {
			LOG.error("binTransactionService isSuccess " + ex.getMessage());
			redirectAttributes.addFlashAttribute("errorMsg", ex.getMessage());
		}

		return new ModelAndView("redirect:./binTransaction");
	}

	private static CellProcessor[] getProcessorsForBranchReceipt() {
		final CellProcessor[] processors = new CellProcessor[] { new ParseInt(), new ParseBigDecimal(),
				new UniqueHashCode(),
				// new ParseEnum(OtherStatus.class),
				new ParseDate("dd/MM/yy", new DateToCalendar()),
				// new ParseBigInteger(),
				// new ParseEnum(CurrencyType.class),
				new Optional(new ParseEnum(CashSource.class)), new ParseEnum(BinCategoryType.class), };
		return processors;
	}

	private static CellProcessor[] getProcessorsForBox() {
		final CellProcessor[] processors1 = new CellProcessor[] { new UniqueHashCode(), new ParseInt(),
				new ParseBigDecimal(), new ParseEnum(CurrencyType.class), };
		return processors1;
	}

	@RequestMapping(value = "/uploadBOXMaster", method = RequestMethod.POST)
	public ModelAndView uploadBinTransaction(@RequestParam MultipartFile file, HttpServletRequest request,
			BoxMaster boxmaster, HttpSession session, RedirectAttributes redirectAttributes) {
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		User user = (User) session.getAttribute("login");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		try {
			try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
				int i;

				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// List<BinTransaction> binList=new ArrayList<BinTransaction>();

		List<BoxMaster> boxlist = new LinkedList<>();
		BoxMaster icmboxmaster = null;
		try {
			try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile),
					CsvPreference.STANDARD_PREFERENCE)) {
				final String[] headers = beanReader.getHeader(true);
				final CellProcessor[] processors1 = getProcessorsForBox();
				while ((icmboxmaster = beanReader.read(BoxMaster.class, headers, processors1)) != null) {
					boxlist.add(icmboxmaster);
				}
			}
		} catch (Exception r) {
			r.printStackTrace();
			LOG.error("Error Message: " + r.getMessage());
			redirectAttributes.addFlashAttribute("errorMsg", "" + r.getMessage() + "");
			return new ModelAndView("redirect:./createBox");
		}

		for (BoxMaster boxlst : boxlist) {
			boxlst.setIsAllocated(0);
			boxlst.setStatus(Status.ENABLED);
			boxlst.setInsertTime(Calendar.getInstance());
			boxlst.setUpdateTime(Calendar.getInstance());
			boxlst.setInsertBy(user.getId());
			boxlst.setUpdateBy(user.getId());
			boxlst.setIcmcId(user.getIcmcId());
			boxlst.setBoxName(boxlst.getBoxName().replaceAll("\\s+", ""));
			if ("".equals(boxlst.getBoxName())) {
				redirectAttributes.addFlashAttribute("errorMsg", "Not allowed to upload blank bin");
				return new ModelAndView("redirect:./createBox");
			}
			BoxMaster dbboxname = binTransactionService.isValidBox(user.getIcmcId(), boxlst.getBoxName());
			BinMaster dbBinName = binDashboardService.isValidBin(user.getIcmcId(), boxlst.getBoxName());
			if (dbboxname != null) {
				redirectAttributes.addFlashAttribute("errorMsg",
						"Duplicate Box " + boxlst.getBoxName() + ", BOX with this name is already Exist.");
				return new ModelAndView("redirect:./createBox");
			}
			if (dbBinName != null) {
				redirectAttributes.addFlashAttribute("errorMsg",
						"Box " + boxlst.getBoxName() + ", is same name  already defined as Bin name.");
				return new ModelAndView("redirect:./createBox");
			}

		}
		binTransactionService.UploadBoxMaster(boxlist, boxmaster);
		redirectAttributes.addFlashAttribute("successMsg", "Box Master records have been uploaded successfully");
		return new ModelAndView("redirect:./createBox");
	}

	@RequestMapping(value = "/uploadBranchReceipt", method = RequestMethod.POST)
	public ModelAndView uploadBinTransactionForBranchReceipt(@RequestParam MultipartFile file,
			HttpServletRequest request, BranchReceipt branchReceipt, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		try {
			try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
				int i;

				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<BranchReceipt> branchReceiptList = new ArrayList<BranchReceipt>();
		BranchReceipt branch = null;
		boolean checkBinCapacity = true;

		try {
			try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile),
					CsvPreference.STANDARD_PREFERENCE)) {
				final String[] headers = beanReader.getHeader(true);
				final CellProcessor[] processors = getProcessorsForBranchReceipt();
				while ((branch = beanReader.read(BranchReceipt.class, headers, processors)) != null) {
					branchReceiptList.add(branch);
				}
			}
		} catch (Exception r) {
			r.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMsgForBR", "" + r.getMessage() + "");
			// redirectAttributes.addFlashAttribute("errorMsgForBR", "CSV file
			// is not of standard format");
			checkBinCapacity = false;

			return new ModelAndView("redirect:./binTransaction");
		}
		for (BranchReceipt brReceipt : branchReceiptList) {
			brReceipt.setBin(brReceipt.getBin().replaceAll("\\s+", ""));
			if ("".equals(brReceipt.getBin())) {
				redirectAttributes.addFlashAttribute("errorMsgForBR", "Not allowed to upload blank bin");
			}
			brReceipt.setIcmcId(user.getIcmcId());
			brReceipt.setStatus(OtherStatus.RECEIVED);
			brReceipt.setCurrencyType(CurrencyType.UNPROCESS);

		}
		List<BinTransaction> binTransactionList = binTransactionService.getBinTransaction(user.getIcmcId());

		for (BranchReceipt branchReceipt2 : branchReceiptList) {
			for (BinTransaction binTransaction : binTransactionList) {
				if (binTransaction.getBinNumber() != null
						&& binTransaction.getBinNumber().equals(branchReceipt2.getBin())
						&& (binTransaction.getDenomination() != 0
								&& binTransaction.getDenomination() == branchReceipt2.getDenomination())
						&& (branchReceipt2.getBundle() != null && binTransaction.getReceiveBundle() != null
								&& branchReceipt2.getBundle().compareTo(binTransaction.getReceiveBundle()) != 0)
						&& (binTransaction.getBinType() != null
								&& binTransaction.getBinType().equals(branchReceipt2.getCurrencyType()))
						&& (binTransaction.getBinCategoryType() != null
								&& binTransaction.getBinCategoryType().equals(branchReceipt2.getBinCategoryType()))) {
					redirectAttributes.addFlashAttribute("errorMsgForBR",
							"Bin- " + branchReceipt2.getBin() + " of Denomination- " + branchReceipt2.getDenomination()
									+ ", Bundle - " + branchReceipt2.getBundle()
									+ ", should be same as in bintransaction bundle-> "
									+ binTransaction.getReceiveBundle());
					checkBinCapacity = false;
					break;
					// return new ModelAndView("redirect:./binTransaction");

				}
			}

		}

		if (checkBinCapacity) {
			try {
				if (binTransactionList.size() != branchReceiptList.size()) {
					redirectAttributes.addFlashAttribute("errorMsgForBR",
							"Bin in Branch List" + branchReceiptList.size()
									+ "should be same  Bin in BinTransaction List " + binTransactionList.size());
					return new ModelAndView("redirect:./binTransaction");
				} else {
					branchReceipt.setIcmcId(user.getIcmcId());
					binTransactionService.uploadBranchReceipt(branchReceiptList, branchReceipt);
				}
			} catch (Exception ex) {
				redirectAttributes.addFlashAttribute("errorMsgForBR", ex.getMessage());
				return new ModelAndView("redirect:./binTransaction");
			}
			// redirectAttributes.addFlashAttribute("successMsgForBR", "Branch
			// Receipt records have been uploaded successfully");
			return new ModelAndView("redirect:./saveDataInBinTransactionBOD");
		}
		return new ModelAndView("redirect:./binTransaction");
	}

}
