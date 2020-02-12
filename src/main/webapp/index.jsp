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


<c:if test = "${sessionScope.alreadyLogged==true}">
<h1 style="color: crimson; font-size: 25px; font-family: 'Palatino Linotype'; text-align: center";  ><fmt:message key="alert.alreadyLogged"/></h1>
</c:if>
<c:remove var="alreadyLogged" scope="session"/>


<c:if test = "${sessionScope.logout==true}">
<label style="color: crimson; font-size: 25px; font-family: 'Palatino Linotype'; text-align: center";  ><fmt:message key="alert.logout"/></label>
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
<ul class="nav navbar-nav navbar-right">
    <li style="background-color: #D1C7BF; width: 50px"><a style="color: black" href="?lang=en"><fmt:message key="label.lang.en" /></a></li>

    <li style="background-color: #D1C7BF; width: 50px" ><a style="color: black" href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
</ul>
<head>



<style>
    body{ background: url("https://images.pexels.com/photos/317356/pexels-photo-317356.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"); background-size: 100% 100%; }

</style>
</head>

<html>
<body>

<ul class="nav navbar-nav navbar-left">
<li style="background-color: mediumaquamarine ; width: 140px; font-size: 30px; font-family: 'Palatino Linotype'; position: relative"><a style="color: black" href="${pageContext.request.contextPath}/login.jsp">
    <fmt:message key="label.login"/></a></li>


<li style="background-color: #D1C7BF; width: 200px; font-size: 30px; font-family: 'Palatino Linotype'; position: relative" ><a style="color: black" href="${pageContext.request.contextPath}/registration.jsp">
    <fmt:message key="label.registration" /></a></li>
</ul>

        <div  class="col-md-6 col-md-offset-3" >
        <h1 style="color: black; text-align: center;   font-family: 'Palatino Linotype'; font-size: 65px "><fmt:message key="label.welcome" /></h1>


            <h2 style="color: black; text-align: center;  margin-top: 50%;  margin-left: 30%; font-family: 'Palatino Linotype'; position: relative"><fmt:message key="label.middleLabel" /> </h2>


        </div>


</html>
