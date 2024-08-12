<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Select</title>


</head>
<body>

<h1>Select Show</h1>

<div>

    <table>
        <thead>
        <tr>
            <th hidden="hidden">ID</th>
            <th>Show Name</th>
            <th>Description</th>
            <th>Select</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="show" items="${sessionScope.allActiveShows}">
            <tr>
                <td hidden="hidden">${show.id}</td>
                <td>${show.name}</td>
                <td>${show.description}</td>
                <td>
                    <button onclick="selectShow(${show.id})">Select</button>
                </td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>


<script>

    function selectShow(id){
        window.location.replace("/test2.do?selectShow=" + id);
    }

</script>





</body>
</html>
