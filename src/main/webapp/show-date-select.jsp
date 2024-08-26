<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Date Select</title>


</head>
<body>

<h1>Select Date</h1>

<h2>Show : ${sessionScope.selectedShow.name}</h2>

<div>

    <table>
        <thead>
        <tr>
            <th>Date</th>
            <th>Select</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="date" items="${sessionScope.showDates}">
            <tr>
                <td>${date}</td>
                <td>
                    <button onclick="selectDate('${date}')">Select</button>
                </td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>


<script>

    function selectDate(date){
        window.location.replace("/cinemaHome.do?selectDate=" + date);
    }

</script>


</body>
</html>
