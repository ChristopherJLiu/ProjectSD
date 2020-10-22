<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consola Admin!</title>
</head>
<body>
	<div align="center">
	 <p>Login
	<s:form action="login" method="post">
		<s:text name="BI:" />
		<s:textfield key="BI" /><br>
		<s:text name="Password:" />
		<s:textfield key="pass" /><br>
		<s:submit />
	</s:form>
	 </div>
</body>
