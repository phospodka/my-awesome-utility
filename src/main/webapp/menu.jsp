<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Base includes are linked only in the menu as this is included in every page. -->
<!-- javascript includes -->
<script type="text/javascript">
    var basepath = '${pageContext.request.contextPath}/';
</script>
<script type="text/javascript" src="js/vkbeautify.0.99.00.beta.js"></script>
<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="js/functions.js"></script>
<!-- css includes -->
<link rel="stylesheet" type="text/css" href="css/layout.css" />
<link rel="stylesheet" type="text/css" href="css/theme-light.css" />

<div id="menu">
    <label id="menulabel">Menu</label>
    <div id="menuitems" class="hidden">
        <c:choose>
            <c:when test="${loggedin eq true}">
                <div><a href="logout">Logout</a></div>
            </c:when>
            <c:otherwise>
                <div><a href="login.jsp">Login</a></div>
            </c:otherwise>
        </c:choose>
        <div class="line"></div>
        <div><a href="db.jsp">Database</a></div>
        <div><a href="format.jsp">Format</a></div>
        <div><a href="ldap.jsp">Ldap</a></div>
        <div><a href="jms.jsp">Jms</a></div>
        <div><a href="mail.jsp">Mail</a></div>
        <div><a href="ws.jsp">Web Service</a></div>
        <div class="line"></div>
        <div><a href="properties">Properties</a></div>
        <div><a href="whoami">Whoami</a></div>
    </div>
</div>