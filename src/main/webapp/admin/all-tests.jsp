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


<ul style="position: relative; margin: auto" class="nav navbar-nav navbar-right ">

    <li style=" width: 50px"><a  href="?lang=en"><fmt:message key="label.lang.en" /></a></li>

    <li style=" width: 50px" ><a  href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>

    <li style=" width: 80px"><a style="color: black" href="${pageContext.request.contextPath}/logout"><fmt:message key="label.logout" /></a></li>

</ul>

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

<div class="col-md-7 col-md-offset-4" style="margin-top: 20px">
<form method="post" action="${pageContext.request.contextPath}/disableTest">
    <input style="width: 300px" type="text" name="testName"><br/>
    <fmt:message key="label.disableTest" var="myMessage1"/>
    <input style="width: 300px" class="btn btn-secondary btn-lg btn-block" type="submit" value="${myMessage1}">
</form>

<form method="post" action="${pageContext.request.contextPath}/enableTest">
    <input style="width: 300px" type="text" name="testName"><br/>
    <fmt:message key="label.enableTest" var="myMessage"/>
    <input style="width: 300px" class="btn btn-secondary btn-lg " type="submit" value="${myMessage}">
</form>
</div>

<label> </label>

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