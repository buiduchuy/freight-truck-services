<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="vn.edu.fpt.fts.pojo.Goods"%>
<!DOCTYPE html>
<title>Tạo hàng</title>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
<jsp:include page="header.jsp" />
<div class="large-12 columns">
	<div class="small-3 columns">
		<div class="form-content "
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="vertical-menu-create-good.jsp" />
		</div>
	</div>
	<div class="small-9 columns">
		<div class="form-content "
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<%
			request.setCharacterEncoding("UTF-8");
			String pickupAddress = request.getParameter("txtpickupAddress");
			String pickupTime = request.getParameter("txtpickupTime");
			String deliveryAddress = request
					.getParameter("txtdeliveryAddress");
			String deliveryTime = request.getParameter("txtdeliveryTime");

			Goods r = new Goods(pickupTime, pickupAddress, deliveryTime,
					deliveryAddress);

			session.setAttribute("route", r);
			%>
			<jsp:include page="process-create-good.jsp" />
			<c:set var="good" value="${sessionScope.goodinfo }" />
			<form action="create-good-next-2.jsp">
			<c:choose>
					<c:when test="${not empty good}">
						<div class="row">
							<div class="large-12 columns">
								<div class="row">
									<div class="small-3 columns">
										<label for="right-label" class="right inline"><small
											class="validate">*</small> Loại hàng: </label>
									</div>
									<div class="small-6 columns">
										<select required
											data-errormessage-value-missing="Vui lòng chọn loại hàng !"
											name="ddlgoodsCategoryID">
											<c:forEach var="row" items="${typeGoods }">

												<c:choose>
													<c:when test="${row.goodsCategoryId==good.goodsCategoryID}">
														<option value="${row.goodsCategoryId }"
															selected="selected">${row.name }</option>
													</c:when>
													<c:otherwise>
														<option value="${row.goodsCategoryId }">${row.name }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
									<div class="small-3 columns"></div>
								</div>
								<div class="row">
									<div class="small-3 columns">
										<label for="right-label" class="right inline"><small
											class="validate">*</small> Khối lượng: </label>
									</div>
									<div class="small-6 columns">
										<input type="text" id="right-label" name="txtWeight"
											onkeypress="return keyPhone(event);"
											placeholder="Nhập khối lượng hàng" required=""
											data-errormessage-value-missing="Vui lòng nhập khối lượng của hàng !"
											maxlength="5" value="${good.weight}" />
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
										<textarea maxlength="250" name="txtNotes">${good.notes }</textarea>
									</div>
									<div class="small-3 columns"></div>
								</div>
								<div class="row">
									<div class="large-12 columns">
										<div class="submit-area right">
											<a href="create-good.jsp"
												class="button secondary"><i class="icon-mail-reply"></i>
												Trở về</a>
											<button class="" name="btnAction" value="save2">
												<i class="icon-save"></i> Lưu thay đổi
											</button>
											<button class="success" name="btnAction" value="next2">
												<i class="icon-mail-forward"></i> Tiếp theo
											</button>

										</div>
										</br>
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
									<div class="small-3 columns">
										<label for="right-label" class="right inline"><small
											class="validate">*</small> Loại hàng: </label>
									</div>
									<div class="small-6 columns">

										<select required
											data-errormessage-value-missing="Vui lòng chọn loại hàng !"
											name="ddlgoodsCategoryID">
											<c:forEach var="row" items="${typeGoods }">
												<c:choose>
													<c:when test="${row.goodsCategoryId==1 }">
														<option value="${row.goodsCategoryId }"
															selected="selected">${row.name }</option>
													</c:when>
													<c:otherwise>
														<option value="${row.goodsCategoryId }">${row.name }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>

									</div>
									<div class="small-3 columns"></div>
								</div>
								<div class="row">
									<div class="small-3 columns">
										<label for="right-label" class="right inline"><small
											class="validate">*</small> Khối lượng: </label>
									</div>
									<div class="small-6 columns">
										<input type="text" id="right-label" name="txtWeight"
											onkeypress="return keyPhone(event);"
											placeholder="Nhập khối lượng hàng" required=""
											data-errormessage-value-missing="Vui lòng nhập khối lượng của hàng !"
											maxlength="5" />
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
										<textarea maxlength="250" name="txtNotes"></textarea>
									</div>
									<div class="small-3 columns"></div>
								</div>
								<div class="row">
									<div class="large-12 columns">
										<div class="submit-area right">
											<a href="create-good.jsp"
												class="button secondary"><i class="icon-mail-reply"></i>
												Trở về</a>

											<button class="success" name="btnAction" value="next2">
												<i class="icon-mail-forward"></i> Tiếp theo
											</button>

										</div>
										</br>
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
<jsp:include page="footer.jsp" />