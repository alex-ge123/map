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
-- 新增创互地图集成相关字段
-- ----------------------------
ALTER TABLE `map` ADD `cmap_project_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图项目ID';
ALTER TABLE `map` ADD `cmap_building_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图建筑ID';
ALTER TABLE `map` ADD `cmap_floor_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图楼层ID',
ALTER TABLE `map` ADD `cmap_cloud_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图云ID';
ALTER TABLE `map` ADD `cmap_accesstoken` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图提供的验证key';
ALTER TABLE `map` ADD `cmap_server` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图接口服务地址';
ALTER TABLE `map` ADD `cmap_config_param` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图配置参数';
