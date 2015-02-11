<%-- 
    Document   : tao-hang-4
    Created on : Jan 30, 2015, 6:54:48 PM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Tạo hàng</title>
<c:set var="error" value="${sessionScope.errorCreateGood }" />
<jsp:include page="header.jsp" />
<div class="large-12 columns">
	<div class="large-2 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="menu-doc-tao-hang.jsp" />
		</div>
	</div>
	<div class="large-8 columns">

		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="menu-tao-hang.jsp" />
			<c:set var="messageSuccess" value="${sessionScope.messageSuccess }" />
			<c:set var="messageError" value="${sessionScope.messageError }" />
			<c:if test="${not empty messageSuccess}">
				<div class="row">
					<div data-alert class="alert-box success radius inline">
						${messageSuccess} <a href="#" class="close">&times;</a>
					</div>
				</div>
				<%
					request.getSession().removeAttribute("messageSuccess");
				%>
			</c:if>
			<c:if test="${not empty messageError}">
				<div class="row">
					<div data-alert class="alert-box alert radius inline">
						${messageError} <a href="#" class="close">&times;</a>
					</div>

				</div>
				<%
					request.getSession().removeAttribute("messageError");
				%>
			</c:if>
			<form action="ControllerCreateGoods" method="post"
				accept-charset="utf-8">

				<div class="row">
					<div class="small-7 columns">
						<div class="row">
							<div class="small-3 columns">
								<label class="right inline">Loại hàng: </label>
							</div>
							<div class="small-9 columns">
								<c:forEach var="row" items="${typeGoods }">
									<c:if test="${row.goodsCategoryId==good.goodsCategoryID }">
										<input type="text" id="right-label" value="${row.name }"
											name="txtWeight" readonly="" />
									</c:if>
								</c:forEach>
							</div>
						</div>
						<div class="row">
							<div class="small-3 columns">
								<label for="right-label" class="right inline"> Khối
									lượng: </label>
							</div>
							<div class="small-9 columns">
								<input type="text" id="right-label" value="${good.weight } Kg"
									name="txtWeight" readonly="" />
							</div>
						</div>
					</div>

					<div class="small-5 columns">
						<div class="row">
							<div class="small-3 columns">
								<label for="right-label" class="right inline">Ghi chú :
								</label>
							</div>
							<div class="small-9 columns">
								<textarea maxlength="250" name="txtNotes" rows="3" readonly="">${good.notes } </textarea>
							</div>
						</div>
					</div>



				</div>
				<div class="row">
					<div class="small-7 columns">
						<div class="row">
							<div class="small-3 columns">
								<label class="right inline">Địa chỉ giao: </label>
							</div>
							<div class="small-9 columns">
								<input type="text" id="right-label"
									value="${router.pickupAddress}" readonly="" />
							</div>
						</div>
						<div class="row">
							<div class="small-3 columns">
								<label class="right inline">Địa chỉ nhận: </label>
							</div>
							<div class="small-9 columns">
								<input type="text" id="right-label"
									value="${router.deliveryAddress}" readonly="" />
							</div>
						</div>
					</div>

					<div class="small-5 columns">
						<div class="row">
							<div class="small-4 columns">
								<label class="right inline">Ngày giao: </label>
							</div>
							<div class="small-8 columns">
								<input type="text" id="right-label" value="${router.pickupTime}"
									readonly="" />
							</div>
						</div>
						<div class="row">
							<div class="small-4 columns">
								<label class="right inline">Ngày nhận: </label>
							</div>
							<div class="small-8 columns">
								<input type="text" id="right-label"
									value="${router.deliveryTime}" readonly="" />
							</div>
						</div>
					</div>

				</div>

				<c:set var="total" value="${sessionScope.total }" />
				<div class="row ">
					<div class="small-2 columns">
						<label class="right inline">Chi phí: </label>
					</div>
					<div class="small-9 columns left">
						<textarea rows="3" readonly="" class="left">
						Chi phí trả tài xế :${price} (Ngàn đồng)
						Chi phí tạo hàng: 15000 (Ngàn đồng)
						Tổng cộng:${total} (Ngàn đồng)
						</textarea>

					</div>
				</div>
				<div class="row">
					<div class="large-12 columns">
						<div class="submit-area right">
							<a href="ControllerCreateGoods?btnAction=viewCreate_3"
								class="button secondary"><i class="icon-mail-reply"></i> Trở
								về</a>
							<button class="success"
								onclick="return confirm('Bạn có muốn tạo hàng không?')"
								name="btnAction" value="createGood">
								<i class="icon-ok"></i> Tạo hàng
							</button>
						</div>
					</div>
				</div>
				<div class="row"></div>
			</form>
		</div>

	</div>
	<div class="large-2 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
		</div>
	</div>
</div>








<!-- dialog box -->




<!-- script of dialog -->

<jsp:include page="footer.jsp" />
