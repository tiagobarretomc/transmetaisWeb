<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#dataInicio").mask('99/99/9999');
    	$("#dataFim").mask('99/99/9999');
    	
    	var options = new Array();
    	 options['language'] = 'pt-BR';
    	 $('.datepicker').datepicker(options);
    	
    	$("#btnFiltrar").click(function(){
    		
    		$("#divTabela").load( '<c:url value="/venda/loadListaVenda"/>', $('#formVenda').serialize() );
    	});
    	
    	$("#btnAdicionar").click(function(){
    		if ($("#cliente").val())
    			document.location.href = "${pageContext.request.contextPath}/venda/novo/" + $("#cliente").val();
    		else
    			$("#divError").css('display', 'block');
    	});
    	
    	$('.selectpicker').selectpicker({
            //'selectedText': 'cat'
        });
    });
 </script>
 
<div class="container">
		<br/>
		<h2>Vendas</h2>
		<br>
		<div class="alert alert-warning alert-dismissable" id="divError" style="display: none">Selecione um cliente!</div>
		<div class="panel panel-default">
  	<div class="panel-body">
		<form action="<c:url value='/venda/'/>" id="formVenda" name="formVenda" method="post">
		<input type="hidden" name="_format" value="json">
		<div class="row">
        	<div class="col-md-5">
        		<label for="clienteId">Cliente:</label>
	        	<select id="cliente" name="clienteId" class="selectpicker form-control" data-live-search="true">
					<option value ="">Selecione</option>
					<c:forEach var="cliente" items="${cliente}" varStatus="contador">
						<option value ="${cliente.id}">${cliente.razaoSocial}</option>
					</c:forEach>	
				</select>
				
				
        	</div>
        	<div class="col-md-1"><br/>
        		<button type="button" id="btnAdicionar" class="btn btn-default btn-lg">
				  <span class="glyphicon glyphicon-plus-sign"></span> 
				</button>
        	</div>
        	<div class="col-md-3">
        	<label for="dataInicio">Data Início:</label>
        	
        		<input type="datetime"  name="dataInicio" id="dataInicio" class="datepicker form-control" data-date-format="dd/mm/yyyy" value="" />
					
        	</div>
        	<div class="col-md-3">
        	<label for="dataFim">Data Fim:</label>
        		<input type="datetime"  name="dataFim" id="dataFim" class="datepicker form-control" data-date-format="dd/mm/yyyy" value="" />
					
        	</div>
        </div>
        <div class="row">
        	<div class="col-md-4">
        	<label for="material">Material:</label>
        	
        		<select id="material" name="materiaisSelecionados" class="selectpicker form-control" multiple>
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
        	<div class="col-md-4">
				<label for="cboTipoFrete">Forma de Frete/Entrega:</label>
				<select style="width: 180px;" id="cboTipoFrete" name="tiposFretes" class="selectpicker form-control" multiple >
					
					<c:forEach var="tipoFrete" items="${tiposFrete}">
						<option value="${tipoFrete.name }" >${tipoFrete.descricao}</option>
					</c:forEach>
				</select>
				</div>
        	<div class="col-md-4">
        		<label for="compra.status">Status Venda:</label>
				<select style="width: 180px;" id="venda.status" name="statusVenda" class="selectpicker form-control" multiple >
					
					<c:forEach var="status" items="${statusList}">
						<option value="${status.name }" >${status.descricao}</option>
					</c:forEach>
				</select>
				
        	</div>
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
		<th >Cliente</th>
		<th >Tipo Frete</th>
		<th>Total</th>
		<th>Status</th>
		<th>Material</th>
		<th>Quantidade</th>
		<th>Valor</th>
		
	</tr>
	</thead>
	<tbody>
	<c:set var="varCont" value="1" />
		<c:forEach var="venda" items="${vendas}" varStatus="contador">
	
		<tr>
			
			
				<td rowspan="${fn:length(venda.itens)}">
					<a href="${pageContext.request.contextPath}/venda/${venda.id}"><span title="Detalhar" style="color: black;" class="glyphicon glyphicon-search"></span></a>
					 
				</td>
				<td rowspan="${fn:length(venda.itens)}">
					<fmt:formatDate value="${venda.data}" type="date" pattern="dd/MM/yyyy"/>
					
				</td>
				<td rowspan="${fn:length(venda.itens)}">
					${compra.cliente.razaoSocial}
				</td>
				
				
				<td rowspan="${fn:length(venda.itens)}">
					${venda.tipoFrete}
				</td>
				<td rowspan="${fn:length(venda.itens)}">
						
						<fmt:formatNumber value="${venda.valor}" minFractionDigits="2" type="currency"/>
					</td>
					
				<td rowspan="${fn:length(venda.itens)}">
					${venda.status.descricao}
				</td>
				
				<c:forEach var="item" items="${venda.itens}" varStatus="cont" >
					<c:if test="${cont.count gt '1'}">
						</tr>
						
						<tr>
					</c:if>
					<td>
						${item.material.sigla} 
					</td>
					<td >
						
						<fmt:formatNumber value="${item.quantidade}" minFractionDigits="2" type="number"/>
					</td>
					<td>
						
						<fmt:formatNumber value="${item.valor}" minFractionDigits="2" type="currency"/>
					</td>
				</c:forEach>
				
			
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
