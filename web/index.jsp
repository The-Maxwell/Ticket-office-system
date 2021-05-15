<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 15.05.2021
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>JavaLab4</title>
  <link rel="stylesheet" href="views/stylesIndex.css"/>
</head>
<body>
<header>
  <h1>Ticket office DB</h1>
</header>
<main>
  <form method="post" target="_blank">
    <p>
    <h2>Table of database:</h2></p>
    <select id="table" name="table">
      <option selected value="vehicle">vehicle</option>
      <option value="journary">journary</option>
      <option value="ticket">ticket</option>
      <option value="receipt">receipt</option>
      <option value="passenger">passenger</option>
    </select>
    <div>
      <button name="act" value="Show" type="submit" formaction="/work_with_db">Show table</button>
      <button name="act" value="Add" type="submit" formaction="/redirect">Add</button>
      <button name="act" value="Delete" type="submit" formaction="/redirect">Delete</button>
      <button name="act" value="Update" type="submit" formaction="/redirect">Update</button>
    </div>
  </form>
</main>
</body>
</html>
