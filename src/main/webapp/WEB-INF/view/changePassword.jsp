<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="<c:url value="/resources/loginuserformstyle.css" />" rel="stylesheet">
</head>
<body background="<c:url value="/resources/images/photo11.jpg" />">
	<a title="Home" href="userHomePage.obj"><img src="<c:url value="/resources/images/home.png" />"/></a>
	 <a title="Logout" href="LoginUserForm.obj" style="float:right"><img src="<c:url value="/resources/images/logout.png" />"/></a>
	<center>
	<hr>
	<h2>Update Details</h2>
		<c:url var="myAction" value="updatePassword.obj" />
	<form:form method="post" modelAttribute="newUser" action="${myAction}">
	
			<table align="center">

			<tr>
				<td><input type="hidden" name="username" value="${newUser.username}" required/></td>
			</tr>
			<tr>
				<td><b>Enter Old Password</b>:</td>
				<td><input type="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" required/></td>
				<form:errors path="password" cssStyle="color:red"></form:errors>
			</tr>
			<tr>
				<td><b>Enter New Password</b>:</td>
				<td><input type="password" name="newPassword1" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" required/></td>
				<form:errors path="password" cssStyle="color:red"></form:errors>
			</tr>


			<tr>
				<td><b>Retype New Password</b>:</td>
				<td><input type="password" name="newPassword2" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" required/></td>
				<form:errors path="password" cssStyle="color:red"></form:errors>
				
			</tr>
	
			<tr>
				<td colspan="2" align="left"><input type="submit" value="Change Password" class="button"/></td>
			</tr>
		</table>
	</form:form>
	<center><span style="color:red;font-size:30px;">${errmsg}</span></center>
	<c:if test="${flag eq true}">
		<center><h1>Password has been updated successfully!!!</h1></center>
	</c:if>
	</center>
</body>
</html>