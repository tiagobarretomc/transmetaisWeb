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
    	
    	$("#btnVoltar").click(function(){
    		 history.go(-1);
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
	<h2>Adiantamento a Fornecedores de Metais Ferrosos e Não Ferrosos</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/adiantamento/add'/>" id="formAdiantamento" name="formAdiantamento" method="post">
		
		
		
		<div class="row">
        	<div class="col-md-1">
        		<label for="adiantamento.id">Código:</label>
        		<input name="adiantamento.id" readonly="readonly" id="adiantamento.id" value="<fmt:formatNumber minIntegerDigits="4" value="${adiantamento.id}" groupingUsed="" />" class="form-control " size="8" maxlength="4"/>
        	</div>
        	
        	<div class="col-md-5">
        		<label for="adiantamento.fornecedor.id">Fornecedor:</label>
	        	<select id="adiantamento.fornecedor.id" name="adiantamento.fornecedor.id" class="selectpicker form-control" data-live-search="true">
					<option value ="" >Selecione</option>
					<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
						<option value ="${fornecedor.id}" ${adiantamento.fornecedor.id eq fornecedor.id  ? 'selected' : ''}>${fornecedor.apelido} - ${fornecedor.nome}</option>
					</c:forEach>	
				</select>
				
				
        	</div>
        	<div class="col-md-3">
        		<label for="adiantamento.tipoPagamento">Valor:</label>
        		<input name="adiantamento.valor" id="adiantamento.valor" value="<fmt:formatNumber value="${adiantamento.valor}" minFractionDigits="2" type="number" />" class="form-control required" />
        	</div>
        	<div class="col-md-2">
        		<label for="adiantamento.data">Data:</label>
        		<input name="adiantamento.data" id="adiantamento.data" value="<fmt:formatDate value="${adiantamento.data}" type="date" pattern="dd/MM/yyyy"/>" class="form-control required datepicker" />
        	</div>
        	
      	</div>
		
      	
      	
      	
      	<br/>
      	<div class="row">
	      	<div class="col-md-2">
				<button type="button" id="btnAdicionar" class="btn btn-default btn-md form-control">
				  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
				</button>
	      	</div>
	      	<div class="col-md-8">
				
	      	</div>
	      	
	      	<div class="col-md-2" style="">
				<button type="button" id="btnVoltar" class="btn btn-default btn-md form-control">
				  <span class="glyphicon glyphicon-arrow-left"></span> Voltar
				</button>
	      	</div>
      	</div>
		</form>
</div>
</div>
</div>