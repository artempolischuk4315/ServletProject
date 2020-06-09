# ServletProject
Servlet Project


Students Rapid Testing System. Student is registering by e-mail, his Success will be attached to him and on this email will come Test result messages. The system has a catalog of Tests for topics, an authorized Student can take tests. At the end of the test the page should display a form showing student errors. All data on academic performance and completed courses are displayed on the page Administrator as a summary table for all Students.

******
Requirements
******

*JDK 1.8

*Apache Tomcat

*Apache Maven

*MySQL


******
Running the project
******

Clone project to your local repository

*If You have Docker:* 

Run scripts from /resources/db_scripts/ folder to create database and empty tables (create_schema.sql) 
and to insert data (populate_schema.sql)

From project root folder run - mvn tomcat7:run

Use http://localhost:8088/api to view website


********
Admin Data
********

*pasword: pass

*email: art4315@gmail.com

******
Set your username and password in resources/db.properties
******
