<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th ></th>
		<th >Data</th>
		<th >Fornecedor</th>
		<th >Tipo Frete</th>
		<th>Total</th>
		<th>Material</th>
		<th>Quantidade</th>
		<th>Valor</th>
		
	</tr>
	</thead>
	<tbody>
	
		<c:forEach var="venda" items="${vendas}" varStatus="contador">
	
		<tr>
			
			
				<td rowspan="${fn:length(venda.itens)}">
					<a href="${pageContext.request.contextPath}/venda/${venda.id}"><span title="Alterar" style="color: black;" class="glyphicon glyphicon-edit"></span></a>
					 
				</td>
				<td rowspan="${fn:length(venda.itens)}">
					<fmt:formatDate value="${venda.data}" type="date" pattern="dd/MM/yyyy"/>
					
				</td>
				<td rowspan="${fn:length(venda.itens)}">
					${venda.cliente.razaoSocial}
				</td>
				
				
				<td rowspan="${fn:length(venda.itens)}">
					${venda.tipoFrete}
				</td>
				<td rowspan="${fn:length(venda.itens)}">
						
						<fmt:formatNumber value="${venda.valor}" minFractionDigits="2" type="currency"/>
					</td>
				
				<c:forEach var="item" items="${venda.itens}" varStatus="cont" >
					<c:if test="${cont.count gt '1'}">
						</tr>
						
						<tr>
					</c:if>
					<td>
						${item.material.sigla} 
					</td>
					<td >
						
						<fmt:formatNumber value="${item.quantidade}" minFractionDigits="2" type="number"/>
					</td>
					<td>
						
						<fmt:formatNumber value="${item.valor}" minFractionDigits="2" type="currency"/>
					</td>
				</c:forEach>
				
			
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
	