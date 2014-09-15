<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#dataInicio").mask('99/99/9999');
    	$("#dataFim").mask('99/99/9999');
    	
    	var options = new Array();
    	 options['language'] = 'pt-BR';
    	 $('.datepicker').datepicker(options);
    	
    	$("#btnSalvar").click(function(){
    		
    		$("#divTabela").load( '<c:url value="/contaBancaria/transferir"/>', $('#formCompra').serialize() );
    	});
    	
    	
    	
    	$('.selectpicker').selectpicker({
            //'selectedText': 'cat'
        });
    });
 </script>
 
<div class="container">
		<br/>
		<h2>Transferência entre Contas</h2>
		<br>
		<div class="alert alert-warning alert-dismissable" id="divError" style="display: none">Selecione uma Conta!</div>
		<div class="panel panel-default">
  	<div class="panel-body">
		<form action="<c:url value='/contaBancaria/loadExtrato'/>" id="formCompra" name="formCompra" method="post">
		<input type="hidden" name="_format" value="json">
		<div class="row">
        	<div class="col-md-5">
        		<label for="fornecedorId">Conta Origem:</label>
	        	<select id="contaOrigemId" name="contaOrigemId" class="selectpicker form-control" data-live-search="true">
					<option value ="">Selecione</option>
					<c:forEach var="conta" items="${contas}" varStatus="contador">
						<option value ="${conta.id}">${conta.descricao}</option>
					</c:forEach>	
				</select>
				
				
        	</div>
        	
        </div>
        <div class="row">
        	<div class="col-md-5">
        		<label for="fornecedorId">Conta Destino:</label>
	        	<select id="contaDestinoId" name="contaDestinoId" class="selectpicker form-control" data-live-search="true">
					<option value ="">Selecione</option>
					<c:forEach var="conta" items="${contas}" varStatus="contador">
						<option value ="${conta.id}">${conta.descricao}</option>
					</c:forEach>	
				</select>
				
				
        	</div>
        	
        </div>
        
        <br/>
        <button id="btnSalvar" type="button" class="btn btn-default btn-sm">
  		<span class="glyphicon glyphicon-filter"></span> Salvar
		</button>
        </form>
        </div>
        </div>
        
        
		
		
		
	</div>
