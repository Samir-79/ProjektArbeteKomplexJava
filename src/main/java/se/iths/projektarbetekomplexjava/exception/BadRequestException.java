package se.iths.projektarbetekomplexjava.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message, Throwable throwable){
        super(message, throwable);
    }
}
