基于企业微信和logback日志系统的异常日志报警

### 使用
在logback的配置文件中新增`WechatAlarmAppender`

```xml
<appender name="WECHAT_APPENDER" class="com.beautyboss.slogen.errorlog.monitor.appender.WechatAlarmAppender">
    <!--使用该组件的应用名称    -->
    <appName>test</appName>
    <!-- 发送微信消息的线程池的核心线程数量-->
    <coreThreadNum>1</coreThreadNum> 
    <!-- 发送微信消息的线程池的最大线程数量-->
    <maxThreadNum>2</maxThreadNum>
    <!--环境-->
    <env>dev</env>
    <!--企业微信群机器人webhookurl地址-->
    <webHookUrl>这里配置微信群机器人webhook地址</webHookUrl>
</appender>

<root level="${root.log.level}">
    <appender-ref ref="APP_FILE"/>
    <appender-ref ref="ERROR_FILE" />
    <appender-ref ref="STDOUT"/>
    <!--新增appender-->
    <appender-ref ref="WECHAT_APPENDER"/>
</root>
```

上面这些参数都是可以不同环境不同数据的。

```xml
<springProperty scope="context" name="appName" source="appName"/>
<springProperty scope="context" name="coreThreadNum" source="coreThreadNum"/>
<springProperty scope="context" name="maxThreadNum" source="maxThreadNum"/>
<springProperty scope="context" name="env" source="spring.profiles.active"/>
<springProperty scope="context" name="webHookUrl" source="webHookUrl"/>


<appender name="WECHAT_APPENDER" class="com.beautyboss.slogen.errorlog.monitor.appender.WechatAlarmAppender">
    <!--使用该组件的应用名称    -->
    <appName>${appName}</appName>
    <!-- 发送微信消息的线程池的核心线程数量-->
    <coreThreadNum>${coreThreadNum}</coreThreadNum> 
    <!-- 发送微信消息的线程池的最大线程数量-->
    <maxThreadNum>${maxThreadNum}</maxThreadNum>
    <!--环境-->
    <env>${env}</env>
    <!--企业微信群机器人webhookurl地址-->
    <webHookUrl>${webHookUrl}</webHookUrl>
</appender>
```
然后在不同环境的配置文件中配置不同的数据。

