USE `virsical_map`;

-- ----------------------------
-- 地图元素表新增序号字段
-- ----------------------------
ALTER TABLE `map_element` ADD `sort` int(11) NULL DEFAULT 0 COMMENT '序号';
