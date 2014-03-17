<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/centroAplicacao/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		
        <br/>
		<h2>Centros de Aplicações</h2>
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
		<th >Centro de Custo</th>
		<th >Descrição</th>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="centroAplicacao" items="${centroAplicacaoList}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/centroAplicacao/'/>${centroAplicacao.id}"><span title="Alterar"  class="glyphicon glyphicon-edit"></span></a>
				<a href="<c:url value='/centroAplicacao/remove/'/>${centroAplicacao.id}"><span title="Excluir" class="glyphicon glyphicon-remove"></span></a> 
			</td>
			<td>
				<fmt:formatNumber minIntegerDigits="4" value="${centroAplicacao.id}" groupingUsed="" />
			</td>
			<td>
				${centroAplicacao.numero} 
			</td>
			<td>
				${centroAplicacao.centroCusto.descricao} 
			</td>
			<td>
				${centroAplicacao.descricao} 
			</td>
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
