<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	$("#dataInicio").mask('99/99/9999');
    	$("#dataFim").mask('99/99/9999');
    	
    	$("#btnFiltrar").click(function(){
    		/*
    		$.ajax({
    	        url: '<c:url value="/compra/loadListaCompra"/>  ',
    	        type: 'post',
    	        dataType: 'json',
    	        data: $('#formCompra').serialize(),
    	        error: function(returnval) {
    	        	alert(returnval);
    	        },
    	        success: function(data) {
    	                   alert(data);
    	                 }
    	    });
    		*/
    		$("#divTabela").load( '<c:url value="/compra/loadListaCompra"/>', $('#formCompra').serialize() );
    	});
    	
    	$("#btnAdicionar").click(function(){
    		if ($("#fornecedor").val())
    			document.location.href = "${pageContext.request.contextPath}/compra/novo/" + $("#fornecedor").val();
    		else
    			$("#divError").css('display', 'block');
    	});
    	
    	$('.selectpicker').selectpicker({
            'selectedText': 'cat'
        });
    });
 </script>
 
<div class="container">
		<br/>
		<h2>Compras</h2>
		<br>
		<div class="alert alert-warning alert-dismissable" id="divError" style="display: none">Selecione um fornecedor!</div>
		<div class="panel panel-default">
  	<div class="panel-body">
		<form action="<c:url value='/compra/'/>" id="formCompra" name="formCompra" method="post">
		<input type="hidden" name="_format" value="json">
		<div class="row">
        	<div class="col-md-4">Fornecedor:<br/>
	        	<select id="fornecedor" name="fornecedorId" class="selectpicker" data-live-search="true">
					<option value ="">Selecione</option>
					<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
						<option value ="${fornecedor.id}">${fornecedor.apelido} - ${fornecedor.nome}</option>
					</c:forEach>	
				</select>
				<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-plus-sign"></span> 
		</button>
        	</div>
        	<div class="col-md-4">Data Início: <br>
        		<input type="datetime"  name="dataInicio" id="dataInicio" class="date" value="" />
					
        	</div>
        	<div class="col-md-4">Data Fim: <br>
        		<input type="datetime"  name="dataFim" id="dataFim" class="date" value="" />
					
        	</div>
        </div>
        <div class="row">
        	<div class="col-md-4">Material<br/>
        	
        		<select id="material" name="materiaisSelecionados" class="selectpicker" multiple>
					<c:set  var="grupo" value=" " scope="request"></c:set>
					<c:set  var="vari" value="1" scope="request"></c:set>
					<c:forEach var="material" items="${materiais}" varStatus="contador">
						<c:if test="${grupo ne material.grupoMaterial.descricao }">
							<c:if test="${contador gt '1'}">
								</optgroup>
							</c:if>
							<optgroup label="${material.grupoMaterial.descricao}">
						</c:if>
						<option value ="${material.id}">${material.descricao}</option>
						<c:set  var="grupo" value="${material.grupoMaterial.descricao }" scope="request"></c:set>
					</c:forEach>
					<c:if test="${not empty grupo }">
						</optgroup>
					</c:if>	
				</select>
        	 
        	</div>
        	<div class="col-md-4">Forma de Frete/Entrega:<br/>
				
				<select style="width: 180px;" id="cboTipoFrete" name="tiposFretes" class="selectpicker" multiple >
					
					<c:forEach var="tipoFrete" items="${tiposFrete}">
						<option value="${tipoFrete.name }" >${tipoFrete.descricao}</option>
					</c:forEach>
				</select>
				</div>
        	<div class="col-md-4"></div>
        </div>
        <br/>
        <button id="btnFiltrar" type="button" class="btn btn-default btn-sm">
  		<span class="glyphicon glyphicon-filter"></span> Filtrar
		</button>
        </form>
        </div>
        </div>
		
		
		<div id="divTabela">
		<table width="1024px" class="table table-bordered table-striped">
		
		<thead>
	<tr>
		<th ></th>
		<th >Data</th>
		<th >Fornecedor</th>
		<th>Material</th>
		<th >Tipo Frete</th>
		<th>Quantidade</th>
		<th>Valor Total</th>
		
	</tr>
	</thead>
	<tbody>
	
		<c:forEach var="compra" items="${compras}" varStatus="contador">
	
		<tr>
			<td>
				<a href="${pageContext.request.contextPath}/compra/${compra.id}"><span title="Alterar" style="color: black;" class="glyphicon glyphicon-edit"></span></a>
				 
			</td>
			<td>
				<fmt:formatDate value="${compra.data}" type="date" pattern="dd/MM/yyyy"/>
				
			</td>
			<td>
				${compra.fornecedorMaterial.fornecedor.apelido} - ${compra.fornecedorMaterial.fornecedor.nome} 
			</td>
			<td>
				${compra.fornecedorMaterial.material.descricao} 
			</td>
			
			<td>
				${compra.fornecedorMaterial.tipoFrete.descricao}
			</td>
			<td >
				
				<fmt:formatNumber value="${compra.quantidade}" minFractionDigits="2" type="number"/>
			</td>
			<td>
				
				<fmt:formatNumber value="${compra.valor}" minFractionDigits="2" type="currency"/>
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
		<div class="col-md-4">Preço Médio: <fmt:formatNumber value="${precoMedio}" minFractionDigits="2" type="currency"/></div>
	
	</div>
	</div>
	</div>
	</div>
	</div>
