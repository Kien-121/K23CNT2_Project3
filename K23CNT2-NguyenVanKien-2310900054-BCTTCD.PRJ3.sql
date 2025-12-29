-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: qlbdgd
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `added_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `carts_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Giỏ hàng của người dùng';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (3,3,1,'2025-12-06 12:33:48');
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Danh mục sản phẩm (ví dụ: Tivi, Máy lạnh, Bếp điện...)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Thiết Bị Nhà Bếp','Các loại bếp, lò nướng, máy rửa chén.'),(2,'Điện Lạnh','Máy lạnh, tủ lạnh, máy giặt.'),(3,'Thiết Bị Gia Dụng Khác','Máy hút bụi, bàn ủi, quạt điện.');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `product_id` int NOT NULL,
  `stock_quantity` int NOT NULL DEFAULT '0',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Quản lý số lượng tồn kho và cảnh báo hết hàng';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,30,'2025-12-14 13:31:02'),(2,15,'2025-12-14 14:30:55'),(3,29,'2025-12-15 04:20:22'),(4,100,'2025-12-06 12:33:28'),(5,20,'2025-12-15 04:17:44');
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news` (
  `news_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` longtext COLLATE utf8mb4_unicode_ci,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`news_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Lưu trữ tin tức, bài viết blog';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'Kinh nghiệm chọn mua tủ lạnh tiết kiệm điện cho gia đình','Tủ lạnh Inverter không chỉ giúp tiết kiệm điện năng mà còn vận hành êm ái, bền bỉ. Khi chọn mua, bạn nên lưu ý đến dung tích phù hợp với số lượng thành viên...','https://cdnv2.tgdd.vn/mwg-static/dmx/News/Thumb/1410050/tu-lanh-panasonic-co-ngan-dong-mem-thumb%20%281%29639013261800532905.jpg','Mẹo vặt','2025-12-15 04:30:22'),(2,'Cách vệ sinh máy giặt cửa ngang đơn giản tại nhà','Máy giặt sau một thời gian sử dụng sẽ tích tụ nhiều cặn bẩn và vi khuẩn. Bạn có thể sử dụng giấm ăn hoặc baking soda để vệ sinh lồng giặt định kỳ hàng tháng...','https://cdn.tgdd.vn/Files/2014/04/16/542288/cach-ve-sinh-may-giat-cua-truoc-5.jpg','Hướng dẫn sử dụng','2025-12-15 04:30:22'),(3,'Sai lầm khiến nồi chiên không dầu nhanh hỏng','Nồi chiên không dầu giúp giảm đến 80% lượng dầu mỡ. Hãy thử ngay món gà nướng mật ong, khoai tây chiên giòn rụm hay thịt ba chỉ quay giòn bì...','https://cafefcdn.com/203337114487263232/2024/12/8/6-sai-lam-thuong-mac-phai-khi-dung-noi-chien-khong-dau-5-1733655066697-17336550675171113388484-1733664749435-1733664749829461303779.jpg','Công thức nấu ăn','2025-12-15 04:30:22'),(4,'Săn Sale Hè Rực Rỡ - Giảm tới 50% toàn bộ gian hàng','Cơ hội mua sắm thiết bị gia dụng giá rẻ chưa từng có. Quạt điều hòa, máy xay sinh tố, nồi cơm điện... đồng loạt giảm giá sâu trong tuần lễ vàng này.','https://hc.com.vn/i/ecommerce/media/ck3100150-thang-4-don-he-ruc-ro-san-sale-het-co-1.jpg','Khuyến mãi','2025-12-15 04:30:22');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `order_item_id` bigint NOT NULL AUTO_INCREMENT,
  `price_at_order` decimal(38,2) NOT NULL,
  `quantity` int DEFAULT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`order_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,16500000.00,1,3,2),(2,16500000.00,1,4,2),(3,16500000.00,1,5,2),(4,16500000.00,1,6,2),(5,16500000.00,1,7,2),(6,16500000.00,1,8,2),(7,16500000.00,1,9,2),(8,16500000.00,1,10,2),(9,4999000.00,1,11,1),(10,4999000.00,1,12,1),(11,4999000.00,1,13,1),(12,16500000.00,1,14,2),(13,16500000.00,1,15,2),(14,9900000.00,1,16,3);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderitems`
--

DROP TABLE IF EXISTS `orderitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderitems` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price_at_order` decimal(10,2) NOT NULL COMMENT 'Giá sản phẩm tại thời điểm đặt hàng',
  PRIMARY KEY (`order_item_id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `orderitems_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE,
  CONSTRAINT `orderitems_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Chi tiết sản phẩm trong từng đơn hàng';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitems`
--

LOCK TABLES `orderitems` WRITE;
/*!40000 ALTER TABLE `orderitems` DISABLE KEYS */;
INSERT INTO `orderitems` VALUES (1,1,1,1,4999000.00),(2,1,4,1,1000000.00),(3,2,2,1,16500000.00);
/*!40000 ALTER TABLE `orderitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `total_amount` decimal(38,2) NOT NULL,
  `shipping_address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `payment_method` enum('COD','E_WALLET','BANK_TRANSFER') COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_status` enum('PENDING','PROCESSING','SHIPPED','COMPLETED','CANCELLED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING' COMMENT 'Trạng thái đơn hàng',
  `promotion_id` int DEFAULT NULL COMMENT 'Mã giảm giá được áp dụng',
  `receiver_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receiver_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  KEY `promotion_id` (`promotion_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`promotion_id`) REFERENCES `promotions` (`promotion_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Thông tin tổng quát của đơn hàng';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,3,'2025-12-06 12:33:36',5999000.00,'Số 1, Đường Quang Trung, Quận 1','COD','COMPLETED',NULL,NULL,NULL),(2,3,'2025-12-06 12:33:36',17500000.00,'Số 1, Đường Quang Trung, Quận 1','BANK_TRANSFER','PROCESSING',1,NULL,NULL),(3,5,'2025-12-10 03:47:35',16500000.00,'dsgfvm','COD','PENDING',NULL,'Kien','012314132432'),(4,5,'2025-12-10 03:47:39',16500000.00,'dsgfvm','COD','PENDING',NULL,'Kien','012314132432'),(5,5,'2025-12-10 03:47:39',16500000.00,'dsgfvm','COD','CANCELLED',NULL,'Kien','012314132432'),(6,5,'2025-12-10 03:47:39',16500000.00,'dsgfvm','COD','PENDING',NULL,'Kien','012314132432'),(7,5,'2025-12-10 03:47:40',16500000.00,'dsgfvm','COD','PROCESSING',NULL,'Kien','012314132432'),(8,5,'2025-12-10 03:47:40',16500000.00,'dsgfvm','COD','PENDING',NULL,'Kien','012314132432'),(9,5,'2025-12-10 03:47:40',16500000.00,'dsgfvm','COD','CANCELLED',NULL,'Kien','012314132432'),(10,5,'2025-12-10 03:47:40',16500000.00,'dsgfvm','COD','SHIPPED',NULL,'Kien','012314132432'),(11,6,'2025-12-10 04:48:16',4999000.00,'1','E_WALLET','CANCELLED',NULL,'Kien','012314132432'),(12,6,'2025-12-10 04:48:19',4999000.00,'1','E_WALLET','PENDING',NULL,'Kien','012314132432'),(13,6,'2025-12-10 04:48:21',4999000.00,'1','BANK_TRANSFER','PENDING',NULL,'Kien','012314132432'),(14,5,'2025-12-14 08:31:06',16500000.00,'ha noi','COD','PROCESSING',NULL,'Kien','012314132432'),(15,5,'2025-12-14 14:30:55',16500000.00,'0987675462','COD','COMPLETED',NULL,'Caca','0123141543'),(16,5,'2025-12-15 04:20:22',9900000.00,'Hà Nội','COD','PENDING',NULL,'Cacao','0987656734');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` longtext COLLATE utf8mb4_unicode_ci COMMENT 'Mô tả chi tiết sản phẩm',
  `list_price` decimal(38,2) NOT NULL,
  `selling_price` decimal(38,2) NOT NULL,
  `brand` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `technical_specs` text COLLATE utf8mb4_unicode_ci COMMENT 'Thông số kỹ thuật (dạng JSON hoặc text)',
  `main_image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category_id` int NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Thông tin chi tiết của từng sản phẩm';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Bếp Điện Từ Cao Cấp H-101','Công suất 2000W, mặt kính chịu nhiệt.',5500000.00,4999000.00,'Teka',NULL,'https://dienmayquanghanh.com/Upload/avatar/content-2023-1/ava-bep-tu-don-hawonkoo-ceh-101-1.jpg',1,1,'2025-12-06 12:33:23'),(2,'Tủ Lạnh Inverter 500L S-Pro','Tiết kiệm điện, công nghệ làm lạnh đa chiều.',18000000.00,16500000.00,'Samsung',NULL,'https://khodienmay.net/wp-content/uploads/2025/08/DL.044738_FEATURE_172732.png',2,1,'2025-12-06 12:33:23'),(3,'Máy Giặt Lồng Ngang P-X9','Khối lượng 9kg, 12 chương trình giặt.',10500000.00,9900000.00,'Panasonic',NULL,'https://cdn.mediamart.vn/images/product/may-giat-long-ngang-sharp-inverter-9-kg-es-fm90cb-sb_15d42a6d.webp',2,1,'2025-12-06 12:33:23'),(4,'Quạt Không Cánh Mini Fan-20','Thiết kế an toàn, điều khiển từ xa.',1200000.00,1000000.00,'Xiaomi',NULL,'https://fujihomevn.com/image/cache/catalog/san-pham/quat-khong-canh/bf16/bf16-600x600.png',3,1,'2025-12-06 12:33:23'),(5,'Tủ lạnh Panasonic Inverter 510 lít Multi Door NR-X561BK-VN​','Dung tích sử dụng 510 lít phù hợp gia đình 4 - 5 thành viên.\nThiết kế âm tường tăng thẩm mỹ cho không gian bếp.\nCánh cửa tủ mở tối đa 120 độ giúp sử dụng thuận tiện, dễ dàng lấy thực phẩm.\nCông nghệ Inverter vận hành êm ái, tiết kiệm điện hiệu quả.\nLàm đá tự động tạo ra những viên đá mát lạnh nhanh chóng.\nKhông khí trong tủ trong lành nhờ Ag+ loại bỏ vi khuẩn.',23999990.00,25000000.00,'Panasonic ',NULL,'https://cdnv2.tgdd.vn/mwg-static/dmx/Products/Images/1943/332039/tu-lanh-panasonic-inverter-510-lit-multi-door-nr-x561bk-vn-1-638991443261328009-700x467.jpg',2,1,'2025-12-15 04:17:15');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotions`
--

DROP TABLE IF EXISTS `promotions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotions` (
  `promotion_id` int NOT NULL AUTO_INCREMENT,
  `coupon_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `promo_type` enum('PERCENT','FIXED') COLLATE utf8mb4_unicode_ci NOT NULL,
  `value` decimal(38,2) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `max_usage` int DEFAULT NULL COMMENT 'Số lần sử dụng tối đa của mã',
  `current_usage` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`promotion_id`),
  UNIQUE KEY `coupon_code` (`coupon_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Chương trình khuyến mãi và mã giảm giá';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotions`
--

LOCK TABLES `promotions` WRITE;
/*!40000 ALTER TABLE `promotions` DISABLE KEYS */;
INSERT INTO `promotions` VALUES (1,'GIAY_VANG','PERCENT',10.00,'2025-12-01','2025-12-31',50,0),(2,'FREE_SHIP','FIXED',30000.00,'2025-12-01','2025-12-25',NULL,0),(3,'MAYGIATTOT','FIXED',200000.00,'2019-01-12','2026-01-12',100,0);
/*!40000 ALTER TABLE `promotions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `review_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `rating` int NOT NULL COMMENT 'Điểm đánh giá từ 1 đến 5',
  `content` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  UNIQUE KEY `uq_user_product` (`user_id`,`product_id`) COMMENT 'Mỗi người chỉ đánh giá 1 lần cho 1 sản phẩm',
  KEY `product_id` (`product_id`),
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE,
  CONSTRAINT `reviews_chk_1` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Đánh giá và bình luận sản phẩm';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,3,1,5,'Bếp dùng rất ổn, nóng nhanh, dễ vệ sinh.','2025-12-06 12:33:45'),(2,5,1,4,'ngon','2025-12-14 08:26:41'),(3,5,4,5,'Quạt dùng ổn, giao hàng nhanh','2025-12-15 02:57:26');
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` enum('CUSTOMER','ADMIN','STAFF') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'CUSTOMER' COMMENT 'Phân quyền: Khách hàng, Quản trị viên, Nhân viên Kho/Vận chuyển',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Lưu trữ thông tin người dùng và quản trị viên';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Admin Hệ Thống','admin@appliance.com','$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVwdFYiNu/vJ2.hI.1fo.hTy','0901234567','Tầng 1, Trụ sở chính','ADMIN','2025-12-06 12:33:17'),(2,'Nhân Viên Kho A','staffA@appliance.com','$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVwdFYiNu/vJ2.hI.1fo.hTy','0907654321','Kho khu vực A','STAFF','2025-12-06 12:33:17'),(3,'Nguyễn Văn Khách','khachhang1@gmail.com','$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVwdFYiNu/vJ2.hI.1fo.hTy','0912345678','Số 1, Đường Quang Trung, Quận 1','CUSTOMER','2025-12-06 12:33:17'),(4,'Nguyễn Văn Kiên','kien1@gmail.com','$2a$10$yJQs.i80QVhe41SWlk5PdupTKi0oWnd8E59MU8Y2vjjvdXCT6aQP.','0843232412','Hà Nội','ADMIN','2025-12-09 04:41:45'),(5,'Nguyễn Thị A','A@gmail.com','$2a$10$xGuAWaAnvNYwhcJVLZikbOCb8leF2cD.v8YuXBohnETkFixvX3yvi','0213523243','Hà Nội','CUSTOMER','2025-12-10 03:46:58'),(6,'pham hoang nam','ngoisao1432@gmail.com','$2a$10$WTx4D9Xjl4KJ6P0BVWRN5OVOXI6ADWaeD4gxKhi0XVMfDgRXqwmHy','0213523243','1','CUSTOMER','2025-12-10 04:47:13'),(7,'Nguyễn Văn Kiên','kien12@gmail.com','$2a$10$Ewznf2Ccm8bL8GV8jiPAbOU4hEnCy1WBlPSrNau/QR44StmQu0SMK','0125634132','Hà Nội','STAFF','2025-12-14 14:04:07');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-15 20:56:12
