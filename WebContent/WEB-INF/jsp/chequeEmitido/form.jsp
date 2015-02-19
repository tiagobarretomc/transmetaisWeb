<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	$(document).ready(function() {
		
		initFields();
		
		$("#btnVoltar").click(function(){
			
			window.location.href = "<c:url value='/chequeEmitido/lista'/>";
			
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
	<h2>Confirmação de Cheques Emitidos Compensados</h2>
	<br />
	<div class="panel panel-default">
  	<div class="panel-body">
			<h4 style="margin-top: 0px">Origem do Cheque: <br/></h4>
		<c:if test="${bean.class.name  == 'br.com.transmetais.bean.ChequeEmitidoAdiantamento'}" >
		<h4 style="margin-top: 0px">Adiantamento a Fornecedor</h4>
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
		<c:if test="${bean.class.name  == 'br.com.transmetais.bean.ChequeEmitidoDespesa'}" >
			<h4 style="margin-top: 0px">Despesa</h4>
			<div class="row">
			<div class="col-md-1">
					<label for="bean.despesa.id">Cód.:</label> 
				<input name="" id="bean.despesa.id" value="${bean.despesa.id }" class="form-control "  maxlength="10" readonly="readonly"/>
				</div>
				<div class="col-md-4">
					<label for="bean.despesa.descricao">Descrição:</label> 
				<input name="" id="bean.despesa.descricao" value="${bean.despesa.descricao}" class="form-control "  maxlength="10" readonly="readonly"/>
				</div>
				<div class="col-md-2">
					<label for="bean.despesa.valor">Valor:</label> 
				<input name="" id="bean.despesa.valor" value="<fmt:formatNumber value="${bean.despesa.valor}" minFractionDigits="2" type="currency"/>" class="form-control "  maxlength="10" readonly="readonly"/>
				</div>
				<div class="col-md-2">
					<label for="bean.despesa.dataCompetencia">Dt Competência:</label> 
				<input name="" id="bean.despesa.dataCompetencia" value='<fmt:formatDate value="${bean.despesa.dataCompetencia}" type="date" pattern="dd/MM/yyyy"/>' class="form-control "  maxlength="10" readonly="readonly"/>
				</div>
				<div class="col-md-3">
					<label for="bean.despesa.dataCompetencia">Centro de Aplicação:</label> 
				<input name="" id="bean.despesa.dataCompetencia" value='${bean.despesa.centroAplicacao.descricao}' class="form-control "   readonly="readonly"/>
				</div>
				</div>
				<c:if test="${not empty bean.parcela}">
				<br/>
				<div class="panel panel-default">
	  				<div class="panel-body">
		  				<div class="row">
							<div class="col-md-1">
								<label for="bean.despesa.centroAplicacao.descricao">Parcela:</label> 
								<input name="" id="bean.despesa.centroAplicacao.descricao" value="${bean.parcela.numero}/${fn:length(bean.despesa.parcelas)}" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							
							<div class="col-md-2">
								<label for="bean.despesa.centroAplicacao.descricao">Vencimento:</label> 
								<input name="" id="bean.despesa.centroAplicacao.descricao" value='<fmt:formatDate value="${bean.parcela.dataVencimento}" type="date" pattern="dd/MM/yyyy"/>' class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							
							<div class="col-md-2">
								<label for="bean.parcela.valor">Valor:</label> 
							<input name="" id="bean.parcela.valor" value="<fmt:formatNumber value="${bean.parcela.valor}" minFractionDigits="2" type="currency"/>" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
						</div>
					</div>
				</div>
				</c:if>
		</c:if>
		
		
		
		
		
		
		
		<c:if test="${bean.class.name  == 'br.com.transmetais.bean.ChequeEmitidoCompra'}" >
			<h4 style="margin-top: 0px">Compra</h4>
			<div class="row">
			<div class="col-md-1">
					<label for="bean.compra.id">Cód.:</label> 
				<input name="" id="bean.compra.id" value="${bean.compra.id }" class="form-control "  maxlength="10" readonly="readonly"/>
				</div>
				<div class="col-md-4">
					<label for="bean.compra.fornecedor.nome">Fornecedor:</label> 
				<input name="" id="bean.compra.fornecedor.nome" value="${bean.compra.fornecedor.apelido} - ${bean.compra.fornecedor.nome}" class="form-control "  maxlength="10" readonly="readonly"/>
				</div>
				<div class="col-md-2">
					<label for="bean.compra.valor">Valor:</label> 
				<input name="" id="bean.compra.valor" value="<fmt:formatNumber value="${bean.compra.valor}" minFractionDigits="2" type="currency"/>" class="form-control "  maxlength="10" readonly="readonly"/>
				</div>
				<div class="col-md-2">
					<label for="bean.compra.dataCompetencia">Dt Competência:</label> 
				<input name="" id="bean.compra.dataCompetencia" value='<fmt:formatDate value="${bean.compra.dataCompetencia}" type="date" pattern="dd/MM/yyyy"/>' class="form-control "  maxlength="10" readonly="readonly"/>
				</div>
				<div class="col-md-3">
					<label for="bean.compra.centroAplicacao.descricao">Centro de Aplicação:</label> 
				<input name="" id="bean.compra.centroAplicacao.descricao" value='${bean.compra.centroAplicacao.descricao}' class="form-control "   readonly="readonly"/>
				</div>
				</div>
				<c:if test="${not empty bean.parcela}">
				<br/>
				<div class="panel panel-default">
	  				<div class="panel-body">
		  				<div class="row">
							<div class="col-md-1">
								<label for="bean.parcela.numero">Parcela:</label> 
								<input name="" id="bean.parcela.numero" value="${bean.parcela.numero}/${fn:length(bean.despesa.parcelas)}" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							
							<div class="col-md-2">
								<label for="bean.parcela.dataVencimento">Vencimento:</label> 
								<input name="" id="bean.parcela.dataVencimento" value='<fmt:formatDate value="${bean.parcela.dataVencimento}" type="date" pattern="dd/MM/yyyy"/>' class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
							
							<div class="col-md-2">
								<label for="bean.parcela.valor">Valor:</label> 
							<input name="" id="bean.parcela.valor" value="<fmt:formatNumber value="${bean.parcela.valor}" minFractionDigits="2" type="currency"/>" class="form-control "  maxlength="10" readonly="readonly"/>
							</div>
						</div>
					</div>
				</div>
				</c:if>
		</c:if>
		
		
		
	</div>
	</div>
	<div class="panel panel-default">
		
		<div class="panel-body">
			<form action="<c:url value='/chequeEmitido/aprovar'/>" id="formCheque"
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
					</div>
					<div class="row">
					<div class="col-md-2">
						<label for="bean.id">Data Compensação:</label> 
						<input name="bean.dataStatus" id="bean.dataStatus" value="" class="form-control datepicker required dateLessThanToday"  maxlength="10" />
					</div>
				</div>

				<br />
				<button type="submit" id="btnSalvar" 
					class="btn btn-default btn-md">
					<span class="glyphicon glyphicon-floppy-disk"></span> Confirmar
				</button>
				<button type="button" id="btnVoltar" 
					class="btn btn-default btn-md">
					Voltar
				</button>
			</form>
		</div>
	</div>
</div>