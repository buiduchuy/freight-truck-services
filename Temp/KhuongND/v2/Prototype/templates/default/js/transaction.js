$(function() {
    
    list_transactions(1);
    
     // date picker
	$('.date-picker').each(function(){
		$(this).fdatepicker({
			format: 'dd/mm/yyyy',
			language: 'vi'
		});
	});

    $('.js_search_transactions').click(function(event){
         event.preventDefault();
        $('input[name=page]').val(1);
        
        // Push history
        var refer_code          = $('input[name=refer_code]').val();
        var type                = $('input[name=type]').val();
        var time_start          = $('input[name=time_start]').val();
        var time_end            = $('input[name=time_end]').val();
        var page                = $('input[name=page]').val();
        
        // data_form ajax
        History.pushState({refer_code:refer_code, type:type, time_start:time_start, time_end:time_end, page:page}, document.title, 
                            '?refer_code='+refer_code+'&type='+type+'&time_start='+time_start+'&time_end='+time_end+'&page='+page);
        return false;
    });
    
    $('.ViewType').livequery('click',function(e){
        e.preventDefault();
        $('.ViewType').parent('dd').removeClass('active');
        $(this).parent('dd').addClass('active');
        $('input[name=type]').val($(this).attr('rel'));
        $('input[name=page]').val(1);
        
        // Push history
        var refer_code          = $('input[name=refer_code]').val();
        var type                = $('input[name=type]').val();
        var time_start          = $('input[name=time_start]').val();
        var time_end            = $('input[name=time_end]').val();
        var page                = $('input[name=page]').val();
        
        // data_form ajax
        $('input[name=status]').val(status);
        History.pushState({refer_code:refer_code, type:type, time_start:time_start, time_end:time_end, page:page}, document.title, 
                        '?refer_code='+refer_code+'&type='+type+'&time_start='+time_start+'&time_end='+time_end+'&page='+page);
        return false;
    });
    
});

// hàm list transactions
    function list_transactions(page) {
        var data_form   = $('#frm-list-transactions').serialize();
        $.ajax({
    		beforeSend: function(){
    			$('.loading-element').show();
    		},
    		url     : 'ajax/frontend/transactions_ajax/list_transactions',
            type    : 'GET',
            data    : data_form,
            dataType: 'JSON',
            success :function(data){
                $('.loading-element').hide();
                $('#data-transactions').html(data.html);
                $('#ListType').html(data.html_group);
                $('.list-paging').html(data.html_paging);
                $('#total-transactions').html(data.total);
                $('#data-paging').css('opacity', '1');
            }
        });
    };
    
//**paging with history**/
(function(window,undefined){
    
    var History = window.History;
    if ( !History.enabled ) {
        return false;
    }
    
    //Thiết lập state khi click vào page link
    $('.js_paging').livequery('click', function(e){
        var refer_code          = $('input[name=refer_code]').val();
        var type                = $('input[name=type]').val();
        var time_start          = $('input[name=time_start]').val();
        var time_end            = $('input[name=time_end]').val();
     
        var page        = $(this).attr('rel');
        History.pushState({refer_code:refer_code, type:type, time_start:time_start, time_end:time_end, page:page}, document.title, 
                            '?refer_code='+refer_code+'&type='+type+'&time_start='+time_start+'&time_end='+time_end+'&page='+page);
        return false;
    });
    
    $('.js_excel_transactions').livequery('click',function(event) {
        event.preventDefault();
        var data_form           = $('#frm-list-transactions').serialize();
        window.location.href    = 'ajax/frontend/transactions_ajax/list_transactions?'+data_form +'&cmd=export';
    });
    
    // Khởi tạo sự kiện bắt sự thay đổi của sate
    History.Adapter.bind(window,'statechange',function(){
        var State               = History.getState();
        var page                = State.data.page ? State.data.page : 1;
        var refer_code          = State.data.refer_code;
        var type                = State.data.type;
        var time_start          = State.data.time_start;
        var time_end            = State.data.time_end;
      
        $.ajax({
    		beforeSend: function(){
    			$('.loading-element').show();
    		},
    		url     : 'ajax/frontend/transactions_ajax/list_transactions',
            type    : 'GET',
            data    : {'refer_code':refer_code, 'type':type, 'time_start':time_start, 'time_end':time_end, 'page':page},
            dataType: 'JSON',
            success :function(data){
                $('.loading-element').hide();
                $('#data-transactions').html(data.html);
                $('#ListType').html(data.html_group);
                $('.list-paging').html(data.html_paging);
                $('#total-transactions').html(data.total);
                $('#data-paging').css('opacity', '1');
            }
        });
        return false;
    });
})(window);