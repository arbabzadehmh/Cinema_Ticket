<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Payment</title>
</head>
<body>

<form action="payment.do" method="post">

    <label> Select Bank</label>
    <select name="bank" >

        <c:forEach var="bank" items="${sessionScope.banks}" >

            <option>${bank.name}</option>

        </c:forEach>

    </select>

    <input type="text" name="description" placeholder="Description">

    <input type="submit" value="Pay">
    
</form>





</body>
</html>
