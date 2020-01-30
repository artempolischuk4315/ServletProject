<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:setBundle basename="messages"/>

<html>
<head>

    <a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.onMain" /></a>
</head>
<body>


<a href="${pageContext.request.contextPath}/logout"><fmt:message key="label.onMain" /></a>
<ul class="nav navbar-nav navbar-right">
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=ua"><fmt:message key="label.lang.ua" /></a></li>
</ul>

<div class="container">
    <div class="row" style="margin-top: 5%">
        <div class="col-md-1"></div>

        <div class="col-md-10 my-auto">
            <table class="table" border="2" id = "table">
                <thead class="thead-dark">
                <tr>
                    <th>#</th>
                    <th><fmt:message key="label.name"/></th>
                    <th><fmt:message key="label.nameUa"/></th>
                    <th><fmt:message key="label.category"/></th>
                    <th><fmt:message key="label.difficulty"/></th>
                    <th><fmt:message key="label.numOfQ"/></th>
                    <th><fmt:message key="label.timeLimit"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:set var="counter" value="1"/>
                <c:forEach items="${availableTests}" var="availableTests">
                    <tr>
                        <th scope="row"><c:out value="${counter}"/></th>
                        <td><c:out value="${availableTests.name}"/></td>
                        <td><c:out value="${availableTests.nameUa}"/></td>
                        <td><c:out value="${availableTests.category}"/></td>
                        <td><c:out value="${availableTests.difficulty}"/></td>
                        <td><c:out value="${availableTests.numberOfQuestions}"/></td>
                        <td><c:out value="${availableTests.timeLimit}"/></td>

                    </tr>
                    <c:set var="counter" value="${counter+1}"/>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-1"></div>
    </div>
</div>

<form method="post" action="${pageContext.request.contextPath}/completeTest" id="form" name="form">
    <div>
        <input type="text" name="name" id="name" ><br><br>
    </div>
</form>

<script>

    let table = document.getElementById('table');

    for(let i = 1; i < table.rows.length; i++)
    {
        table.rows[i].onclick = function()
        {
            //rIndex = this.rowIndex;
            document.getElementById("name").value = this.cells[1].innerHTML;
            submitform1();
        };

    }
    function submitform1()
    {
        document.forms["form"].submit();
    }

</script>


</body>