<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Raise request</title>
<link href="<c:url value="/resources/userhomepagestyle.css" />" rel="stylesheet">
</head>
<body background="<c:url value="/resources/images/photo1.jpg" />">
		<a title="Home" href="userHomePage.obj"><img src="<c:url value="/resources/images/home.png" />"/></a> 
		<a title="logout" href="LoginUserForm.obj" style="float:right"><img src="<c:url value="/resources/images/logout.png" />"/></a>
	<hr>
	<center>
	<c:if test="${check eq 'display'}">
	<form action="raiseRequestForChequeBook.obj">
	<h3 align="center">Raise a request to issue cheque book</h3>
		<table align="center">
			<tr>
				<td><b>Service Description: </b></td>
				<td><input type="text" name="serviceDescription" class="user" /></td>
			</tr>
			<tr>
				<td align="right">
					<input type="submit" value="Raise" class="button"/>		
				</td>
			</tr>
		</table>
	</form>
	</c:if>
	<center><span style="color:red;font-size:30px;">${errmsg}</span></center>
	<c:if test="${flag eq true}">
			<h2 align="center">Your service Id is ${serviceId}. Use it to track service request.</h2>
	</c:if>
	</center>
</body>
</html>