<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 15.05.2021
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ticketoffice</title>
    <link rel="stylesheet" href="styles/css/styleAuthorization.css">
    <link rel="icon" type="image/x-icon" href="styles/img/favicon.ico"/>
</head>
<body>
<section class="container">
    <div class="login">
        <h1>Вхід в систему</h1>
        <form method="post" action="/authorization">
            <p><input type="text" name="login" value="" placeholder="Email"></p>
            <p><input type="password" name="password" value="" placeholder="Password"></p>
            <p class="submit"><input type="submit" name="commit" value="Вхід"></p>
        </form>
    </div>
</section>
<c:if test="${requestScope.errorMessage != null}">
    <section class="result-log">
        <div class="login">
            <h1>Повідомлення</h1>
            <p id="error-text">${requestScope.errorMessage}</p>
            <p class="submit"><input type="button" name="ok" onclick="onOK(event)" value="Ок">
        </div>
    </section>
    <script>
        function onOK(event) {
            var element = document.getElementById("result-log");
            element.style.display = "none";
        }
    </script>
</c:if>
</body>
</html>
