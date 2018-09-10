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
import com.cg.onlinebanking.service.IUserService;
import com.cg.onlinebanking.service.UserServiceImpl;

public class User {

	Scanner scanner = new Scanner(System.in);
	CustomerDTO customerDTO = new CustomerDTO();
	private IUserService iUserService;

	public void start(String username) throws BankingException {
		iUserService = new UserServiceImpl();
		System.out.println("Welcome " + username+"\n");

		int choice = -1;

		while (choice != 7) {

			System.out.println("\n [1]Mini Statement\n [2]Detailed Statement\n [3]Change Adress/Mobile Number\n [4]Request for cheque book\n [5]Track Service request\n [6]Fund Transfer\n [7]Change Password\n [8]Logout\n");
			System.out.print("Choice> ");
			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				int accountId = iUserService.getAccountNumber(username);
				List<TransactionDTO> miniList = new ArrayList<TransactionDTO>();

				miniList = iUserService.getMiniStatement(accountId);

				System.out.println("Transaction Id Transaction Description  Date Of Transaction  Transaction Amount Account Number");
				System.out.println("----------------------------------------------------------------------------------------------");
				for (TransactionDTO transactionDTO : miniList) {
					System.out.println(transactionDTO.getTransactionId()+"  	 	"+transactionDTO.getTransactionDescription()+"   	 	"+transactionDTO.getDateOfTransaction()+"   	 	  "+transactionDTO.getTransactionAmount()+" 		"+transactionDTO.getAccountNumber());
				}
				break;
			case 2:
				int accountId1 = iUserService.getAccountNumber(username);
				Date endDate = null , startDate = null;
				List<TransactionDTO> list=new ArrayList<TransactionDTO>();
	        	 try {
	        		 System.out.println("Enter start date: (in format:dd/mm/yyyy)");
	 				try {
	 				String doj1=scanner.next();
	 				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	 				startDate = java.sql.Date.valueOf(LocalDate.parse(doj1, dateTimeFormatter));
	 				
	 				System.out.println("Enter end date: (in format:dd/mm/yyyy)");
	 				String doj=scanner.next();
	 				DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	 				endDate = java.sql.Date.valueOf(LocalDate.parse(doj, dateTimeFormatter1));
	 				
	 				} catch (DateTimeParseException dateTimeException) {
	 					System.err.println("Please enter date in format(dd/mm/yyyy), Please try again");
	 				} 
	 				
	 				
	        		 
					list = iUserService.getDetailedTransactions(accountId1,startDate, endDate);
					System.out.println("Transaction Id Transaction Description  Date Of Transaction  Transaction Amount Account Number");
					System.out.println("----------------------------------------------------------------------------------------------");
					for (TransactionDTO transactionDTO : list) {
						System.out.println(transactionDTO.getTransactionId()+"  	 	"+transactionDTO.getTransactionDescription()+"   	 	"+transactionDTO.getDateOfTransaction()+"   	 	  "+transactionDTO.getTransactionAmount()+" 		"+transactionDTO.getAccountNumber());
					
					}
				} catch (BankingException exception) {
					throw new BankingException(exception.getMessage()+"Data Cannot be Retrieved");
				}
	        	   break; 
			case 3:
				System.out.println("a. Change Address b. Change Mobile Number c.exit"); 
				String option = scanner.next();
				
				switch(option ){
				case "a":
					System.out.println("Enter new address");
					String address=scanner.next();
					int accountId2 = iUserService.getAccountNumber(username);
					
					String message = iUserService.changeAddress(accountId2,address); 
					System.out.println(message);
					break;
				case "b":
					System.out.println("Enter new Mobile Number");
					String mobileNo = scanner.next();
					int accountId3 = iUserService.getAccountNumber(username);
					
					String message1 = iUserService.changeMobileNumber(accountId3,mobileNo); 
					System.out.println(message1); 
					break;
				case "c" :
					System.exit(0);
				}
			break;
			case 4:
				System.out.println("Service Request for cheque book");
				int accountId6 = iUserService.getAccountNumber(username);
				try{
				int serviceID=iUserService.serviceRequest(accountId6);
				System.out.println("Your Request for cheque Book is succesful with service id " +serviceID);
				}catch (BankingException exception) {
					System.err.println(exception.getMessage()+"Please Try Again!!");
				}
				break;
			case 5:
				System.out.println("Enter your Service Request Id");
				int serviceId=scanner.nextInt();
				int accountId7 = iUserService.getAccountNumber(username);
				try{
					String serviceStatus=iUserService.trackServiceRequest(accountId7,serviceId);
					System.out.println("Status: " +serviceStatus);
					}catch (BankingException exception) {
						System.err.println(exception.getMessage()+"Please Try Again!!");
					}
					break;
			case 6:
				int accountId8 = iUserService.getAccountNumber(username);
				System.out.println("Enter the Account Id of the Payee");
				int desAccountId = scanner.nextInt();
				System.out.println("Enter the amount to be transfered");
				double amount = scanner.nextDouble();
				String message2= iUserService.fundTransfer(accountId8, desAccountId, amount);
				System.out.println(message2);
				break;
				
			case 7:
				System.out.println("Enter the Old Password:");
				String password1=scanner.next();
				System.out.println("Enter the new password:");
				String newPassword=scanner.next();
				System.out.println("Confirm new password:");
				String newPassword1=scanner.next();
				int accountId4 = iUserService.getAccountNumber(username);
				try{
					String message=iUserService.changePassword(username,accountId4,password1,newPassword,newPassword1);
					System.out.println(message);
				}catch(BankingException exception){
					System.err.println(exception.getMessage()+",Please Try Again");
				}
				
			break;
			
			case 8:
				System.exit(0);
				break;
				
			default:
				System.out.println("Please enter a valid option");
				break;
			}
		}
	}
}
