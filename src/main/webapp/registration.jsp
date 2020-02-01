<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>

    <c:if test="${not empty param.lang}">
        <fmt:setLocale value="${param.lang}" scope="session"/>
    </c:if>
    <fmt:setBundle basename="messages"/>
    <ul class="nav navbar-nav navbar-right">
        <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
        <li><a href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
    </ul>
</head>
<body>

<form method="post" action="${pageContext.request.contextPath}/registration">
    <c:if test = "${sessionScope.userAlreadyExists==true}">
        <label class="alert alert-info" ><fmt:message key="alert.userAlreadyExists"/></label>
    </c:if>
    <c:remove var="userAlreadyExists" scope="session"/>
    <c:if test = "${sessionScope.notValidData==true}">
        <label class="alert alert-info" ><fmt:message key="alert.invalidData"/></label>
    </c:if>
    <c:remove var="notValidData" scope="session"/>
    <h3><fmt:message key="label.input.fName" /></h3>
    <input type="text" name="firstName"  required><br/>
    <h3><fmt:message key="label.input.fNameUa" /></h3>
    <input type="text" name="firstNameUa" pattern="[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ']+" required><br/>
    <h3><fmt:message key="label.input.lName" /></h3>
    <input type="text" name="lastName"  pattern="^[a-zA-Z]+$" required><br/>
    <h3><fmt:message key="label.input.lNameUa" /></h3>
    <input type="text" name="lastNameUa" pattern="[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ']+" required><br/>
    <h3><fmt:message key="label.input.email" /></h3>
    <input type="email" name="email" pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required><br/>
    <h3><fmt:message key="label.input.password" /></h3>
    <input type="password" name="password" required><br/><br/>
    <input class="button" type="submit" value=<fmt:message key="label.submit" />>




</form>
</body>
</html>