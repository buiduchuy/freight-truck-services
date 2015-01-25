$('.login-panel').livequery("click",function(){
    $('.login-panel').removeClass('active');
    $(this).addClass('active');
    
    if($(this).attr('rel') == 1){
        $('span.login-source').html('ShipChung.vn');
    }
    else{
        $('span.login-source').html('NgânLượng.vn');
    }
    $('.forgot-password').children('a').attr('href',$(this).attr('url'));
    $('input[name="choice"]').val($(this).attr('rel'));
});

var tm_login;
    $('button#js-btn-login').livequery("click",function(e){
    	window.clearTimeout(tm_login);
        e.preventDefault();
        $('#error-login').html('');
        var dataInput = $('form#frm-login').serialize();
        $('.loading-element').show();
        $.ajax({
            type: "POST",
            url: 'ajax/frontend/user_ajax/login_action',
            data: dataInput,
            dataType: 'json',
            success: function(data){
                if(data.error == 1)
                {
                    $('#error-login').html(data.html).hide().fadeIn();
                    tm_login = window.setTimeout(function(){
                        //location.reload();
                        location.href = data.url;
                    },100);
                }
                else
                {
                   	$('#error-login').html(data.html).hide().fadeIn();
                   	tm_login = window.setTimeout(function(){$('#error-login').slideUp()},3000);
                }
            }
        });
        $('.loading-element').hide();
        return false;
    });
    $('button#js-btn-login-openid').livequery("click",function(e){
        window.clearTimeout(tm_login);
        e.preventDefault();
        $('#error-login').html('');
        var dataInput = $('form#frm-login').serialize();
        $('.loading-element').show();
        $.ajax({
            type: "POST",
            url: $("form#frm-login").attr("action"),
            data: dataInput,
            dataType: 'json',
            success: function(data){
                if(data.error == 1)
                {
                    $('#error-login').html(data.html).hide().fadeIn();
                    tm_login = window.setTimeout(function(){
                        location.href = data.url;
                    },100);
                }
                else
                {
                    $('#error-login').html(data.html).hide().fadeIn();
                    tm_login = window.setTimeout(function(){$('#error-login').slideUp()},3000);
                }
            }
        });
        $('.loading-element').hide();
        return false;
    });
    // Action form register
    $('#choice-reg').livequery('click',function(){
        $('#login_email').focus();
        if($(this).is(':checked')){
            $('#form-reg').addClass('hide');
            $('#login_password').parent().removeClass('large-6 columns');
            $('.logo-nl-register').removeClass('hide');
        }
        else{
            $('#form-reg').removeClass('hide');
            $('#login_password').parent().addClass('large-6 columns');
            $('.logo-nl-register').addClass('hide');
        }
    });
    
    $('button#js-btn-register').livequery("click",function(e){
    	window.clearTimeout(tm_login);
        e.preventDefault();
        $('.loading-element').show();
        $('#error-login').html('');
        var dataInput = $('form#frm-register').serialize();
        $.ajax({
            type: "POST",
            url: 'ajax/frontend/user_ajax/register_action',
            data: dataInput,
            dataType: 'json',
            success: function(data){
                if(data.error == 1)
                {
                    $('#error-login').html(data.html).hide().fadeIn();
                    tm_login = window.setTimeout(function(){window.location.href = '';},1000);
                }
                else
                {
                   	$('#error-login').html(data.html).hide().fadeIn();
                   	tm_login = window.setTimeout(function(){$('#error-login').slideUp()},3000);
                }
            }
        });
        $('.loading-element').hide();
        return false;
    });