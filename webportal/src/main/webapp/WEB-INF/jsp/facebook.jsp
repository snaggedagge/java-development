<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:genericpage>
    <jsp:body>



        <form action="/login" method="POST" >
            <input type="text" name="username" />
            <input type="password" name="password" />
            <input type="submit" value="Login" />
        </form>

        <form action="/signin/facebook" method="POST">
            <input type="hidden" name="scope" value="email" />
            <input type="submit" value="Login using Facebook"/>
        </form>


    </jsp:body>
</t:genericpage>