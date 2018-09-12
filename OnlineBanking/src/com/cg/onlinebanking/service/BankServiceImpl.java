package com.cg.onlinebanking.service;

import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.onlinebanking.bean.CustomerDTO;
import com.cg.onlinebanking.bean.RoleDTO;
import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.dao.BankDaoImpl;
import com.cg.onlinebanking.dao.IBankDao;
import com.cg.onlinebanking.exceptions.BankingException;

public class BankServiceImpl implements IBankService {
	IBankDao dao;

	public BankServiceImpl() {
		dao = new BankDaoImpl();
	}

	@Override
	public int addUser(CustomerDTO userDto) throws BankingException {
		return dao.addUser(userDto);
	}

	@Override
	public List<TransactionDTO> viewAllTransactions(Date startDate, Date endDate)
			throws BankingException {
		List<TransactionDTO> list;
		list = dao.viewAllTransactions(startDate, endDate);
		return list;
	}

	@Override
	public boolean detailsValidation(CustomerDTO customerDTO)
			throws BankingException {
		String errorMessage = "";
		String userName;
		String password;
		String customerName;
		String mobileNumber;
		String amount;
		String mailId;

		userName = customerDTO.getUsername();
		System.out.println(userName);
		Pattern userPattern = Pattern.compile("^[A-Za-z]{3,}$");
		Matcher userMatcher = userPattern.matcher(userName);
		if (!(userMatcher.matches())) {
			errorMessage += "\nUserName should have min 3 letters!";
		}

		customerName = customerDTO.getCustomerName();
		Pattern customerPattern = Pattern.compile("^[A-Z][A-Za-z]{3,10}$");
		Matcher customerMatcher = customerPattern.matcher(customerName);
		if (!(customerMatcher.matches())) {
			errorMessage += "\nCustomer Name should have min 4 and max 10 letters and First letter should be in Uppercase.";
		}

		password = customerDTO.getPassword();
		Pattern passwordPattern = Pattern
				.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");
		Matcher passwordMatcher = passwordPattern.matcher(password);
		if (!(passwordMatcher.matches())) {
			errorMessage += "\nUser Password should have min 6 and max 20 letters with at least one digit,small case, upper case and"
					+ "special character!";

		}

		mobileNumber = customerDTO.getPhoneNo();
		Pattern mobileNoPattern = Pattern.compile("^[0-9]{10}$");
		Matcher mobileNoMatcher = mobileNoPattern.matcher(mobileNumber);
		if (!(mobileNoMatcher.matches())) {
			errorMessage += "\nMobile Number should contain exactly 10 digits!";
		}

		amount = customerDTO.getAccountBalance() + "";
		Pattern amountPattern = Pattern.compile("^\\d+(\\.\\d{1})?$");
		Matcher amountMatcher = amountPattern.matcher(amount);

		if (!(amountMatcher.matches())) {
			errorMessage += "\nPlease enter only numbers!";

		}
		// validating email
		mailId = customerDTO.getEmailId();
		Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3})$");
		Matcher matcher = pattern.matcher(mailId);

		if (!(matcher.matches())) {
			errorMessage += "\n Please enter a valid email id!";

		}
		amount = customerDTO.getPancard();
		Pattern panPattern = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
		Matcher panMatcher = panPattern.matcher(amount);

		if (!(panMatcher.matches())) {
			errorMessage += "\nPlease enter a valid PAN Number";

		}
		if (!errorMessage.isEmpty()) {
			throw new BankingException(errorMessage);
		}
		return false;
	}

	@Override
	public String getRole(String username, String password)
			throws BankingException {

		String position = null;
		RoleDTO role = dao.getUserByName(username);
		if (role == null) {
			throw new BankingException("No Such UserName");
		} else if (!password.equals(role.getPassword())) {
			throw new BankingException("Password Mismatch");
		} else {
			position = role.getPosition();
		}

		return position;

	}

	@Override
	public String getDefaultPassword(String username, int accountId,
			String maidenName) throws BankingException {
		return dao.getDefaultPassword(username, accountId, maidenName);
	}

	@Override
	public List<TransactionDTO> getMiniStatement(int accountId)
			throws BankingException {
		return dao.getMiniStatement(accountId);
	}

	@Override
	public int getAccountNumber(String username) throws BankingException {
		return dao.getAccountNumber(username);
	}

	@Override
	public List<TransactionDTO> getDetailedTransactions(int accountId1,
			Date startDate, Date endDate) throws BankingException {
		List<TransactionDTO> list;
		list = dao.getDetailedTransactions(accountId1, startDate, endDate);
		return list;
	}

	@Override
	public String changeAddress(int accountId2, String address)
			throws BankingException {
		return dao.changeAddress(accountId2, address);

	}

	@Override
	public String changeMobileNumber(int accountId3, String mobileNo)
			throws BankingException {
		return dao.changeMobileNumber(accountId3, mobileNo);
	}

	@Override
	public String changePassword(String username, int accountId4,
			String password1, String newPassword, String newPassword1)
			throws BankingException {
		return dao.changePassword(username, accountId4, password1, newPassword,
				newPassword1);
	}

	@Override
	public int serviceRequest(int accountId6) throws BankingException {
		return dao.serviceRequest(accountId6);
	}

	@Override
	public String trackServiceRequest(int accountId7, int serviceId)
			throws BankingException {
		return dao.trackServiceRequest(accountId7, serviceId);
	}

	@Override
	public String fundTransfer(int accountId8, int desAccountId, double amount)
			throws BankingException {
		return dao.fundTransfer(accountId8, desAccountId, amount);
	}
}
