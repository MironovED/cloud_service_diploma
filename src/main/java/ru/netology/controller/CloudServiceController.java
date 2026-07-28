package ru.netology.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.dto.Auth;

import java.util.List;

@RestController
@RequestMapping("/cloud")
public class CloudServiceController {
    private CloudServiceController cloudServiceController;

    public CloudServiceController(CloudServiceController cloudServiceController) {
        this.cloudServiceController = cloudServiceController;
    }

    /**
     * Авторизация по логину и паролю
     * @param auth
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<String> authorization(@RequestBody Auth auth) {
        return null;
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
