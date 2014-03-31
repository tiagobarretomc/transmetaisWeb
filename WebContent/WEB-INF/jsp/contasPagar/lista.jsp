<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	function exibir(movimentacaoId){
    		alert(movimentacaoId);
    		
    		
    	}
    	
    	jQuery(".btnDetalhe").on("click",function(event){
    		var indiceCampo = $(this).attr('id').split("_")[1];
    		$("#divResultado").load( '<c:url value="/contasPagar/"/>' + indiceCampo, {'_format':'json'});
    		$('#myModal').modal('show');
    	});
    	
    	$("#dataInicio").mask('99/99/9999');
    	$("#dataFim").mask('99/99/9999');
    	
    	var options = new Array();
    	 options['language'] = 'pt-BR';
    	 $('.datepicker').datepicker(options);
    	
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
		<c:forEach var="movimentacao" items="${movimentacaoList}" varStatus="contador">
	
		<tr>
			
			
				<td >
				<%--
					<a href="${pageContext.request.contextPath}/contasPagar/${movimentacao.id}"><span title="Detalhar" style="color: black;" class="glyphicon glyphicon-ok-sign"></span></a>
					
				 --%>
				
				<button class="btn btn-default btn-xs btnDetalhe" id="btnDetalhe_${movimentacao.id}" type="button" >
					<span class="glyphicon glyphicon-ok-sign"></span>
					
					</button>
				</td>
				<td>
					<fmt:formatDate value="${movimentacao.data}" type="date" pattern="dd/MM/yyyy"/>
					
				</td>
				<td>
					
						<c:if test="${movimentacao.class.name  == 'br.com.transmetais.bean.MovimentacaoCompra'}">
							<b>Compra <fmt:formatNumber minIntegerDigits="4" value="${movimentacao.compra.id}" groupingUsed="" /></b> Fornecedor: ${movimentacao.compra.fornecedor.apelido } - ${movimentacao.compra.fornecedor.nome }
						</c:if>
						<c:if test="${movimentacao.class.name  == 'br.com.transmetais.bean.MovimentacaoAdiantamento'}">
							<b>Adiantamento <fmt:formatNumber minIntegerDigits="4" value="${movimentacao.adiantamento.id}" groupingUsed="" /></b> Fornecedor: ${movimentacao.adiantamento.fornecedor.apelido } - ${movimentacao.adiantamento.fornecedor.nome }
						</c:if>
					 
				</td>
				
				
				
				<td>
						<c:set var="valor" value="0"/>
						<fmt:formatNumber value="${movimentacao.tipoOperacao.name eq 'D' ? movimentacao.valor * -1 :  movimentacao.valor}" minFractionDigits="2" type="currency"/>
					</td>
					
				<td >
					${movimentacao.status.descricao}
				</td>
				
				<td>
					<fmt:formatDate value="${movimentacao.dataPagamento}" type="date" pattern="dd/MM/yyyy"/>
					
				</td>
				<td >
					${movimentacao.conta.descricao}
				</td>
			
			</tr>
		</c:forEach>
	</tbody>
	</table>
	<br/>
	<div class="panel panel-default">
  	<div class="panel-body">
	<div class="row">
		<div class="col-md-4"><b>Valor Total:</b> <fmt:formatNumber value="${valorTotal}" minFractionDigits="2" type="currency"/></div>
		<div class="col-md-4"><b>Quantidade Total:</b> <fmt:formatNumber value="${quantidade}" minFractionDigits="2" type="number"/></div>
		<div class="col-md-4">Preço Médio: <fmt:formatNumber value="${ precoMedio}" minFractionDigits="2" type="currency"/></div>
	
	</div>
	</div>
	</div>
	</div>
	</div>
	<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
  Launch demo modal
</button>
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
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>