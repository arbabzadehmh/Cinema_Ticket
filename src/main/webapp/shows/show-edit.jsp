<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Details</title>
</head>
<body>

<h1>Show Details</h1>

<h2>Show: ${sessionScope.editingShow.name}</h2>
<h3>Director: ${sessionScope.editingShow.director}</h3>
<h3>Genre: ${sessionScope.editingShow.genre}</h3>

<c:if test="${not empty sessionScope.editingShow.attachments}">
    <h3>Attachments:</h3>
    <c:forEach var="attachment" items="${sessionScope.editingShow.attachments}">
        <img src="${attachment.fileName}" alt="Attachment Image" style="max-width: 200px;">
    </c:forEach>
</c:if>

</body>
</html>

