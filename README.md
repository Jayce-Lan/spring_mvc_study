# Spring MVC



## 解决maven项目下载过慢

> 添加如下键值对

**name:**archetypeCatalog

**value:**internal



## 第一个SpringMVC项目



### 项目目录

- spring_mvc_study
  - day01-01-start（第一个Spring MVC项目）
    - controller【存放与前端相应的控制器】
      - `HelloController`【获取ServletAPI，RequestMapping属性的样例】
      - `ParamController`【请求参数，返回参数的样例】
      - `AnnoController`【常用注解样例】
    - pojo【存放实体类】
    - utils
      - `StringToDateConverter`【日期转换的工具类】



### 项目创建

#### 创建maven工程导入依赖

> pom.xml

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>${spring.version}</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework/spring-web -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
    <version>${spring.version}</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>${spring.version}</version>
</dependency>

<!-- https://mvnrepository.com/artifact/javax.servlet/servlet-api -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>servlet-api</artifactId>
    <version>2.5</version>
    <scope>provided</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/javax.servlet/jsp-api -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.0</version>
    <scope>provided</scope>
</dependency>
```



#### 配置前端控制器

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>

  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <!--加载配置文件-->
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <!--所有事务都会经过这个控制器-->
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>
```

#### springmvc.xml配置

```xml
<!-- 开启注解扫描 -->
<context:component-scan base-package="com.learn"/>

<!-- 配置视图解析器 -->
<bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <!--文件所在目录-->
    <property name="prefix" value="/WEB-INF/pages/"/>
    <!--文件后缀名-->
    <property name="suffix" value=".jsp"/>
</bean>

<!-- 开启springMVC注解的支持 -->
<mvc:annotation-driven/>
```



### @RequestMapping

建立请求url和处理请求方法之间的对应关系

它与a链接内的url是相对应的

> 实例

```java
import ...
//控制器
@Controller
@RequestMapping("/user")
public class HelloController {

    @RequestMapping(path = "/hello")  //请求映射
    public String sayHello() {
        System.out.println("Hello SpringMVC");
        return "success";
    }

    @RequestMapping("/testRequestMapping")
    public String testRequestMapping() {
        System.out.println("测试RequestMapping注解...");
        return "success";
    }
}
```

与之对应的jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>入门程序</h2>
    <a href="user/hello">入门程序</a>
    <a href="user/testRequestMapping">入门程序</a>
</body>
</html>
```



#### RequestMapping注解的属性

- `value`  用于指定请求的url，与path属性一致
- `method`  用于指定请求的方式
- `params`  用于指定限制请求参数的条件，支持简单的表达式；要求求情参数的key和value必须与配置一致
- `header`  用于指定限制请求消息头的条件



> 实例

```java
@RequestMapping(path = "/testMethod", method = {RequestMethod.POST})
public String testMethod() {
    System.out.println("testMethod...");
    return "success";
}

@RequestMapping(value = "/testRequestMapping", params = {"username"})
//此处声明username不能为空

@RequestMapping(value = "/testRequestMapping", params = {"username=username"})
//此处声明username必须等于username

@RequestMapping(value = "/testRequestMapping", params = {"username!username"})
//此处声明username必须不等于username
```



### 请求参数的绑定



#### 支持的数据类型

- 基本数据类型以及字符串
- 实体类型（JavaBean）
- 集合数据类型（List、Map集合等）



#### 解决传入参数为中文的乱码问题

##### 前端控制器配置

web.xml

```xml
<!--配置解决中文乱码的过滤器-->
<filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <!--拦截所有url-->
    <url-pattern>/*</url-pattern>
</filter-mapping>
```



#### 几种类型转换的实例

```jsp
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
</body>
</html>
```



> 对应的操作方法

```java
import ...

//请求参数的绑定

@Controller
@RequestMapping("/param")
public class ParamController {

    /**
     * 方法中添加参数可以获取到url传回来的参数
     * 封装简单参数
     *
     * @param username 获取url携带的参数
     * @return 返回成功页面
     */
    @RequestMapping("/testParam")
    public String testParam(String username, String password) {
        System.out.println("执行了testParam...");
        System.out.println(username);
        System.out.println(password);
        return "success";
    }

    /**
     * 将数据封装到JavaBean的类当中
     * @param account 在表单中输入对应的属性，就会传值到该对象中
     * @return 返回请求成功页面
     */
    @RequestMapping("/saveAccount")
    public String saveAccount(Account account) {
        System.out.println("执行了保存用户...");
        System.out.println(account);
        return "success";
    }

    /**
     * 将数据封装到JavaBean的类当中
     * 对象属性中包含对象
     *
     * @param account 在表单中输入对应的属性，就会传值到该对象中
     * @return 返回请求成功页面
     */
    @RequestMapping("/saveAccountAndUser")
    public String saveAccountAndUser(Account account) {
        System.out.println("执行了保存用户...");
        System.out.println(account);
        return "success";
    }

    /**
     * 将数据封装到JavaBean的类当中
     * 对象属性中包含集合
     *
     * @param accoountMap 在表单中输入对应的属性，就会传值到该对象中
     * @return 返回请求成功页面
     */
    @RequestMapping("/saveAccountAndMap")
    public String saveAccountAndMap(AccoountMap accoountMap) {
        System.out.println("执行了保存用户...");
        System.out.println(accoountMap);
        return "success";
    }

}
```

> 实体类

```java
public class Account implements Serializable {
    private String username;
    private String password;
    private Double money;

    private User user;
}

public class AccoountMap implements Serializable {
    private String username;
    private String password;
    private Double money;
    private List<User> userList;
    private Map<String, User> map;
}

public class User implements Serializable {
    private String username;
    private Integer age;
}
```



#### 自定义类型的转换

在工具类完成之后，需要去注入容器中

> 工具类

```java
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//把字符串转换为日期的工具类，重写了Converter的方法
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String s) {

        if (s == null)
            throw new RuntimeException("请输入正确日期");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //把字符串转换为日期
            return dateFormat.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException("数据类型转换出错");
        }
    }
}
```

> xml中注入

```xml
<!--配置自定义转换器-->
<!--配置结束后需要在下面支持当中配置生效-->
<bean id="conversionServiceFactoryBean" class="org.springframework.context.support.ConversionServiceFactoryBean">
    <property name="converters">
        <set>
            <!--将重写的方法注入容器中-->
            <bean class="com.learn.utils.StringToDateConverter"/>
        </set>
    </property>
</bean>

<!-- 开启springMVC注解的支持 -->
<mvc:annotation-driven conversion-service="conversionServiceFactoryBean"/>
```



### 常用注解

#### @RequestParam

> 定义

把请求中指定名称的参数给控制器中的形参赋值

> 属性

- `value`  请求参数中的名称
- `required`  请求参数中是否必须提供此参数。默认值 true 表示必须提供，否则报错

> 实例

```java
/**
* 传入参数并返回
* @param username @RequestParam(name = "name", required = false)中
*                 name/value 属性与传入参数的属性像对应，
*                 即使与我们的变量名username不对应，因为加了注解，也可以成功传入
*                 required 属性决定了是否需要传入参数，默认为true
* @return 返回成功页面
*/
@RequestMapping("testRequestParam")
public String testRequestParam(@RequestParam(name = "name", required = false) String username) {
    System.out.println(username);
    return "success";
}
```



#### @RequestBody

> 定义

用于获取请求体内容。直接使用得到的是`key=value&key=value...`的数据结构（常用于异步执行，使用json数据时）

*get*请求方式不适用

> 属性

- `required`  是否必须有请求体。默认值为 *true* 。当取值为true时，*get*请求方式会报错；如果设定为*false*，*get*请求会得到*null*

> 实例

```java
/**
* 获取请求体
* @param body 由于有@RequestBody注解，因此获取到的不会是body这个属性，而是完整的请求体
* @return 返回成功页面
*/
@RequestMapping("/RequestBody")
public String testRequestBody(@RequestBody String body) {
    System.out.println("RequestBody...");
    System.out.println(body);
    return "success";
}
```



#### @PathVaribale

> 定义

拥有绑定 url 中的占位符。例如：url中有/delete/{id}， {id}即为占位符

> 属性

- `value/name`  指定url占位符名称
- `required`  是否必须提供占位符

> *Restful* 风格的URL

- 请求路径一样，可以根据不同的请求方式去执行后台不同的方法
- *restful*风格url的优点
  - 结构清晰
  - 符合标准
  - 易于理解
  - 扩展方便

> 实例

```java
/**
* 占位符
* @param id 由于存入了@PathVariable，前端页面只需要存入testPathVariable/10（id值）即可
*              实例：<a href="anno/testPathVariable/10">测试PathVariable</a><br/>
*           url中的占位符必须与注解中的name相对应
* @return
*/
@RequestMapping("testPathVariable/{uid}")
public String testPathVariable(@PathVariable(name = "uid") String id) {
    System.out.println("testPathVariable...");
    System.out.println(id);
    return "success";
}
```



#### @RequestHeader

> 定义

用于获取请求消息头

> 属性

- `value`  提供消息头名称
- `required`    是否必须有此消息头

> 注意

正式开发中不常用

> 实例

```java
/**
* 获取请求头的值
* @param header @RequestHeader("Accept")注解声明后获取请求头的值
* @return 返回成功页面
*/
@RequestMapping("testRequestHeader")
public String testRequestHeader(@RequestHeader("Accept") String header) {
    System.out.println("testRequestHeader...");
    System.out.println(header);
    return "success";
}
```



#### @CookieValue

> 定义

把指定 cookie 名称的值传入控制器方法参数

> 属性

- `value`  指定cookie的名称
- `required`    是否必须有此cookie

> 实例

```java
/**
 * 获取Cookie的值
 * @param cookie 返回cookie的值
 * @return 返回成功页面
 */
@RequestMapping("testCookieValue")
public String testCookieValue(@CookieValue(value = "JSESSIONID") String cookie) {
    System.out.println("testCookieValue...");
    System.out.println(cookie);
    return "success";
}
```



#### @ModelAttribute

> 定义

- 该注解是SpringMVC4.3版本以后新加入的，可以用于修饰方法和参数
- 出现在方法上，表示当前方法会在控制器的方法之前执行，它可以修饰没有返回值的方法，也可以修饰有具体返回值的方法
- 出现在参数上，获取指定的数据给参数赋值

> 属性

- `value`  用于获取数据的key，key可以是实体类的属性名称，也可以是map结构的key

> 应用场景

当表单数据不是完整的实体类数据时，保证没有提交数据的字段使用数据库原来的对象

例如：

​	我们在编辑一个用户时，用户有一个创建信息字段，该字段的值不允许被修改。在提交表单数据时没有该字段内容，一旦更新会把字段设为*null*，此时就可以使用该注解解决类似问题

> 有返回值方法实例

```java
/**
* 该方法会拿到showModelAttribute方法返回的user
* @param user
* @return
*/
@RequestMapping("testModelAttribute")
public String testModelAttribute(User user) {
    System.out.println("testModelAttribute...");
    System.out.println(user);
    return "success";
}

/**
* 由于有了@ModelAttribute注解，该方法会优先执行，
* 由于优先执行的缘故，可以获取到前端传来的对象以及属性
* 因此可以通过它获取到数据库的对象
* @param username 传入值后我们就可以获取到数据库对象
* @param age 传入年龄
* @return 返回查询出来的结果
*/
@ModelAttribute
public User showModelAttribute(String username, Integer age) {
    System.out.println("ModelAttribute执行了...");
    //模拟获取数据库对象
    User user = new User();
    user.setUsername(username);
    user.setAge(age);
    user.setDate(new Date());
    return user;
}
```



> 无返回值实例

```java
/**
 * 该方法会拿到showModelAttribute方法返回的user
 * @param user @ModelAttribute(value = "user1")加入注解后，会在showModelAttributeNoReturn方法中取得userMap集合的值
 * @return
 */
@RequestMapping("testModelAttribute")
public String testModelAttribute(@ModelAttribute(value = "user1") User user) {
    System.out.println("testModelAttribute...");
    System.out.println(user);
    return "success";
}

/**
 * 无返回值方法使用该注解时，需要声明一个map集合存放获取到的参数
 * @param username 从前端获取的参数
 * @param age      从前端获取的参数
 * @param userMap  将参数封装成一个map集合传给有需要调用的方法
 */
@ModelAttribute
public void showModelAttributeNoReturn(String username, Integer age, Map<String, User> userMap) {
    User user = new User();
    user.setUsername(username);
    user.setAge(age);
    user.setDate(new Date());
    userMap.put("user1", user);
}
```



#### @SessionAttribute

> 定义

用于多次执行控制器方法间的参数共享（只能作用于类中）

> 属性

- value  用于指定存入的属性名称
- type  用于指定存入的数据类型

> 实例

```java
@Controller
@RequestMapping("/anno")
@SessionAttributes(value = {"msg"})     //将msg="李华存入session域中"
public class AnnoController {
	/**
     * 向session中，以键值对形式存入值
     * jsp：<a href="anno/testSessionAttributes">测试SessionAttributes</a><br/>
     * @param model 存入的值
     * @return 返回成功页面
     */
    @RequestMapping("testSessionAttributes")
    public String testSessionAttributes(Model model) {
        model.addAttribute("msg", "李华");
        return "success";
    }

    /**
     * 获取session中的值（由于是键值对形式，因此get方法获取值）
     * jsp：<a href="anno/getSessionAttributes">getSessionAttributes</a><br/>
     * @param modelMap 获取到的值
     * @return 返回成功页面
     */
    @RequestMapping("getSessionAttributes")
    public String getSessionAttributes(ModelMap modelMap) {
        modelMap.get("msg");
        System.out.println(modelMap);
        return "success";
    }

    /**
     * 清除应用中session的值
     * jsp：<a href="anno/deleteSessionAttributes">删除SessionAttributes</a><br/>
     * @param status 获取到session
     * @return 返回成功页面
     */
    @RequestMapping("deleteSessionAttributes")
    public String deleteSessionAttributes(SessionStatus status) {
        status.setComplete();
        return "success";
    }
}
```

> 成功页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>入门成功</h3>
    ${ requestScope.msg }
    ${ sessionScope }
</body>
</html>
```

