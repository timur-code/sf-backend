# sf-backend
## О проекте
Данный проект создан командой RANT для Software Factory

### Заметки
_Когда в ссылках стоит {}, например {userId}, {productId}, то замените скобки на реальный id объекта. То есть ссылка **_https://sf-rant-backend.herokuapp.com/user/{userId}/deposit_** станет **_https://sf-rant-backend.herokuapp.com/user/2/deposit_**.

## Установка
Для установки и запуска сервера необходимо скачать репозиторий, установить базу данных PostgreSQL и создать базу с названием "**SF-DB**", имя и пароль должны быть либо _postges, postgres_, либо поменяйте _spring.datasource.username_ и _spring.datasource.password_ в файле **application.properties**.

Для запуска используется Maven, IntelliJ Idea не требуется, но облегчит использование.

## Регистрация
Для регистрации пользователь должен ввести свою почту, полное имя, телефон и пароль (формат смотрите в [DTO/UserRequest.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/UserRequest.java)) и отправить **POST** запрос в **_https://sf-rant-backend.herokuapp.com/authorize_**. В ответ прийдет JWT токен, который front-end должен сохранить (в cookies, localstorage, keychains и т.д.) и использовать как **Authorization** header для каждого последующего запроса.

## Вход
Для входа пользователь должен ввести свою почту и пароль (формат смотрите в [DTO/AuthenticationRequest.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/AuthenticationRequest.java)) и отправить **POST** запрос в **_https://sf-rant-backend.herokuapp.com/authenticate_**. Если пользователь существует, то в ответ прийдет JWT токен, который front-end должен сохранить как (в cookies, localstorage, keychains и т.д.) и использовать как **Authorization** header для каждого последующего запроса.

## Получение данных пользователя
Для получения данных пользователя после входа или регистрации, сделайте **GET** запрос в **_https://sf-rant-backend.herokuapp.com/_** с JWT токеном как **Authorization** header. В ответ прийдет json объект, содержащий данные пользователя (формат данных как в [DTO/UserResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/UserResponse.java)). Эти пользовательские данные нужно сохранить в cookies, localstorage и т.д., чтобы сократить время загрузки м количество запросов на сервер. Эти данные, вместе с JWT токеном и **Authorization** header нужно удалять при выходе из аккаунта.

## Получение данных о балансе кошелька
Для получения баланса кошелька сделайте **GET** запрос в **_https://sf-rant-backend.herokuapp.com/user/{userId}/balance** с JWT токеном как **Authorization** header. В ответ прийдет json объект, содержащий данные пользователя (формат данных как в [DTO/WalletResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/WalletResponse.java)). Эти данные сохранять не нужно, так как кошелек может часто обновляться. Если один пользователь вывести баланс другого аккаунта, то выйдет ошибка. Желательно не давать такую возможность пользователя ещё с фронт-энда.

## Пополнение кошелька
Для пополнения кошелька сделайте **POST** запрос в **_https://sf-rant-backend.herokuapp.com/user/{userId}/deposit_** с JWT токеном как **Authorization** header. В ответ прийдет сообщение об успешной операции, если все в порядке. Если один пользователь попытается пополнить аккаунт другого, то выйдет ошибка. Желательно не давать такую возможность пользователя с фронт-энда. Единственная возможность пользователя пополнять кошельки другим должна быть через перевод.

## Снятие средств кошелька
Для снятия средств с кошелька сделайте **POST** запрос в **_https://sf-rant-backend.herokuapp.com/user/{userId}/withdraw_** с JWT токеном как **Authorization** header. В ответ прийдет сообщение об успешной операции, если все в порядке. Если один пользователь попытается снять деньги с другого аккаунта, то выйдет ошибка. Желательно не давать такую возможность пользователя ещё с фронт-энда.
