package com.chest.currency.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.co.QueryRequestCo;
import com.chest.currency.enums.ServiceStatus;
import com.chest.currency.message.Response;
import com.chest.currency.service.UserAdministrationService;

@CrossOrigin
@RestController
@RequestMapping("/api/lam")
public class GlobalIntegration {

	@Autowired
	UserAdministrationService userAdministrationService;

	@RequestMapping(value = "/user", method = RequestMethod.GET, consumes = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public List<User> getUser(HttpServletRequest request) {

		List<User> list = new ArrayList<User>();
		list = userAdministrationService.getUserList();
		System.out.println("getRemoteAddr " + request.getRemoteAddr());
		return list;
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public QueryRequestCo addUser(@RequestBody @Valid QueryRequestCo queryRequestCo, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			System.out.println("bindingResult.hasErrors() " + bindingResult.hasErrors());
		}
		System.out.println("getQueryRequest " + queryRequestCo.getQueryRequest());
		System.out.println("getQueryRequest " + queryRequestCo.toString());

		//return Response.setSuccessResponse(ServiceStatus.SUCCESS, ServiceStatus.SUCCESS.getCode());
		return queryRequestCo;
	}
}
