<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/unidadeMedida/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		
        <br/>
		<h2>Unidade de Medidas</h2>
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
		<th >Sigla</th>
		<th >Descrição</th>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="unidade" items="${unidades}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/unidadeMedida/'/>${unidade.id}"><span title="Alterar"  class="glyphicon glyphicon-edit"></span></a>
				<a href="<c:url value='/unidadeMedida/remove/'/>${unidade.id}"><span title="Excluir" class="glyphicon glyphicon-remove"></span></a> 
			</td>
			<td>
				${unidade.codigo} 
			</td>
			<td>
				${unidade.sigla} 
			</td>
			<td>
				${unidade.descricao} 
			</td>
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
