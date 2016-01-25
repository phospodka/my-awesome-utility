<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Login Utility</title>
</head>
<body>
    <jsp:include page="menu.jsp" />
    <div id="logindetailsheader" class="section"><label>Login Details</label></div>
    <form method=post action="${pageContext.request.contextPath}/login">
        <div id="logindetails">
            <label for="j_username" class="stdlabel float">Username:</label> 
            <input type="text" name="j_username" id="j_username" class="shortinput block">

            <label for="j_password" class="stdlabel float">Password:</label>
            <input type="password" name="j_password" id="j_password" class="shortinput">
            <input type="submit" id ="submit" name="submit" value="Submit">
        </div>
    </form>
    <jsp:include page="error.jsp" />
</body>
</html>