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
	var regrasTributacao = ${regras};
    var qtdRegras = ${fn:length(regras)};
    $(document).ready(function(){
    	
        
        $("#btnAdicionarRegra").click(function(){
    		var strLinha = '<tr id="regra_' + qtdRegras + '">';
    		strLinha += '<td style="vertical-align: middle;"><span title="Excluir" class="glyphicon glyphicon-remove" onclick="removerRegra(' + qtdRegras +'"></span></td>';
    		strLinha += '<td style="max-width:130px"><input type="hidden" id="id_' + qtdRegras + '" name="bean.regrasTributacao[' + qtdRegras + '].id"/>';
    		strLinha += '<select id="tipoOperacao_' + qtdRegras + '" name="bean.regrasTributacao[' + qtdRegras + '].tipoOperacao.id" class="selectpicker required form-control" data-live-search="true"></select></td>';
    		strLinha += '<td style="max-width:130px"><select id="origemMercadoria_' + qtdRegras + '" name="bean.regrasTributacao[' + qtdRegras + '].origemMercadoria.id" class="selectpicker required form-control" data-live-search="true"></select></td>';
    		strLinha += '<td style="max-width:130px"><select id="situacaoTributaria_' + qtdRegras + '" name="bean.regrasTributacao[' + qtdRegras + '].situacaoTributaria.id" class="selectpicker required form-control" data-live-search="true"></select></td>';
    		strLinha += '<td style="max-width:130px"><select id="cfop_' + qtdRegras + '" name="bean.regrasTributacao[' + qtdRegras + '].cfop.id" class="selectpicker required form-control" data-live-search="true"></select></td>';
    		strLinha += '<td style="max-width:130px"><select id="baseCalculo_' + qtdRegras + '" name="bean.regrasTributacao[' + qtdRegras + '].baseCalculo.id" class= "selectpicker required form-control" data-live-search="true"></select>';
    		strLinha += '<br/><select id="baseCalculoST_' + qtdRegras + '" name="bean.regrasTributacao[' + qtdRegras + '].baseCalculoST.id" class= "selectpicker form-control" data-live-search="true"></select></td>';
    		strLinha += '<td style="min-width:50px;max-width:80px"><input type="text" name="bean.regrasTributacao[' + qtdRegras + '].aliquota" id="aliquota_' + qtdRegras + '" class="percent required form-control" value=""/>';
    		strLinha += '<br/><input type="text" name="bean.regrasTributacao[' + qtdRegras + '].aliquotaST" id="aliquotaST_' + qtdRegras + '" class="percent form-control" value=""/></td>';
    		strLinha += '<td style="min-width:100px;max-width:120px"><input type="text" name="bean.regrasTributacao[' + qtdRegras + '].credito" id="credito_' + qtdRegras + '" class="valor required form-control" value=""/>';
    		strLinha += '<br/><input type="text" name="bean.regrasTributacao[' + qtdRegras + '].creditoST" id="creditoST_' + qtdRegras + '" class="valor form-control" value=""/></td>';
    		
    		strLinha += '</tr>';
    		$('#tabelaRegras > tbody:last').append(strLinha);
    		
    		carregarCombo($('#tipoOperacao_' + qtdRegras), tipoOperacaoList);
    		carregarCombo($('#origemMercadoria_' + qtdRegras), origemMercadoriaList);
    		carregarCombo($('#situacaoTributaria_' + qtdRegras), situacaoTributariaList);
    		carregarCombo($('#cfop_' + qtdRegras), cfopList);
    		carregarCombo($('#baseCalculo_' + qtdRegras), baseCalculoList);
    		carregarCombo($('#baseCalculoST_' + qtdRegras), baseCalculoSTList);
    		onchangeBaseCalculoST($('#baseCalculoST_' + qtdRegras));
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
       $.each ($("select[id^='tipoOperacao_']"), function(i){
        	carregarCombo($(this), tipoOperacaoList, regrasTributacao[i].tipoOperacao.id);
        });
       $.each ($("select[id^='origemMercadoria_']"), function(i){
       	carregarCombo($(this), origemMercadoriaList, regrasTributacao[i].origemMercadoria.id);
       });
       $.each ($("select[id^='situacaoTributaria_']"), function(i){
       	carregarCombo($(this), situacaoTributariaList, regrasTributacao[i].situacaoTributaria.id);
       });
       $.each ($("select[id^='cfop_']"), function(i){
       	carregarCombo($(this), cfopList, regrasTributacao[i].cfop.id);
       });
       $.each ($("select[id^='baseCalculo_']"), function(i){
       	carregarCombo($(this), baseCalculoList, regrasTributacao[i].baseCalculo.id);
       });
       $.each ($("select[id^='baseCalculoST_']"), function(i){
    	   onchangeBaseCalculoST($(this));
    	   if(regrasTributacao[i].baseCalculoST != undefined){
         		carregarCombo($(this), baseCalculoSTList, regrasTributacao[i].baseCalculoST.id);
    	   }else{
    		   carregarCombo($(this), baseCalculoSTList, null);
    	   }
       });
       initFields();

    });
 
    function onchangeBaseCalculoST(obj){
		$(obj).change(function(){
			if(this.value == 0){
				$('#baseCalculoST_0'). closest('tr').find("input[id^='aliquotaST_']").attr("value",null);
				$('#baseCalculoST_0'). closest('tr').find("input[id^='creditoST_']").attr("value",null);
			}
		});
	}
    	

    
    function removerRegra(id){
    	$("#regra_" + id).remove();
    }
        
</script>

    <div class="container">
    <br>
	<h2>Cadastro de Produtos</h2>
	<br/>
	<div class="panel panel-default">
	<div class="panel-body">
	<form action="<c:url value='/produto/add'/>" id="formProduto" name="formProduto" method="post">
		<input type="hidden" id="produtoId" name="bean.id" value="${bean.id}"/>
		
		
		<div class="row">
        	<div class="col-md-4">
        		<label for="id">Código:</label>
        		<input name="bean.codigo" id="bean.codigo" value="${bean.codigo}" class="form-control required"  size="8" maxlength="4"/>
        	</div>
        	
        	<div class="col-md-4">
        		<label for="bean.descricao">Descrição:</label>
        		<input name="bean.descricao" id="bean.descricao" value="${bean.descricao}" class="form-control required" size="45" maxlength="45"/>
        	</div>
        	<div class="col-md-4">
        		<label for="bean.ncm">Ncm:</label>
        		<input name="bean.ncm" id="bean.ncm" value="${bean.ncm}" class="form-control required" size="8" maxlength="8"/>
        	</div>
      	</div>
		<div class="row">
        	<div class="col-md-4">
        	<label for="bean.unidadeMedida.id">Unidade Medida:</label>
        		<select id="bean.unidadeMedida.id" name="bean.unidadeMedida.id" class="selectpicker required form-control" >
						<option value ="">Selecione</option>
						<c:forEach var="unidade" items="${unidadesMedidas}" varStatus="contador">
						
							<option value ="${unidade.id}" ${bean.unidadeMedida.id eq unidade.id ? 'selected' : ''}>${unidade.sigla} - ${unidade.descricao}</option>
		
						</c:forEach>	
					</select>
        	</div>
        	
        	<div class="col-md-4">
        	<label for="bean.grupoMaterial.id">Grupo Material:</label>
        		<select id="bean.grupoMaterial.id" name="bean.grupoMaterial.id" class="selectpicker required form-control" data-msg-required="Campo obrigatório." >
						<option value ="">Selecione</option>
						<c:forEach var="grupo" items="${grupos}" varStatus="contador">
						
							<option value ="${grupo.id}" ${bean.grupoMaterial.id eq grupo.id ? 'selected' : ''}>${grupo.descricao}</option>
		
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
				<th ></th>
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
				
				<c:if test="${not empty bean.id}">
					<c:forEach var="regraTributacao" items="${bean.regrasTributacao}" varStatus="contador">
					<tr id="regra_${contador.index}">
						<td style="vertical-align: middle;">
							<span title="Excluir" class="glyphicon glyphicon-remove" onclick="removerRegra(${contador.index})"></span>
						</td>
						<td style="max-width:130px" >
						    <input type="hidden" id="id_${contador.index}" name="bean.regrasTributacao[${contador.index}].id" value="${regraTributacao.id}"/>
							<select id="tipoOperacao_${contador.index}" name="bean.regrasTributacao[${contador.index}].tipoOperacao.id" class="required form-control">
							</select>
						</td>
						<td style="max-width:130px" >
							<select id="origemMercadoria_${contador.index}" name="bean.regrasTributacao[${contador.index}].origemMercadoria.id" class="required form-control">
							</select>
							
						</td>
						<td style="max-width:130px" >
							<select id="situacaoTributaria_${contador.index}" name="bean.regrasTributacao[${contador.index}].situacaoTributaria.id" class="required form-control">
							</select>
						</td>
						<td style="max-width:130px" >
							<select id="cfop_${contador.index}"  name="bean.regrasTributacao[${contador.index}].cfop.id" class="required form-control">
							</select>
						</td>
						<td style="max-width:130px" >
							<select id="baseCalculo_${contador.index}" name="bean.regrasTributacao[${contador.index}].baseCalculo.id" class="required form-control">
							</select>
							<br/>
							<select id="baseCalculoST_${contador.index}" name="bean.regrasTributacao[${contador.index}].baseCalculoST.id"  class="form-control">
							</select>
						</td>
						<td style="min-width:50px;max-width:80px">
							<input type="text" name="bean.regrasTributacao[${contador.index}].aliquota" id="aliquota_${contador.index}" class="percent required form-control" value="<fmt:formatNumber value="${regraTributacao.aliquota}" minFractionDigits="2" type="number" />" />
							<br/>
							<input type="text" name="bean.regrasTributacao[${contador.index}].aliquotaST" id="aliquotaST_${contador.index}" class="percent form-control" value="<fmt:formatNumber value="${regraTributacao.aliquotaST}" minFractionDigits="2" type="number" />" />
						</td>
						<td style="min-width:100px;max-width:120px">
							<input type="text" name="bean.regrasTributacao[${contador.index}].credito" id="credito_${contador.index}" class="valor required form-control" value="<fmt:formatNumber value="${regraTributacao.credito}" minFractionDigits="2" type="number" />" />
							<br/>
							<input type="text" name="bean.regrasTributacao[${contador.index}].creditoST" id="creditoST_${contador.index}" class="valor form-control" value="<fmt:formatNumber value="${regraTributacao.creditoST}" minFractionDigits="2" type="number" />" />
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