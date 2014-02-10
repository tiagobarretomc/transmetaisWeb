<%@page contentType="text/html; charset=UTF-8"%> 

<div class="container">
		<br/>
		<h2>Clientes</h2>
		<br>
		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th ></th>
		<th >Razão Social</th>
		<th>Cpf/Cnpj</th>
		<th >Cidade</th>
		<th>Status</th>
		<th>Telefone</th>
		
	</tr>
	</thead>
	<tbody>
	
		<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
	
		<tr>
			<td>
				<a href="${pageContext.request.contextPath}/cliente/${cliente.id}"><span title="Alterar" style="color: black;" class="glyphicon glyphicon-edit"></span></a>
				<a href="${pageContext.request.contextPath}/clienteMaterial/${cliente.id}"><span title="Tabela de Preços" style="color: black;" class="glyphicon glyphicon-list-alt"></span></a> 
			</td>
			<td>
				${cliente.razaoSocial}
			</td>
			<td>
				${cliente.cpfCnpj} 
			</td>
			
			<td>
				${fornecedor.cidade.nome} - ${fornecedor.cidade.uf}
			</td>
			<td >
				${fornecedor.status eq 'A' ? 'Ativo' : 'Inativo'}
			</td>
			<td>
				${fornecedor.telefoneCelular}
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
