<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	//$("#contaContabil\\.numero").mask('999999');
    
  
    	
    	$("#btnAdicionar").click(function(){
    		$("#formConta").submit();
    	});
    	
        $('#formConta').validate({
            
        
    	});
        
      
        
       
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Conta</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/contaContabil/add'/>" id="formConta" name="formConta" method="post">
		
		
		
		<div class="row">
        	<div class="col-md-1">
        		<label for="contaContabil.id">Código:</label>
        		<input name="contaContabil.id" readonly="readonly" id="contaContabil.id" value="<fmt:formatNumber minIntegerDigits="4" value="${contaContabil.id}" groupingUsed="" />" class="form-control " size="8" maxlength="4"/>
        	</div>
        	
        	<div class="col-md-4">
        		<label for="contaContabil.numero">Número:</label>
        		<input name="contaContabil.numero" id="contaContabil.numero" value="${contaContabil.numero}" class="form-control required"  maxlength="100"/>
        	</div>
        	<div class="col-md-3">
        		<label for="contaContabil.descricao">Descrição:</label>
        		<input name="contaContabil.descricao" id="contaContabil.descricao" value="${contaContabil.descricao}" class="form-control required" />
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