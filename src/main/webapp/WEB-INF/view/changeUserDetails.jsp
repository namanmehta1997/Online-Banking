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
	<a title="Home" href="userHomePage.obj"><img
		src="<c:url value="/resources/images/home.png" />" /></a>
		
 <a title="Logout" href="LoginUserForm.obj" style="float:right"><img src="<c:url value="/resources/images/logout.png" />"/></a>


	<hr>
	<center>
	<h2>Update User Details</h2>

		<c:url var="myAction" value="update.obj" />
	<form:form method="post" modelAttribute="newUser" action="${myAction}">
		<table align="center">


			<tr>
				<td><b>Address</b>:</td>
				<td align="left"><form:input path="address" placeholder="${newUser.address}"/></td>
				<td><form:errors path="address" cssStyle="color:red"></form:errors></td>
			</tr>

			<tr>
				<td><b>Phone No</b>:</td>
				<td align="left"><form:input path="phoneNo" placeholder="${newUser.phoneNo}"/></td>
				<td><form:errors path="phoneNo" cssStyle="color:red"></form:errors></td>
			</tr>

			<tr>
				<td align="left"><form:hidden path="username" value="${newUser.username}"/></td>
				<td><form:errors path="username" cssStyle="color:red"></form:errors></td>
			</tr>
			<tr>
				<td align="left"><form:hidden path="password" value="${newUser.password}"/></td>
				<td><form:errors path="password" cssStyle="color:red"></form:errors></td>
			</tr>


			<tr>
				<td align="left"><form:hidden path="customerName"  value="${newUser.customerName}"/></td>
				<td><form:errors path="customerName" cssStyle="color:red"></form:errors></td>
			</tr>

			<tr>
				<td align="left"><form:hidden path="emailId" value="${newUser.emailId}"/></td>
				<td><form:errors path="emailId" cssStyle="color:red"></form:errors></td>
			</tr>

			<tr>
				<td align="left"><form:hidden path="pancard" value="${newUser.pancard}"/></td>
				<td><form:errors path="pancard" cssStyle="color:red"></form:errors></td>
			</tr>
			<tr>
				<td align="left"><form:hidden path="amount" value="${newUser.amount}"/></td>
				<td><form:errors path="amount" cssStyle="color:red"></form:errors></td>
			</tr>
			<tr>

				<td colspan="2" align="right"><input type="submit" class="button" value="Update"/></td>

				<td align="left"><form:hidden path="accStatus" value="${newUser.accStatus}"/></td>
				<td><form:errors path="accStatus" cssStyle="color:red"></form:errors></td>
			</tr>
			<tr>
				<td align="left"><form:hidden path="accountType" value="${newUser.accountType}"/></td>
				<td><form:errors path="accountType" cssStyle="color:red"></form:errors></td>
			</tr>
			<tr>
				<td align="left"><form:hidden path="securityAns" value="${newUser.securityAns}"/></td>
				<td><form:errors path="securityAns" cssStyle="color:red"></form:errors></td>
			</tr>			
			
		</table>
	</form:form>
	<center><span style="color:red;font-size:30px;">${errmsg}</span></center>
	<c:if test="${flag eq true}">
		<center><h2 style="color:green">Address and Mobile number updated successfully!!!</h2></center>
	</c:if>
	</center>
</body>
</html>