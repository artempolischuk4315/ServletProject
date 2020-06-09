-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: myservletdb
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

LOCK TABLES `available_tests` WRITE;
/*!40000 ALTER TABLE `available_tests` DISABLE KEYS */;
INSERT INTO `available_tests` VALUES (3229,64),(3229,68),(3242,70),(3254,64),(3256,68);
/*!40000 ALTER TABLE `available_tests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `completed_tests`
--

DROP TABLE IF EXISTS `completed_tests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `completed_tests` (
  `user_id` int(11) NOT NULL,
  `result` int(11) DEFAULT NULL,
  `test_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`test_id`),
  KEY `test_idx` (`test_id`),
  CONSTRAINT `test_that_was_completed` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_that_completed` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `completed_tests`
--

LOCK TABLES `completed_tests` WRITE;
/*!40000 ALTER TABLE `completed_tests` DISABLE KEYS */;
INSERT INTO `completed_tests` VALUES (3230,35,67),(3230,68,68),(3230,13,70),(3230,3,74),(3256,19,64),(3256,66,71),(3256,81,72),(3256,59,73),(3256,89,74),(3256,26,75);
/*!40000 ALTER TABLE `completed_tests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (64,'Math1','Математика1','5',7,20,NULL,'MATH',''),(67,'History1','Історія1','5',5,15,NULL,'HISTORY',''),(68,'Progr1','Програмування1','5',7,8,NULL,'PROGRAMMING',''),(70,'Math10','Математика10','5',5,35,NULL,'MATH',''),(71,'Physics1','Фізика1','8',4,10,NULL,'PHYSICS',''),(72,'Math2','Математика2','7',30,180,NULL,'MATH',''),(73,'History2','Історія2','5',5,5,NULL,'HISTORY',''),(74,'Progr2','Програмування2','6',8,25,NULL,'PROGRAMMING',''),(75,'Physics2','Фізика2','6',15,25,NULL,'PHYSICS',''),(76,'Math3','Математика3','10',15,35,NULL,'MATH',''),(78,'Math4','Математика4','5',15,100,NULL,'MATH',''),(79,'Math5','Математика5','8',15,45,NULL,'MATH','\0'),(80,'Admin','Адмін','10',25,180,NULL,'PHYSICS','');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=3263 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3229,'Artem','Артем','Polischuk','Поліщук','art4315@gmail.com','1A1DC91C907325C69271DDF0C944BC72','ADMIN',91.5),(3230,'Oleg','Олег','Petrov','Петров','testeraccmail8@gmail.com','1A1DC91C907325C69271DDF0C944BC72','USER',27.6),(3231,'Maryan','Мар\'яна','Bondar','Бондар','mail@knu.ua','1A1DC91C907325C69271DDF0C944BC72','USER',NULL),(3235,'Bogdan','Богдан','Leps','Лєпс','new@mail.com','1A1DC91C907325C69271DDF0C944BC72','USER',NULL),(3240,'Petro','Петро','Mayboroda','Майборода','oldpetro@qmail.com','C4CA4238A0B923820DCC509A6F75849B','USER',NULL),(3241,'Lionel','Ліонель','Messi','Мессі','barsa@leo.com','1A1DC91C907325C69271DDF0C944BC72','USER',NULL),(3242,'Denys','Денис','Garmash','Гармаш','deniska@lol.com','C4CA4238A0B923820DCC509A6F75849B','USER',21.5),(3252,'Semen','Семен','Semenov','Семенов','semka@fff.fff','C4CA4238A0B923820DCC509A6F75849B','USER',NULL),(3253,'Name','Ім\'я','Surname','Прізвище','mail@mail.ua','1A1DC91C907325C69271DDF0C944BC72','USER',NULL),(3254,'Anton','Антон','Karton','Картон','karton@anton.com','1A1DC91C907325C69271DDF0C944BC72','USER',45),(3255,'Volodymyr','Володимир','Lilnov','Лілнов','vovka@mail.com','C4CA4238A0B923820DCC509A6F75849B','USER',NULL),(3256,'Panas','Панас','Myrnyi','Мирний','test@test.com','1A1DC91C907325C69271DDF0C944BC72','USER',56.666666666666664),(3257,'Taras','Тарас','Shevchenko','Шевченко','sheva@taras.com','C4CA4238A0B923820DCC509A6F75849B','USER',NULL),(3262,'Oleg','Артем','Garmash','Поліщук','arddt431ffd5@gmail.com','C4CA4238A0B923820DCC509A6F75849B','USER',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-04 13:30:44
