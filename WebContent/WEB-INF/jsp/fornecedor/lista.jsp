<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Transmetais</title>
</head>
<body>

<script src="js/bootstrap.min.js"></script>
		<h2>Fornecedores</h2>
		<table width="1024px">
		
		<thead>
	<tr>
		<td width="3%">Código</td>
		<td width="10%">Apelido</td>
		<td width="30%">Nome</td>
		<td width="10%">Estado</td>
		<td width="20%">Cidade</td>
		<td width="10%">Email</td>
		<td width="17%">Telefone</td>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/fornecedor/'/>${fornecedor.id}">${fornecedor.id}</a> 
			</td>
			<td>
				<a href="<c:url value='/fornecedor/'/>${fornecedor.id}">${fornecedor.apelido}</a> 
			</td>
			<td>
				${fornecedor.nome} 
			</td>
			<td>
				${fornecedor.cidade.uf}
			</td>
			<td>
				${fornecedor.cidade.nome}
			</td>
			<td>
				${fornecedor.email}
			</td>
			<td>
				${fornecedor.telefoneCelular}
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
</body>
</html>