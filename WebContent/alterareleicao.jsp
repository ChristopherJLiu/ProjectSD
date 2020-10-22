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
	 <p>Alterar eleicao
	<s:form action="alterareleicao" method="post">
		<s:text name="Caso nao saiba qual e o ID eleicao consulte a opcao de Consultar Detalhes" /><br>
		<s:text name="ID da eleicao:" />
		<s:textfield key="ID" /><br>
		<s:text name="Novo titulo:" />
		<s:textfield key="titulo" /><br>
		<s:text name="Nova descricao:" />
		<s:textfield key="descricao" /><br>
		<s:submit />
	</s:form>
	 <input type="button" value="Voltar ao menu" onclick="window.location='index.jsp'">
	 </div>
</body>