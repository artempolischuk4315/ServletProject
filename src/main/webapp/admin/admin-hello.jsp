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

    <li style=" width: 50px"><a  href="?lang=en"><fmt:message key="label.lang.en" /></a></li>

    <li style=" width: 50px" ><a  href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>

    <li style=" width: 80px"><a style="color: black" href="${pageContext.request.contextPath}/logout"><fmt:message key="label.logout" /></a></li>

</ul>
<html>
<head>
    <title>ADMIN THE BASIS</title>

</head>
<body>
<div class="col-md-6 col-md-offset-3" >
<h1><fmt:message key="label.hello.admin" /></h1>
</div>

<c:if test = "${sessionScope.createdTest==true}">
    <label class="alert alert-info" ><fmt:message key="alert.createdTest"/></label>
</c:if>
<c:remove var="createdTest" scope="session"/>

<c:if test = "${sessionScope.addedTestToAvailable==true}">
    <label class="alert alert-info" ><fmt:message key="alert.addedTestToAvailable"/></label>
</c:if>
<c:remove var="addedTestToAvailable" scope="session"/>





<div class="col-md-6 col-md-offset-3" >
<form method="post" action="${pageContext.request.contextPath}/allUsersMenu">
    <fmt:message key="label.allUsers" var="myMessage11"/>
    <input style="
    width: 600px" class="btn btn-secondary btn-lg " type="submit" value="${myMessage11}">
</form>
<form method="post" action="${pageContext.request.contextPath}/admin/add-test.jsp">
    <fmt:message key="label.addTest" var="myMessage1"/>
    <input style="
    width: 600px" class="btn btn-secondary btn-lg " type="submit" value="${myMessage1}">
</form>
<form method="post" action="${pageContext.request.contextPath}/allTests">
    <fmt:message key="label.watch" var="myMessage"/>
    <input style="
    width: 600px" class="btn btn-secondary btn-lg " type="submit" value="${myMessage}">
</form>
</div>


</body>
</html>