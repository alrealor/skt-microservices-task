$(document).ready(function() {

    // Submit function for create product form
    $("#createProductForm").submit(function(event){
       event.preventDefault();
        crateProductAjaxPost();
    });

    // Function that build the success response for product creation
    function successResponse(data) {
        $('#result')
            .html("<div class='alert alert-success' role='alert'> Product was created successfully </div>");
    }

    // Function that build the error response for product creation
    function errorResponse(data) {
        var errorMessage= "<div class='alert alert-danger' role='alert'> <b>" +
            data.error.errorCode + ": </b>" + data.error.errorMessage + "</div>";
        $('#result').html(errorMessage);
    }

    // ajax execution for POST product creation request
    function crateProductAjaxPost() {

        var formData = {
            "name": $("#productName").val(),
            "price":  Number($("#productPrice").val())
        }

        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            url: "http://localhost:8080/createProduct",
            data: JSON.stringify(formData),
            success: function (data) {
                console.log("SUCCESS: ", data);
                successResponse(result);
            },
            error: function (error) {
                console.log("ERROR: ", error.responseJSON);
                errorResponse(error.responseJSON);
            }
        });
    }
});