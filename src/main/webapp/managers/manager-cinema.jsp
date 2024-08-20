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
                    </tr>
                    </thead>

                    <tbody>

                    <tr>
                        <td hidden="hidden">${sessionScope.cinema.id}</td>
                        <td>${sessionScope.cinema.name}</td>
                        <td>${sessionScope.cinema.status}</td>
                        <td>${sessionScope.cinema.description}</td>
                        <td>${sessionScope.cinema.address}</td>

                    </tr>

                    </tbody>

                </table>

            </div>


            <button onclick="editCinema(${sessionScope.manager.id})" class="btn btn-primary w-25 mb-5 mt-4">Edit</button>



            <div class="d-flex justify-content-between mt-5 w-75">
                <a class="btn btn-secondary w-25" href="../index.jsp">Saloons</a>
                <a class="btn btn-secondary w-25" href="/managers/show.do">Shows</a>
                <a class="btn btn-secondary w-25" href="../index.jsp">Show Times</a>
            </div>




        </div>


        <jsp:include page="/footer.jsp"/>


    </div>


</div>


<script>


    function editCinema(id) {
        window.location.replace("/managers/cinema.do?edit=" + id);
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
