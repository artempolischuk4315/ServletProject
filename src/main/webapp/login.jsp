<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:setBundle basename="messages"/>

<body>


        <form method="post" action="${pageContext.request.contextPath}/login">

            <input type="text" name="email"><br/>
            <input type="password" name="pass"><br/><br/>
            <input class="button" type="submit" value=<fmt:message key="label.submit" />>

        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.onMain" /></a>


        <ul class="nav navbar-nav navbar-right">
            <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
            <li><a href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
        </ul>
</body>
</html>