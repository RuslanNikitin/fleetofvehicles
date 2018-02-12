<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test = "${currentLang != null}">
    <fmt:setLocale value="${currentLang}" />
</c:if>

<c:if test = "${currentLang == null}">
    <fmt:setLocale value="en" />
</c:if>

<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="TITLE_LOGIN"/></title>
    <jsp:include page="jsp/bootstrap/links.jsp" flush="true"/>
</head>

<body>

<div align="center">
    <form name="loginForm" method="POST" action="controller">
        <input type="hidden" name="command" value="login"/>
        <br> <br> <br>
        <%--Login:--%>
        <fmt:message key="LOGIN"/>
        <br>
        <input type="text" name="login" value="Bob">
        <br> <br>
        <fmt:message key="PASSWORD"/>
        <br>
        <input type="password" name="password" value="bob78">
        <br> <br>
        <input type="submit" value="<fmt:message key="ENTER"/>">
    </form>
</div>

<jsp:include page="jsp/bootstrap/scripts.jsp" flush="true"/>
</body>
</html>