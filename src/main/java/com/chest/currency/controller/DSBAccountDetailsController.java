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

import com.chest.currency.entity.model.DSBAccountDetail;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.User;
import com.chest.currency.service.DSBAccountDetailsService;
import com.chest.currency.util.HtmlUtils;

@Controller
public class DSBAccountDetailsController {

	private static final Logger LOG = LoggerFactory.getLogger(DSBAccountDetailsController.class);

	@Autowired
	DSBAccountDetailsService dsbAccountService;

	@RequestMapping("/addDSBAccountDetail")
	public ModelAndView AccountDetailsForm() {
		ModelMap map = new ModelMap();
		List<ICMC> icmcList = dsbAccountService.getICMCName();
		DSBAccountDetail obj = new DSBAccountDetail();
		map.put("user", obj);
		map.put("record", icmcList);
		return new ModelAndView("addDSBAccountDetail", map);
	}

	@RequestMapping("/viewDSBAccountDetail")
	public ModelAndView viewDSBAccountDetail(HttpSession session) {
		LOG.info("Going to fetch DSB Account Details");
		List<DSBAccountDetail> dsbAccountDetailList = dsbAccountService.getDSBAccountDetailList();
		return new ModelAndView("viewDSBAccountDetail", "records", dsbAccountDetailList);
	}

	@RequestMapping("/saveDSBAccountDetail")
	public ModelAndView saveDSBAccountDetail(@ModelAttribute("user") DSBAccountDetail accountDetail,
			HttpSession session, RedirectAttributes redirectAttributes) {
		
		//Special Character Validation
		
				boolean vendorName = HtmlUtils.isHtml(accountDetail.getDsbVendorName());
				boolean accountNumber = HtmlUtils.isHtml(accountDetail.getAccountNumber());
				if (vendorName == false || accountNumber == false) {
					redirectAttributes.addFlashAttribute("successMsg", "Error : Special Character Not Allowed");
					return new ModelAndView("redirect:./addDSBAccountDetail");
				}
				
				//End of Special Character Validation
		
		User user = (User) session.getAttribute("login");
		accountDetail.setInsertBy(user.getId());
		accountDetail.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		accountDetail.setInsertTime(now);
		accountDetail.setUpdateTime(now);
		
		DSBAccountDetail vendorNameFromDB = dsbAccountService.isVendorNameValid(accountDetail.getDsbVendorName(), user.getIcmcId());
		if(vendorNameFromDB!=null)
		{
			redirectAttributes.addFlashAttribute("successMsg", "Vendor name Already Exist");
			return new ModelAndView("redirect:./addDSBAccountDetail");
		}
		dsbAccountService.addDSBAccountDetails(accountDetail);
		redirectAttributes.addFlashAttribute("successMsg", "DSB Account details inserted Successfully");
		return new ModelAndView("redirect:./addDSBAccountDetail");
	}
	
	@RequestMapping("/editDSBAccountDetail")
	 public ModelAndView editDSBAccountDetail(@RequestParam long id, DSBAccountDetail dsbAccountDetail) {
		ModelMap model = new ModelMap();
		dsbAccountDetail = dsbAccountService.getDSBAccountDetailById(id);
		List<ICMC> icmcList = dsbAccountService.getICMCName();
		model.put("record", icmcList);
		model.put("user", dsbAccountDetail);
		return new ModelAndView("editDSBAccountDetail", model);
	}
	
	@RequestMapping("/updateDSBAccountDetail")
	public ModelAndView updateCitCRAVehicle(@ModelAttribute("user") DSBAccountDetail dsbAccount, HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		//Special Character Validation
		
		boolean vendorName = HtmlUtils.isHtml(dsbAccount.getDsbVendorName());
		boolean accountNumber = HtmlUtils.isHtml(dsbAccount.getAccountNumber());
		if (vendorName == false || accountNumber == false) {
			redirectAttributes.addFlashAttribute("updateMsg", "Error : Special Character Not Allowed");
			return new ModelAndView("redirect:./viewDSBAccountDetail");
		}
		
		//End of Special Character Validation
		
		User user = (User) session.getAttribute("login");
		dsbAccount.setInsertBy(user.getId());
		dsbAccount.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		dsbAccount.setInsertTime(now);
		dsbAccount.setUpdateTime(now);
		dsbAccountService.updateDSBAccountDetail(dsbAccount);
		redirectAttributes.addFlashAttribute("updateMsg", "DSB Account Detail updated successfully");
		return new ModelAndView("redirect:./viewDSBAccountDetail");
	}
}
