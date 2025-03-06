-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               8.0.25 - MySQL Community Server - GPL
-- Операционная система:         Win64
-- HeidiSQL Версия:              12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Дамп структуры базы данных kp_customs
CREATE DATABASE IF NOT EXISTS `kp_customs` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `kp_customs`;

-- Дамп структуры для таблица kp_customs.carrier
CREATE TABLE IF NOT EXISTS `carrier` (
  `id_carrier` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id_company` int NOT NULL,
  PRIMARY KEY (`id_carrier`),
  KEY `FK_carrier_company` (`id_company`),
  CONSTRAINT `FK_carrier_company` FOREIGN KEY (`id_company`) REFERENCES `company` (`id_company`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы kp_customs.carrier: ~2 rows (приблизительно)
REPLACE INTO `carrier` (`id_carrier`, `name`, `id_company`) VALUES
	(1, 'Минин Сергей', 1),
	(2, 'Иванов Сергей', 1);

-- Дамп структуры для таблица kp_customs.company
CREATE TABLE IF NOT EXISTS `company` (
  `id_company` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id_company`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы kp_customs.company: ~1 rows (приблизительно)
REPLACE INTO `company` (`id_company`, `name`, `address`) VALUES
	(1, 'Брокерсервис', 'г. Минск, ул. ывапрол');

-- Дамп структуры для таблица kp_customs.declarant
CREATE TABLE IF NOT EXISTS `declarant` (
  `id_deсlarant` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `id_company` int NOT NULL,
  `id_user` int NOT NULL,
  PRIMARY KEY (`id_deсlarant`) USING BTREE,
  KEY `FK_declarant_user` (`id_user`),
  KEY `FK_declarant_company` (`id_company`),
  CONSTRAINT `FK_declarant_company` FOREIGN KEY (`id_company`) REFERENCES `company` (`id_company`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_declarant_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы kp_customs.declarant: ~3 rows (приблизительно)
REPLACE INTO `declarant` (`id_deсlarant`, `name`, `id_company`, `id_user`) VALUES
	(20, 'Дудко Валерия Сергеевна', 1, 7),
	(21, 'asdfghj', 1, 8),
	(22, 'sdfghj', 1, 9);

-- Дамп структуры для таблица kp_customs.good
CREATE TABLE IF NOT EXISTS `good` (
  `id_good` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `amount` int NOT NULL DEFAULT (0),
  `weight` double NOT NULL DEFAULT (0),
  `price` double NOT NULL DEFAULT (0),
  `rate_type` int NOT NULL,
  `rate` double NOT NULL DEFAULT (0),
  `VAT` int DEFAULT NULL,
  `excise` double DEFAULT NULL,
  `is_import` tinyint NOT NULL,
  `is_confirmed` tinyint NOT NULL,
  `id_declarant` int NOT NULL,
  PRIMARY KEY (`id_good`),
  KEY `FK_good_declarant` (`id_declarant`),
  CONSTRAINT `FK_good_declarant` FOREIGN KEY (`id_declarant`) REFERENCES `declarant` (`id_deсlarant`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы kp_customs.good: ~1 rows (приблизительно)
REPLACE INTO `good` (`id_good`, `name`, `amount`, `weight`, `price`, `rate_type`, `rate`, `VAT`, `excise`, `is_import`, `is_confirmed`, `id_declarant`) VALUES
	(1, 'sdfghj', 10, 10, 10, 1, 10, 0, 0, 0, 1, 20);

-- Дамп структуры для таблица kp_customs.passing
CREATE TABLE IF NOT EXISTS `passing` (
  `id_passing` int NOT NULL AUTO_INCREMENT,
  `arrival_date` date NOT NULL,
  `departure_date` date NOT NULL,
  `id_post` int NOT NULL,
  `id_carrier` int NOT NULL,
  PRIMARY KEY (`id_passing`),
  KEY `FK_passing_carrier` (`id_carrier`),
  KEY `FK_passing_post` (`id_post`),
  CONSTRAINT `FK_passing_carrier` FOREIGN KEY (`id_carrier`) REFERENCES `carrier` (`id_carrier`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_passing_post` FOREIGN KEY (`id_post`) REFERENCES `post` (`id_post`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы kp_customs.passing: ~3 rows (приблизительно)

-- Дамп структуры для таблица kp_customs.post
CREATE TABLE IF NOT EXISTS `post` (
  `id_post` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id_post`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы kp_customs.post: ~0 rows (приблизительно)

-- Дамп структуры для таблица kp_customs.role
CREATE TABLE IF NOT EXISTS `role` (
  `id_role` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы kp_customs.role: ~3 rows (приблизительно)
REPLACE INTO `role` (`id_role`, `role_name`) VALUES
	(1, 'high_admin'),
	(2, 'admin'),
	(3, 'user');

-- Дамп структуры для таблица kp_customs.user
CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL DEFAULT ' ',
  `pass_hash` blob NOT NULL,
  `is_blocked` tinyint NOT NULL,
  `id_role` int NOT NULL,
  PRIMARY KEY (`id_user`) USING BTREE,
  KEY `FK_user_role` (`id_role`),
  CONSTRAINT `FK_user_role` FOREIGN KEY (`id_role`) REFERENCES `role` (`id_role`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы kp_customs.user: ~4 rows (приблизительно)
REPLACE INTO `user` (`id_user`, `login`, `pass_hash`, `is_blocked`, `id_role`) VALUES
	(7, 'user', _binary 0xee11cbb19052e40b07aac0ca060c23ee, 0, 3),
	(8, 'admin', _binary 0x21232f297a57a5a743894a0e4a801fc3, 0, 2),
	(9, 'hadmin', _binary 0xba6a75c21a4f4ab39d23c0a8ca33fa0f, 0, 1),
	(15, 'testtest', _binary 0xd8578edf8458ce06fbc5bb76a58c5ca4, 0, 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
