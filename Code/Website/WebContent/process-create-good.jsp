<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
ol.progtrckr {
	margin: 0;
	padding: 0;
	list-style-type
	none;
}

ol.progtrckr li {
	display: inline-block;
	text-align: center;
	line-height: 3em;
}

ol.progtrckr li.progtrckr-done {
	color: black;
	border-bottom: 4px solid yellowgreen;
}

ol.progtrckr li.progtrckr-todo {
	color: silver;
	border-bottom: 4px solid silver;
}

ol.progtrckr li:after {
	content: "\00a0\00a0";
}

ol.progtrckr li:before {
	position: relative;
	bottom: -2.5em;
	float: left;
	left: 50%;
	line-height: 1em;
}

ol.progtrckr li.progtrckr-done:before {
	content: "\2713";
	color: white;
	background-color: yellowgreen;
	height: 1.2em;
	width: 1.2em;
	line-height: 1.2em;
	border: none;
	border-radius: 1.2em;
}

ol.progtrckr li.progtrckr-todo:before {
	content: "\039F";
	color: silver;
	background-color: white;
	font-size: 1.5em;
	bottom: -1.6em;
}
</style>
<c:set var="router" value="${sessionScope.router }" />
<c:set var="good" value="${sessionScope.good }" />
<c:set var="price" value="${sessionScope.price }" />

<div class="small-12 columns">
	<div class="row"></div>
	<h2 class="page-title">
		<font color="orange">Tạo hàng</font>
	</h2>
	<c:set var="messageSuccess" value="${requestScope.messageSuccess }" />
	<c:set var="messageError" value="${requestScope.messageError }" />
	<c:if test="${not empty messageSuccess}">
		<div class="row">
			<div data-alert class="alert-box success radius inline">
				${messageSuccess} <a href="#" class="close">&times;</a>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty messageError}">
		<div class="row">
			<div data-alert class="alert-box alert radius inline">
				${messageError} <a href="#" class="close">&times;</a>
			</div>
		</div>
	</c:if>
	<ol class="progtrckr" data-progtrckr-steps="5">
		<c:choose>
			<c:when test="${not empty router }">
				<li class="progtrckr-done" style="width: 30%;">Địa chỉ giao
					nhận hàng</li>
			</c:when>
			<c:otherwise>
				<li class="progtrckr-todo" style="width: 30%;">Địa chỉ giao
					nhận hàng</li>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${not empty good }">
				<li class="progtrckr-done" style="width: 28%;">Thông tin hàng
					hoá</li>
			</c:when>
			<c:otherwise>
				<li class="progtrckr-todo" style="width: 28%;">Thông tin hàng
					hoá</li>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${not empty price }">
				<li class="progtrckr-done" style="width: 20%;">Chi phí</li>
			</c:when>
			<c:otherwise>
				<li class="progtrckr-todo" style="width: 20%;">Chi phí</li>
			</c:otherwise>
		</c:choose>
		<li class="progtrckr-todo" style="width: 20%;">Xác nhận</li>
	</ol>
</div>


<hr>
<script>
	$(document).ready(
			function() {

				$(window).load(
						function() {
							$("ol.progtrckr").each(
									function() {
										$(this).attr("data-progtrckr-steps",
												$(this).children("li").length);
									});
						})
			});
</script>