<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tickets</title>

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
                    <i class="fa fa-ticket mb-3" style="font-size: xxx-large"></i>
                    <h1>Ticket</h1>
                </div>

                <div class=" w-50 align-content-center" style="margin-left: 5%">

                    <input class="input-group m-2" type="number" name="ticketId" placeholder="Ticket ID type to search"
                           oninput="findTicketById(this.value)">

                    <input class="input-group m-2" type="number" name="showTimeId" placeholder="ShowTime ID type to search"
                           oninput="findTicketByShowTimeId(this.value)">

                    <input class="input-group m-2" type="text" name="phoneNumber" placeholder="Phone Number type to search"
                           oninput="findTicketByPhoneNumber(this.value)">

                </div>




            </div>


            <div>
                <h4 class="mb-0">Tickets</h4>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Show</th>
                        <th>Date</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Cinema</th>
                        <th>Saloon</th>
                        <th>Address</th>
                        <th>Seat</th>
                        <th>Name</th>
                        <th>Family</th>
                        <th>Phone Number</th>
                        <th>Verified</th>
                        <th>Price</th>
                        <th>Issue Time</th>
                        <th>Description</th>
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

    function editTicket(id) {
        window.location.replace("/ticket.do?edit=" + id);
    }

    function removeTicket(id) {
        fetch("/rest/ticket/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/ticket.do";
                } else {
                    // If the response is not successful, read the error message
                    return response.text().then(errorMessage => {
                        // Alert the error message returned from the API
                        alert(errorMessage);
                    });
                }
            })
            .catch(error => {
                // Handle any other errors that may occur
                console.error('Error:', error);
                alert("An error occurred: " + error.message);
            });
    }


    function printTicket(id) {
        window.location.replace("/ticket.do?print=" + id);
    }

    function findTicketById(id) {

        $.ajax({
            url: "/rest/ticket/findById/" + id,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (ticket) {

                        var row = "<tr>" +
                            "<td>" + ticket.id + "</td>" +
                            "<td>" + ticket.showName + "</td>" +
                            "<td>" + ticket.showDate + "</td>" +
                            "<td>" + ticket.startHour + "</td>" +
                            "<td>" + ticket.endHour + "</td>" +
                            "<td>" + ticket.cinemaName + "</td>" +
                            "<td>" + ticket.saloonNumber + "</td>" +
                            "<td>" + ticket.address + "</td>" +
                            "<td>" + ticket.seatLabel + "</td>" +
                            "<td>" + ticket.customerName + "</td>" +
                            "<td>" + ticket.customerFamily + "</td>" +
                            "<td>" + ticket.customerPhoneNumber + "</td>" +
                            "<td>" + ticket.verified + "</td>" +
                            "<td>" + ticket.price + "</td>" +
                            "<td>" + ticket.issueTime + "</td>" +
                            "<td>" + ticket.description.substring(0,10) + "</td>" +
                            "<td>" + "<button class='btn btn-secondary' onclick='printTicket(" + ticket.id + ")'>Print</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editTicket(" + ticket.id + ")'>Edit</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-danger' onclick='removeTicket(" + ticket.id + ")'>Remove</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.showName + "</td>" +
                        "<td>" + response.showDate + "</td>" +
                        "<td>" + response.startHour + "</td>" +
                        "<td>" + response.endHour + "</td>" +
                        "<td>" + response.cinemaName + "</td>" +
                        "<td>" + response.saloonNumber + "</td>" +
                        "<td>" + response.address + "</td>" +
                        "<td>" + response.seatLabel + "</td>" +
                        "<td>" + response.customerName + "</td>" +
                        "<td>" + response.customerFamily + "</td>" +
                        "<td>" + response.customerPhoneNumber + "</td>" +
                        "<td>" + response.verified + "</td>" +
                        "<td>" + response.price + "</td>" +
                        "<td>" + response.issueTime + "</td>" +
                        "<td>" + response.description.substring(0,10) + "</td>" +
                        "<td>" + "<button class='btn btn-secondary' onclick='printTicket(" + response.id + ")'>Print</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editTicket(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeTicket(" + response.id + ")'>Remove</button>" + "</td>" +
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


    function findTicketByShowTimeId(id) {

        $.ajax({
            url: "/rest/ticket/findByShowTimeId/" + id,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (ticket) {

                        var row = "<tr>" +
                            "<td>" + ticket.id + "</td>" +
                            "<td>" + ticket.showName + "</td>" +
                            "<td>" + ticket.showDate + "</td>" +
                            "<td>" + ticket.startHour + "</td>" +
                            "<td>" + ticket.endHour + "</td>" +
                            "<td>" + ticket.cinemaName + "</td>" +
                            "<td>" + ticket.saloonNumber + "</td>" +
                            "<td>" + ticket.address + "</td>" +
                            "<td>" + ticket.seatLabel + "</td>" +
                            "<td>" + ticket.customerName + "</td>" +
                            "<td>" + ticket.customerFamily + "</td>" +
                            "<td>" + ticket.customerPhoneNumber + "</td>" +
                            "<td>" + ticket.verified + "</td>" +
                            "<td>" + ticket.price + "</td>" +
                            "<td>" + ticket.issueTime + "</td>" +
                            "<td>" + ticket.description.substring(0,10) + "</td>" +
                            "<td>" + "<button class='btn btn-secondary' onclick='printTicket(" + ticket.id + ")'>Print</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editTicket(" + ticket.id + ")'>Edit</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-danger' onclick='removeTicket(" + ticket.id + ")'>Remove</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.showName + "</td>" +
                        "<td>" + response.showDate + "</td>" +
                        "<td>" + response.startHour + "</td>" +
                        "<td>" + response.endHour + "</td>" +
                        "<td>" + response.cinemaName + "</td>" +
                        "<td>" + response.saloonNumber + "</td>" +
                        "<td>" + response.address + "</td>" +
                        "<td>" + response.seatLabel + "</td>" +
                        "<td>" + response.customerName + "</td>" +
                        "<td>" + response.customerFamily + "</td>" +
                        "<td>" + response.customerPhoneNumber + "</td>" +
                        "<td>" + response.verified + "</td>" +
                        "<td>" + response.price + "</td>" +
                        "<td>" + response.issueTime + "</td>" +
                        "<td>" + response.description.substring(0,10) + "</td>" +
                        "<td>" + "<button class='btn btn-secondary' onclick='printTicket(" + response.id + ")'>Print</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editTicket(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeTicket(" + response.id + ")'>Remove</button>" + "</td>" +
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


    function findTicketByPhoneNumber(phoneNumber) {

        $.ajax({
            url: "/rest/ticket/findByPhone/" + phoneNumber,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (ticket) {

                        var row = "<tr>" +
                            "<td>" + ticket.id + "</td>" +
                            "<td>" + ticket.showName + "</td>" +
                            "<td>" + ticket.showDate + "</td>" +
                            "<td>" + ticket.startHour + "</td>" +
                            "<td>" + ticket.endHour + "</td>" +
                            "<td>" + ticket.cinemaName + "</td>" +
                            "<td>" + ticket.saloonNumber + "</td>" +
                            "<td>" + ticket.address + "</td>" +
                            "<td>" + ticket.seatLabel + "</td>" +
                            "<td>" + ticket.customerName + "</td>" +
                            "<td>" + ticket.customerFamily + "</td>" +
                            "<td>" + ticket.customerPhoneNumber + "</td>" +
                            "<td>" + ticket.verified + "</td>" +
                            "<td>" + ticket.price + "</td>" +
                            "<td>" + ticket.issueTime + "</td>" +
                            "<td>" + ticket.description.substring(0,10) + "</td>" +
                            "<td>" + "<button class='btn btn-secondary' onclick='printTicket(" + ticket.id + ")'>Print</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editTicket(" + ticket.id + ")'>Edit</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-danger' onclick='removeTicket(" + ticket.id + ")'>Remove</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.showName + "</td>" +
                        "<td>" + response.showDate + "</td>" +
                        "<td>" + response.startHour + "</td>" +
                        "<td>" + response.endHour + "</td>" +
                        "<td>" + response.cinemaName + "</td>" +
                        "<td>" + response.saloonNumber + "</td>" +
                        "<td>" + response.address + "</td>" +
                        "<td>" + response.seatLabel + "</td>" +
                        "<td>" + response.customerName + "</td>" +
                        "<td>" + response.customerFamily + "</td>" +
                        "<td>" + response.customerPhoneNumber + "</td>" +
                        "<td>" + response.verified + "</td>" +
                        "<td>" + response.price + "</td>" +
                        "<td>" + response.issueTime + "</td>" +
                        "<td>" + response.description.substring(0,10) + "</td>" +
                        "<td>" + "<button class='btn btn-secondary' onclick='printTicket(" + response.id + ")'>Print</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editTicket(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeTicket(" + response.id + ")'>Remove</button>" + "</td>" +
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
