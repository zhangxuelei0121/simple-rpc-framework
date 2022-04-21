CREATE TABLE `t_service_name` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `service_name` varchar(128) NOT NULL DEFAULT '' COMMENT '服务名称',
  `host` varchar(32) NOT NULL DEFAULT 'localhost' COMMENT 'host',
  `port` int NOT NULL DEFAULT 3306 COMMENT '端口',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='注册中心表';