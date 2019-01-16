package com.chest.currency.controller;

import java.io.StringReader;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chest.currency.entity.model.IcmcPrinter;
import com.chest.currency.entity.model.LamRequestLog;
import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.co.LamIntegrationCo;
import com.chest.currency.entity.model.co.QueryRequestCo;
import com.chest.currency.enums.LamStatus;
import com.chest.currency.enums.Status;
import com.chest.currency.message.Response;
import com.chest.currency.service.CashReceiptService;
import com.chest.currency.service.UserAdministrationService;
import com.chest.currency.util.UtilityService;

@CrossOrigin
@RestController
@RequestMapping("/api/lam/")
public class LAMIntegration {

	@Autowired
	UserAdministrationService userAdministrationService;

	@Autowired
	CashReceiptService cashReceiptService;

	PasswordEncoder encoder = new BCryptPasswordEncoder();

	private static final Logger LOG = LoggerFactory.getLogger(UtilityService.class);

	@RequestMapping(value = "user", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public Response getRequest(@RequestParam("data") @Valid String requestData, HttpServletRequest request)
			throws UnknownHostException {
		QueryRequestCo queryRequestCo = JAXB.unmarshal(new StringReader(requestData.replaceAll("%20", " ")),
				QueryRequestCo.class);
		LOG.info("requestData " + queryRequestCo);
		try {
			return setRequest(queryRequestCo, request);
		} catch (Exception e) {
			return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(),
					"Exception Please check Request Data " + e.getLocalizedMessage());
		}
	}

	@ResponseBody
	public Response setRequest(@RequestBody @Valid QueryRequestCo queryRequestCo, HttpServletRequest request)
			throws UnknownHostException {
		LamIntegrationCo lamIntegrationCo = new LamIntegrationCo();
		lamIntegrationCo.setQueryrequest(queryRequestCo);
		if (queryRequestCo.getAccessRequest() == null || queryRequestCo.getActivity() == null) {
			LamRequestLog requestLog = UtilityService.setLamRequestLog(lamIntegrationCo.getQueryrequest(), request);
			requestLog.setResponse(LamStatus.FAILURE);
			userAdministrationService.createLamLog(requestLog);
			return Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode(),
					"Please check activity or Access Request");
		}
		return verifyActivity(lamIntegrationCo, request);
	}

	@ResponseBody
	public Response verifyActivity(@RequestBody @Valid LamIntegrationCo lamIntegrationCo, HttpServletRequest request)
			throws UnknownHostException {
		LOG.info("lamIntegrationCo " + lamIntegrationCo);
		String activity = lamIntegrationCo.getQueryrequest().getActivity();
		LOG.info("activity " + activity);
		Response response = null;
		switch (activity.toUpperCase()) {
		case "I":
			lamIntegrationCo.getQueryrequest().setActivity("CREATE");
			response = addUser(lamIntegrationCo, request);
			break;
		case "U":
			lamIntegrationCo.getQueryrequest().setActivity("MODIFY");
			response = updateUser(lamIntegrationCo, request);
			break;
		case "UN":
			lamIntegrationCo.getQueryrequest().setActivity("UNLOCK");
			response = unlockUser(lamIntegrationCo, request);
			break;
		case "D":
			lamIntegrationCo.getQueryrequest().setActivity("DELETE");
			response = deleteUser(lamIntegrationCo, request);
			break;
		default:
			LamRequestLog requestLog = UtilityService.setLamRequestLog(lamIntegrationCo.getQueryrequest(), request);
			requestLog.setResponse(LamStatus.FAILURE);
			userAdministrationService.createLamLog(requestLog);
			response = Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode(),
					"Activity should be I,U,UN,D.");
			break;
		}
		return response;
	}

	@ResponseBody
	public Response addUser(@RequestBody @Valid LamIntegrationCo lamIntegrationCo, HttpServletRequest request)
			throws UnknownHostException {
		String activity = lamIntegrationCo.getQueryrequest().getActivity();

		LamRequestLog requestLog = UtilityService.setLamRequestLog(lamIntegrationCo.getQueryrequest(), request);
		requestLog = userAdministrationService.createLamLog(requestLog);

		if (!activity.equalsIgnoreCase("CREATE")) {
			requestLog.setResponse(LamStatus.FAILURE);
			userAdministrationService.updateLamLog(requestLog);
			return Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode(),
					"Please check activity or other field");
		}
		User user = null;
		try {
			user = UtilityService.setUserDetail(lamIntegrationCo.getQueryrequest(), cashReceiptService
					.getICMCByName(getBranchName(lamIntegrationCo.getQueryrequest().getAccessRequest())));
		} catch (Exception e) {
			LOG.info("Exception " + e);
			return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(),
					"Branch not exist in DB please check Branch name");
		}
		User existingUser = userAdministrationService.isUserExists(user.getId());
		if (existingUser != null && (existingUser.getStatus().equals(Status.ENABLED)
				|| existingUser.getStatus().equals(Status.DISABLED))) {
			requestLog.setResponse(LamStatus.FAILURE);
			userAdministrationService.updateLamLog(requestLog);
			return Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode(),
					"User already exists with status of " + existingUser.getStatus());
		}
		userAdministrationService.deleteUser(user);
		IcmcPrinter icmcPrinter = userAdministrationService.getPrinter(user);
		if (icmcPrinter == null)
			return Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode(),
					"Please add printer for this Branch");
		user.setIcmcPrinter(icmcPrinter);
		userAdministrationService.createUser(user, request.getRequestURL().toString());
		requestLog.setResponse(LamStatus.SUCCESS);
		requestLog.setUpdatedDateTime(Calendar.getInstance());
		userAdministrationService.updateLamLog(requestLog);

		return Response.setSuccessResponse(LamStatus.SUCCESS, LamStatus.SUCCESS.getCode(), "user created successfully");
	}

	@ResponseBody
	public Response updateUser(@RequestBody @Valid LamIntegrationCo lamIntegrationCo, HttpServletRequest request)
			throws UnknownHostException {
		String activity = lamIntegrationCo.getQueryrequest().getActivity();

		LamRequestLog requestLog = UtilityService.setLamRequestLog(lamIntegrationCo.getQueryrequest(), request);
		requestLog = userAdministrationService.createLamLog(requestLog);

		if (!activity.equalsIgnoreCase("MODIFY")) {
			requestLog.setResponse(LamStatus.FAILURE);
			userAdministrationService.updateLamLog(requestLog);
			return Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode(),
					"Please check activity or other field");
		}
		User userDb = userAdministrationService
				.getUserById(getUserId(lamIntegrationCo.getQueryrequest().getAccessRequest()));
		if (userDb == null) {
			requestLog.setResponse(LamStatus.EXCEPTION);
			userAdministrationService.updateLamLog(requestLog);
			return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(),
					"User does not exist");
		}
		User user = null;
		try {
			user = UtilityService.setUserDetail(lamIntegrationCo.getQueryrequest(), cashReceiptService
					.getICMCByName(getBranchName(lamIntegrationCo.getQueryrequest().getAccessRequest())));
		} catch (Exception e) {
			return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(),
					"new Branch not exist in DB please check Branch name");
		}
		IcmcPrinter icmcPrinter = userAdministrationService.getPrinter(user);
		if (icmcPrinter == null)
			return Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode(),
					"Please add printer for this Branch");
		user.setIcmcPrinter(icmcPrinter);
		user.setPassword(userDb.getPassword());
		user.setEmail(userDb.getEmail());
		userAdministrationService.updateUser(user);
		requestLog.setResponse(LamStatus.SUCCESS);
		userAdministrationService.updateLamLog(requestLog);

		return Response.setSuccessResponse(LamStatus.SUCCESS, LamStatus.SUCCESS.getCode(), "user successfully update");
	}

	@ResponseBody
	public Response unlockUser(@RequestBody @Valid LamIntegrationCo lamIntegrationCo, HttpServletRequest request)
			throws UnknownHostException {
		String activity = lamIntegrationCo.getQueryrequest().getActivity();

		LamRequestLog requestLog = UtilityService.setLamRequestLog(lamIntegrationCo.getQueryrequest(), request);
		requestLog = userAdministrationService.createLamLog(requestLog);

		if (!activity.equalsIgnoreCase("UNLOCK")) {
			requestLog.setResponse(LamStatus.FAILURE);
			userAdministrationService.updateLamLog(requestLog);
			return Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode(),
					"Please check activity or other field");
		}
		User user = userAdministrationService
				.getUserById(getUserId(lamIntegrationCo.getQueryrequest().getAccessRequest()));
		if (user == null) {
			requestLog.setResponse(LamStatus.EXCEPTION);
			userAdministrationService.updateLamLog(requestLog);
			return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(),
					"User does not exist");
		}
		user.setStatus(Status.ENABLED);
		userAdministrationService.updateUser(user);
		requestLog.setResponse(LamStatus.SUCCESS);
		userAdministrationService.updateLamLog(requestLog);

		return Response.setSuccessResponse(LamStatus.SUCCESS, LamStatus.SUCCESS.getCode(), "User successfully unlock");
	}

	@ResponseBody
	public Response deleteUser(@RequestBody @Valid LamIntegrationCo lamIntegrationCo, HttpServletRequest request)
			throws UnknownHostException {
		String activity = lamIntegrationCo.getQueryrequest().getActivity();

		LamRequestLog requestLog = UtilityService.setLamRequestLog(lamIntegrationCo.getQueryrequest(), request);
		requestLog = userAdministrationService.createLamLog(requestLog);

		if (!activity.equalsIgnoreCase("DELETE")) {
			requestLog.setResponse(LamStatus.FAILURE);
			userAdministrationService.updateLamLog(requestLog);
			return Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode(),
					"Please check activity or other field");
		}
		User user = userAdministrationService
				.getUserById(getUserId(lamIntegrationCo.getQueryrequest().getAccessRequest()));
		if (user == null) {
			requestLog.setResponse(LamStatus.EXCEPTION);
			userAdministrationService.updateLamLog(requestLog);
			return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(),
					"User does not exist");
		}
		user.setStatus(Status.DELETED);
		userAdministrationService.updateUser(user);
		requestLog.setResponse(LamStatus.SUCCESS);
		userAdministrationService.updateLamLog(requestLog);

		return Response.setSuccessResponse(LamStatus.SUCCESS, LamStatus.SUCCESS.getCode(), "user successfully Deleted");
	}

	private String getBranchName(String accessRequest) {
		String solId = accessRequest;
		String[] userDetails = solId.split(Pattern.quote("|"));
		if (userDetails.length > 7) {
			return userDetails[6];
		}
		return userDetails[4];
	}

	private String getUserId(String accessRequest) {
		String userId = accessRequest;
		String[] userDetails = userId.split(Pattern.quote("|"));
		return userDetails[2];
	}

}
