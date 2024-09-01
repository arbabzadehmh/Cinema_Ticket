<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Tickets</title>

    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/customers/customer-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <div class="p-5 ">
                <i class="fa fa-ticket mb-3" style="font-size: xxx-large"></i>
                <h1>My Tickets</h1>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
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

                    <c:forEach var="ticket" items="${sessionScope.customerTickets}">

                        <tr>
                            <td hidden="hidden">${ticket.id}</td>
                            <td>${ticket.showName}</td>
                            <td>${ticket.showDate}</td>
                            <td>${ticket.startHour}</td>
                            <td>${ticket.endHour}</td>
                            <td>${ticket.cinemaName}</td>
                            <td>${ticket.saloonNumber}</td>
                            <td>${ticket.address}</td>
                            <td>${ticket.seatLabel}</td>
                            <td>${ticket.customerName}</td>
                            <td>${ticket.customerFamily}</td>
                            <td>${ticket.customerPhoneNumber}</td>
                            <td>${ticket.verified}</td>
                            <td>${ticket.price}</td>
                            <td>${ticket.issueTime}</td>
                            <td>${ticket.description}</td>




                            <td>
                                <button onclick="printTicket(${ticket.id})" class="btn btn-primary w-25 mt-4">Print</button>
                            </td>
                        </tr>

                    </c:forEach>

                    </tbody>

                </table>

            </div>


        </div>


        <jsp:include page="/footer.jsp"/>


    </div>


</div>


<script>


    function printTicket(id) {
        window.location.replace("/ticket.do?print=" + id);
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
