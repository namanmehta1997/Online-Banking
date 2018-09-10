package com.cg.onlinebanking.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.bean.CustomerDTO;
import com.cg.onlinebanking.exceptions.BankingException;
import com.cg.onlinebanking.util.DBConnection;

public class AdminDaoImpl implements IAdminDao{

	
	@Override
	public int addUser(CustomerDTO userDto) throws BankingException {

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = -1;

		try {
			
			preparedStatement = connection.prepareStatement(QueryMapper.ADD_ACCOUNT);
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
			
			if(preparedStatement!=null) {
				preparedStatement.close();
			}
			
			preparedStatement = connection.prepareStatement(QueryMapper.TRANSACTIONS);
			preparedStatement.setString(1, "credit");
			preparedStatement.setDouble(2, userDto.getAccountBalance());
			preparedStatement.setInt(3, id);
			
			int result1 = preparedStatement.executeUpdate();
			if(result1==1) {
				connection.commit();
			}
			
			preparedStatement = connection.prepareStatement(QueryMapper.ADD_USERS);
			preparedStatement.setString(1, userDto.getUsername());
			preparedStatement.setString(2, userDto.getPassword());
			preparedStatement.setString(3, "user");
			
			int result2 = preparedStatement.executeUpdate();
			if(result2==1) {
				connection.commit();
			}
			
		} catch (SQLException sqlException) {
			throw new BankingException("Could not add account");
		}

		finally {

			if (preparedStatement != null && connection != null
					&& resultSet != null) {
				try {
					resultSet.close();
					preparedStatement.close();
					//connection.close();
				} catch (SQLException sqlException2) {
					throw new BankingException("Could not connect to database");
				}

			}
		}
		return id;
	}

	
	public List<TransactionDTO> viewAllTransactions(Date startDate, Date endDate) throws BankingException  {
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Connection connection = DBConnection.getConnection();
			List<TransactionDTO> list = new ArrayList<TransactionDTO>();
			

			try {
				preparedStatement = connection.prepareStatement(QueryMapper.GETALLTRANSACTIONS);
				preparedStatement.setDate(1, startDate);
				preparedStatement.setDate(2, endDate);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					TransactionDTO transactionDTO = new TransactionDTO();
					transactionDTO.setTransactionId(resultSet.getInt(1));
					transactionDTO.setTransactionDescription(resultSet.getString(2));
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
						//connection.close();
					} catch (SQLException e) {
						throw new BankingException("Could not fetch data");
					}

				}
			}
			return list;

		
		
	}

}
