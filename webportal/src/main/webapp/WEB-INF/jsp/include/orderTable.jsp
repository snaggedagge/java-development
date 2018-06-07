<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<c:set var="totalAmount" value="${0}"/>
<c:if test="${not empty shoppingCart && not empty shoppingCart.productsList}">
    <table class="table table-striped table-hover table-bordered">
        <tbody>
        <tr>
            <th><span class="pull-left">Product ID</span></th>
            <th>Name</th>
            <th><span class="pull-right">Unit Price</span></th>
            <th><span class="pull-right">Amount</span></th>
            <th><span class="pull-right">Total Price</span></th>
        </tr>
        <c:forEach var="products" items="${shoppingCart.productsList}">
            <tr>
                <td>${products.product.productId}</td>
                <td>${products.product.name}</td>
                <td>${products.product.price}</td>
                <td>${products.amount}</td>
                <td>${products.price}</td>
            </tr>
            <c:set var="totalAmount" value="${totalAmount + products.price}"/>
        </c:forEach>

        <tr>
            <th><span class="pull-right">Order Total</span></th>
            <th><c:out value="${totalAmount}"/> €</th>

        </tr>

        </tbody>
    </table>
</c:if>