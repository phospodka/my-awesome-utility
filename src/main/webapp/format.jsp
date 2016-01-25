<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Format Utility</title>
</head>
<body>
    <jsp:include page="menu.jsp" />
    <div id="formatoptionsheader" class="section"><label>Format Options</label></div>
    <div id="formatoptions">
        <span>
            <input type="radio" name="formatselection" id="cssformat" value="cssformat">
            <label for="cssformat">CSS</label>
        </span>
        <span>
            <input type="radio" name="formatselection" id="jsonformat" value="jsonformat">
            <label for="jsonformat">JSON</label>
        </span>
        <span>
            <input type="radio" name="formatselection" id="sqlformat" value="sqlformat">
            <label for="sqlformat">SQL</label>
        </span>
        <span>
            <input type="radio" name="formatselection" id="xmlformat" value="xmlformat">
            <label for="xmlformat">XML</label>
        </span>
    </div>
    
    <div id="formatinputheader" class="section"><label>Format Input</label></div>
    <div id="formatinput">
        <div>
            <label for="input" class="stdlabel float">Input:</label>
            <textarea name="input" id="input" class="stdinput"></textarea>
            <input type="button" id ="formatbutton" name="formatbutton" value="Submit">
        </div>
    </div>

    <jsp:include page="data.jsp" />
    <jsp:include page="error.jsp" />
</body>
</html>