<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Millosz
  Date: 04.10.2018
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<html>
<head>
<link href="<c:url value="/resources/css/style_form.css"/>"
    rel="stylesheet" type="text/css"/>
    <title>Creating an account</title>
</head>
<body>

<div id ="container">
    <div id="header">
        <h1>Create new user</h1>
    </div>
    <form:form action="createUser" modelAttribute="newUser" method="post">

        <table>
            <tbody>
            <tr>
                <td><label class="formLabel">Name: </label></td>
                <td><form:input path="firstName"/></td>
                <td><form:errors path="firstName" cssClass="error"/></td>
            </tr>

            <tr>
                <td><label class="formLabel">Surname: </label></td>
                <td><form:input path="lastName"/></td>
                <td><form:errors path="lastName" cssClass="error"/></td>
            </tr>

            <tr>
                <td><label class="formLabel">Email: </label></td>
                <td><form:input path="email"/></td>
                <td><form:errors path="email" cssClass="error"/></td>
            </tr>

            <tr>
                <td><label class="formLabel">Username: </label></td>
                <td><form:input path="username"/></td>
                <td><form:errors path="username" cssClass="error"/></td>
            </tr>

            <tr>
                <td><label class="formLabel">Password: </label></td>
                <td><form:password  path="password"/></td>
                <td><form:errors path="password" cssClass="error"/></td>
            </tr>

            <tr>
                <td><label class="formLabel">Authority: </label></td>
                <td><form:radiobuttons items="${existingAuthorities}"
                                        cssClass="radioButtons" path="authorities_string"/></td>
                <td><form:errors path="authorities" cssClass="error"/> </td>
            </tr>
            <tr>
                <td><label></label></td>
                <td><input content="Submit" type="submit" value="Register" name="Register"></td>
            </tr>
            </tbody>
        </table>
    </form:form>

    <br>
    <a href="${pageContext.request.contextPath}/">
        Back to home page
    </a>
</div>

</body>
</html>