<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/usuario/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		
        <br/>
		<h2>Usuários</h2>
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
		<th >Nome</th>
		<th >E-mail</th>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="bean" items="${beanList}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/usuario/'/>${bean.id}"><span title="Alterar"  class="glyphicon glyphicon-edit"></span></a>
				<a href="<c:url value='/usuario/remove/'/>${bean.id}"><span title="Excluir" class="glyphicon glyphicon-remove"></span></a> 
			</td>
			<td>
				${bean.nome} 
			</td>
			<td>
				${bean.email} 
			</td>
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
