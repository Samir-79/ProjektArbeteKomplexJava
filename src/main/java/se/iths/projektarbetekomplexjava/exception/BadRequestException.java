package se.iths.projektarbetekomplexjava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(new ResponseStatusException(HttpStatus.BAD_REQUEST, message));
    }
}
