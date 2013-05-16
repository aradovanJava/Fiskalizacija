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


<script language="javascript" type="text/javascript">



function listic(couple,firstBookieName, firstBookieKoef, secondBookieName, secondBookieKoef, firstLink, secondLink, datum, vrijeme)
{
	
	
	newwindow=window.open('','listić','height=330,width=500');
	if (window.focus) {newwindow.focus()}
	tmp = newwindow.document;
	prviKoef = firstBookieKoef;
	drugiKoef = secondBookieKoef;
	if(firstBookieName == "Betfair"){
		prviKoef = ((firstBookieKoef-1)*0.95)+1;
	}
	if(secondBookieName == "Betfair"){
		drugiKoef = ((secondBookieKoef-1)*0.95)+1;
	}
	n=couple.split("-");
	d=datum.split("-");
	t=vrijeme.split(":");
	tmp.write('<html><head><title>'+couple+'</title>');
	tmp.write('<style type="text/css"> .input {height:25px;line-height:25px; border:1px solid; border-color: #9935ff #9935ff #9935ff #9935ff; -moz-border-radius:10px; border-radius:10px; text-indent:4px; outline:none; -webkit-box-shadow: inset 2px 2px 5px #999; -moz-box-shadow: inset 2px 2px 5px #999; box-shadow: inset 3px 3px 5px #999;}</style>');
	tmp.write('<script language="javascript" type="text/javascript">');
	tmp.write('function izracun(firstKoef, secondKoef){');
	tmp.write('x = Number(listic.ulog.value);');
	tmp.write('c=(((x*firstKoef)/(firstKoef+secondKoef)));');
	tmp.write('d=(((x*secondKoef)/(firstKoef+secondKoef)));');
	tmp.write('listic.dobitak.value=(Math.round((d*firstKoef-x)*100.0)/100.0);');
	tmp.write('listic.iznos1.value=(Math.round(d*100.0)/100.0);');
	tmp.write('listic.iznos2.value=(Math.round(c*100.0)/100.0);}');
	tmp.write('</'+'script>');
	tmp.write('</head><body onBlur="self.focus()" style="background: #dddddd; color :#9935ff;"><center><p style="text-align: center; text-shadow: 2px 2px 5px #000; font-size: 25px;">'+couple+'</br>'+d[2]+'.'+d[1]+'.'+d[0]+'., '+t[0]+':'+t[1]+'</p>');
	tmp.write('<table cellpadding="7"><tr><td colspan="4"><form name="listic">Unesite ulog: <input class="input" type="text" name="ulog" /> <input type="button" value="Izračunaj" onclick="javascript:izracun('+prviKoef+','+drugiKoef+');"> </td></tr>');
	tmp.write('<tr><td>'+n[0]+'</td><td><a href="'+firstLink+'" target="_blank">'+firstBookieName+'</a></td> <td>'+firstBookieKoef+'</td> <td><input class="input" type="text" name="iznos1"></td></tr>');
	tmp.write('<tr><td>'+n[1]+'</td><td><a href="'+secondLink+'" target="_blank">'+secondBookieName+'</a></td> <td>'+secondBookieKoef+'</td> <td><input type="text" class="input" name="iznos2"></td></tr>');
	tmp.write('<tr><td colspan="4">Ukupni dobitak: <input class="input" type="text" name="dobitak"></td></tr>');
	tmp.write('</form></table></center></body></html>');
	tmp.close();
	

}
  
</script>
</head>
<body>
<center>
<div class="pozadina">
<h1>SURE-BET OKLADE</h1>
<table cellpadding="5" border = "2"  align="center">
	<tr>
		<th class="naslov"  style="width:200px" >Par</th>
		<th class="naslov1" style="width:200px" >Kladionica 1</th>
		<th class="naslov"  style="width:200px">Koeficijent 1</th>
		<th class="naslov"  style="width:200px">Kladionica 2</th>
		<th class="naslov"  style="width:200px">Koeficijent 2</th>
		<th class="naslov"  style="width:200px">Vrijeme</th>
		<th class="naslov"  style="width:200px">Datum</th>
		<th class="naslov"  style="width:200px">Dobitak</th>
		
	</tr>
	<c:forEach items="${ requestScope['matches'] }" var="data">
		<tr id = "${ data.id }">
			<td onclick="listic('${ data.couple }','${ data.bookie_for_first.name }','${ data.first_odd }','${ data.bookie_for_second.name }', '${ data.second_odd }','${ data.bookie_for_first.tennis_link }', '${ data.bookie_for_second.tennis_link }','${ data.dates }','${ data.times }')"><c:out value="${ data.couple }"/></td>
			<td><c:out value="${ data.bookie_for_first.name }"/></td>
			<td><c:out value="${ data.first_odd }"/></td>
			<td><c:out value="${ data.bookie_for_second.name }"/></td>
			<td><c:out value="${ data.second_odd }"/></td>
			<td><c:out value="${ data.times }"/></td>
			<td><c:out value="${ data.dates }"/></td>
			<td><c:out value="${ data.rate }"/></td>
		</tr>
	</c:forEach>
</table>


<a class="link2" href="<%=response.encodeURL(request.getContextPath() + "/j_spring_security_logout") %>">Odjava</a>
<c:choose>
	<c:when test="${ requestScope['isRole'] }">
<a class="link" href="<%=response.encodeURL(request.getContextPath() + "/admin/showReg.html") %>">Registrirani korisnici</a>
<a class="link1" href="<%=response.encodeURL(request.getContextPath() + "/admin/refresh.html") %>">Refresh</a>
</c:when>
</c:choose>
</div>

</center>
</body>
</html>