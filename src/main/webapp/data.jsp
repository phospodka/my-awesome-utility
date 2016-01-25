<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="dataheader" class="section hidden"><label>Data</label></div>
<pre id="data"></pre>
<c:if test="${data ne null}">
    <script type="text/javascript">
        var json = ${data};
        //this is probably safer, but had problems sometimes with supposedly valid JSONObjects
        //json = jQuery.parseJSON('${data}');
        //pretty pring the json
        var str = JSON.stringify(json, undefined, 3);
        $("#data").text(str);
        $("#dataheader").show();
    </script>
</c:if>