<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	
    	
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
    	
    	
    });
</script>

    <div class="container">
    <%--
    <br>
    
    <c:if test="${movimentacao.class.name  == 'br.com.transmetais.bean.MovimentacaoCompra'}">
	<h2>Confirmação de Pagamento de Compra</h2>
	</c:if>
	<c:if test="${movimentacao.class.name  == 'br.com.transmetais.bean.MovimentacaoAdiantamento'}">
	<h2>Confirmação de Pagamento de Adiantamento</h2>
	</c:if>
	<c:if test="${movimentacao.class.name  == 'br.com.transmetais.bean.MovimentacaoDespesa'}">
	<h2>Confirmação de Pagamento de Despesa</h2>
	</c:if>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
     --%>
	<form action="<c:url value='/adiantamento/confirmar'/>" id="formMovimentacao" name="formMovimentacao" method="post">
		<input type="hidden" name="contaAPagar.id" id="contaAPagar.id" value="${contaAPagar.id}"/>
		<div class="row">
		<%--
			<div class="col-md-12">
			<c:if test="${movimentacao.class.name  == 'br.com.transmetais.bean.MovimentacaoCompra'}">
				<a href="${pageContext.request.contextPath}/compra/${movimentacao.compra.id}"><b>Compra <fmt:formatNumber minIntegerDigits="4" value="${movimentacao.compra.id}" groupingUsed="" /></b> Fornecedor: ${movimentacao.compra.fornecedor.apelido } - ${movimentacao.compra.fornecedor.nome } </a>
			</c:if>
			<c:if test="${movimentacao.class.name  == 'br.com.transmetais.bean.MovimentacaoAdiantamento'}">
				<a href="${pageContext.request.contextPath}/adiantamento/${movimentacao.adiantamento.id}"><b>Adiantamento <fmt:formatNumber minIntegerDigits="4" value="${movimentacao.adiantamento.id}" groupingUsed="" /></b> Fornecedor: ${movimentacao.adiantamento.fornecedor.apelido } - ${movimentacao.adiantamento.fornecedor.nome } </a>
			</c:if>
			<c:if test="${movimentacao.class.name  == 'br.com.transmetais.bean.MovimentacaoDespesa'}">
				<a href="${pageContext.request.contextPath}/despesa/${movimentacao.adiantamento.id}"><b>Despesa <fmt:formatNumber minIntegerDigits="4" value="${movimentacao.despesa.id}" groupingUsed="" /></b> ${movimentacao.despesa.descricao } </a>
			</c:if>
				
			</div>
		 --%>
		</div>
		
		<div class="row">
        	
        	
        	<div class="col-md-3">
        		<label for="contaAPagar.valor">Valor:</label>
        		<input name="contaAPagar.valor" id="contaAPagar.valor" readonly="readonly" value="<fmt:formatNumber value="${contaAPagar.valor}" minFractionDigits="2" type="number" />" class="form-control required" />
        	</div>
        	<div class="col-md-2">
        		<label for="contaAPagar.dataPrevista">Data Prevista:</label>
        		<input name="contaAPagar.dataPrevista" readonly="readonly" id="contaAPagar.dataPrevista" value="<fmt:formatDate value="${contaAPagar.dataPrevista}" type="date" pattern="dd/MM/yyyy"/>"  class="form-control required" />
        	</div>
        	
      	</div>
      	<div class="row">
	      
	      	<div class="col-md-2">
	      	<c:set var="now" value="<%=new java.util.Date()%>" />
        		<label for="contaAPagar.dataPagamento">Data Pagamento:</label>
        		<input name="contaAPagar.dataPagamento"  id="contaAPagar.dataPagamento" value="<fmt:formatDate value="${now}" type="date" pattern="dd/MM/yyyy"/>" class="form-control dateLessThanToday required datepicker" data-date-format="dd/mm/yyyy" />
        	</div>
        	
	        	<div class="col-md-4">
		      		<label for="contaSacada">Conta sacada:</label>
	        		
	        		<c:if test="${ contaAPagar.class.name == 'br.com.transmetais.bean.ContaAPagarCompra' and contaAPagar.compra.fornecedor.tipoFaturamento.name == 'ADIANT'}">
		        		<select  id="contaAPagar.conta.id" name="contaAPagar.conta.id"  class=" form-control required selectpicker">
							<option value="${ contaAPagar.compra.fornecedor.conta.id}" >${ contaAPagar.compra.fornecedor.conta.descricao}</option>
							
						</select>
	        		</c:if>
	        		
	        		<c:if test="${ contaAPagar.class.name != 'br.com.transmetais.bean.ContaAPagarCompra' or contaAPagar.compra.fornecedor.tipoFaturamento.name != 'ADIANT'}">
					<select  id="contaAPagar.conta.id" name="contaAPagar.conta.id" class=" form-control required selectpicker" >
						<option value="" >--Selecione--</option>
						<c:forEach var="conta" items="${contas}">
							<option value="${conta.id }" ${conta.id == contaAPagar.conta.id ? 'selected="selected"' : '' }>${conta.descricao}</option>
						</c:forEach>
					</select>
					</c:if>
		      	</div>
        	
      	</div>
        	<c:if test="${ contaAPagar.class.name == 'br.com.transmetais.bean.ContaAPagarAdiantamento'}">
	        	<div class="row">
	        		<div class="col-md-4">
			      		<label for="contaSacada">Conta Destino:</label>
		        		
						<input type="text" readonly="readonly" value="${contaAPagar.adiantamento.fornecedor.conta.descricao }" class="form-control "/>
			      	</div>
			     </div>
        	</c:if>
		
      	
      	
      	
      	<br/>
		
		</form>
</div>

