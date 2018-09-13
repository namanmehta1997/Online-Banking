package com.cg.onlinebanking.dao.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.onlinebanking.dao.BankDaoImpl;
import com.cg.onlinebanking.dao.IBankDao;
import com.cg.onlinebanking.exceptions.BankingException;

public class GetAccountNumberTest {
	static IBankDao dao;
	static String userName;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new BankDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao=null;
	}

	@Before
	public void setUp() throws Exception {
		userName="Naman";
	}

	@After
	public void tearDown() throws Exception {
		userName="";
	}

	@Test
	public void test1() {
		try {
			int accountNumber = dao.getAccountNumber(userName);
			assertNotNull(accountNumber);
		} catch (BankingException exception) {
			fail(" Exception occured: " + exception.getMessage());
		}
	}
	
	@Test
	public void test2() {
		try {
			int accountNumber = dao.getAccountNumber("");
			assertEquals(0,accountNumber);
		} catch (BankingException exception) {
			fail(" Exception occured: " + exception.getMessage());
		}
	}

}
