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
		IBankService bankService = null;
		try {
			bankService = new BankServiceImpl();
		} catch (BankingException e) {
			System.err.println("Can't connect to database!!! Please try again later");
			scan.close();
			return;
		}

		while (choice != 2) {
			System.out
					.println("---------------------------------------------------");
			System.out
					.println("|        Welcome to Banking System                |");
			System.out
					.println("---------------------------------------------------");
			System.out.print("1.Login\n2.Quit \n");
			System.out.print("Enter your choice: ");
			try {
				choice = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException exception) {
				System.err.println("Please enter a valid choice");
				OnlineBankingMain.main(args);
			}

			if (choice == 1) {
				System.out.print("UserName: ");
				username = scan.nextLine();
				System.out.print("Password: ");

				String password = scan.nextLine();
				try {
					role = bankService.getRole(username, password);
					System.out.println(role + " identified...");
					if (role1.equals(role)) {
						Admin admin = new Admin();
						admin.startAdmin(username);
					} else if (role2.equals(role)) {
						User user = new User();
						user.startUser(username);
					} else {
						if (countMap.containsKey(username)) {
							countMap.put(username, countMap.get(username) + 1);
						} else {
							countMap.put(username, 1);
						}
					}
				} catch (BankingException exception) {
					if (countMap.containsKey(username)) {
						countMap.put(username, countMap.get(username) + 1);
					} else {
						countMap.put(username, 1);
					}
					if (countMap.get(username) == 3) {
						try {
							int accountId = bankService
									.getAccountNumber(username);
							System.err.println("Account Locked!!");
							System.out.println("Forgot passsword?");
							System.out
									.println("Enter your mother's maiden name: ");
							String maidenName = scan.nextLine();
							String defaultPassword = bankService
									.getDefaultPassword(username, accountId,
											maidenName);
							if (defaultPassword != null) {
								System.out
										.println("Your generated password is: "
												+ defaultPassword);
								OnlineBankingMain.main(args);
							} else
								System.err
										.println("Wrong answer to the Security Question!");
							System.out.println("Exiting from the program...");
							System.exit(0);
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
