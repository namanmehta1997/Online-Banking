package com.cg.bankingapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="user_login")
public class UserBean {

	
	@Column(name="username",unique=true)
	@NotEmpty(message="User name should not be empty")
	@Pattern(regexp="[A-Za-z]{3,19}",message="username should contain minimum 3 maximum 19 letters.")
	private String username;
	
	@Column(name="password")
	@NotEmpty(message="Password should not be empty")
	@Pattern(regexp="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$",
				message="Password must contain one digit, one uppercase, one lowercase, no whitespace with minimum 8 characters.")
	private String password;
	
	@Id
	@Column(name="Account_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int accountId;
	
	@Column(name="customer_name")
	@NotEmpty(message="Customer name should not be empty")
	@Pattern(regexp="[A-Z][a-z\\s]{3,19}",message="Start with capital letter has minimum 3 letters. ")
	private String customerName;
	
	@Column(name="Email")
	@NotEmpty(message="Email Id should not be empty")
	@Pattern(regexp="[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$",message="Enter valid EmailId. ")
	private String emailId;
	
	@Column(name="Address")
	@NotEmpty(message="Address should not be empty")
	private String address;
	
	@Column(name="phone_no")
	@NotEmpty(message="Phone number should not be empty")
	@Pattern(regexp="[1-9][\\d]{9}$",message="Mobile number should be 10 digits not starting with 0")
	private String phoneNo;
	
	@Column(name="Pancard")
	@NotEmpty(message="Pan card should not be empty")
	@Pattern(regexp="[A-Z]{5}\\d{4}[A-Z]{1}",message="Enter Valid PAN no.")
	private String pancard;
	
	@Column(name="Account_Type")
	@NotEmpty(message="Please select an account type")
	private String accountType;
	
	@Column(name="Amount")
	private double amount;
	
	@Column(name="security_answer")
	@NotEmpty(message="Please answer the security question")
	private String securityAns;
	
	@Column(name="Account_status")
	private String accStatus;
	
	public UserBean() {
		
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPancard() {
		return pancard;
	}
	public void setPancard(String pancard) {
		this.pancard = pancard;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getSecurityAns() {
		return securityAns;
	}

	public void setSecurityAns(String securityAns) {
		this.securityAns = securityAns;
	}

	public String getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}
	
	
}
