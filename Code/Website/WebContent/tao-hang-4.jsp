<%-- 
    Document   : tao-hang-4
    Created on : Jan 30, 2015, 6:54:48 PM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Tạo hàng</title>
<jsp:include page="header.jsp" />

<section class="container">
	<center>
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 800px;">
			<jsp:include page="menu-tao-hang.jsp" />
			<form action="Controller" method="get" accept-charset="utf-8">
				<div class="row">
					<div class="large-12 columns">
						<div>
							<div class="extra-title">
								<h3>Thông tin hàng</h3>
							</div class="large-12 columns">
							<div class="row">
								<div class="small-6 columns">
									<div class="small-6 columns">
										<label for="right-label" class="right inline">Loại
											hàng: </label>
									</div>
									<div class="small-6 columns">
										<input type="text" id="right-label" value="aaa"
											name="txtWeight" readonly="" />
									</div>
								</div>
								<div class="small-6 columns">
									<div class="small-6 columns">
										<label for="right-label" class="right inline"> Khối
											lượng: </label>
									</div>
									<div class="small-6 columns">
										<input type="text" id="right-label" value="Kg"
											name="txtWeight" readonly="" />
									</div>
								</div>
<div class="row">
								<div class="small-6 columns">
									<label for="right-label" class="right inline">Ghi chú :
									</label>
								</div>
								<div class="small-6 columns">
									<textarea maxlength="250" name="txtNotes" readonly=""></textarea>
								</div>

							</div>



							</div>
							
						</div>



						<div class="row">
							<div class="large-12 columns">
								<div class="submit-area right">
									<a href="Controller?btnAction=viewCreate_3"
										class="button secondary"><i class="icon-mail-reply"></i>
										Trở về</a>
									<button class="success" name="btnAction" value="createGood">
										<i class="icon-mail-forward"></i> Tạo hàng
									</button>
								</div>
								</br>
							</div>
						</div>

						<div class="row"></div>
					</div>
				</div>
			</form>
		</div>
	</center>

</section>

<!-- dialog box -->




<!-- script of dialog -->

<jsp:include page="footer.jsp" />
