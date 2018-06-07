<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<div class="container">

    <h1>Shopping Cart</h1><hr>
    <jsp:include page="../include/orderTable.jsp"/>
    <a href="/checkout" class="pull-right btn btn-success">Checkout</a>

</div>


