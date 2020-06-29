USE `virsical_map`;

-- ----------------------------
-- 删除地图表蜂鸟地图字段
-- ----------------------------
ALTER TABLE `map` DROP COLUMN `fmap_key`;
ALTER TABLE `map` DROP COLUMN `fmap_id`;
ALTER TABLE `map` DROP COLUMN `fmap_app_name`;
ALTER TABLE `map` DROP COLUMN `fmap_server_url`;
ALTER TABLE `map` DROP COLUMN `fmap_start_point`;
ALTER TABLE `map` DROP COLUMN `fmap_name`;
ALTER TABLE `map` DROP COLUMN `fmap_upload_url`;

-- ----------------------------
-- 修改地图元素表字段可为空
-- ----------------------------
alter table `map_element` modify COLUMN `map_id` int(11) NULL DEFAULT NULL COMMENT '地图id';
alter table `map_element` modify COLUMN `svg_type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '素材类型标识';
alter table `map_element` modify COLUMN `svg_id` int(11) NULL DEFAULT NULL COMMENT '素材id';
alter table `map_element` modify COLUMN `custom_element` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义素材';
alter table `map_element` modify COLUMN `custom_element_width` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义素材宽度';
alter table `map_element` modify COLUMN `custom_element_height` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义素材高度';
alter table `map_element` modify COLUMN `map_web_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页面元素ID';

-- ----------------------------
-- 新增创互地图集成相关字段
-- ----------------------------
ALTER TABLE `map` ADD `cmap_project_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图项目ID';
ALTER TABLE `map` ADD `cmap_building_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图建筑ID';
ALTER TABLE `map` ADD `cmap_floor_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图楼层ID',
ALTER TABLE `map` ADD `cmap_cloud_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图云ID';
ALTER TABLE `map` ADD `cmap_accesstoken` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图提供的验证key';
ALTER TABLE `map` ADD `cmap_server` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图接口服务地址';
ALTER TABLE `map` ADD `cmap_config_param` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图配置参数';

-- ----------------------------
-- 新增通用素材类型
-- ----------------------------
INSERT INTO `svg_type` VALUES ('common', '其它', 0, '2019-05-08 00:00:00', NULL);