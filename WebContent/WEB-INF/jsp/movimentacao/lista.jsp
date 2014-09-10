<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/funcionario/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		
        <br/>
		<h2>Movimentação da Conta</h2>
		<br>
		
		<b>Fornecedor:</b> ${fornecedor.apelido} - ${fornecedor.nome}
			
		<br/>
		
		<b>Saldo Atual:</b><fmt:formatNumber value="${fornecedor.conta.saldo}" minFractionDigits="2" type="currency"/>
		<br/>
    
		<table  class="table table-bordered table-striped">
		
		<thead>
	<tr>
		
		<th >Data</th>
		<th >Valor</th>
		<th >Tipo Operação</th>
		<th >Saldo Parcial</th>
		
		
	</tr>
	</thead>
	<tbody>
	<c:set  var="saldoParcial" value="${fornecedor.conta.saldoInicial}" scope="request"></c:set>
		<c:forEach var="movimentacao" items="${movimentacaoList}" varStatus="contador">
		
			<c:if test="${movimentacao.tipoOperacao eq 'D'}">
				<c:set  var="saldoParcial" value="${saldoParcial - movimentacao.valor}" scope="request"></c:set>
			</c:if>
			<c:if test="${movimentacao.tipoOperacao eq 'C'}">
				<c:set  var="saldoParcial" value="${saldoParcial + movimentacao.valor}" scope="request"></c:set>
			</c:if>
		<tr>
			<td style="color: red ;">
				<fmt:formatDate value="${movimentacao.data}" type="date" pattern="dd/MM/yyyy"/>
			</td>
			<td>
				<fmt:formatNumber value="${movimentacao.valor}" minFractionDigits="2" type="currency"/>
			</td>
			<td>
				${movimentacao.tipoOperacao} 
			</td>
			<td>
				<fmt:formatNumber value="${saldoParcial}" minFractionDigits="2" type="currency"/>
			</td>
			
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
