$.fn.hasAttr = function(name) {  
   return this.attr(name) !== undefined;
};

$(document).ready(function() {
    $('input[knumber]').each(function(){
    	$(this).inputNumber();
    });
    
	$('.input-preview').keyup(function(){
		var elem   = $(this);
		var value;
		var type = elem.data('type') ? elem.data('type') : '';
		var target = elem.data('target');

		if(type == '') {
			value  = elem.val();
			$(target).html(value);
		} else if(type == 'select') {
			value = elem.find('option:selected').text();
			$(target).html(value);
		}
	});
	$('.input-preview').change(function(){
		var elem   = $(this);
		preview_lading($(this));
	});
    
    // Tu dong load Balance
    $.ajax({
        url     : "ajax/frontend/cashAjax/getBalanceInfo?alert=1",
        dataType: "JSON",              
        type    : "get",
        success : function(data) {
            if(data.code == 1){
                $('#alert-balance').html(data.html);
            }
            return false;
        }
    });
    
    // auto cacule fee - mr.Kien
    $('#frm-calculate-fee, input[name="product[service]"]').change(function() {
        var dataform = $('#frm-calculate-fee').serializeArray();
        from_city       = parseInt($('select[name="product[from_city]"]').find('option:selected').val());
        from_district   = parseInt($('select[name="product[from_district]"]').find('option:selected').val());
        to_city         = parseInt($('select[name="product[to_city]"]').find('option:selected').val());
        to_district     = parseInt($('select[name="product[to_district]"]').find('option:selected').val());
        quantity        = $('input[name="product[quantity]"]').val();
        weight          = $('input[name="product[weight]"]').val();
        price           = $('input[name="product[price]"]').val();

        quantity        = parseInt(quantity.replace('.',''));
        weight          = parseInt(weight.replace('.',''));
        price           = parseInt(price.replace('.',''));
        
        // Tự động tính phí
        if(from_city > 0 && from_district > 0 && to_city > 0 && to_district > 0 && quantity > 0 && weight > 0 && price > 0)
        {
            $.ajax({
                url     : "ajax/frontend/lading_ajax/calculate_fee?do=create",
                dataType: "JSON",
                data    : dataform,             
                type    : "POST",
                success : function(data) {
                    $('#main-price').html(data.pvc);
                    $('#vas-price').html(data.vas);
                    $('#discount-price').html(data.discount +'đ');
                    $('#sum-price').html(data.sum +'đ');
                    $('#shipchung-discount-price').html(data.sc_discount +'đ');
                }
            });
        }
        return false;
    });

    // Accept create
    $('#product_agree').change(function(){
        if($(this).hasAttr('checked')) {
            $(this).removeAttr('checked');
            $('#js-create-lading').addClass('disabled');
        } else {
            $(this).attr('checked','checked');
            $('#js-create-lading').removeClass('disabled');
        }
    });

    // Create lading - mr.Kien
    $('#js-create-lading').click(function() {
        if($(this).hasClass('disabled'))
            return false;
        var elem     = $(this);
        var dataform = $('#frm-calculate-fee').serializeArray();
        elem.addClass('disabled');
        $.ajax({
            url     : "ajax/frontend/lading_ajax/create_lading",
            dataType: "JSON",
            data    : dataform,
            type    : "POST",
            success : function(data) {
                if(data.error == 1){
                    $('#js-create-lading').slideUp();
                }
                else {
                    elem.removeClass('disabled');
                }
                    
                $('#result-create').html(data.html).hide().fadeIn();
            }
        });
        return false;
    });
    //
    $("#add-more-lading").livequery('click', function(){
        $("#result-create").slideUp();
        $('#js-create-lading').removeClass('disabled');
        $('#js-create-lading').show();
    });
    
    function preview_lading(elem){
        var value;
        var type = elem.data('type') ? elem.data('type') : '';
        var target = elem.data('target');

        if(type == '') {
            value  = elem.val();
            $(target).html(value);
        }else if(type == 'select') {
            value = elem.find('option:selected').text();
            $(target).html(value);
        }else if(type == 'checkbox') {
            value = elem.val();
            if(elem.prop('checked') && value == 'cod'){
                $(target).html("Giao hàng thu tiền.");
                $("#preview-vas-free").html('');
            }
            else if(elem.prop('checked') && value == 'received'){
                $(target).html("Thu phí vận chuyển người nhận.");
                $("#preview-vas-cod").html('');
            }
            else if(elem.prop('checked') && value == 'protected')
                $(target).html("Bảo hiểm hàng hóa.");
            else
                $(target).html('');
        }
    }
    //Khách hàng duyệt vận đơn
    $('.customer-accept').livequery('click',function(){
        var element     = $(this);
        var sc_code     = element.attr('rel');
        alertify.confirm('Bạn có chắc chắn muốn duyệt đơn hàng này không?',function(result){
            if(result) {
                $.ajax({
                    url      : 'ajax/frontend/lading_ajax/CustomerAccept',
                    type     : 'GET',
                    data     : {'sc_code': sc_code},
                    dataType : 'JSON',
                    success  : function(data){
                        alertify.alert(data.msg);
                    }
                });
                return false;
            }
        });
        
    });
    
    
    $("#product_name").autocomplete({
        source: function( request, response ) {
            var string = $("#product_name").val();
            var patt1 = /^[#][0-9]/g;
            if(!string.match(patt1)){
                return false;
            }
                        
            $.ajax({
              url     : "ajax/frontend/lading_ajax/suggest_item",
              dataType: "json",
                data: {
                   keyword: request.term
                },
                success: function( data ) {
                         response( $.map( data, function( item ) {
                            return {
                                value: item.name,
                                label: item.name,
                                content: item
                            }
                        }));
                        $('.ui-helper-hidden-accessible').hide();
                        $('.ui-autocomplete').addClass('f-dropdown');
                }
            });
        },
        autoFocus: true,
    	minLength: 1,
        select: function( event, ui ) {
            $('#product_name').val(ui.item.content.name);
            $('#product_price').val(ui.item.content.price);
            $('#product_weight').val(ui.item.content.weight);
            $('#product_quantity').val(ui.item.content.quantity);
            $('#product_description').html(ui.item.content.description);
            return false;
        }
    });
});