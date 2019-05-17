DROP DATABASE IF EXISTS `virsical_map`;
CREATE DATABASE `virsical_map` default character set utf8mb4 collate utf8mb4_general_ci;

use virsical_map;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building`  (
  `building_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '楼宇主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `park_id` int(11) NOT NULL COMMENT '园区id',
  `building_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '办公楼名称,B1',
  `building_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '办公楼与一体机唯一对应',
  `building_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '办公楼与楼宇外景图一一对应',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`building_id`) USING BTREE
) ENGINE = InnoDB COMMENT = '楼宇' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of building
-- ----------------------------
INSERT INTO `building` VALUES (1, 1, 1, '1号楼', NULL, NULL, 0, '2019-05-08 09:41:36', '2019-05-16 17:28:18');
INSERT INTO `building` VALUES (2, 1, 1, '2号楼', NULL, NULL, 0, '2019-05-08 09:41:42', NULL);
INSERT INTO `building` VALUES (3, 1, 1, '3号楼', NULL, NULL, 0, '2019-05-08 09:41:45', NULL);
INSERT INTO `building` VALUES (4, 1, 2, '1号楼', NULL, NULL, 0, '2019-05-08 09:43:08', NULL);
INSERT INTO `building` VALUES (5, 1, 2, '2号楼', NULL, NULL, 0, '2019-05-08 09:43:10', NULL);

-- ----------------------------
-- Table structure for floor
-- ----------------------------
DROP TABLE IF EXISTS `floor`;
CREATE TABLE `floor`  (
  `floor_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '楼层主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `building_id` int(11) NOT NULL COMMENT '楼宇id',
  `floor_num` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '楼层',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`floor_id`) USING BTREE
) ENGINE = InnoDB COMMENT = '楼层' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of floor
-- ----------------------------
INSERT INTO `floor` VALUES (1, 1, 1, '1F', 0, '2019-05-08 10:02:43', '2019-05-13 16:23:35');
INSERT INTO `floor` VALUES (2, 1, 1, '2F', 0, '2019-05-08 10:02:45', '2019-05-13 16:23:38');
INSERT INTO `floor` VALUES (3, 1, 1, '3F', 0, '2019-05-08 10:02:50', '2019-05-13 16:23:39');
INSERT INTO `floor` VALUES (4, 1, 1, '4F', 0, '2019-05-08 10:02:53', '2019-05-13 16:23:40');
INSERT INTO `floor` VALUES (5, 1, 1, '5F', 0, '2019-05-08 10:02:56', '2019-05-13 16:23:41');
INSERT INTO `floor` VALUES (6, 1, 1, '6F', 0, '2019-05-08 10:03:00', '2019-05-13 16:23:42');
INSERT INTO `floor` VALUES (7, 1, 4, '1F', 0, '2019-05-08 17:47:35', '2019-05-13 16:23:43');
INSERT INTO `floor` VALUES (8, 1, 4, '2F', 0, '2019-05-08 17:47:42', '2019-05-13 16:23:44');
INSERT INTO `floor` VALUES (9, 1, 4, '3F', 0, '2019-05-08 17:47:45', '2019-05-13 16:23:45');

-- ----------------------------
-- Table structure for map
-- ----------------------------
DROP TABLE IF EXISTS `map`;
CREATE TABLE `map`  (
  `map_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地图主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `floor_id` int(11) NOT NULL COMMENT '楼层id',
  `base_map_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地图底图url',
  `base_map_element` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'SVG内容',
  `view_box` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SVG属性',
  `zoom_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地图缩略图url',
  `area_age` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '面积',
  `height` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '高度',
  `width` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '宽度',
  `map_state` tinyint(1) NULL DEFAULT NULL COMMENT '地图状态：0=正常，1=停用',
  `map_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '地图类型：默认0=wafer2D，1=蜂鸟map',
  `fmap_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蜂鸟mapKey',
  `fmap_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蜂鸟fmapID 即上传的文件名',
  `fmap_app_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蜂鸟appName',
  `fmap_server_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蜂鸟地图路径 无文件类型后缀',
  `fmap_start_point` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蜂鸟地图路径起点',
  `fmap_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蜂鸟地图名称',
  `fmap_upload_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蜂鸟地图上传后地址',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`map_id`) USING BTREE
) ENGINE = InnoDB COMMENT = '地图' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of map
-- ----------------------------
INSERT INTO `map` VALUES (1, 1, 1, 'string', 'string', NULL, 'aaaaaaa', 'string', 'string', 'string', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2019-05-08 17:42:50', NULL);
INSERT INTO `map` VALUES (2, 1, 3, 'string', 'string', NULL, 'bbbbb', 'string', 'string', 'string', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2019-05-08 17:43:15', '2019-05-13 16:24:10');
INSERT INTO `map` VALUES (3, 1, 7, 'string', 'string', NULL, 'cccc', 'string', 'string', 'string', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2019-05-09 10:48:45', '2019-05-13 16:24:13');

-- ----------------------------
-- Table structure for map_element
-- ----------------------------
DROP TABLE IF EXISTS `map_element`;
CREATE TABLE `map_element`  (
  `map_element_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地图元素主键',
  `map_id` int(11) NOT NULL COMMENT '地图id',
  `svg_id` int(11) NOT NULL COMMENT '素材id',
  `custom_element` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义素材',
  `map_web_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '页面元素ID',
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
) ENGINE = InnoDB COMMENT = '地图元素' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of map_element
-- ----------------------------
INSERT INTO `map_element` VALUES (1, 1, 1, NULL, 'mapWebId', 'string', 'stationx', 'stationy', 'A1001', '会议室1', '#111111', '0', 'lineStartlineStart5', 'lineMidlineMid5', 'lineEndlineEnd5', NULL, '2019-05-13 18:10:27', '2019-05-16 17:24:57');
INSERT INTO `map_element` VALUES (2, 1, 2, NULL, 'mapWebId', 'string', 'stationx', 'stationy', 'A1002', '会议室2', '#222222', '0', 'lineStartlineStart6', 'lineMidlineMid6', 'lineEndlineEnd6', NULL, '2019-05-13 18:10:27', '2019-05-16 17:24:57');
INSERT INTO `map_element` VALUES (3, 1, 3, NULL, 'mapWebId', 'string', 'stationx', 'stationy', 'A1003', '会议室3', '#333333', '0', NULL, NULL, NULL, NULL, '2019-05-13 18:10:27', '2019-05-16 17:10:13');
INSERT INTO `map_element` VALUES (4, 1, 1, NULL, 'mapWebId', 'string', 'stationx', 'stationy', 'A1004', '会议室4', '#444444', '0', NULL, NULL, NULL, NULL, '2019-05-13 18:10:27', '2019-05-16 17:10:14');

-- ----------------------------
-- Table structure for park
-- ----------------------------
DROP TABLE IF EXISTS `park`;
CREATE TABLE `park`  (
  `park_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '园区主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '国家标识',
  `country_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '国家名称',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '省份标识',
  `province_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '省份名称',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '城市标识',
  `city_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '城市名称',
  `park_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '园区名字',
  `time_zone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '时区',
  `time_zone_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '时区名称',
  `am_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '上午时间段',
  `pm_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '下午时间段',
  `all_day` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '全天时间段',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`park_id`) USING BTREE
) ENGINE = InnoDB COMMENT = '园区' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of park
-- ----------------------------
INSERT INTO `park` VALUES (1, 1, 'china', '', '', '', 'xian', '', '西安阿里园区', '', '', '', '', '', 0, '2019-05-08 09:40:27', '2019-05-16 11:16:14');
INSERT INTO `park` VALUES (2, 1, 'china', '', '', '', 'hangzhou', '', '杭州阿里园区', '', '', '', '', '', 0, '2019-05-08 09:40:43', '2019-05-14 15:02:12');
INSERT INTO `park` VALUES (3, 1, 'china', '', '', '', 'chengdu', '', '成都阿里园区', '', '', '', '', '', 0, '2019-05-08 09:40:52', '2019-05-14 15:02:12');
INSERT INTO `park` VALUES (9, 1, '', '', '', '', '', '', 'aaa', '', '', '', '', '', 0, '2019-05-16 14:49:32', NULL);
INSERT INTO `park` VALUES (10, 1, '', '', '', '', '', '', 'aaa', '', '', '', '', '', 0, '2019-05-16 14:51:19', NULL);
INSERT INTO `park` VALUES (11, 1, '', '', '', '', '', '', 'aaa', '', '', '', '', '', 0, '2019-05-16 14:51:27', NULL);

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
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '启用状态：0—启用，1—停用',
  `font_size` tinyint(2) NOT NULL DEFAULT 12 COMMENT '字体大小',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`svg_id`) USING BTREE
) ENGINE = InnoDB COMMENT = '素材' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of svg
-- ----------------------------
INSERT INTO `svg` VALUES (1, 1, 'meeting-room', '华山会议室', 'string', 'string', 'string', 'string', 'string', 'string', 0, 0, 0, '2019-05-08 16:54:27', '2019-05-08 17:02:12');
INSERT INTO `svg` VALUES (2, 1, 'meeting-room', '大明宫会议室', 'string', 'string', 'string', 'string', 'string', 'string', 0, 0, 0, '2019-05-08 16:55:09', NULL);
INSERT INTO `svg` VALUES (3, 1, 'meeting-room', '华清池会议室', 'string', 'string', 'string', 'string', 'string', 'string', 1, 0, 0, '2019-05-08 16:55:32', '2019-05-08 16:59:27');

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
) ENGINE = InnoDB COMMENT = '素材状态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of svg_state
-- ----------------------------
INSERT INTO `svg_state` VALUES (1, 1, 1, '会议室预定', 'meeting-room-reserve', 'string', NULL, '2019-05-08 15:50:42', NULL);
INSERT INTO `svg_state` VALUES (2, 1, 1, '会议室使用中', 'meeting-room-used', 'string', NULL, '2019-05-08 15:51:28', '2019-05-13 16:24:28');

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
) ENGINE = InnoDB COMMENT = '素材类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of svg_type
-- ----------------------------
INSERT INTO `svg_type` VALUES ('meeting-room', '会议室', 0, '2019-05-08 15:39:56', NULL);
INSERT INTO `svg_type` VALUES ('workspace', '工位', 0, '2019-05-08 15:47:52', NULL);

SET FOREIGN_KEY_CHECKS = 1;
