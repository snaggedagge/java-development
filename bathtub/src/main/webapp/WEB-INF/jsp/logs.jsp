<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:genericpage>
    <jsp:body>


        <div class="container">
            <h1>Logs</h1>
            <c:forEach var="logLine" items="${logEntries}">
                <br><br>
                <c:out value="${logLine}"/>
            </c:forEach>
        </div>

    </jsp:body>
</t:genericpage>