<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ticket Editing</title>




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
                                           value="${sessionScope.editingTicket.id}" disabled>
                                </div>
                            </div>





                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="reserved">Status</label>
                                    <select name="reserved" class="form-control" id="reserved">
                                        <option value="true">Reserved</option>
                                        <option value="false">not Reserved</option>
                                    </select>
                                </div>
                            </div>

                        </form>
                    </div>

                    <div class="mt-5">
                        <button onclick="editingTicket()" class="btn btn-primary w-50">Edit</button>
                        <button onclick="cancelEditingTicket(${sessionScope.editingTicket.id})"
                                class="btn btn-light w-25">Back
                        </button>
                    </div>

                </div>

                <div class="d-inline p-3 w-25 h-75 d-flex flex-column justify-content-between" style="margin-left: 10%">
                    <div class="d-flex w-100 mb-5 justify-content-center">
                        <c:choose>
                            <c:when test="${not empty sessionScope.editingTicket.attachments}">
                                <img src="${sessionScope.editingTicket.attachments.get(0).fileName}" alt="QR Code" height="350px" width="350px">
                            </c:when>
                            <c:otherwise>
                                No QR Code
                            </c:otherwise>
                        </c:choose>
                    </div>

                </div>



            </div>


        </div>

        <jsp:include page="/footer.jsp"/>
    </div>
</div>


<script>

    async function editingTicket() {
        let form = document.getElementById("edit-form");

        // Convert form data to a plain object
        let formDataObj = {};
        new FormData(form).forEach((value, key) => {
            formDataObj[key] = value;
        });

        try {
            // Make the PUT request with JSON data
            const response = await fetch("/ticket.do", {
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
                alert("Ticket updated successfully!");

                window.location.href = '/ticket.do';

            } else {
                // Handle errors
                const errorData = await response.json();
                console.error("Error:", errorData);
                alert("Error updating ticket: " + errorData.message);

                window.location.href = '/ticket.do';
            }
        } catch (error) {
            console.error("Request failed:", error);
            alert("An error occurred while updating the ticket.");
            window.location.href = '/ticket.do';
        }

    }


    async function cancelEditingTicket(id) {

        window.location.replace("/ticket.do?cancel=" + id)
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
