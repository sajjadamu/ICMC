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
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.Status;
import com.chest.currency.enums.Zone;
import com.chest.currency.service.BranchService;
import com.chest.currency.service.ICMCService;
import com.chest.currency.util.HtmlUtils;


@Controller
public class ICMCController {

	private static final Logger LOG = LoggerFactory.getLogger(ICMCController.class);
	
	@Autowired
	ICMCService icmcService;
	
	@Autowired
	BranchService branchService;
	
	@Autowired
	String documentFilePath;

	@RequestMapping("/addICMC")
	public ModelAndView addICMC() {
		ICMC obj = new ICMC();
		LOG.info("ICMC PAGE");
		List<String> rbiNameList = branchService.getRBINameList();
		//List<String> regionList = icmcService.getRegionList(obj);
		ModelMap map = new ModelMap();
		map.put("rbiNameList", rbiNameList);
		map.put("zoneList", Zone.values());
		//map.put("regionList", regionList);
		map.put("user", obj);
		map.addAttribute("documentFilePath", "./files/"+documentFilePath);
		return new ModelAndView("/addICMC", map);
	}
	
	public boolean isValidICMCData(ICMC icmc, @RequestParam MultipartFile file){
		if(file == null || icmc.getName() == null || icmc.getZone() == null 
				|| icmc.getRegion() == null || icmc.getAddress() == null ||  icmc.getCity() == null 
				|| icmc.getPincode() == null || icmc.getLinkBranchSolId() == null){
			return false;
		}
		return true;
	}

	private static CellProcessor[] getProcessorsIcmc() {
		final String solIdVa = "[0-9\\s]+";
        StrRegEx.registerMessage(solIdVa, "SolId must be  only Numeric");
        
        final String nameVali = "[a-zA-Z\\s]+";
        StrRegEx.registerMessage(nameVali, "Name must be  only Alphabetic");
        
        
		
        final CellProcessor[] processors = new CellProcessor[] {
                new NotNull(new StrRegEx(nameVali)), 
                new NotNull(new StrRegEx(solIdVa)), 
                new NotNull(new StrRegEx(nameVali)), 
                new ParseEnum(Zone.class), 
                new NotNull(), 
                new NotNull(), 
                new NotNull(new StrRegEx(nameVali)), 
                new ParseInt(),
                new NotNull(), //code by shahabuddin
                       };
        return processors;
    }

	
	
	
	@RequestMapping("/saveICMC")
	public ModelAndView createICMC(@ModelAttribute("user") ICMC icmc, @RequestParam MultipartFile file, ModelMap model,
			HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes){
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		LOG.info("Going to create new ICMC");
		icmc.setStatus(Status.DISABLED);
		icmc.setInsertBy(user.getId());
		icmc.setUpdateBy(user.getId());
		icmc.setInsertTime(now);
		icmc.setUpdateTime(now);
		
		setFields(icmc);
		
       /*  boolean name = HtmlUtils.isHtml(icmc.getName());		
         boolean linkBranchSolId = HtmlUtils.isHtml(icmc.getLinkBranchSolId());
         boolean address = HtmlUtils.isHtml(icmc.getAddress());
         boolean city = HtmlUtils.isHtml(icmc.getCity());
         //boolean pinCode = HtmlUtils.isHtml(icmc.getPincode().toString());
         
		if(name==false||linkBranchSolId==false||address==false||city==false)
		{
			redirectAttributes.addFlashAttribute("successMsg", "Error : Special Character Not Allowed");
			return new ModelAndView("redirect:./viewICMC");
		}*/

		if (file.getSize() == 0) {
			ICMC dbIcmc = icmcService.isIcmcNameValid(icmc.getName());
			if(dbIcmc != null){
				LOG.info("ICMC Name Already Exists");
				model.put("user", icmc);
				model.put("duplicateICMC", "ICMC with this name already exists");
				return new ModelAndView("addICMC", model);
			}else{
				icmcService.createICMC(icmc);
				redirectAttributes.addFlashAttribute("successMsg", "New ICMC has been created successfully");
				return new ModelAndView("redirect:./viewICMC");
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
			List<ICMC> icmcRecords = new LinkedList<>();
			ICMC icmcUpload=null;
			try{
					try(ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile), CsvPreference.STANDARD_PREFERENCE))
						{ 
							final String[] headers = beanReader.getHeader(true);
								final CellProcessor[] processors = getProcessorsIcmc();
									while ((icmcUpload = beanReader.read(ICMC.class, headers, processors)) != null) {
										icmcRecords.add(icmcUpload);	
									}
        }}catch(Exception r){r.printStackTrace();
        	redirectAttributes.addFlashAttribute("errorMsg", "Csv file is not of standard format ");
        	return new ModelAndView("redirect:./addICMC");
        	}
			icmcService.uploadICMC(icmcRecords, icmc);
			redirectAttributes.addFlashAttribute("successMsg", "List of ICMC's has been uploaded successfully");
			return new ModelAndView("redirect:./viewICMC");
		}
	}
	
	@RequestMapping("/viewICMC")
	public ModelAndView getICMCRecord() {
		List<ICMC> icmcList = icmcService.getICMCList();
		LOG.info("Going to fetch ICMC records");
		return new ModelAndView("/viewICMC", "records", icmcList);
	}
	
	@RequestMapping("/editICMC")
	public ModelAndView editICMC(@RequestParam Long id){
		ICMC icmc = new ICMC();
		icmc = icmcService.getICMCById(id);
		
		getFields(icmc);
		
		List<String> rbiNameList = branchService.getRBINameList();
		List<String> regionList = icmcService.getRegionList(icmc);
		ModelMap map = new ModelMap();
		map.put("user", icmc);
		map.put("rbiNameList", rbiNameList);
		map.put("zoneList", Zone.values());
		map.put("regionList", regionList);
		map.put("statusList", Status.values());
		return new ModelAndView("editICMC", map);
	}
	
	private void getFields(ICMC icmc) {
		icmc.setZoneHidden(icmc.getZone());
		icmc.setRbiNameHidden(icmc.getRbiName());
		icmc.setRegionHidden(icmc.getRegion());
	}

	@RequestMapping("/updateICMC")
	public ModelAndView updateICMC(ICMC icmc, HttpSession session, RedirectAttributes redirectAttributes){
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		icmc.setUpdateBy(user.getId());
		icmc.setUpdateTime(now);
		
		setFields(icmc);
		
		// boolean name = HtmlUtils.isHtml(icmc.getName());		
         boolean linkBranchSolId = HtmlUtils.isHtml(icmc.getLinkBranchSolId());
         boolean address = HtmlUtils.isHtml(icmc.getAddress());
         boolean city = HtmlUtils.isHtml(icmc.getCity());
         boolean pinCode = HtmlUtils.isHtml(icmc.getPincode().toString());
         
		if(linkBranchSolId==false||address==false||city==false||pinCode==false)
		{
			redirectAttributes.addFlashAttribute("updateMsg", "Error : Special Character Not Allowed");
		}		
		else
		{
		icmcService.updateICMC(icmc);
		redirectAttributes.addFlashAttribute("updateMsg", "ICMC record has been updated successfully");
		}
		return new ModelAndView("redirect:./viewICMC");
	}

	private void setFields(ICMC icmc) {
		icmc.setZone(icmc.getZoneHidden());
		icmc.setRbiName(icmc.getRbiNameHidden());
		icmc.setRegion(icmc.getRegionHidden());
	}
	
	@RequestMapping("/removeICMC")
	public ModelAndView removeICMC(@RequestParam Long id) {
		ICMC icmc = new ICMC();
		icmc = icmcService.getICMCById(id);
		return new ModelAndView("removeICMC", "user", icmc);
	}
	
	@RequestMapping("/deleteICMC")
	public ModelAndView deleteICMC(@ModelAttribute("obj") ICMC icmc, HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		Calendar now = Calendar.getInstance();
		icmc.setInsertBy(user.getId());
		icmc.setUpdateBy(user.getId());
		icmc.setInsertTime(now);
		icmc.setUpdateTime(now);
		icmc.setStatus(Status.DELETED);
		icmcService.removeICMC(icmc);
		LOG.info("ICMC has been removed");
		redirectAttributes.addFlashAttribute("icmcRemovalSuccessMsg", "ICMC has been removed successfully");
		return new ModelAndView("redirect:./viewICMC");
	}
	
	@RequestMapping(value = "/getRBINameZoneAndRegion")
	@ResponseBody
	public List<Branch> getRBINameZoneAndRegion(@RequestParam(value = "linkBranchSolId") String linkBranchSolId){
		List<Branch> branchList = icmcService.getRBINameZoneAndRegion(linkBranchSolId);
		return branchList;
	}

}
