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

public class ChangeAddressTest {

	static IBankDao dao;
	int accountId;
	String address;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new BankDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao = null;
	}

	@Before
	public void setUp() throws Exception {
		accountId = 1001;
		address = "Hyderabad";
	}

	@After
	public void tearDown() throws Exception {
		accountId = 0;
		address="";
	}

	@Test
	public void test1() {
		try {
			String message = dao.changeAddress(accountId, address);
			assertEquals("Successfully Updated the address", message);
		} catch (BankingException exception) {
			fail(" Exception occured: " + exception.getMessage());
		}
	}
	
	@Test
	public void test2() {
		try {
			String message = dao.changeAddress(0, "");
			assertEquals("Invalid entry!", message);
		} catch (BankingException exception) {
			fail(" Exception occured: " + exception.getMessage());
		}
	}

}
