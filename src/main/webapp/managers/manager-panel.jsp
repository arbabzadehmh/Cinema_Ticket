<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager</title>


</head>
<body>

<div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Saloon Number</th>
            <th>Capacity</th>
            <th>Status</th>
            <th>Description</th>
            <th>Operation</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="saloon" items="${sessionScope.saloons}">
            <tr>
                <td>${saloon.id}</td>
                <td>${saloon.saloonNumber}</td>
                <td>${saloon.capacity}</td>
                <td>${saloon.status}</td>
                <td>${saloon.description}</td>
                <td>
                        <%--                    <a href="product-group-editing.jsp">Edit</a>--%>
                        <%--                    <button onclick="deleteProductGroup(${productGroup.id})">Remove</button>--%>
                </td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>




<div style="margin-top: 100px">

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Genre</th>
            <th>Director</th>
            <th>Producer</th>
            <th>Signer</th>
            <th>Speaker</th>
            <th>Released Date</th>
            <th>Base Price</th>
            <th>Show Type</th>
            <th>Status</th>
            <th>Description</th>
            <th>Operation</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="show" items="${sessionScope.shows}">
            <tr>
                <td>${show.id}</td>
                <td>${show.name}</td>
                <td>${show.genre}</td>
                <td>${show.director}</td>
                <td>${show.producer}</td>
                <td>${show.singer}</td>
                <td>${show.speaker}</td>
                <td>${show.releasedDate}</td>
                <td>${show.basePrice}</td>
                <td>${show.showType}</td>
                <td>${show.status}</td>
                <td>${show.description}</td>
                <td>
                        <%--                    <a href="product-group-editing.jsp">Edit</a>--%>
                        <%--                    <button onclick="deleteProductGroup(${productGroup.id})">Remove</button>--%>
                </td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>





<div style="margin-top: 100px">

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Saloon</th>
            <th>Show</th>
            <th>Remaining Capacity</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Status</th>
            <th>Description</th>
            <th>Operation</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="showTime" items="${sessionScope.showTimes}">
            <tr>
                <td>${showTime.id}</td>
                <td>${showTime.saloon.saloonNumber}</td>
                <td>${showTime.show.name}</td>
                <td>${showTime.remainingCapacity}</td>
                <td>${showTime.startTime}</td>
                <td>${showTime.endTime}</td>
                <td>${showTime.status}</td>
                <td>${showTime.description}</td>
                <td>
                        <%--                    <a href="product-group-editing.jsp">Edit</a>--%>
                        <%--                    <button onclick="deleteProductGroup(${productGroup.id})">Remove</button>--%>
                </td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>


</body>
</html>
