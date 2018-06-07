<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:genericpage>
    <jsp:attribute name="head">
        <jsp:include page="Heads/headNoUpdate.jsp"/>
    </jsp:attribute>
    <jsp:body>
        <jsp:include page="Bodys/loginBody.jsp"/>
    </jsp:body>
</t:genericpage>