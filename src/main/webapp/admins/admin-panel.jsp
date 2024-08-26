<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Panel</title>

    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/admins/admin-moderator-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column  align-items-center flex-grow-1">

            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Name</th>
                        <th>Family</th>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <tr>
                        <td hidden="hidden">${sessionScope.admin.id}</td>
                        <td>${sessionScope.admin.name}</td>
                        <td>${sessionScope.admin.family}</td>
                        <td>${sessionScope.admin.user.username}</td>
                        <td>${sessionScope.admin.user.password}</td>
                        <td>${sessionScope.admin.phoneNumber}</td>
                        <td>${sessionScope.admin.email}</td>
                        <td>
                            <button onclick="editAdmin(${sessionScope.admin.id})" class="btn btn-primary w-25 mt-4">Edit</button>
                            <button onclick="removeAdmin(${sessionScope.admin.id})" class="btn btn-danger w-25 mt-4">Remove</button>
                        </td>
                    </tr>

                    </tbody>

                </table>

            </div>


            <%--            <button onclick="findManagerByName('${sessionScope.manager.name}')"> name</button>--%>
            <%--            <button onclick="findManagerByPhone('${sessionScope.manager.phoneNumber}')"> phone</button>--%>




        </div>


        <jsp:include page="/footer.jsp"/>


    </div>


</div>


<script>


    function editAdmin(id) {
        window.location.replace("/admins.do?edit=" + id);
    }


    // // Function to call the API and display the result in a table
    // function findManagerByName(name) {
    //
    //     // AJAX call to fetch data from the API
    //     $.ajax({
    //         url: "/rest/manager/findByName/" + name,
    //         method: "GET",
    //         dataType: "json", // Expect JSON response
    //         success: function(response) {
    //             // Clear previous results
    //             $("#resultTable tbody").empty();
    //
    //             // Check if there is any data in the response
    //             if (response && response.length > 0) {
    //                 // Loop through the response and create table rows
    //                 response.forEach(function(manager) {
    //                     var row = "<tr>" +
    //                         "<td hidden='hidden'>" + manager.id + "</td>" +
    //                         "<td>" + manager.name + "</td>" +
    //                         "<td>" + manager.family + "</td>" +
    //                         "<td>" + manager.user.username + "</td>" +
    //                         "<td>" + manager.user.password + "</td>" +
    //                         "<td>" + manager.nationalCode + "</td>" +
    //                         "<td>" + manager.phoneNumber + "</td>" +
    //                         "<td>" + manager.email + "</td>" +
    //                         "<td>" + manager.address + "</td>" +
    //                         "</tr>";
    //                     $("#resultTable tbody").append(row); // Add row to the table
    //                 });
    //             } else {
    //                 // If no data, show "No records found" message
    //                 var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
    //                 $("#resultTable tbody").append(noDataRow);
    //             }
    //         },
    //         error: function(xhr, status, error) {
    //             // Handle error case
    //             alert("Error fetching data: " + error);
    //         }
    //     });
    // }
</script>




<jsp:include page="../js-import.jsp"/>

</body>
</html>
