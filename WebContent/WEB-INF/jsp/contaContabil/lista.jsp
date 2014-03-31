<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/contaContabil/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		
        <br/>
		<h2>Contas</h2>
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
		<th >Código</th>
		<th >Número</th>
		<th >Descrição</th>
		
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="conta" items="${contaContabilList}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/contaContabil/'/>${conta.id}"><span title="Alterar"  class="glyphicon glyphicon-edit"></span></a>
				<a href="<c:url value='/contaContabil/remove/'/>${conta.id}"><span title="Excluir" class="glyphicon glyphicon-remove"></span></a> 
			</td>
			<td>
				<fmt:formatNumber minIntegerDigits="4" value="${conta.id}" groupingUsed="" />
			</td>
			<td>
				${conta.numero} 
			</td>
			<td>
				${conta.descricao} 
			</td>
			
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
