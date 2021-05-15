<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 03.12.2020
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${buttonValue} ${title}</title>
    <link rel="stylesheet" href="views/stylesAdd.css"/>
</head>
<body>
<h2>Table: ${title}</h2>
<p>${pattern1}</p>
<form>
    <input type="text" id="input1" name="entityString"/>
    <button name="act" value=${buttonValue} type="submit" formmethod="post"
            formaction="/work_with_db">${buttonValue}</button>
    <label for="input2">Table: </label>
    <input type="text" id="input2" name="table" value=${title} />
</form>
<a href="/index.jsp">BACK TO MAIN PAGE</a>
</body>
</html>
