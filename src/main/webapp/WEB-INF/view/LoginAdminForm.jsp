<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="<c:url value="/resources/loginuserformstyle.css" />" rel="stylesheet">
</head>
<body background="<c:url value="/resources/images/photo1.jpg" />">
	<a href="home.obj"><img src="<c:url value="/resources/images/back.png" />"/></a>
	<hr>
	<h1 align="center">Admin Login</h1>
	<c:url var="myAction" value="LoginAdminCheck.obj" />
	<form:form method="post" modelAttribute="admin" action="${myAction}">
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
				<td colspan="2" align="right"><input type="submit"
					value="Login" class="button" /></td>
			</tr>
		</table>
		</form:form>
		<center>
	<c:if test="${flag eq true}">
	
			<span style="color:red;font-size:30px;text-align:center">Wrong Credentials!!!</span>
		
	</c:if>
	</center>
</body>
</html>