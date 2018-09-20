<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User HomePage </title>
<link href="<c:url value="/resources/userhomepagestyle.css" />" rel="stylesheet">
</head>
<body background="<c:url value="/resources/images/photo1.jpg" />">
 <span style="display:inline-block;text-align:right"><h3>Welcome ${customerName}</h3></span>
<a id="logout" title="Logout" href="LoginUserForm.obj"><img src="<c:url value="/resources/images/logout.png" />"/></a> 	
	<hr>
	
		<a href="userViewMiniStatement.obj" class="user">Mini/Detailed Statement</a><br><br>
		<a href="userChangeDetails.obj" class="user">Change Address/Phone Number</a><br><br>
		<a href="userRequestChequeBook.obj" class="user">Request Cheque Book</a><br><br>
		<a href="userTrackServiceRequestForm.obj" class="user">Track Service Request</a><br><br>
		<a href="userFundTransfer.obj" class="user">Fund Transfer</a><br><br>
		<a href="userChangePassword.obj" class="user">Change Password</a><br><br>
	
</body>
</html>