package se.iths.projektarbetekomplexjava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid user")
public class NotAuthorizedException extends Exception{
    public NotAuthorizedException(String message) {
        super(message);
    }
}
