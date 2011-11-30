package me.batizhao.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: batizhao
 * @since: 11-11-30 下午3:39
 */
public class QueueMailProducer {

    public static final String brokerURL = "tcp://localhost:61616";
    public static final String queueName = "jms.queue.mail";

    public static void main(String[] args) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put("from_address", "Bati Zhao <zhaobati@gmail.com>");
        map.put("to_address", "zhaobati@gmail.com");
        map.put("subject", "Test for ActiveMQ");
        map.put("content", "Hi, This is test for ActiveMQ.");

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
