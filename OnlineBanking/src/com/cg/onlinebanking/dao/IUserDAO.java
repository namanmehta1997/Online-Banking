package com.cg.onlinebanking.dao;


import java.sql.Date;
import java.util.List;

import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.exceptions.BankingException;

public interface IUserDAO {

	List<TransactionDTO> getMiniStatement(int accountId) throws BankingException;

	int getAccountNumber(String username) throws BankingException;

	List<TransactionDTO> getDetailedTransactions(int accountId1, Date startDate, Date endDate) throws BankingException;

	String changeAddress(int accountId2, String address) throws BankingException;

	String changeMobileNumber(int accountId3, String mobileNo) throws BankingException;

	String changePassword(String username,int accountId4, String password1, String newPassword, String newPassword1) throws BankingException;

	int serviceRequest(int accountId6) throws BankingException;

	String trackServiceRequest(int accountId7, int serviceId) throws BankingException;

	String fundTransfer(int accountId8, int desAccountId, double amount) throws BankingException;

}
