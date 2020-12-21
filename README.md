# Spring MVC



## 解决maven项目下载过慢

> 添加如下键值对

**name:**  archetypeCatalog

**value:**  internal



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



## 第二个SpringMVC项目



### 响应数据和结果视图



#### 返回值分类



##### 字符串

> 定义

`controller`类当中的方法返回字符串可以指定逻辑视图名，通过视图解析器可以解析为物理视图地址

> 实例

`spring.xml`当中的配置

```xml
<!-- 配置视图解析器 -->
<bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <!--文件所在目录-->
    <property name="prefix" value="/WEB-INF/pages/"/>
    <!--文件后缀名-->
    <property name="suffix" value=".jsp"/>
</bean>
```

`Controller`类中的方法

```java
/**
* 返回值类型为String
* @param model 存储键值对的参数
* @return 返回String类型的字符串用于匹配物理路径下的文件
*/
@RequestMapping("testString")
public String testString(Model model) {
    System.out.println("testString执行了...");

    //模拟从数据库获取对象
    User user = new User();
    user.setUsername("李华");
    user.setPassword("1234");
    user.setAge(20);
    model.addAttribute("user", user);

    return "success";
}
```



##### void类型

> 定义

- 默认情况下，该方法在默认情况下会返回@RequestMapping注解路径下的jsp文件

> 实例

```java
/**
 * 执行之后：文.件[/WEB-INF/pages/user/testVoidDefault.jsp] 未找到
 * 在默认情况下，该方法在默认情况下会返回@RequestMapping注解路径下的jsp文件
 */
@RequestMapping("testVoidDefault")
public void testVoidDefault() {
    System.out.println("testVoidDefault执行了...");
}

/**
 * 使用重定向或者请求转发达到跳转目的
 * @param request
 * @param response
 * @throws Exception
 */
@RequestMapping("testVoidRequest")
public void testVoidRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    System.out.println("testVoidRequest执行了...");

    //编写请求转发的程序
        //一次请求，不会修改请求路径，需要写上转发的路径，这里不会使用字符解析器
	request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request, response);

        //重定向，两次请求，最后会跳转到(根目录)index.jsp当中
    response.sendRedirect(request.getContextPath() + "/index.jsp");

    //设置中文乱码
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    //直接写入浏览器，向浏览器进行响应
    response.getWriter().print("成功");

    return; //不会再跳转到下面的方法
}
```



##### ModeAndView对象

> 定义

`ModeAndView` 对象是Spring提供的一个对象，可以用来跳转具体的jsp视图（原理与String相似）

> 实例

```java
/**
* 返回一个ModelAndView对象，该对象是SpringMVC提供的对象
* @return
*/
@RequestMapping("testModelAndView")
public ModelAndView testModelAndView() {
    ModelAndView mv = new ModelAndView();

    //模拟从数据库获取对象
    User user = new User();
    user.setUsername("李华");
    user.setPassword("1234");
    user.setAge(20);

    //存储对象
    mv.addObject("user", user);
    //跳转页面，由视图解析器进行解析
    mv.setViewName("success");

    return mv;
}
```



#### 转发和重定向

##### forward 转发（实际使用较少）

> 格式

返回值为String类型，因此必须遵循指定的写法

> 实例

```java
/**
* 关键字的方式完成请求
* @return 由于不再使用视图解析器，因此此处必须写 forward: + 完整路径
*/
@RequestMapping("testForward")
public String testForward() {
    System.out.println("testForward执行了...");
    //使用转发的固定格式：forward:+路径，此处不再由视图解析器进行解析
    return "forward:/WEB-INF/pages/success.jsp";
}
```



##### redirect 重定向

> 实例

```java
/**
* 由于是重定向，因此必须写根目录的文件
* @return 标准格式：redirect: + 根目录文件名
*/
@RequestMapping("testRedirect")
public String testRedirect() {
    System.out.println("testRedirect执行了...");
    return "redirect:/index.jsp";
}
```



#### 响应json数据

##### 异步请求maven依赖

```xml
<!--json依赖-->
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.12.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-annotations</artifactId>
    <version>2.12.0</version>
</dependency>
```



##### 配置静态资源解除拦截

`DispatcherServlet`会拦截任何资源，因此`spring.xml`文件中需要配置解除对静态资源的拦截

> spring.xml

```xml
<!--配置静态资源不拦截-->
<mvc:resources mapping="/js/**" location="/js/"/>
<mvc:resources mapping="/css/**" location="/css/"/>
<mvc:resources mapping="/img/**" location="/img/"/>
```



##### 获取到前端传来的json参数

```java
/**
* 模拟异步请求
* 获取对应的json数据
* @param data 将前端传来的数据设置为请求体，获取请求体数据
*/
@RequestMapping("testAjax")
public void testAjax(@RequestBody String data) {
    System.out.println("testAjax...");
    System.out.println(data);
}
```

> 与之对应的jsp

```jsp
<script>
$.ajax({
    //编写json格式，设置属性与值
    url: "user/testAjax",   //指定请求路径
    contentType: "application/json;charset=UTF-8",    //指定编码集
    data: '{"username": "李华", "password": "1234", "age": 20}',   //前端传给服务器的数据
    dataType: "json",   //设定数据类型
    type: "post",   //设置请求类型
    success: function (data) {  //设置成功后的回调函数，里面的参数代表服务器端响应的数据
    	//解析服务端的数据
    }
});
</script>
```



##### @ResponseBody 响应对象

```jsp
<script>
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
</script>
```

> 后台

```java
/**
* 模拟异步请求
* 获取对应的json数据
* @param user 将前端传来的数据设置为请求体，获取请求体数据
* @return 返回一个对象，返回值由@ResponseBody来响应
*/
@RequestMapping("testAjax")
public @ResponseBody User testAjax(@RequestBody User user) {
    System.out.println("testAjax...");
    //客户端发送Ajax请求，传入json字符串，后台jar包封装了这个json字符串到user中
    System.out.println(user);
    //相应，模拟查询/修改数据库信息
    user.setAge(40);

    return user;
}
```



## SpringMVC实现上传文件

### 文件上传

#### 导入对应依赖

```xml
<!--文件上传依赖-->
<!-- https://mvnrepository.com/artifact/commons-io/commons-io -->
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.4</version>
</dependency>
<!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.3.3</version>
</dependency>
```

以上依赖是上传文件的必要依赖，会帮助我们解析文件



### 传统的文件上传方式

> 方法

```java
/**
 * 使用传统方式的文件上传
 * @param request 通过HttpServletRequest请求获取到文件
 * @return 返回成功页面
 * @throws Exception
 */
@RequestMapping("traditionFileUpload")
public String traditionFileUpload(HttpServletRequest request) throws Exception {
    System.out.println("文件正在上传...");

    //使用fileupload组件完成文件上传
    //上传的位置
    String path = request.getSession().getServletContext().getRealPath("/uploads/");
    File file = new File(path);     //上传文件夹的路径，该路径存在于tomcat的项目目录下

    if (!file.exists())
        file.mkdirs();  //如果该路径不存在，则新建一个文件夹

    //解析request对象获取上传文件项
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    //解析request
    List<FileItem> items = upload.parseRequest(request);    //返回一个装有文件项目的集合
    //遍历
    for (FileItem item : items) {
        //判断item是否是上传文件项
        if (item.isFormField()) {
            //普通表单项
        } else {
            //上传文件项
            String fileName = item.getName();   //获取文件名称
            //设置唯一文件名
            String uuid = UUID.randomUUID().toString().replace("-", "");
            fileName = uuid + "_" + fileName;

            item.write(new File(path, fileName));   //完成文件上传
            item.delete();      //删除临时文件
        }
    }

    return "success";
}
```

> 对应的jsp

```jsp
<%--上传文件必须指定enctype属性--%>
<form action="file/traditionFileUpload" method="post" enctype="multipart/form-data">
    选择文件：<input type="file" name="upload"/>
    <input type="submit" value="上传"/>
</form>
```



### SpringMVC实现文件上传

#### 执行流程

- 文件上传
- 通过 `request` 传给前端控制器 --> 前端控制器调用文件解析器解析文件
- 以 `upload` 的形式返回给服务器方法



#### 配置文件解析器

在spring中上传文件我们需要依赖于`CommonsMultipartResolver`

> spring.xml

```xml
<!--配置文件解析器-->
<!--这里要求id必须为multipartResolver-->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <property name="maxUploadSize" value="10485760"/>
</bean>
```

> 实现

```java
/**
 * 使用springMVC的方式上传文件
 * @param request 通过HttpServletRequest请求获取到文件
 * @param springMVCUpload 获取上传的文件，该处的参数名必须与表单中，上传文件选项的name一致
 *                        对应jsp：<input type="file" name="springMVCUpload"/>
 * @return 返回成功结果
 * @throws IOException
 */
@RequestMapping("springMVCFileUpload")
public String springMVCFileUpload(HttpServletRequest request, MultipartFile springMVCUpload) throws IOException {
    System.out.println("springMVCFileUpload...");

    String path = request.getSession().getServletContext().getRealPath("/uploads/");
    File file = new File(path);     //上传文件夹的路径

    if (!file.exists())
        file.mkdirs();  //如果该路径不存在，则新建一个文件夹

    //获取文件名
    String fileName = springMVCUpload.getOriginalFilename();
    String uuid = UUID.randomUUID().toString().replace("-", "");
    fileName = uuid + "_" + fileName;

    //文件上传
    springMVCUpload.transferTo(new File(path, fileName));

    return "success";
}
```

由于springMVC会做文件处理，因此无需手动删除文件



### 跨服务器上传文件

#### 导入跨服务器上传文件依赖

```xml
<!--跨服务器上传依赖-->
<!-- https://mvnrepository.com/artifact/com.sun.jersey/jersey-core -->
<dependency>
    <groupId>com.sun.jersey</groupId>
    <artifactId>jersey-core</artifactId>
    <version>1.19.4</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.sun.jersey/jersey-client -->
<dependency>
    <groupId>com.sun.jersey</groupId>
    <artifactId>jersey-client</artifactId>
    <version>1.19.4</version>
</dependency>
```

#### 代码实现

> 实现代码

```java
/**
 * 实现跨服务器上传文件
 * @param upload 这里要与前端表单中上传文件中的name属性一致
 *               对应的jsp：<input type="file" name="upload"/>
 * @return 返回成功页面
 * @throws IOException
 */
@RequestMapping("crossServerFileUpload")
public String crossServerFileUpload(MultipartFile upload) throws IOException {
    System.out.println("跨服务器上传文件...");

    //定义服务器地址，值得注意的是，这里必须要保证该目录下有响应的文件夹
    String path = "http://localhost:8083/day02_02_fileupload/uploads";

    String fileName = upload.getOriginalFilename();
    String uuid = UUID.randomUUID().toString().replace("-", "");
    fileName = uuid + "_" + fileName;

    //创建客户端对象
    Client client = new Client();
    //与文件服务器连接，拿到web资源
    WebResource resource = client.resource(path + "/" + fileName);
    //上传文件，拿到字节数组
    resource.put(upload.getBytes());

    return "success";
}
```

