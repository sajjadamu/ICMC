/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.chest.currency.entity.model.HolidayMaster;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.State;
import com.chest.currency.enums.Zone;
import com.chest.currency.service.HolidayMasterService;
import com.chest.currency.util.UtilityJpa;

@Controller
public class HolidayMasterController {
	
	private static final Logger LOG = LoggerFactory.getLogger(HolidayMasterController.class);
	
	@Autowired
	protected HolidayMasterService holidayMasterService;
	
	@Autowired
	String documentFilePath;
	
	@RequestMapping("/viewHoliday")
	public ModelAndView viewHoliday() {
		ModelMap model = new ModelMap();
		HolidayMaster obj = new HolidayMaster();
		LOG.info("View Holiday List Page");
		model.put("stateList", State.values());
		model.put("user", obj);
		return new ModelAndView("viewHoliday", model);
	}
	
	@RequestMapping("/getHolidayList")
	public ModelAndView getHolidayList(@RequestParam String state, RedirectAttributes redirectAttributes){
		if(state.equalsIgnoreCase("")){
			redirectAttributes.addFlashAttribute("errorMsg", "Please Select an State");
			return new ModelAndView("redirect:./viewHoliday");
		}else{
			List<HolidayMaster> holidayList = holidayMasterService.getHolidayList(state);
			return new ModelAndView("getHolidayList", "records", holidayList);
		}
	}
	
	@RequestMapping("/uploadHoliday")
	public ModelAndView viewHolidayMaster() {
		ModelMap map = new ModelMap();
		map.addAttribute("documentFilePath", "./files/" + documentFilePath);
		return new ModelAndView("viewHolidayMaster", map);
	}
	
	
	private static CellProcessor[] getProcessorsHolidayMaster() {
        final CellProcessor[] processors = new CellProcessor[] {
                new NotNull(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
               // new Optional(),
               
                       };
        return processors;
    }
	
	
	@RequestMapping(value = "/uploadHolidayMaster", method = RequestMethod.POST)
	public ModelAndView holidayMasterUpload(@RequestParam MultipartFile file, HttpServletRequest request,
		HolidayMaster holiday, HttpSession session, RedirectAttributes redirectAttributes) {
		
		User user = (User) session.getAttribute("login");
		holiday.setInsertBy(user.getId());
		holiday.setUpdateBy(user.getId());
		
		Calendar now = Calendar.getInstance();
		holiday.setInsertTime(now);
		holiday.setUpdateTime(now);
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
		List<HolidayMaster> bulkHolidayList = new LinkedList<>();
		HolidayMaster holidayMaster=null;
		try{
				try(ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile), CsvPreference.STANDARD_PREFERENCE))
					{ 
						final String[] headers = beanReader.getHeader(true);
							final CellProcessor[] processors = getProcessorsHolidayMaster();
								while ((holidayMaster = beanReader.read(HolidayMaster.class, headers, processors)) != null) {
									bulkHolidayList.add(holidayMaster);	
								}
					}}catch(Exception r){r.printStackTrace();
    	redirectAttributes.addFlashAttribute("errorMsg", "Csv file is not of standard format ");
    	return new ModelAndView("redirect:./uploadHoliday");
    	}
		holidayMasterService.bulkHolidayRequest(bulkHolidayList, holiday);
		redirectAttributes.addFlashAttribute("successMsg", "Holiday file has been uploaded successfully");
		return new ModelAndView("redirect:./uploadHoliday");
		
		
		
		
	}

}
