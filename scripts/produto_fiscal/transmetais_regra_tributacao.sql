CREATE DATABASE  IF NOT EXISTS `transmetais` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `transmetais`;
-- MySQL dump 10.13  Distrib 5.6.13, for osx10.6 (i386)
--
-- Host: 127.0.0.1    Database: transmetais
-- ------------------------------------------------------
-- Server version	5.6.15

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
-- Table structure for table `regra_tributacao`
--

DROP TABLE IF EXISTS `regra_tributacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regra_tributacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_operacao_id` int(11) NOT NULL,
  `cfop_id` int(11) NOT NULL,
  `situacao_tributaria_id` int(11) NOT NULL,
  `origem_mercadoria_id` int(11) NOT NULL,
  `base_calculo_tributacao_id` int(11) NOT NULL,
  `base_calculo_tributacao_st_id` int(11) DEFAULT NULL,
  `aliquota` decimal(5,2) NOT NULL,
  `credito` decimal(5,2) NOT NULL,
  `aliquota_st` decimal(5,2) DEFAULT NULL,
  `credito_st` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_regra_trib_tp_operacao` (`tipo_operacao_id`),
  KEY `fk_regra_trib_cfop` (`cfop_id`),
  KEY `fk_regra_trib_sit_trib` (`situacao_tributaria_id`),
  KEY `fk_regra_trib_base_calc` (`base_calculo_tributacao_id`),
  KEY `fk_regra_trib_base_calc_st` (`base_calculo_tributacao_st_id`),
  CONSTRAINT `fk_regra_trib_base_calc` FOREIGN KEY (`base_calculo_tributacao_id`) REFERENCES `base_calculo_tributacao` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_regra_trib_base_calc_st` FOREIGN KEY (`base_calculo_tributacao_st_id`) REFERENCES `base_calculo_tributacao_st` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_regra_trib_cfop` FOREIGN KEY (`cfop_id`) REFERENCES `cfop` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_regra_trib_sit_trib` FOREIGN KEY (`situacao_tributaria_id`) REFERENCES `situacao_tributaria` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_regra_trib_tp_operacao` FOREIGN KEY (`tipo_operacao_id`) REFERENCES `tipo_operacao` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regra_tributacao`
--

LOCK TABLES `regra_tributacao` WRITE;
/*!40000 ALTER TABLE `regra_tributacao` DISABLE KEYS */;
INSERT INTO `regra_tributacao` VALUES (28,3,3,2,3,3,NULL,0.01,0.01,0.01,0.01),(29,3,2,3,2,3,NULL,0.01,0.01,0.01,0.01),(30,4,1,2,2,3,NULL,0.01,0.01,NULL,NULL),(31,4,1,2,2,3,NULL,0.01,0.01,NULL,NULL),(32,4,1,2,2,3,NULL,0.01,0.01,NULL,NULL),(33,4,1,2,2,3,NULL,0.01,0.01,NULL,NULL),(34,2,2,2,2,2,NULL,0.01,0.01,0.00,0.00),(35,3,2,2,3,3,NULL,0.01,0.01,NULL,NULL),(37,4,2,2,7,1,NULL,0.11,0.01,NULL,NULL),(38,3,2,2,2,2,NULL,0.01,0.01,NULL,NULL),(39,3,2,2,2,2,NULL,0.01,0.01,NULL,NULL),(40,3,2,2,2,2,NULL,0.01,0.01,0.02,0.02),(41,2,2,2,3,3,NULL,0.02,0.02,NULL,NULL),(42,2,2,2,2,2,NULL,0.01,0.01,0.01,0.01),(43,2,1,3,3,2,NULL,0.01,0.01,0.01,0.01),(44,3,2,2,3,3,3,0.01,0.01,0.01,0.01),(47,2,12,7,7,3,NULL,0.01,0.01,NULL,NULL);
/*!40000 ALTER TABLE `regra_tributacao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-01 21:12:47
