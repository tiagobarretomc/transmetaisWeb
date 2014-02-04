
<fmt:setLocale value="pt-BR" /> 
<script type="text/javascript">

  
</script>

    <div class="container">
    <br>
	<h2>Tabela de Preços do Fornecedor</h2>
	
	<form action="" id="formMateriais" name="formMateriais" method="post">
	<table style="width:  600px;">
		<tr>
			<td>
				<b>Código de Fornecedor:</b>
			</td>
			<td>
				${fornecedor.id}
			</td>
		</tr>
		<tr>
			<td>
				<b>Fornecedor:</b>
			</td>
			<td>
				${fornecedor.nome}
			</td>
		</tr>
		<tr>
			<td>
				<b>Apelido:</b>
			</td>
			<td>
				${fornecedor.apelido}
			</td>
		</tr>
	</table>
	<br/>
	
	
	<div class="row" style="width: 600px;">
        	<div class="col-md-4">Material:<br/>
				<select style="width: 180px;" id="cboMaterial" name="materialId">
					<option value="" >--Selecione--</option>
					<c:forEach var="material" items="${materiais}">
						<option value="${material.id }" >${material.descricao}</option>
					</c:forEach>
				</select></div>
        	<div class="col-md-4">Preço:<br/>
				
			
				<input  type="text" id="precoMaterial" size="15" name="preco"/></div>
        	<div class="col-md-4">Forma de Frete/Entrega:<br/>
				<select style="width: 180px;" id="cboTipoFrete">
					<option value="" >--Selecione--</option>
						<option value="CIF" >Cif Transmetais</option>
						<option value="FOB" >Fob Transmetais</option>
						<option value="POU" >Posto na Usina</option>
						<option value="CAC" >Caminhão Carregado</option>
						<option value="ENC" >Encharutada</option>
					
				</select></div>
      	</div>
	
	
	<button id="btnAdicionar" type="button" class="btn btn-default btn-sm">
  <span class="glyphicon glyphicon-plus"></span> Adicionar
</button>
	
	<br/>
	<br/>
	<div id="tabelaPreco">
	
	
          
	<table  class="table table-bordered table-striped" style="width: 500px">
		<thead>
			<tr>
				<th>Material</th>
				<th>Preço/Kg</th>
				<th>Tipo Frete</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="materialFornecedor" items="${fornecedor.materiaisFornecedores}" varStatus="contador">
		<tr>
			<td>${materialFornecedor.material.descricao}</td>
			<td><fmt:formatNumber value="${materialFornecedor.valor}" minFractionDigits="2" type="currency"/> </td>
			<td>${materialFornecedor.status}</td>
			<td><a href="<c:url value='/fornecedorMaterial/excluir/'/>${materialFornecedor.id}"><span class="glyphicon glyphicon-remove-sign"></span></a></td>
		</tr>
		
		</c:forEach>
		</tbody>
	</table>
    </div>
    
    
</form>
		
</div>

