<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						if ('${cliente.cpfCnpj}'.length <= 14) {
							$("#cpfCnpj").mask('999.999.999-99');
						} else {
							$("#cpfCnpj").mask('99.999.999/9999-99');
						}

						$("#btnAdicionarInfBanc").click(function() {
							$("#formInfoBancarias").submit();
						});

						$('#optTipoPessoaFisica').click(function() {
							$("#cpfCnpj").mask('999.999.999-99');
						});

						$('#optTipoPessoaJuridica').click(function() {
							$("#cpfCnpj").mask('99.999.999/9999-99');
						});

						$('#estado').change(function(){
					    	
					        if($(this).val()) {
					        		$.ajax({
							        type: 'GET',
							        url: '${pageContext.request.contextPath}/endereco/loadCidades?_format=json',
							        data:	{id: $("#estado").val()},
							 	    success: function(json){
							 	    	//alert(json);
							 	    	var jsonObject = eval(json);
							 	    	//alert(jsonObject);
							 	    	var cidades = jsonObject.list;
							 	    	//alert(cidades); 
							 	    	
							 	    	var html = "";  
							 	       html += "<select name='cliente.cidade.id' class='selectpicker form-control' data-live-search='true'>" ;  
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
	<h2>Dados do Cliente</h2>
	<div class="panel panel-default">
		<div class="panel-body">
			<form action="<c:url value='/cliente/adicionar'/>" id="formCliente"
				name="formCliente" method="post" >

				<input type="hidden" id="cliente.id" name="cliente.id"
					value="${cliente.id}" />


				<div class="row">
					<div class="col-md-4">
						<label for="cliente.razaoSocial">Razão Social:</label> <input
							name="cliente.razaoSocial" id="cliente.razaoSocial"
							value="${cliente.razaoSocial}" size="45"
							class="form-control required" />
					</div>
					<div class="col-md-4">
						<label for="cliente.apelido">Apelido:</label> <input
							name="cliente.apelido" id="cliente.apelido"
							value="${cliente.apelido}" class="form-control required"
							size="20" placeholder="Apelido ou Contato do cliente" />
					</div>
					<div class="col-md-1">

						<div class="checkbox">
							<label> <input type="checkbox" name="cliente.status"
								value="A" ${cliente.status eq 'A' ? 'checked="checked"': '' }>
								Ativo
							</label>
						</div>

					</div>
				</div>
				<div class="row">
					
					<div class="col-md-3">
						<label>Tipo Documento:</label><br />

						<div class="radio-inline">
							<label class="radio-inline"> <input type="radio"
								name="tipoPessoa" value="F" id="optTipoPessoaFisica" /> CPF
							</label>

						</div>
						<div class="radio-inline">

							<label class="radio-inline"> <input type="radio"
								name="tipoPessoa" value="J" id="optTipoPessoaJuridica" /> CNPJ
							</label>
						</div>


					</div>
					<div class="col-md-2">
						<label for="cliente.cpfCnpj">Cpf/Cnpj:</label> <input id="cpfCnpj"
							name="cliente.cpfCnpj" value="${cliente.cpfCnpj }" size="20"
							class="form-control required" />
					</div>
				</div>
				<div class="row">

					
					<div class="col-md-2">
						<label for="cliente.inscricaoEstadual">Insc.Estadual:</label> <input
							id="cliente.inscricaoEstadual" name="cliente.inscricaoEstadual"
							value="${cliente.inscricaoEstadual }" size="20"
							class="form-control " />
					</div>
					<div class="col-md-4">
						<label for="cliente.email">Email:</label> <input
							name="cliente.email" id="cliente.email" value="${cliente.email }"
							size="45" class="form-control" />
					</div>
					<div class="col-md-2">
						<label for="cliente.telefoneFixo">Telefone:</label> <input
							id="telefoneFixo" name="cliente.telefoneFixo"
							id="cliente.telefoneFixo" value="${cliente.telefoneFixo}"
							class="form-control" size="20" />
					</div>
					<div class="col-md-2">
						<label for="cliente.telefoneCelular">Celular:</label> <input
							id="telefoneCelular" name="cliente.telefoneCelular"
							id="cliente.telefoneCelular" value="${ cliente.telefoneCelular}"
							class="form-control required" size="20" />
					</div>


				</div>

				<div class="row">
					<div class="col-md-4">
						<label for="cliente.logradouro">Logradouro:</label> <input
							name="cliente.logradouro" id="cliente.logradouro"
							value="${cliente.logradouro}" class="form-control required"
							maxlength="100" />
					</div>
					<div class="col-md-1">
						<label for="cliente.numero">Número:</label> <input
							name="cliente.numero" id="cliente.numero"
							value="${cliente.numero}" class="form-control required"
							maxlength="7" />
					</div>
					<div class="col-md-2">
						<label for="cliente.bairro">Bairro:</label> <input
							name="cliente.bairro" id="cliente.bairro"
							value="${cliente.bairro}" class="form-control required"
							maxlength="45" />
					</div>

					<div class="col-md-2">
						<label for="cliente.cep">Cep:</label> <input name="cliente.cep"
							id="cliente.cep" value="${cliente.cep}" class="form-control "
							maxlength="10" />
					</div>

				</div>



				<div class="row">

					<div class="col-md-3">
						<label for="estado">Estado:</label> <select id="estado"
							name="estado" class="selectpicker required form-control"
							data-live-search="true">
							<option value="">Selecione</option>
							<c:forEach var="estado" items="${estados}" varStatus="contador">

								<option value="${estado.id}"
									${cliente.cidade.estado.id eq estado.id ? 'selected' : ''}>${estado.nome}</option>

							</c:forEach>
						</select>
					</div>

					<div class="col-md-4">
						<label for="cliente.cidade.id">Cidade:</label>
						<div id="ajaxResultDiv">
							<select name="cliente.cidade.id" id="cidade"
								class="selectpicker form-control required"
								data-msg-required="Campo obrigatório" data-live-search="true">
								<option value="">Selecione</option>

								<c:forEach var="cidade" items="${cliente.cidade.estado.cidades}"
									varStatus="contador">

									<option value="${cidade.id}"
										${cliente.cidade.id eq cidade.id ? 'selected' : ''}>${cidade.nome}</option>

								</c:forEach>


							</select>
						</div>
					</div>

				</div>
				<br /> <input type="submit" value="Salvar" class="btn btn-default" />
				<br />

			</form>
		</div>
	</div>
	
</div>

