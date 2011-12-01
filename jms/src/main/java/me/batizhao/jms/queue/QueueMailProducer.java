package me.batizhao.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 这是一个异步发送邮件的 Producer 示例。
 * @author: batizhao
 * @since: 11-11-30 下午3:39
 */
public class QueueMailProducer {

    public static final String brokerURL = "tcp://localhost:61616";
    public static final String queueName = "jms.queue.mail";

    //在启动程序之前，需要输入正确的 Mail 相关信息。
    public static void main(String[] args) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put("from_address", "");
        map.put("to_address", "");
        map.put("subject", "");
        map.put("content", "");

        QueueMailProducer producer = new QueueMailProducer();
        producer.run(map);
    }

    public void run(Map<String, String> map) throws JMSException {

        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);

        Message message = session.createMapMessage();
        message.setStringProperty("from_address", map.get("from_address"));
        message.setStringProperty("to_address", map.get("to_address"));
        message.setStringProperty("subject", map.get("subject"));
        message.setStringProperty("content", map.get("content"));
        producer.send(message);

        System.out.println("Send mail to ActiveMQ success.");

        if (connection != null) {
            connection.close();
        }
    }
}
