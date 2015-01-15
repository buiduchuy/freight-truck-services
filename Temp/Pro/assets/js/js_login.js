
$(function(){
				$('body').append('<div id="screnn"></div>'+
								 	'<div id="lbox">'+
										'<div id="close">'+'</div>'+
										   
											'<div id="blogin">'+
											    '<img src="svta_im/login_signup_bg.png" style="float:left; margin-right:20px"/>'+
												'<form onsubmit="md5hash(vb_login_password, vb_login_md5password, vb_login_md5password_utf, 0)" method="post" action="login.php">'+
               									 '<input name="vb_login_md5password" type="hidden" />'+
    											 '<input name="s" type="hidden" value="" />'+
    											 '<input name="do" type="hidden" value="login" />'+
             									 '<p>Tài khoản: <input type="text" value="" name="vb_login_username"/></p>'+'<br/>'+
              									 '<p>Mật khẩu:  <input type="password" value="" name="vb_login_password"/></p>'+'<br/>'+
												 '<input value="Log in" tabindex="104" title="Đăng nhập" accesskey="s" src="svta_im/login.png" type="image"/>'+
												 '<p style=" float:right; margin-top:8px; width:140px;"><input type="checkbox" name="longtime" checked="" /> Ghi nhớ <p>'+'<br/>'+
												 '<p  style="padding-left:160px;"><a href="login.php?do=lostpw">Quên mật khẩu?</a> <br/>Bạn chưa có tài khoản,<a href="register.php"> Đăng Ký</a></p>'+

           								 		'</form>'+
											'</div>'+
								 		   
									'</div>'
								 							 
								)          
                $('#login').click(function(){
						$('#screnn,#lbox, #close').fadeIn();
					})
				$('*').keyup(function(event){
						if (event.keyCode == '27')
						{
							$('#screnn, #lbox, #close').fadeOut("fast");
						}
					})
				$('#close').click(function(){
						$('#screnn, #lbox, #close').fadeOut("fast");
					})
				//function center
   				 center()
   				$(window).resize(function(){
   			   		 center()
   				 })
   				 function center(){
     			   var $wid=$(window).width()
     			   var $hei=$(window).height()
     			   var $boxLogin=$('#lbox')
       				 $boxLogin.css({'left':($wid-$boxLogin.width())/2,'top':($hei-$boxLogin.height())/2})
    				} 
			});