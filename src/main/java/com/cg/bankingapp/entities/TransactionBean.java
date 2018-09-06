package com.cg.bankingapp.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="transaction")
public class TransactionBean {

	@Id
	@Column(name="Transaction_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int transactionId;
	
	@Column(name="Tran_description")
	private String transactionDescription;
	
	@Column(name="DateofTransaction")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfTransaction;
	
	@Column(name="TranAmount")
	private double transactionAmount;
	
	@Column(name="Account_No")
	private int accountNumber;
	
	@Column(name="amount")
	private double amount;
	
	@Column(name="username")
	private String username;
	

	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public TransactionBean() {
		
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionDescription() {
		return transactionDescription;
	}
	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}
	
	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}
	
	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	
	
	
}
