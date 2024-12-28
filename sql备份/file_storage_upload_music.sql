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
-- Table structure for table `upload_music`
--

DROP TABLE IF EXISTS `upload_music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `upload_music` (
  `id` int NOT NULL AUTO_INCREMENT,
  `music_name` varchar(50) NOT NULL,
  `upload_user` varchar(24) NOT NULL,
  `storage_location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upload_music`
--

LOCK TABLES `upload_music` WRITE;
/*!40000 ALTER TABLE `upload_music` DISABLE KEYS */;
INSERT INTO `upload_music` VALUES (21,'music0','john_doe','/music_storage/john_doe/music0.mp3'),(22,'music1','john_doe','/music_storage/john_doe/music1.mp3'),(24,'music2','john_doe','/music_storage/john_doe/music2.mp3'),(26,'music0','jane_smith','/music_storage/jane_smith/music0.mp3'),(27,'music1','jane_smith','/music_storage/jane_smith/music1.mp3'),(28,'music2','jane_smith','/music_storage/jane_smith/music2.mp3'),(29,'music3','jane_smith','/music_storage/jane_smith/music3.mp3'),(30,'明月--张国荣.128','john_doe','/music_storage/john_doe/明月--张国荣.128.mp3'),(31,'沉默是金-张国荣.128','john_doe','/music_storage/john_doe/沉默是金-张国荣.128.mp3'),(32,'倩幽魂 (电影《倩幽魂》主题曲)-张国荣.128','john_doe','/music_storage/john_doe/倩幽魂 (电影《倩幽魂》主题曲)-张国荣.128.mp3'),(33,'Study and Relax','jane_smith','/music_storage/jane_smith/Study and Relax.mp3');
/*!40000 ALTER TABLE `upload_music` ENABLE KEYS */;
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
