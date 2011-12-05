package me.batizhao.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author: batizhao
 * @since: 11-12-5 下午3:56
 */
public class ConverterProducer {

    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring.xml");
        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTopicTemplate");

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("src/test/resources/02.doc");
            }
        });
    }
}
