<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: Millosz
  Date: 04.10.2018
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Users </title>
    <link href="<c:url value="/resources/css/style_general.css"/>"
          rel="stylesheet" type="text/css">
</head>
<body>
<div id="wrapper">

    <div id="header">
        <h2>Current employees</h2>
        <div id="login_logout" style="justify-content: right">
            <br>
            <table>
                <!-- see if anyone is already logged in -->
                <tr>
                    <security:authorize access="isAuthenticated()" var="isLoggged">
                        <security:authentication property="principal.username" var="loggedUsername"/>
                <tr><br>Logged as: ${loggedUsername}</tr> <br>
                        <form:form action="${pageContext.request.contextPath}/logout" method="post">
                            <input type="button" value="Logout" class="button"/>
                        </form:form>
                        <input type="button" value="Your account" class="button2"
                               onclick="window.location.href= 'updateLoggedUser'; return false">
                    </security:authorize>
                    <!-- if nobody is logged -->
                    <c:if test="${!isLoggged}">
                        <input type="button" value="Login"
                               onclick="window.location.href='showLoginPage';return false;"
                               class="button">
                    </c:if>
                </tr>
            </table>
        </div>
    </div>


<!-- search the database with a phrase -->
<form:form action="search" method="POST" >
    <table class="other">
        <tr>
            <td>Search customers:</td>
            <td><input type="text" name="searchPhrase"/></td>

            <td><input type="button" value="Search" class="button" /></td>
        </tr>
    </table>

</form:form>


<table>
    <tr>
        <th>Username</th>
        <th>Name</th>
        <th>Email</th>
        <th>Function</th>
        <security:authorize access="hasRole('ADMIN')" var="isAdmin">
            <th>Action</th>
        </security:authorize>

    </tr>

<c:forEach var="tempUser" items="${userList}">
    <c:url var="deleteUser" value="/accounts/deleteUser">
        <c:param name="userId" value="${tempUser.id}"/>
    </c:url>
    <c:url var="updateUser" value="/accounts/updateUser">
        <c:param name="userId" value="${tempUser.id}"/>
    </c:url>

    <c:if test="${!(loggedUsername.equals('admin') && (tempUser.username.equals('admin')))}">
    <tr>
        <td>${tempUser.username}</td>
        <td>${tempUser.firstName} ${tempUser.lastName}</td>
        <td>${tempUser.email}</td>
        <td>${tempUser.authorities_string}</td>
        <c:if test="${isAdmin}">
        <td>
            <a href="${deleteUser}"
               onclick="if(!confirm('Do you really want to delete this user?'))
                   return false">Delete<a href="${updateUser}">|Update</a>
            </a>
        </td>
        </c:if>
    </tr>
    </c:if>
</c:forEach>
</table>


</div>
</body>
</html>
