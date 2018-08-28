package com.chest.currency.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = NullPointerException.class)
	public ModelAndView handleNullPointerException(NullPointerException e) {
		logger.error("NullPointerException handler executed");
		ModelMap map=new ModelMap();
		map.put("status", "");
		map.put("message", e.getMessage());
		map.put("GlobalExceptionMessage", "NullPointerException handler executed");
		return new ModelAndView("GlobalExceptionHandler",map);
	}

	@ExceptionHandler(value = IOException.class)
	public ModelAndView handleIOException(IOException e) {
		logger.error("IOException handler executed");
		ModelMap map=new ModelMap();
		map.put("status", HttpStatus.FORBIDDEN);
		map.put("message", e.getMessage());
		map.put("GlobalExceptionMessage", "IOException handler executed");
		return new ModelAndView("GlobalExceptionHandler",map);
	}

	@ExceptionHandler(value = SQLException.class)
	public ModelAndView handle(HttpServletRequest request, SQLException e) {
		logger.info("SQLException Occured:: URL=" + request.getRequestURL());
		ModelMap map=new ModelMap();
		map.put("status", HttpStatus.BAD_REQUEST);
		map.put("message", e.getMessage());
		map.put("GlobalExceptionMessage", "SQLExceptioneption handler executed");
		return new ModelAndView("GlobalExceptionHandler",map);
	}

	@ExceptionHandler(value = ClassNotFoundException.class)
	public ModelAndView handleClassNotFoundException(HttpServletRequest request, ClassNotFoundException e) {
		logger.info("ClassNotFoundException Occured:: URL=" + request.getRequestURL());
		ModelMap map=new ModelMap();
		map.put("status", HttpStatus.NOT_FOUND);
		map.put("message", e.getMessage());
		map.put("GlobalExceptionMessage", "ClassNotFoundException handler executed");
		return new ModelAndView("GlobalExceptionHandler",map);
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleException(Exception e) {
		ModelMap map=new ModelMap();
		map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
		map.put("message", e.getMessage());
		map.put("GlobalExceptionMessage", "Exception handler executed");
		logger.error("Exception handler executed");
		return new ModelAndView("GlobalExceptionHandler",map);
	}
}
