<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/adiantamento/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		
        <br/>
		<h2>Adiantamentos Para Fornecedores</h2>
		<br>
		
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-plus-sign"></span> Adicionar
		</button>
			
		<br/>
		<br/>
    
		<table  class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th ></th>
		<th >Data</th>
		<th >Fornecedor</th>
		<th >Valor</th>
		<th >Status</th>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="funcionario" items="${AdiantamentoList}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/adiantamento/'/>${adiantamento.id}"><span title="Alterar"  class="glyphicon glyphicon-edit"></span></a>
				<a href="<c:url value='/adiantamento/remove/'/>${adiantamento.id}"><span title="Excluir" class="glyphicon glyphicon-remove"></span></a> 
			</td>
			<td>
				<fmt:formatNumber minIntegerDigits="4" value="${adiantamento.id}" groupingUsed="" />
			</td>
			<td>
				${adiantamento.data} 
			</td>
			<td>
				${adiantamento.fornecedor.nome} 
			</td>
			<td>
				${adiantamento.valor} 
			</td>
			<td>
				${adiantamento.situacao} 
			</td>
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
