package com.lisy.first;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ��Ϣ�������ߣ������ߣ�
 *
 * @author Administrator
 *
 */
public class JMSConsumer {


    public static void main(String[] args)  {
        try {
            //��һ��������ConnectionFactory����������Ҫ�����û��������롢�Լ�Ҫ���ӵĵ�ַ����ʹ��Ĭ�ϼ��ɣ�Ĭ�϶˿�Ϊ"tcp://localhost:61616"
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    ActiveMQConnectionFactory.DEFAULT_USER,
                    ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                    "failover:(tcp://118.89.139.209:61616)?Randomize=false");
            connectionFactory.setTrustAllPackages(true);
            //�ڶ�����ͨ��ConnectionFactory�����������Ǵ���һ��Connection���ӣ����ҵ���Connection��start�����������ӣ�ConnectionĬ���ǹرյġ�
            Connection connection = connectionFactory.createConnection();

            connection.start();

            //��������ͨ��Connection���󴴽�Session�Ự�������Ļ������󣩣����ڽ�����Ϣ����������1Ϊ�Ƿ����������񣬲�������2Ϊǩ��ģʽ��һ�����������Զ�ǩ�ա�
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

            //���Ĳ���ͨ��Session����Destination����ָ����һ���ͻ�������ָ��������ϢĿ���������Ϣ��Դ�Ķ�����PTPģʽ�У�Destination������Queue�����У���Pub/Subģʽ��Destination������Topic�����⡣�ڳ����п���ʹ�ö��Queue��Topic��
            Destination destination = session.createQueue("HelloWorld");
            //���岽��ͨ��Session����MessageConsumer
            MessageConsumer consumer = session.createConsumer(destination);

            while(true){
                ObjectMessage msg = (ObjectMessage)consumer.receive();
                if(msg == null) {
                    break;
                }
                System.out.println("�յ������ݣ�" + msg.getObject().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}