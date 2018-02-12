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

<html>
<head>
    <title><fmt:message key="TITLE_MAIN"/></title>
    <jsp:include page="bootstrap/links.jsp" flush="true"/>
</head>

<body>
<jsp:include page="header.jsp" flush="true"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-8">
            <br>
            <h4 align="center"> <fmt:message key="HEADER_ROUTES"/> </h4>
            <br>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="TABLE_HEADER_ROUTE"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_IN_WORK"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_AWAITING"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${menu}" var="item">
                    <tr>
                        <td>
                            <form name="routeNumber" method="POST" action="controller">
                                <input type="hidden" name="command" value="routeNumber"/>
                                <input type="hidden" name="routeId" value="${item.route.id}"/>
                                <input type="submit" value="<c:out value="${item.route}"/>">
                            </form>
                        </td>
                        <td>
                            <c:out value="${item.inWork}"/>
                        </td>
                        <td>
                            <c:out value="${item.awaiting}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <br>
            <fmt:message key="FREE_BUSES"/> <c:out value="${freeBuses}"/>
            <br>
            <fmt:message key="FREE_DRIVERS"/> <c:out value="${freeDrivers}"/>
            <br>
        </div>
    </div>
</div>

<jsp:include page="bootstrap/scripts.jsp" flush="true"/>
</body>
</html>