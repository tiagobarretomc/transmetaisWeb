<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$('.selectpicker').selectpicker({
            'selectedText': 'cat'
        });
    	
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
				 	    	var jsonObject = eval(json);
				 	    	//alert(jsonObject);
				 	    	var cidades = jsonObject.list;
				 	    	//alert(cidades); 
				 	    	
				 	    	var html = "";  
				 	       html += "<select name='fornecedor.cidade.id' class='selectpicker form-control' data-live-search='true'>" ;  
				 	       for(i=0;i<cidades.length;i++) {  
				 	           html += "<option value='"+cidades[i].id +"'>"+cidades[i].nome+"</option>";                           
				 	       }  
				 	       html += "</select> " ;  
				 	       var div = document.getElementById("ajaxResultDiv");  
				 	       div.innerHTML = html; 
				 	      $('.selectpicker').selectpicker({
				 	            'selectedText': 'cat'
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
    	
    	
    	
    	$("#telefoneFixo").mask("(99) 9999-9999");
    	$("#telefoneCelular").mask("(99) 9999-9999");
    	
        $('#formFornecedor').validate({
            
        
    	});
    });
</script>

    <div class="container">
    <br>
	<h2>Dados do Fornecedor</h2>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/fornecedor/adiciona'/>" id="formFornecedor" name="formFornecedor" method="post" role="form">
		
		<input type="hidden" id="fornecedor.conta.id" name="fornecedor.conta.id" value="${fornecedor.conta.id}"/>
		
		
		<div class="row">
			<div class="col-md-1">
        		<label for="fornecedor.nome">Código:</label>
        		<input name="fornecedor.id" readonly="readonly" id="fornecedor.id" value="<fmt:formatNumber minIntegerDigits="4" value="${fornecedor.id}" groupingUsed="" />" class="form-control required"  placeholder=""/>
        	</div>
        	<div class="col-md-4">
        		<label for="fornecedor.nome">Nome:</label>
        		<input name="fornecedor.nome" id="fornecedor.nome" value="${fornecedor.nome}" class="form-control required"  placeholder="Nome do Fornecedor"/>
        	</div>
        	<div class="col-md-4">
        	<label for="fornecedor.apelido">Contato:</label>
        	<input name="fornecedor.apelido" id="fornecedor.apelido" value="${fornecedor.apelido}" class="form-control required" size="20" placeholder="Apelido ou Contato do fornecedor"/></div>
        	<div class="col-md-1">
        	
        		<div class="checkbox">
				    <label>
				      <input type="checkbox" name="fornecedor.status"  value="A" ${fornecedor.status eq 'A' ? 'checked="checked"': '' }> Ativo
				    </label>
				  </div>
        		
        	</div>
        	<div class="col-md-2">
        	<label >Tipo Documento:</label><br/>
        		
        		<div class="radio-inline">
				  <label class="radio-inline">
				    <input type="radio" name="tipoPessoa" value="F" id="optTipoPessoaFisica"/> CPF
				  </label>
				  
				</div>
				<div class="radio-inline">
				  
				  <label class="radio-inline">
				    <input type="radio" name="tipoPessoa" value="J" id="optTipoPessoaJuridica"/> CNPJ
				  </label>
				</div>
        		
				
			</div>
      	</div>
		<div class="row">
        	
        	<div class="col-md-2">
        		<label for="fornecedor.cpfCnpj">Cpf/Cnpj:</label>
        		<input id="cpfCnpj" name="fornecedor.cpfCnpj" value = "${fornecedor.cpfCnpj }" size="20"class="form-control required"/>
        	</div>
        	<div class="col-md-2">
        		<label for="fornecedor.inscricaoEstadual">Insc.Estadual:</label>
        		<input id="fornecedor.inscricaoEstadual" name="fornecedor.inscricaoEstadual" value = "${fornecedor.inscricaoEstadual }" size="20"class="form-control "/>
        	</div>
        	<div class="col-md-4">
        	<label for="fornecedor.email">Email:</label>
        	<input name="fornecedor.email" id="fornecedor.email" value = "${fornecedor.email }" size="45" class="form-control"/></div>
        	<div class="col-md-2">
        	<label for="fornecedor.telefoneFixo">Telefone:</label>
        	<input id="telefoneFixo" name="fornecedor.telefoneFixo" id="fornecedor.telefoneFixo" value = "${fornecedor.telefoneFixo}" class="form-control" size="20"/></div>
        	<div class="col-md-2">
        	<label for="fornecedor.telefoneCelular">Celular:</label>
        	<input id="telefoneCelular"  name="fornecedor.telefoneCelular" id="fornecedor.telefoneCelular" value="${ fornecedor.telefoneCelular}" class="form-control required" size="20"/></div>
        	
        	
      	</div>
      	
      	<div class="row">
			<div class="col-md-4">
        		<label for="fornecedor.logradouro">Logradouro:</label>
        		<input name="fornecedor.logradouro" id="fornecedor.logradouro" value="${fornecedor.logradouro}" class="form-control required"  maxlength="100"/>
        	</div>
        	<div class="col-md-1">
        		<label for="fornecedor.numero">Número:</label>
        		<input name="fornecedor.numero" id="fornecedor.numero" value="${fornecedor.numero}" class="form-control required"  maxlength="7"/>
        	</div>
        	<div class="col-md-2">
        		<label for="fornecedor.bairro">Bairro:</label>
        		<input name="fornecedor.bairro" id="fornecedor.bairro" value="${fornecedor.bairro}" class="form-control required"  maxlength="45"/>
        	</div>
        	<div class="col-md-3">
        		<label for="fornecedor.complemento">Complemento:</label>
        		<input name="fornecedor.complemento" id="fornecedor.bairro" value="${fornecedor.complemento}" class="form-control required"  maxlength="45"/>
        	</div>
        	
        	<div class="col-md-2">
        		<label for="estado">Estado:</label>
					<select id="estado" name="estado" class="selectpicker required form-control" data-live-search="true">
						<option value ="">Selecione</option>
						<c:forEach var="estado" items="${estados}" varStatus="contador">
						
							<option value ="${estado.id}" ${fornecedor.cidade.estado.id eq estado.id ? 'selected' : ''}>${estado.nome}</option>
		
						</c:forEach>	
					</select></div>
        	
		</div>
      	
      	
      	
      	<div class="row">
      	
      	<div class="col-md-3">
        	<label for="fornecedor.cidade.id">Cidade:</label>
        	<div id="ajaxResultDiv">
					<select name="fornecedor.cidade.id"  id="fornecedor.cidade.id" class="selectpicker form-control" data-live-search="true"> 
						<option value="">Selecione</option>
						
						<c:forEach var="cidade" items="${fornecedor.cidade.estado.cidades}" varStatus="contador">
						
							<option value ="${cidade.id}" ${fornecedor.cidade.id eq cidade.id ? 'selected' : ''}>${cidade.nome}</option>
		
						</c:forEach>
						
						
					</select>
 				</div></div>
        	<div class="col-md-8">
        	<label for="fornecedor.endereco">Endereço:</label>
        	<input name="fornecedor.endereco" value = "${fornecedor.endereco }" size="45" class=" form-control"/></div>
        	
      	</div>
      	<br/>
		<input type="submit" value="Salvar" class="btn btn-default"/>	<br/>
		<c:if test="${fornecedor.id ne 0}">
		</form>
		</div>
		</div>
		<div class="panel panel-default">
	<div class="panel-body">
		<form action="<c:url value='/informacaoBancaria/adicionar'/>" id="formInfoBancarias" name="formInfoBancarias" method="post">
		<input type="hidden" id=informacaoBancaria.fornecedor.id name="informacaoBancaria.fornecedor.id" value="${fornecedor.id}"/>
      	<h2>Informações Bancárias</h2>
      	<div class="row">
        	<div class="col-md-3">Banco: <br><input name="informacaoBancaria.banco"  size="35" class="form-control"/></div>
        	<div class="col-md-3">Agência:<br/><input name="informacaoBancaria.agencia"  size="20" maxlength="6" class="form-control"/></div>
        	<div class="col-md-3">Conta Corrente: <br/><input name="informacaoBancaria.conta"  size="20" class="form-control"/></div>
        	<div class="col-md-3">Tipo Conta: <br/>
        		
        		<select  id="informacaoBancaria.tipoConta" name="informacaoBancaria.tipoConta" class="selectpicker form-control">
					<option value="" >--Selecione--</option>
						<option value="C" >Corrente</option>
						<option value="P" >Poupança</option>
						
				</select>
        	</div>
      	</div>
      	<div class="row">
        	<div class="col-md-4">Titular: <br><input name="informacaoBancaria.titular" value = "" size="45" class="form-control"/></div>
        	<div class="col-md-4">Cpf/Cnpj Titular:<br/><input name="informacaoBancaria.cpfCnpjTitular" value = "" size="20" class="form-control"/></div>
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
	</div>
	</div>
	<br/>
	
	<br/>
	<br/>
		</c:if>
</div>

