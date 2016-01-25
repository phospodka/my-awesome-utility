<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="errorheader" class="section hidden"><label>Error</label></div>
<pre id="error">${error}</pre>
<c:if test="${error ne null}">
    <script type="text/javascript">
        $("#error").show();
        $("#errorheader").show();
    </script>
</c:if>