$(document).ready(function(){
    //
    $.ajax({
        url     : "ajax/frontend/cashAjax/getBalanceInfo",
        dataType: "JSON",
        data    : {},                
        type    : "GET",
        success : function(data) {
            $("#balance-info").html(data.html);
            if(data.balance > 0){
                //$('#show-alert-money').slideDown();
            }
        }
    });
    //
    $.ajax({
        url     : "ajax/frontend/cashAjax/priceDS",
        dataType: "JSON",
        data    : {},                
        type    : "GET",
        success : function(data) {
            $("#user-ds").html(data.html);
        }
    });
    //
    $('.export-ds').livequery('click',function(event) {
        event.preventDefault();
        var data_form           = $('#frm-user-ds').serialize();
        window.location.href    = 'ajax/frontend/cashAjax/priceDS?'+data_form +'&cmd=export';
    });
    //
    $('.view-ds').livequery('click',function(event) {
        event.preventDefault();
        var time_start           = $('#f_date').val();
        var time_end           = $('#t_date').val();
        var status_ds           = $('#status_ds').val();
        $.ajax({
            url     : "ajax/frontend/cashAjax/priceDS",
            dataType: "JSON",
            data    : {from_date:time_start,to_date:time_end,status_ds:status_ds},                
            type    : "GET",
            success : function(data) {
                $("#user-ds").html(data.html);
            }
        });
    });
    //
    $(".view-detail-ds").livequery('click',function(event) {
       $('#list-lading').foundation('reveal', 'open');
       var time_start           = $('#f_date').val();
        var time_end           = $('#t_date').val();
        var status           = $('#status_ds').val();
        $.ajax({
            url     : "ajax/frontend/cashAjax/listLadingDS",
            dataType: "JSON",
            data    : {from_date:time_start,to_date:time_end,status_ds:status},                
            type    : "GET",
            success : function(data) {
                $("#result-data").html(data.html);
            }
        });
    });
    //
    $("#payment").livequery('click',function(e) {
        var cash = parseInt($("#price").val());
        var payment_method = $('input[name="checkout_option"]:checked').val();
        if(cash >= 10000 && payment_method == 'PS'){
            $.ajax({
                url     : "ajax/frontend/cashAjax/cashInAccountShipchung",
                dataType: "JSON",
                data    : {price:cash,payment_method:payment_method},                
                type    : "GET",
                success : function(data) {
                    if(data.code == 1){
                        $('.error-code').html(data.html).fadeIn(200).show("fast");
                        return true;
                    }else{
                        $('.error-code').html(data.html).fadeIn(200).show("fast");
                        return false;
                    }
                }
            });
            return false;
        }
        if(cash >= 10000){
            $.ajax({
                url     : "ajax/frontend/cashAjax/cashInNganluong",
                dataType: "JSON",
                data    : {price:cash,payment_method:payment_method},                
                type    : "GET",
                success : function(data) {
                    if(data.code == 1){
                        window.location = data.link_checkout;
                    }else{
                        $('.error-code').html(data.html).fadeIn(200).show("fast");
                    }
                }
            });
        }
    });
    //
    $("#send-request").livequery('click',function(e) {
        var dataform = $("#cash_out").serializeArray();
        if(dataform){
            $.ajax({
                url     : "ajax/frontend/cashAjax/addRequestCashOut",
                dataType: "JSON",
                data    : dataform,                
                type    : "POST",
                success : function(data) {
                    if(data.code == 1){
                        $("#error-cash").css('display','block');
                        $('#error-cash').html('<div data-alert="" class="alert-box radius success">'+data.msg+'</div>');
                    }else{
                        $("#error-cash").css('display','block');
                        $('#error-cash').html('<div data-alert="" class="alert-box radius alert">'+data.msg+'</div>');
                    }
                }
            });
        }
    });
    $("#price").livequery('focusout',function(){
        var cash = parseInt($(this).val());
        if(cash >= 10000 && cash != "NaN"){
            newUrl = $('#payment').attr('href') +'?money='+cash;
            $("#error-cash").css('display','none');
        }else{
            $("#error-cash").css('display','block');
            $("#error-cash").html('<div style="margin-top: 10px;" class="alert-box alert">Bạn nhập lại số tiền, số tiền lớn hơn 10.000 đ</div>');
        }
    });

    $("ul.paygate li").livequery("click",function(e) {     
        if($(this).find('div.display-content').css('display') == 'none') {
            $("div.display-content").hide();   
            $(this).find("div.display-content").slideDown(200);
            $(this).find('input').prop('checked',true);
        }
    });
});