-- ----------------------------
-- 注意：本文件存放的是“2.0升级到2.1版本”新增数据的语句！
-- ----------------------------

ALTER TABLE `t_user_profile` ADD COLUMN `birthday`  datetime NULL AFTER `user_id`;

DROP TABLE IF EXISTS `t_user_screen_show_setting`;
DROP TABLE IF EXISTS `t_screen_show_setting`;
-- ----------------------------
-- Table structure for `t_screen_show_setting`
-- ----------------------------
CREATE TABLE `t_screen_show_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_show` bit(1) DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `screen_category` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `show_category` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `description` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_user_screen_show_setting`
-- ----------------------------
CREATE TABLE `t_user_screen_show_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_show` bit(1) DEFAULT NULL,
  `screen_show_setting_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_SCREEN_SHOW_SETTING_SCREEN_SHOW_SETTING_ID` (`screen_show_setting_id`),
  KEY `FK_USER_SCREEN_SHOW_SETTING_USER_ID` (`user_id`),
  CONSTRAINT `FK_USER_SCREEN_SHOW_SETTING_SCREEN_SHOW_SETTING_ID` FOREIGN KEY (`screen_show_setting_id`) REFERENCES `t_screen_show_setting` (`id`),
  CONSTRAINT `FK_USER_SCREEN_SHOW_SETTING_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for `t_art_big_shot`
-- ----------------------------
DROP TABLE IF EXISTS `t_art_big_shot`;
CREATE TABLE `t_art_big_shot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `introduce` varchar(400) COLLATE utf8mb4_bin DEFAULT NULL,
  `overview` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ART_BIG_SHOT_USER_ID` (`user_id`),
  CONSTRAINT `FK_ART_BIG_SHOT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- “完善个人资料”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.profile.complete', '/api/i/profile', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.profile.complete'));
-- “昵称是否可用”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.profile.nickname.available', '/api/i/profile/nickname-available', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.profile.nickname.available'));
-- “显示界面的参数控制”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.screen.show', '/api/screen-show', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.screen.show'));
-- “推荐大咖”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.art.big.shots.recommend', '/api/art-big-shots/recommend', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.art.big.shots.recommend'));
-- “关注大咖”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.art.big.shots.follows', '/api/art-big-shots/follows', 'PUT', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.art.big.shots.follows'));

-- “显示界面的参数控制”的初始化数据
INSERT INTO `t_screen_show_setting` (`is_show`, `name`, `screen_category`, `show_category`, `status`, `description`, `create_date_time`, `update_date_time`, `version`) VALUE (1, 'COMPLETE_PROFILE', 'GUIDE', 'USER', 'ENABLED', '控制是否显示“完善个人资料”界面', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_screen_show_setting` (`is_show`, `name`, `screen_category`, `show_category`, `status`, `description`, `create_date_time`, `update_date_time`, `version`) VALUE (1, 'RECOMMEND_ART_BIG_SHOT', 'GUIDE', 'USER', 'ENABLED', '控制是否显示“推荐大咖”界面', SYSDATE(), SYSDATE(), '0');

-- 管理端“用户列表”权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.users', '/administrator/users', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.users'));
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.users.id', '/administrator/users/*', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.users.id'));
-- 管理端“大咖列表”权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.art.big.shots', '/administrator/art-big-shots', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.art.big.shots'));
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.art.big.shots.create', '/administrator/art-big-shots', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.art.big.shots.create'));
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.art.big.shots.id', '/administrator/art-big-shots/*', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.art.big.shots.id'));
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.art.big.shots.id.cancel', '/administrator/art-big-shots/*', 'DELETE', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.art.big.shots.id.cancel'));

-- “2.1版本”信息的初始化数据
INSERT INTO `t_version` (`version`, `create_date_time`, `is_upgrade`) VALUES ('v2.1', '2017-01-05', 1);
-- 2.1版本的任务
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '管理端新增用户列表功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/12', (SELECT id FROM t_version WHERE version = 'v2.1'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('ISSUE', '设计“推荐大咖”功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/42', (SELECT id FROM t_version WHERE version = 'v2.1'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“完善个人资料”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/43', (SELECT id FROM t_version WHERE version = 'v2.1'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“是否显示指定界面给用户”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/44', (SELECT id FROM t_version WHERE version = 'v2.1'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '管理端新增将“普通用户”转为“大咖”的功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/47', (SELECT id FROM t_version WHERE version = 'v2.1'));
INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '管理端新增解除用户的“大咖”身份的功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/53', (SELECT id FROM t_version WHERE version = 'v2.1'));
