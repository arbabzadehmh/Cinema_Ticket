<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Date Select</title>

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


    <div class="bg-secondary h-25 d-flex align-items-center">
        <div style="margin-left: 3%">
            <h1>Select Date </h1>
        </div>
    </div>

    <div class="h-50 d-flex p-4 justify-content-center">


        <div class="d-flex w-100 justify-content-center">
            <div class="row w-75 justify-content-center">
                <c:forEach var="date" items="${sessionScope.showDates}">
                    <div class="show-card m-3 "> <!-- Each show occupies 1/5th of the row -->
                        <div class="card h-100"> <!-- Card layout for each date -->
                            <div class="card-body">
                                <h5 class="card-title text-center">${date}</h5>
                                <div class="text-center">
                                    <button class="btn btn-primary" onclick="selectDate('${date}')">Select</button>
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

    function selectDate(date) {
        window.location.replace("/cinemaHome.do?selectDate=" + date);
    }

</script>

<jsp:include page="/js-import.jsp"/>

</body>
</html>
