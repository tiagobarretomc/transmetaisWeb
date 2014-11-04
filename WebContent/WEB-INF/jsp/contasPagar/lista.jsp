<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$.validator.addMethod( "dateLessThanToday", function( value, element ) {
	   	    /* ... regra de validação ... */
	   	    var isValid = false;
	   	    
	   	   var dataPagamento = Date.parseExact(value,'dd/MM/yyyy');
	   	   var hoje = Date.today();
	   	  
	   	  
	   	 	if(dataPagamento.compareTo(hoje) <= 0){
	   	 		return true;
	   	 	}else{
	   	 		return false;
	   	 	}
	   	 	
	   	 	//alert(dataPagamento <= hoje );
	   	 	
	   	    return  isValid;
	   	 
	   	}, "A Data de pagamento deve ser menor ou igual a data atual." );
    	
    	function exibir(movimentacaoId){
    		alert(movimentacaoId);
    		
    		
    	}
    	
    	$(".btnDetalhe").on("click",function(event){
    		var indiceCampo = $(this).attr('id').split("_")[1];
    		$("#divResultado").load( '<c:url value="/contasPagar/"/>' + indiceCampo, {'_format':'json'});
    		$('#myModal').modal('show');
    	});
    	
    	$("#dataInicio").mask('99/99/9999');
    	$("#dataFim").mask('99/99/9999');
    	
    	
    	/* $('.datepicker').datepicker({
	   		language : 'pt-BR',
	   		autoclose : true,
	   		format : 'dd/mm/yyyy'
	   	 	
	   	}); */
    	
    	$("#btnFiltrar").click(function(){
    		
    		$("#divTabela").load( '<c:url value="/contasPagar/loadListaMovimentacao"/>', $('#formCompra').serialize() );
    	});
    	
    	
    	
    	
    });
 </script>
 
<div class="container">
		<br/>
		<h2>Contas a Pagar</h2>
		<br>
		<div class="alert alert-warning alert-dismissable" id="divError" style="display: none">Selecione um fornecedor!</div>
		<div class="panel panel-default">
  	<div class="panel-body">
		<form action="<c:url value='/compra/'/>" id="formCompra" name="formCompra" method="post">
		<input type="hidden" name="_format" value="json">
		<div class="row">
        	
        	<div class="col-md-3">
        	<label for="dataInicio">Data Início:</label>
        	
        		<input type="datetime"  name="dataInicio" id="dataInicio" class="datepicker form-control" data-date-format="dd/mm/yyyy" value="" />
					
        	</div>
        	<div class="col-md-3">
        	<label for="dataFim">Data Fim:</label>
        		<input type="datetime"  name="dataFim" id="dataFim" class="datepicker form-control" data-date-format="dd/mm/yyyy" value="" />
					
        	</div>
        </div>
       
        <br/>
        <button id="btnFiltrar" type="button" class="btn btn-default btn-sm">
  		<span class="glyphicon glyphicon-filter"></span> Filtrar
		</button>
        </form>
        </div>
        </div>
		
		
		<div id="divTabela">
		<table class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th ></th>
		<th >Data</th>
		<th >Descrição</th>
		<th >Valor</th>
		<th>Status</th>
		<th>Dt Pagamento</th>
		<th>Conta</th>
		
		
	</tr>
	</thead>
	<tbody>
	<c:set var="varCont" value="1" />
		<c:forEach var="conta" items="${contaAPagarList}" varStatus="contador">
	
		<tr>
			
			
				<td >
				<%--
					<a href="${pageContext.request.contextPath}/contasPagar/${movimentacao.id}"><span title="Detalhar" style="color: black;" class="glyphicon glyphicon-ok-sign"></span></a>
					
				 --%>
				
					<c:if test="${conta.status == 'A' }">
						<button class="btn btn-default btn-xs btnDetalhe" id="btnDetalhe_${conta.id}" type="button" >
							<span class="glyphicon glyphicon-ok-sign"></span>
						</button>
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${conta.dataPrevista}" type="date" pattern="dd/MM/yyyy"/>
					
				</td>
				<td>
					
						<c:if test="${conta.class.name  == 'br.com.transmetais.bean.ContaAPagarCompra'}">
							<b>Compra <fmt:formatNumber minIntegerDigits="4" value="${conta.compra.id}" groupingUsed="" /></b> Fornecedor: ${conta.compra.fornecedor.apelido } - ${conta.compra.fornecedor.nome }
						</c:if>
						<c:if test="${conta.class.name  == 'br.com.transmetais.bean.ContaAPagarAdiantamento'}">
							<b>Adiantamento <fmt:formatNumber minIntegerDigits="4" value="${conta.adiantamento.id}" groupingUsed="" /></b> Fornecedor: ${conta.adiantamento.fornecedor.apelido } - ${conta.adiantamento.fornecedor.nome }
						</c:if>
						<c:if test="${conta.class.name  == 'br.com.transmetais.bean.ContaAPagarDespesa'}">
							${conta.descricao }
						</c:if>
						
					 
				</td>
				
				
				
				<td>
						<c:set var="valor" value="0"/>
						<fmt:formatNumber value="${ conta.valor}" minFractionDigits="2" type="currency"/>
					</td>
					
				<td >
					${conta.status.descricao}
				</td>
				
				<td>
					<fmt:formatDate value="${conta.dataPagamento}" type="date" pattern="dd/MM/yyyy"/>
					
				</td>
				<td >
					${conta.conta.descricao}
				</td>
			
			</tr>
		</c:forEach>
	</tbody>
	</table>
	<br/>

	</div>
	</div>
	
	 <!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Confirmação de Pagamento</h4>
      </div>
      <div class="modal-body" id="divResultado">
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
        <button type="button" class="btn btn-primary" id="btnConfirmar">Salvar</button>
      </div>
    </div>
  </div>
</div>