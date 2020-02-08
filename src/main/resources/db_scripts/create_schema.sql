-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: testDB
-- ------------------------------------------------------
-- Server version	5.5.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `available_tests`
--
drop schema if exists myservletdb;
create schema myservletdb character set utf8 collate utf8_general_ci;
use myservletdb;
DROP TABLE IF EXISTS `available_tests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `available_tests` (
                                   `user_id` int(11) NOT NULL,
                                   `test_id` int(11) NOT NULL,
                                   PRIMARY KEY (`user_id`,`test_id`),
                                   KEY `user_idx` (`user_id`),
                                   KEY `test_idx` (`test_id`),
                                   CONSTRAINT `test` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                   CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `available_tests`
--

/*LOCK TABLES `available_tests` WRITE;
ALTER TABLE `available_tests` DISABLE KEYS ;
INSERT INTO `available_tests` VALUES (3229,64),(3229,67),(3230,64),(3230,67),(3230,68);
ALTER TABLE `available_tests` ENABLE KEYS;
UNLOCK TABLES;*/

--
-- Table structure for table `completed_tests`
--

DROP TABLE IF EXISTS `completed_tests`;
SET @saved_cs_client     = @@character_set_client ;
SET character_set_client = utf8;
CREATE TABLE `completed_tests` (
                                   `user_id` int(11) NOT NULL,
                                   `result` int(11) DEFAULT NULL,
                                   `test_id` int(11) NOT NULL,
                                   PRIMARY KEY (`user_id`,`test_id`),
                                   KEY `test_idx` (`test_id`),
                                   CONSTRAINT `user_that_completed` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                   CONSTRAINT `test_that_was_completed` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `completed_tests`
--

/*LOCK TABLES `completed_tests` WRITE;
ALTER TABLE `completed_tests` DISABLE KEYS ;
INSERT INTO `completed_tests` VALUES (3230,99,64),(3230,62,67),(3230,89,68);
ALTER TABLE `completed_tests` ENABLE KEYS;
UNLOCK TABLES;*/

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `test` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `name` varchar(45) NOT NULL,
                        `nameUa` varchar(45) NOT NULL,
                        `difficulty` varchar(45) NOT NULL,
                        `numberOfQuestions` int(11) NOT NULL,
                        `timeLimit` int(11) NOT NULL,
                        `result` int(11) DEFAULT NULL,
                        `category` varchar(45) NOT NULL,
                        `active` bit(1) DEFAULT NULL,
                        PRIMARY KEY (`id`,`name`),
                        UNIQUE KEY `name_UNIQUE` (`name`),
                        UNIQUE KEY `nameUa_UNIQUE` (`nameUa`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `test`
--

/*LOCK TABLES `test` WRITE;
ALTER TABLE `test` DISABLE KEYS;
INSERT INTO `test` VALUES
                          (64,'Math1','Математика1','5',7,20,NULL,'MATH',''),
                          (67,'History1','Історія1','5',5,15,NULL,'HISTORY',''),
                          (68,'Progr1','Програмування1','5',7,8,NULL,'PROGRAMMING',''),
                          (70,'Math10','Математика10','5',5,35,NULL,'MATH','');
ALTER TABLE `test` ENABLE KEYS;
UNLOCK TABLES;*/

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8 ;
CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `firstName` varchar(45) NOT NULL,
                        `firstNameUa` varchar(45) NOT NULL,
                        `lastName` varchar(45) DEFAULT NULL,
                        `lastNameUa` varchar(45) NOT NULL,
                        `email` varchar(45) NOT NULL,
                        `password` varchar(45) NOT NULL,
                        `role` varchar(45) NOT NULL,
                        `stats` double DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3240 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `user`
--

/*LOCK TABLES `user` WRITE;
ALTER TABLE `user` DISABLE KEYS;
INSERT INTO `user` VALUES
(3229,'Artem','Артем','Polischuk','Поліщук','art4315@gmail.com','1A1DC91C907325C69271DDF0C944BC72','ADMIN',NULL),
(3230,'Oleg','Олег','Petrov','Петров','testeraccmail8@gmail.com','1A1DC91C907325C69271DDF0C944BC72','USER',83.33333333333333),
(3231,'Maryan','Мар\'яна','Bondar','Бондар','mail@knu.ua','1A1DC91C907325C69271DDF0C944BC72','USER',NULL),
(3235,'Bogdan','Богдан','Keks','Лєпс','new@mail.com','1A1DC91C907325C69271DDF0C944BC72','USER',NULL);*/

