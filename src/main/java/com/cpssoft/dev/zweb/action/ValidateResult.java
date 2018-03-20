package com.cpssoft.dev.zweb.action;

public class ValidateResult {

	public ValidateResultType type;
	public String message;
	public Object data;

	public ValidateResult(ValidateResultType type, String message, Object data) {
		this.type = type;
		this.message = message;
		this.data = data;
	}
}
