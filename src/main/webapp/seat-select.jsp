<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Show Time Select</title>

    <link rel="stylesheet" href="assets/css/seat.css">


    <style>
        #seats {
            display: grid;
            grid-template-columns: repeat(${sessionScope.saloonColum}, 1fr); /* Dynamic column count */
            gap: 10px;
        }

        .seat {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
            cursor: pointer;
            background-color: #7dd3b8;
        }

        .selected-seat {
            background-color: green;
            color: white;
        }
    </style>

</head>
<body>

<h1>Select Seat</h1>
<h2>Show : ${sessionScope.selectedShow.name}</h2>
<h2>Date : ${sessionScope.selectedDate}</h2>
<h2>Cinema : ${sessionScope.selectedCinemaName}</h2>
<h2>Saloon : ${sessionScope.selectedShowTime.saloonNumber}</h2>
<h2>Show Time : ${sessionScope.selectedShowTime.startTime.toLocalTime()}</h2>



<div id="seats">
    <c:forEach var="seat" items="${sessionScope.showSeats}">
        <div id="seatRow${seat.rowNumber}" class="seat" onclick="selectSeat(${seat.id})">
            <h1 hidden="hidden">${seat.id}</h1>
            <h6>${seat.label}</h6>
            <h6>${seat.priceRatio}</h6>
        </div>
    </c:forEach>
</div>



<script>

    function selectSeat(id) {
        // Remove selected class from previously selected seat
        let selectedSeat = document.querySelector('.selected-seat');
        if (selectedSeat) {
            selectedSeat.classList.remove('selected-seat');
        }

        // Add selected class to the clicked seat
        let seatDiv = document.getElementById("seatRow" + id);
        seatDiv.classList.add('selected-seat');
    }





    // function selectSeat(id) {
    //     // window.location.replace("/test3.do?selectShowTime=" + id);
    // }

</script>


</body>
</html>
