package se.iths.projektarbetekomplexjava.jms;

import com.rabbitmq.client.*;
import se.iths.projektarbetekomplexjava.entity.ERole;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

public class Sender {

    public static void sendUser(String firstName, String lastName, String email, String username, String address, String phone, ERole role) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setHost("RabbitMqManager");

        try(Connection connection = factory.newConnection()) {

            Channel channel = connection.createChannel();
            String QUEUE_NAME = "userData";
            channel.queueDeclare(QUEUE_NAME, false, false, false,  null);
            String message = firstName + ", " + lastName + ", "  + address + ", " + phone
                    + ", " + username + ", "  + email + ", " +  role + ", "  + LocalDateTime.now();
            System.out.println("======> Transmitting this message:  " + message);

            channel.basicPublish("", QUEUE_NAME,null, message.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static void sendItem(String ISBN13, String title, LocalDate publishingDate, Long weight, int pages, String language, String category, Long price) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setHost("RabbitMqManager");

        try(Connection connection = factory.newConnection()) {

            Channel channel = connection.createChannel();
            String QUEUE_NAME = "itemData";
            channel.queueDeclare(QUEUE_NAME, false, false, false,  null);
            String message = ISBN13 + ", " + title + ", "  + publishingDate + ", " + weight
                    + ", " + pages + ", "  + language + ", " +  category + ", "  + price + ", " + LocalDateTime.now();
            System.out.println("======> Transmitting this message:  " + message);

            channel.basicPublish("", QUEUE_NAME,null, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}