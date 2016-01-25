<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Web Service Utility</title>
</head>
<body>
    <jsp:include page="menu.jsp" />
    <form method="post" action="${pageContext.request.contextPath}/wsit">
        <div id="connconfigheader" class="section"><label>Connection Config</label></div>
        <div id="connconfig">
            <label for="url" class="stdlabel float">Url:</label> 
            <input type="text" name="url" id="url" value="${props['default.ws.url']}" class="stdinput block">
        </div>
        
        <div id="wsrequestheader" class="section"><label>Web Service Request</label></div>
        <div id="wsrequest">
            <label for="xml" class="stdlabel float">Soap Xml:</label>
            <textarea name="xml" id="xml" class="stdinput"></textarea>
            <input type="submit" id ="submit" name="submit" value="Submit">
        </div>
    </form>
    <jsp:include page="xmldata.jsp" />
    <jsp:include page="error.jsp" />
</body>
</html>