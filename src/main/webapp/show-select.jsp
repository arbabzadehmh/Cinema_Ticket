<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Select</title>

    <jsp:include page="/css-import.jsp"/>

</head>
<body>



<%--<a href="postLogin.do">Login</a>--%>

<div class="content d-flex flex-column flex-grow-1 h-100">

    <jsp:include page="/navbar.jsp"/>


    <h1 class="p-4 text-center">Select Show</h1>

    <div class="mb-auto justify-content-center d-flex">

        <table class="table table-bordered">
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


        <jsp:include page="/footer.jsp"/>


</div>


<script>

    function selectShow(id){
        window.location.replace("/cinemaHome.do?selectShow=" + id);
    }

</script>



<jsp:include page="/js-import.jsp"/>

</body>
</html>
