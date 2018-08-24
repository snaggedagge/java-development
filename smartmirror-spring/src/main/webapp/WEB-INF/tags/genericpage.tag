<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="head" fragment="true" %>

<%@ attribute name="breadcrumb" required="true" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:invoke fragment="head"/>
    <c:if test="${empty head}">
        <c:if test="${empty templateHead}">
            <jsp:include page="../jsp/Heads/head.jsp"/>
        </c:if>
        <c:if test="${not empty templateHead}">
            <jsp:include page="../jsp/${templateHead}"/>
        </c:if>
    </c:if>
</head>
<body>
<div id="pageheader">
    <jsp:invoke fragment="header"/>
    <c:if test="${empty templateHeader}">
        <jsp:include page="../jsp/Headers/header.jsp"/>
    </c:if>
    <c:if test="${not empty templateHeader}">
        <jsp:include page="../jsp/${templateHeader}"/>
    </c:if>


    <jsp:include page="../jsp/Headers/breadcrumb.jsp">
        <jsp:param name="breadcrumb" value="${breadcrumb}"/>
    </jsp:include>


    <c:if test="${not empty errorList}">
        <div class="navbar navbar-expand-sm alert alert-warning navbar-light">
            <c:forEach items="${errorList}" var="error">
                <p><c:out value="${error}"/></p>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${not empty infoList}">
        <div class="navbar navbar-expand-sm alert alert-success navbar-light">
            <c:forEach items="${infoList}" var="info">
                <p><c:out value="${info}"/></p>
            </c:forEach>
        </div>
    </c:if>
</div>
<div id="body">
    <jsp:doBody/>
</div>
<div id="pagefooter">
    <jsp:invoke fragment="footer"/>
</div>
</body>
</html>