<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Statics</title>
</head>
<body>
<h1>Statics Form</h1>
<form action="/showstatics" method="post">
    <label for="courseid">Course ID:</label>
    <input type="text" id="courseid" name="courseid" required><br><br>

    <input type="submit" value="Submit">
</form>
<h2 style="color: ${color}">${message}</h2>
<a href="/home" >back to home page</a>
</body>
</html>
