package com.cg.onlinebanking.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cg.onlinebanking.bean.CustomerDTO;
import com.cg.onlinebanking.exceptions.BankingException;

public class AdminDaoImplTest {
 IAdminDao dao;
	@Test
	public void setUp() throws Exception {
		dao=new AdminDaoImpl();

	}
	public void testAddUser() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setUsername("Manasa");
		customerDTO.setPassword("Manasa");
		customerDTO.setCustomerName("Manasapatagar");
		customerDTO.setEmailId("manasa@gmail.com");
		customerDTO.setPancard("EYSTY1234G");
		customerDTO.setAccountBalance(20000.00);
		customerDTO.setSecretAnswer("chandana");
		customerDTO.setPhoneNo("9731328990");
		customerDTO.setAddress("Delhi");
		
		try {
			System.out.println("test");
			int accountNumber = dao.addUser(customerDTO);
			assertEquals(1025, accountNumber);
		} catch (BankingException exception) {
			fail(" exception occured " + exception.getMessage());
		}
		
	}

	
	public void testAddUser1() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setUsername("Harshitha");
		customerDTO.setPassword("Harshitha");
		customerDTO.setCustomerName("Harshitha");
		customerDTO.setEmailId("harshitha@gmail.com");
		customerDTO.setPancard("QWERT1234G");
		customerDTO.setAccountBalance(60000.00);
		customerDTO.setSecretAnswer("cat");
		customerDTO.setPhoneNo("9731235190");
		customerDTO.setAddress("Delhi");
		
		try {
			System.out.println("test");
			int accountNumber = dao.addUser(customerDTO);
			assertEquals(1026, accountNumber);
		} catch (BankingException exception) {
			fail(" exception occured " + exception.getMessage());
		}
		
	}
	
}