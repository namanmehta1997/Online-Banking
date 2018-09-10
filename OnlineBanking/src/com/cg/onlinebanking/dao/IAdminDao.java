package com.cg.onlinebanking.dao;


import java.sql.Date;
import java.util.List;

import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.bean.CustomerDTO;
import com.cg.onlinebanking.exceptions.BankingException;

public interface IAdminDao {

	
	public int addUser(CustomerDTO user) throws BankingException;
	
	public List<TransactionDTO> viewAllTransactions(Date startDate, Date endDate) throws BankingException;

}
