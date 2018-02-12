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
    <title><fmt:message key="ROUTE"/> ${currentRoute}</title>
    <jsp:include page="bootstrap/links.jsp" flush="true"/>
</head>

<body>
<jsp:include page="header.jsp" flush="true"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-8">
            <br>
            <h4 align="center"> <fmt:message key="ROUTE"/> ${currentRoute} </h4>
            <br>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="TABLE_HEADER_BUS"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_DRIVER"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_STATUS"/></th>
                    <th scope="col"><fmt:message key="REMOVE"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${routeNumberMenu}" var="item">
                    <tr>
                        <td>
                            <c:out value="${item.busContent}"/>
                        </td>
                        <td>
                            <c:out value="${item.driver}"/>
                        </td>
                        <td>
                            <c:out value="${fn:toLowerCase(item.bus.status)}"/>
                        </td>
                        <td>
                            <form name="removeBusFromRoute" method="POST" action="controller">
                                <input type="hidden" name="command" value="removeBusFromRoute"/>
                                <input type="hidden" name="busId" value="${item.bus.id}"/>
                                <button type="submit">X</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br>
            <c:if test = "${!empty freeBusesList}">
            <form method="POST" action="controller">
                <select name="selectedBus">
                    <c:forEach var="item" items="${freeBusesList}">
                        <option value="${item.bus.id}">${item.busContent.number}</option>
                    </c:forEach>
                </select>
                <input type="hidden" name="command" value="appointBusToRoute"/>
                <button type="submit">appoint</button>
            </form>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="bootstrap/scripts.jsp" flush="true"/>
</body>
</html>