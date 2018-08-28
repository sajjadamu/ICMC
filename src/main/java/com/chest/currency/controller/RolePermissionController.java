/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chest.currency.entity.model.Role;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.IcmcAccess;
import com.chest.currency.enums.PermissionModule;
import com.chest.currency.enums.PermissionName;
import com.chest.currency.enums.Status;
import com.chest.currency.service.RolePermissionService;
import com.chest.currency.util.HtmlUtils;

@Controller
public class RolePermissionController {

	private static final Logger LOG = LoggerFactory.getLogger(RolePermissionController.class);

	@Autowired
	protected RolePermissionService rolePermissionService;

	@RequestMapping("/viewRole")
	public ModelAndView viewRole() {
		LOG.info("View existing Roles");
		List<Role> roleList = rolePermissionService.getRoleList();
		return new ModelAndView("/viewRole", "records", roleList);
	}

	@RequestMapping("/addRole")
	public ModelAndView addRole() {
		Role role = new Role();
		ModelMap map = new ModelMap();
		map.put("icmcAccessList", IcmcAccess.values());
		map.put("statusList", Status.values());
		map.put("role", role);

		filterPermissionModules(map);

		return new ModelAndView("addRole", map);
	}

	private void filterPermissionModules(ModelMap map) {
		List<PermissionName> permissionList = Arrays.asList(PermissionName.values());

		List<PermissionName> permissionAdmin = permissionList.stream()
				.filter(permissionName -> permissionName.module == PermissionModule.ADMIN).collect(Collectors.toList());

		List<PermissionName> permissionBinDashboard = permissionList.stream()
				.filter(permissionName -> permissionName.module == PermissionModule.BIN_DASHBOARD)
				.collect(Collectors.toList());

		List<PermissionName> permissionVaultManagement = permissionList.stream()
				.filter(permissionName -> permissionName.module == PermissionModule.VAULT_MANAGEMENT)
				.collect(Collectors.toList());

		List<PermissionName> permissionCITCRADetails = permissionList.stream()
				.filter(permissionName -> permissionName.module == PermissionModule.CIT_CRA_DETAILS)
				.collect(Collectors.toList());

		List<PermissionName> permissionCashReceipt = permissionList.stream()
				.filter(permissionName -> permissionName.module == PermissionModule.CASH_RECEIPT)
				.collect(Collectors.toList());

		List<PermissionName> permissionCashPayment = permissionList.stream()
				.filter(permissionName -> permissionName.module == PermissionModule.CASH_PAYMENT)
				.collect(Collectors.toList());

		List<PermissionName> permissionProcessingRoom = permissionList.stream()
				.filter(permissionName -> permissionName.module == PermissionModule.PROCESSING_ROOM)
				.collect(Collectors.toList());

		List<PermissionName> permissionFakeNoteManagement = permissionList.stream()
				.filter(permissionName -> permissionName.module == PermissionModule.FAKE_NOTE_MANAGEMENT)
				.collect(Collectors.toList());

		List<PermissionName> permissionReports = permissionList.stream()
				.filter(permissionName -> permissionName.module == PermissionModule.REPORTS)
				.collect(Collectors.toList());

		map.put(PermissionModule.ADMIN.name(), permissionAdmin);
		map.put(PermissionModule.BIN_DASHBOARD.name(), permissionBinDashboard);
		map.put(PermissionModule.VAULT_MANAGEMENT.name(), permissionVaultManagement);
		map.put(PermissionModule.CIT_CRA_DETAILS.name(), permissionCITCRADetails);
		map.put(PermissionModule.CASH_RECEIPT.name(), permissionCashReceipt);
		map.put(PermissionModule.CASH_PAYMENT.name(), permissionCashPayment);
		map.put(PermissionModule.PROCESSING_ROOM.name(), permissionProcessingRoom);
		map.put(PermissionModule.FAKE_NOTE_MANAGEMENT.name(), permissionFakeNoteManagement);
		map.put(PermissionModule.REPORTS.name(), permissionReports);
	}

	@RequestMapping(value = "/saveRole")
	@ResponseBody
	public ModelAndView saveRole(@ModelAttribute("role") Role role, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		role.setCreatedBy(user.getId());
		role.setUpdatedBy(user.getId());
		role.setCreatedDateTime(now);
		role.setUpdatedDateTime(now);
		String roleID = role.getId();
		boolean xss = HtmlUtils.isHtml(roleID);
		if (xss == false) {
			redirectAttributes.addFlashAttribute("successMsg", "ERROR : XSS");
		} else {
			boolean isAllSuccess = rolePermissionService.saveRole(role);
			redirectAttributes.addFlashAttribute("successMsg", "New Role has been created successfully");
			if (!isAllSuccess) {
				throw new RuntimeException("Error while saving New Role, Please try again");
			}
		}
		List<Role> roleList = rolePermissionService.getRoleList();
		return new ModelAndView("/viewRole", "records", roleList);
	}

	@RequestMapping("/editRole")
	public ModelAndView editRole(@RequestParam String id, Role role) {
		role = rolePermissionService.getRoleById(id);
		ModelMap map = new ModelMap();
		map.put("user", role);
		map.put("icmcAccessList", IcmcAccess.values());
		map.put("statusList", Status.values());

		filterPermissionModules(map);

		return new ModelAndView("editRole", map);
	}

	@RequestMapping("/updateRole")
	public ModelAndView updateRole(@ModelAttribute("user") Role role, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		role.setUpdatedBy(user.getId());
		Calendar now = Calendar.getInstance();
		role.setUpdatedDateTime(now);
		String roleID = role.getId();
		boolean xss = HtmlUtils.isHtml(roleID);
		if (xss == false) {
			redirectAttributes.addFlashAttribute("updateMsg", "ERROR : XSS");
		} else {
			rolePermissionService.updateRole(role);
			redirectAttributes.addFlashAttribute("updateMsg", "Role has been updated successfully");
		}
		return new ModelAndView("redirect:./viewRole");
	}

}
