package com.cg.onlinebanking.dao.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.onlinebanking.bean.CustomerDTO;
import com.cg.onlinebanking.dao.BankDaoImpl;
import com.cg.onlinebanking.dao.IBankDao;
import com.cg.onlinebanking.exceptions.BankingException;
import com.cg.onlinebanking.util.TestUtil;

public class AddUserTest {
	
	static IBankDao dao;
	static CustomerDTO customerDTO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new BankDaoImpl();
		customerDTO = new CustomerDTO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao = null;
		customerDTO = null;
	}

	@Before
	public void setUp() throws Exception {	
		customerDTO.setUsername(TestUtil.stringGenerator());
		customerDTO.setPassword("Sanjli#1");
		customerDTO.setCustomerName("Sanjli Kumari");
		customerDTO.setEmailId("sanjli@gmail.com");
		customerDTO.setPancard("FTVBH1234G");
		customerDTO.setAccountBalance(45000.00);
		customerDTO.setSecretAnswer("kumari");
		customerDTO.setPhoneNo("9612345684");
		customerDTO.setAddress("Bodh Gaya");
	}

	@After
	public void tearDown() throws Exception {
		customerDTO.setUsername("");
		customerDTO.setPassword("");
		customerDTO.setCustomerName("");
		customerDTO.setEmailId("");
		customerDTO.setPancard("");
		customerDTO.setAccountBalance(0.00);
		customerDTO.setSecretAnswer("");
		customerDTO.setPhoneNo("");
		customerDTO.setAddress("");
	}

	@Test
	public void test() {
		try {
			int accountNumber = dao.addUser(customerDTO);
			assertNotNull(accountNumber);
		} catch (BankingException exception) {
			fail(" Exception occured: " + exception.getMessage());
		}
	}

}
