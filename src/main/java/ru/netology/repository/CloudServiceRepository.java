package ru.netology.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.netology.entity.Auth;
import ru.netology.entity.AuthToken;

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

    public Boolean checkToken(AuthToken authToken) {
        return true;
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
        entityManager.remove(new AuthToken(token));
    }
}
