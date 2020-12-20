<%--
  Created by IntelliJ IDEA.
  User: Jayce
  Date: 2020-12-20
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>传统方式文件上传文件</h3>
    <%--上传文件必须指定enctype属性--%>
    <form action="file/traditionFileUpload" method="post" enctype="multipart/form-data">
        选择文件：<input type="file" name="upload"/>
        <input type="submit" value="上传"/>
    </form>

    <h3>SpringMVC文件上传文件</h3>
    <form action="file/springMVCFileUpload" method="post" enctype="multipart/form-data">
        <%--这里的name必须与方法参数名一致--%>
        选择文件：<input type="file" name="springMVCUpload"/>
        <input type="submit" value="上传"/>
    </form>
</body>
</html>
