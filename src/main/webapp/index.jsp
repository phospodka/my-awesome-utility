<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>My awesome utility</title>
</head>
<body>
    <jsp:include page="menu.jsp" />
    <jsp:include page="data.jsp" />
    <!-- set the homepage data div to some fanciness -->
    <c:if test="${data eq null}">
        <script type="text/javascript">
            welcome();
        </script>
    </c:if>
    <jsp:include page="error.jsp" />
</body>
</html>