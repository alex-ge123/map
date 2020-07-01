DROP DATABASE IF EXISTS `virsical_map`;
CREATE DATABASE `virsical_map` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `virsical_map`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for map
-- ----------------------------
DROP TABLE IF EXISTS `map`;
CREATE TABLE `map`  (
  `map_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地图主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `floor_id` int(11) NOT NULL COMMENT '楼层id',
  `base_map_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地图底图url',
  `base_map_element` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'SVG内容',
  `view_box` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SVG属性',
  `zoom_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地图缩略图url',
  `area_age` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '面积',
  `height` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '高度',
  `width` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '宽度',
  `map_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '地图状态：0=正常，1=停用',
  `map_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '地图类型：0=基础版，1=专业版',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `cmap_project_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图项目ID',
  `cmap_building_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图建筑ID',
  `cmap_floor_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图楼层ID',
  `cmap_cloud_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图云ID',
  `cmap_accesstoken` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图提供的验证key',
  `cmap_server` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图接口服务地址',
  `cmap_config_param` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创互地图配置参数',
  PRIMARY KEY (`map_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '地图' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for map_element
-- ----------------------------
DROP TABLE IF EXISTS `map_element`;
CREATE TABLE `map_element`  (
  `map_element_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地图元素主键',
  `map_id` int(11) NULL DEFAULT NULL COMMENT '地图id',
  `svg_type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '素材类型标识',
  `svg_id` int(11) NULL DEFAULT 0 COMMENT '素材id',
  `custom_element` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义素材',
  `custom_element_width` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义素材宽度',
  `custom_element_height` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义素材高度',
  `map_web_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页面元素ID',
  `cycle_value` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '旋转角度',
  `stationx` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'X坐标',
  `stationy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Y坐标',
  `object_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定对象id',
  `object_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定对象名称',
  `object_color` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定对象颜色',
  `object_svg_state_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定对象svg状态标识',
  `line_start` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '引导线起点',
  `line_mid` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '引导线主体',
  `line_end` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '引导线终点',
  `extend` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展字段',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`map_element_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '地图元素' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for svg
-- ----------------------------
DROP TABLE IF EXISTS `svg`;
CREATE TABLE `svg`  (
  `svg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '素材主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `svg_type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '素材类型标识',
  `svg_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '素材名称',
  `svg_width` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图像宽度',
  `svg_height` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图像高度',
  `svg_element` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'SVG内容',
  `view_box` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SVG属性',
  `axisx` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文字坐标轴X',
  `axixy` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文字坐标轴Y',
  `direction` smallint(1) NOT NULL DEFAULT 0 COMMENT '文字方向',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '启用状态：0—启用，1—停用',
  `font_size` tinyint(2) NOT NULL DEFAULT 12 COMMENT '字体大小',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`svg_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '素材' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for svg_state
-- ----------------------------
DROP TABLE IF EXISTS `svg_state`;
CREATE TABLE `svg_state`  (
  `svg_state_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '素材状态主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `svg_id` int(11) NOT NULL COMMENT '素材id',
  `svg_state_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '素材状态名称',
  `svg_state_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '素材状态标识',
  `svg_state_element` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'SVG内容',
  `view_box` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SVG属性',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`svg_state_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '素材状态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for svg_type
-- ----------------------------
DROP TABLE IF EXISTS `svg_type`;
CREATE TABLE `svg_type`  (
  `svg_type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '素材类型标识',
  `svg_type_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '素材类型名称',
  `svg_type_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '素材类型状态，0-正常，1-停用',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`svg_type_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '素材类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of svg_type
-- ----------------------------
INSERT INTO `svg_type` VALUES ('meeting-room', '会议室', 0, '2019-05-08 00:00:00', NULL);
INSERT INTO `svg_type` VALUES ('workspace', '工位', 0, '2019-05-08 00:00:00', NULL);
INSERT INTO `svg_type` VALUES ('locker', '储物柜', 0, '2019-05-08 00:00:00', NULL);
INSERT INTO `svg_type` VALUES ('common', '其它', 0, '2019-05-08 00:00:00', NULL);

SET FOREIGN_KEY_CHECKS = 1;