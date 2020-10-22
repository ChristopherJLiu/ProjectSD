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
	 <p>Logout
	 <p>Certeza que quer fazer logout? Se sim clique em Submit<br>
	<s:form action="logout" method="post">
		<s:submit  />
	</s:form>
	<input type="button" value="Voltar ao menu" onclick="window.location='index.jsp'">
	 </div>
</body>
