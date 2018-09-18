<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@page import="com.cg.bankingapp.entities.ServiceRequestBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="<c:url value="/resources/userhomepagestyle.css" />" rel="stylesheet">
</head>
<body background="<c:url value="/resources/images/photo12.jpg" />">
		<a title="Home" href="userHomePage.obj"><img src="<c:url value="/resources/images/home.png" />"/></a>
		 <a title="Logout" href="LoginUserForm.obj" style="float:right"><img src="<c:url value="/resources/images/logout.png" />"/></a>

	<hr>
	
	<h2>Track Service Request</h2>
	<form action="userTrackServiceRequest.obj" method = "post">
		<table>
		
			<tr>
				<td><strong><b>Enter Service Request ID:</b> </strong></td>
				<td><input type = "text" name="serviceIdstr" class="user" /></td>
			</tr>
			<tr>
				<td colspan = "2" align="center"><strong>&nbsp;OR</strong></td>
			</tr>
			<tr>
				<td><strong><b>Enter Account ID:</b> </strong></td>
				<td><input type = "text" name="accountIdstr" class="user" /></td>
			</tr>
			<tr>
				<td colspan = "2"><button type="submit" class="button">Submit</button></td>
			</tr>
		</table>
	</form>
	<c:if test="${flag eq 2}">
	 <c:forEach items="${serviceList}" var="serviceList">
		<h2>Requested Service details are:</h2>
		<table border="1">
			<tr>
				<td>Service Request ID:</td>
				<td>${serviceList.serviceId }</td>
			</tr>
			<tr>
				<td>Service Request Description: </td>
				<td>${serviceList.serviceDescription }</td>
			</tr>
			<tr>
				<td>Service Request Raised date: </td>
				<td>${serviceList.serviceRaisedDate}</td>
			</tr>
			<tr>
				<td>Service Request Status: </td>
				<td>${serviceList.serviceStatus}</td>
			</tr>
		</table>
	 </c:forEach>
	</c:if>
	<span style="color:red;font-size:30px;">${errmsg}</span>

	
</body>
</html>