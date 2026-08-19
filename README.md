# Дипломный проект "Облачное хранилище" по профессии Java-разработчик
___

### Что делает проект

Работает с файлами, которые поступают по http запросам (GET, POST, DELETE, PUT) - сохраняет в БД, выгружает из БД, удаляет из БД, изменяет сохраненные файлы.

**Эндпоинты**

1. **cloud/files/{filename}** - GET - возвращает файл
2. **cloud/files** - POST - сохраняет файл
3. **cloud/files/{filename}** - DELETE - удаляет файл
4. **cloud/files/{filename}** - PUT - изменяет файл
5. **cloud/list** - GET - возвращает список файлов

**Структура**

| path                                 | Описание                                                       |
|:-------------------------------------|:---------------------------------------------------------------|
| _src/main/java/ru/netology/advice_     | обработчик ошибок                                              |
| _src/main/java/ru/netology/config_     | веб конфигурация (в allowedOrigins передает адрес веб сервера) |
| _src/main/java/ru/netology/controller_ | контроллеры                                                    |
| _src/main/java/ru/netology/dto_        | DTO                                                            |
| _src/main/java/ru/netology/entity_     | сущности БД                                                    |
| _src/main/java/ru/netology/exception_  | кастомные исключения                                           |
| _src/main/java/ru/netology/repository_ | репозиторий для работы с БД                                    |
| _src/main/java/ru/netology/service_    | сервис облачного хранилища                                     |
| _src/main/java/ru/netology/utils_      | лежит единственный утилитный класс для генерации хэш-кода      |
| _src/test/java/ru/netology_            | интеграционные и юнит тесты                                    |


### Как запустить

1. В веб конфигурации указать порт запущенного веб сервера `.allowedOrigins("http://localhost:8080")`
2. Собрать jar файл `mvn clean package`
3. Запустить команду докера `docker-compose up`

### Тестовые данные

test@test.com / qwerty12345

