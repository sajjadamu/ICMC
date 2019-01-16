package com.chest.currency.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chest.currency.entity.model.DateRange;
import com.chest.currency.entity.model.DelegateRight;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.PasswordReset;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.IcmcAccess;
import com.chest.currency.enums.Status;
import com.chest.currency.service.ICMCService;
import com.chest.currency.service.ProcessingRoomService;
import com.chest.currency.service.UserAdministrationService;
import com.chest.currency.service.UserService;
import com.chest.currency.util.UtilityJpa;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
public class UserLoginController {

	private static final Logger LOG = LoggerFactory.getLogger(UserLoginController.class);

	@Autowired
	UserService userService;

	@Autowired
	UserAdministrationService userAdministrationService;

	@Autowired
	ICMCService iCMCService;

	@Autowired
	ProcessingRoomService processingRoomService;

	PasswordEncoder encoder = new BCryptPasswordEncoder();

	@Value("#{servletContext.contextPath}")
	private String servletContextPath;

	@RequestMapping(value = "/homeAction", method = RequestMethod.GET)
	public ModelAndView homeAction(@ModelAttribute("reportDate") DateRange dateRange, HttpServletRequest request,
			HttpSession session) {
		ModelMap map = new ModelMap();
		String baseUrl = getBaseUrl(request);
		if (StringUtils.isEmpty(servletContextPath)) {
			servletContextPath = baseUrl;
		}
		Calendar dateEOD = null;
		Calendar dateBinTxn = null;
		String parsedEodDate = null;
		String parsedBinTxnDate = null;
		User user = (User) session.getAttribute("login");

		// VAULT CUSTODIAN NOTIFICATION
		String craMsg, msgSAS, msgIndent, msgOtherBank, msgSoiled, msgDiversion = "";
		LOG.info("user " + user);
		LOG.info("user.getRole().getIcmcAccess() " + user.getRole().getIcmcAccess());
		LOG.info("user.getRole().getIcmcAccess().equals(IcmcAccess.ICMC) "
				+ user.getRole().getIcmcAccess().equals(IcmcAccess.ICMC));
		if (user.getRole().getIcmcAccess().equals(IcmcAccess.ICMC)) {
			// List<BinTransactionBOD> lastEOD=
			// userAdministrationService.getLastEODData(user.getIcmcId());
			dateBinTxn = userAdministrationService.getNotificationFromBinTransactionForEOD(user.getIcmcId());
			LOG.info("Calendar date from binTransaction " + dateBinTxn);
			if (dateBinTxn != null) {
				LOG.info("Calendar date from binTransaction " + dateBinTxn.getTime());
				parsedBinTxnDate = parsingDateFormate(dateBinTxn.getTime());
				map.put("binTransactionDate", parsingDateFormate(dateBinTxn.getTime()));

				Calendar sDate = (Calendar) dateBinTxn.clone();
				Calendar eDate = (Calendar) dateBinTxn.clone();
				UtilityJpa.setStartDate(sDate);
				UtilityJpa.setEndDate(eDate);

				LOG.info("sDate from binTransaction " + sDate.getTime());
				LOG.info("eDate from binTransaction " + eDate.getTime());
				dateEOD = userAdministrationService.getNotificationFromBinTransactionBODForEOD(user.getIcmcId(), sDate,
						eDate);
				if (dateEOD != null) {
					map.put("binTransactioEODDate", parsingDateFormate(dateEOD.getTime()));
					parsedEodDate = parsingDateFormate(dateEOD.getTime());
				} else {
					map.put("binTransactioEODDate", dateEOD);
				}
				LOG.info("dateEOD Controller from binTransaction " + dateEOD);
			}

			// Notification pending bundle
			msgSAS = userAdministrationService.getNotificationFromSASAllocation(user.getIcmcId());
			msgIndent = userAdministrationService.getNotificationFromIndent(user.getIcmcId());
			msgOtherBank = userAdministrationService.getNotificationFromOtherBankAllocation(user.getIcmcId());
			msgSoiled = userAdministrationService.getNotificationFromSoiledAllocation(user.getIcmcId());
			msgDiversion = userAdministrationService.getNotificationFromDiversion(user.getIcmcId());
			craMsg = userAdministrationService.getNotificationFromCRA(user.getIcmcId());

			dateBinTxn = userAdministrationService.getNotificationFromBinTransactionForEOD(user.getIcmcId());
			LOG.info("Calendar date from binTransaction " + dateBinTxn);
			if (dateBinTxn != null) {
				map.put("binTransactionDate", parsingDateFormate(dateBinTxn.getTime()));
				parsedBinTxnDate = parsingDateFormate(dateBinTxn.getTime());
				Calendar sDate = (Calendar) dateBinTxn.clone();
				Calendar eDate = (Calendar) dateBinTxn.clone();
				UtilityJpa.setStartDate(sDate);
				UtilityJpa.setEndDate(eDate);

				LOG.info("dateBinTxn.getTime() " + dateBinTxn.getTime());
				LOG.info("sDate from binTransaction " + sDate.getTime());
				LOG.info("eDate from binTransaction " + eDate.getTime());
				dateEOD = userAdministrationService.getNotificationFromBinTransactionBODForEOD(user.getIcmcId(), sDate,
						eDate);
				if (dateEOD != null) {
					map.put("binTransactioEODDate", parsingDateFormate(dateEOD.getTime()));
					parsedEodDate = parsingDateFormate(dateBinTxn.getTime());
				} else {
					map.put("binTransactioEODDate", dateEOD);
				}
				LOG.info("dateEOD Controller from binTransaction " + dateEOD);
			}

			// Notification pending bundle
			msgSAS = userAdministrationService.getNotificationFromSASAllocation(user.getIcmcId());
			msgIndent = userAdministrationService.getNotificationFromIndent(user.getIcmcId());
			msgOtherBank = userAdministrationService.getNotificationFromOtherBankAllocation(user.getIcmcId());
			msgSoiled = userAdministrationService.getNotificationFromSoiledAllocation(user.getIcmcId());
			msgDiversion = userAdministrationService.getNotificationFromDiversion(user.getIcmcId());
			craMsg = userAdministrationService.getNotificationFromCRA(user.getIcmcId());

			// Machine Allocation Pending
			String pendingMachineAlo = userAdministrationService.getNotificationForMachineAllocation(user.getIcmcId());

			// Processing Output Pending
			String processingOutPutPending = userAdministrationService
					.getNotificationForProcessingOutPut(user.getIcmcId());
			UtilityJpa.setNotification(pendingMachineAlo, processingOutPutPending, msgSAS, msgIndent, msgOtherBank,
					msgSoiled, msgDiversion, craMsg, map);

		}
		// Close NOTIFICATION CODE
		LOG.info("dateEOD  " + dateEOD);
		LOG.info("dateBinTxn " + dateBinTxn);

		if (parsedEodDate == null && parsedBinTxnDate != null
				&& (!parsingDateFormate(new Date()).equals(parsedBinTxnDate))) {
			return new ModelAndView("welcomeEOD", map);
		}
		return new ModelAndView("welcome", map);
	}

	private String getBaseUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		url = url.substring(0, url.lastIndexOf("/"));
		return url;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("login");
		return model;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:./login";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.POST)
	public String executeLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("userBean") User user, HttpSession session) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// ModelAndView model = null;
		try {
			user = userAdministrationService
					.getUserById(((org.springframework.security.core.userdetails.User) principal).getUsername());
			DelegateRight delegateRight = null;
			if (user.getIcmcId() != null) {
				delegateRight = userAdministrationService.getRoleFromDelegatedRights(user.getIcmcId());
			}
			if (user != null) {
				userAdministrationService.resetFailAttempts(user);

				IcmcAccess access = null;

				if (delegateRight != null) {

					// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd
					// HH:mm:ss");
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date date = new Date();
					System.out.println(dateFormat.format(date));
					String current = dateFormat.format(date);

					Date startDate = new Date();

					startDate = new SimpleDateFormat("yyyy-MM-dd").parse(current);
					Date currentDate = new Date();
					Date endDate = new Date();
					endDate = delegateRight.getPeriodTo();
					LOG.info("currentDate " + currentDate);
					LOG.info("startDate " + startDate);
					LOG.info("endDate " + endDate);
					if (currentDate.after(startDate) && currentDate.before(endDate)) {
						LOG.info("in between ");
						if (user.getId().equalsIgnoreCase(delegateRight.getUserId())) {
							access = delegateRight.getRole().getIcmcAccess();
							user.setRole(delegateRight.getRole());
						}
					} else {
						LOG.info("out of range ");
					}

				}

				access = user.getRole().getIcmcAccess();

				if (access == IcmcAccess.ICMC) {
					if (user.getIcmcId() != null) {
						ICMC icmc = iCMCService.getICMCById(user.getIcmcId().longValue());
						if (icmc != null) {
							session.setAttribute("icmcName", icmc.getName());
						}
					}

				} else if (access == IcmcAccess.REGION) {
					if (user.getRegionId() != null) {
						session.setAttribute("icmcName", user.getRegionId());
					}

				} else if (access == IcmcAccess.ZONE) {
					if (user.getZoneId() != null) {
						session.setAttribute("icmcName", user.getZoneId());
					}

				} else if (access == IcmcAccess.ALL) {
					session.setAttribute("icmcName", "INDIA");

				}

				session.setAttribute("loggedInUser", user.getRole());
				session.setAttribute("login", user);
				session.setAttribute("loggedInUserName", user.getName());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:./homeAction";
	}

	@RequestMapping(value = "/resetPasswordRequest")
	public ModelAndView resetPassword(@ModelAttribute("passwordReset") PasswordReset passwordReset) {
		return new ModelAndView("resetPasswordRequest");
	}

	@RequestMapping(value = "/resetPasswordRequest", method = RequestMethod.POST)
	public ModelAndView resetPassword(@ModelAttribute("passwordReset") PasswordReset passwordReset,
			RedirectAttributes redirectAttributes, HttpServletRequest httpRequest) {
		ModelMap map = new ModelMap();
		User user = null;
		if (passwordReset == null || StringUtils.isEmpty(passwordReset.getUserId())) {
			map.put("errorMsg", "Please Enter User Id");
			return new ModelAndView("resetPasswordRequest", map);
		} else {
			user = userAdministrationService.getUserById(passwordReset.getUserId());
			if (user == null || Status.DELETED == user.getStatus() || Status.DISABLED == user.getStatus()) {
				map.put("errorMsg", "Invalid User");
				return new ModelAndView("resetPasswordRequest", map);
			}
		}

		String path = httpRequest.getRequestURL().toString();
		userAdministrationService.savePasswordReset(user, passwordReset, path);
		// userAdministrationService.savePasswordReset(user, passwordReset,
		// servletContextPath);

		map.put("successMsg", "Reset Password link has been sent to your email successfully");
		return new ModelAndView("resetPasswordRequest", map);
	}

	@RequestMapping(value = "/resetPassword")
	public ModelAndView resetPassword(@ModelAttribute("passwordReset") PasswordReset passwordReset,
			RedirectAttributes redirectAttributes, HttpSession session) {
		ModelMap map = new ModelMap();
		boolean isValidUser = true;
		PasswordReset passwordResetDb = null;
		if (passwordReset == null || StringUtils.isEmpty(passwordReset.getToken())
				|| StringUtils.isEmpty(passwordReset.getUserId())) {
			isValidUser = false;
		} else {
			// User user =
			// userAdministrationService.getActiveUserById(passwordReset.getUserId());
			User user = userAdministrationService.getUserById(passwordReset.getUserId());
			passwordResetDb = userAdministrationService.getPasswordResetByIdAndToken(passwordReset.getUserId(),
					passwordReset.getToken());
			if (user == null || Status.DELETED == user.getStatus() || Status.DISABLED == user.getStatus()
					|| passwordResetDb == null) {
				isValidUser = false;
			}
		}
		if (!isValidUser) {
			map.put("errorMsg", "Invalid Request");
			return new ModelAndView("resetPassword", map);
		}

		session.setAttribute("passwordReset", passwordResetDb);
		return new ModelAndView("resetPassword");
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ModelAndView updatePassword(@ModelAttribute("passwordReset") PasswordReset passwordResetFormObj,
			RedirectAttributes redirectAttributes, HttpSession session) {
		PasswordReset passwordResetSession = (PasswordReset) session.getAttribute("passwordReset");
		ModelMap map = new ModelMap();
		if (passwordResetSession == null || StringUtils.isEmpty(passwordResetSession.getUserId())) {
			map.put("errorMsg", "Invalid Request");
			return new ModelAndView("resetPassword", map);
		}

		if (StringUtils.isEmpty(passwordResetFormObj.getPassword())
				|| StringUtils.isEmpty(passwordResetFormObj.getRePassword())
				|| !passwordResetFormObj.getRePassword().equals(passwordResetFormObj.getPassword())) {
			map.put("errorMsg", "Password and Confirm Password should match.");
			map.put("passwordReset", passwordResetFormObj);
			return new ModelAndView("resetPassword", map);
		}

		// User user =
		// userAdministrationService.getActiveUserById(passwordReset.getUserId());
		User user = userAdministrationService.getUserById(passwordResetSession.getUserId());
		if (user != null) {
			user.setUpdatedDateTime(Calendar.getInstance());
			user.setPassword(encoder.encode(passwordResetFormObj.getPassword()));
			userAdministrationService.updateUser(user);

			Calendar now = Calendar.getInstance();
			passwordResetSession.setUpdatedDateTime(now);
			passwordResetSession.setStatus(Status.DISABLED);
			userAdministrationService.updatePasswordReset(passwordResetSession);

			redirectAttributes.addFlashAttribute("successMsg", "Password updated successfully");
		}
		return new ModelAndView("redirect:./login");
	}

	@RequestMapping("/viewLockedUser")
	public ModelAndView viewLockedUser() {
		List<User> lockedUserList = new ArrayList<User>();
		lockedUserList = userAdministrationService.getLockedUserList();
		return new ModelAndView("viewLockedUser", "records", lockedUserList);
	}

	@RequestMapping(value = "/unlockLockedUser")
	@ResponseBody
	public ModelAndView unlockLockedUser(@RequestParam(value = "id") String id, HttpServletRequest request) {
		boolean isAllSuccess = userAdministrationService.unlockLockedUser(id, request.getRequestURL().toString());
		if (!isAllSuccess) {
			throw new RuntimeException("Error while unlocking Locked User");
		}
		return new ModelAndView("redirect:./viewLockedUser");
	}

	private String parsingDateFormate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String parsedDate = null;
		if (date != null) {
			parsedDate = sdf.format(date);
		}
		return parsedDate;
	}
}