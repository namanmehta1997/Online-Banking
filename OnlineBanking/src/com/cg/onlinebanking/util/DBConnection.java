package com.cg.onlinebanking.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.cg.onlinebanking.exceptions.BankingException;

public class DBConnection {

	private static Properties properties = new Properties();
	private static Connection connection = null;

	
	
	public static Connection getConnection() throws BankingException {
		if (connection == null)
			try {

				InputStream inputStream = new FileInputStream(
						"resources/dbBanking.properties");
				properties.load(inputStream);
				String user = properties.getProperty("username");
				String password = properties.getProperty("password");
				String url = properties.getProperty("url");
				inputStream.close();
				DriverManager
						.registerDriver(new oracle.jdbc.driver.OracleDriver());
				connection = DriverManager.getConnection(url, user, password);
				// connection.setAutoCommit(false);

			} catch (FileNotFoundException exp) {
				throw new BankingException("File not found");

			} catch (IOException exp1) {
				throw new BankingException("IO exception");
			} catch (SQLException exp1) {
				throw new BankingException("SQL exception");
			}

		return connection;
	}
}
