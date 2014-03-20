<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    
    	$("#funcionario\\.dataInclusao").mask("99/99/9999");
    	
    	var options = new Array();
	   	 options['language'] = 'pt-BR';
	   	 $('.datepicker').datepicker(options);
    	
    	$("#btnAdicionar").click(function(){
    		$("#formFuncionario").submit();
    	});
    	
        $('#formFuncionario').validate({
            
        
    	});
        
        $('.selectpicker').selectpicker({
	            //'selectedText': 'cat'
	        });
        
        
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Adiantamento a Fornecedores de Metais Ferrosos e Não Ferrosos</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/funcionario/add'/>" id="formFuncionario" name="formFuncionario" method="post">
		
		
		
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
        		<label for="adiantamento.tipoPagamento">Tipo Pagamento:</label>
        		<select id="adiantamento.tipoPagamento" name="adiantamento.tipoPagamento" class="selectpicker form-control" data-live-search="true">
					<option value ="" >Selecione</option>
					<c:forEach var="tipo" items="${tiposPagamentos}" varStatus="contador">
						<option value ="${tipo.name}" ${adiantamento.tipoPagamento eq tipo ? 'selected' : ''}>${tipo.descricao}</option>
					</c:forEach>	
				</select>
        	</div>
        	<div class="col-md-2">
        		<label for="adiantamento.valor">Valor:</label>
        		<input name="adiantamento.valor" id="adiantamento.valor" value="${adiantamento.valor}" class="form-control required" />
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