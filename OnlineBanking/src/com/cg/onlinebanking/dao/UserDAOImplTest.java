package com.cg.onlinebanking.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cg.onlinebanking.bean.CustomerDTO;
import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.exceptions.BankingException;

public class UserDAOImplTest {
IUserDAO dao;
	@Before
	public void setUp() throws Exception {
		dao=new UserDAOImpl();
	}

	@Test
	public void testGetMiniStatement() {
		try {
			int accountId = 0;
			List<TransactionDTO> list=dao.getMiniStatement(accountId);
			assertNotNull(list);
		} catch (BankingException exception) {
			fail(" exception occured "+exception.getMessage());
		}
	}

	@Test
	public void testChangeAddress() {
		int accountId2 = 0;
		String address = null;
		CustomerDTO customerDTO=new CustomerDTO();
		customerDTO.setAddress(address);
		try {
			String id=dao.changeAddress(accountId2,address);
			assertNotSame("1025", id.trim());
		} catch (BankingException exception) {
			fail(" exception occured "+exception.getMessage());
		}
	}

	
}
