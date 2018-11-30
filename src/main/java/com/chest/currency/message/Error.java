package com.chest.currency.message;

import java.io.Serializable;

import com.chest.currency.enums.LamStatus;


public class Error implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public int errorCode;
	    public String errorMessage = "";

	    public int getErrorCode() {
	        return errorCode;
	    }

	    public void setErrorCode(int errorCode) {
	        this.errorCode = errorCode;
	    }

	    public String getErrorMessage() {
	        return errorMessage;
	    }

	    public void setErrorMessage(String errorMessage) {
	        this.errorMessage = errorMessage;
	    }

	    public static Error setPreDefinedError(int errorCode, String errorMessage) {
	        Error error = new Error();
	        error.setErrorCode(errorCode);
	        error.setErrorMessage(errorMessage);
	        return error;
	    }

	    public static Response setErrorResponse(LamStatus status, String tokenId, int errorCode, String message) {
	        Response response = new Response();
	        return response;
	    }

	    public static Response setErrorResponse(LamStatus status, int errorCode, String message) {
	        Response response = new Response();
	        response.setStatus(status);
	        response.setCode(errorCode);
	        response.setMessage(message);
	        return response;
	    }
}
