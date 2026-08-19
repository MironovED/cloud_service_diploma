# Дипломная проект "Облачное хранилище" по профессии Java-разработчик
___

### Что делает проект

Работает с файлами, которые поступают по http запросам (GET, POST, DELETE, PUT) - сохраняет в БД, выгружает из БД, удаляет из БД, изменяет сохраненные файлы.

**Эндпоинты**

1. cloud/files/{filename} - GET - возвращает файл
2. cloud/files - POST - сохраняет файл
3. cloud/files/{filename} - DELETE - удаляет файл
4. cloud/files/{filename} - PUT - изменяет файл
5. cloud/list - GET - возвращает список файлов

**Структура**

| path                                 | Описание                                                       |
|:-------------------------------------|:---------------------------------------------------------------|
| src/main/java/ru/netology/advice     | обработчик ошибок                                              |
| src/main/java/ru/netology/config     | веб конфигурация (в allowedOrigins передает адрес веб сервера) |
| src/main/java/ru/netology/controller | контроллеры                                                    |
| src/main/java/ru/netology/dto        | DTO                                                            |
| src/main/java/ru/netology/entity     | сущности БД                                                    |
| src/main/java/ru/netology/exception  | кастомные исключения                                           |
| src/main/java/ru/netology/repository | репозиторий для работы с БД                                    |
| src/main/java/ru/netology/service    | сервис облачного хранилища                                     |
| src/main/java/ru/netology/utils      | лежит единственный утилитный класс для генерации хэш-кода      |
| src/test/java/ru/netology            | интеграционные и юнит тесты                                    |


### Как запустить

1. В веб конфигурации указать порт запущенного веб сервера `.allowedOrigins("http://localhost:8080")`
2. Собрать jar файл `mvn clean package`
3. Запустить команду докера `docker-compose up`

### Тестовые данные

test@test.com / qwerty12345

