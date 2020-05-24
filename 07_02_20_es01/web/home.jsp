<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page session="true"%>




<html>
<head>
	<title>Home</title>
</head>
<body>
	<form action="servletRegistrazione" method="post">
	
		<p>Username:<input type="text" name="name"></p>
		<p>Password:<input type="text" name="pwd"></p>
		<p>Abilità:<input type="text" name="ability"></p>
		<p><input type="submit" name="Register" value="Registrati"></p>
		
	</form>
</body>
</html>