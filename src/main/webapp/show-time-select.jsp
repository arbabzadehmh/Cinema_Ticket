<%@ page import="com.example.cinema_test.model.entity.Show" %>
<%@ page import="com.example.cinema_test.model.entity.enums.ShowType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Time Select</title>

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
                <%
                    Show show = (Show) session.getAttribute("selectedShow");
                    if (show.getShowType().equals(ShowType.MOVIE)) {
                %>

                <div class="mb-4">
                    <h5 style="text-align: left">Director : ${sessionScope.selectedShow.director}</h5>
                </div>

                <div class="mb-4">
                    <h5 style="text-align: left">Producer : ${sessionScope.selectedShow.producer}</h5>
                </div>

                <div class="mb-4">
                    <h5 style="text-align: left">Genre : ${sessionScope.selectedShow.genre}</h5>
                </div>

                <%
                } else if (show.getShowType().equals(ShowType.CONCERT)) {
                %>

                <div class="mb-4">
                    <h5 style="text-align: left">Singer : ${sessionScope.selectedShow.singer}</h5>
                </div>

                <%
                } else if (show.getShowType().equals(ShowType.EVENT)) {
                %>

                <div class="mb-4">
                    <h5 style="text-align: left">Speaker : ${sessionScope.selectedShow.speaker}</h5>
                </div>

                <% } %>

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
            <div class="d-flex m-1 flex-row mb-1">
                <i class="fa fa-calendar" style="font-size: xx-large; margin-right: 8px;"></i>
                <h2>Date </h2>
            </div>
            <div class="d-flex">
                <h2>${sessionScope.selectedDate}</h2>
            </div>
        </div>

        <div class="h-100 align-items-center justify-content-between p-3 d-flex flex-row w-75 text-white" style="background-color: #9aa1a6; margin-left: 10%">
            <div style="margin-left: 2%">
                <div class="d-flex flex-row mb-1">
                    <i class="fa fa-camera-movie" style="font-size: xx-large; margin-right: 8px;"></i>
                    <h2 class="mb-2">Cinema</h2>
                </div>

                <div class="d-flex">
                    <h2 class="mb-2">${sessionScope.selectedCinema.name}</h2>
                </div>

            </div>

            <div>

                <div>
                    <div class="d-flex flex-row">
                        <i class="fa fa-map-location-dot" style="font-size: xx-large; margin-right: 8px;"></i>
                        <h4 style="text-align: left">Address </h4>
                    </div>

                    <div>
                        <h5 style="text-align: left">${sessionScope.selectedCinema.address}</h5>
                    </div>
                </div>
            </div>

            <div style="margin-right: 3%">
                <c:choose>
                    <c:when test="${sessionScope.selectedCinema.imageUrl != ''}">
                        <img src="${sessionScope.selectedCinema.imageUrl}" alt="Cinema Image" height="150px" width="150px">
                    </c:when>
                    <c:otherwise>
                        No Image
                    </c:otherwise>
                </c:choose>
            </div>

        </div>

    </div>

    <div class="h-75 d-flex p-4 justify-content-center">

        <div class="flex-column">
            <h1 style="text-align: left">Select</h1>
            <h1>ShowTime</h1>
        </div>


        <div class="d-flex w-100 justify-content-center">
            <div class="row w-75 justify-content-center">
                <c:forEach var="showTime" items="${sessionScope.showTimes}">
                    <div class="show-card m-3 ">
                        <div class="card h-100">
                            <c:choose>
                                <c:when test="${showTime.saloonImage != ''}">
                                    <img src="${showTime.saloonImage}" class="card-img-top d-block mx-auto"
                                         alt="Cinema Image" style="width: 200px; height: 200px">
                                </c:when>
                                <c:otherwise>
                                    <div class="card-img-top d-block mx-auto text-center py-5"
                                         style="background-color: #f0f0f0; width: 200px; height: 200px;">No Image
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body">
                                <h5 class="card-title text-center">${showTime.startTime.toLocalTime()}-${showTime.endTime.toLocalTime()}</h5>
                                <h5 class="card-title text-center">Saloon : ${showTime.saloonNumber}</h5>
                                <h5 class="card-title text-center">Capacity : ${showTime.remainingCapacity}</h5>
                                <h5 class="card-title text-center">${showTime.saloonDescription}</h5>
                                <div class="text-center">
                                    <button class="btn btn-primary" onclick="selectShowTime(${showTime.id})">Select</button>
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

    function selectShowTime(id){
        window.location.replace("/seatSelect.do?selectShowTimeId=" + id);
    }

</script>

<jsp:include page="/js-import.jsp"/>

</body>
</html>
