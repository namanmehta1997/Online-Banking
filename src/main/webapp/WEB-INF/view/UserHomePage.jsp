<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User HomePage </title>
</head>
<body>
	<a href="LoginUserForm.obj">Logout</a> | <span style="display:inline-block;text-align:right">Welcome ${customerName}</span>
	
	<hr>
	<table border="1" align="center">
		<tr><td><a href="userViewMiniStatement.obj">Mini Statement</a></td></tr>
		<tr><td><a href="userChangeDetails.obj">Change Address/Phone Number</a></td></tr>
		<tr><td><a href="userRequestChequeBook.obj">Request Cheque Book</a></td></tr>
		<tr><td><a href="userTrackServiceRequestForm.obj">Track Service Request</a></td></tr>
		<tr><td><a href="userFundTransfer.obj">Fund Transfer</a></td></tr>
		<tr><td><a href="userChangePassword.obj">Change Password</a></td></tr>
	</table>
</body>
</html>