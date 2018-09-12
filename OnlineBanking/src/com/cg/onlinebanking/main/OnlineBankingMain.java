package com.cg.onlinebanking.main;

import java.util.Scanner;

import com.cg.onlinebanking.exceptions.BankingException;
import com.cg.onlinebanking.service.BankServiceImpl;
import com.cg.onlinebanking.service.IBankService;

public class OnlineBankingMain {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		int choice = -1;
		String role = null;
		int loginAttempts = 0;
		String role1 = "admin";
		String role2 = "user";
		IBankService bankService = new BankServiceImpl();

		while (choice != 2 && loginAttempts <= 2) {
			System.out.print("[1]Login \n [2]Quit \n");
			System.out.println("Choice>> ");
			choice = scan.nextInt();

			if (choice == 1) {
				System.out.print("UserName: ");
				String username = scan.next();
				System.out.print("Password: ");
				String password = scan.next();
				try {
					role = bankService.getRole(username, password);
					System.out.println(role + " identified...");
					if (role1.equals(role)) {
						Admin admin = new Admin();
						admin.start(username);
					} else if (role2.equals(role)) {
						User user = new User();
						user.start(username);
					} else {
						loginAttempts++;
						System.out.println(loginAttempts);
					}
				} catch (BankingException exception) {
					loginAttempts++;
					System.out.println(loginAttempts);
					if (loginAttempts == 3) {
						try {
							int accountId = bankService
									.getAccountNumber(username);
							System.err.println("Account Locked!!");
							System.out.println("Forgot passsword?");
							System.out.println("Enter your mother's maiden name: ");
							String maidenName = scan.next();
							String defaultPassword = bankService
									.getDefaultPassword(username, accountId,
											maidenName);
							if(defaultPassword != null)
							{
								System.out.println("Your generated password is: "
									+ defaultPassword);
								System.exit(0);
							}
							else
								System.out.println("Wrong answer to the Security Question!");
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
		System.out.println("Program Terminated");
	}
}
