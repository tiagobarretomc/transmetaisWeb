<%@page contentType="text/html; charset=UTF-8" isELIgnored ="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">

	<c:if test="${bean.class.simpleName eq 'ComprovantePesagemEntrada' }">
		var materialList = ${materialList};
	</c:if>
	<c:if test="${bean.class.simpleName eq 'ComprovantePesagemSaida' }">
		var produtoList = ${produtoList};
	</c:if>
	var itensPesagem = ${itensPesagem};
    var qtdItensPesagem = ${fn:length(itensPesagem)};
    $(document).ready(function(){
    	
        
        $("#btnAdicionarItem").click(function(){
    		var strLinha = '<tr id="item_' + qtdItensPesagem + '">';
    		strLinha += '<td style="vertical-align: middle;"><span title="Excluir" class="glyphicon glyphicon-remove" onclick="removerItem(' + qtdItensPesagem +')"></span></td>';
    		strLinha += '<td style="max-width:130px"><input type="hidden" id="id_' + qtdItensPesagem + '" name="bean.itens[' + qtdItensPesagem + '].id"/>';
    		strLinha +='<input name="bean.itens[' + qtdItensPesagem + '].nrItem" id="nrItem_' + qtdItensPesagem + 'class="form-control required" size="8" value="' + (qtdItensPesagem + 1) + '" readonly="readonly"/></td>';
    		strLinha +='<td>';
    		<c:if test="${bean.class.simpleName eq 'ComprovantePesagemEntrada' }">
    			strLinha += '<select id="material_' + qtdItensPesagem + '" name="bean.itens[' + qtdItensPesagem + '].material.id" class="selectpicker required form-control" data-live-search="true"></select></td>';
    		</c:if>
    		<c:if test="${bean.class.simpleName eq 'ComprovantePesagemSaida' }">
				strLinha += '<select id="produto_' + qtdItensPesagem + '" name="bean.itens[' + qtdItensPesagem + '].produto.id" class="selectpicker required form-control" data-live-search="true"></select></td>';
			</c:if>;
			strLinha +='</td> ';
			strLinha +='<td style="max-width:130px"><input type="text" name="bean.itens['+ qtdItensPesagem + '].pesoPercentual" id="pesoPercentual_' + qtdItensPesagem + '"  class="required form-control percent"  /></td>';
			strLinha +='<td style="max-width:130px"><input type="text" name="bean.itens[' + qtdItensPesagem + '].pesoLiquido" id="pesoLiquido_' + qtdItensPesagem + '" class="required form-control valor" /></td>';
    		strLinha += '</tr>';
    		$('#tabelaItens > tbody:last').append(strLinha);
    		
    		<c:if test="${bean.class.simpleName eq 'ComprovantePesagemEntrada' }">
    			carregarCombo($('#material_' + qtdItensPesagem), materialList);
    		</c:if>
    		<c:if test="${bean.class.simpleName eq 'ComprovantePesagemSaida' }">
    			carregarCombo($('#produto_' + qtdItensPesagem), produtoList);
    		</c:if>
    		initFields();
    		qtdItensPesagem++;
    	});
        $('#formComprovantePesagem').validate({
        	ignore: ':not(select:hidden, input:visible, textarea:visible)',
            errorPlacement: function (error, element) {
                if ($(element).is('select')) {
                    element.next().after(error); // special placement for select elements
                } else {
                    error.insertAfter(element);  // default placement for everything else
                }
            }
        
    	});
       $.each ($("select[id^='material_']"), function(i){
        	carregarCombo($(this), materialList, itensPesagem[i].material.id);
        });
       $.each ($("select[id^='produto_']"), function(i){
       	carregarCombo($(this), produtoList, itensPesagem[i].produto.id);
       });
       initFields();
	
    });
 
    
    function removerItem(id){
    	$("#item_" + id).remove();
    	
    }
        
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Comprovante de Pesagem</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/cp/add'/>" id="formComprovantePesagem" name="formComprovantePesagem" method="post">
		<input type="hidden" id="comprovantePesagemId" name="bean.id" value="${bean.id}"/>
		
		
		<div class="row">
        	<div class="col-md-4">
        		<label for="id">Data de Emissão:</label>
        		<input name="bean.dataEmissao" id="bean.dataEmissao" value="<fmt:formatDate value="${bean.dataEmissao}" type="date" pattern="dd/MM/yyyy"/>" class="form-control required datepicker" />
        	</div>
        	
        	<div class="col-md-4">
        		<label for="bean.numeroTicket">Número do Ticket:</label>
        		<input name="bean.numeroTicket" id="bean.numeroTicket" value="${bean.numeroTicket}" class="form-control required" size="45" maxlength="45"/>
        	</div>
        	<div class="col-md-4">
        		<label for="bean.balanca">Balança:</label>
        		<input name="bean.balanca" id="bean.balanca" value="${bean.balanca}" class="form-control required" size="45" maxlength="45"/>
        	</div>
        </div>
        <div class="row">
        	<div class="col-md-4">
        		<label for="bean.placaVeiculo">Placa do veículo:</label>
        		<input name="bean.placaVeiculo" id="bean.placaVeiculo" value="${bean.placaVeiculo}" class="form-control required" size="45" maxlength="45"/>
        	</div>
        	<div class="col-md-4">
        		<label for="bean.nomeTransportador">Nome do Transportador:</label>
        		<input name="bean.nomeTransportador" id="bean.nomeTransportador" value="${bean.nomeTransportador}" class="form-control required" size="45" maxlength="45"/>
        	</div>
        	<div class="col-md-4">
				<label for="cboTipoFrete">Forma de Frete/Entrega:</label>
				<select style="width: 180px;" id="cboTipoFrete" name="bean.tipoFrete" class="selectpicker form-control" ${not empty bean.id ? 'disabled="disabled"' : ''}>
					<option value="" >Selecione</option>
					<c:forEach var="tipoFrete" items="${tiposFrete}">
						<option value="${tipoFrete.name }" ${bean.tipoFrete eq tipoFrete ? 'selected' : ''}>${tipoFrete.descricao}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md-4">
				<label for="cboTipoVeiculo">Tipo de veículo:</label>
				<select style="width: 180px;" id="cboTipoVeiculo" name="bean.tipoVeiculo" class="selectpicker form-control" ${not empty bean.id ? 'disabled="disabled"' : ''}>
					<option value="" >Selecione</option>
					<c:forEach var="tipoVeiculo" items="${tiposVeiculo}">
						<option value="${tipoVeiculo.id }" ${bean.tipoVeiculo.id eq tipoVeiculo.id ? 'selected' : ''}>${tipoVeiculo.descricao}</option>
					</c:forEach>
				</select>
			</div>
      	</div>
		<div class="row">
			<div class="col-md-5">
				<label for="taraVeiculo">Tara do Veículo (Kg):</label>
        		<input type="text" name="bean.taraVeiculo" id="taraVeiculo" class="required form-control" value="<fmt:formatNumber value="${bean.taraVeiculo}" minFractionDigits="2" type="number" />" />
        	</div>
        	<div class="col-md-5">
				<label for="percentualImpureza">Impureza (%):</label>
        		<input type="text" name="bean.percentualImpureza" id="percentualImpureza" class="required form-control" value="<fmt:formatNumber value="${bean.percentualImpureza}" minFractionDigits="2" type="number" />" />
        	</div>
        	<div class="col-md-5">
				<label for="pesoBruto">Peso Bruto (Kg):</label>
        		<input type="text" name="bean.pesoBruto" id="pesoBruto" class="required form-control" value="<fmt:formatNumber value="${bean.pesoBruto}" minFractionDigits="2" type="number" />" />
        	</div>
        	<div class="col-md-5">
				<label for="pesoImpureza">Impureza (Kg):</label>
        		<input type="text" name="bean.pesoImpureza" id="pesoImpureza" class="required form-control" value="<fmt:formatNumber value="${bean.pesoImpureza}" minFractionDigits="2" type="number" />" />
        	</div>
        	<div class="col-md-5">
				<label for="pesoLiquido">Peso Líquido (Kg):</label>
        		<input type="text" name="bean.pesoLiquido" id="pesoLiquido" class="required form-control" value="<fmt:formatNumber value="${bean.pesoLiquido}" minFractionDigits="2" type="number" />" />
        	</div>
      	</div>
      	<br/>
      	<div class="panel panel-default" >
			<div class="panel-body">
				<h4>Itens de Pesagem</h4>
				<button type="button" id="btnAdicionarItem" class="btn btn-default btn-md">
				  <span class="glyphicon glyphicon-plus-sign"></span> Adicionar
				</button>
				<br/>
				<br/>
				<div id="divTabela">
				<table  class="table table-bordered table-striped" id="tabelaItens">
				
				<thead>
			<tr>
				<th ></th>
				<th >Item</th>
				<c:if test="${bean.class.simpleName eq 'ComprovantePesagemEntrada' }">
					<th >Material</th>
				</c:if>
				<c:if test="${bean.class.simpleName eq 'ComprovantePesagemSaida' }">
					<th >Produto</th>
				</c:if>
				<th >Peso (%)</th>
				<th>Peso (Kg)</th>
				
			</tr>
			</thead>
			<tbody>
				
				<c:if test="${not empty bean.id}">
					<c:forEach var="item" items="${bean.itens}" varStatus="contador">
					<tr id="item_${contador.index}">
						<td style="vertical-align: middle;">
							<span title="Excluir" class="glyphicon glyphicon-remove" onclick="removerItem(${contador.index})"></span>
						</td>
						<td style="max-width:130px" >
						    <input type="hidden" id="id_${contador.index}" name="bean.itens[${contador.index}].id" value="${item.id}"/>
							<input id="nrItem_${contador.index}" id="bean.itens[${contador.index}]..nrItem class="form-control required" size="8"/>
						</td>
						<td style="max-width:130px" >
							<c:if test="${bean.class.simpleName eq 'ComprovantePesagemEntrada' }">
								<select id="material_${contador.index}" name="bean.itens[${contador.index}].material.id" class="required form-control selectpicker"  data-live-search="true">
								</select>
							</c:if>
							<c:if test="${bean.class.simpleName eq 'ComprovantePesagemSaida' }">
								<select id="produto_${contador.index}" name="bean.itens[${contador.index}].produto.id" class="required form-control selectpicker"  data-live-search="true">
								</select>
							</c:if>
						</td>
						<td style="max-width:130px" >
							<input type="text" name="bean.itens[${contador.index}].pesoPercentual" id="pesoPercentual_${contador.index}" class="required form-control" value="<fmt:formatNumber value="${bean.itens[contador.index].pesoPercentual}" minFractionDigits="2" type="number" />" readonly="readonly"/>
						</td>
						<td style="max-width:130px" >
							<input type="text" name="bean.itens[${contador.index}].pesoLiquido" id="pesoLiquido_${contador.index}" class="required form-control" value="<fmt:formatNumber value="${bean.itens[contador.index].pesoLiquido}" minFractionDigits="2" type="number" />" readonly="readonly"/>
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
		<button type="submit" id="btnAdicionar" class="btn btn-default btn-md">
		  <span class="glyphicon glyphicon-floppy-disk"></span> Salvar
		</button>
		</form>
</div>
</div>
</div>