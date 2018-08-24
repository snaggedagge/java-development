<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<nav aria-label="breadcrumb">

    <ol class="breadcrumb">


        <c:forEach items="${fn:split(param.breadcrumb,'/')}" var="item" varStatus="loop">

            <c:if test="${!loop.last}">
                <li class="breadcrumb-item">
                    <a href="#"><c:out value="${item}"/></a>
                </li>
            </c:if>
            <c:if test="${loop.last}">
                <li class="breadcrumb-item active" aria-current="page">
                    <c:out value="${item}"/>
                </li>
            </c:if>

        </c:forEach>

    </ol>
</nav>