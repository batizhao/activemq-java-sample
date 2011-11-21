package me.batizhao.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author: batizhao
 * @since: 11-11-17 下午4:20
 */
public class Producer {

    public static final String brokerURL = "tcp://localhost:61616";
    public static final String queueName = "test";

    public static void main(String[] args) throws JMSException {
        Producer producer = new Producer();
        producer.run();
    }

    public void run() throws JMSException {

        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
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
