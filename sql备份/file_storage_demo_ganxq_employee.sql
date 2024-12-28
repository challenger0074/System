-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: file_storage
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `demo_ganxq_employee`
--

DROP TABLE IF EXISTS `demo_ganxq_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demo_ganxq_employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `contact_info` varchar(255) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `start_date` timestamp NULL DEFAULT NULL COMMENT '入职时间',
  `state` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demo_ganxq_employee`
--

LOCK TABLES `demo_ganxq_employee` WRITE;
/*!40000 ALTER TABLE `demo_ganxq_employee` DISABLE KEYS */;
INSERT INTO `demo_ganxq_employee` VALUES (1,'Alice Smith','Software Engineer','alice.smith@example.com','F','2024-01-10 01:00:00',0),(2,'Bob Johnson','Data Analyst','bob.johnson@example.com','M','2024-01-12 01:00:00',0),(3,'Charlie Brown','Product Manager','charlie.brown@example.com','M','2024-01-15 01:00:00',1),(4,'Diana Prince','UX Designer','diana.prince@example.com','F','2024-01-18 01:00:00',0),(5,'Evan Lee','DevOps Engineer','evan.lee@example.com','M','2024-01-20 01:00:00',0),(6,'Fiona Green','HR Manager','fiona.green@example.com','F','2024-01-22 01:00:00',1),(7,'George King','Marketing Specialist','george.king@example.com','M','2024-01-25 01:00:00',0),(8,'Hannah White','Sales Executive','hannah.white@example.com','F','2024-01-28 01:00:00',0),(9,'Ian Brown','Customer Support','ian.brown@example.com','M','2024-01-30 01:00:00',0),(10,'Julia Adams','Quality Assurance','julia.adams@example.com','F','2024-02-01 01:00:00',1);
/*!40000 ALTER TABLE `demo_ganxq_employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-26 17:32:39
