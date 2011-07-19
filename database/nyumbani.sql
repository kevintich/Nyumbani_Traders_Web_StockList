-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: localhost    Database: Nyumbani
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.10

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
-- Current Database: `Nyumbani`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `Nyumbani` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `Nyumbani`;

--
-- Table structure for table `CompanyTradingHistory`
--

DROP TABLE IF EXISTS `CompanyTradingHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CompanyTradingHistory` (
  `Company_Trading_Value` varchar(50) NOT NULL DEFAULT ' ',
  `Highest_Value_This_Year` varchar(50) NOT NULL DEFAULT ' ',
  `Lowest_Value_This_Year` varchar(50) NOT NULL DEFAULT ' ',
  `Opening_Price_Today` varchar(50) NOT NULL DEFAULT ' ',
  `Current_Trading_Price` varchar(50) NOT NULL DEFAULT ' ',
  `Change_From_Previous_Price` varchar(50) NOT NULL DEFAULT '0.00',
  `Shares_Moved` int(11) DEFAULT '0',
  `Tiker_Name` varchar(50) NOT NULL DEFAULT ' ',
  PRIMARY KEY (`Tiker_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CompanyTradingHistory`
--

LOCK TABLES `CompanyTradingHistory` WRITE;
/*!40000 ALTER TABLE `CompanyTradingHistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `CompanyTradingHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TradingCompanies`
--

DROP TABLE IF EXISTS `TradingCompanies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TradingCompanies` (
  `Tiker_Name` varchar(50) NOT NULL,
  `Company_Name` varchar(50) NOT NULL,
  `Stock_Type` varchar(50) NOT NULL,
  `Share_Quantity` varchar(50) NOT NULL DEFAULT '',
  `Share_Description` varchar(50) NOT NULL DEFAULT 'details available from 3 pm to 9am',
  PRIMARY KEY (`Tiker_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TradingCompanies`
--

LOCK TABLES `TradingCompanies` WRITE;
/*!40000 ALTER TABLE `TradingCompanies` DISABLE KEYS */;
/*!40000 ALTER TABLE `TradingCompanies` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-07-15 14:57:20
