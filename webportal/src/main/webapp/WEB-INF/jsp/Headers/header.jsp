<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<nav class="navbar navbar-default">
    <div class="container">
        <div id="navbar" class="navbar-inner">
            <ul class="nav navbar-nav">
                <li class=""><a href="/">Home</a></li>
            </ul>

            <security:authorize access="isAuthenticated()">
                <security:authentication var="user" property="principal" />
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <span class="glyphicon glyphicon-user"></span> 
                            <strong><c:out value="${user}"/></strong>
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <div class="navbar-login">
                                    <div class="row">
                                        <div class="col-lg-8">
                                            <p class="text-left"><strong><c:out value="${user}"/></strong></p>
                                            <p class="text-left">
                                                <a href="#" class="btn btn-primary btn-block btn-sm">Settings</a>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="navbar-login navbar-login-session">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <p>
                                                <a href="#" class="btn btn-danger btn-block">Logout</a>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </security:authorize>

        </div>
    </div>
</nav>