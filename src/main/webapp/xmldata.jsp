<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="xmldataheader" class="section hidden"><label>Data</label></div>
<pre id="xmldata"></pre>
<c:if test="${data ne null}">
    <script type="text/javascript">
        //capture as json for string handling
        var json = ${data};
        //pretty print the xml response data
        var str = vkbeautify.xml(json.response, 3);
        $("#xmldata").text(str);
        $("#xmldataheader").show();
    </script>
</c:if>