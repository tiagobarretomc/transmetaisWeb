<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$('.selectpicker').selectpicker({
            'selectedText': 'cat'
        });
    	
    	$("#btnAdicionar").click(function(){
    		$("#formMaterial").submit();
    	});
    	
        $('#formMaterial').validate({
            
        
    	});
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Cadatro de Material (Produto)</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/material/adiciona'/>" id="formMaterial" name="formMaterial" method="post">
		<input type="hidden" id="materialId" name="mterial.id" value="${material.id}"/>
		
		
		<div class="row">
        	<div class="col-md-4">
        		<label for="material.descricao">Descrição:</label>
        		<input name="material.descricao" id="material.descricao" value="${material.descricao}" class="form-control required" size="45" maxlength="45"/>
        	</div>
        	<div class="col-md-4">
        		<label for="material.sigla">Sigla:</label>
        		<input name="material.sigla" id="material.sigla" value="${material.sigla}" class="form-control required" size="4" maxlength="4"/>
        	</div>
        	<div class="col-md-4">
        		<label for="material.ncm">Ncm:</label>
        		<input name="material.ncm" id="material.ncm" value="${material.ncm}" class="form-control" size="8" maxlength="8"/>
        	</div>
      	</div>
		<div class="row">
        	<div class="col-md-4">
        	<label for="material.unidadeMedida.id">Unidade Medida:</label>
        		<select id="material.unidadeMedida.id" name="material.unidadeMedida.id" class="selectpicker required form-control" >
						<option value ="">Selecione</option>
						<c:forEach var="unidade" items="${unidadesMedidas}" varStatus="contador">
						
							<option value ="${unidade.id}" ${material.unidadeMedida.id eq unidade.id ? 'selected' : ''}>${unidade.sigla} - ${unidade.descricao}</option>
		
						</c:forEach>	
					</select>
        	</div>
        	
        	<div class="col-md-4">
        	<label for="material.grupoMaterial.id">Grupo Material:</label>
        		<select id="material.grupoMaterial.id" name="material.grupoMaterial.id" class="selectpicker required form-control" >
						<option value ="">Selecione</option>
						<c:forEach var="grupo" items="${grupos}" varStatus="contador">
						
							<option value ="${grupo.id}" ${material.grupoMaterial.id eq grupo.id ? 'selected' : ''}>${grupo.descricao}</option>
		
						</c:forEach>	
					</select>
        	</div>
				
        	<div class="col-md-4">
        	</div>
        	
      	</div>
      	
      	
      	
      	
      	<br/>
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
		</button>
		
</div>
</div>
</div>