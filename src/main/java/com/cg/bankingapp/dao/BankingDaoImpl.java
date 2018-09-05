package com.cg.bankingapp.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.cg.bankingapp.entities.AdminBean;
import com.cg.bankingapp.entities.PayeeBean;
import com.cg.bankingapp.entities.ServiceRequestBean;
import com.cg.bankingapp.entities.TransactionBean;
import com.cg.bankingapp.entities.UserBean;
import com.cg.bankingapp.exception.BankingException;

@Repository
public class BankingDaoImpl implements IBankingDao {

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
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<TransactionBean> getMiniStatement(int accountId)
			throws BankingException {

		System.out.println(accountId);

		TypedQuery<TransactionBean> query = entityManager
				.createQuery(
						"Select t from TransactionBean t WHERE t.accountNumber= :accno order by t.dateOfTransaction desc",
						TransactionBean.class);
		query.setParameter("accno", accountId);
		query = query.setMaxResults(10);

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
		if (serviceRequest != null)
			return serviceRequest.getServiceStatus();
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

		return serviceRequest.getServiceId();

	}

	@Override
	public ServiceRequestBean checkServiceExist(int accountId, int serviceId)
			throws BankingException {
		ServiceRequestBean serviceRequest = new ServiceRequestBean();
		serviceRequest = entityManager.find(ServiceRequestBean.class, serviceId);
		if(serviceRequest != null)
		{
			if(accountId == serviceRequest.getAccountId())
			{
				return entityManager.find(ServiceRequestBean.class, serviceId);
			}
			else
			{
				return null;
			}
		}
		else
			return null;
	}
	
	@Override
	public ServiceRequestBean checkServiceExistAcc(int accountId1, int accountId2)
			throws BankingException {
		TypedQuery<ServiceRequestBean> query = entityManager.createQuery(
				"SELECT s FROM ServiceRequestBean s WHERE s.accountId=:accno",
				ServiceRequestBean.class);
		query.setParameter("accno", accountId2);
		List<ServiceRequestBean> serviceRequest = query.getResultList();
		if(serviceRequest.size() != 0)
		{	
			if(serviceRequest.get(0).getAccountId() == accountId1)
				return serviceRequest.get(0);
		
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public UserBean changeUserDetails(String address, String phoneNo,
			int accountId) throws BankingException {

		UserBean user = entityManager.find(UserBean.class, accountId);
		user.setAddress(address);
		user.setPhoneNo(phoneNo);
		entityManager.merge(user);
		if (entityManager.merge(user) != null)
			return user;
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
			return true;
		}
		return false;

	}

	public String changeFormat(String date) throws BankingException {

		String finalDate;
		String month[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
				"AUG", "SEP", "OCT", "NOV", "DEC" };

		String year = date.substring(2, 4);

		int mon = Integer.parseInt(date.substring(5, 7));
		String mm = month[mon - 1];
		String day = date.substring(8, 10);
		finalDate = day + '-' + mm + '-' + year;
		return finalDate;
	}

	public List<PayeeBean> getAllUser(int accountId) throws BankingException {

		TypedQuery<PayeeBean> query = entityManager.createQuery(
				"SELECT p FROM PayeeBean p WHERE p.accountId = :accno",
				PayeeBean.class);

		query.setParameter("accno", accountId);

		return query.getResultList();

	}

	@Override
	public boolean fundTransfer(int accountId, double amount)
			throws BankingException {

		UserBean user = entityManager.find(UserBean.class, accountId);
		if (user != null) {
			user.setAmount(user.getAmount() + amount);

			double availBalance = user.getAmount();

			Date date = new Date();
			TransactionBean transaction = new TransactionBean();
			transaction.setAccountNumber(accountId);
			transaction.setDateOfTransaction(date);
			transaction.setAmount(availBalance);
			transaction.setTransactionAmount(amount);
			transaction.setTransactionDescription("Credit");

			entityManager.persist(transaction);
			entityManager.flush();
			return true;
		}
		return false;

	}

	@Override
	public boolean fundSub(int accountId, double amount)
			throws BankingException {

		UserBean user = entityManager.find(UserBean.class, accountId);

		if (user != null) {
			user.setAmount(user.getAmount() - amount);
			double availBalance = user.getAmount();
			if (availBalance < amount)
				return false;
			else {

				Date date = new Date();
				TransactionBean transaction = new TransactionBean();
				transaction.setAccountNumber(accountId);
				transaction.setDateOfTransaction(date);
				transaction.setAmount(availBalance);
				transaction.setTransactionAmount(amount);
				transaction.setTransactionDescription("Debit");

				entityManager.persist(transaction);
				entityManager.flush();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addPayee(PayeeBean payee) throws BankingException {

		entityManager.persist(payee);
		entityManager.flush();
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
				return true;
			}
		}
		return false;

	}

	/*************************************
	 * admin dao implementation
	 *****************************************/
	@Override
	public boolean checkAdminCredentials(AdminBean admin)
			throws BankingException {

		AdminBean admin1 = entityManager.find(AdminBean.class,
				admin.getUsername());
		if (admin1 != null && admin1.getPassword().equals(admin.getPassword())) {
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

		if (list.isEmpty()) {
			entityManager.persist(user);
			entityManager.flush();

			Date date = new Date();
			TransactionBean transaction = new TransactionBean();
			transaction.setAccountNumber(user.getAccountId());
			transaction.setAmount(user.getAmount());
			transaction.setDateOfTransaction(date);
			transaction.setTransactionAmount(user.getAmount());
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

			return user.getAccountId();
		} else {
			return 0;
		}
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
		return query.getResultList();

	}

	@Override
	public boolean blockUser(String username) throws BankingException {
		TypedQuery<UserBean> query = entityManager.createQuery(
				"SELECT user FROM UserBean user WHERE user.username=:username",
				UserBean.class);
		query.setParameter("username", username);
		List<UserBean> users = query.getResultList();
		for (UserBean user : users) {
			user.setAccStatus("block");
			entityManager.flush();
		}
		return true;
	}


	@Override
	public boolean checkSecurity(String ans, String username)
			throws BankingException {
		ans = ans.trim();
		System.out.println(username);
		TypedQuery<UserBean> query = entityManager.createQuery(
				"SELECT user FROM"
						+ " UserBean user WHERE user.username=:username",
				UserBean.class);
		query.setParameter("username", username);
		List<UserBean> users = query.getResultList();

		for (UserBean user : users) {
			if (user.getSecurityAns().equalsIgnoreCase(ans)) {
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
		}
		return true;
	}
}
