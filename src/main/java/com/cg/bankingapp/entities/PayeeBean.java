package com.cg.bankingapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Payee_Table")
public class PayeeBean {

	@Id
	@Column(name="sNo")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int sNo;
	
	@Column(name="Account_Id")
	private int accountId;
	
	@Column(name="Payee_Account_Id")
	private int payeeAccountId;
	
	@Column(name="Payee_name")
	private  String payeeName;
	
	
	
	
	public PayeeBean() {
		
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getPayeeAccountId() {
		return payeeAccountId;
	}
	public void setPayeeAccountId(int payeeAccountId) {
		this.payeeAccountId = payeeAccountId;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	@Override
	public String toString() {
		return "Payee [accountId=" + accountId + ", payeeAccountId="
				+ payeeAccountId + ", payeeName=" + payeeName + "]";
	}
	
	
}
