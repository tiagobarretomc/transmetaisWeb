<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	$("#centroAplicacao\\.numero").mask('9999');
    	$("#btnAdicionar").click(function(){
    		$("#formCentroAplicacao").submit();
    	});
    	
        $('#formCentroAplicacao').validate({
            
        
    	});
        
        $('.selectpicker').selectpicker({
            //'selectedText': 'cat'
        });
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Centro de Aplicação</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/centroAplicacao/add'/>" id="formCentroAplicacao" name="formCentroAplicacao" method="post">
		
		
		
		<div class="row">
        	<div class="col-md-1">
        		<label for="centroAplicacao.id">Código:</label>
        		<input name="centroAplicacao.id" readonly="readonly" id="centroAplicacao.id" value="<fmt:formatNumber minIntegerDigits="4" value="${centroAplicacao.id}" groupingUsed="" />" class="form-control " size="8" maxlength="4"/>
        	</div>
        	
        	<div class="col-md-2">
        		<label for="centroAplicacao.numero">Número:</label>
        		<input name="centroAplicacao.numero" id="centroAplicacao.numero" value="${centroAplicacao.numero}" class="form-control required" size="8" maxlength="4"/>
        	</div>
        </div>
        
        <div class="row">	
        	<div class="col-md-4">
        		<label for="centroAplicacao.centroCusto.id">Centro de Custo:</label>
					<select id="centroAplicacao.centroCusto.id" name="centroAplicacao.centroCusto.id" class="selectpicker required form-control" data-live-search="true">
						<option value ="">Selecione</option>
						<c:forEach var="centro" items="${centrosCustos}" varStatus="contador">
						
							<option value ="${centro.id}" ${centroAplicacao.centroCusto.id eq centro.id ? 'selected' : ''}>${centro.descricao}</option>
		
						</c:forEach>	
					</select></div>
        	<div class="col-md-8">
        		<label for="centroAplicacao.descricao">Descrição:</label>
        		<input name="centroAplicacao.descricao" id="centroAplicacao.descricao" value="${centroAplicacao.descricao}" class="form-control required" size="45" maxlength="45"/>
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