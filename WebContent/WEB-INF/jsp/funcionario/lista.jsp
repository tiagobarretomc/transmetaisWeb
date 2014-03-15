<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/funcionario/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		
        <br/>
		<h2>Funcionários</h2>
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
		<th >Código</th>
		<th >Nome</th>
		<th >Cargo</th>
		<th >Telefone</th>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="funcionario" items="${funcionarioList}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/funcionario/'/>${funcionario.id}"><span title="Alterar"  class="glyphicon glyphicon-edit"></span></a>
				<a href="<c:url value='/funcionario/remove/'/>${funcionario.id}"><span title="Excluir" class="glyphicon glyphicon-remove"></span></a> 
			</td>
			<td>
				<fmt:formatNumber minIntegerDigits="4" value="${funcionario.id}" groupingUsed="" />
			</td>
			<td>
				${funcionario.nome} 
			</td>
			<td>
				${funcionario.cargo} 
			</td>
			<td>
				${funcionario.telefone} 
			</td>
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
