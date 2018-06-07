<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:genericpage>
    <jsp:body>
        <div class="container">
            <form:form method="POST" action="/checkout" modelAttribute="customer">

                <div class="form-row row">
                    <div class="form-group col-sm-2">
                        <form:label path="firstName">First Name</form:label>
                        <form:input path="firstName" class="form-control"/>
                    </div>
                    <div class="form-group col-sm-3">
                        <form:label path="lastName">Last Name</form:label>
                        <form:input path="lastName" class="form-control" />
                    </div>
                    <div class="form-group col-sm-4">
                        <form:label path="email">Email</form:label>
                        <form:input path="email" class="form-control" placeholder="Email"/>
                    </div>
                </div>


                <div class="form-row row">
                    <div class="form-group col-md-6">
                        <form:label path="street">Address</form:label>
                        <form:input path="street" class="form-control" placeholder="1234 Main St" />
                    </div>
                </div>


                <div class="form-row row">
                    <div class="form-group col-md-3">
                        <form:label path="city">City</form:label>
                        <form:input path="city" class="form-control" />
                    </div>
                    <div class="form-group col-sm-2">
                        <form:label path="postcode">Postcode</form:label>
                        <form:input path="postcode" class="form-control" />
                    </div>
                </div>
                <div class="form-row row">
                    <div class="form-group col-md-3">
                        <form:label path="country.countryName">Country Name</form:label>
                        <form:input path="country.countryName" class="form-control" />
                    </div>
                    <div class="form-group col-sm-2">
                        <form:label path="country.countryCode">Country Code</form:label>
                        <form:input path="country.countryCode" class="form-control" />
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <form:button type="submit" class="btn btn-primary">Proceed</form:button>
                    </div>
                </div>


            </form:form>
        </div>
    </jsp:body>
</t:genericpage>