<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:setBundle basename="messages"/>

<body>

<c:if test = "${sessionScope.unSuccessFullCreated==true}">
    <label class="alert alert-info" ><fmt:message key="alert.noSuchTestOrEmail"/></label>
</c:if>
<c:remove var="unSuccessFullCreated" scope="session"/>

<c:if test = "${sessionScope.noSuchTestOrUser==true}">
    <label class="alert alert-info" ><fmt:message key="alert.noSuchTestOrUser"/></label>
</c:if>
<c:remove var="noSuchTestOrUser" scope="session"/>
<form method="post" action="${pageContext.request.contextPath}/allowTest">

    <input type="text" name="email" pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"><br/>
    <input type="text" name="testName" pattern="^[a-zA-Z0-9]+$"><br/><br/>
    <input class="button" type="submit" value=<fmt:message key="label.submit" />>

</form>
<br/>
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
                    <th><fmt:message key="label.fname"/></th>
                    <th><fmt:message key="label.fnameUa"/></th>
                    <th><fmt:message key="label.lname"/></th>
                    <th><fmt:message key="label.lnameUa"/></th>
                    <th><fmt:message key="label.email"/></th>
                    <th><fmt:message key="label.stats"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:set var="counter" value="1"/>
                <c:forEach items="${users}" var="users">
                    <tr>
                        <th scope="row"><c:out value="${counter}"/></th>
                        <td><c:out value="${users.firstName}"/></td>
                        <td><c:out value="${users.firstNameUa}"/></td>
                        <td><c:out value="${users.lastName}"/></td>
                        <td><c:out value="${users.lastNameUa}"/></td>
                        <td><c:out value="${users.email}"/></td>
                        <td><c:out value="${users.stats}"/></td>

                    </tr>
                    <c:set var="counter" value="${counter+1}"/>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-1"></div>
    </div>
</div>

<nav aria-label="Navigation ">
    <ul class="pagination">
        <c:if test="${sessionScope.currentPage != 1}">
            <form class="my-form-menu" action="${pageContext.request.contextPath}/allUsersMenu" method="post">

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
            <form class="my-form-menu" action="${pageContext.request.contextPath}/goOnAllowPage" method="post">

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