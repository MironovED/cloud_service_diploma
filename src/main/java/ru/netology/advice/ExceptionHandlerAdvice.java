package ru.netology.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.exception.ErrorInputDataException;
import ru.netology.exception.ErrorUploadFileException;
import ru.netology.exception.UnauthorizedErrorException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ErrorInputDataException.class)
    public ResponseEntity<String> errorInputDataException() {
        return new ResponseEntity<>("Error input data", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorUploadFileException.class)
    public ResponseEntity<String> errorUploadFileException() {
        return new ResponseEntity<>("Error upload file", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseEntity<String> unauthorizedErrorException() {
        return new ResponseEntity<>("Unauthorized error", HttpStatus.UNAUTHORIZED);
    }
}
