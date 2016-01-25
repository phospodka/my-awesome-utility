<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Ldap Utility</title>
</head>
<body>
    <jsp:include page="menu.jsp" />
    <form method="post" action="${pageContext.request.contextPath}/ldapit">
        <div id="connconfigheader" class="section"><label>Connection Config</label></div>
        <div id="connconfig">
            <label for="url" class="stdlabel float">Url:</label> 
            <input type="text" name="url" id="url" value="${props['default.ldap.url']}" class="stdinput block">

            <label for="userobject" class="stdlabel float">User object:</label> 
            <input type="text" name="userobject" id="userobject" value="${props['default.ldap.user.object']}" class="stdinput block">

            <label for="userbase" class="stdlabel float">User base:</label>
            <input type="text" name="userbase" id="userbase" value="${props['default.ldap.user.base']}" class="stdinput block">

            <label for="groupbase" class="stdlabel float">Group base:</label>
            <input type="text" name="groupbase" id="groupbase" value="${props['default.ldap.group.base']}" class="stdinput block">
        </div>

        <div id="conncredsheader" class="section"><label>Connection Credentials</label></div>
        <div id="conncreds">
            <label for="username" class="stdlabel float">Username:</label>
            <input type="text" name="username" id="username" class="stdinput block">

            <label for="password" class="stdlabel float">Password:</label>
            <input type="password" name="password" id="password" class="stdinput block">
        </div>
        
        <div id="searchselheader" class="section"><label>Search Selection</label></div>
        <div id="searchsel">
            <div>
                <input type="radio" name="searchselection" id="usersearch" value="usersearch">
                <label for="usersearch">Search for all information about a specific user</label>
            </div>
            <div>
                <input type="radio" name="searchselection" id="groupsearch" value="groupsearch">
                <label for="groupsearch">Search for all users of a specific group</label>
            </div>
            <div> 
                <input type="radio" name="searchselection" id="membershipsearh" value="membershipsearh">
                <label for="membershipsearh">Search for all groups that a specific user belongs to</label>
            </div> 

            <label for="search" class="stdlabel float">Search string:</label> 
            <input type="text" name="search" id="search" class="stdinput">
            <input type="submit" id ="submit" name="submit" value="Submit" >
        </div>
    </form>
    <jsp:include page="data.jsp" />
    <jsp:include page="error.jsp" />
</body>
</html>