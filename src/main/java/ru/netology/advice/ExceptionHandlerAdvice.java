package ru.netology.advice;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.exception.BadCredentialsException;
import ru.netology.exception.ErrorInputDataException;
import ru.netology.exception.ErrorUploadFileException;
import ru.netology.exception.UnauthorizedErrorException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ErrorInputDataException.class)
    public ResponseEntity<String> errorInputDataException() {
        return new ResponseEntity<>("Error input data", HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(ErrorUploadFileException.class)
    public ResponseEntity<String> errorUploadFileException() {
        return new ResponseEntity<>("Error upload file", HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseEntity<String> unauthorizedErrorException() {
        return new ResponseEntity<>("Unauthorized error", HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentialsException() {
        return new ResponseEntity<>("Bad credentials", HttpStatusCode.valueOf(400));
    }
}
