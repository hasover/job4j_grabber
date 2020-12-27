[![Build Status](https://travis-ci.org/hasover/job4j_grabber.svg?branch=master)](https://travis-ci.org/hasover/job4j_grabber)
[![codecov](https://codecov.io/gh/hasover/job4j_grabber/branch/master/graph/badge.svg?token=J1YDI59MZ8)](https://codecov.io/gh/hasover/job4j_grabber)
#job4j-grabber
Система запускается по расписанию. Период запуска указывается в настройках - app.properties. 
Первый сайт будет sql.ru. В нем есть раздел job. Программа должно считывать все вакансии относящие к Java и записывать их в базу.
Доступ к интерфейсу будет через REST API.