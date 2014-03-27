<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	var qtdItens = ${quantidadeItens};
    	var materiais;
    	//Caso venha vazio, terá apenas o item inicial
    	if(qtdItens == 0){
    		qtdItens = 1;
    	}
    	
    	

    	$("#btnAdicionarItem").click(function(){
    		var strLinha = '<tr><td><select id="fornecedorMaterial_' + qtdItens + '" name="compra.itens[' + qtdItens + '].material.id" class= "required form-control"><option value ="">Selecione</option></select></td>';
    		strLinha += '<td><input type="text" name="compra.itens[' + qtdItens + '].quantidade" id="quantidade_' + qtdItens + '" value="" class="required form-control"/></td>';
    		strLinha += '<td><input type="text" name="compra.itens[' + qtdItens + '].preco" id="preco_' + qtdItens + '" value="" class="form-control	" readonly="readonly"></td>';
    		strLinha += '<td><input type="text" name="compra.itens[' + qtdItens + '].valor" id="valor_' + qtdItens + '" class="required form-control" value="" readonly="readonly"/></td>';
    		strLinha += '</tr>';
    		$('#tabelaItens > tbody:last').append(strLinha);
    		
    		$.each(materiais.list, function(i){
				
				
					$('#fornecedorMaterial_' + qtdItens).append($("<option></option>")
		                    .attr("value",materiais.list[i].material.id)
		                    .text(materiais.list[i].material.descricao));
	    		
				
			    
			});
    		
    		$("#fornecedorMaterial_" + qtdItens).change(function(){
    			var indiceCampo = $(this).attr('id').split("_")[1];
        		
    	        if($(this).val()) {
    	        		$.ajax({
    			        type: 'GET',
    			        url: '${pageContext.request.contextPath}/fornecedorMaterial/obterPreco?_format=json',
    			        data:	{'fornecedorMaterial.material.id': $(this).val(), 'fornecedorMaterial.fornecedor.id' : $("#fornecedor").val(), 'fornecedorMaterial.tipoFrete': $("#cboTipoFrete").val()},
    			 	    success: function(json){
    			 	    	var preco = float2moeda(parseFloat(json));
    			 	    	
    			 	    	//$("#preco").attr('value',preco.toFixed(2));
    			 	    	
    			 	    	
    			 	    	$("#preco_"+indiceCampo).attr('value',preco);
    			 	    	
    			 	    	
    			 	    	if($("#quantidade_"+indiceCampo).val()){
    			 	    		var quantidade = moeda2float($("#quantidade_"+indiceCampo).val());
    			 	    		var valor = parseFloat(json)*quantidade;
    			 	    		
    			 	    		
    			 	    		$("#valor_" + indiceCampo).attr("value",float2moeda(valor));
    			 	    	}
    			 	    	
    			 	    	

    					},
    				    error: function(xhr){
    				    	alert('erro!');
    					}
    			    });
    	    	
    	    	
    	    	
    	        } else {
    	        	$("#valor_" + indiceCampo).attr("value","");
    	        	$("#preco_"+indiceCampo).attr('value',"");
    	        }
    		});
    		
    		$("#quantidade_" + qtdItens).blur(function(){
        		
        		var indiceCampo = $(this).attr('id').split("_")[1];
        		
        		var preco =  moeda2float($("#preco_"+indiceCampo).val());
        		var quantidade = moeda2float($("#quantidade_"+indiceCampo).val());
        		var valor = preco*quantidade;
        		
        		
        		$("#valor_"+indiceCampo).attr("value",float2moeda(valor));
        		
        		var valorTotalItens = 0;
        		for(i=0;i<qtdItens;i++){
        			valorTotalItens +=moeda2float($("#valor_"+i).val());
        			
        		}
        		$("#compra\\.valor").attr("value",float2moeda(valorTotalItens));
        		
        	});
        	   
        	$("#quantidade_" + qtdItens).priceFormat({
                prefix: '',
                centsSeparator: ',',
                thousandsSeparator: '.',
                limit: 12
                
            });
    		
    		qtdItens++;
    		//alert(qtdItens);
    	});
    	
    	$('.selectpicker').selectpicker({
            //'selectedText': 'cat'
        });
    	
    	var options = new Array();
	   	 options['language'] = 'pt-BR';
	   	 $('.datepicker').datepicker(options);
    	
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
    	//$( "input[name^='news']" )
    	
    	
    	$("#cboTipoFrete").change(function(){
    		
    		for(i=0;i<qtdItens;i++){
    			$('#fornecedorMaterial_' + i + ' option[value!=""]').remove();
    		}
    		
    		
    		$.getJSON('<c:url value="/compra/loadJsonMaterial"/>?tipoFrete=' + $(this).val() + '&fornecedor.id=' + $("#fornecedor").val(), function (json) {
                
    			materiais = json;
    			
   				for(j=0;j<qtdItens;j++){
	    			$.each(json.list, function(i){
	    				
	    					$('#fornecedorMaterial_' + j).append($("<option></option>")
				                    .attr("value",json.list[i].material.id)
				                    .text(json.list[i].material.descricao));
	    				
	    			});
   	    		}
    			
            });
    		
    		
    		
    		
    	});
    	$("select[id^='fornecedorMaterial_']").change(function(){
    		
    		var indiceCampo = $(this).attr('id').split("_")[1];
    		
	        if($(this).val()) {
	        		$.ajax({
			        type: 'GET',
			        url: '${pageContext.request.contextPath}/fornecedorMaterial/obterPreco?_format=json',
			        data:	{'fornecedorMaterial.material.id': $(this).val(), 'fornecedorMaterial.fornecedor.id' : $("#fornecedor").val(), 'fornecedorMaterial.tipoFrete': $("#cboTipoFrete").val()},
			        //data:	{'fornecedorMaterial.id': $(this).val()},
			 	    success: function(json){
			 	    	var preco = float2moeda(parseFloat(json));
			 	    	
			 	    	//$("#preco").attr('value',preco.toFixed(2));
			 	    	
			 	    	
			 	    	$("#preco_"+indiceCampo).attr('value',preco);
			 	    	
			 	    	
			 	    	if($("#quantidade_"+indiceCampo).val()){
			 	    		var quantidade = moeda2float($("#quantidade_"+indiceCampo).val());
			 	    		var valor = parseFloat(json)*quantidade;
			 	    		
			 	    		
			 	    		$("#valor_" + indiceCampo).attr("value",float2moeda(valor));
			 	    	}
			 	    	
			 	    	

					},
				    error: function(xhr){
				    	alert('erro!');
					}
			    });
	    	
	    	
	    	
	        } else {
	        	$("#valor_" + indiceCampo).attr("value","");
	        	$("#preco_"+indiceCampo).attr('value',"");
	        }
	    });
    		
    	 
    	$("input[id^='quantidade_']").blur(function(){
    		
    		var indiceCampo = $(this).attr('id').split("_")[1];
    		
    		var preco =  moeda2float($("#preco_"+indiceCampo).val());
    		var quantidade = moeda2float($("#quantidade_"+indiceCampo).val());
    		var valor = preco*quantidade;
    		
    		
    		$("#valor_"+indiceCampo).attr("value",float2moeda(valor));
    		
    		var valorTotalItens = 0;
    		
    		for(k=0;k<qtdItens;k++){
    			valorTotalItens +=moeda2float($("#valor_"+k).val());
    			
    		}
    		$("#compra\\.valor").attr("value",float2moeda(valorTotalItens));
    	});
    	   
    	$("input[id^='quantidade_']").priceFormat({
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
	<h2>Cadastro de Compra</h2>
	<div class="panel panel-default">
	<div class="panel-body">
	<h3>Dados da Compra</h3>
	<form action="<c:url value='/compra/salvar'/>" id="formCompra" name="formCompra" method="post">
		<input type="hidden" id="compraId" name="compra.id" value="${compra.id}"/>
		<input type="hidden" id="contaId" name="compra.conta.id" value="${compra.conta.id}"/>
		<input type="hidden" id="fornecedor" name="compra.fornecedor.id" value="${fornecedor.id}"/>
		
		<div class="row">
        	<div class="col-md-4">Fornecedor: <br>
        		
				<label>${fornecedor.id} - ${fornecedor.apelido } - ${fornecedor.nome }</label>
        	</div>
        	<div class="col-md-2">Data: <br>
        		<c:set var="now" value="<%=new java.util.Date()%>" />
        		<input type="datetime"  name="compra.data" id="data"  value="<fmt:formatDate value="${compra.data == null ? now : compra.data }" type="date" pattern="dd/MM/yyyy"/>" class="required form-control datepicker" data-date-format="dd/mm/yyyy" />
					
        	</div>
        	<div class="col-md-2">
      			Num Nf-e:<br/>
				<input type="text" name="compra.numNf" id="numNf" value="${compra.numNf}" class="form-control"/>
      		</div>
      		<div class="col-md-4">
				<label for="cboTipoFrete">Forma de Frete/Entrega:</label>
				<select style="width: 180px;" id="cboTipoFrete" name="compra.tipoFrete" class="selectpicker form-control" >
					<option value="" >Selecione</option>
					<c:forEach var="tipoFrete" items="${tiposFrete}">
						<option value="${tipoFrete.name }" >${tipoFrete.descricao}</option>
					</c:forEach>
				</select>
				</div>
        	
      	</div>
      	
      	
      	<div class="row">
      		
      		<div class="col-md-8">Observação:<br/>
      		
      			<textarea rows="5" cols="83" name="compra.observacao" class="form-control">${compra.observacao }</textarea>
      		</div>
      		
      	</div>
      	<br/>
      	
      	
    	<div class="panel panel-default">
			<div class="panel-body">
				<h4>Itens da Compra</h4>
				<button type="button" id="btnAdicionarItem" class="btn btn-default btn-md">
				  <span class="glyphicon glyphicon-plus-sign"></span> Adicionar
				</button>
				<br/>
				<br/>
				<div id="divTabela">
				<table  class="table table-bordered table-striped" id="tabelaItens">
				
				<thead>
			<tr>
				
				<th >Material</th>
				<th >Quantidade</th>
				<th>Preço</th>
				<th >Valor</th>
				
				
			</tr>
			</thead>
			<tbody>
			
				
				<c:if test="${empty compra.id}">
				<tr>
					<td>
						<select id="fornecedorMaterial_0" name="compra.itens[0].material.id" class= "required form-control">
							<option value ="">Selecione</option>
							
						</select>
					</td>
					<td>
						<input type="text" name="compra.itens[0].quantidade" id="quantidade_0" value="<fmt:formatNumber value="${compra.quantidade}" minFractionDigits="2" type="number" />" class="required form-control"/>
						
					</td>
					<td>
						<input type="text" name="compra.itens[0].preco" id="preco_0" value="<fmt:formatNumber value="${compra.preco}" minFractionDigits="2" type="number"/>" class="form-control	" readonly="readonly">
					</td>
					<td>
						<input type="text" name="compra.itens[0].valor" id="valor_0" class="required form-control" value="<fmt:formatNumber value="${compra.valor}" minFractionDigits="2" type="number" />" readonly="readonly"/>
					</td>
					
				</tr>
				</c:if>
				
				<c:if test="${not empty compra.id}">
					<c:forEach var="compra" items="${compras}" varStatus="contador">
					<tr>
						<td>
							<select id="fornecedorMaterial_${contador}" name="compra.itens[${contador}].material.id" class="required form-control">
								<option value ="">Selecione</option>
								<c:forEach var="fornecedorMaterial" items="${fornecedorMateriais}" varStatus="contador">
								
									<option value ="${fornecedorMaterial.id}" ${compra.fornecedorMaterial.id eq fornecedorMaterial.id ? 'selected' : ''}>${fornecedorMaterial.material.descricao} - ${fornecedorMaterial.tipoFrete.descricao}</option>
				
								</c:forEach>	
							</select>
						</td>
						<td>
							<input type="text" name="compra.itens[${contador}].quantidade" id="quantidade_${contador}" value="<fmt:formatNumber value="${compra.quantidade}" minFractionDigits="2" type="number" />" class="required form-control"/>
							
						</td>
						<td>
							<input type="text" name="compra.itens[${contador}].preco" id="preco_${contador}" value="<fmt:formatNumber value="${compra.preco}" minFractionDigits="2" type="number"/>" class="form-control	" readonly="readonly">
						</td>
						<td>
							<input type="text" name="compra.itens[${contador}].valor" id="valor_${contador}" class="required form-control" value="<fmt:formatNumber value="${compra.valor}" minFractionDigits="2" type="number" />" readonly="readonly"/>
						</td>
						
					</tr>
				</c:forEach>
				</c:if>
			</tbody>
			</table>
			<br/>
			<div class="panel panel-default">
		  	<div class="panel-body">
			<div class="row">
				<div class="col-md-4">
					<label for="divValorTotal">Valor Total:</label>
					
					<input type="text" name="compra.valor" id="compra.valor" class="required form-control" value="<fmt:formatNumber value="${compra.valor}" minFractionDigits="2" type="number" />" readonly="readonly"/>
				
				</div>
				
			
			</div>
			</div>
			</div>
			</div>
			</div>
		</div>
      	
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
		</button>
	</form>	
	</div>
	</div>
</div>

