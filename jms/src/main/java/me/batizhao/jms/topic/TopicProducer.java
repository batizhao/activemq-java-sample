package me.batizhao.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author: batizhao
 * @since: 11-11-30 下午3:17
 */
public class TopicProducer {

    public static final String brokerURL = "tcp://localhost:61616";
    public static final String topicName = "jms.topic.test";

    public static void main(String[] args) throws JMSException {
        TopicProducer producer = new TopicProducer();
        producer.run();
    }

    public void run() throws JMSException {

        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(topicName);
        MessageProducer producer = session.createProducer(destination);

        Message message = null;
        for (int i = 0; i < 10; i++) {
            System.out.println("Creating Message " + i);

            message = session.createTextMessage("Hello World! " + i);
            producer.send(message);
        }

        if (connection != null) {
            connection.close();
        }
    }
}
