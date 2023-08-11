<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher dashboard</title>
</head>
<body>
<h1>Teacher dashboard</h1>
<form action="" method="get">
    <button type="submit" formaction="/addstudenttocourse">Add student to course</button>
    <button type="submit" formaction="/entermarks">Enter marks</button>
    <button type="submit" formaction="/showstatics">Show Statics</button>
    <button type="submit" formaction="/logout">Logout</button></form>
    <h1 style="color: red">${message}</h1>
</body>
</html>
