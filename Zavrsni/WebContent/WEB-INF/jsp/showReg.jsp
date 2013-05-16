<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	body{
		background-color:grey;
	}
	
	.naslov{
		text-shadow: 2px 2px 5px #000;
	}
	
	table{
		text-shadow:none;
		text-align: center;
	}
	
	.pozadina{
		position:relative;
		width:auto;
		height:auto;
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
<h1>POPIS REGISTRIRANIH KORISNIKA</h1>
<table cellpadding="5" border="2">
	<tr>
		<th class="naslov"  style="width:200px">Ime</th>
		<th class="naslov"  style="width:200px">Prezime</th>
		<th class="naslov" style="width:200px">Username</th>
		<th class="naslov" style="width:200px">Mail</th>
		<th class="naslov"  style="width:200px">Adresa</th>
		<th class="naslov"  style="width:200px">Grad</th>
		<th class="naslov"  style="width:200px">Mobitel</th>
		<th class="naslov"  style="width:200px">Datum Rođenja</th>
		<th class="naslov"  style="width:200px">Rola</th>
		
	</tr>
	<c:forEach items="${ requestScope['users'] }" var="data">
		<tr>
			<td><c:out value="${ data.first_name }"/></td>
			<td><c:out value="${ data.last_name }"/></td>
			<td><c:out value="${ data.role.username }"/></td>
			<td><c:out value="${ data.mail }"/></td>
			<td><c:out value="${ data.adress }"/></td>
			<td><c:out value="${ data.city }"/></td>
			<td><c:out value="${ data.mobile }"/></td>
			<td><c:out value="${ data.date_of_birth }"/></td>
			<td><c:out value="${ data.role.role }"/></td>
		</tr>
	</c:forEach>
</table>
<a class="link2" href="../j_spring_security_logout">Odjava</a>
<a class="link1" href="<%=response.encodeURL(request.getContextPath() + "/admin/refresh.html") %>">Refresh</a>
</div>
</center>
</body>
</html>