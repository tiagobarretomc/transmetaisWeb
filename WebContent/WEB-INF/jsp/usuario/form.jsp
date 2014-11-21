<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#btnAdicionar").click(function(){
    		$("#formUsuario").submit();
    	});
    	
        $('#formUsuario').validate({
            
        
    	});
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Usu&aacute;rios</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/usuario/add'/>" id="formUsuario" name="formUsuario" method="post">
		<input type="hidden" id="usuariolId" name="usuario.id" value="${usuario.id}"/>
		
		
		<div class="row">
        	<div class="col-md-4">
        		<label for="usuario.nome">Nome:</label>
        		<input name="usuario.nome" id="usuario.nome" value="${usuario.nome}" class="form-control required" size="8" maxlength="4"/>
        	</div>
        	
        	<div class="col-md-4">
        		<label for="usuario.email">E-mail:</label>
        		<input name="usuario.email" id="usuario.email" value="${usuario.email}" class="form-control required" size="45" maxlength="45"/>
        	</div>
      	</div>
      	<div class="row" style="display:${not empty usuario.senha ? 'none':'display'}" >
        	<div class="col-md-4">
        		<label for="usuario.ativo">Situação:</label>
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