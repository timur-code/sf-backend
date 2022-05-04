# sf-backend
## О проекте
Данный проект создан командой RANT для Software Factory

### Заметки
Когда в ссылках стоит {}, например {userId}, {productId}, то замените скобки на реальный id объекта. То есть ссылка **_https://sf-rant-backend.herokuapp.com/user/{userId}/deposit_** станет **_https://sf-rant-backend.herokuapp.com/user/2/deposit_**.

## Установка
Для установки и запуска сервера необходимо скачать репозиторий, установить базу данных PostgreSQL и создать базу с названием "**SF-DB**", имя и пароль должны быть либо _postges, postgres_, либо поменяйте _spring.datasource.username_ и _spring.datasource.password_ в файле **application.properties**.

Для запуска используется Maven, IntelliJ Idea не требуется, но облегчит использование.

## Регистрация
Для регистрации пользователь должен ввести свою почту, полное имя, телефон и пароль (формат смотрите в [DTO/UserRequest.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/UserRequest.java)) и отправить **POST** запрос в **_https://sf-rant-backend.herokuapp.com/authorize_**. В ответ придет JWT токен, который front-end должен сохранить (в cookies, localstorage, keychains и т.д.) и использовать как **Authorization** header для каждого последующего запроса.

## Вход
Для входа пользователь должен ввести свою почту и пароль (формат смотрите в [DTO/AuthenticationRequest.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/AuthenticationRequest.java)) и отправить **POST** запрос в **_https://sf-rant-backend.herokuapp.com/authenticate_**. Если пользователь существует, то в ответ придет JWT токен, который front-end должен сохранить как (в cookies, localstorage, keychains и т.д.) и использовать как **Authorization** header для каждого последующего запроса.

## Получение данных пользователя
Для получения данных пользователя после входа или регистрации, сделайте **GET** запрос в **_https://sf-rant-backend.herokuapp.com/_** с JWT токеном как **Authorization** header. В ответ придет json объект, содержащий данные пользователя (формат данных как в [DTO/UserResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/UserResponse.java)). Эти пользовательские данные нужно сохранить в cookies, localstorage и т.д., чтобы сократить время загрузки м количество запросов на сервер. Эти данные, вместе с JWT токеном и **Authorization** header нужно удалять при выходе из аккаунта.

## Получение данных о балансе кошелька
Для получения баланса кошелька сделайте **GET** запрос в **_https://sf-rant-backend.herokuapp.com/user/{userId}/balance_** с JWT токеном как **Authorization** header. В ответ придет json объект, содержащий данные пользователя (формат данных как в [DTO/WalletResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/WalletResponse.java)). Эти данные сохранять не нужно, так как кошелек может часто обновляться. Если один пользователь попытается вывести баланс другого аккаунта, то выйдет ошибка. Желательно не давать такую возможность пользователя ещё с фронт-энда.

## Пополнение кошелька
Для пополнения кошелька сделайте **POST** запрос в **_https://sf-rant-backend.herokuapp.com/user/{userId}/deposit_** с JWT токеном как **Authorization** header (формат запроса как в [DTO/DepositRequest.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/DepositRequest.java)). В ответ придет сообщение об успешной операции (200), если все в порядке. Если один пользователь попытается пополнить аккаунт другого, то выйдет ошибка (400). Желательно не давать такую возможность пользователя с фронт-энда. Единственная возможность пользователя пополнять кошельки другим должна быть через перевод.

## Снятие средств кошелька
Для снятия средств с кошелька сделайте **POST** запрос в **_https://sf-rant-backend.herokuapp.com/user/{userId}/withdraw_** с JWT токеном как **Authorization** header (формат запроса как в [DTO/WithdrawRequest.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/WithdrawRequest.java)). В ответ придет сообщение об успешной операции (200), если все в порядке. Если один пользователь попытается снять деньги с другого аккаунта, то выйдет ошибка (400). Желательно не давать такую возможность пользователя ещё с фронт-энда.

## Трансфер денег
Для трансфера денег от одного пользователя к другому сделайте **POST** запрос в **_https://sf-rant-backend.herokuapp.com/user/{userId}/transfer_** с JWT токеном как **Authorization** header (формат запроса как в [DTO/TransferRequest.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/TransferRequest.java)). В ответ придет сообщение об успешной операции (200), если все в порядке (формат ответа как в [DTO/TransferResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/TransferResponse.java)). Если один пользователь попробует перевести деньги с другого аккаунта, то выйдет ошибка (400). Желательно не давать такую возможность пользователя ещё с фронт-энда.

## Добавление товаров в магазин
Для добавления продукта нужно отправить **POST** запрос в **_https://sf-rant-backend.herokuapp.com/market/add_** с JWT токеном как **Authorization** header (формат запроса как в [DTO/ProductRequest.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/ProductRequest.java)). В ответ придет сообщение об успешной операции (200), если все в порядке (формат ответа как в [DTO/ProductResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/ProductResponse.java)).

## Получить информацию о товаре
Для получения продукта нужно отправить **GET** запрос в **_https://sf-rant-backend.herokuapp.com/market/{productId}/get_** с JWT токеном как **Authorization** header. В ответ придет сообщение об успешной операции (200), если все в порядке (формат ответа как в [DTO/ProductResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/ProductResponse.java)).

## Получить продукты, высставленные пользователем
Для получения продуктов нужно отправить **GET** запрос в **_https://sf-rant-backend.herokuapp.com/user/{userId}/products_** с JWT токеном как **Authorization** header. В ответ придет сообщение об успешной операции (200), если все в порядке (формат ответа это **массив** [DTO/ProductResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/ProductResponse.java)).

## Получить все продукты в магазине
Для получения продуктов нужно отправить **GET** запрос в **_https://sf-rant-backend.herokuapp.com/market/getAll_** с JWT токеном как **Authorization** header. В ответ придет сообщение об успешной операции (200), если все в порядке (формат ответа это **массив** [DTO/ProductResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/ProductResponse.java)).

## Добавление товаров в корзину
Для добавления продукта нужно отправить **POST** запрос в **_https://sf-rant-backend.herokuapp.com/market/{productId}/add_** с JWT токеном как **Authorization** header. В ответ придет сообщение об успешной операции (200), если все в порядке (формат ответа как в [DTO/ProductResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/ProductResponse.java)).

## Получить корзину пользователя
Для получения продуктов из корзины нужно отправить **POST** запрос в **_https://sf-rant-backend.herokuapp.com/market/cart_** с JWT токеном как **Authorization** header. В ответ придет сообщение об успешной операции (200), если все в порядке (формат ответа это **массив** [DTO/ProductResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/ProductResponse.java)).

## Купить товары в корзине
Для добавления продукта нужно отправить **POST** запрос в **_https://sf-rant-backend.herokuapp.com/market/cart/buy_** с JWT токеном как **Authorization** header. В ответ придет сообщение об успешной операции (200), если все в порядке (формат ответа это **массив** [DTO/ProductResponse.java](https://github.com/timur-code/sf-backend/blob/master/src/main/java/com/rant/sfbackend/DTO/ProductResponse.java)).
