<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
    $(document).ready(function(){
    	/*
    	$("#cpfCnpj").keyup(function() {
    		if($("#cpfCnpj").val().length==14)
        	{
    			$("#cpfCnpj").mask('999.999.999-99');
        	}
        	else if($("#cpfCnpj").val().length==18)
        	{
        		$("#cpfCnpj").mask('99.999.999/9999-99');
        	 
        	}
    		});
    	
    	*/
    	
    	
    	$("#telefoneFixo").mask("(99) 9999-9999");
    	$("#telefoneCelular").mask("(99) 9999-9999");
    	
        $('#formFornecedor').validate({
            
 
        
    	});
    });
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transmetais</title>
</head>
<body>

	<form action="<c:url value='/transportador/adiciona'/>" id="formTransportador" name="formTransportador" method="post">
		<input type="hidden" id="transportadorId" name="transportador.id" value="${transportador.id}"/>
		<table width="1000px">
		<tbody>
		<tr style="background-color: #dcdee0">
			<td width="320px">Motorista: <input name="transportador.nome" id="transportador.nome" value="${transportador.nomeMotorista}" class="required" size="60"/></td>
			<td width="130px">Proprietário: <input name="transportador.nomeProprietario" id="fornecedor.nomeProprietario" value="${transportador.nomeProprietario}" class="required" size="20"/></td>
		    <td >Cpf/Cnpj:<input id="cpfCnpj" name="fornecedor.cpfCnpj" value = "${transportador.cpfCnpj }" size="20"class="required"/></td>
		</tr>
		
		<tr style="background-color: #dcdee0">
			<td >Email: <input name="transportador.email" value = "${transportador.email }" size="60"/></td>
			<td >Telefone:<input id="telefoneFixo" name="transportador.telefoneFixo" value = "${transportador.telefoneFixo}" size="20"/></td>
			<td >Celular:<input id="telefoneCelular" class="required" name="transportador.telefoneCelular" value="${ transportador.telefoneCelular}" size="20"/></td>
		</tr>
		<tr style="background-color: #dcdee0">
			<td >Endere&ccedil;o:<input name="transportador.endereco" value = "${transportador.endereco }" size="60"/></td>
			<td >Cidade:<input name="transportador.cidade" class="required" value = "${transportador.cidade }" size="20"/></td>
			<td >Estado:<input name="transportador.estado" class="required" value = "${transportador.estado }"/></td>
			
		</tr>
		
		
		
		<tr style="background-color: #dcdee0">
			<td >Banco: <input name="transportador.banco" value = "${transportador.banco }" size="60"/></td>
			<td >Ag&ecirc;ncia:<input name="transportador.agencia" value = "${transportador.agencia }" size="20"/></td>
			<td >Conta Corrente: <input name="transportador.contaCorrente" value = "${transportador.contaCorrente }" size="20"/></td>
			
		</tr>
		
		
		<tr style="background-color: #dcdee0">
			<td colspan="2">Nome Titular: <input name="transportador.nomeTitular" value = "${transportador.nomeTitular }" size="80"/></td>
			<td >Cpf/Cnpj Titular:<input name="transportador.cpfCnpjTitular" value = "${transportador.cpfCnpjTitular }" size="20"/></td>
		</tr>
		
	</tbody>
	</table>
	<br>
	<br/>
	
    <input type="submit" value="Salvar"/>
</form>
</body>
</html>