<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login user</title>
</head>
<body>
	<a href="home.obj">Back</a>
	<hr>
	<h1 align="center">
		User Login 
	</h1>

	<c:url var="myAction" value="LoginUserCheck.obj" />
	<form:form method="post" modelAttribute="user" action="${myAction}">
		<table align="center">
			
			<tr>
				<td>Username : </td>
				<td><input type="text" name="username"/></td>
				<td>
					<span style="color:red">
						<form:errors path="username"></form:errors>
					</span>
				</td>
			</tr>
			<tr>
				<td>Password : </td>
				<td><input type="password" name="password"/></td>
				<td>
					<span style="color:red">
						<form:errors path="password"></form:errors>
					</span>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="left"><input type="submit"
					value="Login" /></td>
			</tr>
		</table>
		</form:form>
	<c:if test="${flag eq true}">
		<center>
			<span style="color:red;font-size:30px;text-align:center">Wrong Credentials!!!</span>
		</center>
	</c:if>
	
</body>
</html>