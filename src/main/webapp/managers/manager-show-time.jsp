<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ShowTime</title>



    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/managers/manager-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content flex-column justify-content-center  align-items-center flex-grow-1">



            <div class="d-flex p-4 w-100">

                <div class="p-5">
                    <i class="fa fa-popcorn mb-3" style="font-size: xxx-large"></i>
                    <h1>ShowTime</h1>
                </div>

                <div style="margin-left: 5%">
                    <form action="showtime.do" method="post">

                        <!-- Saloon and Show Section -->
                        <div class="d-flex mb-4">

                            <!-- Align Saloon Label and Select to the Left -->
                            <div class="form-group col-md-4 text-left m-1">
                                <label>Saloon</label>
                                <select name="saloonNumber" id="saloonNumber" class="form-control">
                                    <c:forEach var="saloon" items="${sessionScope.cinemaSaloons}">
                                        <option>${saloon.saloonNumber}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Show Input -->
                            <div class="form-group col-md-8 m-1">
                                <label>Show</label>
                                <select name="show" id="show" class="form-control">
                                    <c:forEach var="show" items="${sessionScope.allUsableShows}">
                                        <option>${show.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                        </div>

                        <!-- Start and End Time Section -->
                        <div class="d-flex mb-4">

                            <div class="form-group col-md-6 m-1">
                                <label for="startTime">Start Time</label>
                                <input class="form-control" type="date" id="date" name="date">
                            </div>

                            <div class="form-group col-md-6 m-1">
                                <label for="startTime">Start Time</label>
                                <input class="form-control" type="time" id="startTime" name="startTime">
                            </div>

                            <div class="form-group col-md-6 m-1">
                                <label for="endTime">End Time</label>
                                <input class="form-control" type="time" id="endTime" name="endTime">
                            </div>

                        </div>

                        <!-- Status, Description, and Save Button Section -->
                        <div class="d-flex mb-4">
                            <!-- Status -->
                            <div class="form-group col-md-3 m-1">
                                <label for="status">Status</label>
                                <select name="status" class="form-control" id="status">
                                    <option value="true">active</option>
                                    <option value="false">not active</option>
                                </select>
                            </div>

                            <!-- Description -->
                            <div class="form-group col-md-6 m-1">
                                <label for="description">Description</label>
                                <input class="form-control" type="text" id="description" name="description" placeholder="Description">
                            </div>

                            <!-- Save Button -->
                            <div class="form-group col-md-3 d-flex align-items-end m-1">
                                <input class="btn btn-dark w-100" type="submit" value="Save">
                            </div>
                        </div>


                    </form>
                </div>





            </div>

            <div>
                <h4 class="mb-0">${sessionScope.cinema.name} CINEMA SHOW TIMES</h4>
            </div>

            <div class="d-flex justify-content-center p-4 w-100">



                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Saloon</th>
                        <th>Show</th>
                        <th>Remaining Capacity</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="showTime" items="${sessionScope.cinemaShowTimes}">

                        <tr>
                            <td>${showTime.id}</td>
                            <td>${showTime.saloon.saloonNumber}</td>
                            <td>${showTime.show.name}</td>
                            <td>${showTime.remainingCapacity}</td>
                            <td>${showTime.startTime}</td>
                            <td>${showTime.endTime}</td>
                            <td>${showTime.status}</td>
                            <td>${showTime.description}</td>

                            <td>
                                <button onclick="editShowTime(${showTime.id})" class="btn btn-primary w-auto mt-4">Edit</button>
                                <button onclick="removeShowTime(${showTime.id})" class="btn btn-danger w-auto mt-4">Remove</button>
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


    function editShowTime(id) {
        window.location.replace("/showtime.do?edit=" + id);
    }

    function removeShowTime(id) {
        fetch("/rest/show-time/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/showtime.do";
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


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
