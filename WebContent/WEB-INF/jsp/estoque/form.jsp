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

		
		$("#bean\\.data").mask('99/99/9999');

		$("#btnAdicionar").click(function() {
			$("#formConta").submit();
		});
		
		

		$('#formConta').validate({

		});

		

		$("#bean\\.valor").priceFormat({
			prefix : '',
			centsSeparator : ',',
			thousandsSeparator : '.',
			allowNegative: true,
			limit : 12

		});
		
		$("#bean\\.quantidade").priceFormat({
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
	<h2>Ajuste de Estoque de Materiais</h2>
	<br />
	<div class="panel panel-default">
		<div class="panel-body">
			<form action="<c:url value='/estoque/adicionar'/>" id="formConta"
				name="formConta" method="post">



				<div class="row">
					

					<div class="col-md-4">
			        	<label for="bean.material.id">Material:</label>
			        		<select id="bean.material.id" name="bean.material.id" class="selectpicker form-control required" data-live-search="true">
									<option value =""></option>
									<c:forEach var="material" items="${materiais}" varStatus="contador">
									
										<option value ="${material.id}" >${material.descricao}</option>
					
									</c:forEach>	
							</select>
			        </div>
			        <div class="col-md-2">
		        		<label for="bean.data">Data:</label>
		        		<input name="bean.data" id="bean.data" value="" class="form-control required datepicker" size="8" maxlength="10" />
		        	</div>
					
					<div class="col-md-2">
						<label for="bean.quantidade">Quantidade:</label> 
						<input name="bean.quantidade"  id="bean.quantidade"
							value="${bean.quantidade }"
							class="form-control required" maxlength="100" />
					</div>

					<div class="col-md-2">
						<label for="bean.valor">Valor:</label> 
						<input name="bean.valor"  id="bean.valor"
							value="${bean.valor }"
							class="form-control required" maxlength="100" />
					</div>
					
					<div class="col-md-2">
						<label for="bean.tipoMovimentacao">Tipo Movimentação:</label>
						<select style="width: 180px;" id="bean.tipoMovimentacao" name="bean.tipoMovimentacao" class="selectpicker form-control required" >
							<option value="" >Selecione</option>
							<c:forEach var="tipo" items="${listaTipos}">
								<option value="${tipo.name }" >${tipo.descricao}</option>
							</c:forEach>
						</select>
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