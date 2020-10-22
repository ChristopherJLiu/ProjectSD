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
	 <p>Alterar dados de uma pessoa
	 <p>Insira os novos dados o BI terá que ser o mesmo
	<s:form action="alterarpessoa" method="post">
		<s:text name="Nome:" />
		<s:textfield key="nome" /><br>
		<s:text name="Tipo de Pessoa:" />
		<s:select label="Tipo de pessoa" list="#{'1':'Aluno', '2':'Docente', '3':'Funcionario'}" key="tipo" /><br>
		<s:text name="Password:" />
		<s:textfield key="password" /><br>
		<s:text name="ContatoTel" />
		<s:textfield key="contato" /><br>
		<s:text name="Morada:" />
		<s:textfield key="morada" /><br>
		<s:text name="Número Bi:" />
		<s:textfield key="bi" /><br>
		<s:text name="Data de validade do Bi " /><br>
		<s:text name="Formato dd/MM/yyyy HH:mm " /><br>
		<s:textfield key="datavalbi" /><br>
		<s:text name="Nome do departamento:" />
		<s:textfield key="dep" /><br>
		<s:submit />
	</s:form>
	 <input type="button" value="Voltar ao menu" onclick="window.location='index.jsp'">
	 </div>
</body>
</html>