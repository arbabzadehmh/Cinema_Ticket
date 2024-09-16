<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Support Panel</title>

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
                    <i class="fa fa-user-headset mb-3" style="font-size: xxx-large"></i>
                    <h1>Support</h1>
                </div>

                <div class=" w-50 align-content-center" style="margin-left: 5%">

                    <input class="input-group m-2" type="number" name="supportId" placeholder="Support ID type to search"
                           oninput="findSupportById(this.value)">

                    <input class="input-group m-2" type="text" name="customerPhone" placeholder="Customer phone type to search"
                           oninput="findByCustomerPhone(this.value)">

                    <input class="input-group m-2" type="text" name="moderatorFamily" placeholder="Moderator family type to search"
                           oninput="findByModeratorFamily(this.value)">

                </div>


            </div>


            <div>
                <h4 class="mb-0">All Supports</h4>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer Name</th>
                        <th>Customer Family</th>
                        <th>Customer Phone</th>
                        <th>Moderator Name</th>
                        <th>Moderator Family</th>
                        <th>Issue Time</th>
                        <th>Solved</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="support" items="${sessionScope.allNotSolvedSupport}">

                        <tr>
                            <td>${support.id}</td>
                            <td>${support.customer.name}</td>
                            <td>${support.customer.family}</td>
                            <td>${support.customer.phoneNumber}</td>
                            <td>${support.moderator.name}</td>
                            <td>${support.moderator.family}</td>
                            <td>${support.issueTime}</td>
                            <td>${support.solved}</td>

                            <td>
                                <button onclick="openSupport(${support.id})" class="btn btn-primary w-100 mt-4">Open</button>
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


    function openSupport(id) {
        window.location.replace("/support.do?open=" + id);
    }


    function findSupportById(id) {

        $.ajax({
            url: "/rest/support/findById/" + id,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.customerName + "</td>" +
                        "<td>" + response.customerFamily + "</td>" +
                        "<td>" + response.customerPhoneNumber + "</td>" +
                        "<td>" + response.moderatorName + "</td>" +
                        "<td>" + response.moderatorFamily + "</td>" +
                        "<td>" + response.issueTime + "</td>" +
                        "<td>" + response.solved + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='openSupport(" + response.id + ")'>Open</button>" + "</td>" +
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


    function findByCustomerPhone(phoneNumber) {

        $.ajax({
            url: "/rest/support/findByCustomerPhone/" + phoneNumber,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (support) {

                        var row = "<tr>" +
                            "<td>" + support.id + "</td>" +
                            "<td>" + support.customerName + "</td>" +
                            "<td>" + support.customerFamily + "</td>" +
                            "<td>" + support.customerPhoneNumber + "</td>" +
                            "<td>" + support.moderatorName + "</td>" +
                            "<td>" + support.moderatorFamily + "</td>" +
                            "<td>" + support.issueTime + "</td>" +
                            "<td>" + support.solved + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='openSupport(" + support.id + ")'>Open</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.customerName + "</td>" +
                        "<td>" + response.customerFamily + "</td>" +
                        "<td>" + response.customerPhoneNumber + "</td>" +
                        "<td>" + response.moderatorName + "</td>" +
                        "<td>" + response.moderatorFamily + "</td>" +
                        "<td>" + response.issueTime + "</td>" +
                        "<td>" + response.solved + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='openSupport(" + response.id + ")'>Open</button>" + "</td>" +
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


    function findByModeratorFamily(family) {

        $.ajax({
            url: "/rest/support/findByModeratorFamily/" + family,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (support) {

                        var row = "<tr>" +
                            "<td>" + support.id + "</td>" +
                            "<td>" + support.customerName + "</td>" +
                            "<td>" + support.customerFamily + "</td>" +
                            "<td>" + support.customerPhoneNumber + "</td>" +
                            "<td>" + support.moderatorName + "</td>" +
                            "<td>" + support.moderatorFamily + "</td>" +
                            "<td>" + support.issueTime + "</td>" +
                            "<td>" + support.solved + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='openSupport(" + support.id + ")'>Open</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.customerName + "</td>" +
                        "<td>" + response.customerFamily + "</td>" +
                        "<td>" + response.customerPhoneNumber + "</td>" +
                        "<td>" + response.moderatorName + "</td>" +
                        "<td>" + response.moderatorFamily + "</td>" +
                        "<td>" + response.issueTime + "</td>" +
                        "<td>" + response.solved + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='openSupport(" + response.id + ")'>Open</button>" + "</td>" +
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
