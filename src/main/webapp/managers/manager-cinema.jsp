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

    <jsp:include page="/managers/manager-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column  align-items-center flex-grow-1">

            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Address</th>
                        <th>Image</th>
                    </tr>
                    </thead>

                    <tbody>

                    <tr>
                        <td hidden="hidden">${sessionScope.cinema.id}</td>
                        <td>${sessionScope.cinema.name}</td>
                        <td>${sessionScope.cinema.status}</td>
                        <td>${sessionScope.cinema.description}</td>
                        <td>${sessionScope.cinema.address}</td>

                        <td>
                            <c:choose>
                                <c:when test="${not empty sessionScope.cinema.attachments}">
                                    <img src="${sessionScope.cinema.attachments.get(0).fileName}" alt="Cinema Image" height="80px" width="80px">
                                </c:when>
                                <c:otherwise>
                                    No Image
                                </c:otherwise>
                            </c:choose>
                        </td>

                    </tr>

                    </tbody>

                </table>

            </div>


            <button onclick="editCinema(${sessionScope.cinema.id})" class="btn btn-primary w-25 mb-5 mt-4">Edit</button>



            <div class="d-flex justify-content-between mt-5 w-75">
                <a class="btn btn-secondary w-25" href="saloon.do">Saloons</a>
                <a class="btn btn-secondary w-25" href="show.do">Shows</a>
                <a class="btn btn-secondary w-25" href="../index.jsp">Show Times</a>
            </div>




        </div>


        <jsp:include page="/footer.jsp"/>


    </div>


</div>


<script>


    function editCinema(id) {
        window.location.replace("cinema.do?edit=" + id);
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
