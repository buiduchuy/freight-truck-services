<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="messageSuccess" value="${requestScope.messageSuccess }" />
<c:set var="messageError" value="${requestScope.messageError }" />
<c:if test="${not empty messageSuccess}">
	<div data-alert class="alert-box success radius inline">
		${messageSuccess} <a href="#" class="close">&times;</a>
	</div>
</c:if>
<c:if test="${not empty messageError}">
	<div data-alert class="alert-box alert radius inline">
		${messageError} <a href="#" class="close">&times;</a>
	</div>
</c:if>