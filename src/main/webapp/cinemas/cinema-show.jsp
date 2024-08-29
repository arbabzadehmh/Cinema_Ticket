<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cinema</title>

    <link rel="stylesheet" href="../assets/css/index.css">
    <link rel="stylesheet" href="../assets/css/all.css">
    <link rel="stylesheet" href="../assets/css/UI.css">
    <link rel="stylesheet" href="../assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/fonts.min.css">
    <link rel="stylesheet" href="../assets/css/animate.min.css">
    <link rel="stylesheet" href="../assets/css/fontawesome/all.css">


    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/admins/admin-moderator-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content flex-column justify-content-center  align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100">

                <div class="p-4 d-flex w-100 align-items-center justify-content-between">
                    <div>
                        <i class="fa fa-theater-masks mb-3" style="font-size: xxx-large"></i>
                        <h1>Show</h1>
                    </div>
                    <div class="ml-auto d-flex flex-column align-items-center">
                        <h4>Show Search</h4>
                        <label style="font-size: large">The Name Of Show</label>
                        <input type="text" name="name" oninput="findShowByName(this.value)">
                    </div>
                </div>



            </div>

            <div>
                <h4 class="mb-0">${sessionScope.manager.cinemaName} CINEMA SHOWS</h4>
            </div>

            <div class="d-flex justify-content-center p-4 w-100">



                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Name</th>
                        <th>Genre</th>
                        <th>Director</th>
                        <th>Producer</th>
                        <th>Singer</th>
                        <th>Speaker</th>
                        <th>Released Date</th>
                        <th>Base Price</th>
                        <th>Type</th>
                        <th>Available</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="show" items="${sessionScope.shows}">

                        <tr>
                            <td hidden="hidden">${show.id}</td>
                            <td>${show.name}</td>
                            <td>${show.genre}</td>
                            <td>${show.director}</td>
                            <td>${show.producer}</td>
                            <td>${show.singer}</td>
                            <td>${show.speaker}</td>
                            <td>${show.releasedDate}</td>
                            <td>${show.basePrice}</td>
                            <td>${show.showType}</td>
                            <td>${show.available}</td>
                            <td>${show.status}</td>
                            <td>${show.description}</td>
                            <td>
                                <div class="d-flex">
                                    <button onclick="removeShowFromList(${show.id})" class="btn btn-danger w-100">Remove</button>
                                </div>

                            </td>

                        </tr>


                    </c:forEach>


                    </tbody>

                </table>

            </div>

            <div>
                <h4 class="mb-0 mt-3">All Shows</h4>
            </div>


            <div class="d-flex justify-content-center p-4 w-100">

                <table id="allResultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Name</th>
                        <th>Genre</th>
                        <th>Director</th>
                        <th>Producer</th>
                        <th>Singer</th>
                        <th>Speaker</th>
                        <th>Released Date</th>
                        <th>Base Price</th>
                        <th>Type</th>
                        <th>Available</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="show" items="${sessionScope.allShows}">

                        <tr>
                            <td hidden="hidden">${show.id}</td>
                            <td>${show.name}</td>
                            <td>${show.genre}</td>
                            <td>${show.director}</td>
                            <td>${show.producer}</td>
                            <td>${show.singer}</td>
                            <td>${show.speaker}</td>
                            <td>${show.releasedDate}</td>
                            <td>${show.basePrice}</td>
                            <td>${show.showType}</td>
                            <td>${show.available}</td>
                            <td>${show.status}</td>
                            <td>${show.description}</td>
                            <td>
                                <div class="d-flex">
                                    <button onclick="addShow(${show.id})" class="btn btn-dark w-100">Add</button>
                                </div>

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

    function addShow(id) {
        window.location.replace("/show.do?add=" + id);
    }

    function removeShowFromList(id){
        window.location.replace("/show.do?removeFromList=" + id);
    }


    // Function to call the API and display the result in a table
    function findShowByName(name) {

        // AJAX call to fetch data from the API
        $.ajax({
            url: "/rest/show/findByName/" + name,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function(response) {
                // Clear previous results
                $("#allResultTable tbody").empty();

                // Check if response is an array
                if (Array.isArray(response) && response.length > 0) {
                    // Loop through the array and create table rows
                    response.forEach(function(show) {
                        var button = "<button class='btn btn-dark' onclick='addShow(" + show.id + ")'>Add</button>";
                        var row = "<tr>" +
                            "<td hidden='hidden'>" + show.id + "</td>" +
                            "<td>" + show.name + "</td>" +
                            "<td>" + show.genre + "</td>" +
                            "<td>" + show.director + "</td>" +
                            "<td>" + show.producer + "</td>" +
                            "<td>" + show.singer + "</td>" +
                            "<td>" + show.speaker + "</td>" +
                            "<td>" + show.releasedDate + "</td>" +
                            "<td>" + show.basePrice + "</td>" +
                            "<td>" + show.showType + "</td>" +
                            "<td>" + show.available + "</td>" +
                            "<td>" + show.status + "</td>" +
                            "<td>" + show.description + "</td>" +
                            "<td>" + button + "</td>" +
                            "</tr>";

                        $("#allResultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {
                    // Handle a single object response
                    var button = "<button class='btn btn-dark w-100' onclick='addShow(" + response.id + ")'>Add</button>";
                    var row = "<tr>" +
                        "<td hidden='hidden'>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.genre + "</td>" +
                        "<td>" + response.director + "</td>" +
                        "<td>" + response.producer + "</td>" +
                        "<td>" + response.singer + "</td>" +
                        "<td>" + response.speaker + "</td>" +
                        "<td>" + response.releasedDate + "</td>" +
                        "<td>" + response.basePrice + "</td>" +
                        "<td>" + response.showType + "</td>" +
                        "<td>" + response.available + "</td>" +
                        "<td>" + response.status + "</td>" +
                        "<td>" + response.description + "</td>" +
                        "<td>" + button + "</td>" +
                        "</tr>";
                    $("#allResultTable tbody").append(row);
                } else {
                    // If no data, show "No records found" message
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#allResultTable tbody").append(noDataRow);
                }
            },

            error: function(xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }

</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
