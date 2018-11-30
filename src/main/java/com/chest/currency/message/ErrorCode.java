package com.chest.currency.message;

public enum ErrorCode {

	BAD_REQUEST(400), UNAUTHORIZED(401), NOT_FOUND(404), NOT_ACCEPTABLE(406);

	private final int code;

	ErrorCode(final int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
