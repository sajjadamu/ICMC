package com.chest.currency.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.chest.currency.enums.LamStatus;
import com.chest.currency.message.Error;
import com.chest.currency.message.ErrorCode;
import com.chest.currency.message.Response;

/*@ControllerAdvice
@CrossOrigin*/
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	private final static Logger LOG = LoggerFactory.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseBody
	public Response AuthAPIException(HttpClientErrorException httpEx, HttpServletRequest request) {
		if (httpEx != null)
			httpEx.printStackTrace();
		return Error.setErrorResponse(LamStatus.EXCEPTION, ErrorCode.UNAUTHORIZED.getCode(), "Authentication Fail.");
	}

	@ExceptionHandler(java.io.IOException.class)
	@ResponseBody
	public Response IOException(java.io.IOException ioEx, HttpServletRequest request) {
		if (ioEx != null)
			ioEx.printStackTrace();

		return new Response();
	}

	@ExceptionHandler(ClassNotFoundException.class)
	@ResponseBody
	public Response ClassNotFoundException(HttpServletRequest request) {
		LOG.info("========ClassNotFoundException=============");
		/*
		 * return Error.setErrorResponse(LamStatus.EXCEPTION,
		 * ErrorCode.NOT_FOUND.getCode(), "Class Not Found .");
		 */
		return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(), "Class Not Found .");
	}

	@ExceptionHandler(ClassCastException.class)
	@ResponseBody
	public Response ClassCastException(HttpServletRequest request) {
		LOG.info("========ClassCastException=============");
		return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(), "Class Cast Exception.");
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	@ResponseBody
	public Response IndexOutOfBoundsException(IndexOutOfBoundsException e, HttpServletRequest request) {
		LOG.info("========IndexOutOfBoundsException=============");
		LOG.info("========Exception request============= " + request.getRequestURL().toString());
		String requestUrl = request.getRequestURL().toString();
		String[] splitedUrl = requestUrl.split("/");
		LOG.info("========splited request url============= " + splitedUrl[4]);
		LOG.info("========splited request url============= " + splitedUrl[5]);
/*		if (!splitedUrl[5].equalsIgnoreCase("api"))
			globalExceptionHandler.IndexOutOfBoundsException(e, request);*/
		return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(),
				"Index Please check Roll or other field .");

	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	@ResponseBody
	public Response TransientPropertyValueException(HttpServletRequest request) {
		LOG.info("========TransientPropertyValueException=============");
		/*
		 * return Error.setErrorResponse(LamStatus.EXCEPTION,
		 * ErrorCode.BAD_REQUEST.getCode(),
		 * "Role is not Define or check other field");
		 */
		return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(),
				"Transient Please check Roll or other field .");
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	public Response NullPointerException(HttpServletRequest request, Exception e) {
		LOG.info("========NullPointerException============= " + e.getClass().getSimpleName());
		/*
		 * return Error.setErrorResponse(LamStatus.EXCEPTION,
		 * ErrorCode.BAD_REQUEST.getCode(),
		 * "Role is not Define or check other field");
		 */
		return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(),
				"NULL Please check Roll or other field .");
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Response Exception(HttpServletRequest request, Exception e) {
		LOG.info("========Exception getSimpleName============= " + e.getClass().getSimpleName());
		LOG.info("========Exception getMessage============= " + e.getMessage());
		LOG.info("========Exception e============= " + e);
		// return Error.setErrorResponse(LamStatus.EXCEPTION,
		// ErrorCode.BAD_REQUEST.getCode(), "Please check Request");
		LOG.info("========Exception request============= " + request.getRemoteHost());
		return Response.setSuccessResponse(LamStatus.EXCEPTION, LamStatus.EXCEPTION.getCode(),
				"Master e Please check Request .");
	}
}
