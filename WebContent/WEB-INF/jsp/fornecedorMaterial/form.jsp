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
		
		$('#precoMaterial').priceFormat({
            prefix: '',
            centsSeparator: ',',
            thousandsSeparator: '.',
            limit: 12
        });
		
	});
  
</script>
    <div class="container">
    <br>
	<h2>Tabela de Preços do Fornecedor</h2>
	
	<form action="${pageContext.request.contextPath}/fornecedorMaterial/associar" id="formMateriais" name="formMateriais" method="post">
	<input type="hidden" id="fornecedorId" name="fornecedorMaterial.fornecedor.id" value="${fornecedor.id}"/>
	<table style="width:  600px;">
		<tr>
			<td>
				<b>Código de Fornecedor:</b>
			</td>
			<td>
				<a href="${pageContext.request.contextPath}/fornecedor/${fornecedor.id}">${fornecedor.id}</a>
			</td>
		</tr>
		<tr>
			<td>
				<b>Fornecedor:</b>
			</td>
			<td>
				<a href="${pageContext.request.contextPath}/fornecedor/${fornecedor.id}">${fornecedor.nome}</a>
			</td>
		</tr>
		<tr>
			<td>
				<b>Apelido:</b>
			</td>
			<td>
				<a href="${pageContext.request.contextPath}/fornecedor/${fornecedor.id}">${fornecedor.apelido}</a>
			</td>
		</tr>
	</table>
	<br/>
	
	
	<div class="row" style="width: 600px;">
        	<div class="col-md-4">Material:<br/>
				<select style="width: 180px;" id="cboMaterial" name="fornecedorMaterial.material.id" class="required">
					<option value="" >--Selecione--</option>
					<c:forEach var="material" items="${materiais}">
						<option value="${material.id }" >${material.descricao}</option>
					</c:forEach>
				</select></div>
        	<div class="col-md-4">Preço:<br/>
				
			
				<input  type="text" id="precoMaterial" size="15" name="fornecedorMaterial.valor" class="required"/></div>
        	<div class="col-md-4">Forma de Frete/Entrega:<br/>
				
				<select style="width: 180px;" id="cboTipoFrete" name="fornecedorMaterial.tipoFrete" class="required">
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
	
	
          
	<table  class="table table-bordered table-striped" style="width: 800px">
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
		<c:forEach var="materialFornecedor" items="${fornecedor.materiaisFornecedores}" varStatus="contador">
		<tr>
			<td>${materialFornecedor.material.descricao}</td>
			<td><fmt:formatNumber value="${materialFornecedor.valor}" minFractionDigits="2" type="currency"/> </td>
			<td>${materialFornecedor.tipoFrete.descricao}</td>
			<td>${materialFornecedor.status}</td>
			<td><fmt:formatDate value="${materialFornecedor.inicioVigencia}" type="date" pattern="dd/MM/yyyy"/></td>
			<td><fmt:formatDate value="${materialFornecedor.fimVigencia}" type="date" pattern="dd/MM/yyyy"/></td>
			<td>
				<c:if test="${materialFornecedor.status eq 'ATIVO' or  materialFornecedor.status eq 'BLOQUEADO'}">
					<a href="<c:url value='/fornecedorMaterial/inativar/'/>${materialFornecedor.id}"><span title="Inativar" class="glyphicon glyphicon-remove-sign"></span></a>
				</c:if>
				<c:if test="${materialFornecedor.status eq 'BLOQUEADO'}">
					<a  href="<c:url value='/fornecedorMaterial/ativar/'/>${materialFornecedor.id}"><span title="Ativar" class="glyphicon glyphicon-ok"></span></a>
				</c:if>
			</td>
		</tr>
		
		</c:forEach>
		</tbody>
	</table>
    </div>
    
    
</form>
		
</div>

