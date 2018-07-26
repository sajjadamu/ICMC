/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.chest.currency.entity.model.Branch;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.RbiMaster;
import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.enums.Status;
import com.chest.currency.enums.Zone;
import com.chest.currency.service.BranchService;
import com.chest.currency.util.HtmlUtils;

@Controller
public class BranchController {

	private static final Logger LOG = LoggerFactory.getLogger(BranchController.class);

	@Autowired
	protected BranchService branchService;

	@Autowired
	String documentFilePath;

	@RequestMapping("/viewBranch")
	public ModelAndView getBranchRecord() {
		LOG.info("View Branch Records");
		List<Branch> branchList = branchService.getBranch();
		return new ModelAndView("/viewBranch", "records", branchList);
	}

	@RequestMapping("/addBranch")
	public ModelAndView addBranch() {
		Branch obj = new Branch();
		ModelMap map = new ModelMap();
		List<ICMC> icmcList = branchService.getServicingICMCName();
		List<RbiMaster> rbiMasterList = branchService.getRBIMasterList();
		map.put("records", icmcList);
		map.put("rbiMasterList", rbiMasterList);
		map.put("zoneList", Zone.values());
		map.put("user", obj);
		map.addAttribute("documentFilePath", "./files/" + documentFilePath);
		return new ModelAndView("/addBranch", map);
	}

	private static CellProcessor[] getProcessorsBranch() {

		final String solIdVa = "[0-9\\s]+";
		StrRegEx.registerMessage(solIdVa, "SolId must be  only Numeric");

		final String nameVali = "[a-zA-Z\\s]+";
		StrRegEx.registerMessage(nameVali, "Name must be  only Alphabetic");

		final CellProcessor[] processors = new CellProcessor[] { new NotNull(new StrRegEx(solIdVa)), new NotNull(),
				new NotNull(new StrRegEx(nameVali)), new NotNull(new StrRegEx(nameVali)),
				new NotNull(new StrRegEx(nameVali)), new ParseEnum(Zone.class), new NotNull(), new NotNull(),
				new NotNull(new StrRegEx(nameVali)), new ParseInt(),

		};
		return processors;
	}

	@RequestMapping("/saveBranch")
	public ModelAndView saveBranch(@ModelAttribute("user") Branch branch, HttpSession session,
			@RequestParam MultipartFile file, HttpServletRequest request, ModelMap model,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		branch.setInsertBy(user.getId());
		branch.setUpdateBy(user.getId());
		branch.setStatus(Status.ENABLED);
		branch.setInsertTime(now);
		branch.setUpdateTime(now);

		/*boolean solId = HtmlUtils.isHtml(branch.getSolId());
		boolean branchname = HtmlUtils.isHtml(branch.getBranch());
		boolean address = HtmlUtils.isHtml(branch.getAddress());
		boolean city = HtmlUtils.isHtml(branch.getCity());
		//boolean pinCode = HtmlUtils.isHtml(branch.getPincode().toString());
		if (solId == false || branchname == false || address == false || city == false) {
			redirectAttributes.addFlashAttribute("successMsg", "Error : Special Character Not Allowed");
			return new ModelAndView("redirect:./viewBranch");
		}*/

		this.setFields(branch);

		if (file.getSize() == 0) {
			Branch dbSolId = branchService.isSolIdValid(branch.getSolId());
			Branch dbBranch = branchService.isBranchNameValid(branch.getBranch());
			List<ICMC> icmcList = branchService.getServicingICMCName();
			if (dbBranch != null) {
				LOG.info("Branch Name Already Exists");
				model.put("records", icmcList);
				model.put("user", branch);
				model.put("duplicateBranch", "Branch name already exists, please choose any other name");
				return new ModelAndView("/addBranch", model);
			} else if (dbSolId != null) {
				LOG.info("Sol ID Already Exists");
				model.put("records", icmcList);
				model.put("user", branch);
				model.put("duplicateSolId", "Sol ID already exists, please choose any other Sol ID");
				return new ModelAndView("/addBranch", model);
			} else {
				branchService.saveBranch(branch);
				redirectAttributes.addFlashAttribute("successMsg", "New branch has been created successfully");
				return new ModelAndView("redirect:./viewBranch");
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
			List<Branch> branchRecords = new LinkedList<>();
			Branch branchUpload = null;
			try {
				try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile),
						CsvPreference.STANDARD_PREFERENCE)) {
					final String[] headers = beanReader.getHeader(true);
					final CellProcessor[] processors = getProcessorsBranch();
					while ((branchUpload = beanReader.read(Branch.class, headers, processors)) != null) {
						branchRecords.add(branchUpload);
					}
				}
			} catch (Exception r) {
				r.printStackTrace();
				redirectAttributes.addFlashAttribute("errorMsg", "Csv file is not of standard formate ");
				return new ModelAndView("redirect:./addBranch");
			}
			
			
			
List<Branch> listOFBranchName=branchService.getBranch();
			
			for(Branch branch2: listOFBranchName){
				for(Branch bBranch22:branchRecords){
					
					if((branch2.getBranch()).equals(bBranch22.getBranch())){
						
						System.out.println("hhhhhhhhh  " +branch2.getBranch()+"       "+ bBranch22.getBranch());
						
						redirectAttributes.addFlashAttribute("errorMsg", "Csv file contains dublicate value of Branch ");
						return new ModelAndView("redirect:./addBranch");
						
					}
				}
			}
			try
			{
			branchService.uploadBranch(branchRecords, branch);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("errorMsg", "Csv file contains dublicate branch name ");
				return new ModelAndView("redirect:./addBranch");
			}
			
			
			//branchService.uploadBranch(branchRecords, branch);
			redirectAttributes.addFlashAttribute("successMsg", "List of branches have been uploaded successfully");
			return new ModelAndView("redirect:./viewBranch");
		}
	}

	private void setFields(Branch branch) {
		branch.setZone(branch.getZoneHidden());
		branch.setRegion(branch.getRegionHidden());
	}

	@RequestMapping("/editBranch")
	public ModelAndView editBranch(@RequestParam Long id) {
		Branch branch = new Branch();
		branch = branchService.getBranchById(id);

		this.getFields(branch);

		List<ICMC> icmcList = branchService.getServicingICMCName();
		List<RbiMaster> rbiMasterList = branchService.getRBIMasterList();
		List<ZoneMaster> regionList = branchService.getRegionList(branch);
		ModelMap map = new ModelMap();
		map.put("icmcList", icmcList);
		map.put("rbiNameList", rbiMasterList);
		map.put("zoneList", Zone.values());
		map.put("regionList", regionList);
		map.put("user", branch);
		map.put("statusList", Status.values());
		return new ModelAndView("editBranch", map);
	}

	private void getFields(Branch branch) {
		branch.setZoneHidden(branch.getZone());
		branch.setRegionHidden(branch.getRegion());
	}

	@RequestMapping("/updateBranch")
	public ModelAndView updateBranch(Branch branch, HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		branch.setUpdateBy(user.getId());
		branch.setUpdateTime(now);

		boolean solId = HtmlUtils.isHtml(branch.getSolId());
		boolean address = HtmlUtils.isHtml(branch.getAddress());
		boolean city = HtmlUtils.isHtml(branch.getCity());
		boolean pinCode = HtmlUtils.isHtml(branch.getPincode().toString());
		if (solId == false || address == false || city == false || pinCode == false) {
			redirectAttributes.addFlashAttribute("successMsg", "Error : Special Character Not Allowed");
		}
		else 
		{
		setFields(branch);
		branchService.updateBranch(branch);
		redirectAttributes.addFlashAttribute("updateMsg", "Branch record has been updated successfully");
		}
		return new ModelAndView("redirect:./viewBranch");
	}

	@RequestMapping("/removeBranch")
	public ModelAndView removeBranch(@RequestParam long id) {
		Branch branch = new Branch();
		branch.setId(id);
		branch = branchService.getBranchById(id);
		return new ModelAndView("removeBranch", "user", branch);
	}

	@RequestMapping("/deleteBranch")
	public ModelAndView deleteBranch(@ModelAttribute("obj") Branch branch, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		branch.setInsertBy(user.getId());
		branch.setUpdateBy(user.getId());

		branch.setStatus(Status.DELETED);
		Calendar now = Calendar.getInstance();
		branch.setInsertTime(now);
		branch.setUpdateTime(now);
		branchService.RemoveBranch(branch);
		redirectAttributes.addFlashAttribute("branchRemovalSuccessMsg", "Branch has been removed successfully");
		return new ModelAndView("redirect:./viewBranch");
	}

	@RequestMapping(value = "/getZoneAndRegion")
	@ResponseBody
	public List<RbiMaster> getZoneAndRegion(@RequestParam(value = "rbiName") String rbiName) {
		List<RbiMaster> zoneAndRegionList = branchService.getZoneAndRegion(rbiName);
		return zoneAndRegionList;
	}
}
