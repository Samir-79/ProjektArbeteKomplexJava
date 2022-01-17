package se.iths.projektarbetekomplexjava.jms;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Receiver {

    public static void receiveUser() throws Exception {

        String QUEUE_NAME = "userData";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setHost("RabbitMqManager");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false,  null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("<====== Transmitting this message:  " + message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }

    public static void receiveItem() throws Exception {

        String QUEUE_NAME = "itemData";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setHost("RabbitMqManager");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false,  null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("<====== Transmitting this message:  " + message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}