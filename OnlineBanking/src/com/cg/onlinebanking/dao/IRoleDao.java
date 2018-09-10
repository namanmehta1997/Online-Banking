package com.cg.onlinebanking.dao;

import com.cg.onlinebanking.bean.RoleDTO;
import com.cg.onlinebanking.exceptions.BankingException;

public interface IRoleDao {

	RoleDTO getUserByName(String username) throws BankingException;

	String getDefaultPassword(String username, int accountId, String petName) throws BankingException;

}
