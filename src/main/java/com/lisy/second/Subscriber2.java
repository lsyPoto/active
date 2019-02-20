package com.lisy.second;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Lisy on 2019/2/19.
 */
public class Subscriber2 {

    private ConnectionFactory factory;
    private Connection connection;
    private Session session;

    public Subscriber2() {
        try {
            factory = new ActiveMQConnectionFactory(null, null,
                                                    "failover:(tcp://118.89.139.209:61616)?Randomize=false");
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receive() throws Exception{
        Destination topic = session.createTopic("Topic002");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new Listener());
    }

    class Listener implements MessageListener {

        public void onMessage(Message message) {
            try {
                TextMessage tm = (TextMessage) message;
                System.out.println("Subscriber1 Received message: " + tm.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        Subscriber2 subscriber = new Subscriber2();
        subscriber.receive();
    }

}
