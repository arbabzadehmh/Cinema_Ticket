<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.cinema_test.model.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="side-bar d-sm-none d-md-flex col-md-1 col-lg-2">

    <div class="items d-flex flex-column w-100 pt-5 justify-content-around ">


        <%
            User loggedUser = (User) session.getAttribute("user");
            if (loggedUser.getRole().getRole().equals("admin")) {
        %>

        <div class="d-flex w-100 mb-5 justify-content-center">
            <c:choose>
                <c:when test="${not empty sessionScope.loggedAdmin.attachments}">
                    <img class="rounded-circle bg-white" src="${sessionScope.loggedAdmin.attachments.get(0).fileName}" alt="Admin Image" height="80px" width="80px">
                </c:when>
                <c:otherwise>
                    No Image
                </c:otherwise>
            </c:choose>
        </div>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="admins.do">
            <div class="item-icon w-25"><i class="fa fa-user-circle"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Profile</div>
        </a>

        <%
            } else if (loggedUser.getRole().getRole().equals("moderator")) {
        %>

        <div class="d-flex w-100 mb-5 justify-content-center">
            <c:choose>
                <c:when test="${not empty sessionScope.loggedModerator.attachments}">
                    <img class="rounded-circle bg-white" src="${sessionScope.loggedModerator.attachments.get(0).fileName}" alt="Moderator Image" height="80px" width="80px">
                </c:when>
                <c:otherwise>
                    No Image
                </c:otherwise>
            </c:choose>
        </div>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="moderator.do">
            <div class="item-icon w-25"><i class="fa fa-user-circle"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Profile</div>
        </a>

        <% } %>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="#">
            <div class="item-icon w-25"><i class="fa fa-user-check"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Account</div>
        </a>

        <a class="item d-flex justify-content-center align-items-center mb-auto ps-lg-4" href="cinema.do">
            <div class="item-icon w-25"><i class="fa fa-camera-movie"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Cinema</div>
        </a>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="#">
            <div class="item-icon w-25"><i class="fa fa-sign-out"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Exit</div>
        </a>


    </div>

</div>
