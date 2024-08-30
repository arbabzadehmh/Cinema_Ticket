<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Moderator Panel</title>

    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/admins/admin-moderator-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100">

                <div class="p-5">
                    <i class="fa fa-id-badge mb-3" style="font-size: xxx-large"></i>
                    <h1>Moderator</h1>
                </div>

                <div style="margin-left: 5%">
                    <form action="moderator.do" method="post" enctype="multipart/form-data">

                        <div class="d-flex mb-4">



                            <input class="m-1" type="text" name="name" placeholder="Name">

                            <input class="m-1" type="text" name="family" placeholder="Family">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="username" placeholder="Username">

                            <input class="m-1" type="text" name="password" placeholder="Password">

                            <input class="m-1" type="text" name="email" placeholder="Email">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="nationalCode" placeholder="National Code">

                            <input class="m-1" type="text" name="phoneNumber" placeholder="Phone Number">

                            <input class="m-1" type="text" name="address" placeholder="Address">


                            <input type="file" name="image" class="m-1">


                        </div>


                        <div class="d-flex mb-4">
                            <input class="btn btn-dark m-1 w-25" type="submit" value="Save">
                        </div>

                    </form>

                </div>


            </div>


            <div>
                <h4 class="mb-0">All Moderators</h4>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Family</th>
                        <th>Username</th>
                        <th>Password</th>
                        <th>National Code</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>Image</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="moderator" items="${sessionScope.allModerators}">

                        <tr>
                            <td>${moderator.id}</td>
                            <td>${moderator.name}</td>
                            <td>${moderator.family}</td>
                            <td>${moderator.user.username}</td>
                            <td>${moderator.user.password}</td>
                            <td>${moderator.nationalCode}</td>
                            <td>${moderator.phoneNumber}</td>
                            <td>${moderator.email}</td>
                            <td>${moderator.address}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${not empty moderator.attachments}">
                                        <img src="${moderator.attachments.get(0).fileName}" alt="Moderator Image" height="80px" width="80px">
                                    </c:when>
                                    <c:otherwise>
                                        No Image
                                    </c:otherwise>
                                </c:choose>
                            </td>


                            <td>
                                <button onclick="editModerator(${moderator.id})" class="btn btn-primary w-25 mt-4">Edit</button>
                                <button onclick="removeModerator(${moderator.id})" class="btn btn-danger w-50 mt-4">Remove</button>
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


    function editModerator(id) {
        window.location.replace("/moderator.do?edit=" + id);
    }

    function removeModerator(id) {
        fetch("/rest/moderator/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/moderator.do";
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
