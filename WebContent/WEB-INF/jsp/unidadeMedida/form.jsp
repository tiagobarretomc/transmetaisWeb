<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#btnAdicionar").click(function(){
    		$("#formUnidade").submit();
    	});
    	
        $('#formUnidade').validate({
            
        
    	});
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Unidade de Medidas</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/unidadeMedida/add'/>" id="formUnidade" name="formUnidade" method="post">
		<input type="hidden" id="unidadelId" name="unidade.id" value="${unidadeMedida.id}"/>
		
		
		<div class="row">
        	<div class="col-md-4">
        		<label for="material.ncm">Código:</label>
        		<input name="unidade.codigo" id="unidade.codigo" value="${unidadeMedida.codigo}" class="form-control required" size="8" maxlength="4"/>
        	</div>
        	<div class="col-md-4">
        		<label for="material.sigla">Sigla:</label>
        		<input name="unidade.sigla" id="unidade.sigla" value="${unidadeMedida.sigla}" class="form-control required" size="4" maxlength="4"/>
        	</div>
        	<div class="col-md-4">
        		<label for="material.descricao">Descrição:</label>
        		<input name="unidade.descricao" id="unidade.descricao" value="${unidadeMedida.descricao}" class="form-control required" size="45" maxlength="45"/>
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