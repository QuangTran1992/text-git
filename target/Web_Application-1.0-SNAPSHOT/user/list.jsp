<%--
  Created by IntelliJ IDEA.
  User: mebemint
  Date: 2021/05/31
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>

    <title>Title</title>

    <!-- CSS only -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link rel="stylesheet" href="/assets/style.css">
</head>
<body>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>
<div class="snowflake"></div>

<div class="container">
    <h1 align="center">User Management Application</h1>
    <h2 align="center"><a href="/users?action=create" class="text-success">Add new user</a></h2>
    <form align="center" method="get">
        <input type="hidden" name="action" value="find">
        <input type="text" name="key">
        <input type="submit" value="Search">
    </form>
</div>
<div align="center">
    <caption><h2>List of Users</h2></caption>
    <table border="1" cellpadding="5" class="table table-success table-striped">

        <tr >
            <th >ID</th>
            <th >Name</th>
            <th >Email</th>
            <th >Country</th>
            <th >Actions</th>
        </tr>
        <c:forEach var="user" items="${listUser}">
            <tr>
                <td ><c:out value="${user.id}"/> </td>
                <td ><c:out value="${user.name}"/></td>
                <td ><c:out value="${user.email}"/></td>
                <td ><c:out value="${user.country}"/></td>
                <td>
                    <a href="/users?action=edit&id=${user.id}">Edit</a>
                    <a href="/users?action=delete&id=${user.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table >
</div>
</body>
<!-- JavaScript Bundle with Popper -->

</html>
