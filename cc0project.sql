/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : cc0project

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 01/06/2019 12:31:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(0) NOT NULL,
  `updated_at` datetime(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES (1, '2018-03-13 17:38:34', '2018-03-13 17:38:34', 'ROLE_ADMIN');
INSERT INTO `authority` VALUES (2, '2018-03-13 17:38:34', '2018-03-13 17:38:34', 'ROLE_USER');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `from_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `from_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '评论',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  `enabled` tinyint(3) DEFAULT 1 COMMENT '启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, '2019-05-11 23:05:28', '2019-05-11 23:05:28', 'admin', 'http://118.89.163.211:81/M00/00/00/Cml0hFzSgKKAI9AJAAEE8b_wmGg474.png', '好资源，赞一个', 1, 23, 1);
INSERT INTO `comment` VALUES (2, '2019-05-12 15:23:25', '2019-05-12 15:23:25', 'admin', 'http://118.89.163.211:81/M00/00/00/Cml0hFzSgKKAI9AJAAEE8b_wmGg474.png', '**', 1, 23, 1);
INSERT INTO `comment` VALUES (3, '2019-05-12 15:23:42', '2019-05-12 15:23:42', 'admin', 'http://118.89.163.211:81/M00/00/00/Cml0hFzSgKKAI9AJAAEE8b_wmGg474.png', '🐴😒', 1, 23, 1);
INSERT INTO `comment` VALUES (5, '2019-05-13 00:05:27', '2019-05-13 00:05:27', 'dengyouquan', '/images/avatar/large/elliot.jpg', '好资源', 2, 15, 1);
INSERT INTO `comment` VALUES (6, '2019-05-13 00:05:33', '2019-05-13 00:05:33', 'dengyouquan', '/images/avatar/large/elliot.jpg', '感谢', 2, 15, 1);
INSERT INTO `comment` VALUES (7, '2019-06-01 09:59:47', '2019-06-01 09:59:47', 'dengyouquan', '/images/avatar/large/elliot.jpg', 'hao😂', 2, 15, 1);

-- ----------------------------
-- Table structure for join_user_authority
-- ----------------------------
DROP TABLE IF EXISTS `join_user_authority`;
CREATE TABLE `join_user_authority`  (
  `user_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `authority_id`) USING BTREE,
  INDEX `FK3urnor11thn0fx328sur23a7d`(`authority_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of join_user_authority
-- ----------------------------
INSERT INTO `join_user_authority` VALUES (1, 1);
INSERT INTO `join_user_authority` VALUES (2, 2);

-- ----------------------------
-- Table structure for rate
-- ----------------------------
DROP TABLE IF EXISTS `rate`;
CREATE TABLE `rate`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `rate` tinyint(4) NOT NULL DEFAULT 0 COMMENT '评分',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rate
-- ----------------------------
INSERT INTO `rate` VALUES (1, '2019-05-11 23:05:38', '2019-05-11 23:05:38', 4, 1, 23);
INSERT INTO `rate` VALUES (2, '2019-06-01 09:59:59', '2019-06-01 09:59:59', 4, 2, 15);

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_enabled` tinyint(3) UNSIGNED DEFAULT NULL COMMENT '是否可用',
  `e_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'elasticsearch ID',
  `file_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件名',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源描述',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件路径',
  `file_format` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '文件格式',
  `file_size` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件大小',
  `type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型',
  `style` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源风格',
  `tags` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标签',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `status` int(20) DEFAULT NULL COMMENT '审核状态',
  `total_rate` bigint(11) UNSIGNED ZEROFILL NOT NULL COMMENT '总评分',
  `total_rate_num` int(11) UNSIGNED ZEROFILL NOT NULL COMMENT '总评分人数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (15, '2019-05-11 15:26:57', '2019-05-13 14:41:47', 1, 'AWsQw9qnn3eBpCmEdk3A', '草地上的伞形蘑菇该', '草地上的伞形蘑菇该', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWeTyAIPRCAAhSf8Xu_Ww427.jpg', 'jpg', '532', 'picture', NULL, NULL, 1, 1, 00000000004, 00000000001);
INSERT INTO `resource` VALUES (16, '2019-05-11 15:29:03', '2019-05-11 15:29:03', 1, 'AWqsAakYs6N7PQjy2E9s', '高原蓝天白云下的山与湖自然风景', '高原蓝天白云下的山与湖自然风景', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWeb2AJHk6ABXhYWMx-MI636.jpg', 'jpg', '1400', 'picture', NULL, NULL, 1, 1, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (17, '2019-05-11 15:29:19', '2019-05-11 15:29:19', 1, 'AWqsBhWts6N7PQjy2E9t', '海上的云彩晚霞景观', '海上的云彩晚霞景观', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWecyAdbKzACUyupjwHRo086.jpg', 'jpg', '2380', 'picture', NULL, NULL, 1, 1, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (18, '2019-05-11 15:34:12', '2019-05-11 15:34:12', 1, 'AWql0EhPrINGrgDTc047', '河流中石头上的青苔', '河流中石头上的青苔', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWevKAQq51AA1uuDkByeA247.jpg', 'jpg', '859', 'picture', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (19, '2019-05-11 15:34:28', '2019-05-11 15:34:28', 1, 'AWql0IYCrINGrgDTc048', '牧场草地上的牛群', '牧场草地上的牛群', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWewGAfM5RABz3Ctg7Ol0215.jpg', 'jpg', '1853', 'picture', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (20, '2019-05-11 15:34:41', '2019-05-11 15:34:41', 1, 'AWql0LuLrINGrgDTc049', '漂亮的绣眼鸟', '漂亮的绣眼鸟', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWew-AcsUNAB_Th8ZbOWg124.jpg', 'jpg', '2036', 'picture', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (21, '2019-05-11 15:34:55', '2019-05-11 15:34:55', 1, 'AWql0PMRrINGrgDTc04-', '水下的帝企鹅', '水下的帝企鹅', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWex2AOozFACpvbLe82WM319.jpg', 'jpg', '2715', 'picture', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (22, '2019-05-11 15:35:10', '2019-05-11 15:35:10', 1, 'AWql0S2IrINGrgDTc04_', '悬崖瀑布与绿色植被自然风景', '悬崖瀑布与绿色植被自然风景', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWeyyAAvdtABiv5KYwZoY229.jpg', 'jpg', '1579', 'picture', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (23, '2019-05-11 15:35:23', '2019-05-11 15:35:23', 1, 'AWql0V3SrINGrgDTc05A', '油菜花田园花海风景', '油菜花田园花海风景', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWezmABbYNAC8VCAOFZzA837.jpg', 'jpg', '3013', 'picture', NULL, NULL, 1, 0, 00000000004, 00000000001);
INSERT INTO `resource` VALUES (24, '2019-05-11 15:35:35', '2019-05-11 15:35:35', 1, 'AWql0YvArINGrgDTc05B', '绽放的玫瑰特写', '绽放的玫瑰特写', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWe0SAa33jAB1L6BDGj2Q579.jpg', 'jpg', '1874', 'picture', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (25, '2019-05-11 15:42:28', '2019-05-11 15:42:28', 1, 'AWql19lQrINGrgDTc05G', '灯塔', '俯视视角略过灯塔，领略不一样的风景。', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWfM6ANenTALd9CIbebhI281.mp4', 'mp4', '11743', 'video', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (26, '2019-05-11 15:43:28', '2019-05-11 15:43:28', 1, 'AWql2MONrINGrgDTc05H', '黑洞特效', '黑洞特效', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWfR2AGBNYAE404RRYk4Q218.mp4', 'mp4', '5005', 'video', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (27, '2019-05-11 15:44:46', '2019-05-11 15:44:46', 1, 'AWqsCtqfs6N7PQjy2E9x', '烟花特效', '烟花特效', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWfWqAGi8bAZPmd1yi0bo253.mp4', 'mp4', '25849', 'video', NULL, NULL, 1, 1, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (28, '2019-05-11 15:59:18', '2019-05-11 15:59:18', 1, 'AWqsBvDcs6N7PQjy2E9v', 'City Sunshine', 'City Sunshine', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWgMqAED99AHDybSyBcKg930.mp3', 'mp3', '7228', 'music', NULL, NULL, 1, 1, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (29, '2019-05-11 16:03:33', '2019-05-11 16:03:33', 1, 'AWql6yfqrINGrgDTc05M', 'A Good Bass for Gambling', 'A Good Bass for Gambling', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWgc2ANPuiACYltGCRyQc757.mp3', 'mp3', '2441', 'music', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (30, '2019-05-11 16:05:34', '2019-05-11 16:05:34', 1, 'AWql7P-5rINGrgDTc05N', 'A Waltz For Naseem', 'A Waltz For Naseem', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWgkKAOHWGADT848F2NxE646.mp3', 'mp3', '3391', 'music', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (31, '2019-05-11 16:07:16', '2019-05-11 16:07:16', 1, 'AWql7o58rINGrgDTc05O', 'Abstract Anxiety', 'Abstract Anxiety', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWgrGAH6qHAEGy8VX1XxE684.mp3', 'mp3', '4204', 'music', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (32, '2019-05-11 16:08:19', '2019-05-11 16:08:19', 1, 'AWql74TgrINGrgDTc05P', 'Epic Boss Battle', 'Epic Boss Battle', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWgu-AAkdqAGftsKzxWro541.mp3', 'mp3', '6651', 'music', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (33, '2019-05-11 16:10:07', '2019-05-11 16:10:07', 1, 'AWql8SrfrINGrgDTc05R', 'Evil Incoming', 'Evil Incoming', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWg1uAP4ecAGqhojpCigM742.mp3', 'mp3', '6824', 'music', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (34, '2019-05-11 16:13:08', '2019-05-11 16:13:08', 1, 'AWql8-4CrINGrgDTc05T', 'rpg_sound_pack', 'rpg音效包', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWhA6AW09xAL4vTYUe86E622.zip', 'zip', '12171', 'sound', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (35, '2019-05-11 16:16:37', '2019-05-11 16:16:37', 1, 'AWql9x5prINGrgDTc05W', 'spell', 'spell', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWhOGAeXeWAA0meP0MZAk889.wav', 'wav', '841', 'sound', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (36, '2019-05-11 16:22:30', '2019-05-11 16:22:30', 1, 'AWql_ID6rINGrgDTc05Z', '3d-nature-pack', '3d自然素材包', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWhkKALpRdABDkhtlzJJc414.zip', 'zip', '1081', 'game', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (37, '2019-05-11 16:28:19', '2019-05-11 16:28:19', 1, 'AWqmAdN6rINGrgDTc05b', 'vehicle_asset_v1', '车辆资源包', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWh5-AdA4TADXZczhxFR8779.zip', 'zip', '3446', 'game', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (38, '2019-05-11 16:30:22', '2019-05-11 16:30:22', 1, 'AWqmA7btrINGrgDTc05c', 'tiny weeds 3', '小杂草', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWiByAbmnyAHSEbp_ajLo8901.7z', '7z', '7457', 'game', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (39, '2019-05-11 16:40:42', '2019-05-11 16:40:42', 1, 'AWqmDSverINGrgDTc05e', '基础音效包', '基础音效', 'http://118.89.163.211:81/M00/00/00/Cml0hFzWinmAUQ4CABXB7kXQkno360.zip', 'zip', '1392', 'sound', NULL, NULL, 1, 0, 00000000000, 00000000000);
INSERT INTO `resource` VALUES (47, '2019-05-13 14:26:00', '2019-05-13 14:26:00', 1, 'AWqv3pazqDoOTQ4CL06Z', '测试', '测试', 'http://118.89.163.211:81/M00/00/00/Cml0hFzZDfSAGU29AAAMz60088c327.png', 'png', '3', 'picture', NULL, NULL, 1, 0, 00000000000, 00000000000);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(0) NOT NULL,
  `updated_at` datetime(0) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `classify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `enable` bit(1) NOT NULL,
  `failtime` int(11) DEFAULT NULL,
  `integral` int(11) DEFAULT NULL,
  `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_sb8bbouer5wak8vyiiy4pf2bx`(`username`) USING BTREE,
  UNIQUE INDEX `UK_ob8kqyqqgmefl0aco34akdtpe`(`email`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '2019-05-12 18:09:04', '2019-05-12 18:09:04', b'1', 'http://118.89.163.211:81/M00/00/00/Cml0hFzX0hSASyl7AAFsbn1GvGc143.png', NULL, NULL, '', b'1', 0, 0, '我是管理员', '管理员', '$2a$10$lBIvHEmMJj38wQefC9chEeGolKTuvM6MU.fjbOcBUa2DAmd.p5iC6', 1, '17729845523', 'admin');
INSERT INTO `user` VALUES (2, '2019-06-01 10:04:24', '2019-06-01 10:04:24', b'1', 'http://118.89.163.211:81/M00/00/00/Cml0hFzx3QaAOOcUAAF1Sppn4I4857.png', NULL, NULL, '1257207999@qq.com', b'1', 0, 0, '', 'CC0小白', '$2a$10$uWdDtUWHsqH2c7rqtYxPyOs/lINhPqq4.yGz5LBTueDEIxCN.bHNy', 1, '15281714097', 'dengyouquan');

SET FOREIGN_KEY_CHECKS = 1;
