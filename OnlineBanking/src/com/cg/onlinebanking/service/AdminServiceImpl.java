package com.cg.onlinebanking.service;

import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.bean.CustomerDTO;
import com.cg.onlinebanking.dao.AdminDaoImpl;
import com.cg.onlinebanking.dao.IAdminDao;
import com.cg.onlinebanking.exceptions.BankingException;

public class AdminServiceImpl implements IAdminService {

	IAdminDao iAdminDao = new AdminDaoImpl();

	@Override
	public int addUser(CustomerDTO userDto) throws BankingException {
		return iAdminDao.addUser(userDto);
	}

	@Override
	public List<TransactionDTO> viewAllTransactions(Date startDate, Date endDate)
			throws BankingException {
		List<TransactionDTO> list;
		list = iAdminDao.viewAllTransactions(startDate, endDate);
		return list;
	}

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
			errorMessage += "\nUser Name should have min 3 letters.";
		}

		customerName = customerDTO.getCustomerName();
		Pattern customerPattern = Pattern.compile("^[A-Z][A-Za-z]{3,10}$");
		Matcher customerMatcher = customerPattern.matcher(customerName);
		if (!(customerMatcher.matches())) {
			errorMessage += "\nCustomer Name should have min 3 and max 10 letters and First letter should start with Uppercase.";
		}

		password = customerDTO.getPassword();
		Pattern passwordPattern = Pattern
				.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");
		Matcher passwordMatcher = passwordPattern.matcher(password);
		if (!(passwordMatcher.matches())) {
			errorMessage += "\nUser Password should have min 6 and max 20 letters with at least one digit,small case, upper case and"
					+ "special character";

		}

		mobileNumber = customerDTO.getPhoneNo();
		Pattern mobileNoPattern = Pattern.compile("^[0-9]{10}$");
		Matcher mobileNoMatcher = mobileNoPattern.matcher(mobileNumber);
		if (!(mobileNoMatcher.matches())) {
			errorMessage += "\nMobile Number should contain 10 digits.";
		}

		amount = customerDTO.getAccountBalance() + "";
		Pattern amountPattern = Pattern.compile("^\\d+(\\.\\d{1})?$");
		Matcher amountMatcher = amountPattern.matcher(amount);

		if (!(amountMatcher.matches())) {
			errorMessage += "\nPlease enter only numbers";

		}
		// validating email
		mailId = customerDTO.getEmailId();
		Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3})$");
		Matcher matcher = pattern.matcher(mailId);

		if (!(matcher.matches())) {
			errorMessage += "\n Please enter a valid email id";

		}
		amount = customerDTO.getPancard();
		Pattern panPattern = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
		Matcher panMatcher = panPattern.matcher(amount);

		if (!(panMatcher.matches())) {
			errorMessage += "\nPlease enter a valid Pan number with upper case";

		}
		if (!errorMessage.isEmpty()) {
			throw new BankingException(errorMessage);
		}
		return false;
	}

}
