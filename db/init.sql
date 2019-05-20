
-- ----------------------------
-- Records of building
-- ----------------------------
INSERT INTO `building` VALUES (1, 1, 1, '1号楼', NULL, NULL, 0, '2019-05-08 09:41:36', '2019-05-16 17:28:18');
INSERT INTO `building` VALUES (2, 1, 1, '2号楼', NULL, NULL, 0, '2019-05-08 09:41:42', NULL);
INSERT INTO `building` VALUES (3, 1, 1, '3号楼', NULL, NULL, 0, '2019-05-08 09:41:45', NULL);
INSERT INTO `building` VALUES (4, 1, 2, '1号楼', NULL, NULL, 0, '2019-05-08 09:43:08', NULL);
INSERT INTO `building` VALUES (5, 1, 2, '2号楼', NULL, NULL, 0, '2019-05-08 09:43:10', NULL);


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
-- Records of map
-- ----------------------------
INSERT INTO `map` VALUES (1, 1, 1, 'string', 'string', NULL, 'aaaaaaa', 'string', 'string', 'string', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2019-05-08 17:42:50', NULL);
INSERT INTO `map` VALUES (2, 1, 3, 'string', 'string', NULL, 'bbbbb', 'string', 'string', 'string', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2019-05-08 17:43:15', '2019-05-13 16:24:10');
INSERT INTO `map` VALUES (3, 1, 7, 'string', 'string', NULL, 'cccc', 'string', 'string', 'string', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2019-05-09 10:48:45', '2019-05-13 16:24:13');


-- ----------------------------
-- Records of map_element
-- ----------------------------
INSERT INTO `map_element` VALUES (1, 1, 1, NULL, 'mapWebId', 'string', 'stationx', 'stationy', 'A1001', '会议室1', '#111111', '0', 'lineStartlineStart5', 'lineMidlineMid5', 'lineEndlineEnd5', NULL, '2019-05-13 18:10:27', '2019-05-16 17:24:57');
INSERT INTO `map_element` VALUES (2, 1, 2, NULL, 'mapWebId', 'string', 'stationx', 'stationy', 'A1002', '会议室2', '#222222', '0', 'lineStartlineStart6', 'lineMidlineMid6', 'lineEndlineEnd6', NULL, '2019-05-13 18:10:27', '2019-05-16 17:24:57');
INSERT INTO `map_element` VALUES (3, 1, 3, NULL, 'mapWebId', 'string', 'stationx', 'stationy', 'A1003', '会议室3', '#333333', '0', NULL, NULL, NULL, NULL, '2019-05-13 18:10:27', '2019-05-16 17:10:13');
INSERT INTO `map_element` VALUES (4, 1, 1, NULL, 'mapWebId', 'string', 'stationx', 'stationy', 'A1004', '会议室4', '#444444', '0', NULL, NULL, NULL, NULL, '2019-05-13 18:10:27', '2019-05-16 17:10:14');


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
-- Records of svg
-- ----------------------------
INSERT INTO `svg` VALUES (1, 1, 'meeting-room', '华山会议室', 'string', 'string', 'string', 'string', 'string', 'string', 0, 0, 0, '2019-05-08 16:54:27', '2019-05-08 17:02:12');
INSERT INTO `svg` VALUES (2, 1, 'meeting-room', '大明宫会议室', 'string', 'string', 'string', 'string', 'string', 'string', 0, 0, 0, '2019-05-08 16:55:09', NULL);
INSERT INTO `svg` VALUES (3, 1, 'meeting-room', '华清池会议室', 'string', 'string', 'string', 'string', 'string', 'string', 1, 0, 0, '2019-05-08 16:55:32', '2019-05-08 16:59:27');


-- ----------------------------
-- Records of svg_state
-- ----------------------------
INSERT INTO `svg_state` VALUES (1, 1, 1, '会议室预定', 'meeting-room-reserve', 'string', NULL, '2019-05-08 15:50:42', NULL);
INSERT INTO `svg_state` VALUES (2, 1, 1, '会议室使用中', 'meeting-room-used', 'string', NULL, '2019-05-08 15:51:28', '2019-05-13 16:24:28');


-- ----------------------------
-- Records of svg_type
-- ----------------------------
INSERT INTO `svg_type` VALUES ('meeting-room', '会议室', 0, '2019-05-08 15:39:56', NULL);
INSERT INTO `svg_type` VALUES ('workspace', '工位', 0, '2019-05-08 15:47:52', NULL);

