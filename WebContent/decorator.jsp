<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html; charset=UTF-8" %>  
<html lang="pt-br">

	<head>
		<title>Transmetais Web</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" /> 
		<meta http-equiv="content-language" content="pt-br" />
		
    	<link href="/transmetaisWeb/css/bootstrap.css" rel="stylesheet" type="text/css">
    	<link href="/transmetaisWeb/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
    	<link href="/transmetaisWeb/css/datepicker.css" rel="stylesheet" type="text/css">
    	
    	
		
		<!--  
		
		-->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		 <script src="/transmetaisWeb/javascripts/bootstrap-select.min.js" type="text/javascript"></script>
		  <script src="/transmetaisWeb/javascripts/bootstrap-datepicker.js" type="text/javascript"></script>
		 <script src="/transmetaisWeb/javascripts/bootstrap.js" type="text/javascript"></script>
		  
		<script src="/transmetaisWeb/javascripts/jquery.validate.min.js" type="text/javascript" ></script>
		<script src="/transmetaisWeb/javascripts/jquery.maskedinput.min.js" type="text/javascript"></script>
		 <script type="text/javascript" src="/transmetaisWeb/javascripts/jquery.price_format.2.0.min.js"/></script>
		  
		<!-- 
		<script type="text/javascript" src="/transmetaisWeb/javascripts/jquery.puts.js"></script>
		 -->
		 <script>
		 jQuery.extend(jQuery.validator.messages, {
			    required: "Campo obrigatório.",
			    remote: "Please fix this field.",
			    email: "Please enter a valid email address.",
			    url: "Please enter a valid URL.",
			    date: "Please enter a valid date.",
			    dateISO: "Please enter a valid date (ISO).",
			    number: "Please enter a valid number.",
			    digits: "Please enter only digits.",
			    creditcard: "Please enter a valid credit card number.",
			    equalTo: "Please enter the same value again.",
			    accept: "Please enter a value with a valid extension.",
			    maxlength: jQuery.validator.format("Please enter no more than {0} characters."),
			    minlength: jQuery.validator.format("Please enter at least {0} characters."),
			    rangelength: jQuery.validator.format("Please enter a value between {0} and {1} characters long."),
			    range: jQuery.validator.format("Please enter a value between {0} and {1}."),
			    max: jQuery.validator.format("Please enter a value less than or equal to {0}."),
			    min: jQuery.validator.format("Please enter a value greater than or equal to {0}.")
			});
		 </script>

		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
		
		<fmt:setLocale value="pt_br"/>
		
		<style>
		#data-grid-local {margin:0 auto}
		.ui-corner-bottom {
		    border-bottom-left-radius: 2px;
		    border-bottom-right-radius: 2px;
		}
		.ui-corner-right {
		    border-top-right-radius: 2px;
		    border-bottom-right-radius: 2px;
		}
		.ui-corner-left { 
		    border-top-left-radius: 2px;
		    border-bottom-left-radius: 2px;
		}
		.ui-corner-all {
		    border-radius: 2px;
		}
	  	form { padding: 10px; }
	   .required.error { border: 1px solid #b94a48!important; background-color: #fee!important; }
	   .error {
	   		color: #F2DADA!important;
	   }
		</style>
	</head>
	
<body>
	<div id="header">
		
	</div>
	<div id="erros">
		<ul>
		</ul>
	</div>
	<div id="body">
		
		<div id="menu">
			<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Transmetais</a>
        </div>
       
         <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            
            <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Cadastros <b class="caret"></b></a>
	          <ul class="dropdown-menu">
	          	<li><a href="<c:url value='/unidade/'/>">Unidades da Transmetais</a></li>
	          	<li><a href="<c:url value='/funcionario/'/>">Funcionários</a></li>
	          	 <li class="divider"></li>
	            <li><a href="<c:url value='/fornecedor/'/>">Fornecedor</a></li>
	            <li><a href="<c:url value='/cliente/'/>">Cliente</a></li>
	            <li><a href="<c:url value='/transportador/'/>">Transportador</a></li>
	            <li class="divider"></li>
	            <li><a href="<c:url value='/material/'/>">Material</a></li>
	             <li><a href="<c:url value='/produto/'/>">Produto</a></li>
	            <li><a href="<c:url value='/unidadeMedida/'/>">Unidade de Medida</a></li>
	            <li><a href="<c:url value='/grupoMaterial/'/>">Grupo de Material</a></li>
	            <li class="divider"></li>
	             <li><a href="<c:url value='/centroCusto/'/>">Centro de Custo</a></li>
	             <li><a href="<c:url value='/centroAplicacao/'/>">Centro de Aplicação</a></li>
	             <li><a href="<c:url value='/contaBancaria/'/>">Contas Bancárias</a></li>
	             <li><a href="<c:url value='/contaContabil/'/>">Contas Contábeis</a></li>
	            
	          </ul>
	        </li>
            <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Compras <b class="caret"></b></a>
	          <ul class="dropdown-menu">
	          	 <li><a href="<c:url value='/compra/'/>">Compra de Metais Ferrosos e Não Ferrosos</a></li>
	          	<li><a href="<c:url value='/adiantamento/'/>">Adiantamento a Fornecedores</a></li>
	          	
	            
	          </ul>
	        </li>
            
            <li><a href="#">Vendas</a></li>
            <li><a href="#"></a></li>
            
            <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Financeiro <b class="caret"></b></a>
	          <ul class="dropdown-menu">
	          	 <li><a href="<c:url value='/contasPagar/'/>">Contas a Pagar</a></li>
	          	<li><a href="<c:url value='/contasReceber/'/>">Contas a Receber</a></li>
	          </ul>
	        </li>
            <li><a href="#">Consultas</a></li>
            
            
            
            
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
		</div>
		<div id="content">
		

		<sitemesh:write property="body" />

		
		
		</div><!-- div content -->
	</div><!-- div body -->
	<div id="footer">
	
	</div>

</body>
</html>