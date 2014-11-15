<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	$(document).ready(function() {
		
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
    	
    	
    
    	
	   	  $('#contaAPagar\\.dataPagamento').datepicker({
	   		language : 'pt-BR',
	   		autoclose : true,
	   		format : 'dd/mm/yyyy'
	   	 	
	   	}); 
    	
    	

	   	
	   	
        
        
        $('#formContaAPagar').validate({
  		   
   		  rules: {  
   			 "contaAPagar.dataPagamento" :  {
   				 
   				dateLessThanToday: true
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
    	
    	
        
        $('.selectpicker').selectpicker({
	           
	    });
    	
        
        $("#contaAPagar\\.juros").change(function(){
        	
        	if($("#contaAPagar\\.juros").val() != ''){
	        	var valorTotal = moeda2float($("#contaAPagar\\.juros").val()) + moeda2float($("#contaAPagar\\.valor").val());
	        	
	        	$("#contaAPagar\\.valorTotal").val(float2moeda(valorTotal));
        		
        	}else{
        		$("#contaAPagar\\.valorTotal").val($("#contaAPagar\\.valor").val());
        	}
        	
        	
        	
        });
        
        $("#contaAPagar\\.juros").priceFormat({
	        prefix: '',
	        centsSeparator: ',',
	        thousandsSeparator: '.',
	        limit: 12
	        
	    });
	
	});
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
								<label for="contaAPagar.despesa.dataVencimento">Vencimento:</label> 
							<input name="" id="contaAPagar.despesa.dataVencimento" value="<fmt:formatDate value="${contaAPagar.parcela.dataVencimento}" type="date" pattern="dd/MM/yyyy"/>" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<div class="col-md-2">
								<label for="contaAPagar.parcela.valor">Valor:</label> 
							<input name="" id="contaAPagar.parcela.valor" value='<fmt:formatNumber value="${contaAPagar.parcela.valor}" minFractionDigits="2" type="currency"/>' class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<c:if test="${not empty contaAPagar.parcela.chequeEmitidoParcela}">
							<div class="col-md-2">
								<label for="bean.id">Número Cheque:</label> 
								<input name="" id="contaAPagar.parcela.chequeEmitido.numeroCheque" value="${contaAPagar.parcela.chequeEmitidoParcela.numeroCheque}" class="form-control "  readonly="readonly" />
									
									
							</div>
							<div class="col-md-2">
								<label for="bean.id">Conta Bancária:</label> 
								<input name="" id="contaAPagar.parcela.chequeEmitido.conta.descricao" value="${contaAPagar.parcela.chequeEmitidoParcela.conta.descricao }" class="form-control "  readonly="readonly" />
									
									
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
				
				<input type="hidden" name="bean.id" value="${contaAPagar.id }"/>


				<div class="row">
					
					</div>
					<div class="row">
					<div class="col-md-2">
						<label for="bean.id">Data Pagamento:</label> 
						<input name="bean.dataStatus" id="bean.dataStatus" value="" class="form-control datepicker required dateLessThanToday"  maxlength="10" />
							
							
					</div>
					
			      	<div class="col-md-2">
		      	
		        		<label for="contaAPagar.valor">Valor:</label>
		        		<input name="contaAPagar.valor"  id="contaAPagar.valor" value='<fmt:formatNumber value="${contaAPagar.valor}" minFractionDigits="2" type="currency"/>' class="form-control" readonly="readonly"/>
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
			        		<input name="contaAPagar.multa"  id="contaAPagar.multa" value="${contaAPagar.multa}" class="form-control"/>
        				</div>
		      			<div class="col-md-2">
				      	
			        		<label for="contaAPagar.juros">Juros:</label>
			        		<input name="contaAPagar.juros"  id="contaAPagar.juros" value="${contaAPagar.juros}" class="form-control"/>
        				</div>
        		</div>
        		<div class="row">
		        	<div class="col-md-2">
			      		<label for="contaAPagar.valorTotal">Valor Total:</label>
		        		<input name="contaAPagar.valorTotal"  id="contaAPagar.valorTotal" value="" class="form-control " readonly="readonly"  />
						
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