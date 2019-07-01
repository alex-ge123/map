
-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building`  (
  `building_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '楼宇主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `park_id` int(11) NOT NULL COMMENT '园区id',
  `building_name` varchar(50) NOT NULL COMMENT '办公楼名称,B1',
  `building_token` varchar(255) NULL DEFAULT NULL COMMENT '办公楼与一体机唯一对应',
  `building_img` varchar(2000) NULL DEFAULT NULL COMMENT '办公楼与楼宇外景图一一对应',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`building_id`)
) COMMENT = '楼宇';


-- ----------------------------
-- Table structure for floor
-- ----------------------------
DROP TABLE IF EXISTS `floor`;
CREATE TABLE `floor`  (
  `floor_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '楼层主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `building_id` int(11) NOT NULL COMMENT '楼宇id',
  `floor_num` varchar(20) NOT NULL COMMENT '楼层',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`floor_id`)
) COMMENT = '楼层';


-- ----------------------------
-- Table structure for map
-- ----------------------------
DROP TABLE IF EXISTS `map`;
CREATE TABLE `map`  (
  `map_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地图主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `floor_id` int(11) NOT NULL COMMENT '楼层id',
  `base_map_url` varchar(255) NULL DEFAULT NULL COMMENT '地图底图url',
  `base_map_element` text NULL COMMENT 'SVG内容',
  `view_box` varchar(50) NULL DEFAULT NULL COMMENT 'SVG属性',
  `zoom_image_url` varchar(255) NULL DEFAULT NULL COMMENT '地图缩略图url',
  `area_age` varchar(20) NULL DEFAULT NULL COMMENT '面积',
  `height` varchar(10) NULL DEFAULT NULL COMMENT '高度',
  `width` varchar(10) NULL DEFAULT NULL COMMENT '宽度',
  `map_state` tinyint(1) NULL DEFAULT NULL COMMENT '地图状态：0=正常，1=停用',
  `map_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '地图类型：默认0=wafer2D，1=蜂鸟map',
  `fmap_key` varchar(100) NULL DEFAULT NULL COMMENT '蜂鸟mapKey',
  `fmap_id` varchar(100) NULL DEFAULT NULL COMMENT '蜂鸟fmapID 即上传的文件名',
  `fmap_app_name` varchar(20) NULL DEFAULT NULL COMMENT '蜂鸟appName',
  `fmap_server_url` varchar(255) NULL DEFAULT NULL COMMENT '蜂鸟地图路径 无文件类型后缀',
  `fmap_start_point` varchar(255) NULL DEFAULT NULL COMMENT '蜂鸟地图路径起点',
  `fmap_name` varchar(100) NULL DEFAULT NULL COMMENT '蜂鸟地图名称',
  `fmap_upload_url` varchar(255) NULL DEFAULT NULL COMMENT '蜂鸟地图上传后地址',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`map_id`)
) COMMENT = '地图';


-- ----------------------------
-- Table structure for map_element
-- ----------------------------
DROP TABLE IF EXISTS `map_element`;
CREATE TABLE `map_element`  (
  `map_element_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地图元素主键',
  `map_id` int(11) NOT NULL COMMENT '地图id',
  `svg_type_code` varchar(50) NOT NULL DEFAULT '' COMMENT '素材类型标识',
  `svg_id` int(11) NOT NULL COMMENT '素材id',
  `custom_element` varchar(500) NULL DEFAULT NULL COMMENT '自定义素材',
  `map_web_id` varchar(50) NOT NULL COMMENT '页面元素ID',
  `cycle_value` varchar(30) NULL DEFAULT NULL COMMENT '旋转角度',
  `stationx` varchar(20) NULL DEFAULT NULL COMMENT 'X坐标',
  `stationy` varchar(20) NULL DEFAULT NULL COMMENT 'Y坐标',
  `object_id` varchar(20) NULL DEFAULT NULL COMMENT '绑定对象id',
  `object_name` varchar(20) NULL DEFAULT NULL COMMENT '绑定对象名称',
  `object_color` varchar(10) NULL DEFAULT NULL COMMENT '绑定对象颜色',
  `object_svg_state_code` varchar(50) NULL DEFAULT NULL COMMENT '绑定对象svg状态标识',
  `line_start` varchar(50) NULL DEFAULT NULL COMMENT '引导线起点',
  `line_mid` text NULL COMMENT '引导线主体',
  `line_end` varchar(50) NULL DEFAULT NULL COMMENT '引导线终点',
  `extend` varchar(100) NULL DEFAULT NULL COMMENT '扩展字段',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`map_element_id`)
) COMMENT = '地图元素';


-- ----------------------------
-- Table structure for park
-- ----------------------------
DROP TABLE IF EXISTS `park`;
CREATE TABLE `park`  (
  `park_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '园区主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `country` varchar(50) NULL DEFAULT '' COMMENT '国家标识',
  `country_name` varchar(50) NULL DEFAULT '' COMMENT '国家名称',
  `province` varchar(50) NULL DEFAULT '' COMMENT '省份标识',
  `province_name` varchar(50) NULL DEFAULT '' COMMENT '省份名称',
  `city` varchar(50) NULL DEFAULT '' COMMENT '城市标识',
  `city_name` varchar(50) NULL DEFAULT '' COMMENT '城市名称',
  `park_name` varchar(50) NOT NULL COMMENT '园区名字',
  `time_zone` varchar(50) NULL DEFAULT '' COMMENT '时区',
  `time_zone_name` varchar(50) NULL DEFAULT '' COMMENT '时区名称',
  `am_time` varchar(50) NULL DEFAULT '' COMMENT '上午时间段',
  `pm_time` varchar(50) NULL DEFAULT '' COMMENT '下午时间段',
  `all_day` varchar(50) NULL DEFAULT '' COMMENT '全天时间段',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`park_id`)
) COMMENT = '园区';


-- ----------------------------
-- Table structure for svg
-- ----------------------------
DROP TABLE IF EXISTS `svg`;
CREATE TABLE `svg`  (
  `svg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '素材主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `svg_type_code` varchar(50) NOT NULL COMMENT '素材类型标识',
  `svg_name` varchar(30) NOT NULL COMMENT '素材名称',
  `svg_width` varchar(10) NOT NULL COMMENT '图像宽度',
  `svg_height` varchar(10) NOT NULL COMMENT '图像高度',
  `svg_element` text NOT NULL COMMENT 'SVG内容',
  `view_box` varchar(50) NULL DEFAULT NULL COMMENT 'SVG属性',
  `axisx` varchar(10) NULL DEFAULT NULL COMMENT '文字坐标轴X',
  `axixy` varchar(10) NULL DEFAULT NULL COMMENT '文字坐标轴Y',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '启用状态：0—启用，1—停用',
  `font_size` tinyint(2) NOT NULL DEFAULT 12 COMMENT '字体大小',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`svg_id`)
) COMMENT = '素材';


-- ----------------------------
-- Table structure for svg_state
-- ----------------------------
DROP TABLE IF EXISTS `svg_state`;
CREATE TABLE `svg_state`  (
  `svg_state_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '素材状态主键',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  `svg_id` int(11) NOT NULL COMMENT '素材id',
  `svg_state_name` varchar(50) NOT NULL COMMENT '素材状态名称',
  `svg_state_code` varchar(50) NOT NULL COMMENT '素材状态标识',
  `svg_state_element` text NOT NULL COMMENT 'SVG内容',
  `view_box` varchar(50) NULL DEFAULT NULL COMMENT 'SVG属性',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`svg_state_id`)
) COMMENT = '素材状态';


-- ----------------------------
-- Table structure for svg_type
-- ----------------------------
DROP TABLE IF EXISTS `svg_type`;
CREATE TABLE `svg_type`  (
  `svg_type_code` varchar(50) NOT NULL COMMENT '素材类型标识',
  `svg_type_name` varchar(20) NOT NULL COMMENT '素材类型名称',
  `svg_type_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '素材类型状态，0-正常，1-停用',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`svg_type_code`)
) COMMENT = '素材类型';


SET FOREIGN_KEY_CHECKS = 1;
