package ru.netology.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.entity.Auth;
import ru.netology.entity.FileData;
import ru.netology.exception.*;
import ru.netology.dto.FileInfo;
import ru.netology.repository.CloudServiceRepository;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
     * Сохранить файл в БД
     * @param fileName      имя файла
     * @param file          файл
     */
    public void saveFile(String fileName, MultipartFile file){
            cloudServiceRepository.saveFile(file, fileName);
    }

    /**
     * Выгрузка файла из БД
     * @param   fileName      имя файла
     * @return  FileData      дто
     */
    public FileData getFile(String fileName) {
        return cloudServiceRepository.getFileByFileName(fileName);
    }

    /**
     * Меняем у существующего файла его название
     * @param fileName      действующее имя
     * @param newFileName   новое имя
     */
    public void editFile(String fileName, String newFileName) {
        try {
            cloudServiceRepository.updateFileByName(fileName, newFileName);
        } catch (RuntimeException e) {
            throw new ErrorUploadFileException();
        }
    }

    /**
     * Удаление файла из БД и фс
     * @param fileName      имя файла
     */
    public void deleteFile(String fileName) {
        cloudServiceRepository.deleteFileByName(fileName);
    }

    /**
     * получить список файлов
     * @param limit         количество выводимых объектов
     * @return              List<FileInfo>
     */
    public List<FileInfo> getListFiles(Integer limit) {
        List<FileInfo> listFileInfo = new ArrayList<>();
        List<FileData> listFile;
        try {
            listFile = cloudServiceRepository.getListFiles();
            if(!listFile.isEmpty()) {
                for (FileData file : listFile) {
                    listFileInfo.add(convertFromFileToFileInfo(file));
                }
            }
            return listFileInfo.stream().limit(Objects.requireNonNullElse(limit, 3)).toList();
        } catch (RuntimeException e) {
            throw new ErrorGetFilesException();
        }
    }

    /**
     * Забирает нужные данные из объекта File и возвращает созданный объект FileInfo
     * @param file      объект File
     * @return          объект FileInfo
     */
    public FileInfo convertFromFileToFileInfo(FileData file) {
        File currentFile = new File(file.getPath());
        return new FileInfo(currentFile.getName(), (int)currentFile.length());
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
     * @param rawToken      токен из запроса
     * @return              true or false
     */
    public Boolean checkToken(String rawToken) {
        return cloudServiceRepository.checkToken(getSplitToken(rawToken));
    }
}
