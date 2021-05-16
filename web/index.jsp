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
    <link rel="stylesheet" href="views/stylesIndex.css"/>
    <link rel="stylesheet" href="styles/css/style.css">
    <link rel="stylesheet" href="styles/css/styleAd.css">
</head>
<body>
<div class="wrapper">
    <section class="container">
        <nav>
            <ul class="nav">
                <li title="home" <c:if test="${requestScope.home == true}">class="active"</c:if>><a href="index.jsp"
                                                                                                    class="nav-icon"
                                                                                                    title="home"><span
                        class="icon-home">Home</span></a></li>
                <li title="vehicle" <c:if test="${requestScope.vehicle == true}">class="active"</c:if>><a
                        href="/work_with_db?act=Show&table=vehicle" title="vehicle">Транспортні засоби</a></li>
                <li title="journary" <c:if test="${requestScope.journary == true}">class="active"</c:if>><a
                        href="/work_with_db?act=Show&table=journary" title="journary">Рейси</a></li>
                <li title="ticket" <c:if test="${requestScope.ticket == true}">class="active"</c:if>><a
                        href="/work_with_db?act=Show&table=ticket" title="ticket">Квитки</a></li>
                <li title="receipt" <c:if test="${requestScope.receipt == true}">class="active"</c:if>><a
                        href="/work_with_db?act=Show&table=receipt" title="receipt">Чеки</a></li>
                <li title="passenger" <c:if test="${requestScope.passenger == true}">class="active"</c:if>><a
                        href="/work_with_db?act=Show&table=passenger" title="passenger">Пасажири</a></li>
                <li><a href="index.jsp" title="Contact">Працівники</a></li>
                <li><a href="index.jsp" title="Contact">Статистика</a></li>
            </ul>
        </nav>
    </section>
    <main>
        <c:if test="${requestScope.columnsName != null}">
            <input type="button" value="Додати" id="add">
        </c:if>
        <script>
            function onAdd(event) {
                var element = document.getElementsByClassName("containerAdd")[0];
                element.style.display = "block";
            }

            var buttonAdd = document.getElementById("add");
            buttonAdd.onclick = onAdd;

        </script>
        <table>
            <thead>
            <tr>
                <c:forEach var="name" items="${requestScope.columnsName}">
                    <th>${name}</th>
                </c:forEach>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="entity" items="${requestScope.entities}">
                <tr>
                    <c:set var="noEdit" scope="session" value="${true}"/>
                    <c:forEach var="column" items="${entity.recieveEntityInfo()}">
                        <c:if test="${noEdit == false}">
                            <td contenteditable="true">${column}</td>
                        </c:if>
                        <c:if test="${noEdit == true}">
                            <c:set var="noEdit" scope="session" value="${false}"/>
                            <td>${column}</td>
                        </c:if>
                    </c:forEach>
                    <td><input type="button" class="delete" value="Видалити"/></td>
                    <td><input type="button" class="edit" value="Редагувати"/></td>
                </tr>
            </c:forEach>
            </tbody>
            <script>
                function onDelete({target: el}) {
                    var el2 = el.parentNode;
                    var el3 = el2.parentElement;
                    let formData = new FormData();
                    formData.set('entityString', el3.children[0].textContent);
                    var table = document.getElementsByClassName("active")[0];
                    formData.append('table', table.getAttribute("title"));
                    formData.append('act', 'Delete');
                    var req = new XMLHttpRequest();
                    req.open("POST", "http://localhost:8082/work_with_db", true);
                    req.send(formData);
                }

                function onEdit({target: el}) {
                    var el2 = el.parentNode;
                    var el3 = el2.parentElement;
                    var entityString = "";
                    for (var i = 0; i < el3.children.length; i++) {
                        entityString += el3.children[i].textContent;
                        if (i + 1 != el3.children.length) entityString += ",";
                    }
                    console.log(entityString);
                    let formData = new FormData();
                    formData.append('entityString', entityString);
                    var table = document.getElementsByClassName("active")[0];
                    formData.append('table', table.getAttribute("title"));
                    formData.append('act', 'Update');
                    var req = new XMLHttpRequest();
                    req.open("POST", "http://localhost:8082/work_with_db");
                    req.send(formData);
                }

                var deleteElems = document.getElementsByClassName("delete");
                var editElems = document.getElementsByClassName("edit");
                for (var i = 0; i < deleteElems.length; i++) {
                    deleteElems[i].onclick = onDelete;
                    editElems[i].onclick = onEdit;
                }
            </script>
        </table>
        <%--  <form method="post" target="_blank">--%>
        <%--    <p>--%>
        <%--    <select id="table" name="table">--%>
        <%--      <option selected value="vehicle">vehicle</option>--%>
        <%--      <option value="journary">journary</option>--%>
        <%--      <option value="ticket">ticket</option>--%>
        <%--      <option value="receipt">receipt</option>--%>
        <%--      <option value="passenger">passenger</option>--%>
        <%--    </select>--%>
        <%--    <div>--%>
        <%--      <button name="act" value="Show" type="submit" formaction="/work_with_db">Show table</button>--%>
        <%--      <button name="act" value="Add" type="submit" formaction="/redirect">Add</button>--%>
        <%--      <button name="act" value="Delete" type="submit" formaction="/redirect">Delete</button>--%>
        <%--      <button name="act" value="Update" type="submit" formaction="/redirect">Update</button>--%>
        <%--    </div>--%>
        <%--  </form>--%>
    </main>
</div>

<section class="containerAdd">
    <div class="login">
        <h1>Login to Web App</h1>
        <form method="post" action="index.html">
            <p><input type="text" name="login" value="" placeholder="Username or Email"></p>
            <p><input type="datetime-local" name="login" value="" placeholder="Date"></p>
            <p><input type="password" name="password" value="" placeholder="Password"></p>
            <p class="submit"><input type="submit" name="commit" value="Додати"></p>
        </form>
    </div>
</section>
</body>
</html>
