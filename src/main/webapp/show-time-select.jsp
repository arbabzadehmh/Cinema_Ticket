<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Time Select</title>


</head>
<body>

<h1>Select Show Time</h1>
<h2>Show : ${sessionScope.selectedShow.name}</h2>
<h2>Date : ${sessionScope.selectedDate}</h2>
<h2>Cinema : ${sessionScope.selectedCinemaName}</h2>

<div>

    <table>
        <thead>
        <tr>
            <th hidden="hidden">ID</th>
            <th>Saloon</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Remaining Capacity</th>
            <th>Description</th>
            <th>Select</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="showTime" items="${sessionScope.showTimes}">
            <tr>
                <td hidden="hidden">${showTime.id}</td>
                <td>${showTime.saloonNumber}</td>
                <td>${showTime.startTime.toLocalTime()}</td>
                <td>${showTime.endTime.toLocalTime()}</td>
                <td>${showTime.remainingCapacity}</td>
                <td>${showTime.description}</td>
                <td>
                    <button onclick="selectShowTime(${showTime.id})">Select</button>
                </td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>


<script>

    function selectShowTime(id){
        window.location.replace("/test3.do?selectShowTimeId=" + id);
    }

</script>





</body>
</html>
