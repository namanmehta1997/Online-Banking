package com.cg.onlinebanking.main;

import java.util.Scanner;

import com.cg.onlinebanking.exceptions.BankingException;
import com.cg.onlinebanking.service.IRoleService;
import com.cg.onlinebanking.service.IUserService;
import com.cg.onlinebanking.service.RoleServiceImpl;
import com.cg.onlinebanking.service.UserServiceImpl;

public class OnlineBankingMain {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int choice = -1;
		String role = null;
		int loginAttempts=0;
		String role1 = "admin";
		String role2 = "user";
		IRoleService roleService=new RoleServiceImpl();
		 IUserService iUserService = new UserServiceImpl();
		
		while(choice!=2 && loginAttempts<=2){
			System.out.print("[1]Login [2]Quit >");
			choice = scan.nextInt();
			
			if(choice==1){
				System.out.print("UserName? ");
				String username = scan.next();
				System.out.print("Password? ");
				String password = scan.next();
				try {
					role = roleService.getRole(username, password) ;
					System.out.println(role+"\n");
					if(role1.equals(role)){
						Admin admin = new Admin();
						admin.start(username);
					}else if(role2.equals(role)){
						User user = new User();
						user.start(username);
					}	else {
						loginAttempts++;
						System.out.println(loginAttempts);
					}		
				} catch (BankingException exception) {
					loginAttempts++;
					System.out.println(loginAttempts);
					if (loginAttempts==3) {
						try {
							int accountId = iUserService.getAccountNumber(username);
							System.out.println("Account Locked");
							System.out.println("Forgot passsword?");
							System.out.println("Enter your PET Name:");
							String petName = scan.next();
							String defaultPassword= roleService.getDefaultPassword(username,accountId,petName);
							System.out.println("Your generated password is: "+defaultPassword);
							System.exit(0);
						} catch (BankingException bankingException) {
							System.err.println(bankingException.getMessage()+", Please try again");
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

