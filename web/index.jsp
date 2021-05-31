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
    <link rel="stylesheet" href="styles/css/styleAuthorization.css.css">
    <link rel="icon" type="image/x-icon" href="styles/img/favicon.ico" />
</head>
<body>
<section class="containerSignIN">
    <div class="add">
        <h1>Вхід в систему</h1>
        <form method="post" action="/work_with_db?act=Home">
            <p><input type="text" name="login" value="" placeholder="Username or Email"></p>
            <p><input type="password" name="password" value="" placeholder="Password"></p>
            <p class="submit"><input type="submit" name="commit" value="Вхід"></p>
        </form>
    </div>
</section>
</body>
</html>
