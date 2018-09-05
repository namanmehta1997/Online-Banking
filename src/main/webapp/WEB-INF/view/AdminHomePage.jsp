<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User HomePage </title>
<link href="<c:url value="/resources/userhomepagestyle.css" />"
	rel="stylesheet">
</head>
<body background="<c:url value="/resources/images/photo1.jpg" />">
	<a title="Back" href="home.obj"><img src="<c:url value="/resources/images/back.png" />"/></a>
	 <a title="Logout"href="LoginAdmin.obj" style="float:right"><img src="<c:url value="/resources/images/logout.png" />"/></a>
	<hr>
	
		<a href="createNewAccountForm.obj" class="user">Create New Account</a><br><br>
		<a href="viewAllTransactionDetails.obj" class="user">View All Transactions</a>
	
</body>
</html>