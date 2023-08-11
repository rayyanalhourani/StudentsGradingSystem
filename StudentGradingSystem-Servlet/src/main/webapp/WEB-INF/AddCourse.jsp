<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Course</title>
</head>
<body>
<h1>Add Course</h1>
<form action="/addcourse" method="post">
    <label for="id">ID:</label>
    <input type="text" id="id" name="id" required><br><br>

    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required><br><br>

    <label for="teacherid">Teacher ID:</label>
    <input type="text" id="teacherid" name="teacherid" required><br><br>

    <input type="submit" value="Submit">
</form>
<h1 style="color: ${color}">${message}</h1>
<a href="/home" >back to home page</a>
</body>
</html>


