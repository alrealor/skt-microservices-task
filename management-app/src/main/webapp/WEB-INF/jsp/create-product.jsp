<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div class="alert alert-secondary text-center" role="alert">
            <h5>CREATE NEW PRODUCT</h5>
            <hr>
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
                        <button type="submit" class="btn btn-primary" style="width:200px;"> Create</button>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>