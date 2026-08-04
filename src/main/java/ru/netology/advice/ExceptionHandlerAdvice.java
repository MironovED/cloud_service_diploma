package ru.netology.advice;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.exception.BadCredentialsException;
import ru.netology.exception.ErrorInputDataException;
import ru.netology.exception.ErrorUploadFileException;
import ru.netology.exception.UnauthorizedErrorException;
import ru.netology.pojo.Error;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ErrorInputDataException.class)
    public ResponseEntity<Error> errorInputDataException() {
        return new ResponseEntity<>(new Error("Error input data", 400), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(ErrorUploadFileException.class)
    public ResponseEntity<Error> errorUploadFileException() {
        return new ResponseEntity<>(new Error("Error upload file", 500), HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseEntity<Error> unauthorizedErrorException() {
        return new ResponseEntity<>(new Error("Unauthorized error", 401), HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> badCredentialsException() {
        return new ResponseEntity<>(new Error("Bad credentials", 400), HttpStatusCode.valueOf(400));
    }
}
