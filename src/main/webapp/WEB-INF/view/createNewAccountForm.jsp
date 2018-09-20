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
<body background="<c:url value="/resources/images/photo11.jpg" />">
	<a title="Home" href="adminHomePage.obj"><img src="<c:url value="/resources/images/home.png" />"/></a> 
	<a title="Logout" href="LoginAdmin.obj" style="float:right"><img src="<c:url value="/resources/images/logout.png" />"/></a>
	<hr>
	<h2 align="center">Create new account</h2>

	<c:url var="myAction" value="createNewAccount.obj" />
	<form:form method="post" modelAttribute="newUser" action="${myAction}">
		<table align="center">

			<tr>
				<td><b>Username</b>:</td>
				<td align="left"><form:input path="username" /></td>
				<td><form:errors path="username" cssStyle="color:red"></form:errors></td>
			</tr>
			<tr>
				<td><b>Password</b>:</td>
				<td align="left"><form:password path="password" /></td>
				<td><form:errors path="password" cssStyle="color:red"></form:errors></td>
			</tr>


			<tr>
				<td><b>Customer Name</b>:</td>
				<td align="left"><form:input path="customerName" /></td>
				<td><form:errors path="customerName" cssStyle="color:red"></form:errors></td>
			</tr>

			<tr>
				<td><b>Email ID</b>:</td>
				<td align="left"><form:input path="emailId" /></td>
				<td><form:errors path="emailId" cssStyle="color:red"></form:errors></td>
			</tr>

			<tr>
				<td><b>Address</b>:</td>
				<td align="left"><form:input path="address" /></td>
				<td><form:errors path="address" cssStyle="color:red"></form:errors></td>
			</tr>

			<tr>
				<td><b>Phone No</b>:</td>
				<td align="left"><form:input path="phoneNo" /></td>
				<td><form:errors path="phoneNo" cssStyle="color:red"></form:errors></td>
			</tr>
			
			<tr>
				<td><b>Account Type</b>:</td>
				<td align="left"><form:select path="accountType"> 
				<form:option value="" label="Please Select"/>
				<form:options items="${typeList}" />
 				</form:select>
				<td><form:errors path="accountType" cssStyle="color:red"></form:errors></td>
			</tr>

			<tr>
				<td><b>Amount</b>:</td>
				<td align="left"><form:input path="amount" /></td>
				<td><form:errors path="amount" cssStyle="color:red"></form:errors></td>
			</tr>
			
			<tr>
				<td><b>PAN</b>:</td>
				<td align="left"><form:input path="pancard" /></td>
				<td><form:errors path="pancard" cssStyle="color:red"></form:errors></td>
			</tr>
			
			<tr>
				<td><b>Mother's Maiden Name (Security Question)</b>: </td>
				<td align="left"><form:input path="securityAns" /></td>
				<td><form:errors path="securityAns" cssStyle="color:red"></form:errors></td>
			</tr>

			<tr>
				<td colspan="2" align="right"><input type="submit" value="Create Account" class="button" /></td>
			</tr>
		</table>
	</form:form>
	<center><span style="color:red;font-size:30px;">${errmsg}</span></center>
	<c:if test="${flag eq true}">
		<center>
			<h3 style="color:blue">Your account has been created. Your account id is ${accId}</h3>
		</center>
	</c:if>
</body>
</html>