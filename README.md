说明
============================================================

activemq-java-sample 是一个使用 ActiveMQ 的示例。

* 在 jms 包中，使用了 Java JMS API。
* 在 spring 包中，使用了 Spring JMS。

使用
-------------------------------------------------------

启动 ActiveMQ Server

    mvn activemq:run

如果你安装了独立版本，可以在命令行（Mac OS X）

    ./activemq start

详细的使用说明，请参考 ActoiveMQ 的官方网站。

示例一：javamail ，演示了异步的邮件发送和接收。
-------------------------------------------------------

在 Producer 中，在 Map 中封装了消息内容：可以自定义的邮件发送地址，接收地址，主题，内容。
在 Consumer 中，接收消息，然后调用 SendMail 服务。完成邮件的异步发送。
在 SendMail 中，定义了 SMTP Server 的相关配置。在调用之前，需要先在 mail.properties 中输入相关的帐号密码。
这里以 Gmail 为例，通过 SSL 来完成邮件的发送。

示例二：[thumbnailator](http://code.google.com/p/thumbnailator/) ，演示了异步生成缩略图。
-------------------------------------------------------

把 thumbnailator 加入到自己的 Maven 代理仓库：

    mvn deploy:deploy-file \
    -DgroupId=com.googlecode \
    -DartifactId=thumbnailator \
    -Dversion=0.3.10 \
    -Dpackaging=jar \
    -Dfile=Thumbnailator-0.3.10-all.jar \
    -Durl=http://10.4.247.93/nexus/content/repositories/thirdparty \
    -DrepositoryId=thirdparty


to be continue...


