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
  `tipo_operacao_cd` int(11) NOT NULL,
  `cfop_cd` int(11) NOT NULL,
  `situacao_tributaria_cd` int(11) NOT NULL,
  `origem_mercadoria_cd` int(11) NOT NULL,
  `base_calculo_tributacao_cd` int(11) NOT NULL,
  `base_calculo_tributacao_st_cd` int(11) NOT NULL,
  `aliquota` decimal(5,2) DEFAULT NULL,
  `credito` decimal(5,2) DEFAULT NULL,
  `aliquota_st` decimal(5,2) DEFAULT NULL,
  `credito_st` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_regra_trib_tp_operacao` (`tipo_operacao_cd`),
  KEY `fk_regra_trib_cfop` (`cfop_cd`),
  KEY `fk_regra_trib_sit_trib` (`situacao_tributaria_cd`),
  KEY `fk_regra_trib_org_merc` (`origem_mercadoria_cd`),
  KEY `fk_regra_trib_base_calc` (`base_calculo_tributacao_cd`),
  KEY `fk_regra_trib_base_calc_st` (`base_calculo_tributacao_st_cd`),
  CONSTRAINT `fk_regra_trib_base_calc` FOREIGN KEY (`base_calculo_tributacao_cd`) REFERENCES `base_calculo_tributacao` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_regra_trib_base_calc_st` FOREIGN KEY (`base_calculo_tributacao_st_cd`) REFERENCES `base_calculo_tributacao_st` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_regra_trib_cfop` FOREIGN KEY (`cfop_cd`) REFERENCES `cfop` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_regra_trib_org_merc` FOREIGN KEY (`origem_mercadoria_cd`) REFERENCES `origem_mercadoria` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_regra_trib_sit_trib` FOREIGN KEY (`situacao_tributaria_cd`) REFERENCES `situacao_tributaria` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_regra_trib_tp_operacao` FOREIGN KEY (`tipo_operacao_cd`) REFERENCES `tipo_operacao` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regra_tributacao`
--

LOCK TABLES `regra_tributacao` WRITE;
/*!40000 ALTER TABLE `regra_tributacao` DISABLE KEYS */;
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

-- Dump completed on 2014-03-17 11:30:14
