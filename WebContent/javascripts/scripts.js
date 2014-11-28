function carregarCombo(obj, list, selecionado) {
	
	if ($(obj).hasClass("required")) {
		$(obj).append(
				$("<option></option>").attr("value", "").text("Selecione"));
	} else {
		$(obj)
				.append(
						$("<option></option>").attr("value", 0).text(
								"Selecione"));
	}

	$.each(list, function(i) {

		$(obj).append(
				$("<option></option>").attr("value", list[i].codigo).attr(
						'selected', list[i].codigo == selecionado).text(
						list[i].descricao));

	});
	
	$(obj).selectpicker({});
};
function initFields() {
	
	addRules();
	try{
		$(".percent").priceFormat({
			prefix : '',
			centsSeparator : ',',
			thousandsSeparator : '.',
			limit : 5
			
		});
	}catch(err) {
	}
	try{
		$(".valor").priceFormat({
			prefix : '',
			centsSeparator : ',',
			thousandsSeparator : '.',
			limit : 12
			
		});
	}catch(err) {
	}
	try{
		$('.selectpicker').selectpicker();
	}catch(err) {
	}
	try{
		$('.datepicker').mask("99/99/9999");
		$('.datepicker').datepicker({
	   		language : 'pt-BR',
	   		autoclose : true,
	   		format : 'dd/mm/yyyy'
	   	 	
	   	}); 
	}catch(err) {
	}
}

function float2moeda(num) {

	   x = 0;

	   if(num<0) {
	      num = Math.abs(num);
	      x = 1;
	   }
	   if(isNaN(num)) num = "0";
	      cents = Math.floor((num*100+0.5)%100);

	   num = Math.floor((num*100+0.5)/100).toString();

	   if(cents < 10) cents = "0" + cents;
	      for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	         num = num.substring(0,num.length-(4*i+3))+'.'
	               +num.substring(num.length-(4*i+3));
	      ret = num + ',' + cents;
	      
	      if (x == 1) ret = ' - ' + ret;return ret;

}

function moeda2float(moeda){
	   var retorno = moeda.replace(".","");
	  
	   retorno = retorno.replace(",",".");

	   return parseFloat(retorno);

	}
function roundNumber (rnum) {

	   return Math.round(rnum*Math.pow(10,2))/Math.pow(10,2);

}
function somaSelectores(selector) {
    var sum = 0;
    $(selector).each(function() {
        sum += moeda2float($(this).val());
    });
    return sum;
}

function addRules(){
	jQuery.validator.addMethod( "dateLessThanToday", function( value, element ) {
   	    /* ... regra de validação ... */
   	    var isValid = false;
   	    
   	   var dataPagamento = Date.parseExact(value,'dd/MM/yyyy');
   	   var hoje = Date.today();
   	  
   	  
   	 	if(dataPagamento.compareTo(hoje) <= 0){
   	 		return true;
   	 	}else{
   	 		return false;
   	 	}
   	 	
   	 	//alert(dataPagamento <= hoje );
   	 	
   	    return  isValid;
   	 
   	}, "A data deve ser menor ou igual a data atual." );
	
}