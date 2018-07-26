/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chest.currency.entity.model.AccountDetail;
import com.chest.currency.entity.model.BinCapacityDenomination;
import com.chest.currency.entity.model.DelegateRight;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.IcmcPrinter;
import com.chest.currency.entity.model.Machine;
import com.chest.currency.entity.model.MachineCompany;
import com.chest.currency.entity.model.MachineModel;
import com.chest.currency.entity.model.Role;
import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DebitOrCredit;
import com.chest.currency.enums.DenominationType;
import com.chest.currency.enums.Status;
import com.chest.currency.enums.VaultSize;
import com.chest.currency.enums.Zone;
import com.chest.currency.service.UserAdministrationService;
import com.chest.currency.util.HtmlUtils;
import com.chest.currency.viewBean.UserAdministration;

@Controller
public class UserAdministrationController {

	private static final Logger LOG = LoggerFactory.getLogger(UserAdministrationController.class);

	@Autowired
	UserAdministrationService userAdministrationService;

	PasswordEncoder encoder = new BCryptPasswordEncoder();

	@RequestMapping("/viewUserAuditTrail")
	public ModelAndView viewUserAuditTrail() {
		UserAdministration obj = new UserAdministration();
		return new ModelAndView("viewUserAuditTrail", "user", obj);
	}

	@RequestMapping("/viewAccountDetails")
	public ModelAndView viewAccountDetails() {
		List<AccountDetail> accountDetailsList = new ArrayList<AccountDetail>();
		accountDetailsList = userAdministrationService.getAccountDetailsList();
		return new ModelAndView("viewAccountDetails", "records", accountDetailsList);
	}

	@RequestMapping("/addAccountDetails")
	public ModelAndView AccountDetailsForm() {
		ModelMap map = new ModelMap();
		AccountDetail obj = new AccountDetail();
		map.put("debitOrCreditList", DebitOrCredit.values());
		map.put("user", obj);
		return new ModelAndView("addAccountDetail", map);
	}

	@RequestMapping("/saveAccountDetails")
	public ModelAndView saveAccountDetails(@ModelAttribute("user") AccountDetail accountDetail, HttpSession session) {
		User userTemp = (User) session.getAttribute("login");
		accountDetail.setInsertBy(userTemp.getId());
		accountDetail.setUpdateBy(userTemp.getId());
		Calendar now = Calendar.getInstance();
		accountDetail.setInsertTime(now);
		accountDetail.setUpdateTime(now);
		userAdministrationService.saveAccountDetails(accountDetail);
		return new ModelAndView("redirect:./viewAccountDetails");
	}

	@RequestMapping(value = "/viewAll", method = RequestMethod.GET)
	public ModelAndView viewAll() {
		List<User> list = new ArrayList<User>();
		list = userAdministrationService.getUserList();
		return new ModelAndView("viewAll", "records", list);
	}

	@RequestMapping("/addUser")
	public ModelAndView showUserForm() {
		ModelMap map = new ModelMap();
		User obj = new User();
		List<Role> roleList = userAdministrationService.getAllRole();
		map.put("rolesList", roleList);
		map.put("zoneList", Zone.values());
		map.put("user", obj);
		return new ModelAndView("addUSER", map);
	}

	@RequestMapping(value = "/getRegion")
	@ResponseBody
	public List<String> getRegion(@RequestParam(value = "zone") String zone, ZoneMaster zm) {
		zm.setZone(Zone.valueOf(zone));
		List<String> regionList = userAdministrationService.getRegion(zm);
		return regionList;
	}

	@RequestMapping(value = "/getRoleType")
	@ResponseBody
	public String getRoleType(@RequestParam(value = "roleType") String roleType) {
		String regionList = userAdministrationService.getRoleType(roleType);
		return regionList;
	}

	@RequestMapping(value = "/getIcmcList")
	@ResponseBody
	public List<String> getIcmcList(@RequestParam(value = "region") String region) {
		List<String> icmcList = userAdministrationService.getIcmc(region);
		return icmcList;
	}

	public boolean isValidUserData(User user) {
		if (user.getId() == null || user.getName() == null || user.getEmail() == null || user.getRoleId() == null) {
			return false;
		}
		return true;
	}

	@RequestMapping("/saveUser")
	public ModelAndView saveUser(@ModelAttribute("user") User user, HttpSession session, ModelMap model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User userTemp = (User) session.getAttribute("login");

		//Special Character Validation
		
		boolean userId = HtmlUtils.isHtml(user.getId());
		boolean userName = HtmlUtils.isHtml(user.getName());
		if (userId == false || userName == false) {
			redirectAttributes.addFlashAttribute("successMsg", "Error : Special Character Not Allowed");
			return new ModelAndView("redirect:./viewAll");
		}
		
		//End of Special Character Validation
		
		if (!isValidUserData(user)) {
			return new ModelAndView("redirect:./addUser");
		}

		User dbUser = userAdministrationService.isValidUser(user.getId());

		if (dbUser != null) {
			LOG.info("User with this UserId Already Exists...");
			List<Role> roleList = userAdministrationService.getAllRole();
			model.put("rolesList", roleList);
			model.put("zoneList", Zone.values());
			model.put("user", user);
			model.put("duplicateUser", "User with this userId already exists");
			return new ModelAndView("addUSER", model);
		} else {
			user.setCreatedBy(userTemp.getId());
			user.setUpdatedBy(userTemp.getId());
			Calendar now = Calendar.getInstance();
			user.setCreatedDateTime(now);
			user.setUpdatedDateTime(now);
			setRole(user);
			setIcmcPrinter(user);
			user.setPassword(encoder.encode("null"));
			userAdministrationService.createUser(user, request.getRequestURL().toString());
			redirectAttributes.addFlashAttribute("successMsg", "New user has been created successfully");
			return new ModelAndView("redirect:./viewAll");
		}
	}

	private void setIcmcPrinter(User user) {
		if (user.getIcmcPrinterId() != null) {
			IcmcPrinter icmcPrinter = new IcmcPrinter();
			icmcPrinter.setId(user.getIcmcPrinterId());
			user.setIcmcPrinter(icmcPrinter);
		}

	}

	@RequestMapping("/editUser")
	public ModelAndView editUser(@RequestParam String id, User user) {
		ModelMap model = new ModelMap();
		user = userAdministrationService.getUserById(id);

		if (user.getRole() != null) {
			user.setRoleId(user.getRole().getId());
		}
		List<ZoneMaster> regionList = userAdministrationService.getRegionList(user);
		List<ICMC> icmcList = userAdministrationService.getICMCList(user);
		List<Role> roleList = userAdministrationService.getAllRole();
		if(user.getIcmcId() !=null){
		List<IcmcPrinter> icmcPrinterList = userAdministrationService.printerList(user.getIcmcId());
		model.put("icmcPrinterList", icmcPrinterList);
		}
		model.put("rolesList", roleList);
		model.put("zoneList", Zone.values());
		model.put("regionList", regionList);
		model.put("icmcList", icmcList);
		model.put("user", user);
		return new ModelAndView("editUser", model);
	}

	@RequestMapping("/updateUser")
	public ModelAndView updateUser(@ModelAttribute("bean") User user, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User userInSession = (User) session.getAttribute("login");
		user.setUpdatedBy(userInSession.getId());
		Calendar now = Calendar.getInstance();
		user.setUpdatedDateTime(now);
		
		
		//Special Character Validation
		
				boolean userId = HtmlUtils.isHtml(user.getId());
				boolean userName = HtmlUtils.isHtml(user.getName());
				if (userId == false || userName == false) {
					redirectAttributes.addFlashAttribute("successMsg", "Error : Special Character Not Allowed");
					return new ModelAndView("redirect:./viewAll");
				}
				
				//End of Special Character Validation
		
		setRole(user);
		setIcmcPrinter(user);
		// db user
		User userDb = userAdministrationService.getUserById(user.getId());
		user.setCreatedDateTime(userDb.getCreatedDateTime());
		user.setCreatedBy(userDb.getCreatedBy());
		user.setPassword(userDb.getPassword());

		userAdministrationService.updateUser(user);
		// If db user status is disabled and user status is enabled then do
		// password reset and send mail
		redirectAttributes.addFlashAttribute("updateMsg", "User has been updated successfully");
		return new ModelAndView("redirect:./viewAll");
	}

	private void setRole(User user) {
		Role role = new Role();
		role.setId(user.getRoleId());
		user.setRole(role);
	}

	@RequestMapping("/addBinCapacity")
	public ModelAndView addBinCapacity() {
		ModelMap map = new ModelMap();
		BinCapacityDenomination obj = new BinCapacityDenomination();
		map.put("denominationList", DenominationType.values());
		map.put("vaultSizeList", VaultSize.values());
		map.put("currencyTypeList", CurrencyType.values());
		map.put("user", obj);
		return new ModelAndView("/binCapacity", map);
	}

	@RequestMapping("/saveBinCapacity")
	public ModelAndView saveBinCapacity(@ModelAttribute("obj") BinCapacityDenomination binCapacity, HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		//Special Character Code
		boolean bincapacity = HtmlUtils.isHtml(binCapacity.getMaxBundleCapacity().toString());
		
		if(bincapacity==false)
		{
			redirectAttributes.addFlashAttribute("duplicateMsg",
					"Special Character not Allowed");
			return new ModelAndView("redirect:./addBinCapacity");
		}
		//End special CharacterCode
		
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		binCapacity.setInsertBy(user.getId());
		binCapacity.setUpdateBy(user.getId());
		// binCapacity.setIcmcId(user.getIcmcId());
		binCapacity.setInsertTime(now);
		binCapacity.setUpdateTime(now);

		BinCapacityDenomination bcd = userAdministrationService.isValidBinCapacityEntry(binCapacity);
		if (bcd != null) {
			redirectAttributes.addFlashAttribute("duplicateMsg",
					"Capacity for entered Vault Size, Currency Type and Denomination has already been defined");
			return new ModelAndView("redirect:./addBinCapacity");
		} else {
			userAdministrationService.saveBinCapacity(binCapacity);
			redirectAttributes.addFlashAttribute("successMsg",
					"Bin capacity for the selected denomination and currency type has been added successfully");
			return new ModelAndView("redirect:./viewBinCapacity");
		}
	}

	@RequestMapping("/editBinCapacity")
	public ModelAndView editBinCapacity(@RequestParam Long id, BinCapacityDenomination binCapacity) {
		ModelMap model = new ModelMap();
		binCapacity.setId(id);
		binCapacity = userAdministrationService.getBinCapacityById(id);
		model.put("denominationList", DenominationType.values());
		model.put("vaultSizeList", VaultSize.values());
		model.put("currencyTypeList", CurrencyType.values());
		model.put("user", binCapacity);
		return new ModelAndView("editBinCapacity", model);
	}

	@RequestMapping("/updateBinCapacity")
	public ModelAndView updateBinCapacity(@ModelAttribute("user") BinCapacityDenomination binCapacity,
			HttpSession session, RedirectAttributes redirectAttributes) {
		
		//Special Character Code
				boolean bincapacity = HtmlUtils.isHtml(binCapacity.getMaxBundleCapacity().toString());
				
				if(bincapacity==false)
				{
					redirectAttributes.addFlashAttribute("updateMsg",
							"Error : Special Character not Allowed");
					return new ModelAndView("redirect:./viewBinCapacity");
				}
				//End special CharacterCode
		
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		binCapacity.setUpdateBy(user.getId());
		// binCapacity.setIcmcId(user.getIcmcId());
		binCapacity.setUpdateTime(now);

		BinCapacityDenomination binCapacityDB = userAdministrationService.getBinCapacityById(binCapacity.getId());
		binCapacity.setInsertBy(binCapacityDB.getInsertBy());
		binCapacity.setInsertTime(binCapacityDB.getInsertTime());

		userAdministrationService.updateBinCapacity(binCapacity);
		redirectAttributes.addFlashAttribute("updateMsg",
				"Bin capacity for the selected denomination has been updated successfully");
		return new ModelAndView("redirect:./viewBinCapacity");
	}

	@RequestMapping("/viewBinCapacity")
	public ModelAndView binCapacityDetails() {
		List<BinCapacityDenomination> binCapacityList = userAdministrationService.getBinCapacity();
		LOG.info("VIEW BIN CAPACITY RECORDS");
		return new ModelAndView("/viewBinCapacity", "records", binCapacityList);
	}

	@RequestMapping(value = "icmcListForUserAdministration.json")
	public @ResponseBody List<ICMC> sectionList(@RequestParam(value = "region", required = true) String region) {
		return userAdministrationService.getICMCName(region);
	}

	@RequestMapping("/viewDelegateRight")
	public ModelAndView viewDelegateRight(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<DelegateRight> delegateRightlist = userAdministrationService.getDelegateRightList(user.getIcmcId());
		LOG.info("View delegate right list");
		return new ModelAndView("/viewDelegateRight", "records", delegateRightlist);
	}

	@RequestMapping("/addDelegateRight")
	public ModelAndView addDelegateRight() {
		DelegateRight obj = new DelegateRight();
		ModelMap map = new ModelMap();
		List<Role> roleList = userAdministrationService.getAllRole();
		map.put("rolesList", roleList);
		map.put("user", obj);
		return new ModelAndView("/DelegateRight", map);
	}

	public boolean isValidDelegateRightData(DelegateRight delegateRight) {
		if (delegateRight.getUserId() == null || delegateRight.getRoleId() == null
				|| delegateRight.getPeriodFrom() == null || delegateRight.getPeriodTo() == null) {
			return false;
		}
		return true;
	}

	@RequestMapping("/saveDelegateRight")
	public ModelAndView saveDelegateRight(@ModelAttribute("user") DelegateRight delegateRight, HttpSession session,
			ModelMap model, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();

		if (!isValidDelegateRightData(delegateRight)) {
			return new ModelAndView("redirect:./addDelegateRight");
		}

		if(delegateRight.getUserId().equalsIgnoreCase(user.getId()))
		{
			redirectAttributes.addFlashAttribute("successMsg1", "Login ID and assign id should not be same");
			return new ModelAndView("redirect:./addDelegateRight");
		}
		
		User userwithSameICMC = userAdministrationService.isValidUser(delegateRight.getUserId(),user.getIcmcId());
		if(userwithSameICMC == null)
		{
			redirectAttributes.addFlashAttribute("successMsg2", "User is from other ICMC ");
			return new ModelAndView("redirect:./addDelegateRight");
		}
		
		User dbUser = userAdministrationService.isValidUser(delegateRight.getUserId());

		if (dbUser != null) {
			delegateRight.setInsertBy(user.getId());
			delegateRight.setUpdateBy(user.getId());
			delegateRight.setInsertTime(now);
			delegateRight.setUpdateTime(now);
			delegateRight.setIcmcId(user.getIcmcId());
			this.setRoleForDelegateRight(delegateRight);
			userAdministrationService.insertDelegateRight(delegateRight);
			redirectAttributes.addFlashAttribute("successMsg", "Right has been delegated successfully");
			return new ModelAndView("redirect:./viewDelegateRight");
		} else {
			LOG.info("User with this userId doesn't exists...");
			List<Role> roleList = userAdministrationService.getAllRole();
			model.put("rolesList", roleList);
			model.put("user", delegateRight);
			model.put("userIdNotExists", "User with this userId doesn't exists");
			return new ModelAndView("DelegateRight", model);
		}
	}

	private void setRoleForDelegateRight(DelegateRight delegateRight) {
		Role role = new Role();
		role.setId(delegateRight.getRoleId());
		delegateRight.setRole(role);
	}

	@RequestMapping("/editDelegateRight")
	public ModelAndView editDelegateRight(@RequestParam Long id, DelegateRight delegateRight) {
		ModelMap model = new ModelMap();
		delegateRight.setId(id);
		delegateRight = userAdministrationService.delegateRightForModify(id);
		List<Role> roleList = userAdministrationService.getAllRole();
		model.put("rolesList", roleList);
		model.put("user", delegateRight);
		return new ModelAndView("editDelegateRight", model);
	}

	@RequestMapping("/updateDelegateRight")
	public ModelAndView updateDelegateRight(@ModelAttribute("user") DelegateRight delegateRight, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		delegateRight.setInsertBy(user.getId());
		delegateRight.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		delegateRight.setInsertTime(now);
		delegateRight.setUpdateTime(now);
		delegateRight.setIcmcId(user.getIcmcId());
		this.setRoleForDelegateRight(delegateRight);
		userAdministrationService.updateDelegateRight(delegateRight);
		redirectAttributes.addFlashAttribute("updateMsg", "Delegate Right record has baan updated successfully");
		return new ModelAndView("redirect:./viewDelegateRight");
	}

	@RequestMapping("/addMachineModelDetails")
	public ModelAndView addMachineModelDetails() {
		MachineModel obj = new MachineModel();
		ModelMap map = new ModelMap();
		List<ICMC> icmcList = userAdministrationService.getICMCForMachineDetails();
		map.put("record", icmcList);
		map.put("user", obj);
		return new ModelAndView("/addMachineModelDetails", map);
	}

	@RequestMapping("/viewMachineModelDetails")
	public ModelAndView viewMachineModelDetails() {
		List<MachineModel> machineModelDetailsList = userAdministrationService.getMachineModelDetails();
		return new ModelAndView("/viewMachineModelDetails", "records", machineModelDetailsList);
	}

	@RequestMapping("/saveMachineModelDetails")
	public ModelAndView saveMachineModelDetails(@ModelAttribute("user") MachineModel machineModel, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User userTemp = (User) session.getAttribute("login");

		//Special Character Code
				boolean machineModelType = HtmlUtils.isHtml(machineModel.getMachineModelType());
				boolean statndardProductivity = HtmlUtils.isHtml(machineModel.getStandardProductivity());
				
				if(machineModelType==false||statndardProductivity==false)
				{
					redirectAttributes.addFlashAttribute("successMsg", "Error : Special Characters not Allowed");
					return new ModelAndView("redirect:./viewMachineModelDetails");
				}
				//End special CharacterCode
						
		
		MachineModel mach = userAdministrationService.isValidMachineModel(machineModel.getMachineModelType());

		if (mach != null) {
			ModelMap map = new ModelMap();
			map.put("duplicateUser", "This model type already exists ");
			return new ModelAndView("/addMachineModelDetails", map);
		} else {
			machineModel.setStatus(Status.ENABLED);
			machineModel.setInsertBy(userTemp.getId());
			machineModel.setUpdateBy(userTemp.getId());
			Calendar now = Calendar.getInstance();
			machineModel.setInsertTime(now);
			machineModel.setUpdateTime(now);
			userAdministrationService.addMachineModelDetails(machineModel);
			redirectAttributes.addFlashAttribute("successMsg", "Machine Model details added Successfully");
			return new ModelAndView("redirect:./viewMachineModelDetails");
		}
	}

	@RequestMapping("/addMachineDetails")
	public ModelAndView addMachineDetails(HttpSession session) {
		Machine obj = new Machine();
		ModelMap map = new ModelMap();
		List<ICMC> icmcList = userAdministrationService.getICMCForMachineDetails();
		List<MachineCompany> machineCompanyNameList = userAdministrationService.getMachineCompanyNameList();
		List<MachineModel> modelTypeList = userAdministrationService.getMachineModelTypeList();
		map.put("modelTypeList", modelTypeList);
		map.put("machineCompanyNameList", machineCompanyNameList);
		map.put("user", obj);
		map.put("icmcList", icmcList);
		return new ModelAndView("/addMachineDetails", map);
	}

	@RequestMapping(value = "/getProductivity")
	@ResponseBody
	public String getProductivity(@RequestParam(value = "modelType") String modelType) {
		List<String> productivity = userAdministrationService.getStandardProductivityByModel(modelType);
		return productivity.toString();
	}

	@RequestMapping(value = "/getModelTypeAcordingIcmc")
	@ResponseBody
	public String getModelTypeAcordingIcmc() {
		List<String> productivity = userAdministrationService.getModelTypeAcordingIcmc();
		return productivity.toString();
	}

	@RequestMapping("/saveMachineDetails")
	public ModelAndView saveMachineDetails(@ModelAttribute("user") Machine machine, HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		ModelMap model = new ModelMap();
		
		//Special Character Code
		boolean machineNumber = HtmlUtils.isHtml(machine.getMachineNo().toString());
		boolean assetCode = HtmlUtils.isHtml(machine.getAssetCode());
		boolean machineSrNo = HtmlUtils.isHtml(machine.getMachineSINo());
		
		
		if(machineNumber==false||assetCode==false||machineSrNo==false)
		{
			return new ModelAndView("redirect:./viewMachineDetails");
		}
		
		/*Integer machineNoFromUI =  machine.getMachineNo();
		List<Machine> machineNoFromDao= userAdministrationService.getMachineList();*/
		/*for(Machine machineNoDetails: machineNoFromDao)
		{
			if(machineNoFromUI==(machineNoDetails.getMachineNo()))
			{
				redirectAttributes.addFlashAttribute("errorMsg", "Machine No should be unique");
				return new ModelAndView("redirect:./addMachineDetails");
			}
		}*/
		//End special CharacterCode
		
		User user = (User) session.getAttribute("login");
		machine.setInsertBy(user.getId());
		machine.setUpdateBy(user.getId());
		machine.setIcmcId(user.getIcmcId());
		Calendar now = Calendar.getInstance();
		machine.setInsertTime(now);
		machine.setUpdateTime(now);
		machine.setStatus(Status.ENABLED);
		Machine obj = new Machine();
		model.put("duplicateMachine", "Machine Already exist");
		model.put("user", obj);
		Machine getMachineFromDB = userAdministrationService.checkMachineIsExistOrNot(user.getIcmcId(),machine.getMachineNo());
		
		if(getMachineFromDB !=null && getMachineFromDB.getMachineNo() != null && getMachineFromDB.getMachineNo()==machine.getMachineNo())
		{
			//return new ModelAndView("addMachineDetails", model);
			redirectAttributes.addFlashAttribute("duplicateMachine", "Machine Already exist");
			return new ModelAndView("redirect:./addMachineDetails");
			
		}
		else
		{
		userAdministrationService.addMachineDetails(machine);
		return new ModelAndView("redirect:./viewMachineDetails");
		}
	}

	@RequestMapping("/viewMachineDetails")
	public ModelAndView viewMachineDetails(HttpSession session) {
		Long ageing = 0L;
		User user = (User) session.getAttribute("login");
		SimpleDateFormat formatObj = new SimpleDateFormat("yyyy-MM-dd");
		String dateStop = formatObj.format(new Date());
		List<Machine> machineList = userAdministrationService.getMachineDetails(user.getIcmcId());
		List<Machine> macMachine = new ArrayList<Machine>();
		Machine mcdate = null;
		for (Machine newDeff : machineList) {
			mcdate = new Machine();
			mcdate.setId(newDeff.getId());
			mcdate.setMachineNo(newDeff.getMachineNo());
			mcdate.setCompanyname(newDeff.getCompanyname());
			mcdate.setAssetCode(newDeff.getAssetCode());
			mcdate.setMachineSINo(newDeff.getMachineSINo());
			mcdate.setModelType(newDeff.getModelType());
			mcdate.setStandardProductivity(newDeff.getStandardProductivity());
			mcdate.setPurchasedate(newDeff.getPurchasedate());
			mcdate.setStatus(newDeff.getStatus());
			Date d1 = null;
			Date d2 = null;
			try {
				d1 = formatObj.parse(newDeff.getPurchasedate().toString().split(" ")[0]);
				d2 = formatObj.parse(dateStop);
				long diff = d2.getTime() - d1.getTime();
				ageing = diff / (24 * 60 * 60 * 1000);
				mcdate.setAgeing(ageing);
			} catch (Exception e) {
				e.printStackTrace();
			}
			macMachine.add(mcdate);
		}
		return new ModelAndView("/viewMachineDetails", "records", macMachine);

	}

	@RequestMapping("/editMachineModel")
	public ModelAndView editMachineModel(@RequestParam long id, MachineModel machine) {
		ModelMap model = new ModelMap();
		machine = userAdministrationService.getModelTypeId(id);
		model.put("statusList", Status.values());
		model.put("user", machine);
		return new ModelAndView("editMachineModel", model);
	}

	@RequestMapping("/updateMachineModelDetails")
	public ModelAndView updateMachineModelDetails(@ModelAttribute("user") MachineModel machine, HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		//Special Character Code
		boolean machineModelType = HtmlUtils.isHtml(machine.getMachineModelType());
		boolean statndardProductivity = HtmlUtils.isHtml(machine.getStandardProductivity());
		
		if(machineModelType==false||statndardProductivity==false)
		{
			redirectAttributes.addFlashAttribute("updateMsg", "Error : Special Characters not Allowed");
			return new ModelAndView("redirect:./viewMachineModelDetails");
		}
		//End special CharacterCode
		
		User user = (User) session.getAttribute("login");
		machine.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		machine.setUpdateTime(now);
		userAdministrationService.updateMachineModel(machine);
		redirectAttributes.addFlashAttribute("updateMsg", "Machine Model Details updated successfully");
		return new ModelAndView("redirect:./viewMachineModelDetails");
	}

	@RequestMapping("/editMachine")
	public ModelAndView editMachine(@RequestParam long id, Machine machine) {
		ModelMap model = new ModelMap();
		machine = userAdministrationService.getMachineDetailsById(id);
		List<MachineCompany> machineCompanyNameList = userAdministrationService.getMachineCompanyNameList();
		List<MachineModel> modelTypeList = userAdministrationService.getMachineModelTypeList();
		model.put("user", machine);
		model.put("statusList", Status.values());
		model.put("modelTypeList", modelTypeList);
		model.put("machineCompanyNameList", machineCompanyNameList);
		return new ModelAndView("editMachine", model);
	}

	@RequestMapping("/updateMachine")
	public ModelAndView updateMachine(@ModelAttribute("user") Machine machine, HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		//Special Character Code
				boolean machineNumber = HtmlUtils.isHtml(machine.getMachineNo().toString());
				boolean assetCode = HtmlUtils.isHtml(machine.getAssetCode());
				boolean machineSrNo = HtmlUtils.isHtml(machine.getMachineSINo());
				
				if(machineNumber==false||assetCode==false||machineSrNo==false)
				{
					redirectAttributes.addFlashAttribute("updateMsg", "Error : Special Characters Not allowed");
					return new ModelAndView("redirect:./viewMachineDetails");
				}
				//End special CharacterCode
				
		
		User user = (User) session.getAttribute("login");
		machine.setUpdateBy(user.getId());
		machine.setInsertBy(user.getId());
		Calendar now = Calendar.getInstance();
		machine.setUpdateTime(now);
		machine.setInsertTime(now);
		machine.setIcmcId(user.getIcmcId());
		userAdministrationService.updateMachine(machine);
		redirectAttributes.addFlashAttribute("updateMsg", "Machine Details updated successfully");
		return new ModelAndView("redirect:./viewMachineDetails");
	}

	@RequestMapping("/addPrinter")
	public ModelAndView addPrinter() {
		ModelMap map = new ModelMap();
		IcmcPrinter icmcPrinter = new IcmcPrinter();
		List<ICMC> icmcList = userAdministrationService.getEnabledICMCList();
		map.put("icmcPrinter", icmcPrinter);
		map.put("icmcList", icmcList);
		map.put("statusList", Status.values());
		return new ModelAndView("addPrinter", map);
	}

	public boolean isValidPrinterData(IcmcPrinter icmcPrinter) {
		if (icmcPrinter.getIcmcId() == null || icmcPrinter.getPrinterName() == null
				|| icmcPrinter.getPrinterIP() == null || icmcPrinter.getPort() == null) {
			return false;
		}
		return true;
	}

	@RequestMapping("/savePrinter")
	public ModelAndView savePrinter(@ModelAttribute("icmcPrinter") IcmcPrinter icmcPrinter, HttpSession session,
			ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User userTemp = (User) session.getAttribute("login");

		if (!isValidPrinterData(icmcPrinter)) {
			return new ModelAndView("redirect:./addPrinter");
		}
		IcmcPrinter dbIcmcPrinter = userAdministrationService.isValidPrinter(icmcPrinter.getPrinterName());

		if (dbIcmcPrinter != null) {
			LOG.info("Printer with this Name Already Exists...");
			List<ICMC> icmcList = userAdministrationService.getEnabledICMCList();
			model.put("icmcList", icmcList);
			model.put("statusList", Status.values());
			model.put("icmcPrinter", icmcPrinter);
			model.put("duplicatePrinter", "Printer with this Name Already Exists");
			return new ModelAndView("addPrinter", model);
		} else {
			icmcPrinter.setInsertBy(userTemp.getId());
			icmcPrinter.setUpdateBy(userTemp.getId());
			Calendar now = Calendar.getInstance();
			icmcPrinter.setInsertTime(now);
			icmcPrinter.setUpdateTime(now);
			userAdministrationService.savePrinter(icmcPrinter);
			redirectAttributes.addFlashAttribute("successMsg", "New Printer has been added successfully");
			return new ModelAndView("redirect:./viewPrinter");
		}
	}

	@RequestMapping("/viewPrinter")
	public ModelAndView viewPrinter() {
		List<IcmcPrinter> icmcPrinterList = new ArrayList<IcmcPrinter>();
		icmcPrinterList = userAdministrationService.getPrinterList();
		return new ModelAndView("viewPrinter", "records", icmcPrinterList);
	}

	@RequestMapping("/editPrinter")
	public ModelAndView editPrinter(@RequestParam Long id, IcmcPrinter icmcPrinter) {
		ModelMap model = new ModelMap();
		icmcPrinter = userAdministrationService.getPrinterById(id);
		List<ICMC> icmcList = userAdministrationService.getEnabledICMCList();
		model.put("icmcList", icmcList);
		model.put("icmcPrinter", icmcPrinter);
		return new ModelAndView("editPrinter", model);
	}

	@RequestMapping("/updatePrinter")
	public ModelAndView updatePrinter(@ModelAttribute("icmcPrinter") IcmcPrinter icmcPrinter, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User userInSession = (User) session.getAttribute("login");
		icmcPrinter.setUpdateBy(userInSession.getId());
		Calendar now = Calendar.getInstance();
		icmcPrinter.setUpdateTime(now);
		// db printer
		IcmcPrinter icmcPrinterDb = userAdministrationService.getPrinterById(icmcPrinter.getId());
		icmcPrinter.setInsertTime(icmcPrinterDb.getInsertTime());
		icmcPrinter.setInsertBy(icmcPrinterDb.getInsertBy());

		userAdministrationService.updatePrinter(icmcPrinter);
		redirectAttributes.addFlashAttribute("updateMsg", "Printer detail has been updated successfully");
		return new ModelAndView("redirect:./viewPrinter");
	}

	@RequestMapping(value = "icmcPrinterList.json")
	public @ResponseBody List<IcmcPrinter> printerList(@RequestParam(value = "icmc", required = true) BigInteger icmc) {
		LOG.info("ICMC_ID From Printer: " + icmc);
		List<IcmcPrinter> icmcPrinterList = userAdministrationService.printerList(icmc);
		return icmcPrinterList;
	}

}
