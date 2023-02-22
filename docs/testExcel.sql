CREATE TABLE `ap_excel_test`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `create_time` datetime(6) DEFAULT NULL,
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `update_time` datetime(6) DEFAULT NULL,
    `delete_flag` bit(1)                                                DEFAULT b'0' COMMENT '0正常 1已删除',
    `age`    int  DEFAULT NULL,
    `sex`    int  DEFAULT NULL COMMENT '性别',
    `name`        varchar(63)  NOT NULL                                 DEFAULT '' COMMENT '标题',
    `url`         varchar(255) NOT NULL COMMENT '链接',
    `test_time` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='excel-test表';