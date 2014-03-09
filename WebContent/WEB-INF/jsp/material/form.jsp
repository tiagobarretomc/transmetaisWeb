<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
    	
    	
    	
        $('#formFornecedor').validate({
            
 
        
    	});
    });
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transmetais</title>
</head>
<body>

	<form action="<c:url value='/material/adiciona'/>" id="formFornecedor" name="formFornecedor" method="post">
		<input type="hidden" id="materialId" name="material.id" value="${material.id}"/>
		<table width="1000px">
		<tbody>
		<tr style="background-color: #dcdee0">
			<td width="320px">Sigla: <input name="material.sigla" maxlength="4" id="material.sigla" value="${material.sigla}" class="required" size="10"/>
			Descri&ccedil;&atilde;o: <input name="material.descricao" id="material.descricao" value="${material.descricao}" class="required" size="60"/></td>
		   
		</tr>
		
		
		
	</tbody>
	</table>
	<br>
	<br/>
	
	
    <input type="submit" value="Salvar"/>
</form>
</body>
</html>