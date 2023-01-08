Запуск автотестов

Клонировать репозиторий на компьютер (https://github.com/Iraynay/AQA.Diplom.git)
Открыть проект в JetBrains IntelliJ IDEA
Запустить контейнер командой:
docker-compose up

Запустить приложение с MySQL командой:
java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" "-Dspring.datasource.username=app" "-Dspring.datasource.password=pass" -jar artifacts/aqa-shop.jar

Запустить приложение с PostgreSQL командой:
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" "-Dspring.datasource.username=app" "-Dspring.datasource.password=pass" -jar artifacts/aqa-shop.jar

Запустить тесты командой .\gradlew clean test -Durl=jdbc:mysql://localhost:3306/app -Duser=app -Dpassword=pass --info

Запустить тесты командой .\gradlew clean test -Durl=jdbc:postgresql://localhost:5432/app -Duser=app -Dpassword=pass --info
