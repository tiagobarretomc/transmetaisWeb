<div class="container">
		
        <br/>
		<h2>Materiais(Produtos)</h2>
		<br>
    
		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th width="10%">Código</th>
		<th width="10%">Sigla</th>
		<th width="90%">Descri&ccedil;&atilde;o</th>
		
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="material" items="${materiais}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/material/'/>${material.id}">${material.id}</a> 
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
