<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Đăng ký</title>
<html class="no-js" xmlns="http://www.w3.org/1999/xhtml" lang="vi-VN">
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />

<link rel="shortcut icon" type="image/x-icon" href="css/img/favicon.png" />
<link type="text/css" rel="stylesheet" href="css/foundation.css"
	media="screen" />
<link type="text/css" rel="stylesheet"
	href="css/font-awesome/font-awesome.css" media="screen" />
<link type="text/css" rel="stylesheet" href="css/style.css"
	media="screen" />

</head>
<body>
	<div class="top-bar">
		<div class="row">
			<div class="large-12 columns">
				<div class="row">
					<div class="small-5 large-6 columns"></div>
					<div class="small-7 large-6 columns">
						<ul class="inline-list top-links right">
							<li><a href="dang-nhap.jsp"><i class="icon-signin"></i>
									Đăng nhập</a></li>
							<li><a href="dang-ky.jsp"><i class="icon-user"></i> Đăng
									ký</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="page-header">
		<div class="row">
		<div class="large-12 columns">
			<nav class="breadcrumbs left" id="login-form">
				<a href="ProcessServlet">Trang chủ</a>
				<a class="current" href="ProcessServlet?btnAction=register">Đăng ký</a>
			</nav>					
		</div>					
	</div>
	<br/>
	</div>
	<section class="container">
		<div class="row">
			<center>
				<div class="form-content large-6"
					style="border: 1px solid #ccc; padding: 10px; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px;">
					<form action="AccountServlet" method="post" accept-charset="utf-8"
						id="frm-register">
						<h2>Đăng ký tài khoản</h2>
						<span id="error-login" style="width: 100%;"></span>
						<hr />

						<div class="field-line text-left">
							<label><i class="icon-envelope"></i> Email đăng ký <small
								class="validate">*</small></label> <input type="email"
								placeholder="Xin hãy nhập email đăng nhập vào đây..."
								required=""
								data-errormessage-value-missing="Bạn nhập thiếu Email !"
								data-errormessage-type-mismatch="Bạn nhập email không đúng dạng. Email phải có dạng: abc@abc.com"
								name="" />
						</div>
						<div class="field-line large-6 columns text-left"
							style="padding: 0;">
							<label><i class="icon-key"></i> Mật khẩu <small
								class="validate">*</small></label> <input id="password" type="password"
								pattern=".{6,20}" placeholder="Nhập mật khẩu đăng nhập" name=""
								required=""
								data-errormessage-value-missing="Bạn nhập thiếu Mật khẩu !"
								data-errormessage-pattern-mismatch="Bạn phải nhập mật khẩu [6-20] kí tự !" />
						</div>
						<div id="form-reg">
							<div class="field-line large-6 columns text-left"
								style="padding-right: 0;">
								<label><i class="icon-user"></i> Họ tên <small
									class="validate">*</small></label> <input type="text" tabindex="4"
									placeholder="Nhập họ tên" name="" required=""
									data-errormessage-value-missing="Bạn nhập thiếu Họ tên !" />
							</div>
							<div class="field-line large-6 columns text-left"
								style="padding: 0;">
								<label><i class="icon-key"></i> Nhập lại mật khẩu <small
									class="validate">*</small></label> <input id="passwordconf"
									type="password" placeholder="Nhập mật khẩu đăng nhập" name=""
									required=""
									data-errormessage-value-missing="Bạn nhập thiếu Mật khẩu !" />
							</div>
							<script language='javascript' type='text/javascript'>
                                        function check(input) {
                                            if (input.value != document.getElementById('password').value) {
                                                input.setCustomValidity('Phải nhập trùng với mật khẩu ở trên.');
                                            } else {
                                                // input is valid -- reset the error message
                                                input.setCustomValidity('');
                                            }
                                        }
                                    </script>
							<div class="field-line large-6 columns text-left"
								style="padding-right: 0;">
								<label><i class="icon-phone"></i> Số điện thoại <small
									class="validate">*</small></label> <input type="text"
									onkeypress="return keyPhone(event);"
									placeholder="Nhập số điện thoại" pattern=".{10,11}" name=""
									required=""
									data-errormessage-value-missing="Bạn nhập thiếu số điện thoại !"
									data-errormessage-pattern-mismatch="Bạn phải nhập số điện thoại [10-11] số !" />
							</div>
							<script>
                                        function keyPhone(e)
                                        {
                                            var keyword = null;
                                            if (window.event)
                                            {
                                                keyword = window.event.keyCode;
                                            } else
                                            {
                                                keyword = e.which; //NON IE;
                                            }

                                            if (keyword < 48 || keyword > 57)
                                            {
                                                if (keyword == 48 || keyword == 127)
                                                {
                                                    return;
                                                }
                                                return false;
                                            }
                                        }

                                    </script>
						</div>
						<p>
							Sử dụng tài khoản <a href="#" target="_blank">NgânLượng.vn</a>
							hoặc <a href="#">BảoKim.vn</a> để đăng ký chi trả.
						</p>
						<span id="error-login" style="width: 100%;"></span> <input
							type="hidden" name="choice" value="1" />
						<div class="row">
							<div class="grayscale columns large-6 login-panel" rel="0"
								url="#">
								<span class="choice-logo-login nl"></span>
							</div>
							<div class="grayscale columns large-6 login-panel active" rel="1"
								url="quen-mat-khau.jsp">
								<span class="choice-logo-login sc"></span>
							</div>
						</div>
						<div class="field-line">
							<button class="large" onclick="check(passwordconf)">Đăng
								ký</button>
							<span class="logo-nl-register right hide"></span>
						</div>

					</form>
				</div>
			</center>
		</div>
	</section>
	<jsp:include page="footer.jsp" />