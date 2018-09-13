package com.cg.onlinebanking.dao.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.onlinebanking.bean.RoleDTO;
import com.cg.onlinebanking.dao.BankDaoImpl;
import com.cg.onlinebanking.dao.IBankDao;
import com.cg.onlinebanking.exceptions.BankingException;

public class GetUserByNameTest {
	static IBankDao dao;
	static RoleDTO roleDTO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new BankDaoImpl();
		roleDTO = new RoleDTO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao = null;
		roleDTO = null;
		
	}

	@Before
	public void setUp() throws Exception {
		roleDTO.setUsername("admin");
	}

	@After
	public void tearDown() throws Exception {
		roleDTO.setUsername("");
	}

	@Test
	public void test1() {
		try {
			 RoleDTO role= dao.getUserByName(roleDTO.getUsername());
			assertNotNull(role);
		} catch (BankingException exception) {
			fail(" Exception occured: " + exception.getMessage());
		}
	
	}
	
	@Test
	public void test2() {
		try {
			 RoleDTO role= dao.getUserByName("");
			assertNull(role);
		} catch (BankingException exception) {
			fail(" Exception occured: " + exception.getMessage());
		}
	
	}

}
