<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Show Time Select</title>

    <jsp:include page="/css-import.jsp"/>


    <style>

        #seats {
            display: grid;
            grid-template-columns: repeat(${sessionScope.saloonColumn}, 1fr); /* Dynamic column count */
            gap: 10px;
            margin: 10%;
            margin-bottom: 50px;
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

        .large-text {
            font-size: 24px;
        }

    </style>

</head>
<body>

<jsp:include page="/navbar.jsp"/>

<div class="d-flex flex-row p-5 justify-content-center " style="background-color: #2a415a; color: white">

    <div class="col align-content-end">
        <div class="flex-column ">
            <i class="fa fa-camera-movie mb-3 large-text"></i>
            <h2 class="mb-0">Show : ${sessionScope.selectedShow.name}</h2>
        </div>
    </div>


    <div class="col">
        <div class="row text-center mb-3">
            <h2 class="large-text">Cinema : ${sessionScope.selectedCinemaName}</h2>
        </div>
        <div class="row text-center">
            <h2 class="large-text">Saloon : ${sessionScope.selectedShowTime.saloonNumber}</h2>
        </div>
    </div>

    <div class="col">
        <div class="row text-center mb-3">
            <h2 class="large-text">Date : ${sessionScope.selectedDate}</h2>
        </div>
        <div class="row text-center">
            <h2 class="large-text">Show Time : ${sessionScope.selectedShowTime.startTime.toLocalTime()}</h2>
        </div>
    </div>

</div>

<img src="stage2.png" alt="no image" class="justify-content-center" style="width: 100%">

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
                <h6>${seat.priceRatio*sessionScope.selectedShow.basePrice}</h6>
            </div>
        </c:forEach>
    </div>

    <button class="btn btn-primary mb-5" type="submit" >Submit Selected Seats</button>
</form>


<jsp:include page="/footer.jsp"/>


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

<jsp:include page="/js-import.jsp"/>

</body>
</html>
