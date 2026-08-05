package ru.netology.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dto.FileDto;
import ru.netology.entity.Auth;
import ru.netology.entity.AuthToken;
import ru.netology.entity.FileData;
import ru.netology.exception.BadCredentialsException;
import ru.netology.exception.ErrorInputDataException;
import ru.netology.dto.FileInfo;
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
     * @return          возвращаем токен авторизации в теле ответа
     */
    @PostMapping("/login")
    public ResponseEntity<AuthToken> authorization(@RequestBody Auth auth) {
            var token = cloudServiceService.login(auth);
            if(token.isEmpty()){
                throw new BadCredentialsException();
            }
        return new ResponseEntity<>(new AuthToken(token.get()), HttpStatus.OK);
    }

    /**
     * Выход из текущий сессии клиента
     * @param token     действующий токен
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("auth-token") String token) {
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
        if(file.isEmpty() || fileName == null || token == null){
            throw new ErrorInputDataException();
        }
        cloudServiceService.saveFile(token, fileName, file);
        return new ResponseEntity<>("Success upload", HttpStatus.OK);
    }

    /**
     * Удаление файла
     * @return      результат завершения удаления
     */
    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestHeader("auth-token") String token,
                                             @RequestParam("filename") String fileName) {
        if(fileName == null || token == null) {
            throw new ErrorInputDataException();
        }
        cloudServiceService.deleteFile(token, fileName);
        return new ResponseEntity<>("Success deleted", HttpStatus.OK);
    }

    //todo скорее всего нужно переделать
    @GetMapping("/file")
    public ResponseEntity<FileDto> downloadFile(@RequestHeader("auth-token") String token,
                                                @RequestParam("filename") String fileName) {
        if(fileName == null || token == null) {
            throw new ErrorInputDataException();
        }
        FileDto file = cloudServiceService.getFile(token, fileName);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @PutMapping("/file")
    public ResponseEntity<String> editFile() {
        return null;
    }

    /**
     * Получение списка файлов в количестве, указанном в query параметре
     * @param token     передаваемый токен в headers
     * @param limit     лимит на получение файлов из БД
     * @return          список файлов
     */
    @GetMapping("/list")
    public ResponseEntity<List<FileInfo>> getAllFiles(@RequestHeader("auth-token") String token,
                                                      @RequestParam(value = "limit", required = false) Integer limit) {
        if (token == null) {
            throw new ErrorInputDataException();
        }
        var listFiles = cloudServiceService.getListFiles(token, limit);
        return new ResponseEntity<>(listFiles, HttpStatus.OK);
    }

}
