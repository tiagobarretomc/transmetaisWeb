<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/despesa/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		
        <br/>
		<h2>Despesas</h2>
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
		<th >Descrição</th>
		<th >Data Vencimento</th>
		<th >Centro de Aplicação</th>
		<th >Valor</th>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="despesa" items="${beanList}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/despesa/'/>${despesa.id}"><span title="Alterar"  class="glyphicon glyphicon-edit"></span></a>
				<a href="<c:url value='/despesa/remove/'/>${despesa.id}"><span title="Excluir" class="glyphicon glyphicon-remove"></span></a> 
			</td>
			<td>
				${despesa.id} 
			</td>
			<td>
				${despesa.descricao} 
			</td>
			<td><fmt:formatDate value="${despesa.dataVencimento}" type="date" pattern="dd/MM/yyyy"/></td>
			<td>
				${despesa.centroAplicacao.numero} - ${despesa.centroAplicacao.descricao}
			</td>
			<td>
				<fmt:formatNumber value="${despesa.valor}" minFractionDigits="2" type="currency"/>
			</td>
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
