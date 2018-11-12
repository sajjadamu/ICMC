package com.chest.currency.controller;

import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/lam/user")
public class GlobalIntegration {

	@Autowired
	UserAdministrationService userAdministrationService;

	@Autowired
	CashReceiptService cashReceiptService;

	PasswordEncoder encoder = new BCryptPasswordEncoder();

	private static final Logger LOG = LoggerFactory.getLogger(UtilityService.class);

	/*
	 * @RequestMapping(value = "/create", method = RequestMethod.POST, consumes =
	 * MediaType.APPLICATION_XML_VALUE)
	 * 
	 * @ResponseBody public Response addUser(@RequestBody @Valid QueryRequestCo
	 * queryRequestCo, BindingResult bindingResult, HttpServletRequest request)
	 * throws UnknownHostException { Activity activity = (Activity)
	 * queryRequestCo.getQueryRequest().get("activity");
	 * 
	 * LamRequestLog requestLog =
	 * UtilityService.setLamServiceLog(queryRequestCo.getQueryRequest(), request);
	 * requestLog = userAdministrationService.createLamLog(requestLog);
	 * 
	 * if (bindingResult.hasErrors() || !activity.equals(Activity.CREATE)) {
	 * requestLog.setResponse(LamStatus.FAILURE);
	 * userAdministrationService.updateLamLog(requestLog); return
	 * Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode());
	 * } User user = UtilityService.setUserDetails(queryRequestCo.getQueryRequest(),
	 * cashReceiptService.getICMCBySolId(getSolId(queryRequestCo.getQueryRequest().
	 * get("accessRequest"))));
	 * 
	 * if (userAdministrationService.isValidUser(user.getId()) != null) {
	 * requestLog.setResponse(LamStatus.EXCEPTION);
	 * userAdministrationService.updateLamLog(requestLog); return
	 * Response.setSuccessResponse(LamStatus.EXCEPTION,
	 * LamStatus.EXCEPTION.getCode()); } userAdministrationService.createUser(user,
	 * request.getRequestURL().toString());
	 * requestLog.setResponse(LamStatus.SUCCESS);
	 * userAdministrationService.updateLamLog(requestLog);
	 * 
	 * return Response.setSuccessResponse(LamStatus.SUCCESS,
	 * LamStatus.SUCCESS.getCode()); }
	 */

	/*
	 * @RequestMapping(value = "/modify", method = RequestMethod.PUT, consumes =
	 * MediaType.APPLICATION_XML_VALUE)
	 * 
	 * @ResponseBody public Response updateUser(@RequestBody @Valid QueryRequestCo
	 * queryRequestCo, BindingResult bindingResult, HttpServletRequest request)
	 * throws UnknownHostException { Activity activity = (Activity)
	 * queryRequestCo.getQueryRequest().get("activity");
	 * 
	 * LamRequestLog requestLog =
	 * UtilityService.setLamServiceLog(queryRequestCo.getQueryRequest(), request);
	 * requestLog = userAdministrationService.createLamLog(requestLog);
	 * 
	 * if (bindingResult.hasErrors() || !activity.equals(Activity.MODIFY)) {
	 * requestLog.setResponse(LamStatus.FAILURE);
	 * userAdministrationService.updateLamLog(requestLog); return
	 * Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode());
	 * } User userDb = userAdministrationService
	 * .getUserById(getUserId(queryRequestCo.getQueryRequest().get("accessRequest"))
	 * ) ; if (userDb == null) { requestLog.setResponse(LamStatus.EXCEPTION);
	 * userAdministrationService.updateLamLog(requestLog); return
	 * Response.setSuccessResponse(LamStatus.EXCEPTION,
	 * LamStatus.EXCEPTION.getCode()); } User user =
	 * UtilityService.setUserDetails(queryRequestCo.getQueryRequest(),
	 * cashReceiptService.getICMCBySolId(getSolId(queryRequestCo.getQueryRequest().
	 * get("accessRequest")))); user.setPassword(userDb.getPassword());
	 * userAdministrationService.updateUser(user);
	 * requestLog.setResponse(LamStatus.SUCCESS);
	 * userAdministrationService.updateLamLog(requestLog);
	 * 
	 * return Response.setSuccessResponse(LamStatus.SUCCESS,
	 * LamStatus.SUCCESS.getCode()); }
	 */

	/*
	 * @RequestMapping(value = "/unlock", method = RequestMethod.PUT, consumes =
	 * MediaType.APPLICATION_XML_VALUE)
	 * 
	 * @ResponseBody public Response unlockUser(@RequestBody @Valid QueryRequestCo
	 * queryRequestCo, BindingResult bindingResult, HttpServletRequest request)
	 * throws UnknownHostException { Activity activity = (Activity)
	 * queryRequestCo.getQueryRequest().get("activity");
	 * 
	 * LamRequestLog requestLog =
	 * UtilityService.setLamServiceLog(queryRequestCo.getQueryRequest(), request);
	 * requestLog = userAdministrationService.createLamLog(requestLog);
	 * 
	 * if (bindingResult.hasErrors() || !activity.equals(Activity.UNLOCK)) {
	 * requestLog.setResponse(LamStatus.FAILURE);
	 * userAdministrationService.updateLamLog(requestLog); return
	 * Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode());
	 * } User user = userAdministrationService
	 * .getUserById(getUserId(queryRequestCo.getQueryRequest().get("accessRequest"))
	 * ) ; if (user == null) { requestLog.setResponse(LamStatus.EXCEPTION);
	 * userAdministrationService.updateLamLog(requestLog); return
	 * Response.setSuccessResponse(LamStatus.EXCEPTION,
	 * LamStatus.EXCEPTION.getCode()); } user.setStatus(Status.DISABLED);
	 * userAdministrationService.updateUser(user);
	 * requestLog.setResponse(LamStatus.SUCCESS);
	 * userAdministrationService.updateLamLog(requestLog);
	 * 
	 * return Response.setSuccessResponse(LamStatus.SUCCESS,
	 * LamStatus.SUCCESS.getCode()); }
	 */

	/*
	 * @RequestMapping(value = "/delete", method = RequestMethod.PUT, consumes =
	 * MediaType.APPLICATION_XML_VALUE)
	 * 
	 * @ResponseBody public Response deleteUser(@RequestBody @Valid QueryRequestCo
	 * queryRequestCo, BindingResult bindingResult, HttpServletRequest request)
	 * throws UnknownHostException { Activity activity = (Activity)
	 * queryRequestCo.getQueryRequest().get("activity");
	 * 
	 * LamRequestLog requestLog =
	 * UtilityService.setLamServiceLog(queryRequestCo.getQueryRequest(), request);
	 * requestLog = userAdministrationService.createLamLog(requestLog);
	 * 
	 * if (bindingResult.hasErrors() || !activity.equals(Activity.DELETE)) {
	 * requestLog.setResponse(LamStatus.FAILURE);
	 * userAdministrationService.updateLamLog(requestLog); return
	 * Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode());
	 * } User user = userAdministrationService
	 * .getUserById(getUserId(queryRequestCo.getQueryRequest().get("accessRequest"))
	 * ) ; if (user == null) { requestLog.setResponse(LamStatus.EXCEPTION);
	 * userAdministrationService.updateLamLog(requestLog); return
	 * Response.setSuccessResponse(LamStatus.EXCEPTION,
	 * LamStatus.EXCEPTION.getCode()); } userAdministrationService.deleteUser(user);
	 * requestLog.setResponse(LamStatus.SUCCESS);
	 * userAdministrationService.updateLamLog(requestLog);
	 * 
	 * return Response.setSuccessResponse(LamStatus.SUCCESS,
	 * LamStatus.SUCCESS.getCode()); }
	 */

	@RequestMapping(value = "/user1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public QueryRequestCo addUserTest1(@RequestBody @Valid QueryRequestCo queryRequestCo, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			System.out.println("bindingResult.hasErrors() " + bindingResult.hasErrors());
		}
		return queryRequestCo;
	}

	@RequestMapping(value = "/user2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public LamIntegrationCo addUserTest2(@RequestBody LamIntegrationCo globalRequestCo, HttpServletRequest request) {
		LOG.info("globalRequestCo map" + globalRequestCo.getQueryRequest());
		LOG.info("globalRequestCo co" + globalRequestCo.getQueryRequest());
		User user = UtilityService.setUserDetail(globalRequestCo.getQueryrequest(),
				cashReceiptService.getICMCBySolId(getSolId(globalRequestCo.getQueryrequest().getAccessRequest())));
		System.out.println("user2 " + user);
		return globalRequestCo;
	}

	@RequestMapping(value = "/user3", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public QueryRequestCo Test2(@RequestBody QueryRequestCo queryRequestCo, HttpServletRequest request) {
		/*
		 * LOG.info("globalRequestCo map" + queryRequestCo.getQueryrequest());
		 * LOG.info("globalRequestCo co" + queryRequestCo.getQueryRequest()); User user
		 * = UtilityService.setUserDetails(queryRequestCo.getQueryRequest(),
		 * cashReceiptService.getICMCBySolId(getSolId(queryRequestCo.getQueryrequest().
		 * getAccessRequest()))); System.out.println("user2 " + user);
		 */
		return queryRequestCo;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public Response addUser(@RequestBody @Valid LamIntegrationCo lamIntegrationCo, BindingResult bindingResult,
			HttpServletRequest request) throws UnknownHostException {
		String activity = lamIntegrationCo.getQueryrequest().getActivity();

		LamRequestLog requestLog = UtilityService.setLamRequestLog(lamIntegrationCo.getQueryrequest(), request);
		requestLog = userAdministrationService.createLamLog(requestLog);

		if (bindingResult.hasErrors() || !activity.equalsIgnoreCase("CREATE")) {
			requestLog.setResponse(LamStatus.FAILURE);
			userAdministrationService.updateLamLog(requestLog);
			return Response.setSuccessResponse(LamStatus.FAILURE, LamStatus.FAILURE.getCode(),
					"Please check activity or other field");
		}
		User user = UtilityService.setUserDetail(lamIntegrationCo.getQueryrequest(),
				cashReceiptService.getICMCBySolId(getSolId(lamIntegrationCo.getQueryrequest().getAccessRequest())));

		if (userAdministrationService.isValidUser(user.getId()) != null) {
			requestLog.setResponse(LamStatus.EXCEPTION);
			userAdministrationService.updateLamLog(requestLog);
			return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(),
					"User already exists");
		}
		userAdministrationService.createUser(user, request.getRequestURL().toString());
		requestLog.setResponse(LamStatus.SUCCESS);
		userAdministrationService.updateLamLog(requestLog);

		return Response.setSuccessResponse(LamStatus.SUCCESS, LamStatus.SUCCESS.getCode(), "user created successfully");
	}

	@RequestMapping(value = "/modify", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public Response updateUser(@RequestBody @Valid LamIntegrationCo lamIntegrationCo, BindingResult bindingResult,
			HttpServletRequest request) throws UnknownHostException {
		String activity = lamIntegrationCo.getQueryrequest().getActivity();

		LamRequestLog requestLog = UtilityService.setLamRequestLog(lamIntegrationCo.getQueryrequest(), request);
		requestLog = userAdministrationService.createLamLog(requestLog);

		if (bindingResult.hasErrors() || !activity.equalsIgnoreCase("MODIFY")) {
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
		User user = UtilityService.setUserDetail(lamIntegrationCo.getQueryrequest(),
				cashReceiptService.getICMCBySolId(getSolId(lamIntegrationCo.getQueryrequest().getAccessRequest())));
		user.setPassword(userDb.getPassword());
		userAdministrationService.updateUser(user);
		requestLog.setResponse(LamStatus.SUCCESS);
		userAdministrationService.updateLamLog(requestLog);

		return Response.setSuccessResponse(LamStatus.SUCCESS, LamStatus.SUCCESS.getCode(), "user successfully update");
	}

	@RequestMapping(value = "/unlock", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public Response unlockUser(@RequestBody @Valid LamIntegrationCo lamIntegrationCo, BindingResult bindingResult,
			HttpServletRequest request) throws UnknownHostException {
		String activity = lamIntegrationCo.getQueryrequest().getActivity();

		LamRequestLog requestLog = UtilityService.setLamRequestLog(lamIntegrationCo.getQueryrequest(), request);
		requestLog = userAdministrationService.createLamLog(requestLog);

		if (bindingResult.hasErrors() || !activity.equalsIgnoreCase("UNLOCK")) {
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

	@RequestMapping(value = "/delete", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public Response deleteUser(@RequestBody @Valid LamIntegrationCo lamIntegrationCo, BindingResult bindingResult,
			HttpServletRequest request) throws UnknownHostException {
		String activity = lamIntegrationCo.getQueryrequest().getActivity();

		LamRequestLog requestLog = UtilityService.setLamRequestLog(lamIntegrationCo.getQueryrequest(), request);
		requestLog = userAdministrationService.createLamLog(requestLog);

		if (bindingResult.hasErrors() || !activity.equalsIgnoreCase("DELETE")) {
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
		//userAdministrationService.deleteUser(user);
		user.setStatus(Status.DELETED);
		userAdministrationService.updateUser(user);
		requestLog.setResponse(LamStatus.SUCCESS);
		userAdministrationService.updateLamLog(requestLog);

		return Response.setSuccessResponse(LamStatus.SUCCESS, LamStatus.SUCCESS.getCode(), "user successfully Deleted");
	}

	private String getSolId(String accessRequest) {
		String solId = accessRequest;
		String[] userDetails = solId.split(Pattern.quote("|"));
		return userDetails[5];
	}

	private String getUserId(String accessRequest) {
		String userId = accessRequest;
		String[] userDetails = userId.split(Pattern.quote("|"));
		return userDetails[2];
	}
}
