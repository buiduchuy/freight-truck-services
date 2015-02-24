<%-- 
    Document   : tao-hang-3
    Created on : Jan 30, 2015, 6:43:36 PM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Tạo hàng</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pricePre" value="${sessionScope.price}" />
<jsp:include page="header.jsp" />
<div class="large-12 columns">
	<div class="small-3 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
	<jsp:include page="vertical-menu-create-good.jsp" />
	<div class="row"></div>
		</div>
		<div class="form-content "
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="vertical-pr.jsp" />
			<div class="row"></div>
		</div>
	</div>
	<div class="small-9 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
				<jsp:include page="process-create-good.jsp" />
			<form action="ControllerCreateGoods" method="post"
				accept-charset="utf-8">
					<div class="row">
				<c:choose>
					<c:when test="${not empty pricePre }">
						<div class="row">
							<div class="large-12 columns">
								<div class="row">
									<div class="small-4 columns">
										<label for="right-label" class="right inline">Chi phí
											hệ thống đề nghị: </label>
									</div>
									<div class="small-6 columns">
										<c:set var="priceSuggest"
											value="${sessionScope.priceSuggest }" />
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
										<label for="right-label" class="left inline">(Ngàn
											đồng)</label>
									</div>
								</div>
								<div class="row">
									<div class="small-4 columns">
										<label for="right-label" class="right inline">Chi phí
											người dùng đưa ra: </label>
									</div>
									<div class="small-6 columns">
										<input type="text" id="right-label" name="txtPrice"
											value="${pricePre }" onkeypress="return keyPhone(event);"
											maxlength="10" />
									</div>
									<div class="small-2 columns">
										<label for="right-label" class="left inline">(Ngàn
											đồng)</label>
									</div>
								</div>
								<div class="row">
									<div class="large-12 columns">
										</br> <label> <font style="color: orange">Nếu bạn để
												trống ô "Chi phí người dùng" thì hệ thống sẽ lấy chi phí đề
												nghị làm chi phí hàng hoá. </font>
										</label>
										<div class="submit-area right">

											<a href="ControllerCreateGoods?btnAction=viewCreate_2"
												class="button secondary"><i class="icon-mail-reply"></i>
												Trở về</a>
											<button class="" name="btnAction" value="save3">
												<i class="icon-save"></i> Lưu thay đổi
											</button>
											<button class="success" name="btnAction" value="next3">
												<i class="icon-mail-forward"></i> Tiếp theo
											</button>
										</div>

									</div>
								</div>
								<div class="row"></div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="row">
							<div class="large-12 columns">
								<div class="row">
									<div class="small-4 columns">
										<label for="right-label" class="right inline">Chi phí
											hệ thống đề nghị: </label>
									</div>
									<div class="small-6 columns">
										<c:set var="priceSuggest"
											value="${sessionScope.priceSuggest }" />
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
										<label for="right-label" class="left inline">(Ngàn
											đồng)</label>
									</div>
								</div>
								<div class="row">
									<div class="small-4 columns">
										<label for="right-label" class="right inline">Chi phí
											người dùng đưa ra: </label>
									</div>
									<div class="small-6 columns">
										<input type="text" id="right-label" name="txtPrice"
											maxlength="10" onkeypress="return keyPhone(event);" />
									</div>
									<div class="small-2 columns">
										<label for="right-label" class="left inline">(Ngàn
											đồng)</label>
									</div>
								</div>
								<div class="row">
									<div class="large-12 columns">
										<label> <font style="color: orange">Nếu bạn để
												trống ô "Chi phí người dùng" thì hệ thống sẽ lấy chi phí đề
												nghị làm chi phí hàng hoá. </font>
										</label>
										<div class="submit-area right">

											<a href="ControllerCreateGoods?btnAction=viewCreate_2"
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
					</c:otherwise>
				</c:choose>
			</form>
		</div>
	</div>

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
<jsp:include page="footer.jsp" />
