<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page session="true"%>
<%@ page import="esame.Beans.Player"%>

<html>

<head>
	<title>Home</title>
</head>
<body>
<%
	Player x = (Player) request.getSession().getAttribute("player");
		if (x.notify){
			%>
				<p>La informiamo che la sua partecipazione è annullata</p>	
			<%		
		}else{
		%>
			<p>Nessuna notifica</p>	
		<%	
		}
	%>
	
	<%
	boolean y = (boolean) request.getSession().getAttribute("found");
		if (y){
			%>
				<p>Prenotazione eseguita</p>	
			<%		
		}else{
		%>
			<p>Nessuna perenotazione in corso</p>	
		<%	
		}
	%>
	
	<%
	boolean z = (boolean) request.getSession().getAttribute("disponibile");
		if (z){
			%>
				<p>Inserimento eseguito</p>	
			<%		
		}else{
		%>
			<p>Nessun inserimento in corso</p>	
		<%	
		}
	%>
	
	<form action="servletRegistrazione" method="post">
		<p><input type="submit" name="Prenota" value="Prenota"></p>
		<p><input type="submit" name="Insert" value="Insert"></p>
		<p id="result"></p>
	</form>
</body>
</html>