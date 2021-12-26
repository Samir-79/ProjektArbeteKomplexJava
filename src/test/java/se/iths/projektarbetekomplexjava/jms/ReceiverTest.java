package se.iths.projektarbetekomplexjava.jms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiverTest {

    private Receiver receiver;

    @BeforeEach
    void init(){
        this.receiver = new Receiver();
    }

    @Test
    @DisplayName("")
    public void ReceiveMessage(){

    }
}
