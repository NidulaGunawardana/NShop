-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.31 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for nshop
CREATE DATABASE IF NOT EXISTS `nshop` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `nshop`;

-- Dumping structure for table nshop.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `email` varchar(100) NOT NULL,
  `verification` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `fname` varchar(20) DEFAULT NULL,
  `lname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.admin: ~1 rows (approximately)
INSERT INTO `admin` (`email`, `verification`, `fname`, `lname`) VALUES
	('nidulam@outlook.com', '61815c1896a3d', 'Nidula', 'Gunawardna');

-- Dumping structure for table nshop.brand
CREATE TABLE IF NOT EXISTS `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.brand: ~5 rows (approximately)
INSERT INTO `brand` (`id`, `name`) VALUES
	(1, 'LG'),
	(2, 'Samsung'),
	(3, 'Heire'),
	(4, 'Panasonic'),
	(5, 'Mitshubishi');

-- Dumping structure for table nshop.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.category: ~3 rows (approximately)
INSERT INTO `category` (`id`, `name`) VALUES
	(1, 'Airconditioners'),
	(2, 'Washing Machines'),
	(3, 'Pipes');

-- Dumping structure for table nshop.city
CREATE TABLE IF NOT EXISTS `city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.city: ~3 rows (approximately)
INSERT INTO `city` (`id`, `name`) VALUES
	(1, 'Colombo'),
	(2, 'Gampaha'),
	(3, 'Kandy');

-- Dumping structure for table nshop.companies
CREATE TABLE IF NOT EXISTS `companies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `contact_no` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.companies: ~4 rows (approximately)
INSERT INTO `companies` (`id`, `name`, `contact_no`) VALUES
	(1, 'Abans', '0115241753'),
	(2, 'Singer', '0152347222'),
	(3, 'Damro', '0774895111'),
	(4, 'Samsung', '0724569871');

-- Dumping structure for table nshop.company_branches
CREATE TABLE IF NOT EXISTS `company_branches` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `co_number` varchar(10) DEFAULT NULL,
  `line1` varchar(45) DEFAULT NULL,
  `line2` varchar(45) DEFAULT NULL,
  `city_id` int NOT NULL,
  `companies_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_company_branches_city1_idx` (`city_id`),
  KEY `fk_company_branches_companies1_idx` (`companies_id`),
  CONSTRAINT `fk_company_branches_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `fk_company_branches_companies1` FOREIGN KEY (`companies_id`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.company_branches: ~2 rows (approximately)
INSERT INTO `company_branches` (`id`, `name`, `co_number`, `line1`, `line2`, `city_id`, `companies_id`) VALUES
	(1, 'Singer 1', '0112345698', '123/30/C', 'Colombo Road', 1, 2),
	(2, 'Abans 1', '0332254874', '123/B/2 Church Avanue', 'Colombo Road', 2, 1);

-- Dumping structure for table nshop.customers
CREATE TABLE IF NOT EXISTS `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `mobile` varchar(10) DEFAULT NULL,
  `gender_id` int NOT NULL,
  `line1` varchar(45) DEFAULT NULL,
  `line2` varchar(45) DEFAULT NULL,
  `city_id` int NOT NULL,
  `ststus_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customers_gender1_idx` (`gender_id`),
  KEY `fk_customers_city1_idx` (`city_id`),
  KEY `fk_customers_ststus1_idx` (`ststus_id`),
  CONSTRAINT `fk_customers_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `fk_customers_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`),
  CONSTRAINT `fk_customers_ststus1` FOREIGN KEY (`ststus_id`) REFERENCES `ststus` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.customers: ~4 rows (approximately)
INSERT INTO `customers` (`id`, `fname`, `lname`, `mobile`, `gender_id`, `line1`, `line2`, `city_id`, `ststus_id`) VALUES
	(0, 'Default', 'Default', 'Default', 1, 'Default', 'Default', 1, 1),
	(1, 'Kamal', 'Gunarathna', '0711234567', 1, '123/B/2', 'Kadawatha Road', 2, 1),
	(3, 'Kapila', 'Kumara', '0781234567', 1, '123/C', 'Kandy Road', 2, 1),
	(4, 'Kumari', 'Perera', '0774568921', 2, '154/N/3', 'Colombo Road', 2, 1);

-- Dumping structure for table nshop.gender
CREATE TABLE IF NOT EXISTS `gender` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.gender: ~2 rows (approximately)
INSERT INTO `gender` (`id`, `name`) VALUES
	(1, 'Male'),
	(2, 'Female');

-- Dumping structure for table nshop.grn
CREATE TABLE IF NOT EXISTS `grn` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_id` int NOT NULL,
  `grn_id` varchar(45) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_supplier1_idx` (`supplier_id`),
  KEY `fk_grn_user1_idx` (`user_id`),
  CONSTRAINT `fk_grn_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`),
  CONSTRAINT `fk_grn_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.grn: ~6 rows (approximately)
INSERT INTO `grn` (`id`, `supplier_id`, `grn_id`, `date_time`, `user_id`) VALUES
	(17, 1, '1658343229716-1', '2022-07-21 00:23:49', 1),
	(18, 1, '1658751897912-1', '2022-07-25 17:54:57', 1),
	(19, 1, '1658771130022-1', '2022-07-25 23:15:30', 1),
	(20, 3, '1658984936181-1', '2022-07-28 10:38:56', 1),
	(21, 2, '1658999433672-1', '2022-07-28 14:40:33', 1),
	(22, 2, '1658999534100-1', '2022-07-28 14:42:14', 1);

-- Dumping structure for table nshop.grn_items
CREATE TABLE IF NOT EXISTS `grn_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grn_id` int NOT NULL,
  `qty` double DEFAULT NULL,
  `qty_units_id` int NOT NULL,
  `buying_price` double DEFAULT NULL,
  `stock_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_items_grn1_idx` (`grn_id`),
  KEY `fk_grn_items_stock1_idx` (`stock_id`),
  KEY `fk_grn_items_qty_units1_idx` (`qty_units_id`),
  CONSTRAINT `fk_grn_items_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`),
  CONSTRAINT `fk_grn_items_qty_units1` FOREIGN KEY (`qty_units_id`) REFERENCES `qty_units` (`id`),
  CONSTRAINT `fk_grn_items_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.grn_items: ~11 rows (approximately)
INSERT INTO `grn_items` (`id`, `grn_id`, `qty`, `qty_units_id`, `buying_price`, `stock_id`) VALUES
	(22, 17, 10, 7, 1500, 18),
	(23, 17, 20, 1, 100, 19),
	(24, 17, 100, 7, 10, 20),
	(25, 18, 5, 3, 100, 21),
	(26, 18, 50, 7, 10, 22),
	(27, 19, 2, 3, 100, 23),
	(28, 20, 50, 1, 100, 24),
	(29, 20, 50, 7, 50, 25),
	(30, 21, 10, 1, 100, 24),
	(31, 21, 50, 7, 50, 26),
	(32, 22, 1, 3, 10, 27);

-- Dumping structure for table nshop.grn_payments
CREATE TABLE IF NOT EXISTS `grn_payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grn_id` int NOT NULL,
  `payment` double DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `payment_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_payments_grn1_idx` (`grn_id`),
  KEY `fk_grn_payments_payment_type1_idx` (`payment_type_id`),
  CONSTRAINT `fk_grn_payments_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`),
  CONSTRAINT `fk_grn_payments_payment_type1` FOREIGN KEY (`payment_type_id`) REFERENCES `payment_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.grn_payments: ~6 rows (approximately)
INSERT INTO `grn_payments` (`id`, `grn_id`, `payment`, `balance`, `payment_type_id`) VALUES
	(13, 17, 18000, 0, 1),
	(14, 18, 1000, 0, 1),
	(15, 19, 190, 10, 1),
	(16, 20, 7500, 0, 1),
	(17, 21, 3500, 0, 1),
	(18, 22, 5, 5, 1);

-- Dumping structure for table nshop.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `invoice_id` varchar(45) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `customers_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_customers1_idx` (`customers_id`),
  KEY `fk_invoice_user1_idx` (`user_id`),
  CONSTRAINT `fk_invoice_customers1` FOREIGN KEY (`customers_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `fk_invoice_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.invoice: ~5 rows (approximately)
INSERT INTO `invoice` (`id`, `invoice_id`, `date_time`, `customers_id`, `user_id`) VALUES
	(8, '1658408636126-2', '2022-07-21 18:33:56', 3, 2),
	(9, '1658772004907-2', '2022-07-25 23:30:04', 1, 2),
	(10, '1658985026386-2', '2022-07-28 10:40:26', 3, 2),
	(11, '1659000078848-2', '2022-07-28 14:51:18', 4, 2),
	(12, '1659063007253-2', '2022-07-29 08:20:07', 4, 2),
	(13, '1683426065481-2', '2023-05-07 07:51:05', 0, 2),
	(14, '1707141613221-2', '2024-02-05 19:30:13', 3, 2);

-- Dumping structure for table nshop.invoice_item
CREATE TABLE IF NOT EXISTS `invoice_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock_id` int NOT NULL,
  `qty` double DEFAULT NULL,
  `qty_units_id` int NOT NULL,
  `invoice_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_item_invoice1_idx` (`invoice_id`),
  KEY `fk_invoice_item_stock1_idx` (`stock_id`),
  KEY `fk_invoice_item_qty_units1_idx` (`qty_units_id`),
  CONSTRAINT `fk_invoice_item_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `fk_invoice_item_qty_units1` FOREIGN KEY (`qty_units_id`) REFERENCES `qty_units` (`id`),
  CONSTRAINT `fk_invoice_item_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.invoice_item: ~13 rows (approximately)
INSERT INTO `invoice_item` (`id`, `stock_id`, `qty`, `qty_units_id`, `invoice_id`) VALUES
	(13, 18, 2, 7, 8),
	(14, 20, 10, 7, 8),
	(15, 19, 2, 1, 8),
	(16, 18, 5, 7, 9),
	(17, 19, 5, 1, 9),
	(18, 25, 10, 7, 10),
	(19, 24, 10, 1, 10),
	(20, 23, 1, 3, 10),
	(21, 20, 20, 7, 11),
	(22, 19, 10, 1, 11),
	(23, 19, 2, 1, 12),
	(24, 21, 1.5, 3, 12),
	(25, 25, 20, 7, 12),
	(26, 22, 10, 7, 13),
	(27, 24, 10, 1, 13),
	(28, 25, 10, 7, 14),
	(29, 24, 10, 1, 14);

-- Dumping structure for table nshop.invoice_payments
CREATE TABLE IF NOT EXISTS `invoice_payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `payment` double DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `payment_type_id` int NOT NULL,
  `invoice_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_payments_payment_type1_idx` (`payment_type_id`),
  KEY `fk_invoice_payments_invoice1_idx` (`invoice_id`),
  CONSTRAINT `fk_invoice_payments_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `fk_invoice_payments_payment_type1` FOREIGN KEY (`payment_type_id`) REFERENCES `payment_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.invoice_payments: ~5 rows (approximately)
INSERT INTO `invoice_payments` (`id`, `payment`, `balance`, `payment_type_id`, `invoice_id`) VALUES
	(6, 5000, 500, 1, 8),
	(7, 100, -10650, 1, 9),
	(8, 2250, 0, 2, 10),
	(9, 1900, 0, 2, 11),
	(10, 1680, 0, 1, 12),
	(11, 2500, 850, 1, 13),
	(12, 2500, 400, 1, 14);

-- Dumping structure for table nshop.money_changes
CREATE TABLE IF NOT EXISTS `money_changes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.money_changes: ~11 rows (approximately)
INSERT INTO `money_changes` (`id`, `description`, `price`, `date_time`) VALUES
	(1, 'Total Price For the GRN - 1658343229716-1', -18000, '2022-07-21 00:23:49'),
	(2, 'Total Price For the INVOICE - 1658408636126-2', 4500, '2022-07-21 18:33:56'),
	(3, 'Total Price For the GRN - 1658751897912-1', -1000, '2022-07-25 17:54:57'),
	(4, 'Total Price For the GRN - 1658771130022-1', -200, '2022-07-25 23:15:30'),
	(5, 'Total Price For the INVOICE - 1658772004907-2', 10750, '2022-07-25 23:30:04'),
	(6, 'Total Price For the GRN - 1658984936181-1', -7500, '2022-07-28 10:38:56'),
	(7, 'Total Price For the INVOICE - 1658985026386-2', 2250, '2022-07-28 10:40:26'),
	(8, 'Total Price For the GRN - 1658999433672-1', -3500, '2022-07-28 14:40:33'),
	(9, 'Total Price For the GRN - 1658999534100-1', -10, '2022-07-28 14:42:14'),
	(10, 'Total Price For the INVOICE - 1659000078848-2', 1900, '2022-07-28 14:51:18'),
	(11, 'Total Price For the INVOICE - 1659063007253-2', 1680, '2022-07-29 08:20:07'),
	(12, 'Total Price For the INVOICE - 1683426065481-2', 1650, '2023-05-07 07:51:05'),
	(13, 'Total Price For the INVOICE - 1707141613221-2', 2100, '2024-02-05 19:30:13');

-- Dumping structure for table nshop.payment_type
CREATE TABLE IF NOT EXISTS `payment_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.payment_type: ~4 rows (approximately)
INSERT INTO `payment_type` (`id`, `name`) VALUES
	(1, 'Cash'),
	(2, 'Credit/Debit card'),
	(3, 'Cheque'),
	(4, 'After Sold');

-- Dumping structure for table nshop.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `category_id` int NOT NULL,
  `brand_id` int NOT NULL,
  `qty_type_id` int NOT NULL,
  `ststus_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_category1_idx` (`category_id`),
  KEY `fk_product_brand1_idx` (`brand_id`),
  KEY `fk_product_qty_type1_idx` (`qty_type_id`),
  KEY `fk_product_ststus1_idx` (`ststus_id`),
  CONSTRAINT `fk_product_brand1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `fk_product_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_product_qty_type1` FOREIGN KEY (`qty_type_id`) REFERENCES `qty_type` (`id`),
  CONSTRAINT `fk_product_ststus1` FOREIGN KEY (`ststus_id`) REFERENCES `ststus` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.product: ~4 rows (approximately)
INSERT INTO `product` (`id`, `name`, `category_id`, `brand_id`, `qty_type_id`, `ststus_id`) VALUES
	(1, '800W Driver Motor', 2, 1, 3, 1),
	(2, 'Cupper Pipe', 1, 4, 1, 1),
	(3, 'Airconditioner fittings', 1, 2, 3, 1),
	(4, 'Nails', 1, 5, 2, 1);

-- Dumping structure for table nshop.qty_type
CREATE TABLE IF NOT EXISTS `qty_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.qty_type: ~4 rows (approximately)
INSERT INTO `qty_type` (`id`, `name`) VALUES
	(1, 'length'),
	(2, 'Mass'),
	(3, 'Item'),
	(4, 'Volume');

-- Dumping structure for table nshop.qty_units
CREATE TABLE IF NOT EXISTS `qty_units` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `qty_type_id` int NOT NULL,
  `multiplication` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_qty_units_qty_type1_idx` (`qty_type_id`),
  CONSTRAINT `fk_qty_units_qty_type1` FOREIGN KEY (`qty_type_id`) REFERENCES `qty_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.qty_units: ~8 rows (approximately)
INSERT INTO `qty_units` (`id`, `name`, `qty_type_id`, `multiplication`) VALUES
	(1, 'm', 1, 1),
	(2, 'mm', 1, 0.001),
	(3, 'Kg', 2, 1),
	(4, 'g', 2, 0.001),
	(5, 'l', 4, 1),
	(6, 'ml', 4, 0.001),
	(7, 'units', 3, 1),
	(8, 'cm', 1, 0.01);

-- Dumping structure for table nshop.stock
CREATE TABLE IF NOT EXISTS `stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `selling_price` double DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `qty_units_id` int NOT NULL,
  `mfd` date DEFAULT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_product1_idx` (`product_id`),
  KEY `fk_stock_qty_units1_idx` (`qty_units_id`),
  CONSTRAINT `fk_stock_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_stock_qty_units1` FOREIGN KEY (`qty_units_id`) REFERENCES `qty_units` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.stock: ~10 rows (approximately)
INSERT INTO `stock` (`id`, `selling_price`, `qty`, `qty_units_id`, `mfd`, `product_id`) VALUES
	(18, 2000, 3, 7, '2022-07-01', 1),
	(19, 150, 1, 1, '2022-06-07', 2),
	(20, 20, 75, 7, '2022-06-01', 3),
	(21, 120, 3.5, 3, '2022-02-08', 4),
	(22, 15, 40, 7, '2022-04-14', 3),
	(23, 150, 1, 3, '2022-02-03', 4),
	(24, 150, 30, 1, '2022-07-01', 2),
	(25, 60, 0, 7, '2022-07-03', 3),
	(26, 60, 50, 7, '2022-07-08', 3),
	(27, 20, 1, 3, '2022-07-08', 4);

-- Dumping structure for table nshop.stock_changes
CREATE TABLE IF NOT EXISTS `stock_changes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `stock_id` int NOT NULL,
  `qty` double DEFAULT NULL,
  `qty_units_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_changes_stock1_idx` (`stock_id`),
  KEY `fk_stock_changes_qty_units1_idx` (`qty_units_id`),
  CONSTRAINT `fk_stock_changes_qty_units1` FOREIGN KEY (`qty_units_id`) REFERENCES `qty_units` (`id`),
  CONSTRAINT `fk_stock_changes_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.stock_changes: ~26 rows (approximately)
INSERT INTO `stock_changes` (`id`, `description`, `date_time`, `stock_id`, `qty`, `qty_units_id`) VALUES
	(32, 'Added To Stock From The GRN -1658343229716-1', '2022-07-21 00:23:49', 18, 10, 7),
	(33, 'Added To Stock From The GRN -1658343229716-1', '2022-07-21 00:23:49', 19, 20, 1),
	(34, 'Added To Stock From The GRN -1658343229716-1', '2022-07-21 00:23:49', 20, 100, 7),
	(35, 'Bought From Invoice -1658408636126-2', '2022-07-21 18:33:56', 18, -2, 7),
	(36, 'Bought From Invoice -1658408636126-2', '2022-07-21 18:33:56', 20, -10, 7),
	(37, 'Bought From Invoice -1658408636126-2', '2022-07-21 18:33:56', 19, -2, 1),
	(38, 'Added To Stock From The GRN -1658751897912-1', '2022-07-25 17:54:57', 21, 5, 3),
	(39, 'Added To Stock From The GRN -1658751897912-1', '2022-07-25 17:54:57', 22, 50, 7),
	(40, 'Added To Stock From The GRN -1658771130022-1', '2022-07-25 23:15:30', 23, 2, 3),
	(41, 'Quantity changed by user -1', '2022-07-25 23:23:55', 20, 5, 7),
	(42, 'Bought From Invoice -1658772004907-2', '2022-07-25 23:30:04', 18, -5, 7),
	(43, 'Bought From Invoice -1658772004907-2', '2022-07-25 23:30:04', 19, -5, 1),
	(44, 'Added To Stock From The GRN -1658984936181-1', '2022-07-28 10:38:56', 24, 50, 1),
	(45, 'Added To Stock From The GRN -1658984936181-1', '2022-07-28 10:38:56', 25, 50, 7),
	(46, 'Bought From Invoice -1658985026386-2', '2022-07-28 10:40:26', 25, -10, 7),
	(47, 'Bought From Invoice -1658985026386-2', '2022-07-28 10:40:26', 24, -10, 1),
	(48, 'Bought From Invoice -1658985026386-2', '2022-07-28 10:40:26', 23, -1, 3),
	(49, 'Added To Stock From The GRN -1658999433672-1', '2022-07-28 14:40:33', 24, 10, 1),
	(50, 'Added To Stock From The GRN -1658999433672-1', '2022-07-28 14:40:33', 26, 50, 7),
	(51, 'Added To Stock From The GRN -1658999534100-1', '2022-07-28 14:42:14', 27, 1, 3),
	(52, 'Quantity changed by user -1', '2022-07-28 14:44:37', 25, -10, 7),
	(53, 'Bought From Invoice -1659000078848-2', '2022-07-28 14:51:18', 20, -20, 7),
	(54, 'Bought From Invoice -1659000078848-2', '2022-07-28 14:51:18', 19, -10, 1),
	(55, 'Bought From Invoice -1659063007253-2', '2022-07-29 08:20:07', 19, -2, 1),
	(56, 'Bought From Invoice -1659063007253-2', '2022-07-29 08:20:07', 21, -1.5, 3),
	(57, 'Bought From Invoice -1659063007253-2', '2022-07-29 08:20:07', 25, -20, 7),
	(58, 'Bought From Invoice -1683426065481-2', '2023-05-07 07:51:05', 22, -10, 7),
	(59, 'Bought From Invoice -1683426065481-2', '2023-05-07 07:51:05', 24, -10, 1),
	(60, 'Bought From Invoice -1707141613221-2', '2024-02-05 19:30:13', 25, -10, 7),
	(61, 'Bought From Invoice -1707141613221-2', '2024-02-05 19:30:13', 24, -10, 1);

-- Dumping structure for table nshop.ststus
CREATE TABLE IF NOT EXISTS `ststus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.ststus: ~2 rows (approximately)
INSERT INTO `ststus` (`id`, `name`) VALUES
	(1, 'Active'),
	(2, 'Deactive');

-- Dumping structure for table nshop.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `contact_no` varchar(10) DEFAULT NULL,
  `company_branches_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_company_branches1_idx` (`company_branches_id`),
  CONSTRAINT `fk_supplier_company_branches1` FOREIGN KEY (`company_branches_id`) REFERENCES `company_branches` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.supplier: ~4 rows (approximately)
INSERT INTO `supplier` (`id`, `fname`, `lname`, `contact_no`, `company_branches_id`) VALUES
	(1, 'Kapila', 'Kumara', '0112364598', 2),
	(2, 'Kavindu', 'Manage', '0712384569', 1),
	(3, 'Ashan Janidu', 'Kavindu', '0332498488', 1),
	(4, 'Kamal', 'Perera', '0714879562', 1);

-- Dumping structure for table nshop.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `gender_id` int NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `user_type_id` int NOT NULL,
  `ststus_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_user_type_idx` (`user_type_id`),
  KEY `fk_user_gender1_idx` (`gender_id`),
  KEY `fk_user_ststus1_idx` (`ststus_id`),
  CONSTRAINT `fk_user_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`),
  CONSTRAINT `fk_user_ststus1` FOREIGN KEY (`ststus_id`) REFERENCES `ststus` (`id`),
  CONSTRAINT `fk_user_user_type` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.user: ~9 rows (approximately)
INSERT INTO `user` (`id`, `fname`, `lname`, `username`, `gender_id`, `password`, `user_type_id`, `ststus_id`) VALUES
	(1, 'Nidula', 'Gunawardana', 'nidula', 1, 'nidula123', 1, 1),
	(2, 'Kithma', 'Gunawardana', 'kithma', 2, 'kithma123', 4, 1),
	(3, 'Saman', 'Gunawardana', 'saman', 1, 'saman123', 2, 1),
	(4, 'Nayana', 'Godagampala', 'nayana', 2, 'nayana123', 3, 1),
	(5, 'Kapila', 'Perera', 'kapila', 1, 'kapila123', 4, 1),
	(7, 'Janidu', 'Chamika', 'janidu', 1, 'janidu123', 1, 1),
	(8, 'Thilina', 'Rajakaruna', 'thilina', 1, 'thilina123', 1, 1),
	(9, 'Ashan', 'Kaavindu', 'ashan', 1, 'ashan123', 1, 1),
	(10, 'Lahiru', 'Viraj', 'lahiru', 1, 'lahiru123', 1, 1);

-- Dumping structure for table nshop.user_type
CREATE TABLE IF NOT EXISTS `user_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table nshop.user_type: ~4 rows (approximately)
INSERT INTO `user_type` (`id`, `name`) VALUES
	(1, 'Admin A'),
	(2, 'Admin B'),
	(3, 'Admin C'),
	(4, 'Cashier');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
