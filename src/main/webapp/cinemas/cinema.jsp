<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: arbab
  Date: 8/20/2024
  Time: 6:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cinema</title>

    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/admins/admin-moderator-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column  align-items-center flex-grow-1">


            <div class="d-flex p-4 w-100">

                <div class="p-5">
                    <i class="fa fa-camera-movie mb-3" style="font-size: xxx-large"></i>
                    <h1>Cinema</h1>
                </div>

                <div style="margin-left: 5%">
                    <form action="cinema.do" method="post" enctype="multipart/form-data">

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="name" placeholder="Name type to search">

                            <select name="status" class="m-1">
                                <option value="true">active</option>
                                <option value="false">not active</option>
                            </select>

                        </div>


                        <div class="d-flex mb-4">

                            <input class="m-1 w-75" type="text" name="description" placeholder="Description">

                            <input type="file" name="image" class="m-1">

                        </div>

                        <div class="d-flex mb-4">
                            <input class="m-1 w-75" type="text" name="address" placeholder="Address">
                            <input class="btn btn-dark m-1 w-25" type="submit" value="Save">
                        </div>

                    </form>

                </div>


            </div>



            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Address</th>
                        <th>Image</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                   <c:forEach var="cinema" items="${sessionScope.allCinemas}">
                       <tr>
                           <td>${cinema.id}</td>
                           <td>${cinema.name}</td>
                           <td>${cinema.status}</td>
                           <td>${cinema.description}</td>
                           <td>${cinema.address}</td>
                           <td>
<%--                               <img src="${cinema.attachments.get(0).fileName}" alt="No Image" width="80" height="80" style="border-radius: 20px">--%>
                           </td>
                           <td>
                               <button onclick="editCinema(${sessionScope.cinema.id})" class="btn btn-primary w-25 mt-4">Edit</button>
                           </td>

                       </tr>
                   </c:forEach>

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


    function editCinema(id) {
        window.location.replace("/cinema.do?edit=" + id);
    }


    // Function to call the API and display the result in a table
    function findCinemaByName(name) {

        // AJAX call to fetch data from the API
        $.ajax({
            url: "/rest/manager/findByName/" + name,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function(response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                // Check if there is any data in the response
                if (response && response.length > 0) {
                    // Loop through the response and create table rows
                    response.forEach(function(manager) {
                        var row = "<tr>" +
                            "<td hidden='hidden'>" + manager.id + "</td>" +
                            "<td>" + manager.name + "</td>" +
                            "<td>" + manager.family + "</td>" +
                            "<td>" + manager.user.username + "</td>" +
                            "<td>" + manager.user.password + "</td>" +
                            "<td>" + manager.nationalCode + "</td>" +
                            "<td>" + manager.phoneNumber + "</td>" +
                            "<td>" + manager.email + "</td>" +
                            "<td>" + manager.address + "</td>" +
                            "</tr>";
                        $("#resultTable tbody").append(row); // Add row to the table
                    });
                } else {
                    // If no data, show "No records found" message
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#resultTable tbody").append(noDataRow);
                }
            },
            error: function(xhr, status, error) {
                // Handle error case
                alert("Error fetching data: " + error);
            }
        });
    }
</script>





<jsp:include page="../js-import.jsp"/>

</body>
</html>
