-- 创建数据库
CREATE SCHEMA IF NOT EXISTS `art_artisan_tc`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

-- 创建数据库用户 （如不需要，可不执行）
CREATE USER 'art_artisan_tc'@'localhost'
  IDENTIFIED BY 'art_artisan_tc2@!6';

-- 为创建的用户授权可以操作的数据库 （如不需要，可不执行）
GRANT SELECT, INSERT, UPDATE, REFERENCES, DELETE, CREATE, DROP, ALTER, INDEX, TRIGGER, CREATE VIEW, SHOW VIEW, EXECUTE, ALTER ROUTINE, CREATE ROUTINE, CREATE TEMPORARY TABLES, LOCK TABLES, EVENT
ON `art_artisan_tc`.* TO 'art_artisan_tc'@'localhost';
GRANT GRANT OPTION ON `art_artisan_tc`.* TO 'art_artisan_tc'@'localhost';

-- 创建可以远程连接的用户 （非特殊需要不应当执行以下语句）
CREATE USER 'art_artisan_tc'@'%'
  IDENTIFIED BY 'art_artisan_tc2@!6';
GRANT SELECT, INSERT, UPDATE, REFERENCES, DELETE, CREATE, DROP, ALTER, INDEX, TRIGGER, CREATE VIEW, SHOW VIEW, EXECUTE, ALTER ROUTINE, CREATE ROUTINE, CREATE TEMPORARY TABLES, LOCK TABLES, EVENT
ON `art_artisan_tc`.* TO 'art_artisan_tc'@'%';
GRANT GRANT OPTION ON `art_artisan_tc`.* TO 'art_artisan_tc'@'%';