package com.cg.onlinebanking.exceptions;

public class BankingException extends Exception{
	private static final long serialVersionUID = 1L;

	public BankingException(String msg) {
		super(msg);
	}
	
}
