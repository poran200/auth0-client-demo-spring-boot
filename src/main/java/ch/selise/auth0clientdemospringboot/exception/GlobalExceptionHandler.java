package ch.selise.auth0clientdemospringboot.exception;

import com.auth0.exception.APIException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//
//    @ExceptionHandler(value = APIException.class)
//    public ResponseEntity<Object> handleAuthApiException(){
//        return ResponseEntity.ok(a)
//    }
//}
