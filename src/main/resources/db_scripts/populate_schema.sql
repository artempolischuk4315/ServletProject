

use myservletdb

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (64,'Math1','Математика1','5',7,20,NULL,'MATH',''),
                          (67,'History1','Історія1','5',5,15,NULL,'HISTORY',''),
                          (68,'Progr1','Програмування1','5',7,8,NULL,'PROGRAMMING',''),
                          (70,'Math10','Математика10','5',5,35,NULL,'MATH','\0'),
                          (71,'Physics1','Фізика1','8',4,10,NULL,'PHYSICS',''),
                          (72,'Math2','Математика2','7',30,180,NULL,'MATH','\0');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3229,'Artem','Артем','Polischuk','Поліщук','art4315@gmail.com','1A1DC91C907325C69271DDF0C944BC72','ADMIN',NULL),
                          (3230,'Oleg','Олег','Petrov','Петров','testeraccmail8@gmail.com','1A1DC91C907325C69271DDF0C944BC72','USER',83.33333333333333),
                          (3231,'Maryan','Мар\'яна','Bondar','Бондар','mail@knu.ua','1A1DC91C907325C69271DDF0C944BC72','USER',NULL),
                          (3235,'Bogdan','Богдан','Leps','Лєпс','new@mail.com','1A1DC91C907325C69271DDF0C944BC72','USER',NULL),
                          (3240,'Petro','Петро','Mayboroda','Майборода','oldpetro@qmail.com','C4CA4238A0B923820DCC509A6F75849B','USER',NULL),
                          (3241,'Lionel','Ліонель','Messi','Мессі','barsa@leo.com','1A1DC91C907325C69271DDF0C944BC72','USER',NULL),
                          (3242,'Denys','Денис','Garmash','Гармаш','deniska@lol.com','C4CA4238A0B923820DCC509A6F75849B','USER',21.5);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `available_tests` WRITE;
/*!40000 ALTER TABLE `available_tests` DISABLE KEYS */;
INSERT INTO `available_tests` VALUES (3229,64),(3229,67),(3230,64),(3230,67),(3230,68),(3242,70);
/*!40000 ALTER TABLE `available_tests` ENABLE KEYS */;
UNLOCK TABLES;



LOCK TABLES `completed_tests` WRITE;
/*!40000 ALTER TABLE `completed_tests` DISABLE KEYS */;
INSERT INTO `completed_tests` VALUES (3230,99,64),(3230,62,67),(3230,89,68),(3242,24,64),(3242,19,67);
/*!40000 ALTER TABLE `completed_tests` ENABLE KEYS */;
UNLOCK TABLES;