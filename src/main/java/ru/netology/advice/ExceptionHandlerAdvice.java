package ru.netology.advice;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.exception.*;
import ru.netology.dto.Error;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ErrorInputDataException.class)
    public ResponseEntity<Error> errorInputDataException() {
        return new ResponseEntity<>(new Error("Error input data", 400), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> badCredentialsException() {
        return new ResponseEntity<>(new Error("Bad credentials", 400), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseEntity<Error> unauthorizedErrorException() {
        return new ResponseEntity<>(new Error("Unauthorized error", 401), HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler(ErrorUploadFileException.class)
    public ResponseEntity<Error> errorUploadFileException() {
        return new ResponseEntity<>(new Error("Error upload file", 500), HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler(ErrorGetFilesException.class)
    public ResponseEntity<Error> errorGetFilesException() {
        return new ResponseEntity<>(new Error("Error getting file list", 500), HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler(ErrorDeleteFileException.class)
    public ResponseEntity<Error> errorDeleteFileException() {
        return new ResponseEntity<>(new Error("Error delete file", 500), HttpStatusCode.valueOf(500));
    }
}
