<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/material/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		
        <br/>
		<h2>Materiais(Produtos)</h2>
		<br>
		
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-plus-sign"></span> Adicionar
		</button>
			
		<br/>
		<br/>
    
		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		
		<th ></th>
		<th >Sigla</th>
		<th >Descrição</th>
		<th >NCM</th>
		<th >Grupo Material</th>
		<th >Unid. de Medida</th>
		
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="material" items="${materiais}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/material/'/>${material.id}"><span title="Alterar" style="color: black;" class="glyphicon glyphicon-edit"></span></a> 
			</td>
			<td>
				${material.sigla} 
			</td>
			<td>
				${material.descricao} 
			</td>
			<td>
				${material.ncm} 
			</td>
			<td>
				${material.grupoMaterial.descricao} 
			</td>
			<td>
				${material.unidadeMedida.sigla} 
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
