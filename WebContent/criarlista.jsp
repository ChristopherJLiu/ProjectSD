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
	 <p>Criar Lista
	<s:form action="criarlista" method="post">
		<s:text name="Introduza o Id de uma eleicao:" /><br>
		<s:text name="Caso nao saiba qual e o ID eleicao consulte a opcao de Consultar Detalhes" /><br>
		<s:textfield key="ID" /><br>
		<s:text name="Tipo de lista:" /><br>
		<s:text name="Deixe em branco se a eleicao nao for do conselho geral" /><br>
		<s:select label="Tipo lista" headerKey="-1" headerValue="Tipo" list="#{'1':'Alunos', '2':'Docente', '3':'Funcionario'}" key="tipo" /><br>
		<s:text name="Nome da Lista" />
		<s:textfield key="nomelista" /><br>
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
</html>