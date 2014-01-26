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
		<td width="30%">Nome</td>
		<td width="20%">CPF</td>
		<td width="10%">Placa</td>
		<td width="10%">Uf</td>
		<td width="10%">ANTT</td>
		<td width="20%">Valor Frete</td>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="transportador" items="${transportadores}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/transportador/'/>${transportador.id}">${transportador.nomeMotorista}</a> 
			</td>
			<td>
				${transportador.cpfMotorista} 
			</td>
			<td>
				${transportador.placaVeiculo}
			</td>
			<td>
				${transportador.ufVeiculo}
			</td>
			<td>
				${transportador.rntrc}
			</td>
			<td>
				${transportador.valorFrete}
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
</body>
</html>