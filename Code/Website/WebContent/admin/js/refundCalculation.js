function refundCalculation() {
	var refundPercentage = Number($('#refundPercentage').val());
	var originPrice = Number($('#originPrice').text());
	var compensation = (((refundPercentage - 100) * originPrice) / 100).toFixed(3);
	$('#compensation').text(compensation);
	var total = (Number(compensation) + Number(originPrice)).toFixed(3);
	$('#total').text(total);
	$('#refundPercentage').change(function () {
		var refundPercentage = Number($('#refundPercentage').val());
		var originPrice = Number($('#originPrice').text());
		var compensation = (((refundPercentage - 100) * originPrice) / 100).toFixed(3);
		$('#compensation').text(compensation);
		var total = (Number(compensation) + Number(originPrice)).toFixed(3);
		$('#total').text(total);
	});
}

