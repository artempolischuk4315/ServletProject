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



<ul class="nav navbar-nav navbar-right">
    <li class="nav-item">
    <li><a style="color: black" href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    </li>
    <li class="nav-item">
    <li><a style="color: black" href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
    </li>
</ul>

<style>
    body{ background: url("https://images.pexels.com/photos/317355/pexels-photo-317355.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");background-size: 100% 100%; }
    div{
        margin-top: 1px;
        border-radius: 25px;
        padding: 20px;
        width: 200px;
        height: 150px;
        position: relative;
    }
    button{
        position:relative;
    }

</style>
<body>
<c:if test = "${sessionScope.successRegistr==true}">
    <label style="color: black; font-size: 20px" ><fmt:message key="alert.successRegistr"/></label>
</c:if>
<c:remove var="notValidData" scope="session"/>



        <div  class="col-md-6 col-md-offset-3" >
        <form method="post" action="${pageContext.request.contextPath}/login"  >
            <label style="font-size: 30px; margin: 0 auto;"><fmt:message key="label.input.email" /></label>
            <input type="text" class="form-control" name="email" align="center"><br/>
            <label style="font-size: 30px; margin: 0 auto;"><fmt:message key="label.input.password"/></label>
            <input type="password" class="form-control" name="pass"><br/><br/>
            <button type="submit" class="btn btn-secondary btn-lg " style="display: block; margin: 0 auto;
            background-color: cadetblue"><fmt:message key="label.submit" /></button>
        </form>
        </div>



</body>
</html>