<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">

</head>
<body>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="reqUrl" value="${pageContext.request.requestURI}"/>
<c:set var="namePage" value="${fn:substringAfter(reqUrl, 'FTS/')}"
    scope="session" />
<a href="abc.jsp">CLICK</a>
	
</html>