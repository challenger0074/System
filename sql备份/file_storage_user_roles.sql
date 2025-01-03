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
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (29,1,2,'2024-11-12 07:44:40'),(30,2,1,'2024-11-12 07:44:40'),(31,3,1,'2024-11-12 07:44:40'),(32,4,1,'2024-11-12 07:44:40'),(33,5,1,'2024-11-12 07:44:40'),(34,6,1,'2024-11-12 07:44:40'),(35,7,1,'2024-11-12 07:44:40'),(36,8,1,'2024-11-12 07:44:40'),(37,9,1,'2024-11-12 07:44:40'),(38,10,1,'2024-11-12 07:44:40'),(39,11,1,'2024-11-12 07:44:40'),(40,12,1,'2024-11-12 07:44:40'),(41,13,1,'2024-11-12 07:44:40'),(42,14,1,'2024-11-12 07:44:40'),(43,15,1,'2024-11-12 07:44:40'),(44,16,1,'2024-11-12 07:44:40'),(45,17,1,'2024-11-12 07:44:40'),(46,18,1,'2024-11-12 07:44:40'),(47,19,1,'2024-11-12 07:44:40'),(48,20,1,'2024-11-12 07:44:40'),(49,21,1,'2024-11-12 07:44:40'),(50,22,1,'2024-11-12 07:44:40'),(51,23,1,'2024-11-12 07:44:40'),(52,24,1,'2024-11-12 07:44:40'),(53,25,1,'2024-11-12 07:44:40'),(54,26,1,'2024-11-12 07:44:40'),(55,27,1,'2024-11-12 07:44:40'),(56,28,2,'2024-11-12 07:44:40'),(57,34,1,'2024-11-19 09:30:23'),(58,41,1,'2024-11-19 09:35:39'),(59,47,1,'2024-12-11 09:32:17');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
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
