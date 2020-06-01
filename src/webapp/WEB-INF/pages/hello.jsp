<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<c:forEach var="msq" items="${messages}">
    <h1>${msq}</h1>
</c:forEach>
<%--@elvariable id="allUsers" type="java.util.List"--%>
<c:forEach items="${allUsers}" var="user">

    <h1><c:forEach items="${user.roles}" var="roles">${roles.name}; </c:forEach></h1>

</c:forEach>
<button type="button"><a href="/logout">Выход</a></button>
</body>
</html>