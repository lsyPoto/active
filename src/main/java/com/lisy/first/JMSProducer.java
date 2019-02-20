package com.lisy.first;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ��Ϣ�������ߣ������ߣ�
 *
 * @author Administrator
 *
 */
public class JMSProducer {


    public static void main(String[] args) {
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
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            //���Ĳ���ͨ��Session����Destination����ָ����һ���ͻ�������ָ��������ϢĿ���������Ϣ��Դ�Ķ�����PTPģʽ�У�Destination������Queue�����У���Pub/Subģʽ��Destination������Topic�����⡣�ڳ����п���ʹ�ö��Queue��Topic��
            Destination destination = session.createQueue("HelloWorld");

            //���岽��������Ҫͨ��Session���󴴽���Ϣ�ķ��ͺͽ��ն��������ߺ������ߣ�MessageProducer/MessageConsumer��
            MessageProducer producer = session.createProducer(destination);

            //�����������ǿ���ʹ��MessageProducer��setDeliveryMode����Ϊ�����ó־û����Ժͷǳ־û����ԣ�DeliveryMode���������Ժ���ϸ���ܡ�
            //producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            //���߲����������ʹ��JMS�淶��TextMessage��ʽ�������ݣ�ͨ��Session���󣩣�����MessageProducer��send�����������ݡ�ͬ��ͻ���ʹ��receive�������н������ݡ����Ҫ���ǹر�Connection���ӡ�

            for(int i = 0 ; i < 10 ; i ++){
                ObjectMessage msg = session.createObjectMessage(new User("a0", i));

                producer.send(msg);
                System.out.println("������Ϣ��" + msg.getObject().toString());
                session.commit(); //��������ʱ�ǵ��ύ���񣬲�Ȼ���Ѷ˽��ղ�����Ϣ
                Thread.sleep(1000);
            }

            if(connection != null){
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}