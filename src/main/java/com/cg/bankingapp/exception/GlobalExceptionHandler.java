package com.cg.bankingapp.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler({BankingException.class})
	public ModelAndView catchError(BankingException ex){
		ModelAndView errorModel  =  new ModelAndView();
		errorModel.setViewName("errorPage");
		errorModel.addObject("errmsg", ex.getMessage());
		return errorModel;
	}
	
	@ExceptionHandler({Exception.class})
	public ModelAndView catchErrorAll(Exception ex){
		ModelAndView errorModel  =  new ModelAndView();
		errorModel.setViewName("errorPage");
		errorModel.addObject("errmsg", "Something went wrong!!! Please try again later");
		return errorModel;
	}
}
