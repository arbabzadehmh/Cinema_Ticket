<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Support Editing</title>

    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>

        <div class="content flex-grow-1 ">

            <div class="d-flex align-items-center h-100">

                <div class="bg-secondary p-5 w-25" style="margin-left: 12%">

                    <div class="mb-5">

                        <form id="edit-form">

                            <div class="form-row">
                                <div class="form-group col-md-3">
                                    <label for="id">ID</label>
                                    <input type="number" class="form-control" id="id" name="id" value="${sessionScope.editingSupport.id}" disabled>
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="customer">Customer ID</label>
                                    <input type="number" class="form-control" id="customer" name="Customer ID" value="${sessionScope.editingSupport.customer_id}" disabled>
                                </div>
                                <div class="form-group col-md-8">
                                    <label for="moderator">Customer ID</label>
                                    <input type="number" class="form-control" id="moderator" name="Moderator ID" value="${sessionScope.editingSupport.moderator_id}" disabled>
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="issue_time">Phone Number</label>
                                    <input type="datetime-local" class="form-control" id="issue_time" name="Issue Time" value="${sessionScope.editingSupport.issue_time}" disabled>
                                </div>
                                <div class="form-group col-md-8">
                                    <label for="solved">Email</label>
                                    <input type="checkbox" class="form-control" id="solved" name="Solved" value="${sessionScope.editingSupport.solved}">
                                </div>

                            </div>

                        </form>

                    </div>


                    <div>
                        <button onclick="editingSupport()" class="btn btn-primary w-50">Edit</button>
                        <button onclick="cancelEditingSupport(${sessionScope.editingSupport.id})" class="btn btn-light w-25">Back</button>
                    </div>


                </div>

                <div class="d-inline p-5 w-25 h-100 d-flex flex-column justify-content-between" style="margin-left: 10%">

                    <table id="resultTable" border="1" class="table-light w-100">
                        <thead>
                        <tr>
                            <th>Message</th>

                        </tr>
                        </thead>

                        <tbody>

                        <c:forEach var="message" items="${sessionScope.Support.message}">

                            <tr>
                                <td>${message.text}</td>
                            </tr>

                        </c:forEach>
                        <button onclick="editMessage" class="btn btn-primary w-25 mt-4">New</button>

                        </tbody>

                    </table>

                </div>

            </div>


        </div>

        <jsp:include page="/footer.jsp"/>
    </div>
</div>


<script>

    async function editingSupport() {
        let form = document.getElementById("edit-form");

        // Convert form data to a plain object
        let formDataObj = {};
        new FormData(form).forEach((value, key) => {
            formDataObj[key] = value;
        });

        try {
            // Make the PUT request with JSON data
            const response = await fetch("/Support.do", {
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
                alert("Support updated successfully!");

                window.location.href = '/support.do';

            } else {
                // Handle errors
                const errorData = await response.json();
                console.error("Error:", errorData);
                alert("Error updating Support: " + errorData.message);

                window.location.href = '/support.do';
            }
        } catch (error) {
            console.error("Request failed:", error);
            alert("An error occurred while updating the Support.");
            window.location.href = '/Support.do';
        }

    }


    async function cancelEditingSupport(id) {

        window.location.replace("/support.do?cancel=" + id)
    }

    function editMessage(id) {
        window.location.replace("/message.do?edit=" + id);
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
