<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Database Utility</title>
</head>
<body>
    <jsp:include page="menu.jsp" />
    <form method="post" action="${pageContext.request.contextPath}/dbit">
        <div id="connconfigheader" class="section"><label>Connection Config</label></div>
        <div id="connconfig">
            <span>
                <input type="radio" name="dbselection" id="jndiselection" value="jndiselection">
                <label for="jndiselection" class="stdlabel">JNDI</label>
            </span>
            <span>
                <input type="radio" name="dbselection" id="connstringselection" value="connstringselection">
                <label for="connstringselection">Connection String</label>
            </span>

            <div id="connstringblock" class="hidden">
                <label for="connstring" class="stdlabel float">Conn String:</label> 
                <input type="text" name="connstring" id="connstring" value="${props['default.db.connection.string']}" class="stdinput block">
                
                <label for="driver" class="stdlabel float">Driver:</label>
                <input type="text" name="driver" id="driver" value="${props['default.db.connection.driver']}" class="stdinput block">
            </div>

            <div id="jndiblock" class="hidden">
                <label for="jndi" class="stdlabel float">JNDI name:</label> 
                <input type="text" name="jndi" id="jndi" class="stdinput">
            </div>
        </div>

        <div id="conncredsheader" class="section"><label>Connection Credentials</label></div>
        <div id="conncreds">
            <label for="username" class="stdlabel float">Username:</label>
            <input type="text" name="username" id="username" class="stdinput block">

            <label for="password" class="stdlabel float">Password:</label>
            <input type="password" name="password" id="password" class="stdinput block">
        </div>

        <div id="sqlinputheader" class="section"><label>Sql Input</label></div>
        <div id="sqlinput">
            <label for="sql" class="stdlabel float">Sql:</label>
            <textarea name="sql" id="sql" class="stdinput"></textarea>
            <input type="submit" id ="submit" name="submit" value="Submit">
        </div>
    </form>
    <jsp:include page="data.jsp" />
    <jsp:include page="error.jsp" />
</body>
</html>