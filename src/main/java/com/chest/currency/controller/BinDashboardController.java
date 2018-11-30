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
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.chest.currency.entity.model.AuditorIndent;
import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinRegister;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BinTransactionBOD;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CRAAllocation;
import com.chest.currency.entity.model.CashTransfer;
import com.chest.currency.entity.model.ChestMaster;
import com.chest.currency.entity.model.DSB;
import com.chest.currency.entity.model.DateRange;
import com.chest.currency.entity.model.Discrepancy;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.DiversionORVAllocation;
import com.chest.currency.entity.model.FlatSummary;
import com.chest.currency.entity.model.FreshFromRBI;
import com.chest.currency.entity.model.GrandSummary;
import com.chest.currency.entity.model.History;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.IcmcPrinter;
import com.chest.currency.entity.model.Indent;
import com.chest.currency.entity.model.MachineAllocation;
import com.chest.currency.entity.model.OtherBankAllocation;
import com.chest.currency.entity.model.Process;
import com.chest.currency.entity.model.RegionSummary;
import com.chest.currency.entity.model.SASAllocation;
import com.chest.currency.entity.model.Sas;
import com.chest.currency.entity.model.SoiledRemittanceAllocation;
import com.chest.currency.entity.model.TrainingRegister;
import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.BinStatus;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DenominationType;
import com.chest.currency.enums.IcmcAccess;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.VaultSize;
import com.chest.currency.enums.YesNo;
import com.chest.currency.enums.Zone;
import com.chest.currency.jpa.dao.CashPaymentJpaDao;
import com.chest.currency.jpa.persistence.converter.CurrencyFormatter;
import com.chest.currency.service.BinDashboardService;
import com.chest.currency.service.BinTransactionService;
import com.chest.currency.service.CashPaymentService;
import com.chest.currency.service.ICMCService;
import com.chest.currency.service.ProcessingRoomService;
import com.chest.currency.service.UserAdministrationService;
import com.chest.currency.util.HtmlUtils;
import com.chest.currency.util.UtilityJpa;
import com.chest.currency.util.UtilityMapper;
import com.chest.currency.viewBean.FreshBean;
import com.ibm.icu.text.SimpleDateFormat;
import com.mysema.query.Tuple;

@org.springframework.stereotype.Controller
@Scope("session")
public class BinDashboardController {

	private static final Logger LOG = LoggerFactory.getLogger(BinDashboardController.class);

	@Autowired
	BinDashboardService binDashboardService;

	@Autowired
	UserAdministrationService userAdministrationService;

	@Autowired
	BinTransactionService binTransactionService;

	@Autowired
	ICMCService icmcService;

	@Autowired
	CashPaymentJpaDao cashPaymentJpaDao;

	@Autowired
	CashPaymentService cashPaymentService;

	@Autowired
	String documentFilePath;

	@Autowired
	String prnFilePath;

	@Autowired
	ProcessingRoomService processingRoomService;

	@RequestMapping("/viewBinFilter")
	public ModelAndView BinFilter(@ModelAttribute("user") ZoneMaster zm, HttpSession session) {
		ModelMap map = new ModelMap();

		List<BinTransaction> list = binDashboardService
				.getBinNumAndTypeFromBinTransactionForVefiedYes(new BigInteger(zm.getIcmcName()));
		List<BinTransaction> listForNull = binDashboardService
				.getBinNumAndTypeFromBinTransactionForVefiedNo(new BigInteger(zm.getIcmcName()));
		LOG.info("COLOR==" + list.size());
		BinTransaction obj = new BinTransaction();
		// Bin Summary Code Start
		List<Tuple> summaryList = binDashboardService.getRecordForSummary(new BigInteger(zm.getIcmcName()));
		Map<Integer, BinTransaction> mapList = new LinkedHashMap<>();

		for (DenominationType denom : DenominationType.values()) {
			mapList.put(denom.getDenomination(), new BinTransaction());
		}

		for (Tuple bin : summaryList) {
			BinTransaction binTransactionSummary = mapList.get(bin.get(1, Integer.class));

			BigDecimal total = bin.get(2, BigDecimal.class);

			if (bin.get(0, CurrencyType.class).equals(CurrencyType.FRESH)) {
				binTransactionSummary.setFresh(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.ISSUABLE)) {
				binTransactionSummary.setIssuable(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.UNPROCESS)) {
				binTransactionSummary.setUnprocess(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.SOILED)) {
				binTransactionSummary.setSoiled(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.ATM)) {
				binTransactionSummary.setATM(bin.get(2, BigDecimal.class));
			}

			if (binTransactionSummary.getTotal() != null) {
				binTransactionSummary.setTotal(binTransactionSummary.getTotal().add(total));
			} else {
				binTransactionSummary.setTotal(total);
			}
		}
		// Bin Summary Code End

		List<Tuple> summaryListForCoins = binDashboardService
				.getRecordCoinsForSummary(new BigInteger(zm.getIcmcName()));

		map.put("summaryListForCoins", summaryListForCoins);
		map.put("denominationList", DenominationType.values());
		map.put("currencyProcessType", CurrencyType.values());
		map.put("records", list);
		map.put("recordsForNull", listForNull);
		map.put("user", obj);
		map.put("summaryRecords", mapList);
		return new ModelAndView("viewBinFilter", map);
	}

	@RequestMapping("/viewBin")
	public ModelAndView binList(HttpSession session, ZoneMaster zm, HttpServletRequest request) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		IcmcAccess access = user.getRole().getIcmcAccess();

		if (access == IcmcAccess.ICMC) {

			/*
			 * List<BinTransaction> list = binDashboardService
			 * .getBinNumAndTypeFromBinTransactionForVefiedYes(user.getIcmcId())
			 * ;
			 */

			List<BinTransaction> listBox = binDashboardService
					.getBinNumAndTypeFromBinTransactionForVefiedYesBox(user.getIcmcId());

			List<BinTransaction> listBin = binDashboardService
					.getBinNumAndTypeFromBinTransactionForVefiedYesBin(user.getIcmcId());

			List<BinTransaction> listBag = binDashboardService
					.getBinNumAndTypeFromBinTransactionForVefiedYesBag(user.getIcmcId());

			List<BinTransaction> listForNull = binDashboardService
					.getBinNumAndTypeFromBinTransactionForVefiedNo(user.getIcmcId());
			LOG.info("icmc id for listBox listBin listBag==" + user.getIcmcId());
			LOG.info("listBox size ==" + listBox.size());
			LOG.info("listBin size ==" + listBin.size());
			LOG.info("listBag size ==" + listBag.size());
			BinTransaction obj = new BinTransaction();
			// Bin Summary Code Start
			List<Tuple> summaryList = binDashboardService.getRecordForSummary(user.getIcmcId());
			Map<Integer, BinTransaction> mapList = new LinkedHashMap<>();

			for (DenominationType denom : DenominationType.values()) {
				mapList.put(denom.getDenomination(), new BinTransaction());
			}
			for (Tuple bin : summaryList) {
				BinTransaction binTransactionSummary = mapList.get(bin.get(1, Integer.class));

				BigDecimal total = bin.get(2, BigDecimal.class);

				if (bin.get(0, CurrencyType.class).equals(CurrencyType.FRESH)) {
					binTransactionSummary.setFresh(bin.get(2, BigDecimal.class));
				} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.ISSUABLE)) {
					binTransactionSummary.setIssuable(bin.get(2, BigDecimal.class));
				} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.UNPROCESS)) {
					binTransactionSummary.setUnprocess(bin.get(2, BigDecimal.class));
				} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.SOILED)) {
					binTransactionSummary.setSoiled(bin.get(2, BigDecimal.class));
				} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.MUTILATED)) {
					binTransactionSummary.setMutilated(bin.get(2, BigDecimal.class));
				} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.ATM)) {
					binTransactionSummary.setATM(bin.get(2, BigDecimal.class));
				}

				if (binTransactionSummary.getTotal() != null) {
					binTransactionSummary.setTotal(binTransactionSummary.getTotal().add(total));
				} else {
					binTransactionSummary.setTotal(total);
				}
			}
			// Bin Summary Code End

			List<Tuple> summaryListForCoins = binDashboardService.getRecordCoinsForSummary(user.getIcmcId());

			map.put("summaryListForCoins", summaryListForCoins);
			map.put("denominationList", DenominationType.values());
			map.put("currencyProcessType", CurrencyType.values());
			map.put("recordsListBox", listBox);
			map.put("recordsListBag", listBag);
			map.put("recordsListBin", listBin);
			map.put("recordsForNull", listForNull);
			map.put("user", obj);
			map.put("summaryRecords", mapList);
			return new ModelAndView("viewBin", map);
		}

		else if (access == IcmcAccess.ALL) {
			GrandSummary grandSummary = new GrandSummary();

			List<Tuple> grandSummaryList = binDashboardService.getGrandSummary();
			List<Tuple> zoneWiseGrandSummaryList = binDashboardService.getZoneWiseGrandSummary();
			List<Tuple> flatZoneSummaryTuples = binDashboardService.getFlatZoneSummaryList();

			UtilityMapper.mapTupleToGrandSummary(grandSummary, grandSummaryList);
			UtilityMapper.mapTupleToZoneWiseGrandSummary(grandSummary, zoneWiseGrandSummaryList);
			List<FlatSummary> flatZoneSummaryList = UtilityMapper.mapTupleToFlatZoneSummary(flatZoneSummaryTuples);

			map.put("zoneList", Zone.values());
			map.put("user", zm);
			map.put("grandSummary", grandSummary);
			map.put("flatZoneSummaryList", flatZoneSummaryList);

			return new ModelAndView("viewBinAll", map);

		} else if (access == IcmcAccess.ZONE) {
			GrandSummary grandSummary = new GrandSummary();

			List<Tuple> zoneWiseGrandSummaryList = binDashboardService.getZoneWiseGrandSummary();
			UtilityMapper.mapTupleToZoneWiseGrandSummary(grandSummary, zoneWiseGrandSummaryList);
			List<Tuple> flatZoneSummaryTuples = binDashboardService.getFlatZoneSummaryList();
			List<FlatSummary> flatZoneSummaryList = UtilityMapper.mapTupleToFlatZoneSummary(flatZoneSummaryTuples);

			map.put("grandSummary", grandSummary);
			map.put("flatZoneSummaryList", flatZoneSummaryList);
			map.put("zone", user.getZoneId());
			map.put("user", zm);
			return new ModelAndView("viewZoneSummary", map);

		} else if (access == IcmcAccess.REGION) {
			FlatSummary flatSummary = new FlatSummary();

			List<RegionSummary> regionWiseSummaryList = binDashboardService
					.getRegionWiseSummaryList(user.getRegionId());
			flatSummary = UtilityMapper.getFlatRegionSummary(regionWiseSummaryList);

			map.put("flatSummary", flatSummary);
			map.put("regionId", user.getRegionId());
			map.put("user", zm);
			return new ModelAndView("viewRegionSummary", map);

		} else if (access == IcmcAccess.IT_ADMIN) {
			return new ModelAndView("welcome");
		}

		return new ModelAndView("welcome");
	}

	@RequestMapping("/viewBinStatus")
	public ModelAndView viewBinStatus(@RequestParam(value = "denomination", required = false) Integer denomination,
			@RequestParam(value = "binType", required = false) CurrencyType binType, HttpSession session) {
		LOG.debug("VIEW BIN STATUS");
		User user = (User) session.getAttribute("login");
		if (binType != null && denomination != null && denomination != 0 && user != null
				&& binType != CurrencyType.ALL) {
			List<BinTransaction> list = binDashboardService.searchBins(binType, denomination, user.getIcmcId());
			return new ModelAndView("viewBinStatus", "records", list);
		} else if (binType != null && denomination != null && denomination != 0 && user != null
				&& binType == CurrencyType.ALL) {
			List<BinTransaction> list = binDashboardService.searchBins(denomination, user.getIcmcId());
			return new ModelAndView("viewBinStatus", "records", list);
		} else if (binType != null && denomination != null && denomination == 0 && user != null
				&& binType == CurrencyType.ALL) {
			List<BinTransaction> list = binDashboardService.getAllNonEmptyBins(user.getIcmcId());
			return new ModelAndView("viewBinStatus", "records", list);
		} else if (binType != null && denomination != null && denomination == 0 && user != null
				&& binType != CurrencyType.ALL) {
			List<BinTransaction> list = binDashboardService.getAllNonEmptyBins(user.getIcmcId(), binType);
			return new ModelAndView("viewBinStatus", "records", list);
		} else {
			return new ModelAndView("redirect:./viewBin");
		}
	}

	@RequestMapping(value = "/availableCapacity")
	@ResponseBody
	public List<BinTransaction> availCapacity(@RequestParam(value = "binNumber") String bin, HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<BinTransaction> list = binDashboardService.getAvailableCapacity(bin, user.getIcmcId());
		LOG.info("LIST=" + list);
		return list;
	}

	@RequestMapping("/viewBinMaster")
	public ModelAndView viewBinMaster(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<BinMaster> binMasterList = binDashboardService.viewBinMaster(user.getIcmcId());
		return new ModelAndView("viewBinMaster", "records", binMasterList);
	}

	private static CellProcessor[] getProcessorsBinMaster() {
		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(),
				new ParseEnum(CurrencyType.class), new ParseEnum(CurrencyType.class), new ParseEnum(CurrencyType.class),
				new ParseEnum(CurrencyType.class), new ParseEnum(CurrencyType.class), new ParseInt(),
				new ParseEnum(VaultSize.class), };
		return processors;
	}

	@RequestMapping("/saveDataInBin")
	public ModelAndView saveBinMaster(@ModelAttribute("user") BinMaster binMaster, HttpSession session,
			@RequestParam MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		/*
		 * // Special Character Code boolean binuNmber =
		 * HtmlUtils.isHtml(binMaster.getBinNumber());
		 * 
		 * if (binuNmber == false) {
		 * redirectAttributes.addFlashAttribute("duplicateMsg",
		 * "Error : Special Character not allowed"); return new
		 * ModelAndView("redirect:./binMaster"); } // End special CharacterCode
		 */
		User user = (User) session.getAttribute("login");
		binMaster.setInsertBy(user.getId());
		binMaster.setUpdateBy(user.getId());
		binMaster.setIcmcId(user.getIcmcId());
		binMaster.setIsAllocated(0);

		if (file.getSize() == 0) {
			binMaster.setBinNumber(binMaster.getBinNumber().replaceAll("\\s+", ""));
			BinMaster dbBinName = binDashboardService.isValidBin(user.getIcmcId(), binMaster.getBinNumber());
			BoxMaster dbboxname = binTransactionService.isValidBox(user.getIcmcId(), binMaster.getBinNumber());
			if (dbBinName != null) {
				redirectAttributes.addFlashAttribute("duplicateMsg", "BIN with this name already Exists in Bin List..");
				return new ModelAndView("redirect:./binMaster");
			}
			if (dbboxname != null) {
				redirectAttributes.addFlashAttribute("duplicateMsg",
						"BIN with this name already Exists as a Box in Box List..");
				return new ModelAndView("redirect:./binMaster");
			} else {
				binDashboardService.insertDataInBinMaster(binMaster);
				if (binMaster.getOldNewIcmc().equalsIgnoreCase("NEW")) {
					return new ModelAndView("redirect:./saveDataInBinTransactionBOD");
				}
				redirectAttributes.addFlashAttribute("successMsg", "New Bin has been added successfully");
				return new ModelAndView("redirect:./viewBinMaster");
			}
		} else {
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
			List<BinMaster> binMasterRecords = new LinkedList<>();
			BinMaster binMasterUpload = null;
			try {
				try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile),
						CsvPreference.STANDARD_PREFERENCE)) {
					final String[] headers = beanReader.getHeader(true);
					final CellProcessor[] processors = getProcessorsBinMaster();
					while ((binMasterUpload = beanReader.read(BinMaster.class, headers, processors)) != null) {
						binMasterRecords.add(binMasterUpload);
					}
				}
			} catch (Exception r) {
				r.printStackTrace();
				// r.getMessage();
				LOG.info("Error Message: " + r.getMessage());
				// redirectAttributes.addFlashAttribute("errorMsg", "Csv file is
				// not of standard format ");
				redirectAttributes.addFlashAttribute("errorMsg", "" + r.getMessage() + "");
				return new ModelAndView("redirect:./binMaster");
			}
			for (BinMaster tempBin : binMasterRecords) {
				tempBin.setBinNumber(tempBin.getBinNumber().replaceAll("\\s+", ""));
				if ("".equals(tempBin.getBinNumber())) {
					redirectAttributes.addFlashAttribute("duplicateMsg", "Not allowed to upload blank bin");
					return new ModelAndView("redirect:./binMaster");
				}
				BinMaster dbBinName = binDashboardService.isValidBin(user.getIcmcId(), tempBin.getBinNumber());
				BoxMaster dbboxname = binTransactionService.isValidBox(user.getIcmcId(), binMaster.getBinNumber());
				if (dbBinName != null) {
					redirectAttributes.addFlashAttribute("duplicateMsg",
							"Duplicate BIN " + tempBin.getBinNumber() + ", already Existing in Database");
					return new ModelAndView("redirect:./binMaster");
				}
				if (dbboxname != null) {
					redirectAttributes.addFlashAttribute("duplicateMsg",
							"BIN " + tempBin.getBinNumber() + ",same name as Box name , already Defined in Box list.");
					return new ModelAndView("redirect:./binMaster");
				}
			}
			binDashboardService.UploadBinMaster(binMasterRecords, binMaster);
			redirectAttributes.addFlashAttribute("uploadMsg", "List of Bin's have been uploaded successfully");
			if (binMaster.getOldNewIcmc().equalsIgnoreCase("NEW")) {
				return new ModelAndView("redirect:./saveDataInBinTransactionBOD");
			}
			return new ModelAndView("redirect:./viewBinMaster");
		}
	}

	@RequestMapping("/binMaster")
	public ModelAndView binMaster() {
		BinMaster obj = new BinMaster();
		ModelMap map = new ModelMap();
		List<ICMC> icmcList = binDashboardService.getICMCName();
		map.put("records", icmcList);
		map.put("binSizeList", VaultSize.values());
		map.put("user", obj);
		map.addAttribute("documentFilePath", "./files/" + documentFilePath);
		return new ModelAndView("/binMaster", map);
	}

	@RequestMapping("/FIFO")
	public ModelAndView FIFO(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<BinTransaction> fifoList = binDashboardService.getRecordForFIFO(user.getIcmcId());
		return new ModelAndView("/FIFO", "records", fifoList);
	}

	@RequestMapping("/dailyRecon")
	public ModelAndView dailyRecon(HttpSession session) {
		User user = (User) session.getAttribute("login");
		BinTransaction obj = new BinTransaction();
		ModelMap map = new ModelMap();
		List<Tuple> summaryList = binDashboardService.getRecordForSummary(user.getIcmcId());
		Map<Integer, BinTransaction> mapList = new HashMap<>();

		for (DenominationType denom : DenominationType.values()) {
			mapList.put(denom.getDenomination(), new BinTransaction());
		}

		for (Tuple bin : summaryList) {
			BinTransaction binTransactionSummary = mapList.get(bin.get(1, Integer.class));

			BigDecimal total = bin.get(2, BigDecimal.class);

			if (bin.get(0, CurrencyType.class).equals(CurrencyType.FRESH)) {
				binTransactionSummary.setFresh(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.ISSUABLE)) {
				binTransactionSummary.setIssuable(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.UNPROCESS)) {
				binTransactionSummary.setUnprocess(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.SOILED)) {
				binTransactionSummary.setSoiled(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.ATM)) {
				binTransactionSummary.setATM(bin.get(2, BigDecimal.class));
			}

			if (binTransactionSummary.getTotal() != null) {
				binTransactionSummary.setTotal(binTransactionSummary.getTotal().add(total));
			} else {
				binTransactionSummary.setTotal(total);
			}
		}
		map.put("user", obj);
		map.put("summaryRecords", mapList);
		return new ModelAndView("dailyRecon", map);
	}

	@RequestMapping("/machineWiseStatus")
	public ModelAndView machineWiseStatus(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		FreshBean fresh = new FreshBean();
		map.put("user", fresh);
		List<Process> processListatm = binDashboardService.getProcessListAtm(user.getIcmcId());
		map.put("processListAtm", processListatm);
		List<Process> processListissuable = binDashboardService.getProcessListIssuable(user.getIcmcId());
		map.put("processListIssuable", processListissuable);
		List<Process> processListSoiled = binDashboardService.getProcessListSoiled(user.getIcmcId());
		map.put("processListSoiled", processListSoiled);
		List<MachineAllocation> machineList = binDashboardService
				.getMachineAllocationForMachineWiseStatus(user.getIcmcId());
		map.put("machineList", machineList);

		return new ModelAndView("machineWiseStatus", map);
	}

	@RequestMapping("/dailyBinRecon")
	public ModelAndView dailyBinRecon(HttpSession session) {
		BinTransaction obj = new BinTransaction();
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

		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		List<BinTransaction> binTxnListForDailyBinRecon = binDashboardService.recordForDailyBinRecon(user.getIcmcId(),
				sDate, eDate);
		BinTransactionBOD eodForNotes = binDashboardService.checkEODHappenOrNot(user.getIcmcId(), CashType.NOTES, sDate,
				eDate);
		BinTransactionBOD eodForCoins = binDashboardService.checkEODHappenOrNot(user.getIcmcId(), CashType.COINS, sDate,
				eDate);
		if (eodForNotes == null && eodForCoins == null) {
			map.put("EODHappenOrNot", "NO");
		} else {
			map.put("EODHappenOrNot", "YES");
			binTxnListForDailyBinRecon.clear();
		}
		String msgSAS, msgIndent, msgOtherBank, msgSoiled, msgDiversion = "";
		String SASmsg = null, indentMsg = null, otherBankMsg = null, soiledMsg = null, diversionMsg = null,
				craMsg = null;
		String mBranchMsg = null, mDSBMsg = null, mRBIMsg = null, mOtherBankMsg = null, mDiversionMsg = null;
		String pOutPutMachineMsg = null, pOutPutManualMsg = null;

		// Vault Acceptance Pending
		msgSAS = userAdministrationService.getNotificationFromSASAllocation(user.getIcmcId());
		msgIndent = userAdministrationService.getNotificationFromIndent(user.getIcmcId());
		msgOtherBank = userAdministrationService.getNotificationFromOtherBankAllocation(user.getIcmcId());
		msgSoiled = userAdministrationService.getNotificationFromSoiledAllocation(user.getIcmcId());
		msgDiversion = userAdministrationService.getNotificationFromDiversion(user.getIcmcId());
		craMsg = userAdministrationService.getNotificationFromCRA(user.getIcmcId());

		// Machine Allocation Pending

		List<Indent> indentListForBranch = processingRoomService
				.getAggregatedIndentRequestForMachineAllocation(user.getIcmcId(), CashSource.BRANCH, sDate, eDate);
		List<Indent> indentListForDSB = processingRoomService
				.getAggregatedIndentRequestForMachineAllocation(user.getIcmcId(), CashSource.DSB, sDate, eDate);
		List<Indent> indentListForRBI = processingRoomService
				.getAggregatedIndentRequestForMachineAllocation(user.getIcmcId(), CashSource.RBI, sDate, eDate);
		List<Indent> indentListForOtherBank = processingRoomService
				.getAggregatedIndentRequestForMachineAllocation(user.getIcmcId(), CashSource.OTHERBANK, sDate, eDate);
		List<Indent> indentListForDiversion = processingRoomService
				.getAggregatedIndentRequestForMachineAllocation(user.getIcmcId(), CashSource.DIVERSION, sDate, eDate);

		// Processing Output Pending

		List<Tuple> pendingBundleList = processingRoomService.getPendingBundle(user.getIcmcId(), sDate, eDate);
		List<Tuple> pendingBundleListForMachine = processingRoomService.getPendingBundleByMachine(user.getIcmcId(),
				sDate, eDate);

		if (msgSAS != null) {
			SASmsg = "Branch Indent Request.";
		}

		if (msgIndent != null) {
			indentMsg = "Processing Room Indent Request.";
		}

		if (msgOtherBank != null) {
			otherBankMsg = "Other Bank Indent Request.";
		}

		if (msgSoiled != null) {
			soiledMsg = "Soiled Indent Request.";
		}

		if (msgDiversion != null) {
			diversionMsg = "Diversion Indent";
		}

		if (craMsg != null) {
			craMsg = "CRA Indent Request";
		}
		map.put("SASmsg", SASmsg);
		map.put("soiledMsg", soiledMsg);
		map.put("otherBankMsg", otherBankMsg);
		map.put("indentMsg", indentMsg);
		map.put("diversionMsg", diversionMsg);
		map.put("craMsg", craMsg);

		if (indentListForBranch.size() != 0) {
			mBranchMsg = "Machine Allocation For Branch";
		}
		if (indentListForDSB.size() != 0) {
			mDSBMsg = "Machine Allocation For DSB";
		}
		if (indentListForRBI.size() != 0) {
			mRBIMsg = "Machine Allocation For RBI";
		}
		if (indentListForOtherBank.size() != 0) {
			mOtherBankMsg = "Machine Allocation For Other Bank";
		}
		if (indentListForDiversion.size() != 0) {
			mDiversionMsg = "Machine Allocation For Diversion";
		}
		if (pendingBundleListForMachine.size() != 0) {
			pOutPutMachineMsg = "Bundle pending for machine in processing Output";
		}
		if (pendingBundleList.size() != 0) {
			pOutPutManualMsg = "Bundle pending for Manual in processing Output";
		}
		map.put("pOutPutMachineMsg", pOutPutMachineMsg);
		map.put("pOutPutManualMsg", pOutPutManualMsg);

		map.put("mBranchMsg", mBranchMsg);
		map.put("mDSBMsg", mDSBMsg);
		map.put("mRBIMsg", mRBIMsg);
		map.put("mOtherBankMsg", mOtherBankMsg);
		map.put("mDiversionMsg", mDiversionMsg);

		map.put("totalBin", binTxnListForDailyBinRecon.size());
		map.put("user", obj);
		map.put("dailyBinRecon", binTxnListForDailyBinRecon);
		return new ModelAndView("/dailyBinRecon", map);
	}

	@RequestMapping(value = "/getDailyBinRecordData")
	@ResponseBody
	public float getDailyBinReco(@RequestBody BinTransaction binTransaction) {
		float bundle = binDashboardService.getReceiveBundleForDailyReconBin(binTransaction);
		return bundle;
	}

	@RequestMapping("/IO2Reports")
	public ModelAndView IO2Reports(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");

		ModelMap map = new ModelMap();

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = (Calendar) dateRange.getFromDate().clone();
		}
		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		List<BinTransactionBOD> binTxBodList = binDashboardService.processIO2(user.getIcmcId(), sDate, eDate,
				dateRange);
		ICMC icmc = binDashboardService.getICMCObj(user.getIcmcId());

		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		Date date = eDate.getTime();
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

		List<Tuple> ibitList = binDashboardService.getIBITForIO2(user.getIcmcId(), sDate, eDate);

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

		String linkBranchSolID = binDashboardService.getLinkBranchSolID(user.getIcmcId().longValue());

		String servicingICMC = binDashboardService.getServicingICMC(linkBranchSolID);

		BigInteger chestSlipNo = binDashboardService.getChestSlipNumber(user.getIcmcId(), sDate, eDate);
		map.put("chestSlipNo", chestSlipNo);

		map.put("servicingICMC", servicingICMC);

		map.put("linkBranchSolID", linkBranchSolID);
		map.put("currentDate", fmt.format(date));
		map.put("icmcName", icmc.getName());
		map.put("icmcChestCode", icmc.getChestCode());
		map.put("icmcChestCodeNumber", icmc.getId());
		map.put("icmcAddress", icmc.getAddress());
		map.put("summaryListForOpeningBalance", binTxBodList.get(0)); // getting
																		// Opening
																		// Balance
		map.put("summaryListForRemittance", binTxBodList.get(1)); // getting
																	// Remittance
																	// Received
		map.put("summaryListForDeposit", binTxBodList.get(2)); // getting
																// Deposit
		map.put("summaryListForRemittanceSoiled", binTxBodList.get(3)); // getting
																		// Remittance
																		// Sent
		map.put("summaryListForWithdrawal", binTxBodList.get(4)); // geting
																	// Withdrawal
		map.put("closingBalanceList", binTxBodList.get(5)); // getting Closing
															// balance
		map.put("summaryListForSoiledNotes", binTxBodList.get(6)); // start
																	// Soiled
																	// Notes
		map.put("summaryListForAddiotionInfoFreshNotes", binTxBodList.get(7)); //// getting
																				//// Fresh
																				//// Info
		map.put("summaryListForCoins", binTxBodList.get(8)); // getting Coins
																// opening
																// balance Info
		map.put("coinSummaryListForRemittance", binTxBodList.get(9)); // getting
																		// Remittance
																		// Received
																		// for
																		// Coins
		map.put("summaryListForCoinWithdrawal", binTxBodList.get(10)); // getting
																		// Coins
																		// Withdrawal
																		// Transfer
		map.put("coinClosingBalanceList", binTxBodList.get(11)); // getting
																	// Closing
		if (dateRange.getFromDate() != null) {
			// eDate = sDate;
			// sDate = eDate;
			dateRange.setFromDate(eDate);
		} // balance

		return new ModelAndView("/IO2Reports", map);
	}

	@RequestMapping("/saveDataInBinTransactionBOD")
	/* @ModelAttribute("user") BinTransactionBOD binTransactionBOD */
	public ModelAndView saveDataInBinTransactionBOD(@ModelAttribute("reportDate") DateRange dateRange,
			HttpSession session, RedirectAttributes redirectAttributes) throws ParseException {
		User user = (User) session.getAttribute("login");

		ChestMaster cMaster = binDashboardService.getLastChestSlipNumber(user.getIcmcId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
		if (cMaster != null) {
			LOG.info("Current Date    " + dateWithoutTime);
			LOG.info("insert database " + cMaster.getInsertTime().getTime());
			String currentDate = sdf.format(dateWithoutTime);
			String databaseDate = sdf.format(cMaster.getInsertTime().getTime());
			if (currentDate.compareTo(databaseDate) == 0) {
				LOG.info("Equal");
			} else {
				LOG.info("Not Equal");
				BigInteger nextChestNumber = cMaster.getChestNumber();
				BigInteger increment = new BigInteger("1");
				cMaster.setId(null);
				cMaster.setChestNumber(nextChestNumber.add(increment));
				Calendar now = Calendar.getInstance();
				cMaster.setInsertBy(user.getId());
				cMaster.setUpdateBy(user.getId());
				cMaster.setInsertTime(now);
				cMaster.setUpdateTime(now);
				binDashboardService.insertChestMaster(cMaster);
			}

		}
		BinTransactionBOD binTransactionBOD = new BinTransactionBOD();
		binTransactionBOD.setInsertBy(user.getId());
		binTransactionBOD.setUpdateBy(user.getId());
		binTransactionBOD.setIcmcId(user.getIcmcId());

		BinTransactionBOD binTransactionBODForSoiled = new BinTransactionBOD();
		binTransactionBODForSoiled.setInsertBy(user.getId());
		binTransactionBODForSoiled.setUpdateBy(user.getId());
		binTransactionBODForSoiled.setIcmcId(user.getIcmcId());

		BinTransactionBOD binTransactionBODForCoins = new BinTransactionBOD();
		binTransactionBODForCoins.setInsertBy(user.getId());
		binTransactionBODForCoins.setUpdateBy(user.getId());
		binTransactionBODForCoins.setIcmcId(user.getIcmcId());

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();
		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = (Calendar) dateRange.getFromDate().clone();
		} else {
			BinTransactionBOD bodData = binDashboardService.getDataFromBinTransactionBOD(user.getIcmcId());
			if (bodData == null) {
				sDate.add(Calendar.DATE, -1);
				eDate.add(Calendar.DATE, -1);
			}
		}

		binTransactionBOD.setInsertTime(sDate);
		binTransactionBOD.setUpdateTime(sDate);

		binTransactionBODForSoiled.setInsertTime(sDate);
		binTransactionBODForSoiled.setUpdateTime(sDate);

		binTransactionBODForCoins.setInsertTime(sDate);
		binTransactionBODForCoins.setUpdateTime(sDate);

		LOG.info("binTransactionBOD.getInsertTime().getTime() " + binTransactionBOD.getInsertTime().getTime());
		LOG.info("eDate.getTime " + eDate);

		List<Tuple> summaryListForOpeningBalance = binDashboardService.getOpeningBalanceForIO2Report(user.getIcmcId(),
				sDate, eDate, CashType.NOTES);
		LOG.info("summaryListForOpeningBalance " + summaryListForOpeningBalance);
		List<Tuple> summaryListForOpeningBalanceFromIndent = binDashboardService
				.getOpeningBalanceForIO2ReportFromIndent(user.getIcmcId(), sDate, eDate, CashType.NOTES);
		LOG.info("summaryListForOpeningBalanceFromIndent " + summaryListForOpeningBalanceFromIndent);
		LOG.info("binTransactionBOD " + binTransactionBOD);

		mapTupleToBinTransactionBOD(binTransactionBOD, summaryListForOpeningBalance,
				summaryListForOpeningBalanceFromIndent);

		binDashboardService.updateCurrentVersionStatus(binTransactionBOD);

		binTransactionBOD.setCurrentVersion("TRUE");
		binTransactionBOD.setCashType(CashType.NOTES);
		binTransactionBODForCoins.setCurrentVersion("TRUE");
		binTransactionBODForCoins.setCashType(CashType.COINS);

		LOG.info("binTransactionBOD " + binTransactionBOD);
		binDashboardService.insertInBinTxnBOD(binTransactionBOD);

		List<Tuple> soiledBalanceForEOD = binDashboardService.getSoiledBalanceForEOD(user.getIcmcId(), CashType.NOTES);
		mapSoiledTupleToBinTransactionBOD(binTransactionBODForSoiled, soiledBalanceForEOD);
		binTransactionBODForSoiled.setCurrentVersion("TRUE");
		binTransactionBODForSoiled.setCashType(CashType.SOILED);

		binDashboardService.insertInBinTxnBOD(binTransactionBODForSoiled);

		List<Tuple> summaryListForCoinsOpeningBalance = binDashboardService
				.getCoinsOpeningBalanceForIO2Report(user.getIcmcId(), CashType.COINS);
		mapCoinsTupleToBinTransactionBOD(binTransactionBODForCoins, summaryListForCoinsOpeningBalance);

		LOG.info("summaryListForCoinsOpeningBalance " + summaryListForCoinsOpeningBalance);
		LOG.info("binTransactionBODForCoins " + binTransactionBODForCoins);

		binDashboardService.insertInBinTxnBOD(binTransactionBODForCoins);
		redirectAttributes.addFlashAttribute("successMsgForEOD", "EOD successfully");
		// return new ModelAndView("redirect:./dailyBinRecon");

		return new ModelAndView("redirect:./IO2Reports");
	}

	private void mapTupleToBinTransactionBOD(BinTransactionBOD binTransactionBOD, List<Tuple> summaryListForRemittance1,
			List<Tuple> summaryListForOpeningBalanceFromIndent) {

		BigDecimal denomination1 = new BigDecimal(0);
		BigDecimal denomination2 = new BigDecimal(0);
		BigDecimal denomination5 = new BigDecimal(0);
		BigDecimal denomination10 = new BigDecimal(0);
		BigDecimal denomination20 = new BigDecimal(0);
		BigDecimal denomination50 = new BigDecimal(0);
		BigDecimal denomination100 = new BigDecimal(0);
		BigDecimal denomination200 = new BigDecimal(0);
		BigDecimal denomination500 = new BigDecimal(0);
		BigDecimal denomination1000 = new BigDecimal(0);
		BigDecimal denomination2000 = new BigDecimal(0);
		for (Tuple tuple : summaryListForRemittance1) {
			if (tuple.get(0, Integer.class).equals(1)) {
				denomination1 = tuple.get(1, BigDecimal.class);
			}
			if (tuple.get(0, Integer.class).equals(2)) {
				denomination2 = tuple.get(1, BigDecimal.class);
			}
			if (tuple.get(0, Integer.class).equals(5)) {
				denomination5 = tuple.get(1, BigDecimal.class);
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				denomination10 = tuple.get(1, BigDecimal.class);
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				denomination20 = tuple.get(1, BigDecimal.class);
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				denomination50 = tuple.get(1, BigDecimal.class);
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				denomination100 = tuple.get(1, BigDecimal.class);
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				denomination200 = tuple.get(1, BigDecimal.class);
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				denomination500 = tuple.get(1, BigDecimal.class);
			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				denomination1000 = tuple.get(1, BigDecimal.class);
			}
			if (tuple.get(0, Integer.class).equals(2000)) {
				denomination2000 = tuple.get(1, BigDecimal.class);
			}
			binTransactionBOD.setCashType(tuple.get(2, CashType.class));
		}
		for (Tuple tuple : summaryListForOpeningBalanceFromIndent) {
			if (tuple.get(0, Integer.class).equals(1)) {
				denomination1 = denomination1.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(2)) {
				denomination2 = denomination2.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(5)) {
				denomination5 = denomination5.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				denomination10 = denomination10.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				denomination20 = denomination20.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				denomination50 = denomination50.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				denomination100 = denomination100.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				denomination200 = denomination200.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				denomination500 = denomination500.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				denomination1000 = denomination1000.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(2000)) {
				denomination2000 = denomination2000.add(tuple.get(1, BigDecimal.class));
			}
			binTransactionBOD.setCashType(CashType.NOTES);
		}
		binTransactionBOD.setDenomination1(denomination1);
		binTransactionBOD.setDenomination2(denomination2);
		binTransactionBOD.setDenomination5(denomination5);
		binTransactionBOD.setDenomination10(denomination10);
		binTransactionBOD.setDenomination20(denomination20);
		binTransactionBOD.setDenomination50(denomination50);
		binTransactionBOD.setDenomination100(denomination100);
		binTransactionBOD.setDenomination200(denomination200);
		binTransactionBOD.setDenomination500(denomination500);
		binTransactionBOD.setDenomination1000(denomination1000);
		binTransactionBOD.setDenomination2000(denomination2000);
	}

	private void mapSoiledTupleToBinTransactionBOD(BinTransactionBOD binTransactionBOD,
			List<Tuple> soiledBalanceForEOD) {

		for (Tuple tuple : soiledBalanceForEOD) {
			if (tuple.get(0, Integer.class).equals(1)) {
				binTransactionBOD.setDenomination1(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(2)) {
				binTransactionBOD.setDenomination2(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(5)) {
				binTransactionBOD.setDenomination5(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				binTransactionBOD.setDenomination10(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				binTransactionBOD.setDenomination20(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				binTransactionBOD.setDenomination50(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				binTransactionBOD.setDenomination100(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				binTransactionBOD.setDenomination200(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				binTransactionBOD.setDenomination500(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				binTransactionBOD.setDenomination1000(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(2000)) {
				binTransactionBOD.setDenomination2000(tuple.get(1, BigDecimal.class));
			}
			binTransactionBOD.setCashType(tuple.get(2, CashType.class));
		}
	}

	private void mapCoinsTupleToBinTransactionBOD(BinTransactionBOD binTransactionBODForCoins,
			List<Tuple> summaryListForCoinsOpeningBalance) {
		BigDecimal multiplier2000 = BigDecimal.valueOf(2000);
		BigDecimal multiplier2500 = BigDecimal.valueOf(2500);

		for (Tuple tuple : summaryListForCoinsOpeningBalance) {
			if (tuple.get(0, Integer.class).equals(1)) {
				binTransactionBODForCoins.setDenomination1(tuple.get(1, BigDecimal.class).multiply(multiplier2500));
			}
			if (tuple.get(0, Integer.class).equals(2)) {
				binTransactionBODForCoins.setDenomination2(tuple.get(1, BigDecimal.class).multiply(multiplier2500));
			}
			if (tuple.get(0, Integer.class).equals(5)) {
				binTransactionBODForCoins.setDenomination5(tuple.get(1, BigDecimal.class).multiply(multiplier2500));
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				binTransactionBODForCoins.setDenomination10(tuple.get(1, BigDecimal.class).multiply(multiplier2000));
			}
			binTransactionBODForCoins.setCashType(tuple.get(2, CashType.class));
		}
	}

	@RequestMapping("/icmcSummary")
	public ModelAndView icmcSummary(HttpSession session, ZoneMaster zm) {
		User user = (User) session.getAttribute("login");
		BigInteger icmcId = null;
		if (zm.getIcmcName() != null && user.getIcmcId() == null) {
			icmcId = new BigInteger(zm.getIcmcName());
		} else {
			icmcId = user.getIcmcId();
		}

		BinTransaction obj = new BinTransaction();
		ModelMap map = new ModelMap();
		// Bin Summary Code Start
		List<Tuple> summaryList = binDashboardService.getRecordForSummary(icmcId);
		Map<Integer, BinTransaction> mapList = new LinkedHashMap<>();

		for (DenominationType denom : DenominationType.values()) {
			mapList.put(denom.getDenomination(), new BinTransaction());
		}

		for (Tuple bin : summaryList) {
			BinTransaction binTransactionSummary = mapList.get(bin.get(1, Integer.class));

			BigDecimal total = bin.get(2, BigDecimal.class);

			if (bin.get(0, CurrencyType.class).equals(CurrencyType.FRESH)) {
				binTransactionSummary.setFresh(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.ISSUABLE)) {
				binTransactionSummary.setIssuable(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.UNPROCESS)) {
				binTransactionSummary.setUnprocess(bin.get(2, BigDecimal.class));
			} else if (bin.get(0, CurrencyType.class).equals(CurrencyType.SOILED)) {
				binTransactionSummary.setSoiled(bin.get(2, BigDecimal.class));
			}

			else if (bin.get(0, CurrencyType.class).equals(CurrencyType.MUTILATED)) {
				binTransactionSummary.setSoiled(bin.get(2, BigDecimal.class));
			}

			else if (bin.get(0, CurrencyType.class).equals(CurrencyType.ATM)) {
				binTransactionSummary.setATM(bin.get(2, BigDecimal.class));
			}

			if (binTransactionSummary.getTotal() != null) {
				binTransactionSummary.setTotal(binTransactionSummary.getTotal().add(total));
			} else {
				binTransactionSummary.setTotal(total);
			}
		}
		// Bin Summary Code End
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();
		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		List<Tuple> summaryListForCoins = binDashboardService.getRecordCoinsForSummary(icmcId);

		// List<Tuple> pendingBundleFromMachineAllocation =
		// binDashboardService.getMachineAllocationSummary(icmcId,sDate,eDate);

		List<Tuple> pendingBundleFromMachineAllocation = binDashboardService.getCurrentMachineAllocationSummary(icmcId,
				sDate, eDate);

		BigDecimal totalICMCBalance = binDashboardService.getTotalICMCBalance(icmcId);

		map.put("summaryListForCoins", summaryListForCoins);
		map.put("denominationList", DenominationType.values());
		map.put("currencyProcessType", CurrencyType.values());
		map.put("user", obj);
		map.put("summaryRecords", mapList);
		map.put("processingRoom", pendingBundleFromMachineAllocation);
		map.put("totalICMCBalance", totalICMCBalance);
		return new ModelAndView("icmcSummary", map);
	}

	@RequestMapping("/chargeReport")
	public ModelAndView chargeReport(HttpSession session, ZoneMaster zm) {
		User user = (User) session.getAttribute("login");
		BigInteger icmcId = null;
		if (zm.getIcmcName() != null && user.getIcmcId() == null) {
			icmcId = new BigInteger(zm.getIcmcName());
		} else {
			icmcId = user.getIcmcId();
		}
		BinTransaction obj = new BinTransaction();
		ModelMap map = new ModelMap();
		// Bin Summary Code Start
		List<Tuple> summaryList = binDashboardService.getRecordForSummary(icmcId);
		Map<Integer, BinTransaction> mapList = new LinkedHashMap<>();

		for (DenominationType denom : DenominationType.values()) {
			mapList.put(denom.getDenomination(), new BinTransaction());
		}
		for (Tuple bin : summaryList) {
			BinTransaction binTransactionSummary = mapList.get(bin.get(1, Integer.class));
			BigDecimal total = bin.get(2, BigDecimal.class);
			if (bin.get(0, CurrencyType.class).equals(CurrencyType.SOILED)
					|| bin.get(0, CurrencyType.class).equals(CurrencyType.MUTILATED)) {
				binTransactionSummary.setSoiled(bin.get(2, BigDecimal.class));
			} else {
				if (binTransactionSummary.getIssuable() != null)
					binTransactionSummary
							.setIssuable(binTransactionSummary.getIssuable().add(bin.get(2, BigDecimal.class)));
				else
					binTransactionSummary.setIssuable(bin.get(2, BigDecimal.class));
			}
			if (binTransactionSummary.getTotal() != null) {
				binTransactionSummary.setTotal(binTransactionSummary.getTotal().add(total));
			} else {
				binTransactionSummary.setTotal(total);
			}
		}

		List<Tuple> summaryListForCoins = binDashboardService.getRecordCoinsForSummary(icmcId);

		BigDecimal totalICMCBalance = binDashboardService.getTotalICMCBalance(icmcId);

		map.put("summaryListForCoins", summaryListForCoins);
		// map.put("denominationList", DenominationType.values());
		// map.put("currencyProcessType", CurrencyType.values());
		// map.put("user", obj);
		map.put("summaryRecords", mapList);
		// map.put("processingRoom", pendingBundleFromMachineAllocation);
		map.put("totalICMCBalance", totalICMCBalance);
		// map.put("chargeReport", "chargeReport");
		return new ModelAndView("chargeReport", map);
	}

	@RequestMapping(value = "/getRegionWiseSummary")
	@ResponseBody
	public FlatSummary getRegionWiseSummary(@RequestParam(value = "region") String region) {
		List<RegionSummary> regionWiseSummaryList = binDashboardService.getRegionWiseSummaryList(region);
		return UtilityMapper.getFlatRegionSummary(regionWiseSummaryList);
	}

	@RequestMapping("/printerSetting")
	public ModelAndView printerSetting(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		IcmcPrinter icmcPrinter = new IcmcPrinter();
		List<IcmcPrinter> icmcPrinterList = userAdministrationService.printerList(user.getIcmcId());
		map.put("icmcPrinter", icmcPrinter);
		map.put("icmcPrinterList", icmcPrinterList);
		return new ModelAndView("printerSetting", map);
	}

	@RequestMapping("/chestSlip")
	public ModelAndView chestSlip(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = (Calendar) dateRange.getFromDate().clone();
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

		List<BinTransactionBOD> binTxBodList = binDashboardService.processIO2(user.getIcmcId(), sDate, eDate,
				dateRange);
		ICMC icmc = binDashboardService.getICMCObj(user.getIcmcId());

		BigDecimal depositeValue = BigDecimal.ZERO;
		BigDecimal withdrawalValue = BigDecimal.ZERO;
		if (binTxBodList.get(2).getTotalValueOfBankNotes()
				.subtract(
						binTxBodList.get(4).getTotalValueOfBankNotes().add(binTxBodList.get(10).getTotalValueOfCoins()))
				.intValue() > 0) {
			depositeValue = binTxBodList.get(2).getTotalValueOfBankNotes().subtract(
					binTxBodList.get(4).getTotalValueOfBankNotes().add(binTxBodList.get(10).getTotalValueOfCoins()));
		} else {
			withdrawalValue = binTxBodList.get(4).getTotalValueOfBankNotes()
					.add(binTxBodList.get(10).getTotalValueOfCoins())
					.subtract(binTxBodList.get(2).getTotalValueOfBankNotes());
		}

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sDate.getTime();

		BigInteger chestSlipNo = binDashboardService.getChestSlipNumber(user.getIcmcId(), sDate, eDate);
		map.put("chestSlipNo", chestSlipNo);

		map.put("currentDate", fmt.format(date));
		map.put("icmcName", icmc.getName());
		map.put("icmcAddress", icmc.getAddress());
		map.put("summaryListForOpeningBalance", binTxBodList.get(0));
		map.put("summaryListForRemittance", binTxBodList.get(1));
		map.put("summaryListForDeposit", binTxBodList.get(2));
		map.put("summaryListForRemittanceSoiled", binTxBodList.get(3));
		map.put("summaryListForWithdrawal", binTxBodList.get(4));
		map.put("closingBalanceList", binTxBodList.get(5));
		map.put("summaryListForCoins", binTxBodList.get(8));
		map.put("summaryListForCoinWithdrawal", binTxBodList.get(10));
		map.put("coinClosingBalanceList", binTxBodList.get(11));
		map.put("depositeValue", depositeValue);
		map.put("withdrawalValue", withdrawalValue);

		return new ModelAndView("/chestSlip", map);
	}

	@RequestMapping("/cashBookDeposit")
	public ModelAndView cashBookDeposit(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = (Calendar) dateRange.getFromDate().clone();
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

		Map<String, BranchReceipt> branchDepositList = binDashboardService.getBranchDepositList(user.getIcmcId(), sDate,
				eDate);

		Map<String, DSB> dsbDepositList = binDashboardService.getDSBDepositList(user.getIcmcId(), sDate, eDate);
		Map<String, BankReceipt> bankDepositList = binDashboardService.getBankDepositList(user.getIcmcId(), sDate,
				eDate);
		Map<String, FreshFromRBI> freshFromRBIList = binDashboardService.getFreshDepositList(user.getIcmcId(), sDate,
				eDate);
		Map<String, DiversionIRV> dirvDepositList = binDashboardService.getDirvDepositList(user.getIcmcId(), sDate,
				eDate);

		ICMC icmc = binDashboardService.getICMCObj(user.getIcmcId());

		List<Tuple> ibitList = binDashboardService.getIBITForIRV(user.getIcmcId(), sDate, eDate);

		List<Indent> ibitIndentList = new ArrayList<Indent>();
		Indent indent = new Indent();

		for (Tuple tuple : ibitList) {
			if (tuple.get(0, Integer.class).equals(5)) {
				indent.setDenom5Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(10)) {
				indent.setDenom10Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(20)) {
				indent.setDenom20Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(50)) {
				indent.setDenom50Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(100)) {
				indent.setDenom100Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(200)) {
				indent.setDenom200Pieces(tuple.get(1, BigDecimal.class));

			}

			if (tuple.get(0, Integer.class).equals(500)) {
				indent.setDenom500Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				indent.setDenom1000Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(2000)) {
				indent.setDenom2000Pieces(tuple.get(1, BigDecimal.class));

			}

		}
		ibitIndentList.add(indent);

		ModelMap modelMap = UtilityMapper.getSummarisedTotalForCashBookDepositReport(branchDepositList, dsbDepositList,
				bankDepositList, dirvDepositList, freshFromRBIList, ibitList);

		String linkBranchSolID = binDashboardService.getLinkBranchSolID(user.getIcmcId().longValue());

		String servicingICMC = binDashboardService.getServicingICMC(linkBranchSolID);
		BigInteger chestSlipNo = binDashboardService.getChestSlipNumber(user.getIcmcId(), sDate, eDate);
		map.put("chestSlipNo", chestSlipNo);

		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		Date date = sDate.getTime();
		map.put("date", fmt.format(date));
		map.put("branchDepositList", branchDepositList);
		map.put("dsbDepositList", dsbDepositList);
		map.put("bankDepositList", bankDepositList);
		map.put("freshFromRBIList", freshFromRBIList);
		map.put("dirvDepositList", dirvDepositList);
		map.put("modelMap", modelMap);
		map.put("icmcName", icmc.getName());
		map.put("icmcAddress", icmc.getAddress());
		map.put("ibitListValues", ibitIndentList);
		map.put("servicingICMC", servicingICMC);
		return new ModelAndView("/cashBookDeposit", map);
	}

	@RequestMapping("/cashBookWithdrawal")
	public ModelAndView cashBookWithdrawal(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = (Calendar) dateRange.getFromDate().clone();
		}

		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		List<Sas> allSasList = cashPaymentService.getORVRecords(user.getIcmcId(), sDate, eDate);
		List<Sas> sasList = new LinkedList<>(allSasList);
		for (Sas sas : allSasList) {
			SASAllocation allocation = cashPaymentService.getRequestedFromSASAllocation(user.getIcmcId(), sDate, eDate,
					sas.getId());
			if (allocation != null)
				sasList.remove(sas);
		}
		Map<String, CRAAllocation> craPaymentList = binDashboardService.getCRAForCashBookWithDrawal(user.getIcmcId(),
				sDate, eDate);

		Map<String, DiversionORVAllocation> diversionPaymentList = binDashboardService
				.getoutwardDiversionForCashBookWithDrawal(user.getIcmcId(), sDate, eDate);

		Map<String, OtherBankAllocation> otherBankAllocationList = binDashboardService
				.getOtherBankAllocationForCashBookWithDrawal(user.getIcmcId(), sDate, eDate);

		Map<String, SoiledRemittanceAllocation> soiledAllocationAllocationList = binDashboardService
				.getSoiledAllocationForCashBookWithDrawal(user.getIcmcId(), sDate, eDate);

		List<Tuple> ibitListValues = binDashboardService.getIBITForIRV(user.getIcmcId(), sDate, eDate);

		List<Indent> ibitIndentList = new ArrayList<Indent>();
		Indent indent = new Indent();

		for (Tuple tuple : ibitListValues) {
			if (tuple.get(0, Integer.class).equals(5)) {
				indent.setDenom5Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(10)) {
				indent.setDenom10Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(20)) {
				indent.setDenom20Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(50)) {
				indent.setDenom50Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(100)) {
				indent.setDenom100Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(200)) {
				indent.setDenom200Pieces(tuple.get(1, BigDecimal.class));

			}

			if (tuple.get(0, Integer.class).equals(500)) {
				indent.setDenom500Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				indent.setDenom1000Pieces(tuple.get(1, BigDecimal.class));

			}
			if (tuple.get(0, Integer.class).equals(2000)) {
				indent.setDenom2000Pieces(tuple.get(1, BigDecimal.class));

			}

		}
		ibitIndentList.add(indent);

		ModelMap modelMap = UtilityMapper.getSummarisedTotalForCashBookWithdrawalReport(craPaymentList,
				diversionPaymentList, otherBankAllocationList, soiledAllocationAllocationList, ibitListValues);

		ICMC icmc = binDashboardService.getICMCObj(user.getIcmcId());

		String linkBranchSolID = binDashboardService.getLinkBranchSolID(user.getIcmcId().longValue());

		String servicingICMC = binDashboardService.getServicingICMC(linkBranchSolID);

		BigInteger chestSlipNo = binDashboardService.getChestSlipNumber(user.getIcmcId(), sDate, eDate);
		map.put("chestSlipNo", chestSlipNo);
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyy");
		Date date = sDate.getTime();
		map.put("date", fmt.format(date));
		map.put("craPaymentList", craPaymentList);
		map.put("sasList", sasList);
		map.put("dirvList", diversionPaymentList);
		map.put("otherBankAllocationList", otherBankAllocationList);
		map.put("soiledAllocationAllocationList", soiledAllocationAllocationList);
		map.put("modelMap", modelMap);
		map.put("icmcName", icmc.getName());
		map.put("icmcAddress", icmc.getAddress());
		map.put("ibitListValues", ibitIndentList);
		map.put("servicingICMC", servicingICMC);
		return new ModelAndView("/cashBookWithdrawal", map);
	}

	@RequestMapping("/binWiseSummaryReport")
	public ModelAndView binWiseSummaryReport(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		List<BinTransaction> binWiseSummaryList = binDashboardService.getBinWiseSummary(user.getIcmcId());
		map.put("binWiseSummaryList", binWiseSummaryList);
		return new ModelAndView("/binWiseSummaryReport", map);
	}

	@RequestMapping("/discFormat")
	public ModelAndView discFormat(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}

		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		BigDecimal sairremTotal = BigDecimal.ZERO;
		BigDecimal samutcurTotal = BigDecimal.ZERO;
		BigDecimal sadscashTotal = BigDecimal.ZERO;
		BigDecimal excessTotal = BigDecimal.ZERO;

		List<Discrepancy> discrepancyList = null;

		if (dateRange.getNormalOrSuspense() != null && dateRange.getNormalOrSuspense().equalsIgnoreCase("NORMAL")) {
			discrepancyList = binDashboardService.getDiscrepancyList(user.getIcmcId(), sDate, eDate, "NORMAL");
		} else if (dateRange.getNormalOrSuspense() != null
				&& dateRange.getNormalOrSuspense().equalsIgnoreCase("SUSPENSE")) {
			discrepancyList = binDashboardService.getDiscrepancyList(user.getIcmcId(), sDate, eDate, "SUSPENSE");
		} else {
			discrepancyList = binDashboardService.getDiscrepancyList(user.getIcmcId(), sDate, eDate);
		}
		for (Discrepancy discrepancy : discrepancyList) {
			/*
			 * for(DiscrepancyAllocation
			 * dAllocation:discrepancy.getDiscrepancyAllocations()){
			 * if(dAllocation.getStatus().equals(OtherStatus.RECEIVED)){
			 */
			sairremTotal = sairremTotal.add(discrepancy.getSairremTotal());
			samutcurTotal = samutcurTotal.add(discrepancy.getSamutcurTotal());
			sadscashTotal = sadscashTotal.add(discrepancy.getSadscashTotal());
			excessTotal = excessTotal.add(discrepancy.getExcessTotal());
		}
		ICMC icmc = binDashboardService.getICMCObj(user.getIcmcId());

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sDate.getTime();

		map.put("discrepancyList", discrepancyList);
		map.put("currentDate", fmt.format(date));
		map.put("icmcName", icmc.getName());
		map.put("icmcAddress", icmc.getAddress());
		map.put("linkBranchSolId", icmc.getLinkBranchSolId());
		map.put("sairremTotal", sairremTotal);
		map.put("samutcurTotal", samutcurTotal);
		map.put("sadscashTotal", sadscashTotal);
		map.put("excessTotal", excessTotal);

		return new ModelAndView("/discFormat", map);
	}

	@RequestMapping("/fcrmFormat")
	public ModelAndView fcrmFormat(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
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

		ICMC icmc = binDashboardService.getICMCObj(user.getIcmcId());
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		Date date = sDate.getTime();

		// List<Discrepancy> discrepancyList =
		// binDashboardService.getDiscrepancyList(user.getIcmcId(), sDate,
		// eDate);

		Map<String, Discrepancy> fcrmList = binDashboardService.getFCRMList(user.getIcmcId(), sDate, eDate);

		map.put("userName", user.getId());
		map.put("icmcName", icmc.getName());
		map.put("currentDate", fmt.format(date));
		// map.put("discrepancyList", discrepancyList);
		map.put("fcrmList", fcrmList);

		return new ModelAndView("fcrmFormat", map);
	}

	@RequestMapping("/iCoreFormat")
	public ModelAndView iCoreFormat(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
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

		ICMC icmc = binDashboardService.getICMCObj(user.getIcmcId());
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		Date date = sDate.getTime();

		BigDecimal sairremTotal = BigDecimal.ZERO;
		BigDecimal samutcurTotal = BigDecimal.ZERO;
		BigDecimal sadscashTotal = BigDecimal.ZERO;
		BigDecimal excessTotal = BigDecimal.ZERO;

		List<Discrepancy> discrepancyList = binDashboardService.getDiscrepancyList(user.getIcmcId(), sDate, eDate);
		for (Discrepancy discrepancy : discrepancyList) {
			sairremTotal = sairremTotal.add(discrepancy.getSairremTotal());
			samutcurTotal = samutcurTotal.add(discrepancy.getSamutcurTotal());
			sadscashTotal = sadscashTotal.add(discrepancy.getSadscashTotal());
			excessTotal = excessTotal.add(discrepancy.getExcessTotal());
		}

		map.put("userName", user.getId());
		map.put("icmcName", icmc.getName());
		map.put("currentDate", fmt.format(date));
		map.put("linkBranchSolId", icmc.getLinkBranchSolId());
		map.put("sairremTotal", sairremTotal);
		map.put("samutcurTotal", samutcurTotal);
		map.put("sadscashTotal", sadscashTotal);
		map.put("excessTotal", excessTotal);
		map.put("discrepancyList", discrepancyList);

		return new ModelAndView("iCoreFormat", map);
	}

	@RequestMapping("/DN2Report")
	public ModelAndView DND2Report(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = (Calendar) dateRange.getFromDate().clone();
		}
		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		BigDecimal denom1Pieces = BigDecimal.ZERO;
		BigDecimal denom2Pieces = BigDecimal.ZERO;
		BigDecimal denom5Pieces = BigDecimal.ZERO;
		BigDecimal denom10Pieces = BigDecimal.ZERO;
		BigDecimal denom20Pieces = BigDecimal.ZERO;
		BigDecimal denom50Pieces = BigDecimal.ZERO;
		BigDecimal denom100Pieces = BigDecimal.ZERO;
		BigDecimal denom200Pieces = BigDecimal.ZERO;
		BigDecimal denom500Pieces = BigDecimal.ZERO;
		BigDecimal denom1000Pieces = BigDecimal.ZERO; // code by shahabuddin
		BigDecimal denom2000Pieces = BigDecimal.ZERO;
		BigDecimal totalInPieces = BigDecimal.ZERO;
		BigDecimal totalValues = BigDecimal.ZERO;

		List<Tuple> mutilatedList = binDashboardService.DN2Report(user.getIcmcId(), sDate, eDate);
		for (Tuple tuple : mutilatedList) {
			if (tuple.get(0, Integer.class).equals(2000)) {
				denom2000Pieces = tuple.get(1, BigDecimal.class);
				totalInPieces = totalInPieces.add(denom2000Pieces);
				totalValues = totalValues.add(denom2000Pieces.multiply(BigDecimal.valueOf(2000)));
			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				denom1000Pieces = tuple.get(1, BigDecimal.class);
				totalInPieces = totalInPieces.add(denom1000Pieces);
				totalValues = totalValues.add(denom1000Pieces.multiply(BigDecimal.valueOf(1000)));
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				denom500Pieces = tuple.get(1, BigDecimal.class);
				totalInPieces = totalInPieces.add(denom500Pieces);
				totalValues = totalValues.add(denom500Pieces.multiply(BigDecimal.valueOf(500)));
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				denom200Pieces = tuple.get(1, BigDecimal.class);
				totalInPieces = totalInPieces.add(denom200Pieces);
				totalValues = totalValues.add(denom200Pieces.multiply(BigDecimal.valueOf(200)));
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				denom100Pieces = tuple.get(1, BigDecimal.class);
				totalInPieces = totalInPieces.add(denom100Pieces);
				totalValues = totalValues.add(denom100Pieces.multiply(BigDecimal.valueOf(100)));
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				denom50Pieces = tuple.get(1, BigDecimal.class);
				totalInPieces = totalInPieces.add(denom50Pieces);
				totalValues = totalValues.add(denom50Pieces.multiply(BigDecimal.valueOf(50)));
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				denom20Pieces = tuple.get(1, BigDecimal.class);
				totalInPieces = totalInPieces.add(denom20Pieces);
				totalValues = totalValues.add(denom20Pieces.multiply(BigDecimal.valueOf(20)));
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				denom10Pieces = tuple.get(1, BigDecimal.class);
				totalInPieces = totalInPieces.add(denom10Pieces);
				totalValues = totalValues.add(denom10Pieces.multiply(BigDecimal.valueOf(10)));
			}
			if (tuple.get(0, Integer.class).equals(5)) {
				denom5Pieces = tuple.get(1, BigDecimal.class);
				totalInPieces = totalInPieces.add(denom5Pieces);
				totalValues = totalValues.add(denom5Pieces.multiply(BigDecimal.valueOf(5)));
			}
			if (tuple.get(0, Integer.class).equals(2)) {
				denom2Pieces = tuple.get(1, BigDecimal.class);
				totalInPieces = totalInPieces.add(denom2Pieces);
				totalValues = totalValues.add(denom2Pieces.multiply(BigDecimal.valueOf(2)));
			}
			if (tuple.get(0, Integer.class).equals(1)) {
				denom1Pieces = tuple.get(1, BigDecimal.class);
				totalInPieces = totalInPieces.add(denom1Pieces);
				totalValues = totalValues.add(denom1Pieces.multiply(BigDecimal.valueOf(1)));
			}
		}
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sDate.getTime();
		map.put("currentDate", fmt.format(date));
		map.put("denom1Pieces", denom1Pieces);
		map.put("denom2Pieces", denom2Pieces);
		map.put("denom5Pieces", denom5Pieces);
		map.put("denom10Pieces", denom10Pieces);
		map.put("denom20Pieces", denom20Pieces);
		map.put("denom50Pieces", denom50Pieces);
		map.put("denom100Pieces", denom100Pieces);
		map.put("denom200Pieces", denom200Pieces);
		map.put("denom500Pieces", denom500Pieces);
		map.put("denom1000Pieces", denom1000Pieces);
		map.put("denom2000Pieces", denom2000Pieces);
		map.put("totalInPieces", totalInPieces);
		map.put("totalValues", totalValues);

		return new ModelAndView("DN2Report", map);

	}

	@RequestMapping("/binRegister")
	public ModelAndView binRegister(HttpSession session) {
		ModelMap map = new ModelMap();
		return new ModelAndView("/binRegister", map);
	}

	@RequestMapping("/binCard")
	public ModelAndView binCard(HttpSession session) {
		ModelMap map = new ModelMap();
		return new ModelAndView("/binCard", map);
	}

	@RequestMapping("/FIRattemptRegister")
	public ModelAndView FIRattemptRegister(HttpSession session) {
		ModelMap map = new ModelMap();
		return new ModelAndView("/FIRattemptRegister", map);
	}

	@RequestMapping("/machineDowntimeRegister")
	public ModelAndView machineDowntimeRegister(HttpSession session) {
		ModelMap map = new ModelMap();
		return new ModelAndView("/machineDowntimeRegister", map);
	}

	/*
	 * @RequestMapping("/cashMovementRegister") public ModelAndView
	 * cashMovementRegister(HttpSession session) { User user = (User)
	 * session.getAttribute("login"); ModelMap map = new ModelMap();
	 * map.put("userID", user.getId()); return new
	 * ModelAndView("/cashMovementRegister", map); }
	 */

	@RequestMapping("/fakeNoteRegister")
	public ModelAndView fakeNoteRegister(HttpSession session) {
		ModelMap map = new ModelMap();
		return new ModelAndView("/fakeNoteRegister", map);
	}

	/*
	 * @RequestMapping("/trainingRegister") public ModelAndView
	 * trainingRegister(HttpSession session) { ModelMap map = new ModelMap();
	 * return new ModelAndView("/trainingRegister", map); }
	 */

	@RequestMapping("/currencyChestBook")
	public ModelAndView TE2Reports(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {

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

		List<BinTransactionBOD> binTxBodList = binDashboardService.processTE2(user.getIcmcId(), sDate, eDate,
				dateRange);
		ICMC icmc = binDashboardService.getICMCObj(user.getIcmcId());

		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		Date date = sDate.getTime();

		BigInteger chestSlipNo = binDashboardService.getChestSlipNumber(user.getIcmcId(), sDate, eDate);
		map.put("chestSlipNo", chestSlipNo);

		map.put("currentDate", fmt.format(date));
		map.put("icmcName", icmc.getName());
		map.put("icmcAddress", icmc.getAddress());

		map.put("summaryListForOpeningBalance", binTxBodList.get(0));
		map.put("summaryListForRemittance", binTxBodList.get(1));
		map.put("summaryListForDeposit", binTxBodList.get(2));
		map.put("summaryListForTotalDeposit", binTxBodList.get(3));
		map.put("summaryListForTotalWithdrawal", binTxBodList.get(4));
		map.put("summaryListTotalForNotes", binTxBodList.get(5));
		map.put("summaryListForCoins", binTxBodList.get(6));
		map.put("summaryListForCoinsDeposit", binTxBodList.get(7));
		map.put("summaryListForCoinWithdrawal", binTxBodList.get(8));
		map.put("summaryListTotalForCoins", binTxBodList.get(9));

		map.put("chestSlipNumber", user.getId());
		return new ModelAndView("/currencyChestBook", map);
	}

	@RequestMapping("/discrepancyDemand")
	public ModelAndView discrepancyDemand(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = (Calendar) dateRange.getFromDate().clone();
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

		ModelMap map = new ModelMap();

		List<Tuple> totalNotesList = null;

		Integer denom2000W = 0;
		Integer denom1000W = 0;
		Integer denom500W = 0;
		Integer denom200W = 0;
		Integer denom100W = 0;
		Integer denom50W = 0;
		Integer denom20W = 0;
		Integer denom10W = 0;
		Integer denom5W = 0;
		Integer denom2W = 0;
		Integer denom1W = 0;

		Integer denom2000D = 0;
		Integer denom1000D = 0;
		Integer denom500D = 0;
		Integer denom200D = 0;
		Integer denom100D = 0;
		Integer denom50D = 0;
		Integer denom20D = 0;
		Integer denom10D = 0;
		Integer denom5D = 0;
		Integer denom2D = 0;
		Integer denom1D = 0;

		if (dateRange.getNormalOrSuspense() != null && dateRange.getNormalOrSuspense().equalsIgnoreCase("NORMAL")) {
			totalNotesList = binDashboardService.getTotalNotesForDiscrepancy(user.getIcmcId(), sDate, eDate, "NORMAL");
		} else if (dateRange.getNormalOrSuspense() != null
				&& dateRange.getNormalOrSuspense().equalsIgnoreCase("SUSPENSE")) {
			totalNotesList = binDashboardService.getTotalNotesForDiscrepancy(user.getIcmcId(), sDate, eDate,
					"SUSPENSE");
		} else {
			totalNotesList = binDashboardService.getTotalNotesForDiscrepancy(user.getIcmcId(), sDate, eDate);
		}

		for (Tuple tuple : totalNotesList) {
			if (tuple.get(0, Integer.class).equals(2000)) {
				if (tuple.get(2, String.class).equals("FAKE")) {
					denom2000W = denom2000W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("MUTILATED")) {
					denom2000W = denom2000W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("SHORTAGE")) {
					denom2000W = denom2000W + tuple.get(1, Integer.class);
				} else {
					denom2000D = denom2000D + tuple.get(1, Integer.class);
				}
			}

			if (tuple.get(0, Integer.class).equals(1000)) {
				if (tuple.get(2, String.class).equals("FAKE")) {
					denom1000W = denom1000W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("MUTILATED")) {
					denom1000W = denom1000W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("SHORTAGE")) {
					denom1000W = denom1000W + tuple.get(1, Integer.class);
				} else {
					denom1000D = denom1000D + tuple.get(1, Integer.class);
				}
			}

			if (tuple.get(0, Integer.class).equals(500)) {
				if (tuple.get(2, String.class).equals("FAKE")) {
					denom500W = denom500W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("MUTILATED")) {
					denom500W = denom500W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("SHORTAGE")) {
					denom500W = denom500W + tuple.get(1, Integer.class);
				} else {
					denom500D = denom500D + tuple.get(1, Integer.class);
				}
			}

			if (tuple.get(0, Integer.class).equals(200)) {
				if (tuple.get(2, String.class).equals("FAKE")) {
					denom200W = denom200W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("MUTILATED")) {
					denom200W = denom200W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("SHORTAGE")) {
					denom200W = denom200W + tuple.get(1, Integer.class);
				} else {
					denom200D = denom200D + tuple.get(1, Integer.class);
				}
			}

			if (tuple.get(0, Integer.class).equals(100)) {
				if (tuple.get(2, String.class).equals("FAKE")) {
					denom100W = denom100W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("MUTILATED")) {
					denom100W = denom100W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("SHORTAGE")) {
					denom100W = denom100W + tuple.get(1, Integer.class);
				} else {
					denom100D = denom100D + tuple.get(1, Integer.class);
				}
			}

			if (tuple.get(0, Integer.class).equals(50)) {
				if (tuple.get(2, String.class).equals("FAKE")) {
					denom50W = denom50W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("MUTILATED")) {
					denom50W = denom50W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("SHORTAGE")) {
					denom50W = denom50W + tuple.get(1, Integer.class);
				} else {
					denom50D = denom50D + tuple.get(1, Integer.class);
				}
			}

			if (tuple.get(0, Integer.class).equals(20)) {
				if (tuple.get(2, String.class).equals("FAKE")) {
					denom20W = denom20W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("MUTILATED")) {
					denom20W = denom20W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("SHORTAGE")) {
					denom20W = denom20W + tuple.get(1, Integer.class);
				} else {
					denom20D = denom20D + tuple.get(1, Integer.class);
				}
			}

			if (tuple.get(0, Integer.class).equals(10)) {
				if (tuple.get(2, String.class).equals("FAKE")) {
					denom10W = denom10W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("MUTILATED")) {
					denom10W = denom10W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("SHORTAGE")) {
					denom10W = denom10W + tuple.get(1, Integer.class);
				} else {
					denom10D = denom10D + tuple.get(1, Integer.class);
				}
			}

			if (tuple.get(0, Integer.class).equals(5)) {
				if (tuple.get(2, String.class).equals("FAKE")) {
					denom5W = denom5W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("MUTILATED")) {
					denom5W = denom5W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("SHORTAGE")) {
					denom5W = denom5W + tuple.get(1, Integer.class);
				} else {
					denom5D = denom5D + tuple.get(1, Integer.class);
				}
			}

			if (tuple.get(0, Integer.class).equals(2)) {
				if (tuple.get(2, String.class).equals("FAKE")) {
					denom2W = denom2W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("MUTILATED")) {
					denom2W = denom2W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("SHORTAGE")) {
					denom2W = denom2W + tuple.get(1, Integer.class);
				} else {
					denom2D = denom2D + tuple.get(1, Integer.class);
				}
			}

			if (tuple.get(0, Integer.class).equals(1)) {
				if (tuple.get(2, String.class).equals("FAKE")) {
					denom1W = denom1W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("MUTILATED")) {
					denom1W = denom1W + tuple.get(1, Integer.class);
				} else if (tuple.get(2, String.class).equals("SHORTAGE")) {
					denom1W = denom1W + tuple.get(1, Integer.class);
				} else {
					denom1D = denom1D + tuple.get(1, Integer.class);
				}
			}
		}

		map.put("deno2000W", denom2000W);
		map.put("deno1000W", denom1000W);
		map.put("deno500W", denom500W);
		map.put("deno200W", denom200W);
		map.put("deno100W", denom100W);
		map.put("deno50W", denom50W);
		map.put("deno20W", denom20W);
		map.put("deno10W", denom10W);
		map.put("deno5W", denom5W);
		map.put("deno2W", denom2W);
		map.put("deno1W", denom1W);

		map.put("deno2000D", denom2000D);
		map.put("deno1000D", denom1000D);
		map.put("deno500D", denom500D);
		map.put("deno200D", denom200D);
		map.put("deno100D", denom100D);
		map.put("deno50D", denom50D);
		map.put("deno20D", denom20D);
		map.put("deno10D", denom10D);
		map.put("deno5D", denom5D);
		map.put("deno2D", denom2D);
		map.put("deno1D", denom1D);

		/* map.put("totalNotesList", totalNotesList); */
		return new ModelAndView("/discrepancyDemand", map);
	}

	@RequestMapping("/cashTransfer")
	public ModelAndView cashTransfer(HttpSession session) {
		ModelMap map = new ModelMap();
		return new ModelAndView("/cashTransfer", map);
	}

	@RequestMapping("/getbinOrBox")
	@ResponseBody
	public List<String> getbinorBox(@RequestParam(value = "radioButtonValue") String radioButtonValue,
			HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<String> binOrBoxList = null;
		if (radioButtonValue.equalsIgnoreCase("binToBox") || radioButtonValue.equalsIgnoreCase("binToBin")) {
			binOrBoxList = binDashboardService.getBinOrBoxFromBinTransactionForCashTransfer(user.getIcmcId(),
					BinCategoryType.BIN);
		} else if (radioButtonValue.equalsIgnoreCase("boxToBox")) {
			binOrBoxList = binDashboardService.getBinOrBoxFromBinTransactionForCashTransfer(user.getIcmcId(),
					BinCategoryType.BOX);
		} else {
			binOrBoxList = binDashboardService.getBinOrBoxFromBinTransactionForCashTransfer(user.getIcmcId(),
					BinCategoryType.BOX);
		}
		return binOrBoxList;
	}

	@RequestMapping(value = "/getbinOrBoxFromMaster")
	@ResponseBody
	public List<String> getbinOrBoxFromMaster(@RequestParam(value = "binOrBox") String binOrBox,
			@RequestParam(value = "bundle") String bundle,
			@RequestParam(value = "radioButtonValue") String radioButtonValue, HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<String> binOrBoxList = null;
		BinTransaction binOrBoxFromDB = binDashboardService.checkBinOrBox(user.getIcmcId(), binOrBox);

		if (radioButtonValue.equalsIgnoreCase("binToBox")) {
			binOrBoxList = binDashboardService.getBoxFromBoxMasterForCashTransfer(user.getIcmcId(),
					binOrBoxFromDB.getDenomination(), binOrBoxFromDB.getBinType());
		} else if (radioButtonValue.equalsIgnoreCase("boxToBox")) {
			binOrBoxList = binDashboardService.getBoxFromBoxMasterForCashTransfer(user.getIcmcId(),
					binOrBoxFromDB.getDenomination(), binOrBoxFromDB.getBinType());
		}

		else if (radioButtonValue.equalsIgnoreCase("boxToBin")) {
			binOrBoxList = binDashboardService.getBinFromBinMasterForCashTransfer(user.getIcmcId(),
					binOrBoxFromDB.getBinType());
		}

		else if (radioButtonValue.equalsIgnoreCase("binToBin")) {
			binOrBoxList = binDashboardService.getBinFromBinMasterForCashTransfer(user.getIcmcId(),
					binOrBoxFromDB.getBinType());
		}

		return binOrBoxList;
	}

	@RequestMapping(value = "/getbinFromBinTransaction")
	@ResponseBody
	public List<String> getbinFromBinTransaction(@RequestParam(value = "denomination") Integer denomination,
			@RequestParam(value = "currencyType") CurrencyType currencyType, HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<String> binList = binDashboardService.getBinFromBinTransaction(user.getIcmcId(), denomination,
				currencyType);
		return binList;
	}

	@RequestMapping(value = "/getBundleFromDBForCashTransfer")
	@ResponseBody
	public String bundle(@RequestParam(value = "binOrBox") String binOrBox, HttpSession session) {
		User user = (User) session.getAttribute("login");
		BinTransaction binOrBoxFromDB = binDashboardService.checkBinOrBox(user.getIcmcId(), binOrBox);
		String receivebundle = binOrBoxFromDB.getReceiveBundle().toString();
		return receivebundle;

	}

	@RequestMapping(value = "/getBinDetailsFromcashTransfer")
	@ResponseBody
	public BinTransaction binDetails(@RequestParam(value = "binOrBox") String binOrBox, HttpSession session) {
		User user = (User) session.getAttribute("login");
		BinTransaction binDetailsByBiNumber = binDashboardService.binDetailsByBinNumber(user.getIcmcId(), binOrBox);
		// String binDetailsByBiNumber =
		// binOrBoxFromDB.getReceiveBundle().toString();
		return binDetailsByBiNumber;

	}

	private String readPRNFileData() throws FileNotFoundException, IOException {
		File file = new File(prnFilePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = "", oldtext = "";
		while ((line = reader.readLine()) != null) {
			oldtext += line + "\r\n";
		}
		reader.close();
		return oldtext;

	}

	@RequestMapping(value = "/transferCash")
	@ResponseBody
	public List<String> transferCash(@RequestParam(value = "binOrBox") String binOrBox,
			@RequestParam(value = "bundle") String bundle, @RequestParam(value = "binFromMaster") String binFromMaster,
			@RequestParam(value = "remarks") String remarks, @RequestParam(value = "reason") String reason,
			@RequestParam(value = "radioButtonValue") String radioButtonValue, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();

		StringBuilder sb = null;
		List<String> prnList = new ArrayList<>();
		StringBuilder sbBinName = new StringBuilder();
		BinTransaction binTxn = new BinTransaction();
		BinTransaction binOrBoxFromDB = binDashboardService.checkBinOrBox(user.getIcmcId(), binOrBox);
		BoxMaster boXmasterCapacity = binDashboardService.getBoxCapacity(user.getIcmcId(), binFromMaster);
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			prnList.add(sbBinName.toString());
			binTxn.setInsertBy(user.getId());
			binTxn.setUpdateBy(user.getId());
			binTxn.setInsertTime(now);
			binTxn.setUpdateTime(now);
			binTxn.setBinNumber(binFromMaster);
			binTxn.setDenomination(binOrBoxFromDB.getDenomination());
			binTxn.setReceiveBundle(new BigDecimal(bundle));
			binTxn.setBinType(binOrBoxFromDB.getBinType());
			binTxn.setStatus(binOrBoxFromDB.getStatus());
			binTxn.setIcmcId(user.getIcmcId());
			binTxn.setCashSource(binOrBoxFromDB.getCashSource());
			if (radioButtonValue.equalsIgnoreCase("binToBox")) {
				binTxn.setBinCategoryType(BinCategoryType.BOX);
				binTxn.setMaxCapacity(boXmasterCapacity.getMaxCapacity());
			} else if (radioButtonValue.equalsIgnoreCase("boxToBin")) {
				binTxn.setBinCategoryType(BinCategoryType.BIN);
				binTxn.setMaxCapacity(binOrBoxFromDB.getMaxCapacity());
			} else if (radioButtonValue.equalsIgnoreCase("binToBin")) {
				binTxn.setBinCategoryType(BinCategoryType.BIN);
				binTxn.setMaxCapacity(binOrBoxFromDB.getMaxCapacity());
			} else if (radioButtonValue.equalsIgnoreCase("boxToBox")) {
				binTxn.setBinCategoryType(BinCategoryType.BOX);
				binTxn.setMaxCapacity(boXmasterCapacity.getMaxCapacity());
			}

			binTxn.setCashType(binOrBoxFromDB.getCashType());
			binTxn.setVerified(binOrBoxFromDB.getVerified());
			binTxn.setRbiOrderNo(binOrBoxFromDB.getRbiOrderNo());
			try {
				cashPaymentService.deleteEmptyBinFromBinTransaction(user.getIcmcId(), binTxn.getBinNumber());
				binDashboardService.cashTransferInBinTxn(binTxn);
				binDashboardService.updateBinTxnAfterCashTransfer(user.getIcmcId(), binOrBox);
				BranchReceipt brReceipt = new BranchReceipt();
				if (binTxn.getCashSource() != null) {
					brReceipt.setDenomination(binTxn.getDenomination());
					brReceipt.setBundle(binTxn.getReceiveBundle());
					brReceipt.setBin(binTxn.getBinNumber());
					brReceipt.setStatus(OtherStatus.RECEIVED);
					brReceipt.setInsertTime(binTxn.getInsertTime());
					brReceipt.setUpdateTime(binTxn.getUpdateTime());
					brReceipt.setInsertBy(binTxn.getInsertBy());
					brReceipt.setUpdateBy(binTxn.getUpdateBy());
					brReceipt.setIcmcId(binTxn.getIcmcId());
					brReceipt.setCurrencyType(binTxn.getBinType());
					brReceipt.setCashSource(binTxn.getCashSource());
					brReceipt.setBinCategoryType(binTxn.getBinCategoryType());
					binDashboardService.insertBranchReceiptAftercashTransfer(brReceipt);
					binDashboardService.updateBranchReceiptAfterCashTransfer(user.getIcmcId(), binOrBox);
				}
				// Update IS_ALLOCATED=2 in Bin Master AND IS_ALLOCATED = 1 in
				// Box Master
				if (radioButtonValue.equalsIgnoreCase("binToBox")) {
					binDashboardService.updateBoxMaster(user.getIcmcId(), binFromMaster, 1);
					binDashboardService.updateBinMaster(user.getIcmcId(), binOrBox, 2);
				} else if (radioButtonValue.equalsIgnoreCase("boxToBin")) {
					binDashboardService.updateBinMaster(user.getIcmcId(), binFromMaster, 1);
					binDashboardService.updateBoxMaster(user.getIcmcId(), binOrBox, 2);
				} else if (radioButtonValue.equalsIgnoreCase("binToBin")) {
					binDashboardService.updateBinMaster(user.getIcmcId(), binFromMaster, 1);
					binDashboardService.updateBinMaster(user.getIcmcId(), binOrBox, 2);
				}
			} catch (Exception ex) {
				LOG.info("Error has occured", ex);
				throw ex;
			}

			// Save data in Cash Transfer Table
			CashTransfer cashTransfer = new CashTransfer();
			cashTransfer.setDenomination(binOrBoxFromDB.getDenomination());
			cashTransfer.setFromBinOrBox(binOrBox);
			cashTransfer.setToBinOrBox(binFromMaster);
			cashTransfer.setInsertBy(user.getId());
			cashTransfer.setUpdateBy(user.getId());
			cashTransfer.setIcmcId(user.getIcmcId());
			cashTransfer.setInsertTime(now);
			cashTransfer.setUpdateTime(now);
			cashTransfer.setReason(reason);
			cashTransfer.setRemarks(remarks);
			cashTransfer.setReceiveBundle(new BigDecimal(bundle));
			boolean isAllSuccess = binDashboardService.saveCashTransfer(cashTransfer);
			prnList.add(sbBinName.toString());
			if (isAllSuccess) {
				sbBinName.append(binFromMaster).append(",");

				try {
					String oldtext = readPRNFileData();
					if (binTxn.getCashSource() == CashSource.BRANCH) {
						BranchReceipt branchReceiptDetails = binDashboardService
								.getBranchReceiptDetailsForCashTransferQR(user.getIcmcId(), binOrBox,
										binTxn.getDenomination());
						String replacedtext = oldtext.replaceAll("bin", "" + binFromMaster);
						replacedtext = replacedtext.replaceAll("branch", "" + branchReceiptDetails.getBranch());
						replacedtext = replacedtext.replaceAll("solId", "" + branchReceiptDetails.getSolId());
						replacedtext = replacedtext.replaceAll("denom", "" + binOrBoxFromDB.getDenomination());
						replacedtext = replacedtext.replaceAll("bundle", "" + new BigDecimal(bundle));
						String formattedTotal = CurrencyFormatter.inrFormatter(branchReceiptDetails.getTotal())
								.toString();
						replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);
						sb = new StringBuilder(replacedtext);
					} else if (binTxn.getCashSource() == CashSource.DSB) {
						DSB dsbDetails = binDashboardService.getDSBDetailsForCashTransferQR(user.getIcmcId(), binOrBox,
								binTxn.getDenomination());
						String replacedtext = oldtext.replaceAll("bin", "" + binFromMaster);
						replacedtext = replacedtext.replaceAll("Branch: ", "" + "Name: ");
						replacedtext = replacedtext.replaceAll("Sol ID :", "" + "A/C No.: ");
						replacedtext = replacedtext.replaceAll("branch", "" + dsbDetails.getName());
						replacedtext = replacedtext.replaceAll("solId", "" + dsbDetails.getAccountNumber());
						replacedtext = replacedtext.replaceAll("denom", "" + binOrBoxFromDB.getDenomination());
						replacedtext = replacedtext.replaceAll("bundle", "" + new BigDecimal(bundle));
						String formattedTotal = CurrencyFormatter.inrFormatter(dsbDetails.getTotal()).toString();
						replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

						sb = new StringBuilder(replacedtext);
					} else if (binTxn.getCashSource() == CashSource.DIVERSION) {
						DiversionIRV dirvDetails = binDashboardService.getDiversionIRVDetailsForCashTransferQR(
								user.getIcmcId(), binOrBox, binTxn.getDenomination());
						String replacedtext = oldtext.replaceAll("bin", "" + binFromMaster);
						replacedtext = replacedtext.replaceAll("Branch: ", "" + "Bank: ");
						replacedtext = replacedtext.replaceAll("Sol ID :", "" + "OrderNo: ");
						replacedtext = replacedtext.replaceAll("branch", "" + dirvDetails.getBankName());
						replacedtext = replacedtext.replaceAll("solId", "" + dirvDetails.getRbiOrderNo());
						replacedtext = replacedtext.replaceAll("denom", "" + binOrBoxFromDB.getDenomination());
						replacedtext = replacedtext.replaceAll("bundle", "" + new BigDecimal(bundle));
						String formattedTotal = CurrencyFormatter.inrFormatter(dirvDetails.getTotal()).toString();
						replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

						sb = new StringBuilder(replacedtext);
					} else if (binTxn.getCashSource() == CashSource.OTHERBANK) {
						BankReceipt bankReceiptDetails = binDashboardService.getBankReceiptDetailsForCashTransferQR(
								user.getIcmcId(), binOrBox, binTxn.getDenomination());
						String replacedtext = oldtext.replaceAll("bin", "" + binFromMaster);
						replacedtext = replacedtext.replaceAll("Branch: ", "" + "Bank: ");
						replacedtext = replacedtext.replaceAll("Sol ID :", "" + "UTR No: ");
						replacedtext = replacedtext.replaceAll("branch", "" + bankReceiptDetails.getBankName());
						replacedtext = replacedtext.replaceAll("solId", "" + bankReceiptDetails.getRtgsUTRNo());
						replacedtext = replacedtext.replaceAll("denom", "" + binOrBoxFromDB.getDenomination());
						replacedtext = replacedtext.replaceAll("bundle", "" + new BigDecimal(bundle));

						String formattedTotal = CurrencyFormatter.inrFormatter(bankReceiptDetails.getTotal())
								.toString();
						replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

						sb = new StringBuilder(replacedtext);
					} else {
						for (int i = 0; i < binTxn.getReceiveBundle().intValue(); i++) {
							String replacedtext = oldtext.replaceAll("bin", "" + binFromMaster);
							replacedtext = replacedtext.replaceAll("Bin: ", "" + "");
							replacedtext = replacedtext.replaceAll("Branch: ", "" + "");
							replacedtext = replacedtext.replaceAll("Sol ID :", "" + "");
							replacedtext = replacedtext.replaceAll("branch", "" + binOrBoxFromDB.getDenomination());
							replacedtext = replacedtext.replaceAll("solId", "" + binOrBoxFromDB.getBinNumber());
							replacedtext = replacedtext.replaceAll("denom", "" + binOrBoxFromDB.getDenomination());
							replacedtext = replacedtext.replaceAll("bundle", "" + BigDecimal.ONE);

							String formattedTotal = CurrencyFormatter
									.inrFormatter(BigDecimal.valueOf(binOrBoxFromDB.getDenomination() * 1000))
									.toString();
							replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

							sb = new StringBuilder(replacedtext);
						}
					}
					prnList.add(sb.toString());
					LOG.info(binTxn.getCashSource() + "PRN: " + sb);

					// Call Printer Method From UTILITY Class

					UtilityJpa.PrintToPrinter(sb, user);

				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		return prnList;
	}

	@RequestMapping("/editBinStatus")
	public ModelAndView editBinStatus(@RequestParam Long id, BinMaster binMaster) {
		ModelMap model = new ModelMap();
		BinMaster binNumberFromDBById = binDashboardService.getBinNumberById(id);
		binMaster.setBinNumber(binNumberFromDBById.getBinNumber());
		model.put("user", binMaster);
		return new ModelAndView("editBinStatus", model);
	}

	@RequestMapping("/updateBinStatus")
	public ModelAndView updateIndent(@ModelAttribute("userPage") BinMaster binMaster, HttpSession session,
			RedirectAttributes redirectAttributes) {

		// Special Character Code
		boolean binuNmber = HtmlUtils.isHtml(binMaster.getBinNumber());

		if (binuNmber == false) {
			redirectAttributes.addFlashAttribute("updateMsg", "Error : Special Character Not Allowed");
			return new ModelAndView("redirect:./viewBinMaster");
		}
		// End special CharacterCode

		User user = (User) session.getAttribute("login");
		BinMaster isAllocatedStatus = binDashboardService.getIsAllocatedValue(user.getIcmcId(),
				binMaster.getBinNumber());
		int isAllocated = isAllocatedStatus.getIsAllocated();
		if (isAllocated == 0) {
			binDashboardService.updateBinMaster(user.getIcmcId(), binMaster.getBinNumber(), 2);
			redirectAttributes.addFlashAttribute("updateMsg", "Bin Status Changed");
			return new ModelAndView("redirect:./viewBinMaster");
		} else {
			redirectAttributes.addFlashAttribute("updateMsg", "Kindly free bin");
			return new ModelAndView("redirect:./viewBinMaster");
		}
	}

	@RequestMapping("/auditorIndent")
	public ModelAndView auditorIndent() {
		ModelMap map = new ModelMap();
		AuditorIndent obj = new AuditorIndent();
		map.put("denominationList", DenominationType.values());
		map.put("currencyTypeList", CurrencyType.values());
		map.put("user", obj);
		return new ModelAndView("/auditorIndent", map);
	}

	@RequestMapping("/saveAuditorIndent")
	public ModelAndView saveAuditorIndent(@ModelAttribute("userPage") AuditorIndent auditorIndent,
			HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		boolean isAllSuccess = false;
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			auditorIndent.setIcmcId(user.getIcmcId());
			auditorIndent.setInsertBy(user.getId());
			auditorIndent.setUpdateBy(user.getId());
			auditorIndent.setInsertTime(now);
			auditorIndent.setUpdateTime(now);
			auditorIndent.setStatus(OtherStatus.REQUESTED);
			auditorIndent.setPendingBundleRequest(auditorIndent.getBundle());
			isAllSuccess = binDashboardService.saveAuditorIndentRequest(auditorIndent);
			if (!isAllSuccess) {
				throw new RuntimeException("Error while indent by Auditor, Please try again");
			}
			auditorIndent.setDenomination(auditorIndent.getDenomination());
			auditorIndent.setBinNumber(auditorIndent.getBinNumber());
			BinTransaction binTxn = binDashboardService.getBinNumListForAuditorIndent(auditorIndent,
					auditorIndent.getBinType());
			BigDecimal pendingBundle = BigDecimal.ZERO;
			BigDecimal pendingBundleFromVault = BigDecimal.ZERO;
			BigDecimal bundleFromUI = BigDecimal.ZERO;

			pendingBundleFromVault = binTxn.getPendingBundleRequest();
			if (pendingBundleFromVault == null) {
				pendingBundleFromVault = BigDecimal.ZERO;
			}
			bundleFromUI = auditorIndent.getBundle();
			pendingBundle = pendingBundleFromVault.add(bundleFromUI);
			binDashboardService.updateBinTxn(user.getIcmcId(), pendingBundle);
		}
		return new ModelAndView("redirect:./viewAuditorIndent");
	}

	@RequestMapping("/disabledBin")
	public ModelAndView getDisabledBin(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<BinMaster> binList = binDashboardService.getDisableBin(user.getIcmcId());
		return new ModelAndView("/disabledBin", "records", binList);
	}

	@RequestMapping("/editDisabledBin")
	public ModelAndView editDisabledBin(@RequestParam Long id, BinMaster binMaster) {
		ModelMap model = new ModelMap();
		BinMaster binNumberFromDBById = binDashboardService.getBinNumberById(id);
		binMaster.setBinNumber(binNumberFromDBById.getBinNumber());
		model.put("user", binMaster);
		return new ModelAndView("editDisabledBin", model);
	}

	@RequestMapping("/updateDisabledBinStatus")
	public ModelAndView updateDisabledBinStatus(@ModelAttribute("userPage") BinMaster binMaster, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		binDashboardService.updateDisabledBinStatus(user.getIcmcId(), binMaster.getBinNumber());
		return new ModelAndView("redirect:./disabledBin");
	}

	@RequestMapping("/viewAuditorIndent")
	public ModelAndView viewAuditorIndent(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<AuditorIndent> auditorIndentList = binDashboardService.viewAuditorIndentList(user.getIcmcId());
		return new ModelAndView("/viewAuditorIndent", "records", auditorIndentList);
	}

	@RequestMapping("/acceptAuditorIndent")
	public ModelAndView acceptAuditorIndent(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<AuditorIndent> auditorIndentList = binDashboardService.viewAuditorIndent(user.getIcmcId());
		return new ModelAndView("/acceptAuditorIndent", "records", auditorIndentList);
	}

	@RequestMapping("/auditorIndentAccept")
	@ResponseBody
	public AuditorIndent updateAuditorStatus(@RequestBody AuditorIndent auditorIndentFromUI, HttpSession session) {

		User user = (User) session.getAttribute("login");
		AuditorIndent auditorIndent = new AuditorIndent();

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			auditorIndent.setId(auditorIndentFromUI.getId());
			auditorIndent.setIcmcId(user.getIcmcId());
			binDashboardService.updateAuditorIndentStatus(auditorIndent);

			BinTransaction binTxn = new BinTransaction();
			binTxn.setBinNumber(auditorIndentFromUI.getBinNumber());
			binTxn.setDenomination(auditorIndentFromUI.getDenomination());
			binTxn.setReceiveBundle(auditorIndentFromUI.getBundle());
			binTxn.setIcmcId(user.getIcmcId());
			binTxn = binDashboardService.getBinRecordForAcceptInVault(binTxn);

			BigDecimal balanceBundle = binTxn.getReceiveBundle().subtract(auditorIndentFromUI.getBundle());

			if (balanceBundle.compareTo(BigDecimal.ZERO) == 0) {
				binTxn.setReceiveBundle(BigDecimal.ZERO);
				binTxn.setPendingBundleRequest(BigDecimal.ZERO);
				binTxn.setStatus(BinStatus.EMPTY);
				binTxn.setCashSource(null);
				binTxn.setVerified(YesNo.NULL);
				binTxn.setUpdateBy(user.getId());
				binTxn.setUpdateTime(Calendar.getInstance());
				boolean count = binDashboardService.updateBinTxn(binTxn);
				if (count) {
					BinMaster binMaster = new BinMaster();
					binMaster.setBinNumber(auditorIndentFromUI.getBinNumber());
					binMaster.setIcmcId(user.getIcmcId());
					binDashboardService.updateBinMaster(binMaster);
				}
			} else if (balanceBundle.compareTo(BigDecimal.ZERO) > 0) {
				binTxn.setReceiveBundle(balanceBundle);
				binTxn.setPendingBundleRequest(
						binTxn.getPendingBundleRequest().subtract(auditorIndentFromUI.getBundle()));
				binDashboardService.updateBinTxn(binTxn);
			}
		}
		return auditorIndent;
	}

	@RequestMapping("/coinDistributionRegister")
	public ModelAndView binCard1(HttpSession session) {
		ModelMap map = new ModelMap();
		return new ModelAndView("/coinDistributionRegister", map);
	}

	@RequestMapping("/trainingRegister")
	public ModelAndView trainingRegister(HttpSession session) {
		TrainingRegister obj = new TrainingRegister();
		ModelMap map = new ModelMap();
		map.put("user", obj);
		return new ModelAndView("/trainingRegister", map);
	}

	@RequestMapping("/saveTrainingRegister")
	public ModelAndView saveTrainingRegister(@ModelAttribute("user") TrainingRegister machine, HttpSession session) {
		User user = (User) session.getAttribute("login");
		machine.setInsertBy(user.getId());
		machine.setUpdateBy(user.getId());
		machine.setIcmcId(user.getIcmcId());
		Calendar now = Calendar.getInstance();
		machine.setInsertTime(now);
		machine.setUpdateTime(now);
		binDashboardService.saveTrainingRegisterData(machine);
		return new ModelAndView("redirect:./viewTrainingRegister");
	}

	@RequestMapping("viewTrainingRegister")
	public ModelAndView viewTrainingRegsiterData(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<TrainingRegister> trainingRegisterList = binDashboardService.getTrainingRegisterData(user.getIcmcId());
		return new ModelAndView("viewTrainingRegister", "records", trainingRegisterList);
	}

	@RequestMapping("/editTrainingRegister")
	public ModelAndView editTrainingRegister(@RequestParam long id) {
		ModelMap map = new ModelMap();
		TrainingRegister trainingRegister = new TrainingRegister();
		trainingRegister = binDashboardService.getTrainingDataBYId(id);
		map.put("user", trainingRegister);
		return new ModelAndView("editTrainingRegister", map);
	}

	@RequestMapping("/deleteTrainingRegister")
	public ModelAndView deleteTrainingRegister(@RequestParam long id) {

		binDashboardService.deleteTrainingRegisterById(id);
		return new ModelAndView("redirect:./viewTrainingRegister");
	}

	@RequestMapping("/updateTrainingRegister")
	public ModelAndView updateMachineMaintenance(@ModelAttribute("user") TrainingRegister trainingRegister,
			HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			trainingRegister.setInsertBy(user.getId());
			trainingRegister.setUpdateBy(user.getId());
			trainingRegister.setInsertTime(now);
			trainingRegister.setUpdateTime(now);
			trainingRegister.setIcmcId(user.getIcmcId());
			binDashboardService.updateTrainingRegsiter(trainingRegister);
			redirectAttributes.addFlashAttribute("updateMsg", "Training Regsiter detail updated successfully");
		}
		return new ModelAndView("redirect:./viewTrainingRegister");
	}

	@RequestMapping("trainingRegisterReport")
	public ModelAndView trainingRegisterReport(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {

		User user = (User) session.getAttribute("login");
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}

		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		List<TrainingRegister> trainingRegisterList = binDashboardService.getTrainingRegisterReport(user.getIcmcId(),
				sDate, eDate);
		return new ModelAndView("trainingRegisterReport", "records", trainingRegisterList);
	}

	@RequestMapping("/coinDistributionReport")
	public ModelAndView coinDistributionRegister(@ModelAttribute("reportDate") DateRange dateRange,
			HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}

		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		List<Sas> coinsList = binDashboardService.coinsRegister(user.getIcmcId(), sDate, eDate);
		return new ModelAndView("coinDistributionReport", "records", coinsList);
	}

	@RequestMapping("/cashMovementRegister")
	public ModelAndView cashMovementRegister(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = (Calendar) dateRange.getFromDate().clone();
			sDate.getTime();// For date
		}

		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		List<BinTransactionBOD> binTxBodList = binDashboardService.cashMovementRegister(user.getIcmcId(), sDate, eDate,
				dateRange);
		List<Sas> sasList = cashPaymentJpaDao.getORVRecords(user.getIcmcId(), sDate, eDate);
		BigDecimal denomination1COINSW = new BigDecimal(0);
		BigDecimal denomination2COINSW = new BigDecimal(0);
		BigDecimal denomination5COINSW = new BigDecimal(0);
		BigDecimal denomination10COINSW = new BigDecimal(0);
		BinTransactionBOD binTransactionBODCoins = new BinTransactionBOD();
		for (Sas sas : sasList) {
			denomination1COINSW = denomination1COINSW.add(sas.getTotalValueOfCoinsRs1());
			denomination2COINSW = denomination2COINSW.add(sas.getTotalValueOfCoinsRs2());
			denomination5COINSW = denomination5COINSW.add(sas.getTotalValueOfCoinsRs5());
			denomination10COINSW = denomination10COINSW.add(sas.getTotalValueOfCoinsRs10());
		}
		binTransactionBODCoins.setDenomination1(denomination1COINSW);
		binTransactionBODCoins.setDenomination2(denomination2COINSW);
		binTransactionBODCoins.setDenomination5(denomination5COINSW);
		binTransactionBODCoins.setDenomination10(denomination10COINSW);

		map.put("summaryListForCoins", binTransactionBODCoins);
		map.put("summaryListForATM", binTxBodList.get(0));
		map.put("summaryListForISSUABLE", binTxBodList.get(1));
		map.put("branchDeposit", binTxBodList.get(2));
		map.put("atmAndIssuableBundleSum", binTxBodList.get(3));
		map.put("currenctDate", sDate.getTime());

		return new ModelAndView("/cashMovementRegister", map);
	}

	@RequestMapping("/binRegister1")
	public ModelAndView binRegister1(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
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

		List<Tuple> depositList = new ArrayList<>();
		String binNumber = dateRange.getBinNumber();

		if (dateRange.getBinNumber() == null) {

			depositList = binDashboardService.getDepositFromHistory(user.getIcmcId(), "00", sDate, eDate);
		} else {
			depositList = binDashboardService.getDepositFromHistory(user.getIcmcId(), dateRange.getBinNumber(), sDate,
					eDate);

		}
		map.put("deposit", depositList);
		map.put("binNumber", binNumber);
		return new ModelAndView("/binRegister1", map);
	}

	@RequestMapping("/updateBinRegister")
	public ModelAndView binRegisterUpdate(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<Tuple> receiptDataFromHistory = binDashboardService.getAllReceiptDataForBinRegister(user.getIcmcId(),
				sDate, eDate);
		map.put("receiptDataFromHistory", receiptDataFromHistory);
		return new ModelAndView("/updateBinRegister", map);
	}

	@RequestMapping("/insertDataForBinRegister")
	@ResponseBody
	public List<History> insertDataForBinRegister(HttpSession session,
			@RequestParam(value = "denomination", required = false) Integer denomination,
			@RequestParam(value = "binNumber", required = false) String binNumber,
			@RequestParam(value = "bundle", required = false) BigDecimal bundleFromUI) {
		User user = (User) session.getAttribute("login");
		BigDecimal depositbundleForBinRegister = bundleFromUI;
		BinRegister binRegister = new BinRegister();
		Calendar now = Calendar.getInstance();
		List<History> saveDatainBinRegisterAndUpdateHistoryTable = binDashboardService
				.saveDataInBinRegister(denomination, binNumber, bundleFromUI, user.getIcmcId());
		// insert in BinRegister Table

		binRegister.setBinNumber(binNumber);
		binRegister.setDenomination(denomination);
		binRegister.setReceiveBundle(depositbundleForBinRegister);
		binRegister.setWithdrawalBundle(BigDecimal.ZERO);
		binRegister.setDepositOrWithdrawal("DEPOSIT");
		binRegister.setInsertTime(now);
		binRegister.setUpdateTime(now);
		binRegister.setType("U");
		binRegister.setIcmcId(user.getIcmcId());
		binRegister.setInsertBy(user.getId());
		binRegister.setUpdateBy(user.getId());
		binDashboardService.insertDataInBinRegister(binRegister);

		// Close bin Register Table
		return saveDatainBinRegisterAndUpdateHistoryTable;

	}

	@RequestMapping("/deleteBinMasterByIcmcId")
	public ModelAndView deleteBinMasterByIcmcId(HttpSession session) {
		User user = (User) session.getAttribute("login");
		binDashboardService.deleteForMigration(user.getIcmcId());
		return new ModelAndView("redirect:./viewBin");
	}

	@RequestMapping("/deleteBinTransactionByIcmcId")
	public ModelAndView deleteBinTransactionByIcmcId(HttpSession session) {
		return new ModelAndView("redirect:./viewBin");
	}

	@RequestMapping("/deleteBranchReceiptByIcmcId")
	public ModelAndView deleteBranchReceiptByIcmcId(HttpSession session) {
		return new ModelAndView("redirect:./viewBin");
	}

	@RequestMapping(value = "/checkAvailableBundleByDenoCategory")
	@ResponseBody
	public String checkAvailableBundleByDenoCategory(@RequestBody BinTransaction binTransaction, HttpSession session) {
		// binTransaction.getDenomination();
		String str = "sajjad";
		return str;
	}

	@RequestMapping(value = "/getbinFromBinTransactionForCashTransfer")
	@ResponseBody
	public List<String> getbinFromBinTransactionForCashTransfer(
			@RequestParam(value = "denomination") Integer denomination,
			@RequestParam(value = "currencyType") CurrencyType currencyType,
			@RequestParam(value = "bundle") BigDecimal bundle, HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<BinTransaction> binList = binDashboardService.getBinFroPartialTransfer(user.getIcmcId(), denomination,
				currencyType);
		List<String> bList = new ArrayList<String>();
		BigDecimal capacity = new BigDecimal(0);
		BigDecimal receive = new BigDecimal(0);
		BigDecimal available = new BigDecimal(0);
		for (BinTransaction btxn : binList) {
			capacity = btxn.getMaxCapacity();
			receive = btxn.getReceiveBundle();
			available = capacity.subtract(receive);
			if (available.compareTo(bundle) >= 0) {
				bList.add(btxn.getBinNumber());
			}

		}
		return bList;
	}

	@RequestMapping("/stockMovementRegister")
	public ModelAndView stockMovementRegister(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
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

		BigDecimal BundleDenomination2000 = BigDecimal.ZERO;
		BigDecimal BundleDenomination1000 = BigDecimal.ZERO;
		BigDecimal BundleDenomination500 = BigDecimal.ZERO;
		BigDecimal BundleDenomination200 = BigDecimal.ZERO;
		BigDecimal BundleDenomination100 = BigDecimal.ZERO;
		BigDecimal BundleDenomination50 = BigDecimal.ZERO;
		BigDecimal BundleDenomination20 = BigDecimal.ZERO;
		BigDecimal BundleDenomination10 = BigDecimal.ZERO;
		BigDecimal BundleDenomination5 = BigDecimal.ZERO;

		BigDecimal BundleDenomination2000Atm = BigDecimal.ZERO;
		BigDecimal BundleDenomination1000Atm = BigDecimal.ZERO;
		BigDecimal BundleDenomination500Atm = BigDecimal.ZERO;
		BigDecimal BundleDenomination200Atm = BigDecimal.ZERO;
		BigDecimal BundleDenomination100Atm = BigDecimal.ZERO;
		BigDecimal BundleDenomination50Atm = BigDecimal.ZERO;
		BigDecimal BundleDenomination20Atm = BigDecimal.ZERO;
		BigDecimal BundleDenomination10Atm = BigDecimal.ZERO;
		BigDecimal BundleDenomination5Atm = BigDecimal.ZERO;

		BigDecimal BundleDenomination2000Issuable = BigDecimal.ZERO;
		BigDecimal BundleDenomination1000Issuable = BigDecimal.ZERO;
		BigDecimal BundleDenomination500Issuable = BigDecimal.ZERO;
		BigDecimal BundleDenomination200Issuable = BigDecimal.ZERO;
		BigDecimal BundleDenomination100Issuable = BigDecimal.ZERO;
		BigDecimal BundleDenomination50Issuable = BigDecimal.ZERO;
		BigDecimal BundleDenomination20Issuable = BigDecimal.ZERO;
		BigDecimal BundleDenomination10Issuable = BigDecimal.ZERO;
		BigDecimal BundleDenomination5Issuable = BigDecimal.ZERO;

		BigDecimal BundleDenomination2000Soiled = BigDecimal.ZERO;
		BigDecimal BundleDenomination1000Soiled = BigDecimal.ZERO;
		BigDecimal BundleDenomination500Soiled = BigDecimal.ZERO;
		BigDecimal BundleDenomination200Soiled = BigDecimal.ZERO;
		BigDecimal BundleDenomination100Soiled = BigDecimal.ZERO;
		BigDecimal BundleDenomination50Soiled = BigDecimal.ZERO;
		BigDecimal BundleDenomination20Soiled = BigDecimal.ZERO;
		BigDecimal BundleDenomination10Soiled = BigDecimal.ZERO;
		BigDecimal BundleDenomination5Soiled = BigDecimal.ZERO;

		List<Indent> indent = binDashboardService.getIndentCash(user.getIcmcId(), sDate, eDate);
		// List<SASAllocation> sasAllocation =
		// binDashboardService.getsasAllocation(user.getIcmcId(), sDate, eDate);

		// getting the bundle from indent

		for (Indent ind : indent) {
			if (ind.getDenomination() == 2000) {
				BundleDenomination2000 = BundleDenomination2000.add(ind.getBundle());
			}
			if (ind.getDenomination() == 1000) {
				BundleDenomination1000 = BundleDenomination1000.add(ind.getBundle());
			}
			if (ind.getDenomination() == 500) {
				BundleDenomination500 = BundleDenomination500.add(ind.getBundle());
			}
			if (ind.getDenomination() == 200) {
				BundleDenomination200 = BundleDenomination200.add(ind.getBundle());
			}
			if (ind.getDenomination() == 100) {
				BundleDenomination100 = BundleDenomination100.add(ind.getBundle());
			}
			if (ind.getDenomination() == 50) {
				BundleDenomination50 = BundleDenomination50.add(ind.getBundle());
			}
			if (ind.getDenomination() == 20) {
				BundleDenomination20 = BundleDenomination20.add(ind.getBundle());
			}
			if (ind.getDenomination() == 10) {
				BundleDenomination10 = BundleDenomination10.add(ind.getBundle());
			}
			if (ind.getDenomination() == 5) {
				BundleDenomination5 = BundleDenomination5.add(ind.getBundle());
			}
		}
		// getting bundle from sasAllocation

		/*
		 * for (SASAllocation sasAll : sasAllocation) { if
		 * (sasAll.getDenomination() == 2000) { BundleDenomination2000 =
		 * BundleDenomination2000.add(sasAll.getBundle()); } if
		 * (sasAll.getDenomination() == 1000) { BundleDenomination1000 =
		 * BundleDenomination1000.add(sasAll.getBundle()); } if
		 * (sasAll.getDenomination() == 500) { BundleDenomination500 =
		 * BundleDenomination500.add(sasAll.getBundle()); } if
		 * (sasAll.getDenomination() == 200) { BundleDenomination200 =
		 * BundleDenomination200.add(sasAll.getBundle()); } if
		 * (sasAll.getDenomination() == 100) { BundleDenomination100 =
		 * BundleDenomination100.add(sasAll.getBundle()); } if
		 * (sasAll.getDenomination() == 50) { BundleDenomination50 =
		 * BundleDenomination50.add(sasAll.getBundle()); } if
		 * (sasAll.getDenomination() == 20) { BundleDenomination20 =
		 * BundleDenomination20.add(sasAll.getBundle()); } if
		 * (sasAll.getDenomination() == 10) { BundleDenomination10 =
		 * BundleDenomination10.add(sasAll.getBundle()); } if
		 * (sasAll.getDenomination() == 5) { BundleDenomination5 =
		 * BundleDenomination5.add(sasAll.getBundle()); } }
		 */

		List<Tuple> process = binDashboardService.getProcessBundleProcessingOutPut(user.getIcmcId(), sDate, eDate);
		for (Tuple tuple : process) {
			if (tuple.get(0, Integer.class).equals(2000)) {
				if (tuple.get(1, Enum.class).equals(CurrencyType.ATM)) {
					BundleDenomination2000Atm = BundleDenomination2000Atm.add(tuple.get(2, BigDecimal.class));
				} else if (tuple.get(1, Enum.class).equals(CurrencyType.ISSUABLE)) {
					BundleDenomination2000Issuable = BundleDenomination2000Issuable.add(tuple.get(2, BigDecimal.class));
				} else {
					BundleDenomination2000Soiled = BundleDenomination2000Soiled.add(tuple.get(2, BigDecimal.class));
				}
			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				if (tuple.get(1, Enum.class).equals(CurrencyType.ATM)) {
					BundleDenomination1000Atm = BundleDenomination1000Atm.add(tuple.get(2, BigDecimal.class));
				} else if (tuple.get(1, Enum.class).equals(CurrencyType.ISSUABLE)) {
					BundleDenomination1000Issuable = BundleDenomination1000Issuable.add(tuple.get(2, BigDecimal.class));
				} else {
					BundleDenomination1000Soiled = BundleDenomination1000Soiled.add(tuple.get(2, BigDecimal.class));
				}
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				if (tuple.get(1, Enum.class).equals(CurrencyType.ATM)) {
					BundleDenomination500Atm = BundleDenomination500Atm.add(tuple.get(2, BigDecimal.class));
				} else if (tuple.get(1, Enum.class).equals(CurrencyType.ISSUABLE)) {
					BundleDenomination500Issuable = BundleDenomination500Issuable.add(tuple.get(2, BigDecimal.class));
				} else {
					BundleDenomination500Soiled = BundleDenomination500Soiled.add(tuple.get(2, BigDecimal.class));
				}
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				if (tuple.get(1, Enum.class).equals(CurrencyType.ATM)) {
					BundleDenomination200Atm = BundleDenomination200Atm.add(tuple.get(2, BigDecimal.class));
				} else if (tuple.get(1, Enum.class).equals(CurrencyType.ISSUABLE)) {
					BundleDenomination200Issuable = BundleDenomination200Issuable.add(tuple.get(2, BigDecimal.class));
				} else {
					BundleDenomination200Soiled = BundleDenomination200Soiled.add(tuple.get(2, BigDecimal.class));
				}
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				if (tuple.get(1, Enum.class).equals(CurrencyType.ATM)) {
					BundleDenomination100Atm = BundleDenomination100Atm.add(tuple.get(2, BigDecimal.class));
				} else if (tuple.get(1, Enum.class).equals(CurrencyType.ISSUABLE)) {
					BundleDenomination100Issuable = BundleDenomination100Issuable.add(tuple.get(2, BigDecimal.class));
				} else {
					BundleDenomination100Soiled = BundleDenomination100Soiled.add(tuple.get(2, BigDecimal.class));
				}
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				if (tuple.get(1, Enum.class).equals(CurrencyType.ATM)) {
					BundleDenomination50Atm = BundleDenomination50Atm.add(tuple.get(2, BigDecimal.class));
				} else if (tuple.get(1, Enum.class).equals(CurrencyType.ISSUABLE)) {
					BundleDenomination50Issuable = BundleDenomination50Issuable.add(tuple.get(2, BigDecimal.class));
				} else {
					BundleDenomination50Soiled = BundleDenomination50Soiled.add(tuple.get(2, BigDecimal.class));
				}
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				if (tuple.get(1, Enum.class).equals(CurrencyType.ATM)) {
					BundleDenomination20Atm = BundleDenomination20Atm.add(tuple.get(2, BigDecimal.class));
				} else if (tuple.get(1, Enum.class).equals(CurrencyType.ISSUABLE)) {
					BundleDenomination20Issuable = BundleDenomination20Issuable.add(tuple.get(2, BigDecimal.class));
				} else {
					BundleDenomination20Soiled = BundleDenomination20Soiled.add(tuple.get(2, BigDecimal.class));
				}
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				if (tuple.get(1, Enum.class).equals(CurrencyType.ATM)) {
					BundleDenomination10Atm = BundleDenomination10Atm.add(tuple.get(2, BigDecimal.class));
				} else if (tuple.get(1, Enum.class).equals(CurrencyType.ISSUABLE)) {
					BundleDenomination10Issuable = BundleDenomination10Issuable.add(tuple.get(2, BigDecimal.class));
				} else {
					BundleDenomination10Soiled = BundleDenomination10Soiled.add(tuple.get(2, BigDecimal.class));
				}
			}
			if (tuple.get(0, Integer.class).equals(5)) {
				if (tuple.get(1, Enum.class).equals(CurrencyType.ATM)) {
					BundleDenomination5Atm = BundleDenomination5Atm.add(tuple.get(2, BigDecimal.class));
				} else if (tuple.get(1, Enum.class).equals(CurrencyType.ISSUABLE)) {
					BundleDenomination5Issuable = BundleDenomination5Issuable.add(tuple.get(2, BigDecimal.class));
				} else {
					BundleDenomination5Soiled = BundleDenomination5Soiled.add(tuple.get(2, BigDecimal.class));
				}
			}
		}

		/*
		 * List<BranchReceipt> branchReceipt =
		 * binDashboardService.getBranchReceiptValue(user.getIcmcId(), sDate,
		 * eDate); List<DiversionIRV> diversionIRV =
		 * binDashboardService.getDiversionIRV(user.getIcmcId(), sDate, eDate);
		 * 
		 * for (DiversionIRV diversionIRVCon : diversionIRV) { if
		 * (diversionIRVCon.getDenomination() == 2000) { if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination2000Atm =
		 * BundleDenomination2000Atm.add(diversionIRVCon.getBundle()); } else if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination2000Issuable =
		 * BundleDenomination2000Issuable.add(diversionIRVCon.getBundle()); }
		 * else { BundleDenomination2000Soiled =
		 * BundleDenomination2000Soiled.add(diversionIRVCon.getBundle()); } } if
		 * (diversionIRVCon.getDenomination() == 1000) { if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination1000Atm =
		 * BundleDenomination1000Atm.add(diversionIRVCon.getBundle()); } else if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination1000Issuable =
		 * BundleDenomination1000Issuable.add(diversionIRVCon.getBundle()); }
		 * else { BundleDenomination1000Soiled =
		 * BundleDenomination1000Soiled.add(diversionIRVCon.getBundle()); } } if
		 * (diversionIRVCon.getDenomination() == 500) { if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination500Atm =
		 * BundleDenomination500Atm.add(diversionIRVCon.getBundle()); } else if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination500Issuable =
		 * BundleDenomination500Issuable.add(diversionIRVCon.getBundle()); }
		 * else { BundleDenomination500Soiled =
		 * BundleDenomination500Soiled.add(diversionIRVCon.getBundle()); } } if
		 * (diversionIRVCon.getDenomination() == 200) { if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination200Atm =
		 * BundleDenomination200Atm.add(diversionIRVCon.getBundle()); } else if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination200Issuable =
		 * BundleDenomination200Issuable.add(diversionIRVCon.getBundle()); }
		 * else { BundleDenomination200Soiled =
		 * BundleDenomination200Soiled.add(diversionIRVCon.getBundle()); } } if
		 * (diversionIRVCon.getDenomination() == 100) { if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination100Atm =
		 * BundleDenomination100Atm.add(diversionIRVCon.getBundle()); } else if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination100Issuable =
		 * BundleDenomination100Issuable.add(diversionIRVCon.getBundle()); }
		 * else { BundleDenomination100Soiled =
		 * BundleDenomination100Soiled.add(diversionIRVCon.getBundle()); } } if
		 * (diversionIRVCon.getDenomination() == 50) { if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination50Atm =
		 * BundleDenomination50Atm.add(diversionIRVCon.getBundle()); } else if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination50Issuable =
		 * BundleDenomination50Issuable.add(diversionIRVCon.getBundle()); } else
		 * { BundleDenomination50Soiled =
		 * BundleDenomination50Soiled.add(diversionIRVCon.getBundle()); } } if
		 * (diversionIRVCon.getDenomination() == 20) { if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination20Atm =
		 * BundleDenomination20Atm.add(diversionIRVCon.getBundle()); } else if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination20Issuable =
		 * BundleDenomination20Issuable.add(diversionIRVCon.getBundle()); } else
		 * { BundleDenomination20Soiled =
		 * BundleDenomination20Soiled.add(diversionIRVCon.getBundle()); } } if
		 * (diversionIRVCon.getDenomination() == 10) { if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination10Atm =
		 * BundleDenomination10Atm.add(diversionIRVCon.getBundle()); } else if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination10Issuable =
		 * BundleDenomination10Issuable.add(diversionIRVCon.getBundle()); } else
		 * { BundleDenomination10Soiled =
		 * BundleDenomination10Soiled.add(diversionIRVCon.getBundle()); } } if
		 * (diversionIRVCon.getDenomination() == 5) { if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination5Atm =
		 * BundleDenomination5Atm.add(diversionIRVCon.getBundle()); } else if
		 * (diversionIRVCon.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination5Issuable =
		 * BundleDenomination5Issuable.add(diversionIRVCon.getBundle()); } else
		 * { BundleDenomination5Soiled =
		 * BundleDenomination5Soiled.add(diversionIRVCon.getBundle()); } } }
		 * 
		 * for (BranchReceipt branchValue : branchReceipt) { if
		 * (branchValue.getDenomination() == 2000) { if
		 * (branchValue.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination2000Atm =
		 * BundleDenomination2000Atm.add(branchValue.getBundle()); } else if
		 * (branchValue.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination2000Issuable =
		 * BundleDenomination2000Issuable.add(branchValue.getBundle()); } else {
		 * BundleDenomination2000Soiled =
		 * BundleDenomination2000Soiled.add(branchValue.getBundle()); } } if
		 * (branchValue.getDenomination() == 1000) { if
		 * (branchValue.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination1000Atm =
		 * BundleDenomination1000Atm.add(branchValue.getBundle()); } else if
		 * (branchValue.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination1000Issuable =
		 * BundleDenomination1000Issuable.add(branchValue.getBundle()); } else {
		 * BundleDenomination1000Soiled =
		 * BundleDenomination1000Soiled.add(branchValue.getBundle()); } } if
		 * (branchValue.getDenomination() == 500) { if
		 * (branchValue.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination500Atm =
		 * BundleDenomination500Atm.add(branchValue.getBundle()); } else if
		 * (branchValue.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination500Issuable =
		 * BundleDenomination500Issuable.add(branchValue.getBundle()); } else {
		 * BundleDenomination500Soiled =
		 * BundleDenomination500Soiled.add(branchValue.getBundle()); } } if
		 * (branchValue.getDenomination() == 200) { if
		 * (branchValue.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination200Atm =
		 * BundleDenomination200Atm.add(branchValue.getBundle()); } else if
		 * (branchValue.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination200Issuable =
		 * BundleDenomination200Issuable.add(branchValue.getBundle()); } else {
		 * BundleDenomination200Soiled =
		 * BundleDenomination200Soiled.add(branchValue.getBundle()); } } if
		 * (branchValue.getDenomination() == 100) { if
		 * (branchValue.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination100Atm =
		 * BundleDenomination100Atm.add(branchValue.getBundle()); } else if
		 * (branchValue.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination100Issuable =
		 * BundleDenomination100Issuable.add(branchValue.getBundle()); } else {
		 * BundleDenomination100Soiled =
		 * BundleDenomination100Soiled.add(branchValue.getBundle()); } } if
		 * (branchValue.getDenomination() == 50) { if
		 * (branchValue.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination50Atm =
		 * BundleDenomination50Atm.add(branchValue.getBundle()); } else if
		 * (branchValue.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination50Issuable =
		 * BundleDenomination50Issuable.add(branchValue.getBundle()); } else {
		 * BundleDenomination50Soiled =
		 * BundleDenomination50Soiled.add(branchValue.getBundle()); } } if
		 * (branchValue.getDenomination() == 20) { if
		 * (branchValue.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination20Atm =
		 * BundleDenomination20Atm.add(branchValue.getBundle()); } else if
		 * (branchValue.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination20Issuable =
		 * BundleDenomination20Issuable.add(branchValue.getBundle()); } else {
		 * BundleDenomination20Soiled =
		 * BundleDenomination20Soiled.add(branchValue.getBundle()); } } if
		 * (branchValue.getDenomination() == 10) { if
		 * (branchValue.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination10Atm =
		 * BundleDenomination10Atm.add(branchValue.getBundle()); } else if
		 * (branchValue.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination10Issuable =
		 * BundleDenomination10Issuable.add(branchValue.getBundle()); } else {
		 * BundleDenomination10Soiled =
		 * BundleDenomination10Soiled.add(branchValue.getBundle()); } } if
		 * (branchValue.getDenomination() == 5) { if
		 * (branchValue.getCurrencyType() == CurrencyType.ATM) {
		 * BundleDenomination5Atm =
		 * BundleDenomination5Atm.add(branchValue.getBundle()); } else if
		 * (branchValue.getCurrencyType() == CurrencyType.ISSUABLE) {
		 * BundleDenomination5Issuable =
		 * BundleDenomination5Issuable.add(branchValue.getBundle()); } else {
		 * BundleDenomination5Soiled =
		 * BundleDenomination5Soiled.add(branchValue.getBundle()); } } }
		 */

		map.put("BundleDenomination2000Atm", BundleDenomination2000Atm);
		map.put("BundleDenomination1000Atm", BundleDenomination1000Atm);
		map.put("BundleDenomination500Atm", BundleDenomination500Atm);
		map.put("BundleDenomination200Atm", BundleDenomination200Atm);
		map.put("BundleDenomination100Atm", BundleDenomination100Atm);
		map.put("BundleDenomination50Atm", BundleDenomination50Atm);
		map.put("BundleDenomination20Atm", BundleDenomination20Atm);
		map.put("BundleDenomination10Atm", BundleDenomination10Atm);
		map.put("BundleDenomination5Atm", BundleDenomination5Atm);

		map.put("BundleDenomination2000Soiled", BundleDenomination2000Soiled);
		map.put("BundleDenomination1000Soiled", BundleDenomination1000Soiled);
		map.put("BundleDenomination500Soiled", BundleDenomination500Soiled);
		map.put("BundleDenomination200Soiled", BundleDenomination200Soiled);
		map.put("BundleDenomination100Soiled", BundleDenomination100Soiled);
		map.put("BundleDenomination50Soiled", BundleDenomination50Soiled);
		map.put("BundleDenomination20Soiled", BundleDenomination20Soiled);
		map.put("BundleDenomination10Soiled", BundleDenomination10Soiled);
		map.put("BundleDenomination5Soiled", BundleDenomination5Soiled);

		map.put("BundleDenomination2000Issuable", BundleDenomination2000Issuable);
		map.put("BundleDenomination1000Issuable", BundleDenomination1000Issuable);
		map.put("BundleDenomination500Issuable", BundleDenomination500Issuable);
		map.put("BundleDenomination200Issuable", BundleDenomination200Issuable);
		map.put("BundleDenomination100Issuable", BundleDenomination100Issuable);
		map.put("BundleDenomination50Issuable", BundleDenomination50Issuable);
		map.put("BundleDenomination20Issuable", BundleDenomination20Issuable);
		map.put("BundleDenomination10Issuable", BundleDenomination10Issuable);
		map.put("BundleDenomination5Issuable", BundleDenomination5Issuable);

		map.put("BundleDenomination2000", BundleDenomination2000);
		map.put("BundleDenomination1000", BundleDenomination1000);
		map.put("BundleDenomination500", BundleDenomination500);
		map.put("BundleDenomination200", BundleDenomination200);
		map.put("BundleDenomination100", BundleDenomination100);
		map.put("BundleDenomination50", BundleDenomination50);
		map.put("BundleDenomination20", BundleDenomination20);
		map.put("BundleDenomination10", BundleDenomination10);
		map.put("BundleDenomination5", BundleDenomination5);
		map.put("currenctDate", sDate.getTime());
		return new ModelAndView("/stockMovementRegister", map);
	}

}