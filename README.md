# CleverBankTask

**Используемый стек технологий:**

Java 17, JDBC, Servlet (JakartaEE 10), Gradle 7.5, PostgreSQL 14, Tomcat 10, Git, JUnit 5, Apache Log4j2, Lombok, Docker, SnakeYAML.

**Общее описание видения предметной области:**

Задание выполнено как некая платежная система, оперирующая данными разных пользователей и банков.

**Краткое описание структуры:**

Тестовое задание реализовано в виде веб-проекта c Layered Architecture, с использованием паттернов: Singleton, Factory, Command, Builder.
Основные слои: controller, service, dao, bean, dto.
Инверсия зависимостей реализована через фабрики, организующие доступ к интерфейсам каждого слоя.
Доступ к базе данных в JDBC осуществляется через ConnectionPool (имплементация находится в подпакете connectionpool в слое dao).
Инициализация ConnectionPool происходит в методе init класса FrontController (он же сервлет).
В этом же методе осуществляется старт потоков (threads) для проверки и начисления процентов.
Значения для начисления процентов храняться в файле \src\main\resources\percentage.yml.
В \src\ располагаются следущие папки:
1. postgres - содержит dockerfile для создания image базы данных с интегрированной бд (инструкция ниже).
2. tomcat - также содержит dockerfile и war-архив проекта для создания image Tomcat-a с уже развернутым на сервере приложением (инструкция ниже).
3. javadoc - здесь располагается сгенерированная документация по проекту.
4. check - примеры чеков.

**Инструкция по запуску проекта:**

1. Используя Docker по следующим шагам:
1.1 Из директории \src\postgres выполнить следующую комманду:
docker image build -t clever-task-db
1.2 Из директории \src\tomcat выполнить следующую комманду:
docker image build -t tomcat-clever-bank
1.3 Для запуска контейнеров выполнить следующие комманды:
docker run -p 127.0.0.1:5432:5432 -e POSTGRES_PASSWORD=qwerty clever-db
docker run -it --rm -p 8080:8080 tomcat-clever-bank
2. Без Docker-a по следующим шагам:
2.1 Импортировать в IDE (c IntelliJ IDEA работает точно) как Gradle-проект.
2.2 Поменять логин и пароль к БД на необходимые в файле \src\main\resources\db.properties.
2.3 Создать таблицы в БД (необходимые SQL-запросы в папке \src\postgres\sql).
2.4 Запустить в IDE через Tomcat 10.
   
**Эндпоинты:**

Хотелось бы отметить, что ввиду запрета на Spring и Hibernate, проект создавался как классическое веб-приложение на сервлетах и jsp. 
Также REST и, соответственно, JSON никоим образом не подразумевались мной (и не требовались по заданию) как элементы проекта.
По причине данных обстоятельств относительно наглядно продемонстрировать входные и выходные данные возможность есть только скриншотами из Postman-a
(располагаются в \src\CRUDexamples)
Без веб-интерфейса направить Put и Delete запросы в body через Postman не получилось.

В текстовом виде есть возможность привести лишь основные паттерны запросов:
1. CREATE (POST request (метод doPost httpservlet)):
localhost:8080/CleverBankTask/FrontController 
в body данного запроса включить следующие параметры:
NewBankName:ExampleBank2 (или другое имя банка)
check:ADD_NEW_BANK

2. READ (GET request (метод doGet httpservlet)):
localhost:8080/CleverBankTask/FrontController?check=READ_BANK&BankName=ExampleBank2

3. UPDATE (PUT request (метод doPut httpservlet), метода doPatch у стандартного httpservlet нет (и это не REST проект)):
localhost:8080/CleverBankTask/FrontController?check=UPDATE_BANK
в body данного запроса включить следующие параметры:
check:UPDATE_BANK
OldBankName:ExampleBank2
NewBankName:ExampleBank2NEW

4. DELETE (DELETE request (метод doDelete httpservlet)):
localhost:8080/CleverBankTask/FrontController
в body данного запроса включить следующие параметры:
check:DELETE_BANK
BankName:ExampleBank2NEW

**Обращаю внимание, что при запуске проекта в Docker использовать следующий паттерн запросов:**
**localhost:8080/APP/FrontController**
**Остальные элементы как и в описании выше.**

Спасибо за прочтение и приятной проверки)
