<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${filter.class.simpleName eq 'ComprovantePesagemEntrada' }">
		
	<c:set var="pageTitle" value="Comprovante de Pesagem - Entrada" />
	<c:set var="controller" value="cpe" />
</c:if>
<c:if test="${filter.class.simpleName eq 'ComprovantePesagemSaida' }">
	<c:set var="controller" value="cps" />
	<c:set var="pageTitle" value="Comprovante de Pesagem - Saída" />
</c:if>

<script type="text/javascript">
	
    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/${controller}/novo";
    	});
    	
    	$("#btnLimpar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/${controller}/lista";
    	});
    	
    	
	    $("#btnFiltrar").click(function(){
			$("#divTabela").load( '<c:url value="/${controller}/filtrar"/>', $('#formComprovantePesagem').serialize() );
		});
    });
</script>


	
<div class="container">

	<br />
	<h2>${pageTitle }</h2>
	<br>

	<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		<span class="glyphicon glyphicon-plus-sign"></span> Adicionar
	</button>

	<br />
	<div class="panel panel-default">
		<div class="panel-body">
			<form action="<c:url value='/${controller }/'/>" id="formComprovantePesagem"
				name="formComprovantePesagem" method="post">
				<input type="hidden" name="_format" value="json">
				<div class="row">
					<div class="col-md-3">
		        		<label for="filter.numeroTicket">Número do Ticket:</label>
		        		<input name="filter.numeroTicket" id="filter.numeroTicket" value="${filter.numeroTicket}" class="form-control required" size="45" maxlength="45"/>
		        	</div>
					<div class="col-md-4">
						<c:if test="${filter.class.simpleName eq 'ComprovantePesagemEntrada' }">
			        		<label for="filter.fornecedor.id">Fornecedor:</label>
				        	<select id="filter.fornecedor.id" name="filter.fornecedor.id" class="selectpicker form-control" data-live-search="true">
								<option value ="" >Selecione</option>
								<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
									<option value ="${fornecedor.id}" ${filter.fornecedor.id eq fornecedor.id  ? 'selected' : ''}>${fornecedor.apelido} - ${fornecedor.nome}</option>
								</c:forEach>	
							</select>
						</c:if>
						<c:if test="${filter.class.simpleName eq 'ComprovantePesagemSaida' }">
			        		<label for="filter.cliente.id">Cliente:</label>
				        	<select id="filter.cliente.id" name="filter.cliente.id" class="selectpicker form-control" data-live-search="true">
								<option value ="" >Selecione</option>
								<c:forEach var="cliente" items="${clientes}" varStatus="contador">
									<option value ="${cliente.id}" ${filter.cliente.id eq cliente.id  ? 'selected' : ''}>${cliente.razaoSocial}</option>
								</c:forEach>	
							</select>
						</c:if>
		        	</div>
        	
					<div class="col-md-2">
		        		<label for="id">Data de Emissão:</label>
		        		<input name="filter.dataEmissao" id="filter.dataEmissao" value="<fmt:formatDate value="${filter.dataEmissao}" type="date" pattern="dd/MM/yyyy"/>" class="form-control required datepicker" />
		        	</div>

				</div>

				<br />
				<button id="btnFiltrar" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-filter"></span> Filtrar
				</button>
				<button id="btnLimpar" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-filter"></span> Limpar
				</button>
			</form>
		</div>
	</div>

	<br />
	<div id="divTabela">
		<table class="table table-bordered table-striped">
	
			<thead>
				<tr>
					<th></th>
					<th>Data de emissão</th>
					<th>Número do ticket</th>
					<c:if test="${filter.class.simpleName eq 'ComprovantePesagemEntrada' }">
					<th>Fornecedor</th>
					</c:if>
					<c:if test="${filter.class.simpleName eq 'ComprovantePesagemSaida' }">
					<th>Cliente</th>
					</c:if>
					<th>Placa do veículo</th>
					<th>Peso Líquido</th>
	
				</tr>
			</thead>
			<tbody>
				<c:forEach var="comprovantePesagem" items="${beanList}"
					varStatus="contador">
	
					<tr>
						<td><a href="<c:url value='/${controller }/'/>${comprovantePesagem.id}"><span
								title="Alterar" class="glyphicon glyphicon-edit"></span></a> <a
							href="<c:url value='/${controller }/remove/'/>${comprovantePesagem.id}"><span
								title="Excluir" class="glyphicon glyphicon-remove"></span></a></td>
						<td><fmt:formatDate value="${comprovantePesagem.dataEmissao}" type="date" pattern="dd/MM/yyyy"/></td>
						<td>${comprovantePesagem.numeroTicket}</td>
						<c:if test="${filter.class.simpleName eq 'ComprovantePesagemEntrada' }">
							<td>${comprovantePesagem.fornecedor.apelido} - ${comprovantePesagem.fornecedor.nome}</td>
						</c:if>
						<c:if test="${filter.class.simpleName eq 'ComprovantePesagemSaida' }">
							<td>${comprovantePesagem.cliente.razaoSocial}</td>
						</c:if>
						<td>${comprovantePesagem.placaVeiculo}</td>
						<td><fmt:formatNumber value="${comprovantePesagem.pesoLiquido}" minFractionDigits="2" type="number"/></td>
	
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>