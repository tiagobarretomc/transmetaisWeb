<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/cliente/novo";
    	});
    	
    	$('.selectpicker').selectpicker({
            //'selectedText': 'cat'
        });
    	
		$("#btnFiltrar").click(function(){
    		$("#divTabela").load( '<c:url value="/cliente/loadListaCliente"/>', $('#formCliente').serialize() );
    	});
    	
    	
    });
</script>
<div class="container">
	<br />
	<h2>Clientes</h2>
	<br>

	<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		<span class="glyphicon glyphicon-plus-sign"></span> Adicionar
	</button>







	<div class="panel panel-default">
		<div class="panel-body">
			<form action="<c:url value='/cliente/'/>" id="formCliente"
				name="formCliente" method="post">
				<input type="hidden" name="_format" value="json">
				<div class="row">
					<div class="col-md-5">
						<label for="clienteId">Cliente:</label> <select id="clienteId"
							name="clienteId" class="selectpicker form-control"
							data-live-search="true">
							<option value="">Selecione</option>
							<c:forEach var="cliente" items="${clientes}" varStatus="contador">
								<option value="${cliente.id}">${cliente.razaoSocial}</option>
							</c:forEach>
						</select>


					</div>

				</div>

				<br />
				<button id="btnFiltrar" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-filter"></span> Filtrar
				</button>
			</form>
		</div>
	</div>





	<br /> <br />
	<div id="divTabela">
		<table width="1024px" class="table table-bordered table-striped">

			<thead>
				<tr>
					<th></th>
					<th>Razão Social</th>
					<th>Cpf/Cnpj</th>
					<th>Cidade</th>
					<th>Status</th>
					<th>Telefone</th>

				</tr>
			</thead>
			<tbody>

				<c:forEach var="cliente" items="${clientes}" varStatus="contador">

					<tr>
						<td><a
							href="${pageContext.request.contextPath}/cliente/${cliente.id}"><span
								title="Alterar" style="color: black;"
								class="glyphicon glyphicon-edit"></span></a> <a
							href="${pageContext.request.contextPath}/clienteMaterial/${cliente.id}"><span
								title="Tabela de Preços" style="color: black;"
								class="glyphicon glyphicon-list-alt"></span></a> <a
							href="${pageContext.request.contextPath}/movimentacao/${cliente.id}"><span
								title="Venda" style="color: black;"
								class="glyphicon glyphicon-usd"></span></a></td>
						<td>${cliente.razaoSocial}</td>
						<td>${cliente.cpfCnpj}</td>

						<td>${cliente.cidade.nome} - ${cliente.cidade.uf}</td>
						<td>${cliente.status eq 'A' ? 'Ativo' : 'Inativo'}</td>
						<td>${cliente.telefoneFixo}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
