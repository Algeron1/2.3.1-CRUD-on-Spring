
<%--
  Created by IntelliJ IDEA.
  User: alger
  Date: 28.04.2020
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Менеджер Персонала</title>
</head>
<body>
<center>
    <h1>Менеджер Пользователей</h1>
    <h2>
        <a href="new">Добавить нового пользователя</a>
        &nbsp;&nbsp;&nbsp;
        <a href="list">Список всех пользователей</a>

    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>Список Пользователей</h2></caption>
        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Роль</th>
            <th>Что сделать</th>
        </tr>
        <%--@elvariable id="listUser" type="java.util.List"--%>
        <c:forEach var="user" items="${listUser}">
            <tr>
                <td><c:out value="${user.id}" /></td>
                <td><c:out value="${user.userName}" /></td>
                <td>
                    <c:forEach items="${user.roles}" var="role">${role.name}; </c:forEach>
                </td>
                <td>
                    <a href="edit=id<c:out value='${user.id}' />">Редактировать</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="delete=id<c:out value='${user.id}' />">Удалить</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button type="button"><a href="/logout">Выход</a></button>
</div>
</body>
</html>
