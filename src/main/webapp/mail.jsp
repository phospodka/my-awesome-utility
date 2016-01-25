<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Mail Utility</title>
</head>
<body>
    <jsp:include page="menu.jsp" />
    <form method="post" action="${pageContext.request.contextPath}/mailit">
        <div id="mailconfigheader" class="section"><label>Mail Config</label></div>
        <div id="mailconfig">
            <label for="server" class="stdlabel float">Mail Server:</label> 
            <input type="text" name="server" id="server" value="${props['default.mail.server']}" class="stdinput block">

            <label for="from" class="stdlabel float">From:</label> 
            <input type="text" name="from" id="from" value="${props['default.mail.from']}" class="stdinput block">
        </div>

        <div id="mailcontentheader" class="section"><label>Mail Content</label></div>
        <div id="mailcontent">
            <label for="to" class="stdlabel float">To:</label> 
            <input type="text" name="to" id="to" class="stdinput block">

            <label for="subject" class="stdlabel float">Subject:</label> 
            <input type="text" name="subject" id="subject" class="stdinput block">

            <label for="body" class="stdlabel float">Body:</label>
            <textarea name="body" id="body" class="stdinput"></textarea>
            <input type="submit" id ="submit" name="submit" value="Submit">
        </div>
    </form>
    <jsp:include page="data.jsp" />
    <jsp:include page="error.jsp" />
</body>
</html>