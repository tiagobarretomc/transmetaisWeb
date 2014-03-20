<%@page contentType="text/html; charset=UTF-8" isELIgnored ="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">

	
	var tipoOperacaoList = ${tipoOperacaoList};
	var origemMercadoriaList = ${origemMercadoriaList};
	var situacaoTributariaList = ${situacaoTributariaList};
	var cfopList = ${cfopList};
	var baseCalculoList = ${baseDeCalculoList};
	var baseCalculoSTList = ${baseDeCalculoSTList};
    var qtdRegras = ${fn:length(produto.regrasTributacao)};
    $(document).ready(function(){
    	
    	$("#btnAdicionar").click(function(){
    		$("#formProduto").submit();
    	});
    	
        $('.selectpicker').selectpicker({
        });
        $(".percent").priceFormat({
            prefix: '',
            centsSeparator: ',',
            thousandsSeparator: '.',
            limit: 5
            
        });
        $(".valor").priceFormat({
            prefix: '',
            centsSeparator: ',',
            thousandsSeparator: '.',
            limit: 12
            
        });
        $("#btnAdicionarRegra").click(function(){
    		var strLinha = '<tr><td style="max-width:130px"><select id="tipoOperacao_' + qtdRegras + '" name="produto.regrasTributacao[' + qtdRegras + '].codTipoOperacao" class="selectpicker required form-control" data-live-search="true"><option value ="">Selecione</option></select></td>';
    		strLinha += '<td style="max-width:130px"><select id="origemMercadoria_' + qtdRegras + '" name="produto.regrasTributacao[' + qtdRegras + '].codOrigemMercadoria" class="selectpicker required form-control" data-live-search="true"><option value ="">Selecione</option></select></td>';
    		strLinha += '<td style="max-width:130px"><select id="situacaoTributaria_' + qtdRegras + '" name="produto.regrasTributacao[' + qtdRegras + '].codSituacaoTributaria" class="selectpicker required form-control" data-live-search="true"><option value ="">Selecione</option></select></td>';
    		strLinha += '<td style="max-width:130px"><select id="cfop_' + qtdRegras + '" name="produto.regrasTributacao[' + qtdRegras + '].codCfop" class="selectpicker required form-control" data-live-search="true"><option value ="" >Selecione</option></select></td>';
    		strLinha += '<td style="max-width:130px"><select id="baseCalculo_' + qtdRegras + '" name="produto.regrasTributacao[' + qtdRegras + '].codBaseCalculo" class= "selectpicker required form-control" data-live-search="true"><option value ="">Selecione</option></select>';
    		strLinha += '<br/><select id="baseCalculoST_' + qtdRegras + '" name="produto.regrasTributacao[' + qtdRegras + '].codBaseCalculoST" class= "selectpicker form-control" data-live-search="true"><option value ="">Selecione</option></select></td>';
    		strLinha += '<td style="min-width:50px;max-width:80px"><input type="text" name="produto.regrasTributacao[' + qtdRegras + '].aliquota" id="aliquota_' + qtdRegras + '" class="percent required form-control" value=""/>';
    		strLinha += '<br/><input type="text" name="produto.regrasTributacao[' + qtdRegras + '].aliquotaST" id="aliquotaST_' + qtdRegras + '" class="percent form-control" value=""/></td>';
    		strLinha += '<td style="min-width:120px;max-width:100px"><input type="text" name="produto.regrasTributacao[' + qtdRegras + '].credito" id="credito_' + qtdRegras + '" class="valor required form-control" value=""/>';
    		strLinha += '<br/><input type="text" name="produto.regrasTributacao[' + qtdRegras + '].creditoST" id="creditoST_' + qtdRegras + '" class="valor form-control" value=""/></td>';
    		
    		strLinha += '</tr>';
    		$('#tabelaRegras > tbody:last').append(strLinha);
    		
    		carregarCombo($('#tipoOperacao_' + qtdRegras), tipoOperacaoList);
    		carregarCombo($('#origemMercadoria_' + qtdRegras), origemMercadoriaList);
    		carregarCombo($('#situacaoTributaria_' + qtdRegras), situacaoTributariaList);
    		carregarCombo($('#cfop_' + qtdRegras), cfopList);
    		carregarCombo($('#baseCalculo_' + qtdRegras), baseCalculoList);
    		carregarCombo($('#baseCalculoST_' + qtdRegras), baseCalculoSTList);
    		
    		qtdRegras++;
    	});
        $('#formProduto').validate({
        	ignore: ':not(select:hidden, input:visible, textarea:visible)',
            errorPlacement: function (error, element) {
                if ($(element).is('select')) {
                    element.next().after(error); // special placement for select elements
                } else {
                    error.insertAfter(element);  // default placement for everything else
                }
            }
        
    	});

    });
 
    function carregarCombo(obj, list){
    	$.each(list, function(i){
			
			
			$(obj).append($("<option></option>")
                    .attr("value",list[i].codigo)
                    .text(list[i].codigo + " - " + list[i].descricao));
		
		
	    
		});
    	$(obj).selectpicker({
        });
    	$(".percent").priceFormat({
            prefix: '',
            centsSeparator: ',',
            thousandsSeparator: '.',
            limit: 5
            
        });
        $(".valor").priceFormat({
            prefix: '',
            centsSeparator: ',',
            thousandsSeparator: '.',
            limit: 12
            
        });
    };

    
    
        
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Produtos</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/produto/add'/>" id="formProduto" name="formProduto" method="post">
		<input type="hidden" id="produtolId" name="produto.id" value="${produto.id}"/>
		
		
		<div class="row">
        	<div class="col-md-4">
        		<label for="id">Código:</label>
        		<input name="produto.codigo" id="produto.codigo" value="${produto.codigo}" class="form-control required"  size="8" maxlength="4"/>
        	</div>
        	
        	<div class="col-md-4">
        		<label for="produto.descricao">Descrição:</label>
        		<input name="produto.descricao" id="produto.descricao" value="${produto.descricao}" class="form-control required" size="45" maxlength="45"/>
        	</div>
        	<div class="col-md-4">
        		<label for="produto.ncm">Ncm:</label>
        		<input name="produto.ncm" id="produto.ncm" value="${produto.ncm}" class="form-control required" size="8" maxlength="8"/>
        	</div>
      	</div>
		<div class="row">
        	<div class="col-md-4">
        	<label for="produto.unidadeMedida.id">Unidade Medida:</label>
        		<select id="produto.unidadeMedida.id" name="produto.unidadeMedida.id" class="selectpicker required form-control" >
						<option value ="">Selecione</option>
						<c:forEach var="unidade" items="${unidadesMedidas}" varStatus="contador">
						
							<option value ="${unidade.id}" ${produto.unidadeMedida.id eq unidade.id ? 'selected' : ''}>${unidade.sigla} - ${unidade.descricao}</option>
		
						</c:forEach>	
					</select>
        	</div>
        	
        	<div class="col-md-4">
        	<label for="produto.grupoMaterial.id">Grupo Material:</label>
        		<select id="produto.grupoMaterial.id" name="produto.grupoMaterial.id" class="selectpicker required form-control" data-msg-required="Campo obrigatório." >
						<option value ="">Selecione</option>
						<c:forEach var="grupo" items="${grupos}" varStatus="contador">
						
							<option value ="${grupo.id}" ${produto.grupoMaterial.id eq grupo.id ? 'selected' : ''}>${grupo.descricao}</option>
		
						</c:forEach>	
					</select>
        	</div>
				
        	<div class="col-md-4">
        		
        	</div>
        	
      	</div>
      	<br/>
      	<div class="panel panel-default" >
			<div class="panel-body">
				<h4>Regras de Tributação</h4>
				<button type="button" id="btnAdicionarRegra" class="btn btn-default btn-md">
				  <span class="glyphicon glyphicon-plus-sign"></span> Adicionar
				</button>
				<br/>
				<br/>
				<div id="divTabela">
				<table  class="table table-bordered table-striped" id="tabelaRegras">
				
				<thead>
			<tr>
				
				<th >Tipo Operação</th>
				<th >Origem</th>
				<th>Situação tributária</th>
				<th>CFOP</th>
				<th >Base de Cálc.</th>
				<th >Alíquota</th>
				<th >Crédito</th>
				
			</tr>
			</thead>
			<tbody>
				
				<c:if test="${not empty produto.id}">
					<c:forEach var="regraTributacao" items="${produto.regrasTributacao}" varStatus="contador">
					<tr>
						<td style="max-width:15%">
							<select id="tipoOperacao_${contador}" name="produto.regrasTributacao[${contador}].codTipoOperacao" class="required form-control">
								<option value ="">Selecione</option>
								<c:forEach var="tipoOperacao" items="${tipoOperacaoList}" varStatus="contador">
								
									<option value ="${tipoOperacao.codigo}" ${regraTributacao.codTipoOperacao eq tipoOperacao.codigo ? 'selected' : ''}>${tipoOperacao.descricao}</option>
				
								</c:forEach>	
							</select>
						</td>
						<td style="max-width:15%">
							<select id="origemMercadoria_${contador}" name="produto.regrasTributacao[${contador}].codOrigemMercadoria" class="required form-control">
								<option value ="">Selecione</option>
								<c:forEach var="origemMercadoria" items="${origemMercadoriaList}" varStatus="contador">
								
									<option value ="${origemMercadoria.codigo}" ${regraTributacao.codOrigemMercadoria eq origemMercadoria.codigo ? 'selected' : ''}>${origemMercadoria.codigo} - ${origemMercadoria.descricao}</option>
				
								</c:forEach>	
							</select>
							
						</td>
						<td style="max-width:15%">
							<select id="situacaoTributaria_${contador}" name="produto.regrasTributacao[${contador}].codSituacaoTributaria" class="required form-control">
								<option value ="">Selecione</option>
								<c:forEach var="situacaoTributaria" items="${situacaoTributariaList}" varStatus="contador">
								
									<option value ="${situacaoTributaria.codigo}" ${regraTributacao.codSituacaoTributaria eq situacaoTributaria.codigo ? 'selected' : ''}>${situacaoTributaria.codigo} - ${situacaoTributaria.descricao}</option>
				
								</c:forEach>	
							</select>
						</td>
						<td style="max-width:15%">
							<select id="cfop_${contador}" name="produto.regrasTributacao[${contador}].codCfop" class="required form-control">
								<option value ="">Selecione</option>
								<c:forEach var="cfop" items="${cfopList}" varStatus="contador">
								
									<option value ="${cfop.codigo}" ${regraTributacao.codCfop eq cfop.codigo ? 'selected' : ''}>${cfop.codigo} - ${cfop.descricao}</option>
				
								</c:forEach>	
							</select>
						</td>
						<td style="max-width:15%">
							<select id="baseCalculo_${contador}" name="produto.regrasTributacao[${contador}].codBaseCalculo" class="required form-control">
								<option value ="">Selecione</option>
								<c:forEach var="baseCalculo" items="${baseCalculoList}" varStatus="contador">
								
									<option value ="${baseCalculo.codigo}" ${regraTributacao.codBaseCalculo eq baseCalculo.codigo ? 'selected' : ''}>${baseCalculo.codigo} - ${baseCalculo.descricao}</option>
				
								</c:forEach>	
							</select>
							<br/>
							<select id="baseCalculoST_${contador}" name="produto.regrasTributacao[${contador}].codBaseCalculoST" class="required form-control">
								<option value ="">Selecione</option>
								<c:forEach var="baseCalculoST" items="${baseCalculoSTList}" varStatus="contador">
								
									<option value ="${baseCalculoST.codigo}" ${regraTributacao.codBaseCalculoST eq baseCalculoST.codigo ? 'selected' : ''}>${baseCalculoST.codigo} - ${baseCalculoST.descricao}</option>
				
								</c:forEach>	
							</select>
						</td>
						<td>
							<input type="text" name="produto.regrasTributacao[${contador}].aliquota" id="aliquota_${contador}" class="required form-control" value="<fmt:formatNumber value="${regraTributacao.aliquota}" minFractionDigits="2" type="number" />" readonly="readonly"/>
							<br/>
							<input type="text" name="produto.regrasTributacao[${contador}].aliquotaST" id="aliquotaST_${contador}" class="required form-control" value="<fmt:formatNumber value="${regraTributacao.aliquotaST}" minFractionDigits="2" type="number" />" readonly="readonly"/>
						</td>
						<td>
							<input type="text" name="produto.regrasTributacao[${contador}].credito" id="credito_${contador}" class="required form-control" value="<fmt:formatNumber value="${regraTributacao.credito}" minFractionDigits="2" type="number" />" readonly="readonly"/>
							<br/>
							<input type="text" name="produto.regrasTributacao[${contador}].creditoST" id="creditoST_${contador}" class="required form-control" value="<fmt:formatNumber value="${regraTributacao.creditoST}" minFractionDigits="2" type="number" />" readonly="readonly"/>
						</td>
						
					</tr>
				</c:forEach>
				</c:if>
			</tbody>
			</table>
			
			</div>
			</div>
		</div>
      	
      	
      	
      	
      	
      	<br/>
		<button type="button" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
		</button>
		</form>
</div>
</div>
</div>