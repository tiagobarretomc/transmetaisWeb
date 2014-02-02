<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<script type="text/javascript">

(function($){
    var $dgLocal = $('#data-grid-local');
     
    $dgLocal.datagrid({
        jsonStore: {
            data: {"rows":[
                {"id":"1","nome":"Fulano de Tal","empresa":"Empresa 1"}
                ,{"id":"2","nome":"Beltrano da Silva","empresa":"Empresa 2"}
                ,{"id":"3","nome":"Beltrano da Silva","empresa":"Empresa do tal"}
                ,{"id":"4","nome":"Beltrano da Silva","empresa":"Empresa 123122"}
                ,{"id":"5","nome":"Beltrano da Silva","empresa":"Empresa 2"}
                ,{"id":"6","nome":"Beltrano da Silva","empresa":"Empresa 3312"}
                ,{"id":"7","nome":"Beltrano da Silva","empresa":"Empresa 312"}
                ,{"id":"8","nome":"Beltrano da Silva","empresa":"Empresa 3123122"}
                ,{"id":"9","nome":"Fulano de Tal","empresa":"Empresa 221"}
                ,{"id":"10","nome":"Beltrano da Silva","empresa":"Empresa 2"}
                ,{"id":"11","nome":"Beltrano da Silva","empresa":"Empresa 3123122"}
                ,{"id":"12","nome":"Beltrano da Silva","empresa":"Empresa 2"}
                ,{"id":"13","nome":"Beltrano da Silva","empresa":"Empresa 2"}
                ,{"id":"14","nome":"Beltrano da Silva","empresa":"Empresa 2"}
                ,{"id":"15","nome":"Beltrano da Silva","empresa":"Empresa 2"}
                ,{"id":"16","nome":"Beltrano da Silva","empresa":"Empresa 2"}
                ,{"id":"17","nome":"Fulano de Tal","empresa":"Empresa 2"}
                ,{"id":"18","nome":"Beltrano da Silva","empresa":"Empresa 1232"}
                ,{"id":"18","nome":"Beltrano da Silva","empresa":"Empresa 2"}
                ,{"id":"20","nome":"Fulano de Tal","empresa":"Empresa 2"}
                ,{"id":"21","nome":"Beltrano da Silva","empresa":"Empresa23 2"}
                ,{"id":"22","nome":"Beltrano da Silva","empresa":"Empresa 2"}
                ,{"id":"23","nome":"Fulano de Tal","empresa":"Empresa 1233332"}
            ]}
        }
        ,pagination: false
        ,mapper:[{
            name: 'id',alias:'Código',css:{width:50,textAlign:'center'}
        },{
            name: 'nome',alias:'Nome',css:{width:200,textAlign:'left'}
        },{
            name: 'empresa',alias:'Empresa',css:{textAlign:'left'}
        }]
    })
     
})(jQuery)
     

</script>
<body>
		
        <div id="data-grid-local"></div>
    
		<table width="1024px">
		
		<thead>
	<tr>
		<td width="10%">Codigo</td>
		<td width="10%">Sigla</td>
		<td width="90%">Descri&ccedil;&atilde;o</td>
		
		
	</tr>
	</thead>
	<tbody>
		<c:forEach var="material" items="${materiais}" varStatus="contador">
	
		<tr>
			<td>
				<a href="<c:url value='/material/'/>${material.id}">${material.id}</a> 
			</td>
			<td>
				${material.sigla} 
			</td>
			<td>
				${material.descricao} 
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
</body>
</html>