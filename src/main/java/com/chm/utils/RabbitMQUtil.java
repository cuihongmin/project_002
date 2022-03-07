package com.chm.utils;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtil {

    public static Connection getConnection() throws IOException, TimeoutException{
        // 1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2.在工厂对象中设置MQ的连接信息（ip,port,virtualhost,username,password)
        factory.setHost("39.107.40.98");
        factory.setPort(5672);
        factory.setVirtualHost("host1");
        factory.setUsername("chm");
        factory.setPassword("admin123");
        // 3.通过工厂对象获取与MQ的连接
        Connection connection = factory.newConnection();
        return connection;
    }
}
