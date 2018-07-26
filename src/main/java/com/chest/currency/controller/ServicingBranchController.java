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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chest.currency.entity.model.ServicingBranch;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.Status;
import com.chest.currency.service.ServicingBranchService;
import com.chest.currency.util.UtilityJpa;

@Controller
public class ServicingBranchController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ServicingBranchController.class);
	
	@Autowired
	ServicingBranchService servicingBranchService;
	
	@Autowired
	String documentFilePath;
	
	@RequestMapping("/servicingBranch")
	public ModelAndView servicingBranch() {
		ServicingBranch obj = new ServicingBranch();
		LOG.info("Servicing Branch Page");
		ModelMap map = new ModelMap();
		map.addAttribute("documentFilePath", "./files/"+documentFilePath);
		map.addAttribute("user", obj);
		return new ModelAndView("/servicingBranch", map);
	}

	@RequestMapping("/UploadServicingBranch")
	public ModelAndView branchEntry(HttpSession session, @RequestParam MultipartFile file, HttpServletRequest request,
		ServicingBranch sb, RedirectAttributes redirectAttributes) {
		
		User user = (User) session.getAttribute("login");
		sb.setInsertBy(user.getId());
		sb.setUpdateBy(user.getId());
		sb.setStatus(Status.ENABLED);
		
		Calendar now = Calendar.getInstance();
		sb.setInsertTime(now);
		sb.setUpdateTime(now);

		BufferedReader csvBuffer = null;
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
		try {
			String csvLine;
			csvBuffer = new BufferedReader(new FileReader(serverFile));
			List<String> branchRecords = new LinkedList<>();
			csvBuffer.readLine();
			while ((csvLine = csvBuffer.readLine()) != null) {
				branchRecords.add(csvLine);
			}
			List<ServicingBranch> servicingList = UtilityJpa.CSVtoArrayListForServicing(branchRecords);
			servicingBranchService.UploadServicingBranch(servicingList, sb);
			redirectAttributes.addFlashAttribute("successMsg", "Servicing branch file has been uploaded successfully");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (csvBuffer != null)
					csvBuffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:./viewServicingBranch");
	}

	@RequestMapping("/viewServicingBranch")
	public ModelAndView servicingBranchRecord() {
		List<ServicingBranch> branchList = servicingBranchService.getServicingBranch();
		LOG.info("VIEW BRANCH RECORD");
		return new ModelAndView("/viewServicingBranch", "records", branchList);
	}

	@RequestMapping("/editServicingBranch")
	public ModelAndView editServicingBranch(@RequestParam Long id, ServicingBranch servicingBranch) {
		servicingBranch = servicingBranchService.editServicingBranch(id);
		return new ModelAndView("editServicingBranch", "user", servicingBranch);
	}

	@RequestMapping("/updateServicingBranch")
	public ModelAndView updateServicingBranch(@ModelAttribute("obj") ServicingBranch servicingBranch,
			HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		servicingBranch.setInsertBy(user.getId());
		servicingBranch.setUpdateBy(user.getId());
		
		Calendar now = Calendar.getInstance();
		servicingBranch.setInsertTime(now);
		servicingBranch.setUpdateTime(now);
		servicingBranch.setStatus(Status.ENABLED);
		servicingBranchService.updateServicingBranch(servicingBranch);
		redirectAttributes.addFlashAttribute("updateMsg", "Servicing branch record has been updated successfully");
		return new ModelAndView("redirect:./viewServicingBranch");
	}

}
