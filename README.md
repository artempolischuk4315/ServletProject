# ServletProject
Servlet Project


Система Быстрого Тестирования Студентов. Студент регистрируется
э-мейлом, к нему привязуеться его Успешность и на него будут приходить
сообщения о результате тестов. В системе существует каталог Тестов по
темам, авторизованный Студент может проходить тесты. В конце теста
должна на странице отобразится форма где показано ошибки студента. Все
данные об успеваемости и пройденных курсах отображаются на странице
Администратора как сводная таблица по всем Студентам.

******
Requirements
******

JDK 1.8
Apache Tomcat
Apache Maven
MySQL


******
Running the project
******

Clone project to your local repository

Run scripts from /resources/db_scripts/ folder to create database and empty tables (create_schema.sql) 
and to insert data (populate_schema.sql)

From project root folder run - mvn tomcat7:run

Use http://localhost:8088/api to view website


********
Admin Data
********

pasword: pass
email: art4315@gmail.com

