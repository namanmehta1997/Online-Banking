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

	public void startAdmin(String username) throws BankingException {

		bankService = new BankServiceImpl();
		System.out.println("Welcome " + username + "\n");

		int choice = -1;

		while (choice != 3) {

			System.out
					.println(" [1]Create User Account\n [2]View All Transactions\n [3]LogOut\n");
			System.out.print("Enter your choice: ");
			try{
				choice = Integer.parseInt(scanner.nextLine());
			}
			catch(NumberFormatException exception){
				System.err.println("Please enter valid choice");
				return;
			}

			switch (choice) {
			case 1:
				try{
					System.out.println("Enter UserName:");
					String user = scanner.nextLine();
					System.out.println("Enter Password:");
					String password = scanner.nextLine();
					System.out.println("Enter Customer Name:");
					String customer = scanner.nextLine();
					System.out.println("Enter Email Id:");
					String email = scanner.nextLine();
					System.out.println("Enter Address:");
					String address = scanner.nextLine();
					System.out.println("Enter Phone number:");
					String phone = scanner.nextLine();
					System.out.println("Enter PAN Number:");
					String panNumber = scanner.nextLine();
					System.out.println("Enter Account Balance:");
					double accountBalance = Double.parseDouble(scanner.nextLine());
					System.out.println("Enter Mother's Maiden Name:");
					String maidenName = scanner.nextLine();
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

				}
				catch (NumberFormatException e) {
					System.err.println("Please enter valid details");
					return;
				}
				
				/**
				 * set bean
				 */
				
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
				List<TransactionDTO> allTransactions = new ArrayList<TransactionDTO>();
				try {
					System.out
							.println("Enter start date: (in format:dd/mm/yyyy)");
					try {
						String date1 = scanner.nextLine();
						dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						startDate = java.sql.Date.valueOf(LocalDate.parse(date1, dateTimeFormatter));

						System.out.println("Enter end date: (in format:dd/mm/yyyy)");
						String date2 = scanner.nextLine();
						DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						endDate = java.sql.Date.valueOf(LocalDate.parse(date2,dateTimeFormatter1));

						allTransactions = bankService.viewAllTransactions(startDate,
								endDate);
						System.out
								.println("Transaction Id Transaction Description  Date Of Transaction  Transaction Amount Account Number");
						System.out
								.println("----------------------------------------------------------------------------------------------");
						for (TransactionDTO transactionDTO : allTransactions) {
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
				return;

			default:
				System.out.println("Please enter a valid option");
				break;

			}

		}

	}

}
