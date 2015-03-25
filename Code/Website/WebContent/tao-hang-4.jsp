<%-- 
    Document   : tao-hang-4
    Created on : Jan 30, 2015, 11:21:10 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Tạo hàng</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
	
		</div>
	</div>
	<div class="small-9 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<div class="form-content">
				<form action="GoodsServlet" method="post"
					accept-charset="utf-8">
					<div class="row">
						<div class="large-12 columns">
							<h2 class="page-title">
								<font color="orange">Tạo hàng</font>
							</h2>
						</div>




						<div class="large-12 columns">
							<div class="extra-title">
							<h3><font color="blue">Thông tin hàng hoá</font>
								</h3>
							</div>


							<div class="row">
								<div class="large-12 columns">
									<div class="row">
										<div class="small-3 columns">
											<label for="right-label" class="right inline"><small
												class="validate">*</small> Loại hàng: </label>
										</div>
										<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
										<c:set var="priceCreate" value="${sessionScope.priceCreate }" />
										<div class="small-6 columns">
											<c:forEach var="row" items="${typeGoods }">
												<c:if test="${row.goodsCategoryId==good.goodsCategoryID }">
													<input type="text" id="right-label" value="${row.name }"
														name="txtWeight" readonly="" />
												</c:if>
											</c:forEach>
										</div>
										<div class="small-3 columns"></div>
									</div>
									<div class="row">
										<div class="small-3 columns">
											<label for="right-label" class="right inline"><small
												class="validate">*</small> Khối lượng: </label>
										</div>
										<div class="small-6 columns">
											<input type="text" id="right-label"
												value="${good.weight }" name="txtWeight" readonly="" />
										</div>
										<div class="small-3 columns">
											<label for="right-label" class="left inline">Kg</label>
										</div>
									</div>
									<div class="row">
										<div class="small-3 columns">
											<label for="right-label" class="right inline">Ghi chú
												: </label>
										</div>
										<div class="small-6 columns">
											<textarea maxlength="250" name="txtNotes" rows="3"
												readonly="">${good.notes } </textarea>
										</div>
										<div class="small-3 columns"></div>
									</div>

								</div>
							</div>
							<div class="row">
								<div class="extra-title">
									<h3><font color="blue">Địa chỉ giao hàng</font></h3>
								</div>
								<div class="row">
									<div class="small-8 columns">
										<div class="small-3 columns">
											<label class="right inline"><small class="validate">*</small>
												Địa chỉ: </label>
										</div>
										<div class="small-9 columns">
											<input type="text" id="right-label"
												value="${router.pickupAddress}" readonly="" />
												<a href="GoodsServlet?btnAction=detailroutepickupAddress"
										class="button secondary">
										Tìm vị trí chính xác</a>
										</div>
									</div>
									<div class="small-4 columns">
										<div class="small-5 columns">
											<label for="right-label" class="right inline"><small
												class="validate">*</small> Ngày: </label>
										</div>
										<div class="small-7 columns">
											<input type="text" id="right-label"
												value="${router.pickupTime}" readonly="" />
										</div>
									</div>
								</div>

								<div class="extra-title">
									<h3><font color="blue">Địa chỉ nhận hàng</font></h3>
								</div>
								<div class="row">
									<div class="small-8 columns">
										<div class="small-3 columns">
											<label for="right-label" class="right inline"><small
												class="validate">*</small> Địa chỉ: </label>
										</div>
										<div class="small-9 columns">
											<input type="text" id="right-label"
												value="${router.deliveryAddress}" readonly="" />
												<a href="GoodsServlet?btnAction=detailroutedeliveryAddress"
										class="button secondary">
										Tìm vị trí chính xác</a>
										</div>
									</div>
									<div class="small-4 columns">
										<div class="small-5 columns">
											<label for="right-label" class="right inline"><small
												class="validate">*</small> Ngày: </label>
										</div>
										<div class="small-7 columns">
											<input type="text" id="right-label"
												value="${router.deliveryTime}" readonly="" />
										</div>
									</div>
								</div>

							</div>
							<div class="row">
								<div class="extra-title">
									<h3><font color="blue">Chi phí</font></h3>
								</div>
								<div class="row">
									<div class="small-4 columns">
										<label class="right inline">Chi phí tài xế: </label>
									</div>
									<div class="small-4 columns left">
										<input type="text" id="right-label" name="txtPrice"
											value="${price}"  readonly="readonly"/>
									</div>
									<div class="small-4 columns left">
										<label class="left inline">(Ngàn đồng) </label>
									</div>
								</div>
								
								<div class="row">
									<div class="small-4 columns">
										<label class="right inline">Chi phí tạo hàng: </label>
									</div>
									<div class="small-4 columns left">
										<input type="text" id="right-label" 
											value="${priceCreate }" readonly="readonly"/>
									</div>
									<div class="small-4 columns left">
										<label class="left inline">(Ngàn đồng) </label>
									</div>
								</div>
									<c:set var="total" value="${sessionScope.total }"/>
								<div class="row">
									<div class="small-4 columns">
										<label class="right inline">Tổng phí: </label>
									</div>
									<div class="small-4 columns left">
										<input type="text" id="right-label" name="txtPrice"
											value="${total}" readonly="readonly"/> 
									</div>
									<div class="small-4 columns left">
									<label class="left inline">(Ngàn đồng) </label>
									</div>
								</div>
							</div>
							<div class="large-12 columns">
								<div class="submit-area right">
									<a href="GoodsServlet?btnAction=viewCreate_3"
										class="button secondary"><i class="icon-mail-reply"></i>
										Trở về</a>
									<button class="success"
										onclick="return confirm('Bạn có muốn tạo hàng không?')"
										name="btnAction" value="createGood">
										<i class="icon-ok"></i> Tạo hàng
									</button>
								</div>
							</div>
							<div class="row"></div>
							<div class="row"></div>
						</div>
				</form>


			</div>
		</div>
	</div>

</div>



<jsp:include page="footer.jsp" />
