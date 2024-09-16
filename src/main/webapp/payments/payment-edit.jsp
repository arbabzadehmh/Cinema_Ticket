<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Payment Editing</title>

    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>

        <div class="content flex-grow-1 ">

            <div class="d-flex align-items-center h-100">

                <div class="bg-secondary p-5 w-25" style="margin-left: 12%">

                    <div class="mb-5">

                        <form id="edit-form">

                            <div class="form-row">
                                <div class="form-group col-md-3">
                                    <label for="id">ID</label>
                                    <input type="number" class="form-control" id="id" name="id" value="${sessionScope.editingPayment.id}" disabled>
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="price">Price</label>
                                    <input type="number" class="form-control" id="price" name="price" value="${sessionScope.editingPayment.price}" disabled>
                                </div>
                                <div class="form-group col-md-8">
                                    <label for="paymentDateTime">Payment Time</label>
                                    <input type="text" class="form-control" id="paymentDateTime" name="paymentDateTime" value="${sessionScope.editingPayment.paymentDateTime}" disabled>
                                </div>
                            </div>



                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="description">Description</label>
                                    <input type="text" class="form-control" id="description" name="description" value="${sessionScope.editingPayment.description}">
                                </div>
                            </div>
                        </form>

                    </div>


                    <div>
                        <button onclick="editingPayment()" class="btn btn-primary w-50">Edit</button>
                        <button onclick="cancelEditingPayment(${sessionScope.editingPayment.id})" class="btn btn-light w-25">Back</button>
                    </div>


                </div>

                <div class="d-inline p-5 w-25 h-100 d-flex flex-column justify-content-between" style="margin-left: 10%">

                    <div class="d-flex w-100 mb-5 bg-body-secondary justify-content-center">
                        <c:choose>
                            <c:when test="${not empty sessionScope.editingPayment.attachments}">
                                <img src="${sessionScope.editingPayment.attachments.get(0).fileName}" alt="Payment Image" height="200px" width="200px">
                            </c:when>
                            <c:otherwise>
                                No Image
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="d-flex justify-content-center mb-5">
                        <form action="payment.do" method="post" enctype="multipart/form-data">
                            <input type="file" name="newImage" class="input-group mb-5">
                            <input type="submit" value="Change Image" class="btn btn-dark">
                        </form>
                    </div>

                </div>

            </div>


        </div>

        <jsp:include page="/footer.jsp"/>
    </div>
</div>


<script>

    async function editingPayment() {
        let form = document.getElementById("edit-form");

        // Convert form data to a plain object
        let formDataObj = {};
        new FormData(form).forEach((value, key) => {
            formDataObj[key] = value;
        });

        try {
            // Make the PUT request with JSON data
            const response = await fetch("/payment.do", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formDataObj)  // Send the form data as JSON
            });

            // Check if the request was successful
            if (response.ok) {
                const data = await response.json(); // Parse the JSON response
                console.log("Success:", data);

                // Display success feedback to the user
                alert("Payment updated successfully!");

                window.location.href = '/payment.do';

            } else {
                // Handle errors
                const errorData = await response.json();
                console.error("Error:", errorData);
                alert("Error updating payment: " + errorData.message);

                window.location.href = '/payment.do';
            }
        } catch (error) {
            console.error("Request failed:", error);
            alert("An error occurred while updating the payment.");
            window.location.href = '/payment.do';
        }

    }


    async function cancelEditingPayment(id) {

        window.location.replace("/payment.do?cancel=" + id)
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
