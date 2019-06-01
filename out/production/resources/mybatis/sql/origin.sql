/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : cc0game

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-05-29 14:28:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1', '2018-03-13 17:38:34', '2018-03-13 17:38:34', 'ROLE_ADMIN');
INSERT INTO `authority` VALUES ('2', '2018-03-13 17:38:34', '2018-03-13 17:38:34', 'ROLE_USER');
INSERT INTO `authority` VALUES ('3', '2018-03-13 17:38:34', '2018-03-13 17:38:34', 'ROLE_TEACHER');

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_enabled` tinyint(3) unsigned DEFAULT NULL COMMENT '是否可用',
  `e_id` varchar(255) unsigned DEFAULT NULL COMMENT 'elasticsearch ID',
  `file_name` varchar(32) NOT NULL DEFAULT '' COMMENT '文件名',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '图片描述',
  `file_path` varchar(128) NOT NULL DEFAULT '' COMMENT '文件路径',
  `file_format` varchar(16) DEFAULT '' COMMENT '文件格式',
  `file_size` varchar(16) DEFAULT '' COMMENT '文件大小',
  `type` varchar(16) DEFAULT NULL COMMENT '类型',
  `tags` varchar(128) DEFAULT NULL COMMENT '标签',
  `u_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='图片信息表';

-- ----------------------------
-- Records of image
-- ----------------------------

-- ----------------------------
-- Table structure for join_user_authority
-- ----------------------------
DROP TABLE IF EXISTS `join_user_authority`;
CREATE TABLE `join_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_id`),
  KEY `FK3urnor11thn0fx328sur23a7d` (`authority_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of join_user_authority
-- ----------------------------
INSERT INTO `join_user_authority` VALUES ('1', '3');
INSERT INTO `join_user_authority` VALUES ('2', '2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `avatar` varchar(200) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `classify` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `enable` bit(1) NOT NULL,
  `failtime` int(11) DEFAULT NULL,
  `integral` int(11) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `username` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '2018-03-15 16:17:32', '2018-03-18 14:40:20', '', '/images/avatar/large/elliot.jpg', null, null, null, '', '0', '0', null, '文思小白', '$2a$10$lBIvHEmMJj38wQefC9chEeGolKTuvM6MU.fjbOcBUa2DAmd.p5iC6', '0', '17729845523', 'dyq');
INSERT INTO `user` VALUES ('2', '2018-03-31 12:54:21', '2018-03-31 12:54:21', '', '/images/avatar/large/elliot.jpg', null, null, null, '', '0', '0', null, '文思小白', '$2a$10$uWdDtUWHsqH2c7rqtYxPyOs/lINhPqq4.yGz5LBTueDEIxCN.bHNy', '0', '15281714097', 'dengyouquan');
SET FOREIGN_KEY_CHECKS=1;

USE `cc0Project`;

CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT now() COMMENT '更新时间',
  -- 强制】表达是与否概念的字段，必须使用 is _ xxx 的方式命名，数据类型是 unsigned tinyint
  -- （ 1 表示是，0 表示否 ） 。
  `is_enabled`  tinyint unsigned DEFAULT NULL COMMENT '是否可用',
  `e_id`  bigint(20) DEFAULT NULL COMMENT 'elasticsearch ID',
  `file_name` varchar(32) NOT NULL DEFAULT '' COMMENT '文件名',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '图片描述',
  `file_path` varchar(255) NOT NULL DEFAULT '' COMMENT '文件路径',
  `file_format` varchar(16) NOT NULL DEFAULT '' COMMENT '文件格式',
  `file_size` varchar(16) NOT NULL DEFAULT '' COMMENT '文件大小',
  `type` varchar(8) DEFAULT NULL COMMENT '类型',
  `tags` varchar(128) DEFAULT NULL COMMENT '标签',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `status` int(20) DEFAULT NULL COMMENT '审核状态',
  PRIMARY KEY (`id`)
) ENGINE=Innodb AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='图片信息表';

CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT now() COMMENT '更新时间',
  -- 强制】表达是与否概念的字段，必须使用 is _ xxx 的方式命名，数据类型是 unsigned tinyint
  -- （ 1 表示是，0 表示否 ） 。
  `is_enabled`  tinyint unsigned DEFAULT NULL COMMENT '是否可用',
  `e_id`  bigint(20) DEFAULT NULL COMMENT 'elasticsearch ID',
  `file_name` varchar(32) NOT NULL DEFAULT '' COMMENT '文件名',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '资源描述',
  `file_path` varchar(255) NOT NULL DEFAULT '' COMMENT '文件路径',
  `file_format` varchar(16) DEFAULT '' COMMENT '文件格式',
  `file_size` varchar(16) NOT NULL DEFAULT '' COMMENT '文件大小',
  `type` varchar(8) DEFAULT NULL COMMENT '类型',
  `style` varchar(8) DEFAULT NULL COMMENT '资源风格',
  `tags` varchar(128) DEFAULT NULL COMMENT '标签',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `status` int(20) DEFAULT NULL COMMENT '审核状态',
  `total_rate` bigint(20) DEFAULT 0 COMMENT '总评分',
  `total_rate_num` int(20) DEFAULT 0 COMMENT '总评分人数',
  PRIMARY KEY (`id`)
) ENGINE=Innodb AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='资源信息表';

CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT now() COMMENT '更新时间',
  -- 强制】表达是与否概念的字段，必须使用 is _ xxx 的方式命名，数据类型是 unsigned tinyint
  -- （ 1 表示是，0 表示否 ） 。
  `is_enabled`  tinyint unsigned DEFAULT NULL COMMENT '是否可用',
  `e_id`  bigint(20) DEFAULT NULL COMMENT 'elasticsearch ID',
  `file_name` varchar(32) NOT NULL DEFAULT '' COMMENT '文件名',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '图片描述',
  `file_path` varchar(255) NOT NULL DEFAULT '' COMMENT '文件路径',
  `file_format` varchar(16) NOT NULL DEFAULT '' COMMENT '文件格式',
  `file_size` varchar(16) NOT NULL DEFAULT '' COMMENT '文件大小',
  `type` varchar(8) DEFAULT NULL COMMENT '类型',
  `tags` varchar(128) DEFAULT NULL COMMENT '标签',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `status` int(20) DEFAULT NULL COMMENT '审核状态',
  PRIMARY KEY (`id`)
) ENGINE=Innodb AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='图片信息表';

CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT now() COMMENT '更新时间',
  -- 强制】表达是与否概念的字段，必须使用 is _ xxx 的方式命名，数据类型是 unsigned tinyint
  -- （ 1 表示是，0 表示否 ） 。
  `from_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `from_avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '用户头像',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '评论',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  `enabled` tinyint(3) DEFAULT 1 COMMENT '启用',
  PRIMARY KEY (`id`)
) ENGINE=Innodb AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='评论信息表';

CREATE TABLE `rate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT now() COMMENT '更新时间',
  -- 强制】表达是与否概念的字段，必须使用 is _ xxx 的方式命名，数据类型是 unsigned tinyint
  -- （ 1 表示是，0 表示否 ） 。
  `rate` tinyint NOT NULL DEFAULT 0 COMMENT '评分',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=Innodb AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='评论信息表';

