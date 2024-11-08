<%@ page import="com.example.cinema_test.model.entity.Show" %>
<%@ page import="com.example.cinema_test.model.entity.enums.ShowType" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="com.example.cinema_test.model.entity.ShowTime" %>
<%@ page import="com.example.cinema_test.model.entity.ShowTimeVo" %>
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

<jsp:include page="/navbar.jsp"/>

<div class="d-flex flex-row p-5 justify-content-between " style="background-color: #2a415a; color: white">

    <div class="d-flex flex-column">
        <div class="d-flex flex-row mb-1">
            <i class="fa fa-theater-masks" style="font-size: xxx-large"></i>
            <h2 style="margin-left: 20px">Show</h2>
        </div>

        <div>
            <h2>${sessionScope.selectedShow.name}</h2>
        </div>
    </div>


    <div class="d-flex flex-column" style="align-items: flex-start">
        <div class="mb-3">
            <h2 class="large-text">Cinema : ${sessionScope.selectedCinema.name}</h2>
        </div>
        <div>
            <h2 class="large-text">Saloon : ${sessionScope.selectedShowTime.saloonNumber}</h2>
        </div>
    </div>

    <div class="d-flex flex-column">
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

                <%
                    ShowTimeVo showTimeVo = (ShowTimeVo) session.getAttribute("selectedShowTime");
                    Show show = (Show) session.getAttribute("selectedShow");
                    if (show.getShowType().equals(ShowType.MOVIE)) {
                        if ((showTimeVo.getStartTime().getDayOfWeek() == DayOfWeek.TUESDAY || showTimeVo.getStartTime().getDayOfWeek() == DayOfWeek.SATURDAY)) {
                %>

                <h6>${sessionScope.selectedShow.basePrice / 2}</h6>

                <%
                } else {
                %>

                <h6>${sessionScope.selectedShow.basePrice}</h6>

                <%
                    }
                } else {
                %>

                <h6>${seat.priceRatio*sessionScope.selectedShow.basePrice}</h6>

                <% } %>


            </div>
        </c:forEach>
    </div>

    <button class="btn btn-primary mb-5" type="submit">Submit Selected Seats</button>
</form>


<jsp:include page="/footer.jsp"/>


<script>
    let selectedSeats = [];

    function selectSeat(id) {
        let seatDiv = document.getElementById("seat" + id);

        // Prevent selection of sold or reserved seats
        if (seatDiv.classList.contains('sold-seat') || seatDiv.classList.contains('reserved-seat')) {
            return;  // Don't allow clicking on sold or reserved seats
        }

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
