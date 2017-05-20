-- ----------------------------
-- 注意：本文件存放的是“1.1升级到1.2版本”新增数据的语句！
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_qr_code`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_qr_code`;
CREATE TABLE `t_user_qr_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `qr_code_base64` mediumtext COLLATE utf8mb4_bin,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_QR_CODE_USER_ID` (`user_id`),
  CONSTRAINT `FK_USER_QR_CODE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 将“资讯”的“content”列的类型修改为“mediumtext”
ALTER TABLE `t_information`
MODIFY COLUMN `content`  mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL AFTER `author`;

-- “我的二维码名片”权限
INSERT INTO `t_permission` (`name`, `uri`, `http_method`, `create_date_time`, `update_date_time`, `version`) VALUES ('text.permission.user.i.qrCode', '/api/i/qr-code', 'GET', SYSDATE(), SYSDATE(), '0');
-- 将“我的二维码名片”权限添加到橘色中
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES ((SELECT id FROM t_role WHERE name = 'text.role.user'), (SELECT id FROM t_permission WHERE name = 'text.permission.user.i.qrCode'));