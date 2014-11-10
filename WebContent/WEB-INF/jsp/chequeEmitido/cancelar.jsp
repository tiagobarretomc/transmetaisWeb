<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	$(document).ready(function() {

		$("#bean\\.dataStatus").mask('99/99/9999');
		
		$(".datepicker").datepicker({
	       	 format: "dd/mm/yyyy",
	       	 laguage: "pt-BR"
	    	});
		
		$('#formCheque').validate({
	  		
			ignore: ':not(select:hidden, input:visible, textarea:visible)',
         	
          	errorPlacement: function (error, element) {
                 if ($(element).is('select')) {
                     element.next().after(error); // special placement for select elements
                 } else {
                     error.insertAfter(element);  // default placement for everything else
                 }
             },
             
         
     	});
	
	});
</script>

<div class="container">
	<br>
	<h2>Cancelamento de Cheques Emitidos</h2>
	<br />
	<div class="panel panel-default">
  	<div class="panel-body">
			<h4 style="margin-top: 0px">Origem do Cheque: <br/>Adiantamento a Fornecedor</h4>
		<c:if test="${bean.class.name  == 'br.com.transmetais.bean.ChequeEmitidoAdiantamento'}" >
				<div class="row">
						<div class="col-md-1">
							<label for="bean.adiantamento.id">Cód.:</label> 
						<input name="" id="bean.adiantamento.id" value="${bean.adiantamento.id }" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-4">
							<label for="bean.adiantamento.fornecedor.nome">Fornecedor:</label> 
						<input name="" id="bean.adiantamento.fornecedor.nome" value="${bean.adiantamento.fornecedor.apelido} - ${bean.adiantamento.fornecedor.nome}" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="bean.adiantamento.valor">Valor:</label> 
						<input name="" id="bean.adiantamento.valor" value="<fmt:formatNumber value="${bean.adiantamento.valor}" minFractionDigits="2" type="currency"/>" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="bean.adiantamento.dataInclusao">Dt Inclusão:</label> 
						<input name="" id="bean.adiantamento.dataInclusao" value="<fmt:formatDate value="${bean.adiantamento.dataInclusao}" type="date" pattern="dd/MM/yyyy"/>" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
						<div class="col-md-2">
							<label for="bean.adiantamento.dataPagamento">Dt Pagamento:</label> 
						<input name="" id="bean.adiantamento.dataPagamento" value="<fmt:formatDate value="${bean.adiantamento.dataPagamento}" type="date" pattern="dd/MM/yyyy"/>" class="form-control "  maxlength="10" readonly="readonly"/>
						</div>
				</div>
		</c:if>
		
	</div>
	</div>
	<div class="panel panel-default">
		
		<div class="panel-body">
			<form action="<c:url value='/chequeEmitido/confirmarCancelamento'/>" id="formCheque"
				name="formCheque" method="post">
				
				<input type="hidden" name="bean.id" value="${bean.id }"/>


				<div class="row">
				<div class="col-md-2">
						<label for="bean.id">Número Cheque:</label> 
						<input name="" id="bean.numeroCheque" value="${bean.numeroCheque }" class="form-control "  readonly="readonly" />
							
							
					</div>
					<div class="col-md-2">
						<label for="bean.id">Conta Bancária:</label> 
						<input name="" id="bean.conta.descricao" value="${bean.conta.descricao }" class="form-control "  readonly="readonly" />
							
							
					</div>
					<div class="col-md-2">
						<label for="bean.id">Data Emissão:</label> 
						<input name="" id="bean.data" value='<fmt:formatDate value="${bean.data}" type="date" pattern="dd/MM/yyyy" />' class="form-control "  readonly="readonly" />
							
							
					</div>
					
					<div class="col-md-2">
						<label for="bean.id">Valor do Cheque:</label> 
						<input name="" id="bean.valor" value="<fmt:formatNumber value="${bean.valor }" minFractionDigits="2" type="currency" />" class="form-control "  readonly="readonly" />
							
							
					</div>
					<div class="col-md-2">
						<label for="bean.id">Data Cancelamento:</label> 
						<input name="bean.dataStatus" id="bean.dataStatus" value="" class="form-control datepicker required"  maxlength="10" />
							
							
					</div>
					</div>
					<div class="row">
					<div class="col-md-8">
						<label for="bean.motivoCancelamento">Motivo Cancelamento:</label> 
						
						<textarea rows="4"   name="bean.motivoCancelamento" id="bean.motivoCancelamento" class="form-control required"></textarea>	
							
					</div>

					
					
					

				</div>

				
				



				<br />
				<button type="submit" id="btnSalvar" 
					class="btn btn-default btn-md">
					<span class="glyphicon glyphicon-floppy-disk"></span> Confirmar
				</button>
			</form>
		</div>
	</div>
</div>