package com.cg.onlinebanking.main;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import com.cg.onlinebanking.exceptions.BankingException;
import com.cg.onlinebanking.service.BankServiceImpl;
import com.cg.onlinebanking.service.IBankService;

public class OnlineBankingMain {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		int choice = -1;
		String role = null;
		Map<String, Integer> countMap = new HashMap<String, Integer>();
		String role1 = "admin";
		String role2 = "user";
		String username;
		IBankService bankService = new BankServiceImpl();

		while (choice != 2) {
			System.out.print("[1]Login [2]Quit >");
			try{
				choice = scan.nextInt();
			}
			catch(InputMismatchException exception){
				System.err.println("Please enter valid input");
				OnlineBankingMain.main(args);
			}

			if (choice == 1) {
				System.out.print("UserName? ");
				username = scan.next();
				System.out.print("Password? ");
				String password = scan.next();
				try {
					role = bankService.getRole(username, password);
					System.out.println(role + "\n");
					if (role1.equals(role)) {
						Admin admin = new Admin();
						admin.start(username);
					} else if (role2.equals(role)) {
						User user = new User();
						user.start(username);
					} else {
						if (countMap.containsKey(username)) {
							countMap.put(username, countMap.get(username) + 1);
						} else {
							countMap.put(username, 1);
						}
						System.out.println();
					}
				} catch (BankingException exception) {
					if (countMap.containsKey(username)) {
						countMap.put(username, countMap.get(username) + 1);
					} else {
						countMap.put(username, 1);
					}
					System.out.println(countMap.get(username));
					if (countMap.get(username) == 3) {
						try {
							int accountId = bankService
									.getAccountNumber(username);
							System.out.println("Account Locked");
							System.out.println("Forgot passsword?");
							System.out.println("Enter your PET Name:");
							String petName = scan.next();
							String defaultPassword = bankService
									.getDefaultPassword(username, accountId,
											petName);
							System.out.println("Your generated password is: "
									+ defaultPassword);
							OnlineBankingMain.main(args);
						} catch (BankingException bankingException) {
							System.err.println(bankingException.getMessage()
									+ ", Please try again");
						}

					}

					System.err.println(exception.getMessage());
				}
			}
		}
		scan.close();
		System.out.println("---------------Thank You--------------------");
	}
}
