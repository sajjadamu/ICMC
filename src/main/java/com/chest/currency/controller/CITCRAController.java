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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.chest.currency.entity.model.CITCRADriver;
import com.chest.currency.entity.model.CITCRAVehicle;
import com.chest.currency.entity.model.CITCRAVendor;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.enums.Status;
import com.chest.currency.enums.Zone;
import com.chest.currency.service.CITCRAService;
import com.chest.currency.service.ICMCService;
import com.chest.currency.util.UtilityJpa;

@Controller
public class CITCRAController {
	private static final Logger LOG = LoggerFactory.getLogger(CITCRAController.class);

	@Autowired
	CITCRAService citCraService;

	@Autowired
	ICMCService iCMCService;

	@RequestMapping("/CITCRAVendor")
	public ModelAndView addVendor() {
		CITCRAVendor obj = new CITCRAVendor();
		return new ModelAndView("/CITCRAVendor", "user", obj);
	}

	@RequestMapping(value = "icmcList.json")
	public @ResponseBody List<ICMC> sectionList(@RequestParam(value = "region", required = true) String region) {
		return citCraService.getICMCName(region);
	}

	@RequestMapping(value = "/addCITCRAVendor", method = RequestMethod.POST)
	public ModelAndView addCitCraVendor(@ModelAttribute("obj") CITCRAVendor citcraVendor, HttpSession session,
			@RequestParam MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		User user = (User) session.getAttribute("login");
		citcraVendor.setInsertBy(user.getId());
		citcraVendor.setUpdateBy(user.getId());

		Calendar now = Calendar.getInstance();
		citcraVendor.setInsertTime(now);
		citcraVendor.setUpdateTime(now);
		citcraVendor.setStatus(Status.ENABLED);

		if (file.getSize() == 0) {
			populateIcmcList(citcraVendor);
			citCraService.addCitCraVendor(citcraVendor);
			redirectAttributes.addFlashAttribute("successMsg", "New vendor has been added successfully");
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
				List<String> icmcRecords = new LinkedList<>();
				csvBuffer.readLine();
				while ((csvLine = csvBuffer.readLine()) != null) {
					icmcRecords.add(csvLine);
				}
				List<ICMC> icmcList = iCMCService.getICMCList();
				List<CITCRAVendor> list = UtilityJpa.CSVtoArrayListForCITCRAVendor(icmcRecords, icmcList);
				citCraService.UploadCITCRAVendor(list, citcraVendor);

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
		return new ModelAndView("redirect:./viewCITCRAVendor");
	}

	@RequestMapping("/editCITCRAVendor")
	public ModelAndView editCITCRAVendor(@RequestParam Long id, CITCRAVendor vendor) {
		ModelMap model = new ModelMap();
		vendor.setId(id);
		vendor = citCraService.vendorRecordForModify(id);
		populateIcmcIds(vendor);
		List<ZoneMaster> regionList = citCraService.getRegionList(vendor);
		List<ICMC> icmcList = citCraService.getIcmcList(vendor);

		model.put("user", vendor);
		model.put("zoneList", Zone.values());
		model.put("regionList", regionList);
		model.put("icmcList", icmcList);
		return new ModelAndView("editCITCRAVendor", model);
	}

	private void populateIcmcIds(CITCRAVendor vendor) {
		List<Long> icmcIds = new ArrayList<Long>();
		if (vendor.getIcmcList() != null) {
			for (ICMC icmc : vendor.getIcmcList()) {
				icmcIds.add(icmc.getId());
			}
		}
		vendor.setIcmcIds(icmcIds);
	}

	@RequestMapping("/viewCITCRAVendor")
	public ModelAndView getCITCRAVendorRecord() {
		List<CITCRAVendor> vendorList = citCraService.getCITCRAVendor();
		LOG.info("VIEW Vendor RECORD");
		return new ModelAndView("/viewCITCRAVendor", "records", vendorList);
	}

	@RequestMapping("/updateCITCRAVendor")
	public ModelAndView updateCitCRAVendor(@ModelAttribute("user") CITCRAVendor vendor, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		vendor.setInsertBy(user.getId());
		vendor.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		vendor.setInsertTime(now);
		vendor.setUpdateTime(now);
		vendor.setStatus(Status.ENABLED);
		populateIcmcList(vendor);

		citCraService.updateCITCRAVendor(vendor);
		redirectAttributes.addFlashAttribute("updateMsg", "Vendor record updated successfully");
		return new ModelAndView("redirect:./viewCITCRAVendor");
	}

	private void populateIcmcList(CITCRAVendor vendor) {
		List<ICMC> icmcList = null;
		if (vendor.getIcmcIds() != null) {
			icmcList = iCMCService.getICMCList(vendor.getIcmcIds());
		}
		vendor.setIcmcList(icmcList);
	}

	@RequestMapping("/removeCITCRAVendor")
	public ModelAndView removeCITCRAVendor(@RequestParam Long id) {
		CITCRAVendor vendor = new CITCRAVendor();
		vendor.setId(id);
		vendor = citCraService.getCitCraVendorById(id);
		return new ModelAndView("removeCITCRAVendor", "user", vendor);
	}

	@RequestMapping("/removeVendor")
	public ModelAndView removeCitCRAVendor(@ModelAttribute("user") CITCRAVendor vendor, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		vendor.setInsertBy(user.getId());
		vendor.setUpdateBy(user.getId());
		vendor.setStatus(Status.DELETED);
		Calendar now = Calendar.getInstance();
		vendor.setInsertTime(now);
		vendor.setUpdateTime(now);
		citCraService.deleteCITCRAVendor(vendor);
		redirectAttributes.addFlashAttribute("removeMsg", "Vendor has been removed successfully");
		return new ModelAndView("redirect:./viewCITCRAVendor");
	}

	@RequestMapping("/viewCITCRAVehicle")
	public ModelAndView getCITCRAVehicleRecord() {
		List<CITCRAVehicle> vehicleList = citCraService.getVehicleDetails();
		LOG.info("VIEW Vehicle RECORD");
		return new ModelAndView("/viewCITCRAVehicle", "records", vehicleList);
	}

	@RequestMapping("/CITCRAVehicle")
	public ModelAndView addVechile(CITCRAVehicle obj, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap map = new ModelMap();
		List<CITCRAVendor> vendorList = citCraService.getVendorNameAccordingToICMC(user.getRegionId());
		map.put("records", vendorList);
		map.put("user", obj);
		return new ModelAndView("/CITCRAVehicle", map);
	}

	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new NotNull(), new NotNull(),
				new ParseDate("yyyy-mm-dd"), new NotNull(), new NotNull(), new ParseDate("yyyy-mm-dd"),
				new ParseDate("yyyy-mm-dd"), new ParseDate("yyyy-mm-dd"), };
		return processors;
	}

	@RequestMapping("/addCITCRAVehicle")
	public ModelAndView citCRAVehicle(@ModelAttribute("obj") CITCRAVehicle vehicle, HttpSession session,
			@RequestParam MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		vehicle.setInsertBy(user.getId());
		vehicle.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		vehicle.setInsertTime(now);
		vehicle.setUpdateTime(now);
		vehicle.setStatus(Status.ENABLED);

		if (file.getSize() == 0) {
			citCraService.addVehicle(vehicle);
			redirectAttributes.addFlashAttribute("successMsg", "Vehicle has been added successfully");
			return new ModelAndView("redirect:./viewCITCRAVehicle");
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

			List<CITCRAVehicle> vehicleRecords = new LinkedList<>();

			CITCRAVehicle icmcc = null;
			try {
				try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile),
						CsvPreference.STANDARD_PREFERENCE)) {
					final String[] headers = beanReader.getHeader(true);
					final CellProcessor[] processors = getProcessors();
					while ((icmcc = beanReader.read(CITCRAVehicle.class, headers, processors)) != null) {
						vehicleRecords.add(icmcc);
					}
				}
			} catch (Exception r) {
				redirectAttributes.addFlashAttribute("errorMsg", "Csv file is not of standard formate ");
				return new ModelAndView("redirect:./CITCRAVehicle");
			}

			// List<CITCRAVehicle> list =
			// UtilityJpa.CSVtoArrayListForCITCRAVehicle(vehicleRecords);
			citCraService.UploadCITCRAVehicle(vehicleRecords, vehicle);
			redirectAttributes.addFlashAttribute("uploadMsg", "List of vehicles has been uploaded successfully");

			/*
			 * try { String csvLine; csvBuffer = new BufferedReader(new
			 * FileReader(serverFile)); List<String> vehicleRecords = new
			 * LinkedList<>(); csvBuffer.readLine(); while ((csvLine =
			 * csvBuffer.readLine()) != null) { vehicleRecords.add(csvLine); }
			 * List<CITCRAVehicle> list =
			 * UtilityJpa.CSVtoArrayListForCITCRAVehicle(vehicleRecords);
			 * citCraService.UploadCITCRAVehicle(list, vehicle);
			 * redirectAttributes.addFlashAttribute("uploadMsg",
			 * "List of vehicles has been uploaded successfully"); } catch
			 * (IOException e) { e.printStackTrace(); } finally { try { if
			 * (csvBuffer != null) csvBuffer.close(); } catch (IOException e) {
			 * e.printStackTrace(); } }
			 */
			return new ModelAndView("redirect:./viewCITCRAVehicle");
		}
	}

	@RequestMapping("/editCITCRAVehicle")
	public ModelAndView editCITCRAVehicle(@RequestParam Long id, CITCRAVehicle vehicle, HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<CITCRAVendor> vendorList = citCraService.getVendorNameAccordingToICMC(user.getRegionId());
		ModelMap model = new ModelMap();
		vehicle.setId(id);
		vehicle = citCraService.vehicleRecordForModify(id);
		model.put("user", vehicle);
		model.put("vendorList", vendorList);
		return new ModelAndView("editCITCRAVehicle", model);
	}

	@RequestMapping("/updateCITCRAVehicle")
	public ModelAndView updateCitCRAVehicle(@ModelAttribute("user") CITCRAVehicle vehicle, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		vehicle.setInsertBy(user.getId());
		vehicle.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		vehicle.setInsertTime(now);
		vehicle.setUpdateTime(now);
		vehicle.setStatus(Status.ENABLED);
		citCraService.updateCITCRAVehicle(vehicle);
		redirectAttributes.addFlashAttribute("updateMsg", "Vehicle record updated successfully");
		return new ModelAndView("redirect:./viewCITCRAVehicle");
	}

	@RequestMapping("/removeCITCRAVehicle")
	public ModelAndView removeCITCRAVehicle(@RequestParam Long id, CITCRAVehicle vehicle) {
		vehicle.setId(id);
		vehicle = citCraService.vehicleRecordForRemove(id);
		return new ModelAndView("removeCITCRAVehicle", "user", vehicle);
	}

	@RequestMapping("/deleteVehicle")
	public ModelAndView deleteCitCRAVehicle(@ModelAttribute("user") CITCRAVehicle vehicle, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		vehicle.setInsertBy(user.getId());
		vehicle.setUpdateBy(user.getId());
		vehicle.setStatus(Status.DELETED);
		Calendar now = Calendar.getInstance();
		vehicle.setInsertTime(now);
		vehicle.setUpdateTime(now);
		citCraService.deleteCITCRAVehicle(vehicle);
		redirectAttributes.addFlashAttribute("removeMsg", "Vehicle has been removed successfully");
		return new ModelAndView("redirect:./viewCITCRAVehicle");
	}

	@RequestMapping("/viewCITCRADriver")
	public ModelAndView getCITCRADriver() {
		List<CITCRADriver> driverList = citCraService.getVehicleDriver();
		LOG.info("VIEW DRIVER RECORD");
		return new ModelAndView("/viewCITCRADriver", "records", driverList);
	}

	@RequestMapping(value = "vehicleList.json")
	public @ResponseBody List<CITCRAVehicle> vehicleNumberList(
			@RequestParam(value = "vendor", required = true) String vendor) {
		return citCraService.getVehicleNumberAccordingToVendorForDriver(vendor);
	}

	@RequestMapping("/CITCRADriver")
	public ModelAndView addDriver(CITCRADriver obj, HttpSession session) {
		User user = (User) session.getAttribute("login");
		List<CITCRAVendor> vendorList = citCraService.getVendorNameAccordingToICMC(user.getRegionId());
		ModelMap map = new ModelMap();
		map.put("records", vendorList);
		map.put("user", obj);
		return new ModelAndView("/CITCRADriver", map);
	}

	private static CellProcessor[] getProcessorsDriver() {
		final CellProcessor[] processors = new CellProcessor[] { new NotNull(), new NotNull(), new NotNull(),
				new NotNull(), new NotNull(), new ParseDate("yyyy-mm-dd"), new ParseDate("yyyy-mm-dd"), };
		return processors;
	}

	@RequestMapping("/addCITCRADriver")
	public ModelAndView citCRADriver(@ModelAttribute("obj") CITCRADriver driver, HttpSession session,
			@RequestParam MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		driver.setInsertBy(user.getId());
		driver.setUpdateBy(user.getId());
		Calendar now = Calendar.getInstance();
		driver.setInsertTime(now);
		driver.setUpdateTime(now);
		driver.setStatus(Status.ENABLED);

		if (file.getSize() == 0) {
			citCraService.addDriver(driver);
			redirectAttributes.addFlashAttribute("successMsg", "Driver has been added successfully");
			return new ModelAndView("redirect:./viewCITCRADriver");
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
				try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(serverFile),
						CsvPreference.STANDARD_PREFERENCE)) {
					final String[] headers = beanReader.getHeader(true);
					final CellProcessor[] processors = getProcessorsDriver();
					while ((beanReader.read(CITCRADriver.class, headers, processors)) != null) {
					}
				}
			} catch (Exception r) {
				r.printStackTrace();
				redirectAttributes.addFlashAttribute("errorMsg", "Csv file is not of standard formate ");
				return new ModelAndView("redirect:./CITCRADriver");
			}

			try {
				String csvLine;
				csvBuffer = new BufferedReader(new FileReader(serverFile));
				List<String> driverRecords = new LinkedList<>();
				csvBuffer.readLine();
				while ((csvLine = csvBuffer.readLine()) != null) {
					driverRecords.add(csvLine);
				}
				List<CITCRADriver> list = UtilityJpa.CSVtoArrayListForCITCRADriver(driverRecords);
				citCraService.UploadCITCRADriver(list, driver);
				redirectAttributes.addFlashAttribute("uploadMsg", "List of drivers has been uploaded successfully");
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
			return new ModelAndView("redirect:./viewCITCRADriver");
		}
	}

	@RequestMapping("/editCITCRADriver")
	public ModelAndView editCITCRADriver(@RequestParam Long id, CITCRADriver driver, HttpSession session) {
		User user = (User) session.getAttribute("login");
		ModelMap model = new ModelMap();
		driver.setId(id);
		driver = citCraService.driverRecordForModify(id);
		List<CITCRAVendor> vendorList = citCraService.getVendorNameAccordingToICMC(user.getRegionId());
		List<String> vehicleNumberList = citCraService.getVehicleNumberList();
		model.put("user", driver);
		model.put("vendorList", vendorList);
		model.put("vehicleNumberList", vehicleNumberList);
		return new ModelAndView("editCITCRADriver", model);
	}

	@RequestMapping("/updateCITCRADriver")
	public ModelAndView updateCitCRADriver(@ModelAttribute("user") CITCRADriver driver, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		driver.setInsertBy(user.getId());
		driver.setUpdateBy(user.getId());
		driver.setStatus(Status.ENABLED);
		Calendar now = Calendar.getInstance();
		driver.setInsertTime(now);
		driver.setUpdateTime(now);
		citCraService.updateCITCRADriver(driver);
		redirectAttributes.addFlashAttribute("updateMsg", "Driver record has been updated successfully");
		return new ModelAndView("redirect:./viewCITCRADriver");
	}

	@RequestMapping("/removeCITCRADriver")
	public ModelAndView removeCITCRADriver(@RequestParam Long id, CITCRADriver driver) {
		driver.setId(id);
		driver = citCraService.driverRecordForRemove(id);
		return new ModelAndView("removeCITCRADriver", "user", driver);
	}

	@RequestMapping("/removeDriver")
	public ModelAndView deleteCITCRADriver(@ModelAttribute("obj") CITCRADriver driver, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = (User) session.getAttribute("login");
		driver.setInsertBy(user.getId());
		driver.setUpdateBy(user.getId());
		driver.setStatus(Status.DELETED);
		Calendar now = Calendar.getInstance();
		driver.setInsertTime(now);
		driver.setUpdateTime(now);
		citCraService.deleteCITCRADriver(driver);
		redirectAttributes.addFlashAttribute("removeMsg", "Driver record has been removed successfully");
		return new ModelAndView("redirect:./viewCITCRADriver");
	}

}
