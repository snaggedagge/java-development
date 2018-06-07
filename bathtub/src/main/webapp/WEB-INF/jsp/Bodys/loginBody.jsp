<%--
  Created by IntelliJ IDEA.
  User: Dag Karlsson
  Date: 07-Dec-17
  Time: 10:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="container">

    <c:if test="${not empty INFO}">
        <div class="alert alert-danger">
            <strong><c:out value="${INFO}"/></strong>
        </div>
    </c:if>


        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="username">Admin username</label>
                    <input type="text" class="form-control" id="username">
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="password">Admin password </label>
                    <input type="password" class="form-control" id="password">
                </div>
            </div>
        </div>
        <a href="#" id="test" class="btn btn-success" role="button" onclick="sendLogin()">Login</a>

</div>

<script>

    function sendLogin() {
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;

        //$.get("/login" + "?username=" + username +"&password="+password,"_self");

        document.getElementById("test").href="/login" + "?username=" + username +"&password="+password;

    }

</script>