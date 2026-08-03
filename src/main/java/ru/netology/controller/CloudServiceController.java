package ru.netology.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.dto.Auth;
import ru.netology.dto.AuthToken;
import ru.netology.exception.BadCredentialsException;
import ru.netology.service.CloudServiceService;

import java.util.List;

@RestController
@RequestMapping("/cloud")
public class CloudServiceController {
    private CloudServiceService cloudServiceService;

    public CloudServiceController(CloudServiceService cloudServiceService) {
        this.cloudServiceService = cloudServiceService;
    }

    /**
     * Авторизация по логину и паролю
     * @param auth      получаем логин и пароль
     * @return          токен авторизации
     */
    @PostMapping("/login")
    public ResponseEntity<AuthToken> authorization(@RequestBody Auth auth) {
        var token = cloudServiceService.login(auth);
        if(token.isEmpty()){
            throw new BadCredentialsException();
        }
        return new ResponseEntity<>(new AuthToken(token.get()), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return null;
    }

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile() {
        return null;
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile() {
        return null;
    }

    @GetMapping("/file")
    public ResponseEntity<String> downloadFile() {
        return null;
    }

    @PutMapping("/file")
    public ResponseEntity<String> editFile() {
        return null;
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> getAllFiles() {
        return null;
    }

}
