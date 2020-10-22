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
	 <p>Criar Eleicao
	<s:form action="criareleicao" method="post">
		<s:text name="Tipo de eleicao:" />
		<s:select label="Tipo de pessoa" list="#{'1':'Nucleo de estudantes', '2':'Conselho Geral', '3':'Direcao de Departamento', '4':'Direcao de Faculdade'}" key="tipo" /><br>
		<s:text name="Nome Faculdade/Departamento onde ira ocorer:" /><br>
		<s:text name="Em caso de ser Conselho Geral deixe em branco" /><br>
		<s:textfield key="depfac" /><br>
		<s:text name="Data de inicio" /><br>
		<s:text name="Formato dd/MM/yyyy HH:mm " /><br>
		<s:textfield key="datai" /><br>
		<s:text name="Data de fim" /><br>
		<s:text name="Formato dd/MM/yyyy HH:mm " /><br>
		<s:textfield key="dataf" /><br>
		<s:text name="Titulo da eleicao:" />
		<s:textfield key="titulo" /><br>
		<s:text name="Descricao da eleicao:" />
		<s:textfield key="descricao" /><br>
		<s:submit />
	</s:form>
	 <input type="button" value="Voltar ao menu" onclick="window.location='index.jsp'">
	 </div>
</body>
</html>