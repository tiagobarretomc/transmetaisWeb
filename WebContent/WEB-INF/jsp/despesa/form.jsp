<%@page contentType="text/html; charset=UTF-8" isELIgnored ="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#bean\\.dataCompetencia").mask('99/99/9999');
		$("#bean\\.dataVencimento").mask('99/99/9999');
		
		$("#valor").priceFormat({
	        prefix: '',
	        centsSeparator: ',',
	        thousandsSeparator: '.',
	        limit: 12
	        
	    });
		
		var options = new Array();
	   	 options['language'] = 'pt-BR';
	   	//$('.datepicker').datepicker(options);
	   	$(".datepicker").datepicker({
       	 format: "dd/mm/yyyy",
       	 endDate: "-2d"
    	})

    .on('changeDate', function(ev){
    	
        var dateData = new Date(ev.date);  // this is the change
        
        if($('input[name=bean\\.formaPagamento]:checked').val() == 'V'){
			$("#bean\\.dataVencimento").val($("#bean\\.dataCompetencia").val());
		}
        
    });
	   	 
	   	$('.selectpicker').selectpicker({
            //'selectedText': 'cat'
        });
	    $("#btnGerarParcelas").click(function(){
	    	
	    	if ($('#qtdParcelas').val() == '' || $('#qtdParcelas').val()=='0'){
	    		alert('Informe a quantidade de parcelas!');
	    		return;
	    	}else{
	    		$("#divCheque").hide();
	    	}
	    	
	    	
	    	
	    	 if ($('#bean\\.dataVencimento').val() == '') {
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
	    $("#btnAdicionarRegra").click(function(){
	    	var qtdParcelas = $("tr[id^='parcela_']").size();
			adicionarParcela(qtdParcelas);
			$("#qtdParcelas").val($("tr[id^='parcela_']").size());
			$('.datepicker').datepicker(options);
		   	 
		 	$('.selectpicker').selectpicker({
		             //'selectedText': 'cat'
		     });
	    });
	    
	    $("#optPagamentoAVista").click(function(){
	    	$('#bean\\.dataVencimento').attr('disabled', true);
	    	$("#bean\\.conta\\.id").attr('disabled', false);
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
	    
	    $("#optPagamentoAPrazo").click(function(){
	    	$('#bean\\.dataVencimento').attr('disabled', false);
	    	$("#bean\\.conta\\.id").attr('disabled', true);
	    	$("#qtdParcelas").attr('readonly', false);
	    	
	   
	    	
	    	$('#bean\\.conta\\.id').children('option').remove();
	    	
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
	    
	    $("#valor").change(function(){
	    	
	    	var parcela =  float2moeda(roundNumber(moeda2float($(this).val()) / moeda2float($('#qtdParcelas').val())));
	    	
	    	$("input[id^='valorParcela']").each(function(){
	    		 $(this).val(parcela); 
	    		 
	    	});
	    	
	    	
	    });
	   	 
 	   	$('.selectpicker').selectpicker({
             //'selectedText': 'cat'
         });
		
	
 	   $('#formDespesa').validate({
 		   
 		  rules: {  
 			 "bean.conta.id" : {  
 	               required: function(element) {  
 	            	   //Caso a Forma de Pagamento for a vista o campo Conta financeira torna-se obrigatório.
 	            	   return $('input[name=bean\\.formaPagamento]:checked').val() == 'V'; 
 	               }  
 	         	}
 	          },
       	ignore: ':not(select:hidden, input:visible, textarea:visible)',
       	
        errorPlacement: function (error, element) {
               if ($(element).is('select')) {
                   element.next().after(error); // special placement for select elements
               } else {
                   error.insertAfter(element);  // default placement for everything else
               }
           },
           
       
   	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	});
	
	function atualizaModalidades(forma){
		$.ajax({
	        type: 'GET',
	        url: '${pageContext.request.contextPath}/despesa/loadModalidades?_format=json',
	        data:	{tipoPagamento: forma},
	 	    success: function(json){
	 	    	
	 	    	var jsonObject = eval(json);
	 	    	
	 	    	var modalidades = jsonObject.map;
	 	    	
	 	    	
	 	    	var html = "";  
	 	       html += "<select name='bean.modalidadePagamento' id='bean.modalidadePagamento' class=' form-control' onchange='atualizaContas()'>" ;  
	 	      html += "<option value=''>Selecione</option>";
	 	       for(i=0;i<modalidades.length;i++) {  
	 	           html += "<option value='"+modalidades[i][0] +"'>"+modalidades[i][1]+"</option>";                           
	 	       }  
	 	       html += "</select> " ;  
	 	       
	 	       var div = document.getElementById("ajaxResultDiv");  
	 	       div.innerHTML = html; 
	 	      $('.selectpicker').selectpicker();
	 	      
	 	     	$('#bean\\.modalidadePagamento').change(function(){
	 	    		
	     	 	});
	 	        

			},
		    error: function(xhr){
		    	alert('erro!');
				    }
	    });
	}
	
	function atualizaContas(){
		
		if($('input[name=bean\\.formaPagamento]:checked').val() == 'S'){
			
		}
		
		
		if ($('#bean\\.modalidadePagamento').val() == 'C'){
    		$("#divCheque").show();
    		$("#bean\\.conta\\.id").attr('disabled', false);
    	}else{
    		$("#divCheque").hide();
    		$("#bean\\.conta\\.id").empty().append('');
    		$("#bean\\.conta\\.id").val('');
    		
    		$("#bean\\.conta\\.id").attr('disabled', true);
    	}
		
		if($('input[name=bean\\.formaPagamento]:checked').val()) {
    		$.ajax({
	        type: 'GET',
	        url: '${pageContext.request.contextPath}/despesa/loadContas?_format=json',
	        data:	{formaPagamento: $('#bean\\.modalidadePagamento').val(), tipoPagamento: $('input[name=bean\\.formaPagamento]:checked').val()},
	 	    success: function(json){
	 	    	
	 	    	var jsonObject = eval(json);
	 	    	
	 	    	var contas = jsonObject.list;
	 	    	
	 	    	var html = "";  
	 	       html += "<select name='bean.conta.id' id='bean.conta.id' class=' form-control' data-live-search='true'>" ; 
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
		   var retorno = moeda.replace(".","");
		  
		   retorno = retorno.replace(",",".");

		   return parseFloat(retorno);

		}
	function roundNumber (rnum) {

		   return Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);

		}
	
	function adicionaMonth(data, qtdMeses) {  
        var now = new Date();  
        alert('Now is ' + now);  
        var month = now.getMonth();  
        now.setMonth(month + 2);  
        alert(now);  
    }  
	
	function adicionarParcela(i){
		//alert($("#bean\\.valor").val());
		//alert($("#qtdParcelas").val());
		
		var parcela =  float2moeda(roundNumber(moeda2float($('#valor').val()) / moeda2float($('#qtdParcelas').val())));
		//var parcela = "0,00";
		var dataParcela = Date.parseExact($('#bean\\.dataVencimento').val(),'dd/MM/yyyy').addMonths(i);
		strLinha = '<tr id="parcela_' + i + '">';
		strLinha += '<td style="vertical-align: middle;"><span title="Excluir" class="glyphicon glyphicon-remove" onclick="removerParcela(' + i +')"></span></td>';
		
		strLinha += '<td style="max-width:130px"><input name="bean.parcelas[' + i + '].dataVencimento" id="dataParcela' + i + '" data-date-format="dd/mm/yyyy" value="' + dataParcela.toString('dd/MM/yyyy') + '" class="form-control required datepicker" size="8" maxlength="10"/></td>';
		strLinha += '<td style="max-width:130px"><input name="bean.parcelas[' + i + '].valor" id="valorParcela' + i + '" value="' + parcela + '" class="form-control required"  maxlength="18"/></td>';
		if($("#bean\\.modalidadePagamento").val() == 'C'){
			strLinha += '<td style="max-width:130px"><input name="bean.parcelas[' + i + '].chequeEmitido.numeroCheque" id="numChequeParcela' + i + '" value="" class="form-control required"  maxlength="18"/></td>';
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
	
	
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Despesas</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/despesa/add'/>" id="formDespesa" name="formDespesa" method="post">
		<input type="hidden" id="despesa.id" name="bean.id" value="${bean.id}"/>
		
		
		<div class="row">
			<div class="col-md-6">
        		<label for="bean.descricao">Descrição:</label>
        		<input name="bean.descricao" id="bean.descricao" value="${bean.descricao}" class="form-control required"  maxlength="255"/>
        	</div>
        	<div class="col-md-2">
        		<label for="bean.valor">Valor:</label>
        		<input name="bean.valor" id="valor" value="<fmt:formatNumber value="${bean.valor}" minFractionDigits="2" type="currency"/>" class="form-control required"  maxlength="18"/>
        	</div>
        	<div class="col-md-4">
        		<label for="bean.fornecedor.id">Fornecedor:</label>
		        	<select id="bean.fornecedor.id" name="bean.fornecedor.id" class="selectpicker form-control " data-live-search="true">
						<option value ="" >Selecione</option>
						<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
							<option value ="${fornecedor.id}" ${bean.fornecedor.id eq fornecedor.id  ? 'selected' : ''}>${fornecedor.apelido} - ${fornecedor.nome}</option>
						</c:forEach>	
					</select>
        	</div>
        	
        	
        	
        	
        	
      	</div>
		<div class="row">
			<div class="col-md-2"><label for="optPagamentoAVista">Forma Pagamento:</label><br/>
				<input type="radio" name="bean.formaPagamento" value="V" id="optPagamentoAVista" ${bean.formaPagamento.name == 'V' ? 'checked="checked"' : '' }/>&nbsp;À vista&nbsp;
				<input type="radio" name="bean.formaPagamento" value="P" id="optPagamentoAPrazo" ${bean.formaPagamento.name == 'P' ? 'checked="checked"' : '' }/>&nbsp;À prazo&nbsp;</div>
        	
	       <div class="col-md-2">
	        		<label for="bean.modalidadePagamento">Modalidade Pag.:</label>
	        		<div id="ajaxResultDiv">
	        		<select id="bean.modalidadePagamento" name="bean.modalidadePagamento" class=" required form-control" >
							<option value ="">Selecione</option>
							<c:forEach var="modalidade" items="${modalidades}" varStatus="contador">
							
								<option value ="${modalidade.name}" ${bean.modalidadePagamento.name eq modalidade.name ? 'selected' : ''}>${modalidade.descricao}</option>
			
							</c:forEach>	
						</select>
	        		</div>
	        </div>
			<div class="col-md-4">
        		<label for="bean.contaContabil.id">Conta Financeira:</label>
				<div id="divCboContas">
	        		<select id="bean.conta.id" name="bean.conta.id" class=" form-control " >
							<option value ="">Selecione</option>
							<c:forEach var="conta" items="${contasFinanceiras}" varStatus="contador">
							
								<option value ="${conta.id}" ${bean.conta.id eq conta.id ? 'selected' : ''}>${conta.descricao}</option>
			
							</c:forEach>	
					</select>
				</div>
        	</div>
        	<div class="col-md-4">
        	<label for="bean.centroAplicacao.id">Centro de Aplicação:</label>
        		<select id="bean.centroAplicacao.id" name="bean.centroAplicacao.id" class="selectpicker required form-control" data-live-search="true">
						<option value ="">Selecione</option>
						<c:forEach var="centro" items="${centros}" varStatus="contador">
						
							<option value ="${centro.id}" ${bean.centroAplicacao.id eq centro.id ? 'selected' : ''}>${centro.numero} - ${centro.descricao}</option>
		
						</c:forEach>	
					</select>
        	</div>
        	
        	
				
        	
      	</div>
      	<div class="row">
      	<c:if test="${empty bean.parcelas and not empty bean.chequeEmitido }">
      		<div class="col-md-2" id="divCheque" >
      	</c:if>
      	<c:if test="${not empty bean.parcelas or empty bean.chequeEmitido or empty bean.id}">
      		<div class="col-md-2" id="divCheque" style="display:none;">
      	</c:if>
      	<!-- <div class="col-md-2" id="divCheque" style="display:none;"> -->
	      	
        		<label for="">Número Cheque:</label>
        		<input name="bean.chequeEmitidoList[0].numeroCheque"  id="bean.chequeEmitidoList[0].numeroCheque" value="${bean.chequeEmitido.numeroCheque }" class="form-control required " maxlength="15"  />
        </div>
        <div class="col-md-4">
        	<label for="bean.contaContabil.id">Conta Contábil:</label>
        		<select id="bean.contaContabil.id" name="bean.contaContabil.id" class="selectpicker required form-control" data-live-search="true">
						<option value ="">Selecione</option>
						<c:forEach var="conta" items="${contas}" varStatus="contador">
						
							<option value ="${conta.id}" ${bean.contaContabil.id eq conta.id ? 'selected' : ''}>${conta.numero} - ${conta.descricao}</option>
		
						</c:forEach>	
				</select>
        	</div>
      	<div class="col-md-2">
        		<label for="bean.dataCompetencia">Data Competência:</label>
        		<input name="bean.dataCompetencia" id="bean.dataCompetencia" value=" <fmt:formatDate value="${bean.dataCompetencia }" type="date" pattern="dd/MM/yyyy"/>" class="form-control required datepicker" size="8" maxlength="10" />
        	</div>
        	<div class="col-md-2">
        		<label for="bean.dataVencimento">Data Vencimento:</label>
        		<input name="bean.dataVencimento" id="bean.dataVencimento"  value="<fmt:formatDate value="${bean.dataVencimento }" type="date" pattern="dd/MM/yyyy"/>" class="form-control required datepicker" size="8" maxlength="10" />
        	</div>
      	<div class="col-md-2">
      			<label for="qtdParcelas">Qtd.Parcelas:</label>
        		<input id="qtdParcelas" value="${fn:length(bean.parcelas)}" size="10" class="form-control "/>
        	</div>
        	
        	
      	</div>
      	<div class="row">
      		<div class="col-md-2"><br/>
      			<button type="button" id="btnGerarParcelas" class="btn btn-default btn-md" style="margin-top: 4px;">
				  Gerar Parcelas
				</button>
      	</div>
      	</div>
      	
      	
        <br/>
        <div class="panel panel-default" id="divPainelParcelas">
        	<div class="panel-body">
        	<h4>Parcelas</h4>
				<button type="button" id="btnAdicionarRegra" class="btn btn-default btn-md">
				  <span class="glyphicon glyphicon-plus-sign"></span> Adicionar
				</button>
				<br/>
				<br/>
				<div id="divTabela">
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
			<c:forEach var="parcela" items="${bean.parcelas}" varStatus="contador">

				
				<tr id="parcela_${contador.index }">
				
					<td style="vertical-align: middle;"><a href="#" onclick="removerParcela(${contador.index}); return false;"><span title="Excluir" class="glyphicon glyphicon-remove" ></span></a></td>
					<td style="max-width:130px"><input name="bean.parcelas[${contador.index}].dataVencimento" id="dataParcela${contador.index}" data-date-format="dd/mm/yyyy" value="<fmt:formatDate value="${parcela.dataVencimento }" type="date" pattern="dd/MM/yyyy"/>" class="form-control required datepicker" size="8" maxlength="10"/></td>
					<td style="max-width:130px"><input name="bean.parcelas[${contador.index}].valor" id="valorParcela${contador.index}" value="<fmt:formatNumber value="${parcela.valor}" minFractionDigits="2" type="currency"/>" class="form-control required"  maxlength="18"/></td>
					<c:if test="${not empty parcela.chequeEmitido }">
						<td style="max-width:130px"><input name="bean.parcelas[${contador.index}].chequeEmitido.numeroCheque" id="numChequeParcela${contador.index}" value="${parcela.chequeEmitido.numeroCheque}" class="form-control required"  maxlength="18"/></td>
					</c:if>
					<c:if test="${ empty parcela.chequeEmitido }">
					<td style="max-width:130px"></td>
					</c:if>
			
		
				</tr>
			</c:forEach>
			</tbody>
			</table>
			</div>
        	</div>
        </div>
      	<br/>
      	
      	
      	<br/>
		<button type="submit" id="btnAdicionar" class="btn btn-default btn-md" >
		  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
		</button>
		</form>
</div>
</div>
</div>