package com.chest.currency.enums;

import javax.validation.constraints.NotNull;

public enum  LamStatus{

	SUCCESS(0), FAILURE(1), EXCEPTION(2);

	private final int code;

	LamStatus(final int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String toString() {
		return String.valueOf(code);
	}

	public static LamStatus getStatus(int code) throws IllegalAccessError {
		if (code == 0)
			return LamStatus.SUCCESS;
		else if (code == 1)
			return LamStatus.FAILURE;
		else if (code == 2)
			return LamStatus.EXCEPTION;
		else
			throw new IllegalAccessError("Status not found in Status Enum");
	}

	public boolean equals(@NotNull LamStatus status) {
		return (status.getCode() == this.getCode());
	}
}
