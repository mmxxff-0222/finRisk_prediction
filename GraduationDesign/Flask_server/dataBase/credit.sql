-- MySQL dump 10.13  Distrib 8.2.0, for macos13 (x86_64)
--
-- Host: localhost    Database: creditRisk
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `borrower`
--

DROP TABLE IF EXISTS `borrower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrower` (
  `userID` int NOT NULL,
  `emp_length` varchar(11) NOT NULL,
  `addr_state` varchar(11) NOT NULL,
  `home_ownership` varchar(20) NOT NULL,
  `annual_inc` float NOT NULL,
  `verification_status` varchar(30) NOT NULL,
  PRIMARY KEY (`userID`),
  CONSTRAINT `borrower_user_userID_fk` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrower`
--

LOCK TABLES `borrower` WRITE;
/*!40000 ALTER TABLE `borrower` DISABLE KEYS */;
INSERT INTO `borrower` VALUES (3,'5 years','LA','RENT',30000,'Not Verified'),(4,'4 years','OH','MORTGAGE',147000,'Verified'),(5,'9 years','IL','RENT',65000,'Verified'),(6,'< 1 year','SC','RENT',40000,'Not Verified'),(7,'9 years','VA','RENT',124000,'Source Verified'),(8,'5 years','MA','MORTGAGE',100000,'Source Verified'),(9,'10+ years','MS','MORTGAGE',52000,'Source Verified'),(10,'10+ years','TX','MORTGAGE',66000,'Source Verified'),(11,'9 years','GA','MORTGAGE',101000,'Source Verified'),(12,'5 years','WI','MORTGAGE',110000,'Source Verified'),(13,'4 years','CO','MORTGAGE',90000,'Verified'),(14,'1 year','PA','OWN',45000,'Source Verified'),(15,'5 years','NJ','RENT',50000,'Verified'),(16,'10+ years','CA','MORTGAGE',30000,'Not Verified'),(17,'7 years','CA','MORTGAGE',48000,'Not Verified'),(18,'1 year','CA','MORTGAGE',45000,'Verified'),(19,'10+ years','CT','MORTGAGE',95000,'Not Verified'),(20,'< 1 year','PA','MORTGAGE',40000,'Not Verified');
/*!40000 ALTER TABLE `borrower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `financial_health`
--

DROP TABLE IF EXISTS `financial_health`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `financial_health` (
  `userID` int NOT NULL,
  `dti` float NOT NULL,
  `delinq_2yrs` float NOT NULL,
  `open_acc` float NOT NULL,
  `pub_rec` float NOT NULL,
  `acc_now_delinq` float NOT NULL,
  `tot_coll_amt` float NOT NULL,
  `tot_cur_bal` float NOT NULL,
  `revol_bal` float NOT NULL,
  `revol_util` float NOT NULL,
  `total_acc` float NOT NULL,
  `total_pymnt` float NOT NULL,
  `total_rec_late_fee` float NOT NULL,
  `last_pymnt_amnt` float NOT NULL,
  PRIMARY KEY (`userID`),
  CONSTRAINT `financial_health_user_userID_fk` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `financial_health`
--

LOCK TABLES `financial_health` WRITE;
/*!40000 ALTER TABLE `financial_health` DISABLE KEYS */;
INSERT INTO `financial_health` VALUES (3,36.12,0,11,1,0,0,34210,9797,64.9,13,1248.29,0,251.61),(4,17.29,0,15,0,0,0,344082,39304,78.8,48,34995.1,0,29733.2),(5,18.2,0,14,0,0,0,18703,18153,56.2,18,24163.2,0,805.46),(6,15.15,0,6,0,0,0,14983,12598,51,10,1470.74,0,240.89),(7,16.93,0,8,0,0,965,270549,20261,91.3,18,0,0,0),(8,16.29,1,15,0,0,0,89495,24018,80.3,35,8919.3,0,594.62),(9,36.23,0,24,0,0,0,169143,26648,63.8,38,24945,0,23360.3),(10,18.49,0,6,0,0,1402,112244,5076,97.6,11,9974.79,0,474.99),(11,13.98,0,18,1,0,0,36415,30953,58.8,34,5925.84,0,1196.05),(12,11.28,0,13,0,0,0,257138,25509,48.6,23,12519.9,0,10609.8),(13,39.55,0,11,0,0,0,335896,92548,78.9,26,13367.3,0,954.81),(14,9.13,1,14,0,0,0,19229,16066,33.1,26,12298.4,0,2357.86),(15,23.62,1,12,0,0,0,33886,18952,28.7,20,705.62,0,358.27),(16,5.96,0,6,1,0,0,101576,3693,33,23,96.28,0,98.88),(17,27.28,1,7,0,0,0,182690,19405,94.7,24,2018.95,0,297.41),(18,19.61,1,16,0,0,0,11895,9520,34.2,25,364.88,0,379.76),(19,14.27,0,11,0,0,0,212266,10501,38.7,42,14516.2,0,500.65),(20,4.44,0,7,0,0,0,135428,4547,13.1,9,5746.83,0,343.39);
/*!40000 ALTER TABLE `financial_health` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan` (
  `loanID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `purpose` varchar(50) NOT NULL,
  `application_type` varchar(11) NOT NULL,
  `loan_amnt` float NOT NULL,
  `term` varchar(11) NOT NULL,
  `int_rate` float NOT NULL,
  `installment` float NOT NULL,
  `grade` varchar(11) NOT NULL,
  PRIMARY KEY (`loanID`),
  KEY `loan_user_userID_fk` (`userID`),
  CONSTRAINT `loan_user_userID_fk` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` VALUES (4,4,'debt_consolidation','INDIVIDUAL',32000,' 60 months',14.65,755.42,'C'),(5,5,'debt_consolidation','INDIVIDUAL',29175,' 60 months',21.98,805.46,'E'),(6,6,'credit_card','INDIVIDUAL',1200,' 36 months',14.09,41.07,'B'),(7,7,'credit_card','INDIVIDUAL',9600,' 36 months',9.17,306.04,'B'),(8,8,'debt_consolidation','INDIVIDUAL',25000,' 60 months',14.99,594.62,'C'),(9,9,'debt_consolidation','INDIVIDUAL',23875,' 60 months',12.69,539.45,'C'),(10,10,'debt_consolidation','INDIVIDUAL',16000,' 60 months',25.57,474.99,'F'),(11,11,'debt_consolidation','INDIVIDUAL',35000,' 36 months',13.99,1196.05,'C'),(12,12,'debt_consolidation','INDIVIDUAL',12000,' 36 months',9.25,383,'A'),(13,13,'credit_card','INDIVIDUAL',33575,' 60 months',23.43,954.81,'F'),(14,14,'home_improvement','INDIVIDUAL',12000,' 36 months',10.16,388.11,'B'),(15,15,'debt_consolidation','INDIVIDUAL',16000,' 60 months',12.29,358.27,'C'),(16,16,'credit_card','INDIVIDUAL',3000,' 36 months',6.24,91.6,'A'),(17,17,'small_business','INDIVIDUAL',8425,' 36 months',16.29,297.41,'D'),(18,18,'other','INDIVIDUAL',15000,' 60 months',17.86,379.76,'D'),(19,19,'debt_consolidation','INDIVIDUAL',16000,' 36 months',7.9,500.65,'A'),(20,20,'home_improvement','INDIVIDUAL',10000,' 36 months',14.33,343.39,'C');
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `password` varchar(11) NOT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `permission` int NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'Wnufis','123456','17835265340',1),(3,'Eapnrelw','123456','13819078174',1),(4,'Qgzlisi','123456','18790585359',1),(5,'Posaie','123456','18452175984',1),(6,'Uvtbzal','123456','18350832928',1),(7,'Yasqnze','123456','18374550519',1),(8,'Pezlzy','123456','14476747431',1),(9,'Riongtr','123456','15069233899',1),(10,'Xjpbvad','123456','13880398798',1),(11,'Kxrcdzc','123456','14844487505',1),(12,'Aipjbsqz','123456','18226058930',1),(13,'Aumvgu','123456','18354875912',1),(14,'Zosyqjs','123456','13964605227',1),(15,'Pfmbjh','123456','15941440777',1),(16,'Pmnhf','123456','15006607623',1),(17,'Fdtobb','123456','19843793976',1),(18,'Bkpux','123456','13958080690',1),(19,'Uapoih','123456','15733802267',1),(20,'Bpzizbd','123456','17261180830',1),(21,'Zhang San','123456','15131742547',0),(22,'Li Si','123456','19873800171',0),(23,'Wang Wu','123456','14780720502',0),(24,'Meng XiangFu','123456','18857855647',0),(25,'Jack Ma','123456','13522380331',1),(26,'Tom Chen','123456','13331121958',0);
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

-- Dump completed on 2024-04-03 20:56:13
