package com.cg.onlinebanking.service;

import com.cg.onlinebanking.exceptions.BankingException;

public interface IRoleService {
	String getRole(String username,String password) throws BankingException;

	String getDefaultPassword(String username, int accountId, String petName) throws BankingException;
}
