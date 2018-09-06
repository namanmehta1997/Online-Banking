<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>index page</title>
<link href="<c:url value="/resources/indexstyle.css" />"
	rel="stylesheet">
</head>

<body background="<c:url value="/resources/images/photo1.jpg" />">


	<center>
		<marquee><h2>Welcome to Online Banking Services</h2></marquee>
		<br> <br> <a href="LoginUserForm.obj">User Login</a><br>
		<br> <a href="LoginAdmin.obj">Admin Login</a>


	</center>
</body>
</html>