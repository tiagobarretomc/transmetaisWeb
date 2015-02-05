<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	$(document).ready(function() {

		var options = new Array();
		options['language'] = 'pt-BR';
		$('.datepicker').datepicker(options);
		
		$('.selectpicker').selectpicker({
            //'selectedText': 'cat'
        });

		
		$("#bean\\.dataSaldoInicial").mask('99/99/9999');

		$("#btnAdicionar").click(function() {
			$("#formConta").submit();
		});
		
		

		$('#formConta').validate({

		});

		

		$("#bean\\.saldoInicial").priceFormat({
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
	<h2>Cadastro de Contas de Clientes</h2>
	<br />
	<div class="panel panel-default">
		<div class="panel-body">
			<form action="<c:url value='/contaCliente/add'/>" id="formConta"
				name="formConta" method="post">



				<div class="row">
					<div class="col-md-1">
						<label for="bean.id">Código:</label> <input name="bean.id"
							readonly="readonly" id="bean.id"
							value="<fmt:formatNumber minIntegerDigits="4" value="${bean.id}" groupingUsed="" />"
							class="form-control " size="8" maxlength="4" />
					</div>

					<div class="col-md-4">
			        	<label for="bean.contaContabil.id">Conta Contábil:</label>
			        		<select id="bean.contaContabil.id" name="bean.contaContabil.id" class="selectpicker form-control required" data-live-search="true">
									<option value =""></option>
									<c:forEach var="contaAux" items="${contasContabeis}" varStatus="contador">
									
										<option value ="${contaAux.id}" ${bean.contaContabil.id eq contaAux.id ? 'selected' : ''}>${contaAux.numero} - ${contaAux.descricao}</option>
					
									</c:forEach>	
							</select>
			        </div>
					
					<div class="col-md-7">
						<label for="bean.descricao">Descrição:</label> 
						<input name="bean.descricao"  id="bean.descricao"
							value="${bean.descricao }"
							class="form-control required" maxlength="100" />
					</div>

				</div>
				<div  class="row" >
					<div class="col-md-4">
						<label for="bean.banco">Cliente:</label> 
						<select id="bean.cliente.id" name="bean.cliente.id" class="selectpicker form-control required" data-live-search="true">
									<option value =""></option>
									<c:forEach var="cliente" items="${clientes}" varStatus="contador">
									
										<option value ="${cliente.id}" ${bean.cliente.id eq cliente.id ? 'selected' : ''}>${cliente.razaoSocial}</option>
					
									</c:forEach>	
							</select>
					</div>
					
					



				</div>
				
				<div class="row">
					<div class="col-md-3">
						<label for="bean.saldoInicial">Saldo Inicial:</label> <input ${ empty bean.id ? '' : 'readonly="readonly"' }
							name="bean.saldoInicial" id="bean.saldoInicial"
							value="<fmt:formatNumber value="${bean.saldoInicial}" minFractionDigits="2" type="number" />"
							class="form-control required" maxlength="18" />
					</div>
					<div class="col-md-2">
						<label for="bean.dataSaldoInicial">Dt. Saldo Inicial:</label> 
						<input name="bean.dataSaldoInicial" ${ empty bean.id ? '' : 'readonly="readonly"' } id="bean.dataSaldoInicial"value="<fmt:formatDate value="${bean.dataSaldoInicial }" type="date" pattern="dd/MM/yyyy"/>" class="datepicker form-control required" data-date-format="dd/mm/yyyy" />
					</div>
					<div class="col-md-3">
						<label for="bean.saldo">Saldo Atual:</label> <input 
							name="bean.saldo" id="bean.saldo" readonly="readonly"
							value="<fmt:formatNumber value="${bean.saldo}" minFractionDigits="2" type="number" />"
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