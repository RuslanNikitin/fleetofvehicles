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
    <title><fmt:message key="TITLE_DRIVERS"/></title>
    <jsp:include page="bootstrap/links.jsp" flush="true"/>
</head>

<body>
<jsp:include page="header.jsp" flush="true"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-8">
            <br>
            <h4 align="center"> <fmt:message key="HEADER_DRIVERS"/> </h4>
            <br>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="TABLE_HEADER_DRIVER"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_BUS"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_ROUTE"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_STATUS"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${driverMenu}" var="item">
                    <tr>
                        <td>
                            <c:out value="${item.driver}"/>
                        </td>
                        <td>
                            <c:out value="${item.busContent}"/>
                        </td>
                        <td>
                            <c:out value="${item.route}"/>
                        </td>
                        <td>
                            <c:out value="${fn:toLowerCase(item.driver.status)}"/>
                        </td>
                        <td>
                            <c:if test="${item.busContent != null}">
                                <form method="POST" action="controller">
                                    <input type="hidden" name="command" value="setDriverToFree"/>
                                    <input type="hidden" name="driverId" value="${item.driver.id}"/>
                                    <button type="submit"><fmt:message key="SET_FREE"/></button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="bootstrap/scripts.jsp" flush="true"/>
</body>
</html>