<%@page contentType="text/html; charset=UTF-8"%> 
<script type="text/javascript">

    $(document).ready(function(){
    	$("#dataInicio").mask('99/99/9999');
    	$("#dataFim").mask('99/99/9999');
    });
 </script>
<div class="container">
		<br/>
		<h2>Compras</h2>
		<br>
		<form action="<c:url value='/compra/'/>" id="formCompra" name="formCompra" method="post">
		<div class="row">
        	<div class="col-md-4">Fornecedor:<br/>
	        	<select id="fornecedor" name="fornecedorId" class="required">
					<option value ="">Selecione</option>
					<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
						<option value ="${fornecedor.id}">${fornecedor.nome}</option>
					</c:forEach>	
				</select>
        	</div>
        	<div class="col-md-4">Data Início: <br>
        		<input type="datetime"  name="dataInicio" id="dataInicio" class="required" value="" />
					
        	</div>
        	<div class="col-md-4">Data Fim: <br>
        		<input type="datetime"  name="dataFim" id="dataFim" class="required" value="" />
					
        	</div>
        </div>
        <input type="submit" value="Filtrar"/>
        </form>
		<br/>
		
		
		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th ></th>
		<th >Data</th>
		<th >Fornecedor</th>
		<th>Material</th>
		<th >Tipo Frete</th>
		<th>Quantidade</th>
		<th>Valor Total</th>
		
	</tr>
	</thead>
	<tbody>
	
		<c:forEach var="compra" items="${compras}" varStatus="contador">
	
		<tr>
			<td>
				<a href="${pageContext.request.contextPath}/compra/${compra.id}"><span title="Alterar" style="color: black;" class="glyphicon glyphicon-edit"></span></a>
				 
			</td>
			<td>
				<fmt:formatDate value="${compra.data}" type="date" pattern="dd/MM/yyyy"/>
				
			</td>
			<td>
				${compra.fornecedorMaterial.fornecedor.nome} 
			</td>
			<td>
				${compra.fornecedorMaterial.material.descricao} 
			</td>
			
			<td>
				${compra.fornecedorMaterial.tipoFrete.descricao}
			</td>
			<td >
				
				<fmt:formatNumber value="${compra.quantidade}" minFractionDigits="2" type="number"/>
			</td>
			<td>
				
				<fmt:formatNumber value="${compra.valor}" minFractionDigits="2" type="currency"/>
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
