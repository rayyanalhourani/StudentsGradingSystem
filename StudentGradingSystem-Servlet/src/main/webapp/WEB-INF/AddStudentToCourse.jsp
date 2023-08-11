<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Stuudent to course</title>
</head>
<body>
<h1>Add Stuudent to course</h1>
<form action="/addstudenttocourse" method="post">
    <label for="studentid">Student ID:</label>
    <input type="text" id="studentid" name="studentid" required><br><br>

    <label for="courseid">Course ID:</label>
    <input type="text" id="courseid" name="courseid" required><br><br>

    <input type="submit" value="Submit">
</form>
<h1 style="color: ${color}">${message}</h1>
<a href="/home" >back to home page</a>
</body>
</html>
