package me.batizhao.jms.queue;

import me.batizhao.mail.MailServices;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 这是一个异步发送邮件的 Consumer 示例。
 * @author: batizhao
 * @since: 11-11-30 下午4:23
 */
public class QueueMailConsumer {

    public static final String brokerURL = "tcp://localhost:61616";
    public static final String queueName = "jms.queue.mail";

    public static void main(String[] args) throws JMSException {
        QueueMailConsumer consumer = new QueueMailConsumer();
        consumer.run();
    }

    public void run() throws JMSException {

        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                MapMessage msg = (MapMessage) message;
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("from_address", msg.getStringProperty("from_address"));
                    map.put("to_address", msg.getStringProperty("to_address"));
                    map.put("subject", msg.getStringProperty("subject"));
                    map.put("content", msg.getStringProperty("content"));

                    //从 Queue 中拿到相关的消息后，调用 Mail 服务发送邮件。
                    MailServices mailServices = new MailServices();
                    mailServices.sendMail(map);

                } catch (JMSException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
