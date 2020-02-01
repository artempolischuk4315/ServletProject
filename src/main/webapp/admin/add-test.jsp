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
<c:if test = "${sessionScope.notCreatedTest==true}">
    <label class="alert alert-info" ><fmt:message key="alert.notCreatedTest"/></label>
</c:if>
<c:remove var="notCreatedTest" scope="session"/>
<form method="post" action="${pageContext.request.contextPath}/createTest">

    <h3><fmt:message key="label.input.testName" /></h3>
    <input type="text" name="name" pattern="^[a-zA-Z0-9]+$" required><br/>
    <h3><fmt:message key="label.input.testNameUa" /></h3>
    <input type="text" name="nameUa" pattern="[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ'0-9]+$" required><br/>
    <fieldset >
        <input type="radio" id="MATH" name="category" value="MATH">
        <label for="MATH" ><fmt:message key="label.category.math" /></label>
        <input type="radio" id="HISTORY" name="category" value="HISTORY">
        <label for="HISTORY" ><fmt:message key="label.category.history" /></label>
        <input type="radio" id="PHYSICS" name="category" value="PHYSICS">
        <label for="PHYSICS" ><fmt:message key="label.category.physics" /></label>
        <input type="radio" id="PROGRAMMING" name="category" value="PROGRAMMING">
        <label for="PROGRAMMING" ><fmt:message key="label.category.progr" /></label>
    </fieldset>
    <h3><fmt:message key="label.input.difficulty" /></h3>
    <input type="number" name="difficulty" pattern="^(?:[1-9]|0[1-9]|10)$" required ><br/>
    <h3><fmt:message key="label.input.numberOfQuestions" /></h3>
    <input type="number" name="numberOfQuestions" pattern="[0-9]{1,2}$" required ><br/>
    <h3><fmt:message key="label.input.timeLimit" /></h3>
    <input type="number" name="timeLimit" pattern="[0-9]{1,3}$" required ><br/>
    <input class="button" type="submit" value=<fmt:message key="label.submit" />>


    <ul class="nav navbar-nav navbar-right">
        <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
        <li><a href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
    </ul>
</form>
</body>
</html>