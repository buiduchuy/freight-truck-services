<%-- 
    Document   : tao-hang-4
    Created on : Jan 30, 2015, 6:54:48 PM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Tạo hàng</title>
<c:set var="error" value="${sessionScope.errorCreateGood }"/>
<jsp:include page="header.jsp" />

<section class="container">
	<center>
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 800px;">
			<jsp:include page="menu-tao-hang.jsp" />
			<c:if test="${not empty error}">
							<font color="red">${error}</font>
						</c:if>
			<form action="Controller" method="post" accept-charset="utf-8">
				<div class="row">
					<div class="large-12 columns">
						<div class="row">
							<div class="extra-title">
								<h3>Thông tin hàng</h3>
							</div class="large-12 columns">
							<div class="row">
								<div class="small-6 columns">
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
											<input type="text" id="right-label"
												value="${good.weight } Kg" name="txtWeight" readonly="" />
										</div>
									</div>
								</div>

								<div class="small-6 columns">
									<div class="row">
										<div class="small-3 columns">
											<label for="right-label" class="right inline">Ghi chú
												: </label>
										</div>
										<div class="small-9 columns">
											<textarea maxlength="250" name="txtNotes" rows="4"
												readonly="">${good.notes } </textarea>
										</div>
									</div>
								</div>



							</div>

						</div>



						<div class="row">
							<div class="extra-title">
								<h3>Địa chỉ giao nhận hàng</h3>
							</div class="large-12 columns">
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
											<input type="text" id="right-label"
												value="${router.pickupTime}" readonly="" />
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

						</div>
						<div class="row">
							<div class="extra-title">
								<h3>Chi phí</h3>
							</div class="large-12 columns">
							<div class="row">

								<div class="row">
									<div class="small-4 columns">
										<label class="right inline">Chi phí tài xế: </label>
									</div>
									<div class="small-4 columns left">
										<input type="text" id="right-label"
											value="${price} (Ngàn đồng)" readonly="" />
									</div>
								</div>
								<div class="row">
									<div class="small-4 columns">
										<label class="right inline">Chi phí tạo hàng: </label>
									</div>
									<div class="small-4 columns left">
										<input type="text" id="right-label"
											value="15000 (Ngàn đồng)" readonly="" />
									</div>
								</div>
								<c:set var="total" value="${sessionScope.total }"/>
								<div class="row">
									<div class="small-4 columns">
										<label class="right inline">Tổng cộng: </label>
									</div>
									<div class="small-4 columns left">
										<input type="text" id="right-label"
											value="${total} (Ngàn đồng)" readonly="" />
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
									<button class="success" onclick="return confirm('Bạn có muốn tạo hàng không?')"name="btnAction" value="createGood">
										<i class="icon-ok"></i> Tạo hàng
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
