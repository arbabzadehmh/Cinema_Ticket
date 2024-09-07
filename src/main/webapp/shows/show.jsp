<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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


        <div class="content flex-column justify-content-center  align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100">

                <div class="p-5">
                    <i class="fa fa-theater-masks mb-3" style="font-size: xxx-large"></i>
                    <h1>Show</h1>
                </div>

                <div style="margin-left: 5%">
                    <form action="show.do" method="post" enctype="multipart/form-data">

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="name" placeholder="Name type to search" oninput="findShowByName(this.value)">

                            <select name="showType" class="m-1">
                                <option value="MOVIE">Movie</option>
                                <option value="THEATER">Theater</option>
                                <option value="EVENT">Event</option>
                                <option value="CONCERT">Concert</option>
                            </select>

                            <select name="status" class="m-1">
                                <option value="true">active</option>
                                <option value="false">not active</option>
                            </select>


                            <select name="genre" class="m-1">
                                <option value="ACTION">ACTION</option>
                                <option value="HORROR">HORROR</option>
                                <option value="ROMANCE">ROMANCE</option>
                                <option value="DRAMA">DRAMA</option>
                                <option value="WESTERN">WESTERN</option>
                                <option value="COMEDY">COMEDY</option>
                                <option value="FANTASY">FANTASY</option>
                                <option value="CRIME">CRIME</option>
                                <option value="MUSICAL">MUSICAL</option>
                                <option value="SHORT">SHORT</option>
                                <option value="STORY">STORY</option>
                                <option value="ANIMATION">ANIMATION</option>
                                <option value="HISTORY">HISTORY</option>
                                <option value="SCIENCE">SCIENCE</option>
                                <option value="BUSINESS">BUSINESS</option>
                                <option value="OTHER">OTHER</option>
                            </select>

                            <input class="m-1 w-100" type="number" name="basePrice" placeholder="Base Price">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="producer" placeholder="Producer">

                            <input class="m-1" type="text" name="singer" placeholder="Singer">

                            <input class="m-1" type="text" name="speaker" placeholder="Speaker">

                            <input class="m-1" type="text" name="director" placeholder="Director">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="date" name="releasedDate" placeholder="ReleasedDate">

                            <input type="file" name="image" class="m-1">

                        </div>

                        <div class="d-flex mb-4">
                            <input class="m-1 w-75" type="text" name="description" placeholder="Description">
                            <input class="btn btn-dark m-1 w-25" type="submit" value="Save">
                        </div>

                    </form>

                </div>


            </div>

            <div>
                <h4 class="mb-0 mt-3">All Shows</h4>
            </div>


            <div class="d-flex justify-content-center p-4 w-100">

                <table id="allResultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
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
                            <td>${show.id}</td>
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
                            <td>${fn:substring(show.description, 0, 10)}...</td>                            <td>
                                <div class="d-flex">
                                    <button onclick="editShow(${show.id})" class="btn btn-primary w-50">Edit</button>
                                    <button onclick="removeShow(${show.id})" class="btn btn-danger w-50">Remove</button>
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

    function editShow(id) {
        window.location.replace("/show.do?edit=" + id);
    }

    function removeShow(id){
        fetch("/rest/show/" + id, {
            method: "DELETE"
        })
            .then(response => {
            if (response.ok) {
                // Redirect if deletion was successful
                window.location = "/show.do";
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



    function findShowByName(name) {

        // AJAX call to fetch data from the API
        $.ajax({
            url: "/rest/show/findByName/" + name,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function(response) {
                // Clear previous results
                $("#allResultTable tbody").empty();


                 if (typeof response === 'object' && response !== null) {
                    // Handle a single object response
                    var button = "<button class='btn btn-primary w-100' onclick='editShow(" + response.id + ")'>Edit</button>";
                    var button1 = "<button class='btn btn-danger w-100' onclick='removeShow(" + response.id + ")'>Remove</button>";
                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
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
                        "<td>" + button1 + "</td>" +
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
