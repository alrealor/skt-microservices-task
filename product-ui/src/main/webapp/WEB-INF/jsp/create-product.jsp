<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Products</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<%--    <script src="js/createProduct.js"></script>--%>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="css/skt.css" rel="stylesheet" type="text/css">
</head>
    <body>
        <div class="wrapper">
            <header class="alert alert-secondary">
                <h4>CREATE A NEW PRODUCT</h4>
            </header>
            <main class="container shadow p-3 mb-5 bg-body rounded">
                <div class="create-product">
                    <form action="/createProduct" method="post">
                        <div class="d-flex flex-column align-items-center">
                            <div class="form-group d-flex flex-column align-items-start">
                                <input type="text" class="form-control" name="name" placeholder="Name">
                            </div>
                            <br>
                            <div class="form-group d-flex flex-column align-items-start">
                                <input type="number" step="0.01" class="form-control" name="price" placeholder="Price">
                            </div>
                            <br>
                            <div class="d-flex justify-content-center">
                                <button type="submit" class="btn btn-secondary" style="width:200px;">Create</button>
                            </div>
                        </div>
                    </form>
                    <%-- Result response --%>
                    <c:if test="${messageSent}">
                        <div>
                            <div class="mt-5">
                                <div class='alert alert-success' role='alert'>Product creation request was sent!</div>
                            </div>
                        </div>
                    </c:if>
                    <%-- Validation error --%>
                    <c:if test="${validationError}">
                        <div>
                            <div class="mt-5">
                                <div class='alert alert-warning' role='alert'>Product data is not valid!</div>
                            </div>
                        </div>
                    </c:if>
                    <%-- Product creation error --%>
                    <c:if test="${productCreationError}">
                        <div>
                            <div class="mt-5">
                                <div class='alert alert-danger' role='alert'>An error occurred, please try again later</div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </main>
            <footer class="alert alert-secondary">
                <a href="/" style="text-decoration: none">BACK</a>
            </footer>
        </div>
    </body>
</html>