-- ----------------------------
-- 注意：本文件存放的是“管理端”初始化数据的语句！
-- ----------------------------

-- ----------------------------
-- 初始化“权限信息”数据
-- ----------------------------
-- “实名认证”的审核权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.real.name.pending.review', '/administrator/real-name/pending-review', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.real.name.pending.review.id', '/administrator/real-name/pending-review/*', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.real.name.pending.review.id.approve', '/administrator/real-name/pending-review/*', 'PATCH', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.real.name.pending.review.id.reject', '/administrator/real-name/pending-review/*', 'DELETE', SYSDATE(), SYSDATE(), '0');
-- “商家认证”的审核权限
# INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.merchants.pending.review', '/administrator/merchants/pending-review', 'GET', SYSDATE(), SYSDATE(), '0');
# INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.merchants.pending.review.id', '/administrator/merchants/pending-review/*', 'GET', SYSDATE(), SYSDATE(), '0');
# INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.merchants.pending.review.id.approve', '/administrator/merchants/pending-review/*', 'PATCH', SYSDATE(), SYSDATE(), '0');
# INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.merchants.pending.review.id.reject', '/administrator/merchants/pending-review/*', 'DELETE', SYSDATE(), SYSDATE(), '0');
-- “待审核拍品”的审核权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.items.pending.review', '/administrator/items/pending-review', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.items.pending.review.id', '/administrator/items/pending-review/*', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.items.pending.review.id.approve', '/administrator/items/pending-review/*', 'PATCH', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.items.pending.review.id.reject', '/administrator/items/pending-review/*', 'DELETE', SYSDATE(), SYSDATE(), '0');
-- “银行”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.banks', '/administrator/banks', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.banks.create', '/administrator/banks', 'POST', SYSDATE(), SYSDATE(), '0');
-- “快递公司”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.express.companies', '/administrator/express-companies', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.express.companies.create', '/administrator/express-companies', 'POST', SYSDATE(), SYSDATE(), '0');
-- “资讯”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.information', '/administrator/information', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.information.create', '/administrator/information', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.information.id', '/administrator/information/*', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.information.upload.images', '/administrator/information/images', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.information.upload.cover', '/administrator/information/cover', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.information.upload.cover.delete', '/administrator/information/cover/*', 'POST', SYSDATE(), SYSDATE(), '0');
-- “广告”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.advertisements', '/administrator/advertisements', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.advertisements.create', '/administrator/advertisements', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.advertisements.id', '/administrator/advertisements/*', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.advertisements.upload.images', '/administrator/advertisements/images', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.advertisements.upload.images.delete', '/administrator/advertisements/images/*', 'POST', SYSDATE(), SYSDATE(), '0');
-- “提现申请”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.withdrawal.balance', '/administrator/withdrawal-balance', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.withdrawal.balance.id', '/administrator/withdrawal-balance/*', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.withdrawal.balance.id.solving', '/administrator/withdrawal-balance/*/solving', 'PATCH', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.withdrawal.balance.id.solved', '/administrator/withdrawal-balance/*/solved', 'PATCH', SYSDATE(), SYSDATE(), '0');
-- “转出保证金申请”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.withdrawal.margin', '/administrator/withdrawal-margin', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.withdrawal.margin.id', '/administrator/withdrawal-margin/*', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.withdrawal.margin.id.solving', '/administrator/withdrawal-margin/*/solving', 'PATCH', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.withdrawal.margin.id.solved', '/administrator/withdrawal-margin/*/solved', 'PATCH', SYSDATE(), SYSDATE(), '0');
-- “意见反馈”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.suggestions', '/administrator/suggestions', 'GET', SYSDATE(), SYSDATE(), '0');
-- “修改密码”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.user.update.password', '/administrator/i/password', 'PATCH', SYSDATE(), SYSDATE(), '0');
-- “系统环境”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.environments', '/administrator/environments', 'GET', SYSDATE(), SYSDATE(), '0');

-- ----------------------------
-- 初始化“角色信息”数据
-- ----------------------------
INSERT INTO `t_administrator_role` (`name`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.role.administrator', SYSDATE(), SYSDATE(), '0');

-- ----------------------------
-- 添加“角色和权限”的权限关系
-- ----------------------------
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.real.name.pending.review'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.real.name.pending.review.id'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.real.name.pending.review.id.approve'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.real.name.pending.review.id.reject'));

# INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.merchants.pending.review'));
# INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.merchants.pending.review.id'));
# INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.merchants.pending.review.id.approve'));
# INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.merchants.pending.review.id.reject'));

INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.items.pending.review'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.items.pending.review.id'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.items.pending.review.id.approve'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.items.pending.review.id.reject'));

INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.banks'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.banks.create'));

INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.express.companies'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.express.companies.create'));

INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.information'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.information.create'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.information.id'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.information.upload.images'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.information.upload.cover'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.information.upload.cover.delete'));

INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.advertisements'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.advertisements.create'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.advertisements.id'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.advertisements.upload.images'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.advertisements.upload.images.delete'));

INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.withdrawal.balance'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.withdrawal.balance.id'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.withdrawal.balance.id.solving'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.withdrawal.balance.id.solved'));

INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.withdrawal.margin'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.withdrawal.margin.id'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.withdrawal.margin.id.solving'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.withdrawal.margin.id.solved'));

INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.suggestions'));

INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.user.update.password'));

INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.environments'));
-- ----------------------------
-- 初始化“管理员”数据
-- ----------------------------
-- 用户名：admin，密码：999999999
INSERT INTO `t_administrator` (`username`, `password`, `mobile`, `create_date_time`, `update_date_time`, `version`) VALUES ('admin', '$2a$10$lqzsW3cL.B6o/TByVa73wuVKui7v0YfpJrM8wYlFzHfuYMtmf2Vfm', '15928872304', SYSDATE(), SYSDATE(), '0');

-- ----------------------------
-- 添加“管理员和角色”的权限关系
-- ----------------------------
INSERT INTO `t_administrator_role_relation` (`administrator_id`, `role_id`) VALUES ((SELECT id FROM t_administrator WHERE username = 'admin'), (SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'));
