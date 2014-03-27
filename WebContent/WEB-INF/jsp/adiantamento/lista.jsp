<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/adiantamento/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		
        <br/>
		<h2>Adiantamentos Para Fornecedores</h2>
		<br>
		
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-plus-sign"></span> Adicionar
		</button>
			
		<br/>
		<br/>
    
		<table  class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th ></th>
		<th >Data</th>
		<th >Fornecedor</th>
		<th >Valor</th>
		<th >Status</th>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="adiantamento" items="${adiantamentoList}" varStatus="contador">
	
		<tr>
			<td>
				<c:if test="${adiantamento.situacao.name eq 'A' }">
					<a href="${pageContext.request.contextPath}/adiantamento/${adiantamento.id}"><span title="Alterar"  class="glyphicon glyphicon-edit"></span></a>
					<a href="${pageContext.request.contextPath}/adiantamento/cancelar/${adiantamento.id}"><span title="Cancelar" class="glyphicon glyphicon-remove"></span></a> 
					<a  href="${pageContext.request.contextPath}/adiantamento/aprovar/${adiantamento.id}"><span title="Ativar" class="glyphicon glyphicon-ok"></span></a>
				</c:if>
			</td>
			
			<td>
				<fmt:formatDate value="${adiantamento.data}" type="date" pattern="dd/MM/yyyy"/>
			</td>
			<td>
				${adiantamento.fornecedor.nome} 
			</td>
			<td>
				<fmt:formatNumber value="${adiantamento.valor}" minFractionDigits="2" type="number" />
			</td>
			<td>
				${adiantamento.situacao.descricao} 
			</td>
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
