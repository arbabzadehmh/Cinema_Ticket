<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ticket Printing</title>


    <jsp:include page="../css-import.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <div class="mt-5 d-flex flex-row flex-grow-1 justify-content-center">


        <div class="d-flex flex-column w-75 h-100 " style="border: 2px solid #4e555b;">

            <div class="m-3 d-flex justify-content-between">
                <div>
                    <h2>Cinema Ticket</h2>
                </div>

                <div class="mt-2">
                    <h6>Ticket Number : ${sessionScope.printingTicket.id}</h6>
                </div>
            </div>

            <div class="h-100 m-3  d-flex justify-content-between p-3" style="background-color: #e6dbb9; border: 2px solid #4e555b;">

                <div>
                    <h2 class="mb-4 text-left">
                        Show : ${sessionScope.printingTicket.showName}
                    </h2>

                    <div class="d-flex">
                        <div class="item-icon m-1 "><i class="fa fa-calendar" style="font-size: large; margin-right: 5px;"></i></div>
                        <h5 class="mb-3">
                            ${sessionScope.printingTicket.showDate}
                        </h5>
                    </div>

                    <div class="d-flex">
                        <div class="item-icon m-1 "><i class="fa fa-clock" style="font-size: large; margin-right: 5px;"></i></div>
                        <h5 class="mb-3">
                            ${sessionScope.printingTicket.startHour} - ${sessionScope.printingTicket.endHour}
                        </h5>
                    </div>
                </div>

                <div>
                    <h2 class="mb-4 text-left">
                        Cinema : ${sessionScope.printingTicket.cinemaName}
                    </h2>

                    <div class="d-flex">
                        <div class="item-icon m-1 "><i class="fa fa-camera-movie" style="font-size: large; margin-right: 5px;"></i></div>
                        <h5 class="mb-3">
                            Saloon Number : ${sessionScope.printingTicket.saloonNumber}
                        </h5>
                    </div>

                    <div class="d-flex">
                        <div class="item-icon m-1 "><i class="fa fa-chair-office" style="font-size: large; margin-right: 5px;"></i></div>
                        <h5 class="mb-3">
                            Seat : ${sessionScope.printingTicket.seatLabel}
                        </h5>
                    </div>
                </div>

                <div>
                    <c:choose>
                        <c:when test="${sessionScope.printingTicket.imageUrl != ''}">
                            <img src="${sessionScope.printingTicket.imageUrl}" alt="Show Image" height="225px" width="150px">
                        </c:when>
                        <c:otherwise>
                            No Image For This Show
                        </c:otherwise>
                    </c:choose>
                </div>




            </div>



            <div class="h-100 m-3  d-flex justify-content-between" >

                <div class="d-flex flex-column w-75 align-items-start p-3">

                    <div class="mb-3 text-left d-flex">
                        <div>
                            <div class="item-icon m-1 "><i class="fa fa-user" style="font-size: large; margin-right: 5px;"></i></div>
                        </div>
                        <div>
                            <h5>
                                ${sessionScope.printingTicket.customerName}
                            </h5>
                        </div>
                        <div style="margin-left: 8%">
                            <h5>
                                ${sessionScope.printingTicket.customerFamily}
                            </h5>
                        </div>
                        <div class="d-flex" style="margin-left: 45%">
                            <div class="item-icon m-1 "><i class="fa fa-mobile-android-alt" style="font-size: large; margin-right: 5px;"></i></div>
                            <h5>
                                ${sessionScope.printingTicket.customerPhoneNumber}
                            </h5>
                        </div>
                    </div>

                    <div class="mb-5 text-left d-flex w-100 ">
                        <div>
                            <div class="item-icon m-1 "><i class="fa fa-money-bill-1" style="font-size: large; margin-right: 5px;"></i></div>
                        </div>
                        <div>
                            <h5>
                                Price:${sessionScope.printingTicket.price}
                            </h5>
                        </div>
                        <div class="d-flex" style="margin-left: 18%" >
                            <div class="item-icon m-1 "><i class="fa fa-timer" style="font-size: large; margin-right: 5px;"></i></div>
                            <h5>
                                Issue Time:${sessionScope.printingTicket.issueTime}
                            </h5>
                        </div>
                    </div>

                    <div class="d-flex">
                        <div class="item-icon m-1 "><i class="fa fa-map-marker-alt" style="font-size: large; margin-right: 5px;"></i></div>
                        <h5 class="mb-4 text-left">
                            Address: ${sessionScope.printingTicket.address}
                        </h5>
                    </div>

                    <div class="d-flex">
                        <div class="item-icon m-1 "><i class="fa fa-sticky-note" style="font-size: large; margin-right: 5px;"></i></div>
                        <h5 class="text-left">
                            Description: ${sessionScope.printingTicket.description}
                        </h5>
                    </div>
                </div>




                <div>

                    <c:choose>
                        <c:when test="${sessionScope.printingTicket.qrCode != ''}">
                            <img src="${sessionScope.printingTicket.qrCode}" alt="QR Code" height="200px" width="200px">
                        </c:when>
                        <c:otherwise>
                            No QR Code
                        </c:otherwise>
                    </c:choose>

                </div>

            </div>


        </div>


    </div>


</div>

<a class="btn btn-secondary w-25 mt-5" href="ticket.do">Back</a>


<script>


    async function cancelEditingTicket(id) {

        window.location.replace("/ticket.do?cancel=" + id)
    }


</script>


<jsp:include page="../js-import.jsp"/>

</body>
</html>
