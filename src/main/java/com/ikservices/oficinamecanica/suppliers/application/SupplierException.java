package com.ikservices.oficinamecanica.suppliers.application;

public class SupplierException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String code;

	public SupplierException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public SupplierException(String message) {
		super(message);
	}
	
	public SupplierException(String message, String code, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public SupplierException(String message, Throwable cause) {
		super(message, cause);
	}
}
