<%@page import="com.cg.bankingapp.entities.TransactionBean"%>
<%@page import="java.util.List"%>
<%@page import ="java.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<a href="adminHomePage.obj">Home</a> | <a href="LoginAdmin.obj">Logout</a>
	<hr />
	
	
		<form action="transactionDetails.obj" align="center">
			
			Start date: <input type="date" name="startDate1" required/>&nbsp;&nbsp;&nbsp; 
			End date: <input type="date" name="endDate1" required/>&nbsp;&nbsp;&nbsp;
			<input type="submit" value="Submit" />

		</form>
	<br />

	<c:if test="${flag eq true}">
		<table border="1" align="center">
			<tr>
				<td>Transaction ID</td>
				<td>Transaction Description</td>
				<td>Date of transaction</td>
				<td>Transaction Amount</td>
				<td>Account Number</td>
				<td>Amount</td>
			</tr>
			<c:forEach items="${transactionDetails}" var="list">
				<tr>
					<td>${list.transactionId}</td>
					<td>${list.transactionDescription}</td>
					<td>${list.dateOfTransaction}</td>
					<td>${list.transactionAmount}</td>
					<td>${list.accountNumber}</td>
					<td>${list.amount}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
<center><span style="color:red;font-size:30px;">${errmsg}</span></center>
</body>
</html>