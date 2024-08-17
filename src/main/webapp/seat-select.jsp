<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Show Time Select</title>

    <link rel="stylesheet" href="assets/css/seat.css">

</head>
<body>

<h1>Select Seat</h1>
<h2>Show : ${sessionScope.selectedShow.name}</h2>
<h2>Date : ${sessionScope.selectedDate}</h2>
<h2>Cinema : ${sessionScope.selectedCinema.name}</h2>
<h2>Show Time : ${sessionScope.selectedShowTime.startTime.toLocalTime()}</h2>

<div id="seats">
    



    <c:forEach var="seatVo" items="${sessionScope.showSeats}">


        <div id="seatRow${seatVo.rowNumber}" onclick="selectSeat(${seatVo.id})">
            <h1 hidden="hidden">${seatVo.id}</h1>
                <h6>${seatVo.label}</h6>
                <h6>${seatVo.seatPrice}</h6>
        </div>


    </c:forEach>


</div>


<script>

    function selectSeat(id) {
        // window.location.replace("/test3.do?selectShowTime=" + id);
    }

</script>


</body>
</html>
