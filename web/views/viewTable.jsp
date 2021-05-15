<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 01.12.2020
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show ${title}</title>
    <link rel="stylesheet" href="views/stylesViewTable.css"/>
</head>
<body>
<h2>Table: ${title}</h2>
<table>
    <tr>
        <c:forEach var="name" items="${requestScope.columnsName}">
            <th>${name}</th>
        </c:forEach>
    </tr>
    <c:forEach var="entity" items="${requestScope.entities}">
        <tr>
            <c:forEach var="column" items="${entity.recieveEntityInfo()}">
                <td>${column}</td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
    <a href="/index.jsp">BACK TO MAIN PAGE</a>
</body>
</html>
