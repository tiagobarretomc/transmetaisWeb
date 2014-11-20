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
	$(".percent").priceFormat({
		prefix : '',
		centsSeparator : ',',
		thousandsSeparator : '.',
		limit : 5

	});
	$(".valor").priceFormat({
		prefix : '',
		centsSeparator : ',',
		thousandsSeparator : '.',
		limit : 12

	});
	$('.selectpicker').selectpicker();
	
	$('.datepicker').datepicker({
   		language : 'pt-BR',
   		autoclose : true,
   		format : 'dd/mm/yyyy'
   	 	
   	}); 
	
	$('.datepicker').mask("99/99/9999");
	
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