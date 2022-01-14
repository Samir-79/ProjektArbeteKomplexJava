package se.iths.projektarbetekomplexjava.jms;

import com.rabbitmq.client.*;
import se.iths.projektarbetekomplexjava.entity.Role;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

public class Sender {

    public static void sender(String firstName, String lastName, String email, String username, String address, String phone, Role role) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setHost("RabbitMqManager");

        try(Connection connection = factory.newConnection()) {

            Channel channel = connection.createChannel();
            String QUEUE_NAME = "findOutOne";
            channel.queueDeclare(QUEUE_NAME, false, false, false,  null);
            String message = firstName + ", " + lastName + ", "  + address + ", " + phone
                    + ", " + username + ", "  + email + ", " +  role + ", "  + LocalDateTime.now();
            System.out.println("======> Hello to Rabbitmq I send this message:  " + message);

            channel.basicPublish("", QUEUE_NAME,null, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}