<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cinema Editing</title>



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

    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>

        <div class="content flex-grow-1 ">

            <div class="d-flex align-items-center h-100">

                <div class="bg-secondary h-100 p-5 w-25" style="margin-left: 12%">

                    <div class="mb-5">
                        <form id="edit-form">

                            <div class="form-row">
                                <div class="form-group col-md-3">
                                    <label for="id">ID</label>
                                    <input type="number" class="form-control" id="id" name="id"
                                           value="${sessionScope.editingCinema.id}" disabled>
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="name">Name</label>
                                    <input type="text" class="form-control" id="name" name="name"
                                           value="${sessionScope.editingCinema.name}">
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="status">Status</label>
                                    <select name="status" class="form-control" id="status">
                                        <option value="true">active</option>
                                        <option value="false">not active</option>
                                    </select>
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="description">Description</label>
                                    <input type="text" class="form-control" id="description" name="description"
                                           value="${sessionScope.editingCinema.description}">
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="address">Address</label>
                                    <input type="text" class="form-control" id="address" name="address"
                                           value="${sessionScope.editingCinema.address}">
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="mt-5">
                        <button onclick="editingCinema()" class="btn btn-primary w-50">Edit</button>
                        <button onclick="cancelEditingCinema(${sessionScope.editingCinema.id})"
                                class="btn btn-light w-25">Back
                        </button>
                    </div>

                </div>

                <div class="d-inline p-3 w-25 h-75 d-flex flex-column justify-content-between" style="margin-left: 10%">
                    <div class="d-flex w-100 mb-5 justify-content-center">
                        <c:choose>
                            <c:when test="${not empty sessionScope.editingCinema.attachments}">
                                <img src="${sessionScope.editingCinema.attachments.get(0).fileName}" alt="Cinema Image" height="80px" width="80px">
                            </c:when>
                            <c:otherwise>
                                No Image
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="d-flex justify-content-center mb-5">
                        <form action="cinema.do" method="post" enctype="multipart/form-data">
                            <input type="file" name="newImage" class="input-group mb-5">
                            <input type="submit" value="Change Image" class="btn btn-dark">
                        </form>
                    </div>

                </div>



            </div>


        </div>

        <jsp:include page="/footer.jsp"/>
    </div>
</div>


<script>

    async function editingCinema() {
        let form = document.getElementById("edit-form");

        // Convert form data to a plain object
        let formDataObj = {};
        new FormData(form).forEach((value, key) => {
            formDataObj[key] = value;
        });

        try {
            // Make the PUT request with JSON data
            const response = await fetch("/cinema.do", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formDataObj)  // Send the form data as JSON
            });

            // Check if the request was successful
            if (response.ok) {
                const data = await response.json(); // Parse the JSON response
                console.log("Success:", data);

                // Display success feedback to the user
                alert("Cinema updated successfully!");

                window.location.href = '/cinema.do';

            } else {
                // Handle errors
                const errorData = await response.json();
                console.error("Error:", errorData);
                alert("Error updating cinema: " + errorData.message);

                window.location.href = '/cinema.do';
            }
        } catch (error) {
            console.error("Request failed:", error);
            alert("An error occurred while updating the cinema.");
            window.location.href = '/cinema.do';
        }

    }


    async function cancelEditingCinema(id) {

        window.location.replace("/cinema.do?cancel=" + id)
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
