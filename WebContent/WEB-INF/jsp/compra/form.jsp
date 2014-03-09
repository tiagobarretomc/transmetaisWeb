<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	$('.selectpicker').selectpicker({
            'selectedText': 'cat'
        });
		$("#data").mask('99/99/9999');
    	function float2moeda(num) {

    		   x = 0;

    		   if(num<0) {
    		      num = Math.abs(num);
    		      x = 1;
    		   }
    		   if(isNaN(num)) num = "0";
    		      cents = Math.floor((num*100+0.5)%100);

    		   num = Math.floor((num*100+0.5)/100).toString();

    		   if(cents < 10) cents = "0" + cents;
    		      for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    		         num = num.substring(0,num.length-(4*i+3))+'.'
    		               +num.substring(num.length-(4*i+3));
    		      ret = num + ',' + cents;
    		      
    		      if (x == 1) ret = ' - ' + ret;return ret;

    	}
    	
    	function moeda2float(moeda){

    		   moeda = moeda.replace(".","");

    		   moeda = moeda.replace(",",".");

    		   return parseFloat(moeda);

    		}
    	function roundNumber (rnum) {

    		   return Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);

    		}
    	
    	$('#fornecedorMaterial').change(function(){
    		$("#data").mask('99/99/9999');
	        if($(this).val()) {
	        		$.ajax({
			        type: 'GET',
			        url: '${pageContext.request.contextPath}/fornecedorMaterial/obterPreco?_format=json',
			        data:	{'fornecedorMaterial.id': $("#fornecedorMaterial").val()},
			 	    success: function(json){
			 	    	var preco = float2moeda(parseFloat(json));
			 	    	
			 	    	//$("#preco").attr('value',preco.toFixed(2));
			 	    	$("#preco").attr('value',preco);
			 	    	
			 	    	
			 	    	if($("#quantidade").val()){
			 	    		var quantidade = moeda2float($("#quantidade").val());
			 	    		var valor = parseFloat(json)*quantidade;
			 	    		
			 	    		
			 	    		$("#valor").attr("value",float2moeda(valor));
			 	    	}
			 	    	
			 	    	

					},
				    error: function(xhr){
				    	alert('erro!');
					}
			    });
	    	
	    	
	    	
	        } else {
	            //estoura um erro qualquer informando que nao foi selecionado nada
	        }
	    });
    		
    	 
    	$('#quantidade').blur(function(){
    		var preco =  moeda2float($("#preco").val());
    		var quantidade = moeda2float($("#quantidade").val());
    		var valor = preco*quantidade;
    		
    		
    		$("#valor").attr("value",float2moeda(valor));
    	});
    	   
    	$('#quantidade').priceFormat({
            prefix: '',
            centsSeparator: ',',
            thousandsSeparator: '.',
            limit: 12
            
        });
		
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
		<input type="hidden" id="contaId" name="compra.conta.id" value="${compra.conta.id}"/>
		
		<div class="row">
        	<div class="col-md-4">Fornecedor: <br>
        		
				<label>${fornecedor.id} - ${fornecedor.apelido } - ${fornecedor.nome }</label>
        	</div>
        	<div class="col-md-4">Material: <br>
        		<select id="fornecedorMaterial" name="compra.fornecedorMaterial.id" class="selectpicker required">
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
        		
				<input type="text" name="compra.quantidade" id="quantidade" value="<fmt:formatNumber value="${compra.quantidade}" minFractionDigits="2" type="number" />" class="required"/><span id="unidadeMedida">${compra.fornecedorMaterial.material.unidadeMedida.sigla }</span>
        	</div>
        	<div class="col-md-4">Valor Total: <br>
        		<input type="text" name="compra.valor" id="valor" class="required" value="<fmt:formatNumber value="${compra.valor}" minFractionDigits="2" type="number" />" readonly="readonly"/>
					
        	</div>
        	<div class="col-md-4">Data: <br>
        		<input type="datetime"  name="compra.data" id="data"  value="<fmt:formatDate value="${compra.data}" type="date" pattern="dd/MM/yyyy"/>" class="required"/>
					
        	</div>
      	</div>
      	<div class="row">
      		<div class="col-md-4">
      			Num Nf-e:<br/>
				<input type="text" name="compra.numNf" id="numNf" value="${compra.numNf}" />
      		</div>
      		<div class="col-md-8">Observação:<br/>
      		
      			<textarea rows="5" cols="83" name="compra.observacao">${compra.observacao }</textarea>
      		</div>
      		
      	</div>
      	<br/>
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
		</button>
	</form>	
	
</div>

