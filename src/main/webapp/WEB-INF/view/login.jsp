<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link href="<c:url value="/resources/css/style_general.css"/>"
          rel="stylesheet" type="text/css">
    <title>Logging in</title>
</head>
<body>

<div id ="container">
    <div id="header">
        <h2>Log in</h2>
    </div>
    <form:form action="${pageContext.request.contextPath}/authenticateUser" method="post">

        <div>
            <c:if test= "${param.logout != null}">
                <div class="error">
                    You have been logged out.
                </div>
            </c:if>
        </div>

        <br> <br>
        <div id="credentials">

        <!-- Username -->
        <input class="input" type="text" name="username" placeholder="username">

        <br>

        <!-- Password -->
        <input class="input" type="password" name="password" placeholder="password">

        <div>
            <c:if test="${param.error != null}">
                <div class="error">
                    Invalid username or password.
                </div>
            </c:if>
        </div>
        </div>

        <br>
        <!-- Login/Submit Button -->
        <button class="button" type="submit">Login</button>
    </form:form>

    <table class="smallerTable">
        <tr>
            <th>Don't have an account?</th>
            <th>
                <input type="button" value="Register"
                       onclick="window.location.href='register';return false;"
                       class="button2">
            </th>

        </tr>
    </table>

    <br>
    <a href="${pageContext.request.contextPath}/">
        Back to home page
    </a>
</div>

</body>
</html>
