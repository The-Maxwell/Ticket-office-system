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
                <li title="statistics" <c:if test="${requestScope.statistics == true}">class="active"</c:if>><a
                        href="/work_with_db?act=Statistics" title="statistics">Статистика</a></li>
            </ul>
        </nav>
    </section>
    <main>

        <c:if test="${requestScope.columnsName != null}">

            <c:if test="${requestScope.vehicle == true}">

            <div class="search_vehicle">
                <form action="" method="post">
                    Пошук транспорту за його типом:
                    <select  name="selType">
                        <option value="bus" >Автобус</option>
                        <option value="train">Потяг</option>
                        <option value="airplane">Літак</option>
                    </select>
                    <input type="button" value="Пошук" id="search1">
                </form>
                    <%--</div> --%>

                </c:if>

            <c:if test="${requestScope.journary == true}">
                <div class="search_journary">
                    <form action="" method="post">
                    Пошук рейсу за місцем відправки:
                    <input type="text" name="departurePoint1" value="" placeholder="Місце відправки">
                    та датою відправлення:
                    <input type="datetime-local" name="dateAndTimeOfArrival1" value="" placeholder="Дата і час відправки">
                    <input type="button" value="Пошук" id="search2">
                    </form>
                        <%--</div> --%>
            </c:if>

            <c:if test="${requestScope.ticket == true}">

                    <div class="search_ticket">
                        <form action="" method="post">
                            Пошук по категорії квитка:
                            <select name="selCategory">
                                <option value="econom">Економ</option>
                                <option value="medium">Бізнес</option>
                                <option value="luxe">Люкс</option>

                            </select>
                            <input type="button" value="Пошук" id="search3">
                        </form>
                            <%--</div> --%>
            </c:if>

            <c:if test="${requestScope.receipt == true}">
                        <div class="search_receipt">
                            <form action="" method="post">
                            Пошук чеків за пасажиром:
                            <input type="number" name="pessenger" value="" placeholder="Номер пасажира">
                            <input type="button" value="Пошук" id="search4">
                            </form>
                                <%--</div> --%>

            </c:if>

            <c:if test="${requestScope.passenger == true}">
                            <div class="search_passenger">
                                    <form action="" method="post">
                                        Пошук пасажирів за пільгами:
                                        <select name="selCat">

                                            <option value="Дитина до 4 років" >Дитина до 4 років</option>
                                            <option value="Школяр" >Школяр</option>
                                            <option value="Студент" >Студент</option>
                                            <option value="Без пільг" >Без пільг</option>
                                            <option value="Пенсіонер" >Пенсіонер</option>
                                            <option value="Людина з інвалідністю" >Людина з інвалідністю</option>

                                        </select>
                                        <input type="button" value="Пошук" id="search5">
                                    </form>

                                    <%--</div> --%>

            </c:if>


            <input type="button" value="Додати" id="add">

            <c:set var="t" scope="session" value="${requestScope.table}"/>
            <script>
                function onAddButton(event) {
                    <%
                    out.println("var table = '" + request.getParameter("table") + "';");
                    %>
                    var element = document.getElementsByClassName("containerAdd" + table.substr(0, 1) + table.substr(1, table.length - 1))[0];
                    console.log(table.substr(0, 1) + table.substr(1, table.length - 1));
                    element.style.display = "block";
                    var elBlur = document.getElementsByClassName("wrapper")[0];
                    elBlur.style.filter = "blur(2px)";
                }

                var buttonAdd = document.getElementById("add");
                buttonAdd.onclick = onAddButton;
            </script>
            <table>
                <thead>
                <tr>
                    <c:forEach var="colName" items="${requestScope.columnsName}">
                        <th>${colName}</th>
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
                        req.open("POST", "http://localhost:8082/work_with_db");
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
        </c:if>

        <c:if test="${requestScope.statistics == true}">
            <div class="reports">
                <div class="flex-item">
                    <form>
                        <a href="/statistics?report=VehicheReport" target="_blank" title="Натисніть, щоб переглянути звіт по транспортним засобам"><img src="styles/img/train.svg" alt="Vehicle Report"></a>
                        <p class="submit statistics"><input type="button" value="Відправити через Email" id="VehicheReport" onclick="onOpenSendForm(event)"></p>
                        <p class="submit statistics"><input type="button" value="Згенерувати" onclick="onGenerate(event)" id="generateVehicleReport"></p>
                    </form>
                    <script>
                        function onGenerate(event) {
                            event.preventDefault();
                            console.log("onGenerate");
                            var el = event.target;
                            console.log(el.getAttribute("id"));
                            let form = new FormData();
                            form.append('act', 'Generate');
                            form.append('generateReport', el.getAttribute("id"));
                            var xhr = new XMLHttpRequest();
                            xhr.open("POST", "/statistics", true);
                            xhr.send(form);
                        }
                        function onOpenSendForm(event) {
                            var element = document.getElementsByClassName("containerSendEmail")[0];
                            element.style.display = "block";
                            var target = event.target;
                            var el = document.getElementById("sendReport");
                            el.value = target.id;
                            console.log(el.value);
                            var elBlur = document.getElementsByClassName("wrapper")[0];
                            elBlur.style.filter = "blur(2px)";
                        }
                    </script>
                </div>
                <div class="flex-item">
                    <form action="post">
                        <a href="/statistics?report=VehicheJournaryTicketReport" target="_blank" title="Натисніть, щоб переглянути звіт по трансп. засобам, рейсам та квиткам"><img src="styles/img/tickets.svg" alt="VehicleJournaryTicket Report"></a>
                        <p class="submit statistics"><input type="button" value="Відправити через Email" id="VehicheJournaryTicketReport" onclick="onOpenSendForm(event)"></p>
                        <p class="submit statistics"><input type="button" value="Згенерувати" onclick="onGenerate(event)" id="generateVehicheJournaryTicketReport"></p>
                    </form>
                </div>
                <div class="flex-item">
                    <form action="post">
                        <a href="/statistics?report=CategoryReport" target="_blank" title="Натисніть, щоб переглянути звіт по категоріям пасажирів"><img src="styles/img/diagram.svg" alt="Category Report"></a>
                        <p class="submit statistics"><input type="button" value="Відправити через Email" id="CategoryReport" onclick="onOpenSendForm(event)"></p>
                        <p class="submit statistics"><input type="button" value="Згенерувати" onclick="onGenerate(event)" id="generateCategoryReport"></p>
                    </form>
                </div>
            </div>
        </c:if>
    </main>
</div>
<section class="containerAddVehicle">
    <div class="add">
        <h1>Додавання нового транспортного засобу</h1>
        <form>
            <p><input type="number" name="vehicleCode" value="" placeholder="Код транспортного засобу"></p>
            <p><select id="vehicleType" name="vehicleType">
                <option disabled selected>Тип</option>
                <option value="bus" selected>Автобус</option>
                <option value="train">Потяг</option>
                <option value="airplane">Літак</option>
            </select></p>
            <p><input type="number" name="numberOfSeats" value="" placeholder="К-сть місць"></p>
            <p><input type="number" name="numberOfEconomyClassSeats" value="" placeholder="К-сть економ. місць"></p>
            <p><input type="number" name="numberOfMediumClassSeats" value="" placeholder="К-сть серед. місць"></p>
            <p><input type="number" name="numberOfLuxuryClassSeats" value="" placeholder="К-сть люкс. місць"></p>
            <p><input type="text" name="vechileCompany" value="" placeholder="Транспортна компанія"></p>
            <p class="submit"><input type="button" name="add" onclick="onAdd(event)" value="Додати"><input type="reset"
                                                                                                           name="reset"
                                                                                                           onclick="onReset(event)"
                                                                                                           value="Відмінити">
            </p>
        </form>
        <script>
            function onAdd(event) {
                let form = new FormData(event.target.parentNode.parentNode);
                var table = document.getElementsByClassName("active")[0];
                form.append('table', table.getAttribute("title"));
                form.append('act', 'Add');
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "/work_with_db", true);
                xhr.send(form);
            }

            function onReset(event) {
                <%
                    out.println("var table = '" + request.getParameter("table") + "';");
                %>
                var element = document.getElementsByClassName("containerAdd" + table.substr(0, 1) + table.substr(1, table.length - 1))[0];
                console.log(table.substr(0, 1) + table.substr(1, table.length - 1));
                element.style.display = "none";
                var elBlur = document.getElementsByClassName("wrapper")[0];
                elBlur.style.filter = "blur(0px)";
            }
        </script>
    </div>
</section>
<section class="containerAddJournary">
    <div class="add">
        <h1>Додавання нового рейсу</h1>
        <form method="post" action="/work_with_db">
            <p><input type="text" name="departurePoint" value="" placeholder="Місце відправки"></p>
            <p><input type="text" name="destination" value="" placeholder="Місце прибуття"></p>
            <p><input type="datetime-local" name="dateAndTimeOfArrival" value="" placeholder="Дата і час відправки"></p>
            <p><input type="datetime-local" name="dateAndTimeOfDeparture" value="" placeholder="Дата і час прибуття">
            </p>
            <p><input type="number" name="vechileId" value="" placeholder="Номер транспортного засобу"></p>
            <p class="submit"><input type="button" name="add" onclick="onAdd(event)" value="Додати"><input type="reset"
                                                                                                           name="reset"
                                                                                                           onclick="onReset(event)"
                                                                                                           value="Відмінити">
            </p>
        </form>
    </div>
</section>
<section class="containerAddTicket">
    <div class="add">
        <h1>Додавання нового квитка</h1>
        <form method="post" action="/work_with_db">
            <p><select id="category" name="category">
                <option disabled selected>Категорія</option>
                <option value="econom">Економний</option>
                <option value="medium">Середній</option>
                <option value="luxe">Люкс</option>
            </select></p>
            <p><input type="number" name="cost" value="" placeholder="Вартість"></p>
            <p><input type="number" name="sequenceNumber" value="" placeholder="Порядковий номер"></p>
            <p><input type="number" name="receiptId" value="" placeholder="Код чека"></p>
            <p><input type="number" name="journaryId" value="" placeholder="Номер рейсу"></p>
            <p class="submit"><input type="button" name="add" onclick="onAdd(event)" value="Додати"><input type="reset"
                                                                                                           name="reset"
                                                                                                           onclick="onReset(event)"
                                                                                                           value="Відмінити">
            </p>
        </form>
    </div>
</section>
<section class="containerAddReceipt">
    <div class="add">
        <h1>Додавання нового чека</h1>
        <form method="post" action="/work_with_db">
            <p><input type="datetime-local" name="dataAndTimeOfSale" value="" placeholder="Дата і час продажі"></p>
            <p><input type="datetime-local" name="dataAndTimeOfBooking" value="" placeholder="Дата і час бронювання">
            </p>
            <p><input type="number" name="totalPrice" value="" placeholder="Загальна ціна"></p>
            <p><input type="number" name="passengerId" value="" placeholder="Код пасажира"></p>
            <p class="submit"><input type="button" name="add" onclick="onAdd(event)" value="Додати"><input type="reset"
                                                                                                           name="reset"
                                                                                                           onclick="onReset(event)"
                                                                                                           value="Відмінити">
            </p>
        </form>
    </div>
</section>
<section class="containerAddPassenger">
    <div class="add">
        <h1>Додавання нового пасажира</h1>
        <form method="post" action="/work_with_db">
            <p><input type="text" name="lastName" value="" placeholder="Прізвище"></p>
            <p><input type="text" name="firstName" value="" placeholder="Ім'я"></p>
            <p><input type="text" name="surname" value="" placeholder="По батькові"></p>
            <p><select id="categoryPassenger" name="category">
                <option disabled selected>Категорія</option>
                <option value="Дитина до 4 років">Дитина до 4 років</option>
                <option value="Школяр">Школяр</option>
                <option value="Студент">Студент</option>
                <option value="Без пільг">Без пільг</option>
                <option value="Пенсіонер">Пенсіонер</option>
                <option value="Людина з інвалідністю">Людина з інвалідністю</option>
            </select></p>
            <p class="submit"><input type="button" name="add" onclick="onAdd(event)" value="Додати"><input type="reset"
                                                                                                           name="reset"
                                                                                                           onclick="onReset(event)"
                                                                                                           value="Відмінити">
            </p>
        </form>
    </div>
</section>
<section class="containerSendEmail">
    <div class="add">
        <h1>Відправка звіту на електрону адресу</h1>
        <form method="post" action="/statistics">
            <p><input type="email" name="email" value="" placeholder="Email"></p>
            <p><textarea name="message" placeholder="Повідомлення в Email. Не більше 500 символів." maxlength="500"></textarea></p>
            <p><input type="hidden" name="act" value="Mail"></p>
            <p><input type="hidden" name="sendReport" id="sendReport"></p>
            <p class="submit"><input type="submit" name="add" value="Відправити">
                <input type="reset"
                                                                                                           name="reset"
                                                                                                           onclick="onResetSendEmail(event)"
                                                                                                           value="Відмінити">
            </p>
        </form>
    </div>
    <script>
        // function onSendEmail(event){
        //     event.preventDefault();
        //     let currentForm = event.target.parentNode.parentNode;
        //     console.log(currentForm.getAttribute("id"));
        //     let form = new FormData(event.target.parentNode.parentNode);
        //     var table = document.getElementsByClassName("active")[0];
        //     form.append('table', table.getAttribute("title"));
        //     form.append('act', 'Add');
        //     var xhr = new XMLHttpRequest();
        //     xhr.open("POST", "/work_with_db", true);
        //     xhr.send(form);
        // }
        function onResetSendEmail(event) {
            var element = document.getElementsByClassName("containerSendEmail")[0];
            element.style.display = "none";
            var elBlur = document.getElementsByClassName("wrapper")[0];
            elBlur.style.filter = "blur(0px)";
        }
    </script>
</section>
</body>
</html>
