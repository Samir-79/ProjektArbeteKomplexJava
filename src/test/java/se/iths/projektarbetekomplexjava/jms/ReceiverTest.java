package se.iths.projektarbetekomplexjava.jms;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiverTest {

    private ConnectionFactory connectionFactory;

    @BeforeEach
    void init(){
        this.connectionFactory = new ConnectionFactory();
    }

    @Test
    @DisplayName("Open a new connection")
    public void NewConnection() throws IOException, TimeoutException {
        assertTrue(connectionFactory.newConnection().isOpen());
    }

    @Test
    @DisplayName("Create a new channel")
    public void NewChannel() throws IOException, TimeoutException {
        assertTrue(connectionFactory.newConnection().createChannel().isOpen());
    }

    @Test
    @DisplayName("Declare a queue")
    public void queueDeclare() throws IOException, TimeoutException {
        connectionFactory.newConnection().createChannel().queueDeclare().getQueue();
        assertTrue(true);
    }

    @Test
    @DisplayName("Consume a Queue")
    public void basicConsume() throws IOException, TimeoutException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("======> Hello from Rabbitmq I send this message:  " + message);
        };
        connectionFactory.newConnection().createChannel()
                .basicConsume("iamATest1", true, deliverCallback, consumerTag -> { });
        assertTrue(true);
    }
}
