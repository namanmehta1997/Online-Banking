<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password</title>
</head>
<body>
	<c:url var="myAction" value="ForgotPasswordCheck.obj" />
	<form:form modelAttribute="user" method="post" action="${myAction}">
		Mother's Maiden Name (Security Question): <input type="text"
			name="ans" required="true">
		<br>
		<input type="hidden" name="username" value="${user.username}">
		<input type="submit" value="Submit">
	</form:form>
</body>
</html>