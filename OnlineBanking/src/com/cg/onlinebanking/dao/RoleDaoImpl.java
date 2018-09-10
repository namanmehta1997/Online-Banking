package com.cg.onlinebanking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cg.onlinebanking.bean.RoleDTO;
import com.cg.onlinebanking.exceptions.BankingException;
import com.cg.onlinebanking.util.DBConnection;

public class RoleDaoImpl implements IRoleDao {

	@Override
	public RoleDTO getUserByName(String username) throws BankingException {
		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;
		connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		
		RoleDTO role = null;
		try{
				preparedStatement = connection.prepareStatement(QueryMapper.GET_USER);
			
						preparedStatement.setString(1, username);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				role = new RoleDTO();
				role.setUsername(resultSet.getString(1));
				role.setPassword(resultSet.getString(2));
				role.setPosition(resultSet.getString(3));
			}
		} catch (SQLException sqlException) {
			//log.error(e);
			throw new BankingException("Unable To FEtch Records");
		}
		return role;
		}

	@Override
	public String getDefaultPassword(String username,int accountId, String petName) throws BankingException {
		PreparedStatement preparedStatement = null;
		Connection connection = DBConnection.getConnection();
		String defaultPassword = "12345";
		try {
			preparedStatement=connection.prepareStatement(QueryMapper.CHANGE_PASSWORD);
			preparedStatement.setString(1, defaultPassword);
			preparedStatement.setInt(2, accountId);
			int result = preparedStatement.executeUpdate();
			if(result==1){
				connection.commit();
			}
			preparedStatement=connection.prepareStatement(QueryMapper.CHANGE_PASSWORD1);
			preparedStatement.setString(1, defaultPassword);
			preparedStatement.setString(2, username);
			int result1 = preparedStatement.executeUpdate();
			if(result1==1){
				connection.commit();
			}
		} catch (SQLException exception) {
			throw new BankingException("Could not update the address");
		}
		return defaultPassword;
	}

}
