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
<div id="divTabela">
	<c:set var="totalPesoLiquido" value="0" />
	<table class="table table-bordered table-striped">

		<thead>
			<tr>
				<th></th>
				<th>Data de emissão</th>
				<th>Número do ticket</th>
				<c:if
					test="${filter.class.simpleName eq 'ComprovantePesagemEntrada' }">
					<th>Fornecedor</th>
				</c:if>
				<c:if
					test="${filter.class.simpleName eq 'ComprovantePesagemSaida' }">
					<th>Cliente</th>
				</c:if>
				<th>Placa do veículo</th>
				<th>Peso Líquido</th>
				<th>Faturado</th>

			</tr>	
		</thead>
		<tbody>
			<c:forEach var="comprovantePesagem" items="${beanList}"
				varStatus="contador">
				<c:set var="totalPesoLiquido"
					value="${totalPesoLiquido + comprovantePesagem.pesoLiquido}" />
				<tr>
					<td><a
						href="<c:url value='/${controller }/'/>${comprovantePesagem.id}"><span
							title="Alterar" class="glyphicon glyphicon-edit"></span></a> <a
						href="<c:url value='/${controller }/remove/'/>${comprovantePesagem.id}"><span
							title="Excluir" class="glyphicon glyphicon-remove"></span></a></td>
					<td><fmt:formatDate value="${comprovantePesagem.dataEmissao}"
							type="date" pattern="dd/MM/yyyy" /></td>
					<td>${comprovantePesagem.numeroTicket}</td>
					<c:if
						test="${filter.class.simpleName eq 'ComprovantePesagemEntrada' }">
						<td>${comprovantePesagem.fornecedor.apelido} -
							${comprovantePesagem.fornecedor.nome}</td>
					</c:if>
					<c:if
						test="${filter.class.simpleName eq 'ComprovantePesagemSaida' }">
						<td>${comprovantePesagem.cliente.razaoSocial}</td>
					</c:if>
					<td>${comprovantePesagem.placaVeiculo}</td>
					<td><fmt:formatNumber
							value="${comprovantePesagem.pesoLiquido}" minFractionDigits="2"
							type="number" /></td>
					<td>${comprovantePesagem.faturado}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br />
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row">
				<div class="col-md-4">
					<b>Quantidade Total:</b>
					<fmt:formatNumber value="${totalPesoLiquido}" minFractionDigits="2"
						type="number" />
				</div>
			</div>
		</div>
	</div>
</div>