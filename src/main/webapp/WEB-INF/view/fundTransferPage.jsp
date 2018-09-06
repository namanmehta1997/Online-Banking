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
	<a href="userHomePage.obj">Home</a> | <a href="LoginUserForm.obj">Logout</a>

	<hr>
	<center>
		<h2>Fund Transfer Page</h2>
		<div>
			<form action="fundTransfer.obj" method="post">
				<table border="1">
					<tr>
						<td>Select a Payee:</td>
						<td><select name="accno">
								<option value="-1">---SELECT---</option>
								<c:forEach var="acc" items="${userList}">
									<option value="${acc.payeeAccountId}">${acc.payeeAccountId}--->${acc.payeeName}</option>
								</c:forEach>
						</select></td>
						<td><a href="addPayee.obj">Add a Payee</a></td>
					</tr>
					<tr>
						<td>Amount:</td>
						<td><input type="number" name="amt" min="0" step="0.01" required></input></td>	
						<td><button type="submit">Transfer</button></td>
					</tr>
				
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