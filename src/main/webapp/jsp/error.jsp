<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>Error</title>
    <jsp:include page="bootstrap/links.jsp" flush="true"/>
</head>

<body>

<div class="container" align="center">
    <br> <br> <br>
    <h3>Error</h3>
    <br>
    <c:out value="${errorMessage}"/>
    <hr/>
    <a href="controller">Return to login page</a>
</div>

<jsp:include page="bootstrap/scripts.jsp" flush="true"/>
</body>
</html>