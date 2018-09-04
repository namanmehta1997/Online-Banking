<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
          <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<a href="userFundTransfer.obj">Back</a> | <a href="LoginUserForm.obj">Logout</a>
	
	<hr>
	<center>
	<h3>Add Payee Page</h3>
	<div>
		<form action="addPayeeDetails.obj" method = "post">
			<table>
			<tr><td>Select Payee Account Id:</td>
			<td><input type="number" name="paccId" required></input></td></tr>
			<tr><td>Select Payee Nick Name:</td>
			<td><input type="text" name="pname" required></input></td></tr>
			
			<tr><td><button type="submit">Submit</button></td></tr>
			</table>
		</form>
	</div>
	<center><span style="color:red;font-size:30px;">${errmsg}</span></center>
	<c:if test="${flag eq true }">
		<h2>${msg}</h2>
	</c:if>
	</center>
</body>
</html>