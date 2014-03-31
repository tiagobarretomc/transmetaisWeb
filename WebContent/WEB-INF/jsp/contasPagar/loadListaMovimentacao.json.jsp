<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


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
					<a href="${pageContext.request.contextPath}/contasPagar/${movimentacao.id}"><span title="Detalhar" style="color: black;" class="glyphicon glyphicon-edit"></span></a>
					 
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
	