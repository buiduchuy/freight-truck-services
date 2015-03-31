<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Chi tiết lộ trình</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="detailRoute" value="${sessionScope.viewDetailRoute }" />
<c:set var="detailGood1" value="${sessionScope.detailGood1 }" />
<jsp:include page="header.jsp" />
<div class="large-12 columns">
	<div class="large-3 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="vertical-menu-make-deal.jsp" />
			<div class="row"></div>
		</div>
	</div>
	<div class="large-9 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<div class="form-content">
				<c:choose>
					<c:when test="${not empty detailRoute }">
						<form action="#" method="post" accept-charset="utf-8"
							enctype="multipart/form-data" data-abide=""
							novalidate="novalidate">
								<div class="row">
									<div class="large-12 columns">
										<h2 class="page-title">
											<font color="orange">Chi tiết lộ trình</font>
										</h2>
										<c:set var="messageSuccess"
											value="${sessionScope.messageSuccess }" />
										<c:set var="messageError"
											value="${sessionScope.messageError }" />
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
									<c:set var="error" value="${sessionScope.errorSendDeal}" />
									<c:if test="${not empty error}">
										<font color="red">${error}</font>
									</c:if>
									<div class="large-12 columns">

										<div class="row">
											<div class="small-4 columns ">
												<label class="right inline">Địa điểm bắt đầu:</label>
											</div>
											<div class="small-6 columns left">
												<input type="text" id="startAddress" class="left inline"
													value="${detailRoute.startingAddress }" readonly="readonly">
											</div>
										</div>
										<div class="row">
											<div class="small-4 columns ">
												<label class="right inline">Thời gian bắt đầu:</label>
											</div>
											<div class="small-6 columns left">
												<input type="text" class="left inline"
													value="${detailRoute.startTime }" readonly="readonly">
											</div>
										</div>
										<div class="row">
											<div class="small-4 columns ">
												<label class="right inline">Địa điểm kết thúc:</label>
											</div>
											<div class="small-6 columns left">
												<input type="text" id="endAddress" class="left inline"
													value="${detailRoute.destinationAddress }"
													readonly="readonly">
											</div>
										</div>
										<div class="row">
											<div class="small-4 columns ">
												<label class="right inline">Thời gian kết thúc:</label>
											</div>
											<div class="small-6 columns left">
												<input type="text" class="left inline"
													value="${detailRoute.finishTime }" readonly="readonly">
											</div>
										</div>
										<div class="row">
											<div class="small-4 columns ">
												<label class="right inline">Khối lượng có thể chở:</label>
											</div>
											<div class="small-6 columns left">
												<input type="text" class="left inline"
													value="${detailRoute.weight } Kg" readonly="readonly">
											</div>
										</div>
										<div class="row">
											<div class="small-4 columns ">
												<label class="right inline">Ghi chú:</label>
											</div>
											<div class="small-6 columns left">
												<input type="text" class="left inline"
													value="${detailRoute.notes }" readonly="readonly">
											</div>
										</div>


										<div class="row">
											<div class="submit-area right">
												<a class="button success"
													href="DealServlet?btnAction=sendDeal&routeID=${detailRoute.routeID }&goodsID=${detailGood1.goodsID}"
													onclick="return confirm('Bạn có muốn gửi đề nghị này không?')">
													<i class="icon-envelope"></i> Gửi để nghị
												</a>

											</div>
										</div>
									</div>
									<div class="row"></div>
									<div class="row"></div>
								</div>
						</form>
					</c:when>
					<c:otherwise>
						<jsp:forward page="goi-y-he-thong.jsp" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

</div>









<jsp:include page="footer.jsp" />
