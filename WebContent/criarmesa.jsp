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
	 <p>Criar Mesa
	<s:form action="criarmesa" method="post">
		<s:text name="Caso nao saiba qual e o ID eleicao consulte a opcao de Consultar Detalhes" /><br>
		<s:text name="ID da eleicao:" />
		<s:textfield key="ID" /><br>
		<s:text name="Local da mesa:" />
		<s:textfield key="local" /><br>
		<s:text name="Numero de terminais:" />
		<s:select label="nterminais" list="#{'1':'1', '2':'2', '3':'3'}" key="terminais" /><br>
		<s:text name="Bi da pessoa1:" />
		<s:textfield key="BI1" /><br>
		<s:text name="Bi da pessoa1:" />
		<s:textfield key="BI2" /><br>
		<s:text name="Bi da pessoa3:" />
		<s:textfield key="BI3" /><br>
		<s:submit />
	</s:form>
	 <input type="button" value="Voltar ao menu" onclick="window.location='index.jsp'">
	 </div>
</body>