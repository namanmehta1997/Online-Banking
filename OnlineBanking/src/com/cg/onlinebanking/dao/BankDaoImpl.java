package com.cg.onlinebanking.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.cg.onlinebanking.bean.CustomerDTO;
import com.cg.onlinebanking.bean.RoleDTO;
import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.exceptions.BankingException;
import com.cg.onlinebanking.util.DBConnection;

public class BankDaoImpl implements IBankDao{

	private final Logger LOGGER;

	public BankDaoImpl() {
		LOGGER = Logger.getLogger(BankDaoImpl.class);
	}

	@Override
	public int addUser(CustomerDTO userDto) throws BankingException {

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = -1;

		try {

			preparedStatement = connection
					.prepareStatement(QueryMapper.ADD_ACCOUNT);
			preparedStatement.setString(1, userDto.getUsername());
			preparedStatement.setString(2, userDto.getPassword());
			preparedStatement.setString(3, userDto.getCustomerName());
			preparedStatement.setString(4, userDto.getEmailId());
			preparedStatement.setString(5, userDto.getAddress());
			preparedStatement.setString(6, userDto.getPhoneNo());
			preparedStatement.setString(7, userDto.getPancard());
			preparedStatement.setDouble(8, userDto.getAccountBalance());
			preparedStatement.setString(9, userDto.getSecretAnswer());

			int result = preparedStatement.executeUpdate();
			if (result == 1) {
				Statement statement = connection.createStatement();
				resultSet = statement.executeQuery(QueryMapper.ACCOUNT_ID);
				if (resultSet.next()) {
					id = resultSet.getInt(1);
				}
				connection.commit();
			}

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			preparedStatement = connection
					.prepareStatement(QueryMapper.TRANSACTIONS);
			preparedStatement.setString(1, "credit");
			preparedStatement.setDouble(2, userDto.getAccountBalance());
			preparedStatement.setInt(3, id);

			int result1 = preparedStatement.executeUpdate();
			if (result1 == 1) {
				connection.commit();
			}

			preparedStatement = connection
					.prepareStatement(QueryMapper.ADD_USERS);
			preparedStatement.setString(1, userDto.getUsername());
			preparedStatement.setString(2, userDto.getPassword());
			preparedStatement.setString(3, "user");

			int result2 = preparedStatement.executeUpdate();
			if (result2 == 1) {
				connection.commit();
			}

		} catch (SQLException sqlException) {
			//System.out.println(sqlException);
			throw new BankingException("Could not add account");
		}

		finally {

			if (preparedStatement != null && connection != null
					&& resultSet != null) {
				try {
					resultSet.close();
					preparedStatement.close();
					// connection.close();
				} catch (SQLException sqlException2) {
					throw new BankingException("Could not connect to database");
				}

			}
		}
		return id;
	}

	public List<TransactionDTO> viewAllTransactions(Date startDate, Date endDate)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = DBConnection.getConnection();
		List<TransactionDTO> list = new ArrayList<TransactionDTO>();

		try {
			preparedStatement = connection
					.prepareStatement(QueryMapper.GETALLTRANSACTIONS);
			preparedStatement.setDate(1, startDate);
			preparedStatement.setDate(2, endDate);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setTransactionId(resultSet.getInt(1));
				transactionDTO
						.setTransactionDescription(resultSet.getString(2));
				transactionDTO.setDateOfTransaction(resultSet.getDate(3));
				transactionDTO.setTransactionAmount(resultSet.getDouble(4));
				transactionDTO.setAccountNumber(resultSet.getInt(5));

				list.add(transactionDTO);
			}
		} catch (SQLException sqlException) {
			throw new BankingException("Could not fetch details");
		}

		finally {

			if (preparedStatement != null && connection != null
					&& resultSet != null) {
				try {
					resultSet.close();
					preparedStatement.close();
					// connection.close();
				} catch (SQLException e) {
					throw new BankingException("Could not fetch data");
				}

			}
		}
		return list;

	}

	@Override
	public RoleDTO getUserByName(String username) throws BankingException {
		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;
		connection = DBConnection.getConnection();
		ResultSet resultSet = null;

		RoleDTO role = null;
		try {
			preparedStatement = connection
					.prepareStatement(QueryMapper.GET_USER);

			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				role = new RoleDTO();
				role.setUsername(resultSet.getString(1));
				role.setPassword(resultSet.getString(2));
				role.setPosition(resultSet.getString(3));
			}
		} catch (SQLException sqlException) {
			// log.error(e);
			throw new BankingException("Unable To Fetch Records");
		}
		return role;
	}

	@Override
	public String getDefaultPassword(String username, int accountId,
			String maidenName) throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		//System.out.println(maidenName);
		Connection connection = DBConnection.getConnection();
		CustomerDTO customer = new CustomerDTO();
		//System.out.println("Before checking, sec ans: "+customer.getSecretAnswer());
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.GET_SECURITYANS);
			preparedStatement.setLong(1, accountId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customer.setSecretAnswer(resultSet.getString(1));
			}
		} catch (SQLException e) {
		}
		if(maidenName.equals(customer.getSecretAnswer()))
		{
			//System.out.println("The secret Answer is: "+customer.getSecretAnswer());
			String defaultPassword = "12345";
			try {
				preparedStatement = connection
						.prepareStatement(QueryMapper.CHANGE_PASSWORD);
				preparedStatement.setString(1, defaultPassword);
				preparedStatement.setInt(2, accountId);
				int result = preparedStatement.executeUpdate();
				if (result == 1) {
					connection.commit();
				}
				preparedStatement = connection
						.prepareStatement(QueryMapper.CHANGE_PASSWORD1);
				preparedStatement.setString(1, defaultPassword);
				preparedStatement.setString(2, username);
				int result1 = preparedStatement.executeUpdate();
				if (result1 == 1) {
					connection.commit();
				}
			} catch (SQLException exception) {
			}
		return defaultPassword;
		}
		else
			return null;
	}

	@Override
	public List<TransactionDTO> getMiniStatement(int accountId)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = DBConnection.getConnection();
		LOGGER.info("Obtained database connection");
		List<TransactionDTO> list = new ArrayList<TransactionDTO>();

		int count = 0;
		try {
			LOGGER.warn("Fetcing mini-statement of the account");
			preparedStatement = connection
					.prepareStatement(QueryMapper.GET_MINI_STATEMENT);
			preparedStatement.setInt(1, accountId);
			LOGGER.warn("About to excecute the mini-statement query");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (count < 10) {
					TransactionDTO transactionDTO = new TransactionDTO();
					transactionDTO.setTransactionId(resultSet.getInt(1));
					transactionDTO.setTransactionDescription(resultSet
							.getString(2));
					transactionDTO.setDateOfTransaction(resultSet.getDate(3));
					transactionDTO.setTransactionAmount(resultSet.getDouble(4));
					transactionDTO.setAccountNumber(resultSet.getInt(5));

					list.add(transactionDTO);

					count++;
					LOGGER.info("Returning the list of details fetched");
				}
			}
		} catch (SQLException exception) {
			LOGGER.error("Fetching mini-statement failed ");
			throw new BankingException("Unable to fetch data");
		}

		finally {

			if (preparedStatement != null && connection != null
					&& resultSet != null) {
				try {
					LOGGER.warn("Closing preparedStatement");
					preparedStatement.close();
					// connection.close();
				} catch (SQLException exception) {
					LOGGER.fatal("Connection failed:hence execution stopped");
					throw new BankingException("Unable to fetch data");
				}

			}
		}
		return list;
	}

	@Override
	public int getAccountNumber(String username) throws BankingException {
		Connection connection = DBConnection.getConnection();
		LOGGER.info("Obtained database connection");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = -1;

		try {
			LOGGER.warn("Fetcing account number");
			preparedStatement = connection
					.prepareStatement(QueryMapper.GET_ACCOUNT_NUMBER);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				id = resultSet.getInt(1);
				LOGGER.info("account number successfully fetched");
			}
		} catch (SQLException sqlException) {
			LOGGER.error("Fetching of accountnumber failed ");
			throw new BankingException("Unable to fetch account id");
		}

		return id;
	}

	@Override
	public List<TransactionDTO> getDetailedTransactions(int accountId1,
			Date startDate, Date endDate) throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = DBConnection.getConnection();
		LOGGER.info("Obtained database connection");
		List<TransactionDTO> list = new ArrayList<TransactionDTO>();

		try {
			LOGGER.warn("To view complete transactions of the account");
			preparedStatement = connection
					.prepareStatement(QueryMapper.GET_DETAILED_STATEMENT);
			preparedStatement.setInt(1, accountId1);
			preparedStatement.setDate(2, startDate);
			preparedStatement.setDate(3, endDate);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setTransactionId(resultSet.getInt(1));
				transactionDTO
						.setTransactionDescription(resultSet.getString(2));
				transactionDTO.setDateOfTransaction(resultSet.getDate(3));
				transactionDTO.setTransactionAmount(resultSet.getDouble(4));
				transactionDTO.setAccountNumber(resultSet.getInt(5));
				list.add(transactionDTO);
				LOGGER.info("Returning the list of transactions");
			}
		} catch (SQLException e1) {
			LOGGER.error("Failed to return the list of transactions ");
		}

		finally {

			if (preparedStatement != null && connection != null
					&& resultSet != null) {
				try {
					LOGGER.warn("Closing preparedStatement");
					preparedStatement.close();
				} catch (SQLException e) {
					LOGGER.fatal("Connection failed:hence execution stopped");
					throw new BankingException("Could not fetch data");
				}

			}
		}
		return list;

	}

	@Override
	public String changeAddress(int accountId2, String address)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		Connection connection = DBConnection.getConnection();
		LOGGER.info("Obtained database connection");
		String message = "Successfully Updated the address";
		try {
			LOGGER.warn("To update the address of the  customer");
			preparedStatement = connection
					.prepareStatement(QueryMapper.CHANGE_ADDRESS);
			preparedStatement.setString(1, address);
			preparedStatement.setInt(2, accountId2);
			int result = preparedStatement.executeUpdate();
			if (result == 1) {
				LOGGER.info("Customer address updated successfully");
				connection.commit();
			}
		} catch (SQLException exception) {
			LOGGER.error("Failed to update the address ");
			throw new BankingException("Could not update the address");
		}
		return message;
	}

	@Override
	public String changeMobileNumber(int accountId3, String mobileNo)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		Connection connection = DBConnection.getConnection();
		LOGGER.info("Obtained database connection");
		String message = "Successfully Updated the mobile number";
		try {

			LOGGER.warn("To update the mobilenumber of the  customer");
			preparedStatement = connection
					.prepareStatement(QueryMapper.CHANGE_MOBILENO);
			preparedStatement.setString(1, mobileNo);
			preparedStatement.setInt(2, accountId3);
			int result = preparedStatement.executeUpdate();
			if (result == 1) {
				connection.commit();
			}
		} catch (SQLException exception) {
			LOGGER.warn("Failed to update the mobilenumber of customer");
			throw new BankingException("Could not update the mobile number");
		}
		return message;
	}

	@Override
	public String changePassword(String username, int accountId4,
			String password1, String newPassword, String newPassword1)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String password = "";
		String message = "Password changed successfully";
		Connection connection = DBConnection.getConnection();
		LOGGER.info("Obtained database connection");

		try {
			LOGGER.warn("To change the password");
			preparedStatement = connection
					.prepareStatement(QueryMapper.GET_PASSWORD);
			preparedStatement.setInt(1, accountId4);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				password = resultSet.getString(1);
				LOGGER.warn("old password fetched");
			}

			if (password.equals(password1)) {
				LOGGER.warn("To check if both the passwords are same");
				if (newPassword.equals(newPassword1)) {
					preparedStatement = connection
							.prepareStatement(QueryMapper.CHANGE_PASSWORD);
					preparedStatement.setString(1, newPassword);
					preparedStatement.setInt(2, accountId4);
					int result = preparedStatement.executeUpdate();
					if (result == 1) {
						connection.commit();
						LOGGER.warn("Succesfully updated the password in Customer Table");
					}
					preparedStatement = connection
							.prepareStatement(QueryMapper.CHANGE_PASSWORD1);
					preparedStatement.setString(1, newPassword);
					preparedStatement.setString(2, username);
					int result1 = preparedStatement.executeUpdate();
					if (result1 == 1) {
						connection.commit();
						LOGGER.warn("Succesfully updated the password in Role Table");
					}
				} else {
					throw new BankingException("Password Mismatch!!!");
				}
			} else {
				LOGGER.info("Mismatch of entered passwords");
				throw new BankingException("Password mismatch occured");
			}
		} catch (SQLException exception) {
			LOGGER.error("Password could not be changed due to mismatch");
			throw new BankingException("Could not change the password");
		}

		return message;

	}

	@Override
	public int serviceRequest(int accountId6) throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = DBConnection.getConnection();
		LOGGER.info("Obtained database connection");
		int id = 0;
		try {
			LOGGER.warn(" Request to obtain Cheque Book");
			preparedStatement = connection
					.prepareStatement(QueryMapper.SERVICE_REQUEST);
			preparedStatement.setString(1, "Request For Cheque Book");
			preparedStatement.setInt(2, accountId6);
			preparedStatement.setString(3, "open");
			int result = preparedStatement.executeUpdate();
			LOGGER.warn(" Request for check book is accepted ");
			if (result == 1) {
				Statement statement = connection.createStatement();
				resultSet = statement
						.executeQuery(QueryMapper.SERVICE_SEQUENCE);
				if (resultSet.next()) {
					id = resultSet.getInt(1);
				}
				connection.commit();
				LOGGER.warn("Fetching and returning the generated Service ID");
			}
		} catch (SQLException exception) {
			LOGGER.error(" Service Request Failed");
			throw new BankingException("Request failed!!!");
		}
		return id;
	}

	@Override
	public String trackServiceRequest(int accountId7, int serviceId)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = DBConnection.getConnection();
		LOGGER.info("Obtained database connection");
		String status = "";
		try {
			LOGGER.warn("To know the status of request for checkbook");
			preparedStatement = connection
					.prepareStatement(QueryMapper.GET_SERVICE_STATUS);
			preparedStatement.setInt(1, serviceId);
			preparedStatement.setInt(2, accountId7);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Date date = new Date(2018);
				Date tempDate = addDays(resultSet.getDate(2), 3);
				if (resultSet.getDate(2).before(tempDate)) {
					status = resultSet.getString(1);
				} else if (resultSet.getDate(2).equals(tempDate)) {
					preparedStatement = connection
							.prepareStatement(QueryMapper.UPDATE_STATUS);
					preparedStatement.setString(1, "Dispatched");
					preparedStatement.setInt(2, serviceId);
					int result1 = preparedStatement.executeUpdate();
					if (result1 == 1) {
						connection.commit();
					}
					preparedStatement = connection
							.prepareStatement(QueryMapper.GET_SERVICE_STATUS);
					preparedStatement.setInt(1, serviceId);
					preparedStatement.setInt(2, accountId7);
					resultSet = preparedStatement.executeQuery();
					if (resultSet.next()) {
						status = resultSet.getString(1);
						LOGGER.warn(" Status of checkbook updated");
					}
				} else {
					LOGGER.warn("Checkbook status not available ");
					throw new BankingException("Status not available");
				}
			} else {
				LOGGER.warn("Service Id not found");
				throw new BankingException("Invalid Service Id!!!");
			}

		} catch (SQLException exception) {
			LOGGER.error("Service Id not available");
			throw new BankingException("Invalid Service Id!!!");
		}
		return status;
	}

	public Date addDays(Date date, int days) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return new Date(calendar.getTime().getTime());
	}

	@Override
	public String fundTransfer(int accountId8, int desAccountId, double amount)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		PreparedStatement transStmt = null;
		ResultSet resultSet = null;
		double accountBal = 0.0;
		Connection connection = DBConnection.getConnection();
		LOGGER.info("Obtained database connection");

		String msg = "Transaction Success";
		double newBalance1 = 0.0;
		double newBalance2 = 0.0;
		try {
			LOGGER.warn("To transfer fund");

			preparedStatement = connection
					.prepareStatement(QueryMapper.SELECT_AMOUNT);
			preparedStatement.setInt(1, accountId8);
			resultSet = preparedStatement.executeQuery();
			LOGGER.warn("Fetching the current balance of source account");

			if (resultSet.next()) {
				accountBal = resultSet.getDouble(1);
				newBalance2 = Double.parseDouble(resultSet.getString(1))
						- amount;
			}
			if ((accountBal - amount) > 1000) {
				preparedStatement = connection
						.prepareStatement(QueryMapper.SELECT_AMOUNT);
				preparedStatement.setInt(1, desAccountId);
				resultSet = preparedStatement.executeQuery();
				LOGGER.warn("Fetching the current balance of destination account");

				if (resultSet.next()) {
					newBalance1 = Double.parseDouble(resultSet.getString(1))
							+ amount;
				}

				preparedStatement = connection
						.prepareStatement(QueryMapper.UPDATE_BALANCE);
				preparedStatement.setDouble(1, newBalance1);
				preparedStatement.setInt(2, desAccountId);
				int result = preparedStatement.executeUpdate();
				if (result == 1) {
					transStmt = connection
							.prepareStatement(QueryMapper.TRANSACTIONS);
					transStmt.setString(1, "credit");
					transStmt.setDouble(2, amount);
					transStmt.setInt(3, desAccountId);
					int isCredit = transStmt.executeUpdate();
					if (isCredit == 0) {
						LOGGER.error("Error while crediting in table");
						throw new BankingException("Error while processing");

					} else {
						LOGGER.warn("Transaction credited in table");
					}
					transStmt.setString(1, "debit");
					transStmt.setDouble(2, amount);
					transStmt.setInt(3, accountId8);
					isCredit = transStmt.executeUpdate();
					if (isCredit == 0) {
						LOGGER.error("Error while debiting in table");
						throw new BankingException("Error while processing");

					} else {
						LOGGER.warn("Transaction debited in table");
					}
					LOGGER.warn("Fund transferred to destination account");
					connection.commit();
				}

				preparedStatement = connection
						.prepareStatement(QueryMapper.UPDATE_BALANCE);
				preparedStatement.setDouble(1, newBalance2);
				preparedStatement.setInt(2, accountId8);
				int result1 = preparedStatement.executeUpdate();

				if (result1 == 1) {
					connection.commit();
					LOGGER.warn("Fund transferred from the source account");
				}
			} else {
				throw new BankingException("Transaction failed");
			}
		} catch (SQLException e) {
			LOGGER.error("Fund transfer was failed");
			throw new BankingException("Transaction failed");
		}
		return msg;
	}

}
