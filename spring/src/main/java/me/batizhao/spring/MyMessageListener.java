package me.batizhao.spring;

import me.batizhao.thumbnail.ThumbnailServices;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author: batizhao
 * @since: 11-12-1 下午3:22
 */
public class MyMessageListener implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String fileName = ((TextMessage) message).getText();
                System.out.println("Message received: " + fileName);

                //调用缩略图转换服务
                ThumbnailServices services = new ThumbnailServices();
                services.doit(fileName);

            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
