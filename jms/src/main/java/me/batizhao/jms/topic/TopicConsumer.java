package me.batizhao.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author: batizhao
 * @since: 11-11-30 下午3:19
 */
public class TopicConsumer implements MessageListener {

    public static final String brokerURL = "tcp://localhost:61616";
    public static final String queueName = "jms.topic.test";

    public static void main(String[] args) throws JMSException {
        TopicConsumer consumer = new TopicConsumer();
        consumer.run();
    }

    public void run() throws JMSException {

        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(queueName);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);

    }

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage txtMessage = (TextMessage) message;
                System.out.println("Message received: " + txtMessage.getText());
            } else {
                System.out.println("Invalid message received.");
            }
        } catch (JMSException e) {
            System.out.println("Caught:" + e);
            e.printStackTrace();
        }
    }
}
