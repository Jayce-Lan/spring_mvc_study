<%--
  Created by IntelliJ IDEA.
  User: Jayce
  Date: 2020-12-17
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="param/testParam?username=Tony&pasword=123">请求参数绑定</a>
    <br/>
    <form action="param/saveAccount" method="post">
        姓名：<input type="text" name="username"/><br/>
        密码：<input type="password" name="password"/><br/>
        金额：<input type="text" name="money" /><br/>
        <input type="submit" value="提交" />
    </form>
    <h3>对象属性中包含对象</h3>
    <form action="param/saveAccountAndUser" method="post">
        姓名：<input type="text" name="username"/><br/>
        密码：<input type="password" name="password"/><br/>
        金额：<input type="text" name="money" /><br/>
        用户名：<input type="text" name="user.username" /><br/>
        年龄：<input type="text" name="user.age" /><br/>
        <input type="submit" value="提交" />
    </form>

    <h3>对象属性中包含集合</h3>
    <form action="param/saveAccountAndMap" method="post">
        姓名：<input type="text" name="username"/><br/>
        密码：<input type="password" name="password"/><br/>
        金额：<input type="text" name="money" /><br/>

        <%--将对象封装进入List集合中--%>
        用户名：<input type="text" name="userList[0].username" /><br/>
        年龄：<input type="text" name="userList[0].age" /><br/>


        用户名：<input type="text" name="map['one'].username" /><br/>
        年龄：<input type="text" name="map['one'].age" /><br/>

        <input type="submit" value="提交" />
    </form>

    <h3>自定义类型的转换</h3>
    <form action="param/saveUser" method="post">
        用户名：<input type="text" name="username" /><br/>
        年龄：<input type="text" name="age" /><br/>
        日期：<input type="text" name="date"/>
        <input type="submit" value="提交" />
    </form>
</body>
</html>
