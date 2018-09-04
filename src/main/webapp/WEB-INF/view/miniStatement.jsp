<%@page import="com.cg.bankingapp.entities.TransactionBean"%>
<%@page import="java.util.List"%>
<%@ page import ="java.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mini Statement</title>
</head>
<body>
	<a href="userHomePage.obj">Home</a> | <a href="LoginUserForm.obj">Logout</a>

	<hr>
	<center>
		<a href="miniStatement.obj">Mini Statement</a> | <a
			href="detailedStatement.obj">Detailed Statement</a>

	</center>
	<br></br>
	<center>
	<c:if test="${check eq true}">
		<form action="finalDetailedStatement.obj">
			
			Start date: <input type="date" name="startDate" required/>&nbsp;&nbsp;&nbsp; 
			End date: <input type="date" name="endDate" required/>&nbsp;&nbsp;&nbsp;
			<input type="submit" value="Submit" />

		</form>
	</c:if>
	</center>
	<c:if test="${flag eq true}">
		<table border="1" align="center">
			<tr>
				<td>Transaction ID</td>
				<td>Transaction Description</td>
				<td>Date of transaction</td>
				<td>Transaction Amount</td>
				<td>Account Number</td>
				<td>Available Balance</td>
			</tr>
			<c:forEach items="${transactionList}" var="list">
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