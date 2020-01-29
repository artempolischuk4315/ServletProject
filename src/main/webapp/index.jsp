<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">

<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:setBundle basename="messages"/>


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



<html>
<body>


    <ul class="nav navbar-nav navbar-right">
        <li class="nav-item">
            <a href="javascript:replace_search('lang', 'en');"><fmt:message key="label.lang.en" /></a>
        </li>
        <li class="nav-item">
            <a href="javascript:replace_search('lang', 'ua');"><fmt:message key="label.lang.ua" /></a>
        </li>
    </ul>



    <fmt:message key="label.welcome" />


    <br/>
    <a href="${pageContext.request.contextPath}/login.jsp"><fmt:message key="label.login" /></a>
    <br>
    <a href="${pageContext.request.contextPath}/registration.jsp"><fmt:message key="label.registration" /></a>
    <br>


</html>
