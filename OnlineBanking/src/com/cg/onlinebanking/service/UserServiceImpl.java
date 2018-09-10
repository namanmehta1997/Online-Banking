package com.cg.onlinebanking.service;

import java.sql.Date;
import java.util.List;
import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.dao.IUserDAO;
import com.cg.onlinebanking.dao.UserDAOImpl;
import com.cg.onlinebanking.exceptions.BankingException;

public class UserServiceImpl implements IUserService{

	IUserDAO iUserDao = new UserDAOImpl();
	@Override
	public List<TransactionDTO> getMiniStatement(int accountId)
			throws BankingException {
		return iUserDao.getMiniStatement(accountId);
	}
	@Override
	public int getAccountNumber(String username) throws BankingException {
		return iUserDao.getAccountNumber(username);
	}
	

	@Override
	public List<TransactionDTO> getDetailedTransactions(int accountId1,
			Date startDate, Date endDate) throws BankingException {
		List<TransactionDTO> list;
		list=iUserDao.getDetailedTransactions(accountId1,startDate, endDate);
		return list;
	}
	@Override
	public String changeAddress(int accountId2, String address) throws BankingException {
		return iUserDao.changeAddress(accountId2,address);
		
		
	}
	@Override
	public String changeMobileNumber(int accountId3, String mobileNo)
			throws BankingException {
		return iUserDao.changeMobileNumber(accountId3,mobileNo);
	}
	@Override
	public String changePassword(String username,int accountId4, String password1, String newPassword, String newPassword1)
			throws BankingException {
		return iUserDao.changePassword(username,accountId4,password1,newPassword,newPassword1);
	}

	@Override
	public int serviceRequest(int accountId6) throws BankingException {
		return iUserDao.serviceRequest(accountId6);
	}
	@Override
	public String trackServiceRequest(int accountId7, int serviceId)throws BankingException {
		return iUserDao.trackServiceRequest(accountId7,serviceId);
	}
	@Override
	public String fundTransfer(int accountId8, int desAccountId, double amount) throws BankingException {
		return iUserDao.fundTransfer(accountId8,desAccountId,amount);
	}
	
	
	}
