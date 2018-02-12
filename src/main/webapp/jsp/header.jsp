<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix = "fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test = "${currentLang != null}">
    <fmt:setLocale value="${fn:toLowerCase(currentLang.code)}" />
</c:if>

<c:if test = "${currentLang == null}">
    <fmt:setLocale value="en" />
</c:if>

<fmt:setBundle basename="lang"/>

<nav class="navbar navbar-light bg-light">
    <span class="navbar-brand mb-0 h1">
        <c:out value="${user.login}"/>, <c:out value="${fn:toLowerCase(user.type)}"/>
    </span>

    <form class="mb-0" name="routes" method="POST" action="controller">
        <input type="hidden" name="command" value="routes"/>
        <button class="btn btn-light navbar-brand" type="submit"><fmt:message key="HEADER_ROUTES"/></button>
    </form>

    <form class="mb-0" name="buses" method="POST" action="controller">
        <input type="hidden" name="command" value="buses"/>
        <button class="btn btn-light navbar-brand" type="submit"><fmt:message key="HEADER_BUSES"/></button>
    </form>

    <form class="mb-0" name="drivers" method="POST" action="controller">
        <input type="hidden" name="command" value="drivers"/>
        <button class="btn btn-light navbar-brand" type="submit"><fmt:message key="HEADER_DRIVERS"/></button>
    </form>

    <form class="mb-0" name="en" method="POST" action="controller">
        <input type="hidden" name="command" value="language"/>
        <input type="hidden" name="lang" value="EN"/>
        <button class="btn btn-light navbar-brand" type="submit"><fmt:message key="HEADER_EN"/></button>
    </form>

    <form class="mb-0" name="ru" method="POST" action="controller">
        <input type="hidden" name="command" value="language"/>
        <input type="hidden" name="lang" value="RU"/>
        <button class="btn btn-light navbar-brand" type="submit"><fmt:message key="HEADER_RU"/></button>
    </form>

    <form class="mb-0" name="logout" method="POST" action="controller">
        <input type="hidden" name="command" value="logout"/>
        <button class="btn btn-light navbar-brand" type="submit"><fmt:message key="HEADER_LOGOUT"/></button>
    </form>

</nav>
