package com.chest.currency.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chest.currency.entity.model.MachineCompany;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.Status;
import com.chest.currency.service.MachineCompanyService;
import com.chest.currency.util.HtmlUtils;

@Controller
public class MachineCompanyController {

	@Autowired
	MachineCompanyService machineCompanyService;

	@RequestMapping("/viewMachineCompany")
	public ModelAndView viewMachineCompany() {
		List<MachineCompany> machineCompanies = machineCompanyService.getMachineCompany();
		return new ModelAndView("/viewMachineCompany", "records", machineCompanies);
	}

	@RequestMapping("/addMachineCompany")
	public ModelAndView addMachineCompany() {
		return new ModelAndView("/addMachineCompany", "user", new MachineCompany());
	}

	@RequestMapping("/saveMachineCompany")
	public ModelAndView saveMachineCompany(@ModelAttribute("user") MachineCompany machineCompany, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");

		// Special Character Code
		boolean companyName = HtmlUtils.isHtml(machineCompany.getCompanyname());
		boolean phoneNumber = HtmlUtils.isHtml(machineCompany.getPhonenumber().toString());
		boolean address = HtmlUtils.isHtml(machineCompany.getAddress());

		if (companyName == false || phoneNumber == false || address == false) {
			redirectAttributes.addFlashAttribute("successMsg", "Error : Special Character Not Allowed");
			return new ModelAndView("redirect:./viewMachineCompany");
		}
		// End special Character Code

		MachineCompany mach = machineCompanyService.isValidMachineCompany(machineCompany.getCompanyname());

		if (mach != null) {
			ModelMap map = new ModelMap();
			map.put("duplicateUser", "This Company Name already exists ");
			return new ModelAndView("/addMachineCompany", map);
		} else {

			Calendar now = Calendar.getInstance();
			machineCompany.setInsertBy(user.getId());
			machineCompany.setUpdateBy(user.getId());
			machineCompany.setInsertTime(now);
			machineCompany.setUpdateTime(now);
			machineCompany.setStatus(Status.ENABLED);
			machineCompanyService.insertMachineCompany(machineCompany);
			redirectAttributes.addFlashAttribute("successMsg", "Machine Company detail added successfully");
			return new ModelAndView("redirect:./viewMachineCompany");
		}
	}

	@RequestMapping("/editMachineCompany")
	public ModelAndView editMachineCompany(@RequestParam Long id, MachineCompany machineCompany) {
		ModelMap model = new ModelMap();
		machineCompany.setId(id);
		machineCompany = machineCompanyService.getMachineCompanyForModify(id);
		model.put("user", machineCompany);
		model.put("statusList", Status.values());
		return new ModelAndView("editMachineCompany", model);
	}

	@RequestMapping("/updateMachineCompany")
	public ModelAndView updateMachineCompany(@ModelAttribute("user") MachineCompany machineCompany, HttpSession session,
			RedirectAttributes redirectAttributes) {

		// Special Character Code
		boolean companyName = HtmlUtils.isHtml(machineCompany.getCompanyname());
		boolean phoneNumber = HtmlUtils.isHtml(machineCompany.getPhonenumber().toString());
		boolean address = HtmlUtils.isHtml(machineCompany.getAddress());

		if (companyName == false || phoneNumber == false || address == false) {
			redirectAttributes.addFlashAttribute("successMsg", "Error : Special Character Not Allowed");
			return new ModelAndView("redirect:./viewMachineCompany");
		}
		// End special CharacterCode

		User user = (User) session.getAttribute("login");
		machineCompany.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		machineCompany.setUpdateTime(now);
		machineCompanyService.updateMachineCompany(machineCompany);
		redirectAttributes.addFlashAttribute("successMsg", "Machine Company detail updted successfully");
		return new ModelAndView("redirect:./viewMachineCompany");
	}

}
