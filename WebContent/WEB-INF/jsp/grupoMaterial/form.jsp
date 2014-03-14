<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#btnAdicionar").click(function(){
    		$("#formGrupo").submit();
    	});
    	
        $('#formGrupo').validate({
            
        
    	});
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Grupo de Material</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='grupoMaterial/add'/>" id="formGrupo" name="formGrupo" method="post">
		<input type="hidden" id="grupoId" name="grupoMaterial.id" value="${grupoMaterial.id}"/>
		
		
		<div class="row">
        	<div class="col-md-2">
        		<label for="material.ncm">Código:</label>
        		<input name="grupoMaterial.codigo" id="grupoMaterial.codigo" value="${grupoMaterial.codigo}" class="form-control required" size="8" maxlength="4"/>
        	</div>
        	<div class="col-md-10">
        		<label for="material.sigla">Descrição:</label>
        		<input name="grupoMaterial.descricao" id="grupoMaterial.descricao" value="${grupoMaterial.descricao}" class="form-control required"  maxlength="100"/>
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