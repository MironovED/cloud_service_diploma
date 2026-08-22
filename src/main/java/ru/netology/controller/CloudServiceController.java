package ru.netology.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dto.EditInfo;
import ru.netology.entity.Auth;
import ru.netology.entity.AuthToken;
import ru.netology.entity.FileData;
import ru.netology.exception.BadCredentialsException;
import ru.netology.exception.ErrorInputDataException;
import ru.netology.dto.FileInfo;
import ru.netology.exception.ErrorUploadFileException;
import ru.netology.exception.UnauthorizedErrorException;
import ru.netology.service.CloudServiceService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cloud")
public class CloudServiceController {
    private static final Logger log = LoggerFactory.getLogger(CloudServiceController.class);
    private CloudServiceService cloudServiceService;

    public CloudServiceController(CloudServiceService cloudServiceService) {
        this.cloudServiceService = cloudServiceService;
    }

    /**
     * Авторизация по логину и паролю
     * @param auth      получаем логин и пароль
     * @return          возвращаем токен авторизации в теле ответа
     */
    @PostMapping("/login")
    public ResponseEntity<AuthToken> authorization(@RequestBody Auth auth) {
        log.info("POST /login {}", auth);
            var token = cloudServiceService.login(auth);
            if(token.isEmpty()){
                throw new BadCredentialsException();
            }
        return new ResponseEntity<>(new AuthToken(token.get()), HttpStatus.OK);
    }

    /**
     * Выход из текущий сессии клиента
     * @param token     действующий токен
     * @return          результат операции
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("auth-token") String token) {
        log.info("POST /logout");
        cloudServiceService.logout(token);
        return new ResponseEntity<>("Success logout", HttpStatus.OK);
    }

    /**
     * Принимаем и сохраняем файл в БД
     * @return      ответ об результате сохранения
     */
    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestHeader("auth-token") String token,
                                             @RequestParam("filename") String fileName,
                                             @RequestPart("file") MultipartFile file) {
        log.info("POST /file - Uploading file: {}", fileName);
        if(file.isEmpty() || fileName == null || token == null){
            throw new ErrorInputDataException();
        }
        if(!cloudServiceService.checkToken(token)) {
            throw new UnauthorizedErrorException();
        }
        cloudServiceService.saveFile(fileName, file);
        return new ResponseEntity<>("Success upload", HttpStatus.OK);
    }

    /**
     * Удаление файла
     * @return      результат завершения удаления
     */
    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestHeader("auth-token") String token,
                                             @RequestParam("filename") String fileName) {
        log.info("POST /file - Deleting file: {}", fileName);
        if(fileName == null || token == null) {
            throw new ErrorInputDataException();
        }
        if(!cloudServiceService.checkToken(token)) {
            throw new UnauthorizedErrorException();
        }
        cloudServiceService.deleteFile(fileName);
        return new ResponseEntity<>("Success deleted", HttpStatus.OK);
    }

    /**
     * Загрузить файл с сервера
     * @param token         токен
     * @param fileName      имя файла
     * @return              поток InputStreamResource
     */
    @GetMapping("/file")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestHeader("auth-token") String token,
                                                            @RequestParam("filename") String fileName) {
        log.info("POST /file - Downloading file: {}", fileName);
        if(fileName == null || token == null) {
            throw new ErrorInputDataException();
        }
        if(!cloudServiceService.checkToken(token)) {
            throw new UnauthorizedErrorException();
        }
        FileData file = cloudServiceService.getFile(fileName);
        try {
            Path path = Path.of(file.getPath());
            InputStream inputStream = Files.newInputStream(path);
            InputStreamResource resource = new InputStreamResource(inputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(Files.size(path));
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            return ResponseEntity.ok().headers(headers).body(resource);
        } catch (IOException e) {
            throw new ErrorUploadFileException();
        }
    }

    /**
     * Изменить имя файла
     * @param token         токен
     * @param fileName      имя файла
     * @param editInfo      новое имя файла
     * @return              результат
     */
    @PutMapping("/file")
    public ResponseEntity<String> editFile(@RequestHeader("auth-token") String token,
                                           @RequestParam("filename") String fileName,
                                           @RequestBody EditInfo editInfo) {
        log.info("PUT /file - Editing file: {}", fileName);
        if(token == null || fileName == null ) {
            throw new ErrorInputDataException();
        }
        if(!cloudServiceService.checkToken(token)) {
            throw new UnauthorizedErrorException();
        }
        cloudServiceService.editFile(fileName, editInfo.getName());
        return new ResponseEntity<>("Success upload", HttpStatus.OK);
    }

    /**
     * Получение списка файлов в количестве, указанном в query параметре
     * @param token     передаваемый токен в headers
     * @param limit     лимит на получение файлов из БД
     * @return          список файлов
     */
    @GetMapping("/list")
    public ResponseEntity<List<FileInfo>> getAllFiles(@RequestHeader("Auth-Token") String token,
                                                      @RequestParam(value = "limit", required = false) Integer limit) {
        log.info("GET /list - Number of output lines: {}", limit);
        if (token == null) {
            throw new ErrorInputDataException();
        }
        if(!cloudServiceService.checkToken(token)) {
            throw new UnauthorizedErrorException();
        }
        var listFiles = cloudServiceService.getListFiles(limit);
        return new ResponseEntity<>(listFiles, HttpStatus.OK);
    }
}
