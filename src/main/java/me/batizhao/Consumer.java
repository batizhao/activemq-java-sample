package me.batizhao;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author: batizhao
 * @since: 11-11-17 下午4:30
 */
public class Consumer implements MessageListener {

    public static final String brokerURL = "tcp://localhost:61616";
    public static final String queueName = "test";

    public static void main(String[] args) throws JMSException{
        Consumer consumer = new Consumer();
        consumer.run();
    }

    public void run() throws JMSException {

        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
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
