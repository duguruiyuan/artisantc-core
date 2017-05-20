/*
Navicat MySQL Data Transfer

Source Server         : localhost_art_artisan_tc
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : art_artisan_tc

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2016-12-15 17:16:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_administrator`
-- ----------------------------
DROP TABLE IF EXISTS `t_administrator`;
CREATE TABLE `t_administrator` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `mobile` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ADMINISTRATOR_USERNAME` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_administrator
-- ----------------------------

-- ----------------------------
-- Table structure for `t_administrator_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_administrator_permission`;
CREATE TABLE `t_administrator_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `http_method` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `uri` varchar(300) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_administrator_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `t_administrator_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_administrator_role`;
CREATE TABLE `t_administrator_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_administrator_role
-- ----------------------------

-- ----------------------------
-- Table structure for `t_administrator_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_administrator_role_permission`;
CREATE TABLE `t_administrator_role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_ADMINISTRATOR_ROLE_PERMISSION_ID` (`role_id`,`permission_id`),
  KEY `FK_ADMINISTRATOR_ROLE_PERMISSION_PERMISSION_ID` (`permission_id`),
  CONSTRAINT `FK_ADMINISTRATOR_ROLE_PERMISSION_PERMISSION_ID` FOREIGN KEY (`permission_id`) REFERENCES `t_administrator_permission` (`id`),
  CONSTRAINT `FK_ADMINISTRATOR_ROLE_PERMISSION_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `t_administrator_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_administrator_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `t_administrator_role_relation`
-- ----------------------------
DROP TABLE IF EXISTS `t_administrator_role_relation`;
CREATE TABLE `t_administrator_role_relation` (
  `administrator_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_ADMINISTRATOR_ROLE_RELATION_ID` (`administrator_id`,`role_id`),
  KEY `FK_ADMINISTRATOR_ROLE_RELATION_ROLE_ID` (`role_id`),
  CONSTRAINT `FK_ADMINISTRATOR_ROLE_RELATION_ADMINISTRATOR_ID` FOREIGN KEY (`administrator_id`) REFERENCES `t_administrator` (`id`),
  CONSTRAINT `FK_ADMINISTRATOR_ROLE_RELATION_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `t_administrator_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_administrator_role_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `t_advertisement`
-- ----------------------------
DROP TABLE IF EXISTS `t_advertisement`;
CREATE TABLE `t_advertisement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `category` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `title` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_advertisement
-- ----------------------------

-- ----------------------------
-- Table structure for `t_advertisement_browse_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_advertisement_browse_record`;
CREATE TABLE `t_advertisement_browse_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `advertisement_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ADVERTISEMENT_BROWSE_RECORD_ADVERTISEMENT_ID` (`advertisement_id`),
  KEY `FK_ADVERTISEMENT_BROWSE_RECORD_USER_ID` (`user_id`),
  CONSTRAINT `FK_ADVERTISEMENT_BROWSE_RECORD_ADVERTISEMENT_ID` FOREIGN KEY (`advertisement_id`) REFERENCES `t_advertisement` (`id`),
  CONSTRAINT `FK_ADVERTISEMENT_BROWSE_RECORD_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_advertisement_browse_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_advertisement_image`
-- ----------------------------
DROP TABLE IF EXISTS `t_advertisement_image`;
CREATE TABLE `t_advertisement_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `image_height` int(11) DEFAULT NULL,
  `image_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `image_width` int(11) DEFAULT NULL,
  `thumbnail_height` int(11) DEFAULT NULL,
  `thumbnail_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `thumbnail_width` int(11) DEFAULT NULL,
  `advertisement_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ADVERTISEMENT_IMAGE_ADVERTISEMENT_ID` (`advertisement_id`),
  CONSTRAINT `FK_ADVERTISEMENT_IMAGE_ADVERTISEMENT_ID` FOREIGN KEY (`advertisement_id`) REFERENCES `t_advertisement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_advertisement_image
-- ----------------------------

-- ----------------------------
-- Table structure for `t_art_moment`
-- ----------------------------
DROP TABLE IF EXISTS `t_art_moment`;
CREATE TABLE `t_art_moment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `content` varchar(400) COLLATE utf8mb4_bin DEFAULT NULL,
  `location` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `category` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `original_art_moment_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IND_T_ART_MOMENT_CATEGORY` (`category`),
  KEY `FK_ART_MOMENT_ORIGINAL_ART_MOMENT_ID` (`original_art_moment_id`),
  KEY `FK_ART_MOMENT_USER_ID` (`user_id`),
  CONSTRAINT `FK_ART_MOMENT_ORIGINAL_ART_MOMENT_ID` FOREIGN KEY (`original_art_moment_id`) REFERENCES `t_art_moment` (`id`),
  CONSTRAINT `FK_ART_MOMENT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_art_moment
-- ----------------------------

-- ----------------------------
-- Table structure for `t_art_moment_browse_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_art_moment_browse_record`;
CREATE TABLE `t_art_moment_browse_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `art_moment_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ART_MOMENT_BROWSE_RECORD_ART_MOMENT_ID` (`art_moment_id`),
  KEY `FK_ART_MOMENT_BROWSE_RECORD_USER_ID` (`user_id`),
  CONSTRAINT `FK_ART_MOMENT_BROWSE_RECORD_ART_MOMENT_ID` FOREIGN KEY (`art_moment_id`) REFERENCES `t_art_moment` (`id`),
  CONSTRAINT `FK_ART_MOMENT_BROWSE_RECORD_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_art_moment_browse_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_art_moment_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_art_moment_comment`;
CREATE TABLE `t_art_moment_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(400) COLLATE utf8mb4_bin DEFAULT NULL,
  `art_moment_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `parent_comment_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ART_MOMENT_COMMENT_ART_MOMENT_ID` (`art_moment_id`),
  KEY `FK_ART_MOMENT_COMMENT_USER_ID` (`user_id`),
  KEY `FK_ART_MOMENT_COMMENT_PARENT_COMMENT_ID` (`parent_comment_id`),
  CONSTRAINT `FK_ART_MOMENT_COMMENT_ART_MOMENT_ID` FOREIGN KEY (`art_moment_id`) REFERENCES `t_art_moment` (`id`),
  CONSTRAINT `FK_ART_MOMENT_COMMENT_PARENT_COMMENT_ID` FOREIGN KEY (`parent_comment_id`) REFERENCES `t_art_moment_comment` (`id`),
  CONSTRAINT `FK_ART_MOMENT_COMMENT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_art_moment_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `t_art_moment_forward_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_art_moment_forward_record`;
CREATE TABLE `t_art_moment_forward_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `art_moment_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ART_MOMENT_FORWARD_RECORD_ART_MOMENT_ID` (`art_moment_id`),
  KEY `FK_ART_MOMENT_FORWARD_RECORD_USER_ID` (`user_id`),
  CONSTRAINT `FK_ART_MOMENT_FORWARD_RECORD_ART_MOMENT_ID` FOREIGN KEY (`art_moment_id`) REFERENCES `t_art_moment` (`id`),
  CONSTRAINT `FK_ART_MOMENT_FORWARD_RECORD_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_art_moment_forward_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_art_moment_image`
-- ----------------------------
DROP TABLE IF EXISTS `t_art_moment_image`;
CREATE TABLE `t_art_moment_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `image_height` int(11) DEFAULT NULL,
  `image_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `image_width` int(11) DEFAULT NULL,
  `thumbnail_height` int(11) DEFAULT NULL,
  `thumbnail_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `thumbnail_width` int(11) DEFAULT NULL,
  `art_moment_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ART_MOMENT_IMAGE_ART_MOMENT_ID` (`art_moment_id`),
  CONSTRAINT `FK_ART_MOMENT_IMAGE_ART_MOMENT_ID` FOREIGN KEY (`art_moment_id`) REFERENCES `t_art_moment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_art_moment_image
-- ----------------------------

-- ----------------------------
-- Table structure for `t_art_moment_like`
-- ----------------------------
DROP TABLE IF EXISTS `t_art_moment_like`;
CREATE TABLE `t_art_moment_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `art_moment_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ART_MOMENT_LIKE_ART_MOMENT_ID` (`art_moment_id`),
  KEY `FK_ART_MOMENT_LIKE_USER_ID` (`user_id`),
  CONSTRAINT `FK_ART_MOMENT_LIKE_ART_MOMENT_ID` FOREIGN KEY (`art_moment_id`) REFERENCES `t_art_moment` (`id`),
  CONSTRAINT `FK_ART_MOMENT_LIKE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_art_moment_like
-- ----------------------------

-- ----------------------------
-- Table structure for `t_art_moment_report_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_art_moment_report_record`;
CREATE TABLE `t_art_moment_report_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `art_moment_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ART_MOMENT_REPORT_RECORD_ART_MOMENT_ID` (`art_moment_id`),
  KEY `FK_ART_MOMENT_REPORT_RECORD_USER_ID` (`user_id`),
  CONSTRAINT `FK_ART_MOMENT_REPORT_RECORD_ART_MOMENT_ID` FOREIGN KEY (`art_moment_id`) REFERENCES `t_art_moment` (`id`),
  CONSTRAINT `FK_ART_MOMENT_REPORT_RECORD_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_art_moment_report_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_bank`
-- ----------------------------
DROP TABLE IF EXISTS `t_bank`;
CREATE TABLE `t_bank` (
  `code` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_bank
-- ----------------------------

-- ----------------------------
-- Table structure for `t_express_company`
-- ----------------------------
DROP TABLE IF EXISTS `t_express_company`;
CREATE TABLE `t_express_company` (
  `code` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `short_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_express_company
-- ----------------------------

-- ----------------------------
-- Table structure for `t_information`
-- ----------------------------
DROP TABLE IF EXISTS `t_information`;
CREATE TABLE `t_information` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `author` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `content` varchar(16000) COLLATE utf8mb4_bin DEFAULT NULL,
  `source` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `title` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_information
-- ----------------------------

-- ----------------------------
-- Table structure for `t_information_browse_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_information_browse_record`;
CREATE TABLE `t_information_browse_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `information_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_INFORMATION_BROWSE_RECORD_INFORMATION_ID` (`information_id`),
  KEY `FK_INFORMATION_BROWSE_RECORD_USER_ID` (`user_id`),
  CONSTRAINT `FK_INFORMATION_BROWSE_RECORD_INFORMATION_ID` FOREIGN KEY (`information_id`) REFERENCES `t_information` (`id`),
  CONSTRAINT `FK_INFORMATION_BROWSE_RECORD_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_information_browse_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_information_image`
-- ----------------------------
DROP TABLE IF EXISTS `t_information_image`;
CREATE TABLE `t_information_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `image_height` int(11) DEFAULT NULL,
  `image_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `image_width` int(11) DEFAULT NULL,
  `thumbnail_height` int(11) DEFAULT NULL,
  `thumbnail_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `thumbnail_width` int(11) DEFAULT NULL,
  `information_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_INFORMATION_IMAGE_INFORMATION_ID` (`information_id`),
  CONSTRAINT `FK_INFORMATION_IMAGE_INFORMATION_ID` FOREIGN KEY (`information_id`) REFERENCES `t_information` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_information_image
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item`
-- ----------------------------
DROP TABLE IF EXISTS `t_item`;
CREATE TABLE `t_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `bid_price` decimal(14,2) DEFAULT NULL,
  `category` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `detail` varchar(4000) COLLATE utf8mb4_bin DEFAULT NULL,
  `end_date_time` datetime DEFAULT NULL,
  `express_fee` decimal(14,2) DEFAULT NULL,
  `fixed` bit(1) DEFAULT NULL,
  `fixed_price` decimal(14,2) DEFAULT NULL,
  `free_express` bit(1) DEFAULT NULL,
  `free_return` bit(1) DEFAULT NULL,
  `initial_price` decimal(14,2) DEFAULT NULL,
  `margin` decimal(14,2) DEFAULT NULL,
  `raise_price` decimal(14,2) DEFAULT NULL,
  `reference_price` decimal(14,2) DEFAULT NULL,
  `start_date_time` datetime DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `title` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `shop_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ITEM_SHOP_ID` (`shop_id`),
  CONSTRAINT `FK_ITEM_SHOP_ID` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_bid_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_bid_record`;
CREATE TABLE `t_item_bid_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `bid_price` decimal(14,2) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ITEM_BID_RECORD_ITEM_ID` (`item_id`),
  KEY `FK_ITEM_BID_RECORD_USER_ID` (`user_id`),
  CONSTRAINT `FK_ITEM_BID_RECORD_ITEM_ID` FOREIGN KEY (`item_id`) REFERENCES `t_item` (`id`),
  CONSTRAINT `FK_ITEM_BID_RECORD_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_bid_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_browse_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_browse_record`;
CREATE TABLE `t_item_browse_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ITEM_BROWSE_RECORD_ITEM_ID` (`item_id`),
  KEY `FK_ITEM_BROWSE_RECORD_USER_ID` (`user_id`),
  CONSTRAINT `FK_ITEM_BROWSE_RECORD_ITEM_ID` FOREIGN KEY (`item_id`) REFERENCES `t_item` (`id`),
  CONSTRAINT `FK_ITEM_BROWSE_RECORD_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_browse_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_category`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_category`;
CREATE TABLE `t_item_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `category_code` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `category_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `icon` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ITEM_CATEGORY_CATEGORY_CODE` (`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_category
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_delivery_address`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_delivery_address`;
CREATE TABLE `t_item_delivery_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `city` varchar(40) COLLATE utf8mb4_bin NOT NULL,
  `district` varchar(80) COLLATE utf8mb4_bin NOT NULL,
  `mobile` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `postcode` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `province` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `is_default` bit(1) NOT NULL,
  `remark` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ITEM_DELIVERY_ADDRESS_USER_ID` (`user_id`),
  CONSTRAINT `FK_ITEM_DELIVERY_ADDRESS_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_delivery_address
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_image`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_image`;
CREATE TABLE `t_item_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `image_height` int(11) DEFAULT NULL,
  `image_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `image_width` int(11) DEFAULT NULL,
  `thumbnail_height` int(11) DEFAULT NULL,
  `thumbnail_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `thumbnail_width` int(11) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ITEM_IMAGE_ITEM_ID` (`item_id`),
  CONSTRAINT `FK_ITEM_IMAGE_ITEM_ID` FOREIGN KEY (`item_id`) REFERENCES `t_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_image
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_margin_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_margin_order`;
CREATE TABLE `t_item_margin_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `amount` decimal(14,2) DEFAULT NULL,
  `order_number` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `payment_channel` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `title` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `category` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `payment_order_number` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ITEM_MARGIN_ORDER_ITEM_ID` (`item_id`),
  KEY `FK_ITEM_MARGIN_ORDER_USER_ID` (`user_id`),
  CONSTRAINT `FK_ITEM_MARGIN_ORDER_ITEM_ID` FOREIGN KEY (`item_id`) REFERENCES `t_item` (`id`),
  CONSTRAINT `FK_ITEM_MARGIN_ORDER_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_margin_order
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_order`;
CREATE TABLE `t_item_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `amount` decimal(14,2) DEFAULT NULL,
  `order_number` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `payment_channel` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `title` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `express_fee` decimal(14,2) DEFAULT NULL,
  `result` varchar(22) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `delivery_address_id` bigint(20) DEFAULT NULL,
  `delivery_express_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `return_address_id` bigint(20) DEFAULT NULL,
  `return_express_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ITEM_ORDER_ORDER_NUMBER` (`order_number`),
  KEY `FK_ITEM_ORDER_DELIVERY_ADDRESS_ID` (`delivery_address_id`),
  KEY `FK_ITEM_ORDER_DELIVERY_EXPRESS_ID` (`delivery_express_id`),
  KEY `FK_ITEM_ORDER_ITEM_ID` (`item_id`),
  KEY `FK_ITEM_ORDER_RETURN_ADDRESS_ID` (`return_address_id`),
  KEY `FK_ITEM_ORDER_RETURN_EXPRESS_ID` (`return_express_id`),
  KEY `FK_ITEM_ORDER_USER_ID` (`user_id`),
  CONSTRAINT `FK_ITEM_ORDER_DELIVERY_ADDRESS_ID` FOREIGN KEY (`delivery_address_id`) REFERENCES `t_item_order_delivery_address` (`id`),
  CONSTRAINT `FK_ITEM_ORDER_DELIVERY_EXPRESS_ID` FOREIGN KEY (`delivery_express_id`) REFERENCES `t_item_order_express` (`id`),
  CONSTRAINT `FK_ITEM_ORDER_ITEM_ID` FOREIGN KEY (`item_id`) REFERENCES `t_item` (`id`),
  CONSTRAINT `FK_ITEM_ORDER_RETURN_ADDRESS_ID` FOREIGN KEY (`return_address_id`) REFERENCES `t_item_order_return_address` (`id`),
  CONSTRAINT `FK_ITEM_ORDER_RETURN_EXPRESS_ID` FOREIGN KEY (`return_express_id`) REFERENCES `t_item_order_express` (`id`),
  CONSTRAINT `FK_ITEM_ORDER_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_order
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_order_delivery_address`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_order_delivery_address`;
CREATE TABLE `t_item_order_delivery_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `city` varchar(40) COLLATE utf8mb4_bin NOT NULL,
  `district` varchar(80) COLLATE utf8mb4_bin NOT NULL,
  `mobile` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `postcode` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `province` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `is_default` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_order_delivery_address
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_order_express`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_order_express`;
CREATE TABLE `t_item_order_express` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `express_company_code` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `express_number` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `status` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_order_express
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_order_return_address`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_order_return_address`;
CREATE TABLE `t_item_order_return_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `city` varchar(40) COLLATE utf8mb4_bin NOT NULL,
  `district` varchar(80) COLLATE utf8mb4_bin NOT NULL,
  `mobile` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `postcode` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `province` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `is_default` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_order_return_address
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_payment_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_payment_order`;
CREATE TABLE `t_item_payment_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `amount` decimal(14,2) DEFAULT NULL,
  `order_number` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `payment_channel` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `title` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `category` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `payment_order_number` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `item_order_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ITEM_PAYMENT_ORDER_ITEM_ORDER_ID` (`item_order_id`),
  KEY `FK_ITEM_PAYMENT_ORDER_USER_ID` (`user_id`),
  CONSTRAINT `FK_ITEM_PAYMENT_ORDER_ITEM_ORDER_ID` FOREIGN KEY (`item_order_id`) REFERENCES `t_item_order` (`id`),
  CONSTRAINT `FK_ITEM_PAYMENT_ORDER_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_payment_order
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_return_address`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_return_address`;
CREATE TABLE `t_item_return_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `city` varchar(40) COLLATE utf8mb4_bin NOT NULL,
  `district` varchar(80) COLLATE utf8mb4_bin NOT NULL,
  `mobile` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `postcode` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `province` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `is_default` bit(1) NOT NULL,
  `remark` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ITEM_RETURN_ADDRESS_USER_ID` (`user_id`),
  CONSTRAINT `FK_ITEM_RETURN_ADDRESS_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_return_address
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_sub_category`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_sub_category`;
CREATE TABLE `t_item_sub_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `category_code` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `category_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `icon` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `parent_item_category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ITEM_SUB_CATEGORY_CATEGORY_CODE` (`category_code`),
  KEY `FK_ITEM_CATEGORY_PARENT_ITEM_CATEGORY_ID` (`parent_item_category_id`),
  CONSTRAINT `FK_ITEM_CATEGORY_PARENT_ITEM_CATEGORY_ID` FOREIGN KEY (`parent_item_category_id`) REFERENCES `t_item_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_item_sub_category
-- ----------------------------

-- ----------------------------
-- Table structure for `t_merchant`
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant`;
CREATE TABLE `t_merchant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `category` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `district` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `identity_number` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `real_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `telephone_number` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MERCHANT_USER_ID` (`user_id`),
  CONSTRAINT `FK_MERCHANT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_merchant
-- ----------------------------

-- ----------------------------
-- Table structure for `t_merchant_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_account`;
CREATE TABLE `t_merchant_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `amount` decimal(14,2) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MERCHANT_ACCOUNT_USER_ID` (`user_id`),
  CONSTRAINT `FK_MERCHANT_ACCOUNT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_merchant_account
-- ----------------------------

-- ----------------------------
-- Table structure for `t_merchant_account_bill`
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_account_bill`;
CREATE TABLE `t_merchant_account_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `category` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `item_order_id` bigint(20) DEFAULT NULL,
  `margin_payment_order_id` bigint(20) DEFAULT NULL,
  `payment_order_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `withdrawal_balance_payment_order_id` bigint(20) DEFAULT NULL,
  `withdrawal_margin_payment_order_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MERCHANT_ACCOUNT_BILL_ITEM_ORDER_ID` (`item_order_id`),
  KEY `FK_MERCHANT_ACCOUNT_BILL_MARGIN_PAYMENT_ORDER_ID` (`margin_payment_order_id`),
  KEY `FK_MERCHANT_ACCOUNT_BILL_PAYMENT_ORDER_ID` (`payment_order_id`),
  KEY `FK_MERCHANT_ACCOUNT_BILL_USER_ID` (`user_id`),
  KEY `FK_MERCHANT_ACCOUNT_BILL_WITHDRAWAL_BALANCE_PAYMENT_ORDER_ID` (`withdrawal_balance_payment_order_id`),
  KEY `FK_MERCHANT_ACCOUNT_BILL_WITHDRAWAL_MARGIN_PAYMENT_ORDER_ID` (`withdrawal_margin_payment_order_id`),
  CONSTRAINT `FK_MERCHANT_ACCOUNT_BILL_ITEM_ORDER_ID` FOREIGN KEY (`item_order_id`) REFERENCES `t_item_order` (`id`),
  CONSTRAINT `FK_MERCHANT_ACCOUNT_BILL_MARGIN_PAYMENT_ORDER_ID` FOREIGN KEY (`margin_payment_order_id`) REFERENCES `t_merchant_margin` (`id`),
  CONSTRAINT `FK_MERCHANT_ACCOUNT_BILL_PAYMENT_ORDER_ID` FOREIGN KEY (`payment_order_id`) REFERENCES `t_item_payment_order` (`id`),
  CONSTRAINT `FK_MERCHANT_ACCOUNT_BILL_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_MERCHANT_ACCOUNT_BILL_WITHDRAWAL_BALANCE_PAYMENT_ORDER_ID` FOREIGN KEY (`withdrawal_balance_payment_order_id`) REFERENCES `t_withdrawal_balance_payment_order` (`id`),
  CONSTRAINT `FK_MERCHANT_ACCOUNT_BILL_WITHDRAWAL_MARGIN_PAYMENT_ORDER_ID` FOREIGN KEY (`withdrawal_margin_payment_order_id`) REFERENCES `t_withdrawal_margin_payment_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_merchant_account_bill
-- ----------------------------

-- ----------------------------
-- Table structure for `t_merchant_bank_card`
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_bank_card`;
CREATE TABLE `t_merchant_bank_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `bank_account` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `bank_code` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `bank_name` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `category` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `is_proceeds` bit(1) DEFAULT NULL,
  `mobile` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `real_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MERCHANT_BANK_CARD_USER_ID` (`user_id`),
  CONSTRAINT `FK_MERCHANT_BANK_CARD_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_merchant_bank_card
-- ----------------------------

-- ----------------------------
-- Table structure for `t_merchant_image`
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_image`;
CREATE TABLE `t_merchant_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `image_height` int(11) DEFAULT NULL,
  `image_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `image_width` int(11) DEFAULT NULL,
  `thumbnail_height` int(11) DEFAULT NULL,
  `thumbnail_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `thumbnail_width` int(11) DEFAULT NULL,
  `enterprise_merchant_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MERCHANT_IMAGE_MERCHANT_ID` (`enterprise_merchant_id`),
  CONSTRAINT `FK_MERCHANT_IMAGE_MERCHANT_ID` FOREIGN KEY (`enterprise_merchant_id`) REFERENCES `t_merchant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_merchant_image
-- ----------------------------

-- ----------------------------
-- Table structure for `t_merchant_margin`
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_margin`;
CREATE TABLE `t_merchant_margin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `merchant_margin` decimal(14,2) DEFAULT NULL,
  `user_margin` decimal(14,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_merchant_margin
-- ----------------------------

-- ----------------------------
-- Table structure for `t_merchant_margin_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_margin_account`;
CREATE TABLE `t_merchant_margin_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `amount` decimal(14,2) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MERCHANT_MARGIN_ACCOUNT_USER_ID` (`user_id`),
  CONSTRAINT `FK_MERCHANT_MARGIN_ACCOUNT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_merchant_margin_account
-- ----------------------------

-- ----------------------------
-- Table structure for `t_merchant_margin_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_margin_order`;
CREATE TABLE `t_merchant_margin_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `amount` decimal(14,2) DEFAULT NULL,
  `order_number` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `payment_channel` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `title` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `category` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `payment_order_number` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `amount_paid` decimal(14,2) DEFAULT NULL,
  `amount_payable` decimal(14,2) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_MERCHANT_MARGIN_ORDER_ORDER_NUMBER` (`order_number`),
  KEY `FK_MERCHANT_MARGIN_ORDER_USER_ID` (`user_id`),
  CONSTRAINT `FK_MERCHANT_MARGIN_ORDER_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_merchant_margin_order
-- ----------------------------

-- ----------------------------
-- Table structure for `t_merchant_review_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_review_record`;
CREATE TABLE `t_merchant_review_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `category` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `district` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `identity_number` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `real_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `telephone_number` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `reviewer_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MERCHANT_REVIEW_RECORD_REVIEWER_ID` (`reviewer_id`),
  KEY `FK_MERCHANT_REVIEW_RECORD_USER_ID` (`user_id`),
  CONSTRAINT `FK_MERCHANT_REVIEW_RECORD_REVIEWER_ID` FOREIGN KEY (`reviewer_id`) REFERENCES `t_administrator` (`id`),
  CONSTRAINT `FK_MERCHANT_REVIEW_RECORD_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_merchant_review_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_my_block`
-- ----------------------------
DROP TABLE IF EXISTS `t_my_block`;
CREATE TABLE `t_my_block` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `block_user_id` bigint(20) DEFAULT NULL,
  `i_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MY_BLOCK_BLOCK_USER_ID` (`block_user_id`),
  KEY `FK_MY_BLOCK_I_USER_ID` (`i_user_id`),
  CONSTRAINT `FK_MY_BLOCK_BLOCK_USER_ID` FOREIGN KEY (`block_user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_MY_BLOCK_I_USER_ID` FOREIGN KEY (`i_user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_my_block
-- ----------------------------

-- ----------------------------
-- Table structure for `t_my_fans`
-- ----------------------------
DROP TABLE IF EXISTS `t_my_fans`;
CREATE TABLE `t_my_fans` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `fans_user_id` bigint(20) DEFAULT NULL,
  `i_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MY_FANS_FANS_USER_ID` (`fans_user_id`),
  KEY `FK_MY_FANS_I_USER_ID` (`i_user_id`),
  CONSTRAINT `FK_MY_FANS_FANS_USER_ID` FOREIGN KEY (`fans_user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_MY_FANS_I_USER_ID` FOREIGN KEY (`i_user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_my_fans
-- ----------------------------

-- ----------------------------
-- Table structure for `t_my_favorite_art_moment`
-- ----------------------------
DROP TABLE IF EXISTS `t_my_favorite_art_moment`;
CREATE TABLE `t_my_favorite_art_moment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `art_moment_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MY_FAVORITE_ART_MOMENT_ART_MOMENT_ID` (`art_moment_id`),
  KEY `FK_MY_FAVORITE_ART_MOMENT_USER_ID` (`user_id`),
  CONSTRAINT `FK_MY_FAVORITE_ART_MOMENT_ART_MOMENT_ID` FOREIGN KEY (`art_moment_id`) REFERENCES `t_art_moment` (`id`),
  CONSTRAINT `FK_MY_FAVORITE_ART_MOMENT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_my_favorite_art_moment
-- ----------------------------

-- ----------------------------
-- Table structure for `t_my_favorite_item`
-- ----------------------------
DROP TABLE IF EXISTS `t_my_favorite_item`;
CREATE TABLE `t_my_favorite_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MY_FAVORITE_ITEM_ITEM_ID` (`item_id`),
  KEY `FK_MY_FAVORITE_ITEM_USER_ID` (`user_id`),
  CONSTRAINT `FK_MY_FAVORITE_ITEM_ITEM_ID` FOREIGN KEY (`item_id`) REFERENCES `t_item` (`id`),
  CONSTRAINT `FK_MY_FAVORITE_ITEM_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_my_favorite_item
-- ----------------------------

-- ----------------------------
-- Table structure for `t_my_favorite_shop`
-- ----------------------------
DROP TABLE IF EXISTS `t_my_favorite_shop`;
CREATE TABLE `t_my_favorite_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `shop_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MY_FAVORITE_SHOP_SHOP_ID` (`shop_id`),
  KEY `FK_MY_FAVORITE_SHOP_USER_ID` (`user_id`),
  CONSTRAINT `FK_MY_FAVORITE_SHOP_SHOP_ID` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`id`),
  CONSTRAINT `FK_MY_FAVORITE_SHOP_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_my_favorite_shop
-- ----------------------------

-- ----------------------------
-- Table structure for `t_my_follow`
-- ----------------------------
DROP TABLE IF EXISTS `t_my_follow`;
CREATE TABLE `t_my_follow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `follow_user_id` bigint(20) DEFAULT NULL,
  `i_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MY_FOLLOW_FOLLOW_USER_ID` (`follow_user_id`),
  KEY `FK_MY_FOLLOW_I_USER_ID` (`i_user_id`),
  CONSTRAINT `FK_MY_FOLLOW_FOLLOW_USER_ID` FOREIGN KEY (`follow_user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_MY_FOLLOW_I_USER_ID` FOREIGN KEY (`i_user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_my_follow
-- ----------------------------

-- ----------------------------
-- Table structure for `t_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `http_method` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `uri` varchar(300) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `t_real_name`
-- ----------------------------
DROP TABLE IF EXISTS `t_real_name`;
CREATE TABLE `t_real_name` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `identity_number` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `real_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `reviewer_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_REAL_NAME__REVIEWER_ID` (`reviewer_id`),
  KEY `FK_REAL_NAME_USER_ID` (`user_id`),
  CONSTRAINT `FK_REAL_NAME_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_REAL_NAME__REVIEWER_ID` FOREIGN KEY (`reviewer_id`) REFERENCES `t_administrator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_real_name
-- ----------------------------

-- ----------------------------
-- Table structure for `t_real_name_image`
-- ----------------------------
DROP TABLE IF EXISTS `t_real_name_image`;
CREATE TABLE `t_real_name_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `image_height` int(11) DEFAULT NULL,
  `image_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `image_width` int(11) DEFAULT NULL,
  `thumbnail_height` int(11) DEFAULT NULL,
  `thumbnail_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `thumbnail_width` int(11) DEFAULT NULL,
  `real_name_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_REAL_NAME_IMAGE_REAL_NAME_ID` (`real_name_id`),
  CONSTRAINT `FK_REAL_NAME_IMAGE_REAL_NAME_ID` FOREIGN KEY (`real_name_id`) REFERENCES `t_real_name` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_real_name_image
-- ----------------------------

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_role
-- ----------------------------

-- ----------------------------
-- Table structure for `t_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_ROLE_PERMISSION_ID` (`role_id`,`permission_id`),
  KEY `FK_ROLE_PERMISSION_PERMISSION_ID` (`permission_id`),
  CONSTRAINT `FK_ROLE_PERMISSION_PERMISSION_ID` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`),
  CONSTRAINT `FK_ROLE_PERMISSION_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `t_rong_cloud_token`
-- ----------------------------
DROP TABLE IF EXISTS `t_rong_cloud_token`;
CREATE TABLE `t_rong_cloud_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_RONG_CLOUD_TOKEN_USER_ID` (`user_id`),
  CONSTRAINT `FK_RONG_CLOUD_TOKEN_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_rong_cloud_token
-- ----------------------------

-- ----------------------------
-- Table structure for `t_scheduler_job`
-- ----------------------------
DROP TABLE IF EXISTS `t_scheduler_job`;
CREATE TABLE `t_scheduler_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `cron_expression` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `description` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `job_class_name` varchar(250) COLLATE utf8mb4_bin DEFAULT NULL,
  `job_group` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `job_name` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_SCHEDULER_JOB_JOB_NAME` (`job_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_scheduler_job
-- ----------------------------

-- ----------------------------
-- Table structure for `t_scheduler_job_execute_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_scheduler_job_execute_record`;
CREATE TABLE `t_scheduler_job_execute_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `scheduler_job_id` bigint(20) DEFAULT NULL,
  `scheduler_job_name` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_scheduler_job_execute_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_shop`
-- ----------------------------
DROP TABLE IF EXISTS `t_shop`;
CREATE TABLE `t_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `avatar_file_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `serial_number` varchar(10) COLLATE utf8mb4_bin NOT NULL,
  `merchant_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USER_SERIAL_NUMBER` (`serial_number`),
  UNIQUE KEY `UK_SHOP_NAME` (`name`),
  KEY `FK_SHOP_MERCHANT_ID` (`merchant_id`),
  KEY `FK_SHOP_USER_ID` (`user_id`),
  CONSTRAINT `FK_SHOP_MERCHANT_ID` FOREIGN KEY (`merchant_id`) REFERENCES `t_merchant` (`id`),
  CONSTRAINT `FK_SHOP_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_shop
-- ----------------------------

-- ----------------------------
-- Table structure for `t_suggestion`
-- ----------------------------
DROP TABLE IF EXISTS `t_suggestion`;
CREATE TABLE `t_suggestion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `content` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_suggestion
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `mobile` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `register_ip` varchar(128) COLLATE utf8mb4_bin NOT NULL,
  `serial_number` varchar(10) COLLATE utf8mb4_bin NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USER_USERNAME` (`username`),
  UNIQUE KEY `UK_USER_SERIAL_NUMBER` (`serial_number`),
  UNIQUE KEY `UK_USER_MOBILE` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_user
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_profile`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_profile`;
CREATE TABLE `t_user_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `avatar` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `avatar_3x` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `identity_number` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `nickname` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `real_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `sex` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IND_T_USER_PROFILE_NICKNAME` (`nickname`),
  KEY `FK_USER_PROFILE_USER_ID` (`user_id`),
  CONSTRAINT `FK_USER_PROFILE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_user_profile
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_USER_ROLE_ID` (`user_id`,`role_id`),
  KEY `FK_USER_ROLE_ROLE_ID` (`role_id`),
  CONSTRAINT `FK_USER_ROLE_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FK_USER_ROLE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_show_image`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_show_image`;
CREATE TABLE `t_user_show_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `image_height` int(11) DEFAULT NULL,
  `image_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `image_width` int(11) DEFAULT NULL,
  `thumbnail_height` int(11) DEFAULT NULL,
  `thumbnail_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `thumbnail_width` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_SHOW_IMAGE_USER_ID` (`user_id`),
  CONSTRAINT `FK_USER_SHOW_IMAGE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_user_show_image
-- ----------------------------

-- ----------------------------
-- Table structure for `t_withdrawal_balance_payment_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_withdrawal_balance_payment_order`;
CREATE TABLE `t_withdrawal_balance_payment_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `amount` decimal(14,2) DEFAULT NULL,
  `order_number` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `payment_channel` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `title` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `category` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `payment_order_number` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `availableAmount` decimal(14,2) DEFAULT NULL,
  `charge` decimal(14,2) DEFAULT NULL,
  `chargeRate` decimal(7,4) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `bank_card_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_WITHDRAWAL_BALANCE_PAYMENT_ORDER_ORDER_NUMBER` (`order_number`),
  KEY `FK_WITHDRAWAL_BALANCE_PAYMENT_ORDER_USER_ID` (`user_id`),
  KEY `FK_WITHDRAWAL_BALANCE_PAYMENT_ORDER_BANK_CARD_ID` (`bank_card_id`),
  CONSTRAINT `FK_WITHDRAWAL_BALANCE_PAYMENT_ORDER_BANK_CARD_ID` FOREIGN KEY (`bank_card_id`) REFERENCES `t_merchant_bank_card` (`id`),
  CONSTRAINT `FK_WITHDRAWAL_BALANCE_PAYMENT_ORDER_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_withdrawal_balance_payment_order
-- ----------------------------

-- ----------------------------
-- Table structure for `t_withdrawal_margin_payment_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_withdrawal_margin_payment_order`;
CREATE TABLE `t_withdrawal_margin_payment_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `amount` decimal(14,2) DEFAULT NULL,
  `order_number` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `payment_channel` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `title` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `category` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `payment_order_number` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `availableAmount` decimal(14,2) DEFAULT NULL,
  `charge` decimal(14,2) DEFAULT NULL,
  `chargeRate` decimal(7,4) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `bank_card_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_WITHDRAWAL_MARGIN_PAYMENT_ORDER_ORDER_NUMBER` (`order_number`),
  KEY `FK_WITHDRAWAL_MARGIN_PAYMENT_ORDER_USER_ID` (`user_id`),
  KEY `FK_WITHDRAWAL_MARGIN_PAYMENT_ORDER_BANK_CARD_ID` (`bank_card_id`),
  CONSTRAINT `FK_WITHDRAWAL_MARGIN_PAYMENT_ORDER_BANK_CARD_ID` FOREIGN KEY (`bank_card_id`) REFERENCES `t_merchant_bank_card` (`id`),
  CONSTRAINT `FK_WITHDRAWAL_MARGIN_PAYMENT_ORDER_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_withdrawal_margin_payment_order
-- ----------------------------
