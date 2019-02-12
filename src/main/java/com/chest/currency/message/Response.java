package com.chest.currency.message;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.chest.currency.enums.LamStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "Output")
public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private LamStatus status;

	@JsonIgnore
	private int code;

	@XmlElement(name = "message1")
	private String message;

	public static Response setSuccessResponse(LamStatus status, int code) {
		Response response = new Response();
		response.setStatus(status);
		response.setCode(code);
		return response;
	}

	public static Response setSuccessResponse(LamStatus status, int code, String message) {
		Response response = new Response();
		// response.setStatus(status);
		if (code == 2) {
			response.setCode(1);
		} else {
			response.setCode(code);
		}
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