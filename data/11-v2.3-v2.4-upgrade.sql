-- ----------------------------
-- 注意：本文件存放的是“2.3升级到2.4版本”新增数据的语句！
-- ----------------------------

-- “t_user_reward_order”增加字段“receiver_account_balance”
ALTER TABLE `t_user_reward_order` ADD COLUMN `receiver_account_balance` decimal(14,2) NULL AFTER `sender_user_id`;

-- ----------------------------
-- Table structure for `t_user_alipay_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_alipay_account`;
CREATE TABLE `t_user_alipay_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `account` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `is_default` bit(1) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_ALIPAY_ACCOUNT_USER_ID` (`user_id`),
  CONSTRAINT `FK_USER_ALIPAY_ACCOUNT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_withdrawal_user_account_balance_payment_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_withdrawal_user_account_balance_payment_order`;
CREATE TABLE `t_withdrawal_user_account_balance_payment_order` (
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
  `user_account_balance` decimal(14,2) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `user_alipay_account_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_WITHDRAWAL_USER_ACCOUNT_BALANCE_ORDER_NUMBER` (`order_number`),
  KEY `FK_WITHDRAWAL_USER_ACCOUNT_BALANCE_PAYMENT_ORDER_USER_ID` (`user_id`),
  KEY `FK_WITHDRAWAL_USER_ACCOUNT_BALANCE_USER_ALIPAY_ACCOUNT_ID` (`user_alipay_account_id`),
  CONSTRAINT `FK_WITHDRAWAL_USER_ACCOUNT_BALANCE_PAYMENT_ORDER_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_WITHDRAWAL_USER_ACCOUNT_BALANCE_USER_ALIPAY_ACCOUNT_ID` FOREIGN KEY (`user_alipay_account_id`) REFERENCES `t_user_alipay_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_user_account_bill`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_account_bill`;
CREATE TABLE `t_user_account_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `category` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `user_reward_order_id` bigint(20) DEFAULT NULL,
  `withdrawal_user_account_balance_payment_order_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_ACCOUNT_BILL_USER_ID` (`user_id`),
  KEY `FK_USER_ACCOUNT_BILL_USER_REWARD_ID` (`user_reward_order_id`),
  KEY `FK_USER_ACCOUNT_BILL_WITHDRAWAL_BALANCE_ORDER_ID` (`withdrawal_user_account_balance_payment_order_id`),
  CONSTRAINT `FK_USER_ACCOUNT_BILL_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_USER_ACCOUNT_BILL_USER_REWARD_ID` FOREIGN KEY (`user_reward_order_id`) REFERENCES `t_user_reward_order` (`id`),
  CONSTRAINT `FK_USER_ACCOUNT_BILL_WITHDRAWAL_BALANCE_ORDER_ID` FOREIGN KEY (`withdrawal_user_account_balance_payment_order_id`) REFERENCES `t_withdrawal_user_account_balance_payment_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_user_login_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_login_record`;
CREATE TABLE `t_user_login_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `app_version` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `device` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `device_os` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `ip` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL,
  `latitude` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `longitude` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `oauth_channel` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `oauth_id` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_agent` varchar(300) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

ALTER TABLE `t_information_message` DROP FOREIGN KEY `FK_INFORMATION_MESSAGE_USER_ID`;
ALTER TABLE `t_information_message` DROP COLUMN `user_id`;
ALTER TABLE `t_information_message` ADD COLUMN `user_id` bigint(20) NULL AFTER `is_read`;

ALTER TABLE `t_art_moment_message` DROP FOREIGN KEY `FK_ART_MOMENT_MESSAGE_USER_ID`;
ALTER TABLE `t_art_moment_message` DROP COLUMN `user_id`;
ALTER TABLE `t_art_moment_message` ADD COLUMN `user_id` bigint(20) NULL AFTER `is_read`;

ALTER TABLE `t_information_comment_like` ADD COLUMN `status` varchar(10) NULL AFTER `user_id`;
ALTER TABLE `t_information_comment_like` ADD COLUMN `is_send_message` bit(1) NULL AFTER `status`;

ALTER TABLE `t_information_like` ADD COLUMN `status` varchar(10) NULL AFTER `user_id`;
ALTER TABLE `t_information_like` ADD COLUMN `is_send_message` bit(1) NULL AFTER `status`;

ALTER TABLE `t_art_moment_like` ADD COLUMN `status` varchar(10) NULL AFTER `user_id`;
ALTER TABLE `t_art_moment_like` ADD COLUMN `is_send_message` bit(1) NULL AFTER `status`;

UPDATE `t_information_comment_like` SET `is_send_message` = false;
UPDATE `t_information_like` SET `is_send_message` = false;
UPDATE `t_art_moment_like` SET `is_send_message` = false;

UPDATE `t_information_comment_like` SET `status` = 'GIVEN';
UPDATE `t_information_like` SET `status` = 'GIVEN';
UPDATE `t_art_moment_like` SET `status` = 'GIVEN';

-- ----------------------------
-- Table structure for `t_art_moment_comment_like`
-- ----------------------------
DROP TABLE IF EXISTS `t_art_moment_comment_like`;
CREATE TABLE `t_art_moment_comment_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_send_message` bit(1) DEFAULT NULL,
  `status` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `comment_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ART_MOMENT_COMMENT_LIKE_COMMENT_ID` (`comment_id`),
  KEY `FK_ART_MOMENT_COMMENT_LIKE_USER_ID` (`user_id`),
  CONSTRAINT `FK_ART_MOMENT_COMMENT_LIKE_COMMENT_ID` FOREIGN KEY (`comment_id`) REFERENCES `t_art_moment_comment` (`id`),
  CONSTRAINT `FK_ART_MOMENT_COMMENT_LIKE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- “绑定手机”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.mobile.bind', '/api/i/mobile', 'PUT', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.mobile.bind'));
-- “我的钱包”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.wallet', '/api/i/wallet', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.wallet'));
-- “个人账户的账单列表”
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.bills', '/api/i/bills', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.bills'));
-- “个人账户的账单明细”
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.bills.id', '/api/i/bills/*', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.bills.id'));
-- “评论艺文点赞”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.art.moment.id.comments.id.give.like', '/api/art-moments/*/comments/*/likes', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.art.moment.id.comments.id.give.like'));
-- “提现申请准备”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.withdrawal.balance.request.prepare', '/api/i/withdrawal-balance-orders/prepare', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.withdrawal.balance.request.prepare'));
-- “提现申请”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.withdrawal.balance.request', '/api/i/withdrawal-balance-orders', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.withdrawal.balance.request'));

-- “2.4版本”信息的初始化数据
INSERT INTO `t_version` (`version`, `create_date_time`, `is_upgrade`) VALUES ('v2.4', '2017-02-24', 1);
-- 2.4版本的任务
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', ' API-支持艺文的删除功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/30', (SELECT id FROM t_version WHERE version = 'v2.4'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('IMPROVEMENT', '增加“用户登录记录”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/40', (SELECT id FROM t_version WHERE version = 'v2.4'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '研究消息推送', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/64', (SELECT id FROM t_version WHERE version = 'v2.4'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“换绑手机”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/66', (SELECT id FROM t_version WHERE version = 'v2.4'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', 'API-支持获取“我的钱包”数据', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/67', (SELECT id FROM t_version WHERE version = 'v2.4'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', 'API-支持获取“我的账单明细”数据', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/68', (SELECT id FROM t_version WHERE version = 'v2.4'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '实现用户对“资讯”进行操作时对“资讯”作者发送消息提示', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/69', (SELECT id FROM t_version WHERE version = 'v2.4'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '实现用户对“艺文”进行操作时对“艺文”作者发送消息提示', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/70', (SELECT id FROM t_version WHERE version = 'v2.4'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', 'API-支持“对艺文的评论点赞”功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/71', (SELECT id FROM t_version WHERE version = 'v2.4'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', 'API-支持用户从“个人账户”中提现功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/73', (SELECT id FROM t_version WHERE version = 'v2.4'));

