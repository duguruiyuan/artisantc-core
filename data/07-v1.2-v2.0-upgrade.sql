-- ----------------------------
-- 注意：本文件存放的是“1.2升级到2.0版本”新增数据的语句！
-- ----------------------------

-- 将“t_user_profile”的“nickname”字段增加唯一索引
ALTER TABLE `t_user_profile` ADD UNIQUE INDEX `UK_USER_PROFILE_NICKNAME` (`nickname`) USING BTREE ;
-- 将“t_user_profile”的“nickname”字段增加不能为空的约束
ALTER TABLE `t_user_profile` MODIFY COLUMN `nickname`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL AFTER `identity_number`;

-- ----------------------------
-- Table structure for `t_oauth2`
-- ----------------------------
DROP TABLE IF EXISTS `t_oauth2`;
CREATE TABLE `t_oauth2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `oauth_access_token` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `oauth_channel` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `oauth_id` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_OAUTH2_USER_ID` (`user_id`),
  CONSTRAINT `FK_OAUTH2_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_version`
-- ----------------------------
DROP TABLE IF EXISTS `t_version`;
CREATE TABLE `t_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `is_upgrade` bit(1) DEFAULT NULL,
  `version` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_VERSION_VERSION` (`version`),
  KEY `IND_T_VERSION_VERSION` (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_version_issue`
-- ----------------------------
DROP TABLE IF EXISTS `t_version_issue`;
CREATE TABLE `t_version_issue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `content` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `link` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `version_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_VERSION_CONTENT_VERSION_ID` (`version_id`),
  CONSTRAINT `FK_VERSION_CONTENT_VERSION_ID` FOREIGN KEY (`version_id`) REFERENCES `t_version` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- “版本”权限的初始化数据
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.versions.v2.upgrade', '/administrator/versions/**', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.versions', '/administrator/versions', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.versions.v2.upgrade'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.versions'));

-- “版本”信息的初始化数据
INSERT INTO `t_version` (`version`, `create_date_time`, `is_upgrade`) VALUES ('v1.0', '2016-12-15', 1);
INSERT INTO `t_version` (`version`, `create_date_time`, `is_upgrade`) VALUES ('v1.1', '2016-12-19', 1);
INSERT INTO `t_version` (`version`, `create_date_time`, `is_upgrade`) VALUES ('v1.2', '2016-12-22', 1);
INSERT INTO `t_version` (`version`, `create_date_time`, `is_upgrade`) VALUES ('v2.0', '2016-12-28', 0);

-- 1.0版本的任务
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '1.0正式版。', '', (SELECT id FROM t_version WHERE version = 'v1.0'));
-- 1.1版本的任务
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '管理端-页面的图片加载随着鼠标滚动进行', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/2', (SELECT id FROM t_version WHERE version = 'v1.1'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '管理端-管理员修改密码', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/3', (SELECT id FROM t_version WHERE version = 'v1.1'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '管理端-查看“意见反馈”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/4', (SELECT id FROM t_version WHERE version = 'v1.1'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '管理端-定时任务机制的建立', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/5', (SELECT id FROM t_version WHERE version = 'v1.1'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '管理端-提供“支付”、“退款”订单的列表功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/13', (SELECT id FROM t_version WHERE version = 'v1.1'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '管理端-增加监控系统环境数据功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/15', (SELECT id FROM t_version WHERE version = 'v1.1'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '管理端-“资讯”功能改善。', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/17', (SELECT id FROM t_version WHERE version = 'v1.1'));
-- 1.2版本的任务
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', 'API-支持通过扫描“二维码”来“添加好友”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/29', (SELECT id FROM t_version WHERE version = 'v1.2'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '“资讯”开放“作者”属性', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/34', (SELECT id FROM t_version WHERE version = 'v1.2'));
-- 2.0版本的任务
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '增加https协议', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/25', (SELECT id FROM t_version WHERE version = 'v2.0'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '管理端-“资讯列表”增加资讯的“创建时间”显示', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/37', (SELECT id FROM t_version WHERE version = 'v2.0'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '第三方登录实现', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/38', (SELECT id FROM t_version WHERE version = 'v2.0'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '调整“生产环境”的图片地址的域名', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/41', (SELECT id FROM t_version WHERE version = 'v2.0'));
