<%-- 
    Document   : tao-hang-3
    Created on : Jan 30, 2015, 6:43:36 PM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Tạo hàng</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />
<section class="container">
	<center>
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 800px;">
			<jsp:include page="menu-tao-hang.jsp" />
			<form action="Controller" method="post" accept-charset="utf-8">
				<div class="row">
					<div class="large-12 columns">
						<div class="row">
							<div class="small-4 columns">
								<label for="right-label" class="right inline">Chi phí hệ
									thống đề nghị: </label>
							</div>
							<div class="small-6 columns">
								<c:set var="priceSuggest" value="${sessionScope.priceSuggest }" />
								<c:choose>
									<c:when test="${not empty priceSuggest}">
										<input type="text" id="right-label" value="${ priceSuggest}"
											name="txtPriceSystem" readonly="" />
									</c:when>
									<c:otherwise>
										<input type="text" id="right-label"
											value="Hệ thống không thể đưa ra giá đề nghị!"
											name="txtPriceSystem" readonly="" />
									</c:otherwise>
								</c:choose>
							</div>
							<div class="small-2 columns">
								<label for="right-label" class="left inline">(Ngàn đồng)</label>
							</div>
						</div>
						<div class="row">
							<div class="small-4 columns">
								<label for="right-label" class="right inline">Chi phí
									người dùng đưa ra: </label>
							</div>
							<div class="small-6 columns">
								<input type="text" id="right-label" name="txtPrice" />
							</div>
							<div class="small-2 columns">
								<label for="right-label" class="left inline">(Ngàn đồng)</label>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								</br> <label> <font style="color: orange">Nếu bạn để
										trống ô "Chi phí người dùng" thì hệ thống sẽ lấy chi phí đề
										nghị làm chi phí hàng hoá. </font>
								</label>
								<div class="submit-area right">

									<a href="Controller?btnAction=viewCreate_2"
										class="button secondary"><i class="icon-mail-reply"></i>
										Trở về</a>
									<button class="success" name="btnAction" value="next3">
										<i class="icon-mail-forward"></i> Tiếp theo
									</button>
								</div>

							</div>
						</div>
						<div class="row"></div>
					</div>
				</div>
			</form>

		</div>


	</center>

</section>
<jsp:include page="footer.jsp" />
