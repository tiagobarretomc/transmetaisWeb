<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    
    	$("#movimentacao\\.dataPagamento").mask("99/99/9999");
    	
    	var options = new Array();
	   	 options['language'] = 'pt-BR';
	   	 $('.datepicker').datepicker(options);
    	
    	$("#btnAprovar").click(function(){
    		$("#formMovimentacao").submit();
    	});
    	
        $('#formMovimentacao').validate({
            
        
    	});
        
        $('.selectpicker').selectpicker({
	            //'selectedText': 'cat'
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
        		<label for="movimentacao.valor">Valor:</label>
        		<input name="movimentacao.valor" id="movimentacao.valor" readonly="readonly" value="<fmt:formatNumber value="${movimentacao.valor}" minFractionDigits="2" type="number" />" class="form-control required" />
        	</div>
        	<div class="col-md-2">
        		<label for="movimentacao.data">Data Prevista:</label>
        		<input name="movimentacao.data" readonly="readonly" id="movimentacao.data" value="<fmt:formatDate value="${movimentacao.data}" type="date" pattern="dd/MM/yyyy"/>"  class="form-control required" />
        	</div>
        	
      	</div>
      	<div class="row">
	      
	      	<div class="col-md-2">
	      	<c:set var="now" value="<%=new java.util.Date()%>" />
        		<label for="movimentacao.dataPagamento">Data Pagamento:</label>
        		<input name="movimentacao.dataPagamento"  id="movimentacao.dataPagamento" value="<fmt:formatDate value="${now}" type="date" pattern="dd/MM/yyyy"/>" class="form-control required datepicker" data-date-format="dd/mm/yyyy" />
        	</div>
        	<c:if test="${movimentacao.tipoOperacao == 'D' }">
	        	<div class="col-md-4">
		      		<label for="contaSacada">Conta sacada:</label>
	        		
					<select  id="contaOrigem.id" name="contaOrigem.id" class=" form-control required" >
						<option value="" >--Selecione--</option>
						<c:forEach var="conta" items="${contas}">
							<option value="${conta.id }" ${conta.id == movimentacao.conta.id ? 'selected="selected"' : '' }>${conta.descricao}</option>
						</c:forEach>
					</select>
		      	</div>
        	</c:if>
        	<c:if test="${movimentacao.tipoOperacao.name == 'C' and  movimentacao.class.name == 'br.com.transmetais.bean.MovimentacaoAdiantamento'}">
        		<div class="col-md-4">
		      		<label for="contaSacada">Conta Destino:</label>
	        		
					<input type="text" readonly="readonly" value="${movimentacao.adiantamento.fornecedor.conta.descricao }" class="form-control "/>
		      	</div>
        	</c:if>
      	</div>
		
      	
      	
      	
      	<br/>
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-floppy-disk"></span> Confirmar
		</button>
		</form>
</div>
</div>
</div>