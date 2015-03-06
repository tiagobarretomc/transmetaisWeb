<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#btnAdicionar").click(function(){
    		$("#formUsuario").submit();
    	});
    	
        $('#formUsuario').validate({
            
        
    	});
    	
    	
    });
</script>

<div class="container">
	<br>
	<h2>Cadastro de Usuários</h2>
	<br />
	<div class="panel panel-default">
		<div class="panel-body">
			<form action="<c:url value='/usuario/add'/>" id="formUsuario"
				name="formUsuario" method="post">
				<input type="hidden" id="usuariolId" name="bean.id"
					value="${bean.id}" />


				<div class="row">
					<div class="col-md-4">
						<label for="bean.nome">Nome:</label> <input name="bean.nome"
							id="bean.nome" value="${bean.nome}"
							class="form-control required" size="8" maxlength="45" />
					</div>

					<div class="col-md-4">
						<label for="bean.email">E-mail:</label> <input
							name="bean.email" id="bean.email" value="${bean.email}"
							class="form-control required" size="45" maxlength="45" />
					</div>
					<div class="col-md-4">
						<label for="bean.nome">Login:</label> <input
							name="bean.login" id="bean.login" value="${bean.login}"
							class="form-control required" size="8" maxlength="15" />
					</div>
				</div>

				<br />
				<button type="button" id="btnAdicionar"
					class="btn btn-default btn-md">
					<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
				</button>
			</form>
		</div>
	</div>
</div>