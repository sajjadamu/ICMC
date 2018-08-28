/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chest.currency.entity.model.CRAAccountDetail;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.User;
import com.chest.currency.service.CRAAccountService;
import com.chest.currency.util.HtmlUtils;

@Controller
public class CRAAccountController {

	private static final Logger LOG = LoggerFactory.getLogger(CRAAccountController.class);

	@Autowired
	CRAAccountService craAccountService;

	@RequestMapping("/addCRAAccountDetail")
	public ModelAndView AccountDetailsForm() {
		ModelMap map = new ModelMap();
		List<ICMC> icmcList = craAccountService.getICMCName();
		CRAAccountDetail obj = new CRAAccountDetail();
		map.put("user", obj);
		map.put("record", icmcList);
		return new ModelAndView("addCRAAccountDetail", map);
	}

	@RequestMapping("/saveCRAAccountDetail")
	public ModelAndView saveCRAAccountDetail(@ModelAttribute("user") CRAAccountDetail craAccountDetail,
			HttpSession session, RedirectAttributes redirectAttributes) {

		// Special Character Validation

		boolean vendorName = HtmlUtils.isHtml(craAccountDetail.getCraVendorName());
		boolean mspName = HtmlUtils.isHtml(craAccountDetail.getMspName());
		boolean accountNumber = HtmlUtils.isHtml(craAccountDetail.getAccountNumber());
		if (vendorName == false || mspName == false || accountNumber == false) {
			redirectAttributes.addFlashAttribute("successMsg", "Error : Special Character not Allowed");
			return new ModelAndView("redirect:./addCRAAccountDetail");
		}

		// End of Special Character Validation

		User user = (User) session.getAttribute("login");
		craAccountDetail.setInsertBy(user.getId());
		craAccountDetail.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		craAccountDetail.setInsertTime(now);
		craAccountDetail.setUpdateTime(now);
		craAccountService.addCRAAccountDetails(craAccountDetail);
		redirectAttributes.addFlashAttribute("successMsg", "CRA Account details inserted Successfully");
		return new ModelAndView("redirect:./addCRAAccountDetail");
	}

	@RequestMapping("/viewCRAAccountDetail")
	public ModelAndView viewCRAAccountDetail(HttpSession session) {
		LOG.info("Going to fetch CRA Account Details");
		List<CRAAccountDetail> craAccountDetailList = craAccountService.getCRAccountDetailList();
		return new ModelAndView("viewCRAAccountDetail", "records", craAccountDetailList);
	}

	@RequestMapping("/editCRAAccountDetail")
	public ModelAndView editDSBAccountDetail(@RequestParam long id, CRAAccountDetail craAccountDetail) {
		ModelMap model = new ModelMap();
		craAccountDetail = craAccountService.getCRAAccountDetailById(id);
		List<ICMC> icmcList = craAccountService.getICMCName();
		model.put("record", icmcList);
		model.put("user", craAccountDetail);
		return new ModelAndView("editCRAAccountDetail", model);
	}

	@RequestMapping("/updateCRAAccountDetail")
	public ModelAndView updateCRAAccountDetail(@ModelAttribute("user") CRAAccountDetail craAccount, HttpSession session,
			RedirectAttributes redirectAttributes) {

		// Special Character Validation

		boolean vendorName = HtmlUtils.isHtml(craAccount.getCraVendorName());
		boolean mspName = HtmlUtils.isHtml(craAccount.getMspName());
		boolean accountNumber = HtmlUtils.isHtml(craAccount.getAccountNumber());
		if (vendorName == false || mspName == false || accountNumber == false) {
			redirectAttributes.addFlashAttribute("updateMsg", "Error : Special Character not Allowed");
			return new ModelAndView("redirect:./addCRAAccountDetail");
		}

		// End of Special Character Validation
		User user = (User) session.getAttribute("login");
		craAccount.setInsertBy(user.getId());
		craAccount.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		craAccount.setInsertTime(now);
		craAccount.setUpdateTime(now);
		craAccountService.updateCRAccountDetail(craAccount);
		redirectAttributes.addFlashAttribute("updateMsg", "CRA Account Detail updated successfully");
		return new ModelAndView("redirect:./viewCRAAccountDetail");
	}

}
