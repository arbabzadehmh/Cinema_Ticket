<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Payment</title>

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

    <div class="content d-flex flex-column flex-grow-1 align-items-center justify-content-center">

        <div class="content flex-column h-75 w-50">

            <div class="mb-5">
                <i class="fa fa-money-bill-transfer mb-3" style="font-size: xxx-large"></i>
                <h2>Payment</h2>
            </div>

            <div class="d-flex justify-content-center align-content-center w-100 p-4" style="background-color: #c1c1c0; border-radius: 50px">
                <form action="payment.do" method="post" enctype="multipart/form-data">

                    <div class="d-flex mb-5">

                        <label class="m-1">Total Price</label>

                        <input type="number" class="m-1" id="totalPrice" name="totalPrice"
                               value="${sessionScope.totalPrice}" disabled>

                    </div>

                    <div class="d-flex mb-5">

                        <label class="m-1"> Select Bank</label>
                        <select class="m-1" name="bank">

                            <c:forEach var="bank" items="${sessionScope.banks}">

                                <option>${bank.name}</option>

                            </c:forEach>

                        </select>

                        <input class="m-1" type="text" name="description" placeholder="Description">

                        <input type="file" name="image" class="m-1">

                    </div>

                    <div class="mb-5">
                        <input class="btn btn-dark m-1 w-25" type="submit" value="Pay">
                    </div>

                </form>

            </div>
        </div>


    </div>
</div>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
