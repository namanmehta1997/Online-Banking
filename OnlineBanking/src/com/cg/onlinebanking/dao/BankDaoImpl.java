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

public class BankDaoImpl implements IBankDao {

	private final Logger LOGGER;
	private final Connection connection;
	public BankDaoImpl() throws BankingException {
		LOGGER = Logger.getLogger(BankDaoImpl.class);
		connection = DBConnection.getInstance().getConnection();
	}

	/*******************************************************************************************************
	 - Function Name	:	addUser
	 - Input Parameters	:	CustomerDTO userDto
	 - Return Type		:	int 
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	adding User Details to database
	 ********************************************************************************************************/
	@Override
	public int addUser(CustomerDTO userDto) throws BankingException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = -1;
		LOGGER.info("Adding User");

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
			LOGGER.error(sqlException.toString());
			throw new BankingException("Could not add account");
		}

		finally {

			if (preparedStatement != null && connection != null
					&& resultSet != null) {
				try {
					resultSet.close();
					preparedStatement.close();
				} catch (SQLException sqlException2) {
					LOGGER.error(sqlException2.toString());
					throw new BankingException("Could not connect to database");
				}

			}
		}
		LOGGER.info("User Added Successfully");
		return id;
	}

	
	/*******************************************************************************************************
	 - Function Name	:	viewAllTransactions
	 - Input Parameters	:	Date startDate,Date endDate
	 - Return Type		:	list
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	Fetch all transactions from database
	 ********************************************************************************************************/
	public List<TransactionDTO> viewAllTransactions(Date startDate, Date endDate)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<TransactionDTO> list = new ArrayList<TransactionDTO>();
		LOGGER.info("Viewing All the Transactuion");

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
			LOGGER.error(sqlException.toString());
			throw new BankingException("Could not fetch details");
		}

		finally {

			if (preparedStatement != null && connection != null
					&& resultSet != null) {
				try {
					resultSet.close();
					preparedStatement.close();
				} catch (SQLException e) {
					LOGGER.error(e.toString());
					throw new BankingException("Could not fetch data");
				}

			}
		}
		LOGGER.info("Transaction successfully fetched");
		return list;

	}

	
	/*******************************************************************************************************
	 - Function Name	:	getUserByName
	 - Input Parameters	:	String username
	 - Return Type		:	RoleDTO
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	retrieving UserDTO by username
	 ********************************************************************************************************/
	@Override
	public RoleDTO getUserByName(String username) throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		LOGGER.info("Reteriving UserDTO by username");

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
			LOGGER.error(sqlException.toString());
			throw new BankingException("Unable To Fetch Records");
		}
		LOGGER.info("User details fetched successfully");
		return role;
	}

	/*******************************************************************************************************
	 - Function Name	:	getDefaultPassword
	 - Input Parameters	:	String username,int accountId,String petName
	 - Return Type		:	String
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	retrieving DefaultPassword
	 ********************************************************************************************************/
	@Override
	public String getDefaultPassword(String username, int accountId,
			String maidenName) throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		CustomerDTO customer = new CustomerDTO();
		LOGGER.info("Fetching the default Password");
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.GET_SECURITYANS);
			preparedStatement.setLong(1, accountId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customer.setSecretAnswer(resultSet.getString(1));
			}
		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new BankingException("Something went Wrong. Please Try again later");
		}
		if(maidenName.equals(customer.getSecretAnswer())){
			String defaultPassword = username+"@"+accountId;
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
			} 
			catch (SQLException exception) {
				LOGGER.error(exception.toString());
				throw new BankingException("Something went wrong!!! Please try again later");
			}
			LOGGER.info("Password Changed Successfully");
			return defaultPassword;
		}
		else
			return null;
	}

	/*******************************************************************************************************
	 - Function Name	:	getMiniStatement
	 - Input Parameters	:	int accountId
	 - Return Type		:	list
	 - Throws			:  	BankingException
	 - Author			:	
	 - Creation Date	:	
	 - Description		:	retrieving MiniStatement
	 ********************************************************************************************************/
	@Override
	public List<TransactionDTO> getMiniStatement(int accountId)
			throws BankingException {
		LOGGER.info("Fetcing mini-statement of the account");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<TransactionDTO> list = new ArrayList<TransactionDTO>();

		try {
			
			preparedStatement = connection
					.prepareStatement(QueryMapper.GET_MINI_STATEMENT);
			preparedStatement.setInt(1, accountId);
			preparedStatement.setMaxRows(10);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
					TransactionDTO transactionDTO = new TransactionDTO();
					transactionDTO.setTransactionId(resultSet.getInt(1));
					transactionDTO.setTransactionDescription(resultSet
							.getString(2));
					transactionDTO.setDateOfTransaction(resultSet.getDate(3));
					transactionDTO.setTransactionAmount(resultSet.getDouble(4));
					transactionDTO.setAccountNumber(resultSet.getInt(5));
					list.add(transactionDTO);

			}
		} catch (SQLException exception) {
			LOGGER.error("Fetching mini-statement failed "+exception.toString());
			throw new BankingException("Something went wrong!!! Please try again later");
		}
		finally {
			if (preparedStatement != null && connection != null
					&& resultSet != null) {
				try {
					LOGGER.warn("Closing preparedStatement");
					preparedStatement.close();
				} catch (SQLException exception) {
					LOGGER.fatal("Connection failed:hence execution stopped");
					throw new BankingException("Unable to fetch data");
				}

			}
		}
		LOGGER.info("Mini statement successfully fetched");
		return list;
	}

	
	/*******************************************************************************************************
	 - Function Name	:	getAccountNumber
	 - Input Parameters	:	String username
	 - Return Type		:	int
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	retrieving AccountNumber
	 ********************************************************************************************************/
	@Override
	public int getAccountNumber(String username) throws BankingException {
		LOGGER.info("Fetching acount number");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = -1;

		try {
			
			preparedStatement = connection
					.prepareStatement(QueryMapper.GET_ACCOUNT_NUMBER);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				id = resultSet.getInt(1);
				
			}
		} catch (SQLException sqlException) {
			LOGGER.error("Fetching of accountnumber failed ");
			throw new BankingException("Unable to fetch account id");
		}
		LOGGER.info("account number successfully fetched");
		return id;
	}

	
	/*******************************************************************************************************
	 - Function Name	:	getDetailedTransactions
	 - Input Parameters	:	int accountId,Date startDate, Date endDate
	 - Return Type		:	list
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	retrieving DetailedTransactions
	 ********************************************************************************************************/
	@Override
	public List<TransactionDTO> getDetailedTransactions(int accountId,
			Date startDate, Date endDate) throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		LOGGER.info("Fetching details for the transaction");
		List<TransactionDTO> list = new ArrayList<TransactionDTO>();

		try {
			
			preparedStatement = connection
					.prepareStatement(QueryMapper.GET_DETAILED_STATEMENT);
			preparedStatement.setInt(1, accountId);
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
				
			}
		} catch (SQLException e1) {
			LOGGER.error("Failed to return the list of transactions ");
		}

		finally {

			if (preparedStatement != null && connection != null
					&& resultSet != null) {
				try {
					
					preparedStatement.close();
				} catch (SQLException e) {
					LOGGER.error(e.toString());
					throw new BankingException("Could not fetch data");
				}

			}
		}
		LOGGER.info("Returning the list of transactions");
		return list;

	}

	/*******************************************************************************************************
	 - Function Name	:	changeAddress
	 - Input Parameters	:	int accountId,String address
	 - Return Type		:	String
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	changing Address
	 ********************************************************************************************************/
	@Override
	public String changeAddress(int accountId, String address)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		LOGGER.info("Proceeding to Update the Address");
		String message = "Successfully Updated the address";
		try {
			
			preparedStatement = connection
					.prepareStatement(QueryMapper.CHANGE_ADDRESS);
			preparedStatement.setString(1, address);
			preparedStatement.setInt(2, accountId);
			int result = preparedStatement.executeUpdate();
			if (result == 1) {
				
				connection.commit();
			}
		} catch (SQLException exception) {
			LOGGER.error(exception.toString());
			throw new BankingException("Could not update the address");
		}
		LOGGER.info("Customer address updated successfully");
		return message;
	}

	/*******************************************************************************************************
	 - Function Name	:	changeMobileNumber
	 - Input Parameters	:	int accountId,String mobileNo
	 - Return Type		:	String
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	changing Address
	 ********************************************************************************************************/
	@Override
	public String changeMobileNumber(int accountId, String mobileNo)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		LOGGER.info("Proceeding to Change the Mobile Number");
		String message = "Successfully Updated the mobile number";
		try {

			preparedStatement = connection
					.prepareStatement(QueryMapper.CHANGE_MOBILENO);
			preparedStatement.setString(1, mobileNo);
			preparedStatement.setInt(2, accountId);
			int result = preparedStatement.executeUpdate();
			if (result == 1) {
				connection.commit();
			}
		} catch (SQLException exception) {
			LOGGER.error(exception.toString());
			throw new BankingException("Could not update the mobile number");
		}
		LOGGER.info("Customer's Mobile Number updated successfully");
		return message;
	}

	/*******************************************************************************************************
	 - Function Name	:	changePassword
	 - Input Parameters	:	String username,int accountId,String oldPassword,String newPassword,String newPassword1
	 - Return Type		:	String
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	changing Password
	 ********************************************************************************************************/
	@Override
	public String changePassword(String username, int accountId,
			String oldPassword, String newPassword, String newPassword1)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String password = "";
		String message = "Password changed successfully";
		LOGGER.info("Proceeding to change Password");

		try {
			
			preparedStatement = connection
					.prepareStatement(QueryMapper.GET_PASSWORD);
			preparedStatement.setInt(1, accountId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				password = resultSet.getString(1);
				LOGGER.info("old password fetched");
			}

			if (password.equals(oldPassword)) {
				
				if (newPassword.equals(newPassword1)) {
					preparedStatement = connection
							.prepareStatement(QueryMapper.CHANGE_PASSWORD);
					preparedStatement.setString(1, newPassword);
					preparedStatement.setInt(2, accountId);
					int result = preparedStatement.executeUpdate();
					if (result == 1) {
						connection.commit();
						
					}
					preparedStatement = connection
							.prepareStatement(QueryMapper.CHANGE_PASSWORD1);
					preparedStatement.setString(1, newPassword);
					preparedStatement.setString(2, username);
					int result1 = preparedStatement.executeUpdate();
					if (result1 == 1) {
						connection.commit();
					
					}
				} else {
					throw new BankingException("Wrong password!!!");
				}
			} else {
				LOGGER.info("Mismatch of entered passwords");
				throw new BankingException("Wrong Password");
			}
		} catch (SQLException exception) {
			LOGGER.error(exception.toString());
			throw new BankingException("Wrong password");
		}
        LOGGER.info("Password Changed Succsessfully");
		return message;

	}

	
	/*******************************************************************************************************
	 - Function Name	:	serviceRequest
	 - Input Parameters	:	int accountId
	 - Return Type		:	int
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	Request for Service
	 ********************************************************************************************************/
	@Override
	public int serviceRequest(int accountId) throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		LOGGER.info("Requesting To Obtain Checque Book");
		int id = 0;
		try {
			
			preparedStatement = connection
					.prepareStatement(QueryMapper.SERVICE_REQUEST);
			preparedStatement.setString(1, "Request For Cheque Book");
			preparedStatement.setInt(2, accountId);
			preparedStatement.setString(3, "open");
			int result = preparedStatement.executeUpdate();
			
			if (result == 1) {
				Statement statement = connection.createStatement();
				resultSet = statement
						.executeQuery(QueryMapper.SERVICE_SEQUENCE);
				if (resultSet.next()) {
					id = resultSet.getInt(1);
				}
				connection.commit();
				
			}
		} catch (SQLException exception) {
			LOGGER.error(exception.toString());
			throw new BankingException("Request failed!!!");
		}
		LOGGER.info("Request Has Been Generated fot the Cheque Book");
		return id;
	}

	
	/*******************************************************************************************************
	 - Function Name	:	trackServiceRequest
	 - Input Parameters	:	int accountId, int serviceId
	 - Return Type		:	String
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	check  status of service
	 ********************************************************************************************************/
	@Override
	public String trackServiceRequest(int accountId, int serviceId)
			throws BankingException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		LOGGER.info("TO Know the status for cheque book request");
		String status = "";
		try {
			
			preparedStatement = connection
					.prepareStatement(QueryMapper.GET_SERVICE_STATUS);
			preparedStatement.setInt(1, serviceId);
			preparedStatement.setInt(2, accountId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime(resultSet.getDate(2));
				calendar.add(Calendar.DATE, 3);
				Date tempDate =  new Date(calendar.getTime().getTime());
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
					preparedStatement.setInt(2, accountId);
					resultSet = preparedStatement.executeQuery();
					if (resultSet.next()) {
						status = resultSet.getString(1);
						
					}
				} else {
					LOGGER.info("Checkbook status not available ");
					throw new BankingException("Status not available");
				}
			} else {
				LOGGER.info("Service Id not found");
				throw new BankingException("Invalid Service Id!!!");
			}

		} catch (SQLException exception) {
			LOGGER.error(exception.toString());
			throw new BankingException("Invalid Service Id!!!");
		}
		LOGGER.info("Returning the status of cheque book request");
		return status;
	}


	/*******************************************************************************************************
	 - Function Name	:	fundTransfer
	 - Input Parameters	:	int accountId,int desAccountId,double amount
	 - Return Type		:	String
	 - Throws			:  	BankingException
	 - Author			:	
	 - Description		:	Transfer fund to other account 
	 ********************************************************************************************************/
	@Override
	public String fundTransfer(int accountId, int desAccountId, double amount)
			throws BankingException {
		LOGGER.info("Proceeding for fund Transfer");
		if(accountId==desAccountId){
			throw new BankingException("You can't transfer amount to yourself");
		}
		PreparedStatement preparedStatement = null;
		PreparedStatement transStmt = null;
		ResultSet resultSet = null;
		double accountBal = 0.0;
		

		String msg = "Transaction Success";
		double newBalance1 = 0.0;
		double sourceAcBalanceLeft = 0.0;
		try {
			

			preparedStatement = connection
					.prepareStatement(QueryMapper.SELECT_AMOUNT);
			preparedStatement.setInt(1, accountId);
			resultSet = preparedStatement.executeQuery();
			

			if (resultSet.next()) {
				accountBal = resultSet.getDouble(1);
				sourceAcBalanceLeft = accountBal-amount;
			}
			if ((accountBal - amount) > 1000) {
				preparedStatement = connection
						.prepareStatement(QueryMapper.SELECT_AMOUNT);
				preparedStatement.setInt(1, desAccountId);
				resultSet = preparedStatement.executeQuery();
				

				if (resultSet.next()) {
					newBalance1 = resultSet.getDouble(1) + amount;
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
						
						throw new BankingException("Error while processing");

					} else {
						LOGGER.info("Transaction credited in table");
					}
					transStmt.setString(1, "debit");
					transStmt.setDouble(2, amount);
					transStmt.setInt(3, accountId);
					isCredit = transStmt.executeUpdate();
					if (isCredit == 0) {
						LOGGER.warn("Error while debiting in table");
						throw new BankingException("Error while processing");

					} else {
						LOGGER.info("Transaction debited in table");
					}
					LOGGER.info("Fund transferred to destination account");
					connection.commit();
				}

				preparedStatement = connection
						.prepareStatement(QueryMapper.UPDATE_BALANCE);
				preparedStatement.setDouble(1, sourceAcBalanceLeft);
				preparedStatement.setInt(2, accountId);
				int result1 = preparedStatement.executeUpdate();

				if (result1 == 1) {
					connection.commit();
					LOGGER.info("Fund transferred from the source account");
				}
			} else {
				throw new BankingException("Transaction failed!!! Insufficient amount");
			}
		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new BankingException("Transaction failed");
		}
		LOGGER.info("Fund Transfer Successfully");
		return msg;
	}

}
