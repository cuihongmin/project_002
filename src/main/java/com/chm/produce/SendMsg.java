package com.chm.produce;

import com.chm.utils.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendMsg {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();
//        channel.txSelect(); // 开启事务
        /*try {
            String msg = "测试消息888";
            channel.basicPublish("ex4","queue1",null,msg.getBytes());
            channel.txCommit();  // 提交事务
        } catch (IOException e) {
           channel.txRollback();  // 事务回滚
        } finally {
            channel.close();
            connection.close();
        }*/
        // 使用Java代码创建队列
          //1.定义队列 （使用Java代在MQ中新建一个队列）
          //参数1： 定义队列名称
          //参数2： 队列中的数据是否持久化 （如果选择了持久化）
          //参数3： 是否排外 （当前队列是否为当前连接私有）
          //参数4： 自动删除 （当此队列的连接数为0时，此队列会销毁 （无论队列中是否还有数据))
          //参数5： 设置当前队列的参数
//        channel.queueDeclare("queue10",true,false,false,null);
//        channel.queueDeclare("queue11",true,false,false,null);
//        channel.queueDeclare("queue12",true,false,false,null);

        // 创建交换机 （订阅交换机）
//        channel.exchangeDeclare("ex3", BuiltinExchangeType.FANOUT);

        // 将队列绑定到交换机
//        channel.queueBind("queue11","ex3","");
//        channel.queueBind("queue12","ex3","");

        // String var1,  交换机名称，如果消息直接发送到队列，则传递空串“”
        // String var2,  目标消息队列名称，如果第一个参数不为空，则需要通过第二个参数指定队列名称
        // BasicProperties var3,  设置当前这条消息的参数（过期时间）
        // byte[] var4,  消息
        String msg = "测试消息1";

        channel.confirmSelect(); // 告诉这个链接，我需要监听消息的发送结果。
        channel.basicPublish("ex3","",null,msg.getBytes());
//         boolean b = channel.waitForConfirms(); // 获取发送结果  (同步)
//         System.out.println("---b为："+b);

          // 异步监听（消息确认机制）
        channel.addConfirmListener(new ConfirmListener() {
                // 参数1 返回消息的表示
                // 参数2 boolean b 是否为批量confirm
            public void handleAck(long l, boolean b) throws IOException {
                System.out.println("----消息发送到交换机成功！");
            }

            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("-----消息发送到交换机失败！");
            }
        });
        // return机制
        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int i, String s, String s1, String s2, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                System.out.println("消息从交换机分发到队列失败！");
                System.out.println("s1:"+s1);  // 交换机名称
                System.out.println("s2:"+s2);  // 队列名称
                System.out.println("bytes:"+new String(bytes));  // 分发的消息内容
            }
        });
        System.out.println("---wahaha");

//        channel.close();
//        connection.close();
    }
}