<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ConsolaAdmin</title>

</head>
<body>
 	<div align="center">
 	
    <%-- Cuidado com inputs que meter STRINGS em ints vai mandar para a pagina do error.jsp ou datas nao no formato pedido --%>
    <p>Menu de Administracao</p>
    	<input type="button" value="RegistarPessoas" onclick="window.location = 'criarpessoas.jsp'" > <br> <%-- Tested --%>
		<input type="button" value="Criar Eleicao" onclick="window.location = 'criareleicao.jsp'"> <br><%-- Tested --%>
		<input type="button" value="Criar listas de candidatos a uma eleicao" onclick="window.location = 'criarlista.jsp'"> <br><%-- Tested--%>
		<input type="button" value="Consultar Detalhes de eleicoes" onclick="window.location = 'consultardetalhes.jsp'"> <br><%-- Tested --%>
		<input type="button" value="Adcionar mesa de voto" onclick="window.location = 'criarmesa.jsp'"> <br><%-- Tested --%>
		<input type="button" value="Alterar propriedades de uma eleicao" onclick="window.location = 'alterareleicao.jsp'"> <br><%-- Tested --%>
		<input type="button" value="Saber em que local votou cada eleitor" onclick="window.location = 'verlocaleleicao.jsp'"> <br><%-- Tested --%>
		<input type="button" value="Consultar resultados de eleicoes passadas" onclick="window.location = 'resultados.jsp'"> <br><%-- Tested on naoacabadas --%>
		<input type="button" value="Alterar dados pessoais" onclick="window.location = 'alterarpessoa.jsp'"> <br><%-- Tested --%>
		<input type="button" value="Logout" onclick="window.location = 'logout.jsp'"> <br><%-- Tested --%>
	 </div>
</body>
</html>