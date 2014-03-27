<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    
    	$("#adiantamento\\.data").mask("99/99/9999");
    	
    	var options = new Array();
	   	 options['language'] = 'pt-BR';
	   	 $('.datepicker').datepicker(options);
    	
    	$("#btnAdicionar").click(function(){
    		$("#formAdiantamento").submit();
    	});
    	
        $('#formAdiantamento').validate({
            
        
    	});
        
        $('.selectpicker').selectpicker({
	            //'selectedText': 'cat'
	        });
        
        $("#adiantamento\\.valor").priceFormat({
            prefix: '',
            centsSeparator: ',',
            thousandsSeparator: '.',
            limit: 12
            
        });
        
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Aprovação de Adiantamentos</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/adiantamento/confirmar'/>" id="formAdiantamento" name="formAdiantamento" method="post">
		
		
		
		<div class="row">
        	<div class="col-md-1">
        		<label for="adiantamento.id">Código:</label>
        		<input name="adiantamento.id" readonly="readonly" id="adiantamento.id" value="<fmt:formatNumber minIntegerDigits="4" value="${adiantamento.id}" groupingUsed="" />" class="form-control " size="8" maxlength="4"/>
        	</div>
        	
        	<div class="col-md-5">
        		<label for="adiantamento.fornecedor.id">Fornecedor:</label>
        		<input name="apelidoNome" readonly="readonly" id="apelidoName" value="${adiantamento.fornecedor.apelido} - ${adiantamento.fornecedor.nome}" class="form-control " />
	        
				
				
        	</div>
        	<div class="col-md-3">
        		<label for="adiantamento.tipoPagamento">Valor:</label>
        		<input name="adiantamento.valor" id="adiantamento.valor" readonly="readonly" value="<fmt:formatNumber value="${adiantamento.valor}" minFractionDigits="2" type="number" />" class="form-control required" />
        	</div>
        	<div class="col-md-2">
        		<label for="adiantamento.data">Data Prevista:</label>
        		<input name="adiantamento.data" readonly="readonly" id="adiantamento.data" value="<fmt:formatDate value="${adiantamento.data}" type="date" pattern="dd/MM/yyyy"/>" class="form-control required" />
        	</div>
        	
      	</div>
      	<div class="row">
	      	<div class="col-md-4">
	      		<label for="adiantamento.tipoPagamento">Forma Pagamento:</label>
        		
				<select  id="adiantamento.tipoPagamento" name="adiantamento.tipoPagamento" class=" form-control required" >
					<option value="" >--Selecione--</option>
					<c:forEach var="tipo" items="${tiposPagamentos}">
						<option value="${tipo.name }" >${tipo.descricao}</option>
					</c:forEach>
				</select>
	      	</div>
	      	<div class="col-md-2">
	      	<c:set var="now" value="<%=new java.util.Date()%>" />
        		<label for="adiantamento.dataPagamento">Data Pagamento:</label>
        		<input name="adiantamento.dataPagamento"  id="adiantamento.dataPagamento" value="<fmt:formatDate value="${now}" type="date" pattern="dd/MM/yyyy"/>" class="form-control required datepicker" data-date-format="dd/mm/yyyy" />
        	</div>
        	<div class="col-md-4">
	      		<label for="contaSacada">Conta sacada:</label>
        		
				<select  id="contaOrigem.id" name="contaOrigem.id" class=" form-control required" >
					<option value="" >--Selecione--</option>
					<c:forEach var="conta" items="${contas}">
						<option value="${conta.id }" >${conta.descricao}</option>
					</c:forEach>
				</select>
	      	</div>
      	</div>
		
      	
      	
      	
      	<br/>
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-floppy-disk"></span> Confirmar
		</button>
		</form>
</div>
</div>
</div>