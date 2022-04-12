# sf-backend
## О проекте
Данный проект создан командой RANT для Software Factory

## Установка
Для установки и запуска сервера необходимо скачать репозиторий, установить базу данных PostgreSQL и создать базу с названием "SF-DB", имя и пароль должны быть либо postges, postgres, либо поменяйте spring.datasource.username и spring.datasource.password в файле application.properties.

Для запуска используется Maven, IntelliJ Idea не требуется, но облегчит использование.

## Регистрация
Для регистрации пользователь должен ввести свою почту, полное имя, телефон и пароль (формат смотрите в [DTO/UserRequest.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/UserRequest.java)) и отправить **POST** запрос в **_localhost:8080/authorize_**. В ответ прийдет JWT токен, который front-end должен сохранить (в cookies, localstorage, keychains и т.д.) и использовать как **Authorization** header для каждого последующего запроса.

## Вход
Для входа пользователь должен ввести свою почту и пароль (формат смотрите в [DTO/AuthenticationRequest.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/AuthenticationRequest.java)) и отправить **POST** запрос в **_localhost:8080/authenticate_**. Если пользователь существует, то в ответ прийдет JWT токен, который front-end должен сохранить как (в cookies, localstorage, keychains и т.д.) и использовать как **Authorization** header для каждого последующего запроса.

## Получение данных пользователя
Для получения данных пользователя после входа или регистрации, сделайте **GET** запрос в **_localhost:8080/_** с JWT токеном как **Authorization** header. В ответ прийдет json объект, содержащий данные пользователя (формат данных как в [DTO/UserResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/UserResponse.java)). Эти пользовательские данные нужно сохранить в cookies, localstorage и т.д., чтобы сократить время загрузки м количество запросов на сервер. Эти данные, вместе с JWT токеном и **Authorization** header нужно удалять при выходе из аккаунта.
