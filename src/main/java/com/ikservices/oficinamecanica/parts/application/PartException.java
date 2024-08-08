package com.ikservices.oficinamecanica.parts.application;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String code;
	
	public PartException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public PartException(String message) {
		super(message);
	}
	
	public PartException(String message, String code, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public PartException(String message, Throwable cause) {
		super(message, cause);
	}
}
