CREATE DATABASE  IF NOT EXISTS `paises` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `paises`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: paises
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `estatuak`
--

DROP TABLE IF EXISTS `estatuak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estatuak` (
  `Pais` varchar(45) NOT NULL,
  `Capital` varchar(45) NOT NULL,
  `Moneda` varchar(30) NOT NULL,
  `Superficie` int unsigned NOT NULL,
  `Poblacion` int unsigned NOT NULL,
  `Bizi_Esperantza` int unsigned NOT NULL,
  `Kontinenteak_Izena` varchar(45) NOT NULL,
  PRIMARY KEY (`Pais`),
  KEY `fk_Estatuak_Kontinenteak_idx` (`Kontinenteak_Izena`),
  CONSTRAINT `fk_Estatuak_Kontinenteak` FOREIGN KEY (`Kontinenteak_Izena`) REFERENCES `kontinenteak` (`Izena`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estatuak`
--

LOCK TABLES `estatuak` WRITE;
/*!40000 ALTER TABLE `estatuak` DISABLE KEYS */;
INSERT INTO `estatuak` VALUES ('Andorra','Andorra La Vieja','Euro',468,64000,0,'Europa'),('Argelia','Argel','Dinar Argelino',2381741,27959000,70,'Africa'),('Eritrea','Asmara','Nakfa',117600,3400000,0,'Africa'),('Grecia','Atenas','Euro',131957,10467000,78,'Europa'),('Holanda','Amsterdam','Euro',41526,15460000,78,'Europa'),('Irak','Bagdad','Dinar Iraquí',438317,20097000,66,'Asia'),('Madagascar','Antananarivo','Ariary',587041,13651000,52,'Africa'),('Paraguay','Asuncion','Guaraní',406752,4828000,68,'America'),('Samoa Occidental','Apia','Tala',2831,165000,68,'Oceania'),('Turkia','Ankara','Lira Turca',783562,61058000,67,'Asia');
/*!40000 ALTER TABLE `estatuak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kontinenteak`
--

DROP TABLE IF EXISTS `kontinenteak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kontinenteak` (
  `Izena` varchar(45) NOT NULL,
  `Superficie` int unsigned NOT NULL,
  `Herrialde_Kop` int unsigned NOT NULL,
  `Populazioa` bigint unsigned NOT NULL,
  PRIMARY KEY (`Izena`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kontinenteak`
--

LOCK TABLES `kontinenteak` WRITE;
/*!40000 ALTER TABLE `kontinenteak` DISABLE KEYS */;
INSERT INTO `kontinenteak` VALUES ('Africa',3037000,54,1340147),('America',3027000,54,1340147),('Asia',4457900,49,46454775),('Europa',10180000,50,746440),('Oceania',2837000,24,1340147);
/*!40000 ALTER TABLE `kontinenteak` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-14 10:34:20
