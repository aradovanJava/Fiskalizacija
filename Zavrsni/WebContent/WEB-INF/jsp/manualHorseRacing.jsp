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
<title>Horse racing manual</title>



</head>
<body>
<center>
<div class="pozadina">
<h1>SURE-BET OKLADE</h1>


<table cellpadding="5" border = "2"  align="center">

	<c:forEach items="${ requestScope['horseRacing'] }" var="horseRacing">
		<tr>
			<td><c:out value="${ horseRacing.naziv }"/></td>
			<td><c:out value="${ horseRacing.runTime }"/></td>
			<td><form action="<%=response.encodeURL(request.getContextPath() + "/specRace.html") %>" method="POST"> <input type="hidden" name="id" value="<c:out value="${ horseRacing.id }"/>" />
			<input type="SUBMIT" value="Utrka" /></form></td>
			
			<td><form action="<%=response.encodeURL(request.getContextPath() + "/deleteRace.html") %>" method="POST"> <input type="hidden" name="id" value="<c:out value="${ horseRacing.id }"/>" />
			<input type="SUBMIT" value="Obriši utrku" /></form></td>
		</tr>
	</c:forEach>
</table>


<a class="link2" href="<%=response.encodeURL(request.getContextPath() + "/j_spring_security_logout") %>">Odjava</a>

<a class="link" href="<%=response.encodeURL(request.getContextPath() + "/admin/showReg.html") %>">Registrirani korisnici</a>


</div>

</center>
</body>
</html>