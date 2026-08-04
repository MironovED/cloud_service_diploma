package ru.netology.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.netology.entity.Auth;
import ru.netology.entity.AuthToken;
import ru.netology.entity.File;
import ru.netology.pojo.FileInfo;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CloudServiceRepository {

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
     * Получить список объектов File из таблицы
     * @return      List<File>
     */
    public List<File> getListFiles() {
        String sqlQuery = "SELECT f FROM File f";
        var query = entityManager.createQuery(sqlQuery, File.class);
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
