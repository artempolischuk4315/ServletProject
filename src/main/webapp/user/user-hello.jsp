<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:setBundle basename="messages"/>


<html>
<head>


</head>
<body>
<c:if test = "${sessionScope.TestDeleted==true}">
    <label class="alert alert-info" ><fmt:message key="alert.TestDeleted"/></label>
</c:if>
<c:remove var="TestDeleted" scope="session"/>
<h1><fmt:message key="label.hello.user" /></h1>
<h2>${email} </h2>
<h2>${stats} </h2>
<a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.onMain" /></a>
<ul class="nav navbar-nav navbar-right">
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
</ul>
<form method="post" action="${pageContext.request.contextPath}/showAvailableTests">
    <input name="category" value="MATH" hidden >
    <input class="button" type="submit" value=<fmt:message key="label.category.math" />>
</form>
<form method="post" action="${pageContext.request.contextPath}/showAvailableTests">
    <input name="category" value="PROGRAMMING" hidden >
    <input class="button" type="submit" value=<fmt:message key="label.category.progr"/> >
</form>
<form method="post" action="${pageContext.request.contextPath}/showAvailableTests">
    <input name="category" value="HISTORY" hidden >
    <input class="button" type="submit" value=<fmt:message key="label.category.history" />>
</form>
<form method="post" action="${pageContext.request.contextPath}/showAvailableTests">
    <input name="category" value="PHYSICS" hidden >
    <input class="button" type="submit" value=<fmt:message key="label.category.physics" />>
</form>

<form method="post" action="${pageContext.request.contextPath}/watchCompletedTests">
    <input class="button" type="submit" value=<fmt:message key="label.completedTests" />>
</form>

</body>
</html>