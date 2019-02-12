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

import com.chest.currency.entity.model.ICMCReport;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.ReportType;
import com.chest.currency.enums.Status;
import com.chest.currency.service.IcmcReportService;

@Controller
public class ICMCReportController {

	private static final Logger LOG = LoggerFactory.getLogger(ICMCReportController.class);

	@Autowired
	protected IcmcReportService icmcReportService;

	@RequestMapping("/viewICMCReport")
	public ModelAndView viewICMCReport() {
		LOG.error("viewICMCReport");
		List<ICMCReport> icmcReportList = icmcReportService.getICMCReport();
		return new ModelAndView("viewICMCReport", "records", icmcReportList);
	}

	@RequestMapping("/addICMCReport")
	public ModelAndView addICMCReport() {
		ModelMap model = new ModelMap();
		ICMCReport obj = new ICMCReport();
		LOG.error("ICMC Report Page");
		model.put("reportList", ReportType.values());
		model.put("user", obj);
		return new ModelAndView("addICMCReport", model);
	}

	public boolean isValidICMCReportData(ICMCReport icmcReport) {
		if (icmcReport.getNewReportType() == null || icmcReport.getReportType() == null) {
			return false;
		}
		return true;
	}

	@RequestMapping("/saveICMCReport")
	public ModelAndView saveICMCReport(@ModelAttribute("user") ICMCReport icmcReport, HttpSession session,
			ModelMap model, RedirectAttributes redirectAttributes) {
		User userTemp = (User) session.getAttribute("login");

		if (!isValidICMCReportData(icmcReport)) {
			return new ModelAndView("redirect:./addICMCReport");
		}

		ICMCReport dbICMCReport = icmcReportService.isCustomReportTypeNameValid(icmcReport.getNewReportType());
		if (dbICMCReport != null) {
			LOG.error("ICMC Report Name Already Exists...");
			model.put("reportList", ReportType.values());
			model.put("user", icmcReport);
			model.put("duplicateReport",
					"New custom report with this name already exists.... Please choose any other name");
			return new ModelAndView("addICMCReport", model);
		} else {
			icmcReport.setInsertBy(userTemp.getId());
			icmcReport.setUpdateBy(userTemp.getId());
			icmcReport.setStatus(Status.ENABLED);
			Calendar now = Calendar.getInstance();
			icmcReport.setInsertTime(now);
			icmcReport.setUpdateTime(now);
			icmcReportService.saveCustomICMCReport(icmcReport);
			redirectAttributes.addFlashAttribute("successMsg", "New custom report has been created successfully");
			return new ModelAndView("redirect:./viewICMCReport");
		}
	}

	@RequestMapping("/removeReport")
	public ModelAndView removeReport(@RequestParam int id, ICMCReport icmcReport) {
		icmcReport.setId(id);
		icmcReport = icmcReportService.getReportById(id);
		return new ModelAndView("removeReport", "user", icmcReport);
	}

	@RequestMapping("/deleteReport")
	public ModelAndView deleteReport(ICMCReport icmcReport, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User userTemp = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		icmcReport.setInsertBy(userTemp.getId());
		icmcReport.setUpdateBy(userTemp.getId());
		icmcReport.setInsertTime(now);
		icmcReport.setUpdateTime(now);
		icmcReport.setStatus(Status.DELETED);
		icmcReportService.deleteReport(icmcReport);
		redirectAttributes.addFlashAttribute("icmcReportRemovalSuccessMsg",
				"Custom ICMC report has been removed successfully");
		return new ModelAndView("redirect:./viewICMCReport");
	}
}
