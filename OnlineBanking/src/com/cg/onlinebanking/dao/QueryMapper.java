package com.cg.onlinebanking.dao;

public interface QueryMapper {

	public static final String GET_USER = "SELECT username, password,position FROM Role WHERE username=?";
	public static final String ADD_ACCOUNT = "INSERT INTO Customer(username, password, customerName, email, address, phoneNo, panNumber, accountNumber,accountBalance,secretAnswer) VALUES (?,?,?,?,?,?,?,accountSequence.NEXTVAL,?,?)";
	public static final String ACCOUNT_ID = "SELECT accountSequence.CURRVAL FROM DUAL";
	public static final String TRANSACTIONS = "INSERT INTO Transactions(transactionId,transactionDescription,dateOfTransaction,transactionAmount,accountNumber) VALUES(transactionSequence.NEXTVAL,?,SYSDATE,?,?)";
	public static final String GETALLTRANSACTIONS = "SELECT transactionId,transactionDescription,dateOfTransaction,transactionAmount,accountNumber FROM Transactions WHERE dateOfTransaction>? AND dateOfTransaction<?";
	public static final String GET_MINI_STATEMENT = "SELECT transactionId,transactionDescription,dateOfTransaction,transactionAmount,accountNumber FROM Transactions WHERE accountNumber=? ORDER BY transactionId DESC";
	public static final String ADD_USERS = "INSERT INTO Role(username, password,position) VALUES(?,?,?)";
	public static final String GET_ACCOUNT_NUMBER = "SELECT accountNumber FROM Customer WHERE username=?";
	public static final String GET_DETAILED_STATEMENT = "SELECT transactionId,transactionDescription,dateOfTransaction,transactionAmount,accountNumber FROM Transactions WHERE accountNumber=? AND  dateOfTransaction BETWEEN ? AND ?" ;
	public static final String CHANGE_ADDRESS = "UPDATE Customer SET address = ? WHERE accountNumber=? ";
	public static final String CHANGE_MOBILENO =  "UPDATE Customer SET phoneNo = ? WHERE accountNumber=? ";
	public static final String GET_PASSWORD = "SELECT password FROM Customer WHERE accountNumber=?";
	public static final String CHANGE_PASSWORD = "UPDATE Customer SET password=? WHERE accountNumber=?";
	public static final String CHANGE_PASSWORD1 = "UPDATE Role SET password=? WHERE username=?";
	public static final String SERVICE_REQUEST = "INSERT INTO serviceRequest(Service_ID,Service_Description,Account_ID,Service_Raised_Date,Service_status) VALUES(serviceIdSequence.NEXTVAL,?,?,SYSDATE,?)";
	public static final String SERVICE_SEQUENCE = "SELECT serviceIdSequence.CURRVAL FROM DUAL";
	public static final String GET_SERVICE_STATUS = "SELECT Service_status,Service_Raised_Date FROM serviceRequest WHERE Service_ID=? AND Account_ID=?";
	public static final String UPDATE_STATUS = "UPDATE serviceRequest SET Service_status=? WHERE Service_ID=?";
	public static final String UPDATE_BALANCE = "UPDATE Customer SET accountBalance=? WHERE accountNumber=?";
	public static final String SELECT_AMOUNT = "SELECT accountBalance FROM Customer WHERE accountNumber=?";
	public static final String UPDATE_SOURCEBALANCE = "UPDATE Customer SET accountBalance=? WHERE accountNumber=?";
	

}
