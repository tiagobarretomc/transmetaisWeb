<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#funcionario\\.cpf").mask('999.999.999-99');
    	$("#funcionario\\.cep").mask('99999-999');
    	$("#funcionario\\.telefone").mask("(99) 9999-9999");
    	$("#funcionario\\.telefoneContato").mask("(99) 9999-9999");
    	$("#funcionario\\.nascimento").mask("99/99/9999");
    	
    	var options = new Array();
	   	 options['language'] = 'pt-BR';
	   	 $('.datepicker').datepicker(options);
    	
    	$("#btnAdicionar").click(function(){
    		$("#formFuncionario").submit();
    	});
    	
        $('#formFuncionario').validate({
            
        
    	});
        
        $('.selectpicker').selectpicker({
	            //'selectedText': 'cat'
	        });
        
        $('#estado').change(function(){
	    	
	        if($(this).val()) {
	        		$.ajax({
			        type: 'GET',
			        url: '${pageContext.request.contextPath}/fornecedor/loadCidades?_format=json',
			        data:	{id: $("#estado").val()},
			 	    success: function(json){
			 	    	//alert(json);
			 	    	var jsonObject = eval(json);
			 	    	//alert(jsonObject);
			 	    	var cidades = jsonObject.list;
			 	    	//alert(cidades); 
			 	    	
			 	    	var html = "";  
			 	       html += "<select name='funcionario.cidade.id' class='selectpicker form-control' data-live-search='true'>" ;  
			 	       for(i=0;i<cidades.length;i++) {  
			 	           html += "<option value='"+cidades[i].id +"'>"+cidades[i].nome+"</option>";                           
			 	       }  
			 	       html += "</select> " ;  
			 	       var div = document.getElementById("ajaxResultDiv");  
			 	       div.innerHTML = html; 
			 	      $('.selectpicker').selectpicker({
			 	            //'selectedText': 'cat'
			 	        });
			 	        

					},
				    error: function(xhr){
				    	alert('erro!');
						    }
			    });
	    	
	    	
	    	
	        } else {
	            //estoura um erro qualquer informando que nao foi selecionado nada
	        }
	    });
    	
    	
    });
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Funcionários</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/funcionario/add'/>" id="formFuncionario" name="formFuncionario" method="post">
		
		
		
		<div class="row">
        	<div class="col-md-1">
        		<label for="funcionario.id">Código:</label>
        		<input name="funcionario.id" readonly="readonly" id="funcionario.id" value="<fmt:formatNumber minIntegerDigits="4" value="${funcionario.id}" groupingUsed="" />" class="form-control required" size="8" maxlength="4"/>
        	</div>
        	
        	<div class="col-md-4">
        		<label for="funcionario.nome">Nome:</label>
        		<input name="funcionario.nome" id="funcionario.nome" value="${funcionario.nome}" class="form-control required"  maxlength="100"/>
        	</div>
        	<div class="col-md-3">
        		<label for="funcionario.cpf">CPF:</label>
        		<input name="funcionario.cpf" id="funcionario.cpf" value="${funcionario.cpf}" class="form-control required" />
        	</div>
        	<div class="col-md-2">
        		<label for="funcionario.rg">RG:</label>
        		<input name="funcionario.rg" id="funcionario.rg" value="${funcionario.rg}" class="form-control required" />
        	</div>
        	<div class="col-md-2">
        		<label for="funcionario.nascimento">Nascimento:</label>
        		<input name="funcionario.nascimento" id="funcionario.nascimento" value="${funcionario.nascimento}" class="form-control required datepicker" data-date-format="dd/mm/yyyy" />
        	</div>
      	</div>
		<div class="row">
			<div class="col-md-4">
        		<label for="funcionario.logradouro">Logradouro:</label>
        		<input name="funcionario.logradouro" id="funcionario.logradouro" value="${funcionario.logradouro}" class="form-control required"  maxlength="100"/>
        	</div>
        	<div class="col-md-1">
        		<label for="funcionario.logradouro">Número:</label>
        		<input name="funcionario.numero" id="funcionario.numero" value="${funcionario.numero}" class="form-control required"  maxlength="7"/>
        	</div>
        	<div class="col-md-2">
        		<label for="funcionario.bairro">Bairro:</label>
        		<input name="funcionario.bairro" id="funcionario.bairro" value="${funcionario.bairro}" class="form-control required"  maxlength="45"/>
        	</div>
        	
        	<div class="col-md-2">
        		<label for="estado">Estado:</label>
					<select id="estado" name="estado" class="selectpicker required form-control" data-live-search="true">
						<option value ="">Selecione</option>
						<c:forEach var="estado" items="${estados}" varStatus="contador">
						
							<option value ="${estado.id}" ${funcionario.cidade.estado.id eq estado.id ? 'selected' : ''}>${estado.nome}</option>
		
						</c:forEach>	
					</select></div>
        	<div class="col-md-3">
        	<label for="fornecedor.cidade.id">Cidade:</label>
        	<div id="ajaxResultDiv">
					<select name="funcionario.cidade.id"  id="funcionario.cidade.id" class="selectpicker form-control" data-live-search="true"> 
						<option value="">Selecione</option>
						
						<c:forEach var="cidade" items="${funcionario.cidade.estado.cidades}" varStatus="contador">
						
							<option value ="${cidade.id}" ${funcionario.cidade.id eq cidade.id ? 'selected' : ''}>${cidade.nome}</option>
		
						</c:forEach>
						
						
					</select>
 				</div></div>
		</div>
		<div class="row">
			<div class="col-md-2">
        		<label for="funcionario.cep">Cep:</label>
        		<input name="funcionario.cep" id="funcionario.cep" value="${funcionario.cep}" class="form-control required"  maxlength="10"/>
        	</div>
        	<div class="col-md-3">
        		<label for="funcionario.telefone">Telefone:</label>
        		<input name="funcionario.telefone" id="funcionario.telefone" value="${funcionario.telefone}" class="form-control required"  maxlength="15"/>
        	</div>
        	<div class="col-md-3">
        		<label for="funcionario.email">E-mail:</label>
        		<input name="funcionario.email" id="funcionario.email" value="${funcionario.email}" class="form-control required"  maxlength="45"/>
        	</div>
        	<div class="col-md-4">
        		<label for="funcionario.cargo">Cargo:</label>
        		<input name="funcionario.cargo" id="funcionario.cargo" value="${funcionario.cargo}" class="form-control required"  maxlength="45"/>
        	</div>
      	</div>
      	<div class="row">
      		<div class="col-md-4">
        		<label for="funcionario.contato">Contato:</label>
        		<input name="funcionario.contato" id="funcionario.contato" value="${funcionario.contato}" class="form-control required"  maxlength="45"/>
        	</div>
        	<div class="col-md-4">
        		<label for="funcionario.telefoneContato">Telefone do Contato:</label>
        		<input name="funcionario.telefoneContato" id="funcionario.telefoneContato" value="${funcionario.telefoneContato}" class="form-control required"  maxlength="45"/>
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