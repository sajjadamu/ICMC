package com.chest.currency.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.Vendor;
import com.chest.currency.enums.Status;
import com.chest.currency.service.VendorService;

@Controller
public class VendorController {

	private static final Logger LOG = LoggerFactory.getLogger(VendorController.class);

	@Autowired
	VendorService vendorService;

	@RequestMapping("/addVendor")
	public ModelAndView addVendor() {
		Vendor obj = new Vendor();
		LOG.info("Add Vendor Page");
		return new ModelAndView("/vendor", "user", obj);
	}

	@RequestMapping("/saveVendor")
	public ModelAndView vendorEntry(@ModelAttribute("user") Vendor vendor, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		vendor.setInsertBy(user.getId());
		vendor.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		vendor.setInsertTime(now);
		vendor.setUpdateTime(now);
		vendor.setStatus(Status.ENABLED);
		vendorService.addVendor(vendor);
		redirectAttributes.addFlashAttribute("successMsg", "New Vendor has been Added Successfully");
		return new ModelAndView("redirect:./viewVendor");
	}

	@RequestMapping("/viewVendor")
	public ModelAndView getVendorRecord() {
		List<Vendor> vendorList = vendorService.getVendorList();
		LOG.info("VIEW VENDOR RECORD");
		return new ModelAndView("/viewVendor", "records", vendorList);
	}

}
