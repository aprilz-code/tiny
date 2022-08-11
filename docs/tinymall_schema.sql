DROP
DATABASE IF EXISTS tinymall;
DROP
USER IF EXISTS 'tinymall'@'%';
-- 支持emoji：需要mysql数据库参数： character_set_server=utf8mb4
CREATE
DATABASE tinymall DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE
tinymall;
CREATE
USER 'tinymall'@'%' IDENTIFIED BY 'tinymall123456';
GRANT ALL PRIVILEGES ON tinymall.* TO
'tinymall'@'%';
FLUSH
PRIVILEGES;