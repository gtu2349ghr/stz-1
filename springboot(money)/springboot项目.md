# 在前端页面加缓存

![image-20220924162312098](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220924162312098.png)

```
 xmlns:th="http://www.thymeleaf.org
```

# 使用redis处理数据

用isNull判断它的值为null且对象内容为空的时候才证明他是null，

![image-20220923202758814](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220923202758814.png)

![image-20220923203027905](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220923203027905.png)

![image-20220923205040580](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220923205040580.png)

优化一：最后给redis加一个时间期限

![image-20220923205550307](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220923205550307.png)

## 优化二：设置一个常量，放到常量类里



## 优化三：设置redis中key的序列化方式（一个项目设置一次就好了），一位默认的是二进制的，不容易看，所以设为String形式，我们写的什么就是什么

![image-20220923212510313](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220923212510313.png)

## 优化四，双重验证解决缓存击穿

测试‘

![image-20220923214608993](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220923214608993.png)

**解决**

![image-20220923214001655](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220923214001655.png)

**依赖**

```
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.9</version>
</dependency>
```

# 注意事项

![image-20220924120430125](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220924120430125.png)

# 常量类

![image-20220923210323033](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220923210323033.png)

# 前端数据拼接

![image-20220924164459074](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220924164459074.png)

## 分页查询

# 后端接受前端数据（前端返回值）

![image-20220925184317886](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220925184317886.png)

![image-20220925184501712](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220925184501712.png)

# 分页查询

主要分析：主要查询的是：总条数，总页数，当前页（直接返回），查询的数据，

前端返回类型和当前页

：然后数据通过map集合来传给方法（查询数据的）

传给它查询的起始条和类型

：用封装类来包装要返回前端的信息

主要有两个方法一个查询数据信息，一个查询总条数



## 业务层

![image-20220926151520121](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220926151520121.png)

## 控制层

![image-20220926151845713](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220926151845713.png)

## mapper.xml

![image-20220926152011927](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220926152011927.png)

## 正确写

![image-20220926171748390](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220926171748390.png)

![image-20220926171815289](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220926171815289.png)

直接返回obs，类型是封装类的类型

因为在控制层new一个新的对象，它里面是没值的

## 前端页面

![image-20220926202220383](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220926202220383.png)

# 两表联查，一对一映射关系

![image-20220927211100374](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220927211100374.png)

![image-20220927211413184](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220927211413184.png)

# 日期格式化

![image-20220928204026842](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220928204026842.png)

# 用户的注册

![image-20220928210415540](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220928210415540.png)

## 失去焦点事件验证手机号

![image-20220928210516544](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220928210516544.png)

![image-20220928211007989](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220928211007989.png)

![image-20220929211043222](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220929211043222.png)

![image-20220929211201304](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220929211201304.png)

![image-20220929211414797](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220929211414797.png)

![image-20220929211431704](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220929211431704.png)

# 用户注册

![image-20220930154511190](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220930154511190.png)

![image-20220930154623803](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220930154623803.png)

## 密码进行加密

![image-20220930160449796](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220930160449796.png)

![image-20220930160624306](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220930160624306.png)

## 解决添加到查不到id，因为是在业务层new的对象，

![image-20220930162339175](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220930162339175.png)

![image-20220930162545791](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20220930162545791.png)

# 验证码验证

![image-20221001090454770](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221001090454770.png)

![image-20221003184648581](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221003184648581.png)

![image-20221001090909931](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221001090909931.png)

![image-20221001090924776](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221001090924776.png)

因为它返回的是json数据，所以需要返回

![image-20221001091148381](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221001091148381.png)

## 解析xml的两种方式和区别

![image-20221001091500359](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221001091500359.png)

## Xpath语法：（有帮助文档）

![image-20221001091740556](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221001091740556.png)

### 案例

![image-20221001092309954](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221001092309954.png)

现根据xml生成一个文本对象

![image-20221001092501719](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221001092501719.png)

其中要导入一个dom4j的依赖，要从maven中央仓库里导入，否则它只有一个依赖（原有两个）

## 验证码倒计时

[jQuery特效_网站模板_PHP实例教程_网站源码_常用代码-素材牛 (sucainiu.com)](https://www.sucainiu.com/)

![image-20221001094503288](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221001094503288.png)

![image-20221001200003096](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221001200003096.png)

![image-20221002103302948](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221002103302948.png)

![image-20221002104924629](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221002104924629.png)

## controler

![image-20221002112155967](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221002112155967.png)

![image-20221002112213387](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221002112213387.png)

![image-20221002113214806](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221002113214806.png)


					1. 使用提前准备好的HttpClient请求
								在common中添加相关依赖
										 <!-- httpclient4.5版本 -->
										<dependency>
											<groupId>org.apache.httpcomponents</groupId>
											<artifactId>httpclient</artifactId>
											<version>4.5.3</version>
										</dependency>
	
										<dependency>
											<groupId>com.alibaba</groupId>
											<artifactId>fastjson</artifactId>
											<version>1.2.36</version>
										</dependency>
									</dependencies>
						
						2. 在web中加入dom4j依赖
							<dependency>
	
	 <dependency>
	            <groupId>org.dom4j</groupId>
	            <artifactId>dom4j</artifactId>
	            <version>2.0.0</version>
	        </dependency>

![image-20221002115335005](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221002115335005.png)

## xml规则

				Status Code: 200
				Time：240ms
				Date：Thu 12 Nov 2020 09:42:41 GMT
				Body：
				{
					"code": "10000",
					"charge": false,
					"remain": 0,
					"msg": "查询成功",
					"result": "<?xml version=\"1.0\" encoding=\"utf-8\" ?><returnsms>\n <returnstatus>Success</returnstatus>\n <message>ok</message>\n <remainpoint>-6850193</remainpoint>\n <taskID>162317289</taskID>\n <successCounts>1</successCounts></returnsms>"
					}

大体框架：

先判断是否连接成功，成功之后解析xml，如果成功之，则将数据保存到redis中，然后前端点击获取验证码时隐藏错误，而输入验证码框进行失去焦点非空验证

点击注册按钮进行后端进行判断redis中的数据和前端传入的验证码是否相等

**注意**

将返回的结果和查询和设置redis进行封装

代码：

```
public  Map<String,Object> verifaction(String phone) throws Exception {
        //这个时返回前端的map
        HashMap<String, Object> params = new HashMap<>();
        params.put("appkey", "cb4d78665c1313a4222d7add081df9f3");
        params.put("mobile", phone);
        String RanDomNumer = gerRandom(6);
        String content="【凯信通】您的验证码是："+ RanDomNumer;
        params.put("content",content);
        //然后生成一个随机验证码方法
        String url = "https://way.jd.com/kaixintong/kaixintong";
        String jsonStr = HttpClientUtils.doPost("https://way.jd.com/kaixintong/kaixintong",params);
//        String jsonStr ="{\n" +
//                "    \"code\": \"10000\",\n" +
//                "    \"charge\": false,\n" +
//                "    \"remain\": 0,\n" +
//                "    \"msg\": \"查询成功\",\n" +
//                "    \"result\": \"<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\" ?><returnsms>\\n <returnstatus>Success</returnstatus>\\n <message>ok</message>\\n <remainpoint>-1600642</remainpoint>\\n <taskID>120359706</taskID>\\n <successCounts>1</successCounts></returnsms>\"\n" +
//                "}";

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        //先解析code，等于10000则连接成功，然后解析下面的值
        String code = jsonObject.getString("code");
        if (!StringUtils.equals(code,"10000")) {
           return Result.erro("发送失败");

        }
            //然后解析xml
            String resultxml = jsonObject.getString("result");
            Document document1 = DocumentHelper.parseText(resultxml);
            Node retuannode = document1.selectSingleNode("//returnstatus");
            String text = retuannode.getText();
            if (!StringUtils.equals(text,"Success")) {

                return Result.erro("验证码发送失败");
            }
            //发送成功的时候加入到redis中
            redisService.put(phone,RanDomNumer);
        return Result.success("验证码发送成功");
    }
```

# 实名验证

同样跟那个一样的方法，用的是同一个验证码方法，实名成功后将信息保存在数据库

# 注册后也页面修改

![image-20221005192904694](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221005192904694.png)

# 用户退出

重定向到首页

![image-20221007090221804](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221007090221804.png)

```
重定向的话可以用于同一个页面并且刷新
```

# 图片验证码

## 工具类

```java
package com.bjpowernode.springboot.cons.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;


public class ImageVerificationCode {

    private int weight = 100;           //验证码图片的长和宽
    private int height = 40;
    private String text;                //用来保存验证码的文本内容
    private Random r = new Random();    //获取随机数对象
    //private String[] fontNames = {"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};   //字体数组
    //字体数组
    private String[] fontNames = {"Georgia"};
    //验证码数组
    private String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

    /**
     * 获取随机的颜色
     *
     * @return
     */
    private Color randomColor() {
        int r = this.r.nextInt(225);  //这里为什么是225，因为当r，g，b都为255时，即为白色，为了好辨认，需要颜色深一点。
        int g = this.r.nextInt(225);
        int b = this.r.nextInt(225);
        return new Color(r, g, b);            //返回一个随机颜色
    }

    /**
     * 获取随机字体
     *
     * @return
     */
    private Font randomFont() {
        int index = r.nextInt(fontNames.length);  //获取随机的字体
        String fontName = fontNames[index];
        int style = r.nextInt(4);         //随机获取字体的样式，0是无样式，1是加粗，2是斜体，3是加粗加斜体
        int size = r.nextInt(10) + 24;    //随机获取字体的大小
        return new Font(fontName, style, size);   //返回一个随机的字体
    }

    /**
     * 获取随机字符
     *
     * @return
     */
    private char randomChar() {
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }

    /**
     * 画干扰线，验证码干扰线用来防止计算机解析图片
     *
     * @param image
     */
    private void drawLine(BufferedImage image) {
        int num = r.nextInt(10); //定义干扰线的数量
        Graphics2D g = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = r.nextInt(weight);
            int y1 = r.nextInt(height);
            int x2 = r.nextInt(weight);
            int y2 = r.nextInt(height);
            g.setColor(randomColor());
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 创建图片的方法
     *
     * @return
     */
    private BufferedImage createImage() {
        //创建图片缓冲区
        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        //设置背景色随机
        g.setColor(new Color(255, 255, r.nextInt(245) + 10));
        g.fillRect(0, 0, weight, height);
        //返回一个图片
        return image;
    }

    /**
     * 获取验证码图片的方法
     *
     * @return
     */
    public BufferedImage getImage() {
        BufferedImage image = createImage();
        Graphics2D g = (Graphics2D) image.getGraphics(); //获取画笔
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++)             //画四个字符即可
        {
            String s = randomChar() + "";      //随机生成字符，因为只有画字符串的方法，没有画字符的方法，所以需要将字符变成字符串再画
            sb.append(s);                      //添加到StringBuilder里面
            float x = i * 1.0F * weight / 4;   //定义字符的x坐标
            g.setFont(randomFont());           //设置字体，随机
            g.setColor(randomColor());         //设置颜色，随机
            g.drawString(s, x, height - 5);
        }
        this.text = sb.toString();
        drawLine(image);
        return image;
    }

    /**
     * 获取验证码文本的方法
     *
     * @return
     */
    public String getText() {
        return text;
    }

    public static void output(BufferedImage image, OutputStream out) throws IOException                  //将验证码图片写出的方法
    {
        ImageIO.write(image, "JPEG", out);
    }
}
```

## 生成图片传给前端

```java
@RequestMapping("getVerifiCode")
@ResponseBody
public void getVerifiCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
/*
     1.生成验证码
     2.把验证码上的文本存在session中
     3.把验证码图片发送给客户端
     */
    ImageVerificationCode ivc = new ImageVerificationCode();     //用我们的验证码类，生成验证码类对象
    BufferedImage image = ivc.getImage();  //获取验证码
    request.getSession().setAttribute("text", ivc.getText()); //将验证码的文本存在session中
    ivc.output(image, response.getOutputStream());//将验证码图片响应给客户端
}
```

## 前端获取数据验证

```java
@RequestMapping("/Login_authentication")
@ResponseBody
public Result Login_authentication(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "message") String message) throws IOException, ServletException {
    request.setCharacterEncoding("utf-8");
    String session_vcode=(String) request.getSession().getAttribute("text");    //从session中获取真正的验证码
    System.out.println(session_vcode);
    if(!StringUtils.equals(message,session_vcode)){
     return   Result.erro("验证码不正确");
    }else{
        return Result.success("验证成功");
    }

}
```

## html

```java
<input type="text" id="migtext" name="migtext"  class="yzm" placeholder="请输入验证码" style="border: 1px solid buttonface;height: 30px;width:150px;left: 83px;top: 20px;position: relative"/>
<a href="javascript:getVerifiCode()">
    <img id="yzm_img" style="cursor:pointer;width: 100px;height: 36px;left:110px;top:20px;border-radius: 3px; position: relative" title="点击刷新验证码" src="/p2p/getVerifiCode"/>
</a>
```

```java
<script type="text/javascript">
    function getVerifiCode() {
        $("#yzm_img").prop('src','/p2p/getVerifiCode?a='+new Date().getTime());
    }
</script>
```

# 登录的时候设置redis和session，如果有redis就直接返回index页面

![image-20221009113626034](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221009113626034.png)

# 登录页面时相当于免密登录

![image-20221009114740839](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221009114740839.png)

![image-20221009153942808](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221009153942808.png)

![image-20221009161214197](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221009161214197.png)

# 注册的时候用redis是存手机号和验证码的（实现免登录的一直是session）

![image-20221009162038920](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221009162038920.png)

# 大致流程

先点验证码，如果都成功则发送验证码然后后台发送验证码，发送后存入redis（phone,messCode），点击注册然后通过redis中message判断写入的验证码是否正确。正确之后存入数据库，然后存入session做免登录。

登录的时候

先判断是都有session没有则进入登录页面

有则进入index

# 用户中心

![image-20221010202351415](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221010202351415.png)

# 用户投资

![image-20221010203850815](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221010203850815.png)

![image-20221010205833073](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221010205833073.png)

![image-20221010204505129](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221010204505129.png)

先是失去焦点事件，然后计算利息传值

![image-20221010211346378](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221010211346378.png)

## 内联脚本拿不到值问题

![image-20221010215005282](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221010215005282.png)

# 点击投资按钮

1.触发失去焦点事件，让输入框返回值，如果是true的话则进行下面的

2.投资时，先判断是否登录，然后是否实名

3.投资的金额必须大于你的余额，

4.投资金额必须大于最小投资金额

5.投资金额必须小于最大投资金额

6.投资金额必须小于剩余可投金额

都成功之后再请求后台

![image-20221012190021387](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221012190021387.png)

![image-20221013111211500](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221013111211500.png)

还有用户的id，再者就是更新其他的记录

![image-20221013111307269](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221013111307269.png)

# 投资排行榜

![image-20221013112939162](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221013112939162.png)

![image-20221013113113713](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221013113113713.png)

![image-20221013113359249](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221013113359249.png)

因为传了三个值所以不能用foreach

# 生成收益计划

## task定时器（定期去查询产品状态为1的产品，生成收益）

![image-20221013224002271](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221013224002271.png)

![image-20221013224103698](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221013224103698.png)

![image-20221013224830553](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221013224830553.png)

## 环境的搭建

![image-20221014095644884](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221014095644884.png)

# 项目的配置

![image-20221014150158912](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221014150158912.png)

![image-20221014150355027](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221014150355027.png)

# 收益计划

![image-20221014152738214](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221014152738214.png)

![image-20221014152751401](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221014152751401.png)

![image-20221014163136706](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221014163136706.png)

# 返还收益

![image-20221014211317718](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221014211317718.png)

![image-20221014214253554](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221014214253554.png)

然后返回的是原有的本金加上投资的钱和收益

![image-20221014222753523](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221014222753523.png)

![image-20221014223008364](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221014223008364.png)

```

```

![image-20221014223452900](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221014223452900.png)

# 支付功能



![image-20221015140038148](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221015140038148.png)

# 块钱支付

![image-20221015140701904](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221015140701904.png)

![image-20221015140746707](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221015140746707.png)

![image-20221015141203563](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221015141203563.png)

<img src="C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221015141345023.png" alt="image-20221015141345023" style="zoom:150%;" />

![image-20221015141629822](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221015141629822.png)

![image-20221015141830907](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221015141830907.png)

# 块钱支付的demo

![image-20221015152936293](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221015152936293.png)

![image-20221015153246947](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221015153246947.png)

![image-20221015153839756](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221015153839756.png)

![image-20221015154001487](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221015154001487.png)

## 出现的问题

![image-20221016194800496](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221016194800496.png)

![image-20221016195118482](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221016195118482.png)

返回表单页面

![image-20221017192224085](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221017192224085.png)

## 获取中文路径无效问题

![image-20221017200955173](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221017200955173.png)

# 异步通知的处理

![image-20221018104717262](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221018104717262.png)![image-20221018104720194](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221018104720194.png)

![image-20221018115948910](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221018115948910.png)

![image-20221018121533923](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221018121533923.png)

## 细节

![image-20221018122718169](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221018122718169.png)

![image-20221018122804461](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221018122804461.png)

![image-20221018122829191](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221018122829191.png)

# 内网穿透

![image-20221019173655147](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221019173655147.png)

直接用这个

![image-20221019173753353](C:\Users\lan'ru'zhan\AppData\Roaming\Typora\typora-user-images\image-20221019173753353.png)

然后开放3389端口

https://blog.csdn.net/qq_39339588/article/details/126615373?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-1-126615373-blog-124123325.pc_relevant_3mothn_strategy_recovery&spm=1001.2101.3001.4242.2&utm_relevant_index=4
