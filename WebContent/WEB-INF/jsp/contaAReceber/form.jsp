<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	$(document).ready(function() {
		
$("#btnVoltar").click(function(){
			
			window.location.href = "<c:url value='/contaAReceber'/>";
			
		});
		
		$("#btnConfirmar").click(function(){
    		//alert("salvar a parada doida!");
    		
    		if($("#formMovimentacao").valid()){
    			
    		
	    		$.ajax({
			        type: 'POST',
			        url: "${pageContext.request.contextPath}/contaAReceber/confirmar",
			        data:	$( "#formMovimentacao" ).serialize(),
			 	    success: function(json){
			 	    	$("#divTabela").load( '<c:url value="/contaAReceber/loadListaMovimentacao"/>', $('#formCompra').serialize() );
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
    	
    	

	   	
	   	
        
        
        $('#formContaAReceber').validate({
  		   
   		 
        
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
        	var valorTotal = moeda2float($("#contaAReceber\\.valor").val());
        	
        	alert('teste');
        	
        	if($("#contaAReceber\\.juros").val() != '' ){
        		
        		juros = moeda2float($("#contaAReceber\\.juros").val());
        	}
        	
			if($("#contaAReceber\\.multa").val() != '' ){
        		
        		multa = moeda2float($("#contaAReceber\\.multa").val());
        	}
        	
			valorTotal = valorTotal + juros + multa;
			
        	
        	$("#contaAReceber\\.valorTotal").val(float2moeda(valorTotal));
        	
        	
        	
        	
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
	<h2>Conta a Receber - Confirmação de Recebimento</h2>
	<br />
	<div class="panel panel-default">
  	<div class="panel-body">
			 
		
				
		
		<c:if test="${contaAReceber.class.name  == 'br.com.transmetais.bean.ContaAReceberVenda'}" >
		<h4 style="margin-top: 0px">Detalhamento da Compra</h4>
		<div class="row">
						<div class="col-md-1">
							<label for="contaAReceber.venda.id">Cód.:</label> 
						<input name="" id="contaAReceber.venda.id" value="${contaAReceber.venda.id }" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-4">
							<label for="contaAReceber.venda.cliente">cliente:</label> 
						<input name="" id="contaAReceber.venda.cliente" value="${contaAReceber.venda.cliente.razaoSocial}" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="contaAReceber.venda.valor">Valor:</label> 
						<input name="" id="contaAReceber.venda.valor" value="<fmt:formatNumber value="${contaAReceber.venda.valor}" minFractionDigits="2" type="currency"/>" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="contaAReceber.venda.tipoFrete.descricao">Tipo Frete:</label> 
						<input name="" id="contaAReceber.venda.tipoFrete.descricao" value="${contaAReceber.venda.tipoFrete.descricao }" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="contaAReceber.venda.numNf">Num NF:</label> 
						<input name="" id="contaAReceber.venda.numNf" value="${contaAReceber.venda.numNf }" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
				</div>
						<div class="row">
							<div class="col-md-2">
								<label for="contaAReceber.venda.data">Dt Competência:</label> 
							<input name="" id="contaAReceber.venda.data" value="<fmt:formatDate value="${contaAReceber.venda.data}" type="date" pattern="dd/MM/yyyy"/>" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<c:if test="${empty  contaAReceber.parcela}">
								<div class="col-md-2">
									<label for="contaAReceber.venda.dataVencimento">Dt Vencimento:</label> 
									<input name="" id="contaAReceber.venda.dataVencimento" value="<fmt:formatDate value="${contaAReceber.venda.dataVencimento}" type="date" pattern="dd/MM/yyyy"/>" class="form-control "  maxlength="10" readonly="readonly"/>
								</div>
							</c:if>
							
							<div class="col-md-2">
								<label for="contaAReceber.venda.modalidadePagamento.descricao">Mod. pagamento:</label> 
							<input name="" id="contaAReceber.venda.descricao" value="${contaAReceber.venda.modalidadePagamento.descricao }" class="form-control "   readonly="readonly"/>
							</div>
							<%-- <c:if test="${(empty contaApagar.parcela) && (contaAPagar.modalidadePagamento == 'C')}">
								<div class="col-md-2">
									<label for="contaAPagar.compra.chequeEmitido.numero">Num. Cheque:</label> 
								<input name="" id="contaAPagar.compra.chequeEmitido.numero" value="${contaAPagar.compra.chequeEmitido.numeroCheque }" class="form-control "   readonly="readonly"/>
								</div>
								
								<div class="col-md-4">
								<label for="contaAPagar.compra.chequeEmitido.conta.descricao">Conta Bancária:</label> 
								<input name="" id="contaAPagar.compra.chequeEmitido.conta.descricao" value="${contaAPagar.compra.chequeEmitido.conta.descricao }" class="form-control "  readonly="readonly" />
									
									
							</div> 
							</c:if>
							--%>
							
						</div>
						<c:if test="${not empty contaAReceber.venda.itens }">
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
							<c:forEach var="item" items="${contaAReceber.venda.itens}" >
				
								
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
						<c:if test="${not empty contaAReceber.parcela}">
						<br/>
		<div class="panel panel-default">
  			<div class="panel-body">
  				<div class="row">
							<div class="col-md-1">
								<label for="contaAReceber.parcela.numero">Parcela:</label> 
							<input name="" id="contaAReceber.parcela.numero" value="${contaAReceber.parcela.numero}/${fn:length(contaAReceber.venda.parcelas)}" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<div class="col-md-2">
								<label for="contaAReceber.parcela.dataVencimento">Vencimento:</label> 
							<input name="" id="contaAReceber.parcela.dataVencimento" value="<fmt:formatDate value="${contaAReceber.parcela.dataVencimento}" type="date" pattern="dd/MM/yyyy"/>" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<div class="col-md-2">
								<label for="contaAReceber.parcela.valor">Valor:</label> 
							<input name="" id="contaAReceber.parcela.valor" value='<fmt:formatNumber value="${contaAReceber.parcela.valor}" minFractionDigits="2" type="currency"/>' class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							<%-- <c:if test="${not empty contaAPagar.parcela.chequeEmitido}">
							<div class="col-md-2">
								<label for="bean.id">Número Cheque:</label> 
								<input name="" id="contaAPagar.parcela.chequeEmitido.numeroCheque" value="${contaAPagar.parcela.chequeEmitido.numeroCheque}" class="form-control "  readonly="readonly" />
									
									
							</div>
							<div class="col-md-2">
								<label for="contaAPagar.parcela.chequeEmitido.conta.descricao">Conta Bancária:</label> 
								<input name="" id="contaAPagar.parcela.chequeEmitido.conta.descricao" value="${contaAPagar.parcela.chequeEmitido.conta.descricao }" class="form-control "  readonly="readonly" />
									
									
							</div>
							
							</c:if> --%>
							
							
						</div>
  			</div>
  		</div>
			 
		
							
	</c:if>
		</c:if>
		
	</div>
	</div>
	
	
	
	<div class="panel panel-default">
		
		<div class="panel-body">
			<form action="<c:url value='/contaAReceber/confirmar'/>" id="formContaAReceber"
				name="formContaAReceber" method="post">
				
				<input type="hidden" name="contaAReceber.id" id="contaAReceber.id" value="${contaAReceber.id }"/>


				<div class="row">
					
					</div>
					<div class="row">
					<div class="col-md-2">
						<label for="bean.id">Data Pagamento:</label> 
						<input name="contaAReceber.dataPagamento" id="contaAReceber.dataPagamento" value="${contaAReceber.dataPagamento }" class="form-control datepicker required dateLessThanToday"  maxlength="10" />
							
							
					</div>
					
			      	<div class="col-md-2">
		      	
		        		<label for="contaAReceber.valor">Valor:</label>
		        		<input name="contaAReceber.valor"  id="contaAReceber.valor" value='<fmt:formatNumber value="${contaAReceber.valor}" minFractionDigits="2" type="number"/>' class="form-control valor" />
		        		
	        		</div>
	        		<c:if test="${contaAReceber.modalidadePagamento eq 'C'}">
	        			</div>
	        		</c:if>
	        		<c:if test="${contaAReceber.modalidadePagamento ne 'C'}">
		        		
							<div class="col-md-4">
				      		<label for="contaSacada">Conta sacada:</label>
			        		<select  id="contaAReceber.conta.id" name="contaAReceber.conta.id" class=" form-control required selectpicker" >
								<option value="" >--Selecione--</option>
								<c:forEach var="conta" items="${contas}">
									<option value="${conta.id }" ${conta.id == contaAReceber.conta.id ? 'selected="selected"' : '' }>${conta.descricao}</option>
								</c:forEach>
							</select>
						
			      		</div>
		      			<div class="col-md-2">
	      	
			        		<label for="contaAReceber.multa">Multa:</label>
			        		<input name="contaAReceber.multa"  id="contaAReceber.multa" value="${contaAReceber.multa}" class="form-control valor"/>
        				</div>
		      			<div class="col-md-2">
				      	
			        		<label for="contaAReceber.juros">Juros:</label>
			        		<input name="contaAReceber.juros"  id="contaAReceber.juros" value="${contaAReceber.juros}" class="form-control valor"/>
        				</div>
        		</div>
        		<div class="row">
		        	<div class="col-md-2">
			      		<label for="contaAReceber.valorTotal">Valor Total:</label>
		        		<input name="contaAReceber.valorTotal"  id="contaAReceber.valorTotal" value="${empty contaAReceber.valorTotal ? contaAReceber.valor : contaAReceber.valorTotal }" class="form-control valor" readonly="readonly"  />
						
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