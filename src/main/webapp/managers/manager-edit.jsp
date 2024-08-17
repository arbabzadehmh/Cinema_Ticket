<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager Editing</title>

    <jsp:include page="../css-import.jsp"/>

</head>
<body>

    <div class="container-fluid d-flex flex-row vh-100 p-0">


        <div class="content d-flex flex-column flex-grow-1">

            <jsp:include page="/navbar.jsp"/>


            <div class="content flex-grow-1 ">

                <div class="d-inline-flex bg-secondary p-5 w-25" style="margin-left: 12%">

                    <form id="edit-form" >
                        <label>ID</label>
                        <input type="number" name="id" value="${sessionScope.editingManager.id}" disabled class="d-block mb-4" >

                        <label class="d-block">Name</label>
                        <input type="text" name="name" value="${sessionScope.editingManager.name}" class="d-block mb-2">

                        <label class="d-block">Family</label>
                        <input type="text" name="family" value="${sessionScope.editingManager.family}" class="d-block mb-4">



                        <label class="d-block">Phone</label>
                        <input type="text" name="phoneNumber" value="${sessionScope.editingManager.phoneNumber}" class="d-block mb-2">

                        <label class="d-block">Email</label>
                        <input type="text" name="email" value="${sessionScope.editingManager.email}" class="d-block mb-2">

                        <label class="d-block">National Code</label>
                        <input type="text" name="nationalCode" value="${sessionScope.editingManager.nationalCode}" class="d-block mb-4">

                        <label class="d-block">Address</label>
                        <input type="text" name="address" value="${sessionScope.editingManager.address}" class="d-block mb-4">

<%--                        <label class="d-block">Username</label>--%>
<%--                        <input type="text" name="username" value="${sessionScope.editingManager.user.username}" class="d-block mb-2">--%>
<%--                        <label class="d-block">Password</label>--%>
<%--                        <input type="password" name="user.password" value="${sessionScope.editingManager.user.password}" class="d-block">--%>

                    </form>

                </div>



                <div class="d-inline p-3" style="margin-left: 15%">

                    <button onclick="editManager()" class="btn btn-primary">Edit</button>
                    <button class="btn btn-secondary">Back</button>

                </div>




            </div>



            <jsp:include page="/footer.jsp"/>



        </div>



    </div>


    <script>

        // function editManager(id) {
        //     // window.location.replace("/manager.do?edit=" + id);
        // }


        // async function editManager() {
        //     let form = document.getElementById("edit-form");
        //
        //     const response = await fetch("/manager.do", {
        //         method: "PUT",
        //         body: JSON.stringify(new FormData(form)),
        //         // body:JSON.stringify({id:1, name:"alisan",family:"alipour", phoneNumber:"09178505323",email:"messbah.a@gmail.com"}),
        //         headers: {
        //             "Content-Type": "application/json"
        //         }
        //     });
        //
        //     const data = await response.json();
        //
        //     // let person = response.json();
        //     // person.phoneNumber
        // }











        // asli
        // async function editManager() {
        //     let form = document.getElementById("edit-form");
        //
        //     // Convert form data to a plain object
        //     let formDataObj = {};
        //     new FormData(form).forEach((value, key) => {
        //         if (key.startsWith("user.")) {
        //             // Handle fields that belong to the user entity (e.g., user.username)
        //             formDataObj['user'] = formDataObj['user'] || {};
        //             formDataObj['user'][key.replace("user.", "")] = value;
        //         } else {
        //             // Other fields directly for the Manager entity
        //             formDataObj[key] = value;
        //         }
        //     });
        //
        //     try {
        //         // Make the PUT request with JSON data
        //         const response = await fetch("/manager.do", {
        //             method: "PUT",
        //             headers: {
        //                 "Content-Type": "application/json"
        //             },
        //             body: JSON.stringify(formDataObj)  // Send the form data as JSON
        //         });
        //
        //         // Check if the request was successful
        //         if (response.ok) {
        //             const data = await response.json(); // Parse the JSON response
        //             console.log("Success:", data);
        //             alert("Manager updated successfully!");
        //         } else {
        //             // Handle errors
        //             const errorData = await response.json();
        //             console.error("Error:", errorData);
        //             alert("Error updating manager: " + errorData.message);
        //         }
        //     } catch (error) {
        //         console.error("Request failed:", error);
        //         alert("An error occurred while updating the manager.");
        //     }
        // }






        async function editManager() {
            let form = document.getElementById("edit-form");

            // Convert form data to a plain object
            let formDataObj = {};
            new FormData(form).forEach((value, key) => {
                formDataObj[key] = value;
            });

            try {
                // Make the PUT request with JSON data
                const response = await fetch("/manager.do", {
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
                    alert("Manager updated successfully!");
                } else {
                    // Handle errors
                    const errorData = await response.json();
                    console.error("Error:", errorData);
                    alert("Error updating manager: " + errorData.message);
                }
            } catch (error) {
                console.error("Request failed:", error);
                alert("An error occurred while updating the manager.");
            }
        }



    </script>



<jsp:include page="../js-import.jsp"/>

</body>
</html>
