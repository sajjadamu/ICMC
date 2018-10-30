package com.chest.currency.enums;

import javax.validation.constraints.NotNull;

public enum ServiceStatus {

	SUCCESS(0), FAILURE_MESSAGE(1), EXCEPTION_MESSAGE(2);

	private final int code;

	ServiceStatus(final int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String toString() {
		return String.valueOf(code);
	}

	public static ServiceStatus getStatus(int code) throws IllegalAccessError {
		if (code == 0)
			return ServiceStatus.SUCCESS;
		else if (code == 1)
			return ServiceStatus.FAILURE_MESSAGE;
		else if (code == 2)
			return ServiceStatus.EXCEPTION_MESSAGE;
		else
			throw new IllegalAccessError("Status not found in Status Enum");
	}

	public boolean equals(@NotNull ServiceStatus status) {
		return (status.getCode() == this.getCode());
	}
}
