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
                <h4>PRODUCTS</h4>
            </header>
            <main class="container shadow mb-5 bg-body rounded">
                <div class="list-products">
                    <div class="d-flex flex-row justify-content-center">
                        <a href="/goToCreatePage" class="btn btn-secondary" role="button" style="width:150px;margin-right: 1em">Create</a>
                        <a href="/goToListPage" class="btn btn-secondary" role="button" style="width:150px;">Display</a>
                    </div>
                    <%-- Result response --%>
                    <c:if test="${error}">
                        <div class="mt-5">
                            <div class='alert alert-danger' role='alert'>
                                <b>Error</b> - list of products cannot de displayed, please try again later
                            </div>
                        </div>
                    </c:if>
                </div>
            </main>
            <footer class="alert alert-secondary">
            </footer>
        </div>
    </body>
</html>