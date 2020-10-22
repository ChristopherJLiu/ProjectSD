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
	 <p>Consultar detalhes<br>
	 <s:text name="Tipos 1-Nucleo de estudantes 2-Conselho Geral 3-Direcao de departamento 4-Direcao de faculdade" /><br>
	 	<s:form action="consultardetalhes" method="post">
	 	<s:submit />
		</s:form>
		<s:text name="Eleicoes Disponiveis:" /><br>
		<s:iterator  value="eleicoes">
		<s:text name="ID:" />	<s:property value="ID"/>
		<s:text name=" Tipo:" />	<s:property value="Tipo"/>
		<s:text name=" Titulo:" />	<s:property value="Titulo"/> 
		<s:text name=" Descricao:" />	<s:property value="Descricao"/>
		<s:text name=" Local:" />	<s:property value="DepFac.Nome"/>
		<s:text name=" DataInicio:" />	<s:property value="DataI"/> 
		<s:text name=" DataFim:" />	<s:property value="DataF"/><br>      
		</s:iterator>
	 <input type="button" value="Voltar ao menu" onclick="window.location='index.jsp'">
	 </div>
</body>
</html>