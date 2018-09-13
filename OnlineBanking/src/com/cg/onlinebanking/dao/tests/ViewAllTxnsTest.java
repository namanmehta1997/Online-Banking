package com.cg.onlinebanking.dao.tests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.dao.BankDaoImpl;
import com.cg.onlinebanking.dao.IBankDao;
import com.cg.onlinebanking.exceptions.BankingException;

public class ViewAllTxnsTest {

	static IBankDao dao;
	static TransactionDTO txnDTO;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new BankDaoImpl();
		txnDTO = new TransactionDTO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao = null;
		txnDTO = null;
	}

	@Test
	public void test1() {
		try {
			DateTimeFormatter dateTimeFormatter;
			String startDate = "12/09/2017";
			String endDate = "13/09/2018";
			dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			Date startDateSQL = java.sql.Date.valueOf(LocalDate.parse(startDate, dateTimeFormatter));
			Date endDateSQL = java.sql.Date.valueOf(LocalDate.parse(endDate, dateTimeFormatter));
			List<TransactionDTO> allTransactions;
			allTransactions = dao.viewAllTransactions(startDateSQL, endDateSQL);
			assertNotNull(allTransactions);
		} catch (BankingException exception) {
			fail("Exception occured: "+exception.getMessage());
		}
	}
	
	@Test(expected = BankingException.class)
	public void test2() throws BankingException {
			Date startDate = null;
			Date endDate = null;
			List<TransactionDTO> allTransactions;
			allTransactions = dao.viewAllTransactions(startDate, endDate);
			assertNotNull(allTransactions);
	}

}
