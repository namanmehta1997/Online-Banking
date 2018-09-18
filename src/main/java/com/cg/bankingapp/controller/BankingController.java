package com.cg.bankingapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cg.bankingapp.entities.AdminBean;
import com.cg.bankingapp.entities.PayeeBean;
import com.cg.bankingapp.entities.ServiceRequestBean;
import com.cg.bankingapp.entities.TransactionBean;
import com.cg.bankingapp.entities.UserBean;
import com.cg.bankingapp.exception.BankingException;
import com.cg.bankingapp.service.IBankingService;

@Controller
public class BankingController {

	ArrayList<String> typeList;

	@Autowired
	IBankingService bankingService;

	UserBean user = null;

	@RequestMapping("/home")
	public String homePage() {
		return "index";
	}

	@RequestMapping("/adminHomePage")
	public String adminHomePage() {
		return "AdminHomePage";
	}

	@RequestMapping("/userHomePage")
	public ModelAndView UserHomePage() {
		ModelAndView mv = null;
		if (user != null) {
			mv = new ModelAndView("UserHomePage");
			mv.addObject("customerName", user.getCustomerName());
		} else {
			mv = new ModelAndView("UserHomePage");
		}
		return mv;
	}

	@RequestMapping("/LoginUserForm")
	public ModelAndView getUserHomePage() {
		user = null;
		return new ModelAndView("LoginUserForm", "user", new UserBean());
	}

	@RequestMapping(value = "/LoginUserCheck", params = "forgot")
	public ModelAndView ForgotPassword(
			@ModelAttribute("user") @Valid UserBean userLogin,
			BindingResult result) {

		return new ModelAndView("ForgotPasswordPage", "user", userLogin);

	}

	@RequestMapping(value = "/ForgotPasswordCheck")
	public ModelAndView checkUsernamePassword(@RequestParam String ans,
			@RequestParam String username, @ModelAttribute("user") UserBean user) {
		ModelAndView mv = new ModelAndView();
		try {
			if (bankingService.checkSecurity(ans, username)) {
				mv.setViewName("changeForgotPassword");
				mv.addObject("user", user);
			} else {
				mv = new ModelAndView("LoginUserForm", "user", new UserBean());
				mv.addObject("errmsg", "Wrong security answer");
				return mv;
			}
		} catch (BankingException e) {

		}
		return mv;

	}

	@RequestMapping(value = "/LoginUserCheck", params = "login")
	public ModelAndView UserHomePage(
			@ModelAttribute("user") @Valid UserBean userLogin,
			BindingResult result, HttpServletRequest request) {
		ModelAndView mv = null;

		try {
			HttpSession session = request.getSession();
			user = bankingService.checkUserCredentials(userLogin.getUsername(),
					userLogin.getPassword());

			if (user != null && user.getAccStatus().equals("active")) {
				mv = new ModelAndView("UserHomePage");
				mv.addObject("customerName", user.getCustomerName());
				Object blockValue = session.getAttribute(userLogin
						.getUsername());
				if (blockValue != null) {
					session.setAttribute(userLogin.getUsername(), null);
				}

			} else {
				mv = new ModelAndView("LoginUserForm", "user", user);
				if (user != null && user.getAccStatus().equals("block")) {
					mv.addObject("status", false);
					mv.addObject("flag", false);
				} else {

					Object str = session.getAttribute(userLogin.getUsername());
					if (str == null) {
						session.setAttribute(userLogin.getUsername(), 1);

					} else {
						int val = (int) session.getAttribute(userLogin
								.getUsername());
						val++;
						session.setAttribute(userLogin.getUsername(), val);

					}
					if ((int) session.getAttribute(userLogin.getUsername()) == 3) {
						bankingService.blockUser(userLogin.getUsername());
						mv.addObject("status", false);
					}
					mv.addObject("flag", true);
				}

			}

		} catch (BankingException e) {
			e.printStackTrace();

		}

		return mv;

	}

	@RequestMapping("/userViewMiniStatement")
	public String userViewMiniStatement() {
		return "miniStatement";
	}

	@RequestMapping("/miniStatement")
	public ModelAndView miniStatement() {

		ModelAndView mv = null;

		try {

			List<TransactionBean> transactionList = bankingService
					.getMiniStatement(user.getUsername());
			if (!transactionList.isEmpty()) {

				mv = new ModelAndView("miniStatement");
				mv.addObject("flag", "true");
				mv.addObject("transactionList", transactionList);

			} else {
				mv = new ModelAndView("miniStatement");
				mv.addObject("errmsg", "No transaction available!!!");
			}
		} catch (BankingException e) {
			mv = new ModelAndView("miniStatement");
			mv.addObject("errmsg", "No transaction available!!!");
		}

		return mv;
	}

	@RequestMapping("/detailedStatement")
	public ModelAndView detailedStatement() {
		return new ModelAndView("miniStatement", "check", "true");
	}

	@RequestMapping("/finalDetailedStatement")
	public ModelAndView finalDetailedStatement(@RequestParam String startDate,
			@RequestParam String endDate) {

		ModelAndView mv = null;

		try {
			List<TransactionBean> transactionList = bankingService
					.getDetailedStatement(startDate, endDate,
							user.getAccountId());
			if (!transactionList.isEmpty()) {
				mv = new ModelAndView("miniStatement");
				mv.addObject("flag", "true");
				mv.addObject("transactionList", transactionList);

			} else {
				mv = new ModelAndView("miniStatement");
				mv.addObject("errmsg", "No transaction available!!!");
			}
		} catch (BankingException e) {
			mv = new ModelAndView("miniStatement");
			mv.addObject("errmsg", "No transaction available!!!");
		}

		return mv;
	}

	@RequestMapping("/userChangeDetails")
	public ModelAndView changeDetails() {

		return new ModelAndView("changeUserDetails", "newUser", user);

	}

	@RequestMapping("/update")
	public ModelAndView updateDetails(
			@ModelAttribute("newUser") @Valid UserBean newUser,
			BindingResult result) {

		ModelAndView mv = null;

		try {
			if (!result.hasErrors()) {
				user = bankingService.changeUserDetails(newUser.getAddress(),
						newUser.getPhoneNo(), user.getAccountId());
				if (user != null) {
					mv = new ModelAndView("changeUserDetails", "newUser", user);
					mv.addObject("flag", true);

				} else {
					mv = new ModelAndView("changeUserDetails", "newUser", user);
					mv.addObject("errmsg", "User not added internal error!!!");

				}
			} else {
				mv = new ModelAndView("changeUserDetails", "newUser", newUser);
			}
		} catch (BankingException e) {
			mv = new ModelAndView("changeUserDetails", "newUser", user);
			mv.addObject("errmsg", "User not added internal error!!!");

		}

		return mv;

	}

	@RequestMapping("/userChangePassword")
	public ModelAndView changePasswordPage() {
		UserBean newUser = new UserBean();
		newUser.setUsername(user.getUsername());
		return new ModelAndView("changePassword", "newUser", newUser);

	}

	@RequestMapping("/updatePassword")
	public ModelAndView updatePassword(@RequestParam String password,
			@RequestParam String newPassword1,
			@RequestParam String newPassword2, @RequestParam String username) {
		ModelAndView mv = null;
		if (user == null || user.getPassword() == null) {
			if (newPassword1.equals(newPassword2)) {
				boolean flag = bankingService.changePasswordByUsername(
						newPassword2, username);
				if (flag) {
					mv = new ModelAndView("LoginUserForm", "user",
							new UserBean());
					mv.addObject("password", true);
					return mv;
				}
			} else {
				mv = new ModelAndView("changeForgotPassword");
				mv.addObject("errmsg", "Passwords did not match!!!");
				return mv;
			}
		}
		if (user.getPassword().equals(password)) {
			if (newPassword1.equals(newPassword2)) {
				boolean flag = bankingService.changePasswordByUsername(newPassword2, username);
				if (flag) {
					mv = new ModelAndView("changePassword");

					mv.addObject("flag", true);

				} else {
					mv = new ModelAndView("changePassword");
					mv.addObject("errmsg",
							"New password should be different!!!");
				}
			} else {
				mv = new ModelAndView("changePassword");
				mv.addObject("errmsg", "Passwords did not match!!!");
			}
		} else {
			mv = new ModelAndView("changePassword");
			mv.addObject("errmsg",
					"Old Password is not correct,Please try again!!!");
		}

		return mv;

	}

	@RequestMapping("/userFundTransfer")
	public ModelAndView userfundTransfer() {

		ModelAndView mv = null;

		try {
			List<PayeeBean> userList = new ArrayList<PayeeBean>();
			userList = bankingService.getAllUser(user.getAccountId());
			mv = new ModelAndView("fundTransferPage", "userList", userList);
		} catch (BankingException e) {

		}
		return mv;
	}

	@RequestMapping("/fundTransfer")
	public ModelAndView fundTransfer(@RequestParam int accno,
			@RequestParam double amt) {

		ModelAndView mv = null;

		try {
			List<PayeeBean> userList = new ArrayList<PayeeBean>();
			userList = bankingService.getAllUser(user.getAccountId());
			if (accno == -1) {
				mv = new ModelAndView("fundTransferPage", "userList", userList);
				mv.addObject("errmsg", "Please select a payee! ");
			} else {
				if (bankingService.fundSub(user.getAccountId(), amt)) {

					bankingService.fundTransfer(accno, amt);

					mv = new ModelAndView("fundTransferPage", "userList",
							userList);
					mv.addObject("flag", true);
					mv.addObject("msg", "Money transferred successfully!");

				} else {
					mv = new ModelAndView("fundTransferPage", "userList",
							userList);
					mv.addObject("errmsg",
							"Transfer amount should be less than available balance");

				}
			}
		} catch (BankingException e) {
			mv = new ModelAndView("fundTransferPage");
			if(e.getMessage().equals("block"))
				mv.addObject("errmsg", "The payee account is blocked!");
			else
				mv.addObject("errmsg",
					"Transfer amount should be less than available balance");
		}
		return mv;
	}

	@RequestMapping("/addPayee")
	public ModelAndView addPayee() {

		ModelAndView mv = new ModelAndView("addPayee");
		return mv;
	}

	@RequestMapping("/addPayeeDetails")
	public ModelAndView addPayeeDetails(@RequestParam int paccId,
			@RequestParam String pname) {

		ModelAndView mv = null;

		try {
			PayeeBean payee = new PayeeBean();
			payee.setPayeeAccountId(paccId);
			payee.setPayeeName(pname);
			if (paccId != user.getAccountId()) {
				if (bankingService.checkPayee(paccId, user.getAccountId())) {
					payee.setAccountId(user.getAccountId());
					if (bankingService.addPayee(payee)) {
						mv = new ModelAndView("addPayee");
						mv.addObject("flag", true);
						mv.addObject("msg",
								"Payee added successfully!");

					}

					else {
						mv = new ModelAndView("addPayee");
						mv.addObject("errmsg", "Payee not added");

					}
				} else {
					mv = new ModelAndView("addPayee");
					mv.addObject("errmsg",
							"No User available with this Payee Account Id");

				}
			} else {
				mv = new ModelAndView("addPayee");
				mv.addObject("errmsg", "User cannot add himself as payee");
			}
		} catch (BankingException e) {

			mv = new ModelAndView("addPayee");
			mv.addObject("errmsg",
					"No payee available with this Payee Account Id");
		}
		return mv;
	}

	@RequestMapping("/userRequestChequeBook")
	public ModelAndView userRequestChequebook() {
		ModelAndView mv = null;

		try {
			String serviceStatus = bankingService.getChequeBookStatus(user
					.getAccountId());

			if (serviceStatus != null) {
				if ("issued".equals(serviceStatus)) {
					mv = new ModelAndView("userRequestChequeBookForm");
					mv.addObject("check", "nDisplay");
					mv.addObject("errmsg",
							"Cheque book has been issued already!!!");
				} else if ("dispatched".equals(serviceStatus)) {
					mv = new ModelAndView("userRequestChequeBookForm");
					mv.addObject("check", "nDisplay");
					mv.addObject("errmsg",
							"Cheque book has been dispatched and it will reach within 3 working days!!!");
				} else if ("not issued".equals(serviceStatus)) {

					mv = new ModelAndView("userRequestChequeBookForm");
					mv.addObject("check", "display");
				} else if ("open".equals(serviceStatus)) {
					mv = new ModelAndView("userRequestChequeBookForm");
					mv.addObject("check", "nDisplay");
					mv.addObject("errmsg", "Your request is in process!!!");
				}

			} else {
				mv = new ModelAndView("userRequestChequeBookForm");
				mv.addObject("errmsg", "No data available!!!");

			}
		} catch (BankingException e) {
			mv = new ModelAndView("userRequestChequeBookForm");
			mv.addObject("errmsg", "internal error!!!");
		}
		return mv;

	}

	@RequestMapping("/raiseRequestForChequeBook")
	public ModelAndView raiseRequestForChequeBook(
			@RequestParam String serviceDescription) {
		ModelAndView mv = null;

		try {
			int serviceId = bankingService.raiseChequeBookRequest(
					user.getAccountId(), serviceDescription);

			if (serviceId != 0) {

				mv = new ModelAndView("userRequestChequeBookForm");
				mv.addObject("check", "display");
				mv.addObject("flag", "true");
				mv.addObject("serviceId", serviceId);

				// view = "view/userRequestChequeBookForm.jsp";

			} else {
				mv = new ModelAndView("userRequestChequeBookForm");
				mv.addObject("errmsg", "No data available!!!");
			}
		} catch (BankingException e) {
			mv = new ModelAndView("userRequestChequeBookForm");
			mv.addObject("errmsg", "internal error!!!");
		}
		return mv;

	}

	@RequestMapping("/userTrackServiceRequestForm")
	public String userTrackServiceRequestForm() {
		return "userTrackServiceRequestForm";
	}

	@RequestMapping("/userTrackServiceRequest")
	public ModelAndView userTrackServiceRequest(
			@RequestParam String serviceIdstr, @RequestParam String accountIdstr) {

		ModelAndView mv = null;

		try {

			if (serviceIdstr.isEmpty() == false) {
				int serviceId = Integer.parseInt(serviceIdstr);
				ServiceRequestBean serviceBean = bankingService
						.checkServiceExist(user.getAccountId(), serviceId);

				if (serviceBean != null) {

					mv = new ModelAndView("userTrackServiceRequestForm");
					mv.addObject("flag", "2");
					mv.addObject("serviceBean", serviceBean);

				} else {

					mv = new ModelAndView("userTrackServiceRequestForm");
					mv.addObject("errmsg",
							"Request Service Id does not exit!!!");

				}
			} else if (serviceIdstr.isEmpty() == true) {
				int accountId = Integer.parseInt(accountIdstr);
				List<ServiceRequestBean> serviceList = bankingService
						.checkServiceExistAcc(user.getAccountId(), accountId);

				if (!serviceList.isEmpty()) {
					
					mv = new ModelAndView("userTrackServiceRequestForm");
					mv.addObject("flag", "2");
					mv.addObject("serviceList", serviceList);

				} else {

					mv = new ModelAndView("userTrackServiceRequestForm");
					mv.addObject("errmsg",
							"Entered Account Id does not exit!!!");

				}
			}
		} catch (BankingException e) {
			mv = new ModelAndView("userTrackServiceRequestForm");
			mv.addObject("errmsg", "internal error!!!");
		}
		return mv;
	}

	@RequestMapping("/LoginAdmin")
	public ModelAndView getAdminHomePage() {
		AdminBean admin = new AdminBean();

		return new ModelAndView("LoginAdminForm", "admin", admin);
	}

	@RequestMapping("/LoginAdminCheck")
	public ModelAndView AdminHomePage(
			@ModelAttribute("admin") @Valid AdminBean admin,
			BindingResult result) {
		ModelAndView mv = null;

		try {

			if (bankingService.checkAdminCredentials(admin)) {
				mv = new ModelAndView("AdminHomePage");
			} else {
				mv = new ModelAndView("LoginAdminForm", "admin", admin);
				mv.addObject("flag", true);
			}

		} catch (BankingException e) {
			mv = new ModelAndView("LoginAdminForm", "admin", admin);
			mv.addObject("flag", true);

		}

		return mv;

	}

	@RequestMapping("/createNewAccountForm")
	public String createNewUserPage(Model model) {
		UserBean newUser = new UserBean();
		typeList = new ArrayList<String>();

		typeList.add("Savings Account");
		typeList.add("Current Account");

		model.addAttribute("typeList", typeList);
		model.addAttribute("newUser", newUser);

		return "createNewAccountForm";
	}

	@RequestMapping("/createNewAccount")
	public ModelAndView addNewUserPage(
			@ModelAttribute("newUser") @Valid UserBean newUser,
			BindingResult result) {
		ModelAndView mv = null;

		try {
			if (!result.hasErrors()) {
				newUser.setAccStatus("active");
				int accId = bankingService.addUser(newUser);
				if (accId != -1 && accId != 0) {
					mv = new ModelAndView("createNewAccountForm", "newUser",
							new UserBean());
					mv.addObject("accId", accId);
					mv.addObject("flag", true);
					typeList = new ArrayList<String>();
					typeList.add("Savings Account");
					typeList.add("Current Account");
					mv.addObject("typeList", typeList);

				} else if (accId == -1) {
					mv = new ModelAndView("createNewAccountForm", "newUser",
							newUser);
					typeList = new ArrayList<String>();
					typeList.add("Savings Account");
					typeList.add("Current Account");
					mv.addObject("typeList", typeList);
					mv.addObject(
							"errmsg",
							"Username already exist!!! Please provide correct info or take another username");
				}
				else if(accId == 0){
					typeList = new ArrayList<String>();
					typeList.add("Savings Account");
					typeList.add("Current Account");
					mv.addObject("typeList", typeList);
					mv.addObject(
							"errmsg",
							"You can't open same type of account twice");
				}
			} else {
				mv = new ModelAndView("createNewAccountForm", "newUser",
						newUser);
				typeList = new ArrayList<String>();
				typeList.add("Savings Account");
				typeList.add("Current Account");
				mv.addObject("typeList", typeList);
			}

		} catch (BankingException e) {
			mv = new ModelAndView("createNewAccountForm", "newUser", newUser);
			mv.addObject("errmsg", e.getMessage());

		}

		return mv;

	}

	@RequestMapping("/viewAllTransactionDetails")
	public String viewTransctionDetails() {
		return "transactionDetails";
	}

	@RequestMapping("/transactionDetails")
	public ModelAndView displayTransctionDetails(
			@RequestParam String startDate1, @RequestParam String endDate1) {

		ModelAndView mv = null;

		try {
			List<TransactionBean> transactionDetails = bankingService
					.getAllTransactions(startDate1, endDate1);
			if (!transactionDetails.isEmpty()) {

				mv = new ModelAndView("transactionDetails");
				mv.addObject("flag", "true");
				mv.addObject("transactionDetails", transactionDetails);

			} else {
				mv = new ModelAndView("transactionDetails");
				mv.addObject("errmsg", "No transaction available!!!");
			}
		} catch (BankingException e) {
			mv = new ModelAndView("transactionDetails");
			mv.addObject("errmsg", "No transaction available!!!");
		}

		return mv;
	}
}
