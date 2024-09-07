<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Panel</title>

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


        <div class="content d-flex flex-column  align-items-center flex-grow-1">

            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Name</th>
                        <th>Family</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Image</th>
                    </tr>
                    </thead>

                    <tbody>

                    <tr>
                        <td hidden="hidden">${sessionScope.customer.id}</td>
                        <td>${sessionScope.customer.name}</td>
                        <td>${sessionScope.customer.family}</td>
                        <td>${sessionScope.customer.phoneNumber}</td>
                        <td>${sessionScope.customer.email}</td>

                        <td>

                            <c:choose>
                                <c:when test="${sessionScope.customer.imageUrl != ''}">
                                    <img src="${sessionScope.customer.imageUrl}" alt="Customer Image" height="80px"
                                         width="80px">
                                </c:when>
                                <c:otherwise>
                                    No Image
                                </c:otherwise>
                            </c:choose>
                        </td>

                    </tr>

                    </tbody>

                </table>

            </div>


            <button onclick="editCustomer(${sessionScope.customer.id})" class="btn btn-primary w-25 mt-4 mb-5">Edit</button>

            <c:choose>
                <c:when test="${sessionScope.selectedShowTime == null}">
                    <a hidden="hidden" class="btn btn-danger w-25 mt-5" href="seatSelect.do">Continue booking</a>
                </c:when>

                <c:otherwise>
                    <a class="btn btn-danger w-25 mt-5" href="seatSelect.do">Continue booking</a>
                </c:otherwise>
            </c:choose>


        </div>


        <jsp:include page="/footer.jsp"/>


    </div>


</div>


<script>


    function editCustomer(id) {
        window.location.replace("/customer.do?edit=" + id);
    }

</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
