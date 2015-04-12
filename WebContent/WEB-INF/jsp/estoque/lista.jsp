<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/estoque/novo";
    	});
    	
    	
    });
</script>
<div class="container">

	<br />
	<h2>Estoque</h2>
	<br>

	<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		<span class="glyphicon glyphicon-plus-sign"></span> Movimentação
	</button>

	<br /> <br />

	<table class="table table-bordered table-striped">

		<thead>
			<tr>
				
				<th>Material</th>
				<th>Quantidade</th>
				<th>Valor Total</th>
				<th>Preço Médio</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="estoque" items="${beanList}" varStatus="contador">

				<tr>
					<td>${estoque.material.descricao }</td>
					<td><fmt:formatNumber minFractionDigits="2" value="${estoque.quantidade}"
							 /></td>
					
					<td><fmt:formatNumber value="${estoque.valor}" minFractionDigits="2" type="currency" /></td>
					<td><fmt:formatNumber value="${estoque.valor/estoque.quantidade}" minFractionDigits="2" type="currency" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>