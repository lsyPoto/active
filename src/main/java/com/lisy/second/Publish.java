package com.lisy.second;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Lisy on 2019/2/19.
 */
public class Publish {

    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public Publish(){
        try {
            factory = new ActiveMQConnectionFactory(null,null,
                                                    "failover:(tcp://118.89.139.209:61616)?Randomize=false");
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(null);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() throws Exception {
        Destination destination = session.createTopic("Topic002");
        TextMessage msg = session.createTextMessage("我是消息内容...");
        producer.send(destination, msg);

        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Publish publish= new Publish();
        publish.sendMessage();
    }

}
