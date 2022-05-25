DROP TABLE IF EXISTS `ap_code`;
CREATE TABLE `ap_code` (
                           `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '文件id',
                           `token` VARCHAR(128) DEFAULT NULL COMMENT 'token',
                           PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


DROP TABLE IF EXISTS `ap_use_info`;
CREATE TABLE `ap_use_info` (
                               `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                               `username` VARCHAR(64) DEFAULT NULL COMMENT '用户名',
                               `phone` VARCHAR(64) DEFAULT NULL COMMENT '手机号码',
                               `bank_card` VARCHAR(64) DEFAULT NULL COMMENT '银行卡号',
                               `open_bank` VARCHAR(64) DEFAULT NULL COMMENT '开户行',
                               `company_name` VARCHAR(64) DEFAULT NULL COMMENT '单位名称',
                               `work_place` VARCHAR(64) DEFAULT NULL COMMENT '工作地址',
                               `address` VARCHAR(64) DEFAULT NULL COMMENT '居住地',
                               `total_amount` DECIMAL(10,2) DEFAULT NULL COMMENT '总额度',
                               `has_car` INT(1)  DEFAULT 0 COMMENT '有车：0->没有；1->有',
                               `has_RESERVED_FUNDS` INT(1)  DEFAULT 0 COMMENT '有公积金：0->没有；1->有',
                               `has_chit` INT(1)  DEFAULT 0 COMMENT '有保单：0->没有；1->有',
                               PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;




