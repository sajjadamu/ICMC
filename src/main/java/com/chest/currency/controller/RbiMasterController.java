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

import com.chest.currency.entity.model.RbiMaster;
import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.enums.State;
import com.chest.currency.enums.Status;
import com.chest.currency.enums.Zone;
import com.chest.currency.service.RbiMasterService;
import com.chest.currency.util.HtmlUtils;

@Controller
public class RbiMasterController {
	
	private static final Logger LOG = LoggerFactory.getLogger(RbiMasterController.class);
	
	@Autowired
	protected RbiMasterService rbiMasterService;
	
	@Autowired
	String documentFilePath;
	
	@RequestMapping("/viewRbiMaster")
	public ModelAndView viewRbiMaster(){
		LOG.info("View RBI Master");
		List<RbiMaster> allRbiMaster = rbiMasterService.getAllRbiMaster();
		return new ModelAndView("viewRbiMaster", "records", allRbiMaster);
	}
	
	@RequestMapping("/addRbiMaster")
	public ModelAndView addRbiMaster(@ModelAttribute("rbiMaster") RbiMaster rbiMaster){
		RbiMaster rbMaster=new RbiMaster();
		ModelMap map=new ModelMap();
		map.put("zoneList", Zone.values());
		map.put("rbMaster",rbMaster );
		map.put("stateList", State.values());
		map.put("statusList", Status.values());
		map.addAttribute("documentFilePath", "./files/"+documentFilePath);
		return new ModelAndView("addRbiMaster", map);
	}

	

	private static CellProcessor[] getProcessorsRbiMaster() {
		
		final String nameVali = "[a-zA-Z\\s]+";
        StrRegEx.registerMessage(nameVali, "Name must be  only Alphabetic");
       
        final CellProcessor[] processors = new CellProcessor[] {
                new NotNull(), 
                new ParseEnum(Zone.class), 
                new NotNull(), 
                new ParseEnum(State.class),
                new NotNull(new StrRegEx(nameVali)), 
                new NotNull(),
                new ParseInt(),
                       };
        return processors;
    }

	
	
	@RequestMapping("/saveRbiMaster")
	public ModelAndView saveRbiMaster(@ModelAttribute("rbiMaster") RbiMaster rbiMaster, @RequestParam MultipartFile file, 
			HttpServletRequest request, ModelMap  model, HttpSession session, RedirectAttributes redirectAttributes){
		
		//Special Character Validation
		
				boolean rbiName = HtmlUtils.isHtml(rbiMaster.getRbiname());
				boolean address = HtmlUtils.isHtml(rbiMaster.getAddress());
				boolean city = HtmlUtils.isHtml(rbiMaster.getCity());
				boolean pinCode  = HtmlUtils.isHtml(rbiMaster.getPinno().toString()); 
				if (rbiName == false || address == false || city == false || pinCode == false) {
					redirectAttributes.addFlashAttribute("successMsg", "Error : Special Character Not Allowed");
					return new ModelAndView("redirect:./viewRbiMaster");
				}
				
				//End of Special Character Validation
				
		User user=(User)session.getAttribute("login");
		rbiMaster.setInsertBy(user.getId());
		rbiMaster.setUpdateBy(user.getId());
		Calendar now=Calendar.getInstance();
		rbiMaster.setInsertTime(now);
		rbiMaster.setUpdateTime(now);
		rbiMaster.setStatus(Status.ENABLED);
		
		if (file.getSize() == 0) {
			RbiMaster rbiNameDb = rbiMasterService.isValidRbiName(rbiMaster.getRbiname());
			if (rbiNameDb != null) {
				model.put("zone", Zone.values());
				model.put("rbiMaster", rbiMaster);
				model.put("duplicateName", "RBI with this name already exists");
				return new ModelAndView("addRbiMaster", model);
			}else{
				rbiMasterService.saveRbiMaster(rbiMaster);
				redirectAttributes.addFlashAttribute("successMsg", "New RBI has been added successfully");
				return new ModelAndView("redirect:./viewRbiMaster");
			}
		}else{
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
			List<RbiMaster> rbiMasterRecords = new LinkedList<>();
			RbiMaster rbiMasterUpload=null;
			try{
					try(ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile), CsvPreference.STANDARD_PREFERENCE))
						{ 
							final String[] headers = beanReader.getHeader(true);
								final CellProcessor[] processors = getProcessorsRbiMaster();
									while ((rbiMasterUpload = beanReader.read(RbiMaster.class, headers, processors)) != null) {
										rbiMasterRecords.add(rbiMasterUpload);	
									}
        }}catch(Exception r){r.printStackTrace();
        	redirectAttributes.addFlashAttribute("errorMsg", "Csv file is not of standard formate ");
        	return new ModelAndView("redirect:./addRbiMaster");
        	}
			rbiMasterService.uploadRBIMaster(rbiMasterRecords, rbiMaster);
			redirectAttributes.addFlashAttribute("uploadMsg", "List of RBI's have been uploaded successfully");
			return new ModelAndView("redirect:./viewRbiMaster");
		}
	}
	
	@RequestMapping("/editRbiMaster")
	public ModelAndView editRbiMaster(@RequestParam Long id, RbiMaster rbiMaster){
		ModelMap map = new ModelMap();
		rbiMaster = rbiMasterService.getRbiMasterObject(id);
		List<ZoneMaster> regionList = rbiMasterService.getRegionList(rbiMaster);
		map.put("regionList", regionList);
		map.put("rbiMaster", rbiMaster);
		map.put("stateList", State.values());
		map.put("statusList", Status.values());
		return new ModelAndView("editRbiMaster", map);
	}
	
	@RequestMapping("/updateRbiMaster")
	public ModelAndView updateMachineMaster(@ModelAttribute("rbiMaster") RbiMaster rbiMaster, HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		//Special Character Validation
		
		boolean rbiName = HtmlUtils.isHtml(rbiMaster.getRbiname());
		boolean address = HtmlUtils.isHtml(rbiMaster.getAddress());
		boolean city = HtmlUtils.isHtml(rbiMaster.getCity());
		boolean pinCode  = HtmlUtils.isHtml(rbiMaster.getPinno().toString()); 
		if (rbiName == false || address == false || city == false || pinCode == false) {
			redirectAttributes.addFlashAttribute("updateMsg", "Error : Special Character Not Allowed");
			return new ModelAndView("redirect:./viewRbiMaster");
		}
		
		//End of Special Character Validation
		
		User user = (User) session.getAttribute("login");
		rbiMaster.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		rbiMaster.setUpdateTime(now);
		rbiMasterService.updateRbiMaster(rbiMaster);
		redirectAttributes.addFlashAttribute("updateMsg", "RBI has been updated successfully");
		return new ModelAndView("redirect:./viewRbiMaster");
	}
}
