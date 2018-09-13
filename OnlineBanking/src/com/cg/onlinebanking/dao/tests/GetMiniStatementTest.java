package com.cg.onlinebanking.dao.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.dao.BankDaoImpl;
import com.cg.onlinebanking.dao.IBankDao;
import com.cg.onlinebanking.exceptions.BankingException;

public class GetMiniStatementTest {


	static IBankDao dao;
	static TransactionDTO transactionDTO;
	static int accountId;
	
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
	}

	@After
	public void tearDown() throws Exception {
		accountId = 0;
	}

	@Test
	public void test1() {
		try {
			List<TransactionDTO> list = new ArrayList<TransactionDTO>();
			list = dao.getMiniStatement(accountId);
			boolean flag;
			int size = list.size();
			if(size == 0)
				flag = false;
			else
				flag = true;
			assertTrue(flag);
		} catch (BankingException exception) {
			fail(" Exception occured: " + exception.getMessage());
		}
	}

	
	@Test
	public void test2() {
		try {
			List<TransactionDTO> list = new ArrayList<TransactionDTO>();
			list = dao.getMiniStatement(0);
			boolean flag;
			int size = list.size();
			if(size == 0)
				flag = false;
			else
				flag = true;
			assertFalse(flag);
		} catch (BankingException exception) {
			fail(" Exception occured: " + exception.getMessage());
		}
	}
}
