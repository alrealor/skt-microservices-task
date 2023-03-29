<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Products</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="css/skt.css" rel="stylesheet" type="text/css">
</head>
    <body>
        <div class="wrapper">
            <header class="alert alert-secondary">
                <h4>LIST OF PRODUCTS</h4>
            </header>
            <main class="container shadow p-3 mb-5 bg-body rounded">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Row</th>
                        <th>Name</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="count" value="0" scope="page" />
                    <c:forEach var="product" items="${products}">
                        <c:set var="count" value="${count + 1}" scope="page"/>
                        <tr>
                            <td><c:out value="${count}"/></td>
                            <td>${product.name}</td>
                            <td>${product.price}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </main>
            <footer class="alert alert-secondary">
                <a href="/" style="text-decoration: none">BACK</a>
            </footer>
        </div>
    </body>
</html>
