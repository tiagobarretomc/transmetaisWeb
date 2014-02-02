<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">

function showResponse(data) {  
    //get the jsonObject  
    var jsonObject = eval(data);  
    //get the user array from the json object  
    var cidades = jsonObject.list;  
    
    alert(jsonObject);
    //update html  
    var html = "";  
     html += "<select name='fornecedor.cidade.id' id='fornecedor.cidade.id'>" ;  
     for(i=0;i<users.length;i++) {  
         html += "<option value='"+cidades[i].id +"'>"+cidades[i].nome+"</option>";                           
     }  
     html += "</select> " ;  
     var div = document.getElementById("ajaxResultDiv");  
     div.innerHTML = html;  
}  


    $(document).ready(function(){
    	//$("#cpfCnpj").keyup(function() {
    	//	if($("#cpfCnpj").val().length==14)
        //	{
    		
    		

    			
    		if('${fornecedor.cpfCnpj}'.length<=14){
	    		$("#cpfCnpj").mask('999.999.999-99');
    		}else{
    			$("#cpfCnpj").mask('99.999.999/9999-99');
    		}
    	/*
        	}
        	else if($("#cpfCnpj").val().length==18)
        	{
        		$("#cpfCnpj").mask('99.999.999/9999-99');
        	 
        	}
    		});
    	
    	*/
    	
    	$("#btnAdicionar").click(function(){
    		
    		$.ajax({
    		method: "get",
    		url: "${pageContext.request.contextPath}/fornecedorMaterial/associar",
    		data: { fornecedorId: $("#fornecedorId").val(),
    				materialId: $("#cboMaterial").val(),
    				preco: $("#precoMaterial").val()},
    		
    		success: function(retorno){
    			//alert(retorno);
    			var startTable = retorno.indexOf("<table width=\"400px\">");
    			var endTable = retorno.indexOf("</table>")+8;
    			//alert(retorno.substring(startTable,endTable));
    			$("#tabelaPreco").html(retorno.substring(startTable,endTable));
    		},
			error: function(data){
		    	alert(data);
		    }		
    				
    		});
    		
    		//alert($("#cboMaterial").val());
    	});
    	
    	
    	
    		$('#optTipoPessoaFisica').click(function(){
    			$("#cpfCnpj").mask('999.999.999-99');
    		});
    		
    		$('#optTipoPessoaJuridica').click(function(){
    			$("#cpfCnpj").mask('99.999.999/9999-99');
    		});
    		
    	    $('#estado').change(function(){
    	    	
    	        if($(this).val()) {
    	        	//alert('testando certinho');
    	    	/*
    	            $.ajax({
    	                  url: "/retorna_dados_para_meu_novo_combo"
    	            }).done(function() {
    	              //lógica para preencher um combo novo com os dados que vem em JSON da chamada ajax
    	            }).fail(function(){ 
    	                alert("Um erro qualquer informando que houve problema na chamada Ajax")
    	            });
    	    	*/
    	    	
    	        	$.ajax({
				        type: 'GET',
				        url: '${pageContext.request.contextPath}/fornecedor/loadCidades?_format=json',
				        data:	{id: $("#estado").val()},
				 	    success: function(json){
				 	    	alert(json);
				 	    	//var jsonObject = eval('(' + json + ')');
				 	    	var cidades = json.list;
				 	    	alert(cidades); 
				 	    	
				 	    	var html = "";  
				 	       html += "<select name='fornecedor.cidade.id'>" ;  
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transmetais</title>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Transmetais</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Cadastros</a></li>
            <li><a href="#about">Comercial</a></li>
            <li><a href="#contact">Relatórios</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
    <br>
	<h2>Dados do Fornecedor</h2>
	<form action="<c:url value='/fornecedor/adiciona'/>" id="formFornecedor" name="formFornecedor" method="post">
		<input type="hidden" id="fornecedorId" name="fornecedor.id" value="${fornecedor.id}"/>
		
		<div class="row">
        	<div class="col-md-4">Nome: <input name="fornecedor.nome" id="fornecedor.nome" value="${fornecedor.nome}" class="required" size="60"/></div>
        	<div class="col-md-4">Apelido: <br><input name="fornecedor.apelido" id="fornecedor.apelido" value="${fornecedor.apelido}" class="required" size="20"/></div>
        	<div class="col-md-4"></div>
      	</div>
		<div class="row">
        	<div class="col-md-4">Tipo Documento: <br/>
				<input type="radio" name="tipoPessoa" value="F" id="optTipoPessoaFisica"/>&nbsp;CPF&nbsp;
				<input type="radio" name="tipoPessoa" value="J" id="optTipoPessoaJuridica"/>&nbsp;CNPJ&nbsp;</div>
        	<div class="col-md-4">Cpf/Cnpj:<br/><input id="cpfCnpj" name="fornecedor.cpfCnpj" value = "${fornecedor.cpfCnpj }" size="20"class="required"/></div>
        	
      	</div>
      	
      	<div class="row">
        	<div class="col-md-4">Email: <input name="fornecedor.email" value = "${fornecedor.email }" size="60"/></div>
        	<div class="col-md-4">Telefone:<br/><input id="telefoneFixo" name="fornecedor.telefoneFixo" value = "${fornecedor.telefoneFixo}" size="20"/></div>
        	<div class="col-md-4">Celular:<br/><input id="telefoneCelular" class="required" name="fornecedor.telefoneCelular" value="${ fornecedor.telefoneCelular}" size="20"/></div>
      	</div>
      	
      	<div class="row">
        	<div class="col-md-4">Endereço:<input name="fornecedor.endereco" value = "${fornecedor.endereco }" size="60"/></div>
        	<div class="col-md-4">Estado:<br/>
					<select id="estado" name="estado" class="required">
						<option value ="">Selecione</option>
						<c:forEach var="estado" items="${estados}" varStatus="contador">
						
							<option value ="${estado.id}" ${fornecedor.cidade.estado.id eq estado.id ? 'selected' : ''}>${estado.nome}</option>
		
						</c:forEach>	
					</select></div>
        	<div class="col-md-4"><div id="ajaxResultDiv">Cidade:<br/>
					<select name="fornecedor.cidade.id" class="required" id="fornecedor.cidade.id"> 
						<option value="">Selecione</option>
						
						<c:forEach var="cidade" items="${fornecedor.cidade.estado.cidades}" varStatus="contador">
						
							<option value ="${cidade.id}" ${fornecedor.cidade.id eq cidade.id ? 'selected' : ''}>${cidade.nome}</option>
		
						</c:forEach>
						
						
					</select>
 				</div></div>
      	</div>
      	<div class="row">
        	<div class="col-md-4">Banco: <input name="fornecedor.banco" value = "${fornecedor.banco }" size="60"/></div>
        	<div class="col-md-4">Agência:<br/><input name="fornecedor.agencia" value = "${fornecedor.agencia }" size="20" maxlength="6"/></div>
        	<div class="col-md-4">Conta Corrente: <br/><input name="fornecedor.contaCorrente" value = "${fornecedor.contaCorrente }" size="20"/></div>
      	</div>
      	<div class="row">
        	<div class="col-md-4">Nome Titular: <input name="fornecedor.nomeTitular" value = "${fornecedor.nomeTitular }" size="60"/></div>
        	<div class="col-md-4">Cpf/Cnpj Titular:<br/><input name="fornecedor.cpfCnpjTitular" value = "${fornecedor.cpfCnpjTitular }" size="20"/></div>
        	<div class="col-md-4"></div>
      	</div>
		
		
	<br>
	<br/>
	<h3>Materiais Fornecidos</h3>
	<table>
		<tr>
			<td>Material:
				<select style="width: 180px;" id="cboMaterial">
					<option value="" >--Selecione--</option>
					<c:forEach var="material" items="${materiais}">
						<option value="${material.id }" >${material.descricao}</option>
					</c:forEach>
				</select>
			</td>
			<td>Preço:
				
			</td>
			<td>
				<input  type="text" id="precoMaterial" size="15"/>
			</td>
			<td><input id="btnAdicionar" type="button" value="Adicionar"/>
			</td>
			
		</tr>
	</table>
	<div id="tabelaPreco">
	<table width="400px">
		<thead>
			<tr>
				<td>Material</td>
				<td>Preço</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="materialFornecedor" items="${fornecedor.materiaisFornecedores}" varStatus="contador">
		<tr>
			<td>${materialFornecedor.material.descricao}</td>
			<td>${materialFornecedor.valor}</td>
			<td><a href="<c:url value='/fornecedorMaterial/excluir/'/>${materialFornecedor.id}">Excluir</a></td>
		</tr>
		
		</c:forEach>
		</tbody>
	</table>
    </div>
    <input type="submit" value="Salvar"/>
</form>
</body>
</html>