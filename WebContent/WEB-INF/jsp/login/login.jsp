<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#btnAdicionar").click(function(){
    		$("#formLogin").submit();
    	});
    	
        $('#formLogin').validate({
            
        
    	});
    	
    	
    });
</script>

<div class="container">
	<br>
	<h2>Bem-vindo</h2>
	<br />
	<div class="panel panel-default">
		<div class="panel-body">
			<form action="<c:url value='/autenticar'/>" id="formLogin"
				name="formLogin" method="post">

				<div class="row">
					<div class="col-md-4">
						<label for="login">Login:</label> <input
							name="login" id="login" 
							class="form-control required" size="8" maxlength="15" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<label for="senha">Senha:</label> <input
							name="senha" id="senha" type="password"
							class="form-control required" size="8" maxlength="8" />
					</div>
				</div>

				<br />
				<button type="button" id="btnAdicionar"
					class="btn btn-default btn-md">
					<span class="glyphicon glyphicon-floppy-disk"></span> Entrar
				</button>
			</form>
		</div>
	</div>
</div>