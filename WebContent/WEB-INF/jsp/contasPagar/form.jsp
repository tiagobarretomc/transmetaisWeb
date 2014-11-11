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
    	
    	

	   	
	   	
        
        
        $('#formMovimentacao').validate({
  		   
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
				
		</c:if>
		
	</div>
	</div>
	
	<c:if test="${not empty contaAPagar.parcela}">
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
							
							
						</div>
  			</div>
  		</div>
			 
		
							
	</c:if>
	
	<div class="panel panel-default">
		
		<div class="panel-body">
			<form action="<c:url value='/chequeEmitido/aprovar'/>" id="formCheque"
				name="formCheque" method="post">
				
				<input type="hidden" name="bean.id" value="${contaAPagar.id }"/>


				<div class="row">
				<div class="col-md-2">
						<label for="bean.id">Número Cheque:</label> 
						<input name="" id="bean.numeroCheque" value="" class="form-control "  readonly="readonly" />
							
							
					</div>
					<div class="col-md-2">
						<label for="bean.id">Conta Bancária:</label> 
						<input name="" id="bean.conta.descricao" value="" class="form-control "  readonly="readonly" />
							
							
					</div>
					<div class="col-md-2">
						<label for="bean.id">Data Emissão:</label> 
						<input name="" id="bean.data" value='' class="form-control "  readonly="readonly" />
							
							
					</div>
					
					<div class="col-md-2">
						<label for="bean.id">Valor do Cheque:</label> 
						<input name="" id="bean.valor" value="<fmt:formatNumber value="" minFractionDigits="2" type="currency" />" class="form-control "  readonly="readonly" />
							
							
					</div>
					</div>
					<div class="row">
					<div class="col-md-2">
						<label for="bean.id">Data Compensação:</label> 
						<input name="bean.dataStatus" id="bean.dataStatus" value="" class="form-control datepicker required dateLessThanToday"  maxlength="10" />
							
							
					</div>

					
					
					

				</div>

				
				



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