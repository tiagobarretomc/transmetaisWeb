<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#btnAdicionar").click(function(){
    		$("#formProduto").submit();
    	});
    	
        $('#formProduto').validate({
            
        
    	});
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Produtos</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/produto/add'/>" id="formProduto" name="formProduto" method="post">
		<input type="hidden" id="produtolId" name="produto.id" value="${produto.id}"/>
		
		
		<div class="row">
        	<div class="col-md-4">
        		<label for="id">Código:</label>
        		<input name="produto.codigo" id="produto.codigo" value="${produto.codigo}" class="form-control required" size="8" maxlength="4"/>
        	</div>
        	
        	<div class="col-md-4">
        		<label for="produto.descricao">Descrição:</label>
        		<input name="produto.descricao" id="produto.descricao" value="${produto.descricao}" class="form-control required" size="45" maxlength="45"/>
        	</div>
        	<div class="col-md-4">
        		<label for="produto.ncm">Ncm:</label>
        		<input name="produto.ncm" id="produto.ncm" value="${produto.ncm}" class="form-control" size="8" maxlength="8"/>
        	</div>
      	</div>
		<div class="row">
        	<div class="col-md-4">
        	<label for="produto.unidadeMedida.id">Unidade Medida:</label>
        		<select id="produto.unidadeMedida.id" name="produto.unidadeMedida.id" class="selectpicker required form-control" >
						<option value ="">Selecione</option>
						<c:forEach var="unidade" items="${unidadesMedidas}" varStatus="contador">
						
							<option value ="${unidade.id}" ${produto.unidadeMedida.id eq unidade.id ? 'selected' : ''}>${unidade.sigla} - ${unidade.descricao}</option>
		
						</c:forEach>	
					</select>
        	</div>
        	
        	<div class="col-md-4">
        	<label for="produto.grupoMaterial.id">Grupo Material:</label>
        		<select id="produto.grupoMaterial.id" name="produto.grupoMaterial.id" class="selectpicker required form-control" >
						<option value ="">Selecione</option>
						<c:forEach var="grupo" items="${grupos}" varStatus="contador">
						
							<option value ="${grupo.id}" ${produto.grupoMaterial.id eq grupo.id ? 'selected' : ''}>${grupo.descricao}</option>
		
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
		</form>
</div>
</div>
</div>