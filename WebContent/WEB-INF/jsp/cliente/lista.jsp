<%@page contentType="text/html; charset=UTF-8"%> 
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/cliente/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		<br/>
		<h2>Clientes</h2>
		<br>
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-plus-sign"></span> Adicionar
		</button>
			
		<br/>
		<br/>	
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
	
		<c:forEach var="cliente" items="${clientes}" varStatus="contador">
	
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
				${cliente.cidade.nome} - ${cliente.cidade.uf}
			</td>
			<td >
				${cliente.status eq 'A' ? 'Ativo' : 'Inativo'}
			</td>
			<td>
				${cliente.telefoneFixo}
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
