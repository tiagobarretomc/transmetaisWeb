<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div id="divTabela">
		<table class="table table-bordered table-striped">
	
			<thead>
				<tr>
					<th></th>
					<th>Data de emissão</th>
					<th>Número do ticket</th>
					<th>Fornecedor</th>
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
						<td>${comprovantePesagem.fornecedor.apelido} - ${comprovantePesagem.fornecedor.nome}</td>
						<td>${comprovantePesagem.placaVeiculo}</td>
						<td><fmt:formatNumber value="${comprovantePesagem.pesoLiquido}" minFractionDigits="2" type="number"/></td>
	
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>