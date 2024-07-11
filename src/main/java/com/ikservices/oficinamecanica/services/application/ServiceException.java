package com.ikservices.oficinamecanica.services.application;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String code;
	
	public ServiceException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ServiceException(String message, String code, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public ServiceException(String message) {
		super(message);
	}
}
