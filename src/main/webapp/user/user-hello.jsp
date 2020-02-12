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


<ul style="position: relative; margin: auto" class="nav navbar-nav navbar-right ">

    <li style="background-color: #D1C7BF; width: 50px"><a style="color: black" href="?lang=en"><fmt:message key="label.lang.en" /></a></li>

    <li style="background-color: #D1C7BF; width: 50px" ><a style="color: black" href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>

    <li style="background-color: cadetblue; width: 80px"><a style="color: black" href="${pageContext.request.contextPath}/logout"><fmt:message key="label.logout" /></a></li>

</ul>

<style>
    body{
        background: url("https://images.pexels.com/photos/590041/pexels-photo-590041.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
    }
    input{
        background-color: cadetblue;
    }
</style>

<html>
<head>



</head>
<body>
<c:if test = "${sessionScope.TestDeleted==true}">
    <label style="color: crimson; background-color: #D1C7BF; font-size: 25px" ><fmt:message key="alert.TestDeleted"/></label>
</c:if>
<c:remove var="TestDeleted" scope="session"/>

<div>
<label style="color: black; background-color: #D1C7BF; width: 200px">${email} </label>
</div>

<div  class="col-md-6 col-md-offset-3" >
<h2 style="color: black"><fmt:message key="label.statsAre" /></h2>
<h1 style="color: black; font-size: 50px;">${stats} </h1>
</div>

<div  class="col-md-6 col-md-offset-3" >
<form method="post" action="${pageContext.request.contextPath}/showAvailableTests">
    <input name="category" value="MATH" hidden >
    <input style="width: 600px" class="btn btn-secondary btn-lg" type="submit" value=<fmt:message key="label.category.math" />>
</form>
    <h1></h1>
<form method="post" action="${pageContext.request.contextPath}/showAvailableTests">
    <input  name="category" value="PROGRAMMING" hidden >
    <input style="width: 600px" class="btn btn-secondary btn-lg" type="submit" value=<fmt:message key="label.category.progr"/> >
</form>
    <h1> </h1>
<form method="post" action="${pageContext.request.contextPath}/showAvailableTests">
    <input name="category" value="HISTORY" hidden >
    <input style="width: 600px" class="btn btn-secondary btn-lg" type="submit" value=<fmt:message key="label.category.history" />>
</form>
    <h1></h1>
<form method="post" action="${pageContext.request.contextPath}/showAvailableTests">
    <input name="category" value="PHYSICS" hidden >
    <input style="width: 600px" class="btn btn-secondary btn-lg" type="submit" value=<fmt:message key="label.category.physics" />>
</form>

    <h1> </h1>
<form method="post" action="${pageContext.request.contextPath}/watchCompletedTests">
    <input style="width: 600px; background-color: #D1C7BF" class="btn btn-secondary btn-lg" type="submit" value=<fmt:message key="label.completedTests" />>
</form>
</div>

</body>
</html>