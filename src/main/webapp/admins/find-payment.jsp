<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Payments</title>

    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<%
    String errorMessage = (String) session.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<div class="alert alert-danger">
    <%= errorMessage %>
</div>
<%
        session.removeAttribute("errorMessage");
    }
%>


<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/admins/admin-moderator-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100 ">

                <div class="p-5 ">
                    <i class="fa fa-money-bill-transfer mb-3" style="font-size: xxx-large"></i>
                    <h1>Payment</h1>
                </div>

                <div class=" w-50 align-content-center" style="margin-left: 5%">

                    <input class="input-group m-2" type="number" name="paymentId" placeholder="Payment ID type to search"
                           oninput="findPaymentById(this.value)">

                    <input class="input-group m-2" type="date" name="date" placeholder="Date select to search"
                           oninput="findPaymentByDate(this.value)">

                    <input class="input-group m-2" type="text" name="phoneNumber" placeholder="Phone Number type to search"
                           oninput="findPaymentByPhoneNumber(this.value)">

                </div>




            </div>


            <div>
                <h4 class="mb-0">Payments</h4>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer Phone</th>
                        <th>Price</th>
                        <th>Date/Time</th>
                        <th>Bank</th>
                        <th>Account Number</th>
                        <th>Tickets</th>
                        <th>Description</th>
                        <th>Image</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>



                    </tbody>

                </table>

            </div>


        </div>


        <jsp:include page="/footer.jsp"/>


    </div>


</div>


<script>

    function editPayment(id) {
        window.location.replace("/payment.do?edit=" + id);
    }


    function findPaymentById(id) {

        $.ajax({
            url: "/rest/payment/findById/" + id,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

               if (typeof response === 'object' && response !== null) {
                   var imageHtml = response.imageUrl ? "<img src='" + response.imageUrl + "' alt='Payment Image' height='80px' width='80px'>" : "No Image";


                   var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.customerPhoneNumber + "</td>" +
                        "<td>" + response.price + "</td>" +
                        "<td>" + response.paymentDateTime + "</td>" +
                        "<td>" + response.bankName + "</td>" +
                        "<td>" + response.accountNumber + "</td>" +
                        "<td>" + response.ticketCounts + "</td>" +
                        "<td>" + response.description + "</td>" +
                       "<td>" + imageHtml + "</td>" +
                       "<td>" + "<button class='btn btn-primary' onclick='editPayment(" + response.id + ")'>Edit</button>" + "</td>" +
                        "</tr>";
                    $("#resultTable tbody").append(row);
                } else {
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#resultTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }


    function findPaymentByDate(date) {

        $.ajax({
            url: "/rest/payment/findByPaymentDate/" + date,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (payment) {
                        var imageHtml = payment.imageUrl ? "<img src='" + payment.imageUrl + "' alt='Payment Image' height='80px' width='80px'>" : "No Image";

                        var row = "<tr>" +
                            "<td>" + payment.id + "</td>" +
                            "<td>" + payment.customerPhoneNumber + "</td>" +
                            "<td>" + payment.price + "</td>" +
                            "<td>" + payment.paymentDateTime + "</td>" +
                            "<td>" + payment.bankName + "</td>" +
                            "<td>" + payment.accountNumber + "</td>" +
                            "<td>" + payment.ticketCounts + "</td>" +
                            "<td>" + payment.description + "</td>" +
                            "<td>" + imageHtml + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editPayment(" + payment.id + ")'>Edit</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {
                    var imageHtml = response.imageUrl ? "<img src='" + response.imageUrl + "' alt='Payment Image' height='80px' width='80px'>" : "No Image";

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.customerPhoneNumber + "</td>" +
                        "<td>" + response.price + "</td>" +
                        "<td>" + response.paymentDateTime + "</td>" +
                        "<td>" + response.bankName + "</td>" +
                        "<td>" + response.accountNumber + "</td>" +
                        "<td>" + response.ticketCounts + "</td>" +
                        "<td>" + response.description + "</td>" +
                        "<td>" + imageHtml + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editPayment(" + response.id + ")'>Edit</button>" + "</td>" +
                        "</tr>";
                    $("#resultTable tbody").append(row);
                } else {
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#resultTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }


    function findPaymentByPhoneNumber(phoneNumber) {

        $.ajax({
            url: "/rest/payment/findByPhone/" + phoneNumber,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (payment) {
                        var imageHtml = payment.imageUrl ? "<img src='" + payment.imageUrl + "' alt='Payment Image' height='80px' width='80px'>" : "No Image";

                        var row = "<tr>" +
                            "<td>" + payment.id + "</td>" +
                            "<td>" + payment.customerPhoneNumber + "</td>" +
                            "<td>" + payment.price + "</td>" +
                            "<td>" + payment.paymentDateTime + "</td>" +
                            "<td>" + payment.bankName + "</td>" +
                            "<td>" + payment.accountNumber + "</td>" +
                            "<td>" + payment.ticketCounts + "</td>" +
                            "<td>" + payment.description + "</td>" +
                            "<td>" + imageHtml + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editPayment(" + payment.id + ")'>Edit</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {
                    var imageHtml = response.imageUrl ? "<img src='" + response.imageUrl + "' alt='Payment Image' height='80px' width='80px'>" : "No Image";

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.customerPhoneNumber + "</td>" +
                        "<td>" + response.price + "</td>" +
                        "<td>" + response.paymentDateTime + "</td>" +
                        "<td>" + response.bankName + "</td>" +
                        "<td>" + response.accountNumber + "</td>" +
                        "<td>" + response.ticketCounts + "</td>" +
                        "<td>" + response.description + "</td>" +
                        "<td>" + imageHtml + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editPayment(" + response.id + ")'>Edit</button>" + "</td>" +
                        "</tr>";
                    $("#resultTable tbody").append(row);
                } else {
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#resultTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
