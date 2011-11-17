package me.batizhao;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author: batizhao
 * @since: 11-11-17 下午4:20
 */
public class Producer extends Thread
{
    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public Producer(ConnectionFactory factory, String queueName) throws JMSException
    {
        this.factory = factory;
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        producer = session.createProducer(destination);
    }

    public void run()
    {
        for (int i = 0; i < 100; i++)
        {
            System.out.println("Creating Message " + i);
            Message message = null;
            try {
                message = session.createTextMessage("Hello World!");
                producer.send(message);
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }

    public void close() throws JMSException
    {
        if (connection != null)
        {
            connection.close();
        }
    }

    public static void main( String[] args ) throws Exception
    {
        String brokerURL = "tcp://localhost:61616";
        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);

        Producer producer = new Producer(factory, "test");
        producer.run();
        producer.close();
    }
}
