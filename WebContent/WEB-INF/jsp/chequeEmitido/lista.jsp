<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	
    	$(".datepicker").datepicker({
	       	 format: "dd/mm/yyyy",
	       	 laguage: "pt-BR"
	    	});
    	
    	$(".datepicker").mask('99/99/9999');
    	
    	$('#formCheque').validate({
  		   
   		 
         	ignore: ':not(select:hidden, input:visible, textarea:visible)',
         	
          errorPlacement: function (error, element) {
                 if ($(element).is('select')) {
                     element.next().after(error); // special placement for select elements
                 } else {
                     error.insertAfter(element);  // default placement for everything else
                 }
             },
             
         
     	});
    	
    });
</script>
<div class="container">

	<br />
	<h2>Cheques Emitidos</h2>
	<br>
	
	<div class="panel panel-default">
  	<div class="panel-body">
		<form action="<c:url value='/chequeEmitido/lista'/>" id="formCheque" name="formCheque" method="post">
		
		<div class="row">
        	<div class="col-md-2">
        		<label for="cheque.numeroCheque">Número Cheque:</label>
	        	<input type="text" name="cheque.numeroCheque" id="cheque.numeroCheque" class="form-control" value="${cheque.numeroCheque }"/>
				
				
        	</div>
        	
        	<div class="col-md-2">
        	<label for="dataInicio">Data Início:</label>
        	
        		<input type="datetime"  name="dataInicio" id="dataInicio" class="datepicker form-control required" data-date-format="dd/mm/yyyy" value="<fmt:formatDate value="${dataInicio}" type="date" pattern="dd/MM/yyyy"/>" />
					
        	</div>
        	<div class="col-md-2">
        	<label for="dataFim">Data Fim:</label>
        		<input type="datetime"  name="dataFim" id="dataFim" class="datepicker form-control required" data-date-format="dd/mm/yyyy" value="<fmt:formatDate value="${dataFim}" type="date" pattern="dd/MM/yyyy"/>" />
					
        	</div>
        	<div class="col-md-3">
        	<label for="material">Conta Bancária:</label>
        	
        		<select id="cheque.conta.id" name="cheque.conta.id" class="selectpicker form-control" >
        			<option value ="">Selecione</option>
					<c:forEach var="conta" items="${contas}" varStatus="contador">
						<option value ="${conta.id}" ${cheque.conta.id eq conta.id ? 'selected' : ''}>${conta.descricao}</option>
						
					</c:forEach>
						
				</select>
        	 
        	</div>
        	<div class="col-md-3">
        	<label for="material">Status:</label>
        	
        		<select id="cheque.status" name="cheque.status" class="selectpicker form-control" >
        			<option value ="">Selecione</option>
					<c:forEach var="status" items="${situacoes}" varStatus="contador">
						<option value ="${status.name}" ${cheque.status eq status ? 'selected' : ''}>${status.descricao}</option>
						
					</c:forEach>
						
				</select>
        	 
        	</div>
        </div>
        
        <br/>
        
        <button id="btnFiltrar" type="submit" class="btn btn-default btn-sm">
  		<span class="glyphicon glyphicon-filter"></span> Filtrar
		</button>
        </form>
        </div>
        </div>
	

	

	<table class="table table-bordered table-striped">

		<thead>
			<tr>
				<th></th>
				<th>Data</th>
				<th>Número</th>
				<th>Conta</th>
				<th>Valor</th>
				<th>Status</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="cheq" items="${beanList}" varStatus="contador">

				<tr>
					<td>
						<c:if test="${cheq.status eq 'A' }">
							<a  href="<c:url value='/chequeEmitido/'/>${cheq.id}"><span title="Confirmar" class="glyphicon glyphicon-ok"></span></a>
							<a  href="<c:url value='/chequeEmitido/cancelar/'/>${cheq.id}"><span title="Cancelar" class="glyphicon glyphicon-remove"></span></a>
						</c:if>
						<c:if test="${cheq.status eq 'C' || cheq.status eq 'K'}">
							<a href="${pageContext.request.contextPath}/chequeEmitido/detalhar/${cheq.id}"><span title="Detalhar" style="color: black;" class="glyphicon glyphicon-search"></span></a>
						</c:if>
						
					</td>
					<td><fmt:formatDate value="${cheq.data}" type="date" pattern="dd/MM/yyyy"/></td>
					<td>${cheq.numeroCheque}</td>
					<td>${cheq.conta.descricao}</td>
					<td><fmt:formatNumber value="${cheq.valor}" minFractionDigits="2" type="currency"/></td>
					<td>${cheq.status.descricao}</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>