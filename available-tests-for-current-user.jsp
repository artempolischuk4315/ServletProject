<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
      integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:setBundle basename="messages"/>



<style>
    body{
        background: url("https://images.pexels.com/photos/317356/pexels-photo-317356.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
    }
    table {
        border-spacing: 0;
        empty-cells: hide;
        margin: auto;
    }
    td {
        padding: 10px 20px;
        text-align: center;
        border-bottom: 1px solid #F4EEE8;
        transition: all 0.5s linear;
    }
    td:first-child {
        text-align: left;
        color: #3D3511;
        font-weight: bold;
    }
    th {
        padding: 10px 20px;
        color: #3D3511;
        border-bottom: 1px solid #F4EEE8;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }
    td:nth-child(even) {
        background: #F6D27E;
    }
    td:nth-child(odd) {
        background: #D1C7BF;
    }
    th:nth-child(even)  {
        background: #F6D27E;
    }
    th:nth-child(odd)  {
        background: #D1C7BF;
    }
    .round-top {
        border-top-left-radius: 5px;
    }
    .round-bottom {
        border-bottom-left-radius: 5px;
    }
    tr:hover td{
        background: #D1C7BF;
        font-weight: bold;
    }
</style>
<html>
<head>

</head>
<body>

<ul style="position: relative; margin: auto" class="nav navbar-nav navbar-right ">

    <li style="background-color: #D1C7BF; width: 50px"><a style="color: black" href="?lang=en"><fmt:message key="label.lang.en" /></a></li>

    <li style="background-color: #D1C7BF; width: 50px" ><a style="color: black" href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>

    <li style="background-color: cadetblue; width: 80px"><a style="color: black" href="${pageContext.request.contextPath}/logout"><fmt:message key="label.logout" /></a></li>

</ul>


<div  class="col-md-6 col-md-offset-3" >
<label style="font-size: 30px"><fmt:message key="label.click" /></label>
    <c:if test = "${sessionScope.noTests==true}">
        <label style="
    color: crimson; font-size: 20px; text-align: center; margin-top: 20px" ><fmt:message key="alert.noTests"/></label>
    </c:if>
    <c:remove var="noTests" scope="session"/>
</div>

<div class="container">
    <div class="row" style="margin-top: 5%">
        <div class="col-md-1"></div>

        <div class="col-md-10 my-auto">
            <table class="table"  id = "table">
                <thead class="thead-dark">
                <tr>
                    <th>#</th>
                    <th><fmt:message key="label.name"/></th>
                    <th><fmt:message key="label.nameUa"/></th>
                    <th><fmt:message key="label.category"/></th>
                    <th><fmt:message key="label.difficulty"/></th>
                    <th><fmt:message key="label.numOfQ"/></th>
                    <th><fmt:message key="label.timeLimit"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:set var="counter" value="1"/>
                <c:forEach items="${availableTests}" var="availableTests">
                    <tr>
                        <th scope="row"><c:out value="${counter}"/></th>
                        <td><c:out value="${availableTests.name}"/></td>
                        <td><c:out value="${availableTests.nameUa}"/></td>
                        <td><c:out value="${availableTests.category}"/></td>
                        <td><c:out value="${availableTests.difficulty}"/></td>
                        <td><c:out value="${availableTests.numberOfQuestions}"/></td>
                        <td><c:out value="${availableTests.timeLimit}"/></td>

                    </tr>
                    <c:set var="counter" value="${counter+1}"/>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-1"></div>
    </div>
</div>

<form method="post" action="${pageContext.request.contextPath}/completeTest" id="form" name="form">
    <div>
        <input type="text" name="name" id="name" hidden><br><br>
    </div>
</form>

<script>

    let table = document.getElementById('table');

    for(let i = 1; i < table.rows.length; i++)
    {
        table.rows[i].onclick = function()
        {
            //rIndex = this.rowIndex;
            document.getElementById("name").value = this.cells[1].innerHTML;
            submitform1();
        };

    }
    function submitform1()
    {
        document.forms["form"].submit();
    }

</script>


</body>