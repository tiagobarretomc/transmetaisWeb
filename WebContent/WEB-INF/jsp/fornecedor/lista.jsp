<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/fornecedor/novo";
    	});
    	
    	$('.selectpicker').selectpicker({
            //'selectedText': 'cat'
        });
    	
		$("#btnFiltrar").click(function(){
    		
    		$("#divTabela").load( '<c:url value="/fornecedor/loadListaFornecedor"/>', $('#formFornecedor').serialize() );
    	});
    	
    	
    });
</script>
<div class="container">
	<br />
	<h2>Fornecedores</h2>
	<br>

	<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		<span class="glyphicon glyphicon-plus-sign"></span> Adicionar
	</button>







	<div class="panel panel-default">
		<div class="panel-body">
			<form action="<c:url value='/fornecedor/'/>" id="formFornecedor"
				name="formFornecedor" method="post">
				<input type="hidden" name="_format" value="json">
				<div class="row">
					<div class="col-md-5">
						<label for="fornecedorId">Fornecedor:</label> <select
							id="fornecedor" name="fornecedorId"
							class="selectpicker form-control" data-live-search="true">
							<option value="">Selecione</option>
							<c:forEach var="fornecedor" items="${fornecedores}"
								varStatus="contador">
								<option value="${fornecedor.id}">${fornecedor.apelido}
									- ${fornecedor.nome}</option>
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
					<th>Apelido</th>
					<th>Nome</th>
					<th>Cidade</th>
					<th>Status</th>
					<th>Telefone</th>

				</tr>
			</thead>
			<tbody>

				<c:forEach var="fornecedor" items="${fornecedores}"
					varStatus="contador">

					<tr>
						<td><a
							href="${pageContext.request.contextPath}/fornecedor/${fornecedor.id}"><span
								title="Alterar" style="color: black;"
								class="glyphicon glyphicon-edit"></span></a> <a
							href="${pageContext.request.contextPath}/fornecedorMaterial/${fornecedor.id}"><span
								title="Tabela de Preços" style="color: black;"
								class="glyphicon glyphicon-list-alt"></span></a> <a
							href="${pageContext.request.contextPath}/movimentacao/${fornecedor.id}"><span
								title="Compra" style="color: black;"
								class="glyphicon glyphicon-usd"></span></a></td>
						<td>${fornecedor.apelido}</td>
						<td>${fornecedor.nome}</td>

						<td>${fornecedor.cidade.nome} - ${fornecedor.cidade.uf}</td>
						<td>${fornecedor.status eq 'A' ? 'Ativo' : 'Inativo'}</td>
						<td>${fornecedor.telefoneCelular}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
