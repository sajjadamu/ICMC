package com.chest.currency.message;

import java.io.Serializable;

import com.chest.currency.enums.LamStatus;

public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	private LamStatus status;

	private int code;
	
	private String message;

	public static Response setSuccessResponse(LamStatus status, int code) {
		Response response = new Response();
		response.setStatus(status);
		response.setCode(code);
		return response;
	}
	public static Response setSuccessResponse(LamStatus status, int code,String message) {
		Response response = new Response();
		response.setStatus(status);
		response.setCode(code);
		response.setMessage(message);
		return response;
	}

	public LamStatus getStatus() {
		return status;
	}

	public void setStatus(LamStatus status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}