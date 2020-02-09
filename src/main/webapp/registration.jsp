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

<head>

    <c:if test="${not empty param.lang}">
        <fmt:setLocale value="${param.lang}" scope="session"/>
    </c:if>
    <fmt:setBundle basename="messages"/>
    <ul class="nav navbar-nav navbar-right">
        <li class="nav-item">
        <li><a style="color: black" href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
        </li>
        <li class="nav-item">
        <li><a style="color: black" href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
        </li>
    </ul>
</head>
<body>

<style>
    body{ background: url("https://images.pexels.com/photos/317355/pexels-photo-317355.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");background-size: 100% 100%; }
    div{
        border-radius: 25px;
        padding: 20px;
        width: 180px;
        height: 130px;
        position: relative;
    }
    button{
        position:relative;
    }

    label{
        position: absolute;
        top: 5%;
        left: 50%;
        transform: translateX(-50%) translateY(-50%);
    }
</style>

<form method="post" action="${pageContext.request.contextPath}/registration">
    <c:if test = "${sessionScope.userAlreadyExists==true}">
        <label style="color: crimson; margin-top: 0px; font-size: 20px" ><fmt:message key="alert.userAlreadyExists"/></label>
    </c:if>
    <c:remove var="userAlreadyExists" scope="session"/>
    <c:if test = "${sessionScope.notValidData==true}">
        <label style="color: black; font-size: 20px" ><fmt:message key="alert.invalidData"/></label>
    </c:if>
    <c:remove var="notValidData" scope="session"/>



    <div class="col-md-6 col-md-offset-3">
    <h3><fmt:message key="label.input.fName" /></h3>
    <input type="text" class="form-control" name="firstName"  required><br/>
    <h3><fmt:message key="label.input.fNameUa" /></h3>
    <input type="text" class="form-control" name="firstNameUa" pattern="[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ']+" required><br/>
    <h3><fmt:message key="label.input.lName" /></h3>
    <input type="text" class="form-control" name="lastName"  pattern="^[a-zA-Z]+$" required><br/>
    <h3><fmt:message key="label.input.lNameUa" /></h3>
    <input type="text" class="form-control" name="lastNameUa" pattern="[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ']+" required><br/>
    <h3><fmt:message key="label.input.email" /></h3>
    <input type="email" class="form-control" name="email" pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required><br/>
    <h3><fmt:message key="label.input.password" /></h3>
    <input type="password" class="form-control" name="password" required><br/><br/>
        <button type="submit" class="btn btn-secondary btn-lg " style="display: block; margin: 0 auto;
            background-color: cadetblue"><fmt:message key="label.submit" /></button>
    </div>



</form>
</body>
</html>