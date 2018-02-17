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
            <h4 align="center"><fmt:message key="NEW_BUS"/></h4>
            <br>

            <form align="center"  method="POST" action="controller">
                <input type="hidden" name="command" value="addBusToDB"/>
                <table class="table table-striped">
                    <thead>
                    <tr align="center">
                        <th scope="col"><fmt:message key="PARAMETER"/></th>
                        <th scope="col"><fmt:message key="EN_DATA"/></th>
                        <th scope="col"><fmt:message key="RU_DATA"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr align="center">
                        <td>
                            <fmt:message key="BRAND"/>
                        </td>
                        <td>
                            <input type="text" name="brandEN" value="${brandEN}">
                        </td>
                        <td>
                            <input type="text" name="brandRU" value="${brandRU}">
                        </td>
                    </tr>
                    <tr align="center">
                        <td>
                            <fmt:message key="MODEL"/>
                        </td>
                        <td>
                            <input type="text" name="modelEN" value="${modelEN}">
                        </td>
                        <td>
                            <input type="text" name="modelRU" value="${modelRU}">
                        </td>
                    </tr>
                    <tr align="center">
                        <td>
                            <fmt:message key="COLOR"/>
                        </td>
                        <td>
                            <input type="text" name="colorEN" value="${colorEN}">
                        </td>
                        <td>
                            <input type="text" name="colorRU" value="${colorRU}">
                        </td>
                    </tr>
                    <tr align="center">
                        <td>
                            <fmt:message key="NUMBER"/>
                        </td>
                        <td colspan="2">
                            <input type="text" name="busNumber" value="${busNumber}">
                        </td>
                    </tr>
                    </tbody>
                </table>
                <input type="submit" value="<fmt:message key="CONFIRM"/>">
            </form>
        </div>
    </div>
</div>

<jsp:include page="bootstrap/scripts.jsp" flush="true"/>
</body>
</html>