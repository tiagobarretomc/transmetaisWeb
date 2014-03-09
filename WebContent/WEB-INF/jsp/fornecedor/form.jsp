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
				 	    	alert(json);
				 	    	var jsonObject = eval(json);
				 	    	alert(jsonObject);
				 	    	var cidades = jsonObject.list;
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

    <div class="container">
    <br>
	<h2>Dados do Fornecedor</h2>
	<form action="<c:url value='/fornecedor/adiciona'/>" id="formFornecedor" name="formFornecedor" method="post">
		<input type="hidden" id="fornecedorId" name="fornecedor.id" value="${fornecedor.id}"/>
		
		
		<div class="row">
        	<div class="col-md-4">Nome: <br><input name="fornecedor.nome" id="fornecedor.nome" value="${fornecedor.nome}" class="required" size="45"/></div>
        	<div class="col-md-4">Apelido: <br><input name="fornecedor.apelido" id="fornecedor.apelido" value="${fornecedor.apelido}" class="required" size="20"/></div>
        	<div class="col-md-4">
        		<input type="checkbox" name="fornecedor.status" value="A" ${fornecedor.status eq 'A' ? 'checked="checked"': '' }>Ativo
        	</div>
      	</div>
		<div class="row">
        	<div class="col-md-4">Tipo Documento: <br/>
				<input type="radio" name="tipoPessoa" value="F" id="optTipoPessoaFisica"/>&nbsp;CPF&nbsp;
				<input type="radio" name="tipoPessoa" value="J" id="optTipoPessoaJuridica"/>&nbsp;CNPJ&nbsp;</div>
        	<div class="col-md-4">Cpf/Cnpj:<br/><input id="cpfCnpj" name="fornecedor.cpfCnpj" value = "${fornecedor.cpfCnpj }" size="20"class="required"/></div>
        	
      	</div>
      	
      	<div class="row">
        	<div class="col-md-4">Email: <br><input name="fornecedor.email" value = "${fornecedor.email }" size="45"/></div>
        	<div class="col-md-4">Telefone:<br/><input id="telefoneFixo" name="fornecedor.telefoneFixo" value = "${fornecedor.telefoneFixo}" size="20"/></div>
        	<div class="col-md-4">Celular:<br/><input id="telefoneCelular" class="required" name="fornecedor.telefoneCelular" value="${ fornecedor.telefoneCelular}" size="20"/></div>
      	</div>
      	
      	<div class="row">
        	<div class="col-md-4">Endereço:<br><input name="fornecedor.endereco" value = "${fornecedor.endereco }" size="45"/></div>
        	<div class="col-md-4">Estado:<br/>
					<select id="estado" name="estado" class="required">
						<option value ="">Selecione</option>
						<c:forEach var="estado" items="${estados}" varStatus="contador">
						
							<option value ="${estado.id}" ${fornecedor.cidade.estado.id eq estado.id ? 'selected' : ''}>${estado.nome}</option>
		
						</c:forEach>	
					</select></div>
        	<div class="col-md-4">Cidade:<br/><div id="ajaxResultDiv">
					<select name="fornecedor.cidade.id" class="required" id="fornecedor.cidade.id"> 
						<option value="">Selecione</option>
						
						<c:forEach var="cidade" items="${fornecedor.cidade.estado.cidades}" varStatus="contador">
						
							<option value ="${cidade.id}" ${fornecedor.cidade.id eq cidade.id ? 'selected' : ''}>${cidade.nome}</option>
		
						</c:forEach>
						
						
					</select>
 				</div></div>
      	</div>
      	<br/>
		<input type="submit" value="Salvar"/>	<br/>
		<c:if test="${fornecedor.id ne 0}">
		</form>
		<form action="<c:url value='/informacaoBancaria/adicionar'/>" id="formInfoBancarias" name="formInfoBancarias" method="post">
		<input type="hidden" id=informacaoBancaria.fornecedor.id name="informacaoBancaria.fornecedor.id" value="${fornecedor.id}"/>
      	<h2>Informações Bancárias</h2>
      	<div class="row">
        	<div class="col-md-3">Banco: <br><input name="informacaoBancaria.banco"  size="35"/></div>
        	<div class="col-md-3">Agência:<br/><input name="informacaoBancaria.agencia"  size="20" maxlength="6"/></div>
        	<div class="col-md-3">Conta Corrente: <br/><input name="informacaoBancaria.conta"  size="20"/></div>
        	<div class="col-md-3">Tipo Conta: <br/>
        		
        		<select  id="informacaoBancaria.tipoConta" name="informacaoBancaria.tipoConta">
					<option value="" >--Selecione--</option>
						<option value="C" >Corrente</option>
						<option value="P" >Poupança</option>
						
				</select>
        	</div>
      	</div>
      	<div class="row">
        	<div class="col-md-4">Titular: <br><input name="informacaoBancaria.titular" value = "" size="45"/></div>
        	<div class="col-md-4">Cpf/Cnpj Titular:<br/><input name="informacaoBancaria.cpfCnpjTitular" value = "" size="20"/></div>
        	<div class="col-md-4"></div>
      	</div>
      	<br/>
      	
      	<button id="btnAdicionarInfBanc" type="button" class="btn btn-default btn-sm">
  		<span class="glyphicon glyphicon-plus"></span> Adicionar
		</button>
		<br/>
		<br/>
      	<table  class="table table-bordered table-striped" style="width: 690px">
		<thead>
			<tr>
				<th>Banco</th>
				<th>Agência</th>
				<th>Conta</th>
				<th>Tipo de Conta</th>
				<th>Titular</th>
				<th>Cpf/Cnpj</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="informacaoBancaria" items="${fornecedor.informacoesBancarias}" varStatus="contador">
		<tr>
			<td>${informacaoBancaria.banco}</td>
			<td>${informacaoBancaria.agencia}</td>
			<td>${informacaoBancaria.conta}</td>
			<td>${informacaoBancaria.tipoConta eq 'P' ? 'Poupança' : 'Corrente'}</td>
			<td>${informacaoBancaria.titular}</td>
			<td>${informacaoBancaria.cpfCnpjTitular}</td>
		</tr>
		
		</c:forEach>
		</tbody>
	</table>
	</form>	
	<br/>
	
	<br/>
	<br/>
		</c:if>
</div>

