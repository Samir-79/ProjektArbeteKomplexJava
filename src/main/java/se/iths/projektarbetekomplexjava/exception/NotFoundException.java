package se.iths.projektarbetekomplexjava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }
}
