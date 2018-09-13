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

public class User {

	Scanner scanner = new Scanner(System.in);
	CustomerDTO customerDTO = new CustomerDTO();
	private IBankService bankService;

	public void startUser(String username) throws BankingException {
		bankService = new BankServiceImpl();
		System.out.println("Welcome " + username + "\n");

		int choice = -1;

		while (choice != 7) {

			System.out.println("1.Mini Statement\n"
							+ "2.Detailed Statement\n"
							+ "3.Change Address/Mobile Number\n"
							+ "4.Request for cheque book\n"
							+ "5.Track Service request\n"
							+ "6.Fund Transfer\n"
							+ "7.Change Password\n"
							+ "8.Logout\n");
			System.out.print("Select your choice:  ");
			try{
				choice = Integer.parseInt(scanner.nextLine());
			}
			catch(NumberFormatException exception){
				System.err.println("Please enter valid choice");
				return;
			}
			

			switch (choice) {
			case 1:
				int accountId = bankService.getAccountNumber(username);
				List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();

				transactions = bankService.getMiniStatement(accountId);

				System.out
						.println("Transaction Id Transaction Description  Date Of Transaction  Transaction Amount Account Number");
				System.out
						.println("----------------------------------------------------------------------------------------------");
				for (TransactionDTO transactionDTO : transactions) {
					System.out.println(transactionDTO.getTransactionId()
							+ "  	 	"
							+ transactionDTO.getTransactionDescription()
							+ "   	 	" + transactionDTO.getDateOfTransaction()
							+ "   	 	  "
							+ transactionDTO.getTransactionAmount() + " 		"
							+ transactionDTO.getAccountNumber());
				}
				break;
			case 2:
				int accountId1 = bankService.getAccountNumber(username);
				Date endDate = null,
				startDate = null;
				List<TransactionDTO> list = new ArrayList<TransactionDTO>();
				try {
					System.out
							.println("Enter start date: (in format:dd/mm/yyyy)");
					try {
						String doj1 = scanner.nextLine();
						DateTimeFormatter dateTimeFormatter = DateTimeFormatter
								.ofPattern("dd/MM/yyyy");
						startDate = java.sql.Date.valueOf(LocalDate.parse(doj1,
								dateTimeFormatter));

						System.out
								.println("Enter end date: (in format:dd/mm/yyyy)");
						String doj = scanner.nextLine();
						DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter
								.ofPattern("dd/MM/yyyy");
						endDate = java.sql.Date.valueOf(LocalDate.parse(doj,
								dateTimeFormatter1));

					} catch (DateTimeParseException dateTimeException) {
						System.err
								.println("Please enter date in format(dd/mm/yyyy), Please try again");
					}

					list = bankService.getDetailedTransactions(accountId1,
							startDate, endDate);
					System.out
							.println("Transaction Id Transaction Description  Date Of Transaction  Transaction Amount Account Number");
					System.out
							.println("----------------------------------------------------------------------------------------------");
					for (TransactionDTO transactionDTO : list) {
						System.out.println(transactionDTO.getTransactionId()
								+ "  	 	"
								+ transactionDTO.getTransactionDescription()
								+ "   	 	"
								+ transactionDTO.getDateOfTransaction()
								+ "   	 	  "
								+ transactionDTO.getTransactionAmount() + " 		"
								+ transactionDTO.getAccountNumber());

					}
				} catch (BankingException exception) {
					throw new BankingException(exception.getMessage()
							+ "Data Cannot be Retrieved");
				}
				break;
			case 3:
				System.out
						.println("a. Change Address b. Change Mobile Number c.exit");
				String option = scanner.nextLine();

				switch (option) {
				case "a":
					System.out.println("Enter new address");
					String address = scanner.nextLine();
					int accountId2 = bankService.getAccountNumber(username);

					String message = bankService.changeAddress(accountId2,
							address);
					System.out.println(message);
					break;
				case "b":
					System.out.println("Enter new Mobile Number");
					String mobileNo = scanner.nextLine();
					int accountId3 = bankService.getAccountNumber(username);

					String message1 = bankService.changeMobileNumber(
							accountId3, mobileNo);
					System.out.println(message1);
					break;
				case "c":
					break;
				}
				break;
			case 4:
				System.out.println("Service Request for cheque book");
				int accountId6 = bankService.getAccountNumber(username);
				try {
					int serviceID = bankService.serviceRequest(accountId6);
					System.out
							.println("Your Request for cheque Book is succesful with service id "
									+ serviceID);
				} catch (BankingException exception) {
					System.err.println(exception.getMessage()
							+ "Please Try Again!!");
				}
				break;
			case 5:
				System.out.println("Enter your Service Request Id");
				int serviceId=0;
				try{
					serviceId = Integer.parseInt(scanner.nextLine());
				}
				catch (NumberFormatException e) {
					System.err.println("Please enter valid choice");
					return;
				}
				
				int accountId7 = bankService.getAccountNumber(username);
				try {
					String serviceStatus = bankService.trackServiceRequest(
							accountId7, serviceId);
					System.out.println("Status: " + serviceStatus);
				} catch (BankingException exception) {
					System.err.println(exception.getMessage()
							+ "Please Try Again!!");
				}
				break;
			case 6:
				int accountId8 = bankService.getAccountNumber(username);
				System.out.println("Enter the Account Id of the Payee");
				int desAccountId=0;
				double amount=0;
				try{
					desAccountId = Integer.parseInt(scanner.nextLine());
					System.out.println("Enter the amount to be transfered");
					amount = Double.parseDouble(scanner.nextLine());
					String message2 = bankService.fundTransfer(accountId8,
							desAccountId, amount);
					System.out.println(message2);
				}
				catch (NumberFormatException e) {
					System.err.println("Please enter valid choice");
					return;
				}
				
				break;

			case 7:
				System.out.println("Enter the Old Password:");
				String password1 = scanner.nextLine();
				System.out.println("Enter the new password:");
				String newPassword = scanner.nextLine();
				System.out.println("Confirm new password:");
				String newPassword1 = scanner.nextLine();
				int accountId4 = bankService.getAccountNumber(username);
				try {
					String message = bankService.changePassword(username,
							accountId4, password1, newPassword, newPassword1);
					System.out.println(message);
				} catch (BankingException exception) {
					System.err.println(exception.getMessage()
							+ ",Please Try Again");
				}
				break;

			case 8:
				return;

			default:
				System.out.println("Please enter a valid option");
				break;
			}
		}
	}
}
