<%@page contentType="text/html; charset=UTF-8" isELIgnored ="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#bean\\.dataCompetencia").mask('99/99/9999');
		$("#bean\\.dataVencimento").mask('99/99/9999');
		
		$("#valor").priceFormat({
	        prefix: '',
	        centsSeparator: ',',
	        thousandsSeparator: '.',
	        limit: 12
	        
	    });
		
		var options = new Array();
	   	 options['language'] = 'pt-BR';
	   	 $('.datepicker').datepicker(options);
	   	 
	   	$('.selectpicker').selectpicker({
            //'selectedText': 'cat'
        });
	    $("#qtdParcelas").blur(function(){
	    	var qtdParcelas = $('#qtdParcelas').val();
	    	var strLinha;
	    	for(i = 0; i < qtdParcelas; i++){
	    		adicionarParcela(i);
	    	}
	    	
    	});
	    $("#btnAdicionarRegra").click(function(){
	    	var qtdParcelas = $("tr[id^='parcela_']").size();
			adicionarParcela(qtdParcelas);
			$("#qtdParcelas").val($("tr[id^='parcela_']").size());
			$('.datepicker').datepicker(options);
		   	 
		 	$('.selectpicker').selectpicker({
		             //'selectedText': 'cat'
		     });
	    });
	    $('.datepicker').datepicker(options);
	   	 
 	   	$('.selectpicker').selectpicker({
             //'selectedText': 'cat'
         });
		
	});
	function adicionarParcela(i){
		strLinha = '<tr id="parcela_' + i + '">';
		strLinha += '<td style="vertical-align: middle;"><span title="Excluir" class="glyphicon glyphicon-remove" onclick="removerParcela(' + i +')"></span></td>';
		strLinha += '<td style="max-width:130px"><input name="parcelas[' + i + '].data" id="dataParcela' + i + '" data-date-format="dd/mm/yyyy" value="${parcelas[' + i + '].data}" class="form-control required datepicker" size="8" maxlength="8"/></td>';
		strLinha += '<td style="max-width:130px"><input name="parcelas[' + i + '].valor" id="valorParcela' + i + '" value="<fmt:formatNumber value="${parcelas[' + i + '].valor}" minFractionDigits="2" type="currency"/>" class="form-control required"  maxlength="18"/></td>';
		strLinha += '</tr>';
		$('#tabelaParcelas > tbody:last').append(strLinha);
		$("#dataParcela" + i).mask('99/99/9999');
		$("#valorParcela" + i).priceFormat({
	        prefix: '',
	        centsSeparator: ',',
	        thousandsSeparator: '.',
	        limit: 12
	        
	    });
		
	}
	function removerParcela(id){
    	$("#parcela_" + id).remove();
    	$("#qtdParcelas").val($("tr[id^='parcela_']").size());
    }
	        
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Despesas</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/despesa/add'/>" id="formDespesa" name="formProduto" method="post">
		<input type="hidden" id="despesa.id" name="bean.id" value="${bean.id}"/>
		
		
		<div class="row">
			<div class="col-md-6">
        		<label for="bean.descricao">Descrição:</label>
        		<input name="bean.descricao" id="bean.descricao" value="${bean.descricao}" class="form-control required"  maxlength="45"/>
        	</div>
        	<div class="col-md-2">
        		<label for="bean.valor">Valor:</label>
        		<input name="bean.valor" id="valor" value="<fmt:formatNumber value="${bean.valor}" minFractionDigits="2" type="currency"/>" class="form-control required"  maxlength="18"/>
        	</div>
        	
        	<div class="row">
        	<div class="col-md-2">Forma de Pagamento: <br/>
				<input type="radio" name="formaPagamento" value="V" id="optPagamentoAVista"/>&nbsp;À vista&nbsp;
				<input type="radio" name="formaPagamento" value="P" id="optPagamentoAPrazo"/>&nbsp;À prazo&nbsp;</div>
        	
        </div>
        	
        	
      	</div>
		<div class="row">
        	<div class="col-md-4">
        	<label for="bean.centroAplicacao.id">Centro de Aplicação:</label>
        		<select id="bean.centroAplicacao.id" name="bean.centroAplicacao.id" class="selectpicker required form-control" data-live-search="true">
						<option value ="">Selecione</option>
						<c:forEach var="centro" items="${centros}" varStatus="contador">
						
							<option value ="${centro.id}" ${bean.centroAplicacao.id eq centro.id ? 'selected' : ''}>${centro.numero} - ${centro.descricao}</option>
		
						</c:forEach>	
					</select>
        	</div>
        	<div class="col-md-4">
        	<label for="bean.contaContabil.id">Conta Contábil:</label>
        		<select id="bean.contaContabil.id" name="bean.contaContabil.id" class="selectpicker required form-control" data-live-search="true">
						<option value ="">Selecione</option>
						<c:forEach var="conta" items="${contas}" varStatus="contador">
						
							<option value ="${conta.id}" ${bean.contaContabil.id eq conta.id ? 'selected' : ''}>${conta.numero} - ${conta.descricao}</option>
		
						</c:forEach>	
				</select>
        	</div>
        	<div class="col-md-2">
        		<label for="bean.dataCompetencia">Data Competência:</label>
        		<input name="bean.dataCompetencia" id="bean.dataCompetencia" data-date-format="dd/mm/yyyy" value="${bean.dataCompetencia}" class="form-control required datepicker" size="8" maxlength="8"/>
        	</div>
        	<div class="col-md-2">
        		<label for="bean.dataVencimento">Data Vencimento:</label>
        		<input name="bean.dataVencimento" id="bean.dataVencimento" data-date-format="dd/mm/yyyy" value="${bean.dataVencimento}" class="form-control required datepicker" size="8" maxlength="8"/>
        	</div>
				
        	
      	</div>
      	<div class="row">
      	<div class="col-md-2">Quantidade de Parcelas:<br/>
        		<input id="qtdParcelas" value="${quantidadeParcelas}" size="10" class="form-control "/>
        	</div>
      	</div>
      	
        <br/>
        <div class="panel panel-default" >
        	<div class="panel-body">
        	<h4>Parcelas</h4>
				<button type="button" id="btnAdicionarRegra" class="btn btn-default btn-md">
				  <span class="glyphicon glyphicon-plus-sign"></span> Adicionar
				</button>
				<br/>
				<br/>
				<div id="divTabela">
				<table  class="table table-bordered table-striped" id="tabelaParcelas">
				
				<thead>
			<tr>
				<th ></th>
				<th >Data</th>
				<th >Valor</th>
				
				
			</tr>
			</thead>
			<tbody>
			</tbody>
			</table>
			</div>
        	</div>
        </div>
      	<br/>
      	
      	
      	<br/>
		<button type="submit" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
		</button>
		</form>
</div>
</div>
</div>