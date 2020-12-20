<%--
  Created by IntelliJ IDEA.
  User: Jayce
  Date: 2020-12-18
  Time: 9:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript">
        //页面加载，绑定单击事件
        $(function () {
            $("#btn").click(function () {
                //发送ajax请求
                $.ajax({
                   //编写json格式，设置属性与值
                    url: "user/testAjax",   //指定请求路径
                    contentType: "application/json;charset=UTF-8",    //指定编码集
                    data: '{"username": "李华", "password": "1234", "age": 20}',   //前端传给服务器的数据
                    dataType: "json",   //设定数据类型
                    type: "post",   //设置请求类型
                    success: function (data) {  //设置成功后的回调函数，里面的参数代表服务器端响应的数据
                        //解析服务端的数据
                        console.log(data);
                        console.log(data.username);
                        console.log(data.password);
                        console.log(data.age);
                    }
                });
            });
        });
    </script>
</head>
<body>
    <a href="user/testString">返回字符串返回值方法</a>
    <br/>
    <a href="user/testVoidDefault">返回默认类型的void返回值方法</a>
    <br/>
    <a href="user/testVoidRequest">返回由HttpServletRequest创建的对象</a>
    <br/>
    <a href="user/testModelAndView">返回ModelAndView返回值方式</a>
    <br/>
    <a href="user/testForward">使用关键字完成转发</a>
    <br/>
    <a href="user/testRedirect">使用关键字完成重定向</a>
    <br/>

    <h3>模拟异步请求</h3>
    <button id="btn">发送ajax请求</button>
</body>
</html>
