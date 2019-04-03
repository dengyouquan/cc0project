USE `cc0game`;

CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_at` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
  `update_at` datetime NOT NULL DEFAULT now() COMMENT '更新时间',
  -- 强制】表达是与否概念的字段，必须使用 is _ xxx 的方式命名，数据类型是 unsigned tinyint
  -- （ 1 表示是，0 表示否 ） 。
  `is_enabled`  tinyint unsigned DEFAULT NULL COMMENT '是否可用',
  `e_id`  tinyint unsigned DEFAULT NULL COMMENT 'elasticsearch ID',
  `file_name` varchar(32) NOT NULL DEFAULT '' COMMENT '文件名',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '图片描述',
  `file_path` varchar(64) NOT NULL DEFAULT '' COMMENT '文件路径',
  `file_format` varchar(16) NOT NULL DEFAULT '' COMMENT '文件格式',
  `file_size` varchar(16) NOT NULL DEFAULT '' COMMENT '文件大小',
  `type` varchar(8) DEFAULT NULL COMMENT '类型',
  `tags` varchar(128) DEFAULT NULL COMMENT '标签',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='图片信息表';

-- Innodb 支持事务处理与外键和行级锁

-- 用户信息
