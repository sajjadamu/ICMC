package com.chest.currency.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
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
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.chest.currency.entity.model.AssignVaultCustodian;
import com.chest.currency.entity.model.AuditorProcess;
import com.chest.currency.entity.model.BinRegister;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CRAAllocation;
import com.chest.currency.entity.model.ChestMaster;
import com.chest.currency.entity.model.CustodianKeySet;
import com.chest.currency.entity.model.DateRange;
import com.chest.currency.entity.model.DefineKeySet;
import com.chest.currency.entity.model.Discrepancy;
import com.chest.currency.entity.model.DiscrepancyAllocation;
import com.chest.currency.entity.model.FreshCurrency;
import com.chest.currency.entity.model.Indent;
import com.chest.currency.entity.model.Machine;
import com.chest.currency.entity.model.MachineAllocation;
import com.chest.currency.entity.model.MachineDowntimeUpdation;
import com.chest.currency.entity.model.MachineMaintenance;
import com.chest.currency.entity.model.MachineSoftwareUpdation;
import com.chest.currency.entity.model.Mutilated;
import com.chest.currency.entity.model.MutilatedIndent;
import com.chest.currency.entity.model.Process;
import com.chest.currency.entity.model.ProcessBundleForCRAPayment;
import com.chest.currency.entity.model.RepeatabilityTestInput;
import com.chest.currency.entity.model.RepeatabilityTestOutput;
import com.chest.currency.entity.model.SuspenseOpeningBalance;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DenominationType;
import com.chest.currency.enums.DowntimeReason;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.ProcessAction;
import com.chest.currency.enums.Status;
import com.chest.currency.exception.BaseGuiException;
import com.chest.currency.jpa.persistence.converter.CurrencyFormatter;
import com.chest.currency.service.ICMCService;
import com.chest.currency.service.MachineService;
import com.chest.currency.service.ProcessingRoomService;
import com.chest.currency.util.UtilityJpa;
import com.mysema.query.Tuple;

@org.springframework.stereotype.Controller
@Scope("session")
public class ProcessingRoomController {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessingRoomController.class);

	@Autowired
	ProcessingRoomService processingRoomService;

	@Autowired
	ICMCService icmcService;

	@Autowired
	MachineService machineService;

	@Autowired
	String prnFilePath;

	@Autowired
	String documentFilePath;

	@RequestMapping("/viewIndentRequest")
	public ModelAndView viewIndentRequest(HttpSession session) {
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");

		List<Tuple> rbiList = processingRoomService.indentSummaryForFreshFromBinTxn(user.getIcmcId(), CashSource.RBI);
		List<Tuple> branchList = processingRoomService.indentSummary(user.getIcmcId(), CashSource.BRANCH);
		List<Tuple> dsbList = processingRoomService.indentSummary(user.getIcmcId(), CashSource.DSB);
		List<Tuple> diversionList = processingRoomService.indentSummary(user.getIcmcId(), CashSource.DIVERSION);
		List<Tuple> otherBankList = processingRoomService.indentSummary(user.getIcmcId(), CashSource.OTHERBANK);
		// List<Machine> machineSoftwareList =
		// machineService.getMachineNumber(user.getIcmcId());
		map.put("summaryRecords", branchList);
		map.put("dsbRecords", dsbList);
		map.put("diversionRecords", diversionList);
		map.put("otherBankRecords", otherBankList);
		map.put("freshFromRBIRecords", rbiList);
		// map.put("machineSoftwareUpdation", machineSoftwareList);
		return new ModelAndView("viewIndentRequest", map);
	}

	@RequestMapping("/indentRequest")
	@ResponseBody
	public Indent saveIndent(@RequestBody Indent indent, HttpSession session, User user,
			RedirectAttributes redirectAttributes) {

		user = (User) session.getAttribute("login");
		boolean isAllSuccess = false;

		List<Indent> eligibleIndentRequestList = new ArrayList<>();
		List<BinTransaction> binTransactionList = new ArrayList<>();
		List<BranchReceipt> branchReceiptList = new ArrayList<>();
		LOG.info("indentRequest controller user " + user);
		LOG.info("indentRequest controller indent " + indent);
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			indent.setInsertBy(user.getId());
			indent.setUpdateBy(user.getId());
			indent.setIcmcId(user.getIcmcId());

			if (indent.getBinCategoryType() == null) {
				indent.setBinCategoryType(BinCategoryType.BIN);
			}
			List<BinTransaction> txnList = processingRoomService.getBinNumListForIndent(indent.getDenomination(),
					indent.getRequestBundle(), user.getIcmcId(), indent.getCashSource(), indent.getBinCategoryType());
			BigDecimal bundleFromTxn = BigDecimal.ZERO;
			for (BinTransaction binTx : txnList) {
				bundleFromTxn = bundleFromTxn
						.add(UtilityJpa.getSubstarctedBundle(binTx, (UtilityJpa.getPendingBundleRequest(binTx))));
			}

			BigDecimal bundleForRequest = indent.getRequestBundle();
			LOG.info("indentRequest controller bundleFromTxn " + bundleFromTxn);
			LOG.info("indentRequest controller bundleForRequest " + bundleForRequest);
			if (bundleFromTxn.compareTo(bundleForRequest) >= 0) {

				binTransactionList.addAll(txnList);

				if (indent.getCashSource() == CashSource.BRANCH) {
					branchReceiptList = processingRoomService.getBinNumListForIndentFromBranchReceipt(
							indent.getDenomination(), indent.getRequestBundle(), user.getIcmcId(),
							indent.getCashSource(), indent.getBinCategoryType());

					eligibleIndentRequestList = UtilityJpa.getBinForBranchReceiptIndentRequest(txnList,
							indent.getDenomination(), indent.getRequestBundle(), user, indent, branchReceiptList);
					LOG.info("eligibleIndentRequestList txnList " + txnList);
					LOG.info("eligibleIndentRequestList branchReceiptList " + branchReceiptList);
				} else if (indent.getCashSource() == CashSource.RBI
						&& indent.getBinCategoryType() == BinCategoryType.BOX) {

					eligibleIndentRequestList = UtilityJpa.getBoxForIndentRequest(txnList, indent.getDenomination(),
							indent.getRequestBundle(), user, indent);

				} else {
					eligibleIndentRequestList = UtilityJpa.getBinForIndentRequest(txnList, indent.getDenomination(),
							indent.getRequestBundle(), user, indent);
				}

				BigDecimal moreBundleNeeded = UtilityJpa.checkMoreRequiredBundleNeeded(eligibleIndentRequestList,
						bundleForRequest);
				LOG.info("indent Request moreBundleNeeded " + moreBundleNeeded);
				if (moreBundleNeeded.compareTo(BigDecimal.ZERO) == 0) {
					isAllSuccess = processingRoomService.insertIndentRequestAndUpdateBinTxAndBranchReceipt(
							eligibleIndentRequestList, binTransactionList, branchReceiptList);
					if (indent.getCashSource().equals(CashSource.DSB)
							|| indent.getCashSource().equals(CashSource.OTHERBANK)
							|| indent.getCashSource().equals(CashSource.DIVERSION)) {
						processingRoomService.updateCashReceiveForIndentRequest(eligibleIndentRequestList);
					}
					if (!isAllSuccess) {
						throw new RuntimeException("Error while Indent Request Saving");
					}
				} else {
					String message = "";
					// String returnBundle = "";
					if (!branchReceiptList.isEmpty()) {
						for (BranchReceipt br : branchReceiptList) {
							message = message + br.getBundle().toPlainString() + ", ";
							/*
							 * if(br.getFilepath() !=null && br.getSasId()
							 * ==null){ returnBundle=returnBundle
							 * +br.getBundle().toString() + ","; }else { message
							 * = message + br.getBundle().toPlainString() + ", "
							 * ; }
							 */
						}
					}
					// message = returnBundle + message;
					indent.setMessage(message);
					indent.setAvailableBundle(bundleFromTxn);
					indent.setStatus(OtherStatus.CANCELLED);
				}
			} else {
				indent.setAvailableBundle(bundleFromTxn);
				indent.setStatus(OtherStatus.CANCELLED);
			}
		}
		return indent;
	}

	@RequestMapping("/searchDiscrepancy")

	public ModelAndView searchDiscrepancy(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		User user = (User) session.getAttribute("login");

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null;
		Date tDate = null;
		try {
			sDate = sdf.parse(fromDate);
			tDate = sdf.parse(toDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<DiscrepancyAllocation> discrepancylist = processingRoomService.getDiscrepancyByDate(user.getIcmcId(),
				sDate, tDate, "NORMAL");
		return new ModelAndView("/viewDiscrepancy", "records", discrepancylist);
	}

	@RequestMapping("/indentRequestForFresh")
	@ResponseBody
	public Indent saveIndentForFresh(@RequestBody Indent indent, HttpSession session, User user,
			RedirectAttributes redirectAttributes) {

		user = (User) session.getAttribute("login");
		indent.setInsertBy(user.getId());
		indent.setUpdateBy(user.getId());
		indent.setIcmcId(user.getIcmcId());

		List<Indent> eligibleIndentRequestList = new ArrayList<>();
		List<BinTransaction> binTransactionList = new ArrayList<>();
		List<BranchReceipt> branchReceiptList = new ArrayList<>();

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			if (indent.getBinCategoryType() == null) {
				indent.setBinCategoryType(BinCategoryType.BIN);
			}

			List<BinTransaction> txnListForFresh = processingRoomService.getBinNumListForFreshIndent(
					indent.getDenomination(), indent.getRequestBundle(), user.getIcmcId(), indent.getCashSource(),
					indent.getBinCategoryType(), indent.getRbiOrderNo());

			BigDecimal bundleFromTxnForFresh = BigDecimal.ZERO;
			for (BinTransaction binTx1 : txnListForFresh) {
				bundleFromTxnForFresh = bundleFromTxnForFresh
						.add(UtilityJpa.getSubstarctedBundle(binTx1, (UtilityJpa.getPendingBundleRequest(binTx1))));
			}

			BigDecimal bundleForRequest = indent.getRequestBundle();
			if (bundleFromTxnForFresh.compareTo(bundleForRequest) >= 0) {

				binTransactionList.addAll(txnListForFresh);

				if (indent.getCashSource() == CashSource.RBI && indent.getBinCategoryType() == BinCategoryType.BOX) {
					eligibleIndentRequestList = UtilityJpa.getBoxForIndentRequest(txnListForFresh,
							indent.getDenomination(), indent.getRequestBundle(), user, indent);
				} else {
					eligibleIndentRequestList = UtilityJpa.getBinForRBIIndentRequest(txnListForFresh,
							indent.getDenomination(), indent.getRequestBundle(), user, indent);
				}

				BigDecimal moreBundleNeeded = UtilityJpa.checkMoreRequiredBundleNeeded(eligibleIndentRequestList,
						bundleForRequest);
				boolean isAllSuccess = false;
				if (moreBundleNeeded.compareTo(BigDecimal.ZERO) == 0) {
					isAllSuccess = processingRoomService.insertIndentRequestAndUpdateBinTxAndBranchReceipt(
							eligibleIndentRequestList, binTransactionList, branchReceiptList);
					if (!isAllSuccess) {
						throw new RuntimeException("Error while Indent Request Saving");
					}
				} else {
					String message = "";
					if (!branchReceiptList.isEmpty()) {
						for (BranchReceipt br : branchReceiptList) {
							message = message + br.getBundle().toPlainString() + ", ";
						}
					}
					indent.setMessage(message);
					indent.setAvailableBundle(bundleFromTxnForFresh);
					indent.setStatus(OtherStatus.CANCELLED);
				}
			} else {
				indent.setAvailableBundle(bundleFromTxnForFresh);
				indent.setStatus(OtherStatus.CANCELLED);
			}
		}
		return indent;
	}

	@RequestMapping("/acceptIndent")
	public ModelAndView acceptIndent(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<Indent> indentList = processingRoomService.getIndentRequest(user.getIcmcId());
		return new ModelAndView("acceptIndent", "records", indentList);
	}

	@RequestMapping("/viewBinDetail")
	@ResponseBody
	public List<Indent> viewBinDetail(@RequestParam int denomination, @RequestParam String bin, HttpSession session) {
		User user = (User) session.getAttribute("login");

		List<Indent> indentList = processingRoomService.viewBinDetail(denomination, bin, user.getIcmcId());

		// make an bintx object, bring receive bundles for a particular bin from
		// BinTxn table
		// make a Indentwrapper which is having two fields, indentList and bintx
		// then return and show total bundle also .

		/*
		 * ModelMap model = new ModelMap(); List<IndentWrapper>
		 * indentWrapperList = new ArrayList<>(); BigDecimal totalBundle =
		 * processingRoomService.getTotalBundleInBin(denomination, bin,
		 * user.getIcmcId()); indentWrapperList.add((IndentWrapper) indentList);
		 * indentWrapperList.add(totalBundle); model.put("indentWrapperList",
		 * indentWrapperList); return new ModelAndView("acceptIndent",
		 * "records", model);
		 */

		return indentList;
	}

	@RequestMapping("/editIndentRequest")
	public ModelAndView editUser(@RequestParam long id) {
		Indent indent = new Indent();
		indent = processingRoomService.getIndentById(id);
		return new ModelAndView("editIndentRequest", "user", indent);
	}

	@RequestMapping("/updateIndentRequest")
	public ModelAndView updateIndent(@ModelAttribute("obj") Indent indent, HttpSession session) {
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			indent.setInsertBy(user.getId());
			indent.setUpdateBy(user.getId());
			indent.setIcmcId(user.getIcmcId());
			processingRoomService.updateIndentRequest(indent);
		}
		List<Indent> indentList = processingRoomService.getIndentRequest(user.getIcmcId());
		return new ModelAndView("viewIndentRequest", "records", indentList);
	}

	@RequestMapping("/editMachineMaintenance")
	public ModelAndView editMachineMaintenance(@RequestParam long id) {
		ModelMap map = new ModelMap();
		map.put("statusList", Status.values());
		MachineMaintenance machineMaintenance = new MachineMaintenance();
		machineMaintenance = processingRoomService.getMachineMaintenanceById(id);
		map.put("user", machineMaintenance);
		return new ModelAndView("editMachineMaintenance", map);
	}

	@RequestMapping("/updateMachineMaintenance")
	public ModelAndView updateMachineMaintenance(@ModelAttribute("user") MachineMaintenance machine,
			HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			machine.setInsertBy(user.getId());
			machine.setUpdateBy(user.getId());
			machine.setInsertTime(now);
			machine.setUpdateTime(now);
			machine.setIcmcId(user.getIcmcId());
			processingRoomService.updateMachineMaintenanceStatus(machine);
			redirectAttributes.addFlashAttribute("updateMsg", "Machine Maintenance detail updated successfully");
		}
		return new ModelAndView("redirect:./viewMachineMaintenance");
	}

	@RequestMapping("/saveMachineAllocationData")
	@ResponseBody
	public MachineAllocation saveMachineAllocationData(@RequestBody MachineAllocation machineAllocation,
			HttpSession session, Indent indent) {
		Calendar now = Calendar.getInstance();
		User user = (User) session.getAttribute("login");
		boolean isAllSuccess = false;

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			machineAllocation.setInsertBy(user.getId());
			machineAllocation.setUpdateBy(user.getId());
			machineAllocation.setInsertTime(now);
			machineAllocation.setUpdateTime(now);
			machineAllocation.setIcmcId(user.getIcmcId());
			machineAllocation.setPendingBundle(machineAllocation.getIssuedBundle());
			machineAllocation.setStatus(OtherStatus.REQUESTED);
			isAllSuccess = processingRoomService.processMachineAllocation(machineAllocation, indent, user);
			if (!isAllSuccess) {
				throw new RuntimeException("Error while Machine Allocation and Indent Updation");
			}
		}
		return machineAllocation;
	}

	@RequestMapping("/updateIndentStatus")
	public ModelAndView updateIndentStatus(@RequestParam(value = "bin") String bin,
			@RequestParam(value = "bundle") BigDecimal bundle,
			@RequestParam(value = "denomination") Integer denomination, HttpSession session) {

		User user = (User) session.getAttribute("login");
		boolean isAllSuccess = false;
		synchronized (icmcService.getSynchronizedIcmc(user)) {

			isAllSuccess = processingRoomService.processIndentRequest(bin, bundle, user);

			if (!isAllSuccess) {
				throw new BaseGuiException("Error while process Indent Request");
			}
		}

		// Bin Register Code
		Calendar now = Calendar.getInstance();
		BinRegister binRegister = new BinRegister();
		binRegister.setBinNumber(bin);
		binRegister.setWithdrawalBundle(bundle);
		binRegister.setDenomination(denomination);
		binRegister.setReceiveBundle(BigDecimal.ZERO);
		binRegister.setInsertBy(user.getId());
		binRegister.setUpdateBy(user.getId());
		binRegister.setIcmcId(user.getIcmcId());
		binRegister.setInsertTime(now);
		binRegister.setUpdateTime(now);
		binRegister.setType("U");
		processingRoomService.saveDataInBinRegister(binRegister);
		// Close bin Register Code
		return new ModelAndView("welcome");
	}

	@ExceptionHandler(BaseGuiException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public BaseGuiException handleException(BaseGuiException ex, HttpServletResponse response) throws IOException {
		return ex;

	}

	@RequestMapping(value = "/QRPathProcess")
	@ResponseBody
	public List<String> getQRCodePath(@RequestBody Process process, HttpSession session) throws IOException {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		List<Process> processList = null;
		StringBuilder sb = null;
		List<String> prnList = new ArrayList<>();
		StringBuilder sbBinName = new StringBuilder();

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			prnList.add(sbBinName.toString());
			process.setInsertBy(user.getId());
			process.setUpdateBy(user.getId());
			process.setInsertTime(now);
			process.setUpdateTime(now);
			try {
				processList = processingRoomService.processRecordForMachine(process, user);
			} catch (Exception ex) {
				LOG.info("Error has occred", ex);
				throw ex;
			}
			boolean isAllSuccess = processList != null && processList.size() > 0;
			if (isAllSuccess) {
				for (Process proc : processList) {
					sbBinName.append(proc.getBinNumber()).append(",");
					try {
						File file = new File(prnFilePath);
						BufferedReader reader = new BufferedReader(new FileReader(file));
						String line = "", oldtext = "";
						while ((line = reader.readLine()) != null) {
							oldtext += line + "\r\n";
						}
						reader.close();
						for (int i = 0; i < proc.getBundle().intValue(); i++) {
							String replacedtext = oldtext.replaceAll("bin", "" + proc.getCurrencyType());
							replacedtext = replacedtext.replaceAll("Bin: ", "" + "");
							replacedtext = replacedtext.replaceAll("Branch: ", "" + "");
							replacedtext = replacedtext.replaceAll("Sol ID :", "" + "");
							replacedtext = replacedtext.replaceAll("branch", "" + proc.getDenomination());
							replacedtext = replacedtext.replaceAll("solId", "" + proc.getBinNumber());
							replacedtext = replacedtext.replaceAll("denom", "" + proc.getDenomination());
							replacedtext = replacedtext.replaceAll("bundle", "" + BigDecimal.ONE);

							String formattedTotal = CurrencyFormatter
									.inrFormatter(BigDecimal.valueOf(proc.getDenomination() * 1000)).toString();
							replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

							sb = new StringBuilder(replacedtext);
							prnList.add(sb.toString());

							UtilityJpa.PrintToPrinter(sb, user);
						}
					} catch (IOException ioe) {
						throw new BaseGuiException("Success... But Problem  in printing " + ioe.getMessage());
					}
				}
			}
			prnList.set(0, sbBinName.toString());
		}
		return prnList;
	}

	@RequestMapping("/processEntry")
	public ModelAndView processEntry(HttpSession session) {
		Process obj = new Process();
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<Tuple> pendingBundleList = processingRoomService.getPendingBundle(user.getIcmcId(), sDate, eDate);
		List<Tuple> pendingBundleListForMachine = processingRoomService.getPendingBundleByMachine(user.getIcmcId(),
				sDate, eDate);
		map.put("denominationList", DenominationType.values());
		map.put("currencyType", CurrencyType.values());
		map.put("processActionList", ProcessAction.values());
		map.put("user", obj);
		map.put("summaryList", pendingBundleList);
		map.put("pendingBundleListForMachine", pendingBundleListForMachine);
		return new ModelAndView("processEntry", map);
	}

	@RequestMapping("/viewProcess")
	public ModelAndView viewProcess(HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<Process> list = processingRoomService.getProcessedDataList(user.getIcmcId(), sDate, eDate);
		return new ModelAndView("viewProcess", "records", list);
	}

	@RequestMapping("/acceptProcessingRoomOutput")
	public ModelAndView acceptProcessingRoomOutput(HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<Process> list = processingRoomService.getProcessedDataList(user.getIcmcId(), sDate, eDate);
		return new ModelAndView("acceptProcessingRoomOutput", "records", list);
	}

	/*
	 * @RequestMapping("/indentSummary") public ModelAndView
	 * indentSummary(HttpSession session) { User user = (User)
	 * session.getAttribute("login"); List<Tuple> list =
	 * processingRoomService.indentSummary(user.getIcmcId()); return new
	 * ModelAndView("indentSummary", "summaryRecords", list); }
	 */

	@RequestMapping("/viewForMachineAllocation")
	public ModelAndView dataForMachineAllocation(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

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
		// List<Machine> machineList =
		// processingRoomService.getMachineNumber(user.getIcmcId());

		map.put("records", indentListForBranch);
		map.put("recordsDSB", indentListForDSB);
		map.put("recordsRBI", indentListForRBI);
		map.put("recordsOtherBank", indentListForOtherBank);
		map.put("recordsDiversion", indentListForDiversion);
		// map.put("machineList", machineList);
		return new ModelAndView("viewForMachineAllocation", map);
	}

	@RequestMapping("/updateprocessedStatus")
	public ModelAndView updateprocessedStatus(@RequestParam(value = "id") long id,
			@RequestParam(value = "type") String type, HttpSession session) {
		Process process = new Process();
		User user = (User) session.getAttribute("login");
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			process.setId(id);
			process.setIcmcId(user.getIcmcId());
			processingRoomService.updatePrcocessStatus(process);
		}
		return new ModelAndView("welcome");
	}

	@RequestMapping("/MachineDowntimeUpdation")
	public ModelAndView machineDownTime(HttpSession session) {
		User user = (User) session.getAttribute("login");
		// List<String> region = machineService.getRegionFromZoneMaster();
		MachineDowntimeUpdation obj = new MachineDowntimeUpdation();
		List<Machine> machineList = machineService.getMachineNumber(user.getIcmcId());
		ModelMap map = new ModelMap();
		map.put("machineList", machineList);
		map.put("reasonList", DowntimeReason.values());
		map.put("user", obj);
		return new ModelAndView("/MachineDowntimeUpdation", map);
	}

	@RequestMapping("/AddMachineDowntimeUpdation")
	public ModelAndView viewMachineDownTime(/*
											 * /
											 * 
											 * @ModelAttribute("user")
											 * MachineDowntimeUpdation machine
											 */
			@RequestParam(name = "machineDownDateFrom", required = false) String machineDownDateFrom,
			@RequestParam(name = "machineDownDateTo", required = false) String machineDownDateTo,
			@RequestParam(name = "engineerAttendedCall", required = false) String engineerAttendedCall,
			@RequestParam(name = "machineType", required = false) String machineType,
			@RequestParam(name = "machineNo", required = false) Integer machineNo,
			@RequestParam(name = "downtimeReason", required = false) DowntimeReason downtimeReason,
			@RequestParam(name = "remarks", required = false) String remarks, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		MachineDowntimeUpdation machine = new MachineDowntimeUpdation();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			machine.setInsertBy(user.getId());
			machine.setUpdateBy(user.getId());
			machine.setInsertTime(now);
			machine.setUpdateTime(now);
			machine.setIcmcId(user.getIcmcId());

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:m:s");
			String dateInString = machineDownDateFrom;
			Date date = null;
			try {
				date = formatter.parse(dateInString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			machine.setMachineDownDateFrom(date);
			String dateInString1 = machineDownDateTo;
			Date date1 = null;
			try {
				date1 = formatter.parse(dateInString1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			machine.setMachineDownDateTo(date1);

			String dateInString2 = engineerAttendedCall;
			Date date2 = null;
			try {
				date2 = formatter.parse(dateInString2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			machine.setEngineerAttendedCall(date2);
			machine.setMachineType(machineType);
			machine.setDowntimeReason(downtimeReason);
			machine.setMachineNo(machineNo);
			machine.setRemarks(remarks);
			machineService.insertMachineDownTime(machine);
			redirectAttributes.addFlashAttribute("successMsg", "Machine downtime detail added successfully");
		}
		return new ModelAndView("redirect:./viewMachineDownTime");
	}

	/*
	 * @RequestMapping("/AddMachineDowntimeUpdation") public ModelAndView
	 * viewMachineDownTime(@ModelAttribute("user") MachineDowntimeUpdation
	 * machine, HttpSession session, RedirectAttributes redirectAttributes) {
	 * User user = (User) session.getAttribute("login"); Calendar now =
	 * Calendar.getInstance(); synchronized
	 * (icmcService.getSynchronizedIcmc(user)) {
	 * machine.setInsertBy(user.getId()); machine.setUpdateBy(user.getId());
	 * machine.setInsertTime(now); machine.setUpdateTime(now);
	 * machine.setIcmcId(user.getIcmcId());
	 * 
	 * //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"
	 * ); machine.setMachineDownDateFrom(machine.getMachineDownDateFrom());
	 * machine.setMachineDownDateTo(machine.getMachineDownDateTo());
	 * machine.setEngineerAttendedCall(machine.getEngineerAttendedCall());
	 * machine.setMachineType(machine.getMachineType());
	 * machine.setDowntimeReason(machine.getDowntimeReason());
	 * machine.setMachineNo(machine.getMachineNo());
	 * machine.setRemarks(machine.getRemarks());
	 * machineService.insertMachineDownTime(machine);
	 * redirectAttributes.addFlashAttribute("successMsg",
	 * "Machine downtime detail added successfully"); } return new
	 * ModelAndView("redirect:./viewMachineDownTime"); }
	 */

	@RequestMapping("/viewMachineDownTime")
	public ModelAndView viewMachineDownTime(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		List<MachineDowntimeUpdation> machinelist = machineService.getMachineDownTime(user.getIcmcId());
		LOG.info("View machine downtime details");
		map.put("records", machinelist);
		return new ModelAndView("/viewMachineDownTime", map);

	}

	@RequestMapping("/editMachineDownTime")
	public ModelAndView editCITCRAVendor(@RequestParam Long id, MachineDowntimeUpdation machine) {
		ModelMap model = new ModelMap();
		List<String> region = machineService.getRegionFromZoneMaster();
		machine.setId(id);
		machine = machineService.machineRecordForModify(id);
		model.put("user", machine);
		model.put("region", region);

		return new ModelAndView("editMachineDownTime", model);
	}

	@RequestMapping("/updateMachineDownTime")
	public ModelAndView updateMachineDownTime(@ModelAttribute("user") MachineDowntimeUpdation machine,
			HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			machine.setInsertBy(user.getId());
			machine.setUpdateBy(user.getId());
			machine.setInsertTime(now);
			machine.setUpdateTime(now);
			machine.setIcmcId(user.getIcmcId());
			/*
			 * machine.setDowntimeReason(machine.getDowntimeReason());
			 * machine.setEngineerAttendedCall(machine.getEngineerAttendedCall()
			 * ); machine.setMachineType(machine.getMachineType());
			 * machine.setMachineNo(machine.getMachineNo());
			 */
			machineService.updateMachineDownTime(machine);
			redirectAttributes.addFlashAttribute("successMsg", "Machine downtime detail updated successfully");
		}
		return new ModelAndView("redirect:./viewMachineDownTime");
	}

	@RequestMapping("/viewMachineSoftware")
	public ModelAndView viewMachineSoftware(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<MachineSoftwareUpdation> machineSoftlist = machineService.getMachineSoftwareUpdation(user.getIcmcId());
		LOG.info("VIEW MachinpSoftware RECORD");
		return new ModelAndView("/viewMachineSoftware", "records", machineSoftlist);
	}

	@RequestMapping("/MachineSoftwareUpdation")
	public ModelAndView MachineSoftwareUpdation(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<Machine> machineList = machineService.getMachineNumber(user.getIcmcId());
		return new ModelAndView("/MachineSoftwareUpdation", "records", machineList);
	}

	@RequestMapping("/AddMachineSoftware")
	@ResponseBody
	public Machine AddMachineSoftware(@RequestBody Machine machine, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			machine.setUpdateBy(user.getId());
			machine.setUpdateTime(now);
			machine.setIcmcId(user.getIcmcId());
			machineService.updateMachineSoftwareUpdation(machine);
		}
		return machine;
	}

	@RequestMapping("/editMachineSoftware")
	public ModelAndView editMachineSoftware(@RequestParam Long id, MachineSoftwareUpdation machine) {
		ModelMap model = new ModelMap();
		List<String> region = machineService.getRegionFromZoneMaster();
		machine.setId(id);
		machine = machineService.machineSoftwareRecordForModify(id);
		model.put("user", machine);
		model.put("region", region);
		return new ModelAndView("editMachineSoftware", model);
	}

	@RequestMapping("/updateMachineSoftware")
	public ModelAndView updateMachineSoftware(@ModelAttribute("user") MachineSoftwareUpdation machine,
			HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			machine.setInsertBy(user.getId());
			machine.setUpdateBy(user.getId());
			machine.setInsertTime(now);
			machine.setUpdateTime(now);
			machine.setIcmcId(user.getIcmcId());
			machineService.updateMachineSoftware(machine);
			redirectAttributes.addFlashAttribute("successMsg", "Machine software detail updated successfully");
		}
		return new ModelAndView("redirect:./viewMachineSoftware");
	}

	@RequestMapping("/viewDefineKeySet")
	public ModelAndView viewDefineKeySet(@ModelAttribute("user") CustodianKeySet defineKeySet, HttpSession session) {
		User user = (User) session.getAttribute("login");
		// List<CustodianKeySet> keySetList =
		// processingRoomService.getDefineKeySet(user.getIcmcId());
		List<String> keySetList = processingRoomService.getDefineKeySet(user.getIcmcId());
		LOG.info("VIEW DefineKeySet RECORD");
		return new ModelAndView("/viewDefineKeySet", "records", keySetList);
	}

	@RequestMapping("/viewKeySetDetail")
	public ModelAndView viewDefineKeySetDetail(@RequestParam String custodian, HttpSession session) {
		User user = (User) session.getAttribute("login");
		// List<DefineKeySet> keySetList =
		// processingRoomService.getKeySetDetail(custodian, user.getIcmcId());
		List<Tuple> keySetList = processingRoomService.getKeySetDetail(custodian, user.getIcmcId());
		return new ModelAndView("/viewKeySetDetail", "records", keySetList);
	}

	@RequestMapping("/DefineKeySet")
	public ModelAndView defineKeySet() {
		DefineKeySet obj = new DefineKeySet();
		ModelMap map = new ModelMap();
		map.put("user", obj);
		map.addAttribute("documentFilePath", "./files/" + documentFilePath);
		return new ModelAndView("/DefineKeySet", map);
	}

	private static CellProcessor[] getKeySet() {
		final CellProcessor[] processors = new CellProcessor[] { new NotNull(), new NotNull(), new NotNull(), };
		return processors;
	}

	private static CellProcessor[] getChestSlip() {
		final CellProcessor[] processors = new CellProcessor[] { new ParseBigInteger(), new ParseBigInteger(),

		};
		return processors;
	}

	@RequestMapping("/AddDefineKeySet")
	public ModelAndView addAddDefineKeySet(@ModelAttribute("user") DefineKeySet defineKeySet,
			@RequestParam MultipartFile file, HttpSession session, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		defineKeySet.setInsertBy(user.getId());
		defineKeySet.setUpdateBy(user.getId());
		defineKeySet.setInsertTime(now);
		defineKeySet.setUpdateTime(now);
		defineKeySet.setIcmcId(user.getIcmcId());

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
		List<DefineKeySet> keySetRecords = new LinkedList<>();
		DefineKeySet keySetUpload = null;
		try {
			try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile),
					CsvPreference.STANDARD_PREFERENCE)) {
				final String[] headers = beanReader.getHeader(true);
				final CellProcessor[] processors = getKeySet();
				while ((keySetUpload = beanReader.read(DefineKeySet.class, headers, processors)) != null) {
					keySetRecords.add(keySetUpload);
				}
			}
		} catch (Exception r) {
			r.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMsg", "CSV file is not of standard format");
			return new ModelAndView("redirect:./DefineKeySet");
		}
		processingRoomService.UploadDefineKeySet(keySetRecords, defineKeySet);
		redirectAttributes.addFlashAttribute("uploadMsg", "Key Set records have been uploaded successfully");
		return new ModelAndView("redirect:./viewDefineKeySet");
	}

	@RequestMapping("/uploadChestMaster")
	public ModelAndView addAddChestSlip(@ModelAttribute("user") ChestMaster chestMaster,
			@RequestParam MultipartFile file, HttpSession session, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar privious = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		privious.add(Calendar.DATE, -1);
		chestMaster.setInsertBy(user.getId());
		chestMaster.setUpdateBy(user.getId());
		chestMaster.setInsertTime(privious);
		chestMaster.setUpdateTime(now);
		chestMaster.setIcmcId(user.getIcmcId());

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
		List<ChestMaster> ChestSlipRecords = new LinkedList<>();
		ChestMaster chestMasterUpload = null;
		try {
			try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile),
					CsvPreference.STANDARD_PREFERENCE)) {
				final String[] headers = beanReader.getHeader(true);
				final CellProcessor[] processors = getChestSlip();
				while ((chestMasterUpload = beanReader.read(ChestMaster.class, headers, processors)) != null) {
					ChestSlipRecords.add(chestMasterUpload);
				}
			}
		} catch (Exception r) {
			r.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMsgChestMaster", "CSV file is not of standard format");
			return new ModelAndView("redirect:./binTransaction");
		}
		processingRoomService.UploadChestSlip(ChestSlipRecords, chestMaster);
		redirectAttributes.addFlashAttribute("uploadMsg", "Chest Master records have been uploaded successfully");
		return new ModelAndView("redirect:./binTransaction");
	}

	@RequestMapping("/editDefineKeySet")
	public ModelAndView editFreshCurrency(@RequestParam Long id, DefineKeySet defineKeySet) {
		ModelMap model = new ModelMap();
		defineKeySet.setId(id);
		defineKeySet = processingRoomService.defineKeySetRecordForModify(id);
		List<DefineKeySet> keyNumberList = processingRoomService.getKeyNumber(defineKeySet);
		List<DefineKeySet> locationOfLockList = processingRoomService.getLocationOfLock(defineKeySet);
		model.put("user", defineKeySet);
		model.put("keyNumberList", keyNumberList);
		model.put("locationOfLockList", locationOfLockList);
		return new ModelAndView("editDefineKeySet", model);
	}

	@RequestMapping("/UpdateDefineKeySet")
	public ModelAndView UpdateDefineKeySet(@ModelAttribute("user") DefineKeySet defineKeySet, HttpSession session,
			RedirectAttributes redirectAttributes, HttpServletRequest request, @RequestParam String custodian) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		// long id=defineKeySet.getId();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			defineKeySet.setInsertBy(user.getId());
			defineKeySet.setUpdateBy(user.getId());
			defineKeySet.setInsertTime(now);
			defineKeySet.setUpdateTime(now);
			defineKeySet.setIcmcId(user.getIcmcId());
			defineKeySet.setId(defineKeySet.getId());
			processingRoomService.modifyDefineKeySet(defineKeySet);
			redirectAttributes.addFlashAttribute("successMsg", "Key Set record has been updated successfully");

		}
		return new ModelAndView("redirect:./viewDefineKeySet");
	}

	@RequestMapping("/viewAssignVaultCustodian")
	public ModelAndView viewAssignVaultCustodian(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<AssignVaultCustodian> assginVaultCustodianList = processingRoomService
				.getAssignVaultCustodian(user.getIcmcId(), sDate, eDate);
		for (AssignVaultCustodian assignVaultCustodian : assginVaultCustodianList) {
			String userName = processingRoomService.getUserName(assignVaultCustodian.getUserId());
			if (userName != null) {
				assignVaultCustodian.setUserName(userName);
			}
		}
		map.put("records", assginVaultCustodianList);
		return new ModelAndView("/viewAssignVaultCustodian", map);
	}

	@RequestMapping("/AssignVaultCustodian")
	public ModelAndView assignVaultCustodian(HttpSession session) {
		User user = (User) session.getAttribute("login");
		AssignVaultCustodian obj = new AssignVaultCustodian();
		ModelMap map = new ModelMap();
		// List<CustodianKeySet> custodianList =
		// processingRoomService.getAssignVaultCustodian(user.getIcmcId());
		List<String> custodianList = processingRoomService.getDefineKeySet(user.getIcmcId());
		map.put("user", obj);
		map.put("custodianList", custodianList);
		return new ModelAndView("/AssignVaultCustodian", map);
	}

	@RequestMapping("/AddAssignVaultCustodian")
	public ModelAndView saveVaultCustodian(@ModelAttribute("user") AssignVaultCustodian assignVaultCustodian,
			HttpSession session, RedirectAttributes redirectAttributes, @RequestParam String custodian) {
		User user = (User) session.getAttribute("login");
		User userFromDBForUserID = processingRoomService.isValidUser(assignVaultCustodian.getUserId(),
				user.getIcmcId());
		if (userFromDBForUserID == null) {
			redirectAttributes.addFlashAttribute("takingOverCharge", "User Id of taking Over Charge not exist in ICMC");
			return new ModelAndView("redirect:./AssignVaultCustodian");
		}
		User userFromDBForUserHandingOver = processingRoomService
				.isValidUser(assignVaultCustodian.getHandingOverCharge(), user.getIcmcId());
		if (userFromDBForUserHandingOver == null) {
			redirectAttributes.addFlashAttribute("HandingOverCharge",
					"User Id of Handing Over Charge not exist in ICMC");
			return new ModelAndView("redirect:./AssignVaultCustodian");
		}

		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			assignVaultCustodian.setInsertBy(user.getId());
			assignVaultCustodian.setUpdateBy(user.getId());
			assignVaultCustodian.setInsertTime(now);
			assignVaultCustodian.setUpdateTime(now);
			assignVaultCustodian.setIcmcId(user.getIcmcId());
			assignVaultCustodian.setCustodian(custodian);
			processingRoomService.saveAssignVaultCustodian(assignVaultCustodian);
			redirectAttributes.addFlashAttribute("successMsg", "Record submitted successfully");
		}
		return new ModelAndView("redirect:./viewAssignVaultCustodian");
	}

	@RequestMapping("/editAssignVaultCustodian")
	public ModelAndView editAssignVaultCustodian(@RequestParam Long id, AssignVaultCustodian assignVaultCustodian,
			HttpSession sessions) {
		ModelMap model = new ModelMap();
		User user = (User) sessions.getAttribute("login");
		assignVaultCustodian.setId(id);
		assignVaultCustodian = processingRoomService.assignVaultCustodianRecordForModify(id);
		List<CustodianKeySet> custodianList = processingRoomService.getAssignVaultCustodian(user.getIcmcId());
		model.put("user", assignVaultCustodian);
		model.put("custodianList", custodianList);
		return new ModelAndView("editAssignVaultCustodian", model);
	}

	@RequestMapping("/updateAssignVaultCustodian")
	public ModelAndView updateAssignVaultCustodian(@ModelAttribute("user") AssignVaultCustodian assignVaultCustodian,
			HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();

		User userFromDBForUserID = processingRoomService.isValidUser(assignVaultCustodian.getUserId(),
				user.getIcmcId());

		if (userFromDBForUserID == null) {
			map.put("takingOverCharge", "User Id of taking Over Charge not exist in ICMC");
			return new ModelAndView("/viewAssignVaultCustodian", map);
		}

		User userFromDBForUserHandingOver = processingRoomService
				.isValidUser(assignVaultCustodian.getHandingOverCharge(), user.getIcmcId());

		if (userFromDBForUserHandingOver == null) {
			map.put("HandingOverCharge", "User Id of Handing Over Charge not exist in ICMC");
			return new ModelAndView("/viewAssignVaultCustodian", map);
		}

		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			assignVaultCustodian.setInsertBy(user.getId());
			assignVaultCustodian.setUpdateBy(user.getId());
			assignVaultCustodian.setIcmcId(user.getIcmcId());
			assignVaultCustodian.setInsertTime(now);
			assignVaultCustodian.setUpdateTime(now);
			processingRoomService.updateAssignVaultCustodian(assignVaultCustodian);
			redirectAttributes.addFlashAttribute("successMsg", "Record updated successfully");
		}
		return new ModelAndView("redirect:./viewAssignVaultCustodian");
	}

	@RequestMapping("/KmrReport")
	public ModelAndView kmrReport(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<AssignVaultCustodian> assginVaultCustodianList = processingRoomService
				.getAssignVaultCustodian(user.getIcmcId(), sDate, eDate);
		for (AssignVaultCustodian assignVaultCustodian : assginVaultCustodianList) {
			String userName = processingRoomService.getUserName(assignVaultCustodian.getUserId());
			if (userName != null) {
				assignVaultCustodian.setUserName(userName);
			}
		}
		map.put("records", assginVaultCustodianList);
		return new ModelAndView("/KmrReport", map);
	}

	@RequestMapping("/bundleRequestForMachineProcessing")
	public ModelAndView bundleRequestedForMachineProcessing(HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<MachineAllocation> machineAllocationList = processingRoomService
				.getMachineAllocationRecordForProcessing(user.getIcmcId(), sDate, eDate);
		return new ModelAndView("bundleRequestForMachineProcessing", "records", machineAllocationList);
	}

	@RequestMapping("/viewRepeatabilityTestInput")
	public ModelAndView viewRepeatabilityTestInput(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<RepeatabilityTestInput> repeatabilityTestInputlist = processingRoomService
				.getRepeatabilityTestInput(user.getIcmcId());
		LOG.info("View Repeatability Test Input Details");
		return new ModelAndView("/viewRepeatabilityTestInput", "records", repeatabilityTestInputlist);

	}

	@RequestMapping("/RepeatabilityTestInput")
	public ModelAndView repeatabilityTestInput(HttpSession session, RepeatabilityTestInput repeatabilityTestInput) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		ModelMap map = new ModelMap();
		repeatabilityTestInput.setInsertTime(now);
		// repeatabilityTestInput.setIcmcId(user.getIcmcId());
		List<Integer> machineList = processingRoomService.getMachineNumberList(user.getIcmcId());
		String icmcName = processingRoomService.getICMCName(user.getIcmcId().longValue());
		map.put("denominationList", DenominationType.values());
		map.put("machineList", machineList);
		map.put("user", repeatabilityTestInput);
		map.put("icmcName", icmcName);
		return new ModelAndView("/RepeatabilityTestInput", map);
	}

	@RequestMapping(value = "/getMachineSerialNo")
	@ResponseBody
	public String getMachineSerialNo(@RequestParam(value = "machineNo") int machineNo, HttpSession session) {
		User user = (User) session.getAttribute("login");
		return processingRoomService.getMachineSerialNo(user.getIcmcId(), machineNo);
	}

	@RequestMapping("/AddRepeatabilityTestInput")
	public ModelAndView addRepeatabilityTestInput(@ModelAttribute("user") RepeatabilityTestInput repeatabilityTestInput,
			HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			repeatabilityTestInput.setInsertBy(user.getId());
			repeatabilityTestInput.setUpdateBy(user.getId());
			repeatabilityTestInput.setInsertTime(now);
			repeatabilityTestInput.setUpdateTime(now);
			repeatabilityTestInput.setIcmcId(user.getIcmcId());
			processingRoomService.insertRepeatabilityTestInput(repeatabilityTestInput);
			redirectAttributes.addFlashAttribute("successMsg", "Repeatability test input details added successfully");
		}
		return new ModelAndView("redirect:./viewRepeatabilityTestInput");
	}

	@RequestMapping("/editRepeatabilityTestInput")
	public ModelAndView editRepeatabilityTestInput(@RequestParam Long id,
			RepeatabilityTestInput repeatabilityTestInput) {
		ModelMap model = new ModelMap();
		repeatabilityTestInput.setId(id);
		repeatabilityTestInput = processingRoomService.repeatabilityTestInputRecordForModify(id);
		model.put("denominationList", DenominationType.values());
		model.put("user", repeatabilityTestInput);
		return new ModelAndView("editRepeatabilityTestInput", model);
	}

	@RequestMapping("/updateRepeatabilityTestInput")
	public ModelAndView updateRepeatabilityTestInput(
			@ModelAttribute("user") RepeatabilityTestInput repeatabilityTestInput, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			repeatabilityTestInput.setInsertBy(user.getId());
			repeatabilityTestInput.setUpdateBy(user.getId());
			repeatabilityTestInput.setInsertTime(now);
			repeatabilityTestInput.setUpdateTime(now);
			repeatabilityTestInput.setIcmcId(user.getIcmcId());
			processingRoomService.updateRepeatabilityTestInput(repeatabilityTestInput);
			redirectAttributes.addFlashAttribute("successMsg", "Repeatability test input details updated successfully");
		}
		return new ModelAndView("redirect:./viewRepeatabilityTestInput");
	}

	@RequestMapping("/viewRepeatabilityTestOutput")
	public ModelAndView viewRepeatabilityTestOutput(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<RepeatabilityTestOutput> repeatabilityTestOutputlist = processingRoomService
				.getRepeatabilityTestOutput(user.getIcmcId());
		LOG.info("View Repeatability Test Output Details");
		return new ModelAndView("/viewRepeatabilityTestOutput", "records", repeatabilityTestOutputlist);

	}

	@RequestMapping("/RepeatabilityTestOutput")
	public ModelAndView repeatabilityTestOutput() {
		RepeatabilityTestOutput obj = new RepeatabilityTestOutput();
		ModelMap map = new ModelMap();
		map.put("denominationList", DenominationType.values());
		map.put("user", obj);
		return new ModelAndView("/RepeatabilityTestOutput", map);
	}

	@RequestMapping("/AddRepeatabilityTestOutput")
	public ModelAndView addRepeatabilityTestOutput(
			@ModelAttribute("user") RepeatabilityTestOutput repeatabilityTestOutput, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			repeatabilityTestOutput.setInsertBy(user.getId());
			repeatabilityTestOutput.setUpdateBy(user.getId());
			repeatabilityTestOutput.setInsertTime(now);
			repeatabilityTestOutput.setUpdateTime(now);
			repeatabilityTestOutput.setIcmcId(user.getIcmcId());
			processingRoomService.insertRepeatabilityTestOutput(repeatabilityTestOutput);
			redirectAttributes.addFlashAttribute("successMsg", "Repeatability test output details added successfully");
		}
		return new ModelAndView("redirect:./viewRepeatabilityTestOutput");
	}

	@RequestMapping("/editRepeatabilityTestOutput")
	public ModelAndView editRepeatabilityTestOutput(@RequestParam Long id,
			RepeatabilityTestOutput repeatabilityTestOutput) {
		ModelMap model = new ModelMap();
		repeatabilityTestOutput.setId(id);
		repeatabilityTestOutput = processingRoomService.repeatabilityTestOutputRecordForModify(id);
		model.put("denominationList", DenominationType.values());
		model.put("user", repeatabilityTestOutput);
		return new ModelAndView("editRepeatabilityTestOutput", model);
	}

	@RequestMapping("/updateRepeatabilityTestOutput")
	public ModelAndView updateRepeatabilityTestOutput(
			@ModelAttribute("user") RepeatabilityTestOutput repeatabilityTestOutput, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			repeatabilityTestOutput.setInsertBy(user.getId());
			repeatabilityTestOutput.setUpdateBy(user.getId());
			repeatabilityTestOutput.setInsertTime(now);
			repeatabilityTestOutput.setUpdateTime(now);
			repeatabilityTestOutput.setIcmcId(user.getIcmcId());
			processingRoomService.updateRepeatabilityTestOutput(repeatabilityTestOutput);
			redirectAttributes.addFlashAttribute("successMsg",
					"Repeatability test output details updated successfully");
		}
		return new ModelAndView("redirect:./viewRepeatabilityTestOutput");
	}

	@RequestMapping("/viewfreshCurrency")
	public ModelAndView viewfreshCurrency(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<FreshCurrency> freshCurrencies = processingRoomService.getFreshCurrency(user.getIcmcId());

		return new ModelAndView("/viewfreshCurrency", "records", freshCurrencies);
	}

	@RequestMapping("/FreshCurrency")
	public ModelAndView freshCurrency() {
		FreshCurrency obj = new FreshCurrency();
		ModelMap map = new ModelMap();
		map.put("denominationList", DenominationType.values());
		map.put("user", obj);
		return new ModelAndView("/FreshCurrency", map);
	}

	@RequestMapping("/AddFreshCurrency")
	public ModelAndView addFreshCurrency(@ModelAttribute("user") FreshCurrency freshCurrency, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			freshCurrency.setInsertBy(user.getId());
			freshCurrency.setUpdateBy(user.getId());
			freshCurrency.setInsertTime(now);
			freshCurrency.setUpdateTime(now);
			freshCurrency.setIcmcId(user.getIcmcId());
			processingRoomService.insertFreshCurrency(freshCurrency);
			redirectAttributes.addFlashAttribute("successMsg", "Fresh currency detail added successfully");
		}
		return new ModelAndView("redirect:./viewfreshCurrency");
	}

	@RequestMapping("/editFreshCurrency")
	public ModelAndView editFreshCurrency(@RequestParam Long id, FreshCurrency freshCurrency) {
		ModelMap model = new ModelMap();
		freshCurrency.setId(id);
		freshCurrency = processingRoomService.freshCurrencyForModify(id);
		model.put("denominationList", DenominationType.values());
		model.put("user", freshCurrency);
		return new ModelAndView("editFreshCurrency", model);
	}

	@RequestMapping("/updateFreshCurrency")
	public ModelAndView updateFreshCurrency(@ModelAttribute("user") FreshCurrency freshCurrency, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			freshCurrency.setInsertBy(user.getId());
			freshCurrency.setUpdateBy(user.getId());
			freshCurrency.setInsertTime(now);
			freshCurrency.setUpdateTime(now);
			freshCurrency.setIcmcId(user.getIcmcId());
			processingRoomService.updateFreshCurrency(freshCurrency);
			redirectAttributes.addFlashAttribute("successMsg", "Fresh currency detail updted successfully");
		}
		return new ModelAndView("redirect:./viewfreshCurrency");
	}

	@RequestMapping("/updateIndentStatusForCancel")
	public ModelAndView updateIndentStatusForCancel(@RequestParam(value = "id") long id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Indent indent = new Indent();
		boolean isSaved = false;
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			indent.setId(id);
			isSaved = processingRoomService.updateIndentStatusForCancel(indent);
			if (!isSaved) {
				throw new RuntimeException("Error while process Indent Request");
			}
		}
		return new ModelAndView("welcome");
	}

	@RequestMapping("/addDiscrepancy")
	public ModelAndView discrepancy(HttpSession session) {
		Discrepancy obj = new Discrepancy();
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");
		List<Integer> machineNumberList = processingRoomService.getMachineNumberList(user.getIcmcId());
		map.put("machineNumberList", machineNumberList);
		map.put("user", obj);
		return new ModelAndView("discrepancy", map);
	}

	@RequestMapping("/machineMaintenance")
	public ModelAndView machineMaintanance(HttpSession session) {
		MachineMaintenance obj = new MachineMaintenance();
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");
		List<Integer> machineNumberList = processingRoomService.getMachineNumberList(user.getIcmcId());
		map.put("user", obj);
		map.put("machineNumberList", machineNumberList);
		return new ModelAndView("/machineMaintenance", map);
	}

	@RequestMapping("/saveMachineMaintenance")
	public ModelAndView saveMachineDetails(@ModelAttribute("user") MachineMaintenance machine, HttpSession session) {
		User user = (User) session.getAttribute("login");
		machine.setInsertBy(user.getId());
		machine.setUpdateBy(user.getId());
		machine.setIcmcId(user.getIcmcId());
		Calendar now = Calendar.getInstance();
		machine.setInsertTime(now);
		machine.setUpdateTime(now);
		machine.setStatus(Status.ENABLED);
		processingRoomService.addMachineMaintenance(machine);
		return new ModelAndView("redirect:./viewMachineMaintenance");
	}

	@RequestMapping("/viewMachineMaintenance")
	public ModelAndView viewMachineMaintenance(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<MachineMaintenance> machineMachineMaitenanceList = processingRoomService
				.viewMachineMaintenance(user.getIcmcId());
		return new ModelAndView("/viewMachineMaintenance", "records", machineMachineMaitenanceList);
	}

	@RequestMapping("/addSuspenseDiscrepancy")
	public ModelAndView discrepancySuspense(HttpSession session) {
		Discrepancy obj = new Discrepancy();
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");
		List<Integer> machineNumberList = processingRoomService.getMachineNumberList(user.getIcmcId());
		map.put("machineNumberList", machineNumberList);
		map.put("user", obj);
		return new ModelAndView("discrepancySuspense", map);
	}

	@RequestMapping(value = "/DiscrepancyAllocation")
	@ResponseBody
	public Discrepancy insertDiscrepancy(@RequestBody Discrepancy discrepancy, HttpSession session) throws IOException {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		boolean isAllSuccess = false;
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			discrepancy.setIcmcId(user.getIcmcId());
			discrepancy.setInsertBy(user.getId());
			discrepancy.setUpdateBy(user.getId());
			discrepancy.setInsertTime(now);
			discrepancy.setUpdateTime(now);
			discrepancy.setStatus(0);
			discrepancy.setNormalOrSuspense("NORMAL");

			// discrepancy.setFilepath(UtilityJpa.getImages(discrepancy.getFilepath()));

			isAllSuccess = processingRoomService.saveDiscrepancy(discrepancy);
			if (!isAllSuccess) {
				throw new RuntimeException(
						"Error while saving Discrepancy And Discrepancy Allocation, Please try again");
			}
		}
		return discrepancy;
	}

	@RequestMapping(value = "/insertImage")
	@ResponseBody
	public long insertImage(@RequestParam long id, @RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("sajjad file " + file);

		if (!file.getOriginalFilename().isEmpty()) {
			// Discrepancy discrepancy = null;
			// discrepancy.setFilepath(UtilityJpa.getImages(file.getOriginalFilename()));
			// System.out.println("discrepancy "+discrepancy.getFilepath());
			BufferedOutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(new File("/home/inayat/image/", file.getOriginalFilename())));
			outputStream.write(file.getBytes());
			outputStream.flush();
			outputStream.close();
		} else {

		}
		return id;
	}

	@RequestMapping(value = "/suspenseDiscrepancyAllocation")
	@ResponseBody
	public Discrepancy insertSuspenseDiscrepancy(@RequestBody Discrepancy discrepancy, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		boolean isAllSuccess = false;
		synchronized (icmcService.getSynchronizedIcmc(user)) {
			discrepancy.setIcmcId(user.getIcmcId());
			discrepancy.setInsertBy(user.getId());
			discrepancy.setUpdateBy(user.getId());
			discrepancy.setInsertTime(now);
			discrepancy.setUpdateTime(now);
			discrepancy.setStatus(0);
			discrepancy.setNormalOrSuspense("SUSPENSE");
			isAllSuccess = processingRoomService.saveDiscrepancy(discrepancy);
			if (!isAllSuccess) {
				throw new RuntimeException(
						"Error while saving Suspense Discrepancy And Discrepancy Allocation, Please try again");
			}
		}
		return discrepancy;
	}

	@RequestMapping("/viewDiscrepancy")
	public ModelAndView viewDiscrepancy(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<DiscrepancyAllocation> discrepancylist = processingRoomService.getDiscrepancy(user.getIcmcId(), "NORMAL");
		return new ModelAndView("/viewDiscrepancy", "records", discrepancylist);
	}

	@RequestMapping("/viewSuspenseDiscrepancy")
	public ModelAndView viewSuspenseDiscrepancy(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<DiscrepancyAllocation> discrepancylist = processingRoomService.getDiscrepancy(user.getIcmcId(),
				"SUSPENSE");
		return new ModelAndView("/viewSuspenseDiscrepancy", "records", discrepancylist);
	}

	@RequestMapping("/getMachineNumber")
	@ResponseBody
	public List<Machine> getMachineNumber(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<Machine> machineList = processingRoomService.getMachineNumber(user.getIcmcId());
		return machineList;
	}

	@RequestMapping("/CRAPaymentForProcessing")
	public ModelAndView CRAPaymentForProcessing(HttpSession session) {
		ProcessBundleForCRAPayment obj = new ProcessBundleForCRAPayment();
		User user = (User) session.getAttribute("login");
		List<CRAAllocation> list = processingRoomService.getDataFromCRAAllocationForProcessing(user.getIcmcId());
		ModelMap map = new ModelMap();
		map.put("user", obj);
		map.put("records", list);
		return new ModelAndView("CRAPaymentForProcessing", map);
	}

	@RequestMapping(value = "/QRPathProcessForCRAPayment")
	@ResponseBody
	public ProcessBundleForCRAPayment getQRPathProcessForCRAPayment(
			@RequestBody ProcessBundleForCRAPayment processBundleForCRAPayment, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			processBundleForCRAPayment.setInsertBy(user.getId());
			processBundleForCRAPayment.setUpdateBy(user.getId());
			processBundleForCRAPayment.setInsertTime(now);
			processBundleForCRAPayment.setUpdateTime(now);
			processBundleForCRAPayment.setIcmcId(user.getIcmcId());

			ProcessBundleForCRAPayment processBundle = null;
			synchronized (icmcService.getSynchronizedIcmc(user)) {
				processBundle = processingRoomService.processRecordForCRAPayment(processBundleForCRAPayment, user);
			}
			String filePath = processBundle.getFilepath();
			processBundleForCRAPayment.setFilepath(filePath);
		}
		return processBundleForCRAPayment;
	}

	@RequestMapping("/discrepancyReports")
	public ModelAndView discrepancyReports(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}
		sDate = UtilityJpa.getStartDate();
		eDate = UtilityJpa.getEndDate();

		List<Discrepancy> discrepancyList = null;

		if (dateRange.getNormalOrSuspense() != null && dateRange.getNormalOrSuspense().equalsIgnoreCase("NORMAL")) {
			discrepancyList = processingRoomService.getDiscrepancyReports(user.getIcmcId(), sDate, eDate, "NORMAL");
		} else if (dateRange.getNormalOrSuspense() != null
				&& dateRange.getNormalOrSuspense().equalsIgnoreCase("SUSPENSE")) {
			discrepancyList = processingRoomService.getDiscrepancyReports(user.getIcmcId(), sDate, eDate, "SUSPENSE");
		}

		else {
			discrepancyList = processingRoomService.getDiscrepancyReports(user.getIcmcId(), sDate, eDate);
		}
		map.put("discrepancyList", discrepancyList);

		return new ModelAndView("/discrepancyReports", map);
	}

	@RequestMapping("/inputOutputReports")
	public ModelAndView inputOutputReports(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");

		ModelMap map = new ModelMap();

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();
		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = (Calendar) dateRange.getFromDate().clone();
		}
		sDate = UtilityJpa.getStartDate();
		eDate = UtilityJpa.getEndDate();

		List<Tuple> machineAllocationList = processingRoomService.getMachineAllocationRecord(user.getIcmcId(), sDate,
				eDate);
		List<Tuple> processList = processingRoomService.getProcessRecord(user.getIcmcId(), sDate, eDate,
				CurrencyType.ISSUABLE);
		List<Tuple> processSoiledList = processingRoomService.getProcessRecord(user.getIcmcId(), sDate, eDate,
				CurrencyType.SOILED);
		List<Tuple> processATMList = processingRoomService.getProcessRecord(user.getIcmcId(), sDate, eDate,
				CurrencyType.ATM);
		List<Tuple> processDiscrepancyList = processingRoomService.getDiscrepancyListForIOReport(user.getIcmcId(),
				sDate, eDate);

		map.put("processList", processList);
		map.put("processSoiledList", processSoiledList);
		map.put("processATMList", processATMList);
		map.put("machineAllocationList", machineAllocationList);
		map.put("processDiscrepancyList", processDiscrepancyList);

		return new ModelAndView("/inputOutputReports", map);
	}

	@RequestMapping("/returnBackToVault")
	public ModelAndView returnBackToVault(HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		List<MachineAllocation> branchBundleReturned = processingRoomService
				.getAggregatedBundleToBeReturnedToVault(user.getIcmcId(), CashSource.BRANCH, sDate, eDate);
		List<MachineAllocation> dsbBundleReturned = processingRoomService
				.getAggregatedBundleToBeReturnedToVault(user.getIcmcId(), CashSource.DSB, sDate, eDate);
		List<MachineAllocation> otherBankBundleReturned = processingRoomService
				.getAggregatedBundleToBeReturnedToVault(user.getIcmcId(), CashSource.OTHERBANK, sDate, eDate);
		List<MachineAllocation> diversionBundleReturned = processingRoomService
				.getAggregatedBundleToBeReturnedToVault(user.getIcmcId(), CashSource.DIVERSION, sDate, eDate);

		map.put("branchBundleReturned", branchBundleReturned);
		map.put("dsbBundleReturned", dsbBundleReturned);
		map.put("otherBankBundleReturned", otherBankBundleReturned);
		map.put("diversionBundleReturned", diversionBundleReturned);
		return new ModelAndView("returnBackToVault", map);
	}

	/*
	 * @RequestMapping("/discrepancyRPCFormat") public ModelAndView
	 * discrepancyRPCFormat(@ModelAttribute("reportDate") DateRange dateRange,
	 * HttpSession session) { //User user = (User)
	 * session.getAttribute("login"); ModelMap map = new ModelMap();
	 * 
	 * Calendar sDate = Calendar.getInstance(); Calendar eDate =
	 * Calendar.getInstance();
	 * 
	 * if(dateRange.getFromDate() != null && dateRange.getToDate() != null){
	 * sDate = dateRange.getFromDate(); eDate = dateRange.getToDate(); }
	 * sDate.set(Calendar.HOUR, 0); sDate.set(Calendar.HOUR_OF_DAY, 0);
	 * sDate.set(Calendar.MINUTE, 0); sDate.set(Calendar.SECOND, 0);
	 * sDate.set(Calendar.MILLISECOND, 0);
	 * 
	 * eDate.set(Calendar.HOUR, 24); eDate.set(Calendar.HOUR_OF_DAY, 23);
	 * eDate.set(Calendar.MINUTE, 59); eDate.set(Calendar.SECOND, 59);
	 * eDate.set(Calendar.MILLISECOND, 999);
	 * 
	 * return new ModelAndView("/discrepancyRPCFormat", map); }
	 */

	@RequestMapping("/rePrintQR")
	public ModelAndView rePrintQR(@RequestParam Long id, Process process) {
		ModelMap model = new ModelMap();
		process = processingRoomService.getRepritRecord(id);
		if (process != null) {
			model.put("user", process);
		}
		return new ModelAndView("rePrintQR", model);
	}

	@RequestMapping("/printingQR")
	public ModelAndView printingQR(@ModelAttribute("user") Process process, HttpSession session) {
		User user = (User) session.getAttribute("login");
		StringBuilder sb = null;
		try {
			File file = new File(prnFilePath);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "";
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();
			for (int i = 0; i < process.getBundle().intValue(); i++) {
				String replacedtext = oldtext.replaceAll("bin", "" + process.getCurrencyType());
				replacedtext = replacedtext.replaceAll("Bin: ", "" + "");
				replacedtext = replacedtext.replaceAll("Branch: ", "" + "");
				replacedtext = replacedtext.replaceAll("Sol ID :", "" + "");
				replacedtext = replacedtext.replaceAll("branch", "" + process.getDenomination());
				replacedtext = replacedtext.replaceAll("solId", "" + process.getBinNumber());
				replacedtext = replacedtext.replaceAll("denom", "" + process.getDenomination());
				replacedtext = replacedtext.replaceAll("bundle", "" + BigDecimal.ONE);

				String formattedTotal = CurrencyFormatter
						.inrFormatter(BigDecimal.valueOf(process.getDenomination() * 1000)).toString();
				replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

				sb = new StringBuilder(replacedtext);
				LOG.info("Processing Room O/P PRN  =" + sb);

				UtilityJpa.PrintToPrinter(sb, user);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return new ModelAndView("redirect:./viewProcess");
	}

	@RequestMapping("/fullValue")
	public ModelAndView fullValue(HttpSession session) {
		Mutilated obj = new Mutilated();
		ModelMap map = new ModelMap();
		// User user = (User)session.getAttribute("login");
		map.put("denominationList", DenominationType.values());
		map.put("user", obj);
		return new ModelAndView("fullValue", map);
	}

	@RequestMapping("/viewMutilatedData")
	public ModelAndView viewMutilatedData(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<Mutilated> list = processingRoomService.getMitulatedFullValue(user.getIcmcId());
		return new ModelAndView("viewMutilatedData", "records", list);
	}

	@RequestMapping("/MutilatedProcess")
	@ResponseBody
	public List<Mutilated> getBinForMutilated(@RequestBody Mutilated mutilated, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		List<Mutilated> mutilatedList = null;
		mutilated.setInsertBy(user.getId());
		mutilated.setUpdateBy(user.getId());
		mutilated.setInsertTime(now);
		mutilated.setUpdateTime(now);
		mutilated.setCurrencyType(CurrencyType.MUTILATED);
		mutilatedList = processingRoomService.processRecordForMutilated(mutilated, user);
		return mutilatedList;
	}

	/*
	 * @RequestMapping("/saveMutilatedFullValue") public ModelAndView
	 * saveMutilatedFullValue(@ModelAttribute("user") Mutilated mutilated,
	 * HttpSession session) { User user = (User) session.getAttribute("login");
	 * Calendar now = Calendar.getInstance();
	 * mutilated.setInsertBy(user.getId()); mutilated.setUpdateBy(user.getId());
	 * mutilated.setInsertTime(now); mutilated.setUpdateTime(now);
	 * mutilated.setCurrencyType(CurrencyType.MUTILATED);
	 * mutilated.setIcmcId(user.getIcmcId());
	 * processingRoomService.insertFullValueMutilated(mutilated); return new
	 * ModelAndView("redirect:./viewMutilatedData");
	 * 
	 * }
	 */
	@RequestMapping(value = "/saveMutilatedFullValue", method = RequestMethod.POST)
	public ModelAndView saveMutilatedFullValue(@RequestParam String deno2000, @RequestParam String deno1000,
			@RequestParam String deno500, @RequestParam String deno200, @RequestParam String deno10,
			@RequestParam String deno100, @RequestParam String deno50, @RequestParam String deno20,
			@RequestParam String deno5, @RequestParam String deno2, @RequestParam String deno1, HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar now = Calendar.getInstance();
		if (!deno2000.equals("")) {
			Mutilated mutilated = new Mutilated();
			mutilated.setInsertBy(user.getId());
			mutilated.setUpdateBy(user.getId());
			mutilated.setInsertTime(now);
			mutilated.setUpdateTime(now);
			mutilated.setCurrencyType(CurrencyType.MUTILATED);
			mutilated.setIcmcId(user.getIcmcId());
			mutilated.setBundle(new BigDecimal(deno2000));
			mutilated.setDenomination(2000);
			mutilated.setOtherStatus(OtherStatus.REQUESTED);
			processingRoomService.insertFullValueMutilated(mutilated);
		}
		if (!deno1000.equals("")) {
			Mutilated mutilated = new Mutilated();
			mutilated.setInsertBy(user.getId());
			mutilated.setUpdateBy(user.getId());
			mutilated.setInsertTime(now);
			mutilated.setUpdateTime(now);
			mutilated.setCurrencyType(CurrencyType.MUTILATED);
			mutilated.setIcmcId(user.getIcmcId());
			mutilated.setBundle(new BigDecimal(deno1000));
			mutilated.setDenomination(1000);
			mutilated.setOtherStatus(OtherStatus.REQUESTED);
			processingRoomService.insertFullValueMutilated(mutilated);
		}
		if (!deno500.equals("")) {
			Mutilated mutilated = new Mutilated();
			mutilated.setInsertBy(user.getId());
			mutilated.setUpdateBy(user.getId());
			mutilated.setInsertTime(now);
			mutilated.setUpdateTime(now);
			mutilated.setCurrencyType(CurrencyType.MUTILATED);
			mutilated.setIcmcId(user.getIcmcId());
			mutilated.setBundle(new BigDecimal(deno500));
			mutilated.setDenomination(500);
			mutilated.setOtherStatus(OtherStatus.REQUESTED);
			processingRoomService.insertFullValueMutilated(mutilated);
		}
		if (!deno200.equals("")) {
			Mutilated mutilated = new Mutilated();
			mutilated.setInsertBy(user.getId());
			mutilated.setUpdateBy(user.getId());
			mutilated.setInsertTime(now);
			mutilated.setUpdateTime(now);
			mutilated.setCurrencyType(CurrencyType.MUTILATED);
			mutilated.setIcmcId(user.getIcmcId());
			mutilated.setBundle(new BigDecimal(deno200));
			mutilated.setDenomination(200);
			mutilated.setOtherStatus(OtherStatus.REQUESTED);
			processingRoomService.insertFullValueMutilated(mutilated);
		}
		if (!deno100.equals("")) {
			Mutilated mutilated = new Mutilated();
			mutilated.setInsertBy(user.getId());
			mutilated.setUpdateBy(user.getId());
			mutilated.setInsertTime(now);
			mutilated.setUpdateTime(now);
			mutilated.setCurrencyType(CurrencyType.MUTILATED);
			mutilated.setIcmcId(user.getIcmcId());
			mutilated.setBundle(new BigDecimal(deno100));
			mutilated.setDenomination(100);
			mutilated.setOtherStatus(OtherStatus.REQUESTED);
			processingRoomService.insertFullValueMutilated(mutilated);
		}
		if (!deno50.equals("")) {
			Mutilated mutilated = new Mutilated();
			mutilated.setInsertBy(user.getId());
			mutilated.setUpdateBy(user.getId());
			mutilated.setInsertTime(now);
			mutilated.setUpdateTime(now);
			mutilated.setCurrencyType(CurrencyType.MUTILATED);
			mutilated.setIcmcId(user.getIcmcId());
			mutilated.setBundle(new BigDecimal(deno50));
			mutilated.setDenomination(50);
			mutilated.setOtherStatus(OtherStatus.REQUESTED);
			processingRoomService.insertFullValueMutilated(mutilated);
		}
		if (!deno20.equals("")) {
			Mutilated mutilated = new Mutilated();
			mutilated.setInsertBy(user.getId());
			mutilated.setUpdateBy(user.getId());
			mutilated.setInsertTime(now);
			mutilated.setUpdateTime(now);
			mutilated.setCurrencyType(CurrencyType.MUTILATED);
			mutilated.setIcmcId(user.getIcmcId());
			mutilated.setBundle(new BigDecimal(deno20));
			mutilated.setDenomination(20);
			mutilated.setOtherStatus(OtherStatus.REQUESTED);
			processingRoomService.insertFullValueMutilated(mutilated);
		}
		if (!deno10.equals("")) {
			Mutilated mutilated = new Mutilated();
			mutilated.setInsertBy(user.getId());
			mutilated.setUpdateBy(user.getId());
			mutilated.setInsertTime(now);
			mutilated.setUpdateTime(now);
			mutilated.setCurrencyType(CurrencyType.MUTILATED);
			mutilated.setIcmcId(user.getIcmcId());
			mutilated.setBundle(new BigDecimal(deno10));
			mutilated.setDenomination(10);
			mutilated.setOtherStatus(OtherStatus.REQUESTED);
			processingRoomService.insertFullValueMutilated(mutilated);
		}
		if (!deno5.equals("")) {
			Mutilated mutilated = new Mutilated();
			mutilated.setInsertBy(user.getId());
			mutilated.setUpdateBy(user.getId());
			mutilated.setInsertTime(now);
			mutilated.setUpdateTime(now);
			mutilated.setCurrencyType(CurrencyType.MUTILATED);
			mutilated.setIcmcId(user.getIcmcId());
			mutilated.setBundle(new BigDecimal(deno5));
			mutilated.setDenomination(5);
			mutilated.setOtherStatus(OtherStatus.REQUESTED);
			processingRoomService.insertFullValueMutilated(mutilated);
		}
		if (!deno2.equals("")) {
			Mutilated mutilated = new Mutilated();
			mutilated.setInsertBy(user.getId());
			mutilated.setUpdateBy(user.getId());
			mutilated.setInsertTime(now);
			mutilated.setUpdateTime(now);
			mutilated.setCurrencyType(CurrencyType.MUTILATED);
			mutilated.setIcmcId(user.getIcmcId());
			mutilated.setBundle(new BigDecimal(deno2));
			mutilated.setDenomination(2);
			mutilated.setOtherStatus(OtherStatus.REQUESTED);
			processingRoomService.insertFullValueMutilated(mutilated);
		}
		if (!deno1.equals("")) {
			Mutilated mutilated = new Mutilated();
			mutilated.setInsertBy(user.getId());
			mutilated.setUpdateBy(user.getId());
			mutilated.setInsertTime(now);
			mutilated.setUpdateTime(now);
			mutilated.setCurrencyType(CurrencyType.MUTILATED);
			mutilated.setIcmcId(user.getIcmcId());
			mutilated.setBundle(new BigDecimal(deno1));
			mutilated.setDenomination(1);
			mutilated.setOtherStatus(OtherStatus.REQUESTED);
			processingRoomService.insertFullValueMutilated(mutilated);
		}
		return new ModelAndView("redirect:./viewMutilatedData");

	}

	@RequestMapping("/mutilatedPayment")
	public ModelAndView mutilatedPayment(HttpSession session) {
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");
		List<Tuple> mutilatedPaymentList = processingRoomService.indentSummaryForMutilated(user.getIcmcId());
		map.put("mutilatedList", mutilatedPaymentList);
		return new ModelAndView("mutilatedPayment", map);
	}

	@RequestMapping("indentRequestForMutilate")
	@ResponseBody
	public MutilatedIndent saveMutilatedindent(@RequestBody MutilatedIndent mutilatedIndent, HttpSession session,
			User user, RedirectAttributes redirectAttributes) {
		user = (User) session.getAttribute("login");

		List<MutilatedIndent> eligibleIndentRequestList = new ArrayList<>();
		List<BinTransaction> binTransactionList = new ArrayList<>();

		List<BinTransaction> txnList = processingRoomService.getBinNumListForMutilatedIndent(
				mutilatedIndent.getDenomination(), mutilatedIndent.getRequestBundle(), user.getIcmcId(),
				mutilatedIndent.getBinCategoryType());

		BigDecimal bundleFromTxnForMutilated = BigDecimal.ZERO;

		for (BinTransaction binTx : txnList) {
			bundleFromTxnForMutilated = bundleFromTxnForMutilated
					.add(UtilityJpa.getSubstarctedBundle(binTx, (UtilityJpa.getPendingBundleRequest(binTx))));
		}

		BigDecimal bundleForRequest = mutilatedIndent.getRequestBundle();

		if (bundleFromTxnForMutilated.compareTo(bundleForRequest) >= 0) {
			binTransactionList.addAll(txnList);

			if (mutilatedIndent.getBinCategoryType() == BinCategoryType.BOX) {
				eligibleIndentRequestList = UtilityJpa.getBoxForMUtilatedIndentRequest(txnList,
						mutilatedIndent.getDenomination(), mutilatedIndent.getRequestBundle(), user, mutilatedIndent);
			} else {
				eligibleIndentRequestList = UtilityJpa.getBinForMutilatedIndentRequest(txnList,
						mutilatedIndent.getDenomination(), mutilatedIndent.getRequestBundle(), user, mutilatedIndent);
			}

			BigDecimal moreBundleNeeded = UtilityJpa
					.checkMoreRequiredBundleNeededForMutilated(eligibleIndentRequestList, bundleForRequest);
			boolean isAllSuccess = false;
			if (moreBundleNeeded.compareTo(BigDecimal.ZERO) == 0) {
				isAllSuccess = processingRoomService
						.insertMutilatedIndentRequestAndUpdateBinTxn(eligibleIndentRequestList, binTransactionList);
				if (!isAllSuccess) {
					throw new RuntimeException("Error while Indent Request Saving");
				}
			}
		}

		return mutilatedIndent;
	}

	@RequestMapping("/acceptMutilatedIndent")
	public ModelAndView acceptMutilated(HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		ModelMap map = new ModelMap();

		List<Mutilated> indentList = processingRoomService.getMutilatedValueDetails(user.getIcmcId(), sDate, eDate);
		map.put("indentList", indentList);
		return new ModelAndView("acceptMutilatedIndent", map);
	}

	@RequestMapping("/updateMutilatedIndentStatus")
	public ModelAndView updateMutilatedFullValueStatus(@RequestParam(value = "id") Long id, HttpSession session) {
		boolean isAllSuccess = false;
		isAllSuccess = processingRoomService.processMutilatedRequest(id);
		if (!isAllSuccess) {
			throw new RuntimeException("Error while process Mutilated Full Value Request");
		}
		return new ModelAndView("welcome");
	}

	@RequestMapping("/keyMovementRegister")
	public ModelAndView keyMovementRegister(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}

		sDate = UtilityJpa.getStartDate();
		eDate = UtilityJpa.getEndDate();

		ModelMap map = new ModelMap();
		List<AssignVaultCustodian> assginVaultCustodianList = processingRoomService
				.getAssignVaultCustodian(user.getIcmcId(), sDate, eDate);
		for (AssignVaultCustodian assignVaultCustodian : assginVaultCustodianList) {
			String userName = processingRoomService.getUserName(assignVaultCustodian.getUserId());
			if (userName != null) {
				assignVaultCustodian.setUserName(userName);
			}
		}
		map.put("records", assginVaultCustodianList);
		return new ModelAndView("/keyMovementRegister", map);
	}

	@RequestMapping("/editDiscrepancy")
	public ModelAndView editDiscrepancy(HttpSession session, HttpServletRequest request) {
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");
		// Long id = Long.parseLong(request.getParameter("id"));
		Long allocationId = Long.parseLong(request.getParameter("allocationId"));
		// Discrepancy discrepancyDataForEdit =
		// processingRoomService.getDescripancyDataForEdit(id,
		// user.getIcmcId());
		DiscrepancyAllocation discrepancyAllocation = processingRoomService
				.getDescripancyAllocationDataForEdit(allocationId, user.getIcmcId());
		// map.put("user", discrepancyDataForEdit);
		List<Integer> machineNumberList = processingRoomService.getMachineNumberList(user.getIcmcId());
		map.put("machineNumberList", machineNumberList);
		map.put("discrepancyAllocation", discrepancyAllocation);
		return new ModelAndView("editDiscrepancy", map);
	}

	@RequestMapping("/updateDiscrepancy")
	public ModelAndView updateDiscrepancy(DiscrepancyAllocation discrepancyAllocation, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		discrepancyAllocation.setUpdateBy(user.getId());
		discrepancyAllocation.setUpdateTime(now);
		processingRoomService.updateDiscrepancy(discrepancyAllocation);
		// redirectAttributes.addFlashAttribute("updateMsg", "ICMC record has
		// been updated successfully");
		String normalOrSuspense = discrepancyAllocation.getNormalOrSuspense();
		if (normalOrSuspense.equalsIgnoreCase("NORMAL")) {
			return new ModelAndView("redirect:./viewDiscrepancy");
		} else
			return new ModelAndView("redirect:./viewSuspenseDiscrepancy");
	}

	@RequestMapping("/editSuspenseDiscrepancy")
	public ModelAndView editSuspenseDiscrepancy(HttpSession session, HttpServletRequest request) {
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");
		Long allocationId = Long.parseLong(request.getParameter("allocationId"));
		DiscrepancyAllocation discrepancyAllocation = processingRoomService
				.getDescripancyAllocationDataForEdit(allocationId, user.getIcmcId());
		map.put("discrepancyAllocation", discrepancyAllocation);
		return new ModelAndView("editSuspenseDiscrepancy", map);
	}

	@RequestMapping("/mutilatednotesSummary")
	public ModelAndView mutilatednotesSummary(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session,
			HttpServletRequest request) {
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}

		sDate = UtilityJpa.getStartDate();
		eDate = UtilityJpa.getEndDate();

		List<Tuple> mutilatedNotesSummary = null;

		if (dateRange.getNormalOrSuspense() != null && dateRange.getNormalOrSuspense().equalsIgnoreCase("NORMAL")) {
			mutilatedNotesSummary = processingRoomService.getMutilatedNotesSummary(user.getIcmcId(), sDate, eDate,
					"NORMAL");

		} else if (dateRange.getNormalOrSuspense() != null
				&& dateRange.getNormalOrSuspense().equalsIgnoreCase("SUSPENSE")) {
			mutilatedNotesSummary = processingRoomService.getMutilatedNotesSummary(user.getIcmcId(), sDate, eDate,
					"SUSPENSE");

		} else {
			mutilatedNotesSummary = processingRoomService.getMutilatedNotesSummary(user.getIcmcId(), sDate, eDate);
		}
		map.put("mutilatedNotesSummary", mutilatedNotesSummary);
		return new ModelAndView("mutilatednotesSummary", map);
	}

	@RequestMapping("/auditorProcessEntry")
	public ModelAndView auditorProcessEntry(HttpSession session) {
		AuditorProcess obj = new AuditorProcess();
		ModelMap map = new ModelMap();
		User user = (User) session.getAttribute("login");
		List<Tuple> pendingBundleList = processingRoomService.getPendingBundleForAuditor(user.getIcmcId());
		map.put("denominationList", DenominationType.values());
		map.put("currencyType", CurrencyType.values());
		map.put("user", obj);
		map.put("summaryList", pendingBundleList);
		return new ModelAndView("auditorProcessEntry", map);
	}

	@RequestMapping(value = "/QRPathProcessAuditor")
	@ResponseBody
	public List<String> QRPathProcessAuditor(@RequestBody AuditorProcess process, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		List<AuditorProcess> processList = null;
		StringBuilder sb = null;
		List<String> prnList = new ArrayList<>();
		StringBuilder sbBinName = new StringBuilder();

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			prnList.add(sbBinName.toString());
			process.setInsertBy(user.getId());
			process.setUpdateBy(user.getId());
			process.setInsertTime(now);
			process.setUpdateTime(now);
			try {
				processList = processingRoomService.processRecordForAuditorIndent(process, user);
			} catch (Exception ex) {
				LOG.info("Error has occred", ex);
				throw ex;
			}
			boolean isAllSuccess = processList != null && processList.size() > 0;
			if (isAllSuccess) {
				for (AuditorProcess proc : processList) {
					sbBinName.append(proc.getBinNumber()).append(",");
					try {
						File file = new File(prnFilePath);
						BufferedReader reader = new BufferedReader(new FileReader(file));
						String line = "", oldtext = "";
						while ((line = reader.readLine()) != null) {
							oldtext += line + "\r\n";
						}
						reader.close();
						for (int i = 0; i < proc.getBundle().intValue(); i++) {
							String replacedtext = oldtext.replaceAll("bin", "" + proc.getCurrencyType());
							replacedtext = replacedtext.replaceAll("Bin: ", "" + "");
							replacedtext = replacedtext.replaceAll("Branch: ", "" + "");
							replacedtext = replacedtext.replaceAll("Sol ID :", "" + "");
							replacedtext = replacedtext.replaceAll("branch", "" + proc.getDenomination());
							replacedtext = replacedtext.replaceAll("solId", "" + proc.getBinNumber());
							replacedtext = replacedtext.replaceAll("denom", "" + proc.getDenomination());
							replacedtext = replacedtext.replaceAll("bundle", "" + BigDecimal.ONE);

							String formattedTotal = CurrencyFormatter
									.inrFormatter(BigDecimal.valueOf(proc.getDenomination() * 1000)).toString();
							replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);

							sb = new StringBuilder(replacedtext);
							prnList.add(sb.toString());
							LOG.info("Processing Room O/P PRN  =" + sb);

							UtilityJpa.PrintToPrinter(sb, user);
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
			prnList.set(0, sbBinName.toString());
		}
		return prnList;
	}

	@RequestMapping(value = "/cancelProcessedData")
	@ResponseBody
	public String cancelProcessedData(@RequestParam(value = "id") long id,
			@RequestParam(value = "bundle") BigDecimal bundleFromUI,
			@RequestParam(value = "denomination") int denomination, @RequestParam(value = "binNumber") String binNumber,
			@RequestParam(value = "type") CurrencyType type, @RequestParam(value = "machineId") long machineId,
			HttpSession session, User user, Process process) {
		user = (User) session.getAttribute("login");
		process.setIcmcId(user.getIcmcId());
		process.setId(id);
		String cancelProcessedDataFromProcessingRoom = null;
		try {
			cancelProcessedDataFromProcessingRoom = processingRoomService.cancelProcessedDataFromProcessingRoom(id,
					bundleFromUI, denomination, binNumber, type, machineId, user, process);
		} catch (Exception e) {
			throw new BaseGuiException(e.getMessage());
		}
		return cancelProcessedDataFromProcessingRoom;
	}

	@RequestMapping("machineDowntimeReport")
	public ModelAndView trainingRegisterReport(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session)
			throws ParseException {

		User user = (User) session.getAttribute("login");
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
			sDate = dateRange.getFromDate();
			eDate = dateRange.getToDate();
		}

		sDate = UtilityJpa.getStartDate();
		eDate = UtilityJpa.getEndDate();

		List<MachineDowntimeUpdation> machineDowntimeList = machineService.getListmachineDownTime(user.getIcmcId(),
				sDate, eDate);
		List<Long> diffList = new ArrayList<Long>();
		ModelMap map = new ModelMap();
		for (MachineDowntimeUpdation mUpdation : machineDowntimeList) {

			long diffDateToFrom = mUpdation.getMachineDownDateTo().getTime()
					- mUpdation.getMachineDownDateFrom().getTime();
			long diffSeconds = diffDateToFrom / 1000 % 60;
			long diffMinutes = diffDateToFrom / (60 * 1000) % 60;
			long diffHours = diffDateToFrom / (60 * 60 * 1000) % 24;
			long diffDays = diffDateToFrom / (24 * 60 * 60 * 1000);
			diffList.add(diffSeconds);
			diffList.add(diffMinutes);
			diffList.add(diffHours);
			diffList.add(diffDays);
			mUpdation.setHours(diffHours);

		}
		map.put("records", machineDowntimeList);
		map.put("diffList", diffList);

		return new ModelAndView("machineDowntimeReport", map);

	}

	@RequestMapping("/suspenseOpeningBalance")
	public ModelAndView suspenseOpeningBalance(HttpSession session) {
		SuspenseOpeningBalance obj = new SuspenseOpeningBalance();
		ModelMap map = new ModelMap();
		map.put("user", obj);
		return new ModelAndView("openingBalanceForSuspense", map);
	}

	@RequestMapping("/saveSuspenseOpeningBalance")
	public ModelAndView addSuspenseOpeningBalance(@ModelAttribute("user") SuspenseOpeningBalance suspenseOpeningBalance,
			HttpSession session) {
		User user = (User) session.getAttribute("login");
		suspenseOpeningBalance.setIcmcId(user.getIcmcId());
		suspenseOpeningBalance.setInsertBy(user.getId());
		suspenseOpeningBalance.setUpdateBy(user.getId());
		suspenseOpeningBalance.setCurrentVersion("TRUE");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		suspenseOpeningBalance.setInsertTime(sDate);
		suspenseOpeningBalance.setUpdateTime(eDate);
		processingRoomService.insertSuspenseOpeningBalance(suspenseOpeningBalance);
		return new ModelAndView("redirect:./viewSuspenseOpeningBalance");
	}

	@RequestMapping("/viewSuspenseOpeningBalance")
	public ModelAndView viewSuspenseOpeningBalance(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<SuspenseOpeningBalance> suspenseOpeningBalanceList = processingRoomService
				.getSuspenseOpeningBalance(user.getIcmcId());
		return new ModelAndView("viewSuspenseOpeningBalance", "records", suspenseOpeningBalanceList);
	}

	@RequestMapping("/suspenseCashRegister")
	public ModelAndView suspanceCashRegister(@ModelAttribute("reportDate") DateRange dateRange, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null) {
			sDate = dateRange.getFromDate();
			// eDate = (Calendar) dateRange.getFromDate().clone();
			eDate = dateRange.getToDate();
		}

		sDate = UtilityJpa.getStartDate();
		eDate = UtilityJpa.getEndDate();

		Calendar pSDate = Calendar.getInstance();

		Calendar pEDate = Calendar.getInstance();

		if (dateRange.getFromDate() != null) {
			pSDate = dateRange.getFromDate();
			pSDate.add(Calendar.DATE, -1);
			// pEDate = (Calendar) dateRange.getFromDate().clone();
			pEDate = dateRange.getToDate();
			pEDate.add(Calendar.DATE, -1);

		} else if (dateRange.getFromDate() == null) {
			pSDate.add(Calendar.DATE, -1);
			pEDate.add(Calendar.DATE, -1);
		}
		pSDate = UtilityJpa.getStartDate();
		pEDate = UtilityJpa.getEndDate();

		Integer closingBalDeno5 = 0;
		Integer closingBalDeno10 = 0;
		Integer closingBalDeno20 = 0;
		Integer closingBalDeno50 = 0;
		Integer closingBalDeno100 = 0;
		Integer closingBalDeno200 = 0;
		Integer closingBalDeno500 = 0;
		Integer closingBalDeno2000 = 0;

		List<SuspenseOpeningBalance> openingBalance = new ArrayList<>();
		if (dateRange.getFromDate() != null) {
			openingBalance = processingRoomService.openingBalanceForSuspenseRegisterPreviousDate(user.getIcmcId(),
					pSDate, pEDate);
		} else {
			openingBalance = processingRoomService.openingBalanceForSuspenseRegister(user.getIcmcId());
		}
		for (SuspenseOpeningBalance opening : openingBalance) {
			if (opening.getDenomination5() != null) {
				closingBalDeno5 = opening.getDenomination5().intValue();
			}
			if (opening.getDenomination10() != null) {
				closingBalDeno10 = opening.getDenomination10().intValue();
			}
			if (opening.getDenomination20() != null) {
				closingBalDeno20 = opening.getDenomination20().intValue();
			}
			if (opening.getDenomination50() != null) {
				closingBalDeno50 = opening.getDenomination50().intValue();
			}
			if (opening.getDenomination100() != null) {
				closingBalDeno100 = opening.getDenomination100().intValue();
			}
			if (opening.getDenomination200() != null) {
				closingBalDeno200 = opening.getDenomination200().intValue();
			}
			if (opening.getDenomination500() != null) {
				closingBalDeno500 = opening.getDenomination500().intValue();
			}
			if (opening.getDenomination2000() != null) {
				closingBalDeno2000 = opening.getDenomination2000().intValue();
			}
		}

		List<Tuple> withdrawalList = processingRoomService.getTotalNotesForWithdrawal(user.getIcmcId(), sDate, eDate);
		List<DiscrepancyAllocation> withdrawalListData = new ArrayList<DiscrepancyAllocation>();
		DiscrepancyAllocation discrepancyAllocation = new DiscrepancyAllocation();
		for (Tuple tuple : withdrawalList) {
			// discrepancyAllocation = new DiscrepancyAllocation();
			if (tuple.get(0, Integer.class).equals(5)) {
				discrepancyAllocation.setDenom5Pieces(tuple.get(1, Integer.class));

				closingBalDeno5 = closingBalDeno5 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				discrepancyAllocation.setDenom10Pieces(tuple.get(1, Integer.class));

				closingBalDeno10 = closingBalDeno10 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				discrepancyAllocation.setDenom20Pieces(tuple.get(1, Integer.class));

				closingBalDeno20 = closingBalDeno20 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				discrepancyAllocation.setDenom50Pieces(tuple.get(1, Integer.class));

				closingBalDeno50 = closingBalDeno50 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				discrepancyAllocation.setDenom100Pieces(tuple.get(1, Integer.class));

				closingBalDeno100 = closingBalDeno100 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				discrepancyAllocation.setDenom200Pieces(tuple.get(1, Integer.class));

				closingBalDeno200 = closingBalDeno200 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				discrepancyAllocation.setDenom500Pieces(tuple.get(1, Integer.class));

				closingBalDeno500 = closingBalDeno500 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(2000)) {
				discrepancyAllocation.setDenom2000Pieces(tuple.get(1, Integer.class));

				closingBalDeno2000 = closingBalDeno2000 - tuple.get(1, Integer.class);
			}

		}
		withdrawalListData.add(discrepancyAllocation);

		List<Tuple> withdrawalPrevious = processingRoomService.getTotalNotesForWithdrawal(user.getIcmcId(), pSDate,
				pEDate);
		List<DiscrepancyAllocation> withdrawalListPre = new ArrayList<DiscrepancyAllocation>();
		DiscrepancyAllocation discrepancyAllocationpre = new DiscrepancyAllocation();
		for (Tuple tuple : withdrawalPrevious) {

			// discrepancyAllocationpre = new DiscrepancyAllocation();
			if (tuple.get(0, Integer.class).equals(5)) {
				discrepancyAllocationpre.setDenom5Pieces(tuple.get(1, Integer.class));

				closingBalDeno5 = closingBalDeno5 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				discrepancyAllocationpre.setDenom10Pieces(tuple.get(1, Integer.class));

				closingBalDeno10 = closingBalDeno10 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				discrepancyAllocationpre.setDenom20Pieces(tuple.get(1, Integer.class));

				closingBalDeno20 = closingBalDeno20 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				discrepancyAllocationpre.setDenom50Pieces(tuple.get(1, Integer.class));

				closingBalDeno50 = closingBalDeno50 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				discrepancyAllocationpre.setDenom100Pieces(tuple.get(1, Integer.class));

				closingBalDeno100 = closingBalDeno100 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				discrepancyAllocationpre.setDenom200Pieces(tuple.get(1, Integer.class));

				closingBalDeno200 = closingBalDeno200 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				discrepancyAllocationpre.setDenom500Pieces(tuple.get(1, Integer.class));

				closingBalDeno500 = closingBalDeno500 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(2000)) {
				discrepancyAllocationpre.setDenom2000Pieces(tuple.get(1, Integer.class));

				closingBalDeno2000 = closingBalDeno2000 + tuple.get(1, Integer.class);
			}

		}

		withdrawalListPre.add(discrepancyAllocationpre);

		List<Tuple> depositList = processingRoomService.geTotalNotesForDeposit(user.getIcmcId(), sDate, eDate);

		List<DiscrepancyAllocation> depositListData = new ArrayList<DiscrepancyAllocation>();
		DiscrepancyAllocation discrepancyAllocationDeposit = new DiscrepancyAllocation();
		for (Tuple tuple : depositList) {

			if (tuple.get(0, Integer.class).equals(5)) {
				discrepancyAllocationDeposit.setDenom5Pieces(tuple.get(1, Integer.class));

				closingBalDeno5 = closingBalDeno5 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				discrepancyAllocationDeposit.setDenom10Pieces(tuple.get(1, Integer.class));

				closingBalDeno10 = closingBalDeno10 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				discrepancyAllocationDeposit.setDenom20Pieces(tuple.get(1, Integer.class));

				closingBalDeno20 = closingBalDeno20 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				discrepancyAllocationDeposit.setDenom50Pieces(tuple.get(1, Integer.class));

				closingBalDeno50 = closingBalDeno50 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				discrepancyAllocationDeposit.setDenom100Pieces(tuple.get(1, Integer.class));

				closingBalDeno100 = closingBalDeno100 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				discrepancyAllocationDeposit.setDenom200Pieces(tuple.get(1, Integer.class));

				closingBalDeno200 = closingBalDeno200 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				discrepancyAllocationDeposit.setDenom500Pieces(tuple.get(1, Integer.class));

				closingBalDeno500 = closingBalDeno500 + tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(2000)) {
				discrepancyAllocationDeposit.setDenom2000Pieces(tuple.get(1, Integer.class));

				closingBalDeno2000 = closingBalDeno2000 + tuple.get(1, Integer.class);
			}

		}

		depositListData.add(discrepancyAllocationDeposit);

		List<Tuple> depositListPrevious = processingRoomService.geTotalNotesForDeposit(user.getIcmcId(), pSDate,
				pEDate);
		List<DiscrepancyAllocation> depositListPre = new ArrayList<DiscrepancyAllocation>();
		DiscrepancyAllocation discAllocationPre = new DiscrepancyAllocation();
		for (Tuple tuple : depositListPrevious) {

			if (tuple.get(0, Integer.class).equals(5)) {
				discAllocationPre.setDenom5Pieces(tuple.get(1, Integer.class));

				closingBalDeno5 = closingBalDeno5 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				discAllocationPre.setDenom10Pieces(tuple.get(1, Integer.class));

				closingBalDeno10 = closingBalDeno10 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				discAllocationPre.setDenom20Pieces(tuple.get(1, Integer.class));

				closingBalDeno20 = closingBalDeno20 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				discAllocationPre.setDenom50Pieces(tuple.get(1, Integer.class));

				closingBalDeno50 = closingBalDeno50 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				discAllocationPre.setDenom100Pieces(tuple.get(1, Integer.class));

				closingBalDeno100 = closingBalDeno100 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				discAllocationPre.setDenom200Pieces(tuple.get(1, Integer.class));

				closingBalDeno200 = closingBalDeno200 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				discAllocationPre.setDenom500Pieces(tuple.get(1, Integer.class));

				closingBalDeno500 = closingBalDeno500 - tuple.get(1, Integer.class);
			}
			if (tuple.get(0, Integer.class).equals(2000)) {
				discAllocationPre.setDenom2000Pieces(tuple.get(1, Integer.class));

				closingBalDeno2000 = closingBalDeno2000 - tuple.get(1, Integer.class);
			}
		}

		depositListPre.add(discAllocationPre);

		Integer totalClosingBalance = closingBalDeno5 * 5 + closingBalDeno10 * 10 + closingBalDeno20 * 20
				+ closingBalDeno50 * 50 + closingBalDeno100 * 100 + closingBalDeno200 * 200 + closingBalDeno500 * 500
				+ closingBalDeno2000 * 2000;

		map.put("openingBalance", openingBalance);
		map.put("withdrawalList", withdrawalListData);
		map.put("withdrawalPrevious", withdrawalListPre);
		map.put("depositList", depositListData);
		map.put("depositListPrevious", depositListPre);
		map.put("userID", user.getId());

		map.put("closingBalDeno5", closingBalDeno5);
		map.put("closingBalDeno10", closingBalDeno10);
		map.put("closingBalDeno20", closingBalDeno20);
		map.put("closingBalDeno50", closingBalDeno50);
		map.put("closingBalDeno100", closingBalDeno100);
		map.put("closingBalDeno200", closingBalDeno200);
		map.put("closingBalDeno500", closingBalDeno500);
		map.put("closingBalDeno2000", closingBalDeno2000);

		map.put("totalClosingBalance", totalClosingBalance);

		return new ModelAndView("/suspenseCashRegister", map);
	}

	@RequestMapping(value = "/generateSuspenseEOD")
	@ResponseBody
	public void generateSuspenseEOD(@RequestBody SuspenseOpeningBalance suspenseOpeningBalance, HttpSession session) {
		// String message = "";
		User user = (User) session.getAttribute("login");
		suspenseOpeningBalance.setIcmcId(user.getIcmcId());
		suspenseOpeningBalance.setInsertBy(user.getId());
		suspenseOpeningBalance.setUpdateBy(user.getId());
		suspenseOpeningBalance.setCurrentVersion("TRUE");
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		suspenseOpeningBalance.setInsertTime(sDate);
		suspenseOpeningBalance.setUpdateTime(eDate);

		processingRoomService.updateCurrentVersionStatus(suspenseOpeningBalance);

		processingRoomService.insertSuspenseOpeningBalance(suspenseOpeningBalance);

	}

	@RequestMapping("/deleteDiscrepancy")
	@ResponseBody
	public String deleteDiscrepancy(@RequestParam(value = "id", required = false) Integer id, HttpSession session) {
		User user = (User) session.getAttribute("login");
		processingRoomService.deleteDiscrepancy(id, user.getIcmcId());
		processingRoomService.deleteDiscrepancywithooutallocation(id, user.getIcmcId());
		return "SUCCESS";
	}

	@RequestMapping(value = "/suspenseCashFromLink", method = RequestMethod.POST)
	public ModelAndView suspenseCashFromLink(@RequestParam(value = "id") Long id,
			@RequestParam(value = "replenishment_2000") BigDecimal replenishment_2000,
			@RequestParam(value = "replenishment_500") BigDecimal replenishment_500,
			@RequestParam(value = "replenishment_200") BigDecimal replenishment_200,
			@RequestParam(value = "replenishment_100") BigDecimal replenishment_100,
			@RequestParam(value = "replenishment_50") BigDecimal replenishment_50,
			@RequestParam(value = "replenishment_20") BigDecimal replenishment_20,
			@RequestParam(value = "replenishment_10") BigDecimal replenishment_10,
			@RequestParam(value = "depletion_2000") BigDecimal depletion_2000,
			@RequestParam(value = "depletion_500") BigDecimal depletion_500,
			@RequestParam(value = "depletion_200") BigDecimal depletion_200,
			@RequestParam(value = "depletion_100") BigDecimal depletion_100,
			@RequestParam(value = "depletion_50") BigDecimal depletion_50,
			@RequestParam(value = "depletion_20") BigDecimal depletion_20,
			@RequestParam(value = "depletion_10") BigDecimal depletion_10,
			@RequestParam(value = "srNumber") String srNumber, HttpSession session) {
		User user = (User) session.getAttribute("login");
		Calendar nDate = Calendar.getInstance();
		Calendar nsDate = Calendar.getInstance();
		Calendar neDate = Calendar.getInstance();

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		nsDate.add(Calendar.DATE, 1);
		nsDate.set(Calendar.HOUR, 0);
		nsDate.set(Calendar.HOUR_OF_DAY, 0);
		nsDate.set(Calendar.MINUTE, 0);
		nsDate.set(Calendar.SECOND, 0);
		nsDate.set(Calendar.MILLISECOND, 0);

		neDate.add(Calendar.DATE, 1);
		neDate.set(Calendar.HOUR_OF_DAY, 23);
		neDate.set(Calendar.MINUTE, 59);
		neDate.set(Calendar.SECOND, 59);
		neDate.set(Calendar.MILLISECOND, 999);

		nDate.add(Calendar.DATE, 1);
		nDate.set(Calendar.HOUR, 0);
		nDate.set(Calendar.HOUR_OF_DAY, 0);
		nDate.set(Calendar.MINUTE, 0);
		nDate.set(Calendar.SECOND, 1);
		nDate.set(Calendar.MILLISECOND, 0);

		List<SuspenseOpeningBalance> openingBalance = new ArrayList<>();
		openingBalance = processingRoomService.openingBalanceForSuspenseRegisterPreviousDate(user.getIcmcId(), sDate,
				eDate);

		BigDecimal openingBalanceOfDenomination_10 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_20 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_50 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_100 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_200 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_500 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_2000 = BigDecimal.ZERO;

		BigDecimal openingBalanceOfDenominationAdd_10 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_20 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_50 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_100 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_200 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_500 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_2000 = BigDecimal.ZERO;

		BigDecimal openingBalanceOfDenominationSub_10 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_20 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_50 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_100 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_200 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_500 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_2000 = BigDecimal.ZERO;

		// initialize denomination of all type
		BigDecimal denomination10 = BigDecimal.ZERO;
		BigDecimal denomination20 = BigDecimal.ZERO;
		BigDecimal denomination50 = BigDecimal.ZERO;
		BigDecimal denomination100 = BigDecimal.ZERO;
		BigDecimal denomination200 = BigDecimal.ZERO;
		BigDecimal denomination500 = BigDecimal.ZERO;
		BigDecimal denomination2000 = BigDecimal.ZERO;

		// initialize deposit of all type
		BigDecimal deposit_10 = BigDecimal.ZERO;
		BigDecimal deposit_20 = BigDecimal.ZERO;
		BigDecimal deposit_50 = BigDecimal.ZERO;
		BigDecimal deposit_100 = BigDecimal.ZERO;
		BigDecimal deposit_200 = BigDecimal.ZERO;
		BigDecimal deposit_500 = BigDecimal.ZERO;
		BigDecimal deposit_2000 = BigDecimal.ZERO;

		// Initialize withdrawal of all type
		BigDecimal withdrawal_10 = BigDecimal.ZERO;
		BigDecimal withdrawal_20 = BigDecimal.ZERO;
		BigDecimal withdrawal_50 = BigDecimal.ZERO;
		BigDecimal withdrawal_100 = BigDecimal.ZERO;
		BigDecimal withdrawal_200 = BigDecimal.ZERO;
		BigDecimal withdrawal_500 = BigDecimal.ZERO;
		BigDecimal withdrawal_2000 = BigDecimal.ZERO;

		// Initialize replenishment of all type
		if (replenishment_10 == null) {
			replenishment_10 = BigDecimal.ZERO;
		}
		if (replenishment_20 == null) {
			replenishment_20 = BigDecimal.ZERO;
		}
		if (replenishment_50 == null) {
			replenishment_50 = BigDecimal.ZERO;
		}
		if (replenishment_100 == null) {
			replenishment_100 = BigDecimal.ZERO;
		}
		if (replenishment_200 == null) {
			replenishment_200 = BigDecimal.ZERO;
		}
		if (replenishment_500 == null) {
			replenishment_500 = BigDecimal.ZERO;
		}
		if (replenishment_2000 == null) {
			replenishment_2000 = BigDecimal.ZERO;
		}

		// Initialize withdrawal of all type
		if (depletion_10 == null) {
			depletion_10 = BigDecimal.ZERO;
		}
		if (depletion_20 == null) {
			depletion_20 = BigDecimal.ZERO;
		}
		if (depletion_50 == null) {
			depletion_50 = BigDecimal.ZERO;
		}
		if (depletion_100 == null) {
			depletion_100 = BigDecimal.ZERO;
		}
		if (depletion_200 == null) {
			depletion_200 = BigDecimal.ZERO;
		}
		if (depletion_500 == null) {
			depletion_500 = BigDecimal.ZERO;
		}
		if (depletion_2000 == null) {
			depletion_2000 = BigDecimal.ZERO;
		}

		for (SuspenseOpeningBalance opening : openingBalance) {
			// validating of denomination of all type
			if (opening.getDenomination10() != null) {
				denomination10 = opening.getDenomination10();
			}
			if (opening.getDenomination20() != null) {
				denomination20 = opening.getDenomination20();
			}
			if (opening.getDenomination50() != null) {
				denomination50 = opening.getDenomination50();
			}
			if (opening.getDenomination100() != null) {
				denomination100 = opening.getDenomination100();
			}
			if (opening.getDenomination200() != null) {
				denomination200 = opening.getDenomination200();
			}
			if (opening.getDenomination500() != null) {
				denomination500 = opening.getDenomination500();
			}
			if (opening.getDenomination2000() != null) {
				denomination2000 = opening.getDenomination2000();
			}

			// validating of deposit of all type
			if (opening.getDeposit_10() != null) {
				deposit_10 = opening.getDeposit_10();
			}
			if (opening.getDeposit_20() != null) {
				deposit_20 = opening.getDeposit_20();
			}
			if (opening.getDeposit_50() != null) {
				deposit_50 = opening.getDeposit_50();
			}
			if (opening.getDeposit_100() != null) {
				deposit_100 = opening.getDeposit_100();
			}
			if (opening.getDeposit_200() != null) {
				deposit_200 = opening.getDeposit_200();
			}
			if (opening.getDeposit_500() != null) {
				deposit_500 = opening.getDeposit_500();
			}
			if (opening.getDeposit_2000() != null) {
				deposit_2000 = opening.getDeposit_2000();
			}

			// validating the withdrawal of all type
			if (opening.getWithdrawal_10() != null) {
				withdrawal_10 = opening.getWithdrawal_10();
			}
			if (opening.getWithdrawal_20() != null) {
				withdrawal_20 = opening.getWithdrawal_20();
			}
			if (opening.getWithdrawal_50() != null) {
				withdrawal_50 = opening.getWithdrawal_50();
			}
			if (opening.getWithdrawal_100() != null) {
				withdrawal_100 = opening.getWithdrawal_100();
			}
			if (opening.getWithdrawal_200() != null) {
				withdrawal_200 = opening.getWithdrawal_200();
			}
			if (opening.getWithdrawal_500() != null) {
				withdrawal_500 = opening.getWithdrawal_500();
			}
			if (opening.getWithdrawal_2000() != null) {
				withdrawal_2000 = opening.getWithdrawal_2000();
			}
		}

		// opening balance of denomination 10 of next day
		openingBalanceOfDenominationAdd_10 = (denomination10.add(deposit_10.add(replenishment_10)));
		openingBalanceOfDenominationSub_10 = (withdrawal_10.add(depletion_10));
		openingBalanceOfDenomination_10 = (openingBalanceOfDenominationAdd_10
				.subtract(openingBalanceOfDenominationSub_10));

		// opening balance of denomination 20 of next day
		openingBalanceOfDenominationAdd_20 = (denomination20.add(deposit_20.add(replenishment_20)));
		openingBalanceOfDenominationSub_20 = (withdrawal_20.add(depletion_20));
		openingBalanceOfDenomination_20 = (openingBalanceOfDenominationAdd_20
				.subtract(openingBalanceOfDenominationSub_20));

		// opening balance of denomination 50 of next day
		openingBalanceOfDenominationAdd_50 = (denomination50.add(deposit_50.add(replenishment_50)));
		openingBalanceOfDenominationSub_50 = (withdrawal_50.add(depletion_50));
		openingBalanceOfDenomination_50 = (openingBalanceOfDenominationAdd_50
				.subtract(openingBalanceOfDenominationSub_50));

		// opening balance of denomination 50 of next day
		openingBalanceOfDenominationAdd_100 = (denomination100.add(deposit_100.add(replenishment_100)));
		openingBalanceOfDenominationSub_100 = (withdrawal_100.add(depletion_100));
		openingBalanceOfDenomination_100 = (openingBalanceOfDenominationAdd_100
				.subtract(openingBalanceOfDenominationSub_100));

		// opening balance of denomination 50 of next day
		openingBalanceOfDenominationAdd_200 = (denomination200.add(deposit_200.add(replenishment_200)));
		openingBalanceOfDenominationSub_200 = (withdrawal_200.add(depletion_200));
		openingBalanceOfDenomination_200 = (openingBalanceOfDenominationAdd_200
				.subtract(openingBalanceOfDenominationSub_200));

		// opening balance of denomination 50 of next day
		openingBalanceOfDenominationAdd_500 = (denomination500.add(deposit_500.add(replenishment_500)));
		openingBalanceOfDenominationSub_500 = (withdrawal_500.add(depletion_500));
		openingBalanceOfDenomination_500 = (openingBalanceOfDenominationAdd_500
				.subtract(openingBalanceOfDenominationSub_500));

		// opening balance of denomination 50 of next day
		openingBalanceOfDenominationAdd_2000 = (denomination2000.add(deposit_2000.add(replenishment_2000)));
		openingBalanceOfDenominationSub_2000 = (withdrawal_2000.add(depletion_2000));
		openingBalanceOfDenomination_2000 = (openingBalanceOfDenominationAdd_2000
				.subtract(openingBalanceOfDenominationSub_2000));

		SuspenseOpeningBalance suspenseOpeningBalance = new SuspenseOpeningBalance();
		suspenseOpeningBalance.setInsertTime(nDate);
		suspenseOpeningBalance.setIcmcId(user.getIcmcId());
		suspenseOpeningBalance.setInsertBy(user.getName());
		suspenseOpeningBalance.setDenomination10(openingBalanceOfDenomination_10);
		suspenseOpeningBalance.setDenomination20(openingBalanceOfDenomination_20);
		suspenseOpeningBalance.setDenomination50(openingBalanceOfDenomination_50);
		suspenseOpeningBalance.setDenomination100(openingBalanceOfDenomination_100);
		suspenseOpeningBalance.setDenomination200(openingBalanceOfDenomination_200);
		suspenseOpeningBalance.setDenomination500(openingBalanceOfDenomination_500);
		suspenseOpeningBalance.setDenomination2000(openingBalanceOfDenomination_2000);

		List<SuspenseOpeningBalance> openingBalanceNextDay = new ArrayList<>();

		openingBalanceNextDay = processingRoomService.openingBalanceForSuspenseRegisterPreviousDate(user.getIcmcId(),
				nsDate, neDate);

		synchronized (icmcService.getSynchronizedIcmc(user)) {

			processingRoomService.updateSuspenseBalanceFromLink(id, replenishment_2000, replenishment_500,
					replenishment_200, replenishment_100, replenishment_50, replenishment_20, replenishment_10,
					depletion_2000, depletion_500, depletion_200, depletion_100, depletion_50, depletion_20,
					depletion_10, srNumber);

			long nextDayId = 0l;
			for (SuspenseOpeningBalance openingBalanceNext : openingBalanceNextDay) {
				if (openingBalanceNext.getId() != null) {
					nextDayId = openingBalanceNext.getId();
				}
			}

			if (nextDayId == 0l) {
				processingRoomService.insertSuspenseOpeningBalanceLink(suspenseOpeningBalance);
			} else {
				processingRoomService.InsertByDAteSuspeseOpeningBalance(nextDayId, user.getIcmcId(),
						openingBalanceOfDenomination_10, openingBalanceOfDenomination_20,
						openingBalanceOfDenomination_50, openingBalanceOfDenomination_100,
						openingBalanceOfDenomination_200, openingBalanceOfDenomination_500,
						openingBalanceOfDenomination_2000);
			}
		}

		return new ModelAndView("redirect:./suspenseCashDetails");
	}

	@RequestMapping(value = "/suspenseCashDetailFromIcmc", method = RequestMethod.POST)
	public ModelAndView suspenseCashDetailFromIcmc(@RequestParam(value = "id") Long id,
			@RequestParam(value = "withdrawal_2000") BigDecimal withdrawal_2000,
			@RequestParam(value = "withdrawal_500") BigDecimal withdrawal_500,
			@RequestParam(value = "withdrawal_200") BigDecimal withdrawal_200,
			@RequestParam(value = "withdrawal_100") BigDecimal withdrawal_100,
			@RequestParam(value = "withdrawal_50") BigDecimal withdrawal_50,
			@RequestParam(value = "withdrawal_20") BigDecimal withdrawal_20,
			@RequestParam(value = "withdrawal_10") BigDecimal withdrawal_10,
			@RequestParam(value = "deposit_2000") BigDecimal deposit_2000,
			@RequestParam(value = "deposit_500") BigDecimal deposit_500,
			@RequestParam(value = "deposit_200") BigDecimal deposit_200,
			@RequestParam(value = "deposit_100") BigDecimal deposit_100,
			@RequestParam(value = "deposit_50") BigDecimal deposit_50,
			@RequestParam(value = "deposit_20") BigDecimal deposit_20,
			@RequestParam(value = "deposit_10") BigDecimal deposit_10, HttpSession session) {
		User user = (User) session.getAttribute("login");

		synchronized (icmcService.getSynchronizedIcmc(user)) {
			processingRoomService.updateSuspenseBalance(id, deposit_2000, deposit_500, deposit_200, deposit_100,
					deposit_50, deposit_20, deposit_10, withdrawal_2000, withdrawal_500, withdrawal_200, withdrawal_100,
					withdrawal_50, withdrawal_20, withdrawal_10);
		}

		return new ModelAndView("redirect:./suspenseCashDetails");
	}

	@RequestMapping(value = "/suspenseCashDetails")
	public ModelAndView suspenseCashDetails(HttpSession session) {
		User user = (User) session.getAttribute("login");

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();

		Integer closingBalDeno10 = 0;
		Integer closingBalDeno20 = 0;
		Integer closingBalDeno50 = 0;
		Integer closingBalDeno100 = 0;
		Integer closingBalDeno200 = 0;
		Integer closingBalDeno500 = 0;
		Integer closingBalDeno2000 = 0;

		// initialize denomination of all type
		BigDecimal denomination10 = BigDecimal.ZERO;
		BigDecimal denomination20 = BigDecimal.ZERO;
		BigDecimal denomination50 = BigDecimal.ZERO;
		BigDecimal denomination100 = BigDecimal.ZERO;
		BigDecimal denomination200 = BigDecimal.ZERO;
		BigDecimal denomination500 = BigDecimal.ZERO;
		BigDecimal denomination2000 = BigDecimal.ZERO;

		// initialize deposit of all type
		BigDecimal deposit_10 = BigDecimal.ZERO;
		BigDecimal deposit_20 = BigDecimal.ZERO;
		BigDecimal deposit_50 = BigDecimal.ZERO;
		BigDecimal deposit_100 = BigDecimal.ZERO;
		BigDecimal deposit_200 = BigDecimal.ZERO;
		BigDecimal deposit_500 = BigDecimal.ZERO;
		BigDecimal deposit_2000 = BigDecimal.ZERO;

		// Initialize withdrawal of all type
		BigDecimal withdrawal_10 = BigDecimal.ZERO;
		BigDecimal withdrawal_20 = BigDecimal.ZERO;
		BigDecimal withdrawal_50 = BigDecimal.ZERO;
		BigDecimal withdrawal_100 = BigDecimal.ZERO;
		BigDecimal withdrawal_200 = BigDecimal.ZERO;
		BigDecimal withdrawal_500 = BigDecimal.ZERO;
		BigDecimal withdrawal_2000 = BigDecimal.ZERO;

		// initialize replenishment of all type
		BigDecimal replenishment_10 = BigDecimal.ZERO;
		BigDecimal replenishment_20 = BigDecimal.ZERO;
		BigDecimal replenishment_50 = BigDecimal.ZERO;
		BigDecimal replenishment_100 = BigDecimal.ZERO;
		BigDecimal replenishment_200 = BigDecimal.ZERO;
		BigDecimal replenishment_500 = BigDecimal.ZERO;
		BigDecimal replenishment_2000 = BigDecimal.ZERO;

		// initialize depletion of all type
		BigDecimal depletion_10 = BigDecimal.ZERO;
		BigDecimal depletion_20 = BigDecimal.ZERO;
		BigDecimal depletion_50 = BigDecimal.ZERO;
		BigDecimal depletion_100 = BigDecimal.ZERO;
		BigDecimal depletion_200 = BigDecimal.ZERO;
		BigDecimal depletion_500 = BigDecimal.ZERO;
		BigDecimal depletion_2000 = BigDecimal.ZERO;

		List<SuspenseOpeningBalance> openingBalance1 = new ArrayList<>();
		openingBalance1 = processingRoomService.openingBalanceForSuspenseRegisterPreviousDate(user.getIcmcId(), sDate,
				eDate);

		List<SuspenseOpeningBalance> openingBalanceDesc = new ArrayList<>();
		long previousId = 0l;
		for (SuspenseOpeningBalance opening : openingBalance1) {
			if (opening.getId() != null) {
				previousId = opening.getId();
			}
		}

		if (previousId == 0l) {
			openingBalanceDesc = processingRoomService
					.openingBalanceForSuspenseRegisterPreviousDateByDesc(user.getIcmcId());
		}

		for (SuspenseOpeningBalance openingDesc : openingBalanceDesc) {
			// Getting the value of denomination
			if (openingDesc.getDenomination10() != null) {
				denomination10 = openingDesc.getDenomination10();
			}
			if (openingDesc.getDenomination20() != null) {
				denomination20 = openingDesc.getDenomination20();
			}
			if (openingDesc.getDenomination50() != null) {
				denomination50 = openingDesc.getDenomination50();
			}
			if (openingDesc.getDenomination100() != null) {
				denomination100 = openingDesc.getDenomination100();
			}
			if (openingDesc.getDenomination200() != null) {
				denomination200 = openingDesc.getDenomination200();
			}
			if (openingDesc.getDenomination500() != null) {
				denomination500 = openingDesc.getDenomination500();
			}
			if (openingDesc.getDenomination2000() != null) {
				denomination2000 = openingDesc.getDenomination2000();
			}

			// getting the value of deposit
			if (openingDesc.getDeposit_10() != null) {
				deposit_10 = openingDesc.getDeposit_10();
			}
			if (openingDesc.getDeposit_20() != null) {
				deposit_20 = openingDesc.getDeposit_20();
			}
			if (openingDesc.getDeposit_50() != null) {
				deposit_50 = openingDesc.getDeposit_50();
			}
			if (openingDesc.getDeposit_100() != null) {
				deposit_100 = openingDesc.getDeposit_100();
			}
			if (openingDesc.getDeposit_200() != null) {
				deposit_200 = openingDesc.getDeposit_200();
			}
			if (openingDesc.getDeposit_500() != null) {
				deposit_500 = openingDesc.getDeposit_500();
			}
			if (openingDesc.getDeposit_2000() != null) {
				deposit_2000 = openingDesc.getDeposit_2000();
			}

			// getting the value of withdrawal
			if (openingDesc.getWithdrawal_10() != null) {
				withdrawal_10 = openingDesc.getWithdrawal_10();
			}
			if (openingDesc.getWithdrawal_20() != null) {
				withdrawal_20 = openingDesc.getWithdrawal_20();
			}
			if (openingDesc.getWithdrawal_50() != null) {
				withdrawal_50 = openingDesc.getWithdrawal_50();
			}
			if (openingDesc.getWithdrawal_100() != null) {
				withdrawal_100 = openingDesc.getWithdrawal_100();
			}
			if (openingDesc.getWithdrawal_200() != null) {
				withdrawal_200 = openingDesc.getWithdrawal_200();
			}
			if (openingDesc.getWithdrawal_500() != null) {
				withdrawal_500 = openingDesc.getWithdrawal_500();
			}
			if (openingDesc.getWithdrawal_2000() != null) {
				withdrawal_2000 = openingDesc.getWithdrawal_2000();
			}

			// getting the value of replenishment
			if (openingDesc.getReplenishment_10() != null) {
				replenishment_10 = openingDesc.getReplenishment_10();
			}
			if (openingDesc.getReplenishment_20() != null) {
				replenishment_20 = openingDesc.getReplenishment_20();
			}
			if (openingDesc.getReplenishment_50() != null) {
				replenishment_50 = openingDesc.getReplenishment_50();
			}
			if (openingDesc.getReplenishment_100() != null) {
				replenishment_100 = openingDesc.getReplenishment_100();
			}
			if (openingDesc.getReplenishment_200() != null) {
				replenishment_200 = openingDesc.getReplenishment_200();
			}
			if (openingDesc.getReplenishment_500() != null) {
				replenishment_500 = openingDesc.getReplenishment_500();
			}
			if (openingDesc.getReplenishment_2000() != null) {
				replenishment_2000 = openingDesc.getReplenishment_2000();
			}

			// getting the value of depletion
			if (openingDesc.getDepletion_10() != null) {
				depletion_10 = openingDesc.getDepletion_10();
			}
			if (openingDesc.getDepletion_20() != null) {
				depletion_20 = openingDesc.getDepletion_20();
			}
			if (openingDesc.getDepletion_50() != null) {
				depletion_50 = openingDesc.getDepletion_50();
			}
			if (openingDesc.getDepletion_100() != null) {
				depletion_100 = openingDesc.getDepletion_100();
			}
			if (openingDesc.getDepletion_200() != null) {
				depletion_200 = openingDesc.getDepletion_200();
			}
			if (openingDesc.getDepletion_500() != null) {
				depletion_500 = openingDesc.getDepletion_500();
			}
			if (openingDesc.getDepletion_2000() != null) {
				depletion_2000 = openingDesc.getDepletion_2000();
			}

		}

		BigDecimal openingBalanceOfDenomination_10 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_20 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_50 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_100 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_200 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_500 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenomination_2000 = BigDecimal.ZERO;

		BigDecimal openingBalanceOfDenominationAdd_10 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_20 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_50 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_100 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_200 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_500 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationAdd_2000 = BigDecimal.ZERO;

		BigDecimal openingBalanceOfDenominationSub_10 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_20 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_50 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_100 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_200 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_500 = BigDecimal.ZERO;
		BigDecimal openingBalanceOfDenominationSub_2000 = BigDecimal.ZERO;

		// opening balance of denomination 10 of next day
		openingBalanceOfDenominationAdd_10 = (denomination10.add(deposit_10.add(replenishment_10)));
		openingBalanceOfDenominationSub_10 = (withdrawal_10.add(depletion_10));
		openingBalanceOfDenomination_10 = (openingBalanceOfDenominationAdd_10
				.subtract(openingBalanceOfDenominationSub_10));

		// opening balance of denomination 20 of next day
		openingBalanceOfDenominationAdd_20 = (denomination20.add(deposit_20.add(replenishment_20)));
		openingBalanceOfDenominationSub_20 = (withdrawal_20.add(depletion_20));
		openingBalanceOfDenomination_20 = (openingBalanceOfDenominationAdd_20
				.subtract(openingBalanceOfDenominationSub_20));

		// opening balance of denomination 50 of next day
		openingBalanceOfDenominationAdd_50 = (denomination50.add(deposit_50.add(replenishment_50)));
		openingBalanceOfDenominationSub_50 = (withdrawal_50.add(depletion_50));
		openingBalanceOfDenomination_50 = (openingBalanceOfDenominationAdd_50
				.subtract(openingBalanceOfDenominationSub_50));

		// opening balance of denomination 50 of next day
		openingBalanceOfDenominationAdd_100 = (denomination100.add(deposit_100.add(replenishment_100)));
		openingBalanceOfDenominationSub_100 = (withdrawal_100.add(depletion_100));
		openingBalanceOfDenomination_100 = (openingBalanceOfDenominationAdd_100
				.subtract(openingBalanceOfDenominationSub_100));

		// opening balance of denomination 50 of next day
		openingBalanceOfDenominationAdd_200 = (denomination200.add(deposit_200.add(replenishment_200)));
		openingBalanceOfDenominationSub_200 = (withdrawal_200.add(depletion_200));
		openingBalanceOfDenomination_200 = (openingBalanceOfDenominationAdd_200
				.subtract(openingBalanceOfDenominationSub_200));

		// opening balance of denomination 50 of next day
		openingBalanceOfDenominationAdd_500 = (denomination500.add(deposit_500.add(replenishment_500)));
		openingBalanceOfDenominationSub_500 = (withdrawal_500.add(depletion_500));
		openingBalanceOfDenomination_500 = (openingBalanceOfDenominationAdd_500
				.subtract(openingBalanceOfDenominationSub_500));

		// opening balance of denomination 50 of next day
		openingBalanceOfDenominationAdd_2000 = (denomination2000.add(deposit_2000.add(replenishment_2000)));
		openingBalanceOfDenominationSub_2000 = (withdrawal_2000.add(depletion_2000));
		openingBalanceOfDenomination_2000 = (openingBalanceOfDenominationAdd_2000
				.subtract(openingBalanceOfDenominationSub_2000));

		SuspenseOpeningBalance suspenseOpeningBalance = new SuspenseOpeningBalance();

		suspenseOpeningBalance.setIcmcId(user.getIcmcId());
		suspenseOpeningBalance.setInsertBy(user.getId());
		suspenseOpeningBalance.setUpdateBy(user.getId());
		suspenseOpeningBalance.setCurrentVersion("TRUE");
		suspenseOpeningBalance.setInsertTime(sDate);
		suspenseOpeningBalance.setUpdateTime(eDate);
		suspenseOpeningBalance.setDenomination10(openingBalanceOfDenomination_10);
		suspenseOpeningBalance.setDenomination20(openingBalanceOfDenomination_20);
		suspenseOpeningBalance.setDenomination50(openingBalanceOfDenomination_50);
		suspenseOpeningBalance.setDenomination100(openingBalanceOfDenomination_100);
		suspenseOpeningBalance.setDenomination200(openingBalanceOfDenomination_200);
		suspenseOpeningBalance.setDenomination500(openingBalanceOfDenomination_500);
		suspenseOpeningBalance.setDenomination2000(openingBalanceOfDenomination_2000);

		if (previousId == 0l) {
			processingRoomService.insertSuspenseOpeningBalance(suspenseOpeningBalance);
		}

		List<SuspenseOpeningBalance> openingBalance = new ArrayList<>();
		openingBalance = processingRoomService.openingBalanceForSuspenseRegisterPreviousDate(user.getIcmcId(), sDate,
				eDate);

		ModelMap map = new ModelMap();

		map.put("openingBalance", openingBalance);
		map.put("closingBalDeno10", closingBalDeno10);
		map.put("closingBalDeno20", closingBalDeno20);
		map.put("closingBalDeno50", closingBalDeno50);
		map.put("closingBalDeno100", closingBalDeno100);
		map.put("closingBalDeno200", closingBalDeno200);
		map.put("closingBalDeno500", closingBalDeno500);
		map.put("closingBalDeno2000", closingBalDeno2000);
		return new ModelAndView("/suspenseCashDetails", map);
	}

}
