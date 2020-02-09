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

drop schema if exists myservletdb;
create schema myservletdb character set utf8 collate utf8_general_ci;
use myservletdb;
DROP TABLE IF EXISTS `available_tests`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `available_tests` (
                                   `user_id` int(11) NOT NULL,
                                   `test_id` int(11) NOT NULL,
                                   PRIMARY KEY (`user_id`,`test_id`),
                                   KEY `user_idx` (`user_id`),
                                   KEY `test_idx` (`test_id`),
                                   CONSTRAINT `test` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                   CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

