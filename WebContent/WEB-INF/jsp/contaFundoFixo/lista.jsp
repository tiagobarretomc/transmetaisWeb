<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/contaFundoFixo/novo";
    	});
    	
    	
    });
</script>
<div class="container">

	<br />
	<h2>Contas de Fundo Fixo</h2>
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
				<th>Descrição</th>
				<th>Unidade</th>
				<th>Saldo</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="conta" items="${beanList}" varStatus="contador">

				<tr>
					<td><a href="<c:url value='/contaFundoFixo/'/>${conta.id}"><span
							title="Alterar" class="glyphicon glyphicon-edit"></span></a> <a
						href="<c:url value='/contaFundoFixo/remove/'/>${conta.id}"><span
							title="Excluir" class="glyphicon glyphicon-remove"></span></a></td>
					<td><fmt:formatNumber minIntegerDigits="4" value="${conta.id}"
							groupingUsed="" /></td>
					<td>${conta.descricao}</td>
					<td>${conta.unidade.descricao}</td>
					<td><fmt:formatNumber value="${conta.saldo}"
							minFractionDigits="2" type="currency" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>