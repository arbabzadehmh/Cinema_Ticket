<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bank Editing</title>


    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>

        <div class="content flex-grow-1 ">

            <div class="d-flex align-items-center h-100">

                <div class="bg-secondary h-100 p-5 w-25" style="margin-left: 12%">

                    <div class="mb-5">
                        <form id="edit-form">

                            <div class="form-row">
                                <div class="form-group col-md-3">
                                    <label for="id">ID</label>
                                    <input type="number" class="form-control" id="id" name="id"
                                           value="${sessionScope.editingBank.id}" disabled>
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="name">Name</label>
                                    <input type="text" class="form-control" id="name" name="name" value="${sessionScope.editingBank.name}" disabled>
                                </div>
                                <div class="form-group col-md-8">
                                    <label for="accountNumber">Account Number</label>
                                    <input type="text" class="form-control" id="accountNumber" name="accountNumber" value="${sessionScope.editingBank.accountNumber}" disabled>
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="branchName">Branch Name</label>
                                    <input type="text" class="form-control" id="branchName" name="branchName" value="${sessionScope.editingBank.branchName}">
                                </div>
                                <div class="form-group col-md-8">
                                    <label for="branchCode">Branch Code</label>
                                    <input type="number" class="form-control" id="branchCode" name="branchCode" value="${sessionScope.editingBank.branchCode}">
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-8">
                                        <label for="status">Status</label>
                                        <select name="status" class="form-control" id="status">
                                            <option value="true">active</option>
                                            <option value="false">not active</option>
                                        </select>
                                    </div>
                                </div>

                            </div>

                        </form>
                    </div>

                    <div class="mt-5">
                        <button onclick="editingBank()" class="btn btn-primary w-50">Edit</button>
                        <button onclick="cancelEditingBank(${sessionScope.editingBank.id})"
                                class="btn btn-light w-25">Back
                        </button>
                    </div>

                </div>


            </div>


        </div>

        <jsp:include page="/footer.jsp"/>
    </div>
</div>


<script>

    async function editingBank() {
        let form = document.getElementById("edit-form");

        // Convert form data to a plain object
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
                alert("Bank updated successfully!");

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
            alert("An error occurred while updating the bank.");
            window.location.href = '/bank.do';
        }

    }


    async function cancelEditingBank(id) {

        window.location.replace("/bank.do?cancel=" + id)
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
