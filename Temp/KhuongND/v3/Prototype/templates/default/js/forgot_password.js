$(function() {
	//
	$(".process").click(function(){
		var email = $(".email").val();
		var code  = $(".code").val();
		if(email){
			$.ajax({
	            url     : "ajax/frontend/user_ajax/forgot_password_action",
	            dataType: "JSON",
	            data    : {'email':email,'code':code},
	            type    : "GET",
	            success : function(data) {
	                if(data.code == 1){
	                	$("#error").html('<div data-alert="" class="alert-box radius success">'+data.msg+'</div>').hide().fadeIn();
	                }else{
	                	$("#error").html('<div data-alert="" class="alert-box radius alert">'+data.msg+'</div>').hide().fadeIn();
	                }
	            }
	        });
		}
	});
});