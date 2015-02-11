<%-- 
    Document   : menu-tao-hang
    Created on : Feb 5, 2015, 12:46:19 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="router" value="${sessionScope.router}" />
<c:set var="good" value="${sessionScope.good}" />
<c:set var="price" value="${sessionScope.price}" />
<c:set var="namePage" value="${sessionScope.namePage}" />
<div class="row"></div>
<div class="large-12 columns">
	<h1 class="page-title">
		<font color="orange">Tạo hàng</font>
	</h1>
	<ul class="button-group even-4">
		<c:choose>
			<c:when test="${not empty router}">
				<li><a href="ControllerCreateGoods?btnAction=viewCreate_1"
					class="button success"><i class="icon-ok"></i> Địa chỉ giao nhận
						hàng</a></li>
			</c:when>
			<c:when test="${namePage=='tao-hang-1.jsp'}">
				<li><a href="#" class="button">Địa chỉ giao nhận hàng</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#" class="button disabled">Địa chỉ giao nhận
						hàng</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${not empty good}">
				<li><a href="ControllerCreateGoods?btnAction=viewCreate_2"
					class="button success"><i class="icon-ok"></i> Thông tin hàng
						hoá</a></li>
			</c:when>
			<c:when test="${namePage=='tao-hang-2.jsp'}">
				<li><a href="#" class="button">Thông tin hàng hoá</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#" class="button disabled">Thông tin hàng hoá</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${not empty price}">
				<li><a href="ControllerCreateGoods?btnAction=viewCreate_3"
					class="button success"><i class="icon-ok"></i> Chi phí</a></li>
			</c:when>
			<c:when test="${namePage=='tao-hang-3.jsp'}">
				<li><a href="#" class="button">Chi phí</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#" class="button disabled">Chi phí</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${namePage=='tao-hang-4.jsp'}">
				<li><a href="#" class="button">Xác nhận</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#" class="button disabled">Xác nhận</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>