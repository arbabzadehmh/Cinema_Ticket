<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bank</title>
    <link rel="stylesheet" href="assets/css/bank.css">
</head>
<body>

<form action="bank.do" method="post">
    <label>
        <input type="text" name="name" placeholder="name">
    </label>

    <label>
        <input type="text" name="branchCode" placeholder="branchCode">
    </label>

    <label>
        <input type="text" name="branchName" placeholder="branchName">
    </label>

    <label>
        <input type="text" name="accountBalance" placeholder="accountBalance">
    </label>

    <label>
        <input type="text" name="accountNumber" placeholder="accountNumber">
    </label>

    <label>
        <input type="text" name="status" placeholder="status" hidden="hidden">
    </label>
    <input type="submit" value="save">
</form>

<table>
    <thead>
    <tr>

        <th>name</th>
        <th>branchCode</th>
        <th>branchName</th>
        <th>accountBalance</th>
        <th>accountNumber</th>
        <th>status</th>
        <th>action</th>

    </tr>
    </thead>

    <tbody>
    <c:forEach var="bank" items="${sessionScope.bankList}">
    <tr>

        <td hidden="hidden">${bank.id}</td>
        <td>${bank.name}</td>
        <td>${bank.branchCode}</td>
        <td>${bank.branchName}</td>
        <td>${bank.accountBalance}</td>
        <td>${bank.accountNumber}</td>
        <td>${bank.status}</td>

        <td>
            <button onclick="editBank()" class="btn btn-primary w-50">Edit</button>
            <button>Remove</button>
        </td>

    </tr>
    </c:forEach>
    </tbody>

</table>

<script>
    async function editBank() {
        let form = document.getElementById("edit-form")

        let formDataObj = {};
        new FormData(form).forEach((value, key) => {
            formDataObj[key] = value;
        });

        try {
            // Make the PUT request with JSON data
            const response = await fetch("/bank.do", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formDataObj)  // Send the form data as JSON
            });

            // Check if the request was successful
            if (response.ok) {
                const data = await response.json(); // Parse the JSON response
                console.log("Success:", data);

                // Display success feedback to the user
                alert("bank updated successfully!");

                window.location.href = '/bank.do';

            } else {
                // Handle errors
                const errorData = await response.json();
                console.error("Error:", errorData);
                alert("Error updating bank: " + errorData.message);

                window.location.href = '/bank.do';
            }
        } catch (error) {
            console.error("Request failed:", error);
            alert("An error occurred while updating the manager.");
            window.location.href = '/manager.do';
        }
    }

</script>

</body>
</html>
