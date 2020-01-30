<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:setBundle basename="messages"/>

<a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.onMain" /></a>
<ul class="nav navbar-nav navbar-right">
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
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
<body>
<h1 <fmt:message key="label.test.over" /> </h1>
<h1 <fmt:message key="label.onMain" /> </h1>
<form method="post" action="${pageContext.request.contextPath}/user/user-hello.jsp" >
    <div class="button" >
        <input type="submit" class="btn btn-secondary btn-lg " <fmt:message key="label.test.over" /> >
    </div>
</form>
<form action="${pageContext.request.contextPath}/sendMail"  method="post" >
    <div class="button" >
        <input type="submit" class="btn btn-secondary btn-lg " <fmt:message key="label.getResult" /> onclick="this.disabled=true;this.value='Submitting...'; this.form.submit();" >
    </div>
</form>
</body>
</html>