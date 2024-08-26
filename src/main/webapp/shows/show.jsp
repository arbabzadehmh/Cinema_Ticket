<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cinema</title>

    <link rel="stylesheet" href="../assets/css/index.css">
    <link rel="stylesheet" href="../assets/css/all.css">
    <link rel="stylesheet" href="../assets/css/UI.css">
    <link rel="stylesheet" href="../assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/fonts.min.css">
    <link rel="stylesheet" href="../assets/css/animate.min.css">
    <link rel="stylesheet" href="../assets/css/fontawesome/all.css">


    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/admins/admin-moderator-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content flex-column justify-content-center  align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100">

                <div class="p-5">
                    <i class="fa fa-theater-masks mb-3" style="font-size: xxx-large"></i>
                    <h1>Show</h1>
                </div>

                <div style="margin-left: 5%">
                    <form action="show.do" method="post" enctype="multipart/form-data">

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="name" placeholder="Name type to search">

                            <select name="showType" class="m-1">
                                <option value="MOVIE">Movie</option>
                                <option value="THEATER">Theater</option>
                                <option value="EVENT">Event</option>
                                <option value="CONCERT">Concert</option>
                            </select>

                            <select name="status" class="m-1">
                                <option value="true">active</option>
                                <option value="false">not active</option>
                            </select>


                            <select name="genre" class="m-1">
                                <option value="ACTION">ACTION</option>
                                <option value="HORROR">HORROR</option>
                                <option value="ROMANCE">ROMANCE</option>
                                <option value="DRAMA">DRAMA</option>
                                <option value="WESTERN">WESTERN</option>
                                <option value="COMEDY">COMEDY</option>
                                <option value="FANTASY">FANTASY</option>
                                <option value="CRIME">CRIME</option>
                                <option value="MUSICAL">MUSICAL</option>
                                <option value="SHORT">SHORT</option>
                                <option value="STORY">STORY</option>
                                <option value="ANIMATION">ANIMATION</option>
                                <option value="HISTORY">HISTORY</option>
                                <option value="SCIENCE">SCIENCE</option>
                                <option value="BUSINESS">BUSINESS</option>
                                <option value="OTHER">OTHER</option>
                            </select>

                            <input class="m-1 w-100" type="number" name="basePrice" placeholder="Base Price">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="producer" placeholder="Producer">

                            <input class="m-1" type="text" name="singer" placeholder="Singer">

                            <input class="m-1" type="text" name="speaker" placeholder="Speaker">

                            <input class="m-1" type="text" name="director" placeholder="Director">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="date" name="releasedDate" placeholder="ReleasedDate">

                            <input type="file" name="image" class="m-1">

                        </div>

                        <div class="d-flex mb-4">
                            <input class="m-1 w-75" type="text" name="description" placeholder="Description">
                            <input class="btn btn-dark m-1 w-25" type="submit" value="Save">
                        </div>

                    </form>

                </div>


            </div>

            <div>
                <h4 class="mb-0 mt-3">All Shows</h4>
            </div>


            <div class="d-flex justify-content-center p-4 w-100">

                <table id="allResultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Name</th>
                        <th>Genre</th>
                        <th>Director</th>
                        <th>Producer</th>
                        <th>Singer</th>
                        <th>Speaker</th>
                        <th>Released Date</th>
                        <th>Base Price</th>
                        <th>Type</th>
                        <th>Available</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="show" items="${sessionScope.allShows}">

                        <tr>
                            <td hidden="hidden">${show.id}</td>
                            <td>${show.name}</td>
                            <td>${show.genre}</td>
                            <td>${show.director}</td>
                            <td>${show.producer}</td>
                            <td>${show.singer}</td>
                            <td>${show.speaker}</td>
                            <td>${show.releasedDate}</td>
                            <td>${show.basePrice}</td>
                            <td>${show.showType}</td>
                            <td>${show.available}</td>
                            <td>${show.status}</td>
                            <td>${show.description}</td>
                            <td>
                                <div class="d-flex">
                                    <button onclick="editShow(${show.id})" class="btn btn-primary w-50">Edit</button>
                                    <button onclick="removeShow(${show.id})" class="btn btn-danger w-50">Remove</button>
                                </div>

                            </td>

                        </tr>


                    </c:forEach>


                    </tbody>

                </table>

            </div>


        </div>


        <jsp:include page="/footer.jsp"/>


    </div>


</div>


<script>

    function editShow(id) {
        window.location.replace("/show.do?edit=" + id);
    }

    function removeShow(id){
        fetch("/rest/show/" + id, {
            method: "DELETE"
        }).then(() => {
            window.location = "/show.do"
        })
    }

</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
