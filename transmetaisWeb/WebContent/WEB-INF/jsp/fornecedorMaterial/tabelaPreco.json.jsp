<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<table width="400px">
		<thead>
			<tr>
				<td>Material</td>
				<td>Pre&ccedil;o</td>
			
			</tr>
		</thead>
		<tbody>
		<c:forEach var="materialFornecedor" items="${fornecedor.materiaisFornecedores}" varStatus="contador">
		<tr>
			<td>${materialFornecedor.material.descricao}</td>
			<td>${materialFornecedor.valor}</td>
			<td><a href="<c:url value='/fornecedorMaterial/excluir/'/>${materialFornecedor.id}">Excluir</a></td>
		</tr>
		
		</c:forEach>
		</tbody>
	</table>