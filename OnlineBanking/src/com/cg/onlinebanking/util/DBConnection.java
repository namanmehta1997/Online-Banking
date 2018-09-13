package com.cg.onlinebanking.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;


import com.cg.onlinebanking.exceptions.BankingException;

/**************************************************************************************
*File                      : DBConnection.java
*Author Name               : Naman Mehta(Capgemini)
*Desc                      : Util class to provide database connections ( Singleton class )
*Version                   : 1.0
*Change Description        : NA
**************************************************************************************/

public class DBConnection {

	private static Connection connection = null;
	private static OracleDataSource dataSource = null;
	private static Properties properties = null;
	private static DBConnection dbConnection = null;
	private static Logger logger = null;

	// Making the class singleton
	private DBConnection() {
		logger = Logger.getLogger(DBConnection.class);
	}

	
	/*****************************************************************
	 * Method Name:getInstance()
	 * Input Parameters : N/A
	 * Return Type : DBConnection
	 * DBConnection instance 
	 * Author : Naman Mehta(Capgemini) -
	 * Description : To get an object of DBCOnnection
	 *******************************************************************/
	
	public static DBConnection getInstance() {
		if (dbConnection == null) {
			dbConnection = new DBConnection();
		}
		return dbConnection;
	}

	
	/****************************************************
	 * Method Name:loadProperties()
	 * Return Type : void
	 * Author - Naman Mehta(Capgemini)
	 * Description : Load Database Properties to properties object 
	 ****************************************************/
	
	private void loadProperty() throws BankingException {
		if (properties == null) {
			properties = new Properties();
			String fileName = "src/jdbc.properties";
			InputStream is = null;
			try {
				is = new FileInputStream(fileName);
				properties.load(is);
			} catch (IOException exception) {
				logger.error(exception.getMessage());
				throw new BankingException(
						"Sorry!!! Can't process your request now");
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
						throw new BankingException(
								"Sorry!!! Can't process your request now");
					}
				}
			}
		}
	}

	

	/****************************************************
	 * Method Name:prepareDataSource()
	 * Return Type : OracleDataSource object 
	 * Author - Naman Mehta(Capgemini)
	 * Description : Returns OracleDataSource object
	 ****************************************************/
	
	private OracleDataSource prepareDataSource()
			throws BankingException {
		loadProperty();
		if (dataSource == null) {
			if (properties != null) {
				String connectionURL = properties.getProperty("url");
				String username = properties.getProperty("username");
				String password = properties.getProperty("password");
				try {
					dataSource = new OracleDataSource();
					DriverManager.registerDriver(new OracleDriver());
					dataSource.setURL(connectionURL);
					dataSource.setUser(username);
					dataSource.setPassword(password);
				} catch (SQLException e) {
					logger.error(e.getMessage());
					throw new BankingException(
							"Sorry!!! Can't process your request now");
				}

			}
		}
		return dataSource;
	}

	
	/****************************************************
	 * Method Name:getConnection()
	 * Return Type : Connection
	 * Author - Naman Mehta(Capgemini)
	 * Description : Returns a Connection object 
	 ****************************************************/
	public Connection getConnection() throws BankingException {
		if (connection == null) {
			if (dataSource == null) {
				dataSource = prepareDataSource();
			}
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new BankingException(
						"Sorry!!! Can't process your request now");
			}

		}

		return connection;
	}
}
