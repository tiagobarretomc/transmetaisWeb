<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	var options = new Array();
   	 options['language'] = 'pt-BR';
   	 $('.datepicker').datepicker(options);
   	 
    	$("#btnAdicionar").click(function(){
    		document.location.href = "${pageContext.request.contextPath}/despesa/novo";
    	});
    	
    	$('.selectpicker').selectpicker({
            //'selectedText': 'cat'
        });
    });
</script>
<div class="container">
		
        <br/>
		<h2>Despesas</h2>
		<br>
		
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-plus-sign"></span> Adicionar
		</button>
			
		<br/>
		<br/>
		
		<div class="panel panel-default">
  	<div class="panel-body">
		<form action="<c:url value='/consulta/despesa'/>" id="formDespesa" name="formDespesa" method="post">
		<!-- <input type="hidden" name="_format" value="json"> -->
		<div class="row">
		
			<div class="col-md-4">
			
			<label for="filter.fornecedor.id">Fornecedor:</label>
				        	<select id="filter.fornecedor.id" name="filter.fornecedor.id" class="selectpicker form-control" data-live-search="true">
								<option value ="" >Selecione</option>
								<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
									<option value ="${fornecedor.id}" ${filter.fornecedor.id eq fornecedor.id  ? 'selected' : ''}>${fornecedor.apelido} - ${fornecedor.nome}</option>
								</c:forEach>	
							</select>
			</div>
        	
        	<div class="col-md-2">
        	<label for="dataInicio">Data Início:</label>
        	
        		<input type="datetime"  name="filter.dataInicio" id="filter.dataInicio" class="datepicker form-control" data-date-format="dd/mm/yyyy" value="<fmt:formatDate value="${filter.dataInicio}" type="date" pattern="dd/MM/yyyy"/>" />
					
        	</div>
        	<div class="col-md-2">
        	<label for="dataFim">Data Fim:</label>
        		<input type="datetime"  name="filter.dataFim" id="filter.dataFim" class="datepicker form-control" data-date-format="dd/mm/yyyy" value="<fmt:formatDate value="${filter.dataFim}" type="date" pattern="dd/MM/yyyy"/>" />
					
        	</div>
        	<div class="col-md-3">
        		<label for="status">Status:</label>
				<select  id="filter.status" name="filter.status" class="selectpicker form-control"  >
					<option value="" ></option>
					<c:forEach var="statusL" items="${statusList}">
						<option value="${statusL.name }" ${statusL.name eq filter.status.name ? 'selected' : ''}>${statusL.descricao}</option>
					</c:forEach>
				</select>
        	</div>
        	
        </div>
        <div class="row">
        	<div class="col-md-5">
        	<label for="filter.descricao">Descrição:</label>
        	<input type="text"  name="filter.descricao" id="filter.descricao" class="form-control" data-date-format="dd/mm/yyyy" value="${filter.descricao }" />
        	</div>
        </div>
       
        <br/>
        <button id="btnFiltrar" type="submit" class="btn btn-default btn-sm">
  		<span class="glyphicon glyphicon-filter"></span> Filtrar
		</button>
        </form>
        </div>
        </div>
        
    
		<table  class="table table-bordered table-striped">
		
		<thead>
	<tr>
		
		
		<th >Data Competência</th>
		<th >Conta Contábil</th>
		<th >Centro de Aplicação</th>
		<th >Descrição</th>
		<th >Valor</th>
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="despesa" items="${beanList}" varStatus="contador">
	
		<tr>
			
			
			<td><fmt:formatDate value="${despesa.dataCompetencia}" type="date" pattern="dd/MM/yyyy"/></td>
			<td>
				${despesa.contaContabil.numero} - ${despesa.contaContabil.descricao} 
			</td>
			<td>
				${despesa.centroAplicacao.numero} - ${despesa.centroAplicacao.descricao}
			</td>
			<td>
				${despesa.descricao} 
			</td>
			<td>
				<fmt:formatNumber value="${despesa.valor}" minFractionDigits="2" type="currency"/>
			</td>
			
		</tr>
		</c:forEach>
	</tbody>
	</table>
	
	<div class="panel panel-default">
  	<div class="panel-body">
	<div class="row">
		<div class="col-md-4"><b>Valor Total:</b> <fmt:formatNumber value="${valorTotal}" minFractionDigits="2" type="currency"/></div>
		
	
	</div>
	</div>
	</div>
