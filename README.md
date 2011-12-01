说明
============================================================

activemq-java-sample 是一个使用 ActiveMQ 的示例。

* 在 jms 模块中，使用了 Java JMS API。
* 在 spring 模块中，使用了 Spring JMS。

使用
-------------------------------------------------------

启动 ActiveMQ Server

    mvn activemq:run

如果你安装了独立版本，可以在命令行运行（Mac OS X）

    ./activemq start

如果是使用独立运行模式，可以通过 WebConsole 对 Queue/Topic 进行监控。
详细的使用说明，请参考 ActiveMQ 的官方网站。

在 jms 模块中，演示了异步的邮件发送和接收。
-------------------------------------------------------

这里以 Gmail 为例，通过 SSL 来完成邮件的发送。

* QueueMailProducer 是这个示例的入口，在 Map 中封装了消息内容：邮件发送地址，接收地址，主题，内容。
在启动程序之前，请输入消息的内容。程序运行以后，会把相关消息发送到 Queue 。
* 运行 QueueMailConsumer ，从 Queue 中接收消息，自动调用 MailServices 服务。完成邮件的异步发送。
* 在 MailServices 中，定义了 SMTP Server 的相关配置。在调用之前，需要先在 mail.properties 中输入 SMTP 的帐号密码。
* 在 queue 包中，还有一个基于 Text 的最简单的消息示例。
* 在 topic 包中，演示了 MQ 中另外一种 Pub/Sub 模式。

在 spring 模块中，演示了异步生成缩略图。
-------------------------------------------------------

这个例子是通过给 ThumbnailProducer 传递一个图片，异步生成一张缩略图。

在启动程序之前，需要把 [thumbnailator](http://code.google.com/p/thumbnailator/) 库加入到自己的 Maven 代理仓库。

    mvn deploy:deploy-file \
    -DgroupId=com.googlecode \
    -DartifactId=thumbnailator \
    -Dversion=0.3.10 \
    -Dpackaging=jar \
    -Dfile=Thumbnailator-0.3.10-all.jar \
    -Durl=http://10.4.247.93/nexus/content/repositories/thirdparty \
    -DrepositoryId=thirdparty

ThumbnailProducer 是这个程序的入口。通过消息，把原图路径传递给 Queue，Listener 收到消息以后，
把路径传递给 ThumbnailServices ，在项目的 data 目录下生成一张缩略图。

可以看到，在使用了 Spring JmsTemplate 之后，代码量大大减少。
并且，根本都不需要再写 Consumer 类，只要把实现 onMessage 方法的 Listener 注册到 Spring 的容器中，就可以实现消息的接收。


