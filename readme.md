#Drools 和 Play 结合使用

##Kie Drools脚本服务器的搭建
参考文章[规则引擎KIE Drools服务器搭建](http://blog.csdn.net/lifetragedy/article/details/51143914)

###安装环境
1. Linux 服务器
2. Tomcat
3. Mysql  (因为本地使用我采用的是H2)

###Tomcat 环境配置

1. 相关依赖包导入tomca/lib中
2. tomcat中环境变量和配置文件导入,用户权限配置

###注意事项
>最新版的[Drools 6.4.0.Final](http://download.jboss.org/drools/release/6.4.0.Final/kie-drools-wb-distribution-wars-6.4.0.Final-tomcat7.war)我一直安装失败,我使用的也和以上教程中[Drools 6.3.0.Final](http://download.jboss.org/drools/release/6.3.0.Final/)

配置完成后,启动Tomcat.可以直接访问进入kie-workbeanch的首页.第一次启动时kie会在你配置的Mysql中生成INFORMATION_SCHEMA 数据库和相应的数据表,主要存储你的kie 配置信息.

##创建规则工程
![](http://7xor08.com1.z0.glb.clouddn.com/drools_login.png)
此处的登陆名为Tomcat/conf/tomcat-users.xml 中配置的用户名
![](http://7xor08.com1.z0.glb.clouddn.com/drools_tomcat_cong.png)

1. 新建资料库(项目文件文件夹)
>编写->管理->资料库->新建资料库

![](http://7xor08.com1.z0.glb.clouddn.com/ziliaoku.png)
**可以通过Git管理**
2. 创建规则工程
>编写->项目编写->新建条目->新建项目

可以看到这是一个标准的Maven工程.可以通过编写pom.xml引入相关jar包,便于在创建用户对象,或者编写dsl脚本文件时使用.
![](http://7xor08.com1.z0.glb.clouddn.com/droolsCost.png)
3. 创建用户对象
>新建条目->用户对象

![](http://7xor08.com1.z0.glb.clouddn.com/userduixiang.png)
![](http://7xor08.com1.z0.glb.clouddn.com/usercode.png)

你可以在编辑器中新增,也可以直接在源码中直接编写Java代码的User对象.最终结构都一样.

4. 创建规则脚本文件

>新建条目->DRL文件


```java
package cn.bmkp.jiang.droolscost;
import java.util.*;
import java.util.Date;
import java.time.LocalTime;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import java.util.*;
import models.User;

function JSONArray toJsonTest(){
    String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
    Object obj = JSONValue.parse(s);
    return (JSONArray)obj;
}

rule basicCost
    no-loop true
    when
        u:User(distance<3,waitTime<5);
    then
        u.setCost(8.0);
        update(u);
    end
    
rule longDistance  
    no-loop true
    when 
        u:User(distance>3,waitTime<=5);
    then
        u.setCost(8.0+(u.distance-3)*1.5);
        update(u);
    end

rule waitCost 
    no-loop true
    when 
        u:User(distance<3,waitTime<5);
    then 
        u.setCost(8.0+(u.waitTime-5)*0.1);
        update(u);
    end
    
rule LongDistancLongWaitTime
    no-loop true
    when
        u:User(distance>3,waitTime>5); 
    then
        u.setCost(8.0+(u.distance-3)*1.5+(u.waitTime-5)*0.1);
        update(u);
    end
 
rule busyTime 
    no-loop true
    when
        //isBusy()
        eval(isBusy())
        u:User()
    then
        u.setCost(u.getCost()*2);
end
        
function Boolean isBusy() {
    LocalTime time = LocalTime.now();
    int hour = time.getHour();
    return (hour<20&&hour>9) ? true : false;
}
     
```


5. 测试脚本文件
>新建条目->测试场景


6. 发布规则工程
测试如果没有问题就可以发布了
>打开项目编辑器->保存->发布

我们可以在`编写->资产资料库`中看到我们发布的规则生成jar包内容.
每一个项目工程最终都会生成一个唯一的jar文件.我们的规则内容也就包含在其中.请copy出你对应的路径内容.我们在Play中就是根据次来调用规则内容.
![](http://7xor08.com1.z0.glb.clouddn.com/jarku.png)


##Play 中调用规则内容

1. sbt 中引入需要的kie jar 文件

```scala
name := """drool-demo-sample"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "org.kie" % "kie-ci" % "6.3.0.Final"

libraryDependencies += "org.kie" % "kie-api" % "6.3.0.Final"

libraryDependencies += "org.drools" % "drools-core" % "6.3.0.Final"

libraryDependencies += "org.drools" % "drools-compiler" % "6.3.0.Final"

libraryDependencies += "org.apache.maven" % "maven-core" % "3.3.9"

libraryDependencies += "org.apache.maven" % "maven-artifact" % "3.3.9"

//Tomcat Maven配置库
resolvers += "Guvnor M2 Repo" at "http://localhost:8080/kie-drools/maven2/"

fork in run := true

routesGenerator := InjectedRoutesGenerator
```

2. 调用规则执行规则
![](http://7xor08.com1.z0.glb.clouddn.com/usercostresult.png)

String url 即为我们在Kie中droolsCost的路径

3. 测试接口