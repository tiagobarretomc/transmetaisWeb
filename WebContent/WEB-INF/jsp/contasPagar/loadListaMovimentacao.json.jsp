<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	jQuery(".btnDetalhe").on("click",function(event){
    		var indiceCampo = $(this).attr('id').split("_")[1];
    		$("#divResultado").load( '<c:url value="/contasPagar/"/>' + indiceCampo, {'_format':'json'});
    		$('#myModal').modal('show');
    	});
    	
    	
    });
</script>

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
							${conta.despesa.descricao }
						</c:if>
					 
				</td>
				
				
				
				<td>
						<c:set var="valor" value="0"/>
						<fmt:formatNumber value="${ conta.valor }" minFractionDigits="2" type="currency"/>
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
	