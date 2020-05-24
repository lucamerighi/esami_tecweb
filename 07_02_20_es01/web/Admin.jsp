<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page session="true"%>
<%@ page import="esame.Beans.Player"%>
<%@ page import="esame.Beans.Campo"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<html>

<head>
	<title>Home</title>
</head>
<body>
	<form action="servletRegistrazione" method="post">
		<%
			List<Campo> campi = (ArrayList<Campo>) request.getSession().getAttribute("campi");
			for (Campo x : campi){
		%>
			<p>Giocatore1: <%= x.player1.nome %><p>
			<p>Giocatore2: <%= x.player2.nome %><p>
			<p/>
		<%
			}
		%>
	</form>
</body>
</html>