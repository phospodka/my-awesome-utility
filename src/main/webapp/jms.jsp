<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Jms Utility</title>
</head>
<body>
    <jsp:include page="menu.jsp" />
    <form method="post" action="${pageContext.request.contextPath}/jmssendit">
        <div id="connconfigheader" class="section"><label>Connection Config</label></div>
        <div id="connconfig">
            <label for="factory" class="stdlabel float">Conn Factory:</label> 
            <input type="text" name="factory" id="factory" value="${props['default.jms.connection.factory']}" class="stdinput block">

            <span>
                <input type="radio" name="jmsselection" id="topicselection" value="topicselection">
                <label for="topicselection" class="stdlabel">Topic</label>
            </span>
            <span>
                <input type="radio" name="jmsselection" id="queueselection" value="queueselection">
                <label for="queueselection">Queue</label>
            </span>

            <div id="topicblock" class="hidden">
                <label for="topic" class="stdlabel float">Topic Jndi:</label> 
                <input type="text" name="topic" id="topic" value="${props['default.jms.topic']}" class="stdinput block">
            </div>

            <div id="queueblock" class="hidden">
                <label for="queue" class="stdlabel float">Queue Jndi:</label>
                <input type="text" name="queue" id="queue" value="${props['default.jms.queue']}" class="stdinput block">
            </div>
        </div>

        <div id="conncredsheader" class="section"><label>Connection Credentials</label></div>
        <div id="conncreds">
            <label for="username" class="stdlabel float">Username:</label>
            <input type="text" name="username" id="username" class="stdinput block">
            <label for="username" class="stdlabel block">(optional)</label>

            <label for="password" class="stdlabel float">Password:</label>
            <input type="password" name="password" id="password" class="stdinput block">
            <label for="password" class="stdlabel block">(optional)</label>
        </div>

        <div id="jmsactionheader" class="section"><label>Jms Action</label></div>
        <div id="jmsaction">
            <span>
                <input type="radio" name="jmsactionselection" id="jmsreceive" value="jmsreceive">
                <label for="jmsreceive" class="stdlabel">Receive</label>
            </span>
            <span>
                <input type="radio" name="jmsactionselection" id="jmssend" value="jmssend">
                <label for="jmssend">Send</label>
            </span>
            <span>
                <input type="radio" name="jmsactionselection" id="jmsclear" value="jmsclear">
                <label for="jmsclear">Clear</label>
            </span>

            <div id="receiveblock" class="hidden">
                <label for="delay" class="stdlabel float">Poll Period:</label> 
                <input type="text" name="delay" id="delay" value="${props['default.jms.receieve.delay.seconds']}" class="shortinput">
                <label for="delay" class="stdlabel">(in seconds)</label>
                <input type="submit" id ="submit" name="submit" value="Submit">
            </div>

            <div id="sendblock" class="hidden">
                <label for="message" class="stdlabel float">Message:</label>
                <textarea name="message" id="message" class="stdinput"></textarea>
                <input type="submit" id ="submit" name="submit" value="Submit">
            </div>

            <div id="clearblock" class="hidden">
                <label class="stdlabel float">Clear jms cache</label>
                <input type="submit" id ="submit" name="submit" value="Submit">
            </div>
        </div>
    </form>
    <jsp:include page="data.jsp" />
    <jsp:include page="error.jsp" />
</body>
</html>