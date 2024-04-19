-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: demo_vn_work
-- ------------------------------------------------------
-- Server version	8.2.0

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q0uja26qgu1atulenwup9rxyr` (`email`),
  UNIQUE KEY `UK_gex1lmaqpg0ir5g1f5eftyaa1` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'syhien85@hotmail.com','$2a$10$eK3HsRb2pXuLayVHsxBjO.ogIxmpdbcFg1RDYVdyDsrb8k2SXgk82','admin'),(2,'user1@syhien.com','$2a$10$9OIV8BoM3LdYjF4cryEjZ.HMuSKm7LA8reemM6s6jOAdKqjmuS9aa','user1'),(3,'company1@syhien.com','$2a$10$GePng5uFMKvzY03H4hsf6e4bX6SIxrgRBkN5oPmF7GaOpUuRdxyQ2','company1');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_roles`
--

DROP TABLE IF EXISTS `account_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_roles` (
  `account_id` bigint NOT NULL,
  `roles_id` int NOT NULL,
  KEY `FK70s9enq5d1oywl7v8vis5ke5w` (`roles_id`),
  KEY `FKtp61eta5i06bug3w1qr6286uf` (`account_id`),
  CONSTRAINT `FK70s9enq5d1oywl7v8vis5ke5w` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKtp61eta5i06bug3w1qr6286uf` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_roles`
--

LOCK TABLES `account_roles` WRITE;
/*!40000 ALTER TABLE `account_roles` DISABLE KEYS */;
INSERT INTO `account_roles` VALUES (1,1),(2,2),(3,3);
/*!40000 ALTER TABLE `account_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `application_status` tinyint DEFAULT NULL,
  `employee_status` tinyint DEFAULT NULL,
  `job_id` bigint DEFAULT NULL,
  `resume_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UniqueResumeIdAndJobId` (`resume_id`,`job_id`),
  KEY `FKls6sryk64ga8o5t4bym8qu3vm` (`job_id`),
  CONSTRAINT `FK1l7lh9cf5rwfbfiln6ufoap58` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`),
  CONSTRAINT `FKls6sryk64ga8o5t4bym8qu3vm` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
  CONSTRAINT `application_chk_1` CHECK ((`application_status` between 0 and 0)),
  CONSTRAINT `application_chk_2` CHECK ((`employee_status` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (1,'2024-04-19 14:21:29.203000','2024-04-19 14:21:29.203000',0,NULL,1,1);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `created_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `account_id` bigint NOT NULL,
  `company_info_id` bigint DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `UK_bgfrdu9f5oyf4goxrnt0fjifr` (`company_info_id`),
  CONSTRAINT `FK75jxrkyqa7rjrx2sv72j7mnrf` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FKqr8arsgx7anax6nj3ey8kvpur` FOREIGN KEY (`company_info_id`) REFERENCES `company_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES ('2024-04-19 00:32:04.474000','2024-04-19 00:32:04.474000',3,1);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_info`
--

DROP TABLE IF EXISTS `company_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `company_logo_url` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `company_profile` varchar(255) DEFAULT NULL,
  `company_size` tinyint DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `why_work_with_us` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `company_info_chk_1` CHECK ((`company_size` between 0 and 4))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_info`
--

LOCK TABLES `company_info` WRITE;
/*!40000 ALTER TABLE `company_info` DISABLE KEYS */;
INSERT INTO `company_info` VALUES (1,'address','companyLogoUrl','companyName','companyProfile',1,'contactEmail','contactName','website','whyWorkWithUs');
/*!40000 ALTER TABLE `company_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `industry`
--

DROP TABLE IF EXISTS `industry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `industry` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `industry`
--

LOCK TABLES `industry` WRITE;
/*!40000 ALTER TABLE `industry` DISABLE KEYS */;
INSERT INTO `industry` VALUES (1,'Dịch vụ lưu trú/Nhà hàng/Khách sạn/Du lịch'),(2,'Kế toán/Kiểm toán'),(3,'Nông nghiệp/Lâm nghiệp/Nuôi trồng thủy sản'),(4,'Thú y'),(5,'Kiến trúc/Thiết kế nội thất'),(6,'Nghệ thuật/Giải trí'),(7,'Tự động hoá'),(8,'Ô tô'),(9,'Ngân hàng'),(10,'Làm đẹp (Mỹ phẩm) & Chăm sóc cá nhân'),(11,'Vật liệu xây dựng'),(12,'Hoá chất/Hoá sinh'),(13,'Kỹ thuật xây dựng/Cơ sở hạ tầng'),(14,'Thương mại điện tử'),(15,'Giáo dục/Đào Tạo'),(16,'Điện/Điện tử'),(17,'Dịch vụ môi trường/Chất thải'),(18,'Thời trang/Trang sức'),(19,'Tài Chính'),(20,'Hàng tiêu dùng'),(21,'Nội thất/Gỗ'),(22,'Chính phủ & NGO'),(23,'Nhập khẩu/Xuất khẩu'),(24,'Bảo hiểm'),(25,'Phần Mềm CNTT/Dịch vụ Phần mềm'),(26,'Hệ thống CNTT & Thiết bị'),(27,'Cung cấp nhân lực'),(28,'Luật/Dịch vụ pháp lý'),(29,'Hậu cần/Giao nhận'),(30,'Sản xuất'),(31,'Cơ khí/Máy móc/Thiết bị công nghiệp'),(32,'Truyền thông/Báo chí/Quảng cáo'),(33,'Thiết bị y tế'),(34,'Dịch vụ Y tế/Chăm sóc sức khỏe'),(35,'Khai khoáng/Dầu khí'),(36,'Bao bì/In ấn/Dán nhãn'),(37,'Dược phẩm'),(38,'Nhựa & Cao su'),(39,'Sản xuất và Phân phối Điện/Khí đốt/Nước'),(40,'Bất Động Sản/Cho thuê'),(41,'Nghiên cứu'),(42,'Bán lẻ/Bán sỉ'),(43,'Chứng khoán'),(44,'Chuỗi cung ứng'),(45,'Viễn thông'),(46,'Dệt may/May mặc/Giày dép'),(47,'Vận tải'),(48,'Dịch vụ kho bãi'),(49,'Khác'),(50,'IT Helpdesk');
/*!40000 ALTER TABLE `industry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `company_account_id` bigint DEFAULT NULL,
  `job_detail_id` bigint DEFAULT NULL,
  `job_status_id` bigint DEFAULT NULL,
  `job_summary_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_30knw05hjoiqcac4t6iv86gnu` (`job_detail_id`),
  UNIQUE KEY `UK_157wwm6ucp3xrs4u40o0u0yvs` (`job_status_id`),
  UNIQUE KEY `UK_jcyh15bnte6kdv3hyy4w2tgdv` (`job_summary_id`),
  KEY `FKcj3r0fofyg8dmtb6by9rqt94l` (`company_account_id`),
  CONSTRAINT `FK37ihwhh9p8esoouppo1xd1peb` FOREIGN KEY (`job_detail_id`) REFERENCES `job_detail` (`id`),
  CONSTRAINT `FK8u96qf9vkum8nfet2p87m2uvs` FOREIGN KEY (`job_summary_id`) REFERENCES `job_summary` (`id`),
  CONSTRAINT `FKcj3r0fofyg8dmtb6by9rqt94l` FOREIGN KEY (`company_account_id`) REFERENCES `company` (`account_id`),
  CONSTRAINT `FKga3rfgg5id2vai41oh9h1hhg8` FOREIGN KEY (`job_status_id`) REFERENCES `job_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,'2024-04-19 14:19:55.820000','2024-04-19 14:19:55.820000',3,1,1,1);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_detail`
--

DROP TABLE IF EXISTS `job_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `benefits` varchar(255) DEFAULT NULL,
  `job_description` varchar(255) DEFAULT NULL,
  `job_requirement` varchar(255) DEFAULT NULL,
  `job_title` varchar(255) DEFAULT NULL,
  `working_location_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4p8459lk146ep4al9mdi1h3d7` (`working_location_id`),
  CONSTRAINT `FK4p8459lk146ep4al9mdi1h3d7` FOREIGN KEY (`working_location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_detail`
--

LOCK TABLES `job_detail` WRITE;
/*!40000 ALTER TABLE `job_detail` DISABLE KEYS */;
INSERT INTO `job_detail` VALUES (1,'string','string','string','string',2);
/*!40000 ALTER TABLE `job_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_function`
--

DROP TABLE IF EXISTS `job_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_function` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `job_function_parent_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK59oygjik9mgnklb9w2gr0tvse` (`job_function_parent_id`),
  CONSTRAINT `FK59oygjik9mgnklb9w2gr0tvse` FOREIGN KEY (`job_function_parent_id`) REFERENCES `job_function_parent` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_function`
--

LOCK TABLES `job_function` WRITE;
/*!40000 ALTER TABLE `job_function` DISABLE KEYS */;
INSERT INTO `job_function` VALUES (1,'Tư Vấn Giáo Dục',1),(2,'Quản Lý Giáo Dục',1),(3,'Nghiên Cứu Học Thuật',1),(4,'Dịch Vụ Sinh Viên/Hỗ Trợ Học Viên',1),(5,'Giảng Dạy/Đào Tạo',1),(6,'Kế Toán Thanh Toán',2),(7,'Kế Toán Công Nợ',2),(8,'Kiểm Toán',2),(9,'Kế hoạch/Tư Vấn Doanh Nghiệp',2),(10,'Kế Toán Chi Phí',2),(11,'Kế Toán Tài Chính',2),(12,'Kiểm Soát Viên Tài Chính',2),(13,'Kế Toán Tổng Hợp',2),(14,'Kế Toán Quản Trị',2),(15,'Kế Toán Thuế',2),(16,'Kế Toán Doanh Thu',2),(17,'Nông/Lâm/Ngư nghiệp',3),(18,'Thiết Kế Kiến Trúc/Họa Viên Kiến Trúc',4),(19,'Xây Dựng',4),(20,'Phát Triển Dự Án/Đấu Thầu',4),(21,'An Toàn Lao Động',4),(22,'Thiết Kế Nội Thất',4),(23,'Quản Lý Dự Án',4),(24,'Thiết Kế & Quy Hoạch Đô Thị',4),(25,'Phân Tích Kinh Doanh/Phân Tích Hệ Thống',5),(26,'Quản Trị Cơ Sở Dữ Liệu',5),(27,'Data Engineer/Data Analyst/AI',5),(28,'Chuyển Đổi Số',5),(29,'Phần Cứng Máy Tính',5),(30,'Quản Lý Công Nghệ Thông Tin',5),(31,'IT Support/Help Desk',5),(32,'System/Cloud/DevOps Engineer',5),(33,'Quản Lý Dự Án Công Nghệ',5),(34,'QA/QC/Software Testing',5),(35,'Bảo Mật Công Nghệ Thông Tin',5),(36,'Phần Mềm Máy Tính',5),(37,'Viễn Thông',5),(38,'UX/UI Design',5),(39,'Dịch Vụ Khách Hàng',6),(40,'Dịch Vụ Khách Hàng - Call Center',6),(41,'Dịch Vụ Khách Hàng - Hướng Khách Hàng',6),(42,'Thiết Kế Thời Trang/Trang Sức',7),(43,'Thiết Kế Đồ Họa',7),(44,'Thiết Kế Công Nghiệp/Kỹ Thuật',7),(45,'Chỉnh Sửa Video',7),(46,'Dịch Vụ Hàng Không',8),(47,'Dịch Vụ Vận Tải Công Cộng',8),(48,'Vận Tải Đường Sắt & Hàng Hải',8),(49,'Vận Tải Đường Bộ',8),(50,'Kỹ Thuật Ô Tô',9),(51,'Công Nghệ Sinh Học',9),(52,'Kỹ Thuật Hóa Học',9),(53,'Kỹ Thuật Điện/Điện Tử',9),(54,'Kỹ Thuật Môi Trường',9),(55,'Công Nghệ Thực Phẩm',9),(56,'Cơ Khí & Điện Lạnh',9),(57,'Khai Thác Mỏ',9),(58,'Điện/Nước/Chất Thải',9),(59,'Quản Lý Quan Hệ Khách Hàng',10),(60,'Phân Tích & Báo Cáo Tài Chính',10),(61,'Dịch Vụ Hỗ Trợ Khách Hàng',10),(62,'Tuân Thủ & Kiểm Soát Rủi Ro',10),(63,'Tín Dụng',10),(64,'Thu Hồi Nợ',10),(65,'Quản Lý Quỹ',10),(66,'Đầu Tư Tài Chính',10),(67,'Môi Giới & Giao Dịch Chứng Khoán',10),(68,'Quầy Bar/Đồ Uống/Phục vụ',11),(69,'Đầu Bếp',11),(70,'Quản Lý F&B',11),(71,'Gắn Kết Nhân Viên',12),(72,'Lương Thưởng & Phúc Lợi',12),(73,'Nhân Sự Tổng Hợp',12),(74,'Quản Trị Hiệu Suất & Sự Nghiệp',12),(75,'Đào Tạo Và Phát Triển',12),(76,'Tuyển Dụng',12),(77,'Quản Lý Đội Xe',13),(78,'Vận Tải/Giao Nhận Hàng Hóa',13),(79,'Xuất Nhập Khẩu & Thủ Tục Hải Quan',13),(80,'Thu Mua & Quản Trị Hàng Tồn Kho',13),(81,'Quản Lý Chuỗi Cung Ứng',13),(82,'Quản Lý Kho & Phân Phối',13),(83,'Định Phí Bảo Hiểm',14),(84,'Bồi Thường Bảo Hiểm',14),(85,'Tư Vấn Rủi Ro',14),(86,'Bao Tiêu/Bảo Lãnh',14),(87,'Bộ Phận Tiền Sảnh & Dịch Vụ Khách Hàng',15),(88,'Đặt Phòng Khách Sạn',15),(89,'Vệ Sinh Buồng Phòng',15),(90,'Công Ty Kinh Doanh Lữ Hành',15),(91,'Hướng Dẫn Viên Du Lịch',15),(92,'Đại Lý Du Lịch',15),(93,'Luật Tài Chính Ngân Hàng Thương mại',16),(94,'Luật Xây Dựng',16),(95,'Tư Vấn Pháp Lý',16),(96,'Luật Lao động/Hưu Trí',16),(97,'Luật Sở Hữu Trí Tuệ',16),(98,'Thư Ký Luật & Trợ Lý Luật',16),(99,'Quản Lý Thi Hành Pháp Luật',16),(100,'Thư Ký Pháp Lý',16),(101,'Luật Thuế',16),(102,'Quản Lý Tài Khoản Khách Hàng',17),(103,'Quản Lý Thương Hiệu',17),(104,'Tiếp Thị Nội Dung',17),(105,'Tiếp Thị Trực Tuyến',17),(106,'Quản Lý Sự Kiện',17),(107,'Nghiên Cứu & Phân Tích Thị Trường',17),(108,'Tiếp Thị',17),(109,'Quản Lý & Phát Triển Sản Phẩm',17),(110,'Quan Hệ Công Chúng',17),(111,'Tiếp Thị Thương Mại',17),(112,'Đạo Diễn Nghệ Thuật/Nhiếp Ảnh',18),(113,'In Ấn & Xuất Bản',18),(114,'Sản Xuất Chương Trình',18),(115,'Bác Sĩ/Điều Trị Đa Khoa/Điều Trị Nội Trú',19),(116,'Kỹ Thuật Viên Y Tế',19),(117,'Y Tá',19),(118,'Dược Sĩ',19),(119,'Tư Vấn Tâm Lý & Công Tác Xã Hội',19),(120,'Hành Chính',20),(121,'Điều Phối',20),(122,'Biên Phiên Dịch',20),(123,'Quản Lý Văn Phòng',20),(124,'Thu Mua',20),(125,'Lễ Tân/Tiếp Tân',20),(126,'Trợ Lý Kinh Doanh',20),(127,'Thư Ký',20),(128,'Bảo Vệ',20),(129,'Bán Hàng/Phát Triển Kinh Doanh',21),(130,'Bán Hàng Kỹ Thuật',21),(131,'Bán Hàng Qua Điện Thoại',21),(132,'Cơ Khí Tự Động Hoá',22),(133,'Kỹ Thuật CNC',22),(134,'Bảo trì/Bảo Dưỡng',22),(135,'In Ấn',22),(136,'Phân Tích Dự Án Bất Động Sản',23),(137,'Quản Lý Cơ Sở Vật Chất',23),(138,'Kinh Doanh Thương Mại, Cho Thuê & Quản Lý Tài Sản',23),(139,'Cho Thuê & Quản Lý Căn Hộ',23),(140,'Phát Triển Bất Động Sản',23),(141,'Định Giá',23),(142,'Quản Lý Khu Vực',24),(143,'Thu Mua',24),(144,'Trợ Lý Bán Lẻ',24),(145,'Quản Lý Cửa Hàng',24),(146,'NGO/Phi Lợi Nhuận',25),(147,'Chính sách, Quy hoạch & Quy định',25),(148,'Quản Lý Đơn Hàng',26),(149,'Phát Triển Sản Phẩm May Mặc',26),(150,'Quy Trình & Lắp Ráp',27),(151,'Vận Hành Máy Móc',27),(152,'Phân Tích Sản Xuất',27),(153,'Hoạch Định & Quản Lý Sản Xuất',27),(154,'Đảm Bảo Chất Lượng/Kiểm Soát Chất Lượng/Quản Lý Chất Lượng',27),(155,'Nghiên Cứu & Phát Triển',27),(156,'Phân Phối Dược Phẩm',28),(157,'CEO',29),(158,'Quản Lý Cấp Cao',29),(159,'Khác',30);
/*!40000 ALTER TABLE `job_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_function_parent`
--

DROP TABLE IF EXISTS `job_function_parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_function_parent` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_function_parent`
--

LOCK TABLES `job_function_parent` WRITE;
/*!40000 ALTER TABLE `job_function_parent` DISABLE KEYS */;
INSERT INTO `job_function_parent` VALUES (1,'Giáo Dục'),(2,'Kế Toán/Kiểm Toán'),(3,'Nông/Lâm/Ngư Nghiệp'),(4,'Kiến Trúc/Xây Dựng'),(5,'Công Nghệ Thông Tin/Viễn Thông'),(6,'Dịch Vụ Khách Hàng'),(7,'Thiết Kế'),(8,'Vận Tải'),(9,'Khoa Học & Kỹ Thuật'),(10,'Ngân Hàng & Dịch Vụ Tài Chính'),(11,'Dịch Vụ Ăn Uống'),(12,'Nhân Sự/Tuyển Dụng'),(13,'Hậu Cần/Xuất Nhập Khẩu/Kho Bãi'),(14,'Bảo Hiểm'),(15,'Nhà Hàng - Khách Sạn/Du Lịch'),(16,'Pháp Lý'),(17,'Tiếp Thị, Quảng Cáo/Truyền Thông'),(18,'Nghệ thuật, Truyền thông/In ấn/Xuất bản'),(19,'Y Tế/Chăm Sóc Sức Khoẻ'),(20,'Hành Chính Văn Phòng'),(21,'Kinh Doanh'),(22,'Kỹ Thuật'),(23,'Bất Động Sản'),(24,'Bán Lẻ/Tiêu Dùng'),(25,'Chính Phủ/Phi Lợi Nhuận'),(26,'Dệt May/Da Giày'),(27,'Sản Xuất'),(28,'Dược'),(29,'CEO & General Management'),(30,'Khác');
/*!40000 ALTER TABLE `job_function_parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_skill`
--

DROP TABLE IF EXISTS `job_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_skill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `job_id` bigint DEFAULT NULL,
  `skill_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9ix4wg520ii2gu2felxdhdup6` (`job_id`),
  KEY `FKj33qbbf3vk1lvhqpcosnh54u1` (`skill_id`),
  CONSTRAINT `FK9ix4wg520ii2gu2felxdhdup6` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
  CONSTRAINT `FKj33qbbf3vk1lvhqpcosnh54u1` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_skill`
--

LOCK TABLES `job_skill` WRITE;
/*!40000 ALTER TABLE `job_skill` DISABLE KEYS */;
INSERT INTO `job_skill` VALUES (1,1,1),(2,1,3);
/*!40000 ALTER TABLE `job_skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_status`
--

DROP TABLE IF EXISTS `job_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `approved_on` datetime(6) DEFAULT NULL,
  `expired_on` datetime(6) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `is_approved` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_status`
--

LOCK TABLES `job_status` WRITE;
/*!40000 ALTER TABLE `job_status` DISABLE KEYS */;
INSERT INTO `job_status` VALUES (1,'2024-04-19 14:20:07.544000','2024-05-19 14:20:07.544000',_binary '',_binary '');
/*!40000 ALTER TABLE `job_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_summary`
--

DROP TABLE IF EXISTS `job_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_summary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_salary_visible` bit(1) DEFAULT NULL,
  `job_level` tinyint DEFAULT NULL,
  `pretty_salary` varchar(255) DEFAULT NULL,
  `salary` varchar(255) DEFAULT NULL,
  `salary_max` double DEFAULT NULL,
  `salary_min` double DEFAULT NULL,
  `type_working` tinyint DEFAULT NULL,
  `years_experience` int DEFAULT NULL,
  `job_function_id` int DEFAULT NULL,
  `language_id` int DEFAULT NULL,
  `location_city_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3thpjqs0f941ga27kn5mq21yg` (`job_function_id`),
  KEY `FK53ddscp3dhoogy834x5aq1xyt` (`language_id`),
  KEY `FKa9luchpuhgmeydmysjl83emm9` (`location_city_id`),
  CONSTRAINT `FK3thpjqs0f941ga27kn5mq21yg` FOREIGN KEY (`job_function_id`) REFERENCES `job_function` (`id`),
  CONSTRAINT `FK53ddscp3dhoogy834x5aq1xyt` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `FKa9luchpuhgmeydmysjl83emm9` FOREIGN KEY (`location_city_id`) REFERENCES `location_city` (`id`),
  CONSTRAINT `job_summary_chk_1` CHECK ((`job_level` between 0 and 4)),
  CONSTRAINT `job_summary_chk_2` CHECK ((`type_working` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_summary`
--

LOCK TABLES `job_summary` WRITE;
/*!40000 ALTER TABLE `job_summary` DISABLE KEYS */;
INSERT INTO `job_summary` VALUES (1,_binary '',0,'20000000 - 50000000','20000000 - 50000000',50000000,20000000,0,2,1,1,1);
/*!40000 ALTER TABLE `job_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_summary_industries`
--

DROP TABLE IF EXISTS `job_summary_industries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_summary_industries` (
  `job_summary_id` bigint NOT NULL,
  `industries_id` bigint NOT NULL,
  KEY `FK7lntk3j8p2uwnj1vjlkoppk7x` (`industries_id`),
  KEY `FKq9ftflhis0pdlgssjwe1lfg9w` (`job_summary_id`),
  CONSTRAINT `FK7lntk3j8p2uwnj1vjlkoppk7x` FOREIGN KEY (`industries_id`) REFERENCES `industry` (`id`),
  CONSTRAINT `FKq9ftflhis0pdlgssjwe1lfg9w` FOREIGN KEY (`job_summary_id`) REFERENCES `job_summary` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_summary_industries`
--

LOCK TABLES `job_summary_industries` WRITE;
/*!40000 ALTER TABLE `job_summary_industries` DISABLE KEYS */;
INSERT INTO `job_summary_industries` VALUES (1,1);
/*!40000 ALTER TABLE `job_summary_industries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `language` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES (1,'Tiếng Việt'),(2,'English');
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `location_district_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdnil7ndth7apkgrw7dyd3bcqr` (`location_district_id`),
  CONSTRAINT `FKdnil7ndth7apkgrw7dyd3bcqr` FOREIGN KEY (`location_district_id`) REFERENCES `location_district` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'111 Kim Mã',1),(2,'string',1);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_city`
--

DROP TABLE IF EXISTS `location_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_city` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_city`
--

LOCK TABLES `location_city` WRITE;
/*!40000 ALTER TABLE `location_city` DISABLE KEYS */;
INSERT INTO `location_city` VALUES (1,'Thành phố Hà Nội'),(2,'Tỉnh Hà Giang'),(4,'Tỉnh Cao Bằng'),(6,'Tỉnh Bắc Kạn'),(8,'Tỉnh Tuyên Quang'),(10,'Tỉnh Lào Cai'),(11,'Tỉnh Điện Biên'),(12,'Tỉnh Lai Châu'),(14,'Tỉnh Sơn La'),(15,'Tỉnh Yên Bái'),(17,'Tỉnh Hoà Bình'),(19,'Tỉnh Thái Nguyên'),(20,'Tỉnh Lạng Sơn'),(22,'Tỉnh Quảng Ninh'),(24,'Tỉnh Bắc Giang'),(25,'Tỉnh Phú Thọ'),(26,'Tỉnh Vĩnh Phúc'),(27,'Tỉnh Bắc Ninh'),(30,'Tỉnh Hải Dương'),(31,'Thành phố Hải Phòng'),(33,'Tỉnh Hưng Yên'),(34,'Tỉnh Thái Bình'),(35,'Tỉnh Hà Nam'),(36,'Tỉnh Nam Định'),(37,'Tỉnh Ninh Bình'),(38,'Tỉnh Thanh Hóa'),(40,'Tỉnh Nghệ An'),(42,'Tỉnh Hà Tĩnh'),(44,'Tỉnh Quảng Bình'),(45,'Tỉnh Quảng Trị'),(46,'Tỉnh Thừa Thiên Huế'),(48,'Thành phố Đà Nẵng'),(49,'Tỉnh Quảng Nam'),(51,'Tỉnh Quảng Ngãi'),(52,'Tỉnh Bình Định'),(54,'Tỉnh Phú Yên'),(56,'Tỉnh Khánh Hòa'),(58,'Tỉnh Ninh Thuận'),(60,'Tỉnh Bình Thuận'),(62,'Tỉnh Kon Tum'),(64,'Tỉnh Gia Lai'),(66,'Tỉnh Đắk Lắk'),(67,'Tỉnh Đắk Nông'),(68,'Tỉnh Lâm Đồng'),(70,'Tỉnh Bình Phước'),(72,'Tỉnh Tây Ninh'),(74,'Tỉnh Bình Dương'),(75,'Tỉnh Đồng Nai'),(77,'Tỉnh Bà Rịa - Vũng Tàu'),(79,'Thành phố Hồ Chí Minh'),(80,'Tỉnh Long An'),(82,'Tỉnh Tiền Giang'),(83,'Tỉnh Bến Tre'),(84,'Tỉnh Trà Vinh'),(86,'Tỉnh Vĩnh Long'),(87,'Tỉnh Đồng Tháp'),(89,'Tỉnh An Giang'),(91,'Tỉnh Kiên Giang'),(92,'Thành phố Cần Thơ'),(93,'Tỉnh Hậu Giang'),(94,'Tỉnh Sóc Trăng'),(95,'Tỉnh Bạc Liêu'),(96,'Tỉnh Cà Mau');
/*!40000 ALTER TABLE `location_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_district`
--

DROP TABLE IF EXISTS `location_district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_district` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `location_city_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd51j2asxw2dhh3oc71rpf76d4` (`location_city_id`),
  CONSTRAINT `FKd51j2asxw2dhh3oc71rpf76d4` FOREIGN KEY (`location_city_id`) REFERENCES `location_city` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_district`
--

LOCK TABLES `location_district` WRITE;
/*!40000 ALTER TABLE `location_district` DISABLE KEYS */;
INSERT INTO `location_district` VALUES (1,'Quận Ba Đình',1),(2,'Quận Hoàn Kiếm',1),(3,'Quận Tây Hồ',1),(4,'Quận Long Biên',1),(5,'Quận Cầu Giấy',1),(6,'Quận Đống Đa',1),(7,'Quận Hai Bà Trưng',1),(8,'Quận Hoàng Mai',1),(9,'Quận Thanh Xuân',1),(16,'Huyện Sóc Sơn',1),(17,'Huyện Đông Anh',1),(18,'Huyện Gia Lâm',1),(19,'Quận Nam Từ Liêm',1),(20,'Huyện Thanh Trì',1),(21,'Quận Bắc Từ Liêm',1),(24,'Thành phố Hà Giang',2),(26,'Huyện Đồng Văn',2),(27,'Huyện Mèo Vạc',2),(28,'Huyện Yên Minh',2),(29,'Huyện Quản Bạ',2),(30,'Huyện Vị Xuyên',2),(31,'Huyện Bắc Mê',2),(32,'Huyện Hoàng Su Phì',2),(33,'Huyện Xín Mần',2),(34,'Huyện Bắc Quang',2),(35,'Huyện Quang Bình',2),(40,'Thành phố Cao Bằng',4),(42,'Huyện Bảo Lâm',4),(43,'Huyện Bảo Lạc',4),(44,'Huyện Thông Nông',4),(45,'Huyện Hà Quảng',4),(46,'Huyện Trà Lĩnh',4),(47,'Huyện Trùng Khánh',4),(48,'Huyện Hạ Lang',4),(49,'Huyện Quảng Uyên',4),(50,'Huyện Phục Hoà',4),(51,'Huyện Hoà An',4),(52,'Huyện Nguyên Bình',4),(53,'Huyện Thạch An',4),(58,'Thành Phố Bắc Kạn',6),(60,'Huyện Pác Nặm',6),(61,'Huyện Ba Bể',6),(62,'Huyện Ngân Sơn',6),(63,'Huyện Bạch Thông',6),(64,'Huyện Chợ Đồn',6),(65,'Huyện Chợ Mới',6),(66,'Huyện Na Rì',6),(70,'Thành phố Tuyên Quang',8),(71,'Huyện Lâm Bình',8),(72,'Huyện Nà Hang',8),(73,'Huyện Chiêm Hóa',8),(74,'Huyện Hàm Yên',8),(75,'Huyện Yên Sơn',8),(76,'Huyện Sơn Dương',8),(80,'Thành phố Lào Cai',10),(82,'Huyện Bát Xát',10),(83,'Huyện Mường Khương',10),(84,'Huyện Si Ma Cai',10),(85,'Huyện Bắc Hà',10),(86,'Huyện Bảo Thắng',10),(87,'Huyện Bảo Yên',10),(88,'Huyện Sa Pa',10),(89,'Huyện Văn Bàn',10),(94,'Thành phố Điện Biên Phủ',11),(95,'Thị Xã Mường Lay',11),(96,'Huyện Mường Nhé',11),(97,'Huyện Mường Chà',11),(98,'Huyện Tủa Chùa',11),(99,'Huyện Tuần Giáo',11),(100,'Huyện Điện Biên',11),(101,'Huyện Điện Biên Đông',11),(102,'Huyện Mường Ảng',11),(103,'Huyện Nậm Pồ',11),(105,'Thành phố Lai Châu',12),(106,'Huyện Tam Đường',12),(107,'Huyện Mường Tè',12),(108,'Huyện Sìn Hồ',12),(109,'Huyện Phong Thổ',12),(110,'Huyện Than Uyên',12),(111,'Huyện Tân Uyên',12),(112,'Huyện Nậm Nhùn',12),(116,'Thành phố Sơn La',14),(118,'Huyện Quỳnh Nhai',14),(119,'Huyện Thuận Châu',14),(120,'Huyện Mường La',14),(121,'Huyện Bắc Yên',14),(122,'Huyện Phù Yên',14),(123,'Huyện Mộc Châu',14),(124,'Huyện Yên Châu',14),(125,'Huyện Mai Sơn',14),(126,'Huyện Sông Mã',14),(127,'Huyện Sốp Cộp',14),(128,'Huyện Vân Hồ',14),(132,'Thành phố Yên Bái',15),(133,'Thị xã Nghĩa Lộ',15),(135,'Huyện Lục Yên',15),(136,'Huyện Văn Yên',15),(137,'Huyện Mù Căng Chải',15),(138,'Huyện Trấn Yên',15),(139,'Huyện Trạm Tấu',15),(140,'Huyện Văn Chấn',15),(141,'Huyện Yên Bình',15),(148,'Thành phố Hòa Bình',17),(150,'Huyện Đà Bắc',17),(151,'Huyện Kỳ Sơn',17),(152,'Huyện Lương Sơn',17),(153,'Huyện Kim Bôi',17),(154,'Huyện Cao Phong',17),(155,'Huyện Tân Lạc',17),(156,'Huyện Mai Châu',17),(157,'Huyện Lạc Sơn',17),(158,'Huyện Yên Thủy',17),(159,'Huyện Lạc Thủy',17),(164,'Thành phố Thái Nguyên',19),(165,'Thành phố Sông Công',19),(167,'Huyện Định Hóa',19),(168,'Huyện Phú Lương',19),(169,'Huyện Đồng Hỷ',19),(170,'Huyện Võ Nhai',19),(171,'Huyện Đại Từ',19),(172,'Thị xã Phổ Yên',19),(173,'Huyện Phú Bình',19),(178,'Thành phố Lạng Sơn',20),(180,'Huyện Tràng Định',20),(181,'Huyện Bình Gia',20),(182,'Huyện Văn Lãng',20),(183,'Huyện Cao Lộc',20),(184,'Huyện Văn Quan',20),(185,'Huyện Bắc Sơn',20),(186,'Huyện Hữu Lũng',20),(187,'Huyện Chi Lăng',20),(188,'Huyện Lộc Bình',20),(189,'Huyện Đình Lập',20),(193,'Thành phố Hạ Long',22),(194,'Thành phố Móng Cái',22),(195,'Thành phố Cẩm Phả',22),(196,'Thành phố Uông Bí',22),(198,'Huyện Bình Liêu',22),(199,'Huyện Tiên Yên',22),(200,'Huyện Đầm Hà',22),(201,'Huyện Hải Hà',22),(202,'Huyện Ba Chẽ',22),(203,'Huyện Vân Đồn',22),(204,'Huyện Hoành Bồ',22),(205,'Thị xã Đông Triều',22),(206,'Thị xã Quảng Yên',22),(207,'Huyện Cô Tô',22),(213,'Thành phố Bắc Giang',24),(215,'Huyện Yên Thế',24),(216,'Huyện Tân Yên',24),(217,'Huyện Lạng Giang',24),(218,'Huyện Lục Nam',24),(219,'Huyện Lục Ngạn',24),(220,'Huyện Sơn Động',24),(221,'Huyện Yên Dũng',24),(222,'Huyện Việt Yên',24),(223,'Huyện Hiệp Hòa',24),(227,'Thành phố Việt Trì',25),(228,'Thị xã Phú Thọ',25),(230,'Huyện Đoan Hùng',25),(231,'Huyện Hạ Hoà',25),(232,'Huyện Thanh Ba',25),(233,'Huyện Phù Ninh',25),(234,'Huyện Yên Lập',25),(235,'Huyện Cẩm Khê',25),(236,'Huyện Tam Nông',25),(237,'Huyện Lâm Thao',25),(238,'Huyện Thanh Sơn',25),(239,'Huyện Thanh Thuỷ',25),(240,'Huyện Tân Sơn',25),(243,'Thành phố Vĩnh Yên',26),(244,'Thị xã Phúc Yên',26),(246,'Huyện Lập Thạch',26),(247,'Huyện Tam Dương',26),(248,'Huyện Tam Đảo',26),(249,'Huyện Bình Xuyên',26),(250,'Huyện Mê Linh',1),(251,'Huyện Yên Lạc',26),(252,'Huyện Vĩnh Tường',26),(253,'Huyện Sông Lô',26),(256,'Thành phố Bắc Ninh',27),(258,'Huyện Yên Phong',27),(259,'Huyện Quế Võ',27),(260,'Huyện Tiên Du',27),(261,'Thị xã Từ Sơn',27),(262,'Huyện Thuận Thành',27),(263,'Huyện Gia Bình',27),(264,'Huyện Lương Tài',27),(268,'Quận Hà Đông',1),(269,'Thị xã Sơn Tây',1),(271,'Huyện Ba Vì',1),(272,'Huyện Phúc Thọ',1),(273,'Huyện Đan Phượng',1),(274,'Huyện Hoài Đức',1),(275,'Huyện Quốc Oai',1),(276,'Huyện Thạch Thất',1),(277,'Huyện Chương Mỹ',1),(278,'Huyện Thanh Oai',1),(279,'Huyện Thường Tín',1),(280,'Huyện Phú Xuyên',1),(281,'Huyện Ứng Hòa',1),(282,'Huyện Mỹ Đức',1),(288,'Thành phố Hải Dương',30),(290,'Thị xã Chí Linh',30),(291,'Huyện Nam Sách',30),(292,'Huyện Kinh Môn',30),(293,'Huyện Kim Thành',30),(294,'Huyện Thanh Hà',30),(295,'Huyện Cẩm Giàng',30),(296,'Huyện Bình Giang',30),(297,'Huyện Gia Lộc',30),(298,'Huyện Tứ Kỳ',30),(299,'Huyện Ninh Giang',30),(300,'Huyện Thanh Miện',30),(303,'Quận Hồng Bàng',31),(304,'Quận Ngô Quyền',31),(305,'Quận Lê Chân',31),(306,'Quận Hải An',31),(307,'Quận Kiến An',31),(308,'Quận Đồ Sơn',31),(309,'Quận Dương Kinh',31),(311,'Huyện Thuỷ Nguyên',31),(312,'Huyện An Dương',31),(313,'Huyện An Lão',31),(314,'Huyện Kiến Thuỵ',31),(315,'Huyện Tiên Lãng',31),(316,'Huyện Vĩnh Bảo',31),(317,'Huyện Cát Hải',31),(318,'Huyện Bạch Long Vĩ',31),(323,'Thành phố Hưng Yên',33),(325,'Huyện Văn Lâm',33),(326,'Huyện Văn Giang',33),(327,'Huyện Yên Mỹ',33),(328,'Huyện Mỹ Hào',33),(329,'Huyện Ân Thi',33),(330,'Huyện Khoái Châu',33),(331,'Huyện Kim Động',33),(332,'Huyện Tiên Lữ',33),(333,'Huyện Phù Cừ',33),(336,'Thành phố Thái Bình',34),(338,'Huyện Quỳnh Phụ',34),(339,'Huyện Hưng Hà',34),(340,'Huyện Đông Hưng',34),(341,'Huyện Thái Thụy',34),(342,'Huyện Tiền Hải',34),(343,'Huyện Kiến Xương',34),(344,'Huyện Vũ Thư',34),(347,'Thành phố Phủ Lý',35),(349,'Huyện Duy Tiên',35),(350,'Huyện Kim Bảng',35),(351,'Huyện Thanh Liêm',35),(352,'Huyện Bình Lục',35),(353,'Huyện Lý Nhân',35),(356,'Thành phố Nam Định',36),(358,'Huyện Mỹ Lộc',36),(359,'Huyện Vụ Bản',36),(360,'Huyện Ý Yên',36),(361,'Huyện Nghĩa Hưng',36),(362,'Huyện Nam Trực',36),(363,'Huyện Trực Ninh',36),(364,'Huyện Xuân Trường',36),(365,'Huyện Giao Thủy',36),(366,'Huyện Hải Hậu',36),(369,'Thành phố Ninh Bình',37),(370,'Thành phố Tam Điệp',37),(372,'Huyện Nho Quan',37),(373,'Huyện Gia Viễn',37),(374,'Huyện Hoa Lư',37),(375,'Huyện Yên Khánh',37),(376,'Huyện Kim Sơn',37),(377,'Huyện Yên Mô',37),(380,'Thành phố Thanh Hóa',38),(381,'Thị xã Bỉm Sơn',38),(382,'Thị xã Sầm Sơn',38),(384,'Huyện Mường Lát',38),(385,'Huyện Quan Hóa',38),(386,'Huyện Bá Thước',38),(387,'Huyện Quan Sơn',38),(388,'Huyện Lang Chánh',38),(389,'Huyện Ngọc Lặc',38),(390,'Huyện Cẩm Thủy',38),(391,'Huyện Thạch Thành',38),(392,'Huyện Hà Trung',38),(393,'Huyện Vĩnh Lộc',38),(394,'Huyện Yên Định',38),(395,'Huyện Thọ Xuân',38),(396,'Huyện Thường Xuân',38),(397,'Huyện Triệu Sơn',38),(398,'Huyện Thiệu Hóa',38),(399,'Huyện Hoằng Hóa',38),(400,'Huyện Hậu Lộc',38),(401,'Huyện Nga Sơn',38),(402,'Huyện Như Xuân',38),(403,'Huyện Như Thanh',38),(404,'Huyện Nông Cống',38),(405,'Huyện Đông Sơn',38),(406,'Huyện Quảng Xương',38),(407,'Huyện Tĩnh Gia',38),(412,'Thành phố Vinh',40),(413,'Thị xã Cửa Lò',40),(414,'Thị xã Thái Hoà',40),(415,'Huyện Quế Phong',40),(416,'Huyện Quỳ Châu',40),(417,'Huyện Kỳ Sơn',40),(418,'Huyện Tương Dương',40),(419,'Huyện Nghĩa Đàn',40),(420,'Huyện Quỳ Hợp',40),(421,'Huyện Quỳnh Lưu',40),(422,'Huyện Con Cuông',40),(423,'Huyện Tân Kỳ',40),(424,'Huyện Anh Sơn',40),(425,'Huyện Diễn Châu',40),(426,'Huyện Yên Thành',40),(427,'Huyện Đô Lương',40),(428,'Huyện Thanh Chương',40),(429,'Huyện Nghi Lộc',40),(430,'Huyện Nam Đàn',40),(431,'Huyện Hưng Nguyên',40),(432,'Thị xã Hoàng Mai',40),(436,'Thành phố Hà Tĩnh',42),(437,'Thị xã Hồng Lĩnh',42),(439,'Huyện Hương Sơn',42),(440,'Huyện Đức Thọ',42),(441,'Huyện Vũ Quang',42),(442,'Huyện Nghi Xuân',42),(443,'Huyện Can Lộc',42),(444,'Huyện Hương Khê',42),(445,'Huyện Thạch Hà',42),(446,'Huyện Cẩm Xuyên',42),(447,'Huyện Kỳ Anh',42),(448,'Huyện Lộc Hà',42),(449,'Thị xã Kỳ Anh',42),(450,'Thành Phố Đồng Hới',44),(452,'Huyện Minh Hóa',44),(453,'Huyện Tuyên Hóa',44),(454,'Huyện Quảng Trạch',44),(455,'Huyện Bố Trạch',44),(456,'Huyện Quảng Ninh',44),(457,'Huyện Lệ Thủy',44),(458,'Thị xã Ba Đồn',44),(461,'Thành phố Đông Hà',45),(462,'Thị xã Quảng Trị',45),(464,'Huyện Vĩnh Linh',45),(465,'Huyện Hướng Hóa',45),(466,'Huyện Gio Linh',45),(467,'Huyện Đa Krông',45),(468,'Huyện Cam Lộ',45),(469,'Huyện Triệu Phong',45),(470,'Huyện Hải Lăng',45),(471,'Huyện Cồn Cỏ',45),(474,'Thành phố Huế',46),(476,'Huyện Phong Điền',46),(477,'Huyện Quảng Điền',46),(478,'Huyện Phú Vang',46),(479,'Thị xã Hương Thủy',46),(480,'Thị xã Hương Trà',46),(481,'Huyện A Lưới',46),(482,'Huyện Phú Lộc',46),(483,'Huyện Nam Đông',46),(490,'Quận Liên Chiểu',48),(491,'Quận Thanh Khê',48),(492,'Quận Hải Châu',48),(493,'Quận Sơn Trà',48),(494,'Quận Ngũ Hành Sơn',48),(495,'Quận Cẩm Lệ',48),(497,'Huyện Hòa Vang',48),(498,'Huyện Hoàng Sa',48),(502,'Thành phố Tam Kỳ',49),(503,'Thành phố Hội An',49),(504,'Huyện Tây Giang',49),(505,'Huyện Đông Giang',49),(506,'Huyện Đại Lộc',49),(507,'Thị xã Điện Bàn',49),(508,'Huyện Duy Xuyên',49),(509,'Huyện Quế Sơn',49),(510,'Huyện Nam Giang',49),(511,'Huyện Phước Sơn',49),(512,'Huyện Hiệp Đức',49),(513,'Huyện Thăng Bình',49),(514,'Huyện Tiên Phước',49),(515,'Huyện Bắc Trà My',49),(516,'Huyện Nam Trà My',49),(517,'Huyện Núi Thành',49),(518,'Huyện Phú Ninh',49),(519,'Huyện Nông Sơn',49),(522,'Thành phố Quảng Ngãi',51),(524,'Huyện Bình Sơn',51),(525,'Huyện Trà Bồng',51),(526,'Huyện Tây Trà',51),(527,'Huyện Sơn Tịnh',51),(528,'Huyện Tư Nghĩa',51),(529,'Huyện Sơn Hà',51),(530,'Huyện Sơn Tây',51),(531,'Huyện Minh Long',51),(532,'Huyện Nghĩa Hành',51),(533,'Huyện Mộ Đức',51),(534,'Huyện Đức Phổ',51),(535,'Huyện Ba Tơ',51),(536,'Huyện Lý Sơn',51),(540,'Thành phố Qui Nhơn',52),(542,'Huyện An Lão',52),(543,'Huyện Hoài Nhơn',52),(544,'Huyện Hoài Ân',52),(545,'Huyện Phù Mỹ',52),(546,'Huyện Vĩnh Thạnh',52),(547,'Huyện Tây Sơn',52),(548,'Huyện Phù Cát',52),(549,'Thị xã An Nhơn',52),(550,'Huyện Tuy Phước',52),(551,'Huyện Vân Canh',52),(555,'Thành phố Tuy Hoà',54),(557,'Thị xã Sông Cầu',54),(558,'Huyện Đồng Xuân',54),(559,'Huyện Tuy An',54),(560,'Huyện Sơn Hòa',54),(561,'Huyện Sông Hinh',54),(562,'Huyện Tây Hoà',54),(563,'Huyện Phú Hoà',54),(564,'Huyện Đông Hòa',54),(568,'Thành phố Nha Trang',56),(569,'Thành phố Cam Ranh',56),(570,'Huyện Cam Lâm',56),(571,'Huyện Vạn Ninh',56),(572,'Thị xã Ninh Hòa',56),(573,'Huyện Khánh Vĩnh',56),(574,'Huyện Diên Khánh',56),(575,'Huyện Khánh Sơn',56),(576,'Huyện Trường Sa',56),(582,'Thành phố Phan Rang-Tháp Chàm',58),(584,'Huyện Bác Ái',58),(585,'Huyện Ninh Sơn',58),(586,'Huyện Ninh Hải',58),(587,'Huyện Ninh Phước',58),(588,'Huyện Thuận Bắc',58),(589,'Huyện Thuận Nam',58),(593,'Thành phố Phan Thiết',60),(594,'Thị xã La Gi',60),(595,'Huyện Tuy Phong',60),(596,'Huyện Bắc Bình',60),(597,'Huyện Hàm Thuận Bắc',60),(598,'Huyện Hàm Thuận Nam',60),(599,'Huyện Tánh Linh',60),(600,'Huyện Đức Linh',60),(601,'Huyện Hàm Tân',60),(602,'Huyện Phú Quí',60),(608,'Thành phố Kon Tum',62),(610,'Huyện Đắk Glei',62),(611,'Huyện Ngọc Hồi',62),(612,'Huyện Đắk Tô',62),(613,'Huyện Kon Plông',62),(614,'Huyện Kon Rẫy',62),(615,'Huyện Đắk Hà',62),(616,'Huyện Sa Thầy',62),(617,'Huyện Tu Mơ Rông',62),(618,'Huyện Ia H\' Drai',62),(622,'Thành phố Pleiku',64),(623,'Thị xã An Khê',64),(624,'Thị xã Ayun Pa',64),(625,'Huyện KBang',64),(626,'Huyện Đăk Đoa',64),(627,'Huyện Chư Păh',64),(628,'Huyện Ia Grai',64),(629,'Huyện Mang Yang',64),(630,'Huyện Kông Chro',64),(631,'Huyện Đức Cơ',64),(632,'Huyện Chư Prông',64),(633,'Huyện Chư Sê',64),(634,'Huyện Đăk Pơ',64),(635,'Huyện Ia Pa',64),(637,'Huyện Krông Pa',64),(638,'Huyện Phú Thiện',64),(639,'Huyện Chư Pưh',64),(643,'Thành phố Buôn Ma Thuột',66),(644,'Thị Xã Buôn Hồ',66),(645,'Huyện Ea H\'leo',66),(646,'Huyện Ea Súp',66),(647,'Huyện Buôn Đôn',66),(648,'Huyện Cư M\'gar',66),(649,'Huyện Krông Búk',66),(650,'Huyện Krông Năng',66),(651,'Huyện Ea Kar',66),(652,'Huyện M\'Đrắk',66),(653,'Huyện Krông Bông',66),(654,'Huyện Krông Pắc',66),(655,'Huyện Krông A Na',66),(656,'Huyện Lắk',66),(657,'Huyện Cư Kuin',66),(660,'Thị xã Gia Nghĩa',67),(661,'Huyện Đăk Glong',67),(662,'Huyện Cư Jút',67),(663,'Huyện Đắk Mil',67),(664,'Huyện Krông Nô',67),(665,'Huyện Đắk Song',67),(666,'Huyện Đắk R\'Lấp',67),(667,'Huyện Tuy Đức',67),(672,'Thành phố Đà Lạt',68),(673,'Thành phố Bảo Lộc',68),(674,'Huyện Đam Rông',68),(675,'Huyện Lạc Dương',68),(676,'Huyện Lâm Hà',68),(677,'Huyện Đơn Dương',68),(678,'Huyện Đức Trọng',68),(679,'Huyện Di Linh',68),(680,'Huyện Bảo Lâm',68),(681,'Huyện Đạ Huoai',68),(682,'Huyện Đạ Tẻh',68),(683,'Huyện Cát Tiên',68),(688,'Thị xã Phước Long',70),(689,'Thị xã Đồng Xoài',70),(690,'Thị xã Bình Long',70),(691,'Huyện Bù Gia Mập',70),(692,'Huyện Lộc Ninh',70),(693,'Huyện Bù Đốp',70),(694,'Huyện Hớn Quản',70),(695,'Huyện Đồng Phú',70),(696,'Huyện Bù Đăng',70),(697,'Huyện Chơn Thành',70),(698,'Huyện Phú Riềng',70),(703,'Thành phố Tây Ninh',72),(705,'Huyện Tân Biên',72),(706,'Huyện Tân Châu',72),(707,'Huyện Dương Minh Châu',72),(708,'Huyện Châu Thành',72),(709,'Huyện Hòa Thành',72),(710,'Huyện Gò Dầu',72),(711,'Huyện Bến Cầu',72),(712,'Huyện Trảng Bàng',72),(718,'Thành phố Thủ Dầu Một',74),(719,'Huyện Bàu Bàng',74),(720,'Huyện Dầu Tiếng',74),(721,'Thị xã Bến Cát',74),(722,'Huyện Phú Giáo',74),(723,'Thị xã Tân Uyên',74),(724,'Thị xã Dĩ An',74),(725,'Thị xã Thuận An',74),(726,'Huyện Bắc Tân Uyên',74),(731,'Thành phố Biên Hòa',75),(732,'Thị xã Long Khánh',75),(734,'Huyện Tân Phú',75),(735,'Huyện Vĩnh Cửu',75),(736,'Huyện Định Quán',75),(737,'Huyện Trảng Bom',75),(738,'Huyện Thống Nhất',75),(739,'Huyện Cẩm Mỹ',75),(740,'Huyện Long Thành',75),(741,'Huyện Xuân Lộc',75),(742,'Huyện Nhơn Trạch',75),(747,'Thành phố Vũng Tàu',77),(748,'Thành phố Bà Rịa',77),(750,'Huyện Châu Đức',77),(751,'Huyện Xuyên Mộc',77),(752,'Huyện Long Điền',77),(753,'Huyện Đất Đỏ',77),(754,'Huyện Tân Thành',77),(755,'Huyện Côn Đảo',77),(760,'Quận 1',79),(761,'Quận 12',79),(762,'Quận Thủ Đức',79),(763,'Quận 9',79),(764,'Quận Gò Vấp',79),(765,'Quận Bình Thạnh',79),(766,'Quận Tân Bình',79),(767,'Quận Tân Phú',79),(768,'Quận Phú Nhuận',79),(769,'Quận 2',79),(770,'Quận 3',79),(771,'Quận 10',79),(772,'Quận 11',79),(773,'Quận 4',79),(774,'Quận 5',79),(775,'Quận 6',79),(776,'Quận 8',79),(777,'Quận Bình Tân',79),(778,'Quận 7',79),(783,'Huyện Củ Chi',79),(784,'Huyện Hóc Môn',79),(785,'Huyện Bình Chánh',79),(786,'Huyện Nhà Bè',79),(787,'Huyện Cần Giờ',79),(794,'Thành phố Tân An',80),(795,'Thị xã Kiến Tường',80),(796,'Huyện Tân Hưng',80),(797,'Huyện Vĩnh Hưng',80),(798,'Huyện Mộc Hóa',80),(799,'Huyện Tân Thạnh',80),(800,'Huyện Thạnh Hóa',80),(801,'Huyện Đức Huệ',80),(802,'Huyện Đức Hòa',80),(803,'Huyện Bến Lức',80),(804,'Huyện Thủ Thừa',80),(805,'Huyện Tân Trụ',80),(806,'Huyện Cần Đước',80),(807,'Huyện Cần Giuộc',80),(808,'Huyện Châu Thành',80),(815,'Thành phố Mỹ Tho',82),(816,'Thị xã Gò Công',82),(817,'Thị xã Cai Lậy',82),(818,'Huyện Tân Phước',82),(819,'Huyện Cái Bè',82),(820,'Huyện Cai Lậy',82),(821,'Huyện Châu Thành',82),(822,'Huyện Chợ Gạo',82),(823,'Huyện Gò Công Tây',82),(824,'Huyện Gò Công Đông',82),(825,'Huyện Tân Phú Đông',82),(829,'Thành phố Bến Tre',83),(831,'Huyện Châu Thành',83),(832,'Huyện Chợ Lách',83),(833,'Huyện Mỏ Cày Nam',83),(834,'Huyện Giồng Trôm',83),(835,'Huyện Bình Đại',83),(836,'Huyện Ba Tri',83),(837,'Huyện Thạnh Phú',83),(838,'Huyện Mỏ Cày Bắc',83),(842,'Thành phố Trà Vinh',84),(844,'Huyện Càng Long',84),(845,'Huyện Cầu Kè',84),(846,'Huyện Tiểu Cần',84),(847,'Huyện Châu Thành',84),(848,'Huyện Cầu Ngang',84),(849,'Huyện Trà Cú',84),(850,'Huyện Duyên Hải',84),(851,'Thị xã Duyên Hải',84),(855,'Thành phố Vĩnh Long',86),(857,'Huyện Long Hồ',86),(858,'Huyện Mang Thít',86),(859,'Huyện  Vũng Liêm',86),(860,'Huyện Tam Bình',86),(861,'Thị xã Bình Minh',86),(862,'Huyện Trà Ôn',86),(863,'Huyện Bình Tân',86),(866,'Thành phố Cao Lãnh',87),(867,'Thành phố Sa Đéc',87),(868,'Thị xã Hồng Ngự',87),(869,'Huyện Tân Hồng',87),(870,'Huyện Hồng Ngự',87),(871,'Huyện Tam Nông',87),(872,'Huyện Tháp Mười',87),(873,'Huyện Cao Lãnh',87),(874,'Huyện Thanh Bình',87),(875,'Huyện Lấp Vò',87),(876,'Huyện Lai Vung',87),(877,'Huyện Châu Thành',87),(883,'Thành phố Long Xuyên',89),(884,'Thành phố Châu Đốc',89),(886,'Huyện An Phú',89),(887,'Thị xã Tân Châu',89),(888,'Huyện Phú Tân',89),(889,'Huyện Châu Phú',89),(890,'Huyện Tịnh Biên',89),(891,'Huyện Tri Tôn',89),(892,'Huyện Châu Thành',89),(893,'Huyện Chợ Mới',89),(894,'Huyện Thoại Sơn',89),(899,'Thành phố Rạch Giá',91),(900,'Thị xã Hà Tiên',91),(902,'Huyện Kiên Lương',91),(903,'Huyện Hòn Đất',91),(904,'Huyện Tân Hiệp',91),(905,'Huyện Châu Thành',91),(906,'Huyện Giồng Riềng',91),(907,'Huyện Gò Quao',91),(908,'Huyện An Biên',91),(909,'Huyện An Minh',91),(910,'Huyện Vĩnh Thuận',91),(911,'Huyện Phú Quốc',91),(912,'Huyện Kiên Hải',91),(913,'Huyện U Minh Thượng',91),(914,'Huyện Giang Thành',91),(916,'Quận Ninh Kiều',92),(917,'Quận Ô Môn',92),(918,'Quận Bình Thuỷ',92),(919,'Quận Cái Răng',92),(923,'Quận Thốt Nốt',92),(924,'Huyện Vĩnh Thạnh',92),(925,'Huyện Cờ Đỏ',92),(926,'Huyện Phong Điền',92),(927,'Huyện Thới Lai',92),(930,'Thành phố Vị Thanh',93),(931,'Thị xã Ngã Bảy',93),(932,'Huyện Châu Thành A',93),(933,'Huyện Châu Thành',93),(934,'Huyện Phụng Hiệp',93),(935,'Huyện Vị Thuỷ',93),(936,'Huyện Long Mỹ',93),(937,'Thị xã Long Mỹ',93),(941,'Thành phố Sóc Trăng',94),(942,'Huyện Châu Thành',94),(943,'Huyện Kế Sách',94),(944,'Huyện Mỹ Tú',94),(945,'Huyện Cù Lao Dung',94),(946,'Huyện Long Phú',94),(947,'Huyện Mỹ Xuyên',94),(948,'Thị xã Ngã Năm',94),(949,'Huyện Thạnh Trị',94),(950,'Thị xã Vĩnh Châu',94),(951,'Huyện Trần Đề',94),(954,'Thành phố Bạc Liêu',95),(956,'Huyện Hồng Dân',95),(957,'Huyện Phước Long',95),(958,'Huyện Vĩnh Lợi',95),(959,'Thị xã Giá Rai',95),(960,'Huyện Đông Hải',95),(961,'Huyện Hoà Bình',95),(964,'Thành phố Cà Mau',96),(966,'Huyện U Minh',96),(967,'Huyện Thới Bình',96),(968,'Huyện Trần Văn Thời',96),(969,'Huyện Cái Nước',96),(970,'Huyện Đầm Dơi',96),(971,'Huyện Năm Căn',96),(972,'Huyện Phú Tân',96),(973,'Huyện Ngọc Hiển',96);
/*!40000 ALTER TABLE `location_district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `method` tinyint DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `visibility` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UniquePathAndMethod` (`path`,`method`),
  CONSTRAINT `permission_chk_1` CHECK ((`method` between 0 and 3))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_roles`
--

DROP TABLE IF EXISTS `permission_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission_roles` (
  `permission_id` int NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  KEY `FKn79fuf4ud4dfml7rsoo8s6oin` (`permission_id`),
  CONSTRAINT `FKn79fuf4ud4dfml7rsoo8s6oin` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_roles`
--

LOCK TABLES `permission_roles` WRITE;
/*!40000 ALTER TABLE `permission_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expired` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1gnoedr4u8s5p7ccdkftq38bh` (`account_id`),
  CONSTRAINT `FKiox3wo9jixvp9boxfheq7l99w` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (1,'2024-05-19 00:36:24.495000','1a2af027-4668-4c91-9b52-38a081a24b96',1);
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resume`
--

DROP TABLE IF EXISTS `resume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resume` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `resume_status_id` bigint DEFAULT NULL,
  `user_account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_i8xa6l6qkrc3evb68kbqm5la5` (`resume_status_id`),
  UNIQUE KEY `UK_gndmy1w97qh5s29toggrct1sw` (`user_account_id`),
  CONSTRAINT `FKai3dbfyx2rk23fusd1b3cegj3` FOREIGN KEY (`resume_status_id`) REFERENCES `resume_status` (`id`),
  CONSTRAINT `FKe5o7iffqgy5rpl3w2nswscyeg` FOREIGN KEY (`user_account_id`) REFERENCES `user` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resume`
--

LOCK TABLES `resume` WRITE;
/*!40000 ALTER TABLE `resume` DISABLE KEYS */;
INSERT INTO `resume` VALUES (1,'2024-04-19 13:15:14.564000','2024-04-19 13:57:12.092000',2,2);
/*!40000 ALTER TABLE `resume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resume_status`
--

DROP TABLE IF EXISTS `resume_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resume_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_search` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resume_status`
--

LOCK TABLES `resume_status` WRITE;
/*!40000 ALTER TABLE `resume_status` DISABLE KEYS */;
INSERT INTO `resume_status` VALUES (1,_binary '\0'),(2,_binary '');
/*!40000 ALTER TABLE `resume_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review_resume`
--

DROP TABLE IF EXISTS `review_resume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review_resume` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `employee_status` tinyint DEFAULT NULL,
  `job_id` bigint DEFAULT NULL,
  `resume_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UniqueResumeIdAndJobId` (`resume_id`,`job_id`),
  UNIQUE KEY `UK_mgb8f7gcvb6uvv035v8es58vq` (`job_id`),
  UNIQUE KEY `UK_2ukrm6tpnjmr48txnih521489` (`resume_id`),
  CONSTRAINT `FK25lvla9xlgpvnuloud8pwvly1` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
  CONSTRAINT `FKm6hlltyr11oakwhtfahios715` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`),
  CONSTRAINT `review_resume_chk_1` CHECK ((`employee_status` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review_resume`
--

LOCK TABLES `review_resume` WRITE;
/*!40000 ALTER TABLE `review_resume` DISABLE KEYS */;
INSERT INTO `review_resume` VALUES (1,'2024-04-19 14:21:56.529000','2024-04-19 14:21:56.529000',1,1,1);
/*!40000 ALTER TABLE `review_resume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(3,'COMPANY'),(2,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5ljf2l2h4odhtxrsuohlro4ir` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill`
--

LOCK TABLES `skill` WRITE;
/*!40000 ALTER TABLE `skill` DISABLE KEYS */;
INSERT INTO `skill` VALUES (1,'Java'),(3,'Spring boot'),(2,'SQL');
/*!40000 ALTER TABLE `skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `created_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`account_id`),
  CONSTRAINT `FKc3b4xfbq6rbkkrddsdum8t5f0` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('2024-04-19 00:32:04.389000','2024-04-19 00:32:04.389000',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_attachedcv`
--

DROP TABLE IF EXISTS `user_attachedcv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_attachedcv` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `attached_url` varchar(255) DEFAULT NULL,
  `is_attached` bit(1) NOT NULL,
  `resume_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ojx1goseg72siaihl0nwqk9g5` (`resume_id`),
  CONSTRAINT `FK455nq14q99y4wsi1wpmjuco6g` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_attachedcv`
--

LOCK TABLES `user_attachedcv` WRITE;
/*!40000 ALTER TABLE `user_attachedcv` DISABLE KEYS */;
INSERT INTO `user_attachedcv` VALUES (3,'3d6a2391-4416-4546-a21a-0e35989bdabb.png',_binary '',1);
/*!40000 ALTER TABLE `user_attachedcv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_contact`
--

DROP TABLE IF EXISTS `user_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_contact` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `birthdate` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` tinyint DEFAULT NULL,
  `marital_status` bit(1) DEFAULT NULL,
  `nationality` bit(1) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `location_id` bigint DEFAULT NULL,
  `user_account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2t0u68lehn24dwaapx9agy5w2` (`location_id`),
  UNIQUE KEY `UK_fmqfs1do6ugdy0uod0qimq38q` (`user_account_id`),
  CONSTRAINT `FK9krr2f96koy6g1o9860p50jsq` FOREIGN KEY (`user_account_id`) REFERENCES `user` (`account_id`),
  CONSTRAINT `FKlh8bamsv1l2x1yvax79bttm6k` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `user_contact_chk_1` CHECK ((`gender` between 0 and 1))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_contact`
--

LOCK TABLES `user_contact` WRITE;
/*!40000 ALTER TABLE `user_contact` DISABLE KEYS */;
INSERT INTO `user_contact` VALUES (1,'1991-01-01','user1@syhien.com',0,_binary '\0',_binary '\0','0123123123',1,2);
/*!40000 ALTER TABLE `user_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_education`
--

DROP TABLE IF EXISTS `user_education`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_education` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `highest_degree` tinyint DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `school_name` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `resume_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UniqueSchoolNameIdAndUserId` (`school_name`,`resume_id`),
  KEY `FKixihudltwybcj2opmje1jny7x` (`resume_id`),
  CONSTRAINT `FKixihudltwybcj2opmje1jny7x` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`),
  CONSTRAINT `user_education_chk_1` CHECK ((`highest_degree` between 0 and 6))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_education`
--

LOCK TABLES `user_education` WRITE;
/*!40000 ALTER TABLE `user_education` DISABLE KEYS */;
INSERT INTO `user_education` VALUES (1,'description string','2024-01-01',0,'major string','schoolName string','2024-01-01',1);
/*!40000 ALTER TABLE `user_education` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_experience`
--

DROP TABLE IF EXISTS `user_experience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_experience` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `company` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `job_title` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `resume_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UniqueCompanyIdAndUserId` (`company`,`resume_id`),
  KEY `FK9mpd9290wk5197q2f8vtrar0p` (`resume_id`),
  CONSTRAINT `FK9mpd9290wk5197q2f8vtrar0p` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_experience`
--

LOCK TABLES `user_experience` WRITE;
/*!40000 ALTER TABLE `user_experience` DISABLE KEYS */;
INSERT INTO `user_experience` VALUES (1,'company string','description string','2024-01-01','jobTitle string','2024-01-01',1);
/*!40000 ALTER TABLE `user_experience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_language`
--

DROP TABLE IF EXISTS `user_language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_language` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `level` int DEFAULT NULL,
  `language_id` int DEFAULT NULL,
  `resume_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UniqueLanguageIdAndUserId` (`language_id`,`resume_id`),
  KEY `FK604x3unwekntcxlaw5pgfvo91` (`resume_id`),
  CONSTRAINT `FK5dhe6kqh7ol7f48ker70e368r` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `FK604x3unwekntcxlaw5pgfvo91` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_language`
--

LOCK TABLES `user_language` WRITE;
/*!40000 ALTER TABLE `user_language` DISABLE KEYS */;
INSERT INTO `user_language` VALUES (1,3,1,1);
/*!40000 ALTER TABLE `user_language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar_url` varchar(255) DEFAULT NULL,
  `current_salary` double DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `highest_degree` tinyint DEFAULT NULL,
  `job_level` tinyint DEFAULT NULL,
  `job_title` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `years_experience` int DEFAULT NULL,
  `job_function_id` int DEFAULT NULL,
  `resume_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_93uxmh0dert9qwq52c1mxpbje` (`resume_id`),
  KEY `FK10qihqdf50tv5lolpl1y4reik` (`job_function_id`),
  CONSTRAINT `FK10qihqdf50tv5lolpl1y4reik` FOREIGN KEY (`job_function_id`) REFERENCES `job_function` (`id`),
  CONSTRAINT `FK7rt08du0eyqcq5fnpaqa166bv` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`),
  CONSTRAINT `user_profile_chk_1` CHECK ((`highest_degree` between 0 and 6)),
  CONSTRAINT `user_profile_chk_2` CHECK ((`job_level` between 0 and 4))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (1,NULL,5000000,'Hien','Nguyen Sy Hien',0,0,'string','Nguyen Sy',2,1,1);
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile_industries`
--

DROP TABLE IF EXISTS `user_profile_industries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile_industries` (
  `user_profile_id` bigint NOT NULL,
  `industries_id` bigint NOT NULL,
  KEY `FKt73uaqb6gruvsm9x01023oe0b` (`industries_id`),
  KEY `FKl3xn9tplbkisft1fv1k8e41b0` (`user_profile_id`),
  CONSTRAINT `FKl3xn9tplbkisft1fv1k8e41b0` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`),
  CONSTRAINT `FKt73uaqb6gruvsm9x01023oe0b` FOREIGN KEY (`industries_id`) REFERENCES `industry` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile_industries`
--

LOCK TABLES `user_profile_industries` WRITE;
/*!40000 ALTER TABLE `user_profile_industries` DISABLE KEYS */;
INSERT INTO `user_profile_industries` VALUES (1,1);
/*!40000 ALTER TABLE `user_profile_industries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_skill`
--

DROP TABLE IF EXISTS `user_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_skill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `level` int DEFAULT NULL,
  `resume_id` bigint DEFAULT NULL,
  `skill_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UniqueSkillIdAndUserId` (`skill_id`,`resume_id`),
  KEY `FK427m87guxydv4s8ilbyfif0jp` (`resume_id`),
  CONSTRAINT `FK427m87guxydv4s8ilbyfif0jp` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`),
  CONSTRAINT `FKj53flyds4vknyh8llw5d7jdop` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_skill`
--

LOCK TABLES `user_skill` WRITE;
/*!40000 ALTER TABLE `user_skill` DISABLE KEYS */;
INSERT INTO `user_skill` VALUES (1,3,1,2);
/*!40000 ALTER TABLE `user_skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_summary`
--

DROP TABLE IF EXISTS `user_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_summary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `summary` varchar(255) DEFAULT NULL,
  `resume_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4mlnet5w0gojx9okpyjva8f3` (`resume_id`),
  CONSTRAINT `FKk8sqqrvrb6gdakdu9auack5fp` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_summary`
--

LOCK TABLES `user_summary` WRITE;
/*!40000 ALTER TABLE `user_summary` DISABLE KEYS */;
INSERT INTO `user_summary` VALUES (1,'summary',1);
/*!40000 ALTER TABLE `user_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `working_preference`
--

DROP TABLE IF EXISTS `working_preference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `working_preference` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `benefits` varchar(255) DEFAULT NULL,
  `is_relocate` bit(1) DEFAULT NULL,
  `job_level` tinyint DEFAULT NULL,
  `job_title` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `job_function_id` int DEFAULT NULL,
  `language_id` int DEFAULT NULL,
  `location_city_id` int DEFAULT NULL,
  `resume_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dqym7xstes1u6xl1vqi4ie1o6` (`resume_id`),
  KEY `FKdse43tjun1icsawjn9bpaegpq` (`job_function_id`),
  KEY `FKs6ry50vuid7c33tckctcpu446` (`language_id`),
  KEY `FKj2fgmnng2dcr2epo5mv5hbyjk` (`location_city_id`),
  CONSTRAINT `FKdse43tjun1icsawjn9bpaegpq` FOREIGN KEY (`job_function_id`) REFERENCES `job_function` (`id`),
  CONSTRAINT `FKj2fgmnng2dcr2epo5mv5hbyjk` FOREIGN KEY (`location_city_id`) REFERENCES `location_city` (`id`),
  CONSTRAINT `FKs6ry50vuid7c33tckctcpu446` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `FKt6h5889n0dnvnndhebtxf331a` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`),
  CONSTRAINT `working_preference_chk_1` CHECK ((`job_level` between 0 and 4))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `working_preference`
--

LOCK TABLES `working_preference` WRITE;
/*!40000 ALTER TABLE `working_preference` DISABLE KEYS */;
INSERT INTO `working_preference` VALUES (1,'string',_binary '',0,'string',5000000,1,1,1,1);
/*!40000 ALTER TABLE `working_preference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `working_preference_industries`
--

DROP TABLE IF EXISTS `working_preference_industries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `working_preference_industries` (
  `working_preference_id` bigint NOT NULL,
  `industries_id` bigint NOT NULL,
  KEY `FKsylv7r0hc11s7da06kjj4r869` (`industries_id`),
  KEY `FK567ewrsoprh30a59ojgowljlx` (`working_preference_id`),
  CONSTRAINT `FK567ewrsoprh30a59ojgowljlx` FOREIGN KEY (`working_preference_id`) REFERENCES `working_preference` (`id`),
  CONSTRAINT `FKsylv7r0hc11s7da06kjj4r869` FOREIGN KEY (`industries_id`) REFERENCES `industry` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `working_preference_industries`
--

LOCK TABLES `working_preference_industries` WRITE;
/*!40000 ALTER TABLE `working_preference_industries` DISABLE KEYS */;
INSERT INTO `working_preference_industries` VALUES (1,1);
/*!40000 ALTER TABLE `working_preference_industries` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-19 14:51:52
