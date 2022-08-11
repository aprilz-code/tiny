/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.38-log : Database - tinymall
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE
DATABASE /*!32312 IF NOT EXISTS*/`tinymall` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE
`tinymall`;

/*Table structure for table `ap_admin` */

DROP TABLE IF EXISTS `ap_admin`;

CREATE TABLE `ap_admin`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL,
    `create_time` datetime(6) DEFAULT NULL,
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL,
    `update_time` datetime(6) DEFAULT NULL,
    `delete_flag` bit(1)                                                 DEFAULT b'0' COMMENT '0正常 1已删除',
    `status`      bit(1)                                                 DEFAULT b'1' COMMENT '帐号启用状态：0->禁用；1->启用',
    `username`    varchar(64)                                            DEFAULT NULL,
    `password`    varchar(64)                                            DEFAULT NULL,
    `mobile`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机',
    `sex`         tinyint(4) DEFAULT '2' COMMENT '性别：0->女；1->男 2-未知',
    `avatar`      varchar(500)                                           DEFAULT NULL COMMENT '头像',
    `email`       varchar(100)                                           DEFAULT NULL COMMENT '邮箱',
    `nick_name`   varchar(200)                                           DEFAULT NULL COMMENT '昵称',
    `login_time`  datetime                                               DEFAULT NULL COMMENT '最后登录时间',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

/*Data for the table `ap_admin` */

insert into `ap_admin`(`id`, `create_by`, `create_time`, `update_by`, `update_time`, `delete_flag`, `status`,
                       `username`, `password`, `mobile`, `sex`, `avatar`, `email`, `nick_name`, `login_time`,
                       `description`)
values (1, 'ADMIN', '2022-08-11 15:04:32.000000', 'ADMIN', '2022-08-11 15:04:39.000000', '\0', '', 'admin',
        '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', '17600000000', 2,
        'https://thirdwx.qlogo.cn/mmopen/vi_32/2KOBFlndeR5aIzSMFAzfQewiawkmT6LnZpiaf5DAKWAcTn0qaXCmI6wzP71qXHL55xAwqZLVvvs9j7wUYNlmmpiaw/132',
        '1981196280@qq.com', 'aprilz', NULL, NULL);

/*Table structure for table `ap_admin_permission_relation` */

DROP TABLE IF EXISTS `ap_admin_permission_relation`;

CREATE TABLE `ap_admin_permission_relation`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `create_by`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `create_time`   datetime(6) DEFAULT NULL,
    `update_by`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `update_time`   datetime(6) DEFAULT NULL,
    `delete_flag`   bit(1)                                                DEFAULT b'0' COMMENT '0正常 1已删除',
    `admin_id`      bigint(20) DEFAULT NULL,
    `permission_id` bigint(20) DEFAULT NULL,
    `type`          int(1) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户和权限关系表(除角色中定义的权限以外的加减权限)';

/*Data for the table `ap_admin_permission_relation` */

/*Table structure for table `ap_admin_role_relation` */

DROP TABLE IF EXISTS `ap_admin_role_relation`;

CREATE TABLE `ap_admin_role_relation`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `create_time` datetime(6) DEFAULT NULL,
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `update_time` datetime(6) DEFAULT NULL,
    `status`      bit(1)                                                DEFAULT NULL COMMENT '状态：0->无效；1->有效',
    `admin_id`    bigint(20) DEFAULT NULL,
    `role_id`     bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='后台用户和角色关系表';

/*Data for the table `ap_admin_role_relation` */

insert into `ap_admin_role_relation`(`id`, `create_by`, `create_time`, `update_by`, `update_time`, `status`, `admin_id`,
                                     `role_id`)
values (1, NULL, NULL, NULL, NULL, NULL, 1, 1),
       (2, NULL, NULL, NULL, NULL, NULL, 1, 1);

/*Table structure for table `ap_permission` */

DROP TABLE IF EXISTS `ap_permission`;

CREATE TABLE `ap_permission`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `create_time` datetime(6) DEFAULT NULL,
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `update_time` datetime(6) DEFAULT NULL,
    `delete_flag` bit(1)                                                DEFAULT b'0' COMMENT '0正常 1已删除',
    `pid`         bigint(20) DEFAULT NULL COMMENT '父级权限id',
    `name`        varchar(100)                                          DEFAULT NULL COMMENT '名称',
    `value`       varchar(200)                                          DEFAULT NULL COMMENT '权限值',
    `icon`        varchar(500)                                          DEFAULT NULL COMMENT '图标',
    `type`        int(1) DEFAULT NULL COMMENT '权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）',
    `uri`         varchar(200)                                          DEFAULT NULL COMMENT '前端资源路径',
    `sort`        int(11) DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='后台用户权限表';

/*Data for the table `ap_permission` */

insert into `ap_permission`(`id`, `create_by`, `create_time`, `update_by`, `update_time`, `delete_flag`, `pid`, `name`,
                            `value`, `icon`, `type`, `uri`, `sort`)
values (1, NULL, '2018-09-29 16:15:14.000000', NULL, NULL, '\0', 0, '商品', NULL, NULL, 0, NULL, 0),
       (2, NULL, '2018-09-29 16:17:01.000000', NULL, NULL, '\0', 1, '商品列表', 'pms:product:read', NULL, 1,
        '/pms/product/index', 0),
       (3, NULL, '2018-09-29 16:18:51.000000', NULL, NULL, '\0', 1, '添加商品', 'pms:product:create', NULL, 1,
        '/pms/product/add', 0),
       (4, NULL, '2018-09-29 16:23:07.000000', NULL, NULL, '\0', 1, '商品分类', 'pms:productCategory:read', NULL, 1,
        '/pms/productCate/index', 0),
       (5, NULL, '2018-09-29 16:24:43.000000', NULL, NULL, '\0', 1, '商品类型', 'pms:productAttribute:read', NULL, 1,
        '/pms/productAttr/index', 0),
       (6, NULL, '2018-09-29 16:25:45.000000', NULL, NULL, '\0', 1, '品牌管理', 'pms:brand:read', NULL, 1,
        '/pms/brand/index', 0),
       (7, NULL, '2018-09-29 16:34:23.000000', NULL, NULL, '\0', 2, '编辑商品', 'pms:product:update', NULL, 2,
        '/pms/product/updateProduct', 0),
       (8, NULL, '2018-09-29 16:38:33.000000', NULL, NULL, '\0', 2, '删除商品', 'pms:product:delete', NULL, 2,
        '/pms/product/delete', 0),
       (9, NULL, '2018-09-29 16:43:23.000000', NULL, NULL, '\0', 4, '添加商品分类', 'pms:productCategory:create', NULL, 2,
        '/pms/productCate/create', 0),
       (10, NULL, '2018-09-29 16:43:55.000000', NULL, NULL, '\0', 4, '修改商品分类', 'pms:productCategory:update', NULL, 2,
        '/pms/productCate/update', 0),
       (11, NULL, '2018-09-29 16:44:38.000000', NULL, NULL, '\0', 4, '删除商品分类', 'pms:productCategory:delete', NULL, 2,
        '/pms/productAttr/delete', 0),
       (12, NULL, '2018-09-29 16:45:25.000000', NULL, NULL, '\0', 5, '添加商品类型', 'pms:productAttribute:create', NULL, 2,
        '/pms/productAttr/create', 0),
       (13, NULL, '2018-09-29 16:48:08.000000', NULL, NULL, '\0', 5, '修改商品类型', 'pms:productAttribute:update', NULL, 2,
        '/pms/productAttr/update', 0),
       (14, NULL, '2018-09-29 16:48:44.000000', NULL, NULL, '\0', 5, '删除商品类型', 'pms:productAttribute:delete', NULL, 2,
        '/pms/productAttr/delete', 0),
       (15, NULL, '2018-09-29 16:49:34.000000', NULL, NULL, '\0', 6, '添加品牌', 'pms:brand:create', NULL, 2,
        '/pms/brand/add', 0),
       (16, NULL, '2018-09-29 16:50:55.000000', NULL, NULL, '\0', 6, '修改品牌', 'pms:brand:update', NULL, 2,
        '/pms/brand/update', 0),
       (17, NULL, '2018-09-29 16:50:59.000000', NULL, NULL, '\0', 6, '删除品牌', 'pms:brand:delete', NULL, 2,
        '/pms/brand/delete', 0),
       (18, NULL, '2018-09-29 16:51:57.000000', NULL, NULL, '\0', 0, '首页', NULL, NULL, 0, NULL, 0);

/*Table structure for table `ap_role` */

DROP TABLE IF EXISTS `ap_role`;

CREATE TABLE `ap_role`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `create_time` datetime(6) DEFAULT NULL,
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `update_time` datetime(6) DEFAULT NULL,
    `delete_flag` bit(1)                                                DEFAULT b'0' COMMENT '0正常 1已删除',
    `name`        varchar(100)                                          DEFAULT NULL COMMENT '名称',
    `description` varchar(500)                                          DEFAULT NULL COMMENT '描述',
    `admin_count` int(11) DEFAULT NULL COMMENT '后台用户数量',
    `sort`        int(11) DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台用户角色表';

/*Data for the table `ap_role` */

insert into `ap_role`(`id`, `create_by`, `create_time`, `update_by`, `update_time`, `delete_flag`, `name`,
                      `description`, `admin_count`, `sort`)
values (1, NULL, '2018-09-30 15:46:11.000000', NULL, NULL, '\0', '商品管理员', '商品管理员', 0, 0);

/*Table structure for table `ap_role_permission_relation` */

DROP TABLE IF EXISTS `ap_role_permission_relation`;

CREATE TABLE `ap_role_permission_relation`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `create_by`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `create_time`   datetime(6) DEFAULT NULL,
    `update_by`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `update_time`   datetime(6) DEFAULT NULL,
    `delete_flag`   bit(1)                                                DEFAULT b'0' COMMENT '0正常 1已删除',
    `role_id`       bigint(20) DEFAULT NULL,
    `permission_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='后台用户角色和权限关系表';

/*Data for the table `ap_role_permission_relation` */

insert into `ap_role_permission_relation`(`id`, `create_by`, `create_time`, `update_by`, `update_time`, `delete_flag`,
                                          `role_id`, `permission_id`)
values (1, NULL, NULL, NULL, NULL, NULL, 1, 1),
       (2, NULL, NULL, NULL, NULL, NULL, 1, 2),
       (3, NULL, NULL, NULL, NULL, NULL, 1, 3),
       (4, NULL, NULL, NULL, NULL, NULL, 1, 4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
