/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.exception;

public class BaseGuiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2611294658639059644L;
	String errorMessage;
	
	public BaseGuiException(String message){
			super(message);
			errorMessage = message;
	}

}
