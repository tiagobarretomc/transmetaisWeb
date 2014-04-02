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
}