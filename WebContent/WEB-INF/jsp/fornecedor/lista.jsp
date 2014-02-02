<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Transmetais</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Cadastros</a></li>
            <li><a href="#about">Comercial</a></li>
            <li><a href="#contact">Relatórios</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
<script src="js/bootstrap.min.js"></script>
<div class="container">
		<br/>
		<h2>Fornecedores</h2>
		<br>
		<table width="1024px">
		
		<thead>
	<tr>
		<td width="3%"></td>
		<td width="10%">Apelido</td>
		<td width="30%">Nome</td>
		<td width="10%">Estado</td>
		<td width="20%">Cidade</td>
		<td width="10%">Email</td>
		<td width="17%">Telefone</td>
		
	</tr>
	</thead>
	<tbody>
	
		<c:forEach var="fornecedor" items="${fornecedores}" varStatus="contador">
	
		<tr>
			<td>
				<a href="${fornecedor.id}"><span style="color: black;" class="glyphicon glyphicon-pencil"></span></a> 
			</td>
			<td>
				${fornecedor.apelido}
			</td>
			<td>
				${fornecedor.nome} 
			</td>
			<td>
				${fornecedor.cidade.uf}
			</td>
			<td>
				${fornecedor.cidade.nome}
			</td>
			<td>
				${fornecedor.email}
			</td>
			<td>
				${fornecedor.telefoneCelular}
			</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
</body>
</html>