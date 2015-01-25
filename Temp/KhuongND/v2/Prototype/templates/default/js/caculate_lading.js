$(document).ready(function() {

    // thay đổi thành phố
    $('.change_city').livequery('change',function() {
        var city_id     = $(this).val();
        var target      = $(this).attr('data-target');
        var from_city   = $("#from-city option:selected").val();
        var to_city   = $("#to-city option:selected").val();

        if(to_city == 18 && from_city == 18 || to_city == 52 && from_city == 52){
            $("#express").removeAttr("disabled");
        }else{
            $("#express").attr('disabled', 'true');
            $("#express").removeAttr("checked");
        }

        $.ajax({
            url     : "ajax/frontend/lading_ajax/change_city",
            dataType: "HTML",
            data    : {'city_id': city_id},
            type    : "GET",
            success : function(data) {
                $('select'+target+'-select').html('<option value="0">Quận/Huyện</option>'+data);
                $("#cptk").removeAttr("disabled");
            }
        });
    });
    // dua ra phuong xa ho tro
    $("#preview-to-city-select").livequery('change',function(){
        var from_city = $("#from-city option:selected").val();
        var to_city   = $("#to-city option:selected").val();
        var district_id = $('#preview-to-city-select option:selected').val();
        var care = $('#preview-to-city-select option:selected').attr("care");
        var from_location    = $('#preview-from-city-select option:selected').attr("location");
        var to_location    = $('#preview-to-city-select option:selected').attr("location");
        var check = 0;
        if(from_location <= 2 && to_location <= 2){
            check = 1;
        }
        if(check == 1 && to_city == 18 && from_city == 18 || to_city == 52 && from_city == 52 && check == 1){
            $("#express").removeAttr("disabled");
        }else{
            $("#express").attr('disabled', 'true');
            $("#express").removeAttr("checked");
        }
        if(care > 1){
            $.ajax({
                url     : "ajax/frontend/lading_ajax/list_ward_care",
                dataType: "JSON",
                data    : {'district_id': district_id},
                type    : "GET",
                success : function(data) {
                    if(data.code == 1){
                        $(".ward-care").css('display','block');
                        $(".to_ward").css('display','block');
                        $('#return-ward').html('<select name="product[to_ward]" class="input-preview" id="preview-to-ward-select" data-type="select" data-target="#preview-to-ward"><option value="0">Phường Xã</option>'+data.html+'</select>');
                    }else{
                        $("#return-ward").html('');
                        $(".to_ward").css('display','none');
                    }
                }
            });
        }else{
            $("#return-ward").html('');
            $(".to_ward").css('display','none');
        }
    });
    //check cptk
    $("#preview-to-ward-select").livequery('change',function() {
        var location = $('#preview-to-ward-select option:selected').attr("rel");
        if(location > 0){
            $("#cptk").attr('disabled', 'true');
        }else{
            $("#cptk").removeAttr("disabled");
        }
    });
    //tùy chọn khối lượng
    $('.js-option').click(function() {
        $('[class="icon-ok-circle"]').removeClass('icon-ok-circle').addClass('icon-circle-blank');
        $(this).find('i:first').removeClass('icon-circle-blank').addClass('icon-ok-circle');
    });
    
    // Auto caculate weight
    $('input[name="product[service]"], .js-option[rel="length"]').bind('focusout change',function() {
        dai           = $('input[name="product[length]"]').val();
        rong          = $('input[name="product[width]"]').val();
        cao           = $('input[name="product[height]"]').val();

        weight_percent = ($('input[name="product[service]"]:checked').val() == 1) ? 3 : 6;
        
        if($('.js-option[rel="length"]').find('i').hasClass('icon-ok-circle') && dai > 0 && rong > 0 && cao > 0)
        {
            weight = parseInt(dai)*parseInt(rong)*parseInt(cao)/weight_percent;
            $('input[name="product[weight]"]').val(Math.round(parseInt(weight)));
        }
        return false;
    });
        
    // tùy chọn vas
    $('.option_vas').livequery('click', function() {
        $(this).parent().find('span').css('color', '#000');
        if($(this).val() == 'cod') {
            $('input[value=received]').attr('checked',false);
            $('input[value=received]').parent().find('span').css('color', '#ccc');
        } else {
            $('input[value=cod]').attr('checked',false);
            $('input[value=cod]').parent().find('span').css('color', '#ccc');
        }
        
        if($('.option_vas:checked').length == 0) {
            $(this).parent().parent().parent().find('span').css('color', '#000');
        }
        
    });
    // Auto load Stock User
    $.ajax({
        url     : "ajax/frontend/lading_ajax/auto_load_stock",
        dataType: "json",
        success : function(data) {
            if(!data)
                return false;
            $('#product_from_name').val(data.name);
            $('#product_from_phone').val(data.phone);
            $('select[data-target="#preview-from-city"]').val(data.city_id);
            $('#preview-from-city-select').html(data.district_id);
            $('#product_from_address').val(data.address);
            
            // add Preview
            $('#preview-from-name').html(data.name);
            $('#preview-from-phone').html(data.phone);
            if(data.address != '')
            {
                $('#preview-from-address').html(data.address);
                $('#preview-from-city').html($('select[name="product[from_city]"]').find('option:selected').html());
                $('#preview-from-district').html($('select[name="product[from_district]"]').find('option:selected').html());
            }
            return false;
        }
    });
    // tính phí
    $('.calculate_fee').click(function(event) {
        event.preventDefault();
        $('#box-calculate-fee').show();
        $('#img-loading').show();
        $('#result-calculate-fee').html('');
               
        var data_form   = $('#frm-calculate-fee').serialize();
        
        $.ajax({
            beforeSend :  function() {},
            url     : "ajax/frontend/lading_ajax/calculate_fee",
            dataType: "HTML",
            data    : data_form,
            type    : "POST",
            success : function(data) {
                $('#img-loading').hide();
                $('#result-calculate-fee').html(data);
            }
        })
        
        return false;
    });

function addCommas(nStr)
{
    nStr   += '';
    x       = nStr.split('.');
    x1      = x[0];
    x2      = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1  = x1.replace(rgx, '$1' + '.' + '$2');
    }
    return x1 + x2;
}

});


