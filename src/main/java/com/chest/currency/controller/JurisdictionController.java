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
import java.sql.SQLException;
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

import com.chest.currency.entity.model.Jurisdiction;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.Status;
import com.chest.currency.service.JurisdictionService;
import com.chest.currency.util.UtilityJpa;

@Controller
public class JurisdictionController {
	private static final Logger LOG = LoggerFactory.getLogger(JurisdictionController.class);

	@Autowired
	JurisdictionService jurisdictionService;

	@Autowired
	String documentFilePath;

	@RequestMapping("/addJurisdiction")
	public ModelAndView addJurisdiction() {
		Jurisdiction obj = new Jurisdiction();
		LOG.info("JURISDICTION PAGE");
		ModelMap map = new ModelMap();
		map.addAttribute("documentFilePath", "./files/" + documentFilePath);
		map.addAttribute("user", obj);
		return new ModelAndView("/jurisdiction", map);
	}

	@RequestMapping("/viewJurisdiction")
	public ModelAndView getICMCRecord() {
		List<Jurisdiction> jurisdictionList = jurisdictionService.getJurisdictionList();
		return new ModelAndView("/viewJurisdiction", "records", jurisdictionList);
	}

	@RequestMapping("/saveJurisdiction")
	public ModelAndView createJurisdiction(@ModelAttribute("obj") Jurisdiction jurisdiction, HttpSession session,
			@RequestParam MultipartFile file, HttpServletRequest request, ModelMap model,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		jurisdiction.setInsertBy(user.getId());
		jurisdiction.setUpdateBy(user.getId());

		Calendar now = Calendar.getInstance();
		jurisdiction.setInsertTime(now);
		jurisdiction.setUpdateTime(now);
		jurisdiction.setStatus(Status.ENABLED);

		if (file.getSize() == 0) {
			jurisdictionService.createJurisdiction(jurisdiction);
			redirectAttributes.addFlashAttribute("successMsg", "New jurisdiction has been created successfully");
			return new ModelAndView("redirect:./viewJurisdiction");
		} else {
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
				List<String> jurisdictionRecords = new LinkedList<>();
				csvBuffer.readLine();
				while ((csvLine = csvBuffer.readLine()) != null) {
					jurisdictionRecords.add(csvLine);
				}
				List<Jurisdiction> jurisdictionList = UtilityJpa.CSVtoArrayListForJurisdiction(jurisdictionRecords);
				jurisdictionService.UploadJurisdiction(jurisdictionList, jurisdiction);
				redirectAttributes.addFlashAttribute("successMsg",
						"List of jurisdiction's has been uploaded successfully");

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
			return new ModelAndView("redirect:./viewJurisdiction");
		}
	}

	@RequestMapping("/editJurisdiction")
	public ModelAndView editJurisdiction(@RequestParam int id, Jurisdiction jurisdiction) {
		jurisdiction = jurisdictionService.getJurisdictionById(id);
		return new ModelAndView("editJurisdiction", "user", jurisdiction);
	}

	@RequestMapping("/updateJurisdiction")
	public ModelAndView updateJurisdiction(@ModelAttribute("obj") Jurisdiction jurisdiction, HttpSession session,
			RedirectAttributes redirectAttributes) throws SQLException {
		User user = (User) session.getAttribute("login");
		jurisdiction.setInsertBy(user.getId());
		jurisdiction.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		jurisdiction.setInsertTime(now);
		jurisdiction.setUpdateTime(now);
		jurisdiction.setStatus(Status.ENABLED);
		jurisdictionService.updateJurisdiction(jurisdiction);
		redirectAttributes.addFlashAttribute("updateMsg", "Jurisdiction updated successfully");
		return new ModelAndView("redirect:./viewJurisdiction");
	}

}
