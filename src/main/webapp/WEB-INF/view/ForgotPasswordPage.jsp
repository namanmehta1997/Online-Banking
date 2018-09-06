<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password</title>
<link href="<c:url value="/resources/loginuserformstyle.css" />" rel="stylesheet">
</head>
<body background="<c:url value="/resources/images/photo11.jpg" />" style="background-repeat:no-repeat; background-size:cover">
	<a title="Back" href="userFundTransfer.obj"><img src="<c:url value="/resources/images/back.png" />"/></a> 
	 <a title="Logout" href="LoginUserForm.obj" style="float:right"><img src="<c:url value="/resources/images/logout.png" />"/></a>
	
	<hr>
	<center>
	<c:url var="myAction" value="ForgotPasswordCheck.obj" />
	<form:form modelAttribute="user" method="post" action="${myAction}">
		<b>Mother's Maiden Name (Security Question)</b>: <input type="text"
			name="ans" required="true">
		<br>
		<input type="hidden" name="username" value="${user.username}">
		<input type="submit" value="Submit" class="button">
	</form:form>
    </center>
</body>
</html>