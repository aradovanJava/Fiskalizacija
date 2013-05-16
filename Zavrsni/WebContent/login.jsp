<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	
	body{
		background-color:grey;
	}
	
	.vanjski{
		width:600px;
		height:300px;
		background-color:grey;
		border-radius: 4em;
		top:50px;
	}
	
	.pozadina{
		position:relative;
		width:580px;
		height:280px;
		background-color:#dddddd;
		border:1px solid #9935ff; 
		border-radius: 4em;
		top:10px;
		color:#9935ff;
		text-shadow: 2px 2px 5px #000;
		top:80%;
	}
	
	.link{
		position:relative;
		top:170px;
		left:150px;
	 }
	
	.input {
			height:25px; 
			line-height:25px; 
			border:1px solid; 
			border-color: #9935ff #9935ff #9935ff #9935ff; 
			-moz-border-radius:10px; 
			border-radius:10px; 
			text-indent:4px; 
			outline:none; 
			-webkit-box-shadow: inset 2px 2px 5px #999; 
			-moz-box-shadow: inset 2px 2px 5px #999; 
			box-shadow: inset 3px 3px 5px #999;
		}
		
		
	a{
		
		text-decoration:none;
		
	}
	
</style>
<title>Sure Bet</title>
</head>
<body>
<c:choose>
	<c:when test="${param.logout == 't'}">
<center>
<div class="vanjski">
<div class="pozadina">
	<table cellpadding="5" style="text-align:center;">
		<tr>
			<th colspan="2"><h1>Odjava</h1></th>
		</tr>
		<tr>
			<td>Uspješno ste se odlogirali.</td>
		</tr>
		<tr>
			<td>Za ponovnu prijavu pritisnite <a href="index.html" >ovdje</a></td>
		</tr>
	</table>
</div>
</div>
</center>
</c:when>
<c:when test="${param.login_error == 't'}">
<center>
<div class="vanjski">
<div class="pozadina">
	<form method="POST" action="<%= response.encodeURL(request.getContextPath() + "/j_spring_security_check") %>">
	<table cellpadding="5">
		<tr>
			<th colspan="2"><h1>SURE BET</h1></th>
		</tr>
		<tr>
			<td>Username:</td>
			<td><input class="input" type="text" name="j_username" /></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input class="input" type="password" name="j_password" /></td>
		</tr>
		<tr>
			<th colspan="2"><input type="submit" value="Login" name="prijava" /></th>
		</tr>
		
		<tr>
			<td colspan="2" style="text-align:center; color:red;"><h3>Krivi username ili password, pokušajte ponovno</h3></td>
		</tr>
	</table>
	</form>
</div>
<a class="link" href="<%=response.encodeURL(request.getContextPath() + "/registration.html")%>">Registracija</a>
</div>
</center>
	</c:when>
	   <c:otherwise>
<center>
<div class="vanjski">
<div class="pozadina">
	<form method="POST" action="j_spring_security_check" >
	<table cellpadding="5">
		<tr>
			<th colspan="2"><h1>SURE BET</h1></th>
		</tr>
		<tr>
			<td>Login:</td>
			<td><input class="input" type="text" name="j_username" /></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input class="input" type="password" name="j_password" /></td>
		</tr>
		<tr>
			<th colspan="2"><input type="submit" value="Login" name="prijava" /></th>
		</tr>
	</table>
	</form>
</div>
<a class="link" href="<%=response.encodeURL(request.getContextPath() + "/registration.html")%>">Registracija</a>
</div>
</center>
</c:otherwise>
</c:choose>




</body>
</html>