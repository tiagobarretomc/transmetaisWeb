<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	$(document).ready(function() {

		var options = new Array();
		options['language'] = 'pt-BR';
		$('.datepicker').datepicker(options);
		
		$("#conta\\.dataSaldoInicial").mask('99/99/9999');

		
		$('.selectpicker').selectpicker({});
		
		$("#btnAdicionar").click(function() {
			$("#formConta").submit();
		});
		
		$("#conta\\.bancaria").click(function(){
			
			
			if($(this).is(':checked')){
				
				$("#divDadosBancarios").show();
				
			}else{
				$("#divDadosBancarios").hide();
				
			}
		});

		$('#formConta').validate({

		});

		

		$("#conta\\.saldoInicial").priceFormat({
			prefix : '',
			centsSeparator : ',',
			thousandsSeparator : '.',
			allowNegative: true,
			limit : 12

		});
		
		$("#conta\\.limite").priceFormat({
			prefix : '',
			centsSeparator : ',',
			thousandsSeparator : '.',
			allowNegative: true,
			limit : 12

		});
		
		
		

	});
</script>

<div class="container">
	<br>
	<h2>Cadastro de Contas de Fornecedores</h2>
	<br />
	<div class="panel panel-default">
		<div class="panel-body">
			<form action="<c:url value='/contaFornecedor/add'/>" id="formConta"
				name="formConta" method="post">



				<div class="row">
					<div class="col-md-1">
						<label for="conta.id">Código:</label> <input name="conta.id"
							readonly="readonly" id="conta.id"
							value="<fmt:formatNumber minIntegerDigits="4" value="${conta.id}" groupingUsed="" />"
							class="form-control " size="8" maxlength="4" />
					</div>

					<div class="col-md-4">
			        	<label for="conta.contaContabil.id">Conta Contábil:</label>
			        		<select id="conta.contaContabil.id" name="conta.contaContabil.id" class="selectpicker form-control required" data-live-search="true">
									<option value ="">Selecione</option>
									<c:forEach var="contaAux" items="${contas}" varStatus="contador">
									
										<option value ="${contaAux.id}" ${conta.contaContabil.id eq contaAux.id ? 'selected' : ''}>${contaAux.numero} - ${contaAux.descricao}</option>
					
									</c:forEach>	
							</select>
			        </div>
					
					<div class="col-md-7">
						<label for="conta.descricao">Descrição:</label> 
						<input name="conta.descricao"  id="conta.descricao"
							value="${conta.descricao }"
							class="form-control required" maxlength="100" />
					</div>

				</div>
				<div  class="row" id="divDadosBancarios">
					<div class="col-md-4">
						<label for="conta.fornecedor">Fornecedor:</label>
						<c:if test="${ empty conta.id }">
			        	<select id="conta.fornecedor.id" name="conta.fornecedor.id" class="selectpicker form-control" data-live-search="true">
							<option value ="">Selecione</option>
							<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
								<option value ="${fornecedor.id}" ${conta.fornecedor.id eq fornecedor.id ? 'selected' : ''}>${fornecedor.apelido} - ${fornecedor.nome}</option>
							</c:forEach>	
						</select>
						</c:if>
						<c:if test="${not empty conta.id }">
							<input type="text" value="${conta.fornecedor.apelido } - ${conta.fornecedor.nome }" disabled="disabled" class="form-control"/>
							<input type="hidden" id="conta.fornecedor.id" name="conta.fornecedor.id" value="${conta.fornecedor.id }"/>
							
						</c:if>
						
					</div>
					<div class="col-md-4">
						<label for="conta.limite">Limite de Crédito:</label> 
						<input 
								name="conta.limite" id="conta.limite"
								value="<fmt:formatNumber value="${conta.limite}" minFractionDigits="2" type="number" />"
								class="form-control " maxlength="18" />
						
					</div>
					

				</div>
				
				<div class="row">
					<div class="col-md-3">
						<label for="conta.saldoInicial">Saldo Inicial:</label> <input ${ empty conta.id ? '' : 'readonly="readonly"' }
							name="conta.saldoInicial" id="conta.saldoInicial"
							value="<fmt:formatNumber value="${conta.saldoInicial}" minFractionDigits="2" type="number" />"
							class="form-control " maxlength="18" />
					</div>
					<div class="col-md-2">
						<label for="conta.dataSaldoInicial">Dt. Saldo Inicial:</label> 
						<input name="conta.dataSaldoInicial" ${ empty conta.id ? '' : 'readonly="readonly"' } 
						id="conta.dataSaldoInicial"value="<fmt:formatDate value="${conta.dataSaldoInicial }" type="date" 
						pattern="dd/MM/yyyy"/>" class="datepicker form-control" data-date-format="dd/mm/yyyy" />
					</div>
					<div class="col-md-3">
						<label for="conta.saldo">Saldo Atual:</label> <input 
							name="conta.saldo" id="conta.saldo" readonly="readonly"
							value="<fmt:formatNumber value="${conta.saldo}" minFractionDigits="2" type="number" />"
							class="form-control " maxlength="18" />
					</div>
				</div>

				<br />
				<button type="button" id="btnAdicionar" 
					class="btn btn-default btn-md">
					<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
				</button>
			</form>
		</div>
	</div>
</div>