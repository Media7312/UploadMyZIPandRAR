<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<html>
<body>
<form action="/upload" encType="multipart/form-data" method="post">
    <label>选择文件</label>
    <input name="uploadFile" type="file" />
    <%--<input name="uploadFile" type="file" multiple="multiple"/>--%>
    <input type="submit" value="提交"/>
</form>
<h2>Hello World!</h2>
</body>
</html>
