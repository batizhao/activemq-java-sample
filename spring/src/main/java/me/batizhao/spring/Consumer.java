package me.batizhao.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author: batizhao
 * @since: 11-11-21 下午3:47
 */
public class Consumer {

    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring.xml");
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        System.out.println("Message received: " + jmsTemplate.receiveAndConvert());

    }
}
