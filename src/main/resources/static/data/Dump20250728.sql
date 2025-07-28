-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: book_rental
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `authorid` bigint NOT NULL AUTO_INCREMENT,
  `author_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authorid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'minh'),(3,'nghĩa'),(4,'J. K. Rowling'),(5,'Jeff Kinney'),(6,'Stephenie Meyer'),(7,'kiett');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `bookid` bigint NOT NULL AUTO_INCREMENT,
  `available_copies` int NOT NULL,
  `book_name` varchar(255) DEFAULT NULL,
  `description` text,
  `imageurl` varchar(255) DEFAULT NULL,
  `published_date` date DEFAULT NULL,
  `rental_price` double NOT NULL,
  `stock` varchar(255) DEFAULT NULL,
  `total_copies` int NOT NULL,
  `authorid` bigint DEFAULT NULL,
  `publisher_id` bigint DEFAULT NULL,
  `borrow_count` int NOT NULL,
  PRIMARY KEY (`bookid`),
  KEY `FKhcj0e0ky3ftaweqnllqfwbn99` (`authorid`),
  KEY `FKgtvt7p649s4x80y6f4842pnfq` (`publisher_id`),
  CONSTRAINT `FKgtvt7p649s4x80y6f4842pnfq` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`publisherid`),
  CONSTRAINT `FKhcj0e0ky3ftaweqnllqfwbn99` FOREIGN KEY (`authorid`) REFERENCES `author` (`authorid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (2,22,'hoho','sadasdasa','https://covers.openlibrary.org/b/id/315113-L.jpg','2025-07-08',12,'In Stock',22,1,2,0),(4,0,'The Man Who Knew Too Much','aaaaaaaaaaaaaaaaaaaaaaaaaaaa','https://covers.openlibrary.org/b/id/2977036-L.jpg','2021-07-06',44,'Out of Stock',0,1,2,0),(5,1,'What was I scared of?','xxxxxxxxxxxxxxxxxxxxxxxxxx','https://covers.openlibrary.org/b/id/14850122-L.jpg','2023-05-29',22,'In Stock',2,NULL,1,0),(6,4,'Moby Dick','aaaaaaaaasaaaaaaaaaaaa','https://covers.openlibrary.org/b/id/8612368-L.jpg','2020-05-26',18,'In Stock',4,3,2,0),(7,5,'Phantastes','jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj','https://covers.openlibrary.org/b/id/14364442-L.jpg','2015-07-21',6,'In Stock',8,3,1,0),(8,2,'Harry Potter and the Sorcerer\'s Stone','Harry Potter #1\r\n\r\nWhen mysterious letters start arriving on his doorstep, Harry Potter has never heard of Hogwarts School of Witchcraft and Wizardry. ','https://covers.openlibrary.org/b/id/15093275-L.jpg','1997-07-07',9,'In Stock',2,4,1,0),(9,10,'Harry Potter and the Chamber of Secrets','Throughout the summer holidays after his first year at Hogwarts School of Witchcraft and Wizardry, Harry Potter has been receiving sinister warnings from a house-elf called Dobby. ','https://covers.openlibrary.org/b/id/15095437-L.jpg','1999-05-05',22,'In Stock',11,4,3,0),(10,0,'Harry Potter and the Prisoner of Azkaban','For Twelve long years, the dread fortress of Azkaban held an infamous prisoner named Sirius Black. Convicted of killing thirteen people with a single curse, he was said to be the heir apparent to the Dark Lord, Voldemort.','https://covers.openlibrary.org/b/id/14852024-L.jpg','2007-07-04',15,'Out of Stock',2,4,3,0),(11,2,'Harry Potter and The Goblet of Fire 4','The fourth book in the Harry Potter franchise sees Harry returning for his fourth year at Hogwarts School of Witchcraft and Wizardry, along with his friends, Ron and Hermione ','https://covers.openlibrary.org/b/id/15096178-L.jpg','2013-07-10',5,'In Stock',3,4,4,0),(12,3,'Nevykėlio dienoraštis','Greg skriver dagbok for alle sine fremtidige fans, han må bare takle mellomstadiet først. Som nummer to i en søskenflokk på tre er han skviset inn mellom en sjefete storebror og en sippete lillebror. Foreldrene er velmenende, men helt håpløse ifølge Greg.','https://covers.openlibrary.org/b/id/15103624-L.jpg','2002-05-13',2,'In Stock',4,5,5,0),(13,6,'Dog Days (Diary of a Wimpy Kid book 4)','In Dog Days, book 4 of the Diary of a Wimpy Kid series from #1 international bestselling author Jeff Kinney, it\'s summer vacation, the weather\'s great, and all the kids are having fun outside. So where\'s Greg Heffley? ','https://covers.openlibrary.org/b/id/14812500-L.jpg','2012-07-16',9,'In Stock',6,5,5,0),(14,8,'Diary of a Wimpy Kid Rodrick Rules','Greg Heffley tells about his summer vacation and his attempts to steer clear of trouble when he returns to middle school and tries to keep his older brother Rodrick from telling everyone about Greg\'s most humiliating experience of the summer. ','https://covers.openlibrary.org/b/id/14819388-L.jpg','2008-07-21',3,'In Stock',8,5,5,0),(15,10,'Twilight','When seventeen-year-old Bella leaves Phoenix to live with her father in Forks, Washington, she meets an exquisitely handsome boy at school for whom she feels an overwhelming attraction and who she comes to realize is not wholly human. ','https://covers.openlibrary.org/b/id/14352258-L.jpg','2009-07-29',4,'In Stock',12,6,2,0),(16,7,'New Moon','Love stories. Horror fiction. Now in a Special Trade Demy Paperback Edition. The dramatic sequel to TWILIGHT, following the tale of Bella, a teenage girl whose love for a vampire gets her into trouble.','https://covers.openlibrary.org/b/id/14852004-L.jpg','2006-07-19',5,'In Stock',7,6,2,0);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_category`
--

DROP TABLE IF EXISTS `book_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_category` (
  `book_categoryid` bigint NOT NULL AUTO_INCREMENT,
  `book_cat_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`book_categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_category`
--

LOCK TABLES `book_category` WRITE;
/*!40000 ALTER TABLE `book_category` DISABLE KEYS */;
INSERT INTO `book_category` VALUES (1,'Fiction'),(2,'Non-Fiction'),(3,'Children'),(4,'Young Adult'),(5,'Biography'),(6,'Science'),(7,'History'),(8,'Fantasy'),(9,'Mystery'),(11,'Romance');
/*!40000 ALTER TABLE `book_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_category_map`
--

DROP TABLE IF EXISTS `book_category_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_category_map` (
  `bookid` bigint NOT NULL,
  `book_categoryid` bigint NOT NULL,
  KEY `FKo7aksvq0nqk5fd2qwyoy8f1vy` (`book_categoryid`),
  KEY `FKaob19w1325ji4f5i6r5f5wf9o` (`bookid`),
  CONSTRAINT `FKaob19w1325ji4f5i6r5f5wf9o` FOREIGN KEY (`bookid`) REFERENCES `book` (`bookid`),
  CONSTRAINT `FKo7aksvq0nqk5fd2qwyoy8f1vy` FOREIGN KEY (`book_categoryid`) REFERENCES `book_category` (`book_categoryid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_category_map`
--

LOCK TABLES `book_category_map` WRITE;
/*!40000 ALTER TABLE `book_category_map` DISABLE KEYS */;
INSERT INTO `book_category_map` VALUES (4,5),(5,8),(6,1),(6,7),(6,9),(7,2),(7,3),(7,4),(7,5),(7,6),(8,4),(12,2),(12,3),(12,7),(13,2),(13,3),(13,4),(14,2),(14,3),(14,4),(15,3),(2,2),(2,3),(2,8),(2,9),(9,1),(9,8),(9,9),(10,1),(10,8),(10,9),(11,1),(11,4),(11,8),(11,9),(16,1),(16,8),(16,9);
/*!40000 ALTER TABLE `book_category_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrow_record`
--

DROP TABLE IF EXISTS `borrow_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrow_record` (
  `borrowid` bigint NOT NULL AUTO_INCREMENT,
  `borrow_date` date DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `reservationid` bigint DEFAULT NULL,
  PRIMARY KEY (`borrowid`),
  KEY `FKdh7dkpm7mpkrdadavdtc6snnc` (`reservationid`),
  CONSTRAINT `FKdh7dkpm7mpkrdadavdtc6snnc` FOREIGN KEY (`reservationid`) REFERENCES `reservation` (`reservationid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow_record`
--

LOCK TABLES `borrow_record` WRITE;
/*!40000 ALTER TABLE `borrow_record` DISABLE KEYS */;
INSERT INTO `borrow_record` VALUES (1,'2025-07-25',NULL,NULL,'BORROWING',4),(2,'2025-07-25',NULL,NULL,'BORROWING',4),(3,'2025-07-25',NULL,'2025-08-09','LATE',1),(4,'2025-07-25','2025-08-08','2025-08-09','RETURNED',1),(5,'2025-07-26','2025-08-09','2025-08-09','RETURNED',1),(6,'2025-07-28','2025-08-11','2025-08-05','RETURNED',1),(7,'2025-07-28','2025-08-11',NULL,'BORROWING',1);
/*!40000 ALTER TABLE `borrow_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publisher` (
  `publisherid` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`publisherid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO `publisher` VALUES (1,'hanoi','asdadasas'),(2,'HCM','aaaaaaaaa'),(3,'UK','Arthur A. Levine Books'),(4,'UK','Bloomsbury'),(5,'UK','Puffin');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `reservationid` bigint NOT NULL AUTO_INCREMENT,
  `created_date` date DEFAULT NULL,
  `status` enum('CANCELLED','RESERVED','RETURNED') DEFAULT NULL,
  `bookid` bigint DEFAULT NULL,
  `userid` bigint DEFAULT NULL,
  `reserved_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`reservationid`),
  KEY `FK6h43oam15hoedgr70s05uos5o` (`bookid`),
  KEY `FKbmofx0u4skuijdcuekxaowjp2` (`userid`),
  CONSTRAINT `FK6h43oam15hoedgr70s05uos5o` FOREIGN KEY (`bookid`) REFERENCES `book` (`bookid`),
  CONSTRAINT `FKbmofx0u4skuijdcuekxaowjp2` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,'2025-07-25','CANCELLED',6,1,NULL),(2,'2025-07-25','RESERVED',10,1,NULL),(3,'2025-07-25','RESERVED',7,1,NULL),(4,'2025-07-25','CANCELLED',6,4,NULL),(5,'2025-07-25','CANCELLED',2,3,NULL),(6,'2025-07-26','RESERVED',9,3,NULL),(7,'2025-07-26','RESERVED',11,3,NULL),(8,'2025-07-26','CANCELLED',6,3,NULL),(9,'2025-07-26','RESERVED',15,3,NULL),(10,'2025-07-26','RESERVED',15,3,NULL),(11,'2025-07-26','RESERVED',12,3,NULL),(12,'2025-07-26','RESERVED',7,3,NULL),(13,'2025-07-26','RESERVED',7,3,NULL),(14,'2025-07-28','RESERVED',10,3,NULL);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userid` bigint NOT NULL AUTO_INCREMENT,
  `created_date` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,'leducminh2934@gmail.com','Đức Minh Lê',NULL,'ADMIN',NULL,NULL,NULL,NULL),(2,'2025-07-25','admin@gmail.com','admin','123','ADMIN',NULL,NULL,NULL,NULL),(3,'2025-07-25','admin1@gmail.com','admin1','$2a$10$jzATgjR56HOy1OSi1HihMOpK29bZOChdyYUogKHRFMohAvvkFOOU6','admin','default.jpg','2000-01-01','male',NULL),(4,'2025-07-25','minhldhe180598@fpt.edu.vn','Le Duc Minh (K18 HL)',NULL,'member','default.jpg','2000-01-01','male',NULL),(5,'2025-07-25','minh1@gmail.com','minh1','$2a$10$IvPMZFqoDZoD979mRduAI.hcOHNpqrEY2b1/xZtbTL/IN5eEeEUnq','member','default.jpg','2000-01-01','male',NULL);
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

-- Dump completed on 2025-07-28 14:58:09
