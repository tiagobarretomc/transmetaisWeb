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
    	   
    	
		$("#data").mask('99/99/9999');
	
    	
        $('#formFornecedor').validate({
            
        
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
        		<select id="fornecedorMaterial" name="compra.fornecedorMaterial.id" class="required">
					<option value ="">Selecione</option>
					<c:forEach var="fornecedorMaterial" items="${fornecedorMateriais}" varStatus="contador">
					
						<option value ="${fornecedorMaterial.id}" ${compra.fornecedorMaterial.id eq fornecedorMaterial.id ? 'selected' : ''}>${fornecedorMaterial.material.descricao} - ${fornecedorMaterial.tipoFrete.descricao}</option>
	
					</c:forEach>	
				</select>
        	</div>
        	<div class="col-md-4">
        		Preço:<br/>
        		<input type="text" name="compra.preco" id="preco" value="${compra.preco}" readonly="readonly">
        	</div>
      	</div>
      	
      	<div class="row">
        	<div class="col-md-4">Quantidade: <br>
        		
				<input type="text" name="compra.quantidade" id="quantidade" value="${compra.quantidade }"/>
        	</div>
        	<div class="col-md-4">Valor Total: <br>
        		<input type="text" name="compra.valor" id="valor" class="required" value="${compra.quantidade }" readonly="readonly"/>
					
        	</div>
        	<div class="col-md-4">Data: <br>
        		<input type="datetime"  name="compra.data" id="data" class="required" value="${compra.data }" />
					
        	</div>
      	</div>
      	<div class="row">
      		<div class="col-md-4">Observação:<br/>
      		
      			<textarea rows="5" cols="60" name="compra.observacao">${compra.observacao }</textarea>
      		</div>
      		<div class="col-md-4">
      		</div>
      		<div class="col-md-4">
      		</div>
      	</div>
	
	</form>	
	
</div>

