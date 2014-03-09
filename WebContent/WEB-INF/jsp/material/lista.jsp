<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="container">
		
        <br/>
		<h2>Materiais(Produtos)</h2>
		<br>
    
		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		
		<th ></th>
		<th >Sigla</th>
		<th >Descrição</th>
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
				${material.unidadeMedida.sigla} 
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
