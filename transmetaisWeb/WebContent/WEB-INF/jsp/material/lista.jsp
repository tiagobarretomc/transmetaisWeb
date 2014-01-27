<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Transmetais</title>
</head>
<body>
		<table width="1024px">
		
		<thead>
	<tr>
		<td width="10%">Codigo</td>
		<td width="10%">Sigla</td>
		<td width="90%">Descri&ccedil;&atilde;o</td>
		
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="material" items="${materiais}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/material/'/>${material.id}">${material.id}</a> 
			</td>
			<td>
				${material.sigla} 
			</td>
			<td>
				${material.descricao} 
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
</body>
</html>