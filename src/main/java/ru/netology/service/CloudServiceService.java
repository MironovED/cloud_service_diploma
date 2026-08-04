package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.entity.Auth;
import ru.netology.entity.File;
import ru.netology.exception.BadCredentialsException;
import ru.netology.exception.ErrorGetFilesException;
import ru.netology.exception.UnauthorizedErrorException;
import ru.netology.pojo.FileInfo;
import ru.netology.repository.CloudServiceRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CloudServiceService {
    private CloudServiceRepository cloudServiceRepository;

    public CloudServiceService(CloudServiceRepository cloudServiceRepository) {
        this.cloudServiceRepository = cloudServiceRepository;
    }

    /**
     * Метод авторизации и предоставления токена клиенту
     * @param   auth    логин и пароль пользователя
     * @return          токен в виде строки в 32 символа
     */
    public Optional<String> login(Auth auth) {
        if (cloudServiceRepository.checkUser(auth)) {
            var token = generateToken();
            try {
                cloudServiceRepository.addToken(token);
                return Optional.ofNullable(token);
            } catch (RuntimeException e) {
                throw new BadCredentialsException();
            }
        }
        return Optional.empty();
    }

    /**
     * Метод удаления сохраненного токена авторизации
     * @param token     действующий токен
     */
    public void logout(String token){
        cloudServiceRepository.remoteToken(getSplitToken(token));
    }

    /**
     * получить список файлов
     * @param rawToken      токен
     * @param limit         количество выводимых объектов
     * @return              List<FileInfo>
     */
    public List<FileInfo> getListFiles(String rawToken, Integer limit) {
        List<FileInfo> listFileInfo = new ArrayList<>();
        String token = getSplitToken(rawToken);
        if(!checkToken(token)) {
            throw new UnauthorizedErrorException();
        }
        var listFile = cloudServiceRepository.getListFiles();
        if(listFile.isEmpty()) {
            throw new ErrorGetFilesException();
        }
        for (File file : listFile) {
            listFileInfo.add(convertFromFileToFileInfo(file));
        }
        if(limit != null) {
            listFileInfo = listFileInfo.stream().limit(3).toList();
        }
        return listFileInfo;
    }

    /**
     * Забирает нужные данные из объекта File и возвращает созданный объект FileInfo
     * @param file      объект File
     * @return          объект FileInfo
     */
    public FileInfo convertFromFileToFileInfo(File file) {
        Path path = Paths.get(file.getFile());
        try {
            return new FileInfo(path.getFileName().toString(), (int) Files.size(path));
        } catch (IOException e){
            throw new ErrorGetFilesException();
        }
    }

    /**
     * Метод генерации токена сессии
     * @return      сгенерированная строка длиной 32 символа
     */
    public String generateToken() {
        char[] charArray = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();
        SecureRandom random = new SecureRandom();
        char[] token = new char[62];
        for(int i = 0; i < 62; i++) {
            int randomIndex = random.nextInt(32);
            token[i] = charArray[randomIndex];
        }
        return new String(token);
    }

    /**
     * Метод получения токена из значения хидера
     * @param rawToken      необработанный токен из хидера
     * @return              чистое значение токена
     */
    public String getSplitToken(String rawToken) {
        String[] splitToken = rawToken.split(" ");
        return splitToken[1];
    }

    /**
     * Проверка существования токена
     * @param token      токен из запроса
     * @return              true or false
     */
    public Boolean checkToken(String token) {
        return cloudServiceRepository.checkToken(token);
    }

}
