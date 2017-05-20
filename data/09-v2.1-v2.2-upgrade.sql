-- ----------------------------
-- 注意：本文件存放的是“2.1升级到2.2版本”新增数据的语句！
-- ----------------------------

-- ----------------------------
-- Table structure for `t_information_like`
-- ----------------------------
DROP TABLE IF EXISTS `t_information_like`;
CREATE TABLE `t_information_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `information_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_INFORMATION_LIKE_INFORMATION_ID` (`information_id`),
  KEY `FK_INFORMATION_LIKE_USER_ID` (`user_id`),
  CONSTRAINT `FK_INFORMATION_LIKE_INFORMATION_ID` FOREIGN KEY (`information_id`) REFERENCES `t_information` (`id`),
  CONSTRAINT `FK_INFORMATION_LIKE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_tag`
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IND_T_TAG_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_information_secondary_tag`
-- ----------------------------
DROP TABLE IF EXISTS `t_information_secondary_tag`;
CREATE TABLE `t_information_secondary_tag` (
  `information_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_INFORMATION_SECONDARY_TAG_ID` (`information_id`,`tag_id`),
  KEY `FK_INFORMATION_SECONDARY_TAG_TAG_ID` (`tag_id`),
  CONSTRAINT `FK_INFORMATION_SECONDARY_TAG_INFORMATION_ID` FOREIGN KEY (`information_id`) REFERENCES `t_information` (`id`),
  CONSTRAINT `FK_INFORMATION_SECONDARY_TAG_TAG_ID` FOREIGN KEY (`tag_id`) REFERENCES `t_tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

ALTER TABLE `t_information` ADD COLUMN `primary_tag_id`  bigint(20) NULL AFTER `title`;
ALTER TABLE `t_information` ADD CONSTRAINT `FK_INFORMATION_PRIMARY_TAG_ID` FOREIGN KEY (`primary_tag_id`) REFERENCES `t_tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `t_information` ADD COLUMN `user_id`  bigint(20) NULL AFTER `primary_tag_id`;
ALTER TABLE `t_information` ADD CONSTRAINT `FK_INFORMATION_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- ----------------------------
-- Table structure for `t_information_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_information_comment`;
CREATE TABLE `t_information_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(400) COLLATE utf8mb4_bin DEFAULT NULL,
  `information_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `parent_comment_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_INFORMATION_COMMENT_INFORMATION_ID` (`information_id`),
  KEY `FK_INFORMATION_COMMENT_USER_ID` (`user_id`),
  KEY `FK_INFORMATION_COMMENT_PARENT_COMMENT_ID` (`parent_comment_id`),
  CONSTRAINT `FK_INFORMATION_COMMENT_INFORMATION_ID` FOREIGN KEY (`information_id`) REFERENCES `t_information` (`id`),
  CONSTRAINT `FK_INFORMATION_COMMENT_PARENT_COMMENT_ID` FOREIGN KEY (`parent_comment_id`) REFERENCES `t_information_comment` (`id`),
  CONSTRAINT `FK_INFORMATION_COMMENT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_my_favorite_information`
-- ----------------------------
DROP TABLE IF EXISTS `t_my_favorite_information`;
CREATE TABLE `t_my_favorite_information` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `information_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MY_FAVORITE_INFORMATION_INFORMATION_ID` (`information_id`),
  KEY `FK_MY_FAVORITE_INFORMATION_USER_ID` (`user_id`),
  CONSTRAINT `FK_MY_FAVORITE_INFORMATION_INFORMATION_ID` FOREIGN KEY (`information_id`) REFERENCES `t_information` (`id`),
  CONSTRAINT `FK_MY_FAVORITE_INFORMATION_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_information_comment_like`
-- ----------------------------
DROP TABLE IF EXISTS `t_information_comment_like`;
CREATE TABLE `t_information_comment_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `comment_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_INFORMATION_COMMENT_LIKE_COMMENT_ID` (`comment_id`),
  KEY `FK_INFORMATION_COMMENT_LIKE_USER_ID` (`user_id`),
  CONSTRAINT `FK_INFORMATION_COMMENT_LIKE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_INFORMATION_COMMENT_LIKE_COMMENT_ID` FOREIGN KEY (`comment_id`) REFERENCES `t_information_comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- “调度任务”的执行时间，由10分钟执行一次调整为30分钟执行一次
UPDATE `t_scheduler_job` SET cron_expression =  '0 0/30 * * * ?' WHERE job_name = 'SCAN_ITEMS_JOB';

-- “资讯列表”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.information', '/api/information', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.information'));
-- “资讯点赞”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.information.id.give.like', '/api/information/*/likes', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.information.id.give.like'));
-- “资讯评论”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.information.id.comments.on', '/api/information/*/comments', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.information.id.comments.on'));
-- “收藏资讯”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.favorites.information', '/api/i/favorites/information', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.favorites.information'));
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.favorites.information.add', '/api/i/favorites/information', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.favorites.information.add'));
-- “评论资讯点赞”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.information.id.comments.id.give.like', '/api/information/*/comments/*/likes', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.information.id.comments.id.give.like'));
-- “发表资讯”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.information.create', '/api/information', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.information.create'));

-- “2.2版本”信息的初始化数据
INSERT INTO `t_version` (`version`, `create_date_time`, `is_upgrade`) VALUES ('v2.2', '2017-01-12', 1);
-- 2.2版本的任务
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '“资讯”详情页面的新功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/46', (SELECT id FROM t_version WHERE version = 'v2.2'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“发布艺讯”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/48', (SELECT id FROM t_version WHERE version = 'v2.2'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“点赞艺讯”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/49', (SELECT id FROM t_version WHERE version = 'v2.2'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“评论艺讯”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/50', (SELECT id FROM t_version WHERE version = 'v2.2'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“点赞艺讯的评论”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/51', (SELECT id FROM t_version WHERE version = 'v2.2'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“收藏艺讯”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/52', (SELECT id FROM t_version WHERE version = 'v2.2'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '设计“标签”功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/54', (SELECT id FROM t_version WHERE version = 'v2.2'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('IMPROVEMENT', '优化了登录接口使其向下兼容', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/56', (SELECT id FROM t_version WHERE version = 'v2.2'));
