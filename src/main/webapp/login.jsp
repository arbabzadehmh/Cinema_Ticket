<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <title>Login</title>
    <link rel="stylesheet" href="assets/css/boot.css">
    <link rel="stylesheet" href="assets/css/UI.css">

</head>
<body style="background-color:#f3f3f3">

<br><br><br>
<div class="container d-flex justify-content-center">
    <div class="register-item col-12 col-md-6 col-lg-6 bg-secondary">
        <div class="header-register text-center">
            <h4>Login</h4>
            <br><br><hr style="border-top: 3px solid rgba(0, 0, 0, 0.1); margin-top: -18px;">
            <p>Enter To Your Panel</p>
        </div><br><br>

        <div class="input-register text-center mx-auto">
            <form action="j_security_check" method="post">
                <input class="mb-2" type="text" style="display: block; width: 80%; margin: 0 auto;" placeholder="Enter Username / Phone Number" name="j_username">
                <input class="mb-4" type="password" style="display: block; width: 80%; margin: 0 auto;" placeholder="Enter password" name="j_password">
                <input type="submit" class="btn btn-outline-dark" style="display: block; width: 80%; margin: 0 auto;" value="Login">
            </form>
        </div>

        <div class="footer-register text-center mx-auto">
            <a href="cinemaHome.do" class="btn-reg">Back</a>
            <a href="user.do" class="btn-reg">Forget Password</a>
            <a href="customer.do" class="btn-reg">Create account</a>
        </div>
    </div>
</div>

<br><br><br><br>

</body>

<script src="assets/js/jquery-3.7.1.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>

</html>
