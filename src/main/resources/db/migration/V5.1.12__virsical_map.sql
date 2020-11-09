USE `virsical_map`;

-- ----------------------------
-- 加入绑定对象业务信息字段object_business
-- ----------------------------
ALTER TABLE `map_element` ADD `object_business` varchar(1000) NOT NULL DEFAULT '' COMMENT '绑定对象业务信息';

-- ----------------------------
-- 素材类型表新增序号、英文名、繁体名字段
-- ----------------------------
ALTER TABLE `svg_type` ADD `sort` int(11) NULL DEFAULT 0 COMMENT '序号';
ALTER TABLE `svg_type` ADD `svg_type_name_en` varchar(50) NULL DEFAULT '' COMMENT '素材类型英文名称';
ALTER TABLE `svg_type` ADD `svg_type_name_tw` varchar(20) NULL DEFAULT '' COMMENT '素材类型繁体名称';
-- 新增厕位素材类型
INSERT INTO `svg_type` VALUES ('toilet-cubicles', '厕位', 1, NULL, NULL, 4, 'Toilet cubicles', '廁位');
-- 修改素材类型序号
UPDATE `svg_type` SET sort = 1, svg_type_name_en = 'Meeting room', svg_type_name_tw = '會議室' WHERE svg_type_code = 'meeting-room';
UPDATE `svg_type` SET sort = 2, svg_type_name_en = 'Workspace', svg_type_name_tw = '工位' WHERE svg_type_code = 'workspace';
UPDATE `svg_type` SET sort = 3, svg_type_name_en = 'Locker', svg_type_name_tw = '儲物櫃' WHERE svg_type_code = 'locker';
UPDATE `svg_type` SET sort = 99, svg_type_name_en = 'Other', svg_type_name_tw = '其它' WHERE svg_type_code = 'common';

