package ru.netology.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.entity.Auth;
import ru.netology.entity.AuthToken;
import ru.netology.entity.FileData;
import ru.netology.exception.ErrorDeleteFileException;
import ru.netology.exception.ErrorGetFilesException;
import ru.netology.exception.ErrorUploadFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static ru.netology.utils.HashUtil.generateHash;

@Transactional
@Repository
public class CloudServiceRepository {

    @Value("${files.path}")
    private String filesPath;

    @PersistenceContext
    private EntityManager entityManager;

    public CloudServiceRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Метод проверки клиента в БД
     * @param auth      объект Auth, содержащий логин и пароль
     * @return          true or false
     */
    public Boolean checkUser(Auth auth) {
        String sqlQuery = "SELECT a FROM Auth a WHERE a.login = :login";
        var query = entityManager.createQuery(sqlQuery, Auth.class);
        query.setParameter("login", auth.getLogin());
        Optional<Auth> result = query.getResultStream().findFirst();
        return result.isPresent();
    }

    /**
     * Сохраняет часть информации в таблицу БД, а сам файл кладет в фс
     * @param file          файл
     * @param fileName      имя файла
     */
    public void saveFile(MultipartFile file, String fileName){
        String path = filesPath + "/" + fileName;
        try(FileOutputStream fos = new FileOutputStream(path)) {
            entityManager.persist(new FileData(generateHash(file), path));
            byte[] bytes = file.getBytes();
            fos.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ErrorUploadFileException();
        }
        //todo добавить обработку, если мы пытаемся загрузить два одинаковых файла.
    }

    /**
     * Переименовывает файл в фс и меняет path в БД
     * @param name          текущее имя файла
     * @param newName       новое имя файла
     */
    public void updateFileByName(String name, String newName) {
        String currentPath = filesPath + "/" + name;
        String newPath = filesPath + "/" + newName;
        String sqlQuery = "UPDATE FileData f SET f.path = :new WHERE f.path = :old";
        var query = entityManager.createQuery(sqlQuery);
        query.setParameter("new", newPath);
        query.setParameter("old", currentPath);
        query.executeUpdate();
        entityManager.clear();

        Path currentFile = Path.of(currentPath);
        Path newFile = Path.of(newPath);
        try {
            Files.move(currentFile, newFile);
        } catch (IOException e) {
            throw new ErrorUploadFileException();
        }
    }

    /**
     * Получить объект FileData из таблицы
     * @param       fileName имя искомого файла
     * @return      FileData
     */
    public FileData getFileByFileName(String fileName) {
        try {
            String sqlQuery = "SELECT f FROM FileData f WHERE f.path = :path";
            var query = entityManager.createQuery(sqlQuery, FileData.class);
            String path = filesPath + "/" + fileName;
            query.setParameter("path", path);
            return query.getSingleResult();
        } catch (RuntimeException e) {
            throw new ErrorGetFilesException();
        }
    }

    /**
     * Удалить файл по имени
     * @param fileName      имя удаляемого файла
     */
    public void deleteFileByName(String fileName) {
        String path = filesPath + "/" + fileName;
        try {
            FileData file = entityManager.find(FileData.class, path);
            entityManager.remove(file);
            File fileDelete = new File(path);
            fileDelete.delete();
        } catch (RuntimeException e) {
            throw new ErrorDeleteFileException();
        }
    }

    /**
     * Получить список объектов FileData из таблицы
     * @return      List<FileData>
     */
    public List<FileData> getListFiles() {
        String sqlQuery = "SELECT f FROM FileData f";
        var query = entityManager.createQuery(sqlQuery, FileData.class);
        return query.getResultList();
    }

    /**
     * Метод проверки существования токена в таблице
     * @param token     токен
     * @return          true or false
     */
    public Boolean checkToken(String token) {
        AuthToken authToken = entityManager.find(AuthToken.class, token);
        return authToken != null;
    }

    /**
     * Метод добавления токена в БД
     * @param token     сгенерированный токен
     */
    public void addToken(String token) {
        entityManager.persist(new AuthToken(token));
    }

    /**
     * Метод удаления токена из таблицы в БД
     * @param token     действующий токен
     */
    public void remoteToken(String token) {
        AuthToken authToken = entityManager.find(AuthToken.class, token);
        entityManager.remove(authToken);
    }
}
