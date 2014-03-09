<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	if('${fornecedor.cpfCnpj}'.length<=14){
	    		$("#cpfCnpj").mask('999.999.999-99');
    		}else{
    			$("#cpfCnpj").mask('99.999.999/9999-99');
    		}
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/fornecedorMaterial/associar?fornecedorId="+ $("#fornecedorId").val()+"&materialId=" + $("#cboMaterial").val() + "&preco="+$("#precoMaterial").val();
    	});
    	
    	$("#btnAdicionarInfBanc").click(function(){
    		$("#formInfoBancarias").submit();
    	});
    	
    		$('#optTipoPessoaFisica').click(function(){
    			$("#cpfCnpj").mask('999.999.999-99');
    		});
    		
    		$('#optTipoPessoaJuridica').click(function(){
    			$("#cpfCnpj").mask('99.999.999/9999-99');
    		});
    		
    	    $('#estado').change(function(){
    	    	
    	        if($(this).val()) {
    	        		$.ajax({
				        type: 'GET',
				        url: '${pageContext.request.contextPath}/fornecedor/loadCidades?_format=json',
				        data:	{id: $("#estado").val()},
				 	    success: function(json){
				 	    	//alert(json);
				 	    	var jsonObject = eval('(' + json + ')');
				 	    	var cidades = jsonObject.list;
				 	    	//alert(cidades); 
				 	    	
				 	    	var html = "";  
				 	       html += "<select name='cliente.cidade.id'>" ;  
				 	       for(i=0;i<cidades.length;i++) {  
				 	           html += "<option value='"+cidades[i].id +"'>"+cidades[i].nome+"</option>";                           
				 	       }  
				 	       html += "</select> " ;  
				 	       var div = document.getElementById("ajaxResultDiv");  
				 	       div.innerHTML = html; 
				 	        

						},
					    error: function(xhr){
					    	alert('erro!');
							    }
				    });
    	    	
    	    	
    	    	
    	        } else {
    	            //estoura um erro qualquer informando que nao foi selecionado nada
    	        }
    	    });
    	
    	
    	
    	$("#telefoneFixo").mask("(99) 9999-9999");
    	$("#telefoneCelular").mask("(99) 9999-9999");
    	
        $('#formFornecedor').validate({
            
        
    	});
    });
</script>

    <div class="container">
    <br>
	<h2>Dados do Cliente</h2>
	<form action="<c:url value='/cliente/adicionar'/>" id="formCliente" name="formCliente" method="post">
		<input type="hidden" id="clienteId" name="cliente.id" value="${cliente.id}"/>
		
		<div class="row">
        	<div class="col-md-4">Razão Social: <br><input name="cliente.razaoSocial" id="cliente.razaoSocial" value="${cliente.razaoSocial}" class="required" size="45"/></div>
        	<div class="col-md-4">Apelido: <br><input name="cliente.apelido" id="cliente.apelido" value="${cliente.apelido}" class="required" size="20"/></div>
        	<div class="col-md-4">
        		<input type="checkbox" name="cliente.status" value="A" ${cliente.status eq 'A' ? 'checked="checked"': '' }>Ativo
        	</div>
      	</div>
		<div class="row">
        	<div class="col-md-4">Tipo Documento: <br/>
				<input type="radio" name="tipoPessoa" value="F" id="optTipoPessoaFisica"/>&nbsp;CPF&nbsp;
				<input type="radio" name="tipoPessoa" value="J" id="optTipoPessoaJuridica"/>&nbsp;CNPJ&nbsp;</div>
        	<div class="col-md-4">Cpf/Cnpj:<br/><input id="cpfCnpj" name="cliente.cpfCnpj" value = "${cliente.cpfCnpj }" size="20"class="required"/></div>
        	<div class="col-md-4">Inscrição Estadual:<br/><input id="cpfCnpj" name="cliente.inscricaoEstadual" value = "${cliente.inscricaoEstadual }" size="20"class="required"/></div>
        	
      	</div>
      	
      	<div class="row">
        	<div class="col-md-4">Email: <br><input name="cliente.email" value = "${cliente.email }" size="45"/></div>
        	<div class="col-md-4">Telefone:<br/><input id="telefoneFixo" name="cliente.telefoneFixo" value = "${cliente.telefoneFixo}" size="20"/></div>
        	<div class="col-md-4">Celular:<br/><input id="telefoneCelular" class="required" name="cliente.telefoneCelular" value="${ cliente.telefoneCelular}" size="20"/></div>
      	</div>
      	
      	<div class="row">
        	<div class="col-md-4">Logradouro:<br><input name="cliente.logradouro" value = "${cliente.logradouro }" size="45"/></div>
        	<div class="col-md-4">Número:<br><input name="cliente.numero" value = "${cliente.numero }" size="15"/></div>
        	<div class="col-md-4">Bairro:<br><input name="cliente.bairro" value = "${cliente.bairro }" size="15"/></div>
        	
      	</div>
      		<div class="row">
      		<div class="col-md-4">Estado:<br/>
					<select id="estado" name="estado" class="required">
						<option value ="">Selecione</option>
						<c:forEach var="estado" items="${estados}" varStatus="contador">
						
							<option value ="${estado.id}" ${cliente.cidade.estado.id eq estado.id ? 'selected' : ''}>${estado.nome}</option>
		
						</c:forEach>	
					</select>
			</div>
      		<div class="col-md-4">Cidade:<br/><div id="ajaxResultDiv">
					<select name="cliente.cidade.id" class="required" id="cliente.cidade.id"> 
						<option value="">Selecione</option>
						
						<c:forEach var="cidade" items="${cliente.cidade.estado.cidades}" varStatus="contador">
						
							<option value ="${cidade.id}" ${cliente.cidade.id eq cidade.id ? 'selected' : ''}>${cidade.nome}</option>
		
						</c:forEach>
						
						
					</select>
 				</div></div>
 				<div class="col-md-4">Cep:<br><input name="cliente.cep" value = "${cliente.cep }" size="15"/></div>
 			</div>
      	<br/>
		<input type="submit" value="Salvar"/>	<br/>
		
</div>

