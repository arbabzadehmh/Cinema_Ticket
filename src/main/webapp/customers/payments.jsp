<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Payments</title>

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
                <i class="fa fa-money-bill-transfer mb-3" style="font-size: xxx-large"></i>
                <h1>My Payments</h1>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Price</th>
                        <th>Payment Time</th>
                        <th>Bank</th>
                        <th>Account Number</th>
                        <th>Tickets</th>
                        <th>Description</th>
                        <th>Image</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="payment" items="${sessionScope.paymentList}">

                        <tr>
                            <td>${payment.id}</td>
                            <td>${payment.price}</td>
                            <td>${payment.paymentDateTime}</td>
                            <td>${payment.bankName}</td>
                            <td>${payment.accountNumber}</td>
                            <td>${payment.ticketCounts}</td>
                            <td>${payment.description}</td>

                            <td>

                                <c:choose>
                                    <c:when test="${payment.imageUrl != ''}">
                                        <img src="${payment.imageUrl}" alt="Payment Image" height="80px" width="80px">
                                    </c:when>
                                    <c:otherwise>
                                        No Image
                                    </c:otherwise>
                                </c:choose>

                            </td>



                            <td>
                                <button onclick="editPayment(${payment.id})" class="btn btn-primary w-50 mt-4">Edit</button>
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


    function editPayment(id) {
        window.location.replace("/payment.do?edit=" + id);
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
