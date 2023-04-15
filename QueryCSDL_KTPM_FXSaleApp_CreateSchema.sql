CREATE DATABASE  IF NOT EXISTS `fxsalemanagementapp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fxsalemanagementapp`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: fxsalemanagementapp
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `chinhanh`
--

DROP TABLE IF EXISTS `chinhanh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chinhanh` (
  `id_chi_nhanh` int NOT NULL,
  `dia_chi` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_chi_nhanh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chinhanh`
--

LOCK TABLES `chinhanh` WRITE;
/*!40000 ALTER TABLE `chinhanh` DISABLE KEYS */;
INSERT INTO `chinhanh` VALUES (0,'Không có chi nhánh'),(1,'371 Nguyễn Kiệm, Q. Gò Vấp, TP. Hồ Chí Minh'),(2,'97 Võ Văn Tần, Q. 3, TP. Hồ Chí Minh'),(3,'35 Hồ Hảo Hớn, Q. 1, TP. Hồ Chí Minh'),(4,'101 Nguyễn Hiền, Q. Hai Bà Trưng, TP. Hà Nội'),(5,'99 Hùng Vương, Q. Hải Châu, TP. Đà Nẵng');
/*!40000 ALTER TABLE `chinhanh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitiethoadon`
--

DROP TABLE IF EXISTS `chitiethoadon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitiethoadon` (
  `id_CTHD` int NOT NULL,
  `id_san_pham` int DEFAULT NULL,
  `id_hoa_don` int DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `thanh_tien` double DEFAULT NULL,
  PRIMARY KEY (`id_CTHD`),
  KEY `id_san_pham` (`id_san_pham`),
  KEY `id_hoa_don` (`id_hoa_don`),
  CONSTRAINT `chitiethoadon_ibfk_1` FOREIGN KEY (`id_san_pham`) REFERENCES `sanpham` (`id_san_pham`),
  CONSTRAINT `chitiethoadon_ibfk_2` FOREIGN KEY (`id_hoa_don`) REFERENCES `hoadon` (`id_hoa_don`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitiethoadon`
--

LOCK TABLES `chitiethoadon` WRITE;
/*!40000 ALTER TABLE `chitiethoadon` DISABLE KEYS */;
INSERT INTO `chitiethoadon` VALUES (1,1,1,2,400000),(2,2,2,6,960000),(3,7,2,5,700000),(4,22,2,2,120000),(5,32,2,1,6000),(6,32,3,8,48000),(7,37,3,1,34000),(8,22,3,5,300000),(9,7,3,7,980000),(10,2,3,3,480000),(11,2,4,1,160000),(12,17,5,6,840000),(13,22,5,5,300000),(14,2,5,1,160000),(15,5,6,3,42000),(16,10,6,5,520000),(17,14,6,8,224000),(18,20,6,9,189000),(19,25,6,2,4000),(20,35,7,10,300000),(21,39,7,10,1080000),(22,20,7,1,21000),(23,8,8,1,86000),(24,15,8,6,84000),(25,19,8,5,50000),(26,24,8,5,540000),(27,34,8,2,760000),(28,30,8,9,0),(29,4,8,1,84000),(30,4,9,9,756000),(31,8,9,6,516000),(32,15,9,1,14000),(33,19,9,5,50000);
/*!40000 ALTER TABLE `chitiethoadon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoadon`
--

DROP TABLE IF EXISTS `hoadon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoadon` (
  `id_hoa_don` int NOT NULL,
  `id_nhan_vien` int DEFAULT NULL,
  `id_chi_nhanh` int DEFAULT NULL,
  `id_thanh_vien` int DEFAULT NULL,
  `tong_tien` double DEFAULT NULL,
  `so_tien_nhan` double DEFAULT NULL,
  `ngay_CT` date DEFAULT NULL,
  PRIMARY KEY (`id_hoa_don`),
  KEY `id_nhan_vien` (`id_nhan_vien`),
  KEY `id_chi_nhanh` (`id_chi_nhanh`),
  KEY `id_thanh_vien` (`id_thanh_vien`),
  CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`id_nhan_vien`) REFERENCES `nhanvien` (`id_nhan_vien`),
  CONSTRAINT `hoadon_ibfk_2` FOREIGN KEY (`id_chi_nhanh`) REFERENCES `chinhanh` (`id_chi_nhanh`),
  CONSTRAINT `hoadon_ibfk_3` FOREIGN KEY (`id_thanh_vien`) REFERENCES `thanhvien` (`id_thanh_vien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoadon`
--

LOCK TABLES `hoadon` WRITE;
/*!40000 ALTER TABLE `hoadon` DISABLE KEYS */;
INSERT INTO `hoadon` VALUES (1,1,1,1,120000,150000,'2022-03-22'),(2,2,2,1,1786000,3000000,'2023-04-15'),(3,2,2,1,1842000,2000000,'2023-04-15'),(4,2,2,1,160000,200000,'2023-04-15'),(5,2,2,9,1300000,9000000,'2023-04-15'),(6,5,5,6,979000,1000000,'2023-04-15'),(7,5,5,15,1260900,1300000,'2023-04-15'),(8,9,4,0,1604000,1700000,'2023-04-15'),(9,9,4,0,1336000,1400000,'2023-04-15');
/*!40000 ALTER TABLE `hoadon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khuyenmai`
--

DROP TABLE IF EXISTS `khuyenmai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khuyenmai` (
  `id_khuyen_mai` int NOT NULL,
  `ten_khuyen_mai` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `gia_tri` double DEFAULT NULL,
  `ngay_bat_dau` date DEFAULT NULL,
  `ngay_ket_thuc` date DEFAULT NULL,
  PRIMARY KEY (`id_khuyen_mai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khuyenmai`
--

LOCK TABLES `khuyenmai` WRITE;
/*!40000 ALTER TABLE `khuyenmai` DISABLE KEYS */;
INSERT INTO `khuyenmai` VALUES (0,'Không có khuyến mãi',0,'1000-01-01','1000-01-01'),(1,'Giảm 10%',10000,'2023-01-19','2023-02-23'),(2,'Giảm 15%',15000,'2023-02-02','2023-03-29'),(3,'Giảm 20%',40000,'2023-03-15','2023-04-22'),(4,'Giảm 25%',20000,'2023-04-10','2023-05-02'),(5,'Giảm 30%',60000,'2023-04-01','2023-04-24'),(6,'Giảm 35%',80000,'2023-06-06','2023-06-24'),(7,'Giảm 40%',35000,'2023-07-05','2023-08-28'),(8,'Giảm 45%',30000,'2023-03-18','2023-04-17'),(9,'Giảm 50%',90000,'2023-10-15','2023-11-20'),(10,'Giảm 55%',50000,'2023-11-11','2023-12-24'),(11,'Giảm  60%',100000,'2023-03-31','2023-04-30');
/*!40000 ALTER TABLE `khuyenmai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhanvien`
--

DROP TABLE IF EXISTS `nhanvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhanvien` (
  `id_nhan_vien` int NOT NULL,
  `ho_nhan_vien` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `ten_nhan_vien` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `id_chi_nhanh` int DEFAULT NULL,
  `tai_khoan` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `mat_khau` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `quan_ly` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_nhan_vien`),
  UNIQUE KEY `tai_khoan` (`tai_khoan`),
  KEY `id_chi_nhanh` (`id_chi_nhanh`),
  CONSTRAINT `nhanvien_ibfk_1` FOREIGN KEY (`id_chi_nhanh`) REFERENCES `chinhanh` (`id_chi_nhanh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhanvien`
--

LOCK TABLES `nhanvien` WRITE;
/*!40000 ALTER TABLE `nhanvien` DISABLE KEYS */;
INSERT INTO `nhanvien` VALUES (1,'Dương Hữu',' Thành',1,'thanh','1',1),(2,'Trần Đăng',' Tuấn',2,'tuan','1',0),(3,'Nguyễn Thanh',' Hải',3,'hai','1',0),(4,'Hoàng Thị Thúy',' Quỳnh',4,'quynh','1',0),(5,'Bạch Thị Cẩm',' Tú',5,'tu','1',0),(6,'Nguyễn Thùy',' Chi',1,'chi','1',0),(7,'Cao Quốc',' Bảo',2,'bao','1',0),(8,'Mai Kim',' Trí',3,'tri','1',0),(9,'Lê Thị Huỳnh',' Như',4,'nhu','1',0),(10,'Nguyễn Vũ Diễm',' My',5,'my','1',0),(11,'Ngô Bá',' Khá',1,'kha','1',1),(12,'Thái Thị Thanh',' Nga',2,'nga','1',0),(13,'Lê Hoài',' Việt',3,'viet','1',0),(14,'Võ Minh',' Hiếu',4,'hieu','1',0),(15,'Nguyễn Văn',' Bảy',5,'bay','1',0);
/*!40000 ALTER TABLE `nhanvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sanpham`
--

DROP TABLE IF EXISTS `sanpham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanpham` (
  `id_san_pham` int NOT NULL,
  `ten_san_pham` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `gia` double DEFAULT NULL,
  `don_vi` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `id_khuyen_mai` int DEFAULT NULL,
  `id_chi_nhanh` int DEFAULT NULL,
  PRIMARY KEY (`id_san_pham`),
  KEY `id_khuyen_mai` (`id_khuyen_mai`),
  KEY `id_chi_nhanh` (`id_chi_nhanh`),
  CONSTRAINT `sanpham_ibfk_1` FOREIGN KEY (`id_khuyen_mai`) REFERENCES `khuyenmai` (`id_khuyen_mai`),
  CONSTRAINT `sanpham_ibfk_2` FOREIGN KEY (`id_chi_nhanh`) REFERENCES `chinhanh` (`id_chi_nhanh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sanpham`
--

LOCK TABLES `sanpham` WRITE;
/*!40000 ALTER TABLE `sanpham` DISABLE KEYS */;
INSERT INTO `sanpham` VALUES (1,'Ức gà phi lê',106000,'kg',8,1),(2,'Tôm càng xanh',190000,'kg',8,2),(3,'Trứng gà Happy Egg',28000,'hộp 10 quả',NULL,3),(4,'Phô mai que sữa',84000,'gói',7,4),(5,'Nấm bào ngư trắng',14000,'hộp',6,5),(6,'Thanh long đỏ',48000,'kg',NULL,1),(7,'Dầu đậu nhà Simply',140000,'chai 2 lít',9,2),(8,'Hạt nêm Knor',86000,'gói 900g',NULL,4),(9,'Kem khoai môn Vinamilk',82000,'hộp 500g',4,3),(10,'Gạo thơm lài',104000,'túi 5kg',2,5),(11,'Mì lẫu thái tôm',180000,'thùng 24 ly',5,1),(12,'Strongbow dâu đen',390000,'thùng 24 lon',10,2),(13,'Coca cola Zero',132000,'thùng 24 chai',9,3),(14,'Trà hoa cúc green',28000,'hộp',NULL,5),(15,'TH true water',14000,'hộp',7,4),(16,'Sữa bột Anlene',420000,'lon',6,1),(17,'Dầu gội Rejoice',200000,'chai',5,2),(18,'Nước giặt Downy',86000,'gói',NULL,3),(19,'Kem đánh răng Closeup',30000,'hộp',4,4),(20,'Xúc xích heo Ponnie',21000,'gói',NULL,5),(21,'Kim chi cải thảo',23000,'gói',NULL,1),(22,'Chả mực Hạ Long',80000,'gói',4,2),(23,'Cá ngừ đại dương',166000,'kg',6,3),(24,'Cafe phố',108000,'gói',7,4),(25,'Bông cải trắng',62000,'kg',5,5),(26,'Snack tôm hùm',50000,'lon',11,1),(27,'Nước tương Chinsu',19000,'chai',NULL,2),(28,'String gold',180000,'thùng 24 lon',4,3),(29,'Nước lau sàn Sunlight',64000,'chai',4,5),(30,'Lotte Xylitol Mint',34000,'hộp',11,4),(31,'Kitkat matcha',52000,'gói',NULL,1),(32,'Cơm nắm mayo',26000,'gói',4,2),(33,'Bánh caro hoàng kim',70000,'túi',6,3),(34,'Bia tiger crystal',380000,'thùng 24 lon',7,4),(35,'Pizza phô mai',90000,'hộp',5,5),(36,'Bánh bao trứng muối',58000,'gói',11,1),(37,'Váng sữa monte',34000,'lốc',NULL,2),(38,'Chân gà rút xương',120000,'gói',4,3),(39,'Kem chống nắng sunplay',128000,'hộp',4,5),(40,'Ô gấp Improok',150000,'chiếc',3,1);
/*!40000 ALTER TABLE `sanpham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thanhvien`
--

DROP TABLE IF EXISTS `thanhvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thanhvien` (
  `id_thanh_vien` int NOT NULL,
  `ho_thanh_vien` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `ten_thanh_vien` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `ngay_sinh_thanh_vien` date DEFAULT NULL,
  `so_dien_thoai` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_thanh_vien`),
  UNIQUE KEY `so_dien_thoai` (`so_dien_thoai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thanhvien`
--

LOCK TABLES `thanhvien` WRITE;
/*!40000 ALTER TABLE `thanhvien` DISABLE KEYS */;
INSERT INTO `thanhvien` VALUES (0,'NULL NULL',' NULL','1900-01-01','0000000000'),(1,'Nguyễn Lệ Nam',' Em','1996-01-15','0123456789'),(2,'Nguyễn Thị Thanh',' Thảo','2002-01-03','0987654321'),(3,'Hồ Quỳnh',' Hương','1980-10-16','0912345678'),(4,'Dương Hoàng',' Yến','1991-06-15','0999999999'),(5,'Võ Vũ Trường',' Giang','1983-04-20','0966666666'),(6,'Lý Huỳnh',' My','1994-11-26','0933333333'),(7,'Huỳnh Tấn',' Đạt','1993-07-17','0977777777'),(8,'Đặng Thu',' Hà','2000-11-15','09667898989'),(9,'Lê Thành',' Dương','1988-07-05','0944444444'),(10,'Đàm Vĩnh',' Hưng','1971-10-02','0911111111'),(11,'Mai Thị Tuyết',' Trinh','1992-04-04','0211111111'),(12,'Nguyễn Cao Thảo',' Nguyên','2002-09-04','0466666666'),(13,'Nguyễn Tiến',' Đạt','1965-08-26','0522222222'),(14,'Nguyễn Thị Mộng',' Tuyền','1974-11-28','0788888888'),(15,'Lâm Tâm',' Như','1978-04-15','0399999999');
/*!40000 ALTER TABLE `thanhvien` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-15 16:57:22
