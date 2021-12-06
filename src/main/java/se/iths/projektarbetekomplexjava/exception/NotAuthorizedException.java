package se.iths.projektarbetekomplexjava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(String message) {
        super(new ResponseStatusException(HttpStatus.UNAUTHORIZED, message));
    }
}
