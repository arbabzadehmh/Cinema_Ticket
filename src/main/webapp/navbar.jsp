<%@ page import="com.example.cinema_test.model.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="nav d-sm-none d-md-flex justify-content-center">

    <%
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null) {
    %>
    <!-- Display the username if logged in -->
    <div class="item d-flex justify-content-center align-items-center m-auto ">
        <div class="item-icon w-25"><i class="fa fa-user"></i></div>
        <div class="w-50 d-sm-none d-lg-flex m-auto text-light">User:<%= loggedUser.getUsername() %></div>
    </div>
    <% } else { %>
    <!-- Display the login link if not logged in -->
    <a class="item d-flex justify-content-center align-items-center m-auto " href="postLogin.do">
        <div class="item-icon w-25"><i class="fa fa-sign-in"></i></div>
        <div class="w-50 d-sm-none d-lg-flex m-auto">Log_in</div>
    </a>
    <% } %>

    <a class="item d-flex justify-content-center align-items-center m-auto " href="#">
        <div class="item-icon w-25"><i class="fa fa-search"></i></div>
        <div class="w-50 d-sm-none d-lg-flex m-auto">Search</div>
    </a>

    <a class="item d-flex justify-content-center align-items-center m-auto " href="#">
        <div class="item-icon w-25"><i class="fa fa-home"></i></div>
        <div class="w-50 d-sm-none d-lg-flex m-auto">Home</div>
    </a>

</div>


<button class="d-sm-flex d-md-none">Menu</button>
