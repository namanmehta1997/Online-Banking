<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@page import="com.cg.bankingapp.entities.ServiceRequestBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<a href="userHomePage.obj">Home</a> | <a href="LoginUserForm.obj">Logout</a>

	<hr>
	<center>
	<h2>Track Service Request</h2>
	<form action="userTrackServiceRequest.obj" method = "post">
		<table>
		
			<tr>
				<td><strong>Enter Service Request ID: </strong></td>
				<td><input type = "text" name="serviceIdstr" /></td>
			</tr>
			<tr>
				<td colspan = "2"><strong>OR</strong></td>
			</tr>
			<tr>
				<td><strong>Enter Account ID: </strong></td>
				<td><input type = "text" name="accountIdstr" /></td>
			</tr>
			<tr>
				<td colspan = "2"><button type="submit">Submit</button></td>
			</tr>
		</table>
	</form>
	<c:if test="${flag eq 2}">
		<h2>Requested Service details are:</h2>
		<table border="1">
			<tr>
				<td>Service Request ID:</td>
				<td>${serviceBean.serviceId }</td>
			</tr>
			<tr>
				<td>Service Request Description: </td>
				<td>${serviceBean.serviceDescription }</td>
			</tr>
			<tr>
				<td>Service Request Raised date: </td>
				<td>${serviceBean.serviceRaisedDate}</td>
			</tr>
			<tr>
				<td>Service Request Status: </td>
				<td>${serviceBean.serviceStatus}</td>
			</tr>
		</table>
	</c:if>
	<center><span style="color:red;font-size:30px;">${errmsg}</span></center>

	</center>
</body>
</html>