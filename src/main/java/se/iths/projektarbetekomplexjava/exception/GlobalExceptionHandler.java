package se.iths.projektarbetekomplexjava.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({NotAuthorizedException.class})
    public ResponseEntity<Object> NotAuthorizedException(NotAuthorizedException ex){
        logger.info(ex.getClass().getName());
        String errorMessage = "Unauthorized user.";
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, errorMessage, ex));
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> BadRequestException(BadRequestException ex){
        logger.info(ex.getClass().getName());
        String errorMessage = "Missing data.";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, errorMessage, ex));
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> NotFoundException(NotFoundException ex){
        logger.info(ex.getClass().getName());
        String errorMessage = "User not found.";
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, errorMessage, ex));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("Error: ", ex);
        String errorMessage = "An unexpected error occurred.";
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, ex));
    }
}
