package com.chest.currency.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chest.currency.entity.model.BinRegister;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CITCRAVendor;
import com.chest.currency.entity.model.CRA;
import com.chest.currency.entity.model.CRAAccountDetail;
import com.chest.currency.entity.model.CRAAllocation;
import com.chest.currency.entity.model.CashReleased;
import com.chest.currency.entity.model.DateRange;
import com.chest.currency.entity.model.DiversionORV;
import com.chest.currency.entity.model.DiversionORVAllocation;
import com.chest.currency.entity.model.ForwardBundleForCRAPayment;
import com.chest.currency.entity.model.ORV;
import com.chest.currency.entity.model.ORVAllocation;
import com.chest.currency.entity.model.OtherBank;
import com.chest.currency.entity.model.OtherBankAllocation;
import com.chest.currency.entity.model.ProcessBundleForCRAPayment;
import com.chest.currency.entity.model.SASAllocation;
import com.chest.currency.entity.model.Sas;
import com.chest.currency.entity.model.SoiledRemittance;
import com.chest.currency.entity.model.SoiledRemittanceAllocation;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DenominationType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.exception.BaseGuiException;
import com.chest.currency.jpa.persistence.converter.ConvertNumberInWords;
import com.chest.currency.jpa.persistence.converter.CurrencyFormatter;
import com.chest.currency.service.BinDashboardService;
import com.chest.currency.service.CashPaymentService;
import com.chest.currency.service.ICMCService;
import com.chest.currency.service.ProcessingRoomService;
import com.chest.currency.util.UtilityJpa;
import com.chest.currency.util.UtilityMapper;
import com.chest.currency.viewBean.CRAWrapper;
import com.chest.currency.viewBean.SASAllocationGrouped;
import com.chest.currency.viewBean.SASAllocationWrapper;
import com.chest.currency.viewBean.SASReleaseWrapper;
import com.chest.currency.viewBean.SASReleased;
import com.mysema.query.Tuple;

@Controller
public class CashPaymentController {

	private static final Logger LOG = LoggerFactory.getLogger(CashPaymentController.class);

	@Autowired
	CashPaymentService cashPaymentService;

	@Autowired
	ProcessingRoomService processingRoomService;

	@Autowired
	BinDashboardService binDashboardService;

	@Autowired
	ICMCService icmcService;

	@Autowired
	String documentFilePath;

	@Autowired
	String prnFilePath;

	// Upload SAS Page
	@RequestMapping("/UploadSAS")
	public ModelAndView SASUpload() {
		ModelMap map = new ModelMap();
		map.addAttribute("documentFilePath", "./files/" + documentFilePath);
		return new ModelAndView("UploadSAS", map);
	}

	// Upload SAS data From CSV and Save in Table
	@RequestMapping(value = "/uploadSAS", method = RequestMethod.POST)
	public ModelAndView sasUpload(@RequestParam MultipartFile file, HttpServletRequest request, Sas sas,
			HttpSession session, ModelMap model, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		// Sas sasFile = cashPaymentService.getFileName(user.getIcmcId());
		Sas sasFile = cashPaymentService.getSameDayFileName(user.getIcmcId(), sDate, eDate);
		if (sasFile != null && file.getOriginalFilename().equalsIgnoreCase(sasFile.getFileName())) {
			redirectAttributes.addFlashAttribute("duplicateFile",
					"File with this file name already exist, Please Check");
			return new ModelAndView("redirect:./UploadSAS");
		}
		sas.setInsertBy(user.getId());
		sas.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		sas.setInsertTime(now);
		sas.setUpdateTime(now);
		sas.setIcmcId(user.getIcmcId());

		BufferedReader csvBuffer = null;
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		sas.setFileName(file.getOriginalFilename());
		try {
			try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
				int i;
				// write file to server

				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			String csvLine;
			csvBuffer = new BufferedReader(new FileReader(serverFile));
			List<String> sasRecords = new LinkedList<>();
			csvBuffer.readLine(); // Skip Inserting First Line

			while ((csvLine = csvBuffer.readLine()) != null) {
				sasRecords.add(csvLine);
			}
			List<Sas> sasList = UtilityJpa.CSVtoArrayList(sasRecords);
			for (Sas wrongTotal : sasList) {
				if (wrongTotal.getStatus() == 1) {
					redirectAttributes.addFlashAttribute("duplicateFile",
							"Please check Total value of Branch: " + wrongTotal.getBranch()
									+ ". The Currect sum of denomination is " + wrongTotal.getTotalValue());
					return new ModelAndView("redirect:./UploadSAS");
				}
			}
			cashPaymentService.sasUpload(sasList, sas);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (csvBuffer != null)
					csvBuffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		redirectAttributes.addFlashAttribute("successMsg", "SAS File Uploaded Successfully");
		return new ModelAndView("redirect:./SAS");
	}

	// View data After Uploadin in SAS Table
	@RequestMapping("/SAS")
	public ModelAndView binList(HttpSession session) {
		Sas sas = new Sas();
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();

		// List<Sas> list = cashPaymentService.getSASRecord(user);

		List<Sas> list = cashPaymentService.getSASRecordFromSasfile(user);
		List<Object> autoId = new ArrayList<>();
		Sas sasId = new Sas();
		if (list != null) {
			for (Sas idList : list) {
				autoId.add(idList.getId());
				sasId.setId(idList.getId());
			}

		}

		List<Tuple> summaryList = cashPaymentService.getRecordForSummary(user.getIcmcId());

		Map<Integer, BinTransaction> mapList = new LinkedHashMap<>();

		for (DenominationType denom : DenominationType.values()) {
			mapList.put(denom.getDenomination(), new BinTransaction());
		}

		for (Tuple bin : summaryList) {
			BinTransaction binTransactionSummary = mapList.get(bin.get(1, Integer.class));

			if (bin.get(0, CurrencyType.class).equals(CurrencyType.ATM)) {
				binTransactionSummary.setATM(bin.get(2, BigDecimal.class));
			}

			if (bin.get(0, CurrencyType.class).equals(CurrencyType.FRESH)) {
				binTransactionSummary.setFresh(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.ISSUABLE)) {
				binTransactionSummary.setIssuable(bin.get(2, BigDecimal.class));
			}
		}

		List<Tuple> summaryListForCoins = cashPaymentService.getRecordCoinsForSummary(user.getIcmcId());
		map.put("sasIdList", sasId);
		map.put("autoIdList", autoId);
		map.put("user", sas);
		map.put("records", list);
		map.put("summaryRecords", mapList);
		map.put("summaryListForCoins", summaryListForCoins);
		return new ModelAndView("/SAS", map);
	}

	// Update SAS
	@RequestMapping(value = "/updateSAS", method = RequestMethod.POST)
	@ResponseBody
	public long updateSAS(@RequestBody Sas sas) {
		long count = cashPaymentService.updateSAS(sas);
		return count;
	}

	// REMOVE BRANCH FROM SAS
	@RequestMapping(value = "/removeBranch", method = RequestMethod.POST)
	@ResponseBody
	public long removeBranch(@RequestBody Sas sas) {
		long count = cashPaymentService.removeBranchFromSAS(sas);
		return count;
	}

	// get bin For SAS Payment
	@RequestMapping(value = "/AssignBinForBulkIndent")
	@ResponseBody
	public SASAllocationWrapper bulkRequest(@RequestBody SASAllocationWrapper sASAllocationWrapper,
			HttpSession session) {

		User user = (User) session.getAttribute("login");
		sASAllocationWrapper.setIcmcId(user.getIcmcId());

		removeBlankSasAllocationGrouped(sASAllocationWrapper, user);
		Sas sas = new Sas();
		sas.setId(sASAllocationWrapper.getId());
		try {
			cashPaymentService.processSASAllocation(sASAllocationWrapper, user, sas);
			// int status=7;
			// cashPaymentService.updateSASStatusForSASFile(user.getIcmcId(),
			// status);
		} catch (Exception ex) {
			LOG.error("Error has occred", ex);
			throw ex;
		}
		LOG.error("INSERT IN SAS ALLOCATION==" + sASAllocationWrapper);
		return sASAllocationWrapper;
	}

	@ExceptionHandler(BaseGuiException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public BaseGuiException handleException(BaseGuiException ex, HttpServletResponse response) throws IOException {
		return ex;

	}

	private void removeBlankSasAllocationGrouped(SASAllocationWrapper sasAllocation, User user) {
		Iterator<SASAllocationGrouped> itr = sasAllocation.getSasAllocationList().iterator();
		while (itr.hasNext()) {
			SASAllocationGrouped sas = itr.next();

			if ((sas.getAtmBundle() == null || sas.getAtmBundle().compareTo(BigDecimal.ZERO) == 0)
					&& (sas.getIssuableBundle() == null || sas.getIssuableBundle().compareTo(BigDecimal.ZERO) == 0)
					&& (sas.getFreshBundle() == null || sas.getFreshBundle().compareTo(BigDecimal.ZERO) == 0)
					&& (sas.getCoinsBag() == null || sas.getCoinsBag().compareTo(BigDecimal.ZERO) == 0)) {
				itr.remove();
			} else {

				if (sas.getAtmBundle() != null && sas.getAtmBundle().compareTo(BigDecimal.ZERO) > 0) {
					sas.setAtmBin(CurrencyType.ATM);
					sas.setCashType(CashType.NOTES);
				}

				if (sas.getIssuableBundle() != null && sas.getIssuableBundle().compareTo(BigDecimal.ZERO) > 0) {
					sas.setIssuableBin(CurrencyType.ISSUABLE);
					sas.setCashType(CashType.NOTES);
				}

				if (sas.getFreshBundle() != null && sas.getFreshBundle().compareTo(BigDecimal.ZERO) > 0) {
					sas.setFreshBin(CurrencyType.FRESH);
					sas.setCashType(CashType.NOTES);
				}

				if (sas.getCoinsBag() != null && sas.getCoinsBag().compareTo(BigDecimal.ZERO) > 0) {
					// sas.setFreshBin(CurrencyType.FRESH);
					sas.setCashType(CashType.COINS);
				}
				sas.setIcmcId(user.getIcmcId());

				LOG.debug("sas:" + sas);
			}
		}
	}

	// Open Soiled Page
	@RequestMapping("/Soiled")
	public ModelAndView Soiled(HttpSession session) {
		ModelMap map = new ModelMap();
		SoiledRemittance obj = new SoiledRemittance();
		User user = (User) session.getAttribute("login");
		List<BinTransaction> preparedBoxList = cashPaymentService.getPreparedInActiveSoiledBoxes(user.getIcmcId());
		List<BinTransaction> soiledList = new LinkedList<>();
		List<BinTransaction> mutilatedList = new LinkedList<>();
		for (BinTransaction soiledOrMutilated : preparedBoxList) {
			if (soiledOrMutilated.getBinType().equals(CurrencyType.SOILED)) {
				soiledList.add(soiledOrMutilated);
			} else {
				mutilatedList.add(soiledOrMutilated);
			}
		}
		// map.put("records", preparedBoxList);
		map.put("soiledList", soiledList);
		map.put("mutilatedList", mutilatedList);
		map.put("user", obj);
		return new ModelAndView("Soiled", map);
	}

	// View Soiled Data
	@RequestMapping("/viewSoiled")
	public ModelAndView getSoiled(HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		// List<SoiledRemittance> soiledList =
		// cashPaymentService.soiledRecord(user.getIcmcId(), sDate, eDate);

		List<SoiledRemittance> soiledList = cashPaymentService.getSoiledRequestAcceptRecord(user.getIcmcId(), sDate,
				eDate);
		return new ModelAndView("/viewSoiled", "records", soiledList);
	}

	@RequestMapping("/editViewSoiled")
	public ModelAndView eidtViewSoiled(@RequestParam long id, HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		SoiledRemittance soiledRemitationRecord = cashPaymentService.getSoiledRemittanceById(user.getIcmcId(), sDate,
				eDate, id);
		int row = soiledRemitationRecord.getRemittanceAllocations().size();
		List<BinTransaction> preparedBoxList = cashPaymentService.getPreparedInActiveSoiledBoxes(user.getIcmcId());
		ModelMap map = new ModelMap();

		map.put("user", soiledRemitationRecord);
		map.put("records", preparedBoxList);
		map.put("row", row);
		map.put("status", soiledRemitationRecord.getStatus());
		map.put("soiledRemitationRecord", soiledRemitationRecord.getRemittanceAllocations());
		return new ModelAndView("editViewSoiled", map);
	}

	@RequestMapping(value = "/updateInsertSoiledRemittance")
	@ResponseBody
	public SoiledRemittance insertUpdateSoiledRemittance(@RequestBody SoiledRemittance soiledRemittance,
			HttpSession session) {
		User user = (User) session.getAttribute("login");

		boolean isAllSuccess = false;
		// long soiledId = soiledRemittance.getId();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			// cashPaymentService.updateInsertSoiledAndSoiledAllocation(user.getIcmcId(),
			// soiledId);
			soiledRemittance.setIcmcId(user.getIcmcId());
			soiledRemittance.setInsertBy(user.getId());
			soiledRemittance.setUpdateBy(user.getId());
			soiledRemittance.setInsertTime(Calendar.getInstance());
			soiledRemittance.setUpdateTime(Calendar.getInstance());
			// soiledRemittance.setId(null);
			isAllSuccess = cashPaymentService.processSoiledRemmitanceAllocation(soiledRemittance, user);
			if (!isAllSuccess) {
				throw new RuntimeException("Problem while saving Soiled And Soiled Allocation, Please try again");
			}
		}
		return soiledRemittance;
	}

	@RequestMapping("/cancelSoiledPayment")
	public ModelAndView cancelSoiledPayment(@RequestParam(value = "idFromUI") Long idFromUI, HttpSession session) {
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			try {
				LOG.error("ID From UI==" + idFromUI);
				cashPaymentService.updateInsertSoiledAndSoiledAllocation(user.getIcmcId(), idFromUI);
			} catch (Exception ex) {
				LOG.error("Error has occred", ex);
				throw ex;
			}
		}
		return new ModelAndView("redirect:./acceptCRAPayment");
	}

	// Accept SAS Indent For Vault
	@RequestMapping("/AcceptSASIndent")
	public ModelAndView getSASAllocation(HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();
		List<Tuple> sasList = cashPaymentService.getSASAllocationRecordFromTuple(user.getIcmcId(), sDate, eDate);
		return new ModelAndView("/AcceptSASIndent", "records", sasList);
	}

	// Save Soiled Remittance record in DATABASE
	@RequestMapping(value = "/soiledRemittance")
	@ResponseBody
	public SoiledRemittance insertSoiledRemittance(@RequestBody SoiledRemittance soiledRemittance, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");

		boolean isAllSuccess = false;
		synchronized (icmcService.getSynchronizedIcmc(user)) {

			soiledRemittance.setIcmcId(user.getIcmcId());
			soiledRemittance.setInsertBy(user.getId());
			soiledRemittance.setUpdateBy(user.getId());
			soiledRemittance.setInsertTime(Calendar.getInstance());
			soiledRemittance.setUpdateTime(Calendar.getInstance());

			isAllSuccess = cashPaymentService.processSoiledRemmitanceAllocation(soiledRemittance, user);

			if (!isAllSuccess) {
				throw new RuntimeException("Problem while saving Soiled And Soiled Allocation, Please try again");
			}
		}
		return soiledRemittance;
	}

	@RequestMapping("/viewDorv")
	public ModelAndView getDORV(HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<DiversionORV> dorvList = cashPaymentService.getDiversionORV(user.getIcmcId(), sDate, eDate);

		return new ModelAndView("/viewDorv", "records", dorvList);
	}

	@RequestMapping("/Dorv")
	public ModelAndView DORV() {
		DiversionORV obj = new DiversionORV();

		return new ModelAndView("Dorv", "user", obj);
	}

	@RequestMapping(value = "/DorvAllocation")
	@ResponseBody
	public DiversionORV insertDiversionORV(@RequestBody DiversionORV diversionORV, HttpSession session) {
		User user = (User) session.getAttribute("login");

		boolean isAllSuccess = false;

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			diversionORV.setIcmcId(user.getIcmcId());
			diversionORV.setInsertBy(user.getId());
			diversionORV.setUpdateBy(user.getId());
			diversionORV.setInsertTime(Calendar.getInstance());
			diversionORV.setUpdateTime(Calendar.getInstance());
			diversionORV.setOtherStatus(OtherStatus.REQUESTED);
			isAllSuccess = cashPaymentService.processDiversionORVAllocation(diversionORV, user);

			if (!isAllSuccess) {
				throw new BaseGuiException("Error while saving Diversion And Diversion Allocation, Please try again");
			}
		}
		return diversionORV;
	}

	@RequestMapping("/viewORV")
	@ResponseBody
	public ModelAndView getORV(HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<Sas> sasList = cashPaymentService.getRequestAcceptORVRecords(user.getIcmcId(), sDate, eDate);

		return new ModelAndView("/viewORV", "records", sasList);
	}

	@RequestMapping("/editViewOrv")
	public ModelAndView getEditViewOrv(@RequestParam long id, Sas sasDetails, HttpSession session,
			RedirectAttributes redirectAttributes) {
		ModelMap model = new ModelMap();
		User user = (User) session.getAttribute("login");

		sasDetails = cashPaymentService.getSASRecordById(user.getIcmcId(), id);
		model.put("total", sasDetails.getTotalValue());

		List<SASAllocation> sasAlList = cashPaymentService.getSasAllocationBySasId(id);
		LOG.error("sasAlList " + sasAlList);
		int row = sasAlList.size();

		BranchReceipt branchReceipt = cashPaymentService.checkBinOrBoxFromBranchReceipt(user.getIcmcId(),
				sasAlList.get(0).getDenomination(), sasAlList.get(0).getBundle(), sasAlList.get(0).getBinNumber());
		LOG.error("branchReceipt " + branchReceipt);
		if (branchReceipt != null) {
			sasDetails.setBinCategoryType(branchReceipt.getBinCategoryType());
		}
		model.put("user", sasDetails);
		model.put("sasPayment", sasAlList);
		model.put("row", row);
		if (row != 0) {
			// model.put("status", sasAlList.get(0).getStatus());
			model.put("status", sasDetails.getStatus());
		} else {
			redirectAttributes.addFlashAttribute("sasDataMsg", "this can't be edit, Payment comes from SAS file");
			return new ModelAndView("redirect:./viewORV");
		}

		return new ModelAndView("/editViewOrv", model);

	}

	@RequestMapping("/ORVBranch")
	public ModelAndView ORVForBranch(HttpSession session) {
		ORV obj = new ORV();
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();

		List<CITCRAVendor> vendorList = cashPaymentService.getVendor();
		// List<Tuple> branchReceipts =
		// cashPaymentService.getAllShrinkWrapBundleFromBranchReceipt(user.getIcmcId());
		List<Tuple> branchReceipts = processingRoomService.indentSummary(user.getIcmcId(), CashSource.BRANCH);

		map.put("branchReceipts", branchReceipts);
		map.put("user", obj);
		map.put("vendorList", vendorList);

		return new ModelAndView("ORVBranch", map);
	}

	@RequestMapping("/viewShrinkBundle")
	@ResponseBody
	public List<BranchReceipt> viewShrinkBundle(@RequestParam int denomination,
			@RequestParam BinCategoryType binCategoryType, HttpSession session) {
		User user = (User) session.getAttribute("login");
		LOG.error("binCategoryType " + binCategoryType);
		/*
		 * List<BranchReceipt> shrinkBundle =
		 * cashPaymentService.getShrinkWrapBundleByDenomination(denomination,
		 * user.getIcmcId(), binCategoryType);
		 */
		List<BranchReceipt> shrinkBundle = processingRoomService.getBinNumListForIndentFromBranchReceipt(denomination,
				new BigDecimal(0), user.getIcmcId(), CashSource.BRANCH, binCategoryType);
		return shrinkBundle;
	}

	@RequestMapping(value = "/orvBranchAllocation")
	@ResponseBody
	public ORV insertORV(@RequestBody ORV orv, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("login");

		orv.setIcmcId(user.getIcmcId());
		orv.setInsertBy(user.getId());
		orv.setUpdateBy(user.getId());
		boolean isAllSuccess = cashPaymentService.processORVAllocation(orv, user);
		if (!isAllSuccess) {
			throw new RuntimeException("Error while saving ORV And ORV Allocation, Please try again");
		}
		return orv;
	}

	@RequestMapping(value = "/insertUpdateOrvBranchAllocation")
	@ResponseBody
	public ORV insertUpdateORV(@RequestBody ORV orv, HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");

		boolean isAllSuccess = cashPaymentService.processORVAllocation(orv, user);

		if (!isAllSuccess) {
			throw new RuntimeException("Error while saving ORV And ORV Allocation, Please try again");
		}

		return orv;
	}

	@RequestMapping("/cancelBranchPaymentSas")
	public ModelAndView cancelBranchPaymentSas(@RequestParam(value = "idFromUI") Long idFromUI, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			List<SASAllocation> sas = cashPaymentService.getSasAllocationBySasId(idFromUI);
			int row = sas.size();
			if (row == 0) {
				throw new BaseGuiException("Sas file Accepted value can not be Cancelled");
			}
			LOG.error("ID From UI==" + sas.get(0).getStatus());
			try {
				LOG.error("ID From UI==" + idFromUI);
				if (sas.get(0).getStatus().toString().equals("ACCEPTED")) {
					throw new BaseGuiException("Accepted value can not be Cancelled");
				} else {
					LOG.error("elseblock");
					try {
						cashPaymentService.processForCancelBranchPayment(user.getIcmcId(), idFromUI);
					} catch (Exception ex) {
						LOG.error("Error has occred", ex);
						throw ex;
					}

				}
			} catch (Exception ex) {
				LOG.error("Error has occred", ex);
				throw ex;
			}

		}

		return new ModelAndView("redirect:./acceptCRAPayment");
	}

	@RequestMapping("/AcceptSoiledIndent")
	public ModelAndView getSoiledAllocation(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<SoiledRemittanceAllocation> soiledList = cashPaymentService.getSoiledForAccept(user.getIcmcId());
		return new ModelAndView("/AcceptSoiledIndent", "records", soiledList);
	}

	@RequestMapping("/AcceptORVIndent")
	public ModelAndView getORVAllocation(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<ORVAllocation> orvList = cashPaymentService.getORVForAccept(user.getIcmcId());
		return new ModelAndView("/AcceptORVIndent", "records", orvList);
	}

	@RequestMapping("/AcceptDORVIndent")
	public ModelAndView getDORVAllocation(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<DiversionORVAllocation> dorvList = cashPaymentService.getDiversionORVForAccept(user.getIcmcId());
		return new ModelAndView("/AcceptDORVIndent", "records", dorvList);
	}

	@RequestMapping("/acceptSoiledIndent")
	@ResponseBody
	public SoiledRemittanceAllocation updateSoiledIndentStatus(@RequestBody SoiledRemittanceAllocation soiledRemittance,
			HttpSession session) {

		User user = (User) session.getAttribute("login");

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			try {
				cashPaymentService.processForAcceptanceSoiledIndent(soiledRemittance, user);
			} catch (Exception ex) {
				throw new BaseGuiException("process soiled accept " + ex.getMessage());
			}
		}
		return soiledRemittance;
	}

	@RequestMapping("/acceptDorvIndent")
	@ResponseBody
	public DiversionORVAllocation updateDorvIndentStatus(@RequestBody DiversionORVAllocation diversionORV,
			HttpSession session) {
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			Calendar now = Calendar.getInstance();
			UtilityJpa.setStartDate(now);

			try {
				cashPaymentService.processForAcceptanceOutwardDiversion(diversionORV, now, user);
			} catch (Exception ex) {
				throw new BaseGuiException("problem for acceptance " + ex.getMessage());
			}
		}
		return diversionORV;
	}

	@RequestMapping("/acceptOrvIndent")
	public ModelAndView updateOrvIndentStatus(@RequestParam(value = "id") long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ORVAllocation orv = new ORVAllocation();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			orv.setId(id);
			long count = cashPaymentService.updateOrvStatus(orv);
			if (count == 0) {
				throw new RuntimeException("Error while process Indent Request");
			}
		}
		return new ModelAndView("welcome");
	}

	@RequestMapping("/acceptSASIndent")
	@ResponseBody
	public SASAllocation updateSASIndentStatus(@RequestBody SASAllocation sasAccept, HttpSession session) {
		User user = (User) session.getAttribute("login");
		try {
			cashPaymentService.updateSasIndent(sasAccept, user);
		} catch (Exception ex) {
			LOG.error("Problem acceptSASIndent is", ex);
			// throw new BaseGuiException("Problem acceptSASIndent is
			// "+ex.getMessage());
			throw ex;
		}
		return sasAccept;
	}

	@RequestMapping("/cancelSASIndent")
	@ResponseBody
	public SASAllocation cancelSASIndent(@RequestBody SASAllocation sasCancel, HttpSession session) {
		SASAllocation sas = new SASAllocation();
		sas.setId(sasCancel.getId());
		User user = (User) session.getAttribute("login");
		cashPaymentService.updateSasAllocationForCancelBranchPayment(user.getIcmcId(), sasCancel.getId());
		cashPaymentService.updateSasForCancelBranchPayment(user.getIcmcId(), sasCancel.getParentId());
		BinTransaction binTxn = new BinTransaction();

		binTxn.setBinNumber(sasCancel.getBinNumber());
		binTxn.setBinType(sasCancel.getBinType());
		binTxn.setCashType(sasCancel.getCashType());
		binTxn.setDenomination(sasCancel.getDenomination());

		BinTransaction pendingBundleFromDB = cashPaymentService.getPendingBundleFromDB(user.getIcmcId(), binTxn);
		BigDecimal pendingBundle = pendingBundleFromDB.getPendingBundleRequest().subtract(sasCancel.getBundle());
		binTxn.setPendingBundleRequest(pendingBundle);
		cashPaymentService.updateBinTransactionCancelBranchPayment(user.getIcmcId(), binTxn);
		return sasCancel;
	}

	@RequestMapping("/cashReleased")
	public ModelAndView cashReleased(HttpSession session) {
		User user = (User) session.getAttribute("login");
		CashReleased obj = new CashReleased();
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		ModelMap map = new ModelMap();
		// List<Sas> solIdList =
		// cashPaymentService.solIdForSASPayment(user.getIcmcId(), sDate,
		// eDate);

		List<Sas> solIdList = new ArrayList<>();
		List<SASAllocation> acceptedListFromSASAllocation = cashPaymentService
				.getAllTodayAcceptedFromSASAllocation(user.getIcmcId(), sDate, eDate);

		Set<Long> pList = new HashSet<Long>();
		Set<Long> parentList = new HashSet<Long>();
		for (SASAllocation parentId : acceptedListFromSASAllocation) {
			if (parentId.getParentId() != null) {
				pList.add(parentId.getParentId());
			}
		}
		List<Sas> sasFileUploadList = cashPaymentService.getSASRecordForceHandover(user, sDate, eDate);
		for (Sas sasId : sasFileUploadList) {
			if (sasId.getId() != null) {
				pList.add(sasId.getId());
			}
		}
		for (Long sasId : pList) {
			SASAllocation allocation = cashPaymentService.getRequestedFromSASAllocation(user.getIcmcId(), sDate, eDate,
					sasId);
			parentList.add(sasId);
			/*
			 * if (allocation != null) pList.remove(sasId);
			 */
			if (allocation == null)
				parentList.remove(sasId);
		}
		if (acceptedListFromSASAllocation.size() != 0 || sasFileUploadList.size() != 0) {
			// solIdList =
			// cashPaymentService.solIdForSASPaymentAccepted(user.getIcmcId(),
			// sDate, eDate, parentList);
			solIdList = cashPaymentService.sasForCashHandover(user.getIcmcId(), sDate, eDate, parentList);

		}
		map.put("user", obj);
		map.put("sas", solIdList);
		map.put("denominationList", DenominationType.values());
		return new ModelAndView("cashReleased", map);
	}

	@RequestMapping("/forceHandover")
	public ModelAndView forceHandover(HttpSession session) {
		User user = (User) session.getAttribute("login");
		CashReleased obj = new CashReleased();
		ModelMap map = new ModelMap();
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<Sas> solIdList = new ArrayList<>();
		List<SoiledRemittance> remittanceOrder = new ArrayList<>();

		List<SASAllocation> acceptedListFromSASAllocation = cashPaymentService
				.getAllTodayAcceptedFromSASAllocation(user.getIcmcId(), sDate, eDate);
		Set<Long> pList = new HashSet<Long>();
		Set<Long> parentList = new HashSet<Long>();
		for (SASAllocation parentId : acceptedListFromSASAllocation) {
			if (parentId.getParentId() != null)
				pList.add(parentId.getParentId());
		}
		List<Sas> sasFileUploadList = cashPaymentService.getSASRecordForceHandover(user, sDate, eDate);
		for (Sas sasId : sasFileUploadList) {
			if (sasId.getId() != null)
				pList.add(sasId.getId());
		}
		for (Long sasId : pList) {
			SASAllocation allocation = cashPaymentService.getRequestedFromSASAllocation(user.getIcmcId(), sDate, eDate,
					sasId);
			parentList.add(sasId);
			/*
			 * if (allocation != null) pList.remove(sasId);
			 */
			if (allocation == null)
				parentList.remove(sasId);
		}
		if (acceptedListFromSASAllocation.size() != 0 || sasFileUploadList.size() != 0) {
			solIdList = cashPaymentService.solIdForSASPaymentAccepted(user.getIcmcId(), sDate, eDate, parentList);
		}
		/*
		 * ProcessBundleForCRAPayment processDetails = new
		 * ProcessBundleForCRAPayment();
		 * 
		 * processDetails = cashPaymentService.getCRAId(user.getIcmcId());
		 */
		// List<ProcessBundleForCRAPayment> processDetails =
		// (List<ProcessBundleForCRAPayment>) new ProcessBundleForCRAPayment();
		List<ProcessBundleForCRAPayment> processDetails = cashPaymentService.getListCRAId(user.getIcmcId());
		List<CRA> solIdForCra = new ArrayList<>();

		solIdForCra = cashPaymentService.solIdForCRAPayment(user.getIcmcId(), sDate, eDate);
		HashSet<Long> sortingCraId = new HashSet<Long>();

		if (processDetails != null) {
			for (ProcessBundleForCRAPayment process : processDetails) {
				sortingCraId.add(process.getCraId());
				/*
				 * List<CRA> solIdForCraById =
				 * cashPaymentService.valueFromCRA(user.getIcmcId(),
				 * process.getCraId()); solIdForCra.addAll(solIdForCraById);
				 */
			}
		}
		for (Long cr : sortingCraId) {
			List<CRA> solIdForCraById = cashPaymentService.valueFromCRA(user.getIcmcId(), cr.longValue());
			solIdForCra.addAll(solIdForCraById);
		}
		if (solIdForCra.size() > 0) {
			map.put("cra", solIdForCra);
		}

		// List<CRAAllocation> dataFromProcessingRoom =
		// cashPaymentService.getForCRAPayment(user.getIcmcId(), sDate, eDate);

		List<DiversionORV> bankName = cashPaymentService.diversionListForRbiOrderNo(user.getIcmcId());
		List<OtherBank> otherBankList = cashPaymentService.bankNameFromOtherBank(user.getIcmcId());
		List<ProcessBundleForCRAPayment> craForwardedList = cashPaymentService
				.processBundleForCRAPayment(user.getIcmcId());
		List<SoiledRemittanceAllocation> acceptedList = cashPaymentService
				.getAcceptedListForSoiledRemitAlloc(user.getIcmcId());
		if (acceptedList.size() == 0) {
			remittanceOrder = cashPaymentService.getRemittanceOrderNo(user.getIcmcId());
		}

		map.put("user", obj);
		map.put("sas", solIdList);
		/* map.put("cra", solIdForCra); */
		map.put("diversion", bankName);
		map.put("otherBank", otherBankList);
		map.put("craForwardedList", craForwardedList);
		map.put("remittanceOrder", remittanceOrder);
		map.put("denominationList", DenominationType.values());
		return new ModelAndView("forceHandover", map);
	}

	@RequestMapping("/ORVReports")
	public ModelAndView ORVReports(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}
		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		List<Sas> allSasList = cashPaymentService.getORVReport(user.getIcmcId(), sDate, eDate);
		List<Sas> orvList = new LinkedList<>(allSasList);
		for (Sas sas : allSasList) {
			SASAllocation allocation = cashPaymentService.getRequestedFromSASAllocation(user.getIcmcId(), sDate, eDate,
					sas.getId());
			if (allocation == null)
				orvList.remove(sas);
		}
		List<CRA> craList = cashPaymentService.getCRARecord(user.getIcmcId(), sDate, eDate);
		List<OtherBank> otherBankList = cashPaymentService.getOtherBankPaymentRecord(user.getIcmcId(), sDate, eDate);

		// List<Tuple> ibitList =
		// cashPaymentService.getIBITForIRV(user.getIcmcId(), sDate, eDate);
		List<Tuple> ibitList = binDashboardService.getIBITForIRV(user.getIcmcId(), sDate, eDate);

		map.put("sum", UtilityJpa.getSumAllIndent(ibitList));

		String linkBranchSolID = cashPaymentService.getLinkBranchSolID(user.getIcmcId().longValue());

		String servicingICMC = cashPaymentService.getServicingICMC(linkBranchSolID);

		map.put("servicingICMC", servicingICMC);
		map.put("linkBranchSolID", linkBranchSolID);
		map.put("records", orvList);
		map.put("linkBranchSolID", linkBranchSolID);
		map.put("craRecords", craList);
		map.put("otherBankRecords", otherBankList);

		return new ModelAndView("ORVReports", map);
	}

	@RequestMapping(value = "/custodianName")
	@ResponseBody
	public String custodianList(@RequestParam(value = "vendor") String vendor) {
		List<String> custodianList = cashPaymentService.getCustodianName(vendor);
		return custodianList.toString();
	}

	@RequestMapping(value = "/vehicleNumber")
	@ResponseBody
	public String vehicleList(@RequestParam(value = "vendor") String vendor) {
		List<String> vehicleList = cashPaymentService.getVehicleNumber(vendor);
		return vehicleList.toString();
	}

	@RequestMapping(value = "/bundleDetails")
	@ResponseBody
	public List<SASReleased> bundleDetails(@RequestParam(value = "id") long id) {
		List<SASAllocation> sasAlList = cashPaymentService.getSasAllocationBySasId(id);
		LOG.error("sasAlList " + sasAlList);
		SASReleased sasReleased = null;
		List<SASReleased> sasReleasedList = new ArrayList<>();
		for (SASAllocation sasAlo : sasAlList) {
			if (sasAlo.getBinType().equals(CurrencyType.UNPROCESS)) {
				sasReleased = new SASReleased();
				sasReleased.setId(id);
				sasReleased.setDenomination(sasAlo.getDenomination());
				sasReleased.setCategory(sasAlo.getBinType());
				sasReleased.setBundle(sasAlo.getBundle());
				sasReleasedList.add(sasReleased);
			}
		}
		if (sasReleased != null) {
			return sasReleasedList;
		}
		Sas sas = cashPaymentService.sasPaymentDetails(id);
		if (sas.getTotalValueOfNotesRs1F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs1F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs1F().compareTo(sas.getReleasedNotes1F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(1);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs1F().subtract(sas.getReleasedNotes1F()));
			sasReleasedList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs1I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs1I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs1I().compareTo(sas.getReleasedNotes1I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(1);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs1I().subtract(sas.getReleasedNotes1I()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs10F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs10F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs10F().compareTo(sas.getReleasedNotes10F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(10);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs10F().subtract(sas.getReleasedNotes10F()));
			sasReleasedList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs10I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs10I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs10I().compareTo(sas.getReleasedNotes10F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(10);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs10I().subtract(sas.getReleasedNotes10I()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs100F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs100F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs100F().compareTo(sas.getReleasedNotes100F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(100);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs100F().subtract(sas.getReleasedNotes100F()));
			sasReleasedList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs100I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs100I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs100I().compareTo(sas.getReleasedNotes100I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(100);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs100I().subtract(sas.getReleasedNotes100I()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs100A().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs100A().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs100A().compareTo(sas.getReleasedNotes100A()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(100);
			sasReleased.setCategory(CurrencyType.ATM);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs100A().subtract(sas.getReleasedNotes100A()));
			sasReleasedList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs1000F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs1000F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs1000F().compareTo(sas.getReleasedNotes1000F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(1000);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs1000F().subtract(sas.getReleasedNotes1000F()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs1000I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs1000I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs1000I().compareTo(sas.getReleasedNotes1000I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(1000);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs1000I().subtract(sas.getReleasedNotes1000I()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs1000A().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs1000A().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs1000A().compareTo(sas.getReleasedNotes1000A()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(1000);
			sasReleased.setCategory(CurrencyType.ATM);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs1000A().subtract(sas.getReleasedNotes1000A()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs200F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs200F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs200F().compareTo(sas.getReleasedNotes200F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(200);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs200F().subtract(sas.getReleasedNotes200F()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs200I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs200I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs200I().compareTo(sas.getReleasedNotes200I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(200);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs200I().subtract(sas.getReleasedNotes200I()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs200A().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs200A().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs200A().compareTo(sas.getReleasedNotes200A()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(200);
			sasReleased.setCategory(CurrencyType.ATM);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs200A().subtract(sas.getReleasedNotes200A()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs2F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs2F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs2F().compareTo(sas.getReleasedNotes2F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(2);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs2F().subtract(sas.getReleasedNotes2F()));
			sasReleasedList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs2I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs2I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs2I().compareTo(sas.getReleasedNotes2I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(2);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs2I().subtract(sas.getReleasedNotes2I()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs20F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs20F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs20F().compareTo(sas.getReleasedNotes20F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(20);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs20F().subtract(sas.getReleasedNotes20F()));
			sasReleasedList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs20I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs20I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs20I().compareTo(sas.getReleasedNotes20I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(20);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs20I().subtract(sas.getReleasedNotes20I()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs5F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs5F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs5F().compareTo(sas.getReleasedNotes5F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(5);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs5F().subtract(sas.getReleasedNotes5F()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs5I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs5I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs5I().compareTo(sas.getReleasedNotes5I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(5);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs5I().subtract(sas.getReleasedNotes5I()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs50F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs50F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs50F().compareTo(sas.getReleasedNotes50F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(50);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs50F().subtract(sas.getReleasedNotes50F()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs50I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs50I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs50I().compareTo(sas.getReleasedNotes50I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(50);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs50I().subtract(sas.getReleasedNotes50I()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs500F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs500F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs500F().compareTo(sas.getReleasedNotes500F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(500);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs500F().subtract(sas.getReleasedNotes500F()));
			sasReleasedList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs500I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs500I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs500I().compareTo(sas.getReleasedNotes500I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(500);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs500I().subtract(sas.getReleasedNotes500I()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs500A().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs500A().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs500A().compareTo(sas.getReleasedNotes500A()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(500);
			sasReleased.setCategory(CurrencyType.ATM);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs500A().subtract(sas.getReleasedNotes500A()));
			sasReleasedList.add(sasReleased);
		}

		setSasReleaseForCurrency2000(sas, sasReleasedList);

		if (sas.getTotalValueOfCoinsRs1().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfCoinsRs1().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfCoinsRs1().compareTo(sas.getReleasedCoins1()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(1);
			sasReleased.setCategory(CurrencyType.COINS);
			sasReleased.setBundle(sas.getTotalValueOfCoinsRs1().subtract(sas.getReleasedCoins1()));
			sasReleasedList.add(sasReleased);
		}
		if (sas.getTotalValueOfCoinsRs2().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfCoinsRs2().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfCoinsRs2().compareTo(sas.getReleasedCoins2()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(2);
			sasReleased.setCategory(CurrencyType.COINS);
			sasReleased.setBundle(sas.getTotalValueOfCoinsRs2().subtract(sas.getReleasedCoins2()));
			sasReleasedList.add(sasReleased);
		}
		if (sas.getTotalValueOfCoinsRs5().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfCoinsRs5().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfCoinsRs5().compareTo(sas.getReleasedCoins5()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(5);
			sasReleased.setCategory(CurrencyType.COINS);
			sasReleased.setBundle(sas.getTotalValueOfCoinsRs5().subtract(sas.getReleasedCoins5()));
			sasReleasedList.add(sasReleased);
		}
		if (sas.getTotalValueOfCoinsRs10().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfCoinsRs10().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfCoinsRs10().compareTo(sas.getReleasedCoins10()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(10);
			sasReleased.setCategory(CurrencyType.COINS);
			sasReleased.setBundle(sas.getTotalValueOfCoinsRs10().subtract(sas.getReleasedCoins10()));
			sasReleasedList.add(sasReleased);
		}

		// Unprocess Code

		if (sas.getTotalValueOfNotesRs1U().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs1U().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs1U().compareTo(sas.getReleasedNotes1U()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(1);
			sasReleased.setCategory(CurrencyType.UNPROCESS);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs1U().subtract(sas.getReleasedNotes1U()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs2U().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs2U().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs2U().compareTo(sas.getReleasedNotes2U()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(2);
			sasReleased.setCategory(CurrencyType.UNPROCESS);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs2U().subtract(sas.getReleasedNotes2U()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs5U().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs5U().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs5U().compareTo(sas.getReleasedNotes1U()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(5);
			sasReleased.setCategory(CurrencyType.UNPROCESS);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs5U().subtract(sas.getReleasedNotes5U()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs10U().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs10U().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs10U().compareTo(sas.getReleasedNotes10U()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(10);
			sasReleased.setCategory(CurrencyType.UNPROCESS);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs10U().subtract(sas.getReleasedNotes10U()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs20U().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs20U().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs20U().compareTo(sas.getReleasedNotes20U()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(20);
			sasReleased.setCategory(CurrencyType.UNPROCESS);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs20U().subtract(sas.getReleasedNotes20U()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs50U().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs50U().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs50U().compareTo(sas.getReleasedNotes50U()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(50);
			sasReleased.setCategory(CurrencyType.UNPROCESS);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs50U().subtract(sas.getReleasedNotes50U()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs100U().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs100U().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs100U().compareTo(sas.getReleasedNotes100U()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(100);
			sasReleased.setCategory(CurrencyType.UNPROCESS);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs100U().subtract(sas.getReleasedNotes100U()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs200U().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs200U().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs200U().compareTo(sas.getReleasedNotes200U()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(200);
			sasReleased.setCategory(CurrencyType.UNPROCESS);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs200U().subtract(sas.getReleasedNotes200U()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs500U().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs500U().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs500U().compareTo(sas.getReleasedNotes500U()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(500);
			sasReleased.setCategory(CurrencyType.UNPROCESS);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs500U().subtract(sas.getReleasedNotes500U()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs2000U().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs2000U().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs2000U().compareTo(sas.getReleasedNotes2000U()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(2000);
			sasReleased.setCategory(CurrencyType.UNPROCESS);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs2000U().subtract(sas.getReleasedNotes2000U()));
			sasReleasedList.add(sasReleased);
		}
		// Close Unprocess Code

		return sasReleasedList;
	}

	private void setSasReleaseForCurrency2000(Sas sas, List<SASReleased> sasReleasedList) {
		SASReleased sasReleased;
		if (sas.getTotalValueOfNotesRs2000F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs2000F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs2000F().compareTo(sas.getReleasedNotes2000F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(2000);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs2000F().subtract(sas.getReleasedNotes2000F()));
			sasReleasedList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs2000I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs2000I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs2000I().compareTo(sas.getReleasedNotes2000I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(2000);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs2000I().subtract(sas.getReleasedNotes2000I()));
			sasReleasedList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs2000A().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs2000A().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs2000A().compareTo(sas.getReleasedNotes2000A()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(2000);
			sasReleased.setCategory(CurrencyType.ATM);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs2000A().subtract(sas.getReleasedNotes2000A()));
			sasReleasedList.add(sasReleased);
		}

	}

	@RequestMapping(value = "/bundleDetailsForCRA")
	@ResponseBody
	public List<CRAAllocation> craBundleDetails(@RequestParam(value = "id") long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<CRAAllocation> craAllocation = cashPaymentService.craPaymentDetails(user.getIcmcId(), id);
		return craAllocation;
	}

	@RequestMapping(value = "/forwardedBundleDetailsForCra")
	@ResponseBody
	public List<ProcessBundleForCRAPayment> forwardedCRABundles(@RequestParam(value = "id") long id,
			HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<ProcessBundleForCRAPayment> forwardedCraBundles = cashPaymentService
				.forwardedCraPaymentDetails(user.getIcmcId(), id);

		return forwardedCraBundles;
	}

	@RequestMapping(value = "/bundleDetailsSAS")
	@ResponseBody
	public List<Sas> sasBundleDetails(@RequestParam(value = "id") long id, HttpSession session) {

		List<Sas> sasTotal = cashPaymentService.getRecordFromSAS(id);

		return sasTotal;
	}

	@RequestMapping(value = "/bundleDetailsForDiversion")
	@ResponseBody
	public List<DiversionORVAllocation> diversionBundleDetails(@RequestParam(value = "id") long id,
			HttpSession session) {
		User user = (User) session.getAttribute("login");

		List<DiversionORVAllocation> dorvAllocation = cashPaymentService.dorvPaymentDetails(user.getIcmcId(), id);

		return dorvAllocation;
	}

	@RequestMapping(value = "/bundleDetailsForOtherBank")
	@ResponseBody
	public List<OtherBankAllocation> otherBankBundleDetails(@RequestParam(value = "id") long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<OtherBankAllocation> otherBankAllocation = cashPaymentService.otherBankPaymentDetails(user.getIcmcId(),
				id);
		return otherBankAllocation;
	}

	@RequestMapping(value = "/bundleDetailsForSoiled")
	@ResponseBody
	public List<SoiledRemittanceAllocation> bundleDetailsForSoiled(@RequestParam(value = "id") long id,
			HttpSession session) {
		User user = (User) session.getAttribute("login");

		List<SoiledRemittanceAllocation> soiledRemittanceAllocation = cashPaymentService
				.soiledRemittancePaymentDetails(user.getIcmcId(), id);

		return soiledRemittanceAllocation;
	}

	@RequestMapping(value = "/updateSASBundleDetails")
	@ResponseBody
	public SASReleaseWrapper updateSASBundleDetails(@RequestBody SASReleaseWrapper SASReleasedList) {
		// get SAS record from SAS table by sas table id (SAME ABOVE FUNCTION)
		// //
		// call mapSasReleaseToSas

		SASReleased sr = SASReleasedList.getSASReleasedList().get(0);
		sr.getId();
		Sas sas = cashPaymentService.sasPaymentDetails(sr.getId()); // Sas sas =
		// new Sas();// get from db

		for (SASReleased sasRelease : SASReleasedList.getSASReleasedList()) {

			if (CurrencyType.ISSUABLE == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_1.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes1I();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs1I();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes1I(val);
				sas.setStatus(2);
			}
			if (CurrencyType.FRESH == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_1.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes1F();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs1F();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes1F(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ISSUABLE == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_10.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes10I();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs10I();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes10I(val);
				sas.setStatus(2);
			}
			if (CurrencyType.FRESH == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_10.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes10F();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs10F();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes10F(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ISSUABLE == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_100.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes100I();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs100I();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes100I(val);
				sas.setStatus(2);
			}
			if (CurrencyType.FRESH == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_100.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes100F();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs1F();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes100F(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ATM == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_100.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes100A();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs100A();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes100A(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ISSUABLE == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_1000.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes1000I();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs1000I();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes1000I(val);
				sas.setStatus(2);
			}
			if (CurrencyType.FRESH == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_1000.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes1000F();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs100F();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes1000F(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ATM == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_1000.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes1000A();
				// val++;
				// BigDecimal val = sas.getTotalValueOfNotesRs1000A();
				BigDecimal val = sas.getTotalValueOfNotesRs1000A();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes1000A(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ISSUABLE == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_2.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes2I();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs2I();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes2I(val);
				sas.setStatus(2);
			}
			if (CurrencyType.FRESH == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_2.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes2F();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs1F();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes2F(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ISSUABLE == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_20.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes20I();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs20I();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes20I(val);
				sas.setStatus(2);
			}
			if (CurrencyType.FRESH == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_20.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes20F();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs20F();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes20F(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ISSUABLE == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_5.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes5I();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs5I();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes5I(val);
				sas.setStatus(2);
			}

			if (CurrencyType.FRESH == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_5.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes5F();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs5F();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes5F(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ISSUABLE == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_50.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes50I();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs50I();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes50I(val);
				sas.setStatus(2);
			}

			if (CurrencyType.FRESH == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_50.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes50F();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs50F();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes50F(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ISSUABLE == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_500.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes500I();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs500I();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes500I(val);
				sas.setStatus(2);
			}

			if (CurrencyType.FRESH == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_500.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes500F();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs500F();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes500F(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ATM == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_500.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes500A();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs500A();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes500A(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ISSUABLE == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_2000.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes500I();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs2000I();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes2000I(val);
				sas.setStatus(2);
			}

			if (CurrencyType.FRESH == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_2000.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes500F();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs2000F();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes2000F(val);
				sas.setStatus(2);
			}

			if (CurrencyType.ATM == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_2000.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes500A();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs2000A();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes2000A(val);
				sas.setStatus(2);
			}

			// Unprocess code

			if (CurrencyType.UNPROCESS == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_2000.getDenomination()) {
				// BigDecimal val = sas.getReleasedNotes500A();
				// val++;
				BigDecimal val = sas.getTotalValueOfNotesRs2000U();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes2000U(val);
				sas.setStatus(2);
			}

			if (CurrencyType.UNPROCESS == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_500.getDenomination()) {
				BigDecimal val = sas.getTotalValueOfNotesRs500U();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes500U(val);
				sas.setStatus(2);
			}

			if (CurrencyType.UNPROCESS == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_100.getDenomination()) {
				BigDecimal val = sas.getTotalValueOfNotesRs100U();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes100U(val);
				sas.setStatus(2);
			}

			if (CurrencyType.UNPROCESS == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_200.getDenomination()) {
				BigDecimal val = sas.getTotalValueOfNotesRs200U();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes200U(val);
				sas.setStatus(2);
			}

			if (CurrencyType.UNPROCESS == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_50.getDenomination()) {
				BigDecimal val = sas.getTotalValueOfNotesRs50U();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes50U(val);
				sas.setStatus(2);
			}

			if (CurrencyType.UNPROCESS == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_200.getDenomination()) {
				BigDecimal val = sas.getTotalValueOfNotesRs20U();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes20U(val);
				sas.setStatus(2);
			}

			if (CurrencyType.UNPROCESS == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_10.getDenomination()) {
				BigDecimal val = sas.getTotalValueOfNotesRs10U();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes10U(val);
				sas.setStatus(2);
			}

			if (CurrencyType.UNPROCESS == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_5.getDenomination()) {
				BigDecimal val = sas.getTotalValueOfNotesRs5U();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes5U(val);
				sas.setStatus(2);
			}

			if (CurrencyType.UNPROCESS == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_2.getDenomination()) {
				BigDecimal val = sas.getTotalValueOfNotesRs2U();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes2U(val);
				sas.setStatus(2);
			}

			if (CurrencyType.UNPROCESS == sasRelease.getCategory()
					&& sasRelease.getDenomination() == DenominationType.DENOM_1.getDenomination()) {
				BigDecimal val = sas.getTotalValueOfNotesRs1U();
				val.add(BigDecimal.ONE);
				sas.setReleasedNotes1U(val);
				sas.setStatus(2);
			}
			// Close Unprocess Code
		}
		cashPaymentService.updateSASForSASRelease(sas);
		return SASReleasedList;
	}

	@RequestMapping("/CRAPayment")
	public ModelAndView craPayment(HttpSession session) {
		CRA obj = new CRA();
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		map.put("user", obj);
		List<CRAAccountDetail> vendorName = cashPaymentService.getVendorAndMSPName(user.getIcmcId());
		map.put("vendorName", vendorName);
		return new ModelAndView("CRAPayment", map);
	}

	@RequestMapping(value = "/CRAAllocation")
	@ResponseBody
	public CRA insertCRA(@RequestBody CRA cra, HttpSession session) {
		User user = (User) session.getAttribute("login");
		boolean isAllSuccess = false;

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			cra.setIcmcId(user.getIcmcId());
			cra.setInsertBy(user.getId());
			cra.setUpdateBy(user.getId());
			cra.setInsertTime(Calendar.getInstance());
			cra.setUpdateTime(Calendar.getInstance());
			LOG.error("CRAAllocation " + cra);
			isAllSuccess = cashPaymentService.processCRAAllocation(cra, user);

			if (!isAllSuccess) {
				throw new RuntimeException("Error while saving CRA And CRA Allocation, Please try again");
			}
		}
		return cra;
	}

	@RequestMapping("/editCRADetail")
	public ModelAndView editCRADetail(@RequestParam long id, CRA craDetails, HttpSession session) {
		ModelMap model = new ModelMap();

		craDetails = cashPaymentService.getCRADetailById(id);
		int row = craDetails.getCraAllocations().size();
		/*
		 * if(craDetails.getStatus().toString().equals("ACCEPTED")){ row=row-1;
		 * }
		 */
		User user = (User) session.getAttribute("login");
		List<CRAAccountDetail> vendorName = cashPaymentService.getVendorAndMSPName(user.getIcmcId());
		model.put("vendorName", vendorName);
		model.put("user", craDetails);
		model.put("row", row);
		model.put("status", craDetails.getStatus());
		model.put("cradata", craDetails.getCraAllocations());

		return new ModelAndView("editCRADetail", model);
	}

	@RequestMapping(value = "/insertUpdateCRAAllocationCRA")
	@ResponseBody
	public CRA insertUpdateCRA(@RequestBody CRA cra, HttpSession session) {
		User user = (User) session.getAttribute("login");

		CRA craDetails = cashPaymentService.getCRADetailById(cra.getId());

		if (craDetails.getStatus().equals(OtherStatus.REQUESTED)) {
			boolean isAllSuccess = false;
			synchronized (icmcService.getSynchronizedIcmc(user)) {
				cra.setStatus(OtherStatus.CANCELLED);
				cashPaymentService.updateCRAAndCRAAllocation(cra);
				cra.setIcmcId(user.getIcmcId());
				cra.setInsertBy(user.getId());
				cra.setUpdateBy(user.getId());
				cra.setInsertTime(Calendar.getInstance());
				cra.setUpdateTime(Calendar.getInstance());
				cra.setId(null);
				isAllSuccess = cashPaymentService.processCRAAllocation(cra, user);

				if (!isAllSuccess) {
					throw new RuntimeException("Error while saving CRA And CRA Allocation, Please try again");
				}
			}
		} else {
			throw new BaseGuiException(craDetails.getStatus() + " by vault Management Can not be edited");
		}
		return cra;
	}

	@RequestMapping(value = "/getAccountNumberForCRA", method = RequestMethod.GET)
	@ResponseBody
	public String getAccountNumber(@RequestParam(value = "mspName") String mspName,
			@RequestParam(value = "vendor") String vendor, HttpSession session) {
		User user = (User) session.getAttribute("login");

		return cashPaymentService.getAccountNumberByMSPName(mspName, vendor, user.getIcmcId());
	}

	@RequestMapping("/otherBankPayment")
	public ModelAndView otherBankPayment(HttpSession session) {
		OtherBank obj = new OtherBank();
		// User user = (User)session.getAttribute("login");
		ModelMap map = new ModelMap();
		map.put("user", obj);
		// List<CRAAccountDetail> vendorName =
		// cashPaymentService.getVendorAndMSPName(user.getIcmcId());
		// map.put("vendorName", vendorName);
		return new ModelAndView("otherBankPayment", map);
	}

	@RequestMapping(value = "/editViewOtherBankPayment")
	public ModelAndView editViewOtherBankAllocation(@RequestParam long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		OtherBank otherBankRecord = cashPaymentService.getOtherBankRecordById(user.getIcmcId(), id);
		int row = otherBankRecord.getOtherBankAllocations().size();

		ModelMap map = new ModelMap();

		map.put("user", otherBankRecord);
		map.put("status", otherBankRecord.getStatus());
		map.put("row", row);
		map.put("otherBankAloocationRecord", otherBankRecord.getOtherBankAllocations());

		return new ModelAndView("editViewOtherBankPayment", map);
	}

	@RequestMapping(value = "/OtherBankAllocation")
	@ResponseBody
	public OtherBank insertOtherBankPayment(@RequestBody OtherBank otherBank, HttpSession session) {
		User user = (User) session.getAttribute("login");

		boolean isAllSuccess = false;

		synchronized (icmcService.getSynchronizedIcmc(user)) {

			otherBank.setIcmcId(user.getIcmcId());
			otherBank.setInsertBy(user.getId());
			otherBank.setUpdateBy(user.getId());
			otherBank.setInsertTime(Calendar.getInstance());
			otherBank.setUpdateTime(Calendar.getInstance());
			otherBank.setStatus(OtherStatus.REQUESTED);

			isAllSuccess = cashPaymentService.processOtherBankAllocation(otherBank, user);

			if (!isAllSuccess) {
				throw new RuntimeException("Problem while making OtherBank payment, Please try again");
			}
		}
		return otherBank;
	}

	@RequestMapping(value = "/updateInsertOtherBankAllocation")
	@ResponseBody
	public OtherBank insertUpdateOtherBankPayment(@RequestBody OtherBank otherBank, HttpSession session) {
		User user = (User) session.getAttribute("login");

		boolean isAllSuccess = false;
		long otherBankId = otherBank.getId();

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			otherBank.setIcmcId(user.getIcmcId());
			otherBank.setInsertBy(user.getId());
			otherBank.setUpdateBy(user.getId());
			otherBank.setInsertTime(Calendar.getInstance());
			otherBank.setUpdateTime(Calendar.getInstance());
			otherBank.setId(null);
			otherBank.setStatus(OtherStatus.REQUESTED);
			cashPaymentService.processOtherBankPaymentCancellation(user, otherBankId);

			isAllSuccess = cashPaymentService.processOtherBankAllocation(otherBank, user);

			if (!isAllSuccess) {
				throw new RuntimeException("Error while making OtherBank payment, Please try again");
			}

			cashPaymentService.updateEditOtherBankAndOtherBankAllocation(user.getIcmcId(), otherBankId);
		}
		return otherBank;
	}

	@RequestMapping(value = "/binRequestForCRAPayment")
	@ResponseBody
	public List<CRAAllocation> craPayment(@RequestBody CRA cra, HttpSession session) {
		User user = (User) session.getAttribute("login");

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			try {
				cashPaymentService.processCRAPayment(cra.getCraAllocations(), user);
			} catch (Exception ex) {
				LOG.error("Error has occred", ex);
				throw ex;
			}
		}

		return cra.getCraAllocations();
	}

	@RequestMapping("/viewCRA")
	public ModelAndView getCRA(HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		// List<CRA> craList = cashPaymentService.getCRARecord(user.getIcmcId(),
		// sDate, eDate);

		List<CRA> craList = cashPaymentService.getCRARequestAcceptRecord(user.getIcmcId(), sDate, eDate);

		return new ModelAndView("/viewCRA", "records", craList);
	}

	@RequestMapping("/viewOtherBankPayment")
	public ModelAndView getOtherBankPayment(HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		// public List<OtherBank>
		// getOtherBankPaymentRequestAcceptRecord(BigInteger icmcId, Calendar
		// sDate, Calendar eDate)

		/*
		 * List<OtherBank> otherBankList =
		 * cashPaymentService.getOtherBankPaymentRecord(user.getIcmcId(), sDate,
		 * eDate);
		 */
		List<OtherBank> otherBankList = cashPaymentService.getOtherBankPaymentRequestAcceptRecord(user.getIcmcId(),
				sDate, eDate);

		return new ModelAndView("/viewOtherBankPayment", "records", otherBankList);
	}

	@RequestMapping("/ORVVoucher")
	public ModelAndView IRVVoucher(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		Sas obj = new Sas();
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<Sas> allSasList = cashPaymentService.getAcceptSolId(user.getIcmcId(), sDate, eDate);
		// List<Sas> sasList = cashPaymentService.getSolId(user.getIcmcId(),
		// sDate, eDate);
		List<Sas> sasList = new LinkedList<>(allSasList);
		for (Sas sas : allSasList) {
			SASAllocation allocation = cashPaymentService.getRequestedFromSASAllocation(user.getIcmcId(), sDate, eDate,
					sas.getId());
			if (allocation == null)
				sasList.remove(sas);
		}
		map.put("records", sasList);
		map.put("user", obj);
		return new ModelAndView("ORVVoucher", map);
	}

	public boolean isValidSolId(String solId) {
		if (solId.length() == 0) {
			return false;
		}
		return true;
	}

	@RequestMapping("/viewORVVoucher")
	public ModelAndView ORVVocuher(@RequestParam(value = "solID") String solId, @RequestParam(value = "id") String id,
			@RequestParam(value = "srNo") String srNumber, HttpSession session) {
		ModelMap map = new ModelMap();
		String[] spltId = id.split(",");
		String idSplit = spltId[1];
		long solAutoId = Long.parseLong(idSplit);

		User user = (User) session.getAttribute("login");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();
		// List<Tuple> summaryList =
		// cashPaymentService.getRecordORVVoucher(solId, sDate, eDate);
		List<Tuple> summaryList = cashPaymentService.getRecordORVVoucherById(solAutoId, sDate, eDate);

		BigDecimal totalValue = UtilityMapper.getTotalValueForSingleORVVoucher(summaryList);
		String numberInwords = ConvertNumberInWords.getNumberInWords(totalValue.intValue());

		String userName = user.getName();
		String userId = user.getId();
		String icmcName = cashPaymentService.getICMCName(user.getIcmcId());

		map.put("record", summaryList);
		map.put("userName", userName);
		map.put("userId", userId);
		map.put("icmcName", icmcName);
		map.put("numberInwords", numberInwords);
		return new ModelAndView("viewORVVoucher", map);

	}

	@RequestMapping("/printAllVoucher")
	public ModelAndView printAllVoucher(HttpSession session) {
		ModelMap map = new ModelMap();

		User user = (User) session.getAttribute("login");
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<Tuple> summaryList = new ArrayList<>();
		List<String> recordTotalInWordList = new ArrayList<>();
		List<Tuple> allSummaryList = new ArrayList<>();
		List<Sas> solIdList = cashPaymentService.getSolId(user.getIcmcId(), sDate, eDate);
		LOG.error("solIdList for all printe vaucher " + solIdList);
		String userName = user.getName();
		String userId = user.getId();
		if (solIdList != null) {
			for (Sas sas : solIdList) {
				// summaryList =
				// cashPaymentService.getRecordORVVoucher(sas.getSolID(), sDate,
				// eDate, user.getIcmcId());
				summaryList = cashPaymentService.getRecordORVVoucherById(sas.getId(), sDate, eDate);

				allSummaryList.addAll(summaryList);

				// Code for converting a number into words.. 21st April
				BigDecimal totalValue = sas.getTotalValue();
				String numberInwords = ConvertNumberInWords.getNumberInWords(totalValue.intValue());
				recordTotalInWordList.add(numberInwords);
				LOG.error("Number in Words: " + numberInwords);
			}
		}
		map.put("record", allSummaryList);
		map.put("recordTotalInWordList", recordTotalInWordList);
		map.put("userName", userName);
		map.put("userId", userId);
		return new ModelAndView("viewORVVoucher1", map);
	}

	@RequestMapping("/viewSelectedORVVoucher")
	public ModelAndView selectedORVVocuher(@RequestParam(value = "id") Long sasAutoId[], HttpSession session) {
		ModelMap map = new ModelMap();

		User user = (User) session.getAttribute("login");
		List<Tuple> summaryList = new ArrayList<>();
		List<String> recordTotalInWordList = new ArrayList<>();
		List<Tuple> allSummaryList = new ArrayList<>();
		List<Sas> solIdList = cashPaymentService.getSasRecordById(user.getIcmcId(), sasAutoId);
		LOG.error("solIdList for all printe vaucher " + solIdList);
		String userName = user.getName();
		String userId = user.getId();
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();
		if (solIdList != null) {
			for (Sas sas : solIdList) {

				summaryList = cashPaymentService.getRecordORVVoucherById(sas.getId(), sDate, eDate);
				allSummaryList.addAll(summaryList);

				BigDecimal totalValue = sas.getTotalValue();
				String numberInwords = ConvertNumberInWords.getNumberInWords(totalValue.intValue());
				recordTotalInWordList.add(numberInwords);
				LOG.error("Number in Words: " + numberInwords);
			}
		}
		map.put("record", allSummaryList);
		map.put("recordTotalInWordList", recordTotalInWordList);
		map.put("userName", userName);
		map.put("userId", userId);

		return new ModelAndView("viewORVVoucher1", map);
	}

	@RequestMapping("/acceptCRAPayment")
	public ModelAndView getCRAAllocation(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		CRAAllocation obj = new CRAAllocation();
		List<CRAWrapper> craWrapperList = new ArrayList<>();

		List<CRA> craListForID = cashPaymentService.getRecordFromCRA(user.getIcmcId());
		List<CRA> MSPList = new LinkedList<CRA>();
		for (CRA cralist : craListForID) {
			MSPList.add(cralist);
		}
		List<CRAAllocation> craAllocation = cashPaymentService.craPaymentDetailForAccept(user.getIcmcId());
		for (CRA cra : craListForID) {
			List<Tuple> craAllocationList = cashPaymentService.craRequestSummary(cra.getId());

			CRAWrapper craWrapper = new CRAWrapper();
			craWrapper.setTupleList(craAllocationList);
			craWrapper.setCra(cra);
			craWrapper.getCra().setVendor(cra.getVendor());
			craWrapper.getCra().setMspName(cra.getMspName());

			craWrapperList.add(craWrapper);
		}
		map.put("user", obj);
		map.put("craWrapperList", craWrapperList);
		map.put("craAllocation", craAllocation);
		map.put("craList", MSPList);
		return new ModelAndView("/acceptCRAPayment", map);
	}

	@RequestMapping("/AcceptCRAPayment")
	@ResponseBody
	public CRAAllocation updateDorvIndentStatus(@RequestBody CRAAllocation craAllocation, HttpSession session) {

		User user = (User) session.getAttribute("login");
		CRAAllocation cra = new CRAAllocation();
		BinTransaction btx = new BinTransaction();
		boolean updateRecord = false;

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			btx.setIcmcId(user.getIcmcId());
			cra.setId(craAllocation.getId());
			craAllocation.setIcmcId(user.getIcmcId());
			updateRecord = cashPaymentService.updateBinTransactionAndCRAAllocation(craAllocation);
			if (!updateRecord) {
				throw new RuntimeException("Error while saving CRA Payment, Please try again");
			}
		}
		return craAllocation;

	}

	@RequestMapping("/AcceptOtherBankPayment")
	public ModelAndView getOtherBankPaymentForAccept(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<OtherBankAllocation> otherBankList = cashPaymentService.getOtherBankForAccept(user.getIcmcId());
		return new ModelAndView("/AcceptOtherBankPayment", "records", otherBankList);
	}

	@RequestMapping("/acceptOtherBankPayment")
	@ResponseBody
	public OtherBankAllocation updateOtherBankIndentStatus(@RequestBody OtherBankAllocation otherBankAllocation,
			HttpSession session) {

		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			try {
				cashPaymentService.processForAcceptanceOtherBankPayment(otherBankAllocation, now, user);
			} catch (Exception ex) {
				throw new BaseGuiException("Other bank acceptance" + ex.getMessage());
			}
		}
		return otherBankAllocation;
	}

	@RequestMapping(value = "/getBinForCRA")
	@ResponseBody
	public List<BinTransaction> getBinInDropDown(@RequestBody BinTransaction binTx, HttpSession session) {
		User user = (User) session.getAttribute("login");
		binTx.setIcmcId(user.getIcmcId());
		binTx.setRcvBundle(binTx.getRcvBundle());

		return cashPaymentService.getBinForCRAPayment(binTx);
	}

	@RequestMapping(value = "/forwardCRAPayment")
	@ResponseBody
	public ForwardBundleForCRAPayment getBinInDropDown(@RequestBody ForwardBundleForCRAPayment forwardBundlePayment,
			HttpSession session) {
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {

			forwardBundlePayment.setIcmcId(user.getIcmcId());
			Calendar now = Calendar.getInstance();
			forwardBundlePayment.setInsertBy(user.getId());
			forwardBundlePayment.setUpdateBy(user.getId());
			forwardBundlePayment.setInsertTime(now);
			forwardBundlePayment.setUpdateTime(now);

			cashPaymentService.ForwardCRAPayment(forwardBundlePayment);
		}
		return forwardBundlePayment;
	}

	/*
	 * @RequestMapping(value = "/consolidatedRecord")
	 * 
	 * @ResponseBody public List<Tuple> craPayment(@RequestBody List<CRA> cra,
	 * HttpSession session) { User user = (User) session.getAttribute("login");
	 * //List<Tuple> CRAPaymentIndentList =
	 * cashPaymentService.craRequestSummary(user.getIcmcId(), cra.getId());
	 * return CRAPaymentIndentList; }
	 */

	/*
	 * @RequestMapping("/soiledPreparation") public ModelAndView
	 * soiledRemittancePreparation(HttpSession session, ModelMap model) { User
	 * user = (User) session.getAttribute("login"); List<Tuple> soiledSummary =
	 * cashPaymentService.getSoiledSummary(user.getIcmcId());
	 * model.put("soiledBinSummary", soiledSummary); return new
	 * ModelAndView("soiledRemittancePreparation", model); }
	 */

	@RequestMapping("/soiledPreparation")
	@ResponseBody
	public ModelAndView soiledRemittancePreparation(HttpSession session, ModelMap model,
			@RequestParam(value = "soiledvalue", required = false) String soiledvalue) {
		User user = (User) session.getAttribute("login");
		List<Tuple> soiledSummary = null;
		if (soiledvalue != null && soiledvalue.equalsIgnoreCase("mutilated")) {
			soiledSummary = cashPaymentService.getSoiledSummary(user.getIcmcId(), CurrencyType.MUTILATED);
		} else {
			soiledSummary = cashPaymentService.getSoiledSummary(user.getIcmcId(), CurrencyType.SOILED);
		}
		model.put("soiledBinSummary", soiledSummary);

		return new ModelAndView("soiledRemittancePreparation", model);
	}

	@RequestMapping("/getBinForSoiledAndBoxPreparation")
	@ResponseBody
	public List<SoiledRemittanceAllocation> deductSelectedBundleFromSoiled(
			@RequestBody SoiledRemittanceAllocation soiled, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("login");
		List<SoiledRemittanceAllocation> eligibleIndentRequestList = new ArrayList<>();
		StringBuilder sb = null;
		StringBuilder sbBinName = new StringBuilder();
		List<String> prnList = new ArrayList<>();

		synchronized (icmcService.getSynchronizedIcmc(user)) {

			soiled.setInsertBy(user.getId());
			soiled.setUpdateBy(user.getId());
			soiled.setIcmcId(user.getIcmcId());
			List<BinTransaction> bundles = cashPaymentService.getBundleFromBinTxnToCompareForSoiled(soiled.getIcmcId(),
					soiled.getDenomination(), soiled.getCurrencyType());

			BigDecimal bundleFromTxn = BigDecimal.ZERO;
			for (BinTransaction binTx : bundles) {
				bundleFromTxn = bundleFromTxn.add(binTx.getReceiveBundle());
			}
			BigDecimal bundleForRequest = soiled.getRequestBundle();
			if (bundleFromTxn.compareTo(bundleForRequest) >= 0) {
				List<BinTransaction> txnList = cashPaymentService.getBinNumListForSoiled(soiled,
						soiled.getCurrencyType());

				eligibleIndentRequestList = UtilityJpa.getBinForSoiledIndentRequest(txnList, soiled.getDenomination(),
						soiled.getRequestBundle(), user);

				SoiledRemittanceAllocation soiledQrData = cashPaymentService
						.processSoiledBoxPreparation(eligibleIndentRequestList, soiled, user);
				eligibleIndentRequestList.add(soiledQrData);

				if (soiledQrData == null) {
					throw new RuntimeException("Error while Indent Request Saving");
				} else {
					sbBinName.append(soiledQrData.getBinNumber()).append(",");
					try {
						String oldtext = readPRNFileData();
						String replacedtext = oldtext.replaceAll("bin", "" + "SOILED");
						replacedtext = replacedtext.replaceAll("Bin: ", "" + "");
						replacedtext = replacedtext.replaceAll("Branch: ", "" + "");
						replacedtext = replacedtext.replaceAll("Sol ID :", "" + "");
						replacedtext = replacedtext.replaceAll("solId", "" + "");
						replacedtext = replacedtext.replaceAll("branch", "" + soiledQrData.getBox());
						replacedtext = replacedtext.replaceAll("denom", "" + soiledQrData.getDenomination());
						replacedtext = replacedtext.replaceAll("bundle", "" + soiledQrData.getBundle());

						String formattedTotal = CurrencyFormatter.inrFormatter(soiledQrData.getTotal()).toString();
						replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

						sb = new StringBuilder(replacedtext);
						prnList.add(sb.toString());

						UtilityJpa.PrintToPrinter(sb, user);
						LOG.error("#### User Activity :-Prepration Soild Remittence PRN: " + sb);
					} catch (IOException ioe) {
						ioe.printStackTrace();
						LOG.error("PRN CATCH " + ioe.getMessage());
					}
					LOG.error("Prepration Soild Remittence sbBinName.toString() " + sbBinName.toString());
					/*
					 * if(sbBinName.toString() !=""){ prnList.set(0,
					 * sbBinName.toString()); }
					 */
					LOG.error("Prepration Soild Remittence ");
				}
			}
		}
		return eligibleIndentRequestList;
	}

	private String readPRNFileData() throws FileNotFoundException, IOException {
		File file = new File(prnFilePath);
		LOG.error("file for bank print " + file);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		LOG.error("reader " + reader);
		String line = "", oldtext = "";
		while ((line = reader.readLine()) != null) {
			oldtext += line + "\r\n";
		}
		reader.close();
		return oldtext;
	}

	@RequestMapping("/updateCRAStatus")
	@ResponseBody
	public String updateIndentStatus(@RequestParam(value = "id") long id, @RequestParam(value = "bin") String bin,
			@RequestParam(value = "bundle") BigDecimal bundle, @RequestParam(value = "craId") long craId,
			@RequestParam(value = "denomination") Integer denomination,
			@RequestParam(value = "category", required = false) String category, HttpSession session) {
		User user = (User) session.getAttribute("login");
		boolean isAllSuccess = false;
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			isAllSuccess = cashPaymentService.processCRAPaymentRequest(id, bin, bundle, user, craId);
			if (!isAllSuccess) {
				throw new RuntimeException("Error while process CRA Request");
			}
		}
		// Bin Register Code
		Calendar now = Calendar.getInstance();
		BinRegister binRegister = new BinRegister();
		binRegister.setIcmcId(user.getIcmcId());
		binRegister.setInsertBy(user.getId());
		binRegister.setUpdateBy(user.getId());
		binRegister.setInsertTime(now);
		binRegister.setUpdateTime(now);
		binRegister.setDenomination(denomination);
		binRegister.setBinNumber(bin);
		binRegister.setReceiveBundle(BigDecimal.ZERO);
		binRegister.setWithdrawalBundle(bundle);
		binRegister.setDepositOrWithdrawal("WITHDRAWAL");
		binRegister.setType(category);
		cashPaymentService.saveDataInBinRegister(binRegister);

		return "success";
	}

	@RequestMapping("/updateOtherBankStatus")
	public ModelAndView updateOtherBankStatus(@RequestParam(value = "id") long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		boolean isAllSuccess = false;
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			isAllSuccess = cashPaymentService.updateOtherBankAndOtherBankAllocation(user.getIcmcId(), id);
			if (!isAllSuccess) {
				throw new RuntimeException("Error while Update Status in Other Bank");
			}
		}
		return new ModelAndView("welcome");
	}

	@RequestMapping("/updateProcessbundleForCRAPayment")
	public ModelAndView updateProcessbundleForCRAPayment(@RequestParam(value = "id") long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			cashPaymentService.updateProcessbundleForCRAPayment(user.getIcmcId(), id);
		}
		return new ModelAndView("welcome");
	}

	@RequestMapping("/updateSASStatusForPayment")
	public ModelAndView updateSASStatusForPayment(@RequestParam(value = "id") long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		cashPaymentService.updateSASForceHandoverStatus(user.getIcmcId(), id);
		return new ModelAndView("welcome");
	}

	@RequestMapping("/updateCRAStatusForPayment")
	public ModelAndView updateCRAStatusForPayment(@RequestParam(value = "id") long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		boolean isAllSuccess = false;
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			isAllSuccess = cashPaymentService.updateCRAAndCRAAllocation(user.getIcmcId(), id);
			if (!isAllSuccess) {
				throw new RuntimeException("Error while Update Status in Other Bank");
			}
		}
		return new ModelAndView("welcome");
	}

	@RequestMapping("/updateDiversionStatusForPayment")
	public ModelAndView updateDiversionStatusForPayment(@RequestParam(value = "id") long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		boolean isAllSuccess = false;
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			isAllSuccess = cashPaymentService.updateDiversionAndDiversionAllocation(user.getIcmcId(), id);
			if (!isAllSuccess) {
				throw new RuntimeException("Error while Update Status in Diversion");
			}
		}
		return new ModelAndView("welcome");
	}

	@RequestMapping("/updateSoiledStatusForPayment")
	public ModelAndView updateSoiledStatusForPayment(@RequestParam(value = "id") long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		boolean isAllSuccess = false;
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			isAllSuccess = cashPaymentService.updateSoiledAndSoiledAllocation(user.getIcmcId(), id);
			if (!isAllSuccess) {
				throw new RuntimeException("Error while Update Status in Soiled");
			}
		}
		return new ModelAndView("welcome");
	}

	@RequestMapping("/CashOutReport")
	public ModelAndView cashOurReports(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}
		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		List<Tuple> cashOutList = cashPaymentService.getBranchOutRecordFromSAS(user.getIcmcId(), sDate, eDate);
		return new ModelAndView("CashOutReport", "records", cashOutList);
	}

	@RequestMapping(value = "/cancelSAS")
	public ModelAndView cancelSAS(@RequestParam String sasIdList, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Long count = null;
		List<Sas> sasList = new ArrayList<Sas>();
		String str[] = sasIdList.split(":");

		// List<Sas> sasList = cashPaymentService.getSASRecord(user);

		try {
			// count = cashPaymentService.cancelSAS(user, sasList);
			Sas s = new Sas();
			for (int i = 0; i < str.length; i++) {
				Long id = Long.parseLong(str[i]);

				s.setId(id);
				sasList.add(s);
				cashPaymentService.cancelSAS(user, sasList);

			}

			LOG.error("Count: " + count);
		} catch (Exception ex) {
			LOG.error("Error has occred", ex);
			throw ex;
		}
		return new ModelAndView("redirect:./SAS");
	}

	@RequestMapping("/cancelDiversionORV")
	public ModelAndView cancelDiversionORV(@RequestParam Long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			try {
				cashPaymentService.processDiversionORVCancellation(user, id);
			} catch (Exception ex) {
				LOG.error("Error has occred", ex);
				throw ex;
			}
		}
		return new ModelAndView("redirect:./viewDorv");
	}

	@RequestMapping("/cancelOtherBankPayment")
	public ModelAndView cancelOtherBankPayment(@RequestParam Long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			try {
				cashPaymentService.processOtherBankPaymentCancellation(user, id);
			} catch (Exception ex) {
				LOG.error("Error has occred", ex);
				throw ex;
			}
		}
		return new ModelAndView("redirect:./viewOtherBankPayment");
	}

	@RequestMapping("/cancelCRAPayment")
	public ModelAndView cancelCRAPayment(@RequestParam(value = "idFromUI") Long idFromUI, HttpSession session) {
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			try {
				LOG.error("ID From UI==" + idFromUI);
				cashPaymentService.processCRAPaymentCancellation(user, idFromUI);
			} catch (Exception ex) {
				LOG.error("Error has occred", ex);
				throw ex;
			}
		}
		return new ModelAndView("redirect:./acceptCRAPayment");
	}

	@RequestMapping("/machineInputOutputReport")
	public ModelAndView machineInputOutprutReport(HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		ModelMap map = new ModelMap();

		List<Tuple> machineInputOutputList1 = cashPaymentService.machineInputReport(user.getIcmcId(), sDate, eDate, 1);
		List<Tuple> machineInputOutputList2 = cashPaymentService.machineInputReport(user.getIcmcId(), sDate, eDate, 2);

		List<Tuple> machineInputOutputList5 = cashPaymentService.machineInputReport(user.getIcmcId(), sDate, eDate, 5);
		List<Tuple> machineInputOutputList10 = cashPaymentService.machineInputReport(user.getIcmcId(), sDate, eDate,
				10);
		List<Tuple> machineInputOutputList20 = cashPaymentService.machineInputReport(user.getIcmcId(), sDate, eDate,
				20);
		List<Tuple> machineInputOutputList50 = cashPaymentService.machineInputReport(user.getIcmcId(), sDate, eDate,
				50);
		List<Tuple> machineInputOutputList100 = cashPaymentService.machineInputReport(user.getIcmcId(), sDate, eDate,
				100);
		List<Tuple> machineInputOutputList500 = cashPaymentService.machineInputReport(user.getIcmcId(), sDate, eDate,
				500);
		List<Tuple> machineInputOutputList1000 = cashPaymentService.machineInputReport(user.getIcmcId(), sDate, eDate,
				1000);
		List<Tuple> machineInputOutputList2000 = cashPaymentService.machineInputReport(user.getIcmcId(), sDate, eDate,
				2000);
		map.put("machineInputOutputList1", machineInputOutputList1);
		map.put("machineInputOutputList2", machineInputOutputList2);
		map.put("machineInputOutputList5", machineInputOutputList5);
		map.put("machineInputOutputList10", machineInputOutputList10);
		map.put("machineInputOutputList20", machineInputOutputList20);
		map.put("machineInputOutputList50", machineInputOutputList50);
		map.put("machineInputOutputList100", machineInputOutputList100);
		map.put("machineInputOutputList500", machineInputOutputList500);
		map.put("machineInputOutputList1000", machineInputOutputList1000);
		map.put("machineInputOutputList2000", machineInputOutputList2000);

		List<Tuple> machineOutputListFresh1 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 1,
				CurrencyType.FRESH);
		List<Tuple> machineOutputListFresh2 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 2,
				CurrencyType.FRESH);

		List<Tuple> machineOutputListFresh5 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 5,
				CurrencyType.FRESH);
		List<Tuple> machineOutputListFresh10 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				10, CurrencyType.FRESH);
		List<Tuple> machineOutputListFresh20 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				20, CurrencyType.FRESH);
		List<Tuple> machineOutputListFresh50 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				50, CurrencyType.FRESH);
		List<Tuple> machineOutputListFresh100 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				100, CurrencyType.FRESH);
		List<Tuple> machineOutputListFresh500 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				500, CurrencyType.FRESH);
		List<Tuple> machineOutputListFresh1000 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				1000, CurrencyType.FRESH);
		List<Tuple> machineOutputListFresh2000 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				2000, CurrencyType.FRESH);

		map.put("machineOutputListFresh1", machineOutputListFresh1);
		map.put("machineOutputListFresh2", machineOutputListFresh2);
		map.put("machineOutputListFresh5", machineOutputListFresh5);
		map.put("machineOutputListFresh10", machineOutputListFresh10);
		map.put("machineOutputListFresh20", machineOutputListFresh20);
		map.put("machineOutputListFresh50", machineOutputListFresh50);
		map.put("machineOutputListFresh100", machineOutputListFresh100);
		map.put("machineOutputListFresh500", machineOutputListFresh500);
		map.put("machineOutputListFresh1000", machineOutputListFresh1000);
		map.put("machineOutputListFresh2000", machineOutputListFresh2000);

		List<Tuple> machineOutputListATM1 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 1,
				CurrencyType.ATM);
		List<Tuple> machineOutputListATM2 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 2,
				CurrencyType.ATM);

		List<Tuple> machineOutputListATM5 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 5,
				CurrencyType.ATM);
		List<Tuple> machineOutputListATM10 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 10,
				CurrencyType.ATM);
		List<Tuple> machineOutputListATM20 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 20,
				CurrencyType.ATM);
		List<Tuple> machineOutputListATM50 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 50,
				CurrencyType.ATM);
		List<Tuple> machineOutputListATM100 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				100, CurrencyType.ATM);
		List<Tuple> machineOutputListATM500 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				500, CurrencyType.ATM);
		List<Tuple> machineOutputListATM1000 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				1000, CurrencyType.ATM);
		List<Tuple> machineOutputListATM2000 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				2000, CurrencyType.ATM);

		map.put("machineOutputListATM1", machineOutputListATM1);
		map.put("machineOutputListATM2", machineOutputListATM2);
		map.put("machineOutputListATM5", machineOutputListATM5);
		map.put("machineOutputListATM10", machineOutputListATM10);
		map.put("machineOutputListATM20", machineOutputListATM20);
		map.put("machineOutputListATM50", machineOutputListATM50);
		map.put("machineOutputListATM100", machineOutputListATM100);
		map.put("machineOutputListATM500", machineOutputListATM500);
		map.put("machineOutputListATM1000", machineOutputListATM1000);
		map.put("machineOutputListATM2000", machineOutputListATM2000);

		List<Tuple> machineOutputListISSUABLE1 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				1, CurrencyType.ISSUABLE);
		List<Tuple> machineOutputListISSUABLE2 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				2, CurrencyType.ISSUABLE);

		List<Tuple> machineOutputListISSUABLE5 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				5, CurrencyType.ISSUABLE);
		List<Tuple> machineOutputListISSUABLE10 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				10, CurrencyType.ISSUABLE);
		List<Tuple> machineOutputListISSUABLE20 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				20, CurrencyType.ISSUABLE);
		List<Tuple> machineOutputListISSUABLE50 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				50, CurrencyType.ISSUABLE);
		List<Tuple> machineOutputListISSUABLE100 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate,
				eDate, 100, CurrencyType.ISSUABLE);
		List<Tuple> machineOutputListISSUABLE500 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate,
				eDate, 500, CurrencyType.ISSUABLE);
		List<Tuple> machineOutputListISSUABLE1000 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate,
				eDate, 1000, CurrencyType.ISSUABLE);
		List<Tuple> machineOutputListISSUABLE2000 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate,
				eDate, 2000, CurrencyType.ISSUABLE);

		map.put("machineOutputListISSUABLE1", machineOutputListISSUABLE1);
		map.put("machineOutputListISSUABLE2", machineOutputListISSUABLE2);
		map.put("machineOutputListISSUABLE5", machineOutputListISSUABLE5);
		map.put("machineOutputListISSUABLE10", machineOutputListISSUABLE10);
		map.put("machineOutputListISSUABLE20", machineOutputListISSUABLE20);
		map.put("machineOutputListISSUABLE50", machineOutputListISSUABLE50);
		map.put("machineOutputListISSUABLE100", machineOutputListISSUABLE100);
		map.put("machineOutputListISSUABLE500", machineOutputListISSUABLE500);
		map.put("machineOutputListISSUABLE1000", machineOutputListISSUABLE1000);
		map.put("machineOutputListISSUABLE2000", machineOutputListISSUABLE2000);

		List<Tuple> machineOutputListSOILED1 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 1,
				CurrencyType.SOILED);
		List<Tuple> machineOutputListSOILED2 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 2,
				CurrencyType.SOILED);

		List<Tuple> machineOutputListSOILED5 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate, 5,
				CurrencyType.SOILED);
		List<Tuple> machineOutputListSOILED10 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				10, CurrencyType.SOILED);
		List<Tuple> machineOutputListSOILED20 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				20, CurrencyType.SOILED);
		List<Tuple> machineOutputListSOILED50 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				50, CurrencyType.SOILED);
		List<Tuple> machineOutputListSOILED100 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				100, CurrencyType.SOILED);
		List<Tuple> machineOutputListSOILED500 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				500, CurrencyType.SOILED);
		List<Tuple> machineOutputListSOILED1000 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				1000, CurrencyType.SOILED);
		List<Tuple> machineOutputListSOILED2000 = cashPaymentService.machineOutputReport(user.getIcmcId(), sDate, eDate,
				2000, CurrencyType.SOILED);

		map.put("machineOutputListSOILED1", machineOutputListSOILED1);
		map.put("machineOutputListSOILED2", machineOutputListSOILED2);
		map.put("machineOutputListSOILED5", machineOutputListSOILED5);
		map.put("machineOutputListSOILED10", machineOutputListSOILED10);
		map.put("machineOutputListSOILED20", machineOutputListSOILED20);
		map.put("machineOutputListSOILED50", machineOutputListSOILED50);
		map.put("machineOutputListSOILED100", machineOutputListSOILED100);
		map.put("machineOutputListSOILED500", machineOutputListSOILED500);
		map.put("machineOutputListSOILED1000", machineOutputListSOILED1000);
		map.put("machineOutputListSOILED2000", machineOutputListSOILED2000);

		return new ModelAndView("/machineInputOutputReport", map);
	}

	@RequestMapping("/viewPreparedSoiledBoxes")
	public ModelAndView viewPreparedSoiledBoxes(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<BinTransaction> preparedBoxList = cashPaymentService.getPreparedSoiledBoxes(user.getIcmcId());
		return new ModelAndView("/viewPreparedSoiledBoxes", "records", preparedBoxList);
	}

	@RequestMapping("/cancelPreparedSoiledBox")
	@ResponseBody
	public String cancelPreparedSoiledBox(HttpSession session, @RequestParam(value = "binNum") String binNum) {
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			cashPaymentService.processForCancelPreparedSoildBox(binNum, user.getIcmcId());
		}
		return binNum;
	}

	@RequestMapping("/indentAndPayment")
	public ModelAndView indentAndPaymentReport(HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<Tuple> branchPaymentList = cashPaymentService.getBranchPaymentTotal(user.getIcmcId(), sDate, eDate);
		ModelMap map = new ModelMap();
		List<Tuple> craProcessedDataList = cashPaymentService.getCraPaymentTotalProcessed(user.getIcmcId(), sDate,
				eDate);
		List<CRAAllocation> craProcessedData = new ArrayList<>();

		CRAAllocation cra = new CRAAllocation();

		for (Tuple tuple : craProcessedDataList) {
			if (tuple.get(0, Integer.class).equals(2000)) {
				cra.setDenom2000Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				cra.setDenom1000Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				cra.setDenom500Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				cra.setDenom200Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				cra.setDenom100Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				cra.setDenom50Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				cra.setDenom20Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				cra.setDenom10Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(5)) {
				cra.setDenom5Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(2)) {
				cra.setDenom2Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(1)) {
				cra.setDenom1Pieces(tuple.get(1, BigDecimal.class));
			}
		}
		craProcessedData.add(cra);

		List<Tuple> craReleasedDataList = cashPaymentService.getCraPaymentTotalReleased(user.getIcmcId(), sDate, eDate);
		List<CRAAllocation> craReleasedData = new ArrayList<>();

		CRAAllocation craReleased = new CRAAllocation();

		for (Tuple tuple : craReleasedDataList) {
			if (tuple.get(0, Integer.class).equals(2000)) {

				BigDecimal vault2000 = tuple.get(1, BigDecimal.class);
				BigDecimal forward2000 = tuple.get(2, BigDecimal.class);
				if (vault2000 == null) {
					vault2000 = BigDecimal.ZERO;
				} else if (forward2000 == null) {
					forward2000 = BigDecimal.ZERO;
				}
				craReleased.setDenom2000Pieces(vault2000.add(forward2000));
			}

			if (tuple.get(0, Integer.class).equals(1000)) {

				BigDecimal vault1000 = tuple.get(1, BigDecimal.class);
				BigDecimal forward1000 = tuple.get(2, BigDecimal.class);
				if (vault1000 == null) {
					vault1000 = BigDecimal.ZERO;
				} else if (forward1000 == null) {
					forward1000 = BigDecimal.ZERO;
				}

				craReleased.setDenom1000Pieces(vault1000.add(forward1000));
			}

			if (tuple.get(0, Integer.class).equals(500)) {

				BigDecimal vault500 = tuple.get(1, BigDecimal.class);
				BigDecimal forward500 = tuple.get(2, BigDecimal.class);
				if (vault500 == null) {
					vault500 = BigDecimal.ZERO;
				} else if (forward500 == null) {
					forward500 = BigDecimal.ZERO;
				}

				craReleased.setDenom500Pieces(vault500.add(forward500));
			}

			if (tuple.get(0, Integer.class).equals(200)) {

				BigDecimal vault200 = tuple.get(1, BigDecimal.class);
				BigDecimal forward200 = tuple.get(2, BigDecimal.class);
				if (vault200 == null) {
					vault200 = BigDecimal.ZERO;
				} else if (forward200 == null) {
					forward200 = BigDecimal.ZERO;
				}

				craReleased.setDenom200Pieces(vault200.add(forward200));
			}

			if (tuple.get(0, Integer.class).equals(100)) {

				BigDecimal vault100 = tuple.get(1, BigDecimal.class);
				BigDecimal forward100 = tuple.get(2, BigDecimal.class);
				if (vault100 == null) {
					vault100 = BigDecimal.ZERO;
				} else if (forward100 == null) {
					forward100 = BigDecimal.ZERO;
				}

				craReleased.setDenom100Pieces(vault100.add(forward100));
			}

			if (tuple.get(0, Integer.class).equals(50)) {

				BigDecimal vault50 = tuple.get(1, BigDecimal.class);
				BigDecimal forward50 = tuple.get(2, BigDecimal.class);
				if (vault50 == null) {
					vault50 = BigDecimal.ZERO;
				} else if (forward50 == null) {
					forward50 = BigDecimal.ZERO;
				}

				craReleased.setDenom50Pieces(vault50.add(forward50));
			}

			if (tuple.get(0, Integer.class).equals(20)) {

				BigDecimal vault20 = tuple.get(1, BigDecimal.class);
				BigDecimal forward20 = tuple.get(2, BigDecimal.class);
				if (vault20 == null) {
					vault20 = BigDecimal.ZERO;
				} else if (forward20 == null) {
					forward20 = BigDecimal.ZERO;
				}
				craReleased.setDenom20Pieces(vault20.add(forward20));
			}

			if (tuple.get(0, Integer.class).equals(10)) {

				BigDecimal vault10 = tuple.get(1, BigDecimal.class);
				BigDecimal forward10 = tuple.get(2, BigDecimal.class);
				if (vault10 == null) {
					vault10 = BigDecimal.ZERO;
				} else if (forward10 == null) {
					forward10 = BigDecimal.ZERO;
				}
				craReleased.setDenom10Pieces(vault10.add(forward10));
			}

			if (tuple.get(0, Integer.class).equals(5)) {

				BigDecimal vault5 = tuple.get(1, BigDecimal.class);
				BigDecimal forward5 = tuple.get(2, BigDecimal.class);
				if (vault5 == null) {
					vault5 = BigDecimal.ZERO;
				} else if (forward5 == null) {
					forward5 = BigDecimal.ZERO;
				}

				craReleased.setDenom5Pieces(vault5.add(forward5));
			}

			if (tuple.get(0, Integer.class).equals(2)) {

				BigDecimal vault2 = tuple.get(1, BigDecimal.class);
				BigDecimal forward2 = tuple.get(2, BigDecimal.class);
				if (vault2 == null) {
					vault2 = BigDecimal.ZERO;
				} else if (forward2 == null) {
					forward2 = BigDecimal.ZERO;
				}
				craReleased.setDenom2Pieces(vault2.add(forward2));
			}

			if (tuple.get(0, Integer.class).equals(1)) {

				BigDecimal vault1 = tuple.get(1, BigDecimal.class);
				BigDecimal forward1 = tuple.get(2, BigDecimal.class);
				if (vault1 == null) {
					vault1 = BigDecimal.ZERO;
				} else if (forward1 == null) {
					forward1 = BigDecimal.ZERO;
				}
				craReleased.setDenom1Pieces(vault1.add(forward1));
			}
		}

		craReleasedData.add(craReleased);

		BigDecimal pendingBundle2000 = BigDecimal.ZERO;
		BigDecimal pendingBundle1000 = BigDecimal.ZERO;
		BigDecimal pendingBundle500 = BigDecimal.ZERO;
		BigDecimal pendingBundle200 = BigDecimal.ZERO;
		BigDecimal pendingBundle100 = BigDecimal.ZERO;
		BigDecimal pendingBundle50 = BigDecimal.ZERO;
		BigDecimal pendingBundle20 = BigDecimal.ZERO;
		BigDecimal pendingBundle10 = BigDecimal.ZERO;
		BigDecimal pendingBundle5 = BigDecimal.ZERO;
		BigDecimal pendingBundle2 = BigDecimal.ZERO;
		BigDecimal pendingBundle1 = BigDecimal.ZERO;

		BigDecimal receivedBundle2000 = BigDecimal.ZERO;
		BigDecimal receivedBundle1000 = BigDecimal.ZERO;
		BigDecimal receivedBundle500 = BigDecimal.ZERO;
		BigDecimal receivedBundle200 = BigDecimal.ZERO;
		BigDecimal receivedBundle100 = BigDecimal.ZERO;
		BigDecimal receivedBundle50 = BigDecimal.ZERO;
		BigDecimal receivedBundle20 = BigDecimal.ZERO;
		BigDecimal receivedBundle10 = BigDecimal.ZERO;
		BigDecimal receivedBundle5 = BigDecimal.ZERO;
		BigDecimal receivedBundle2 = BigDecimal.ZERO;
		BigDecimal receivedBundle1 = BigDecimal.ZERO;

		receivedBundle2000 = cra.getDenom2000Pieces();
		if (receivedBundle2000 == null) {
			receivedBundle2000 = BigDecimal.ZERO;
		}

		receivedBundle1000 = cra.getDenom1000Pieces();
		if (receivedBundle1000 == null) {
			receivedBundle1000 = BigDecimal.ZERO;
		}
		receivedBundle500 = cra.getDenom500Pieces();
		if (receivedBundle500 == null) {
			receivedBundle500 = BigDecimal.ZERO;
		}
		receivedBundle200 = cra.getDenom200Pieces();
		if (receivedBundle200 == null) {
			receivedBundle200 = BigDecimal.ZERO;
		}

		receivedBundle100 = cra.getDenom100Pieces();
		if (receivedBundle100 == null) {
			receivedBundle100 = BigDecimal.ZERO;
		}
		receivedBundle50 = cra.getDenom50Pieces();
		if (receivedBundle50 == null) {
			receivedBundle50 = BigDecimal.ZERO;
		}
		receivedBundle50 = cra.getDenom20Pieces();
		if (receivedBundle50 == null) {
			receivedBundle50 = BigDecimal.ZERO;
		}
		receivedBundle10 = cra.getDenom10Pieces();
		if (receivedBundle10 == null) {
			receivedBundle10 = BigDecimal.ZERO;
		}
		receivedBundle5 = cra.getDenom50Pieces();
		if (receivedBundle5 == null) {
			receivedBundle5 = BigDecimal.ZERO;
		}
		receivedBundle2 = cra.getDenom2Pieces();
		if (receivedBundle2 == null) {
			receivedBundle2 = BigDecimal.ZERO;
		}

		receivedBundle1 = cra.getDenom1Pieces();
		if (receivedBundle1 == null) {
			receivedBundle1 = BigDecimal.ZERO;
		}

		BigDecimal releasedBundle2000 = BigDecimal.ZERO;
		BigDecimal releasedBundle1000 = BigDecimal.ZERO;
		BigDecimal releasedBundle500 = BigDecimal.ZERO;
		BigDecimal releasedBundle200 = BigDecimal.ZERO;
		BigDecimal releasedBundle100 = BigDecimal.ZERO;
		BigDecimal releasedBundle50 = BigDecimal.ZERO;
		BigDecimal releasedBundle20 = BigDecimal.ZERO;
		BigDecimal releasedBundle10 = BigDecimal.ZERO;
		BigDecimal releasedBundle5 = BigDecimal.ZERO;
		BigDecimal releasedBundle2 = BigDecimal.ZERO;
		BigDecimal releasedBundle1 = BigDecimal.ZERO;

		releasedBundle2000 = craReleased.getDenom2000Pieces();
		if (releasedBundle2000 == null) {
			releasedBundle2000 = BigDecimal.ZERO;
		}
		releasedBundle1000 = craReleased.getDenom1000Pieces();
		if (releasedBundle1000 == null) {
			releasedBundle1000 = BigDecimal.ZERO;
		}
		releasedBundle500 = craReleased.getDenom500Pieces();
		if (releasedBundle500 == null) {
			releasedBundle500 = BigDecimal.ZERO;
		}
		releasedBundle200 = craReleased.getDenom200Pieces();
		if (releasedBundle200 == null) {
			releasedBundle200 = BigDecimal.ZERO;
		}
		releasedBundle100 = craReleased.getDenom100Pieces();
		if (releasedBundle100 == null) {
			releasedBundle100 = BigDecimal.ZERO;
		}
		releasedBundle50 = craReleased.getDenom50Pieces();
		if (releasedBundle50 == null) {
			releasedBundle50 = BigDecimal.ZERO;
		}
		releasedBundle20 = craReleased.getDenom20Pieces();
		if (releasedBundle20 == null) {
			releasedBundle20 = BigDecimal.ZERO;
		}
		releasedBundle10 = craReleased.getDenom10Pieces();
		if (releasedBundle10 == null) {
			releasedBundle10 = BigDecimal.ZERO;
		}
		releasedBundle5 = craReleased.getDenom5Pieces();
		if (releasedBundle5 == null) {
			releasedBundle5 = BigDecimal.ZERO;
		}
		releasedBundle2 = craReleased.getDenom2Pieces();
		if (releasedBundle2 == null) {
			releasedBundle2 = BigDecimal.ZERO;
		}
		releasedBundle1 = craReleased.getDenom1Pieces();
		if (releasedBundle1 == null) {
			releasedBundle1 = BigDecimal.ZERO;
		}

		pendingBundle2000 = receivedBundle2000.subtract(releasedBundle2000);
		pendingBundle1000 = receivedBundle1000.subtract(releasedBundle1000);
		pendingBundle500 = receivedBundle500.subtract(releasedBundle500);
		pendingBundle200 = receivedBundle200.subtract(releasedBundle200);
		pendingBundle100 = receivedBundle100.subtract(releasedBundle100);
		pendingBundle50 = receivedBundle50.subtract(releasedBundle50);
		pendingBundle20 = receivedBundle20.subtract(releasedBundle20);
		pendingBundle10 = receivedBundle10.subtract(releasedBundle10);
		pendingBundle5 = receivedBundle5.subtract(releasedBundle5);
		pendingBundle2 = receivedBundle2.subtract(releasedBundle2);
		pendingBundle1 = receivedBundle1.subtract(releasedBundle1);

		map.put("branchPaymentList", branchPaymentList);
		map.put("craProcessedDataList", craProcessedData);
		map.put("craReleased", craReleasedData);

		map.put("pendingBundle2000", pendingBundle2000);
		map.put("pendingBundle1000", pendingBundle1000);
		map.put("pendingBundle500", pendingBundle500);
		map.put("pendingBundle200", pendingBundle200);
		map.put("pendingBundle100", pendingBundle100);
		map.put("pendingBundle50", pendingBundle50);
		map.put("pendingBundle20", pendingBundle20);
		map.put("pendingBundle10", pendingBundle10);
		map.put("pendingBundle5", pendingBundle5);
		map.put("pendingBundle2", pendingBundle2);
		map.put("pendingBundle1", pendingBundle1);

		return new ModelAndView("indentAndPayment", map);

	}

	@RequestMapping("/getSRNumberBySolId")
	@ResponseBody
	public String srNumberBySolId(HttpSession session, @RequestParam(value = "Id") long Id) {

		return cashPaymentService.getSRNumberById(Id);
	}

	@RequestMapping("/TRReports")
	public ModelAndView trReports(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = (Calendar) dateRange.getFromDate().clone();
		}

		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		List<SoiledRemittanceAllocation> trReports = cashPaymentService.TRReports(user.getIcmcId(), sDate, eDate);
		map.put("trReports", trReports);
		return new ModelAndView("TRReports", map);

	}

	@RequestMapping("/editDiversionORV")
	public ModelAndView editDiversionReceipt(@RequestParam Long id, DiversionORV diversionORV, HttpSession session) {
		diversionORV = cashPaymentService.getDiversionORVById(id);
		int row = diversionORV.getDiversionAllocations().size();
		ModelMap map = new ModelMap();

		map.put("user", diversionORV);
		map.put("row", row);
		map.put("status", diversionORV.getOtherStatus());
		map.put("diversionAllocations", diversionORV.getDiversionAllocations());

		return new ModelAndView("editDiversionORV", map);
	}

	@RequestMapping(value = "/UpdateDorvAllocation")
	@ResponseBody
	public DiversionORV updateDiversionORV(@RequestBody DiversionORV diversionORV, HttpSession session) {
		User user = (User) session.getAttribute("login");

		/*
		 * long id =diversionORV.getId();
		 * cashPaymentService.processDiversionORVCancellation(user, id); long
		 * count = cashPaymentService.updateOrvStatus1(id); //code for update
		 * long count1=cashPaymentService.updateOrvAllocationStatus1(id);
		 */

		Calendar now = Calendar.getInstance();

		boolean isAllSuccess = false;

		synchronized (icmcService.getSynchronizedIcmc(user)) {

			diversionORV.setIcmcId(user.getIcmcId());
			diversionORV.setInsertBy(user.getId());
			diversionORV.setUpdateBy(user.getId());
			diversionORV.setInsertTime(now);
			diversionORV.setUpdateTime(now);
			diversionORV.setOtherStatus(OtherStatus.REQUESTED);
			diversionORV.setId(null);

			isAllSuccess = cashPaymentService.processDiversionORVAllocation(diversionORV, user);

			if (!isAllSuccess) {
				throw new RuntimeException("Error while saving Diversion And Diversion Allocation, Please try again");
			}
		}
		return diversionORV;
	}

	@RequestMapping("/TR64Report")
	public ModelAndView getTR64Report(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}

		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		List<SoiledRemittanceAllocation> soiledList = cashPaymentService.getSoiledForAccept(user.getIcmcId(), sDate,
				eDate);

		return new ModelAndView("/TR64Report", "records", soiledList);
	}
}