package com.lisy.first;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息的消费者（接受者）
 *
 * @author Administrator
 *
 */
public class JMSConsumer {


    public static void main(String[] args)  {
        try {
            //第一步：建立ConnectionFactory工厂对象，需要填入用户名、密码、以及要连接的地址，均使用默认即可，默认端口为"tcp://localhost:61616"
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    ActiveMQConnectionFactory.DEFAULT_USER,
                    ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                    "failover:(tcp://118.89.139.209:61616)?Randomize=false");
            connectionFactory.setTrustAllPackages(true);
            //第二步：通过ConnectionFactory工厂对象我们创建一个Connection连接，并且调用Connection的start方法开启连接，Connection默认是关闭的。
            Connection connection = connectionFactory.createConnection();

            connection.start();

            //第三步：通过Connection对象创建Session会话（上下文环境对象），用于接收消息，参数配置1为是否启用是事务，参数配置2为签收模式，一般我们设置自动签收。
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

            //第四步：通过Session创建Destination对象，指的是一个客户端用来指定生产消息目标和消费消息来源的对象，在PTP模式中，Destination被称作Queue即队列；在Pub/Sub模式，Destination被称作Topic即主题。在程序中可以使用多个Queue和Topic。
            Destination destination = session.createQueue("HelloWorld");
            //第五步：通过Session创建MessageConsumer
            MessageConsumer consumer = session.createConsumer(destination);

            while(true){
                ObjectMessage msg = (ObjectMessage)consumer.receive();
                if(msg == null) {
                    break;
                }
                System.out.println("收到的内容：" + msg.getObject().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}