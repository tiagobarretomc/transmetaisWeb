<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	var qtdItens = ${quantidadeItens};
    	var materiais;
    	//Caso venha vazio, terá apenas o item inicial
    	if(qtdItens == 0){
    		qtdItens = 1;
    	}
    	
     	$(".datepicker").datepicker({
          	 format: "dd/mm/yyyy",
          	 endDate: "-2d"
       	})
       	
       	$(".datepicker").mask('99/99/9999');

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
        
        
        $("#optPagamentoAVista").click(function(){
    		alert('teste');
	    	$('#copra\\.dataVencimento').attr('disabled', true);
	    	$("#compra\\.conta\\.id").attr('disabled', false);
	    	$("#qtdParcelas").attr('readonly', true);
	    	$("#qtdParcelas").val('');
	    	
	    	var linhas = $("#tabelaParcelas tr").length;
    		
    		for(j=0; j< linhas; j++){

    			//$('#tabelaParcelas > tbody:last').remove();
    			$("#parcela_" + j).remove();
    			
    		}
    		
    		$("#divPainelParcelas").hide();
	    	
	    	atualizaModalidades($(this).val());
	    	
	    });
        
        $("#btnGerarParcelas").click(function(){
	    	
	    	if ($('#qtdParcelas').val() == '' || $('#qtdParcelas').val()=='0'){
	    		alert('Informe a quantidade de parcelas!');
	    		return;
	    	}else{
	    		$("#divCheque").hide();
	    	}
	    	
	    	
	    	
	    	 if ($('#compra\\.dataVencimento').val() == '') {
	    		 alert('Informe a Data de Vencimento da primeira parcela!');
		    		return;
	    		  }
	    	
	    
	    	
	    	var qtdParcelas = $('#qtdParcelas').val();
	    	var strLinha;
	    	
	    	if ($("#tabelaParcelas tr").length > 1){
	    		
	    		var linhas = $("#tabelaParcelas tr").length;
	    		
	    		for(j=0; j< linhas; j++){
	
	    			//$('#tabelaParcelas > tbody:last').remove();
	    			$("#parcela_" + j).remove();
	    			
	    		}
	    		
	    	}
	    	
	    	for(i = 0; i < qtdParcelas; i++){
	    		
	    		adicionarParcela(i);
	    	}
	    	
	    	
	    	
    	});
        
        $("#optPagamentoAPrazo").click(function(){
	    	$('#compra\\.dataVencimento').attr('disabled', false);
	    	$("#compra\\.conta\\.id").attr('disabled', true);
	    	$("#qtdParcelas").attr('readonly', false);
	    	
	   
	    	
	    	$('#compra\\.conta\\.id').children('option').remove();
	    	
	    	$("#divPainelParcelas").show();
	    	
	    	atualizaModalidades($(this).val());
	    	
	    });
	    
	    
	    
	    $("input[id^='valorParcela']").priceFormat({
	        prefix: '',
	        centsSeparator: ',',
	        thousandsSeparator: '.',
	        limit: 12
	        
	    });
	    
	    //qualquer parcela quendo alterar o valor da mesma alterar o valor total da despesa.
	    $("input[id^='valorParcela']").change(function(){
	    	
	    	var valorTotal=0.00;
	    	
	    	$("input[id^='valorParcela']").each(function(){
	    		 valorTotal += moeda2float($(this).val()); 
	    		 
	    	});
	    	
	    	
	    	$("#valor").val(float2moeda(valorTotal));
	    	
	    	
	    	
	    });
        
        
        
        
        
        
        
        
    });
    
    
    
    
    
    
    
    
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
	
	function atualizaModalidades(forma){
		$.ajax({
	        type: 'GET',
	        url: '${pageContext.request.contextPath}/despesa/loadModalidades?_format=json',
	        data:	{tipoPagamento: forma},
	 	    success: function(json){
	 	    	
	 	    	var jsonObject = eval(json);
	 	    	
	 	    	var modalidades = jsonObject.map;
	 	    	
	 	    	
	 	    	var html = "";  
	 	       html += "<select name='compra.modalidadePagamento' id='compra.modalidadePagamento' class=' form-control' onchange='atualizaContas()'>" ;  
	 	      html += "<option value=''>Selecione</option>";
	 	       for(i=0;i<modalidades.length;i++) {  
	 	           html += "<option value='"+modalidades[i][0] +"'>"+modalidades[i][1]+"</option>";                           
	 	       }  
	 	       html += "</select> " ;  
	 	       
	 	       var div = document.getElementById("ajaxResultDiv");  
	 	       div.innerHTML = html; 
	 	      $('.selectpicker').selectpicker();
	 	      
	 	     	$('#compra\\.modalidadePagamento').change(function(){
	 	    		
	     	 	});
	 	        

			},
		    error: function(xhr){
		    	alert('erro!');
				    }
	    });
	}
	
	function adicionaMonth(data, qtdMeses) {  
        var now = new Date();  
        alert('Now is ' + now);  
        var month = now.getMonth();  
        now.setMonth(month + 2);  
        alert(now);  
    } 
	
	function adicionarParcela(i){
		//alert($("#compra\\.valor").val());
		//alert($("#qtdParcelas").val());
		
		var parcela =  float2moeda(roundNumber(moeda2float($('#compra\\.valor').val()) / moeda2float($('#qtdParcelas').val())));
		//var parcela = "0,00";
		var dataParcela = Date.parseExact($('#compra\\.dataVencimento').val(),'dd/MM/yyyy').addMonths(i);
		strLinha = '<tr id="parcela_' + i + '">';
		strLinha += '<td style="vertical-align: middle;"><span title="Excluir" class="glyphicon glyphicon-remove" onclick="removerParcela(' + i +')"></span></td>';
		
		strLinha += '<td style="max-width:130px"><input name="compra.parcelas[' + i + '].dataVencimento" id="dataParcela' + i + '" data-date-format="dd/mm/yyyy" value="' + dataParcela.toString('dd/MM/yyyy') + '" class="form-control required datepicker" size="8" maxlength="10"/></td>';
		strLinha += '<td style="max-width:130px"><input name="compra.parcelas[' + i + '].valor" id="valorParcela' + i + '" value="' + parcela + '" class="form-control required"  maxlength="18"/></td>';
		if($("#compra\\.modalidadePagamento").val() == 'C'){
			strLinha += '<td style="max-width:130px"><input name="compra.parcelas[' + i + '].chequeEmitido.numeroCheque" id="numChequeParcela' + i + '" value="" class="form-control required"  maxlength="18"/></td>';
		}else{
			strLinha += '<td style="max-width:130px"></td>';
		}
		
		strLinha += '</tr>';
		$('#tabelaParcelas > tbody:last').append(strLinha);
		$("#dataParcela" + i).mask('99/99/9999');
		$("#valorParcela" + i).priceFormat({
	        prefix: '',
	        centsSeparator: ',',
	        thousandsSeparator: '.',
	        limit: 12
	        
	    });
		
		$("input[id^='valorParcela']").change(function(){
	    	
	    	var valorTotal=0.00;
	    	
	    	$("input[id^='valorParcela']").each(function(){
	    		 valorTotal += moeda2float($(this).val()); 
	    		 
	    	});
	    	
	    	
	    	$("#valor").val(float2moeda(valorTotal));
	    	
	    	
	    	
	    });
		
	}
	function removerParcela(id){
		
    	$("#parcela_" + id).remove();
    	$("#qtdParcelas").val($("tr[id^='parcela_']").size());
    }
	
	function atualizaContas(){
		
		if($('input[name=compra\\.formaPagamento]:checked').val() == 'S'){
			
		}
		
		
		if ($('#compra\\.modalidadePagamento').val() == 'C'){
    		$("#divCheque").show();
    		$("#compra\\.conta\\.id").attr('disabled', false);
    	}else{
    		$("#divCheque").hide();
    		$("#compra\\.conta\\.id").empty().append('');
    		$("#compra\\.conta\\.id").val('');
    		
    		$("#compra\\.conta\\.id").attr('disabled', true);
    	}
		
		if($('input[name=compra\\.formaPagamento]:checked').val()) {
    		$.ajax({
	        type: 'GET',
	        url: '${pageContext.request.contextPath}/despesa/loadContas?_format=json',
	        data:	{formaPagamento: $('#compra\\.modalidadePagamento').val(), tipoPagamento: $('input[name=compra\\.formaPagamento]:checked').val()},
	 	    success: function(json){
	 	    	
	 	    	var jsonObject = eval(json);
	 	    	
	 	    	var contas = jsonObject.list;
	 	    	
	 	    	var html = "";  
	 	       html += "<select name='compra.conta.id' id='compra.conta.id' class=' form-control' data-live-search='true'>" ; 
	 	      html += "<option value=''>Selecione</option>";
	 	       for(i=0;i<contas.length;i++) {  
	 	           html += "<option value='"+contas[i].id +"'>"+contas[i].descricao+"</option>";                           
	 	       }  
	 	       html += "</select> " ; 
	 	       
	 	       
	 	       var div = document.getElementById("divCboContas");  
	 	       div.innerHTML = html; 
	 	      $('.selectpicker').selectpicker();
	 	        

			},
		    error: function(xhr){
		    	alert('erro!');
				    }
	    });
	
	
	
    	}  
		
		
		
	}
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
        	<div class="col-md-4"><label for="compra.centroAplicacao.id">Fornecedor:</label>
        		
				<label>${fornecedor.id} - ${fornecedor.apelido } - ${fornecedor.nome }</label>
        	</div>
        	<div class="col-md-2"><label for="compra.data">Data:</label>
        		<c:set var="now" value="<%=new java.util.Date()%>" />
        		<input type="datetime"  name="compra.data" id="data"  value="<fmt:formatDate value="${compra.data == null ? now : compra.data }" type="date" pattern="dd/MM/yyyy"/>" class="required form-control datepicker" data-date-format="dd/mm/yyyy" ${not empty compra.id ? 'disabled="disabled"' : ''}/>
					
        	</div>
        	<div class="col-md-2">
      			<label for="compra.numNf">Num NF:</label>
				<input type="text" name="compra.numNf" id="numNf" value="${compra.numNf}" class="form-control" ${not empty compra.id ? 'disabled="disabled"' : ''}/>
      		</div>
      		<div class="col-md-4">
				<label for="cboTipoFrete">Forma de Frete/Entrega:</label>
				<select style="width: 180px;" id="cboTipoFrete" name="compra.tipoFrete" class="selectpicker form-control" ${not empty compra.id ? 'disabled="disabled"' : ''}>
					<option value="" >Selecione</option>
					<c:forEach var="tipoFrete" items="${tiposFrete}">
						<option value="${tipoFrete.name }" ${compra.tipoFrete eq tipoFrete ? 'selected' : ''}>${tipoFrete.descricao}</option>
					</c:forEach>
				</select>
				</div>
        	
      	</div>
      	
      	<div class="row">
	   		<div class="col-md-4">
	        	<label for="compra.centroAplicacao.id">Centro de Aplicação:</label>
	        		<select id="compra.centroAplicacao.id" name="compra.centroAplicacao.id" class="selectpicker required form-control" data-live-search="true">
							<option value ="">Selecione</option>
							<c:forEach var="centro" items="${centros}" varStatus="contador">
							
								<option value ="${centro.id}" ${compra.centroAplicacao.id eq centro.id ? 'selected' : ''}>${centro.numero} - ${centro.descricao}</option>
			
							</c:forEach>	
					</select>
	       	</div>
	       	<div class="col-md-4">
	       	<label for="compra.contaContabil.id">Conta Contábil:</label>
	       		<select id="compra.contaContabil.id" name="compra.contaContabil.id" class="selectpicker required form-control" data-live-search="true">
						<option value ="">Selecione</option>
						<c:forEach var="conta" items="${contas}" varStatus="contador">
						
							<option value ="${conta.id}" ${compra.contaContabil.id eq conta.id ? 'selected' : ''}>${conta.numero} - ${conta.descricao}</option>
		
						</c:forEach>	
				</select>
	       	</div>
      	</div>
      	
      	<div class="row">
      		
      		<div class="col-md-8"><label for="compra.observacao">Observação:</label>
      		
      			<textarea rows="5" cols="83" name="compra.observacao" class="form-control" ${not empty compra.id ? 'disabled="disabled"' : ''}>${compra.observacao }</textarea>
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
						<input type="text" name="compra.itens[0].quantidade" id="quantidade_0" value="" class="required form-control"/>
						
					</td>
					<td>
						<input type="text" name="compra.itens[0].preco" id="preco_0" value="" class="form-control	" readonly="readonly">
					</td>
					<td>
						<input type="text" name="compra.itens[0].valor" id="valor_0" class="required form-control" value="<fmt:formatNumber value="${compra.valor}" minFractionDigits="2" type="number" />" readonly="readonly"/>
					</td>
					
				</tr>
				</c:if>
				
				<c:if test="${not empty compra.id}">
					<c:forEach var="item" items="${compra.itens}" varStatus="contador">
					<tr>
						<td>
							<select id="fornecedorMaterial_${contador}" name="compra.itens[${contador}].material.id" class="required form-control" disabled="disabled">
								<option value ="">Selecione</option>
								<c:forEach var="fornecedorMaterial" items="${fornecedorMateriais}" varStatus="contador">
								
									<option value ="${fornecedorMaterial.material.id}" ${item.material.id eq fornecedorMaterial.material.id ? 'selected' : ''}>${fornecedorMaterial.material.descricao}</option>
				
								</c:forEach>	
							</select>
						</td>
						<td>
							<input type="text" name="compra.itens[${contador}].quantidade" id="quantidade_${contador}" value="<fmt:formatNumber value="${item.quantidade}" minFractionDigits="2" type="number" />" class="required form-control" readonly="readonly"/>
							
						</td>
						<td>
							<input type="text" name="compra.itens[${contador}].preco" id="preco_${contador}" value="<fmt:formatNumber value="${item.preco}" minFractionDigits="2" type="number"/>" class="form-control	" readonly="readonly">
						</td>
						<td>
							<input type="text" name="compra.itens[${contador}].valor" id="valor_${contador}" class="required form-control" value="<fmt:formatNumber value="${item.valor}" minFractionDigits="2" type="number" />" readonly="readonly"/>
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
		<div class="panel panel-default">
		  	<div class="panel-body">
				<h4>Faturamento</h4> ${empty fornecedor.conta ? '': ' - Valor a ser abatido no saldo de adiantamentos do fornecedor' }
				<c:if test="${empty fornecedor.conta }">
				
				
				<div class="row">
					<div class="col-md-2">
						<label for="optPagamentoAVista">Forma Pagamento:</label><br/>
						<input type="radio" name="compra.formaPagamento" value="V" id="optPagamentoAVista" ${compra.formaPagamento.name == 'V' ? 'checked="checked"' : '' }/>&nbsp;À vista&nbsp;
						<input type="radio" name="compra.formaPagamento" value="P" id="optPagamentoAPrazo" ${compra.formaPagamento.name == 'P' ? 'checked="checked"' : '' }/>&nbsp;À prazo&nbsp;
					</div>
	        		
			       	<div class="col-md-2">
			        		<label for="compra.modalidadePagamento">Modalidade Pag.:</label>
			        		<div id="ajaxResultDiv">
			        		<select id="compra.modalidadePagamento" name="compra.modalidadePagamento" class=" required form-control" >
									<option value ="">Selecione</option>
									<c:forEach var="modalidade" items="${modalidades}" varStatus="contador">
									
										<option value ="${modalidade.id}" ${compra.modalidade.id eq modalidade.id ? 'selected' : ''}>${centro.numero} - ${centro.descricao}</option>
					
									</c:forEach>	
								</select>
			        		</div>
			        </div>
			        <div class="col-md-2" id="divCheque" style="display:none;">
	      	
        				<label for="">Número Cheque:</label>
        				<input name="compra.chequeEmitidoList[0].numeroCheque"  id="compra.chequeEmitidoList[0].numeroCheque" value="" class="form-control required " maxlength="15"  />
        			</div>
		       </div> 
				    <div class="row">
						<div class="col-md-4">
			        		<label for="compra.conta.id">Conta Financeira:</label>
							<div id="divCboContas">
				        		<select id="compra.conta.id" name="compra.conta.id" class=" form-control " >
										<option value ="">Selecione</option>
										<c:forEach var="conta" items="${contasFinanceiras}" varStatus="contador">
										
											<option value ="${conta.id}" ${compra.conta.id eq conta.id ? 'selected' : ''}>${conta.numero} - ${conta.descricao}</option>
						
										</c:forEach>	
								</select>
							</div>
			        	</div>
			        	
      	
      				<div class="col-md-2">
        				<label for="compra.dataCompetencia">Data Competência:</label>
        				<input name="compra.dataCompetencia" id="compra.dataCompetencia" value=" <fmt:formatDate value="${compra.dataCompetencia }" type="date" pattern="dd/MM/yyyy"/>" class="form-control required datepicker" size="8" maxlength="10" />
        			</div>
        			<div class="col-md-2">
        				<label for="compra.dataVencimento">Data Vencimento:</label>
        				<input name="compra.dataVencimento" id="compra.dataVencimento"  value="<fmt:formatDate value="${compra.dataVencimento }" type="date" pattern="dd/MM/yyyy"/>" class="form-control required datepicker" size="8" maxlength="10" />
        			</div>
      				<div class="col-md-2">
      					<label for="qtdParcelas">Qtd.Parcelas:</label>
        				<input id="qtdParcelas" value="${fn:length(compra.parcelas)}" size="10" class="form-control "/>
        			</div>
        	
        	<div class="col-md-2"><br/>
      			<button type="button" id="btnGerarParcelas" class="btn btn-default btn-md" style="margin-top: 4px;">
				  Gerar Parcelas
				</button>
				
				
      		</div>
    		</div>
      	
      		<br/>
      		<div id="divTabelaParcelas">
				<table  class="table table-bordered table-striped" id="tabelaParcelas">
				
				<thead>
			<tr>
				<th ></th>
				<th >Data</th>
				<th >Valor</th>
				<th >Num Cheque</th>
				
				
			</tr>
			</thead>
			<tbody>
			<c:forEach var="parcela" items="${compra.parcelas}" varStatus="contador">

				
				<tr id="parcela_${contador.index }">
				
					<td style="vertical-align: middle;"><a href="#" onclick="removerParcela(${contador.index}); return false;"><span title="Excluir" class="glyphicon glyphicon-remove" ></span></a></td>
					<td style="max-width:110px"><input name="compra.parcelas[${contador.index}].dataVencimento" id="dataParcela${contador.index}" data-date-format="dd/mm/yyyy" value="<fmt:formatDate value="${parcela.dataVencimento }" type="date" pattern="dd/MM/yyyy"/>" class="form-control required datepicker" size="8" maxlength="10"/></td>
					<td style="max-width:110px"><input name="compra.parcelas[${contador.index}].valor" id="valorParcela${contador.index}" value="<fmt:formatNumber value="${parcela.valor}" minFractionDigits="2" type="currency"/>" class="form-control required"  maxlength="18"/></td>
					<c:if test="${not empty parcela.chequeEmitido }">
						<td style="max-width:110px"><input name="compra.parcelas[${contador.index}].chequeEmitido.numeroCheque" id="numChequeParcela${contador.index}" value="${parcela.chequeEmitido.numeroCheque}" class="form-control required"  maxlength="18"/></td>
					</c:if>
					<c:if test="${ empty parcela.chequeEmitido }">
					<td style="max-width:110px"></td>
					</c:if>
			
		
				</tr>
			</c:forEach>
			</tbody>
			</table>
			</div>
			</c:if>
      		<c:if test="${not empty fornecedor.conta }">
      			<div class="row">
      				<div class="col-md-8">
      				<label>Saldo Atual:</label>
      				<label><fmt:formatNumber value="${fornecedor.conta.saldo}" minFractionDigits="2" type="currency" /></label>
      				</div>
      			</div>
      		</c:if>
		        	
		        	
	        
			</div>
			</div>
      	<div class="row">
			<div class="col-md-1">
				<button type="button" id="btnAdicionar" class="btn btn-default btn-md"  ${not empty compra.id ? 'disabled="disabled"' : ''}>
				  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
				</button>
			</div>
			<div class="col-md-1">
				<button type="button" id="btnCancelar" class="btn btn-default btn-md"  ${not empty compra.id and compra.status.name eq 'A' ? '' : 'disabled="disabled"'}>
				  <span class="glyphicon glyphicon-remove"></span> Cancelar
				</button>
			</div>
		</div>
	</form>	
	</div>
	</div>
</div>

