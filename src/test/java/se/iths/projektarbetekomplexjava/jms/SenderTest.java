package se.iths.projektarbetekomplexjava.jms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SenderTest {

    private Sender sender;

    @BeforeEach
    void init(){
        this.sender = new Sender();
    }

    @Test
    @DisplayName("Test to send no message")
    public void SendNoMessageTest(){
        assertNotNull(sender);
    }
}
