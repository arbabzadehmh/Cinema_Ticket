<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Support Panel</title>

    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/admins/admin-moderator-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100 ">

                <div class="p-5 ">
                    <i class="fa fa-person mb-3" style="font-size: xxx-large"></i>
                    <h1>Support</h1>
                </div>

                <div class=" w-50 align-content-center" style="margin-left: 5%">

                    <input class="input-group m-2" type="number" name="supportId" placeholder="Support ID type to search"
                           oninput="findSupportById(this.value)">

                    <input class="input-group m-2" type="number" name="customerId" placeholder="Customer ID type to search"
                           oninput="findCustomerById(this.value)">

                    <input class="input-group m-2" type="number" name="moderatorId" placeholder="Moderator ID type to search"
                           oninput="findModeratorById(this.value)">

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
                        <th>Customer ID </th>
                        <th>Moderator ID</th>
                        <th>Issue Time</th>
                        <th>Solved</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="support" items="${sessionScope.allSupport}">

                        <tr>
                            <td>${support.id}</td>
                            <td>${support.customer.id}</td>
                            <td>${support.moderator.id}</td>
                            <td>${support.issueTime}</td>
                            <td>${support.solved}</td>

                            <td>
                                <button onclick="editSupport(${support.id})" class="btn btn-primary w-25 mt-4">Edit</button>
                                <button onclick="removeSupport(${support.id})" class="btn btn-danger w-50 mt-4">Remove</button>
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


    function editSupport(id) {
        window.location.replace("/support.do?edit=" + id);
    }

    function removeSupport(id) {
        fetch("/rest/support/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/support.do";
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

    function findCustomerById(id) {

        $.ajax({
            url: "/rest/support/findCustomerById/" + id,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.customerId + "</td>" +
                        "<td>" + response.moderatorId + "</td>" +
                        "<td>" + response.issueTime + "</td>" +
                        "<td>" + response.solved + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editSupport(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeSupport(" + response.id + ")'>Remove</button>" + "</td>" +
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
                        "<td>" + response.customerId + "</td>" +
                        "<td>" + response.moderatorId + "</td>" +
                        "<td>" + response.issueTime + "</td>" +
                        "<td>" + response.solved + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editSupport(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeSupport(" + response.id + ")'>Remove</button>" + "</td>" +
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



    function findModeratorById(id) {

        $.ajax({
            url: "/rest/support/findModeratorById/" + id,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.customerId + "</td>" +
                        "<td>" + response.moderatorId + "</td>" +
                        "<td>" + response.issueTime + "</td>" +
                        "<td>" + response.solved + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editSupport(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeSupport(" + response.id + ")'>Remove</button>" + "</td>" +
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
