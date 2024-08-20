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
        window.location.replace("/managers/show.do?edit=" + id);
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
