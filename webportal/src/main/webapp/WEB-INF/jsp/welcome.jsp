<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:genericpage>
    <jsp:body>


    <div class="container row">

        <c:if test="${not empty websites}">
            <c:forEach items="${websites}" var="website">
                <a href="<c:out value="${website.websiteLink}"/>">
                    <div class="alert alert-success col-md-4">
                        <h1><c:out value="${website.websiteName}"/></h1>
                        <p><c:out value="${website.websiteDescription}"/></p>
                    </div>
                </a>

            </c:forEach>

        </c:if>

    </div>

    </jsp:body>
</t:genericpage>