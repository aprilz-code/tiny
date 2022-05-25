DROP TABLE IF EXISTS `ap_code`;
CREATE TABLE `ap_code` (
                           `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '文件id',
                           `token` VARCHAR(128) DEFAULT NULL COMMENT 'token',

                           PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



