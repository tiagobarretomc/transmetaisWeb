<%@page contentType="text/html; charset=UTF-8"%> 

<div class="container">
		<br/>
		<h2>Compras</h2>
		<br>
		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th ></th>
		<th >Fornecedor</th>
		<th>Cpf/Cnpj</th>
		<th >Cidade</th>
		<th>Quantidade</th>
		<th>Valor Total</th>
		
	</tr>
	</thead>
	<tbody>
	
		<c:forEach var="cliente" items="${compras}" varStatus="contador">
	
		<tr>
			<td>
				<a href="${pageContext.request.contextPath}/compra/${compra.id}"><span title="Alterar" style="color: black;" class="glyphicon glyphicon-edit"></span></a>
				 
			</td>
			<td>
				${compra.fornecedorMaterial.fornecedor.nome}
			</td>
			<td>
				${compra.fornecedorMaterial.fornecedor.cpfCnpj} 
			</td>
			
			<td>
				${cliente.cidade.nome} - ${cliente.cidade.uf}
			</td>
			<td >
				${compra.quantidade}
			</td>
			<td>
				${compra.valor}
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
