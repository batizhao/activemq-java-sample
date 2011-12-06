说明
============================================================

`activemq-java-sample` 是一个使用 ActiveMQ 的示例。

* 在核心模块中，提供了三个服务。邮件、缩略图、文件转换。
* 在 `jms` 模块中，使用了 Java JMS API。演示了邮件的异步发送。
* 在 `spring` 模块中，使用了 Spring JMS。分别用 `Queue` 演示了缩略图和 `Topic` 演示了文件转换服务。

使用
-------------------------------------------------------

启动 ActiveMQ Server

    mvn activemq:run

如果你安装了独立版本，可以在命令行运行（Mac OS X）

    ./activemq start

如果是使用独立运行模式，可以通过 WebConsole 对 Queue/Topic 进行监控。
详细的使用说明，请参考 ActiveMQ 的官方网站。

在 jms 模块中，演示了异步的邮件发送。
-------------------------------------------------------

这里以 Gmail 为例，通过 SSL 来完成邮件的发送。

* `QueueMailProducer` 是这个示例的入口，在 Map 中封装了消息内容：邮件发送地址，接收地址，主题，内容。
在启动程序之前，请输入消息的内容。程序运行以后，会把相关消息发送到 Queue 。
* 运行 QueueMailConsumer ，从 Queue 中接收消息，自动调用 MailServices 服务。完成邮件的异步发送。
* 在 MailServices 中，定义了 SMTP Server 的相关配置。在调用之前，需要先在 mail.properties 中输入 SMTP 的帐号密码。
* 在 queue 包中，还有一个基于 Text 的最简单的消息示例。
* 在 topic 包中，演示了 MQ 中另外一种 Pub/Sub 模式。

在 spring 模块中，演示了异步生成缩略图和 Doc 格式转换 PDF 。
-------------------------------------------------------

在 `ThumbnailProducer` 中，通过传递一个“图片路径”消息，异步生成一张缩略图。在项目的 data 目录下生成一张缩略图。

在启动程序之前，需要把 [thumbnailator](http://code.google.com/p/thumbnailator/) 库加入到自己的 Maven 代理仓库。

    mvn deploy:deploy-file \
    -DgroupId=com.googlecode \
    -DartifactId=thumbnailator \
    -Dversion=0.3.10 \
    -Dpackaging=jar \
    -Dfile=Thumbnailator-0.3.10-all.jar \
    -Durl=http://10.4.247.93/nexus/content/repositories/thirdparty \
    -DrepositoryId=thirdparty

可以看到，在使用了 Spring JmsTemplate 之后，代码量大大减少。
并且，根本都不需要再写 Consumer 类，只要把实现 onMessage 方法的 Listener 注册到 Spring 的容器中，就可以实现消息的接收。

在 `ConverterProducer` 中，通过传递一个“Doc文件路径”，异步转换成 PDF 格式。

在程序启动之前，需要安装 OpenOffice 。然后运行以下命令：

    ./soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard

你可以把 OpenOffice 和 jodconverter 结合起来做成一个文件转换的服务。
支持的格式及其它详细的说明请参照 [jodconverter 的官方文档](http://www.artofsolving.com/opensource/jodconverter)。
另外，在 [stackoverflow](http://stackoverflow.com/questions/6201736/javausing-apache-poi-how-to-convert-ms-word-file-to-pdf) 上，
还有人提出可以结合 `Apache POI` 和 `iText` 完成转换工作。这部分工作暂时没有实现，你可以自己验证。
