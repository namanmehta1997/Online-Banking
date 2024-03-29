package com.cg.bankingapp.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cg.bankingapp.entities.AdminBean;
import com.cg.bankingapp.entities.PayeeBean;
import com.cg.bankingapp.entities.ServiceRequestBean;
import com.cg.bankingapp.entities.TransactionBean;
import com.cg.bankingapp.entities.UserBean;
import com.cg.bankingapp.exception.BankingException;

@Repository
public class BankingDaoImpl implements IBankingDao {
	private final Logger LOGGER = Logger.getLogger(BankingDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserBean checkUserCredentials(String username, String password)
			throws BankingException {
		TypedQuery<UserBean> query = entityManager.createQuery(
				"SELECT u from UserBean u WHERE u.username= :username",
				UserBean.class);
		query.setParameter("username", username);

		List<UserBean> list = query.getResultList();

		if (!list.isEmpty() && list.get(0).getPassword().equals(password)) {
			LOGGER.info("User credentials checked successfully");
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<TransactionBean> getMiniStatement(String username)
			throws BankingException {

		TypedQuery<TransactionBean> query = entityManager
				.createQuery(
						"Select t from TransactionBean t WHERE t.username= :username order by t.dateOfTransaction desc",
						TransactionBean.class);
		query.setParameter("username", username);
		query = query.setMaxResults(10);
		LOGGER.info("Mini statement printed successfully");

		return query.getResultList();
	}

	@Override
	public List<TransactionBean> getDetailedStatement(String startDate,
			String endDate, int accountId) throws BankingException {

		startDate = changeFormat(startDate);
		endDate = changeFormat(endDate);

		TypedQuery<TransactionBean> query = entityManager
				.createQuery(
						"SELECT t FROM TransactionBean t "
								+ "WHERE (t.dateOfTransaction BETWEEN to_date(:start,'dd-MON-yy') AND "
								+ "to_date(:end,'dd-MON-yy')+(1-1/24/60/60)) AND t.accountNumber=:accno ORDER BY t.dateOfTransaction desc",
						TransactionBean.class);
		query.setParameter("accno", accountId);
		query.setParameter("start", startDate);
		query.setParameter("end", endDate);
		LOGGER.info("Detailed statement printed successfully");
		return query.getResultList();

	}

	@Override
	public String getChequeBookStatus(int accountNumber)
			throws BankingException {

		TypedQuery<ServiceRequestBean> query = entityManager.createQuery(
				"SELECT s FROM ServiceRequestBean s WHERE s.accountId=:accno",
				ServiceRequestBean.class);
		query.setParameter("accno", accountNumber);
		ServiceRequestBean serviceRequest = query.getSingleResult();
		if (serviceRequest != null){
			
			LOGGER.info("Check book status accepted successfully");
			return serviceRequest.getServiceStatus();
		}
		else
			return null;

	}

	@Override
	public int raiseChequeBookRequest(int accountId, String serviceDescription)
			throws BankingException {

		TypedQuery<ServiceRequestBean> query = entityManager.createQuery(
				"SELECT s FROM ServiceRequestBean s WHERE s.accountId=:accno",
				ServiceRequestBean.class);
		query.setParameter("accno", accountId);
		ServiceRequestBean serviceRequest = query.getSingleResult();

		serviceRequest.setServiceDescription(serviceDescription);
		serviceRequest.setServiceStatus("open");
		entityManager.merge(serviceRequest);
		LOGGER.info("Check book status raised successfully");

		return serviceRequest.getServiceId();

	}

	@Override
	public ServiceRequestBean checkServiceExist(int accountId, int serviceId)
			throws BankingException {
		ServiceRequestBean serviceRequest = new ServiceRequestBean();
		serviceRequest = entityManager
				.find(ServiceRequestBean.class, serviceId);
		if (serviceRequest != null) {
			if (accountId == serviceRequest.getAccountId()) {
				LOGGER.info("Service request checked successfully");
				return entityManager.find(ServiceRequestBean.class, serviceId);
			} else {
				return null;
			}
		} else
			return null;
	}

	@Override
	public List<ServiceRequestBean> checkServiceExistAcc(int accountId1,
			int accountId2) throws BankingException {
		TypedQuery<ServiceRequestBean> query = entityManager.createQuery(
				"SELECT s FROM ServiceRequestBean s WHERE s.accountId=:accno",
				ServiceRequestBean.class);
		query.setParameter("accno", accountId2);
		List<ServiceRequestBean> serviceRequest = query.getResultList();
		List<ServiceRequestBean> serviceList = new ArrayList<ServiceRequestBean>();
		for(ServiceRequestBean serRequestBean : serviceRequest){
			if(serRequestBean.getAccountId() == accountId1){
				
				serviceList.add(serRequestBean);
			}
		}
		LOGGER.info("Service request existance checked successfully");
		return serviceList;
	}

	@Override
	public UserBean changeUserDetails(String address, String phoneNo,
			int accountId) throws BankingException {

		UserBean user = entityManager.find(UserBean.class, accountId);
		user.setAddress(address);
		user.setPhoneNo(phoneNo);
		entityManager.merge(user);
		if (entityManager.merge(user) != null){
			
			LOGGER.info("User details changed successfully");
			return user;
		}
		else
			return null;

	}

	@Override
	public boolean changePassword(String password, int accountId)
			throws BankingException {

		UserBean user = entityManager.find(UserBean.class, accountId);
		if (user != null && !user.getPassword().equals(password)) {
			user.setPassword(password);
			entityManager.merge(user);
			LOGGER.info("Password changed successfully");
			return true;
		}
		return false;

	}

	public String changeFormat(String date) throws BankingException {

		String finalDate;
		String month[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
				"AUG", "SEP", "OCT", "NOV", "DEC" };

		String year = date.substring(2, 4);
		try{
			int mon = Integer.parseInt(date.substring(5, 7));
			String mm = month[mon - 1];
			String day = date.substring(8, 10);
			finalDate = day + '-' + mm + '-' + year;
		}
		catch(Exception exception){
			throw new BankingException("Enter correct value");
		}

		return finalDate;
	}

	public List<PayeeBean> getAllUser(int accountId) throws BankingException {

		TypedQuery<PayeeBean> query = entityManager.createQuery(
				"SELECT p FROM PayeeBean p WHERE p.accountId = :accno",
				PayeeBean.class);
		query.setParameter("accno", accountId);
		LOGGER.info("User details received successfully");
		return query.getResultList();

	}

	@Override
	public boolean fundTransfer(int sourceAccountId,int destAccountId, double amount)
			throws BankingException {
		

		UserBean destUser = entityManager.find(UserBean.class, destAccountId);
		UserBean sourceUser = entityManager.find(UserBean.class, sourceAccountId);
		
		if (destUser!=null && destUser.getAccStatus().equals("block")){
			LOGGER.error("Transaction failed as payee account is blocked");	
			throw new BankingException("block");
			
		}
		if (destUser != null && sourceUser !=null) {
			destUser.setAmount(destUser.getAmount() + amount);
			double availBalance = destUser.getAmount();
			Date date = new Date();
			TransactionBean destTransaction = new TransactionBean();
			destTransaction.setAccountNumber(destAccountId);
			destTransaction.setDateOfTransaction(date);
			destTransaction.setAmount(availBalance);
			destTransaction.setTransactionAmount(amount);
			destTransaction.setTransactionDescription("Credit");
			destTransaction.setUsername(destUser.getUsername());
			entityManager.persist(destTransaction);
			entityManager.flush();
			TransactionBean sourceTransaction = new TransactionBean();
			sourceTransaction.setAccountNumber(sourceUser.getAccountId());
			sourceTransaction.setDateOfTransaction(date);
			sourceUser.setAmount(sourceUser.getAmount()-amount);
			sourceTransaction.setAmount(sourceUser.getAmount());
			sourceTransaction.setTransactionAmount(amount);
			sourceTransaction.setTransactionDescription("Debit");
			sourceTransaction.setUsername(sourceUser.getUsername());
			entityManager.persist(sourceTransaction);
			entityManager.flush();
			LOGGER.info("Transaction successfully added into the database ");
			return true;
		}
		return false;
	}

	@Override
	public boolean fundSub(int accountId, double amount)
			throws BankingException {

		UserBean user = entityManager.find(UserBean.class, accountId);
		
		if (user != null) {
			double newBal = user.getAmount() - amount;
			if (newBal < 1000)
				return false;
			else {
				LOGGER.info("User account balance checked successfully");
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addPayee(PayeeBean payee) throws BankingException {

		entityManager.persist(payee);
		entityManager.flush();
		LOGGER.info("Payee added  successfully");
		return true;

	}

	@Override
	public boolean checkPayee(int paccId, int accId) throws BankingException {

		UserBean user = entityManager.find(UserBean.class, paccId);
		if (user != null) {
			TypedQuery<PayeeBean> query = entityManager
					.createQuery(
							"SELECT p FROM PayeeBean p WHERE p.accountId = :accno AND p.payeeAccountId=:payeeno",
							PayeeBean.class);

			query.setParameter("accno", accId);
			query.setParameter("payeeno", paccId);

			List<PayeeBean> list = query.getResultList();
			if (list.isEmpty()) {
				LOGGER.info("Payee checked successfully");
				return true;
			}
		}
		return false;

	}


	@Override
	public boolean checkAdminCredentials(AdminBean admin)
			throws BankingException {

		AdminBean admin1 = entityManager.find(AdminBean.class,
				admin.getUsername());
		if (admin1 != null && admin1.getPassword().equals(admin.getPassword())) {
			LOGGER.info("Admin credentials checked successfully");
			return true;
		}
		return false;

	}

	@Override
	public int addUser(UserBean user) throws BankingException {

		TypedQuery<UserBean> query = entityManager.createQuery(
				"SELECT u FROM UserBean u WHERE u.username = :username",
				UserBean.class);

		query.setParameter("username", user.getUsername());
		List<UserBean> list = query.getResultList();
		if(list.size()==1){
			UserBean oldUser = list.get(0);
			boolean flag = true;
			boolean accStatus = oldUser.getAccStatus().equals(user.getAccStatus());
			boolean accUsername = oldUser.getUsername().equals(user.getUsername());
			boolean accName = oldUser.getCustomerName().equals(user.getCustomerName());
			boolean accMail = oldUser.getEmailId().equals(user.getEmailId());
			boolean accPan = oldUser.getPancard().equals(user.getPancard());
			boolean accPass = oldUser.getPassword().equals(user.getPassword());
			boolean accPhone = oldUser.getPhoneNo().equals(user.getPhoneNo());
			flag = accStatus && accUsername && accName && accMail && accPan && accPass && accPhone;
			if(oldUser.getAccountType().equals(user.getAccountType())){
				return 0;
			}
			if(flag){
				entityManager.persist(user);
				entityManager.flush();
				
			}
			else{
				return -1;
			}
		}
		entityManager.persist(user);
		entityManager.flush();

		Date date = new Date();
		TransactionBean transaction = new TransactionBean();
		transaction.setAccountNumber(user.getAccountId());
		transaction.setAmount(user.getAmount());
		transaction.setDateOfTransaction(date);
		transaction.setTransactionAmount(user.getAmount());
		transaction.setUsername(user.getUsername());
		transaction.setTransactionDescription("Credit");
		entityManager.persist(transaction);
		entityManager.flush();
		ServiceRequestBean serv = new ServiceRequestBean();
		serv.setServiceDescription("newly created account");
		serv.setAccountId(user.getAccountId());
		serv.setServiceRaisedDate(date);
		serv.setServiceStatus("not issued");
		entityManager.persist(serv);
		entityManager.flush();
		LOGGER.info("User added successfully");

		return user.getAccountId();
	}

	@Override
	public List<TransactionBean> getAllTransactions(String startDate1,
			String endDate1) throws BankingException {

		startDate1 = changeFormat(startDate1);
		endDate1 = changeFormat(endDate1);
		TypedQuery<TransactionBean> query = entityManager
				.createQuery(
						"SELECT t FROM TransactionBean t "
								+ "WHERE (t.dateOfTransaction BETWEEN to_date(:start,'dd-MON-yy') AND "
								+ "to_date(:end,'dd-MON-yy')+(1-1/24/60/60)) ORDER BY t.accountNumber",
						TransactionBean.class);
		query.setParameter("start", startDate1);
		query.setParameter("end", endDate1);
		LOGGER.info("All transactions retrieved successfully");
		return query.getResultList();

	}

	@Override
	public boolean blockUser(String username) throws BankingException {
		System.out.println("Block");
		TypedQuery<UserBean> query = entityManager.createQuery(
				"SELECT user FROM UserBean user WHERE user.username=:username",
				UserBean.class);
		System.out.println("Block");
		query.setParameter("username", username);
		List<UserBean> users = query.getResultList();
		for (UserBean user : users) {
			user.setAccStatus("block");
			entityManager.flush();
		}
		System.out.println("Block");
		return true;
	}

	@Override
	public boolean checkSecurity(String ans, String username)
			throws BankingException {
		ans = ans.trim();
		TypedQuery<UserBean> query = entityManager.createQuery(
				"SELECT user FROM"
						+ " UserBean user WHERE user.username=:username",
				UserBean.class);
		query.setParameter("username", username);
		List<UserBean> users = query.getResultList();

		for (UserBean user : users) {
			if (user.getSecurityAns().equalsIgnoreCase(ans)) {
				LOGGER.info("Security answer checked successfully");
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean changePasswordByUsername(String newPassword2, String username) {
		TypedQuery<UserBean> query = entityManager.createQuery(
				"SELECT user FROM"
						+ " UserBean user WHERE user.username=:username",
				UserBean.class);
		query.setParameter("username", username);
		List<UserBean> users = query.getResultList();
		for (UserBean user : users) {
			user.setPassword(newPassword2);
			entityManager.flush();
			LOGGER.info("Password changed by user successfully");
		}
		return true;
	}
}
