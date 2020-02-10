<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:setBundle basename="messages"/>

<ul class="nav navbar-nav navbar-right">

    <li><a style="color: black" href="?lang=en"><fmt:message key="label.lang.en" /></a></li>

    <li><a style="color: black" href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>

    <li><a style="color: black" href="${pageContext.request.contextPath}/logout"><fmt:message key="label.logout" /></a></li>

</ul>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<!--<link rel="stylesheet" type="text/css" href="style.css" />-->


</head>

<style>
    body{
        background: url("https://images.pexels.com/photos/317356/pexels-photo-317356.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
    }
</style>
<body>

<form method="post" action="${pageContext.request.contextPath}/goOnMain" >
    <fmt:message key="label.onMain" var="m"/>
        <input style="width: 400px" value="${m}" type="submit" class="btn btn-secondary btn-lg " >
</form>

<form action="${pageContext.request.contextPath}/sendMail"  method="post" >
    <fmt:message key="label.getResult" var="m1" />
        <input style="width: 400px" value="${m1}" type="submit" class="btn btn-secondary btn-lg " onclick="this.disabled=true;this.value='Submitting...'; this.form.submit();">

</form>


</body>
</html>