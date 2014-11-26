<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/cp/novo";
    	});
    	
    	
    });
</script>
<div class="container">
		
        <br/>
		<h2>Comprovante de Pesagem</h2>
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
		<th >Data de emissão</th>
		<th >Número do ticket</th>
		<th >Placa do veículo</th>
		<th >Peso Líquido</th>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="comprovantePesagem" items="${beanList}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/cp/'/>${comprovantePesagem.id}"><span title="Alterar"  class="glyphicon glyphicon-edit"></span></a>
				<a href="<c:url value='/cp/remove/'/>${comprovantePesagem.id}"><span title="Excluir" class="glyphicon glyphicon-remove"></span></a> 
			</td>
			<td>
				${comprovantePesagem.dataEmissao} 
			</td>
			<td>
				${comprovantePesagem.numeroTicket} 
			</td>
			<td>
				${comprovantePesagem.placaVeiculo} 
			</td>
			<td>
				${comprovantePesagem.pesoLiquido} 
			</td>
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
