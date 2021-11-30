package se.iths.projektarbetekomplexjava.exception;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
