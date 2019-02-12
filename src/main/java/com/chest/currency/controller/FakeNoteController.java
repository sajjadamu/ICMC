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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chest.currency.entity.model.FakeNote;
import com.chest.currency.entity.model.User;
import com.chest.currency.service.FakeNoteService;
import com.chest.currency.util.UtilityJpa;

@Controller
public class FakeNoteController {

	private static final Logger LOG = LoggerFactory.getLogger(FakeNoteController.class);

	@Autowired
	FakeNoteService fakeNoteService;

	@RequestMapping("/fakeNoteEntry")
	public ModelAndView fakeNoteEntry() {
		LOG.error("Displaying Fake Note Form..");
		FakeNote obj = new FakeNote();
		return new ModelAndView("/fakeNoteEntry", "user", obj);
	}

	@RequestMapping("/addFakeNote")
	public ModelAndView addFakeNote(@ModelAttribute("user") FakeNote fakeNote, HttpSession session,
			@RequestParam MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		fakeNote.setInsertBy(user.getId());
		fakeNote.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		fakeNote.setInsertTime(now);
		fakeNote.setUpdateTime(now);
		fakeNote.setIcmcId(user.getIcmcId());

		if (file.getSize() == 0) {
			fakeNoteService.fakeNoteEntry(fakeNote);
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
				List<String> fakeNoteRecords = new LinkedList<>();
				csvBuffer.readLine();
				while ((csvLine = csvBuffer.readLine()) != null) {
					fakeNoteRecords.add(csvLine);
				}
				List<FakeNote> fakeNotelist = UtilityJpa.CSVtoArrayListForFakeNote(fakeNoteRecords);
				fakeNoteService.uploadFakeNote(fakeNotelist, fakeNote);

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

		}

		redirectAttributes.addFlashAttribute("successMsg", "Record Submitted Successfully");
		return new ModelAndView("redirect:./viewFakeNote");
	}

	@RequestMapping("/viewFakeNote")
	public ModelAndView viewFakeNoteEntry(HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<FakeNote> fakeNoteList = new ArrayList<FakeNote>();
		fakeNoteList = fakeNoteService.getFakeNoteList(user.getIcmcId());
		return new ModelAndView("viewFakeNote", "records", fakeNoteList);
	}

	@RequestMapping("/editFakeNote")
	public ModelAndView editFakeNote(@RequestParam long id, FakeNote fakeNote) {
		fakeNote = fakeNoteService.getFakeNoteById(id);
		return new ModelAndView("editFakeNote", "user", fakeNote);
	}

	@RequestMapping("/updateFakeNote")
	public ModelAndView updateFakeNote(@ModelAttribute("obj") FakeNote fakeNote, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		fakeNote.setUpdateBy(user.getId());
		fakeNote.setInsertBy(user.getId());
		Calendar now = Calendar.getInstance();
		fakeNote.setUpdateTime(now);
		fakeNote.setInsertTime(now);
		fakeNote.setIcmcId(user.getIcmcId());
		fakeNoteService.updateFakeNote(fakeNote);
		redirectAttributes.addFlashAttribute("successMsg", "Record Update Successfully");
		return new ModelAndView("redirect:./viewFakeNote");
	}
}
