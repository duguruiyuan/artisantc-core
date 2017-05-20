-- ----------------------------
-- 注意：本文件存放的是“1.0升级到1.1版本”新增数据的语句！
-- ----------------------------
-- “拍品支付订单”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.item.payment.orders', '/administrator/item-payment-orders', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.item.payment.orders.order.number', '/administrator/item-payment-orders/*', 'GET', SYSDATE(), SYSDATE(), '0');
-- “拍品保证金的支付订单”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.item.margin.orders', '/administrator/item-margin-orders', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.item.margin.orders.order.number', '/administrator/item-margin-orders/*', 'GET', SYSDATE(), SYSDATE(), '0');
-- “商家保证金的支付订单”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.merchant.margin.orders', '/administrator/merchant-margin-orders', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.merchant.margin.orders.order.number', '/administrator/merchant-margin-orders/*', 'GET', SYSDATE(), SYSDATE(), '0');
-- “拍品退款订单”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.item.refund.orders', '/administrator/item-refund-orders', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.item.refund.orders.order.number', '/administrator/item-refund-orders/*', 'GET', SYSDATE(), SYSDATE(), '0');
-- “拍品保证金退款订单”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.item.margin.refund.orders', '/administrator/item-margin-refund-orders', 'GET', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.item.margin.refund.orders.order.number', '/administrator/item-margin-refund-orders/*', 'GET', SYSDATE(), SYSDATE(), '0');
-- “资讯”的权限
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.information.create.and.publish', '/administrator/information/publish', 'POST', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.information.id.update', '/administrator/information/*', 'PUT', SYSDATE(), SYSDATE(), '0');
INSERT INTO `t_administrator_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.administrator.permission.information.id.update.and.publish', '/administrator/information/*/publish', 'PUT', SYSDATE(), SYSDATE(), '0');


-- “将新增权限增加到管理员权限中”
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.item.payment.orders'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.item.payment.orders.order.number'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.item.margin.orders'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.item.margin.orders.order.number'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.merchant.margin.orders'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.merchant.margin.orders.order.number'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.item.refund.orders'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.item.refund.orders.order.number'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.item.margin.refund.orders'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.item.margin.refund.orders.order.number'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.information.create.and.publish'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.information.id.update'));
INSERT INTO `t_administrator_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_administrator_role WHERE name = 'text.role.administrator'), (SELECT id FROM t_administrator_permission WHERE name = 'text.administrator.permission.information.id.update.and.publish'));

-- “调度任务”的初始化数据
INSERT INTO `t_scheduler_job` (`job_name`, `job_group`, `job_class_name`, `cron_expression`, `description`, `create_date_time`, `update_date_time`, `version`) VALUES ('SCAN_ITEMS_JOB', 'SCAN', 'cn.artisantc.core.service.quartz.ScanItemsJob', '0 0/10 * * * ?', '“扫描拍品”任务，用于根据拍品的“开始时间”和“结束时间”到达后自动更新拍品的状态', SYSDATE(), SYSDATE(), '0');

-- 更新“资讯”状态
UPDATE `t_information` SET `status` = 'PUBLISHED' WHERE `status` IS NULL ;

-- 更新“广告”状态
UPDATE `t_advertisement` SET `status` = 'PUBLISHED' WHERE `status` IS NULL ;