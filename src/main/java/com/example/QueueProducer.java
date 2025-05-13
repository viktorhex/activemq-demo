package com.example;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class QueueProducer {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic("TEST.TOPIC");
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 10; i++){
            TextMessage message = session.createTextMessage("Hello " + i);
            producer.send(message);
        }

        session.close();
        connection.close();
    }
}