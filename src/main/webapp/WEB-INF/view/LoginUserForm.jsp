<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login user</title>
<link href="<c:url value="/resources/loginuserformstyle.css" />"
	rel="stylesheet">
</head>
<body background="<c:url value="/resources/images/photo1.jpg" />">
	<a title="Back" href="home.obj"><img src="<c:url value="/resources/images/back.png" />"/></a>
	<hr>
	<h1 align="center">User Login</h1>

	<c:url var="myAction" value="LoginUserCheck.obj" />
	<form:form method="post" modelAttribute="user" action="${myAction}">
		<table align="center">

			<tr>
				<td><b>Username</b> :</td>
				<td><input type="text" name="username" /></td>
				<td><span style="color: red"> <form:errors
							path="username"></form:errors>
				</span></td>
			</tr>
			<tr>
				<td><b>Password</b> :</td>
				<td><input type="password" name="password" /></td>
				<td><span style="color: red"> <form:errors
							path="password"></form:errors>
				</span></td>
			</tr>
			<tr>
                <td align="left"><input type="submit" value="Login" class="button" name="login"/></td>
			    <td align="right"><input type="submit" value="Forgot Password" class="button" name="forgot"/></td>
				
				

			</tr>
		</table>
	</form:form>
	<c:if test="${flag eq true}">
		<center>
			<div class="error" align="center">Wrong Username/Password!!!</div>
		</center>
	</c:if>
	<c:if test="${status eq false}">
		<center>
			<div class="error" align="center">Your account has been blocked</div>
		</center>
	</c:if>
	<c:if test="${password eq true}">
		<center>
			<div class="error" align="center">Password successfully changed</div>
		</center>
	</c:if>
	<c:if test="${errmsg ne null}">
		<center>
			<div class="error" align="center">${errmsg}</div>
		</center>
	</c:if>

</body>
</html>