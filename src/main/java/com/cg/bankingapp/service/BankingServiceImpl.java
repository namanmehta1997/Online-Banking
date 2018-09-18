package com.cg.bankingapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.bankingapp.dao.IBankingDao;
import com.cg.bankingapp.entities.AdminBean;
import com.cg.bankingapp.entities.PayeeBean;
import com.cg.bankingapp.entities.ServiceRequestBean;
import com.cg.bankingapp.entities.TransactionBean;
import com.cg.bankingapp.entities.UserBean;
import com.cg.bankingapp.exception.BankingException;

@Service
@Transactional
public class BankingServiceImpl implements IBankingService {

	@Autowired
	IBankingDao dao;

	public IBankingDao getDao() {
		return dao;
	}

	public void setDao(IBankingDao dao) {
		this.dao = dao;
	}

	@Override
	public UserBean checkUserCredentials(String username, String password)
			throws BankingException {

		return dao.checkUserCredentials(username, password);
	}

	@Override
	public boolean checkAdminCredentials(AdminBean admin)
			throws BankingException {

		return dao.checkAdminCredentials(admin);
	}

	@Override
	public List<TransactionBean> getMiniStatement(String username)
			throws BankingException {

		return dao.getMiniStatement(username);
	}

	@Override
	public List<TransactionBean> getDetailedStatement(String startDate,
			String endDate, int accountId) throws BankingException {
		return dao.getDetailedStatement(startDate, endDate, accountId);
	}

	@Override
	public String getChequeBookStatus(int accountNumber)
			throws BankingException {
		return dao.getChequeBookStatus(accountNumber);
	}

	@Override
	public int raiseChequeBookRequest(int accountId, String serviceDescription)
			throws BankingException {
		// TODO Auto-generated method stub
		return dao.raiseChequeBookRequest(accountId, serviceDescription);
	}

	@Override
	public ServiceRequestBean checkServiceExist(int accountId ,int serviceId)
			throws BankingException {
		// TODO Auto-generated method stub

		return dao.checkServiceExist(accountId, serviceId);
	}
	
	@Override
	public List<ServiceRequestBean> checkServiceExistAcc(int accountId1, int accountId2) 
			throws BankingException {
		return dao.checkServiceExistAcc(accountId1, accountId2);
	}

	@Override
	public UserBean changeUserDetails(String address, String phoneNo,
			int accountId) throws BankingException {

		return dao.changeUserDetails(address, phoneNo, accountId);
	}

	@Override
	public boolean changePassword(String password, int accountId)
			throws BankingException {

		return dao.changePassword(password, accountId);
	}

	@Override
	public boolean fundTransfer(int sourceAcNo, int destAcNo, double amount)
			throws BankingException {
		// TODO Auto-generated method stub
		return dao.fundTransfer(sourceAcNo,destAcNo, amount);
	}

	@Override
	public List<PayeeBean> getAllUser(int accountId) throws BankingException {
		// TODO Auto-generated method stub
		return dao.getAllUser(accountId);
	}

	@Override
	public boolean fundSub(int accountId, double amount)
			throws BankingException {
		return dao.fundSub(accountId, amount);

	}

	@Override
	public boolean addPayee(PayeeBean payee) throws BankingException {
		// TODO Auto-generated method stub
		return dao.addPayee(payee);
	}

	@Override
	public boolean checkPayee(int paccId, int accId) throws BankingException {
		// TODO Auto-generated method stub
		return dao.checkPayee(paccId, accId);
	}

	// admin services

	@Override
	public int addUser(UserBean user) throws BankingException {
		// TODO Auto-generated method stub
		return dao.addUser(user);
	}

	@Override
	public List<TransactionBean> getAllTransactions(String startDate1,
			String endDate1) throws BankingException {
		// TODO Auto-generated method stub
		return dao.getAllTransactions(startDate1, endDate1);
	}

	@Override
	public boolean blockUser(String username) throws BankingException {
		return dao.blockUser(username);
	}

	@Override
	public boolean checkSecurity(String ans, String username)
			throws BankingException {
		return dao.checkSecurity(ans, username);
	}

	@Override
	public boolean changePasswordByUsername(String newPassword2, String username) {
		return dao.changePasswordByUsername(newPassword2, username);
	}

}
