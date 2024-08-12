<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cinema Select</title>


</head>
<body>

<h1>Select Cinema</h1>

<h2>Show : ${sessionScope.selectedShow.name}</h2>
<h2>Date : ${sessionScope.selectedDate}</h2>


<div>

    <table>
        <thead>
        <tr>
            <th hidden="hidden">id</th>
            <th>Name</th>
            <th>Address</th>
            <th>Select</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="cinema" items="${sessionScope.cinemas}">
            <tr>
                <td hidden="hidden">${cinema.id}</td>
                <td>${cinema.name}</td>
                <td>${cinema.address}</td>
                <td>
                    <button onclick="selectCinema(${cinema.id})">Select</button>
                </td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>


<script>

    function selectCinema(id){
        window.location.replace("/test2.do?selectCinema=" + id);
    }

</script>


</body>
</html>
