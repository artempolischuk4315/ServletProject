<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
      integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<html lang="en">

<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:setBundle basename="messages"/>

<c:if test = "${sessionScope.logout==true}">
<label style="color: aliceblue; font-size: 20px"  ><fmt:message key="alert.logout"/></label>
</c:if>
<c:remove var="logout" scope="session"/>
<script>
    function replace_search(name, value) {
        var str = location.search;
        if (new RegExp("[&?]" + name + "([=&].+)?$").test(str)) {
            str = str.replace(new RegExp("(?:[&?])" + name + "[^&]*", "g"), "")
        }
        str += "&";
        str += name + "=" + value;
        str = "?" + str.slice(1);
        location.assign(location.origin + location.pathname + str + location.hash)
    };
</script>

<head>

    <ul class="nav navbar-nav navbar-right">
        <li class="nav-item">
            <a style="color: aliceblue" href="javascript:replace_search('lang', 'en');"><fmt:message key="label.lang.en" /></a>
        </li>
        <li class="nav-item">
            <a style="color: aliceblue" href="javascript:replace_search('lang', 'ua');"><fmt:message key="label.lang.ua" /></a>
        </li>
    </ul>

<style>
    body{ background: url("https://images.pexels.com/photos/583846/pexels-photo-583846.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"); background-size: 100% 100%; }

    span{
        position: absolute;
        top: 4%;
        left: 45%;
        transform: translateX(-50%) translateY(-50%);
    }
    label{
        position: absolute;
        top: 20%;
        left: 14%;
        transform: translateX(-50%) translateY(-50%);
    }
</style>
</head>

<html>
<body>

        <a style=" color: aliceblue; font-family: 'Franklin Gothic Demi Cond'; font-size: 30px  "
                class="btn btn-secondary btn-lg " onclick="window.location.href = '${pageContext.request.contextPath}/login.jsp';">
            <fmt:message key="label.login" /></a>


        <a style="color: aliceblue; font-family: 'Franklin Gothic Demi Cond'; font-size: 30px "
                class="btn btn-secondary btn-lg "
                onclick="window.location.href = '${pageContext.request.contextPath}/registration.jsp';">
            <fmt:message key="label.registration" /></a>
        <span style="color: aliceblue; font-family: 'Franklin Gothic Demi Cond'; font-size: 60px;"><fmt:message key="label.welcome" /></span>

        <h1></h1>
        <h1></h1>
        <h1></h1>
        <h1></h1>
        <h1></h1>







</html>
