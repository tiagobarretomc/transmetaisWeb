<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
   
    	
    	var options = new Array();
	   	 options['language'] = 'pt-BR';
	   	 $('.datepicker').datepicker(options);
    	
    	$("#btnAdicionar").click(function(){
    		$("#formConta").submit();
    	});
    	
        $('#formConta').validate({
            
        
    	});
        
        $('.selectpicker').selectpicker({
	            //'selectedText': 'cat'
	        });
        
        $("conta\\.saldoInicial").priceFormat({
            prefix: '',
            centsSeparator: ',',
            thousandsSeparator: '.',
            limit: 12
            
        });
        
        
    	
    	
    });
</script>

    <div class="container">
    <br>
<h2>Cadastro de Contas Bancárias e não Bancárias</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/contaBancaria/add'/>" id="formConta" name="formConta" method="post">
		
		
		
		<div class="row">
        	<div class="col-md-1">
        		<label for="conta.id">Código:</label>
        		<input name="conta.id" readonly="readonly" id="conta.id" value="<fmt:formatNumber minIntegerDigits="4" value="${conta.id}" groupingUsed="" />" class="form-control " size="8" maxlength="4"/>
        	</div>
        	
        	<div class="col-md-2">
        		<label for="conta.bancaria">Conta Bancária:</label>
        		<input type="checkbox" name="conta.bancaria" id="conta.bancaria" ${conta.bancaria  ? 'checked="checked"': '' }  class="form-control"  maxlength="100"/>
        	</div>
        	
      	</div>
		<div class="row">
			<div class="col-md-4">
        		<label for="conta.banco">Banco:</label>
        		<input name="conta.banco" id="conta.banco" value="${conta.banco}" class="form-control "  maxlength="100"/>
        	</div>
        	<div class="col-md-2">
        		<label for="conta.agencia">Agência:</label>
        		<input name="conta.agencia" id="conta.agencia" value="${conta.agencia}" class="form-control "  maxlength="6"/>
        	</div>
        	<div class="col-md-3">
        		<label for="conta.contaCorrente">Conta Corrente:</label>
        		<input name="conta.contaCorrente" id="conta.contaCorrente" value="${conta.contaCorrente}" class="form-control "  maxlength="18"/>
        	</div>
        	<div class="col-md-3">
        		<label for="conta.saldoInicial">Saldo Inicial:</label>
        		<input name="conta.saldoInicial" id="conta.saldoInicial" value="<fmt:formatNumber value="${conta.saldoInicial}" minFractionDigits="2" type="number" />" class="form-control "  maxlength="18"/>
        	</div>
        	
        	
        	
		</div>
		
      	
      	
      	
      	<br/>
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
		</button>
		</form>
</div>
</div>
</div>