<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#bean\\.data").mask('99/99/9999');
		
		
		$("#bean\\.valor").priceFormat({
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
    	
    	$("#btnAdicionar").click(function(){
    		$("#formTransferencia").submit();
    	});
    	
        $('#formTransferencia').validate({
            
        
    	});
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Transferência entre contas</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/transferencia/add'/>" id="formTransferencia" name="formTransferencia" method="post">
		<input type="hidden" id="transferencialId" name="bean.id" value="${bean.id}"/>
		
		
		<div class="row">
        	<div class="col-md-2">
        		<label for="bean.codigo">Código:</label>
        		<input name="bean.codigo" id="bean.codigo" value="${bean.id}" class="form-control " size="8"  readonly="readonly"/>
        	</div>
        	
        	<div class="col-md-4">
        		<label for="bean.descricao">Descrição:</label>
        		<input name="bean.descricao" id="bean.descricao" value="${bean.descricao}" class="form-control required" size="45" maxlength="45"/>
        	</div>
        	<div class="col-md-2">
        		<label for="bean.data">Data:</label>
        		<input name="bean.data" id="bean.data" value="<fmt:formatDate value="${bean.data }" type="date" pattern="dd/MM/yyyy"/>" class="form-control datepicker required " size="8" maxlength="10"/>
        	</div>
        	<div class="col-md-2">
        		<label for="bean.valor">Valor:</label>
        		<input name="bean.valor" id="bean.valor" value="<fmt:formatNumber value="${bean.valor}" minFractionDigits="2" type="currency"/>" class="form-control required valor" size="8"  maxlength="12"/>
        	</div>
      	</div>
      	
      	<div class="row">
        	<div class="col-md-4">
        		<label for="fornecedorId">Conta Origem:</label>
	        	<select id="bean.contaOrigem.id" name="bean.contaOrigem.id" class="selectpicker form-control" data-live-search="true">
					<option value ="">Selecione</option>
					<c:forEach var="conta" items="${contas}" varStatus="contador">
						<option value ="${conta.id}">${conta.descricao}</option>
					</c:forEach>	
				</select>
        	</div>
        	
        	<div class="col-md-4">
        		<label for="fornecedorId">Conta Destino:</label>
	        	<select id="bean.contaDestino.id" name="bean.contaDestino.id" class="selectpicker form-control" data-live-search="true">
					<option value ="">Selecione</option>
					<c:forEach var="conta" items="${contas}" varStatus="contador">
						<option value ="${conta.id}">${conta.descricao}</option>
					</c:forEach>	
				</select>
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