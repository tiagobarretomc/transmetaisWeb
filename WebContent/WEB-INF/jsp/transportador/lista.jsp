<div class="container">
<br/>
		<h2>Transpordadores</h2>
		<br>
		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th width="30%">Nome</th>
		<th width="20%">CPF</th>
		<th width="10%">Placa</th>
		<th width="10%">Uf</th>
		<th width="10%">ANTT</th>
		<th width="20%">Valor Frete</th>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="transportador" items="${transportadores}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/transportador/'/>${transportador.id}">${transportador.nomeMotorista}</a> 
			</td>
			<td>
				${transportador.cpfMotorista} 
			</td>
			<td>
				${transportador.placaVeiculo}
			</td>
			<td>
				${transportador.ufVeiculo}
			</td>
			<td>
				${transportador.rntrc}
			</td>
			<td>
				<fmt:formatNumber value="${transportador.valorFrete}" minFractionDigits="2" type="currency"/>
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
</div>