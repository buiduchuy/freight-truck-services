<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">

</head>
<body>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="namePage" value="${sessionScope.account}"/>
<c:if test="${not empty namePage }">
${namePage }
</c:if>


</html>