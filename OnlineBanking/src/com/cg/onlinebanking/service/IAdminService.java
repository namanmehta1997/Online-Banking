package com.cg.onlinebanking.service;


import java.sql.Date;
import java.util.List;

import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.bean.CustomerDTO;
import com.cg.onlinebanking.exceptions.BankingException;

public interface IAdminService {
	
	public int addUser(CustomerDTO userDto) throws BankingException;
	

	public List<TransactionDTO> viewAllTransactions(Date startDate, Date endDate) throws BankingException;

	public boolean detailsValidation(CustomerDTO customerDTO) throws BankingException;
	
	
}
