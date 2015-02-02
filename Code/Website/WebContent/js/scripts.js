$(function() {
	$(document).foundation(null,null,null,null,true);
    
    /*$.get('ajax/frontend/tutorial_ajax/show_popup', function(data) {
        var modal = $('<div id="tutorial-modal" style="padding:0;">').addClass('reveal-modal medium').appendTo('body');
        modal.empty().html(data).append('<a class="close-reveal-modal">&#215;</a>').foundation('reveal','open');
   	});*/
           
    $('.js-tutorial').livequery('click',function(e) {
        $('.js-tutorial').removeClass('active');
        $(this).addClass('active');
        divId = $(this).attr('rel');
        $('.tutorial-panel div').addClass('hide');
        $('#tutorial-'+divId).removeClass('hide');
	});       
                
    $('.main-nav nav').meanmenu();

    $('[kmodal]').livequery('click',function(e) {
	    e.preventDefault();
        divid =  $(this).attr('kmodal');
        if($('#'+divid).length != 0)
	    {
	       $('#'+divid).remove();
	    }
        else{
			$('.reveal-modal').remove();
		}
		
        var elem    = $(this);
        if(elem.hasClass('disabled')) {
            return false;
        }
        
        var modal = $('<div id="'+ divid +'">').addClass('reveal-modal medium').appendTo('body');
        $('.loading-element').show();
        elem.addClass('disabled');
        
	    $.get('ajax/frontend/' + $(this).attr('rel'), function(data) {
	        elem.removeClass('disabled');
	        $('.loading-element').hide();
	    	modal.empty().html(data).append('<a class="close-reveal-modal">&#215;</a>').foundation('reveal','open');
    	});

	});
    
	if($.fn.bxSlider) {
		$('.featured-slider').bxSlider({
			mode: 'fade',
			pager: false,
			auto: true,
			controls: false,
			adaptiveHeight: true
		});
		$('.carousel').each(function(){
			var width = $(this).find('article:first').outerWidth();
			var slide_num = $(this).data('slide-num') ? $(this).data('slide-num') : 3;
			var slide_mode = $(this).data('slide-mode') ? $(this).data('slide-mode') : 'horizontal';
			$(this).bxSlider({
				slideWidth: width,//275,
				minSlides: 1,
				maxSlides: slide_num,
				slideMargin: 0,
				pager: false,
				moveSlides: 1,
				adaptiveHeight: true,
				auto: true,
				pause: 5000,
				mode: slide_mode
			});
		});
	}
    
    $('#sub-notice').livequery('click',function(){
        var parent = $(this).parent('li');
        var style = $('.sub-notice').attr('style');
        if(style){
            $('.sub-notice-show').empty();
            $('.sub-notice').attr('style','');
            $('.top-alert').html('').removeClass('top-alert');
            parent.css('background-color','#28343C');
            $('#sub-notice').css('color','#FFFFFF');
        }else{
            parent.css('background-color','#FFFFFF');
            $('#sub-notice').css('color','#28343C');
            $.ajax({
                beforeSend  :  function() {
                $('.sub-notice-show').html('<ul><li style="text-align:center">Đang tải dữ liệu.....</li></ul>').show();
                $('.sub-notice').attr('style','display: block !important');
                },
                url         : 'ajax/frontend/user_ajax/ShowSubNotice',
                dataType    : 'JSON',
                success     : function(data){
                    if(data.code == 1){
                        $('.sub-notice-show').html(data.html);
                        $('.top-alert').html('').removeClass('top-alert');
                        return false;
                    }else if(data.code == 0){
                        $('.sub-notice-show').html('<ul><li style="text-align:center">'+data.html+'</li></ul>');
                    }
                    return false;
                }
            });
            
        }
        return false;
    });
    
    $("body").click(function(e) {
      if(e.target.className !== 'sub-notice'){
        $('.sub-notice-show').empty();
        $('.sub-notice').attr('style','');
        $('#sub-notice').parent('li').css('background-color','#28343C');
        $('#sub-notice').attr('style','');
      }      
    });
    
    $(".gioto").jCarouselLite({
		vertical: true,
		hoverPause:true,
		visible: 1,
		auto:5000,
		speed:1000
	});
});

/** Custom function **/

jQuery.fn.inputNumber = function() {
    return this.each(function() {
        $(this).keydown(function(event) {
            if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
                (event.keyCode == 65 && event.ctrlKey === true) || 
                (event.keyCode >= 35 && event.keyCode <= 39)) {
                     return;
            }
            else {
                if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
                    event.preventDefault(); 
                }   
            }
        });
    });
};