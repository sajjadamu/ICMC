package com.chest.currency.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = NullPointerException.class)
	public ModelAndView handleNullPointerException(NullPointerException e) {
		e.printStackTrace();
		logger.error("NullPointerException handler executed" + e);
		ModelMap map = new ModelMap();
		map.put("status", "");
		map.put("message", "Try with Re-Login");
		map.put("GlobalExceptionMessage", "NullPointerException handler executed");
		return new ModelAndView("GlobalExceptionHandler", map);
	}

	@ExceptionHandler(value = IOException.class)
	public ModelAndView handleIOException(IOException e) {
		e.printStackTrace();
		logger.error("IOException handler executed" + e);
		ModelMap map = new ModelMap();
		map.put("status", HttpStatus.FORBIDDEN);
		map.put("message", e.getMessage());
		map.put("GlobalExceptionMessage", "IOException handler executed");
		return new ModelAndView("GlobalExceptionHandler", map);
	}

	@ExceptionHandler(value = SQLException.class)
	public ModelAndView handle(HttpServletRequest request, SQLException e) {
		e.printStackTrace();
		logger.info("SQLException Occured:: URL=" + request.getRequestURL());
		logger.info("SQLException Occured:: msg=" + e);
		ModelMap map = new ModelMap();
		map.put("status", HttpStatus.BAD_REQUEST);
		map.put("message", e.getMessage());
		map.put("GlobalExceptionMessage", "SQLExceptioneption handler executed");
		return new ModelAndView("GlobalExceptionHandler", map);
	}

	@ExceptionHandler(value = ClassNotFoundException.class)
	public ModelAndView handleClassNotFoundException(HttpServletRequest request, ClassNotFoundException e) {
		e.printStackTrace();
		logger.info("ClassNotFoundException Occured:: URL=" + request.getRequestURL());
		logger.info("ClassNotFoundException Occured:: msg=" + e);
		ModelMap map = new ModelMap();
		map.put("status", HttpStatus.NOT_FOUND);
		map.put("message", e.getMessage());
		map.put("GlobalExceptionMessage", "ClassNotFoundException handler executed");
		return new ModelAndView("GlobalExceptionHandler", map);
	}

	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleException(Exception e, HttpServletRequest request) {
		logger.error("INTERNAL_SERVER_ERROR msg " + e.getMessage());
		logger.error("INTERNAL_SERVER_ERROR exception " + e);
		logger.info("INTERNAL_SERVER_ERROR Occured:: URL= " + request.getRequestURL());
		ModelMap map = new ModelMap();
		map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
		map.put("message", e.getMessage());
		map.put("GlobalExceptionMessage", "INTERNAL SERVER ERROR");
		logger.error("INTERNAL_SERVER_ERROR");
		return new ModelAndView("GlobalExceptionHandler", map);
	}

	@ExceptionHandler(value = FileNotFoundException.class)
	public ModelAndView handleFileNotFoundException(FileNotFoundException e, HttpServletRequest request) {
		e.printStackTrace();
		logger.error("FileNotFoundException msg " + e.getMessage());
		logger.error("FileNotFoundException exception " + e);
		logger.info("FileNotFoundException Occured:: URL= " + request.getRequestURL());
		ModelMap map = new ModelMap();
		map.put("status", HttpStatus.NOT_FOUND);
		map.put("message", e.getMessage());
		map.put("GlobalExceptionMessage", "FileNotFoundException");
		logger.error("FileNotFoundException");
		return new ModelAndView("GlobalExceptionHandler", map);
	}
}
