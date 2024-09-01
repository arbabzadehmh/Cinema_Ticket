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

                        <input type="hidden" name="managerId" value="">

                        <div class="d-flex mb-4">

                            <input class="m-1 text-danger-emphasis bg-secondary-subtle" type="text" name="name" placeholder="Name type to search"
                                   oninput="findCinemaByName(this.value)">

                            <select name="status" class="m-1">
                                <option value="true">active</option>
                                <option value="false">not active</option>
                            </select>

                            <button type="button" onclick="setManager()" class="btn btn-secondary w-50 m-1">Select
                                Manager
                            </button>


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

                <div id="manager-select-div" style="margin-left: 5%; display: none">

                    <h5>
                        Manager
                    </h5>

                    <table id="managerTable" border="1" class="table-light w-100">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Family</th>
                            <th>National Code</th>
                            <th>Operation</th>
                        </tr>
                        </thead>

                        <tbody>


                        </tbody>
                    </table>

                </div>


            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <div class="w-100">
                    <h3>
                        All Cinemas
                    </h3>

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

                                    <c:choose>
                                        <c:when test="${cinema.imageUrl != ''}">
                                            <img src="${cinema.imageUrl}" alt="Cinema Image" height="80px" width="80px">
                                        </c:when>
                                        <c:otherwise>
                                            No Image
                                        </c:otherwise>
                                    </c:choose>

                                </td>


                                <td>
                                    <button onclick="setShow(${cinema.id})" class="btn btn-secondary w-auto mt-4">Show
                                    </button>
                                    <button onclick="setSaloon(${cinema.id})" class="btn btn-secondary w-auto mt-4">
                                        Saloon
                                    </button>
                                    <button onclick="setShowTime(${cinema.id})" class="btn btn-secondary w-auto mt-4">
                                        ShowTime
                                    </button>
                                    <button onclick="editCinema(${cinema.id})" class="btn btn-primary w-auto mt-4">
                                        Edit
                                    </button>
                                    <button onclick="removeCinema(${cinema.id})" class="btn btn-danger w-auto mt-4">
                                        Remove
                                    </button>
                                </td>

                            </tr>
                        </c:forEach>

                        </tbody>

                    </table>
                </div>

            </div>


        </div>


        <jsp:include page="/footer.jsp"/>


    </div>


</div>


<script>


    function editCinema(id) {
        window.location.replace("/cinema.do?edit=" + id);
    }

    function removeCinema(id) {
        fetch("/rest/cinema/" + id, {
            method: "DELETE"
        }).then(() => {
            window.location = "/cinema.do"
        })
    }

    function setManager() {

        $("#manager-select-div").show();

        $.ajax({
            url: "/rest/manager/manager-for-cinema",
            method: "GET",
            dataType: "json",
            success: function (response) {
                $("#managerTable tbody").empty();

                if (response && response.length > 0) {
                    response.forEach(function (manager) {
                        var row = "<tr>" +
                            "<td>" + manager.id + "</td>" +
                            "<td>" + manager.name + "</td>" +
                            "<td>" + manager.family + "</td>" +
                            "<td>" + manager.nationalCode + "</td>" +
                            "<td><button onclick='selectManager(" + manager.id + ")' class='btn btn-secondary'>Select</button></td>" +
                            "</tr>";
                        $("#managerTable tbody").append(row);
                    });
                } else {
                    var noDataRow = "<tr><td colspan='5'>No records found</td></tr>";
                    $("#managerTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                alert("Error fetching data: " + error);
            }
        });
    }

    // Function to set selected manager in a hidden field
    function selectManager(managerId) {
        $("input[name='managerId']").val(managerId);
        alert("Manager with ID " + managerId + " selected!");
    }

    // Form handling for cinema creation including manager selection
    $('form').submit(function () {
        var managerId = $("input[name='managerId']").val();
        if (!managerId) {
            alert("Please select a manager before submitting.");
            return false; // Prevent form submission if no manager selected
        }
    });


    function setShow(id) {
        window.location.replace("/show.do?cinemaId=" + id);
    }

    function setSaloon(id) {
        window.location.replace("/saloon.do?cinemaId=" + id);
    }

    function setShowTime(id) {
        window.location.replace("/showtime.do?cinemaId=" + id);
    }


    function findCinemaByName(name) {

        $.ajax({
            url: "/rest/cinema/findByName/" + name,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (cinema) {
                        var imageHtml = cinema.imageUrl ? "<img src='" + cinema.imageUrl + "' alt='Cinema Image' height='80px' width='80px'>" : "No Image";

                        var row = "<tr>" +
                            "<td>" + cinema.id + "</td>" +
                            "<td>" + cinema.name + "</td>" +
                            "<td>" + cinema.status + "</td>" +
                            "<td>" + cinema.description + "</td>" +
                            "<td>" + cinema.address + "</td>" +
                            "<td>" + imageHtml + "</td>" +
                            "<td>" + "<button class='btn btn-secondary' onclick='setShow(" + cinema.id + ")'>Show</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-secondary' onclick='setSaloon(" + cinema.id + ")'>Saloon</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-secondary' onclick='setShowTime(" + cinema.id + ")'>ShowTime</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editCinema(" + cinema.id + ")'>Edit</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-danger' onclick='removeCinema(" + cinema.id + ")'>Remove</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {
                    var imageHtml = response.imageUrl ? "<img src='" + response.imageUrl + "' alt='Cinema Image' height='80px' width='80px'>" : "No Image";

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.status + "</td>" +
                        "<td>" + response.description + "</td>" +
                        "<td>" + response.address + "</td>" +
                        "<td>" + imageHtml + "</td>" +
                        "<td>" + "<button class='btn btn-secondary' onclick='setShow(" + response.id + ")'>Show</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-secondary' onclick='setSaloon(" + response.id + ")'>Saloon</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-secondary' onclick='setShowTime(" + response.id + ")'>ShowTime</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editCinema(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeCinema(" + response.id + ")'>Remove</button>" + "</td>" +
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
