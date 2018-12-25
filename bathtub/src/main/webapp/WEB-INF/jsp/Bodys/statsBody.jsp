<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">
    <img src="/images/logo.png" style="width:inherit;max-width: 100%;"/>

    <c:if test="${not empty INFO}">
        <div class="alert alert-danger">
            <strong><c:out value="${INFO}"/></strong>
        </div>
    </c:if>



    <div class="row">
        <div class="col-sm-3">
            <div class="alert alert-info">
                Heater time since started: <strong><fmt:formatNumber type="number" maxFractionDigits="2" value="${timeHeatingSinceStarted}"/></strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-info">
                Heater hours is <strong><fmt:formatNumber type="number" maxFractionDigits="2" value="${runningTime.runningTimeHeaterHours}"/></strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-info">
                Circulation hours is <strong><fmt:formatNumber type="number" maxFractionDigits="2" value="${runningTime.runningTimeCirculationHours}"/></strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-info">
                Total effective bath time is <strong><fmt:formatNumber type="number" maxFractionDigits="2" value="${runningTime.bathTotalTimeHours}"/></strong>
            </div>
        </div>
    </div>
</div>


