<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Panel</title>

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

            <div class="d-flex p-4 w-100">

                <div class="p-5">
                    <i class="fa fa-user mb-3" style="font-size: xxx-large"></i>
                    <h1>Admin</h1>
                </div>

                <div style="margin-left: 5%">
                    <form action="admins.do" method="post" enctype="multipart/form-data">

                        <div class="d-flex mb-4">



                            <input class="m-1" type="text" name="name" placeholder="Name">

                            <input class="m-1" type="text" name="family" placeholder="Family">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="username" placeholder="Username">

                            <input class="m-1" type="text" name="password" placeholder="Password">


                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="phoneNumber" placeholder="Phone Number">

                            <input class="m-1" type="text" name="email" placeholder="Email">

                            <input type="file" name="image" class="m-1">


                        </div>


                        <div class="d-flex mb-4">
                            <input class="btn btn-dark m-1 w-25" type="submit" value="Save">
                        </div>

                    </form>

                </div>


            </div>


            <div>
                <h4 class="mb-0">All Admins</h4>
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
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Image</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="admin" items="${sessionScope.allAdmins}">

                        <tr>
                            <td>${admin.id}</td>
                            <td>${admin.name}</td>
                            <td>${admin.family}</td>
                            <td>${admin.user.username}</td>
                            <td>${admin.user.password}</td>
                            <td>${admin.phoneNumber}</td>
                            <td>${admin.email}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${not empty admin.attachments}">
                                        <img src="${admin.attachments.get(0).fileName}" alt="Admin Image" height="80px" width="80px">
                                    </c:when>
                                    <c:otherwise>
                                        No Image
                                    </c:otherwise>
                                </c:choose>
                            </td>


                            <td>
                                <button onclick="editAdmin(${admin.id})" class="btn btn-primary w-25 mt-4">Edit</button>
                                <button onclick="removeAdmin(${admin.id})" class="btn btn-danger w-50 mt-4">Remove</button>
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


    function editAdmin(id) {
        window.location.replace("/admins.do?edit=" + id);
    }

    function removeAdmin(id) {
        fetch("/rest/admins/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/admins.do";
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
