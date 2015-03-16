<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
    var grupos = ${gruposDoUsuarioJson};
   	 alert(grupos);
      
   	var qtdGrupos = ${fn:length(grupos)};
    $(document).ready(function(){
    	$("#btnSalvar").click(function(){
    		$("#formUsuario").submit();
    	});
    	
        $('#formUsuario').validate({
            
        
    	});
        $("#btnAdicionarGrupo").click(function(){
        	qtdGrupos++;
        	value = $('#grupo').val();
        	strLi ='<li class="list-group-item"><span id="removerGrupo_' + value + '" title="Excluir"'
				  + 'class="glyphicon glyphicon-remove"> ' + value 
				  + '</span><input type="hidden" id="grupos[' + qtdGrupos + ']" name="grupos[' + qtdGrupos + ']" value="'+ value + '"/></li>';
        	$('#grupoList ul').append(strLi);
        	$('#grupo').find('[value="' + value + '"]').remove();
        	$("#removerGrupo_" + value).click(function(){
                 	$(this).parent().remove();
                	$('#grupo').append('<option value="'+ $(this).html() + '">'+ $(this).html() + '</option>');
       		 });
        });
        $("span[id^='removerGrupo_']").click(function(){
         	$(this).parent().remove();
         	$('#grupo').append('<option value="'+ $(this).html() + '">'+ $(this).html() + '</option>');
         });
       
    	
    });
    function removerGrupo(obj){
    	alert(value);
    	alert($(obj));
    	$('#grupoList ul li[id="' + value + '"]').remove();
    }
</script> 

<div class="container">
	<br>
	<h2>Cadastro de Usuários</h2>
	<br />
	<div class="panel panel-default">
		<div class="panel-body">
			<form action="<c:url value='/usuario/adicionar'/>" id="formUsuario"
				name="formUsuario" method="post">
				<input type="hidden" id="usuariolId" name="bean.id"
					value="${bean.id}" />
				<input type="hidden" id="senhaInicial" name="bean.senhaInicial"
					value="${bean.senhaInicial}" />
				<input type="hidden" id="senha" name="bean.senha"
					value="${bean.senha}" />


				<div class="row">
					<div class="col-md-4">
						<label for="bean.nome">Nome:</label> <input name="bean.nome"
							id="bean.nome" value="${bean.nome}" class="form-control required"
							size="8" maxlength="45" />
					</div>

					<div class="col-md-4">
						<label for="bean.email">E-mail:</label> <input name="bean.email"
							id="bean.email" value="${bean.email}"
							class="form-control required" size="45" maxlength="45" />
					</div>
					<div class="col-md-4">
						<label for="bean.nome">Login:</label> <input name="bean.login"
							id="bean.login" value="${bean.login}"
							class="form-control required" size="8" maxlength="15" />
					</div>
				</div>
				</br>
				<div class="row">
					<div class="panel panel-default">
						<!-- Default panel contents -->
						<div class="panel-heading">Grupos</div>
						<div class="panel-body">
						<div style="width:350px; float:left">
							<label for="grupo">Grupo:</label> 
							<select id="grupo" name="grupo" 
								class="selectpicker form-control"
								data-live-search="true">
								<option value="">Selecione</option>
								<c:forEach var="grupo" items="${gruposUsuarios}"
									varStatus="contador">
									<option value="${grupo}">${grupo}</option>
								</c:forEach>
							</select>
						</div>
						<div style="width:150px;float:left;margin: 25px 5px">
							<button type="button" id="btnAdicionarGrupo"
								class="btn btn-default btn-md">
								<span class="glyphicon glyphicon-floppy-disk"></span> Adicionar
							</button>
						</div>
							<!-- List group -->
						<div id="grupoList" style="clear:both">
							<ul class="list-group">
								<c:forEach var="grupoUsuario" items="${gruposDoUsuario}"
									varStatus="contador">
									<li class="list-group-item"><span id="removerGrupo_${contador.index}"  title="Excluir"'
				  					class="glyphicon glyphicon-remove"> ${grupoUsuario} 
				  					</span><input type="hidden" id="grupos[${contador.index}]" name="grupos[${contador.index}]" value="${grupoUsuario}"/></li>
								</c:forEach>
							</ul>
							</div>
						</div>
					</div>
				</div>

				<br />
				<button type="button" id="btnSalvar"
					class="btn btn-default btn-md">
					<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
				</button>
			</form>
		</div>
	</div>
</div>