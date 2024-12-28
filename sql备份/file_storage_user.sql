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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识符',
  `username` varchar(25) DEFAULT NULL COMMENT '用户登录名',
  `password` varchar(500) DEFAULT NULL COMMENT '用户密码',
  `email` varchar(50) DEFAULT NULL COMMENT '用户电子邮件地址',
  `role` varchar(12) DEFAULT NULL COMMENT '用户角色',
  `enabled` tinyint DEFAULT NULL COMMENT 'security状态',
  `state` tinyint DEFAULT NULL COMMENT '账户状态',
  `userId` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '账户创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `nickname` varchar(255) DEFAULT 'nickname' COMMENT '昵称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'john_doe','password123','john.doe@example.com','超级管理员',1,1,NULL,'2024-10-24 09:02:34','2024-12-25 07:49:10','John'),(2,'jane_smith','password456','jane.smith@example.com','普通用户',1,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Jane'),(3,'alice_jones','password789','alice.jones@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Alice'),(4,'bob_brown','password321','bob.brown@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Bob'),(5,'charlie_davis','password654','charlie.davis@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Charlie'),(6,'david_miller','password987','david.miller@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','David'),(7,'emily_wilson','password234','emily.wilson@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Emily'),(8,'frank_moore','password567','frank.moore@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Frank'),(9,'grace_taylor','password890','grace.taylor@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Grace'),(10,'hannah_anderson','password135','hannah.anderson@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Hannah'),(11,'joshua_white','password246','joshua.white@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Joshua'),(12,'laura_hall','password753','laura.hall@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Laura'),(13,'michael_wright','password159','michael.wright@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Michael'),(14,'nina_scott','password864','nina.scott@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Nina'),(15,'olivia_clark','password147','olivia.clark@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Olivia'),(16,'peter_harris','password258','peter.harris@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Peter'),(17,'quinn_adams','password369','quinn.adams@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Quinn'),(18,'ryan_evans','password951','ryan.evans@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Ryan'),(19,'samantha_lewis','password753','samantha.lewis@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Samantha'),(20,'tyler_thompson','password357','tyler.thompson@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Tyler'),(21,'uma_baker','password468','uma.baker@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Uma'),(22,'victor_king','password579','victor.king@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Victor'),(23,'willow_james','password682','willow.james@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Willow'),(24,'xander_lee','password794','xander.lee@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Xander'),(25,'yasmine_lopez','password805','yasmine.lopez@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Yasmine'),(26,'zachary_jameson','password916','zachary.jameson@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Zachary'),(27,'amanda_martin','password027','amanda.martin@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Amanda'),(28,'brandon_sanders','password138','brandon.sanders@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Brandon'),(29,'cynthia_garcia','password249','cynthia.garcia@example.com','普通用户',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Cynthia'),(30,'daniel_ortiz','password360','daniel.ortiz@example.com','普通用户',0,0,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Daniel'),(31,'admin_user','admin_password','admin@example.com','超级管理员',1,1,NULL,'2024-10-24 09:02:34','2024-11-13 09:53:15','Admin'),(32,'huonzy','$2a$10$kNrjJ3.1wCSmSnjs1JI.RO6RMrQc.oRiJ93T2sefyCOzXb7yy.Mmm',NULL,'user',NULL,NULL,'1','2024-10-24 09:02:34','2024-10-24 09:02:34',NULL),(33,'test','123','123@qq.com','普通用户',NULL,1,NULL,'2024-11-19 08:37:56','2024-11-19 08:37:56','nickname'),(34,'test2','234','234@qq.com','普通用户',NULL,1,NULL,'2024-11-19 09:30:02','2024-11-19 09:30:02','nickname'),(41,'test3','235','234@qq.com','普通用户',NULL,1,NULL,'2024-11-19 09:35:39','2024-11-19 09:35:39','nickname'),(42,'kan',NULL,'192d7594@qq.com',NULL,NULL,1,NULL,'2024-12-11 09:21:17','2024-12-11 09:21:17','nickname'),(44,'kans2','123456','15517594@qq.com',NULL,NULL,1,NULL,'2024-12-11 09:27:29','2024-12-11 09:27:29','nickname'),(46,'kas21','123456','15517594@qq.com',NULL,NULL,1,NULL,'2024-12-11 09:29:57','2024-12-11 09:29:57','nickname'),(47,'mith','2356','192594@qq.com','普通用户',NULL,1,NULL,'2024-12-11 09:32:17','2024-12-11 09:32:17','nickname');
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

-- Dump completed on 2024-12-26 17:32:40
