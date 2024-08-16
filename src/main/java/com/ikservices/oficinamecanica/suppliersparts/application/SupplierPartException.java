package com.ikservices.oficinamecanica.suppliersparts.application;

public class SupplierPartException extends RuntimeException{
	private static final long serialVersionUID = 1L;	
	private String code;
	
	public SupplierPartException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public SupplierPartException(String message) {
		super(message);
	}
	
	public SupplierPartException(String message, String code, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public SupplierPartException(String message, Throwable cause) {
		super(message, cause);
	}
}
