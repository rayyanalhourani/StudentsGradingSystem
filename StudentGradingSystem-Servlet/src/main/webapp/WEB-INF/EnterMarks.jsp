<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Enter marks</title>
</head>
<body>
<h1>Enter marks</h1>
<form action="/entermarks" method="post">
    <label for="studentid">Student ID:</label>
    <input type="text" id="studentid" name="studentid" required><br><br>

    <label for="courseid">Course ID:</label>
    <input type="text" id="courseid" name="courseid" required><br><br>

    <label for="midtermgrade">Midterm Grade:</label>
    <input type="number" id="midtermgrade" name="midtermgrade" min="0" max="30" required><br><br>

    <label for="assignmentsgrade">Assignments Grade:</label>
    <input type="number" id="assignmentsgrade" name="assignmentsgrade" min="0" max="30" required><br><br>

    <label for="finalgrade">Final exam Grade:</label>
    <input type="number" id="finalgrade" name="finalgrade" min="0" max="40" required><br><br>

    <input type="submit" value="Submit">
</form>
<h1 style="color: ${color}">${message}</h1>
<a href="/home" >back to home page</a>
</body>
</html>
