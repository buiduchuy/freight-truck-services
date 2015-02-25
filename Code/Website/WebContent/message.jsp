<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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