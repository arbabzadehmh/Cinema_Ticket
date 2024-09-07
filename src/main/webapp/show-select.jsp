<%@ page import="com.example.cinema_test.model.entity.Attachment" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Select</title>
    <jsp:include page="/css-import.jsp"/>

    <style>
        /* Custom style to fit 5 shows in a row */
        .show-card {
            width: 20%; /* Each show takes 20% of the row (1/5th) */
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


<div class="content d-flex flex-column flex-grow-1 h-100">

    <jsp:include page="/navbar.jsp"/>

    <div class="mt-5 mb-3">
        <div class="item-icon "><i class="fa fa-masks-theater" style="font-size: xxx-large;"></i></div>
        <h1 class="p-4 text-center">Select Show</h1>
    </div>

    <div class="mb-auto justify-content-center d-flex">


        <div class="container">
            <div class="row">
                <c:forEach var="show" items="${sessionScope.allActiveShows}">
                    <div class="show-card mb-5 p-2"> <!-- Each show occupies 1/5th of the row -->
                        <div class="card h-100"> <!-- Card layout for each show -->
                            <c:choose>
                                <c:when test="${not empty show.attachments}">
                                    <img src="${show.attachments.get(0).fileName}" class="card-img-top d-block mx-auto" alt="Show Poster" style="width: 190px; height: 280px">
                                </c:when>
                                <c:otherwise>
                                    <div class="card-img-top d-block mx-auto text-center py-5" style="background-color: #f0f0f0; width: 190px; height: 280px;">No Image</div>
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body">
                                <h5 class="card-title text-center">${show.name}</h5>
                                <div class="text-center">
                                    <button class="btn btn-primary" onclick="selectShow(${show.id})">Select</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

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
