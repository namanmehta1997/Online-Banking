package com.cg.bankingapp.service;

import java.util.List;

import com.cg.bankingapp.entities.AdminBean;
import com.cg.bankingapp.entities.PayeeBean;
import com.cg.bankingapp.entities.ServiceRequestBean;
import com.cg.bankingapp.entities.TransactionBean;
import com.cg.bankingapp.entities.UserBean;
import com.cg.bankingapp.exception.BankingException;

public interface IBankingService {

	public UserBean checkUserCredentials(String username, String password)
			throws BankingException;

	public boolean blockUser(String username) throws BankingException;

	
	public String getChequeBookStatus(int accountNumber) throws BankingException;
	
	public int raiseChequeBookRequest(int accountId,String serviceDescription) throws BankingException;
	
	public List<TransactionBean> getMiniStatement(String username) throws BankingException;
	
	public List<TransactionBean> getDetailedStatement(String startDate,String endDate,int accountId) throws BankingException;
	
	public UserBean changeUserDetails(String address,String phoneNo,int accountId) throws BankingException;
	
	public boolean changePassword(String password,int accountId) throws BankingException;
	
	public ServiceRequestBean checkServiceExist(int accountId, int serviceId) throws BankingException;



	public List<PayeeBean> getAllUser(int accountId) throws BankingException;

	public boolean fundTransfer(int sourceAcNo,int destAcNo,double amount)
			throws BankingException;

	public boolean fundSub(int accountId, double amount)
			throws BankingException;

	public boolean addPayee(PayeeBean payee) throws BankingException;

	public boolean checkPayee(int paccId, int accId) throws BankingException;

	public boolean checkAdminCredentials(AdminBean admin)
			throws BankingException;

	public int addUser(UserBean user) throws BankingException;
	
	public List<TransactionBean> getAllTransactions(String startDate1,String endDate1) throws BankingException;

	public List<ServiceRequestBean> checkServiceExistAcc(int accountId1, int accountId2) throws BankingException;
	

	public boolean checkSecurity(String ans, String username) throws BankingException;

	public boolean changePasswordByUsername(String newPassword2, String username);

}
