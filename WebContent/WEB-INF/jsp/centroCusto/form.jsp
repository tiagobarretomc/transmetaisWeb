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
	<h2>Cadastro de Unidades</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/unidade/add'/>" id="formUnidade" name="formUnidade" method="post">
		<input type="hidden" id="unidadelId" name="unidade.id" value="${unidade.id}"/>
		
		
		<div class="row">
        	<div class="col-md-4">
        		<label for="material.ncm">Código:</label>
        		<input name="unidade.codigo" id="unidade.codigo" value="${unidade.codigo}" class="form-control required" size="8" maxlength="4"/>
        	</div>
        	
        	<div class="col-md-4">
        		<label for="material.descricao">Descrição:</label>
        		<input name="unidade.descricao" id="unidade.descricao" value="${unidade.descricao}" class="form-control required" size="45" maxlength="45"/>
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