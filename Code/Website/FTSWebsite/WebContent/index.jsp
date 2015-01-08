<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FTS Login</title>
</head>
<body>
	<form action="ProcessServlet" method="POST">
		<table>
			<tr>
				<td>Username</td>
				<td><input type="text" name="txtUsername"></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="txtPassword"></td>
			</tr>
			<tr>
				<td><input type="submit" name="Login" value="Login"></td>
				<td><input type="reset" name="Reset"></td>
			</tr>
		</table>
	</form>
</body>
</html>