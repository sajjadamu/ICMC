package com.chest.currency.message;

import java.io.Serializable;
import java.util.List;

import com.chest.currency.enums.ServiceStatus;

public class Response<T> implements Serializable {

	private ServiceStatus StatusMessage;

	private int StatusCode;

	public static Response setSuccessResponse(ServiceStatus status, int code) {
		Response<List<?>> response = new Response<>();
		response.setStatusMessage(status);
		response.setStatusCode(code);
		return response;
	}

	public ServiceStatus getStatusMessage() {
		return StatusMessage;
	}

	public void setStatusMessage(ServiceStatus statusMessage) {
		StatusMessage = statusMessage;
	}

	public int getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(int statusCode) {
		StatusCode = statusCode;
	}

}