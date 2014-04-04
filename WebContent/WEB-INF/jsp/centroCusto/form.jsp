<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#btnAdicionar").click(function(){
    		$("#formCentroCusto").submit();
    	});
    	
        $('#formCentroCusto').validate({
            
        
    	});
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Centro de Custo</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/centroCusto/add'/>" id="formCentroCusto" name="formCentroCusto" method="post">
		
		
		
		<div class="row">
		<div class="col-md-2">
        		<label for="centroCusto.id">Código:</label>
        		<input name="centroCusto.id" readonly="readonly" id="centroCusto.id" value="<fmt:formatNumber minIntegerDigits="4" value="${centroCusto.id}" groupingUsed="" />" class="form-control " maxlength="4"/>
        	</div>
        	<div class="col-md-4">
        		<label for="centroCusto.numero">Número:</label>
        		<input name="centroCusto.numero" id="centroCusto.numero" value="${centroCusto.numero}" class="form-control required" size="8" maxlength="10"/>
        	</div>
        	
        	<div class="col-md-4">
        		<label for="centroCusto.descricao">Descrição:</label>
        		<input name="centroCusto.descricao" id="centroCusto.descricao" value="${centroCusto.descricao}" class="form-control required" size="45" maxlength="45"/>
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