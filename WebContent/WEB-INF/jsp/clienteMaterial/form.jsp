<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="pt-BR" /> 
<script type="text/javascript">
	
	$(document).ready(function(){
		$("#btnAdicionar").click(function(){
			
			$("#formMateriais").submit();
		});
		
		$('#formMateriais').validate({
	        
	        
		});
		
		$('.selectpicker').selectpicker({
            
        });
		
		$('#precoMaterial').priceFormat({
            prefix: '',
            centsSeparator: ',',
            thousandsSeparator: '.',
            limit: 12,
            centsLimit: 4
        });
		
	});
  
</script>
    <div class="container">
    <br>
	<h2>Tabela de Preços do Cliente</h2>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="${pageContext.request.contextPath}/clienteMaterial/associar" id="formMateriais" name="formMateriais" method="post">
	<input type="hidden" id="clienteId" name="clienteMaterial.cliente.id" value="${cliente.id}"/>
	<table style="width:  600px;">
		<tr>
			<td>
				<b>Código do Cliente:</b>
			</td>
			<td>
				<a href="${pageContext.request.contextPath}/cliente/${cliente.id}">${cliente.id}</a>
			</td>
		</tr>
		<tr>
			<td>
				<b>Cliente:</b>
			</td>
			<td>
				<a href="${pageContext.request.contextPath}/cliente/${cliente.id}">${cliente.razaoSocial}</a>
			</td>
		</tr>
		<tr>
			<td>
				<b>Contato:</b>
			</td>
			<td>
				<a href="${pageContext.request.contextPath}/cliente/${cliente.id}">${cliente.apelido}</a>
			</td>
		</tr>
	</table>
	<br/>
	
	
	<div class="row" >
        	<div class="col-md-4">Material:<br/>
				<select style="width: 180px;" id="cboMaterial" name="clienteMaterial.material.id" class="required form-control selectpicker">
					<option value="" >--Selecione--</option>
					<c:forEach var="material" items="${materiais}">
						<option value="${material.id }" >${material.descricao}</option>
					</c:forEach>
				</select></div>
        	<div class="col-md-2">Preço:<br/>
				
			
				<input  type="text" id="precoMaterial" size="15" name="clienteMaterial.valor" class="required form-control"/></div>
        	<div class="col-md-4">Forma de Frete/Entrega:<br/>
				
				<select style="width: 180px;" id="cboTipoFrete" name="clienteMaterial.tipoFrete" class="required selectpicker form-control">
					<option value="" >--Selecione--</option>
					<c:forEach var="tipoFrete" items="${tiposFrete}">
						<option value="${tipoFrete.name }" >${tipoFrete.descricao}</option>
					</c:forEach>
				</select>
				</div>
      	</div>
	
	
	<button id="btnAdicionar" type="button" class="btn btn-default btn-sm">
  <span class="glyphicon glyphicon-plus"></span> Adicionar
</button>
	
	<br/>
	<br/>
	<div id="tabelaPreco">
	
	
          
	<table  class="table table-bordered table-striped">
		<thead>
			<tr>
				<th>Material</th>
				<th>Preço/Kg</th>
				<th>Tipo Frete</th>
				<th>Status</th>
				<th>Início Vigência</th>
				<th>Fim Vigência</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="materialCliente" items="${cliente.clientesMateriais}" varStatus="contador">
		<tr>
			<td>${materialCliente.material.descricao}</td>
			<td><fmt:formatNumber value="${materialCliente.valor}" minFractionDigits="4" type="currency"/> </td>
			<td>${materialCliente.tipoFrete.descricao}</td>
			<td>${materialCliente.status}</td>
			<td><fmt:formatDate value="${materialCliente.inicioVigencia}" type="date" pattern="dd/MM/yyyy"/></td>
			<td><fmt:formatDate value="${materialCliente.fimVigencia}" type="date" pattern="dd/MM/yyyy"/></td>
			<td>
				<c:if test="${materialCliente.status eq 'ATIVO' or  materialCliente.status eq 'BLOQUEADO'}">
					<a href="<c:url value='/clienteMaterial/inativar/'/>${materialCliente.id}"><span title="Inativar" class="glyphicon glyphicon-remove-sign"></span></a>
				</c:if>
				<c:if test="${materialCliente.status eq 'BLOQUEADO'}">
					<a  href="<c:url value='/clienteMaterial/ativar/'/>${materialCliente.id}"><span title="Ativar" class="glyphicon glyphicon-ok"></span></a>
				</c:if>
			</td>
		</tr>
		
		</c:forEach>
		</tbody>
	</table>
    </div>
    
    
</form>
	</div>
	</div>	
</div>

