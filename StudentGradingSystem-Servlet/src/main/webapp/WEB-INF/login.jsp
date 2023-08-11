<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login Form</h1>
<form action="/login" method="post">
    <label for="id">ID:</label>
    <input type="text" id="id" name="id" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <input type="submit" value="Login">
</form>
<h1 style="color: red">${message}</h1>


</body>
</html>
