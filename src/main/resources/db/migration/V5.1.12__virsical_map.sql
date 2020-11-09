USE `virsical_map`;

-- ----------------------------
-- 素材类型表新增序号字段
-- ----------------------------
ALTER TABLE `svg_type` ADD `sort` int(11) NULL DEFAULT 0 COMMENT '序号';
-- 新增厕位素材类型
INSERT INTO `svg_type` VALUES ('toilet-cubicles', '厕位', 1, NULL, NULL, 4);
-- 修改素材类型序号
UPDATE `svg_type` SET sort = 1 WHERE svg_type_code = 'meeting-room';
UPDATE `svg_type` SET sort = 2 WHERE svg_type_code = 'workspace';
UPDATE `svg_type` SET sort = 3 WHERE svg_type_code = 'locker';
UPDATE `svg_type` SET sort = 99 WHERE svg_type_code = 'common';

