
<%--
  Created by IntelliJ IDEA.
  User: Святослав
  Date: 25.11.2019
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<b>
    <h2>
        ${message}
    </h2>
</b>

<form method="post" action="/login">
    <input name="j_username"/>
    <input name="j_password"/>
    <input type="submit"/>
</form>

</body>
</html>
