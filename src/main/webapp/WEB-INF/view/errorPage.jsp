<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<a href="login.obj">Home</a> | 
	<span style="text-align:right">Welcome ${userDetails.customerName}</span>
	<hr>
	<p>
	<span style="color:red;font-size:80px;text-align:center">
		${errmsg}
	</span>
	
</p>
</body>
</html>