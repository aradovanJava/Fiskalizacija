<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	body{
		
		background-color : grey;
	}
	
	.naslov{
		text-shadow: 2px 2px 5px #000;
	}
	.naslov1{
		text-shadow: 2px 2px 5px #000;
		border-right-style: hidden;
	}
	
	table{
		text-shadow:none;
		text-align: center;
	}
	
	.pozadina{
		width:auto;
		height:auto;
		position:relative;
		background-color:#dddddd;
		border:1px solid #9935ff; 
		border-radius:4em;
		top:10px;
		color:#9935ff;
		text-shadow: 2px 2px 5px #000;
		
		
	}
				
	a{
		text-decoration:none;
	}
	
	.link{
		position:relative;
		top:25px;
		left:200px;
	 }
	 
	 .link2{
		position:relative;
		top:25px;
		right:200px;
	 }
</style>
<title>Sure Bet</title>


</head>
<body>
<center>
<div class="pozadina">
<h1>SURE-BET OKLADE</h1>
<a href="<%=response.encodeURL(request.getContextPath() + "/viewMatches.html") %>">Tenis</a></br>
<a href="<%=response.encodeURL(request.getContextPath() + "/manualHorseRacing.html") %>">Manual Horse racing</a></br>
<a href="<%=response.encodeURL(request.getContextPath() + "/HorseRacing.html") %>">Horse racing</a>

<a class="link2" href="<%=response.encodeURL(request.getContextPath() + "/j_spring_security_logout") %>">Odjava</a>

</div>

</center>
</body>
</html>