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
    		
    		$("#divTabela").load( '<c:url value="/compra/loadListaCompra"/>', $('#formCompra').serialize() );
    	});
    	
    	$("#btnAdicionar").click(function(){
    		if ($("#fornecedor").val())
    			document.location.href = "${pageContext.request.contextPath}/compra/novo/" + $("#fornecedor").val();
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
		<h2>Contas a Pagar</h2>
		<br>
		<div class="alert alert-warning alert-dismissable" id="divError" style="display: none">Selecione um fornecedor!</div>
		<div class="panel panel-default">
  	<div class="panel-body">
		<form action="<c:url value='/compra/'/>" id="formCompra" name="formCompra" method="post">
		<input type="hidden" name="_format" value="json">
		<div class="row">
        	<div class="col-md-5">
        		<label for="fornecedorId">Fornecedor:</label>
	        	<select id="fornecedor" name="fornecedorId" class="selectpicker form-control" data-live-search="true">
					<option value ="">Selecione</option>
					<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
						<option value ="${fornecedor.id}">${fornecedor.apelido} - ${fornecedor.nome}</option>
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
		<th >Descrição</th>
		<th >Valor</th>
		<th>Status</th>
		<th>Data Pagamento</th>
		<th>Valor</th>
		
	</tr>
	</thead>
	<tbody>
	<c:set var="varCont" value="1" />
		<c:forEach var="compra" items="${compras}" varStatus="contador">
	
		<tr>
			
			
				<td rowspan="${fn:length(compra.itens)}">
					<a href="${pageContext.request.contextPath}/compra/${compra.id}"><span title="Detalhar" style="color: black;" class="glyphicon glyphicon-search"></span></a>
					 
				</td>
				<td rowspan="${fn:length(compra.itens)}">
					<fmt:formatDate value="${compra.data}" type="date" pattern="dd/MM/yyyy"/>
					
				</td>
				<td rowspan="${fn:length(compra.itens)}">
					${compra.fornecedor.apelido} - ${compra.fornecedor.nome} 
				</td>
				
				
				<td rowspan="${fn:length(compra.itens)}">
					${compra.tipoFrete}
				</td>
				<td rowspan="${fn:length(compra.itens)}">
						
						<fmt:formatNumber value="${compra.valor}" minFractionDigits="2" type="currency"/>
					</td>
					
				<td rowspan="${fn:length(compra.itens)}">
					${compra.status.descricao}
				</td>
				
				<c:forEach var="item" items="${compra.itens}" varStatus="cont" >
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
