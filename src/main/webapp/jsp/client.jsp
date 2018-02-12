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
<jsp:include page="header_client.jsp" flush="true"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-8">
            <br>
            <h4 align="center"> Hello, <c:out value="${user.login}"/>!  </h4>
            <br>

            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="TABLE_HEADER_DRIVER"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_BUS"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_ROUTE"/></th>
                    <th scope="col"><fmt:message key="TABLE_HEADER_STATUS"/></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <c:out value="${user}"/>
                        </td>
                        <td>
                            <c:out value="${busContent}"/>
                        </td>
                        <td>
                            <c:out value="${route}"/>
                        </td>
                        <td>
                            <c:out value="${user.status}"/>
                        </td>
                        <td>
                            <c:if test="${fn:toLowerCase(user.status).equals(\"awaiting\") && route != null && busContent != null}">
                                <form method="POST" action="controller">
                                    <input type="hidden" name="command" value="agreeWithAppointment"/>
                                    <input type="hidden" name="userId" value="${user.id}"/>
                                    <button type="submit">agree</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </tbody>
            </table>



        </div>
    </div>
</div>

<jsp:include page="bootstrap/scripts.jsp" flush="true"/>
</body>
</html>