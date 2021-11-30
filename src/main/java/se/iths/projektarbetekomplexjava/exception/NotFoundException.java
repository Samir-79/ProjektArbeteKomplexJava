package se.iths.projektarbetekomplexjava.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}
