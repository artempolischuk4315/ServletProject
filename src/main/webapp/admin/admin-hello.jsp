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

</head>
<body>

<h1><fmt:message key="label.hello.admin" /></h1>
<a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.onMain" /></a>
<ul class="nav navbar-nav navbar-right">
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
</ul>

<form method="post" action="${pageContext.request.contextPath}/allUsersMenu">
    <input class="button" type="submit" value=<fmt:message key="label.allUsers" />>
</form>
<form method="post" action="${pageContext.request.contextPath}/admin/add-test.jsp">
    <input class="button" type="submit" value=<fmt:message key="label.addTest" />>
</form>
<form method="post" action="${pageContext.request.contextPath}/allTests">
    <input class="button" type="submit" value=<fmt:message key="label.watch" />>
</form>



</body>
</html>