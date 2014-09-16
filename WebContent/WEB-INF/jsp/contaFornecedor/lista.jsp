<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/contaFornecedor/novo";
    	});
    	
    	
    });
</script>
<div class="container">

	<br />
	<h2>Contas de Fornecedores</h2>
	<br>

	<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		<span class="glyphicon glyphicon-plus-sign"></span> Adicionar
	</button>

	<br /> <br />

	<table class="table table-bordered table-striped">

		<thead>
			<tr>
				<th></th>
				<th>Código</th>
				<th>Fornecedor</th>
				<th>Limite</th>
				<th>Saldo</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="conta" items="${contaFornecedorList}" varStatus="contador">

				<tr>
					<td><a href="<c:url value='/contaFornecedor/'/>${conta.id}"><span
							title="Alterar" class="glyphicon glyphicon-edit"></span></a> <a
						href="<c:url value='/contaFornecedor/remove/'/>${conta.id}"><span
							title="Excluir" class="glyphicon glyphicon-remove"></span></a></td>
					<td><fmt:formatNumber minIntegerDigits="4" value="${conta.id}"
							groupingUsed="" /></td>
					<td>${conta.fornecedor.apelido}</td>
					<td><fmt:formatNumber value="${conta.limite}"
							minFractionDigits="2" type="currency" /></td>
					<td><fmt:formatNumber value="${conta.saldo}"
							minFractionDigits="2" type="currency" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>