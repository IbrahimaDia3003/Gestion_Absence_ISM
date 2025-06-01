package sn.ism.gestion.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InterceptorExceptionHandler {

    @ExceptionHandler({ EntityNotFoundExecption.class })
    public ResponseEntity<String> handleEntityNotFoundExecption(EntityNotFoundExecption ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
