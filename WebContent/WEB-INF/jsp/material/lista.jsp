<%@page contentType="text/html; charset=UTF-8"%> 
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
		</tr>
		</c:forEach>
	</tbody>
	</table>
