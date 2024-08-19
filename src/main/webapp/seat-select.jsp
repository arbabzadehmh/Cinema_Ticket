<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            margin: 10%;
        }

        .seat {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
            cursor: pointer;
            background-color: #7dd3b8;
        }

        .selected-seat {
            background-color: #dcb12c;
            color: black;
        }

        .sold-seat {
            background-color: darkred;
        }

        .reserved-seat {
            background-color: gray;
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
<h2>sold : ${sessionScope.soldSeatsId}</h2>

<img src="stage.png" alt="no image" class="justify-content-center" style="width: 100%">

<form id="seatForm" action="ticket.do" method="post">
    <input type="hidden" id="selectedSeats" name="selectedSeats" value="">

    <div id="seats">
        <c:forEach var="seat" items="${sessionScope.showSeats}">
            <div id="seat${seat.id}"
                 class="seat ${sessionScope.soldSeatsId.contains(seat.id) ? 'sold-seat' :
                sessionScope.reservedSeatsId.contains(seat.id) ? 'reserved-seat' : ''}"
                 onclick="selectSeat(${seat.id})">
                <h1 hidden="hidden">${seat.id}</h1>
                <h6>${seat.label}</h6>
                <h6>${seat.priceRatio}</h6>
            </div>
        </c:forEach>
    </div>

    <button type="submit">Submit Selected Seats</button>
</form>

<script>
    let selectedSeats = [];

    function selectSeat(id) {
        let seatDiv = document.getElementById("seat" + id);

        // Toggle selection
        if (seatDiv.classList.contains('selected-seat')) {
            seatDiv.classList.remove('selected-seat');
            selectedSeats = selectedSeats.filter(seatId => seatId !== id);  // Remove seat from list
        } else {
            seatDiv.classList.add('selected-seat');
            selectedSeats.push(id);  // Add seat to list
        }

        // Update hidden input with selected seat IDs
        document.getElementById("selectedSeats").value = selectedSeats.join(",");
    }
</script>

</body>
</html>
