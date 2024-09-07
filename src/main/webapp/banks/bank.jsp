<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bank</title>
    <jsp:include page="../css-import.jsp"/>
</head>
<body>


<%
    String errorMessage = (String) session.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<div class="alert alert-danger">
    <%= errorMessage %>
</div>
<%
        session.removeAttribute("errorMessage");
    }
%>



<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/admins/admin-moderator-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100">

                <div class="p-5">
                    <i class="fa fa-bank mb-3" style="font-size: xxx-large"></i>
                    <h1>Bank</h1>
                </div>

                <div style="margin-left: 20%">
                    <form action="bank.do" method="post">

                        <div class="d-flex mb-4">


                            <input class="m-1 w-100 text-danger-emphasis bg-secondary-subtle" type="text" name="name" placeholder="Name Type to search" oninput="findBankByName(this.value)">


                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="branchName" placeholder="Branch Name">

                            <input class="m-1" type="number" name="branchCode" placeholder="branchCode">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1 text-danger-emphasis bg-secondary-subtle" type="text" name="accountNumber" placeholder="Account Number Type to search" oninput="findBankByAccountNumber(this.value)">

                            <input class="m-1" type="number" name="accountBalance" placeholder="Balance">



                        </div>


                        <div class="d-flex mb-4">

                            <select name="status" class="m-1 w-50" oninput="findBankByStatus(this.value)">
                                <option value="true">active</option>
                                <option value="false">not active</option>
                            </select>

                            <input class="btn btn-dark m-1 w-50" type="submit" value="Save">
                        </div>

                    </form>

                </div>


            </div>


            <div>
                <h4 class="mb-0">Banks</h4>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="allResultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Branch Code</th>
                        <th>Branch Name</th>
                        <th>Balance</th>
                        <th>Account Number</th>
                        <th>Status</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="bank" items="${sessionScope.bankList}">
                        <tr>

                            <td>${bank.id}</td>
                            <td>${bank.name}</td>
                            <td>${bank.branchCode}</td>
                            <td>${bank.branchName}</td>
                            <td>${bank.accountBalance}</td>
                            <td>${bank.accountNumber}</td>
                            <td>${bank.status}</td>

                            <td>
                                <button onclick="editBank(${bank.id})" class="btn btn-primary w-25 mt-4">Edit
                                </button>
                                <button onclick="removeBank(${bank.id})" class="btn btn-danger w-50 mt-4">Remove
                                </button>
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

    function editBank(id) {
        window.location.replace("/bank.do?edit=" +id);
    }

    function removeBank(id) {
        fetch("/rest/bank/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/bank.do";
                } else {
                    // If the response is not successful, read the error message
                    return response.text().then(errorMessage => {
                        // Alert the error message returned from the API
                        alert(errorMessage);
                    });
                }
            })
            .catch(error => {
                // Handle any other errors that may occur
                console.error('Error:', error);
                alert("An error occurred: " + error.message);
            });
    }

    function findBankByName(name) {

        $.ajax({
            url: "/rest/bank/findByName/" + name,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#allResultTable tbody").empty();

                if (typeof response === 'object' && response !== null) {


                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.branchCode + "</td>" +
                        "<td>" + response.branchName + "</td>" +
                        "<td>" + response.accountBalance + "</td>" +
                        "<td>" + response.accountNumber + "</td>" +
                        "<td>" + response.status + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editBank(\"" + response.id + "\")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger w-50' onclick='removeBank(\"" + response.id + "\")'>Remove</button>" + "</td>" +
                    "</tr>";
                    $("#allResultTable tbody").append(row);
                } else {
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#allResultTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }

    function findBankByAccountNumber(accountNumber) {

        $.ajax({
            url: "/rest/bank/findByAccountNumber/" + accountNumber,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#allResultTable tbody").empty();

                if (typeof response === 'object' && response !== null) {


                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.branchCode + "</td>" +
                        "<td>" + response.branchName + "</td>" +
                        "<td>" + response.accountBalance + "</td>" +
                        "<td>" + response.accountNumber + "</td>" +
                        "<td>" + response.status + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editBank(\"" + response.id + "\")'>Edit</button>" + "</td>" +
                    "<td>" + "<button class='btn btn-danger w-50' onclick='removeBank(\"" + response.id + "\")'>Remove</button>" + "</td>" +
                    "</tr>";
                    $("#allResultTable tbody").append(row);
                } else {
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#allResultTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }


    function findBankByStatus(status) {

        $.ajax({
            url: "/rest/bank/findByStatus/" + status,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#allResultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (bank) {

                        var row = "<tr>" +
                            "<td>" + bank.id + "</td>" +
                            "<td>" + bank.name + "</td>" +
                            "<td>" + bank.branchCode + "</td>" +
                            "<td>" + bank.branchName + "</td>" +
                            "<td>" + bank.accountBalance + "</td>" +
                            "<td>" + bank.accountNumber + "</td>" +
                            "<td>" + bank.status + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editBank(\"" + bank.id + "\")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger w-50' onclick='removeBank(\"" + bank.id + "\")'>Remove</button>" + "</td>" +
                        "</tr>";

                        $("#allResultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.branchCode + "</td>" +
                        "<td>" + response.branchName + "</td>" +
                        "<td>" + response.accountBalance + "</td>" +
                        "<td>" + response.accountNumber + "</td>" +
                        "<td>" + response.status + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editBank(\"" + response.id + "\")'>Edit</button>" + "</td>" +
                    "<td>" + "<button class='btn btn-danger w-50' onclick='removeBank(\"" + response.id + "\")'>Remove</button>" + "</td>" +
                    "</tr>";
                    $("#allResultTable tbody").append(row);
                } else {
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#allResultTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }

</script>

<jsp:include page="../js-import.jsp"/>

</body>
</html>
