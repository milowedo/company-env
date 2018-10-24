<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<html>
<head>
<link href="<c:url value="/resources/css/style_form.css"/>"
    rel="stylesheet" type="text/css">
    <title>Updating your account</title>
</head>
<body>

<div id ="container">
    <div id="header" >
        <h1>Updating your account</h1>
    </div>

    <form:form action="createUser" modelAttribute="newUser" method="post">

        <table>
            <tbody>
            <form:hidden path="id"/>
            <tr>
                <td><label>Name: </label></td>
                <td><form:input path="firstName"/></td>
                <td><form:errors path="firstName" cssClass="error"/></td>
            </tr>

            <tr>
                <td><label>Surname: </label></td>
                <td><form:input path="lastName"/></td>
                <td><form:errors path="lastName" cssClass="error"/></td>
            </tr>

            <tr>
                <td><label>Email: </label></td>
                <td><form:input path="email"/></td>
                <td><form:errors path="email" cssClass="error"/></td>
            </tr>

            <form:hidden path="username"/>
            <form:hidden path="authorities_string"/>

            <tr>
                <td><br><br></td>
            </tr>

            <tr>
                <td><label>Confirm: </label></td>
                <td><form:password  path="password"/></td>
                <td><form:errors path="password" cssClass="error"/></td>
                <!-- <td><a href="${pageContext.request.contextPath}/accounts/changePassword">change password</a></td> -->
            </tr>



            <tr>
                <td><label></label></td>
                <td><input content="Submit" type="submit" name="Accept changes"></td>
            </tr>
            </tbody>
        </table>
    </form:form>

    <br>
    <security:authentication property="principal.id" var="userId"/>

    <security:authorize access="!hasRole('ADMIN')">
    <c:url var = "deleteAcc" value="/accounts/deleteUser">
        <c:param name="userId" value="${userId}"/>
    </c:url>
    <a href="${deleteAcc}"
       onclick="if(!confirm('Do you really want to DELETE your account?'))
                   return false">Delete my account</a>
    </security:authorize>

    <br>
    <br>
    <br>
    <br>
    <a href="${pageContext.request.contextPath}/">
        Back to home page
    </a>
</div>

</body>
</html>
