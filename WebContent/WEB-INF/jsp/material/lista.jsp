
<!DOCTYPE html>


</head>
<body>
<div id="data-grid-local"></div>

<script>
(function($){
	
	${listaJson}
	
    var $dgLocal = $('#data-grid-local')
    
    $dgLocal.datagrid({
        jsonStore: {
            data: {"rows":[
			<c:forEach var="material" items="${materiais}" varStatus="contador">
                {"id":"${material.id}","sigla":"${material.sigla}","descricao":"${material.descricao}"},
                
			</c:forEach>
                
            ]}
        }
    
        ,pagination:false
        ,title: 'Materiais(Produtos)'
        ,mapper:[{
            name: 'id',title:'Código',width:120,align:'center',sort: true
        },{
            name: 'sigla',title:'Sigla',width:200,align:'left',sort: true
        },{
            name: 'descricao',title:'Descrição',align:'left'
        }]
        ,onComplete: function() {
            var $config = $(this)
                .datagrid('getTbody')
                .parent() // table
                .tablesorter()
                .end();

            var cache = null;
            
            $(this).datagrid('getThead').on('mouseup','th.ui-datagrid-sort',{thead:$config.prev()[0]},function(e){
                e = $( $(e.data.thead.rows[0].cells[this.cellIndex]).trigger('sort.tablesorter')[0].className.split(' ')).filter(function(index,val){
                    return /tablesorter/.test(val) ? val : null;
                }).get().join(' ');

                $( cache === null ? this : [cache,this] )
                    .removeClass('tablesorter-header')
                    .removeClass('tablesorter-headerAsc')
                    .removeClass('tablesorter-headerDesc');
                cache = $(this).addClass(e)[0];

                e = null;
            });
        }
        ,toolBarButtons:[{
            text: 'Change Title - See console'
            ,click: function(btn) {
                btn = $(this).datagrid('option','title',(new Date()).getTime());
                console.log( btn.datagrid('option','title') )

            }
        }]
    })
    
})(jQuery)
</script>
</body> 
</html>