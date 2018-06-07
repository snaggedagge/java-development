<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:genericpage>
    <jsp:attribute name="head">
        <jsp:include page="Heads/headNoUpdate.jsp"/>
    </jsp:attribute>
    <jsp:body>
        <div class="container">

            <c:if test="${timerIsSet == true}">
                <div class="alert alert-success">
                    <h1>Timer is set!</h1>
                </div>
            </c:if>
            <c:if test="${alreadyOnTimer == true}">
                <div class="alert alert-info">
                    <h1>Timer is already on!</h1>
                    <h3>Starts in <c:out value="${hoursUntilStart}"/> Hours and <c:out value="${minutesUntilStart}"/> Minutes</h3>
                </div>
            </c:if>


            <form:form modelAttribute="timer" action="/setTimer" method="post">
                <div class="form-group row">
                    <form:label path="hottubTemperature" class="col-sm-2 col-form-label">Hottub Temperature</form:label>
                    <form:input path="hottubTemperature" class="form-control"/>
                </div>
                <div class="form-group row">
                    <form:label path="circulationTemperature" class="col-sm-2 col-form-label">Circulation Temperature</form:label>
                    <form:input path="circulationTemperature" class="form-control" aria-describedby="help"/>
                    <small id="help" class="form-text text-muted">This property is else automatically set</small>
                </div>
                <div class="form-group row">
                    <form:label path="startHeatingTime" class="col-sm-2 col-form-label">Time</form:label>
                    <form:input path="startHeatingTime" class="form-control" aria-describedby="timeHelp"/>
                    <small id="timeHelp" class="form-text text-muted">Pattern is dd-MM-yyyy HH:mm</small>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form:form>
        </div>
    </jsp:body>
</t:genericpage>