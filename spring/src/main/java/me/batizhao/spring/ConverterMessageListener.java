package me.batizhao.spring;

import me.batizhao.converter.DocToPdfServices;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.net.ConnectException;

/**
 * @author: batizhao
 * @since: 11-12-5 下午4:25
 */
public class ConverterMessageListener implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String fileName = ((TextMessage) message).getText();
                System.out.println("Message received: " + fileName);

                //调用文件转换服务
                DocToPdfServices services = new DocToPdfServices();
                services.doIt(fileName);

                System.out.println("Document has converted.");

            } catch (ConnectException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (JMSException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

}
