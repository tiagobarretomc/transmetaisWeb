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
	    $("#qtdParcelas").blur(function(){
	    	var qtdParcelas = $('#qtdParcelas').val();
	    	var strLinha;
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
	    	
	    	atualizaModalidades($(this).val());
	    	
	    });
	    
	    $("#optPagamentoAPrazo").click(function(){
	    	$('#bean\\.dataVencimento').attr('disabled', false);
	    	$("#bean\\.conta\\.id").attr('disabled', true);
	    	$("#qtdParcelas").attr('readonly', false);
	    	
	   
	    	
	    	$('#bean\\.conta\\.id').children('option').remove();
	    	
	    	atualizaModalidades($(this).val());
	    	
	    });
	    
	    
	   	 
 	   	$('.selectpicker').selectpicker({
             //'selectedText': 'cat'
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
	 	       html += "<select name='bean.modalidadePagamento' id='bean.modalidadePagamento' class='selectpicker form-control' onchange='atualizaContas()'>" ;  
	 	      html += "<option value=''>Selecione</option>";
	 	       for(i=0;i<modalidades.length;i++) {  
	 	           html += "<option value='"+modalidades[i][0] +"'>"+modalidades[i][1]+"</option>";                           
	 	       }  
	 	       html += "</select> " ;  
	 	       alert(html);
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
	
	$('#bean\\.modalidadePagamento').change(function(){
    	
        
        		
    		//alert($('input[name=formaPagamento]:checked', '#formDespesa').val());
    		//alert('teste');
    	
    	
       
    });
	
	/* $("#bean\\.dataCompetencia").change(function(){
		
		alert($("#bean\\.dataCompetencia"));
		if($('input[name=formaPagamento]:checked').val() == 'V'){
			$("#bean\\.dataVencimento").val($("#bean\\.dataCompetencia").val());
		}
	}); */
	
	/* $(".datepicker").on('change', function(ev){
		
		alert('funcionou');
	    var dateData = $(this).val();
	    window.location.href = "?day=" + dateData ;
	}); */
	
	

    
	
	
	
	
	
	function atualizaContas(){
		
		
		if($('input[name=bean\\.formaPagamento]:checked').val()) {
    		$.ajax({
	        type: 'GET',
	        url: '${pageContext.request.contextPath}/despesa/loadContas?_format=json',
	        data:	{formaPagamento: $('#bean\\.modalidadePagamento').val(), tipoPagamento: $('input[name=bean\\.formaPagamento]:checked').val()},
	 	    success: function(json){
	 	    	
	 	    	var jsonObject = eval(json);
	 	    	
	 	    	var contas = jsonObject.list;
	 	    	
	 	    	var html = "";  
	 	       html += "<select name='bean.conta.id' id='bean.conta.id' class='selectpicker form-control' data-live-search='true'>" ; 
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
	
	function adicionarParcela(i){
		strLinha = '<tr id="parcela_' + i + '">';
		strLinha += '<td style="vertical-align: middle;"><span title="Excluir" class="glyphicon glyphicon-remove" onclick="removerParcela(' + i +')"></span></td>';
		strLinha += '<td style="max-width:130px"><input name="bean.parcelas[' + i + '].dataVencimento" id="dataParcela' + i + '" data-date-format="dd/mm/yyyy" value="${parcelas[' + i + '].data}" class="form-control required datepicker" size="8" maxlength="8"/></td>';
		strLinha += '<td style="max-width:130px"><input name="bean.parcelas[' + i + '].valor" id="valorParcela' + i + '" value="<fmt:formatNumber value="${parcelas[' + i + '].valor}" minFractionDigits="2" type="currency"/>" class="form-control required"  maxlength="18"/></td>';
		strLinha += '</tr>';
		$('#tabelaParcelas > tbody:last').append(strLinha);
		$("#dataParcela" + i).mask('99/99/9999');
		$("#valorParcela" + i).priceFormat({
	        prefix: '',
	        centsSeparator: ',',
	        thousandsSeparator: '.',
	        limit: 12
	        
	    });
		
	}
	function removerParcela(id){
    	$("#parcela_" + id).remove();
    	$("#qtdParcelas").val($("tr[id^='parcela_']").size());
    }
	
	//$('.datepicker').datepicker(options);
	        
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Despesas</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/despesa/add'/>" id="formDespesa" name="formProduto" method="post">
		<input type="hidden" id="despesa.id" name="bean.id" value="${bean.id}"/>
		
		
		<div class="row">
			<div class="col-md-6">
        		<label for="bean.descricao">Descrição:</label>
        		<input name="bean.descricao" id="bean.descricao" value="${bean.descricao}" class="form-control required"  maxlength="45"/>
        	</div>
        	<div class="col-md-2">
        		<label for="bean.valor">Valor:</label>
        		<input name="bean.valor" id="valor" value="<fmt:formatNumber value="${bean.valor}" minFractionDigits="2" type="currency"/>" class="form-control required"  maxlength="18"/>
        	</div>
        	
        	
        	<div class="col-md-2"><label for="optPagamentoAVista">Forma Pagamento:</label><br/>
				<input type="radio" name="bean.formaPagamento" value="V" id="optPagamentoAVista"/>&nbsp;À vista&nbsp;
				<input type="radio" name="bean.formaPagamento" value="P" id="optPagamentoAPrazo"/>&nbsp;À prazo&nbsp;</div>
        	
	       <div class="col-md-2">
	        		<label for="bean.modalidadePagamento">Modalidade Pag.:</label>
	        		<div id="ajaxResultDiv">
	        		<select id="bean.modalidadePagamento" name="bean.modalidadePagamento" class="selectpicker required form-control" >
							<option value ="">Selecione</option>
							<c:forEach var="modalidade" items="${modalidades}" varStatus="contador">
							
								<option value ="${modalidade.id}" ${bean.modalidade.id eq modalidade.id ? 'selected' : ''}>${centro.numero} - ${centro.descricao}</option>
			
							</c:forEach>	
						</select>
	        		</div>
	        </div>
        	
        	
      	</div>
		<div class="row">
			<div class="col-md-4">
        		<label for="bean.contaContabil.id">Conta Financeira:</label>
				<div id="divCboContas">
	        		<select id="bean.conta.id" name="bean.conta.id" class=" required form-control selectpicker" >
							<option value ="">Selecione</option>
							<c:forEach var="conta" items="${contasFinanceiras}" varStatus="contador">
							
								<option value ="${conta.id}" ${bean.conta.id eq conta.id ? 'selected' : ''}>${conta.numero} - ${conta.descricao}</option>
			
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
        	<div class="col-md-4">
        	<label for="bean.contaContabil.id">Conta Contábil:</label>
        		<select id="bean.contaContabil.id" name="bean.contaContabil.id" class="selectpicker required form-control" data-live-search="true">
						<option value ="">Selecione</option>
						<c:forEach var="conta" items="${contas}" varStatus="contador">
						
							<option value ="${conta.id}" ${bean.contaContabil.id eq conta.id ? 'selected' : ''}>${conta.numero} - ${conta.descricao}</option>
		
						</c:forEach>	
				</select>
        	</div>
        	
				
        	
      	</div>
      	<div class="row">
      	<div class="col-md-2">
        		<label for="bean.dataCompetencia">Data Competência:</label>
        		<input name="bean.dataCompetencia" id="bean.dataCompetencia" value="${bean.dataCompetencia}" class="form-control required datepicker" size="8" maxlength="8"/>
        	</div>
        	<div class="col-md-2">
        		<label for="bean.dataVencimento">Data Vencimento:</label>
        		<input name="bean.dataVencimento" id="bean.dataVencimento"  value="${bean.dataVencimento}" class="form-control required datepicker" size="8" maxlength="8"/>
        	</div>
      	<div class="col-md-2">
      			<label for="qtdParcelas">Qtd.Parcelas:</label>
        		<input id="qtdParcelas" value="${quantidadeParcelas}" size="10" class="form-control "/>
        	</div>
      	</div>
      	
        <br/>
        <div class="panel panel-default" >
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
				
				
			</tr>
			</thead>
			<tbody>
			</tbody>
			</table>
			</div>
        	</div>
        </div>
      	<br/>
      	
      	
      	<br/>
		<button type="submit" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
		</button>
		</form>
</div>
</div>
</div>