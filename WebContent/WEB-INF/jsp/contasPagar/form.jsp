<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	$(document).ready(function() {
		
$("#btnVoltar").click(function(){
			
			window.location.href = "<c:url value='/contasPagar'/>";
			
		});
		
		$("#btnConfirmar").click(function(){
    		//alert("salvar a parada doida!");
    		
    		if($("#formMovimentacao").valid()){
    			
    		
	    		$.ajax({
			        type: 'POST',
			        url: "${pageContext.request.contextPath}/contasPagar/confirmar",
			        data:	$( "#formMovimentacao" ).serialize(),
			 	    success: function(json){
			 	    	$("#divTabela").load( '<c:url value="/contasPagar/loadListaMovimentacao"/>', $('#formCompra').serialize() );
			 	    	$('#myModal').modal('hide');
	
					},
				    error: function(xhr){
				    	alert('erro!');
						    }
			    });
    		}
    	});
    
    	$("#movimentacao\\.dataPagamento").mask("99/99/9999");
    	
    	
    
    	
	   	  $('.datepicker').datepicker({
	   		language : 'pt-BR',
	   		autoclose : true,
	   		format : 'dd/mm/yyyy'
	   	 	
	   	}); 
    	
    	

	   	
	   	
        
        
        $('#formContaAPagar').validate({
  		   
   		 
        
   	      ignore: ':not(select:hidden, input:visible, textarea:visible)',
         	
          errorPlacement: function (error, element) {
                 if ($(element).is('select')) {
                     element.next().after(error); // special placement for select elements
                 } else {
                     error.insertAfter(element);  // default placement for everything else
                 }
             },
             
         
     	});
    	
    	
        
        $('.selectpicker').selectpicker({
	           
	    });
    	
        
        $(".valor").change(function(){
        	
        	var juros = 0.00;
        	var multa = 0.00;
        	var valorTotal = moeda2float($("#contaAPagar\\.valor").val());
        	
        	
        	if($("#contaAPagar\\.juros").val() != '' ){
        		
        		juros = moeda2float($("#contaAPagar\\.juros").val());
        	}
        	
			if($("#contaAPagar\\.multa").val() != '' ){
        		
        		multa = moeda2float($("#contaAPagar\\.multa").val());
        	}
        	
			valorTotal = valorTotal + juros + multa;
			
        	
        	$("#contaAPagar\\.valorTotal").val(float2moeda(valorTotal));
        	
        	
        	
        	
        });
        
        $(".valor").priceFormat({
	        prefix: '',
	        centsSeparator: ',',
	        thousandsSeparator: '.',
	        limit: 12
	        
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
	 	   var retorno = moeda.replace(".","");
	 	  
	 	   retorno = retorno.replace(",",".");

	 	   return parseFloat(retorno);

	 	}
	 function roundNumber (rnum) {

	 	   return Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);

	 	}
</script>

<div class="container">
	<br>
	<h2>Conta a Pagar - Confirmação de Pagamento</h2>
	<br />
	<div class="panel panel-default">
  	<div class="panel-body">
			 
		<c:if test="${contaAPagar.class.name  == 'br.com.transmetais.bean.ContaAPagarDespesa'}" >
		<h4 style="margin-top: 0px">Detalhamento da Despesa</h4>
				<div class="row">
						<div class="col-md-1">
							<label for="bean.adiantamento.id">Cód.:</label> 
						<input name="" id="bean.adiantamento.id" value="${contaAPagar.despesa.id }" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-4">
							<label for="conta.despesa.descricao">Descrição:</label> 
						<input name="" id="conta.despesa.descricao" value="${contaAPagar.despesa.descricao}" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="conta.despesa.valor">Valor:</label> 
						<input name="" id="conta.despesa.valor" value="<fmt:formatNumber value="${contaAPagar.despesa.valor}" minFractionDigits="2" type="currency"/>" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="conta.despesa.centroAplicao.descricao">Centro Aplicação:</label> 
						<input name="" id="conta.despesa.centroAplicao.descricao" value="${contaAPagar.despesa.centroAplicacao.descricao }" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="conta.despesa.contaContabil.descricao">Conta Contábil:</label> 
						<input name="" id="conta.despesa.contaContabil.descricao" value="${contaAPagar.despesa.contaContabil.descricao }" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
				</div>
						<div class="row">
							<div class="col-md-2">
								<label for="conta.despesa.dataCompetencia">Dt Competência:</label> 
							<input name="" id="conta.despesa.dataCompetencia" value="<fmt:formatDate value="${contaAPagar.despesa.dataCompetencia}" type="date" pattern="dd/MM/yyyy"/>" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<div class="col-md-2">
								<label for="conta.despesa.formaPagamento">Forma pagamento:</label> 
							<input name="" id="conta.despesa.formaPagamento" value="${contaAPagar.despesa.formaPagamento.descricao }" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<div class="col-md-2">
								<label for="contaAPagar.despesa.modalidadePagamento.descricao">Mod. pagamento:</label> 
							<input name="" id="contaAPagar.despesa.modalidadePagamento.descricao" value="${contaAPagar.despesa.modalidadePagamento.descricao }" class="form-control "   readonly="readonly"/>
							</div>
							<c:if test="${(empty contaApagar.parcela) && (contaAPagar.modalidadePagamento == 'C')}">
								<div class="col-md-2">
									<label for="contaAPagar.despesa.chequeEmitido.numero">Num. Cheque:</label> 
								<input name="" id="contaAPagar.despesa.chequeEmitido.numero" value="${contaAPagar.despesa.chequeEmitido.numeroCheque }" class="form-control "   readonly="readonly"/>
								</div>
								
								<div class="col-md-4">
								<label for="contaAPagar.despesa.chequeEmitido.conta.descricao">Conta Bancária:</label> 
								<input name="" id="contaAPagar.despesa.chequeEmitido.conta.descricao" value="${contaAPagar.despesa.chequeEmitido.conta.descricao }" class="form-control "  readonly="readonly" />
									
									
							</div>
							</c:if>
							
						</div>
						
						<c:if test="${not empty contaAPagar.parcela}">
						<br/>
		<div class="panel panel-default">
  			<div class="panel-body">
  				<div class="row">
							<div class="col-md-1">
								<label for="conta.despesa.dataCompetencia">Parcela:</label> 
							<input name="" id="conta.despesa.dataCompetencia" value="${contaAPagar.parcela.numero}/${fn:length(contaAPagar.despesa.parcelas)}" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<div class="col-md-2">
								<label for="contaAPagar.parcela.dataVencimento">Vencimento:</label> 
							<input name="" id="contaAPagar.parcela.dataVencimento" value="<fmt:formatDate value="${contaAPagar.parcela.dataVencimento}" type="date" pattern="dd/MM/yyyy"/>" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<div class="col-md-2">
								<label for="contaAPagar.parcela.valor">Valor:</label> 
							<input name="" id="contaAPagar.parcela.valor" value='<fmt:formatNumber value="${contaAPagar.parcela.valor}" minFractionDigits="2" type="currency"/>' class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<c:if test="${not empty contaAPagar.parcela.chequeEmitido}">
							<div class="col-md-2">
								<label for="bean.id">Número Cheque:</label> 
								<input name="" id="contaAPagar.parcela.chequeEmitido.numeroCheque" value="${contaAPagar.parcela.chequeEmitido.numeroCheque}" class="form-control "  readonly="readonly" />
									
									
							</div>
							<div class="col-md-2">
								<label for="contaAPagar.parcela.chequeEmitido.conta.descricao">Conta Bancária:</label> 
								<input name="" id="contaAPagar.parcela.chequeEmitido.conta.descricao" value="${contaAPagar.parcela.chequeEmitido.conta.descricao }" class="form-control "  readonly="readonly" />
									
									
							</div>
							
							</c:if>
							
							
						</div>
  			</div>
  		</div>
			 
		
							
	</c:if>
				
		</c:if>
		<c:if test="${contaAPagar.class.name  == 'br.com.transmetais.bean.ContaAPagarCompra'}" >
		<h4 style="margin-top: 0px">Detalhamento da Compra</h4>
		<div class="row">
						<div class="col-md-1">
							<label for="contaAPagar.compra.id">Cód.:</label> 
						<input name="" id="contaAPagar.compra.id" value="${contaAPagar.compra.id }" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-4">
							<label for="contaAPagar.compra.fornecedor">Fornecedor:</label> 
						<input name="" id="contaAPagar.compra.fornecedor" value="${contaAPagar.compra.fornecedor.apelido} - ${contaAPagar.compra.fornecedor.nome}" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="contaAPagar.compra.valor">Valor:</label> 
						<input name="" id="contaAPagar.compra.valor" value="<fmt:formatNumber value="${contaAPagar.compra.valor}" minFractionDigits="2" type="currency"/>" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="contaAPagar.compra.tipoFrete.descricao">Tipo Frete:</label> 
						<input name="" id="contaAPagar.compra.tipoFrete.descricao" value="${contaAPagar.compra.tipoFrete.descricao }" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="contaAPagar.compra.numNf">Num NF:</label> 
						<input name="" id="contaAPagar.compra.numNf" value="${contaAPagar.compra.numNf }" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
				</div>
						<div class="row">
							<div class="col-md-2">
								<label for="conta.compra.dataCompetencia">Dt Competência:</label> 
							<input name="" id="conta.compra.dataCompetencia" value="<fmt:formatDate value="${contaAPagar.compra.data}" type="date" pattern="dd/MM/yyyy"/>" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<c:if test="${empty  contaAPagar.parcela}">
								<div class="col-md-2">
									<label for="conta.compra.dataVencimento">Dt Vencimento:</label> 
									<input name="" id="conta.compra.dataVencimento" value="<fmt:formatDate value="${contaAPagar.compra.dataVencimento}" type="date" pattern="dd/MM/yyyy"/>" class="form-control "  maxlength="10" readonly="readonly"/>
								</div>
							</c:if>
							
							<div class="col-md-2">
								<label for="contaAPagar.compra.modalidadePagamento.descricao">Mod. pagamento:</label> 
							<input name="" id="contaAPagar.compra.modalidadePagamento.descricao" value="${contaAPagar.compra.modalidadePagamento.descricao }" class="form-control "   readonly="readonly"/>
							</div>
							<c:if test="${(empty contaApagar.parcela) && (contaAPagar.modalidadePagamento == 'C')}">
								<div class="col-md-2">
									<label for="contaAPagar.compra.chequeEmitido.numero">Num. Cheque:</label> 
								<input name="" id="contaAPagar.compra.chequeEmitido.numero" value="${contaAPagar.compra.chequeEmitido.numeroCheque }" class="form-control "   readonly="readonly"/>
								</div>
								
								<div class="col-md-4">
								<label for="contaAPagar.compra.chequeEmitido.conta.descricao">Conta Bancária:</label> 
								<input name="" id="contaAPagar.compra.chequeEmitido.conta.descricao" value="${contaAPagar.compra.chequeEmitido.conta.descricao }" class="form-control "  readonly="readonly" />
									
									
							</div>
							</c:if>
							
						</div>
						<c:if test="${not empty contaAPagar.compra.itens }">
						<br/>
						<div id="divTabelaParcelas">
								<table  class="table table-bordered table-striped" id="tabelaParcelas">
								
								<thead>
							<tr>
								<th >Material</th>
								<th >Preço</th>
								<th >Quantidade</th>
								<th >Valor</th>
								
								
							</tr>
							</thead>
							<tbody>
							<c:forEach var="item" items="${contaAPagar.compra.itens}" >
				
								
								<tr >
								
									<td style="vertical-align: middle;">${item.material.descricao}</td>
									<td style="max-width:110px"><fmt:formatNumber value="${item.preco}" minFractionDigits="2" type="currency"/></td>
									<td style="max-width:110px"><fmt:formatNumber value="${item.quantidade}" minFractionDigits="2" /></td>
									
									<td style="max-width:110px"><fmt:formatNumber value="${item.valor}" minFractionDigits="2" type="currency"/></td>
									
						
								</tr>
							</c:forEach>
							</tbody>
							</table>
							</div>
						
						</c:if>
						<c:if test="${not empty contaAPagar.parcela}">
						<br/>
		<div class="panel panel-default">
  			<div class="panel-body">
  				<div class="row">
							<div class="col-md-1">
								<label for="contaAPagar.parcela.numero">Parcela:</label> 
							<input name="" id="contaAPagar.parcela.numero" value="${contaAPagar.parcela.numero}/${fn:length(contaAPagar.compra.parcelas)}" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<div class="col-md-2">
								<label for="contaAPagar.parcela.dataVencimento">Vencimento:</label> 
							<input name="" id="contaAPagar.parcela.dataVencimento" value="<fmt:formatDate value="${contaAPagar.parcela.dataVencimento}" type="date" pattern="dd/MM/yyyy"/>" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<div class="col-md-2">
								<label for="contaAPagar.parcela.valor">Valor:</label> 
							<input name="" id="contaAPagar.parcela.valor" value='<fmt:formatNumber value="${contaAPagar.parcela.valor}" minFractionDigits="2" type="currency"/>' class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<c:if test="${not empty contaAPagar.parcela.chequeEmitido}">
							<div class="col-md-2">
								<label for="bean.id">Número Cheque:</label> 
								<input name="" id="contaAPagar.parcela.chequeEmitido.numeroCheque" value="${contaAPagar.parcela.chequeEmitido.numeroCheque}" class="form-control "  readonly="readonly" />
									
									
							</div>
							<div class="col-md-2">
								<label for="contaAPagar.parcela.chequeEmitido.conta.descricao">Conta Bancária:</label> 
								<input name="" id="contaAPagar.parcela.chequeEmitido.conta.descricao" value="${contaAPagar.parcela.chequeEmitido.conta.descricao }" class="form-control "  readonly="readonly" />
									
									
							</div>
							
							</c:if>
							
							
						</div>
  			</div>
  		</div>
			 
		
							
	</c:if>
		</c:if>
		
	</div>
	</div>
	
	
	
	<div class="panel panel-default">
		
		<div class="panel-body">
			<form action="<c:url value='/contasPagar/confirmar'/>" id="formContaAPagar"
				name="formContaAPagar" method="post">
				
				<input type="hidden" name="contaAPagar.id" id="contaAPagar.id" value="${contaAPagar.id }"/>


				<div class="row">
					
					</div>
					<div class="row">
					<div class="col-md-2">
						<label for="bean.id">Data Pagamento:</label> 
						<input name="contaAPagar.dataPagamento" id="contaAPagar.dataPagamento" value="${contaAPagar.dataPagamento }" class="form-control datepicker required dateLessThanToday"  maxlength="10" />
							
							
					</div>
					
			      	<div class="col-md-2">
		      	
		        		<label for="contaAPagar.valor">Valor:</label>
		        		<input name="contaAPagar.valor"  id="contaAPagar.valor" value='<fmt:formatNumber value="${contaAPagar.valor}" minFractionDigits="2" type="number"/>' class="form-control" readonly="readonly"/>
		        		
	        		</div>
	        		<c:if test="${contaAPagar.modalidadePagamento eq 'C'}">
	        			</div>
	        		</c:if>
	        		<c:if test="${contaAPagar.modalidadePagamento ne 'C'}">
		        		
							<div class="col-md-4">
				      		<label for="contaSacada">Conta sacada:</label>
			        		<select  id="contaAPagar.conta.id" name="contaAPagar.conta.id" class=" form-control required selectpicker" >
								<option value="" >--Selecione--</option>
								<c:forEach var="conta" items="${contas}">
									<option value="${conta.id }" ${conta.id == contaAPagar.conta.id ? 'selected="selected"' : '' }>${conta.descricao}</option>
								</c:forEach>
							</select>
						
			      		</div>
		      			<div class="col-md-2">
	      	
			        		<label for="contaAPagar.multa">Multa:</label>
			        		<input name="contaAPagar.multa"  id="contaAPagar.multa" value="${contaAPagar.multa}" class="form-control valor"/>
        				</div>
		      			<div class="col-md-2">
				      	
			        		<label for="contaAPagar.juros">Juros:</label>
			        		<input name="contaAPagar.juros"  id="contaAPagar.juros" value="${contaAPagar.juros}" class="form-control valor"/>
        				</div>
        		</div>
        		<div class="row">
		        	<div class="col-md-2">
			      		<label for="contaAPagar.valorTotal">Valor Total:</label>
		        		<input name="contaAPagar.valorTotal"  id="contaAPagar.valorTotal" value="${empty contaAPagar.valorTotal ? contaAPagar.valor : contaAPagar.valorTotal }" class="form-control " readonly="readonly"  />
						
			      	</div>
				</div>
			</c:if>
				
				



				<br />
				<button type="submit" id="btnSalvar" 
					class="btn btn-default btn-md">
					<span class="glyphicon glyphicon-floppy-disk"></span> Confirmar
				</button>
				<button type="button" id="btnVoltar" 
					class="btn btn-default btn-md">
					Voltar
				</button>
			</form>
		</div>
	</div>
</div>