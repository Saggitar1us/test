# Test-demo

Тесты написаны с использованием следующих технологий: Java + Junit5 + Allure + Maven

## Структура тестов
 - Проверяется API основных запросов (POST, GET, DELETE)
 - Используется параметризация JUnit 5
 - Детализация прохождения тестов по шагам (@Step)

## Сборка 
Для сборки используется Maven
 `$ mvn clean install -Dmaven.test.skip=true`
## Запуск тестов
 `$ mvn clean test` 

## Создание отчета
 После прохождения тестов
- Создать отчет 
`$ mvn allure:report`
- Просмотреть отчет
`$ mvn allure:serve` 

