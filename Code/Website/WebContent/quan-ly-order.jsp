<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Quản lý hoá đơn</title>
<jsp:include page="header.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="namePage" value="${sessionScope.namePage}" />
<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
<div class="large-12 columns">
	<div class="large-2 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="menu-doc-quan-ly.jsp" />
		</div>
	</div>
	<div class="large-8 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<div class="form-content">
				<form action="#" method="post" accept-charset="utf-8"
					enctype="multipart/form-data" data-abide="" novalidate="novalidate">
					<div class="row">
						<div class="large-12 columns">
							<h2 class="page-title">
								<font color="orange">Quản lý hoá đơn</font>
							</h2>
							<c:set var="messageSuccess"
								value="${sessionScope.messageSuccess }" />
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
						</div>
						<div class="large-12 columns">
							<div class="filter-bar">
								<form action="#" accept-charset="utf-8" id="frm-list-lading"
									method="GET">
									<div class="row">
										<div class="large-3 columns">
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

										<div class="large-3 columns">
											<div class="date-picker-wrap">
												<input type="text" value="Từ ngày" name="from_date"
													placeholder="Từ ngày" class="date-picker" /> <i
													class="icon-calendar"></i>
											</div>
										</div>

										<div class="large-3 columns date-picker-wrap">
											<div class="date-picker-wrap">
												<input type="text" value="Đến ngày" name="to_date"
													placeholder="Đến ngày" class="date-picker" /> <i
													class="icon-calendar"></i>
											</div>
										</div>

										<div class="large-3 columns">
											<button class="js_search_lading">
												<i class="icon-filter"></i> Lọc
											</button>
										</div>
									</div>
								</form>

							</div>
							<div class="large-12 columns">

								<table id="example" class="display" cellspacing="0" width="100%">
									<thead>
										<tr>
											<th>#</th>
											<th width="125"><font color="orange">LOẠI HÀNG</font></th>
											<th width="225"><font color="orange">THỜI GIAN
													GIAO HÀNG</font></th>
											<th width="250"><font color="orange">THỜI GIAN
													NHẬN HÀNG</font></th>

											<th width="200"><h4>
													<font color="orange"></font>
												</h4></th>
										</tr>
									</thead>
									<tbody>
									<c:set var="count" value="0"/>
										<c:set var="list1" value="${sessionScope.listOrder }" />
										<c:if test="${not empty list1 }">
											<c:forEach var="good1" items="${list1 }">
											<c:set var="count" value="${count+1 }"/>
												<tr>
													<td>${count }</td>
													<c:forEach var="row" items="${typeGoods }">
														<c:if
															test="${good1.goodsCategoryID==row.goodsCategoryId }">
															<td>${row.name }</td>
														</c:if>
													</c:forEach>
													<td>${good1.pickupTime }</td>
													<td>${good1.deliveryTime }</td>
													<td><a class="button"
														href="ControllerManageOrder?btnAction=viewDetailOrder&idGood=${good1.goodsID }">Xem
															chi tiết</a></td>
												</tr>
											</c:forEach>

										</c:if>
									</tbody>

								</table>
								</br>


							</div>


						</div>



					</div>
				</form>


			</div>
		</div>
	</div>
	<div class="large-2 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
		</div>
	</div>
</div>
<jsp:include page="footer.jsp" />