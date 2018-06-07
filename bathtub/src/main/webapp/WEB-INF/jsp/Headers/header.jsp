<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default">
    <div class="container">
        <div id="navbar" class="navbar-inner">
            <ul class="nav navbar-nav">
                <li class=""><a href="/">Overview</a></li>
                <li class=""><a href="/stats">Stats</a></li>
                <li class=""><a href="/login">Login</a></li>
                <li class=""><a href="/seeLogs?linesPerError=10">See Logs</a></li>
                <li class=""><a href="/setTimer">Set Timer</a></li>
                <c:if test="${sessionScope.ADMIN == true}">
                    <li class="" style="color: #ffac1d">Logged in as admin</li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>