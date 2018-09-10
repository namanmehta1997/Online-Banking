package com.cg.onlinebanking.service;

import java.sql.Date;
import java.util.List;

import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.exceptions.BankingException;

public interface IUserService {
	public List<TransactionDTO> getMiniStatement(int accountId) throws BankingException;

	public int getAccountNumber(String username) throws BankingException;

	public List<TransactionDTO> getDetailedTransactions(int accountId1, Date startDate,Date endDate) throws BankingException;

	public String changeAddress(int accountId2, String address) throws BankingException;

	public String changeMobileNumber(int accountId3, String mobileNo) throws BankingException;

	public String changePassword(String username, int accountId4, String password1, String newPassword, String newPassword1) throws BankingException;

	public int serviceRequest(int accountId6) throws BankingException;

	public String trackServiceRequest(int accountId7, int serviceId) throws BankingException;

	public String fundTransfer(int accountId8, int desAccountId, double amount) throws BankingException;
}
