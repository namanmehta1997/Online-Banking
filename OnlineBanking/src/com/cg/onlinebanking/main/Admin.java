package com.cg.onlinebanking.main;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cg.onlinebanking.bean.TransactionDTO;
import com.cg.onlinebanking.bean.CustomerDTO;
import com.cg.onlinebanking.exceptions.BankingException;
import com.cg.onlinebanking.service.BankServiceImpl;
import com.cg.onlinebanking.service.IBankService;

public class Admin {

	Scanner scanner = new Scanner(System.in);

	CustomerDTO customerDTO;
	private DateTimeFormatter dateTimeFormatter;
	private IBankService bankService;

	public void start(String username) throws BankingException {
		// adminService = new AdminServiceImpl();
		bankService = new BankServiceImpl();
		System.out.println("Welcome " + username + "\n");

		int choice = -1;

		while (choice != 3) {

			System.out
					.println(" [1]Create User Account\n [2]View All Transactions\n [3]LogOut\n");
			System.out.print("Choice>> ");
			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter UserName:");
				String user = scanner.next();
				System.out.println("Enter Password:");
				String password = scanner.next();
				System.out.println("Enter Customer Name:");
				String customer = scanner.next();
				System.out.println("Enter Email Id:");
				String email = scanner.next();
				System.out.println("Enter Address:");
				String address = scanner.nextLine();
				address = scanner.nextLine();
				System.out.println("Enter Phone number:");
				String phone = scanner.next();
				System.out.println("Enter PAN Number:");
				String panNumber = scanner.next();
				System.out.println("Enter Account Balance:");
				double accountBalance = scanner.nextDouble();
				System.out.println("Enter Mother's Maiden Name:");
				String maidenName = scanner.next();

				/**
				 * set bean
				 */
				customerDTO = new CustomerDTO();
				customerDTO.setUsername(user);
				customerDTO.setPassword(password);
				customerDTO.setCustomerName(customer);
				customerDTO.setEmailId(email);
				customerDTO.setAddress(address);
				customerDTO.setPhoneNo(phone);
				customerDTO.setPancard(panNumber);
				customerDTO.setAccountBalance(accountBalance);
				customerDTO.setSecretAnswer(maidenName);
				try {
					if (bankService.detailsValidation(customerDTO) == false) {
						int id = bankService.addUser(customerDTO);
						System.out
								.println("Your Account is successfully created. Your generated Account Number is "
										+ id);
					}
				} catch (BankingException bankingException) {
					System.err.println(bankingException.getMessage()
							+ " Please try again");
				}
				break;
			case 2:
				Date endDate = null,
				startDate = null;
				List<TransactionDTO> list = new ArrayList<TransactionDTO>();
				try {
					System.out
							.println("Enter start date: (in format:dd/mm/yyyy)");
					try {
						String date1 = scanner.next();
						dateTimeFormatter = DateTimeFormatter
								.ofPattern("dd/MM/yyyy");
						startDate = java.sql.Date.valueOf(LocalDate.parse(
								date1, dateTimeFormatter));

						System.out
								.println("Enter end date: (in format:dd/mm/yyyy)");
						String date2 = scanner.next();
						DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter
								.ofPattern("dd/MM/yyyy");
						endDate = java.sql.Date.valueOf(LocalDate.parse(date2,
								dateTimeFormatter1));

						list = bankService.viewAllTransactions(startDate,
								endDate);
						System.out
								.println("Transaction Id Transaction Description  Date Of Transaction  Transaction Amount Account Number");
						System.out
								.println("----------------------------------------------------------------------------------------------");
						for (TransactionDTO transactionDTO : list) {
							System.out
									.println(transactionDTO.getTransactionId()
											+ "  	 	"
											+ transactionDTO
													.getTransactionDescription()
											+ "   	 	"
											+ transactionDTO
													.getDateOfTransaction()
											+ "   	 	  "
											+ transactionDTO
													.getTransactionAmount()
											+ " 		"
											+ transactionDTO.getAccountNumber());

						}

					} catch (DateTimeParseException dateTimeException) {
						System.err
								.println("Please enter date in format(dd/mm/yyyy), Please try again");
					}

				} catch (BankingException exception) {
					throw new BankingException(exception.getMessage()
							+ ", Data Cannot be Retrieved");
				}
				break;

			case 3:
				System.exit(0);
				break;

			default:
				System.out.println("Please enter a valid option");
				break;

			}

		}

	}

}
