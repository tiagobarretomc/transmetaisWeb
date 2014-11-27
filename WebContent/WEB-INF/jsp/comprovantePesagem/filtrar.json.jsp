<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div id="divTabela">
		<table class="table table-bordered table-striped">
	
			<thead>
				<tr>
					<th></th>
					<th>Data de emiss�o</th>
					<th>N�mero do ticket</th>
					<th>Placa do ve�culo</th>
					<th>Peso L�quido</th>
	
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
						<td>${comprovantePesagem.dataEmissao}</td>
						<td>${comprovantePesagem.numeroTicket}</td>
						<td>${comprovantePesagem.placaVeiculo}</td>
						<td>${comprovantePesagem.pesoLiquido}</td>
	
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>