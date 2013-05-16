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
	<%-- 
		<c:set var="lastRunner" value="'nema'" />
	<c:forEach items="${ requestScope['listRunners'] }" var="runner">
	<c:choose>
	<tr>
	<c:when test="${lastRunner == 'nema'}">
    <c:set var="lastRunner" value="runner.runnerName" />
    <td><c:out value="${ runner.runnerName }"/></td>
    <td><c:out value="${ runner.odd }"/></td>
  </c:when>
  <c:when test="${lastRunner eq runner.runnerName}">
   <td><c:out value="${ runner.odd }"/></td>
  </c:when>
  <c:otherwise>
  </tr>
  <tr>
  	<c:set var="lastRunner" value="runner.runnerName" />
    <td><c:out value="${ runner.runnerName }"/></td>
    <td><c:out value="${ runner.odd }"/></td>
  </c:otherwise>
  </c:choose>
		
	</c:forEach>
	--%>
	
	<c:forEach items="${ requestScope['listForJSP'] }" var="runnerList">
			<tr>
			<td><c:out value="${ runnerList[0].runnerName  }"/></td>
		<c:forEach items="runnerList" var="runner">
				<td><c:out value="${ runner.odd }"/></td>
		</c:forEach>
		</tr>
	</c:forEach>

</table>


<a class="link2" href="<%=response.encodeURL(request.getContextPath() + "/j_spring_security_logout") %>">Odjava</a>

<a class="link" href="<%=response.encodeURL(request.getContextPath() + "/refreshHorse.html") %>">Refresh</a>


</div>

</center>
</body>
</html>