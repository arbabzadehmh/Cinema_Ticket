<%@ page import="com.example.cinema_test.model.entity.Support" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Messages</title>

    <jsp:include page="../css-import.jsp"/>

    <style>

        .customer {
            width: 30%;
            margin-left: 60%;
            border: 1px solid #ccc;
            background-color: #e6dbb9;
        }

        .moderator {
            width: 30%;
            margin-left: 1%;
            border: 1px solid #ccc;
            background-color: #8fd19e;
        }

    </style>

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

    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>

        <div class="content flex-grow-1 ">

            <div class="d-flex flex-row p-5 mt-5">

                <div style="margin-left: 8%; background-color: #8fd19e">

                    <div class="d-flex w-100 mb-5 justify-content-center">
                        <c:choose>
                            <c:when test="${not empty sessionScope.openingSupport.moderator.attachments}">
                                <img class="rounded-circle bg-white" src="${sessionScope.openingSupport.moderator.attachments.get(0).fileName}" alt="Moderator Image" height="80px" width="80px">
                            </c:when>
                            <c:otherwise>
                                No Image
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <h4>${sessionScope.openingSupport.moderator.name} ${sessionScope.openingSupport.moderator.family}</h4>
                </div>

                <div style="margin-left: 45%; background-color: #e6dbb9">

                    <c:choose>
                        <c:when test="${not empty sessionScope.openingSupport.customer.attachments}">
                            <img class="rounded-circle bg-white" src="${sessionScope.openingSupport.customer.attachments.get(0).fileName}" alt="Moderator Image" height="80px" width="80px">
                        </c:when>
                        <c:otherwise>
                            No Image
                        </c:otherwise>
                    </c:choose>

                    <h4>${sessionScope.openingSupport.customer.family}-${sessionScope.openingSupport.customer.phoneNumber}</h4>
                </div>

            </div>

            <div class="d-flex align-items-center h-100">


                <div class=" p-5 w-100">


                    <c:forEach var="message" items="${sessionScope.sortedMessageList}">


                        <div class="customer ${message.sender != 'customer' ? 'moderator' : ''} p-3">


                            <c:choose>
                                <c:when test="${not empty message.attachments}">
                                    <img src="${message.attachments.get(0).fileName}"
                                         class="card-img-top d-block mx-auto" alt="Show Poster"
                                         style="width: 190px; height: 280px">
                                </c:when>
                            </c:choose>

                            <p style="white-space: pre-line; text-align: left; font-size: large">${message.text}</p>
                            <h6 style="font-size: x-small">${message.sendTime}</h6>
                        </div>


                    </c:forEach>

                </div>
            </div>
        </div>


        <div style="margin-left: 5%; margin-top: 15%">

            <%
                Support support = (Support) request.getSession().getAttribute("openingSupport");
                if (!support.isSolved()) {
            %>


            <div class="d-flex flex-row w-100">
                <div class="w-75">
                    <form action="message.do" method="post" enctype="multipart/form-data">
                        <div class="d-flex mb-4">

                            <input class="m-1 w-100" type="text" name="text" placeholder="Type Here">

                            <input type="file" name="image" class="m-1">

                            <input class="btn btn-dark m-1 w-25" type="submit" value="Send">

                        </div>
                    </form>
                </div>

                <div>
                    <button onclick="solve(${sessionScope.openingSupport.id})" class="btn btn-danger w-100 m-1">End of
                        conversation
                    </button>

                </div>
            </div>

            <%
            } else {
            %>

            <div class="bg-info w-100">
                <h3>This conversation is over</h3>
            </div>

            <% } %>


        </div>

        <jsp:include page="/footer.jsp"/>


    </div>
</div>


<script>


    function solve(id) {
        window.location.replace("/support.do?solve=" + id);
    }

</script>

</body>
</html>
