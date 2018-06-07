<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:if test="${not empty productList}">

    <div class="container">
        <div class="row">
            <c:forEach items="${productList}" var="product">

                    <div class="alert alert-<c:out value="${product.stock > 0 ? 'success': 'danger'}"/>">
                        <div class="row">
                            <div class="col-xs-6">
                                <h2>${product.name}</h2>
                                <p>${product.description}</p>
                                <p>${product.price} €</p>
                                <c:choose>
                                    <c:when test="${product.stock > 0}">
                                        <p>${product.stock} left in stock</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>None left in stock!</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-xs-6">
                                <form action="/addProductToCart">
                                    <input type="hidden" name="productId" value="${product.productId}">

                                    <input type="number" name="numberOfItems" value="1">

                                    <button type="submit" class="btn btn-success">Add to cart</button>
                                </form>
                            </div>
                        </div>

                    </div>
            </c:forEach>
        </div>
    </div>
</c:if>


