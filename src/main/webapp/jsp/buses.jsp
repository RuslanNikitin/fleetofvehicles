<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${currentLang != null}">
    <fmt:setLocale value="${fn:toLowerCase(currentLang.code)}"/>
</c:if>

<c:if test="${currentLang == null}">
    <fmt:setLocale value="en"/>
</c:if>

<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="TITLE_BUSES"/></title>
    <jsp:include page="bootstrap/links.jsp" flush="true"/>
</head>

<body>
<jsp:include page="header.jsp" flush="true"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-8">
            <br>
            <h4 align="center"><fmt:message key="HEADER_BUSES"/></h4>
            <br>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="TABLE_HEADER_BUS"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_ROUTE"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_DRIVER"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_STATUS"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${busMenu}" var="item">
                    <tr>
                        <td>
                            <c:out value="${item.busContent}"/>
                        </td>
                        <td>
                            <c:out value="${item.route}"/>
                        </td>

                        <c:choose>
                            <c:when test="${item.driver != null}">
                                <td><c:out value="${item.driver}"/></td>
                                <td><c:out value="${fn:toLowerCase(item.driver.status)}"/></td>
                            </c:when>

                            <c:otherwise>
                                <td colspan="2">
                                    <form method="POST" action="controller">
                                        <select name="selectedDriver">
                                            <c:forEach var="i" items="${freeDriversList}">
                                                <option value="${i.id}">${i}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="hidden" name="command" value="appointDriverToBus"/>
                                        <input type="hidden" name="busId" value="${item.bus.id}"/>
                                        <button type="submit"><fmt:message key="APPOINT"/></button>
                                    </form>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form align="center" method="POST" action="controller">
                <input type="hidden" name="command" value="addNewBus"/>
                <button type="submit"><fmt:message key="ADD_NEW_BUS"/></button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="bootstrap/scripts.jsp" flush="true"/>
</body>
</html>