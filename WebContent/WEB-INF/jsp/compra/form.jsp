<%@page contentType="text/html; charset=UTF-8"%> 
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$('#fornecedorMaterial').change(function(){
	    	
	        if($(this).val()) {
	        		$.ajax({
			        type: 'GET',
			        url: '${pageContext.request.contextPath}/fornecedorMaterial/obterPreco?_format=json',
			        data:	{'fornecedorMaterial.id': $("#fornecedorMaterial").val()},
			 	    success: function(json){
			 	    	var preco = parseFloat(json);
			 	    	$("#preco").attr('value',preco.toFixed(2));

					},
				    error: function(xhr){
				    	alert('erro!');
					}
			    });
	    	
	    	
	    	
	        } else {
	            //estoura um erro qualquer informando que nao foi selecionado nada
	        }
	    });
    		
    	 
    	$('#quantidade').change(function(){
    		var preco = parseFloat($("#preco").val());
    		var quantidade = parseFloat($("#quantidade").val());
    		var valor = preco*quantidade;
    		
    		
    		$("#valor").attr("value",valor.toFixed(2));
    	});
    	   
    	$('#quantidade').priceFormat({
            prefix: '',
            centsSeparator: ',',
            thousandsSeparator: '.',
            limit: 12
            
        });
    	
    	
    	
		$("#data").mask('99/99/9999');
		
		$("#btnAdicionar").click(function(){
    		$("#formCompra").submit();
    	});
    	
        $('#formCompra').validate({
            
        
    	});
    });
</script>

    <div class="container">
    <br>
	<h2>Dados da Compra</h2>
	<form action="<c:url value='/compra/adicionar'/>" id="formCompra" name="formCompra" method="post">
		<input type="hidden" id="compraId" name="compra.id" value="${compra.id}"/>
		
		<div class="row">
        	<div class="col-md-4">Fornecedor: <br>
        		
				<label>${fornecedor.id} - ${fornecedor.apelido } - ${fornecedor.nome }</label>
        	</div>
        	<div class="col-md-4">Material: <br>
        		<select id="fornecedorMaterial" name="compra.fornecedorMaterial.id" class="selectpicker,required">
					<option value ="">Selecione</option>
					<c:forEach var="fornecedorMaterial" items="${fornecedorMateriais}" varStatus="contador">
					
						<option value ="${fornecedorMaterial.id}" ${compra.fornecedorMaterial.id eq fornecedorMaterial.id ? 'selected' : ''}>${fornecedorMaterial.material.descricao} - ${fornecedorMaterial.tipoFrete.descricao}</option>
	
					</c:forEach>	
				</select>
        	</div>
        	<div class="col-md-4">
        		Preço:<br/>
        		<input type="text" name="compra.preco" id="preco" value="<fmt:formatNumber value="${compra.preco}" minFractionDigits="2" type="number"/>" readonly="readonly">
        	</div>
      	</div>
      	
      	<div class="row">
        	<div class="col-md-4">Quantidade: <br>
        		
				<input type="text" name="compra.quantidade" id="quantidade" value="${compra.quantidade *100}" class="required"/><span id="unidadeMedida">${compra.fornecedorMaterial.material.unidadeMedida.sigla }</span>
        	</div>
        	<div class="col-md-4">Valor Total: <br>
        		<input type="text" name="compra.valor" id="valor" class="required" value="<fmt:formatNumber value="${compra.valor}" minFractionDigits="2" type="number" />" readonly="readonly"/>
					
        	</div>
        	<div class="col-md-4">Data: <br>
        		<input type="datetime"  name="compra.data" id="data" class="required" value="<fmt:formatDate value="${compra.data}" type="date" pattern="dd/MM/yyyy"/>" />
					
        	</div>
      	</div>
      	<div class="row">
      		<div class="col-md-4">Observação:<br/>
      		
      			<textarea rows="5" cols="60" name="compra.observacao">${compra.observacao }</textarea>
      		</div>
      		<div class="col-md-4">
      		

      		</div>
      		<div class="col-md-4">
      			<!-- Single button -->
				<div class="btn-group">
				  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
				    Action <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" role="menu">
				    <li><a href="#">Action</a></li>
				    <li><a href="#">Another action</a></li>
				    <li><a href="#">Something else here</a></li>
				    <li class="divider"></li>
				    <li><a href="#">Separated link</a></li>
				  </ul>
				</div>
      		</div>
      	</div>
      	<br/>
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
		</button>
	</form>	
	
</div>

