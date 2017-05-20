-- ----------------------------
-- 注意：本文件存放的是“2.4升级到2.5版本”新增数据的语句！
-- ----------------------------

-- 增加“个人简介”字段
ALTER TABLE `t_user_profile` ADD COLUMN `personal_introduction`  varchar(500) NULL AFTER `birthday`;
-- 增加“个性签名”字段
ALTER TABLE `t_user_profile` ADD COLUMN `personalized_signature`  varchar(100) NULL AFTER `personal_introduction`;

-- “获得用户打赏艺文的支付签名”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.art.moments.id.rewards.reward', '/api/art-moments/*/rewards', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.art.moments.id.rewards.reward'));
-- “修改生日”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.birthday.update', '/api/i/birthday', 'PUT', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.birthday.update'));
-- “修改个人简介”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.personal.introduction.update', '/api/i/personal-introduction', 'PUT', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.personal.introduction.update'));
-- “修改个性前面”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.personalized.signature.update', '/api/i/personalized-signature', 'PUT', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.personalized.signature.update'));

-- “2.5版本”信息的初始化数据
# INSERT INTO `t_version` (`version`, `create_date_time`, `is_upgrade`) VALUES ('v2.5', '2017-03-10', 1);
-- 2.5版本的任务
# INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', ' API-支持艺文的删除功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/30', (SELECT id FROM t_version WHERE version = 'v2.5'));
# INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('IMPROVEMENT', '增加“用户登录记录”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/40', (SELECT id FROM t_version WHERE version = 'v2.5'));
# INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '研究消息推送', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/64', (SELECT id FROM t_version WHERE version = 'v2.5'));
# INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '新增接口“换绑手机”', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/66', (SELECT id FROM t_version WHERE version = 'v2.5'));
# INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', 'API-支持获取“我的钱包”数据', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/67', (SELECT id FROM t_version WHERE version = 'v2.5'));
# INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', 'API-支持获取“我的账单明细”数据', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/68', (SELECT id FROM t_version WHERE version = 'v2.5'));
# INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '实现用户对“资讯”进行操作时对“资讯”作者发送消息提示', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/69', (SELECT id FROM t_version WHERE version = 'v2.5'));
# INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', '实现用户对“艺文”进行操作时对“艺文”作者发送消息提示', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/70', (SELECT id FROM t_version WHERE version = 'v2.5'));
# INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', 'API-支持“对艺文的评论点赞”功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/71', (SELECT id FROM t_version WHERE version = 'v2.5'));
# INSERT INTO `t_version_issue` (`category`, `content`, `link`, `version_id`) VALUES ('FEATURE', 'API-支持用户从“个人账户”中提现功能', 'https://git.oschina.net/ArtisantcSay/aatc-core/issues/73', (SELECT id FROM t_version WHERE version = 'v2.5'));

