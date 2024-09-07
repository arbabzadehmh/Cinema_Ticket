<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cinema Select</title>

    <jsp:include page="/css-import.jsp"/>

    <style>
        /* Custom style to fit 5 shows in a row */
        .show-card {
            width: 25%; /* Each show takes 20% of the row (1/5th) */
        }
    </style>

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



<div class="content d-flex flex-column flex-grow-1 h-100">

    <jsp:include page="/navbar.jsp"/>

    <div class="bg-dark h-50 d-flex flex-row p-5">
        <div>
            <img src="${sessionScope.selectedShow.attachments.get(0).fileName}" alt="No Image" height="280px"
                 width="190px">
        </div>

        <div class="text-white  d-flex flex-column justify-content-between" style="margin-left: 5%">
            <div class="mb-2">
                <h2 style="text-align: left">${sessionScope.selectedShow.name}</h2>
            </div>

            <div>
                <div class="mb-4">
                    <h5 style="text-align: left">Director : ${sessionScope.selectedShow.director}</h5>
                </div>

                <div class="mb-4">
                    <h5 style="text-align: left">Producer : ${sessionScope.selectedShow.producer}</h5>
                </div>

                <div class="mb-4">
                    <h5 style="text-align: left">Genre : ${sessionScope.selectedShow.genre}</h5>
                </div>

                <div>
                    <h5 style="text-align: left">Release Date : ${sessionScope.selectedShow.releasedDate}</h5>
                </div>
            </div>

        </div>


        <div class="text-white p-4 w-25" style="margin-left: 15%">
            <p style="white-space: pre-line; text-align: left;">${sessionScope.selectedShow.description}</p>
        </div>

    </div>


    <div class="bg-secondary d-flex h-25 flex-row align-items-center">
        <div class="bg-secondary p-4 w-25 h-100 text-white">
            <div class="d-flex m-1 flex-row">
                <i class="fa fa-calendar" style="font-size: xx-large; margin-right: 8px;"></i>
                <h2>Date </h2>
            </div>
            <div class="d-flex">
                <h2>${sessionScope.selectedDate}</h2>
            </div>
        </div>

        <div class="h-100 align-content-center w-75" style="background-color: #9aa1a6; margin-left: 10%">
            <h1 style="text-align: left; margin-left: 5%">Select Cinema</h1>

        </div>

    </div>

    <div class="h-75 d-flex p-4 justify-content-center">


        <div class="d-flex w-100 justify-content-center">
            <div class="row w-75 justify-content-center">
                <c:forEach var="cinema" items="${sessionScope.cinemas}">
                    <div class="show-card m-3 "> <!-- Each show occupies 1/5th of the row -->
                        <div class="card h-100"> <!-- Card layout for each date -->
                            <c:choose>
                                <c:when test="${cinema.imageUrl != ''}">
                                    <img src="${cinema.imageUrl}" class="card-img-top d-block mx-auto"
                                         alt="Cinema Image" style="width: 200px; height: 200px">
                                </c:when>
                                <c:otherwise>
                                    <div class="card-img-top d-block mx-auto text-center py-5"
                                         style="background-color: #f0f0f0; width: 200px; height: 200px;">No Image
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body">
                                <h5 class="card-title text-center">${cinema.name}</h5>
                                <h6 class="card-title text-center">${cinema.address}</h6>
                                <div class="text-center">
                                    <button class="btn btn-primary" onclick="selectCinema(${cinema.id})">Select</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>
    <jsp:include page="/footer.jsp"/>

</div>


<script>

    function selectCinema(id) {
        window.location.replace("/cinemaHome.do?selectCinema=" + id);
    }

</script>

<jsp:include page="/js-import.jsp"/>

</body>
</html>
