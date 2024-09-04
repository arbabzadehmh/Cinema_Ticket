<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Editing</title>

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
                                    <input type="number" class="form-control" id="id" name="id" value="${sessionScope.editingShow.id}" disabled>
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="name">Name</label>
                                    <input type="text" class="form-control" id="name" name="name" value="${sessionScope.editingShow.name}" disabled>
                                </div>
                            </div>

                            <div class="form-row">

                                <div class="form-group col-md-8">
                                    <label for="status">Status</label>
                                    <select name="status" class="form-control" id="status">
                                        <option value="true">active</option>
                                        <option value="false">not active</option>
                                    </select>
                                </div>

                                <div class="form-group col-md-8">
                                    <label for="showType">Type</label>
                                    <select name="showType" class="form-control" id="showType">
                                        <option value="MOVIE" ${sessionScope.editingShow.showType == 'MOVIE' ? 'selected' : ''}>Movie</option>
                                        <option value="THEATER" ${sessionScope.editingShow.showType == 'THEATER' ? 'selected' : ''}>Theater</option>
                                        <option value="EVENT" ${sessionScope.editingShow.showType == 'EVENT' ? 'selected' : ''}>Event</option>
                                        <option value="CONCERT" ${sessionScope.editingShow.showType == 'CONCERT' ? 'selected' : ''}>Concert</option>
                                    </select>
                                </div>

                                <div class="form-group col-md-8">
                                    <label for="genre">Genre</label>
                                    <select name="genre" class="form-control" id="genre">
                                        <option value="ACTION" ${sessionScope.editingShow.genre == 'ACTION' ? 'selected' : ''}>ACTION</option>
                                        <option value="HORROR" ${sessionScope.editingShow.genre == 'HORROR' ? 'selected' : ''}>HORROR</option>
                                        <option value="ROMANCE" ${sessionScope.editingShow.genre == 'ROMANCE' ? 'selected' : ''}>ROMANCE</option>
                                        <option value="DRAMA" ${sessionScope.editingShow.genre == 'DRAMA' ? 'selected' : ''}>DRAMA</option>
                                        <option value="WESTERN" ${sessionScope.editingShow.genre == 'WESTERN' ? 'selected' : ''}>WESTERN</option>
                                        <option value="COMEDY" ${sessionScope.editingShow.genre == 'COMEDY' ? 'selected' : ''}>COMEDY</option>
                                        <option value="FANTASY" ${sessionScope.editingShow.genre == 'FANTASY' ? 'selected' : ''}>FANTASY</option>
                                        <option value="CRIME" ${sessionScope.editingShow.genre == 'CRIME' ? 'selected' : ''}>CRIME</option>
                                        <option value="MUSICAL" ${sessionScope.editingShow.genre == 'MUSICAL' ? 'selected' : ''}>MUSICAL</option>
                                        <option value="SHORT" ${sessionScope.editingShow.genre == 'SHORT' ? 'selected' : ''}>SHORT</option>
                                        <option value="STORY" ${sessionScope.editingShow.genre == 'STORY' ? 'selected' : ''}>STORY</option>
                                        <option value="ANIMATION" ${sessionScope.editingShow.genre == 'ANIMATION' ? 'selected' : ''}>ANIMATION</option>
                                        <option value="HISTORY" ${sessionScope.editingShow.genre == 'HISTORY' ? 'selected' : ''}>HISTORY</option>
                                        <option value="SCIENCE" ${sessionScope.editingShow.genre == 'SCIENCE' ? 'selected' : ''}>SCIENCE</option>
                                        <option value="BUSINESS" ${sessionScope.editingShow.genre == 'BUSINESS' ? 'selected' : ''}>BUSINESS</option>
                                        <option value="OTHER" ${sessionScope.editingShow.genre == 'OTHER' ? 'selected' : ''}>OTHER</option>
                                    </select>
                                </div>


                            </div>


                            <div class="form-row">

                                <div class="form-group col-md-8">
                                    <label for="director">Director</label>
                                    <input type="text" class="form-control" id="director" name="director" value="${sessionScope.editingShow.director}">
                                </div>

                                <div class="form-group col-md-8">
                                    <label for="producer">Producer</label>
                                    <input type="text" class="form-control" id="producer" name="producer" value="${sessionScope.editingShow.producer}">
                                </div>

                                <div class="form-group col-md-8">
                                    <label for="singer">Signer</label>
                                    <input type="text" class="form-control" id="singer" name="singer" value="${sessionScope.editingShow.singer}">
                                </div>

                                <div class="form-group col-md-8">
                                    <label for="speaker">Speaker</label>
                                    <input type="text" class="form-control" id="speaker" name="speaker" value="${sessionScope.editingShow.speaker}">
                                </div>

                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="releasedDate">Released Date</label>
                                    <input type="date" class="form-control" id="releasedDate" name="releasedDate" value="${sessionScope.editingShow.releasedDate}">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="basePrice">Base Price</label>
                                    <input type="number" class="form-control" id="basePrice" name="basePrice" value="${sessionScope.editingShow.basePrice}">
                                </div>

                                <div class="form-group col-md-12">
                                    <label for="description">Description</label>
                                    <input type="text" class="form-control" id="description" name="description" value="${sessionScope.editingShow.description}">
                                </div>
                            </div>


                        </form>

                    </div>


                    <div>
                        <button onclick="editingShow()" class="btn btn-primary w-50">Edit</button>
                        <button onclick="cancelEditingShow(${sessionScope.editingShow.id})" class="btn btn-light w-25">Back</button>
                    </div>


                </div>

                <div class="d-inline p-5 w-25 h-100 d-flex flex-column justify-content-between" style="margin-left: 5%">

                    <div class="d-flex w-100 mb-5 bg-body-secondary justify-content-center">
                        <c:choose>
                            <c:when test="${not empty sessionScope.editingShow.attachments}">
                                <img src="${sessionScope.editingShow.attachments.get(0).fileName}" alt="Show Image" height="300px" width="200px">
                            </c:when>
                            <c:otherwise>
                                No Image
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="d-flex justify-content-center mb-5">
                        <form action="show.do" method="post" enctype="multipart/form-data">
                            <input type="file" name="newImage" class="input-group mb-5">
                            <input type="submit" value="Change Image" class="btn btn-dark">
                        </form>
                    </div>

                </div>


                <div class="d-inline p-5 w-25 h-100 d-flex flex-column justify-content-between" style="margin-left: 1%">

                    <div class="d-flex w-100 mb-5 bg-body-secondary justify-content-center">
                        <c:choose>
                            <c:when test="${fn:length(sessionScope.editingShow.attachments) gt 1}">
                                <img src="${sessionScope.editingShow.attachments.get(1).fileName}" alt="Show Image" height="300px" width="200px">
                            </c:when>
                            <c:otherwise>
                                No Image
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="d-flex justify-content-center mb-5">
                        <form action="show.do" method="post" enctype="multipart/form-data">
                            <input type="file" name="newImage" class="input-group mb-5">
                            <input type="submit" value="Change Image" class="btn btn-dark">
                        </form>
                    </div>

                </div>



                <div class="d-inline p-5 w-25 h-100 d-flex flex-column justify-content-between" style="margin-left: 1%">

                    <div class="d-flex w-100 mb-5 bg-body-secondary justify-content-center">
                        <c:choose>
                            <c:when test="${fn:length(sessionScope.editingShow.attachments) gt 2}">
                                <img src="${sessionScope.editingShow.attachments.get(2).fileName}" alt="Show Image" height="300px" width="200px">
                            </c:when>
                            <c:otherwise>
                                No Image
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="d-flex justify-content-center mb-5">
                        <form action="show.do" method="post" enctype="multipart/form-data">
                            <input type="file" name="newImage" class="input-group mb-5">
                            <input type="submit" value="Change Image" class="btn btn-dark">
                        </form>
                    </div>

                </div>



            </div>


        </div>

        <jsp:include page="/footer.jsp"/>
    </div>
</div>


<script>

    async function editingShow() {
        let form = document.getElementById("edit-form");

        // Convert form data to a plain object
        let formDataObj = {};
        new FormData(form).forEach((value, key) => {
            formDataObj[key] = value;
        });

        try {
            // Make the PUT request with JSON data
            const response = await fetch("/show.do", {
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
                alert("Show updated successfully!");

                window.location.href = '/show.do';

            } else {
                // Handle errors
                const errorData = await response.json();
                console.error("Error:", errorData);
                alert("Error updating show: " + errorData.message);

                window.location.href = '/show.do';
            }
        } catch (error) {
            console.error("Request failed:", error);
            alert("An error occurred while updating the show.");
            window.location.href = '/show.do';
        }

    }


    async function cancelEditingShow(id) {

        window.location.replace("/show.do?cancel=" + id)
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
