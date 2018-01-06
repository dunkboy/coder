# 子模块
## basic
公共模块，和业务无关的公共类

## model
领域模型模块，存放项目的领域模型

## libs
第三方库

## portal
目录结构规划
portal
        -${A} 如视频管理
          -dao
          -mapping
          -service
          -controller
        -${B} 如图片管理
          -dao
          -mapping
          -service
          -controller
门户模块，与前台页面对接，提供REST服务

## www
前台页面开发，打包输出静态文件，挂着负载均衡器上

1. npm install -g cnpm --registry=https://registry.npm.taobao.org
2. npm start

开发过程: 在redux中定义 => *.types.js -> *.actions.js -> *.reducers.js -> 在 components 中开发组件


# 规则和约束
## src/main/java/com/four/king/kong/*/dao：接口包
## src/main/java/com/four/king/kong/*/mapping：xml文件实现dao接口
## src/main/java/com/four/king/kong/*/service：接口包
## src/main/java/com/four/king/kong/*/service/support：接口的实现
## src/main/java/com/four/king/kong/*/controller：前端入口，spring mvc REST 风格


## 配置文件加载(放在这个目录下就会自动被加载了)
1. spring service: classpath*:/META-INF/*/service/*.service.xml
2. spring servlet: classpath*:/META-INF/*/servlet/*.servlet.xml

## spring bean全部用注解: @RestController, @Service
##依赖注入
@Autowired
@Qualifier(IHibernateOperations.SERVICE_ID)
private IHibernateOperations hibernateOperations;

## 有事物的在service层方法上加注解@Transactional，无事物的可以不加

## REST 规则
1、使用标准 spring mvc REST，网上一搜就很多了
2、请求总入口: /api/*, 配在 basic/src/main/webapp/WEB-INF/web.xml
3、区分模块，以便权限控制，如portal可以为: /services/portal/entity
4、按照四种：GET, POST, PUT, DELETE


## 前台规则
1. TODO

# 准备工作
1. 代码和运行是jdk1.8，所以没有的就要去下载了
2. 下载gradle http://gradle.org/gradle-download/
3. 配置环境变量，类似 mvn 的一样

# 工程导入 eclipse
1. cd portal
2. gradle eclipse
3. 执行到这里就已经生成了 .project .classpath .settings，在eclipse导入即可

# 工程导入 idea
在 idea 中 import... 弹出框中选中 build.gradle 就可以了

# 本地开发

## 开发步骤
1. 在eclipse、idea、vim 里面修改文件
2. cd portal
3. gradle jettyRun
4. 浏览器 -> http://127.0.0.1:8080/
5. 回到步骤1

## 调试
* 这么运行: gradle jettyRunDebug
* 在 eclipse、idea 里面远程调试就可以了，默认端口：8000
* 修改java文件：不修改方法名称不需要重启
* 修改静态文件：实时生效
