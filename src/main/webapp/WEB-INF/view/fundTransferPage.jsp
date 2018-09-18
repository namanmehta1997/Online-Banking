<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
          <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="<c:url value="/resources/userhomepagestyle.css" />" rel="stylesheet">
<link href="<c:url value="/resources/table.css" />" rel="stylesheet">

</head>
<body background="<c:url value="/resources/images/photo1.jpg" />">
	<a title="Home" href="userHomePage.obj"><img src="<c:url value="/resources/images/home.png" />"/></a>
	 <a title="Logout" href="LoginUserForm.obj" style="float:right"><img src="<c:url value="/resources/images/logout.png" />"/></a>

	<hr>
	<center>
		<h2>Fund Transfer Page</h2>
		<div>
			<form action="fundTransfer.obj" method="post">
				<table border="1">
				<!-- -------------------------------------------------------------- -->
					<%-- <tr>
						<td>Select your account:</td>
						<td><select name="acctype">
								<option value="-1">---SELECT---</option>
								<c:forEach var="acc" items="${userList}">
									<option value="${acc.payeeAccountType}">${acc.payeeAccountType}</option>
								</c:forEach>
						</select></td>
						<td><a href="selectAccType.obj">Select Account</a></td>
					</tr> --%>
					<!-- -------------------------------------------------------------- -->
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
						<td><input type="number" name="amt" min="0" step="0.01"required></input></td>	
						<td><button type="submit" class="button">Transfer</button></td>
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