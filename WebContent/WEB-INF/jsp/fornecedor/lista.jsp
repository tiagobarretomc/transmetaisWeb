

<div class="container">
		<br/>
		<h2>Fornecedores</h2>
		<br>
		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th width="3%"></th>
		<th width="10%">Apelido</th>
		<th width="30%">Nome</th>
		<th width="10%">Estado</th>
		<th width="20%">Cidade</th>
		<th width="10%">Status</th>
		<th width="17%">Telefone</th>
		
	</tr>
	</thead>
	<tbody>
	
		<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
	
		<tr>
			<td>
				<a href="${fornecedor.id}"><span style="color: black;" class="glyphicon glyphicon-edit"></span></a> 
			</td>
			<td>
				${fornecedor.apelido}
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
