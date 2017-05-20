-- ----------------------------
-- 注意：本文件存放的是“2.2升级到2.3版本”新增数据的语句！
-- ----------------------------

-- 移除对字段“password”和“username”的“不能为空”的约束
ALTER TABLE `t_user`
MODIFY COLUMN `password`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL AFTER `serial_number`,
MODIFY COLUMN `username`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL AFTER `password`;

-- “艺文”增加“status”字段，并做对应的初始化数据
ALTER TABLE `t_art_moment` ADD COLUMN `status`  varchar(20) NULL AFTER `user_id`;
UPDATE `t_art_moment` SET `status` = 'AVAILABLE';

-- ----------------------------
-- Table structure for `t_art_moment_secondary_tag`
-- ----------------------------
DROP TABLE IF EXISTS `t_art_moment_secondary_tag`;
CREATE TABLE `t_art_moment_secondary_tag` (
  `art_moment_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_ART_MOMENT_SECONDARY_TAG_ID` (`art_moment_id`,`tag_id`),
  KEY `FK_ART_MOMENT_SECONDARY_TAG_TAG_ID` (`tag_id`),
  CONSTRAINT `FK_ART_MOMENT_SECONDARY_TAG_ART_MOMENT_ID` FOREIGN KEY (`art_moment_id`) REFERENCES `t_art_moment` (`id`),
  CONSTRAINT `FK_ART_MOMENT_SECONDARY_TAG_TAG_ID` FOREIGN KEY (`tag_id`) REFERENCES `t_tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

ALTER TABLE `t_art_moment` ADD COLUMN `primary_tag_id`  bigint(20) NULL AFTER `status`;
ALTER TABLE `t_art_moment` ADD CONSTRAINT `FK_ART_MOMENT_PRIMARY_TAG_ID` FOREIGN KEY (`primary_tag_id`) REFERENCES `t_tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- ----------------------------
-- Table structure for `t_art_moment_message`
-- ----------------------------
DROP TABLE IF EXISTS `t_art_moment_message`;
CREATE TABLE `t_art_moment_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `category` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `content_id` bigint(20) DEFAULT NULL,
  `is_read` bit(1) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IND_T_ART_MOMENT_MESSAGE_CATEGORY` (`category`),
  KEY `FK_ART_MOMENT_MESSAGE_USER_ID` (`user_id`),
  CONSTRAINT `FK_ART_MOMENT_MESSAGE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_information_message`
-- ----------------------------
DROP TABLE IF EXISTS `t_information_message`;
CREATE TABLE `t_information_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `category` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `content_id` bigint(20) DEFAULT NULL,
  `is_read` bit(1) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IND_T_INFORMATION_MESSAGE_CATEGORY` (`category`),
  KEY `FK_INFORMATION_MESSAGE_USER_ID` (`user_id`),
  CONSTRAINT `FK_INFORMATION_MESSAGE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_user_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_account`;
CREATE TABLE `t_user_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `amount` decimal(14,2) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_ACCOUNT_USER_ID` (`user_id`),
  CONSTRAINT `FK_USER_ACCOUNT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_user_reward_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_reward_order`;
CREATE TABLE `t_user_reward_order` (
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
  `leave_message` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `art_moment_id` bigint(20) DEFAULT NULL,
  `receiver_user_id` bigint(20) DEFAULT NULL,
  `sender_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_REWARD_ORDER_ART_MOMENT_ID` (`art_moment_id`),
  KEY `FK_USER_REWARD_ORDER_RECEIVER_USER_ID` (`receiver_user_id`),
  KEY `FK_USER_REWARD_ORDER_SENDER_USER_ID` (`sender_user_id`),
  CONSTRAINT `FK_USER_REWARD_ORDER_ART_MOMENT_ID` FOREIGN KEY (`art_moment_id`) REFERENCES `t_art_moment` (`id`),
  CONSTRAINT `FK_USER_REWARD_ORDER_RECEIVER_USER_ID` FOREIGN KEY (`receiver_user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_USER_REWARD_ORDER_SENDER_USER_ID` FOREIGN KEY (`sender_user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- “删除艺文”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.art.moments.id.delete', '/api/art-moments/*', 'DELETE', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.art.moments.id.delete'));
-- “大咖列表”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.art.big.shots', '/api/art-big-shots', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.art.big.shots'));
-- “我关注的用户发布的艺文”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.follows.art.moments', '/api/i/follows/art-moments', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.follows.art.moments'));
-- “艺文的未读消息”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.art.moment.messages', '/api/art-moment-messages', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.art.moment.messages'));
-- “资讯的未读消息”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.information.messages', '/api/information-messages', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.information.messages'));
-- “薪赏列表”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.art.moments.id.rewards', '/api/art-moments/*/rewards', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.art.moments.id.rewards'));

-- “2.3版本”信息的初始化数据
INSERT INTO `t_version` (`version`, `create_date_time`, `is_upgrade`) VALUES ('v2.3', '2017-02-07', 1);
-- 2.3版本的任务
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('IMPROVEMENT', '移除对“资讯封面”的水印添加功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/36', (SELECT id FROM t_version WHERE version = 'v2.3'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“删除艺文”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/58', (SELECT id FROM t_version WHERE version = 'v2.3'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', ' “艺文”增加“标签”的支持', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/59', (SELECT id FROM t_version WHERE version = 'v2.3'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“我关注的用户的艺文”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/60', (SELECT id FROM t_version WHERE version = 'v2.3'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '设计新功能“我的消息”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/61', (SELECT id FROM t_version WHERE version = 'v2.3'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '设计新功能“薪赏”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/62', (SELECT id FROM t_version WHERE version = 'v2.3'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“大咖列表”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/63', (SELECT id FROM t_version WHERE version = 'v2.3'));

