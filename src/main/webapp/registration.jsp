<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
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
</head>
<body>

<form method="post" action="${pageContext.request.contextPath}/registration">

    <h3><fmt:message key="label.input.fName" /></h3>
    <input type="text" name="firstName" pattern="^[a-zA-Z]+$"><br/>
    <h3><fmt:message key="label.input.fNameUa" /></h3>
    <input type="text" name="firstNameUa" pattern="[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ']+"><br/>
    <h3><fmt:message key="label.input.lName" /></h3>
    <input type="text" name="lastName"  pattern="^[a-zA-Z]+$"><br/>
    <h3><fmt:message key="label.input.lNameUa" /></h3>
    <input type="text" name="lastNameUa" pattern="[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ']+"><br/>
    <h3><fmt:message key="label.input.email" /></h3>
    <input type="email" name="email" pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2, 4}$"><br/>
    <h3><fmt:message key="label.input.password" /></h3>
    <input type="password" name="password"><br/><br/>
    <input class="button" type="submit" value=<fmt:message key="label.submit" />>



    <ul class="nav navbar-nav navbar-right">
        <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
        <li><a href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
    </ul>
</form>
</body>
</html>