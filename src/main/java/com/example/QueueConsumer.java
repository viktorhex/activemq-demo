package com.example;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class QueueConsumer {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic("TEST.TOPIC");
        
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage) {
                    System.out.println("Received: " + ((TextMessage) message).getText());
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Waiting for messages...");
        // Keep running until interrupted
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupted status
        } finally {
            session.close();
            connection.close();
        }
    }
}