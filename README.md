A simple message sending service

Описание:
сервис отправки текстовых сообщений, в текущей версии только email (но предусмотреть в реализации возможностью дальнейшего расширения: СМС и различные мессенджеры и соцсети). 

Стек:
 - Java 17
 - Spring Boot 3.3.3
 - Liquibase
 - Lombok
 - Docker compose 3.8
 - Model Mapper 3.2.1
 - PostgreSQL 15
 - Open API 2.3.0

Запуск:
 - Необхожимо иметь JDK способный скомпилировать Java 17 (в докерфайле: 3.8.4-openjdk-17)
 - Необходимо иметь Maven, так как с помощью него производится сборка проекта
 - Необходимо иметь Docker с compose версии 3.8 или выше (или в compose.yml прописать нужную версию)
 - В директории проектра необходимо создать и заполнить файл ".env", пример будет дальше по тексту
 - В терминале из директории проекта необходимо выполнить команду ``` docker compose up ```, дождаться деплоя

По умолчанию приложение находится по адресу http://localhost:8181/ \
Получить доступ к OpenAPI можно по ссылке http://localhost:8181/swagger-ui/index.html

Пример .env файла: 
```dosini
MAIL_HOST=<your smtp host>              (example: smtp.yandex.ru)
MAIL_PORT=<port>                        (example: 465)
MAIL_SENDER=<your host login>           (example: y.mail)
MAIL_SENDER_MAIL=<sender email address> (example: y.mail@mail.com)
MAIL_PASSWORD=<password>                (example: fnjefhwoifiowejfiow)           
MAIL_PROTOCOL=smtps
MAIL_AUTH=true
MAIL_STARTTLS_ENABLE=true
EMAIL_DEBUG=true
```

Инструкция по настройке yandex smtp сервера: https://yandex.com/support/mail/mail-clients/others.html
