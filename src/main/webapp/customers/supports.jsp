<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Supports</title>

    <jsp:include page="../css-import.jsp"/>

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


<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/customers/customer-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <div class="p-5 ">
                <i class="fa fa-user-headset mb-3" style="font-size: xxx-large"></i>
                <h1>My Supports</h1>
                <form action="support.do" method="post">
                    <input class="btn btn-dark mt-5 w-75" type="submit" value="New Support">
                </form>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Issue Time</th>
                        <th>Moderator</th>
                        <th>Solved</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="support" items="${sessionScope.supportList}">

                        <tr>
                            <td hidden="hidden">${support.id}</td>
                            <td>${support.issueTime}</td>
                            <td>${support.moderator.name}-${support.moderator.family}</td>
                            <td>${support.solved}</td>

                            <td>
                                <button onclick="openSupport(${support.id})" class="btn btn-primary w-50 mt-4">Open</button>
                            </td>
                        </tr>

                    </c:forEach>

                    </tbody>

                </table>

            </div>


        </div>


        <jsp:include page="/footer.jsp"/>


    </div>


</div>


<script>


    function openSupport(id) {
        window.location.replace("/support.do?open=" + id);
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
