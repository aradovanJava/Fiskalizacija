<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	body{
		background-color:grey;
	}
	
	.vanjski{
		width:600px;
		height:760px;
		background-color:grey;
		border-radius: 4em;
		top:50px;
	}
	
	.pozadina{
		position:relative;
		width:580px;
		height:760px;
		background-color:#dddddd;
		border:1px solid #9935ff; 
		border-radius: 4em;
		top:10px;
		color:#9935ff;
		text-shadow: 2px 2px 5px #000;
	}
		
	a{
		text-decoration:none;
	}
	
	.link{
		position:relative;
		top:5px;
		right:100px;
	 }
	 
	 .link2{
		position:relative;
		top:5px;
		left:100px;
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
</style>
<title>Registracija</title>
</head>
<body>
	<center>
<div class="vanjski">
<div class="pozadina">
		<br />
		<table cellpadding="10">
		<tr>
		<th colspan="2", ><font size = "10px">Registracija</font></th>
		<form:form action="registration.html" commandName="user">
			<tr><td></td><td></td></tr>
			<tr>
			<td>Korisničko ime:</td>
			<td><form:input class="input" path="role.username" SIZE="20" maxlength="50" />
			<br />
			<form:errors path="role.username" style="color:red" /></td>
			</tr><tr>
			<td>Lozinka:</td>
			<td><form:input class="input" path="password" SIZE="20" maxlength="50"/>
			<br />
			<form:errors path="password" style="color:red" /></td>
			</tr><tr>
			<td>Ime:</td>
			<td><form:input class="input" path="first_name" SIZE="20" maxlength="50" />
			<br />
			<form:errors path="first_name" style="color:red" /></td>
			</tr><tr>
			<td>Prezime:</td>
			<td><form:input class="input" path="last_name" SIZE="20" maxlength="50" />
			<br />
			<form:errors path="last_name" style="color:red" /></td>
			</tr><tr>
			<td>Mail:</td>
			<td><form:input class="input" path="mail" SIZE="20" maxlength="50" />
			<br />
			<form:errors path="mail" style="color:red" /></td>
			</tr><tr>
			<td>Adresa:</td>
			<td><form:input class="input" path="adress" SIZE="20" maxlength="50" />
			<br />
			<form:errors path="adress" style="color:red" /></td>
			</tr><tr>
			<td>Grad:</td>
			<td><form:input class="input" path="city" SIZE="20" maxlength="50" />
			<br />
			<form:errors path="city" style="color:red" /></td>
			</tr><tr>
			<td>Mobitel:</td>
			<td><form:input class="input" path="mobile" SIZE="20" maxlength="50" />
			<br />
			<form:errors path="mobile" style="color:red" /></td>
			</tr><tr>
			
			<td colspan="2"><INPUT TYPE="SUBMIT" VALUE="Unesi"></td></tr>
		</form:form>
		</table>
		<c:if test="${ requestScope['uneseno'] != null }">
			<h3>
				<c:out value="${ uneseno }" />
			</h3>
		</c:if>
		<br/>
		<a class="link" href="<%=response.encodeURL(request.getContextPath() + "/viewMatches.html")%>">Login</a>
</div>
</div>
</center>
</body>
</html>