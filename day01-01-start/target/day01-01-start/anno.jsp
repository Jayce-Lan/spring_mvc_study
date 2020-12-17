<%--
  Created by IntelliJ IDEA.
  User: Jayce
  Date: 2020-12-17
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%--常用注解--%>
    <a href="anno/testRequestParam">测试RequestParam(无参)</a><br/>
    <a href="anno/testRequestParam?name=李华">测试RequestParam(带参)</a><br/>

    <h3>获取请求体</h3>
    <form action="anno/RequestBody" method="post">
        用户名：<input type="text" name="username" /><br/>
        年龄：<input type="text" name="age" /><br/>
        日期：<input type="text" name="date"/><br/>
        <input type="submit" value="提交" />
    </form>
    <br/>
    <a href="anno/testPathVariable/10">测试PathVariable</a><br/>
    <a href="anno/testRequestHeader">测试RequestHeader</a><br/>
    <a href="anno/testCookieValue">测试CookieValue</a><br/>

    <h3>ModelAttribute</h3>
    <form action="anno/testModelAttribute" method="post">
        用户名：<input type="text" name="username" /><br/>
        年龄：<input type="text" name="age" /><br/>
        <input type="submit" value="提交" />
    </form>
    <br/>
    <a href="anno/testSessionAttributes">测试SessionAttributes</a><br/>
    <a href="anno/getSessionAttributes">获取SessionAttributes</a><br/>
    <a href="anno/deleteSessionAttributes">删除SessionAttributes</a><br/>
</body>
</html>
