<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		
		
		<div class="panel panel-default">
  	<div class="panel-body">
		
		<input type="hidden" name="_format" value="json">
		<div class="row">
        	<div class="col-md-4">
        		<label for="fornecedorId">Fornecedor/Cliente:</label>
        		${titular.nome}
        	</div>
        	<div class="col-md-4">
        		<label for="fornecedorId">Saldo Atual:</label>
        		<fmt:formatNumber value="${conta.saldo}" minFractionDigits="2" type="currency"/>
        	</div>
        	<div class="col-md-4">
        		<label for="fornecedorId">Saldo Inicial:</label>
        		<fmt:formatNumber value="${conta.saldoInicial}" minFractionDigits="2" type="currency"/>
        	</div>
        </div>
        
        
        </div>
        </div>

		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		
		<th >Data</th>
		<th>Descrição</th>
		<th >Valor</th>
		<th>Tipo</th>
		<th>Parcial</th>
		
		
	</tr>
	</thead>
	<tbody>
	
		<c:set var="parcial" value="${conta.saldoInicial}" />
		<c:forEach var="movimentacao" items="${movimentacoes}" varStatus="contador">
		
			<c:if test="${movimentacao.tipoOperacao  == 'D'}">
				<c:set var="parcial" value="${parcial-movimentacao.valor}" />
			</c:if>
			<c:if test="${movimentacao.tipoOperacao  == 'C'}">
				<c:set var="parcial" value="${parcial+movimentacao.valor}" />
			</c:if>
	
		<tr>
			
			
				<td >
					<fmt:formatDate value="${movimentacao.data}" type="date" pattern="dd/MM/yyyy"/>
					
				</td>
				<td>
					<c:if test="${movimentacao.class.name  == 'br.com.transmetais.bean.MovimentacaoContasAPagar'}">
						<c:if test="${movimentacao.contaAPagar.class.name  == 'br.com.transmetais.bean.ContaAPagarAdiantamento'}">
							Adiant <fmt:formatNumber minIntegerDigits="4" value="${movimentacao.contaAPagar.adiantamento.id}" groupingUsed="" /> - 
							${movimentacao.contaAPagar.adiantamento.fornecedor.apelido}
						</c:if>
						<c:if test="${movimentacao.contaAPagar.class.name  == 'br.com.transmetais.bean.ContaAPagarCompra'}">
							Compra <fmt:formatNumber minIntegerDigits="4" value="${movimentacao.contaAPagar.compra.id}" groupingUsed="" /> - 
							${movimentacao.contaAPagar.compra.fornecedor.apelido}
						</c:if>
						<c:if test="${movimentacao.contaAPagar.class.name  == 'br.com.transmetais.bean.ContaAPagarDespesa'}">
							Despesa - ${movimentacao.contaAPagar.despesa.id} - ${movimentacao.contaAPagar.despesa.descricao}
						</c:if>
					
					
					</c:if>
					<c:if test="${movimentacao.class.name  == 'br.com.transmetais.bean.MovimentacaoDespesa'}">
						
							Despesa - ${movimentacao.despesa.id} - ${movimentacao.despesa.descricao}
						
					</c:if>
					<c:if test="${movimentacao.class.name  == 'br.com.transmetais.bean.MovimentacaoAdiantamento'}">
						
							Adiantamento - ${movimentacao.adiantamento.id} - Fornecedor: ${movimentacao.adiantamento.fornecedor.nome}
						
					</c:if>
				</td>
				
				<td >
						
						<fmt:formatNumber value="${movimentacao.valor}" minFractionDigits="2" type="currency"/>
				</td>
				<td >
						
						${movimentacao.tipoOperacao.descricao}
				</td>
				<td><fmt:formatNumber value="${parcial}" minFractionDigits="2" type="currency"/></td>
				
				
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
	<br/>
	
	