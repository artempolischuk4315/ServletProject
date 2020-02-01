<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:setBundle basename="messages"/>


<html>
<head>
    <title>ADMIN THE BASIS</title>
    <h1><fmt:message key="label.hello.admin" /></h1>
</head>
<body>


<c:if test = "${sessionScope.createdTest==true}">
    <label class="alert alert-info" ><fmt:message key="alert.createdTest"/></label>
</c:if>
<c:remove var="createdTest" scope="session"/>

<c:if test = "${sessionScope.addedTestToAvailable==true}">
    <label class="alert alert-info" ><fmt:message key="alert.addedTestToAvailable"/></label>
</c:if>
<c:remove var="addedTestToAvailable" scope="session"/>

<a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.onMain" /></a>
<ul class="nav navbar-nav navbar-right">
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
</ul>



<form method="post" action="${pageContext.request.contextPath}/allUsersMenu">
    <fmt:message key="label.allUsers" var="myMessage11"/>
    <input class="btn btn-secondary btn-lg btn-block" type="submit" value="${myMessage11}">
</form>
<form method="post" action="${pageContext.request.contextPath}/admin/add-test.jsp">
    <fmt:message key="label.addTest" var="myMessage1"/>
    <input class="btn btn-secondary btn-lg btn-block" type="submit" value="${myMessage1}">
</form>
<form method="post" action="${pageContext.request.contextPath}/allTests">
    <fmt:message key="label.watch" var="myMessage"/>
    <input class="btn btn-secondary btn-lg btn-block" type="submit" value="${myMessage}">
</form>



</body>
</html>