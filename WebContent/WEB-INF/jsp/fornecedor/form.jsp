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
    			$("#cpfCnpj").mask('999.999.999-99');
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
    	
    	
    	$(function(){
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
				 	    	//alert(json);
				 	    	var jsonObject = eval('(' + json + ')');
				 	    	var cidades = jsonObject.list;
				 	    	//alert(cidades); 
				 	    	
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

	<form action="<c:url value='/fornecedor/adiciona'/>" id="formFornecedor" name="formFornecedor" method="post">
		<input type="hidden" id="fornecedorId" name="fornecedor.id" value="${fornecedor.id}"/>
		<table width="1000px">
		<tbody>
		<tr style="background-color: #dcdee0">
			<td width="320px">Nome: <input name="fornecedor.nome" id="fornecedor.nome" value="${fornecedor.nome}" class="required" size="60"/></td>
			<td width="130px">Apelido: <input name="fornecedor.apelido" id="fornecedor.apelido" value="${fornecedor.apelido}" class="required" size="20"/></td>
		    <td></td>
		</tr>
		<tr style="background-color: #dcdee0">
			<td colspan="2">Tipo Pessoa: 
				<input type="radio" name="tipoPessoa" value="F" id="optTipoPessoaFisica"/>Física
				<input type="radio" name="tipoPessoa" value="J" id="optTipoPessoaJuridica"/>Jurídica
			</td>
			
			<td >Cpf/Cnpj:<input id="cpfCnpj" name="fornecedor.cpfCnpj" value = "${fornecedor.cpfCnpj }" size="20"class="required"/></td>
			
			
			
		</tr>
		
		<tr style="background-color: #dcdee0">
			<td >Email: <input name="fornecedor.email" value = "${fornecedor.email }" size="60"/></td>
			<td >Telefone:<input id="telefoneFixo" name="fornecedor.telefoneFixo" value = "${fornecedor.telefoneFixo}" size="20"/></td>
			<td >Celular:<input id="telefoneCelular" class="required" name="fornecedor.telefoneCelular" value="${ fornecedor.telefoneCelular}" size="20"/></td>
		</tr>
		<tr style="background-color: #dcdee0">
			<td >Endere&ccedil;o:<input name="fornecedor.endereco" value = "${fornecedor.endereco }" size="60"/></td>
			<td >Estado:<br/>
					<select id="estado" name="estado" class="required">
						<option value ="">Selecione</option>
						<c:forEach var="estado" items="${estados}" varStatus="contador">
						
							<option value ="${estado.id}" ${fornecedor.cidade.estado.id eq estado.id ? 'selected' : ''}>${estado.nome}</option>
		
						</c:forEach>	
					</select>
				</td>
			<td >Cidade:
				<div id="ajaxResultDiv">
					<select name="fornecedor.cidade.id" class="required" id="fornecedor.cidade.id"> 
						<option value="">Selecione</option>
						
						<c:forEach var="cidade" items="${fornecedor.cidade.estado.cidades}" varStatus="contador">
						
							<option value ="${cidade.id}" ${fornecedor.cidade.id eq cidade.id ? 'selected' : ''}>${cidade.nome}</option>
		
						</c:forEach>
						
						
					</select>
 				</div>
			</td>
		</tr>
		
		<tr style="background-color: #dcdee0">
			<td >Banco: <input name="fornecedor.banco" value = "${fornecedor.banco }" size="60"/></td>
			<td >Ag&ecirc;ncia:<input name="fornecedor.agencia" value = "${fornecedor.agencia }" size="20"/></td>
			<td >Conta Corrente: <input name="fornecedor.contaCorrente" value = "${fornecedor.contaCorrente }" size="20"/></td>
			
		</tr>
		
		
		<tr style="background-color: #dcdee0">
			<td colspan="2">Nome Titular: <input name="fornecedor.nomeTitular" value = "${fornecedor.nomeTitular }" size="80"/></td>
			<td >Cpf/Cnpj Titular:<input name="fornecedor.cpfCnpjTitular" value = "${fornecedor.cpfCnpjTitular }" size="20"/></td>
		</tr>
	</tbody>
	</table>
	<br>
	<br/>
	<div>Tablela de Pre&ccedil;os</div>
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
			<td>Pre&ccedil;o:
				
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
				<td>Pre&ccedil;o</td>
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