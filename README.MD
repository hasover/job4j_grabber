# job4j-grabber
[![Build Status](https://travis-ci.org/hasover/job4j_grabber.svg?branch=master)](https://travis-ci.org/hasover/job4j_grabber)
[![codecov](https://codecov.io/gh/hasover/job4j_grabber/branch/master/graph/badge.svg?token=J1YDI59MZ8)](https://codecov.io/gh/hasover/job4j_grabber)

* [Описание](#описание)
* [Технологии](#технологии)
* [Функционал](#функционал)
* [Контакты](#контакты)

## Описание
Приложение - парсер вакансий. Время периодического запуска указывается в настройках, данные сохраняются в БД, также данные можно просмотреть в браузере.

## Технологии
* Java14
* PostgreSQL
* JDBC
* Travis CI
* Maven

## Функционал

### 1. Сохранение данных  в БД
![alt text](https://github.com/hasover/job4j_grabber/blob/master/images/post1.PNG)
### 2. Просмотр данных в браузере
![alt text](https://github.com/hasover/job4j_grabber/blob/master/images/post2.PNG)

## Сборка приложения
- Для сборки приложения на вашем компьютере должны быть установлены:
    - JDK 14+
    - Maven
    - PostgreSQL
- Укажите настройки для подключения к БД в файле `resources/app.properties`
- Также укажите период time и порт port
- Выполните скрипт `ru/job4j/grabber/post.sql`
- Выполните команду `mvn package`
- Далее `java -jar target\job4j_grabber-1.0-SNAPSHOT.jar`

Также данные можно посмотреть по адресу `localhost:9000`

## Контакты
telegram: @hasover
