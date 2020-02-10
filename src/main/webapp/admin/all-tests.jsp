<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:setBundle basename="messages"/>

<html>
<head>

    <c:if test = "${sessionScope.DisablingError==true}">
        <label class="alert alert-info" ><fmt:message key="alert.DisablingError"/></label>
    </c:if>
    <c:remove var="DisablingError" scope="session"/>
    <c:if test = "${sessionScope.EnablingError==true}">
        <label class="alert alert-info" ><fmt:message key="alert.EnablingError"/></label>
    </c:if>
    <c:remove var="EnablingError" scope="session"/>
</head>
<body>

<form method="post" action="${pageContext.request.contextPath}/disableTest">
    <input type="text" name="testName"><br/>
    <fmt:message key="label.disableTest" var="myMessage1"/>
    <input class="btn btn-secondary btn-lg btn-block" type="submit" value="${myMessage1}">
</form>

<form method="post" action="${pageContext.request.contextPath}/enableTest">
    <input type="text" name="testName"><br/>
    <fmt:message key="label.enableTest" var="myMessage"/>
    <input class="btn btn-secondary btn-lg btn-block" type="submit" value="${myMessage}">
</form>

<a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.onMain" /></a>
<ul class="nav navbar-nav navbar-right">
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
</ul>

<div class="container">
    <div class="row" style="margin-top: 5%">
        <div class="col-md-1"></div>

        <div class="col-md-10 my-auto">
            <table class="table" border="2">
                <thead class="thead-dark">
                <tr>
                    <th>#</th>
                    <th><fmt:message key="label.name"/></th>
                    <th><fmt:message key="label.nameUa"/></th>
                    <th><fmt:message key="label.category"/></th>
                    <th><fmt:message key="label.difficulty"/></th>
                    <th><fmt:message key="label.numOfQ"/></th>
                    <th><fmt:message key="label.timeLimit"/></th>
                    <th><fmt:message key="label.isActive"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:set var="counter" value="1"/>
                <c:forEach items="${allTests}" var="allTests">

                    <tr>
                        <th scope="row"><c:out value="${counter}"/></th>
                        <td><c:out value="${allTests.name}"/></td>
                        <td><c:out value="${allTests.nameUa}"/></td>
                        <td><c:out value="${allTests.category}"/></td>
                        <td><c:out value="${allTests.difficulty}"/></td>
                        <td><c:out value="${allTests.numberOfQuestions}"/></td>
                        <td><c:out value="${allTests.timeLimit}"/></td>
                        <td><c:out value="${allTests.active}"/></td>

                    </tr>
                    <c:set var="counter" value="${counter+1}"/>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-1"></div>
    </div>
</div>

<%--For displaying Previous link except for the 1st page --%>
<nav aria-label="Navigation ">
    <ul class="pagination">
        <c:if test="${sessionScope.currentPage != 1}">
            <form class="my-form-menu" action="${pageContext.request.contextPath}/allTests" method="post">

                <input type="hidden" name="recordsPerPage" value="${recordsPerPage}">
                <input type="hidden" name="currentPage" value="${sessionScope.currentPage-1}">
                    <%--<input class="nav-link" type="submit" value="<fmt:message key="results-menu"/>">--%>
                <input class="nav-link" type="submit" value="<<">
            </form>
        </c:if>

        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${sessionScope.currentPage eq i}">
                    <li class="page-item active"><a class="page-link">
                            ${i} <span class="sr-only">(current)</span></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${sessionScope.currentPage lt noOfPages}">
            <form class="my-form-menu" action="${pageContext.request.contextPath}/allTests" method="post">

                <input type="hidden" name="recordsPerPage" value="${recordsPerPage}">
                <input type="hidden" name="currentPage" value="${sessionScope.currentPage+1}">
                    <%--<input class="nav-link" type="submit" value="<fmt:message key="results-menu"/>">--%>
                <input class="nav-link" type="submit" value=">>">
            </form>
        </c:if>
    </ul>
</nav>


</body>
</html>