<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th ></th>
		<th >Data</th>
		<th >Fornecedor</th>
		<th>Material</th>
		<th >Tipo Frete</th>
		<th>Quantidade</th>
		<th>Valor Total</th>
		
	</tr>
	</thead>
	<tbody>
	
		<c:forEach var="compra" items="${compras}" varStatus="contador">
	
		<tr>
			<td>
				<a href="${pageContext.request.contextPath}/compra/${compra.id}"><span title="Alterar" style="color: black;" class="glyphicon glyphicon-edit"></span></a>
				 
			</td>
			<td>
				<fmt:formatDate value="${compra.data}" type="date" pattern="dd/MM/yyyy"/>
				
			</td>
			<td>
				${compra.fornecedorMaterial.fornecedor.apelido} - ${compra.fornecedorMaterial.fornecedor.nome} 
			</td>
			<td>
				${compra.fornecedorMaterial.material.descricao} 
			</td>
			
			<td>
				${compra.fornecedorMaterial.tipoFrete.descricao}
			</td>
			<td >
				
				<fmt:formatNumber value="${compra.quantidade}" minFractionDigits="2" type="number"/>
			</td>
			<td>
				
				<fmt:formatNumber value="${compra.valor}" minFractionDigits="2" type="currency"/>
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	<br/>
	<div class="panel panel-default">
  	<div class="panel-body">
	<div class="row">
		<div class="col-md-4"><b>Valor Total:</b> <fmt:formatNumber value="${valorTotal}" minFractionDigits="2" type="currency"/></div>
		<div class="col-md-4"><b>Quantidade Total:</b> <fmt:formatNumber value="${quantidade}" minFractionDigits="2" type="number"/></div>
		<div class="col-md-4">Preço Médio: <fmt:formatNumber value="${precoMedio}" minFractionDigits="2" type="currency"/></div>
	
	</div>
	</div>
	</div>
	